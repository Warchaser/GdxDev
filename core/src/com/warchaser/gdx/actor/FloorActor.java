package com.warchaser.gdx.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warchaser.gdx.actor.base.BaseImageActor;
import com.warchaser.gdx.util.AssetManagerUtils;
import com.warchaser.gdx.util.ResConstant;

public class FloorActor extends BaseImageActor {

    /**
     * 地面水平移动速度 px/s
     * */
    private float mMoveVelocity;

    /**
     * 地板文理区域
     * */
    private TextureRegion mTextureRegion;

    /**
     * 水平偏移量
     * */
    private float mOffsetX;

    /**
     * 地板是否再移动
     * */
    private boolean mIsFloorMoving;

    public FloorActor() {
        super();
        mTextureRegion = AssetManagerUtils.getInstance().getTextureAtlas().findRegion(ResConstant.Atlas.IMAGE_GAME_FLOOR);
        setBounds(0,0,mTextureRegion.getRegionWidth(), mTextureRegion.getRegionHeight());
    }

    public float getMoveVelocity(){
        return mMoveVelocity;
    }

    public void setMoveVelocity(float velocity){
        mMoveVelocity = velocity;
    }

    public boolean isFloorMoving(){
        return mIsFloorMoving;
    }

    public void setIsFloorMoving(boolean isMoving){
        mIsFloorMoving = isMoving;
    }

    @Override
    public void act(float delta) {
        if(mIsFloorMoving){
            mOffsetX += (delta * mMoveVelocity);
            mOffsetX %= getWidth();

            if(mOffsetX > 0){
                mOffsetX -= getWidth();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // 绘制两次以达到视觉上的循环移动效果
        batch.draw(mTextureRegion, getX() + mOffsetX, getY());
        if(Math.abs(mOffsetX) > 0.001f){
            batch.draw(mTextureRegion, getX() + (getWidth() + mOffsetX), getY());
        }
    }
}
