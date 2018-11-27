package com.warchaser.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.warchaser.gdx.MainGame;
import com.warchaser.gdx.stage.GameOverStage;
import com.warchaser.gdx.stage.MainGameStage;

/**
 * 主游戏场景
 * */
public class MainGameScreen extends ScreenAdapter {

    private MainGame mMainGame;

    private MainGameStage mMainGameStage;

    private GameOverStage mGameOverStage;

    public MainGameScreen(MainGame game){
        this.mMainGame = game;
        initialize();
    }

    private void initialize(){

        mMainGameStage = new MainGameStage(new StretchViewport(mMainGame.getWorldWidth(), mMainGame.getWorldHeight()));

        mMainGameStage.setOnStateListener(new MainGameStage.OnStateListener() {
            @Override
            public void onOver() {
                showGameOverStage();
            }
        });

        mGameOverStage = new GameOverStage(new StretchViewport(mMainGame.getWorldWidth(), mMainGame.getWorldHeight()));
        mGameOverStage.setStageVisible(false);

        mGameOverStage.setOnButtonClickListener(new GameOverStage.OnButtonClickListener() {
            @Override
            public void onRestartClick() {
                restartReadyGame();
            }
        });

        Gdx.input.setInputProcessor(mMainGameStage);
    }

    private void showGameOverStage(){
        mGameOverStage.setStageVisible(true);

        Gdx.input.setInputProcessor(mGameOverStage);

//        mGameOverStage.
    }

    private void restartReadyGame(){
        mGameOverStage.setStageVisible(false);

        Gdx.input.setInputProcessor(mMainGameStage);

        mMainGameStage.setReady();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(mMainGameStage.isStageVisible()){
            mMainGameStage.act();
            mMainGameStage.draw();
        }

        if(mGameOverStage.isStageVisible()){
            mGameOverStage.act();
            mGameOverStage.draw();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if(mMainGameStage != null){
            mMainGameStage.dispose();
        }

        if(mGameOverStage != null){
            mGameOverStage.dispose();
        }
    }
}
