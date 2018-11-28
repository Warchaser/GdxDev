package com.warchaser.gdx.stage;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.warchaser.gdx.actor.ResultGroup;
import com.warchaser.gdx.actor.framework.ImageActor;
import com.warchaser.gdx.stage.base.BaseStage;
import com.warchaser.gdx.util.AssetManagerUtils;
import com.warchaser.gdx.util.ResConstant;

public class GameOverStage extends BaseStage {

    /**
     * 游戏结束提示
     * */
    private ImageActor mGameOverTipActor;
    /**
     * 重新开始按钮
     * */
    private ImageButton mBtnRestart;

    private ResultGroup mResultGroup;

    private OnButtonClickListener mOnButtonClickListener;

    public GameOverStage(Viewport viewport) {
        super(viewport);
        initialize();
    }

    private void initialize(){

        TextureAtlas atlas = AssetManagerUtils.getInstance().getTextureAtlas();

        mGameOverTipActor = new ImageActor(atlas.findRegion(ResConstant.Atlas.IMAGE_GAME_OVER));
        mGameOverTipActor.setCenterX(getWidth() / 2);
        mGameOverTipActor.setTopY(getHeight() - 150);
        addActor(mGameOverTipActor);

        mResultGroup = new ResultGroup();
        mResultGroup.setCenterX(getWidth() / 2);
        mResultGroup.setTopY(mGameOverTipActor.getY() - 30);
        addActor(mResultGroup);

        mBtnRestart = new ImageButton(
                new TextureRegionDrawable(atlas.findRegion(ResConstant.Atlas.IMAGE_GAME_START_01_TO_02, 1)),
                new TextureRegionDrawable(atlas.findRegion(ResConstant.Atlas.IMAGE_GAME_START_01_TO_02, 2))
        );

        mBtnRestart.setX(getWidth() / 2 - mBtnRestart.getWidth() / 2);
        mBtnRestart.setY(getHeight() / 2 - mBtnRestart.getHeight() / 2);
        addActor(mBtnRestart);

        mBtnRestart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(mOnButtonClickListener != null){
                    mOnButtonClickListener.onRestartClick();
                }
            }
        });
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        mOnButtonClickListener = listener;
    }

    public interface OnButtonClickListener{

        void onRestartClick();

    }

    public void setCurrentScore(int score){
        if(mResultGroup != null){
            mResultGroup.refreshThisTimeScore(score);
        }
    }
}
