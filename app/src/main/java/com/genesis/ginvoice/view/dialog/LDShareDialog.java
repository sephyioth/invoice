package com.genesis.ginvoice.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.genesis.ginvoice.R;


/**
 * 项目名称：huituapp
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/4/29 15:57
 * 修改人：Genesis
 * 修改时间：16/4/29 15:57
 * 修改备注：
 */

public class LDShareDialog extends LDBasicDialog implements View.OnClickListener {

    public static final Integer TYPE_SHARE_QQ = 1;
    public static final Integer TYPE_SHARE_QZONE = 2;
    public static final Integer TYPE_SHARE_WECHAR = 3;
    public static final Integer TYPE_SHARE_WEIBO = 4;
    public static final Integer TYPE_SHARE_WECHAR_C = 5;


    private ImageView mImageWechat, mImageQQ, mImageQZone, mImageWb, mImageWechatCircle;

    public LDShareDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_share_dialog;
    }

    @Override
    protected void initView() {
        setCanceledOnTouchOutside(true);
        mImageQQ = (ImageView) findViewById(R.id.image_qq);
        mImageWb = (ImageView) findViewById(R.id.image_wb);
        mImageQZone = (ImageView) findViewById(R.id.image_qzone);
        mImageWechat = (ImageView) findViewById(R.id.image_wc);
        mImageWechatCircle = (ImageView) findViewById(R.id.image_wcc);
        mImageQQ.setOnClickListener(this);
        mImageQZone.setOnClickListener(this);
        mImageWb.setOnClickListener(this);
        mImageWechat.setOnClickListener(this);
        mImageWechatCircle.setOnClickListener(this);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int retType = 0;
        switch (v.getId()) {
            case R.id.image_qq:
                retType = TYPE_SHARE_QQ;
                break;
            case R.id.image_qzone:
                retType = TYPE_SHARE_QZONE;
                break;
            case R.id.image_wb:
                retType = TYPE_SHARE_WEIBO;
                break;
            case R.id.image_wc:
                retType = TYPE_SHARE_WECHAR;
                break;
            case R.id.image_wcc:
                retType = TYPE_SHARE_WECHAR_C;
                break;
        }
        if (mDialogItemsClickListener != null) {
            mDialogItemsClickListener.onClick(v, retType);
        }
    }
}
