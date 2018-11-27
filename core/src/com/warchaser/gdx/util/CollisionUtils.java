package com.warchaser.gdx.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CollisionUtils {

    private static final Rectangle RECT_1 = new Rectangle();
    private static final Rectangle RECT_2 = new Rectangle();
    private static final Rectangle RECT_TEMP = new Rectangle();

    /**
     * 判断两个演员是否碰撞
     *
     * @param actor1 1
     * @param actor2 2
     * @param depth 碰撞深度, 两个演员的包围矩阵重叠部分的矩形宽高均超过 depth 才算碰撞
     * @return isCollision
     */
    public static synchronized boolean isCollision(Actor actor1, Actor actor2, float depth){

        boolean result = false;

        if(actor1 == null || actor2 == null){
            return result;
        }

        final float doubleDepth = 2 * depth;

        /* 这里只是粗略地判断两个演员的碰撞, 没有考虑演员自身旋转等导致的误差, 精确的碰撞检测可使用 Box2D */

        // 获取 演员1 缩放后的包围矩阵
        RECT_1.setSize(
                actor1.getWidth() * actor1.getScaleX(),
                actor1.getHeight() * actor1.getScaleY()
        );

        RECT_1.setPosition(
                actor1.getX() - (actor1.getOriginX() * actor1.getScaleX() - actor1.getOriginX()),
                actor1.getY() - (actor1.getOriginY() * actor1.getScaleY() - actor1.getOriginY())
                );

        // 获取 演员2 缩放后的包围矩阵
        RECT_2.setSize(
                actor2.getWidth() * actor2.getScaleX(),
                actor2.getHeight() * actor2.getScaleY()
        );

        RECT_2.setPosition(
                actor2.getX() - (actor2.getOriginX() * actor2.getScaleX() - actor2.getOriginX()),
                actor2.getY() - (actor2.getOriginY() * actor2.getScaleY() - actor2.getOriginY())
        );

        result = RECT_1.overlaps(RECT_2);

        // 将矩形的四条边分别向中心缩减 depth 后再判断是否碰撞
        if(RECT_2.width > doubleDepth && RECT_2.height > doubleDepth){
            RECT_TEMP.set(
                    RECT_2.x + depth,
                    RECT_2.y + depth,
                    RECT_2.width - doubleDepth,
                    RECT_2.height - doubleDepth
            );

            result = RECT_TEMP.overlaps(RECT_1);
        } else if(RECT_1.width > doubleDepth && RECT_1.height > doubleDepth){
            RECT_TEMP.set(
                    RECT_1.x + depth,
                    RECT_1.y + depth,
                    RECT_1.width - doubleDepth,
                    RECT_1.height - doubleDepth
            );

            result = RECT_TEMP.overlaps(RECT_2);
        }

        // 如果两个演员的包围矩阵宽高均比两倍的碰撞深度还小, 则不考虑碰撞深度
        return result;
    }

}
