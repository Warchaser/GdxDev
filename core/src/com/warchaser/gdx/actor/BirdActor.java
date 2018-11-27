package com.warchaser.gdx.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.warchaser.gdx.actor.base.BaseAnimationActor;
import com.warchaser.gdx.util.AssetManagerUtils;
import com.warchaser.gdx.util.GameState;
import com.warchaser.gdx.util.ResConstant;

public class BirdActor extends BaseAnimationActor {

    /**
     * 当前游戏状态
     * */
    private GameState mGameState;

    /**
     * 小鸟垂直方向上的速度
     * */
    private float mVelocityY;

    public BirdActor() {
        super();
        Animation animation = new Animation(
                0.2f,
                AssetManagerUtils.getInstance().getTextureAtlas().findRegions(ResConstant.Atlas.IMAGE_BIRD_YELLOW_01_TO_03)
        );

        animation.setPlayMode(Animation.PlayMode.LOOP);

        setAnimation(animation);

        refreshFrameAndRotation(GameState.READY);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(mGameState == GameState.FLY || mGameState == GameState.DIE){
            /*
             * 应用物理效应（简单模拟物理效果, 帧率较低时物理效果的误差可能较大）
             * v = v0 + a * t
             * s = v0 * t + a * t^2
             */
            // 递增速度
            mVelocityY += ResConstant.Physics.GRAVITY * delta;

            // 递增位移
            setY(getY() + mVelocityY * delta);
        }

        if(mGameState == GameState.FLY){
            changeBirdRotation(delta);
        }
    }

    public void refreshFrameAndRotation(GameState state){
        if(state == null || mGameState == state){
            return;
        }

        mGameState = state;

        switch (mGameState){
            case READY:
                // 准备状态循环播放动画, 帧持续时间为 0.2 秒
                setIsPlayAnimation(true);
                setRotation(0);
                setFrameDuration(0.2f);
                break;
            case FLY:
                // 准备状态循环播放动画, 帧持续时间为 0.18 秒
                setIsPlayAnimation(true);
                setFrameDuration(0.18f);
                break;
            case DIE:
                break;
            case GAMEOVER:
                // 游戏结束状态停止播放动画, 并固定显示第1帧
                setIsPlayAnimation(false);
                setFixedShowKeyFrameIndex(1);
                setRotation(-75);
                break;
            default:
                break;
        }
    }

    public float getVelocityY() {
        return mVelocityY;
    }

    public void setVelocityY(float velocityY) {
        this.mVelocityY = velocityY;
    }

    private void changeBirdRotation(float delta){
        float rotation = getRotation();
        float scale = mVelocityY * delta;
        rotation += scale;

        if(mVelocityY > 0){
            // 向上飞时稍微加大角度旋转的速度
            rotation += scale * 1.5f;
        } else {
            // 向下飞时稍微减小角度旋转的速度
            rotation += scale * 0.2f;
        }

        // 校准旋转角度: -75 <= rotation <= 45
        if(rotation < -75){
            rotation = -75;
        } else if(rotation > 45){
            rotation = 45;
        }

        setRotation(rotation);
    }

}
