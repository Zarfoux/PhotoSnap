package helloandroid.m2dl.photosnap.delegates;

import helloandroid.m2dl.photosnap.domain.GameContext;

public interface GameDelegate {

    void onWin(GameContext gameContext);
    void onLose(GameContext gameContext);

}
