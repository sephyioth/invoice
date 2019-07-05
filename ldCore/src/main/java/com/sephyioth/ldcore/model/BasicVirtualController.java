package com.sephyioth.ldcore.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-11 20:32
 * 修改人：genesis
 * 修改时间：2019-06-11 20:32
 * 修改备注：
 */
public abstract class BasicVirtualController {

    //全局观察者
    protected Context                       mContext;
    protected ArrayList<VirtualDevCallback> mDataSetObservable;

    protected BasicVirtualController (Context context) {
        mDataSetObservable = new ArrayList<VirtualDevCallback>();
        mContext = context;
    }

    public void notifyChanged () {
        onDataChage();
    }

    protected void onDataChage () {

    }


    public boolean registCallback (VirtualDevCallback callback) {
        if (mDataSetObservable == null) {
            mDataSetObservable = new ArrayList<>();
        }
        mDataSetObservable.add(callback);
        return true;
    }

    public boolean unregistCallback (VirtualDevCallback callback) {
        if (mDataSetObservable == null) {
            mDataSetObservable = new ArrayList<>();
        }
        mDataSetObservable.remove(callback);
        return true;
    }

    public interface VirtualDevCallback {

        void onError (String errorMsg, String error);

    }
}
