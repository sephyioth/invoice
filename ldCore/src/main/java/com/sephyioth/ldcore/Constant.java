package com.sephyioth.ldcore;


/**
 * 类描述：
 * 变量通用命名
 * 创建人：genesis
 * 创建时间：2019-06-11 15:06
 * 修改人：genesis
 * 修改时间：2019-06-11 15:06
 * 修改备注：
 */
public class Constant {

    //系统消息发送机制，消息头部分
    public static final int MSG_DATA_CHANGE  = 10000;
    public static final int MSG_DATA_INVALID = 10001;
    public static final int MSG_VIEW_REFRESH = 10002;

    //系统通知部分代码
    public static final int KEY_LOGIN_DATA_INIT = 1;
    public static final int KEY_NORMAL_DATA     = 2;

    public static final String MSG_LOGOUT             = "logout";
    public static final String MSG_NETWORK_ERROR      = "network_error";
    public static final String MSG_NOTIFY_DATA_CHANGE = "fresh_view";
    public static final String MSG_SERVICE_START      = "service_start";
    public static final String MSG_SERVICE_STOP       = "service_stop";

}
