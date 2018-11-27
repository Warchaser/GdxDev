package com.warchaser.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.warchaser.gdx.util.AssetManagerUtils;
import com.warchaser.gdx.util.ResConstant;

public class CurrentScoreActor extends NumberGroup{

    public CurrentScoreActor(){
        Array<TextureAtlas.AtlasRegion> regions = AssetManagerUtils.getInstance().getTextureAtlas().findRegions(ResConstant.Atlas.IMAGE_NUM_BIG_00_TO_09);
        TextureRegion[] digitRegions = new TextureRegion[regions.size];
        for(int i = 0; i < regions.size; i++){
            digitRegions[i] = regions.get(i);
        }

        setDigitRegions(digitRegions);
    }

}
