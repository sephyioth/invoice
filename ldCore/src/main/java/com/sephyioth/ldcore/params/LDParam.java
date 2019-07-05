package com.sephyioth.ldcore.params;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：2016/10/12 15:18
 * 修改人：Genesis
 * 修改时间：2016/10/12 15:18
 * 修改备注：
 */

public class LDParam {

    protected Object mObj;
    protected int    mControllerID;
    protected int    mRequestID;
    protected int    mControllerType;
    protected int    mResultCode;

    public int getControllerType () {
        return mControllerType;
    }

    public void setControllerType (int mControllerType) {
        this.mControllerType = mControllerType;
    }

    public int getControllerID () {
        return mControllerID;
    }

    public void setControllerID (int mResultCode) {
        this.mControllerID = mResultCode;
    }

    public int getRequestID () {
        return mRequestID;
    }

    public void setRequestID (int mRequestID) {
        this.mRequestID = mRequestID;
    }

    public int getResultCode () {
        return mResultCode;
    }

    public void setResultCode (int resultCode) {
        this.mResultCode = resultCode;
    }

    public Object getObj () {
        return mObj;
    }

    public void setObj (Object mObj) {
        this.mObj = mObj;
    }

}
