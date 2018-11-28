package com.warchaser.gdx.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.warchaser.gdx.actor.base.BaseGroup;
import com.warchaser.gdx.actor.framework.AnimationActor;
import com.warchaser.gdx.actor.framework.ImageActor;
import com.warchaser.gdx.util.AssetManagerUtils;
import com.warchaser.gdx.util.ResConstant;

public class ResultGroup extends BaseGroup {

    /**
     * 本次得分对象
     * */
    private ResultScoreActor mScoreThisTimeActor;

    /**
     * 最高得分对象
     * */
    private ResultScoreActor mScoreHighestActor;

    private AnimationActor mMedalActor;

    private final int RIGHT_X = 290;

    private final int PLATINUM  = 40;
    private final int GOLD = 30;
    private final int SILVER = 20;
    private final int COPPER = 10;

    public ResultGroup(){
        initialize();
    }

    private void initialize(){

        ImageActor scoreResultBgActor = new ImageActor(AssetManagerUtils.getInstance().getTextureAtlas().findRegion(ResConstant.Atlas.IMAGE_GAME_RESULT_BG));
        addActor(scoreResultBgActor);
        setSize(scoreResultBgActor.getWidth(), scoreResultBgActor.getHeight());

        mMedalActor = new AnimationActor(0, AssetManagerUtils.getInstance().getTextureAtlas().findRegions(ResConstant.Atlas.IMAGE_GAME_MEDAL_01_TO_04));
        mMedalActor.setPosition(44, 45);
        mMedalActor.setVisible(false);
        mMedalActor.setIsPlayAnimation(false);
        addActor(mMedalActor);

        mScoreThisTimeActor = new ResultScoreActor();
        mScoreThisTimeActor.setRightX(RIGHT_X);
        mScoreThisTimeActor.setY(95);
        addActor(mScoreThisTimeActor);

        mScoreHighestActor = new ResultScoreActor();
        mScoreHighestActor.setRightX(RIGHT_X);
        mScoreHighestActor.setY(35);
        addActor(mScoreHighestActor);

        refreshHighestScore(false, 0);
    }

    public void refreshThisTimeScore(int score){
        if(score < 0){
            score = 0;
        }

        mScoreThisTimeActor.setCurrentNumber(score);
        mScoreThisTimeActor.setRightX(RIGHT_X);

        Preferences preferences = Gdx.app.getPreferences(ResConstant.Prefs.PREFS_FILE_NAME);
        if(score > preferences.getInteger(ResConstant.Prefs.KEY_BEST_SCORE, 0)){
            preferences.putInteger(ResConstant.Prefs.KEY_BEST_SCORE, score).flush();

            refreshHighestScore(true, score);
        }

        mMedalActor.setVisible(true);
        if(score >= PLATINUM){
            mMedalActor.setFixedShowKeyFrameIndex(3);
        } else if(score >= GOLD){
            mMedalActor.setFixedShowKeyFrameIndex(2);
        } else if(score >= SILVER){
            mMedalActor.setFixedShowKeyFrameIndex(1);
        } else if(score >= COPPER){
            mMedalActor.setFixedShowKeyFrameIndex(0);
        } else {
            mMedalActor.setVisible(false);
        }
    }

    private void refreshHighestScore(boolean isThisTimeHighest, int score){
        int highestScore;
        if(isThisTimeHighest){
            highestScore = score;
        } else {
            Preferences preferences = Gdx.app.getPreferences(ResConstant.Prefs.PREFS_FILE_NAME);
            highestScore = preferences.getInteger(ResConstant.Prefs.KEY_BEST_SCORE);
        }

        mScoreHighestActor.setCurrentNumber(highestScore);
        mScoreHighestActor.setRightX(RIGHT_X);

    }

}
