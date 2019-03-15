package helloandroid.m2dl.photosnap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import helloandroid.m2dl.photosnap.views.GameView;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = findViewById(R.id.game_view);

        setContentView(R.layout.activity_main);
    }
}
