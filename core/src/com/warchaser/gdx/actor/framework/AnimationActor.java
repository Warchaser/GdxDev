package com.warchaser.gdx.actor.framework;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AnimationActor extends ImageActor{

    private Animation mAnimation;

    private boolean mIsPlayAnimation = true;

    /**
     * 不播放动画时固定显示的帧索引
     * */
    private int mFixedShowKeyFrameIndex;

    /**
     * 时间步 delta 的累加值
     * */
    private float mStateTime;

    public AnimationActor(){

    }

    public AnimationActor(float frameDuration, Array<? extends TextureRegion> keyFrames){
        this(new Animation(frameDuration, keyFrames));
    }

    public AnimationActor(float frameDuration, TextureRegion... keyFrames){
        this(new Animation(frameDuration, keyFrames));
    }

    public AnimationActor(Animation animation) {
        setAnimation(animation);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(mAnimation != null){
            TextureRegion region = null;
            if(mIsPlayAnimation){
                // 如果需要播放动画, 则累加时间步, 并按累加值获取需要显示的关键帧
                mStateTime += delta;
                region = (TextureRegion) mAnimation.getKeyFrame(mStateTime);
            } else {
                // 不需要播放动画, 则获取 fixedShowKeyFrameIndex 指定的关键帧
                TextureRegion[] keyFrames = (TextureRegion[]) mAnimation.getKeyFrames();
                if(mFixedShowKeyFrameIndex >= 0 && mFixedShowKeyFrameIndex < keyFrames.length){
                    region = keyFrames[mFixedShowKeyFrameIndex];
                }
            }

            setTextureRegion(region);
        }
    }

    protected void setFrameDuration(float frameDuration){
        if(mAnimation != null){
            mAnimation.setFrameDuration(frameDuration);
        }
    }

    protected void setAnimation(Animation animation){
        mAnimation = animation;
        if(mAnimation != null){
            Object[] keyFrames = mAnimation.getKeyFrames();
            if(keyFrames.length > 0){
                setTextureRegion((TextureRegion) keyFrames[0]);
            }
        }
    }

    public void setIsPlayAnimation(boolean playOrNot){
        mIsPlayAnimation = playOrNot;
    }

    public void setFixedShowKeyFrameIndex(int fixedShowKeyFrameIndex){
        mFixedShowKeyFrameIndex = fixedShowKeyFrameIndex;
    }
}
