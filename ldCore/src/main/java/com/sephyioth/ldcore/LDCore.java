package com.sephyioth.ldcore;


import com.sephyioth.ldcore.observer.LDDataSetObservable;

/**
 * 类描述：
 *
 * 创建人：genesis
 * 创建时间：2019-06-11 15:02
 * 修改人：genesis
 * 修改时间：2019-06-11 15:02
 * 修改备注：
 */
public class LDCore {

    private static LDDataSetObservable mObservable;
    private static LDCore              mSystem;

    public static void initSys() {
        mObservable = new LDDataSetObservable();
        mSystem = new LDCore();
    }

    public static LDCore getIntance () {
        if (mSystem == null) {
            mSystem = new LDCore();
        }
        return mSystem;
    }

    public LDDataSetObservable getObservable() {
        return mObservable;
    }

    public void setObservable(LDDataSetObservable mObservable) {
        LDCore.mObservable = mObservable;
    }
}
