package com.warchaser.gdx.stage;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.warchaser.gdx.actor.BarActor;
import com.warchaser.gdx.actor.BirdActor;
import com.warchaser.gdx.actor.CurrentScoreActor;
import com.warchaser.gdx.actor.FloorActor;
import com.warchaser.gdx.actor.framework.ImageActor;
import com.warchaser.gdx.stage.base.BaseStage;
import com.warchaser.gdx.util.AssetManagerUtils;
import com.warchaser.gdx.util.CollisionUtils;
import com.warchaser.gdx.util.GameState;
import com.warchaser.gdx.util.ResConstant;

import java.util.ArrayList;

public class MainGameStage extends BaseStage {

    /**
     * 顶部当前分数对象
     * */
    private CurrentScoreActor mCurrentScoreActor;

    /**
     * 地板对象
     * */
    private FloorActor mFloorActor;

    /**
     * 小鸟对象
     * */
    private BirdActor mBirdActor;

    /**
     * 点击提示对象
     * */
    private ImageActor mTapTipActor;

    /**
     * 小鸟初始时的y轴坐标
     * */
    private float mBirdActorOriginPositionY;

    /**
     * 当前界面可见的所有水管集合
     * */
    private final ArrayList<BarActor> mBarActors = new ArrayList<BarActor>();

    /**
     * 水管对象缓冲池，因为水管需要频繁地生成和移除，所以使用缓冲池来减少对象的频繁创建
     * */
    private Pool<BarActor> mBarActorPool;

    /**
     * 底部水管的TopY坐标的最小值
     * */
    private float mMinBottomBarTopY;

    /**
     * 底部水管的TopY坐标的最大值
     * */
    private float mMaxBottomBarTopY;

    /**
     * 距离下次生成水管的时间累加器
     * */
    private float mNextBarGeneratedTimeCounter;

    /**
     * 点击屏幕音效
     * */
    private Sound mTouchSound;

    /**
     * 撞击水管声音
     * */
    private Sound mHitSound;

    /**
     * 得分声音
     * */
    private Sound mScoreSound;

    /**
     * 撞击地板声音
     * */
    private Sound mDeadSound;

    private GameState mGameState;

    private OnStateListener mOnStateListener;

    public MainGameStage(Viewport viewport) {
        super(viewport);
        initialize();
    }

    private void initialize(){

        ImageActor backgroundActor = new ImageActor(AssetManagerUtils.getInstance().getTextureAtlas().findRegion(ResConstant.Atlas.IMAGE_GAME_BG));
        backgroundActor.setCenter(getWidth() / 2, getHeight() / 2);
        addActor(backgroundActor);

        mFloorActor = new FloorActor();
        // 设置地板移动速度
        mFloorActor.setMoveVelocity(ResConstant.Physics.MOVE_VELOCITY);
        // 水平居中
        mFloorActor.setCenterX(getWidth() / 2);
        // 兼容性设置纵坐标（为了兼容不同尺寸的屏幕）
        mFloorActor.setTopY(Math.min(
                    mFloorActor.getHeight(),
                    getHeight() * (mFloorActor.getHeight() / 800.0f)
                )
        );
        addActor(mFloorActor);

        mCurrentScoreActor = new CurrentScoreActor();
        mCurrentScoreActor.setCenterX(getWidth() / 2);
        mCurrentScoreActor.setTopY(getHeight() - 50);
        addActor(mCurrentScoreActor);

        mTapTipActor = new ImageActor(AssetManagerUtils.getInstance().getTextureAtlas().findRegion(ResConstant.Atlas.IMAGE_GAME_TAP_TIP));
        mTapTipActor.setCenterX(getWidth() / 2);
        mTapTipActor.setCenterY(getHeight() / 2);
        addActor(mTapTipActor);

        mBirdActor = new BirdActor();
        mBirdActor.setX(mTapTipActor.getX() - 20);
        mBirdActor.setY(mTapTipActor.getY() + 80);
        mBirdActor.setScale(1.2f);
        // 将缩放和旋转的支点设置到小鸟中心
        mBirdActor.setOrigin(Align.center);
        addActor(mBirdActor);

        //将地板设置到小鸟前面(ZIndex必须再对象添加到舞台后设置才有效， ZIndex越大显示越靠前)
        mFloorActor.setZIndex(mBirdActor.getZIndex());
        mCurrentScoreActor.setZIndex(getRoot().getChildren().size - 1);

        mBirdActorOriginPositionY = mBirdActor.getY();

        //水管
        mBarActorPool = Pools.get(BarActor.class, 10);

        //计算下方水管TopY坐标值范围，水管高度为400
        float barHeight = 400;
        float maxRegion = 300;

        mMinBottomBarTopY = Math.max(mFloorActor.getTopY() + 40, getHeight() - barHeight - ResConstant.Physics.INTERVAL_BAR);
        mMaxBottomBarTopY = Math.min(mMinBottomBarTopY + maxRegion, getHeight() - ResConstant.Physics.INTERVAL_BAR - 60);
        mMaxBottomBarTopY = Math.min(mMaxBottomBarTopY, mFloorActor.getTopY() + barHeight);

        mTouchSound = AssetManagerUtils.getInstance().getAssetManager().get(ResConstant.Audios.AUDIO_TOUCH, Sound.class);
        mDeadSound = AssetManagerUtils.getInstance().getAssetManager().get(ResConstant.Audios.AUDIO_DIE, Sound.class);
        mHitSound = AssetManagerUtils.getInstance().getAssetManager().get(ResConstant.Audios.AUDIO_HIT, Sound.class);
        mScoreSound = AssetManagerUtils.getInstance().getAssetManager().get(ResConstant.Audios.AUDIO_SCORE, Sound.class);

        setReady();
    }

