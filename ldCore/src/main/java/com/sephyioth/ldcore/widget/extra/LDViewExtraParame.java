package com.sephyioth.ldcore.widget.extra;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;


import com.sephyioth.ldcore.R;
import com.sephyioth.ldcore.model.BasicApplication;
import com.sephyioth.ldcore.widget.titlebar.TitleBar;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * LD Sys 参数配置选项
 * <p/>
 * 创建人：Genesis
 * 创建时间：16/8/8 11:53
 * 修改人：Genesis
 * 修改时间：16/8/8 11:53
 * 修改备注：
 */

public class LDViewExtraParame {

    private boolean                    isShow;
    private boolean                    isRightShow;
    private boolean                    isLeftShow;
    private boolean                    isFloatButtonShow;
    private String                     mTitle;
    private String                     mLeftTitle;
    private Bitmap                     mFloatBtnBackground;
    private Bitmap                     mLeftBtnBackground;
    private View.OnClickListener       mClickListener;
    private ArrayList<TitleBar.Action> mActionList;

    public LDViewExtraParame () {
        mActionList = new ArrayList<>();
        isFloatButtonShow = false;
        isRightShow = isLeftShow = true;
        isShow = true;
        mTitle = BasicApplication.getCoreApplication().getString(R.string.system_name);
        mLeftTitle = "";
        mFloatBtnBackground =
                BitmapFactory.decodeResource(BasicApplication.getCoreApplication().getResources(),
                        R.mipmap.ic_dehaze_white);
        mLeftBtnBackground =
                BitmapFactory.decodeResource(BasicApplication.getCoreApplication().getResources(),
                        R.mipmap.ic_navigate_before_white);
    }

    public void addAction (TitleBar.Action action) {
        mActionList.add(action);
    }

    public void clearAction () {
        mActionList.clear();
    }

    public ArrayList<TitleBar.Action> getActionList () {
        return mActionList;
    }

    public boolean isRightShow () {
        return isRightShow;
    }

    public void setRightShow (boolean rightShow) {
        isRightShow = rightShow;
    }

    public boolean isLeftShow () {
        return isLeftShow;
    }

    public void setLeftShow (boolean leftShow) {
        isLeftShow = leftShow;
    }

    public boolean isFloatButtonShow () {
        return isFloatButtonShow;
    }

    public void setFloatButtonShow (boolean floatButtonShow) {
        isFloatButtonShow = floatButtonShow;
    }

    public String getTitle () {
        return mTitle;
    }

    public void setTitle (String mTitle) {
        this.mTitle = mTitle;
    }


    public String getLeftTitle () {
        return mLeftTitle;
    }

    public void setLeftTitle (String mLeftTitle) {
        this.mLeftTitle = mLeftTitle;
    }

    public Bitmap getFloatBtnBackground () {
        return mFloatBtnBackground;
    }

    public void setFloatBtnBackground (Bitmap mFloatBtnBackground) {
        this.mFloatBtnBackground = mFloatBtnBackground;
    }

    public Bitmap getLeftBtnBackground () {
        return mLeftBtnBackground;
    }

    public void setLeftBtnBackground (Bitmap mLeftBtnBackground) {
        this.mLeftBtnBackground = mLeftBtnBackground;
    }

    public View.OnClickListener getClickListener () {
        return mClickListener;
    }

    public void setClickListener (View.OnClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void setShow (boolean show) {
        this.isShow = show;
    }

    public boolean isShow () {
        return isShow;
    }
}
