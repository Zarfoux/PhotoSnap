package helloandroid.m2dl.photosnap;

import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import helloandroid.m2dl.photosnap.delegate.DataChange;
import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.domain.Obstacle;
import helloandroid.m2dl.photosnap.domain.ObstacleBlock;
import helloandroid.m2dl.photosnap.helpers.Square;
import helloandroid.m2dl.photosnap.views.GameView;
import helloandroid.m2dl.photosnap.views.GameView;

public class MainActivity extends AppCompatActivity  implements DataChange {

    private SensorManager sensorManager;
    private  TextView dir;

    private GameView gameView;

    private Handler mHandler = new Handler();;

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            gameView.invalidate();
            mHandler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.game_view);

        System.out.println(gameView);

        Ball ball = new Ball(500, 128, 64);
        ball.setDir(Direction.NONE);


        ObstacleBlock obstacle = new ObstacleBlock(new Rect(500,400,800,800));
        Exit exit = new Exit(new Square(128, 800, 800));
        GameContext gameContext = new GameContext(ball, exit);
        gameContext.getObstacles().add(obstacle);

        gameView.setGameContext(gameContext);
        gameView.invalidate();



        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        SensorAcceleration capt = new SensorAcceleration(sensorManager,this);
        sensorManager.registerListener(capt,capt.getSensor(), SensorManager.SENSOR_DELAY_FASTEST);

        mHandler.postDelayed(mUpdateTimeTask,3000);
    }

    @Override
    public void dataDidChange(SensorAcceleration capteur, Direction direction) {

        
        Ball ball = gameView.getGameContext().getBall();
        //Si la balle ne bouge pas on l'a fait avancer dans la direction du capteur
        if ( Direction.NONE.equals(ball.getDir()) ) {
            ball.setMoving(true);
            ball.setDir(direction);
        }

    }
}
