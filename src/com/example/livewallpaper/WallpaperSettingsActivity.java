package com.example.livewallpaper;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class WallpaperSettingsActivity extends PreferenceActivity {

    public static final String SETTINGS_KEY = "live_wallpaper_settings";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // tell the preference API what file name should be used
        getPreferenceManager().setSharedPreferencesName(SETTINGS_KEY);

        // load the preferences from an XML resource
        addPreferencesFromResource(R.xml.wallpaper_settings);
        setContentView(R.layout.act_wallpaper_settings);

    }

}
