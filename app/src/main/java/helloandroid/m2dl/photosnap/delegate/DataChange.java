package helloandroid.m2dl.photosnap.delegate;

import helloandroid.m2dl.photosnap.CapteurAcceleration;
import helloandroid.m2dl.photosnap.Direction;

public interface DataChange {

    void dataDidChange(CapteurAcceleration capteur, Direction dir);
}
