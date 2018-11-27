package com.warchaser.gdx.actor.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.warchaser.gdx.actor.framework.ImageActor;

public abstract class BaseImageActor extends ImageActor {


    public BaseImageActor(){

    }

    public BaseImageActor(TextureRegion region) {
        super(region);
    }
}
