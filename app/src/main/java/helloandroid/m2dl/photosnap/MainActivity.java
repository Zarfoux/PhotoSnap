package helloandroid.m2dl.photosnap;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import helloandroid.m2dl.photosnap.delegate.DataChange;

public class MainActivity extends AppCompatActivity  implements DataChange {

    private SensorManager sensorManager;
    private  TextView dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dir =  findViewById(R.id.dir);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        CapteurAcceleration capt = new CapteurAcceleration(sensorManager,this);
        sensorManager.registerListener(capt,capt.getSensor(), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void dataDidChange(CapteurAcceleration capteur, Direction direction) {


        dir.setText(direction.toString());

    }
}
