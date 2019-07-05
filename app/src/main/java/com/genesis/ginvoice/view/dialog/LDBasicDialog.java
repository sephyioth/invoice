package com.genesis.ginvoice.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.view.dialog.interfaces.OnDialogClickListener;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/8/13 21:48
 * 修改人：Genesis
 * 修改时间：16/8/13 21:48
 * 修改备注：
 */

public abstract class LDBasicDialog extends Dialog {

    private   Window                mWindow = null;
    private   Context               mContext;
    protected OnDialogClickListener mDialogItemsClickListener;

    public LDBasicDialog(Context context) {
        super(context);
        init();
    }

    public LDBasicDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void init() {
        setCanceledOnTouchOutside(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int id = getLayoutId();
        if (getContext().getResources().getLayout(id) != null) {
            setContentView(id);
        }
        setCanceledOnTouchOutside(true);
        initView();
        windowDeploy();
    }

    protected int getAnimations() {
        return R.style.dialogWindowAnim;
    }

    protected void windowDeploy() {
        mWindow = getWindow();
        mWindow.setWindowAnimations(getAnimations());
        mWindow.setBackgroundDrawableResource(R.color.colorTrans);
        WindowManager.LayoutParams wl = mWindow.getAttributes();
        mWindow.setGravity(getGravity());
        mWindow.setAttributes(wl);
    }

    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @SuppressWarnings("deprecation")
    public void setDialogFullScreen(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // ���ÿ��
        getWindow().setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        initData();
        setDialogFullScreen(getWindow().getWindowManager());
    }

    public OnDialogClickListener getDialogClickListener() {
        return mDialogItemsClickListener;
    }

    public void setDialogClickListener(
            OnDialogClickListener mDialogItemsClickListener) {
        this.mDialogItemsClickListener = mDialogItemsClickListener;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

}
