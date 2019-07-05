package com.genesis.ginvoice.view.dialog;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.genesis.ginvoice.R;


/**
 * 项目名称：kongzhiguiji
 * 类描述：
 * <p>
 * 为了方便，拿了老代码，真作践
 * <p>
 * 创建人：Administrator
 * 创建时间：2016/5/18 12:57
 * 修改人：Administrator
 * 修改时间：2016/5/18 12:57
 * 修改备注：
 */

public class LDChoseDialog extends LDBasicDialog implements View.OnTouchListener,
        View.OnClickListener {
    private View.OnClickListener mClickListener;

    public LDChoseDialog (Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId () {
        return R.layout.layout_choseaction_dialog;
    }

    @Override
    protected void initView () {
        setCanceledOnTouchOutside(true);
        setCanceledOnTouchOutside(true);
        findViewById(R.id.btn_cancel_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void initData () {
        findViewById(R.id.btn_choose_camera).setOnClickListener(this);
        findViewById(R.id.btn_choose_album).setOnClickListener(this);
        findViewById(R.id.btn_choose_ocr_normal).setOnClickListener(this);
    }

    public LDChoseDialog (Context context, View.OnClickListener clickListener) {
        super(context);
        this.mClickListener = clickListener;
    }

    @Override
    public boolean onTouch (View v, MotionEvent event) {
        // TODO Auto-generated method stub
        if (v.getId() != R.id.listView1) {
            dismiss();
        }
        return false;
    }

    @Override
    public void onClick (View v) {
        if (mClickListener != null) {
            mClickListener.onClick(v);
        }
        dismiss();
    }
}
