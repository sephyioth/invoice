package com.sephyioth.ldcore.widget.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sephyioth.ldcore.model.BasicApplication;

/**
 * 类描述：
 * 单例Toast
 * 创建人：genesis
 * 创建时间：2019-06-11 15:05
 * 修改人：genesis
 * 修改时间：2019-06-11 15:05
 * 修改备注：
 */
public class LDToast {
    private static final String TAG           = "LdToast";
    private static final int    MESSAGE_TOAST = 100023;
    public static final  int    LENGTH_LONG   = 1;
    public static final  int    LENGTH_SHORT  = 0;

    public static Toast   mToast = null;
    public        Context mContext;

    private static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage (Message msg) {
            if (msg.what == MESSAGE_TOAST) {
                LDToast.makeText(BasicApplication.getCoreApplication(), (String) msg.obj,
                        LDToast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };

    public LDToast (Context context) {
//        super(context);
        this.mContext = context;
    }

    public static Toast getIntence () {
        return mToast;
    }

    public static Toast makeText (Context context, String txt, int time) {
        if (context == null) {
            context = BasicApplication.getCoreApplication();
        }
        if (mToast != null) {
            mToast.setText(txt);
            Log.i(TAG, "Toast add" + txt);
        } else {
            Log.i(TAG, "Toast new " + txt);
            if (context != null) {
                mToast = Toast.makeText(context, txt, time);
            }
        }
        return mToast;
    }

    public static void sendMessage (String msg) {
        if (msg != null) {
            Message message = new Message();
            message.what = MESSAGE_TOAST;
            message.obj = msg;
            mHandler.sendMessage(message);
        }
    }

    public static Toast makeText (String string, int time) {
        return LDToast.makeText(BasicApplication.getCoreApplication(), string, time);
    }

    public static Toast makeText (String string) {
        return LDToast.makeText(BasicApplication.getCoreApplication(), string, Toast.LENGTH_LONG);
    }
}
