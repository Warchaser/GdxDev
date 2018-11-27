package com.warchaser.gdx.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetManagerUtils {

    private static AssetManagerUtils mInstance;
    private AssetManager mAssetManager;
    /**
     * 纹理图集
     * */
    private TextureAtlas mTextureAtlas;

    private AssetManagerUtils(){
        mAssetManager = new AssetManager();

        mAssetManager.load(ResConstant.Atlas.ATLAS_PATH, TextureAtlas.class);
        mAssetManager.load(ResConstant.Audios.AUDIO_DIE, Sound.class);
        mAssetManager.load(ResConstant.Audios.AUDIO_HIT, Sound.class);
        mAssetManager.load(ResConstant.Audios.AUDIO_TOUCH, Sound.class);
        mAssetManager.load(ResConstant.Audios.AUDIO_RESTART, Sound.class);
        mAssetManager.load(ResConstant.Audios.AUDIO_SCORE, Sound.class);

        mAssetManager.load(ResConstant.FPS_BITMAP_FONT_PATH, BitmapFont.class);

        mAssetManager.finishLoading();

        mTextureAtlas = mAssetManager.get(ResConstant.Atlas.ATLAS_PATH, TextureAtlas.class);
    }

    public static AssetManagerUtils getInstance(){

        if(mInstance == null){
            synchronized (AssetManagerUtils.class){
                if(mInstance == null){
                    mInstance = new AssetManagerUtils();
                }
            }
        }

        return mInstance;

    }

    public AssetManager getAssetManager(){
        return mAssetManager;
    }

    public TextureAtlas getTextureAtlas(){
        return mTextureAtlas;
    }

    public void dispose(){
        if(mAssetManager != null){
            mAssetManager.dispose();
            mAssetManager = null;
        }
    }

}
