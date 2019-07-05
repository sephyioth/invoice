package com.sephyioth.ldcore.model;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sephyioth.ldcore.callback.LDNetDateCallback;
import com.sephyioth.ldcore.observer.LDDataSetObserver;
import com.sephyioth.ldcore.widget.titlebar.TitleBar;


/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/5/17 15:17
 * 修改人：Genesis
 * 修改时间：16/5/17 15:17
 * 修改备注：
 */

public abstract class BasicFragment<M extends BasicModel, V extends BasicLayout> extends Fragment implements LDNetDateCallback {

    private   LDDataSetObserver<M, V> mServer;
    protected M                       mModel;
    protected V                       mView;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        BasicApplication.getCoreApplication().notifyDataInvalidated();
        BasicApplication.getCoreApplication().unregisterDataSetObserver(mServer);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        Log.i("Basicfragment", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        initView(getView());
        mServer = new LDDataSetObserver(this, mModel, mView);
        BasicApplication.getCoreApplication().registerDataSetObserver(mServer);
    }


    public V getControllerView () {
        return mView;
    }

    public void setControllerView (V mView) {
        this.mView = mView;
    }

    public M getModel () {
        return mModel;
    }

    public void setModel (M mModel) {
        this.mModel = mModel;
    }

    protected abstract int onCreateViewResID ();

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        Log.i("Basicfragment", "onCreateView");
        View view = null;
        int id = onCreateViewResID();
        if (getResources().getLayout(id) != null) {
            view = inflater.inflate(id, null);
        }
        if (view == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }

    @Override
    public void onResume () {
        super.onResume();
    }

    protected void setTitle (String title) {
        Activity activity = getActivity();
        if (activity instanceof BasicActivity) {
            ((BasicActivity) activity).setTitle(title);
        }

    }

    protected void setRightBtnVisable (int visable) {
        Activity activity = getActivity();
        if (activity instanceof BasicActivity) {
            ((BasicActivity) activity).setRightBtnVisable(visable);
        }
    }

    protected void setLeftBtnVisable (int visable) {
        Activity activity = getActivity();
        if (activity instanceof BasicActivity) {
            ((BasicActivity) activity).setLeftBtnVisable(visable);
        }
    }

    protected void clearRightBtnAction () {
        Activity activity = getActivity();
        if (activity instanceof BasicActivity) {
            ((BasicActivity) activity).clearRightBtnAction();
        }
    }

    protected void addRightBtnAction (TitleBar.Action onClickListener) {
        Activity activity = getActivity();
        if (activity instanceof BasicActivity) {
            ((BasicActivity) activity).addRightBtnAction(onClickListener);
        }
    }

    protected int getObserverId () {
        return getId();
    }

    protected TitleBar getTitleBar () {
        Activity activity = getActivity();
        if (activity instanceof BasicActivity) {
            return ((BasicActivity) activity).getTitleBar();
        }
        return null;
    }

    protected abstract void initView (View view);
}
