package com.genesis.ginvoice.beans;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-12 14:14
 * 修改人：genesis
 * 修改时间：2019-06-12 14:14
 * 修改备注：
 */
public class SingleCharBean {

    private String    singleChar;
    private LocationBean location;

    public String getSingleChar () {
        return singleChar;
    }

    public void setSingleChar (String singleChar) {
        this.singleChar = singleChar;
    }

    public LocationBean getLocation () {
        return location;
    }

    public void setLocation (LocationBean location) {
        this.location = location;
    }
}
