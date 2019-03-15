package helloandroid.m2dl.photosnap.helpers;

import android.graphics.Rect;

public class Square {

    private int sideLength;
    private int top;
    private int left;

    public Square(int sideLength, int top, int left) {
        this.sideLength = sideLength;
        this.top = top;
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
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
