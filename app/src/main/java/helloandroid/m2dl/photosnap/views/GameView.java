package helloandroid.m2dl.photosnap.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import helloandroid.m2dl.photosnap.Direction;
import helloandroid.m2dl.photosnap.delegates.GameDelegate;
import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.domain.Obstacle;
import helloandroid.m2dl.photosnap.domain.ObstacleBlock;

public class GameView extends View {

    private GameContext gameContext;

    private GameDelegate delegate;

    private Rect rect;

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


        for (Obstacle obstacle : obstacles)
            canvas.drawRect(obstacle.getRect(), obstacle.getPaint());

        //Si la balle bouge on avance
        if (ball.isMoving()) {
            Direction direction = ball.getDir();
            //si elle touche pas de mur
            //boolean a = hitBoard(ball);
            boolean b = hitObstacles(ball);

            if (!hitObstacles(ball)) {

                if (Direction.TOP.equals(direction)) {
                    ball.subToCy(8);
                } else if (Direction.BOTTOM.equals(direction)) {
                    ball.addToCy(8);
                } else if (Direction.RIGHT.equals(direction)) {
                    ball.addToCx(8);
                } else if (Direction.LEFT.equals(direction)) {
                    ball.subToCx(8);
                }
                canvas.drawCircle(ball.getCx(), ball.getCy(), ball.getRadius(), ball.getPaint());
            } else if (inExit(ball)) {


            } else {

                canvas.drawCircle(ball.getCx(), ball.getCy(), ball.getRadius(), ball.getPaint());
                ball.setMoving(false);
                ball.setDir(Direction.NONE);
            }

        } else if (!ball.isMoving()) {
            canvas.drawCircle(ball.getCx(), ball.getCy(), ball.getRadius(), ball.getPaint());
        }


    }

    private boolean hitObstacles(Ball ball) {

        for (Obstacle o : gameContext.getObstacles()) {
            if (o instanceof ObstacleBlock) {
                if (Rect.intersects(ball.rectCollision(), o.getRect())) {
                    return true;
                }
            } else {
                //Perdu
            }
        }
        return false;
    }

    private boolean inExit(Ball ball) {

        if (Rect.intersects(ball.rectCollision(), gameContext.getExit().getRect())) {
            return true;
        }
        return  false;
    }

}
