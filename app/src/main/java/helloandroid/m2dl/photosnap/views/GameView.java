package helloandroid.m2dl.photosnap.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import helloandroid.m2dl.photosnap.delegates.GameDelegate;
import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.domain.Obstacle;

public class GameView extends View {

    private GameContext gameContext;

    private GameDelegate delegate;

    private Rect rect;

    private boolean init = true;

    public GameContext getGameContext() {
        return gameContext;
    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public GameDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(GameDelegate delegate) {
        this.delegate = delegate;
    }

    public GameView(Context context) {
        super(context);
        commonInit();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        commonInit();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        commonInit();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        commonInit();
    }

    public void commonInit() {

    }

    @Override
    public void onDraw(Canvas canvas) {
        if (gameContext != null)
            drawGameContext(canvas);
    }

    private void drawGameContext(Canvas canvas) {
        Ball ball = gameContext.getBall();
        Exit exit = gameContext.getExit();
        List<Obstacle> obstacles = gameContext.getObstacles();

        canvas.drawRect(exit.getRect(), exit.getPaint());

        canvas.drawCircle(ball.getCx(), ball.getCy(), ball.getRadius(), ball.getPaint());

        for (Obstacle obstacle : obstacles) {
            canvas.drawRect(obstacle.getRect(), obstacle.getPaint());
        }
    }

}
