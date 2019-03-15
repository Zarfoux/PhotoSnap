package helloandroid.m2dl.photosnap.domain;

import android.graphics.Rect;

public abstract class Obstacle extends Entity {

    private Rect rect;

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

}
