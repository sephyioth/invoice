package com.genesis.ginvoice.view.dialog;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.genesis.ginvoice.R;

/**
 * 项目名称：CardShare
 * 类描述：
 * 适用5个以内的选择框
 * 创建人：genesis
 * 创建时间：2019-06-28 16:57
 * 修改人：genesis
 * 修改时间：2019-06-28 16:57
 * 修改备注：
 */
public class LDChoseArrayDialog extends LDBasicDialog {

    private String[] mItems;

    public LDChoseArrayDialog (Context context, int resId) {
        super(context);
        mItems = context.getResources().getStringArray(resId);
    }

    @Override
    protected int getLayoutId () {
        return R.layout.layout_chose_dialog;
    }

    @Override
    protected void initView () {
        RelativeLayout layout = new RelativeLayout(getContext());
        layout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout layoutCell = new LinearLayout(getContext());
        layoutCell.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(layoutCell);

        if (mItems == null) {


        }

    }

    @Override
    protected void initData () {

    }
}
