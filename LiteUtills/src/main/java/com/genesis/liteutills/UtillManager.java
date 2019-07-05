package com.genesis.liteutills;

import android.content.Context;
import android.util.Log;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/5/9 10:29
 * 修改人：Genesis
 * 修改时间：16/5/9 10:29
 * 修改备注：
 */

public class UtillManager {

    public final static String LOG_TAG = "Utill_Manager";
    public static Context mContext;

    public UtillManager(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
    }

    public static Context getContext() {
        if (mContext == null) {
            Log.e(LOG_TAG, "init error");
        }
        return mContext;
    }

    public static void init (Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
    }
}
