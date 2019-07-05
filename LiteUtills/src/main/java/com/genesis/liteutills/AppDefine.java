package com.genesis.liteutills;

/**
 * Created by hjy on 2015/12/14.
 */
public class AppDefine {
    //网络连接状态
    public static final int CONNECT_NO = 0;
    public static final int CONNECT_WIFI = 1;
    public static final int CONNECT_MOBILE = 2;
    // 文件格式
    /**
     * 编码格式
     */
    public static final String CHARSET_NAME_UTF_8 = "utf-8";

    // 手机网络的三种状态
    /**
     * 正在连接
     */
    public static final int PHONE_NETWORK_STATES_CONNECTTING = 1;
    /**
     * 连接成功
     */
    public static final int PHONE_NETWORK_STATES_CONNECTED = 2;
    /**
     * 连接失败
     */
    public static final int PHONE_NETWORK_STATES_DISCONNECTED = 3;

	/* 无效值定义 */
    /**
     * 无效的数据
     */
    public static final int INVALID = -1;
    /**
     * 无效的Id(可用于判断
     */
    public static final int INVALID_ID = 0;
    /**
     * 无效的算术
     */
    public static final int INVALID_MERTICS = -1;

    // 目前网络链接的类型
    /**
     * 无网络
     */
    public static final int NONETWORK = 0;
    /**
     * WIFI链接
     */
    public static final int WIFI = 1;
    /**
     * 蜂窝数据
     */
    public static final int FWDATA = 2;


}
