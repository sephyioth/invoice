package com.sephyioth.ldcore.model;

import android.content.Intent;

import com.sephyioth.ldcore.R;
import com.sephyioth.ldcore.params.LDParam;
import com.sephyioth.ldcore.widget.toast.LDToast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 类描述：
 * < * 解析类基类
 * 创建人：genesis
 * 创建时间：2019-06-11 15:04
 * 修改人：genesis
 * 修改时间：2019-06-11 15:04
 * 修改备注：
 */
public abstract class BasicModel<B> {

    protected final static String JSON_KEY_CODE     = "resultCode";
    protected final static String JSON_KEY_MESSAGE  = "returnObject";
    protected final static String JSON_KEY_ERRORMSG = "erroMsg";

    public final static int    RESULT_OK       = 1000;
    public final static int    RESULT_ERROR    = -2;
    public final static int    RESULT_FAILED   = -6;
    public final static String RESULT_FAIL_MSG = "error code";

    public    String     mJson         = "";
    protected int        mResult       = -1;
    protected String     mResultMsg;
    protected String     mErrorMsg;
    protected BasicModel mCloneModel   = null;
    protected int        mControllerID = 0;

    public boolean isParseFinish = false;

    public int getControllerID () {
        return mControllerID;
    }

    public void setControllerID (int controllerID) {
        this.mControllerID = controllerID;
    }

    /**
     * 方法描述：
     * 通用字符库解析
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/10/22
     * 修改备注：
     *
     * @version
     */
    public B parse (String json) {
        if (json == null) {
            LDToast.makeText(BasicApplication.getCoreApplication().getString(R.string.str_parse_error)).show();
            return null;
        }
        mJson = json;
//        try {
//            JSONObject object = new JSONObject(json);
//            if (object != null) {
//                mResult = object.optInt(JSON_KEY_CODE, RESULT_ERROR);
//                mResultMsg = object.optString(JSON_KEY_MESSAGE, RESULT_FAIL_MSG);
//                if (mResult != RESULT_OK) {
//                    JSONObject object1 = new JSONObject(mResultMsg);
//                    if (object1 != null) {
//                        mErrorMsg = object1.optString(JSON_KEY_ERRORMSG, "");
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    public void parseParam (LDParam param) {

    }

    public String getErrorMsg () {
        return mErrorMsg;
    }

    public String getJson () {
        return mJson;
    }

    public void setJson (String mJson) {
        this.mJson = mJson;
    }

    public String packageUrl (String... args) {
        return null;
    }

    public String packageParams (String... args) {
        return null;
    }

    public int getResult () {
        return mResult;
    }

    public void setResult (int mResult) {
        this.mResult = mResult;
    }

    public String getResultMsg () {
        return mResultMsg;
    }

    public void setResultMsg (String mResultMsg) {
        this.mResultMsg = mResultMsg;
    }

    public void parse () {
    }

    public void parseNativeFile () {

    }

    public void parseDatabase () {

    }

    public void parseNativeFile (String path) {

    }

    public JSONObject packageJson () {
        return null;
    }

    public String packageString () {
        return null;
    }

    public void parseIntent (Intent intent) {
    }

    public void save (String json) {
    }

    public void save () {
    }

    public void remove () {

    }

    public void queryDb () {

    }

    /**
     * 方法描述：
     * clone对象方法，保存运行时数据业务，防止线程异步导致错误。
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/10/21
     * 修改备注：
     *
     * @version
     */
    public Object clone () {
        BasicModel o = null;
        try {
            mCloneModel = (BasicModel) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return mCloneModel;
    }


}
