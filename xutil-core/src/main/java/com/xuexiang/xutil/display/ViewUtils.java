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
package com.xuexiang.xutil.display;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewUtils
 * <ul>
 * <strong>get view height</strong>
 * <li>{@link ViewUtils#getListViewHeightBasedOnChildren(ListView)}</li>
 * <li>{@link ViewUtils#getAbsListViewHeightBasedOnChildren(AbsListView)}</li>
 * </ul>
 * <ul>
 * <strong>set view height</strong>
 * <li>{@link ViewUtils#setViewHeight(View, int)} set view height</li>
 * <li>{@link ViewUtils#setListViewHeightBasedOnChildren(ListView)}</li>
 * <li>{@link ViewUtils#setAbsListViewHeightBasedOnChildren(AbsListView)}</li>
 * </ul>
 * <ul>
 * <strong>set other info</strong>
 * <li>{@link ViewUtils#setSearchViewOnClickListener(View, OnClickListener)}</li>
 * </ul>
 * <p>
 * <pre>
 *     desc   : 控件工具类
 *     author : xuexiang
 *     time   : 2018/4/23 下午4:29
 * </pre>
 */
public final class ViewUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private ViewUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * get ListView height according to every children
     *
     * @param view
     * @return
     */
    public static int getListViewHeightBasedOnChildren(ListView view) {
        int height = getAbsListViewHeightBasedOnChildren(view);
        ListAdapter adapter;
        int adapterCount;
        if (view != null && (adapter = view.getAdapter()) != null
                && (adapterCount = adapter.getCount()) > 0) {
            height += view.getDividerHeight() * (adapterCount - 1);
        }
        return height;
    }

    /**
     * get AbsListView height according to every children
     *
     * @param view
     * @return
     */
    public static int getAbsListViewHeightBasedOnChildren(AbsListView view) {
        ListAdapter adapter;
        if (view == null || (adapter = view.getAdapter()) == null) {
            return 0;
        }

        int height = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, view);
            if (item instanceof ViewGroup) {
                item.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }
            item.measure(0, 0);
            height += item.getMeasuredHeight();
        }
        height += view.getPaddingTop() + view.getPaddingBottom();
        return height;
    }

    /**
     * set view height
     *
     * @param view
     * @param height
     */
    public static void setViewHeight(View view, int height) {
        if (view == null) {
            return;
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }

    /**
     * set ListView height which is calculated by
     * {@link # getListViewHeightBasedOnChildren(ListView)}
     *
     * @param view
     * @return
     */
    public static void setListViewHeightBasedOnChildren(ListView view) {
        setViewHeight(view, getListViewHeightBasedOnChildren(view));
    }

    /**
     * set AbsListView height which is calculated by
     * {@link # getAbsListViewHeightBasedOnChildren(AbsListView)}
     *
     * @param view
     * @return
     */
    public static void setAbsListViewHeightBasedOnChildren(AbsListView view) {
        setViewHeight(view, getAbsListViewHeightBasedOnChildren(view));
    }

    /**
     * set SearchView OnClickListener
     *
     * @param v
     * @param listener
     */
    public static void setSearchViewOnClickListener(View v, OnClickListener listener) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = group.getChildAt(i);
                if (child instanceof LinearLayout
                        || child instanceof RelativeLayout) {
                    setSearchViewOnClickListener(child, listener);
                }

                if (child instanceof TextView) {
                    TextView text = (TextView) child;
                    text.setFocusable(false);
                }
                child.setOnClickListener(listener);
            }
        }
    }

    /**
     * get descended views from parent.
     *
     * @param parent
     * @param filter          Type of views which will be returned.
     * @param includeSubClass Whether returned list will include views which are subclass of
     *                        filter or not.
     * @return
     */
    public static <T extends View> List<T> getDescendants(ViewGroup parent, Class<T> filter, boolean includeSubClass) {
        List<T> descendedViewList = new ArrayList<>();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            Class<? extends View> childsClass = child.getClass();
            if ((includeSubClass && filter.isAssignableFrom(childsClass))
                    || (!includeSubClass && childsClass == filter)) {
                descendedViewList.add(filter.cast(child));
            }
            if (child instanceof ViewGroup) {
                descendedViewList.addAll(getDescendants((ViewGroup) child,
                        filter, includeSubClass));
            }
        }
        return descendedViewList;
    }

    /**
     * Helps determine if the app is running in a Tablet context.
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取activity最顶层的父布局
     * <p>@android:id/content 对应的控件是FrameLayout</p>
     *
     * @param activity
     * @return
     */
    public static FrameLayout getContentView(@NonNull final Activity activity) {
        ViewGroup view = (ViewGroup) activity.getWindow().getDecorView();
        return view.findViewById(android.R.id.content);
    }

    /**
     * View设备背景
     *
     * @param context
     * @param view
     * @param res
     */
    public static void setBackground(Context context, View view, int res) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), res);
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bmp);
        if (view != null) {
            view.setBackgroundDrawable(bd);
        }
    }

    /**
     * 释放图片资源
     *
     * @param v
     */
    public static void recycleBackground(@NonNull View v) {
        Drawable d = v.getBackground();
        v.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
        if (d != null && d instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) d).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
            }
        }
        if (d != null) {
            d.setCallback(null);
        }
    }

    /**
     * 遍历View,清除所有ImageView的缓存
     *
     * @param view
     */
    public static void clearImageView(@NonNull View view) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                clearImageView(parent.getChildAt(i));
            }
        } else if (view instanceof ImageView) {
            clearImgMemory((ImageView) view);
        }
    }

    /**
     * 清空图片的内存
     */
    public static void clearImgMemory(@NonNull ImageView imageView) {
        Drawable d = imageView.getDrawable();
        if (d != null && d instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) d).getBitmap();
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
            }
        }
        imageView.setImageBitmap(null);
        if (d != null) {
            d.setCallback(null);
        }
    }

    //=====================View 常用操作==============================//

    /**
     * 设置控件的可见度
     *
     * @param view   控件
     * @param isShow 是否可见
     */
    public static void setVisibility(View view, boolean isShow) {
        if (view != null) {
            view.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 设置控件的可见度
     *
     * @param view       控件
     * @param visibility
     */
    public static void setVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }


    /**
     * 设置控件是否可用
     *
     * @param view    控件
     * @param enabled 是否可用
     */
    public static void setEnabled(View view, boolean enabled) {
        if (view != null) {
            view.setEnabled(enabled);
            if (view instanceof EditText) {
                view.setFocusable(enabled);
                view.setFocusableInTouchMode(enabled);
            }
        }
    }

    /**
     * 设置控件的文字
     *
     * @param view 控件
     * @param text 文字
     */
    public static void setText(TextView view, String text) {
        if (view != null) {
            view.setText(text);
        }
    }

    /**
     * 设置控件的文字
     *
     * @param view   控件
     * @param textId 文字资源
     */
    public static void setText(TextView view, @StringRes int textId) {
        if (view != null) {
            view.setText(textId);
        }
    }

    /**
     * 设置控件的文字颜色
     *
     * @param view    控件
     * @param colorId 文字颜色
     */
    public static void textColorId(TextView view, @ColorRes int colorId) {
        if (view != null) {
            view.setTextColor(ContextCompat.getColor(view.getContext(), colorId));
        }
    }

    /**
     * 设置控件的图片资源
     *
     * @param view    控件
     * @param imageId 图片资源ID
     */
    public static void setImageResource(ImageView view, @DrawableRes int imageId) {
        if (view != null) {
            view.setImageResource(imageId);
        }
    }

    /**
     * 设置控件的图片资源
     *
     * @param view     控件
     * @param drawable 图片资源
     */
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        if (view != null) {
            view.setImageDrawable(drawable);
        }
    }

    /**
     * 设置控件的图片资源
     *
     * @param view 控件
     * @param uri  图片资源
     */
    public static void setImageURI(ImageView view, Uri uri) {
        if (view != null) {
            view.setImageURI(uri);
        }
    }

    /**
     * 设置图片的等级
     *
     * @param view  控件
     * @param level 图片等级
     */
    public static void setImageLevel(ImageView view, int level) {
        if (view != null) {
            view.setImageLevel(level);
        }
    }

    /**
     * 给图片着色
     *
     * @param view 控件
     * @param tint 着色
     */
    public static void setImageTint(ImageView view, ColorStateList tint) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setImageTintList(tint);
            }
        }
    }

    /**
     * 设置控件的选中状态
     *
     * @param view    控件
     * @param isCheck 是否选中
     */
    public static void setChecked(CompoundButton view, boolean isCheck) {
        if (view != null) {
            view.setChecked(isCheck);
        }
    }

    /**
     * 设置控件的选中监听
     *
     * @param view                  控件
     * @param checkedChangeListener 选中监听
     */
    public static void setOnCheckedChangeListener(CompoundButton view, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        if (view != null) {
            view.setOnCheckedChangeListener(checkedChangeListener);
        }
    }

    /**
     * 设置控件的选中状态【静默】
     *
     * @param view                  控件
     * @param isCheck               是否选中
     * @param checkedChangeListener 选中监听
     */
    public static void setCheckedSilent(CompoundButton view, boolean isCheck, CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        if (view != null) {
            view.setOnCheckedChangeListener(null);
            view.setChecked(isCheck);
            view.setOnCheckedChangeListener(checkedChangeListener);
        }
    }


    //=====================设置Padding==============================//

    /**
     * 扩展点击区域的范围
     *
     * @param view       需要扩展的元素，此元素必需要有父级元素
     * @param expendSize 需要扩展的尺寸（以xp为单位的）
     */
    public static void expendTouchArea(final View view, final int expendSize) {
        if (view != null) {
            final View parentView = (View) view.getParent();

            parentView.post(new Runnable() {
                @Override
                public void run() {
                    //屏幕的坐标原点在左上角
                    Rect rect = new Rect();
                    view.getHitRect(rect); //如果太早执行本函数，会获取rect失败，因为此时UI界面尚未开始绘制，无法获得正确的坐标
                    rect.left -= expendSize;
                    rect.top -= expendSize;
                    rect.right += expendSize;
                    rect.bottom += expendSize;
                    parentView.setTouchDelegate(new TouchDelegate(rect, view));
                }
            });
        }
    }

    /**
     * 设置控件的padding
     *
     * @param view    控件
     * @param padding
     */
    public static void setPadding(View view, int padding) {
        if (view != null) {
            view.setPadding(padding, padding, padding, padding);
        }
    }

    /**
     * 对 View 设置 paddingLeft
     *
     * @param view  需要被设置的 View
     * @param value 设置的值
     */
    public static void setPaddingLeft(View view, int value) {
        if (value != view.getPaddingLeft()) {
            view.setPadding(value, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /**
     * 对 View 设置 paddingTop
     *
     * @param view  需要被设置的 View
     * @param value 设置的值
     */
    public static void setPaddingTop(View view, int value) {
        if (value != view.getPaddingTop()) {
            view.setPadding(view.getPaddingLeft(), value, view.getPaddingRight(), view.getPaddingBottom());
        }
    }

    /**
     * 对 View 设置 paddingRight
     *
     * @param view  需要被设置的 View
     * @param value 设置的值
     */
    public static void setPaddingRight(View view, int value) {
        if (value != view.getPaddingRight()) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), value, view.getPaddingBottom());
        }
    }

    /**
     * 对 View 设置 paddingBottom
     *
     * @param view  需要被设置的 View
     * @param value 设置的值
     */
    public static void setPaddingBottom(View view, int value) {
        if (value != view.getPaddingBottom()) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), value);
        }
    }

    //=====================设置Margin==============================//

    /**
     * 设置view的margin<br>
     * 当且仅当 新的start,top,end,bottom 至少有之一 与原值不相同，为避免不必要的requestlayout
     *
     * @param view   控件
     * @param start  左侧距离
     * @param top    顶部距离
     * @param end    右侧距离
     * @param bottom 底部距离
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setViewMargin(View view, int start, int top, int end, int bottom) {
        ViewGroup.MarginLayoutParams lp = getLayoutParams(view, ViewGroup.MarginLayoutParams.class);
        if (lp != null) {
            int oldStart = lp.getMarginStart();
            int oldTop = lp.topMargin;
            int oldEnd = lp.getMarginEnd();
            int oldBottom = lp.bottomMargin;

            if (oldStart != start || oldTop != top || oldEnd != end || oldBottom != bottom) {
                lp.setMarginStart(start);
                lp.topMargin = top;
                lp.setMarginEnd(end);
                lp.bottomMargin = bottom;
                setLayoutParams(view, lp);
            }
        }
    }

    /**
     * 获取视图布局参数
     *
     * @param view  视图
     * @param clazz 待转换的类型
     * @param <T>   数据类型模板
     * @return 视图布局参数
     */
    public static <T> T getLayoutParams(View view, Class<? extends ViewGroup.LayoutParams> clazz) {
        if (view == null || clazz == null) {
            return null;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (clazz.isInstance(layoutParams)) {
            return (T) layoutParams;
        }
        return null;
    }

    /**
     * 设置视图布局参数
     *
     * @param view   视图
     * @param params 布局参数
     */
    public static void setLayoutParams(View view, ViewGroup.LayoutParams params) {
        if (view != null && params != null) {
            view.setLayoutParams(params);
        }
    }

}
