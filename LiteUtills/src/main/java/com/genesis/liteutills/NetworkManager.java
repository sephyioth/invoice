package com.genesis.liteutills;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.genesis.liteutills.listener.NetworkStateListener;
import com.genesis.liteutills.utils.SPConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 该类用于操作与网络相关的方法
 *
 * @author ww
 */
public class NetworkManager {

    public static final String TAG = "HXNetworkManager";
    /**
     * 检测到的网络状态
     */
    public static final int STATUS_CONNECTED = 0;
    public static final int STATUS_CONNECTTING_AND_CONNECTTING = 1;
    public static final int STATUS_CONNECTTING_AND_AUTHENTICATING = 2;
    public static final int STATUS_CONNECTTING_AND_OBTAINING_IPADDR = 3;
    public static final int STATUS_DISCONNECTED_AND_SCANNING = 4;
    public static final int STATUS_DISCONNECTED = 5;
    public static final int STATUS_CONNMANAGER_NULL = 6;
    // activeInfo不为null但是状态未知
    public static final int STATUS_ACTIVEINFO_NOT_NULL_UNKNOWN = 7;
    // activeInfo为null且状态未知
    public static final int STATUS_ACTIVEINFO_NULL_WIFI_UNKNOWN = 8;
    // activeInfo为null且状态未知
    public static final int STATUS_ACTIVEINFO_NULL_MOBILE_UNKNOWN = 9;
    // 网络连接方式
    public static final int TYPE_NETWORK_CONN_UNKOWN = 0; // 未知
    public static final int TYPE_NETWORK_CONN_WIFI = 1; // wifi连接
    public static final int TYPE_NETWORK_CONN_MOBILE = 2; // mobile连接
    // mobile下的连接方式
    public static final int TYPE_MOBIEL_CONN_INVALID = -1; // 未初始化
    public static final int TYPE_MOBILE_CONN_UNKOWN = 0; // 未知
    public static final int TYPE_MOBILE_CONN_3GNET = 1; // 3gnet连接
    public static final int TYPE_MOBILE_CONN_3GWAP = 2; // 3gwap连接
    public static final int TYPE_MOBILE_CONN_UNIET = 3; // uniet连接
    public static final int TYPE_MOBILE_CONN_UNIWAP = 4; // uniwap连接
    public static final int TYPE_MOBILE_CONN_CMNET = 5; // cmnet连接
    public static final int TYPE_MOBILE_CONN_CMWAP = 6; // cmwap连接
    public static final int TYPE_MOBILE_CONN_CTNET = 7; // ctnet连接
    public static final int TYPE_MOBILE_CONN_CTWAP = 8; // ctwap连接
    // 运营商信息
    public static final int TYPE_SIM_OPERATOR_UNKOWN = 0; // 未知
    public static final int TYPE_SIM_OPERATOR_MOBILE = 1; // 中国移动
    public static final int TYPE_SIM_OPERATOR_UNICOM = 2; // 中国联通
    public static final int TYPE_SIM_OPERATOR_TELECOM = 3; // 中国电信
    // 在wap接入点下是否可以连接9528端口
    public static final int TYPE_WAP_CONNECTIVITY_UNKOWN = 0; // 未知
    public static final int TYPE_WAP_CONNECTIVITY_ENABLE = 1; // 可连通
    public static final int TYPE_WAP_CONNECTIVITY_UNENABLE = -1; // 不可连通

    // 对应的net和wap接入点
    public static final String CTWAP = "ctwap";
    public static final String CMWAP = "cmwap";
    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    public static final String CTNET = "ctnet";
    public static final String CMNET = "cmnet";
    public static final String NET_3G = "3gnet";
    public static final String UNINET = "uninet";
    public static final Uri PREFERRED_APN_URI = Uri
            .parse("content://telephony/carriers/preferapn");

    // 这个过程中检查网络的次数最多3次
    public static final int CHECK_NETWORK_MOST_TIME = 5;

    // 网络状态
    private int networkConnectionStatus;
    private Context context;
    private Timer timer;
    private TimerTask task;
    // 如果联网准备过程中检测到正在连接需要在此检查的次数，是要累加的
    private int checkNetTypeTime = 0;
    private GoNextAfterCheckNet goNextAfterCheckNet;
    // 是否在检查过程中
    private boolean isChecking = false;
    private boolean isDebug = false;
    private int mobileApnType = TYPE_MOBIEL_CONN_INVALID;
    private byte[] lockTimer = new byte[0]; // Time的线程同步锁

