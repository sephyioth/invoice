package com.genesis.liteutills.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 类说明： SPConfig
 *
 * @author 作者 E-mail: lzrwolf@126.com
 * @version 创建时间：2015-7-11 下午10:17:41
 */
@SuppressWarnings("deprecation")
@SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
public class SPConfig {
    // ** 常量 **/
    public static final String SP_KEY_USER_LIST = "user_list";
    public static final String SP_LAST_USERNAME = "_sp_last_username";

    public static final String SP_DEVICE_ID = "sp_device_id";
    public static final String SP_WAP_CONNECTIVITY_FLAG = "sp_wap_connectivity";

    // ** 变量 **/

    // ** 构造函数 **/

    // ** 成员方法 **/

    // ** 静态方法 **/

    /**
     * 保存String型数据
     *
     * @param context
     * @param spName  SP存储名
     * @param key
     * @param value
     */
    public static void saveStringSPValue(Context context, String spName,
                                         String key, String value) {
        // 获取Context
        if (context == null) {
            return;
        }
        // 保存数据

        Editor editor = context.getSharedPreferences(spName,
                Context.MODE_WORLD_WRITEABLE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存int型数据
     *
     * @param context
     * @param spName  SP存储名
     * @param key
     * @param value
     */
    public static void saveIntSPValue(Context context, String spName,
                                      String key, int value) {
        // 获取Context
        if (context == null) {
            return;
        }
        // 保存数据
        Editor editor = context.getSharedPreferences(spName,
                Context.MODE_WORLD_WRITEABLE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存long型数据
     *
     * @param context
     * @param spName  SP存储名
     * @param key
     * @param value
     */
    public static void saveLongSPValue(Context context, String spName,
                                       String key, long value) {
        // 获取Context
        if (context == null) {
            return;
        }
        // 保存数据
        Editor editor = context.getSharedPreferences(spName,
                Context.MODE_MULTI_PROCESS).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取简单long数据
     *
     * @param context
     * @param spName  SP存储名
     * @param key
     * @return
     */
    public static long getLongSPValue(Context context, String spName, String key) {
        long vaule = 0;
        // 获取Context
        if (context == null) {
            return vaule;
        }
        // 读取数据
        SharedPreferences sp = context.getSharedPreferences(spName,
                Context.MODE_MULTI_PROCESS);
        if (sp != null && key != null) {
            vaule = sp.getLong(key, 0);
        }
        return vaule;
    }

    /**
     * 获取简单数据
     *
     * @param context
     * @param spName  SP存储名
     * @param key
     * @return
     */
    public static String getStringSPValue(Context context, String spName,
                                          String key) {
        String vaule = null;
        // 获取Context
        if (context == null) {
            return vaule;
        }
        // 读取数据
        SharedPreferences sp = context.getSharedPreferences(spName,
                Context.MODE_WORLD_READABLE);
        if (sp != null && key != null) {
            vaule = sp.getString(key, "");
        }
        return vaule;
    }

    /**
     * 获取简单数据
     *
     * @param context
     * @param spName  SP存储名
     * @param key
     * @return
     */
    public static int getIntSPValue(Context context, String spName, String key,
                                    int defaultValue) {
        int vaule = defaultValue;
        // 获取Context
        if (context == null) {
            return vaule;
        }
        // 读取数据

        SharedPreferences sp = context.getSharedPreferences(spName,
                Context.MODE_WORLD_READABLE);
        if (sp != null) {
            vaule = sp.getInt(key, defaultValue);
        }
        return vaule;
    }

    /**
     * 获取boolean数据
     *
     * @param context
     * @param spName       SP存储名
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBooleanSPValue(Context context, String spName,
                                            String key, boolean defaultValue) {
        boolean vaule = defaultValue;
        // 获取Context
        if (context == null) {
            return vaule;
        }
        // 读取数据
        SharedPreferences sp = context.getSharedPreferences(spName,
                Context.MODE_WORLD_READABLE);
        if (sp != null) {
            vaule = sp.getBoolean(key, defaultValue);
        }
        return vaule;
    }

    /**
     * 保存boolean数据
     *
     * @param context
     * @param spName  SP存储名
     * @param key
     * @param value
     */
    public static void saveBooleanSPValue(Context context, String spName,
                                          String key, boolean value) {
        // 获取Context
        if (context == null) {
            return;
        }
        // 保存数据

        Editor editor = context.getSharedPreferences(spName,
                Context.MODE_WORLD_WRITEABLE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 删除SharedPreferences键值
     *
     * @param context
     * @param spName  SP存储名
     * @param key     SP键
     */
    public static void removeSPValue(Context context, String spName, String key) {
        // 获取Context
        if (context == null) {
            return;
        }
        // 保存数据
        Editor editor = context.getSharedPreferences(spName,
                Context.MODE_WORLD_WRITEABLE).edit();
        editor.remove(key);
        editor.commit();
    }
    // ** 内部类接口 **/
}
