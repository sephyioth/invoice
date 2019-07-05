package com.sephyioth.ldcore.callback;

/**
 * 类描述：
 * 观察回调接口
 * 创建人：genesis
 * 创建时间：2019-06-11 15:03
 * 修改人：genesis
 * 修改时间：2019-06-11 15:03
 * 修改备注：
 */
public interface LDNetDateCallback {

    /**
     * 方法描述：
     * 数据刷新业务接口并非UI线程
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/10/21
     * 修改备注：
     *
     * @version
     */
    void onChanged (Object mObj);

    /**
     * 方法描述：
     * 数据失效业务接口，并非UI线程
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/10/21
     * 修改备注：
     *
     * @version
     */
    void onInvalidated (Object mObj);

}
