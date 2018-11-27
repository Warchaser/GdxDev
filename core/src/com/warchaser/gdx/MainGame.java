package com.warchaser.gdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.warchaser.gdx.screen.MainGameScreen;
import com.warchaser.gdx.util.AssetManagerUtils;
import com.warchaser.gdx.util.FPS;
import com.warchaser.gdx.util.ResConstant;

public class MainGame extends Game {

    /**
     * 是否显示帧率
     * */
    private static final boolean IS_SHOW_FPS = true;

    /**
     * 世界宽度
     * */
    private float mWorldWidth;

    /**
     * 世界高度
     * */
    private float mWorldHeight;

    private FPS mFps;

    @Override
    public void create() {

        Gdx.app.setLogLevel(Application.LOG_ERROR);

        mWorldWidth = ResConstant.FIX_WORLD_WIDTH;
        mWorldHeight = Gdx.graphics.getHeight() * mWorldWidth / Gdx.graphics.getWidth();

        setScreen(new MainGameScreen(this));

        //显示帧率所需要的位图字体
        mFps = new FPS(
                AssetManagerUtils.getInstance().getAssetManager().get(ResConstant.FPS_BITMAP_FONT_PATH, BitmapFont.class),
                ResConstant.FPS_FONT_SIZE
        );
    }

    public float getWorldWidth(){
        return mWorldWidth;
    }

    public float getWorldHeight(){
        return mWorldHeight;
    }

    @Override
    public void render() {

        //黑色清屏
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        mFps.renderFps();
    }

    @Override
    public void dispose() {
        super.dispose();

        AssetManagerUtils.getInstance().dispose();

        if(mFps != null){
            mFps.dispose();
            mFps = null;
        }
    }


}
