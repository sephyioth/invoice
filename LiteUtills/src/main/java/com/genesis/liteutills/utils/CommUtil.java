package com.genesis.liteutills.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.genesis.liteutills.AppDefine;
import com.genesis.liteutills.UtillManager;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hjy on 2015/12/11.
 */
public class CommUtil {
    private static final String TAG = "CommUtil";

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String stringToMD5(String string) {
        byte[] hash;
        if (string == null) {
            return "";
        }

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    /* *
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)
     *来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src, int ilen) {
        StringBuilder stringBuilder = new StringBuilder("");
        int iproclen = 0;
        if (src == null || src.length <= 0) {
            return null;
        }
        if (ilen < src.length && ilen > 0)
            iproclen = ilen;
        else
            iproclen = src.length;
        for (int i = 0; i < iproclen; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            //versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String md5_16(String str) {
        String md532 = md5(str);
        return md532.substring(8, 24);
    }

    public static boolean SimpleisMobile(String mobilenum) {
        if (!mobilenum.matches("^1\\d{10}$")) {
            return false;
        }
        return true;
    }

    public static String GetMachineInfo() {
        String strinfo = android.os.Build.MODEL + "-" + android.os.Build.VERSION.RELEASE;
        if (strinfo.length() > 99)
            return strinfo.substring(0, 99);
        else
            return strinfo;
    }

    /**
     * @param chk
     * @return
     */
    public static String chkString(String chk) {
        return md5("PzxJKuiej7^5()huituu" + chk);
    }

    public static String GetInetAddress(String host) {
        String IPAddress = "127.0.0.1";
        InetAddress ReturnStr1 = null;
        try {
            ReturnStr1 = InetAddress.getByName(host);
            IPAddress = ReturnStr1.getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return IPAddress;
        }
        return IPAddress;
    }

}
