package helloandroid.m2dl.photosnap;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

import helloandroid.m2dl.photosnap.delegates.GameDelegate;
import helloandroid.m2dl.photosnap.helpers.GameContextBuilder;
import helloandroid.m2dl.photosnap.views.GameView;

public class MainActivity extends AppCompatActivity implements GameDelegate {

    private GameView gameView;
    private PopupWindow popWindow;

    private int width;
    private int height;

    private Rect gameViewRect;

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

        gameView.setDelegate(this);

        gameView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                gameViewRect = new Rect(gameView.getLeft(), gameView.getTop(), gameView.getRight(), gameView.getBottom());
                gameView.setGameContext(GameContextBuilder.buildGameContext(gameViewRect, 1,1));
                gameView.invalidate();
            }
        });

        onShowPopupMenu(mainView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            //popWindow.showAtLocation(findViewById(R.id.game_view), Gravity.CENTER, 0,90);
        }
    }

    /*
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    */

    public void onShowPopupMenu(View v) {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(R.layout.menu_popup, null,false);

        popWindow = new PopupWindow(inflatedView, width - 256, height - 480, false);

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
    }

}
