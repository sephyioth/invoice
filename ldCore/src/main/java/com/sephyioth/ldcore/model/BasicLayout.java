package com.sephyioth.ldcore.model;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：Genesis
 * 创建时间：16/9/30 09:03
 * 修改人：Genesis
 * 修改时间：16/9/30 09:03
 * 修改备注：
 */

public abstract class BasicLayout<T extends BasicModel> extends ConstraintLayout {

    public BasicLayout (Context context) {
        super(context);
    }

    public BasicLayout (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicLayout (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 方法描述：
     * 框架自动处理Model数据
     * 参数：
     * 返回：
     * 创建人：Genesis
     * 创建时间：16/10/22
     * 修改备注：
     *
     * @version
     */
    public abstract void onFreshModel (T model);

}
