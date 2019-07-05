package com.genesis.ginvoice.moudel;

import android.text.TextUtils;

import com.genesis.ginvoice.beans.InVoiceBean;
import com.genesis.ginvoice.db.Invoice;
import com.sephyioth.ldcore.model.BasicModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * <p>
 * "{\"log_id\": 5772987397598916052, \"direction\": 0, " +
 * "\"words_result_num\": 30,\n" + "  \"words_result\": {\"InvoiceNum\": \"111\",
 * " + "\"SellerName\": \"宁波杭州海新区吉研酒店管理有限公司\", \"CommodityTaxRate\": [{\n" + "
 * \"word" + "\":" + " \"10501%\", \"row\": \"1\"}], \"SellerBank\": " +
 * "\"中国银行股份有限公司宁波杭州湾新区支行28837187506\", \"Checker\":\n" + "  \"\",
 * \"TotalAmount\": " + "\"1.83\", \"CommodityAmount\": [{\"word\": \"1.83\",
 * \"row\": \"1\"}], " + "\"InvoiceDate\":\n" + "  \"\", \"CommodityTax\": [],
 * \"PurchaserName\": " + "\"湖北亿师通科技有限公司\", \"CommodityNum\": [{\"word\": \"0\",
 * \"row\":\n" + "  \"1\"}], " + "\"PurchaserBank\":
 * \"市信银行选疗好移线米外方区多918111651012400\", \"Remarks\": \"\", " + "\"Password\": \"\",
 * \n" + "  \"SellerAddress\": \"宁波杭州海新区演海二路918号0574-23724657\"," + "
 * \"PurchaserAddress\": \"62764853536\",\n" + "  \"InvoiceCode\": \"\", " +
 * "\"CommodityUnit\": [], \"Payee\": \"\", \"PurchaserRegisterNum\":\n" + "  " +
 * "\"91420100MA47857T7\", \"CommodityPrice\": [{\"word\": \"183.0128792\", " +
 * "\"row\":" + " \"1\"}], \"NoteDrawer\":\n" + "  \"\", \"AmountInWords\": " +
 * "\"壹佰玖拾肆圆整\", " + "\"AmountInFiguers\": \"194.00\", \"TotalTax\": \"192
 * .169998\"," + "\n" + "  " + "\"InvoiceType\": \"\", \"SellerRegisterNum\": " +
 * "\"91330201MA28214A5W\", " + "\"CommodityName\": [{\"word\":\n" + "  \"服务\", "
 * + "\"row\": \"1\"}], " + "\"CommodityType\": [], \"CheckCode\": \"\"}}";
 * 也可以在外部取得对象
 * <p>
 * 创建人：genesis
 * 创建时间：2019-07-01 17:56
 * 修改人：genesis
 * 修改时间：2019-07-01 17:56
 * 修改备注：
 */
public class InvoiceModel extends BasicModel {

    private static final String TYPE_LOG_ID           = "log_id";
    private static final String TYPE_DIRECTION        = "direction";
    private static final String TYPE_WORDS_RESULT     = "words_result";
    private static final String TYPE_WORDS_RESULT_NUM = "words_result_num";
    private static final String TYPE_INVOICENUM       = "InvoiceNum";
    private static final String TYPE_SELLERNAME       = "SellerName";
    private static final String TYPE_COMMODITYTAXRATE = "CommodityTaxRate";
    private static final String TYPE_SELLER_BANK      = "SellerBank";
    private static final String TYPE_CHECKER          = "Checker";
    private static final String TYPE_COMMODITYAMOUNT  = "CommodityAmount";
    private static final String TYPE_INVOICEDATE      = "InvoiceDate";
    private static final String TYPE_INVOICE_CODE     = "InvoiceCode";

