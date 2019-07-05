package com.genesis.ginvoice.moudel;

import com.genesis.ginvoice.controller.DBVirtualController;
import com.genesis.ginvoice.db.Card;
import com.sephyioth.ldcore.model.BasicModel;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-13 16:11
 * 修改人：genesis
 * 修改时间：2019-06-13 16:11
 * 修改备注：
 */
public class RecordModel extends BasicModel {

    private ArrayList<Card> mCardList;

    public RecordModel () {
        readDb();
    }

    public void readDb () {
        mCardList = DBVirtualController.getVirtualDev().getCardList();
    }

    public ArrayList<Card> getCardList () {
        return mCardList;
    }

    public void delete (int pos) {
        if (mCardList!=null&&mCardList.size()>pos&&pos>=0)
        {
            DBVirtualController.getVirtualDev().deleteCard(mCardList.get(pos));
        }
    }

}
