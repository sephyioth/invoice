package com.genesis.ginvoice.beans;


import java.io.File;
import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-27 15:25
 * 修改人：genesis
 * 修改时间：2019-06-27 15:25
 * 修改备注：
 */
public class FileItemBean {

    public ArrayList<String> mPathList;
    public ArrayList<String> mChosePathList;

    public FileItemBean () {
        mPathList = new ArrayList<>();
        mChosePathList = new ArrayList<>();
    }
}
