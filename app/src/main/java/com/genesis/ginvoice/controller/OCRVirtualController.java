package com.genesis.ginvoice.controller;

import android.content.Context;
import android.util.Log;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.genesis.ginvoice.moudel.OCRDataModel;
import com.genesis.ginvoice.service.RecognizeService;
import com.sephyioth.ldcore.model.BasicApplication;
import com.sephyioth.ldcore.model.BasicVirtualController;
import com.sephyioth.ldcore.params.LDNetParam;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 项目名称：gInVoice
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-11 15:32
 * 修改人：genesis
 * 修改时间：2019-06-11 15:32
 * 修改备注：
 */
public class OCRVirtualController extends BasicVirtualController implements RecognizeService.ServiceListener {

    public static final int REQUEST_OCR_RECOGNITION = 1 << 1;
    public static final int REQUEST_OCR_INVOICE     = 1 << 2;

    private volatile        OCRDataModel                  mModel;
    private volatile static OCRVirtualController          mDev;
    private volatile        LinkedBlockingQueue<QuneItem> mQune;
    private                 Context                       mContext;

    protected OCRVirtualController (Context context) {
        super(context);
        mContext = context;
        mModel = new OCRDataModel();
        mQune = new LinkedBlockingQueue<QuneItem>();
    }

    public synchronized static OCRVirtualController getVirtualDev () {
        if (mDev != null) {
            return mDev;
        }
        mDev = new OCRVirtualController(BasicApplication.getCoreApplication());
        mDev.initOCRWithLiscense();
        return mDev;
    }

    public static OCRVirtualController build (Context context) {
        mDev = new OCRVirtualController(context);
        return mDev;
    }

    /**
     * 用明文ak，sk初始化
     */
    public void initOCRWithAkSk (String apkey, String sckey) {
        OCR.getInstance(mContext).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult (AccessToken result) {
                String token = result.getAccessToken();
//                hasGotToken = true;
            }

            @Override
            public void onError (OCRError error) {
                error.printStackTrace();
//                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, BasicApplication.getCoreApplication(), apkey, sckey);
    }

    /**
     * 方法描述：
     * 初始化方法
     * 参数：
     * 返回：
     * 创建人：genesis
     * 创建时间：2019-06-11
     * 修改备注：
     *
     * @version
     */
    private void initOCRWithLiscense () {
        OCR.getInstance(mContext).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult (AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
            }

            @Override
            public void onError (OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                String errorMsg = error.getMessage();
            }
        }, BasicApplication.getCoreApplication());
    }

    public void recVatInvoice (int requestCode, String path) {
        RecognizeService.recVatInvoice(BasicApplication.getCoreApplication(), path, this);
        QuneItem item = new QuneItem();
        item.resultCode = REQUEST_OCR_INVOICE;
        item.controllerId = requestCode;
        mQune.add(item);
    }

    public void recGeneral (int requestCode, String path) {
        RecognizeService.recGeneral(BasicApplication.getCoreApplication(), path, this);
        QuneItem item = new QuneItem();
        item.resultCode = REQUEST_OCR_RECOGNITION;
        item.controllerId = requestCode;
        mQune.add(item);
    }

    @Override
    protected void onDataChage () {
        super.onDataChage();
    }

    @Override
    public void onResult (String result) {
        LDNetParam params = null;
        QuneItem item = null;
        try {
            item = mQune.take();
            params = new LDNetParam(item.controllerId, result);
            params.setResultCode(item.resultCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BasicApplication.getCoreApplication().putData(params);
        mQune.remove(0);
    }

    public interface OCRDevResultCallback extends BasicVirtualController.VirtualDevCallback {
        void onResult (AccessToken result);

        void onError (OCRError error);

        void onDataChange (OCRDataModel dataModel);
    }

    private class QuneItem {
        public int resultCode;
        public int controllerId;
    }
}
