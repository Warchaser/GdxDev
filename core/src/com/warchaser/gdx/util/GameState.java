package com.warchaser.gdx.util;

/**
 * 游戏状态
 */
public enum GameState {

    /** 准备状态 */
    READY,

    /** 游戏状态(小鸟处于飞翔状态) */
    FLY,

    /** 小鸟死亡状态(撞到水管, 掉落到地板前) */
    DIE,

    /** 游戏结束(撞到地板或撞到水管后掉落到地板) */
    GAMEOVER
}
