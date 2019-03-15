package helloandroid.m2dl.photosnap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.helpers.Square;
import helloandroid.m2dl.photosnap.views.GameView;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gameView = findViewById(R.id.game_view);

        System.out.println(gameView);

        Ball ball = new Ball(128, 128, 64);
        Exit exit = new Exit(new Square(128, 800, 800));
        GameContext gameContext = new GameContext(ball, exit);

        gameView.setGameContext(gameContext);
        gameView.invalidate();
    }
}
