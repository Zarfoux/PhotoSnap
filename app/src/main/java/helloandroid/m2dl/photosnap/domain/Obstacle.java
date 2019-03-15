package helloandroid.m2dl.photosnap.domain;

import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Obstacle extends Entity {

    private Rect rect;

    Obstacle(Paint paint, Rect rect) {
        super(paint);
        this.rect = rect;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

}
