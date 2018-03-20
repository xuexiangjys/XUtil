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

package com.xuexiang.xutil.app.notify;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.app.notify.builder.BaseBuilder;
import com.xuexiang.xutil.app.notify.builder.BigPicBuilder;
import com.xuexiang.xutil.app.notify.builder.BigTextBuilder;
import com.xuexiang.xutil.app.notify.builder.MailboxBuilder;

import java.util.Map;

/**
 * 通知栏工具类
 *
 * @author xuexiang
 * @date 2018/3/20 下午9:43
 */
public final class NotificationUtils {

    private static NotificationManager sNotificationManager;


    //==================通知的api======================//

    /**
     * 简单的通知
     *
     * @param id            通知的ID
     * @param smallIcon     顶部状态栏的小图标
     * @param contentTitle  通知中心的标题
     * @param contentText   通知中心中的内容
     * @param contentIntent 通知点击的事件
     * @return
     */
    public static BaseBuilder buildSimple(int id, int smallIcon, CharSequence contentTitle, CharSequence contentText, PendingIntent contentIntent) {
        return new BaseBuilder()
                .setId(id)
                .setBaseInfo(smallIcon, contentTitle, contentText)
                .setContentIntent(contentIntent);
    }

    /**
     * 带图片的通知
     *
     * @param id           通知的ID
     * @param smallIcon    顶部状态栏的小图标
     * @param contentTitle 通知中心的标题
     * @param summaryText  图片的概要信息
     * @return
     */
    public static BigPicBuilder buildBigPic(int id, int smallIcon, CharSequence contentTitle, CharSequence summaryText) {
        return new BigPicBuilder()
                .setId(id)
                .setSmallIcon(smallIcon)
                .setContentTitle(contentTitle)
                .setSummaryText(summaryText);
    }

    /**
     * 多文本通知
     * @param id 通知的ID
     * @param smallIcon 顶部状态栏的小图标
     * @param contentTitle 通知中心的标题
     * @param contentText  通知中心中的内容
     * @return
     */
    public static BigTextBuilder buildBigText(int id, int smallIcon, CharSequence contentTitle, CharSequence contentText){
        return new BigTextBuilder()
                .setId(id)
                .setBaseInfo(smallIcon, contentTitle, contentText);
    }

    /**
     * 带多条消息合并的消息盒通知
     * @param id 通知的ID
     * @param smallIcon 顶部状态栏的小图标
     * @param contentTitle 通知中心的标题
     * @return
     */
    public static MailboxBuilder buildMailBox(int id, int smallIcon, CharSequence contentTitle){
        return new MailboxBuilder()
                .setId(id)
                .setSmallIcon(smallIcon)
                .setContentTitle(contentTitle);
    }


    /**
     * 通知
     *
     * @param id           通知ID
     * @param notification 通知的内容
     */
    public static void notify(int id, Notification notification) {
        if (sNotificationManager == null) {
            sNotificationManager = getNotificationManager();
        }
        sNotificationManager.notify(id, notification);
    }

    //==================构建点击跳转Activity的意图======================//

    /**
     * 构建点击跳转Activity的意图
     *
     * @param clazz
     * @return
     */
    public static PendingIntent buildActivityIntent(Class<? extends Activity> clazz) {
        return buildActivityIntent(clazz, 0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 构建点击跳转Activity的意图
     *
     * @param clazz
     * @return
     */
    public static PendingIntent buildActivityIntent(Class<? extends Activity> clazz, String key, Object param) {
        return buildActivityIntent(clazz, key, param, 0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 构建点击跳转Activity的意图
     *
     * @param clazz
     * @return
     */
    public static PendingIntent buildActivityIntent(Class<? extends Activity> clazz, Map<String, Object> map) {
        return buildActivityIntent(clazz, map, 0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 构建点击跳转Activity的意图
     *
     * @param clazz       Activity类
     * @param requestCode 请求码
     * @param flags
     * @return
     */
    public static PendingIntent buildActivityIntent(Class<? extends Activity> clazz, int requestCode, int flags) {
        Intent intent = ActivityUtils.getActivityIntent(clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(XUtil.getContext(), requestCode, intent, flags);
        return pi;
    }

    /**
     * 构建点击跳转Activity的意图
     *
     * @param clazz       Activity类
     * @param key
     * @param param
     * @param requestCode 请求码
     * @param flags
     * @return
     */
    public static PendingIntent buildActivityIntent(Class<? extends Activity> clazz, String key, Object param, int requestCode, int flags) {
        Intent intent = ActivityUtils.getActivityIntent(clazz, key, param);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(XUtil.getContext(), requestCode, intent, flags);
        return pi;
    }

    /**
     * 构建点击跳转Activity的意图
     *
     * @param clazz       Activity类
     * @param map         携带的数据
     * @param requestCode 请求码
     * @param flags
     * @return
     */
    public static PendingIntent buildActivityIntent(Class<? extends Activity> clazz, Map<String, Object> map, int requestCode, int flags) {
        Intent intent = ActivityUtils.getActivityIntent(clazz, map);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getActivity(XUtil.getContext(), requestCode, intent, flags);
        return pi;
    }


    public static NotificationManager getNotificationManager() {
        return (NotificationManager) XUtil.getContext().getSystemService(Activity.NOTIFICATION_SERVICE);
    }
}
