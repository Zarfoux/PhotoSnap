package helloandroid.m2dl.photosnap.domain;

import android.graphics.Paint;
import android.graphics.Rect;

import helloandroid.m2dl.photosnap.helpers.Paints;
import helloandroid.m2dl.photosnap.helpers.Square;

public class Exit extends Entity {

    private Square square;

    public Exit(Square square) {
        super(Paints.getDefaultExitPaint());
        this.square = square;
    }

    public Exit(Paint paint, Square square) {
        super(paint);
        this.square = square;
    }

    public Rect getRect() {
        return square.getRect();
    }

}