    public void setReady(){
        mGameState = GameState.READY;//

        mBirdActor.setY(mBirdActorOriginPositionY);

        mBirdActor.refreshFrameAndRotation(mGameState);
        mFloorActor.setIsFloorMoving(false);

        for(BarActor barActor : mBarActors){
            getRoot().removeActor(barActor);
        }

        mBarActors.clear();

        mTapTipActor.setVisible(true);

        resetCurrentScore();
    }

    private void setStart(){
        mGameState = GameState.FLY;
        mBirdActor.refreshFrameAndRotation(mGameState);

        mFloorActor.setIsFloorMoving(true);

        mTapTipActor.setVisible(false);

        mNextBarGeneratedTimeCounter = 0;
    }

    private void setOver(){
        mGameState = GameState.GAMEOVER;
        mBirdActor.refreshFrameAndRotation(mGameState);

        mFloorActor.setIsFloorMoving(false);

        stopAllBars();

        if(mOnStateListener != null){
            mOnStateListener.onOver(mCurrentScoreActor.getCurrentNumber());
        }
    }

    private void stopAllBars(){
        for(BarActor barActor : mBarActors){
            if(barActor.isMoving()){
                barActor.setIsMoving(false);
            }
        }
    }

    private void isCollision(){

        if(mGameState == GameState.FLY){
            int size = mBarActors.size();
            for(int i = 0; i < size ; i++){
                BarActor actor = mBarActors.get(i);
                if(CollisionUtils.isCollision(mBirdActor, actor, ResConstant.Physics.DEPTH)){
                    mGameState = GameState.DIE;
                    stopAllBars();
                    mFloorActor.setIsFloorMoving(false);
                    playHitSound();
                    break;
                }

                //因为已经通过了碰撞判断，再判断小鸟的右边是否大于任意水管的右边，即为得分
                //actor.isPassedByBird()避免重负判断
                if(!actor.isPassedByBird() && actor.getIsTopBar() && mBirdActor.getX() > actor.getRightX()){
                    addOneScore();
                    playScoreSound();
                    actor.setIsPassedByBird(true);
                }

                if(actor.getRightX() < 0){
                    getRoot().removeActor(actor);
                    //在for 循环中remove元素会造成size减一, 并且index迁移，要注意，如不要求效率，可以考虑迭代器
                    mBarActors.remove(i);
                    i--;
                    size--;
                    //在for 循环中remove元素会造成size减一，并且index迁移，要注意，如不要求效率，可以考虑迭代器
                    mBarActorPool.free(actor);
                    System.out.println(size);
                }
            }
        }

//        int size = mBarActors.size();
//        for(int i = 0; i < size; i++){
//            BarActor actor = mBarActors.get(i);
//            if(actor.getRightX() < 0){
//                getRoot().removeActor(actor);
//                //在for 循环中remove元素会造成size减一, 并且index迁移，要注意，如不要求效率，可以考虑迭代器
//                mBarActors.remove(i);
//                i--;
//                size--;
//                //在for 循环中remove元素会造成size减一，并且index迁移，要注意，如不要求效率，可以考虑迭代器
//                mBarActorPool.free(actor);
//            }
//        }

        if(CollisionUtils.isCollision(mBirdActor, mFloorActor, ResConstant.Physics.DEPTH)){

            setOver();
            playDeadSound();
        }

    }

