package helloandroid.m2dl.photosnap.domain;

import java.util.ArrayList;
import java.util.List;

public class GameContext {

    private Ball ball;

    private Exit exit;

    private List<Obstacle> obstacles = new ArrayList<>();

    public GameContext(Ball ball, Exit exit) {
        this.ball = ball;
        this.exit = exit;
    }

    public GameContext(Ball ball, Exit exit, List<Obstacle> obstacles) {
        this.ball = ball;
        this.exit = exit;
        this.obstacles = obstacles;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Exit getExit() {
        return exit;
    }

    public void setExit(Exit exit) {
        this.exit = exit;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void addObstacle(Obstacle obstacle) {
        this.obstacles.add(obstacle);
    }

}
