package com.genesis.ginvoice.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.genesis.ginvoice.db.Card;
import com.genesis.ginvoice.db.CardDao;
import com.genesis.ginvoice.db.DaoMaster;
import com.genesis.ginvoice.db.DaoSession;
import com.genesis.ginvoice.db.Invoice;
import com.genesis.ginvoice.db.InvoiceDao;
import com.genesis.ginvoice.db.PersonalInfo;
import com.genesis.ginvoice.db.PersonalInfoDao;
import com.sephyioth.ldcore.model.BasicApplication;
import com.sephyioth.ldcore.model.BasicVirtualController;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-14 10:27
 * 修改人：genesis
 * 修改时间：2019-06-14 10:27
 * 修改备注：
 */
public class DBVirtualController extends BasicVirtualController {

    private static final String TAG     = "DBVirtualController";
    private static final String DB_NAME = "invoice.db";

    private                 DaoSession          mDaoSession;
    private volatile static DBVirtualController mVirtualDev;


    public static DBVirtualController getVirtualDev () {
        if (mVirtualDev == null) {
            mVirtualDev = new DBVirtualController(BasicApplication.getCoreApplication());
        }
        return mVirtualDev;
    }

    public static DBVirtualController build (Context context) {
        return new DBVirtualController(context);
    }

    private DBVirtualController (Context context) {
        super(context);
        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(BasicApplication.getCoreApplication(), DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        mVirtualDev = this;
    }

    public boolean addCard (Card card) {
        CardDao dao = mDaoSession.getCardDao();
        return dao.insertOrReplace(card) > 0;
    }

    public boolean addInvoice (Invoice invoice) {
        InvoiceDao dao = mDaoSession.getInvoiceDao();
        return dao.insertOrReplace(invoice) > 0;
    }

    public ArrayList<Card> getCardList () {
        ArrayList<Card> cardList = new ArrayList<>();
        CardDao dao = mDaoSession.getCardDao();
        List<Card> list = dao.queryBuilder().build().list();
        for (Card card : list) {
            cardList.add(card);
        }
        return cardList;
    }

    public ArrayList<Invoice> getInVoiceList (int cardid) {
        ArrayList<Invoice> list = new ArrayList<>();
        InvoiceDao dao = mDaoSession.getInvoiceDao();
        List<Invoice> list1 =
                dao.queryBuilder().where(InvoiceDao.Properties.Card_id.eq(cardid)).build().list();
        for (Invoice invoice : list1) {
            list.add(invoice);
        }
        return list;
    }

    public PersonalInfo getPersonalInfo () {
        PersonalInfoDao dao = mDaoSession.getPersonalInfoDao();
        List<PersonalInfo> list1 = dao.queryBuilder().build().list();
        if (list1.size() > 0) {
            return list1.get(0);
        }
        return new PersonalInfo();
    }

    public void savePersonalInfo (PersonalInfo mPersonalInfo) {
        PersonalInfoDao dao = mDaoSession.getPersonalInfoDao();
        dao.insertOrReplace(mPersonalInfo);
    }

    public long queryMaxCardID () {
        CardDao dao = mDaoSession.getCardDao();
        List<Card> list = dao.queryBuilder().list();
        long max = 0;
        for (Card card : list) {
            long id = card.getId();
            if (card.getId() > max) {
                max = card.getId();
            }
        }
        return max + 1;
    }

    public void deleteCard (Card card) {
        CardDao dao = mDaoSession.getCardDao();
        dao.delete(card);
    }
}