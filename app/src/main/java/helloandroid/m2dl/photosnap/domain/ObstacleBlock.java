package helloandroid.m2dl.photosnap.domain;

import android.graphics.Paint;
import android.graphics.Rect;

import helloandroid.m2dl.photosnap.helpers.Paints;

public class ObstacleBlock extends Obstacle {

    public ObstacleBlock(Rect rect) {
        super(Paints.getDefaultObstacleBlockPaint(), rect);
    }

    public ObstacleBlock(Paint paint, Rect rect) {
        super(paint, rect);
    }
}
