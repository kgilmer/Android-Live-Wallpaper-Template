package com.example.livewallpaper;

import com.example.livewallpaper.model.SceneModel;
import com.example.livewallpaper.render.SceneRenderer;
import com.example.livewallpaper.theme.DefaultTheme;
import com.example.livewallpaper.theme.PinkTheme;
import com.example.livewallpaper.theme.WallpaperTheme;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class WallpaperController implements Runnable, OnSharedPreferenceChangeListener {

    private static final String TAG = "AnimationThread";

    private final Object pauseLock = new Object();

    private boolean running = true;
    private boolean paused = true;

    private final int fps = 30;
    private final int timeFrame = 1000 / fps; // drawing time frame in miliseconds 1000 ms / fps

    private final SurfaceHolder surfaceHolder;
    private final SceneRenderer sceneRenderer;
    private final SceneModel sceneModel;
    private WallpaperTheme theme;

    WallpaperController(SurfaceHolder surfaceHolder, SceneModel sceneModel, SceneRenderer sceneRenderer, WallpaperTheme theme) {
        this.surfaceHolder = surfaceHolder;
        this.sceneModel = sceneModel;
        this.sceneRenderer = sceneRenderer;
        this.theme = theme;
    }

    @Override
    public void run() {

        while (running) {

            waitOnPause();

            if (!running) {
                return;
            }

            long beforeDrawTime = System.currentTimeMillis();

            Canvas canvas = null;
            try {

                canvas = surfaceHolder.lockCanvas();

                /** Workaround for: SurfaceTextureClient: dequeueBuffer failed (No such device) */
                if (canvas == null) {
                    continue;
                }

                sceneModel.update();
                sceneRenderer.draw(sceneModel, theme, canvas);

            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Error during surfaceHolder.lockCanvas()", e);
                stopThread();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (IllegalArgumentException e) {
                        Log.e(TAG, "Error during unlockCanvasAndPost()", e);
                        stopThread();
                    }
                }
            }

            long afterDrawTime = System.currentTimeMillis() - beforeDrawTime;
            try {
                if (timeFrame > afterDrawTime) {
                    Thread.sleep(timeFrame - afterDrawTime);
                }
            } catch (InterruptedException ex) {
                Log.e(TAG, "Exception during Thread.sleep().", ex);
            }

        }

    }

    public void stopThread() {
        synchronized (pauseLock) {
            paused = false;
            running = false;
            pauseLock.notifyAll();
        }
        Log.d(TAG, "Stopped thread (" + Thread.currentThread().getId() + ")");
    }

    public void pauseThread() {
        synchronized (pauseLock) {
            paused = true;
        }
        Log.d(TAG, "Paused thread (" + Thread.currentThread().getId() + ")");
    }

    public void resumeThread() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
        Log.d(TAG, "Resumed thread (" + Thread.currentThread().getId() + ")");
    }

    private void waitOnPause() {
        synchronized (pauseLock) {
            while (paused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("theme_color")) {
            if (sharedPreferences.getString(key, "Default").equals("Pink")) {
                theme = new PinkTheme();
            } else {
                theme = new DefaultTheme();
            }
        }
    }

}
