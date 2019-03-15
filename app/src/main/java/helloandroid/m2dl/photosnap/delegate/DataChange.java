package helloandroid.m2dl.photosnap.delegate;

import helloandroid.m2dl.photosnap.SensorAcceleration;
import helloandroid.m2dl.photosnap.Direction;

public interface DataChange {

    void dataDidChange(SensorAcceleration capteur, Direction dir);
}
