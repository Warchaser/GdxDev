package com.warchaser.gdx.stage.base;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BaseStage extends Stage {

    /**
     * 舞台是否可见
     * */
    private boolean mIsStageVisible = true;

    public BaseStage(Viewport viewport){
        super(viewport);
    }

    public boolean isStageVisible(){
        return mIsStageVisible;
    }

    public void setStageVisible(boolean isStageVisible){
        mIsStageVisible = isStageVisible;
    }

}
