/*******************************************************************************
 * Unpublished work, Copyright 2013, Clear Channel Communications, All Rights Reserved
 ******************************************************************************/

package com.example.livewallpaper.model;

/**
 * 
 */
public interface SceneModel {

    public abstract void onSurfaceChanged(int width, int height);

    public abstract void update();

    /**
     * @return
     */
    public abstract float outerCircleRadius();

    /**
     * @return
     */
    public abstract float getCenterX();

    /**
     * @return
     */
    public abstract float getCenterY();

    /**
     * @return
     */
    public abstract float circleY();

    /**
     * @return
     */
    public abstract float circleX();

    /**
     * @return
     */
    public abstract float circleRadius();

}