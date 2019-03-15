package helloandroid.m2dl.photosnap.helpers;

import android.graphics.Rect;

public class Square {

    private int sideLength;
    private int left;
    private int top;

    public Square(int sideLength, int left, int top) {
        this.sideLength = sideLength;
        this.left = left;
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getSideLength() {
        return sideLength;
    }

    public void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }

    public Rect getRect() {
        return new Rect(left, top, left + sideLength, top + sideLength);
    }

}
