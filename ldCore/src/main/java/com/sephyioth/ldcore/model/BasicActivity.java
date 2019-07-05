package com.sephyioth.ldcore.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sephyioth.ldcore.R;
import com.sephyioth.ldcore.callback.LDNetDateCallback;
import com.sephyioth.ldcore.observer.LDDataSetObserver;
import com.sephyioth.ldcore.widget.titlebar.TitleBar;
import com.sephyioth.ldcore.widget.toast.LDToast;


import java.io.ObjectInputStream;
import java.lang.reflect.Type;

import static com.sephyioth.ldcore.Constant.MSG_LOGOUT;
import static com.sephyioth.ldcore.Constant.MSG_NETWORK_ERROR;
import static com.sephyioth.ldcore.Constant.MSG_SERVICE_START;
import static com.sephyioth.ldcore.Constant.MSG_SERVICE_STOP;


/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/5/3 16:39
 * 修改人：Genesis
 * 修改时间：16/5/3 16:39
 * 修改备注：
 */
public abstract class BasicActivity<M extends BasicModel, V extends BasicLayout> extends Activity implements LDNetDateCallback {
    protected static final int               TYPE_UNUSING_ACTIVITY_TITLE = 0;
    private                Handler           mHandler                    = new Handler();
    private                TitleBar          mTitleBar;
    private                View              mContentView;
    private                ProgressDialog    mPDWait;
    private                Runnable          mRunable;
    private                LDDataSetObserver mServer;
    protected              V                 mView;
    protected              M                 mModel;
    protected              int               mIds;  //业务系统ID
    public static          int               mIDCount;
    private                View              headerView;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive (Context context, Intent intent) {
            if (intent != null) {
                doAction(intent.getAction());
                if (MSG_LOGOUT.equals(intent.getAction())) {
                    unregisterReceiver(this); // 这句话必须要写要不会报错，不写虽然能关闭，会报一堆错
                    ((Activity) context).finish();
                }

            }
        }
    };

    protected void doAction (String action) {
        if (MSG_NETWORK_ERROR.equals(action)) {

        } else if (MSG_SERVICE_START.equals(action)) {

        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        mIds = ++mIDCount;
        android.util.Log.i("basicActivity", "create parents");
        super.onCreate(savedInstanceState);
        boolean isImmersive = false;
        if (!hasLollipop()) {
            isImmersive = true;
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
        } else if (hasLollipop()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            isImmersive = true;
        }
        try {
            headerView = RelativeLayout.inflate(this, R.layout.layout_top_title, null);
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
            int dim = (int) getResources().getDimension(R.dimen.default_titlebar_height);
            if (isImmersive)
                dim = (int) getResources().getDimension(R.dimen.default_titlebar_height) + TitleBar.getStatusBarHeight();

            params.height = dim;
            params.width = getWindowManager().getDefaultDisplay().getWidth();
            params.bottomMargin = 100;
            setContentView(headerView, params);
            int id = onCreateViewResID();

            mPDWait = new ProgressDialog(this);
            mPDWait.setCancelable(false);
            mPDWait.setIndeterminate(true);
            mPDWait.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            if (headerView != null) {
                android.util.Log.i("titleview", "getTitle ...");
                mTitleBar = (TitleBar) findViewById(R.id.title_bar);
                mTitleBar.setImmersive(isImmersive);
                mTitleBar.setBackgroundColor(getResources().getColor(R.color.color_background));
                mTitleBar.setImmersive(isImmersive);
                mTitleBar.setLeftImageResource(R.mipmap.ic_arrow_back_black);
                mTitleBar.setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        finish();
                    }
                });
            }
            if (getResources().getLayout(id) != null) {
                mContentView = FrameLayout.inflate(this, onCreateViewResID(), null);
                FrameLayout.LayoutParams contentParams =
                        new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                contentParams.setMargins(0, dim, 0, 0);
                contentParams.bottomMargin = dim / 2;
                addContentView(mContentView, contentParams);
            } else {
                mTitleBar.setVisibility(View.GONE);
                onCreateActivity(savedInstanceState);
            }
        } catch (Resources.NotFoundException e) {
            onCreateActivity(savedInstanceState);
        } catch (NoClassDefFoundError e) {

        }
        initView();
        registSystem();
    }

    public View getHeaderView () {
        return headerView;
    }

    private void registSystem () {
        if (mModel == null || mView == null) {
            return;
        }
        mServer = new LDDataSetObserver<M, V>(this, mModel, mView);
        mModel.setControllerID(getIds());
        BasicApplication.getCoreApplication().registerDataSetObserver(mServer);
        registActivity();
    }

    protected void onCreateActivity (Bundle savedInstanceState) {

    }

    @Override
    public void setTitle (CharSequence title) {
        super.setTitle(title);
        if (mTitleBar != null) {
            mTitleBar.setTitle(title);
        }
    }

    @Override
    public void setTitle (int titleId) {
        super.setTitle(titleId);
        if (mTitleBar != null) {
            mTitleBar.setTitle(titleId);
        }
    }

    private void registActivity () {
        IntentFilter filter = new IntentFilter();
        filter.addAction(MSG_LOGOUT);
        filter.addAction(MSG_NETWORK_ERROR);
        filter.addAction(MSG_SERVICE_START);
        filter.addAction(MSG_SERVICE_STOP);
        registerReceiver(this.mBroadcastReceiver, filter); // 注册
    }

    private void unRegistActivity () {
        try {
            unregisterReceiver(this.mBroadcastReceiver);
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
        }
    }

    /**
     * 方法描述：
     * <p/>
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/5/19
     * 修改备注：
     *
     * @version
     */
    public void setLeftBtnVisable (int isVisable) {
        if (mTitleBar != null) {
            mTitleBar.setLeftVisible(isVisable == View.VISIBLE);
        }
    }

    public V getControllerView () {
        return mView;
    }

    public void setControllerView (V mView) {
        if (mServer != null) {
            mServer.setView(mView);
        }
        this.mView = mView;
    }

    public M getModel () {

        return mModel;
    }

    public void setModel (M mModel) {
        if (mServer != null) {
            mServer.setModel(mModel);
        }
        this.mModel = mModel;
    }

    /**
     * 方法描述：
     * 设置右边按键的可见性
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/9/13
     * 修改备注：
     *
     * @version
     */
    public void setRightBtnVisable (int isVisable) {
        if (mTitleBar != null) {
            mTitleBar.setRightVisable(isVisable == View.VISIBLE);
        }
    }

    /**
     * 方法描述：
     * 显示等待框Dialog
     * <p>
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/9/13
     * 修改备注：
     *
     * @version
     */
    protected void showWait (String msg) {
        if (msg != null) {
            mPDWait.setMessage(msg);
        } else {
            mPDWait.setMessage(getString(R.string.str_waiting));
        }
        mRunable = new Runnable() {
            @Override
            public void run () {
                hideWait();
                LDToast.makeText(getApplicationContext(), getString(R.string.str_parse_error),
                        LDToast.LENGTH_LONG).show();
            }
        };
        mHandler.postDelayed(mRunable, 10000);
        mPDWait.show();
    }

    @Override
    protected void onResume () {
        super.onResume();
    }

    /**
     * 方法描述：
     * 取得titlebar相关控件的引用
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/9/13
     * 修改备注：
     *
     * @version
     */
    public TitleBar getTitleBar () {
        return mTitleBar;
    }

    /**
     * 隐藏dialog框
     */
    protected void hideWait () {
        mPDWait.cancel();
        mHandler.removeCallbacks(mRunable);
    }

    /**
     * 方法描述：
     * 设置title
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/9/13
     * 修改备注：
     *
     * @version
     */
    protected void setTitle (String title) {
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        if (mTitleBar != null) {
            mTitleBar.setTitle(title);
            mTitleBar.invalidate();
        }
    }

    /**
     * 方法描述：
     * 设置做标题监听事件
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/9/13
     * 修改备注：
     *
     * @version
     */
    public void setTopLeftBtnListener (View.OnClickListener onClickListener) {
        setLeftBtnVisable(View.VISIBLE);
        if (mTitleBar != null) {
            mTitleBar.setLeftClickListener(onClickListener);
        }
    }

    /**
     * 方法描述：
     * 设置右标题
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/9/13
     * 修改备注：
     *
     * @version
     */
    public void addRightBtnAction (TitleBar.Action action) {
        setRightBtnVisable(View.VISIBLE);
        if (mTitleBar != null) {
            mTitleBar.addAction(action);
        }
    }

    public int getIds () {
        return mIds;
    }

    public void setIds (int mIds) {
        this.mIds = mIds;
    }

    /**
     * 方法描述：
     * 清楚所有Action
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/9/13
     * 修改备注：
     *
     * @version
     */
    public void clearRightBtnAction () {
        if (mTitleBar != null) {
            mTitleBar.removeAllActions();
            mTitleBar.invalidate();
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        unRegistActivity();
        BasicApplication.getCoreApplication().notifyDataInvalidated();
        BasicApplication.getCoreApplication().unregisterDataSetObserver(mServer);
    }

    public boolean hasLollipop () {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    protected int getObserverId () {
        if (mServer == null) {
            return mIds;
        }
        return mServer.getIds();
    }

    protected abstract void initView ();

    protected abstract int onCreateViewResID ();

}
