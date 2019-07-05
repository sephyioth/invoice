package com.sephyioth.ldcore.observer;

import android.database.DataSetObservable;
import android.os.Message;

import com.sephyioth.ldcore.Constant;
import com.sephyioth.ldcore.model.BasicApplication;
import com.sephyioth.ldcore.params.LDParam;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/9/29 17:14
 * 修改人：Genesis
 * 修改时间：16/9/29 17:14
 * 修改备注：
 */
public class LDDataSetObservable extends DataSetObservable {

    /**
     * 方法描述：
     * notify方法调度，前列控制数据存储
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/10/21
     * 修改备注：
     *
     * @version
     */
    @Override
    public void notifyChanged () {
        LDParam param = BasicApplication.getCoreApplication().getLDParam();
        Message msg = new Message();
        msg.what = Constant.MSG_DATA_CHANGE;
        msg.obj = param;
        if (param != null) {
            msg.arg1 = param.getControllerType();
        }
        BasicApplication.getCoreApplication().sendPretreatmentMessage(msg);
        super.notifyChanged();
    }

    @Override
    public void notifyInvalidated () {
        LDParam param = BasicApplication.getCoreApplication().getLDParam();
        Message msg = new Message();
        msg.what = Constant.MSG_DATA_INVALID;
        msg.obj = param;
        BasicApplication.getCoreApplication().sendPretreatmentMessage(msg);
        if (param != null) {
            msg.arg1 = param.getControllerType();
        }
        super.notifyInvalidated();
    }
}