    private void generateAPairOfBars(){
        float bottomBarTopY = MathUtils.random(mMinBottomBarTopY, mMaxBottomBarTopY);

        BarActor bottomBarActor = mBarActorPool.obtain();
        bottomBarActor.setIsTopBar(false);
        bottomBarActor.setX(getWidth());
        bottomBarActor.setTopY(bottomBarTopY);
        bottomBarActor.setIsMoving(true);
        addActor(bottomBarActor);
        //将水管加入水管集合
        mBarActors.add(bottomBarActor);
        //将水管Z轴层次设置为与小鸟的Z轴层次（就是同一Z轴坐标上）
        bottomBarActor.setZIndex(mBirdActor.getZIndex());

        BarActor topBarActor = mBarActorPool.obtain();
        topBarActor.setIsTopBar(true);
        topBarActor.setX(getWidth());
        topBarActor.setY(bottomBarActor.getTopY() + ResConstant.Physics.INTERVAL_BAR);
        topBarActor.setIsMoving(true);
        addActor(topBarActor);
        mBarActors.add(topBarActor);
        topBarActor.setZIndex(mBirdActor.getZIndex());
    }

    private void playTouchSound(){
        if(mTouchSound != null){
            mTouchSound.play();
        }
    }

    private void playDeadSound(){
        if(mDeadSound != null){
            mDeadSound.play();
        }
    }

    private void playHitSound(){
        if(mHitSound != null){
            mHitSound.play();
        }
    }

    private void playScoreSound(){
        if(mScoreSound != null){
            mScoreSound.play();
        }
    }

    private void resetCurrentScore(){
        if(mCurrentScoreActor != null){
            mCurrentScoreActor.setCurrentNumber(0);
            mCurrentScoreActor.setCenterX(getWidth() / 2);
        }
    }

    private void addOneScore(){
        if(mCurrentScoreActor != null){
            mCurrentScoreActor.addNumber(1);
            mCurrentScoreActor.setCenterX(getWidth() / 2);
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        if(mBarActors != null){
            mBarActors.clear();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(mGameState == GameState.FLY || mGameState == GameState.DIE){
            isCollision();
        }

        if(mGameState == GameState.FLY){
            mNextBarGeneratedTimeCounter += delta;
            if(mNextBarGeneratedTimeCounter >= ResConstant.Physics.INTERVAL_GENERATE_BAR_TIME){
                generateAPairOfBars();

                mNextBarGeneratedTimeCounter = 0;
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(mGameState == GameState.READY){
            // 在准备状态下触摸屏幕开始游戏
            setStart();
            // 播放触摸屏幕音效
            playTouchSound();

            // 开始游戏时给小鸟设置一个向上的速度
            mBirdActor.setVelocityY(ResConstant.Physics.JUMP_VELOCITY);
        } else if(mGameState == GameState.FLY){
            if(mBirdActor.getTopY() < getHeight()){
                mBirdActor.setVelocityY(ResConstant.Physics.JUMP_VELOCITY);
                playTouchSound();
            }
        }

        return true;
    }

    public void setOnStateListener(OnStateListener listener){
        mOnStateListener = listener;
    }

    public interface OnStateListener{
        void onOver(int score);

    }
}
