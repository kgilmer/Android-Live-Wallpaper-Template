package com.example.livewallpaper;

import com.example.livewallpaper.model.Scene;
import com.example.livewallpaper.render.FlatSceneRenderer;
import com.example.livewallpaper.theme.DefaultTheme;
import com.example.livewallpaper.theme.WallpaperTheme;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class Wallpaper extends WallpaperService {

    private static final String TAG = "Wallpaper";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }

    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine();
    }

    class WallpaperEngine extends Engine {

        private static final String TAG = "WallpaperEngine";

        private Thread animationThread;
        private Scene scene;

        private WallpaperController controller;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            Log.d(TAG, "onCreate");
            
            // load preferences
            SharedPreferences sharedPrefs = getSharedPreferences(WallpaperSettingsActivity.SETTINGS_KEY, 0);
            
            // create the scene with default theme
            scene = new Scene(WallpaperTheme.getTheme(sharedPrefs));

            // start animation thread; thread starts paused
            // will run onVisibilityChanged
            controller = new WallpaperController(surfaceHolder, scene, new FlatSceneRenderer(), null);
            animationThread = new Thread(controller);
            
            // Register controller for updates to settings
            sharedPrefs.registerOnSharedPreferenceChangeListener(controller);
            
            animationThread.start();
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");

            controller.stopThread();
            joinThread(animationThread);
            animationThread = null;

            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.d(TAG, "onVisibilityChanged: " + (visible ? "visible" : "invisible"));
            if (visible) {
                controller.resumeThread();
            } else {
                controller.pauseThread();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            Log.d(TAG, "onSurfaceChanged: width: " + width + ", height: " + height);

            scene.onSurfaceChanged(width, height);

        }

        private void joinThread(Thread thread) {
            boolean retry = true;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
