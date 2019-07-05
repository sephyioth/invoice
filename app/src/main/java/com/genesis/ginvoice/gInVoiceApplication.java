package com.genesis.ginvoice;

import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.genesis.ginvoice.controller.DBVirtualController;
import com.genesis.ginvoice.controller.OCRVirtualController;
import com.genesis.ginvoice.moudel.OCRDataModel;
import com.genesis.liteutills.UtillManager;
import com.genesis.liteutills.utils.GeneralUtil;
import com.sephyioth.ldcore.LDCore;
import com.sephyioth.ldcore.model.BasicApplication;

/**
 * 项目名称：gInVoice
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-11 15:00
 * 修改人：genesis
 * 修改时间：2019-06-11 15:00
 * 修改备注：
 */
public class gInVoiceApplication extends BasicApplication {


    private OCRVirtualController.OCRDevResultCallback mOCRCallback =
            new OCRVirtualController.OCRDevResultCallback() {
        @Override
        public void onResult (AccessToken result) {

        }

        @Override
        public void onError (OCRError error) {

        }

        @Override
        public void onDataChange (OCRDataModel dataModel) {

        }

        @Override
        public void onError (String errorMsg, String error) {

        }
    };

    @Override
    public void onCreate () {
        super.onCreate();
        LDCore.initSys();
        UtillManager.init(this);
        OCRVirtualController.build(this).initOCRWithAkSk("dRxG2yFobcv2bUj70rii3jma",
                "r4RbYmMCn1FAt0vnOkvLEFexA9YBqyAQ");
        OCRVirtualController.getVirtualDev().registCallback(mOCRCallback);
        DBVirtualController.build(this);
        checkPermissions();
    }

    private void checkPermissions () {


    }
}
