package helloandroid.m2dl.photosnap.helpers;

import android.graphics.Path;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import helloandroid.m2dl.photosnap.Direction;
import helloandroid.m2dl.photosnap.domain.Ball;
import helloandroid.m2dl.photosnap.domain.Exit;
import helloandroid.m2dl.photosnap.domain.GameContext;
import helloandroid.m2dl.photosnap.domain.Obstacle;
import helloandroid.m2dl.photosnap.domain.ObstacleBlock;
import helloandroid.m2dl.photosnap.domain.ObstacleDeath;

public class GameContextBuilder {

    public static GameContext buildGameContext(Rect rect, int difficulty, int borderType) {
        int borderWidth = 16;

        Random rand = new Random();

        Ball ball = new Ball(rand.nextInt(rect.right - 128 - borderWidth * 2) + rect.left + 64 + borderWidth, rand.nextInt(300) + rect.top + borderWidth + 64, 64);
        ball.setDir(Direction.NONE);
        ball.setMoving(false);
        Square square = new Square(128, rand.nextInt(rect.right - 256 - borderWidth * 2) + rect.left + 128 + borderWidth, rect.bottom - rand.nextInt(236) - borderWidth - 128);
        Exit exit = new Exit(square);

        List<Obstacle> obstacles = new ArrayList<>();

        Rect topBorder = new Rect(rect.left, rect.top, rect.right, rect.top + borderWidth);
        Rect leftBorder = new Rect(rect.left, rect.top, rect.left + borderWidth, rect.bottom);
        Rect rightBorder = new Rect(rect.right - borderWidth, rect.top, rect.right, rect.bottom);
        Rect bottomBorder = new Rect(rect.left, rect.bottom - borderWidth, rect.right, rect.bottom);

        switch (borderType) {
            case 0:
                obstacles.add(new ObstacleBlock(topBorder));
                obstacles.add(new ObstacleBlock(leftBorder));
                obstacles.add(new ObstacleBlock(rightBorder));
                obstacles.add(new ObstacleBlock(bottomBorder));
                break;
            case 1:
                obstacles.add(new ObstacleBlock(topBorder));
                obstacles.add(new ObstacleBlock(leftBorder));
                obstacles.add(new ObstacleBlock(rightBorder));
                obstacles.add(new ObstacleBlock(bottomBorder));
                break;
            case 2:
                obstacles.add(new ObstacleBlock(topBorder));
                obstacles.add(new ObstacleBlock(leftBorder));
                obstacles.add(new ObstacleBlock(rightBorder));
                obstacles.add(new ObstacleBlock(bottomBorder));
                break;
            default:
                obstacles.add(new ObstacleDeath(topBorder));
                obstacles.add(new ObstacleDeath(leftBorder));
                obstacles.add(new ObstacleDeath(rightBorder));
                obstacles.add(new ObstacleDeath(bottomBorder));
                break;
        }

        int width;
        int height;
        int left;
        int top;

        Rect randomBlockObstacle;
        Rect randomDeathObstacle;

        switch (difficulty) {
            case 0:
                break;
            case 1:
                width = rand.nextInt(256) + 64;
                height = rand.nextInt(256) + 64;

                left = rand.nextInt(rect.right - width - borderWidth * 2) + rect.left + borderWidth;
                top = rand.nextInt(rect.bottom - 760 - height) + 380 + rect.top;

                randomBlockObstacle = new Rect(left, top, left + width, top + height);
                obstacles.add(new ObstacleBlock(randomBlockObstacle));
                break;
            default:
                for (int i = 0; i < difficulty - 1; ++i) {
                    width = rand.nextInt(256) + 64;
                    height = rand.nextInt(256) + 64;

                    left = rand.nextInt(rect.right - width - borderWidth * 2) + rect.left + borderWidth;
                    top = rand.nextInt(rect.bottom - 760 - height) + 380 + rect.top;

                    randomBlockObstacle = new Rect(left, top, left + width, top + height);
                    obstacles.add(new ObstacleBlock(randomBlockObstacle));


                    width = rand.nextInt(256) + 64;
                    height = rand.nextInt(256) + 64;

                    left = rand.nextInt(rect.right - width - borderWidth * 2) + rect.left + borderWidth;
                    top = rand.nextInt(rect.bottom - 760 - height) + 380 + rect.top;

                    randomDeathObstacle = new Rect(left, top, left + width, top + height);
                    obstacles.add(new ObstacleDeath(randomDeathObstacle));
                }
                break;
        }

        return new GameContext(ball, exit, obstacles, difficulty);
    }

}
