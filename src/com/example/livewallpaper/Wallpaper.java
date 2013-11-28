package com.example.livewallpaper;

import com.example.livewallpaper.model.Scene;
import com.example.livewallpaper.render.SimpleSceneRenderer;
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
            
            // create the scene
            scene = new Scene();

            // start animation thread; thread starts paused
            // will run onVisibilityChanged
            controller = new WallpaperController(surfaceHolder, scene, new SimpleSceneRenderer(), null);
            animationThread = new Thread(controller);
            
            // load and register for updates to settings
            SharedPreferences sharedPrefs = getSharedPreferences(WallpaperSettingsActivity.SETTINGS_KEY, 0);
            sharedPrefs.registerOnSharedPreferenceChangeListener(controller);
            // Required on creation to set values to selection
            controller.onSharedPreferenceChanged(sharedPrefs, "theme_color");

            
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
