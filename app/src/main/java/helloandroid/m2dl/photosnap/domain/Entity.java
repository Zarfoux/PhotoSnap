package helloandroid.m2dl.photosnap.domain;

import android.graphics.Paint;

public abstract class Entity {

    private Paint paint;

    Entity(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

}