    /**
     * 于框架无关的构造函数
     */
    public NetworkManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public void checkNetworkAvalible(GoNextAfterCheckNet goNextAfterCheckNet) {
        // 防止这次没有检测完又有下一次检测
        if (!isChecking) {
            isChecking = true;
            checkNetTypeTime = 0;
            this.goNextAfterCheckNet = goNextAfterCheckNet;
            handleNetworkType();
        } else {
            Log.e(TAG,
                    "HXNetwork checkNetworkAvalible isChecking true");
        }
    }

    /**
     * @return 判断网络连接是否可用
     */
    public static boolean isAvaliable() {
        boolean avaliable = false;
        ConnectivityManager cm = (ConnectivityManager) UtillManager.getContext()
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                avaliable = networkInfo.isAvailable();
            }
        }

        return avaliable;
    }

    /**
     * 检查网络是否可用
     *
     * @return
     */
    public static boolean isNetAvaliable() {
        boolean isNetAvaliable = false;
        ConnectivityManager cm = (ConnectivityManager) UtillManager.getContext()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            isNetAvaliable = false;
        } else {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                State state = ni.getState();
                if (state == State.CONNECTED || state == State.UNKNOWN) {
                    isNetAvaliable = true;
                }
            } else {
                NetworkInfo wifiInfo = cm
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobileInfo = cm
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (wifiInfo == null && mobileInfo == null) {
                    isNetAvaliable = false;
                } else {
                    State wifiState = State.DISCONNECTED;
                    State mobileState = State.DISCONNECTED;
                    if (wifiInfo != null) {
                        wifiState = wifiInfo.getState();
                    }

                    if (mobileInfo != null) {
                        mobileState = mobileInfo.getState();
                    }

                    if (wifiState == State.DISCONNECTED
                            && mobileState == State.DISCONNECTED
                            || wifiState == State.CONNECTING
                            || mobileState == State.CONNECTING) {
                        isNetAvaliable = false;
                    } else {
                        // wifiState == State.UNKNOWN || mobileState ==
                        // State.UNKNOWN
                        isNetAvaliable = true;
                    }
                }
            }
        }
        return isNetAvaliable;
    }

    /**
     * 检查网络当前状态
     *
     * @return
     */
    public int getNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return STATUS_CONNMANAGER_NULL;
        } else {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                State state = ni.getState();
                if (state == State.CONNECTED) {
                    return STATUS_CONNECTED;
                } else if (state == State.UNKNOWN) {
                    return STATUS_ACTIVEINFO_NOT_NULL_UNKNOWN;
                }
            } else {
                NetworkInfo wifiInfo = cm
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobileInfo = cm
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (wifiInfo == null && mobileInfo == null) {
                    return STATUS_DISCONNECTED;
                }
                State wifiState = State.DISCONNECTED;
                State mobileState = State.DISCONNECTED;
                DetailedState wifiDetailedState = DetailedState.DISCONNECTED;
                DetailedState mobileDetailedState = DetailedState.DISCONNECTED;
                if (wifiInfo != null) {
                    wifiState = wifiInfo.getState();
                    wifiDetailedState = wifiInfo.getDetailedState();
                }

                if (mobileInfo != null) {
                    mobileState = mobileInfo.getState();
                    mobileDetailedState = mobileInfo.getDetailedState();
                }

                /**
                 * 如果wifi和mobile的状态都是DISCONNECTED，先轮询检查一下
                 */
                if (wifiState == State.DISCONNECTED
                        && mobileState == State.DISCONNECTED) {
                    if (mobileDetailedState == DetailedState.SCANNING
                            || wifiDetailedState == DetailedState.SCANNING) {
                        return STATUS_DISCONNECTED_AND_SCANNING;
                    } else {
                        /**
                         * 两种情况： (mobileDetailedState ==
                         * DetailedState.DISCONNECTED && wifiDetailedState ==
                         * DetailedState.DISCONNECTED) || (mobileDetailedState
                         * == DetailedState.IDLE || wifiDetailedState ==
                         * DetailedState.IDLE)
                         */
                        return STATUS_DISCONNECTED;
                    }
                } else if (wifiState == State.CONNECTING
                        || mobileState == State.CONNECTING) {
                    int networkConnectionType = STATUS_CONNECTTING_AND_CONNECTTING;
                    if (wifiDetailedState == DetailedState.CONNECTING
                            || mobileDetailedState == DetailedState.CONNECTING) {
                        networkConnectionType = STATUS_CONNECTTING_AND_CONNECTTING;
                    } else if (wifiDetailedState == DetailedState.AUTHENTICATING
                            || mobileDetailedState == DetailedState.AUTHENTICATING) {// 正在认证
                        networkConnectionType = STATUS_CONNECTTING_AND_AUTHENTICATING;
                    } else if (wifiDetailedState == DetailedState.OBTAINING_IPADDR
                            || mobileDetailedState == DetailedState.OBTAINING_IPADDR) {// 正在获取ip地址
                        networkConnectionType = STATUS_CONNECTTING_AND_OBTAINING_IPADDR;
                    }
                    return networkConnectionType;
                } else if (wifiState == State.UNKNOWN) {
                    return STATUS_ACTIVEINFO_NULL_WIFI_UNKNOWN;
                } else if (mobileState == State.UNKNOWN) {
                    return STATUS_ACTIVEINFO_NULL_MOBILE_UNKNOWN;
                } else {
                    return STATUS_DISCONNECTED;
                }
            }
        }
        return STATUS_DISCONNECTED;
    }

    private void handleNetWorkStateInTimeTask() {
        boolean isCancleTimeTask = false;
        boolean isSuccess = false; // 网络是否可用
        networkConnectionStatus = getNetworkStatus();
        if (isDebug) {
            networkConnectionStatus = 0;
        }
        switch (networkConnectionStatus) {
            case NetworkManager.STATUS_CONNECTED:
                isSuccess = true;
                isCancleTimeTask = true;
                break;
            case NetworkManager.STATUS_CONNMANAGER_NULL:
            case NetworkManager.STATUS_ACTIVEINFO_NOT_NULL_UNKNOWN:
            case NetworkManager.STATUS_ACTIVEINFO_NULL_WIFI_UNKNOWN:
            case NetworkManager.STATUS_ACTIVEINFO_NULL_MOBILE_UNKNOWN:
                isSuccess = true;
                isCancleTimeTask = true;
                break;
            case NetworkManager.STATUS_CONNECTTING_AND_CONNECTTING:
                isCancleTimeTask = checkNetTypeTime > NetworkManager.CHECK_NETWORK_MOST_TIME - 1;
                break;
            case NetworkManager.STATUS_CONNECTTING_AND_AUTHENTICATING:
                isCancleTimeTask = checkNetTypeTime > NetworkManager.CHECK_NETWORK_MOST_TIME - 1;
                break;
            case NetworkManager.STATUS_CONNECTTING_AND_OBTAINING_IPADDR:
                isCancleTimeTask = checkNetTypeTime > NetworkManager.CHECK_NETWORK_MOST_TIME - 1;
                break;
            case NetworkManager.STATUS_DISCONNECTED_AND_SCANNING:
                isCancleTimeTask = checkNetTypeTime > NetworkManager.CHECK_NETWORK_MOST_TIME - 1;
                break;
            case NetworkManager.STATUS_DISCONNECTED:
                isCancleTimeTask = true;
                break;
            default:
                isCancleTimeTask = true;
                break;
        }
        if (isCancleTimeTask) {
            timerCancel();
            isChecking = false;
            if (isSuccess) {
                goNextAfterCheckNet.goNextSuccess(networkConnectionStatus);
            } else {
                goNextAfterCheckNet.goNextFail();
            }
        } else {
            checkNetTypeTime++;
        }
    }

    /**
     * 处理网络类型
     */
    private void handleNetworkType() {
        synchronized (lockTimer) {
            if (timer == null) {
                timer = new Timer("timer_HXNetworkManager");
            }
        }
        task = new TimerTask() {
            @Override
            public void run() {
                handleNetWorkStateInTimeTask();
            }
        };
        try {
            if (timer != null && task != null) {
                timer.schedule(task, 0, 400);
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG,
                    "HXNetwork handleNetworkType " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG,
                    "HXNetwork handleNetworkType " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getNetworkConnectionStatus() {
        return networkConnectionStatus;
    }

    /**
     * 取消定时器
     */
    private void timerCancel() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        synchronized (lockTimer) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    /**
     * 获取接入点类型
     *
     * @return 如果正在使用的网络是手机卡，就返回正在使用的接入点类型；否则返回为未知
     */
    public int getMobileApnType() {
        return this.mobileApnType;
    }

    /**
     * 根据networkInfo获取手机卡的apn名称
     *
     * @return
     */
    public int getMobileApnType(NetworkInfo activeNetInfo) {
        int apnType = NetworkManager.TYPE_MOBILE_CONN_UNKOWN;

        String apnName = activeNetInfo.getExtraInfo();
        Log.e(TAG, "getMobileApnType apnName=" + apnName);
        if (apnName != null) {
            /**
             * 注意三： 判断是移动联通wap:
             * 其实还有一种方法通过getString(c.getColumnIndex("proxy")获取代理ip来判断接入点
             * ，10.0.0.172就是移动联通wap，
             * 10.0.0.200就是电信wap，但在实际开发中并不是所有机器都能获取到接入点代理信息，例如魅族M9 （2.2）等...
             * 所以采用getExtraInfo获取接入点名字进行判断
             */
            apnName = apnName.toLowerCase();
            if (apnName.equals(CMWAP)) {
                apnType = NetworkManager.TYPE_MOBILE_CONN_CMWAP;
            } else if (apnName.equals(WAP_3G)) {
                apnType = NetworkManager.TYPE_MOBILE_CONN_3GWAP;
            } else if (apnName.equals(UNIWAP)) {
                apnType = NetworkManager.TYPE_MOBILE_CONN_UNIWAP;
            } else if (apnName.equals(CMNET)) {
                apnType = NetworkManager.TYPE_MOBILE_CONN_CMNET;
            } else if (apnName.equals(NET_3G)) {
                apnType = NetworkManager.TYPE_MOBILE_CONN_3GNET;
            } else if (apnName.equals(UNINET)) {
                apnType = NetworkManager.TYPE_MOBILE_CONN_UNIET;
            }
        }
        // 如果还是未知的话，说明不是移动、联通接入点
        if (apnType == NetworkManager.TYPE_MOBILE_CONN_UNKOWN) {
            /**
             * 注意二： 判断是否电信wap: 不要通过getExtraInfo获取接入点名称来判断类型，
             * 因为通过目前电信多种机型测试发现接入点名称大都为#777或者null，
             * 电信机器wap接入点中要比移动联通wap接入点多设置一个用户名和密码, 所以可以通过这个进行判断！
             */
            // 4.2以上版本，google禁掉了NO Permission to write APN setting
            if (android.os.Build.VERSION.SDK_INT <= 16) {
                final Cursor c = context.getContentResolver().query(
                        PREFERRED_APN_URI, null, null, null, null);
                // if cursor is empty then c.moveToFirst() retruns false， right
                // now ， we should not
                if (c != null) {
                    if (c.moveToFirst()) {
                        int index = c.getColumnIndex("user");
                        if (index > -1) {
                            final String user = c.getString(index);
                            if (user != null && user.length() > 0) {
                                if (user.startsWith(CTWAP)) {
                                    apnType = NetworkManager.TYPE_MOBILE_CONN_CTWAP;
                                } else if (user.startsWith(CTNET)) {
                                    apnType = NetworkManager.TYPE_MOBILE_CONN_CTNET;
                                }
                            }
                        }
                    }
                    c.close();
                }
            }
        }
        this.mobileApnType = apnType;
        return apnType;
    }

    // 获取sim卡串号
    public String getSimSubscriberId() {
        if (context != null) {
            TelephonyManager telManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return telManager.getSubscriberId();
        }
        return null;
    }

    /**
     * 运营商信息,0--unkown 1--移动 2--联通 3--电信
     *
     * @return
     */
    public int getSimOperatorType() {
        int simOperType = NetworkManager.TYPE_SIM_OPERATOR_UNKOWN;
        String imsi = getSimSubscriberId();
        Log.e(TAG, "mobileApnType=" + mobileApnType + ", imsi=" + imsi);

        // 先要初始化接入点的类型，才取获取运营商的类型
        if (mobileApnType == TYPE_MOBIEL_CONN_INVALID) {
            ConnectivityManager cManager = (ConnectivityManager) this.context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cManager != null) {
                // 在没有网络的情况下会返回 netInfo = null
                NetworkInfo netInfo = cManager.getActiveNetworkInfo();
                if (netInfo != null) {
                    this.mobileApnType = getMobileApnType(netInfo);
                }
            }
        }
        /**
         * 经测试验证，双卡双待手机优先使用接入点判断运营商类型更准确，因为SIM2取到的手机卡串号是第一张卡的，这是不准确的
         * 这样也不影响wifi联网时的判断，那时如果取不到接入点信息，未知情况下会再根据sim卡的串号判断
         */
        switch (mobileApnType) {
            case TYPE_MOBILE_CONN_CMNET:
            case TYPE_MOBILE_CONN_CMWAP:
                simOperType = NetworkManager.TYPE_SIM_OPERATOR_MOBILE;
                break;
            case TYPE_MOBILE_CONN_3GNET:
            case TYPE_MOBILE_CONN_3GWAP:
            case TYPE_MOBILE_CONN_UNIET:
            case TYPE_MOBILE_CONN_UNIWAP:
                simOperType = NetworkManager.TYPE_SIM_OPERATOR_UNICOM;
                break;
            case TYPE_MOBILE_CONN_CTNET:
            case TYPE_MOBILE_CONN_CTWAP:
                simOperType = NetworkManager.TYPE_SIM_OPERATOR_TELECOM;
                break;
        }
        if (simOperType == NetworkManager.TYPE_SIM_OPERATOR_UNKOWN) {
            if (imsi != null) {
                if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                    simOperType = NetworkManager.TYPE_SIM_OPERATOR_MOBILE;
                } else if (imsi.startsWith("46001")) {
                    simOperType = NetworkManager.TYPE_SIM_OPERATOR_UNICOM;
                } else if (imsi.startsWith("46003")) {
                    simOperType = NetworkManager.TYPE_SIM_OPERATOR_TELECOM;
                }
            }
        }
        return simOperType;
    }

    /**
     * 跳转到无线网络设置页面
     */
    public void goToWirelessSettings(Context context) {
        Intent intent = null;
        // 14对应为4.0版本
        if (android.os.Build.VERSION.SDK_INT >= 14) {
            intent = new Intent(Settings.ACTION_SETTINGS);
        } else {
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        }
        // 注意必须加上该flag，否则会导致无线设置页面进入我们自己程序的页面跳转堆栈
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取手机网的三种状态 1--正在连接 2--连接成功 3--连接失败
     */
    public int getPhoneNetworkState() {
        Application app = (Application) UtillManager.getContext().getApplicationContext();
        if (app == null) {
            return AppDefine.PHONE_NETWORK_STATES_DISCONNECTED;
        }
        ConnectivityManager cManager = (ConnectivityManager) app
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cManager == null) {
            return AppDefine.PHONE_NETWORK_STATES_DISCONNECTED;
        }
        NetworkInfo netInfo = cManager.getActiveNetworkInfo();
        if (netInfo == null) {
            return AppDefine.PHONE_NETWORK_STATES_DISCONNECTED;
        }
        if (netInfo.isAvailable() && netInfo.isConnected()) {
            return AppDefine.PHONE_NETWORK_STATES_CONNECTED;
        } else if (netInfo.isConnectedOrConnecting()) {
            return AppDefine.PHONE_NETWORK_STATES_CONNECTTING;
        } else {
            return AppDefine.PHONE_NETWORK_STATES_DISCONNECTED;
        }
    }

    /**
     * 获取当前的网络类型，返回：无网络--(-1)，wifi--(1)，蜂窝网络--(7) ，未知网络--(0) 其中的一个
     */
    public int getPhoneNetworkType() {
        int networkType = NetworkStateListener.NETWORK_DISABLE;
        NetworkInfo activeNetInfo = null;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            activeNetInfo = cm.getActiveNetworkInfo();
            if (activeNetInfo != null) {
                String networkTypeName = activeNetInfo.getTypeName();
                if (networkTypeName == null) {
                    networkType = NetworkStateListener.NETWORK_DISABLE;
                } else if (networkTypeName.equalsIgnoreCase("wifi")) {
                    networkType = NetworkStateListener.NETWORK_WIFI;
                } else if (networkTypeName.toLowerCase().indexOf("mobile") >= 0) {
                    int mobileNetworkType = getNetworkType(getMobileApnType(activeNetInfo));
                    if (mobileNetworkType == NetworkStateListener.NETWORK_UNKNOWN) {
                        networkType = NetworkStateListener.NETWORK_UNKNOWN;
                    } else {
                        networkType = NetworkStateListener.NETWORK_MOBILE;
                    }
                } else {
                    networkType = NetworkStateListener.NETWORK_UNKNOWN;
                }
            } else {
                networkType = NetworkStateListener.NETWORK_DISABLE;
            }
        }
        return networkType;
    }

    /**
     * 获取文件输入流
     *
     * @param filename 文件名
     */
    public InputStream openFileInputStream(String filename) {
        if (filename.startsWith("/")) {
            filename = filename.substring(1);
        }
        InputStream is = null;
        if (context != null) {
            try {
                is = context.openFileInput(filename);
            } catch (FileNotFoundException e1) {
                try {
                    is = context.getAssets().open(filename);
                } catch (IOException e) {
                }
            } catch (IllegalArgumentException e2) {
                try {
                    is = context.getAssets().open(filename);
                } catch (IOException e) {
                }
            } catch (Exception e3) {
            }
        }
        return is;
    }

    /**
     * 根据接入点获取networkType
     *
     * @param mobileApnType
     * @return
     */
    public int getNetworkType(int mobileApnType) {
        int networkType = NetworkStateListener.NETWORK_UNKNOWN;
        if (mobileApnType == NetworkManager.TYPE_MOBILE_CONN_3GNET
                || mobileApnType == NetworkManager.TYPE_MOBILE_CONN_CMNET
                || mobileApnType == NetworkManager.TYPE_MOBILE_CONN_UNIET
                || mobileApnType == NetworkManager.TYPE_MOBILE_CONN_CTNET) {
            networkType = NetworkStateListener.NETWORK_MOBILE_NET;
        } else if (mobileApnType == NetworkManager.TYPE_MOBILE_CONN_CMWAP) {
            networkType = NetworkStateListener.NETWORK_MOBILE_CM_WAP;
        } else if (mobileApnType == NetworkManager.TYPE_MOBILE_CONN_UNIWAP) {
            networkType = NetworkStateListener.NETWORK_MOBILE_CU_WAP;
        } else if (mobileApnType == NetworkManager.TYPE_MOBILE_CONN_CTWAP) {
            networkType = NetworkStateListener.NETWORK_MOBILE_CT_WAP;
        } else if (mobileApnType == NetworkManager.TYPE_MOBILE_CONN_3GWAP) {
            networkType = NetworkStateListener.NETWORK_MOBILE_3G_WAP;
        }
        return networkType;
    }


    /**
     * 将该sim卡在wap接入点下能否使用socket联网的状态
     *
     * @param flag
     */
    public void setWapConnectivityUseSocket(boolean flag) {
        if (context != null) {
            String simSubscriberId = getSimSubscriberId();
            if (simSubscriberId == null || simSubscriberId.length() <= 0) {
                return;
            }
            int status = flag ? TYPE_WAP_CONNECTIVITY_ENABLE
                    : TYPE_WAP_CONNECTIVITY_UNENABLE;
            SPConfig.saveIntSPValue(context, SPConfig.SP_WAP_CONNECTIVITY_FLAG,
                    simSubscriberId, status);
        }
    }

    public int getWapConnectivityUseSocket() {
        int status = TYPE_WAP_CONNECTIVITY_UNKOWN;
        if (context != null) {
            String simSubscriberId = getSimSubscriberId();
            if (simSubscriberId == null || simSubscriberId.length() <= 0) {
                return TYPE_WAP_CONNECTIVITY_UNKOWN;
            }
            return SPConfig.getIntSPValue(context,
                    SPConfig.SP_WAP_CONNECTIVITY_FLAG, simSubscriberId,
                    TYPE_WAP_CONNECTIVITY_UNKOWN);
        }
        return status;
    }

    // 检查完网络下一步要做的事情接口类
    public interface GoNextAfterCheckNet {

        public void goNextSuccess(int sucType);

        public void goNextFail();
    }
}
