/*******************************************************************************
 * Unpublished work, Copyright 2013, Clear Channel Communications, All Rights Reserved
 ******************************************************************************/

package com.example.livewallpaper.util;

import java.util.HashMap;
import java.util.Map;
import com.example.livewallpaper.theme.DefaultTheme;
import com.example.livewallpaper.theme.PinkTheme;
import com.example.livewallpaper.theme.WallpaperTheme;


/**
 * 
 */
public class PreferenceHelper {
    
    private final static Map<String, WallpaperTheme> themes;
    static {
        themes = new HashMap<String, WallpaperTheme>();
        themes.put("Pink", new PinkTheme());
        themes.put("Default", new DefaultTheme());
    }
    
    private PreferenceHelper() {
    }
    
    public static WallpaperTheme getTheme(String name) {
        return themes.get(name);
    }
}
