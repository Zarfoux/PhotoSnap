package helloandroid.m2dl.photosnap;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import helloandroid.m2dl.photosnap.delegate.DataChange;

public class CapteurAcceleration implements SensorEventListener {

    protected SensorManager sm;
    protected Sensor sensor;
    protected DataChange delegate;

    protected int x ;
    protected int y;

    public CapteurAcceleration(SensorManager sm, DataChange delegate) {
        this.sm = sm;
        this.delegate = delegate;
        this.sensor = this.sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }




    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        x = (int) values[0];
        y = (int) values[1];
        Direction dir;

        if ( x >0  && Math.abs(x) >Math.abs(y) )  {
            dir = Direction.LEFT;
        } else if ( y < 0 && Math.abs(x) < Math.abs(y) ) {
            dir = Direction.TOP;
        } else if (  x < 0 && Math.abs(x) >Math.abs(y))
            dir = Direction.RIGHT;
        else  if  ( x== 0 & y ==0) {
            dir = Direction.NONE;
        }else  if (Math.abs(x)==Math.abs(y) & y > 0) {
            dir = Direction.BOTTOM;
        }else if ( y > 0  && Math.abs(x) < Math.abs(y)) {
            dir = Direction.BOTTOM;
        } else {
            dir = Direction.TOP;
        }

        delegate.dataDidChange(this, dir);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
