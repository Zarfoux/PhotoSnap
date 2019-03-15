package helloandroid.m2dl.photosnap.domain;

import android.graphics.Paint;
import android.graphics.Rect;

import helloandroid.m2dl.photosnap.helpers.Paints;

public class ObstacleDeath extends Obstacle {

    public ObstacleDeath(Rect rect) {
        super(Paints.getDefaultObstacleDeathPaint(), rect);
    }

    public ObstacleDeath(Paint paint, Rect rect) {
        super(paint, rect);
    }
}
