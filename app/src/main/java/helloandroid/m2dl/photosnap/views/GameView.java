package helloandroid.m2dl.photosnap.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import helloandroid.m2dl.photosnap.Direction;
import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.domain.Obstacle;

public class GameView extends View {

    private GameContext gameContext;
    private boolean init = true;
    private boolean obstacle = false;
    public GameContext getGameContext() {
        return gameContext;
    }


    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
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
        if ( ball.isMoving()) {
            Direction direction = ball.getDir();
            //si elle touche pas de mur
            //boolean a = hitBoard(ball);
            boolean b= hitObstacles(ball);

            if ( !hitObstacles(ball)) {

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
            } else {
                canvas.drawCircle(ball.getCx(), ball.getCy(), ball.getRadius(), ball.getPaint());
                ball.setMoving(false);
                ball.setDir(Direction.NONE);
            }

        }else if( !ball.isMoving() ){
            canvas.drawCircle(ball.getCx(), ball.getCy(), ball.getRadius(), ball.getPaint());
        }


    }

    private boolean hitBoard (Ball ball){

        int cercleRight = (int) ((int)ball.getCx()+ball.getRadius());
        int cercleLeft = (int) ((int)ball.getCx()-ball.getRadius());
        int cercleTop= (int) ((int)ball.getCy()-ball.getRadius());
        int cercleBottom = (int) ((int)ball.getCy()+ball.getRadius());

        Rect rectCircle = new Rect(cercleLeft,cercleTop,cercleRight,cercleBottom);

        return  ( cercleRight > getRight()  || cercleTop > getTop()
                ||  cercleBottom >getBottom()+5 ||  cercleLeft > getLeft());

    }

    private  boolean hitObstacles (Ball ball){

        for ( Obstacle o : gameContext.getObstacles()){
            if ( Rect.intersects(ball.rectCollision(),o.getRect())) {

                return  true;
            }
        }
        return  false;

    }



}
