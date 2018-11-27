package com.warchaser.gdx.actor;

import com.badlogic.gdx.utils.Pool;
import com.warchaser.gdx.actor.base.BaseImageActor;
import com.warchaser.gdx.util.AssetManagerUtils;
import com.warchaser.gdx.util.ResConstant;

public class BarActor extends BaseImageActor implements Pool.Poolable {

    /**
     * 是否是上方水管
     * */
    private boolean mIsTopBar;

    /**
     * 是否已被小鸟通过
     * */
    private boolean mIsPassedByBird = false;

    private boolean mIsMoving;

    @Override
    public void reset() {
        setIsMoving(false);
        setIsPassedByBird(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        //如果水管正在移动，递增水管的水平移动位置
        if(mIsMoving){
            setX(getX() + ResConstant.Physics.MOVE_VELOCITY * delta);
        }
    }

    public boolean getIsTopBar(){
        return mIsTopBar;
    }

    public void setIsTopBar(boolean isTopBar){
        mIsTopBar = isTopBar;
        if(mIsTopBar){
            setTextureRegion(AssetManagerUtils.getInstance().getTextureAtlas().findRegion(ResConstant.Atlas.IMAGE_BAR_UP));
        } else {
            setTextureRegion(AssetManagerUtils.getInstance().getTextureAtlas().findRegion(ResConstant.Atlas.IMAGE_BAR_DOWN));
        }
    }

    public boolean isPassedByBird(){
        return mIsPassedByBird;
    }

    public void setIsPassedByBird(boolean isPassedByBird){
        mIsPassedByBird = isPassedByBird;
    }

    public boolean isMoving(){
        return mIsMoving;
    }

    public void setIsMoving(boolean isMoving){
        mIsMoving = isMoving;
    }
}
