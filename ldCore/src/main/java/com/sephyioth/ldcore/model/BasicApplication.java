package com.sephyioth.ldcore.model;

import android.app.Application;
import android.content.Intent;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Message;
import android.util.Log;

import com.sephyioth.ldcore.LDCore;
import com.sephyioth.ldcore.core.LDCoreHandler;
import com.sephyioth.ldcore.observer.LDDataSetObservable;
import com.sephyioth.ldcore.params.LDNetParam;
import com.sephyioth.ldcore.params.LDParam;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/9/29 22:50
 * 修改人：Genesis
 * 修改时间：16/9/29 22:50
 * 修改备注：
 */

public abstract class BasicApplication extends Application {

    private static   BasicApplication             mLdApp;
    //全局观察者
    protected final  LDDataSetObservable          mDataSetObservable = new LDDataSetObservable();
    //LDParame内存缓存区
    private volatile LinkedBlockingQueue<LDParam> mQune;
    //预处理事件Handler
    protected        LDCoreHandler                mHandler;

    @Override
    public void onCreate () {
        super.onCreate();
        mLdApp = this;
        //初始化系统
        LDCore.initSys();
        mQune = new LinkedBlockingQueue<>();
    }

    public static BasicApplication getCoreApplication () {
        return mLdApp;
    }

    protected DataSetObservable getDataSetObservable () {
        return mDataSetObservable;
    }

    public LDParam getLDParam () {
        LDParam param = null;
        param= mQune.peek();
        return param;
    }


    public void registerDataSetObserver (DataSetObserver var1) {
        if (var1 != null) {
            mDataSetObservable.registerObserver(var1);
        }
    }

    public void unregisterDataSetObserver (DataSetObserver var1) {
        if (var1 != null) {
            mDataSetObservable.unregisterObserver(var1);
        }
    }

    public void putData (LDParam param) {
        mQune.add(param);
        notifyDataChange();
    }

    public void notifyDataChange () {
        mDataSetObservable.notifyChanged();
    }

    public void notifyDataInvalidated () {
        mDataSetObservable.notifyInvalidated();
    }

    public void sendPretreatmentMessage (Message msg) {
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    public void logout (String string) {
        Intent intent = new Intent();
        intent.setAction(com.sephyioth.ldcore.Constant.MSG_LOGOUT); // 说明动作
        sendBroadcast(intent);// 该函数用于发送广播
    }

    public void removeQune (LDParam param) {
        mQune.remove(param);
    }
}
