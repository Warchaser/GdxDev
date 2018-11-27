package com.warchaser.gdx.util;

public final class ResConstant {

    private ResConstant(){

    }

    /**
     * 帧数字体大小
     * */
    public static final int FPS_FONT_SIZE = 24;

    /**
     * 固定世界宽度为480，高度根据实际屏幕比例换算
     * */
    public static final float FIX_WORLD_WIDTH = 480;

    /**
     * 文件路径
     * 帧率显示所需要的位图字体
     * */
    public static final String FPS_BITMAP_FONT_PATH = "fps/fps24px.fnt";

    public static class Physics{

        private Physics(){

        }

        /**
         * 水管和地板的移动速度，单位：px/s
         * */
        public static final float MOVE_VELOCITY = -150.0f;

        /**
         * 点击屏幕时给小鸟的垂直向上的速度，单位：px/s
         * */
        public static final float JUMP_VELOCITY = 270.0f;

        /**
         * 小鸟垂直方向向下的重力加速度，单位：px/(s*s)
         * */
        public static final float GRAVITY = -520.0f;

        /**
         * 撞入深度，小鸟撞入水管或地板此深度后才算碰撞
         * */
        public static final float DEPTH = 0.0f;

        /**
         * 生成水管时间间隔，单位：s
         * */
        public static final float INTERVAL_GENERATE_BAR_TIME = 2.2f;

        /**
         * 上下水管之间的间隔
         * */
        public static final float INTERVAL_BAR = 180.0f;
    }

    /**
     * 纹理图集
     */
    public static class Atlas{

        private Atlas(){

        }

        /** 纹理图集 文件路径 */
        public static final String ATLAS_PATH = "atlas/images.atlas";

        /* 纹理图集中的小图名称 */
        public static final String IMAGE_GAME_BG = "game_bg";
        public static final String IMAGE_GAME_FLOOR = "game_floor";
        public static final String IMAGE_GAME_RESULT_BG = "game_result_bg";
        public static final String IMAGE_BAR_DOWN = "bar_down";
        public static final String IMAGE_BAR_UP = "bar_up";
        public static final String IMAGE_GAME_TAP_TIP = "game_tap_tip";
        public static final String IMAGE_GAME_READY = "game_ready";
        public static final String IMAGE_GAME_OVER = "game_over";

        public static final String IMAGE_GAME_START_01_TO_02 = "game_start";
        public static final String IMAGE_GAME_MEDAL_01_TO_04 = "game_medal";
        public static final String IMAGE_NUM_BIG_00_TO_09 = "num_big";
        public static final String IMAGE_NUM_SCORE_00_TO_09 = "num_score";
        public static final String IMAGE_BIRD_YELLOW_01_TO_03 = "bird_yellow";
    }

    /**
     * 音效
     */
    public static class Audios{

        private Audios(){

        }

        /** 音效资源文件夹路径 */
        public static final String AUDIO_BASE_DIR	= "audio/";

        // 音效资源路径
        public static final String AUDIO_DIE = AUDIO_BASE_DIR + "die.ogg";
        public static final String AUDIO_HIT = AUDIO_BASE_DIR + "hit.ogg";
        public static final String AUDIO_TOUCH = AUDIO_BASE_DIR + "touch.ogg";
        public static final String AUDIO_RESTART = AUDIO_BASE_DIR + "restart.ogg";
        public static final String AUDIO_SCORE = AUDIO_BASE_DIR + "score.ogg";
    }

    /**
     * Preferences 本地存储相关
     */
    public static class Prefs{

        private Prefs(){

        }

        public static final String PREFS_FILE_NAME = "prefs_flappy_bird";

        public static final String KEY_BEST_SCORE = "best_score";
    }
}
