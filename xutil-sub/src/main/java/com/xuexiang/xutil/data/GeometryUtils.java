/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xutil.data;

import android.graphics.PointF;

/**
 * 几何图形工具
 *
 * @author xuexiang
 * @since 2018/6/27 下午5:22
 */
public final class GeometryUtils {

    private GeometryUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * As meaning of method name.
     * 获得两点之间的距离
     *
     * @param p0
     * @param p1
     * @return
     */
    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
        return distance;
    }

    /**
     * Get middle point between p1 and p2.
     * 获得两点连线的中点
     *
     * @param p1
     * @param p2
     * @return
     */
    public static PointF getMiddlePoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }

    /**
     * Get point between p1 and p2 by percent.
     * 根据百分比获取两点之间的某个点坐标
     *
     * @param p1
     * @param p2
     * @param percent
     * @return
     */
    public static PointF getPointByPercent(PointF p1, PointF p2, float percent) {
        return new PointF(evaluateValue(percent, p1.x, p2.x), evaluateValue(percent, p1.y, p2.y));
    }

    /**
     * 根据分度值，计算从start到end中，fraction位置的值。fraction范围为0 -> 1
     *
     * @param fraction
     * @param start
     * @param end
     * @return
     */
    public static float evaluateValue(float fraction, Number start, Number end) {
        return start.floatValue() + (end.floatValue() - start.floatValue()) * fraction;
    }

    /**
     * Get the point of intersection between circle and line.
     * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
     *
     * @param pMiddle The circle center point.
     * @param radius  The circle radius.
     * @param lineK   The slope of line which cross the pMiddle.
     * @return
     */
    public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK) {
        PointF[] points = new PointF[2];
        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);
        return points;
    }
}