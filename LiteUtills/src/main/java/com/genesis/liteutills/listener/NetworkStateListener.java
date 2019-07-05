package com.genesis.liteutills.listener;

public interface NetworkStateListener {

    // 网络不可用
    public static final int NETWORK_DISABLE = -1;

    // 未知网络
    public static final int NETWORK_UNKNOWN = 0;

    // WIFI网络
    public static final int NETWORK_WIFI = 1;

    // 蜂窝网络
    public static final int NETWORK_MOBILE = 7;

    // 手机移动联通NET网络
    public static final int NETWORK_MOBILE_NET = 2;

    // 手机移动WAP网络,cmwap
    public static final int NETWORK_MOBILE_CM_WAP = 3;

    // 手机联通WAP网络,uniwap
    public static final int NETWORK_MOBILE_CU_WAP = 4;

    // 手机移动联通WAP网络,3gwap
    public static final int NETWORK_MOBILE_3G_WAP = 5;

    // 手机电信WAP网络,ctwap
    public static final int NETWORK_MOBILE_CT_WAP = 6;

    public void onNetworkConnected(int networkType);

    public void onNetWorkDisConnedted(int networkType);

    public void onNetWorkConnecting(int networkType);
}
