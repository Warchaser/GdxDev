package com.warchaser.gdx.actor.base;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.warchaser.gdx.actor.framework.AnimationActor;

public abstract class BaseAnimationActor extends AnimationActor {

    public BaseAnimationActor(){
    }

    public BaseAnimationActor(Animation animation){
        super(animation);
    }

    public BaseAnimationActor(float frameDuration, Array<? extends TextureRegion> keyFrames){
        super(frameDuration, keyFrames);
    }

    public BaseAnimationActor(float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
    }

}
