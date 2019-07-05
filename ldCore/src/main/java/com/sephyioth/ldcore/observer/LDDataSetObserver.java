package com.sephyioth.ldcore.observer;

import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sephyioth.ldcore.Constant;
import com.sephyioth.ldcore.model.BasicActivity;
import com.sephyioth.ldcore.model.BasicApplication;
import com.sephyioth.ldcore.model.BasicFragment;
import com.sephyioth.ldcore.model.BasicLayout;
import com.sephyioth.ldcore.model.BasicModel;
import com.sephyioth.ldcore.params.LDNetParam;
import com.sephyioth.ldcore.params.LDParam;


/**
 * 类描述：
 * 观察对象
 * 创建人：Genesis
 * 创建时间：16/9/29 16:23
 * 修改人：Genesis
 * 修改时间：16/9/29 16:23
 * 修改备注：
 */
public class LDDataSetObserver<M, V> extends DataSetObserver {

    private int           mIds;
    private BasicActivity mController;
    private BasicFragment mControllerFragment;
    private M             mModel;
    private V             mView;

    /**
     * 方法描述：
     * 系统调度刷新业务接口
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/10/21
     * 修改备注：
     *
     * @version
     */
    protected static Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            if (msg.what == Constant.MSG_VIEW_REFRESH) {
                if (msg.obj instanceof LDDataSetObject) {
                    LDDataSetObject object = (LDDataSetObject) msg.obj;
                    final Object model = object.getModel();
                    final Object view = object.getView();
                    if (view != null) {
                        if (view instanceof BasicLayout) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run () {
                                    ((BasicLayout) view).onFreshModel((BasicModel) model);
                                }
                            });
                        }
                    }
                }
            }
        }
    };


    public LDDataSetObserver (BasicActivity controller) {
        this.mController = controller;
        mIds = controller.getIds();
    }

    public LDDataSetObserver (BasicActivity controller, M model) {
        this.mController = controller;
        this.mModel = model;
        mIds = controller.getIds();
    }

    public LDDataSetObserver (BasicActivity controller, M model, V view) {
        this.mController = controller;
        this.mModel = model;
        this.mView = view;
        mIds = controller.getIds();
    }

    public LDDataSetObserver (BasicFragment controller) {
        this.mControllerFragment = controller;
        mIds = controller.getId();
    }

    public LDDataSetObserver (BasicFragment controller, M model) {
        this.mControllerFragment = controller;
        this.mModel = model;
        mIds = controller.getId();
    }

    public LDDataSetObserver (BasicFragment controller, M model, V view) {
        this.mControllerFragment = controller;
        this.mModel = model;
        this.mView = view;
        mIds = controller.getId();
    }

    @Override
    public void onChanged () {
        super.onChanged();
        final LDParam param = BasicApplication.getCoreApplication().getLDParam();
        if (param != null) {
            if (mController != null) {
                if (mIds == param.getControllerID()) {
                    if (mModel != null) {
                        if (mModel instanceof BasicModel) {
                            new Thread(new Runnable() {
                                @Override
                                public void run () {
                                    ((BasicModel) mModel).parseParam(param);
                                    mController.onChanged(mModel);
                                    Message message = new Message();
                                    message.what = Constant.MSG_VIEW_REFRESH;
                                    LDDataSetObject object = new LDDataSetObject(mModel, mView);
                                    message.obj = object;
                                    mHandler.sendMessage(message);
                                }
                            }).start();
                        }
                    }
                    mController.onChanged(param);
                }
            } else if (mControllerFragment != null) {
                if (mIds == mControllerFragment.getId()) {
                    if (mModel != null) {
                        if (mModel instanceof BasicModel) {
                            new Thread(new Runnable() {
                                @Override
                                public void run () {
                                    ((BasicModel) mModel).parseParam(param);
                                    mControllerFragment.onChanged(mModel);
                                    Message message = new Message();
                                    message.what = Constant.MSG_VIEW_REFRESH;
                                    LDDataSetObject object = new LDDataSetObject(mModel, mView);
                                    message.obj = object;
                                    mHandler.sendMessage(message);
                                }
                            }).start();
                        }
                    }
                    mControllerFragment.onChanged(param);
                }
            }
        }
        BasicApplication.getCoreApplication().removeQune(param);
    }

    @Override
    public void onInvalidated () {
        super.onInvalidated();
        LDParam param = BasicApplication.getCoreApplication().getLDParam();
        if (param != null) {
            if (mController != null) {
                mController.onInvalidated(param);
            } else if (mControllerFragment != null) {
                mControllerFragment.onInvalidated(param);
            }
        }
        BasicApplication.getCoreApplication().removeQune(param);
    }

    public int getIds () {
        return mIds;
    }

    public M getModel () {
        return mModel;
    }

    public void setModel (M mModel) {
        this.mModel = mModel;
    }

    public V getView () {
        return mView;
    }

    public void setView (V mView) {
        this.mView = mView;
    }

    private static class LDDataSetObject {
        private Object mModel;
        private Object mView;

        public LDDataSetObject (Object model, Object view) {
            this.mModel = model;
            this.mView = view;
        }

        public Object getModel () {
            return mModel;
        }

        public void setModel (Object mModel) {
            this.mModel = mModel;
        }

        public Object getView () {
            return mView;
        }

        public void setView (Object mView) {
            this.mView = mView;
        }
    }
}
