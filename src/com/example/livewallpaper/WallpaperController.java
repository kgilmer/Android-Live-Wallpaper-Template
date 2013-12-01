package com.example.livewallpaper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import com.example.livewallpaper.model.Scene;
import com.example.livewallpaper.render.SceneRenderer;
import com.example.livewallpaper.theme.WallpaperTheme;

public class WallpaperController implements Runnable, OnSharedPreferenceChangeListener {

    private static final String TAG = "AnimationThread";

    private final Object pauseLock = new Object();

    private boolean running = true;
    private boolean paused = true;

    private final int fps = 30;
    private final int timeFrame = 1000 / fps; // drawing time frame in milliseconds 1000 ms / fps

    private final SurfaceHolder surfaceHolder;
    private final SceneRenderer sceneRenderer;
    private final Scene scene;
    private WallpaperTheme theme;

    WallpaperController(SurfaceHolder surfaceHolder, Scene sceneModel, SceneRenderer sceneRenderer, WallpaperTheme theme) {
        this.surfaceHolder = surfaceHolder;
        this.scene = sceneModel;
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

                scene.update();
                sceneRenderer.draw(scene, theme, canvas);

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
        theme = WallpaperTheme.getTheme(sharedPreferences);
        scene.onThemeChanged(theme);
    }
}
