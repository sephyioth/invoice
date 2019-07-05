package com.genesis.ginvoice.beans;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-20 15:39
 * 修改人：genesis
 * 修改时间：2019-06-20 15:39
 * 修改备注：
 */
public class InVoiceBean {

    private String          logid;
    private long            direction;
    private int             wordResultNum;
    private String          invoiceNum;
    private String          sellerName;
    private String          sellerBank;
    private String          sellerAddress;
    private String          sellerRegistNum;
    private String          checker;
    private String          inVoiceData;
    private String          purchaserName;
    private String          purchaserBank;
    private String          purchaserAddr;
    private String          purchaserRegisterNum;
    private String          invoiceCode;
    private ArrayList<Item> commodityPrice;
    private ArrayList<Item> commodityName;
    private String          amountInWords;
    private float           amountInFiguers;
    private boolean         isCheck;
    private String          photoPath ;


    public String getPhotoPath () {
        return photoPath;
    }

    public void setPhotoPath (String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isCheck () {
        return isCheck;
    }

    public void setCheck (boolean check) {
        isCheck = check;
    }

    public String getLogid () {
        return logid;
    }

    public void setLogid (String logid) {
        this.logid = logid;
    }

    public long getDirection () {
        return direction;
    }

    public void setDirection (long direction) {
        this.direction = direction;
    }

    public int getWordResultNum () {
        return wordResultNum;
    }

    public void setWordResultNum (int wordResultNum) {
        this.wordResultNum = wordResultNum;
    }

    public String getInvoiceNum () {
        return invoiceNum;
    }

    public void setInvoiceNum (String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getSellerName () {
        return sellerName;
    }

    public void setSellerName (String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerBank () {
        return sellerBank;
    }

    public void setSellerBank (String sellerBank) {
        this.sellerBank = sellerBank;
    }

    public String getSellerAddress () {
        return sellerAddress;
    }

    public void setSellerAddress (String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerRegistNum () {
        return sellerRegistNum;
    }

    public void setSellerRegistNum (String sellerRegistNum) {
        this.sellerRegistNum = sellerRegistNum;
    }

    public String getChecker () {
        return checker;
    }

    public void setChecker (String checker) {
        this.checker = checker;
    }

    public String getInVoiceData () {
        return inVoiceData;
    }

    public void setInVoiceData (String inVoiceData) {
        this.inVoiceData = inVoiceData;
    }

    public String getPurchaserName () {
        return purchaserName;
    }

    public void setPurchaserName (String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserBank () {
        return purchaserBank;
    }

    public void setPurchaserBank (String purchaserBank) {
        this.purchaserBank = purchaserBank;
    }

    public String getPurchaserAddr () {
        return purchaserAddr;
    }

    public void setPurchaserAddr (String purchaserAddr) {
        this.purchaserAddr = purchaserAddr;
    }

    public String getPurchaserRegisterNum () {
        return purchaserRegisterNum;
    }

    public void setPurchaserRegisterNum (String purchaserRegisterNum) {
        this.purchaserRegisterNum = purchaserRegisterNum;
    }

    public String getInvoiceCode () {
        return invoiceCode;
    }

    public void setInvoiceCode (String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public ArrayList<Item> getCommodityPrice () {
        return commodityPrice;
    }

    public void setCommodityPrice (ArrayList<Item> commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public ArrayList<Item> getCommodityName () {
        return commodityName;
    }

    public void setCommodityName (ArrayList<Item> commodityName) {
        this.commodityName = commodityName;
    }

    public String getAmountInWords () {
        return amountInWords;
    }

    public void setAmountInWords (String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public float getAmountInFiguers () {
        return amountInFiguers;
    }

    public void setAmountInFiguers (float amountInFiguers) {
        this.amountInFiguers = amountInFiguers;
    }

    public static class Item {
        public String word;
        public int    row;
    }

}
