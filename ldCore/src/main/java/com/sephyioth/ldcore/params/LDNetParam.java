package com.sephyioth.ldcore.params;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/9/30 09:11
 * 修改人：Genesis
 * 修改时间：16/9/30 09:11
 * 修改备注：
 */

public class LDNetParam extends LDParam {
    private String mNetData;
    private byte[] mBuffer;

    public LDNetParam (int requestCode, String string) {
        this.mControllerID = requestCode;
        this.mNetData = string;
        this.mObj = string;
    }

    public LDNetParam (int requestCode, byte[] buffers) {
        this.mControllerID = requestCode;
        this.mBuffer = buffers;
        this.mObj = buffers;
    }


    public String getNetData () {
        return mNetData;
    }

    public void setNetData (String mNetData) {
        this.mNetData = mNetData;
    }

    public byte[] getBuffer () {
        return mBuffer;
    }

    public void setBuffer (byte[] mBuffer) {
        this.mBuffer = mBuffer;
    }


}