    private static final String                 TYPE_PURCHASERNAME         = "PurchaserName";
    private static final String                 TYPE_PURCHASERBANK         = "PurchaserBank";
    private static final String                 TYPE_SELLERADDRESS         = "SellerAddress";
    private static final String                 TYPE_PURCHASER_ADDRESS     = "PurchaserAddress";
    private static final String                 TYPE_PURCHASER_REGISTERNUM = "PurchaserRegisterNum";
    private static final String                 TYPE_COMMODITY_PRICE       = "CommodityPrice";
    private static final String                 TYPE_AMOUNTIN_WORDS        = "AmountInWords";
    private static final String                 TYPE_AMOUNTIN_FIGUERS      = "AmountInFiguers";
    private static final String                 TYPE_SELLER_REGISTNUM      = "SellerRegisterNum";
    private static final String                 TYPE_COMMODITY_NAME        = "CommodityName";
    private static final String                 TYPE_ITEM_WORD             = "word";
    private static final String                 TYPE_ITEM_ROW              = "row";
    private              ArrayList<InVoiceBean> mInVoiceBeans;

    public InvoiceModel () {
        mInVoiceBeans = new ArrayList<InVoiceBean>();
    }

    @Override
    public Object parse (String json) {
        return parseInvoice(json);
    }

    public InVoiceBean parseInvoice (String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        InVoiceBean bean = new InVoiceBean();
        try {
            JSONObject object = new JSONObject(json);
            bean.setLogid(object.optString(TYPE_LOG_ID));
            bean.setDirection(object.optLong(TYPE_DIRECTION));
            bean.setWordResultNum(object.optInt(TYPE_WORDS_RESULT_NUM));
            String words = object.optString(TYPE_WORDS_RESULT);
            JSONObject obj = new JSONObject(words);
            bean.setInvoiceNum(obj.optString(TYPE_INVOICENUM));
            bean.setSellerName(obj.optString(TYPE_SELLERNAME));
            bean.setSellerBank(obj.optString(TYPE_SELLER_BANK));
            bean.setChecker(obj.optString(TYPE_CHECKER));
            bean.setInVoiceData(obj.optString(TYPE_INVOICEDATE));
            bean.setPurchaserName(obj.optString(TYPE_PURCHASERNAME));
            bean.setPurchaserBank(obj.optString(TYPE_PURCHASERBANK));
            bean.setSellerAddress(obj.optString(TYPE_SELLERADDRESS));
            bean.setPurchaserAddr(obj.optString(TYPE_PURCHASER_ADDRESS));
            bean.setInvoiceCode(obj.optString(TYPE_INVOICE_CODE));
            bean.setPurchaserRegisterNum(obj.getString(TYPE_PURCHASER_REGISTERNUM));
            bean.setAmountInWords(obj.optString(TYPE_AMOUNTIN_WORDS));
            bean.setAmountInFiguers((float) obj.optDouble(TYPE_AMOUNTIN_FIGUERS));
            bean.setSellerRegistNum(obj.optString(TYPE_SELLER_REGISTNUM));
            ArrayList<InVoiceBean.Item> price = new ArrayList<>();
            JSONArray array = obj.optJSONArray(TYPE_COMMODITY_PRICE);
            for (int i = 0; i < array.length(); i++) {
                InVoiceBean.Item item = new InVoiceBean.Item();
                item.word = array.optJSONObject(i).optString(TYPE_ITEM_WORD);
                item.row = array.optJSONObject(i).optInt(TYPE_ITEM_ROW);
                price.add(item);
            }
            bean.setCommodityPrice(price);
            ArrayList<InVoiceBean.Item> names = new ArrayList<>();
            array = obj.optJSONArray(TYPE_COMMODITY_NAME);
            for (int i = 0; i < array.length(); i++) {
                InVoiceBean.Item item = new InVoiceBean.Item();
                item.word = array.optJSONObject(i).optString(TYPE_ITEM_WORD);
                item.row = array.optJSONObject(i).optInt(TYPE_ITEM_ROW);
                names.add(item);
            }
            bean.setCommodityName(names);
            bean.setCheck(true);
            mInVoiceBeans.add(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public int size () {
        return mInVoiceBeans.size();
    }

    public void remove (int i) {
        mInVoiceBeans.remove(i);
    }

    public ArrayList<InVoiceBean> getInvoices () {
        return mInVoiceBeans;
    }

    public void clear () {
        mInVoiceBeans.clear();
    }

    public InVoiceBean get (int n) {
        return mInVoiceBeans.get(n);
    }

    public void setInvoices (ArrayList<InVoiceBean> invoices) {
        mInVoiceBeans = invoices;
    }
}
