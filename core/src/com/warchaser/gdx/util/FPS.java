package com.warchaser.gdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class FPS implements Disposable {

    private SpriteBatch mSpriteBatch;

    private BitmapFont mFpsBitmapFont;

    /**
     * 文本高度占屏幕高度的比例
     * */
    private static final float OCCUPY_HEIGHT_RATIO = 14.0f / ResConstant.FIX_WORLD_WIDTH;

    /**
     * 显示的文本偏移右下角的x轴和y轴的比例（相对于字体高度的比例）
     * */
    private static final float DISPLAY_ORIGIN_OFFSET_RATIO = 0.5f;

    private float mX;
    private float mY;

    public FPS(BitmapFont font, int fontRawPixelSize){
        initialize(font, fontRawPixelSize);
    }

    private void initialize(BitmapFont font, int fontRawPixelSize){
        mSpriteBatch = new SpriteBatch();
        mFpsBitmapFont = font;

        float height = Gdx.graphics.getHeight();
        float scale = (height * OCCUPY_HEIGHT_RATIO) / (float) fontRawPixelSize;
        float scaledFontSize = fontRawPixelSize * scale;
        float offset = scaledFontSize * DISPLAY_ORIGIN_OFFSET_RATIO;
        mX = scaledFontSize - offset;
        mY = scaledFontSize * 1.85f - offset;
        mFpsBitmapFont.getData().setScale(scale);
    }

    public void renderFps(){
        mSpriteBatch.begin();
        mFpsBitmapFont.draw(mSpriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), mX, mY);
        mSpriteBatch.end();
    }

    @Override
    public void dispose() {
        if(mSpriteBatch != null){
            mSpriteBatch.dispose();
            mSpriteBatch = null;
        }
    }
}
