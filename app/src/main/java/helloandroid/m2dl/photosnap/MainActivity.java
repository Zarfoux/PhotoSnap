package helloandroid.m2dl.photosnap;

import android.content.Context;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.helpers.Square;
import helloandroid.m2dl.photosnap.views.GameView;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;
    private PopupWindow popWindow;

    private int width;
    private int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        setContentView(R.layout.activity_main);

        ConstraintLayout mainView = findViewById(R.id.main_layout);

        gameView = findViewById(R.id.game_view);

        Ball ball = new Ball(128, 128, 64);
        Exit exit = new Exit(new Square(128, gameView.getTop(), 800));
        GameContext gameContext = new GameContext(ball, exit);

        gameView.setGameContext(gameContext);
        gameView.invalidate();

        onShowPopupMenu(mainView);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            popWindow.showAtLocation(findViewById(R.id.game_view), Gravity.CENTER, 0,90);
        }
    }

    public void onShowPopupMenu(View v) {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(R.layout.menu_popup, null,false);

        popWindow = new PopupWindow(inflatedView, width - 256, height - 480, true);

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
    }

}
