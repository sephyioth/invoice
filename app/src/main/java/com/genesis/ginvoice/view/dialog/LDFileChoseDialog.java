package com.genesis.ginvoice.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.genesis.ginvoice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-28 17:09
 * 修改人：genesis
 * 修改时间：2019-06-28 17:09
 * 修改备注：
 */
public class LDFileChoseDialog extends LDBasicDialog implements View.OnClickListener {

    @BindView(R.id.btn_cancel_next)
    Button mBtnCancle;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    private View.OnClickListener mClickListener;

    public LDFileChoseDialog (Context context) {
        super(context);
    }

    public LDFileChoseDialog (Context context, View.OnClickListener clickListener) {
        super(context);
        mClickListener = clickListener;
    }

    public void setClickListener (View.OnClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @Override
    protected int getLayoutId () {
        return R.layout.layout_file_chose;
    }

    @Override
    protected void initView () {
        ButterKnife.bind(this);
        mBtnCancle.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
    }

    @Override
    protected void initData () {

    }

    @Override
    public void onClick (View v) {
        if (v.getId() == R.id.btn_delete) {
            if (mClickListener != null) {
                mClickListener.onClick(v);
            }
        }
        dismiss();
    }
}
