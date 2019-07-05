package com.genesis.ginvoice.moudel;

import android.content.Intent;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.beans.table.Cell;
import com.genesis.ginvoice.beans.table.ColumnHeader;
import com.genesis.ginvoice.beans.table.RowHeader;
import com.genesis.ginvoice.controller.DBVirtualController;
import com.genesis.ginvoice.db.Invoice;
import com.sephyioth.ldcore.model.BasicApplication;
import com.sephyioth.ldcore.model.BasicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-07-03 15:43
 * 修改人：genesis
 * 修改时间：2019-07-03 15:43
 * 修改备注：
 */
public class CardDetailModel extends BasicModel<Invoice> {

    private static final int                     KEY_INVOICE_NUM    = 0;
    private static final int                     KEY_INVOICE_PATH   = 1;
    private static final int                     KEY_INVOICE_BITMAP = 2;
    private static final int                     KEY_INVOICE_PRICE  = 3;
    private static final int                     KEY_SELLER_NAME    = 4;
    private static final int                     KEY_SELLER_BANK    = 5;
    private static final int                     KEY_SELLER_ADDR    = 6;
    private static final int                     KEY_PURCH_NAME     = 7;
    private static final int                     KEY_PURCH_ADDR     = 8;
    private static final int                     KEY_PURCH_BANK     = 9;
    private static final int                     KEY_AMOUNTiN_WORDS = 10;
    private static final int                     KEY_FIGURERS       = 11;
    private static final int                     KEY_CASH_DETAIL    = 12;
    private static final int                     KEY_CREATE_TIME    = 14;
    private static final int                     KEY_CHECKER        = 13;
    private              int                     mCardId;
    private              List<List<Cell>>        mInvoices;
    private              ArrayList<ColumnHeader> mColumnHeader;
    private              ArrayList<RowHeader>    mRowHeader;

    public CardDetailModel (Intent intent) {
        if (intent != null) {
            parseIntent(intent);
        }
        initData();
    }

    @Override
    public void parseIntent (Intent intent) {
        super.parseIntent(intent);
        if (intent == null) {
            return;
        }
        mCardId = (int) intent.getLongExtra("cardID", 0);
    }

    public CardDetailModel (int cardId) {
        mCardId = cardId;
        initData();
    }

    private void initData () {
        String[] titles =
                BasicApplication.getCoreApplication().getResources().getStringArray(R.array.array_title);
        mColumnHeader = new ArrayList<>();
        mRowHeader = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mColumnHeader.add(new ColumnHeader(String.valueOf(i), titles[i]));
        }
        mInvoices = new ArrayList<List<Cell>>();
        parseDatabase();
    }

    @Override
    public void parseDatabase () {
        super.parseDatabase();
        ArrayList<Invoice> invoices =
                DBVirtualController.getVirtualDev().getInVoiceList((int) mCardId);
        for (int j = 0; j < invoices.size(); j++) {
            Invoice invoice = invoices.get(j);
            ArrayList<Cell> list = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                String data = "";
                switch (i) {
                    case KEY_AMOUNTiN_WORDS:
                        data = invoice.getAmountInWords();
                        break;
                    case KEY_INVOICE_PATH:
                        data = invoice.getPhoto_file_path();
                        break;
                    case KEY_INVOICE_BITMAP:
                        data = invoice.getSmall_photo_path();
                        break;
                    case KEY_INVOICE_NUM:
                        data = invoice.getInvoiceNum();
                        break;
                    case KEY_CREATE_TIME:
                        data = invoice.getCreate_time().toString();
                        break;
                    case KEY_INVOICE_PRICE:
                        data = String.valueOf(invoice.getAmountInFiguers());
                        break;
                    case KEY_PURCH_NAME:
                        data = invoice.getPurchaserName();
                        break;
                    case KEY_PURCH_ADDR:
                        data = invoice.getPurchaserAddress();
                        break;
                    case KEY_PURCH_BANK:
                        data = invoice.getPurchaserRegisterNum();
                        break;
                    case KEY_SELLER_BANK:
                        data = invoice.getSellerBank();
                        break;
                    case KEY_SELLER_ADDR:
                        data = invoice.getSellerAddress();
                        break;
                    case KEY_FIGURERS:
                        data = String.valueOf(invoice.getAmountInFiguers());
                    case KEY_SELLER_NAME:
                        data = invoice.getSellerName();
                        break;
                    case KEY_CASH_DETAIL:
                        data = invoice.getSellerBank();
                        break;
                    case KEY_CHECKER:
                        data = invoice.getChecker();
                        break;

                }
                list.add(new Cell("" + i, data));
            }

            mRowHeader.add(new RowHeader("" + j, "" + j + "line "));
            mInvoices.add(list);
        }
    }

    public List<List<Cell>> getInvoices () {
        return mInvoices;
    }

    public CardDetailModel setCardId (int cardId) {
        this.mCardId = cardId;
        return this;
    }

    public int getCardId () {
        return mCardId;
    }

    public ArrayList<ColumnHeader> getColumnHeader () {
        return mColumnHeader;
    }

    public ArrayList<RowHeader> getRowHeader () {
        return mRowHeader;
    }

    @Override
    public void save () {
        super.save();
        CoreMoudle moudle = new CoreMoudle();
        ArrayList<Invoice> invoices =
                DBVirtualController.getVirtualDev().getInVoiceList((int) mCardId);
        moudle.setInvoices(invoices);
    }
}
