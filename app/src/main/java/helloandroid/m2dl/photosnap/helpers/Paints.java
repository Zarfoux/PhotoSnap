package helloandroid.m2dl.photosnap.helpers;

import android.graphics.Color;
import android.graphics.Paint;

public class Paints {

    public static Paint getDefaultBallPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        return paint;
    }

    public static Paint getDefaultExitPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);

        return paint;
    }

    public static Paint getDefaultObstacleBlockPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);

        return paint;
    }

    public static Paint getDefaultObstacleDeathPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        return paint;
    }

}
