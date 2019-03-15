package helloandroid.m2dl.photosnap.domain;

import android.graphics.Paint;

import helloandroid.m2dl.photosnap.Direction;
import helloandroid.m2dl.photosnap.helpers.Paints;

public class Ball extends Entity {

    private float cx;
    private float cy;
    private Direction dir ;
    private boolean moving = false;
    private float radius;




    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }



    public Ball(float cx, float cy, float radius) {
        super(Paints.getDefaultBallPaint());
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }

    public Ball(Paint paint, float cx, float cy, float radius) {
        super(paint);
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void addToCx( int val){
        this.cx = this.cx + val;
    }

    public void subToCx (int val){
        this.cx = this.cx - val;
    }

    public void addToCy( int val){
        this.cy = this.cy + val;
    }

    public void subToCy( int val){
        this.cy = this.cy - val;
    }
}
