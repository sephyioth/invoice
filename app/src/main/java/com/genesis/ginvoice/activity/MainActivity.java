package com.genesis.ginvoice.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.genesis.ginvoice.R;
import com.genesis.ginvoice.controller.OCRVirtualController;
import com.genesis.ginvoice.moudel.CoreMoudle;
import com.genesis.ginvoice.service.RecognizeService;
import com.genesis.ginvoice.utils.FileUtil;
import com.genesis.ginvoice.view.MainView;
import com.genesis.ginvoice.view.dialog.LDChoseDialog;
import com.sephyioth.ldcore.model.BasicActivity;
import com.sephyioth.ldcore.widget.titlebar.TitleBar;

import java.io.File;
import java.util.ArrayList;

import static com.genesis.ginvoice.Contansts.REQUEST_CODE_GENERAL;


public class MainActivity extends BasicActivity<CoreMoudle, MainView> implements RecognizeService.ServiceListener, View.OnClickListener {
    protected static final int REQUEST_CODE_LOCAL   = 2;
    private static final   int KEY_REQUEST_FILE     = 10002;
    private static final   int KEY_REQUEST_OCR_FILE = 10003;

    private AlertDialog.Builder mDialog;

    private TitleBar.Action mAction = new TitleBar.Action() {
        @Override
        public String getText () {
            return getString(R.string.str_export_file);
        }

        @Override
        public int getDrawable () {
            return 0;
        }

        @Override
        public void performAction (View view) {
            alertText(getString(R.string.str_dialog_title),
                    getString(R.string.str_dialog_export_msg));
//            clearRightBtnAction();
//            addRightBtnAction(mRecordingAction);
        }
    };

    private TitleBar.Action mRecordingAction = new TitleBar.Action() {
        @Override
        public String getText () {
            return getString(R.string.str_history);
        }

        @Override
        public int getDrawable () {
            return 0;
        }

        @Override
        public void performAction (View view) {
            Intent intent = new Intent(MainActivity.this, RecordActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    };

    @Override
    protected void initView () {
        mModel = new CoreMoudle();
        mView = findViewById(R.id.invoice_view);
        mView.setOnClickListener(this);
        setTitle(R.string.str_invoice);
        addRightBtnAction(mRecordingAction);
        setLeftBtnVisable(View.GONE);
        setTopLeftBtnListener(this);
        getTitleBar().setLeftImageResource(android.R.drawable.ic_menu_preferences);
        addRightBtnAction(mAction);
        mView.onFreshModel(mModel);
        mDialog = new AlertDialog.Builder(this);
//        testModel();
        initPermission();
    }

    private void initPermission () {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {//Android 6.0申请权限
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CODE_LOCAL);
        }
    }

    //fixme this is test interface
    private void testModel () {

        String invoice = "{\"log_id\": 5772987397598916052, \"direction\": 0, " +
                "\"words_result_num\": 30,\n" + "  \"words_result\": {\"InvoiceNum\": \"111\", " + "\"SellerName\": \"宁波杭州海新区吉研酒店管理有限公司\", \"CommodityTaxRate\": [{\n" + " \"word" + "\":" + " \"10501%\", \"row\": \"1\"}], \"SellerBank\": " + "\"中国银行股份有限公司宁波杭州湾新区支行28837187506\", \"Checker\":\n" + "  \"\", \"TotalAmount\": " + "\"1.83\", \"CommodityAmount\": [{\"word\": \"1.83\", \"row\": \"1\"}], " + "\"InvoiceDate\":\n" + "  \"\", \"CommodityTax\": [], \"PurchaserName\": " + "\"湖北亿师通科技有限公司\", \"CommodityNum\": [{\"word\": \"0\", \"row\":\n" + "  \"1\"}], " + "\"PurchaserBank\": \"市信银行选疗好移线米外方区多918111651012400\", \"Remarks\": \"\", " + "\"Password\": \"\",\n" + "  \"SellerAddress\": \"宁波杭州海新区演海二路918号0574-23724657\"," + " \"PurchaserAddress\": \"62764853536\",\n" + "  \"InvoiceCode\": \"\", " + "\"CommodityUnit\": [], \"Payee\": \"\", \"PurchaserRegisterNum\":\n" + "  " + "\"91420100MA47857T7\", \"CommodityPrice\": [{\"word\": \"183.0128792\", " + "\"row\":" + " \"1\"}], \"NoteDrawer\":\n" + "  \"\", \"AmountInWords\": " + "\"壹佰玖拾肆圆整\", " + "\"AmountInFiguers\": \"194.00\", \"TotalTax\": \"192.169998\"," + "\n" + "  " + "\"InvoiceType\": \"\", \"SellerRegisterNum\": " + "\"91330201MA28214A5W\", " + "\"CommodityName\": [{\"word\":\n" + "  \"服务\", " + "\"row\": \"1\"}], " + "\"CommodityType\": [], \"CheckCode\": \"\"}}";
        mModel.parse(invoice);
        mModel.parse(invoice);
        mModel.parse(invoice);

//        String chars = "{\"log_id\": 7119016235910391586, \"direction\": 0, " +
//                "\"words_result_num\":" + " 7, \"words_result\": [{\"vertexes_location\": " +
//                "[{\"y\": 8, \"x\": 53}, {\"y\": " + "8, \"x\": 556}, {\"y\": 30, \"x\": 556}, " + "{\"y\": 30, \"x\": 53}], \"chars\": " + "[{\"char\": \"ⅸ\", \"location\": " + "{\"width\": 13, \"top\": 8, \"left\": 58, " + "\"height\": 23}}, {\"char\": " + "\"J\", \"location\": {\"width\": 14, \"top\": 8, " + "\"left\": 72, \"height\": " + "23}}, {\"char\": \"R\", \"location\": {\"width\": 14, " + "\"top\": 8, \"left\":" + " 87, \"height\": 23}}, {\"char\": \"A\", \"location\": " + "{\"width\": 14, " + "\"top\": 8, \"left\": 102, \"height\": 23}}, {\"char\": \"面\", " + "\"location" + "\": {\"width\": 13, \"top\": 8, \"left\": 132, \"height\": 23}}, " + "{\"char\":" + " \"板\", \"location\": {\"width\": 20, \"top\": 8, \"left\": 146, " + "\"height" + "\": 23}}, {\"char\": \"·\", \"location\": {\"width\": 12, \"top\": 8, " + "\"left\": 169, \"height\": 23}}, {\"char\": \"项\", \"location\": {\"width\": 19," + " \"top\": 8, \"left\": 191, \"height\": 23}}, {\"char\": \"目\", \"location\": " + "{\"width\": 13, \"top\": 8, \"left\": 213, \"height\": 23}}, {\"char\": \"·\"," + " " + "\"location\": {\"width\": 12, \"top\": 8, \"left\": 228, \"height\": 23}}," + " " + "{\"char\": \"问\", \"location\": {\"width\": 14, \"top\": 8, \"left\": 257," + " " + "\"height\": 23}}, {\"char\": \"题\", \"location\": {\"width\": 19, \"top\":" + " 8, " + "\"left\": 272, \"height\": 23}}, {\"char\": \"·\", \"location\": " + "{\"width\": 12," + " \"top\": 8, \"left\": 295, \"height\": 23}}, {\"char\": " + "\"敏\", \"location\": " + "{\"width\": 19, \"top\": 8, \"left\": 317, \"height\":" + " 23}}, {\"char\": \"捷\", " + "\"location\": {\"width\": 13, \"top\": 8, " + "\"left\": 339, \"height\": 23}}, " + "{\"char\": \"·\", \"location\": " + "{\"width\": 12, \"top\": 8, \"left\": 354, " + "\"height\": 23}}, {\"char\": " + "\"S\", \"location\": {\"width\": 8, \"top\": 15, " + "\"left\": 381, \"height\":" + " 11}}, {\"char\": \"u\", \"location\": {\"width\": 11," + " \"top\": 15, " + "\"left\": 386, \"height\": 11}}, {\"char\": \"b\", \"location\": " + "{\"width" + "\": 11, \"top\": 15, \"left\": 394, \"height\": 11}}, {\"char\": \"v\", " + "\"location\": {\"width\": 12, \"top\": 15, \"left\": 401, \"height\": 11}}, " + "{\"char\": \"e\", \"location\": {\"width\": 10, \"top\": 15, \"left\": 409, " + "\"height\": 11}}, {\"char\": \"r\", \"location\": {\"width\": 9, \"top\": 15, " + "\"left\": 416, \"height\": 11}}, {\"char\": \"s\", \"location\": {\"width\": 10," + " \"top\": 15, \"left\": 421, \"height\": 11}}, {\"char\": \"i\", \"location\": " + "{\"width\": 8, \"top\": 15, \"left\": 428, \"height\": 11}}, {\"char\": \"o\", " + "\"location\": {\"width\": 9, \"top\": 15, \"left\": 433, \"height\": 11}}, " + "{\"char\": \"n\", \"location\": {\"width\": 11, \"top\": 15, \"left\": 438, " + "\"height\": 11}}, {\"char\": \"a\", \"location\": {\"width\": 9, \"top\": 15, " + "\"left\": 454, \"height\": 11}}, {\"char\": \"l\", \"location\": {\"width\": 12," + " \"top\": 15, \"left\": 459, \"height\": 11}}, {\"char\": \"m\", \"location\": " + "{\"width\": 11, \"top\": 15, \"left\": 467, \"height\": 11}}, {\"char\": \"·\", " + "\"location\": {\"width\": 12, \"top\": 8, \"left\": 487, \"height\": 23}}, " + "{\"char\": \"创\", \"location\": {\"width\": 13, \"top\": 8, \"left\": 524, " + "\"height\": 23}}, {\"char\": \"建\", \"location\": {\"width\": 19, \"top\": 8, " + "\"left\": 538, \"height\": 23}}], \"min_finegrained_vertexes_location\": " + "[{\"y\": 8, \"x\": 53}, {\"y\": 8, \"x\": 555}, {\"y\": 29, \"x\": 555}, {\"y\":" + " 29, \"x\": 53}], \"finegrained_vertexes_location\": [{\"y\": 8, \"x\": 53}, " + "{\"y\": 8, \"x\": 74}, {\"y\": 8, \"x\": 95}, {\"y\": 8, \"x\": 117}, {\"y\": 8," + " \"x\": 138}, {\"y\": 8, \"x\": 159}, {\"y\": 8, \"x\": 181}, {\"y\": 8, \"x\": " + "202}, {\"y\": 8, \"x\": 223}, {\"y\": 8, \"x\": 245}, {\"y\": 8, \"x\": 266}, " + "{\"y\": 8, \"x\": 287}, {\"y\": 8, \"x\": 309}, {\"y\": 8, \"x\": 330}, {\"y\": " + "8, \"x\": 352}, {\"y\": 8, \"x\": 373}, {\"y\": 8, \"x\": 394}, {\"y\": 8, " + "\"x\": 416}, {\"y\": 8, \"x\": 437}, {\"y\": 8, \"x\": 458}, {\"y\": 8, \"x\": " + "480}, {\"y\": 8, \"x\": 501}, {\"y\": 8, \"x\": 522}, {\"y\": 8, \"x\": 544}, " + "{\"y\": 8, \"x\": 556}, {\"y\": 18, \"x\": 556}, {\"y\": 29, \"x\": 556}, " + "{\"y\": 30, \"x\": 556}, {\"y\": 30, \"x\": 534}, {\"y\": 30, \"x\": 513}, " + "{\"y\": 30, \"x\": 492}, {\"y\": 30, \"x\": 470}, {\"y\": 30, \"x\": 449}, " + "{\"y\": 30, \"x\": 428}, {\"y\": 30, \"x\": 406}, {\"y\": 30, \"x\": 385}, " + "{\"y\": 30, \"x\": 364}, {\"y\": 30, \"x\": 342}, {\"y\": 30, \"x\": 321}, " + "{\"y\": 30, \"x\": 300}, {\"y\": 30, \"x\": 278}, {\"y\": 30, \"x\": 257}, " + "{\"y\": 30, \"x\": 236}, {\"y\": 30, \"x\": 214}, {\"y\": 30, \"x\": 193}, " + "{\"y\": 30, \"x\": 172}, {\"y\": 30, \"x\": 150}, {\"y\": 30, \"x\": 129}, " + "{\"y\": 30, \"x\": 108}, {\"y\": 30, \"x\": 86}, {\"y\": 30, \"x\": 65}, {\"y\":" + " 30, \"x\": 53}, {\"y\": 19, \"x\": 53}, {\"y\": 9, \"x\": 53}], \"location\": " + "{\"width\": 504, \"top\": 8, \"left\": 53, \"height\": 23}, \"words\": " + "\"ⅸJRA面板·项目·问题·敏捷· Subversion alm·创建\"}, {\"vertexes_location\": [{\"y\": 60, " + "\"x\": 23}, {\"y\": 61, \"x\": 190}, {\"y\": 84, \"x\": 190}, {\"y\": 84, \"x\":" + " 23}], \"chars\": [{\"char\": \"o\", \"location\": {\"width\": 10, \"top\": 62, " + "\"left\": 32, \"height\": 22}}, {\"char\": \")\", \"location\": {\"width\": 15, " + "\"top\": 62, \"left\": 54, \"height\": 22}}, {\"char\": \"e\", \"location\": " + "{\"width\": 10, \"top\": 62, \"left\": 70, \"height\": 22}}, {\"char\": \"c\", " + "\"location\": {\"width\": 13, \"top\": 62, \"left\": 76, \"height\": 22}}, " + "{\"char\": \"a\", \"location\": {\"width\": 13, \"top\": 62, \"left\": 85, " + "\"height\": 22}}, {\"char\": \"r\", \"location\": {\"width\": 11, \"top\": 62, " + "\"left\": 94, \"height\": 22}}, {\"char\": \"X\", \"location\": {\"width\": 13, " + "\"top\": 62, \"left\": 101, \"height\": 22}}, {\"char\": \"B\", \"location\": " + "{\"width\": 11, \"top\": 62, \"left\": 113, \"height\": 22}}, {\"char\": \"a\", " + "\"location\": {\"width\": 12, \"top\": 62, \"left\": 121, \"height\": 22}}, " + "{\"char\": \"i\", \"location\": {\"width\": 10, \"top\": 62, \"left\": 130, " + "\"height\": 22}}, {\"char\": \"d\", \"location\": {\"width\": 11, \"top\": 62, " + "\"left\": 137, \"height\": 22}}, {\"char\": \"u\", \"location\": {\"width\": 13," + " \"top\": 62, \"left\": 144, \"height\": 22}}, {\"char\": \"A\", \"location\": " + "{\"width\": 9, \"top\": 62, \"left\": 160, \"height\": 22}}, {\"char\": \"p\", " + "\"location\": {\"width\": 13, \"top\": 62, \"left\": 165, \"height\": 22}}, " + "{\"char\": \"p\", \"location\": {\"width\": 14, \"top\": 62, \"left\": 174, " + "\"height\": 22}}], \"min_finegrained_vertexes_location\": [{\"y\": 60, \"x\": " + "23}, {\"y\": 60, \"x\": 190}, {\"y\": 84, \"x\": 190}, {\"y\": 84, \"x\": 23}], " + "\"finegrained_vertexes_location\": [{\"y\": 60, \"x\": 23}, {\"y\": 60, \"x\": " + "44}, {\"y\": 60, \"x\": 65}, {\"y\": 60, \"x\": 87}, {\"y\": 60, \"x\": 108}, " + "{\"y\": 60, \"x\": 130}, {\"y\": 61, \"x\": 151}, {\"y\": 61, \"x\": 173}, " + "{\"y\": 61, \"x\": 190}, {\"y\": 71, \"x\": 190}, {\"y\": 82, \"x\": 190}, " + "{\"y\": 84, \"x\": 190}, {\"y\": 84, \"x\": 169}, {\"y\": 84, \"x\": 147}, " + "{\"y\": 84, \"x\": 126}, {\"y\": 84, \"x\": 104}, {\"y\": 84, \"x\": 83}, " + "{\"y\": 84, \"x\": 61}, {\"y\": 84, \"x\": 40}, {\"y\": 84, \"x\": 23}, {\"y\": " + "73, \"x\": 23}, {\"y\": 63, \"x\": 23}], \"location\": {\"width\": 169, \"top\":" + " 60, \"left\": 23, \"height\": 26}, \"words\": \"o) ecarX Baidu App\"}, " + "{\"vertexes_location\": [{\"y\": 64, \"x\": 297}, {\"y\": 64, \"x\": 329}, " + "{\"y\": 79, \"x\": 329}, {\"y\": 79, \"x\": 297}], \"chars\": [{\"char\": \"附\"," + " \"location\": {\"width\": 11, \"top\": 65, \"left\": 301, \"height\": 15}}, " + "{\"char\": \"件\", \"location\": {\"width\": 11, \"top\": 65, \"left\": 314, " + "\"height\": 15}}], \"min_finegrained_vertexes_location\": [{\"y\": 64, \"x\": " + "297}, {\"y\": 64, \"x\": 329}, {\"y\": 79, \"x\": 329}, {\"y\": 79, \"x\": " + "297}], \"finegrained_vertexes_location\": [{\"y\": 64, \"x\": 297}, {\"y\": 64, " + "\"x\": 311}, {\"y\": 64, \"x\": 325}, {\"y\": 64, \"x\": 329}, {\"y\": 71, " + "\"x\": 329}, {\"y\": 78, \"x\": 329}, {\"y\": 79, \"x\": 329}, {\"y\": 79, " + "\"x\": 315}, {\"y\": 79, \"x\": 301}, {\"y\": 79, \"x\": 297}, {\"y\": 72, " + "\"x\": 297}, {\"y\": 66, \"x\": 297}], \"location\": {\"width\": 33, \"top\": " + "64, \"left\": 297, \"height\": 17}, \"words\": \"附件\"}, {\"vertexes_location\": " + "[{\"y\": 89, \"x\": 7}, {\"y\": 84, \"x\": 144}, {\"y\": 105, \"x\": 145}, " + "{\"y\": 110, \"x\": 8}], \"chars\": [{\"char\": \"创\", \"location\": {\"width\":" + " 13, \"top\": 86, \"left\": 75, \"height\": 21}}, {\"char\": \"建\", " + "\"location\": {\"width\": 13, \"top\": 85, \"left\": 88, \"height\": 21}}, " + "{\"char\": \"工\", \"location\": {\"width\": 12, \"top\": 85, \"left\": 101, " + "\"height\": 21}}, {\"char\": \"作\", \"location\": {\"width\": 13, \"top\": 84, " + "\"left\": 113, \"height\": 21}}, {\"char\": \"板\", \"location\": {\"width\": 17," + " \"top\": 84, \"left\": 126, \"height\": 21}}], " + "\"min_finegrained_vertexes_location\": [{\"y\": 88, \"x\": 6}, {\"y\": 83, " + "\"x\": 144}, {\"y\": 104, \"x\": 145}, {\"y\": 110, \"x\": 7}], " + "\"finegrained_vertexes_location\": [{\"y\": 89, \"x\": 7}, {\"y\": 88, \"x\": " + "27}, {\"y\": 87, \"x\": 47}, {\"y\": 86, \"x\": 67}, {\"y\": 86, \"x\": 87}, " + "{\"y\": 85, \"x\": 107}, {\"y\": 84, \"x\": 128}, {\"y\": 84, \"x\": 144}, " + "{\"y\": 94, \"x\": 145}, {\"y\": 104, \"x\": 145}, {\"y\": 105, \"x\": 145}, " + "{\"y\": 105, \"x\": 125}, {\"y\": 106, \"x\": 105}, {\"y\": 107, \"x\": 85}, " + "{\"y\": 108, \"x\": 65}, {\"y\": 108, \"x\": 45}, {\"y\": 109, \"x\": 25}, " + "{\"y\": 110, \"x\": 8}, {\"y\": 100, \"x\": 8}, {\"y\": 90, \"x\": 7}], " + "\"location\": {\"width\": 140, \"top\": 84, \"left\": 7, \"height\": 27}, " + "\"words\": \"创建工作板\"}, {\"vertexes_location\": [{\"y\": 105, \"x\": 503}, " + "{\"y\": 105, \"x\": 689}, {\"y\": 123, \"x\": 689}, {\"y\": 123, \"x\": 504}], " + "\"chars\": [{\"char\": \"个\", \"location\": {\"width\": 15, \"top\": 105, " + "\"left\": 517, \"height\": 19}}, {\"char\": \"拖\", \"location\": {\"width\": 16," + " \"top\": 105, \"left\": 534, \"height\": 19}}, {\"char\": \"拽\", \"location\": " + "{\"width\": 11, \"top\": 105, \"left\": 551, \"height\": 19}}, {\"char\": \"文\"," + " \"location\": {\"width\": 16, \"top\": 105, \"left\": 563, \"height\": 19}}, " + "{\"char\": \"件\", \"location\": {\"width\": 10, \"top\": 105, \"left\": 581, " + "\"height\": 19}}, {\"char\": \"来\", \"location\": {\"width\": 16, \"top\": 105, " + "\"left\": 592, \"height\": 19}}, {\"char\": \"上\", \"location\": {\"width\": 11," + " \"top\": 105, \"left\": 610, \"height\": 19}}, {\"char\": \"传\", \"location\": " + "{\"width\": 16, \"top\": 105, \"left\": 621, \"height\": 19}}, {\"char\": \"或\"," + " \"location\": {\"width\": 15, \"top\": 105, \"left\": 645, \"height\": 19}}, " + "{\"char\": \"浏\", \"location\": {\"width\": 11, \"top\": 105, \"left\": 662, " + "\"height\": 19}}, {\"char\": \"览\", \"location\": {\"width\": 15, \"top\": 105, " + "\"left\": 674, \"height\": 19}}], \"min_finegrained_vertexes_location\": " + "[{\"y\": 105, \"x\": 503}, {\"y\": 105, \"x\": 689}, {\"y\": 123, \"x\": 689}, " + "{\"y\": 123, \"x\": 503}], \"finegrained_vertexes_location\": [{\"y\": 105, " + "\"x\": 503}, {\"y\": 105, \"x\": 522}, {\"y\": 105, \"x\": 540}, {\"y\": 105, " + "\"x\": 559}, {\"y\": 105, \"x\": 577}, {\"y\": 105, \"x\": 595}, {\"y\": 105, " + "\"x\": 614}, {\"y\": 105, \"x\": 632}, {\"y\": 105, \"x\": 651}, {\"y\": 105, " + "\"x\": 669}, {\"y\": 105, \"x\": 687}, {\"y\": 105, \"x\": 689}, {\"y\": 114, " + "\"x\": 689}, {\"y\": 123, \"x\": 689}, {\"y\": 123, \"x\": 670}, {\"y\": 123, " + "\"x\": 652}, {\"y\": 123, \"x\": 634}, {\"y\": 123, \"x\": 615}, {\"y\": 123, " + "\"x\": 597}, {\"y\": 123, \"x\": 579}, {\"y\": 123, \"x\": 560}, {\"y\": 123, " + "\"x\": 542}, {\"y\": 123, \"x\": 523}, {\"y\": 123, \"x\": 505}, {\"y\": 123, " + "\"x\": 504}, {\"y\": 113, \"x\": 504}], \"location\": {\"width\": 187, \"top\": " + "105, \"left\": 503, \"height\": 19}, \"words\": \"个拖拽文件来上传或浏览\"}, " + "{\"vertexes_location\": [{\"y\": 126, \"x\": 12}, {\"y\": 126, \"x\": 79}, " + "{\"y\": 146, \"x\": 79}, {\"y\": 146, \"x\": 12}], \"chars\": [{\"char\": \"O\"," + " \"location\": {\"width\": 10, \"top\": 130, \"left\": 15, \"height\": 15}}, " + "{\"char\": \"问\", \"location\": {\"width\": 12, \"top\": 130, \"left\": 52, " + "\"height\": 15}}, {\"char\": \"题\", \"location\": {\"width\": 13, \"top\": 130, " + "\"left\": 65, \"height\": 15}}], \"min_finegrained_vertexes_location\": [{\"y\":" + " 126, \"x\": 12}, {\"y\": 126, \"x\": 79}, {\"y\": 146, \"x\": 79}, {\"y\": 146," + " \"x\": 12}], \"finegrained_vertexes_location\": [{\"y\": 126, \"x\": 12}, " + "{\"y\": 126, \"x\": 26}, {\"y\": 126, \"x\": 40}, {\"y\": 126, \"x\": 54}, " + "{\"y\": 126, \"x\": 68}, {\"y\": 126, \"x\": 79}, {\"y\": 133, \"x\": 79}, " + "{\"y\": 140, \"x\": 79}, {\"y\": 146, \"x\": 79}, {\"y\": 146, \"x\": 65}, " + "{\"y\": 146, \"x\": 52}, {\"y\": 146, \"x\": 38}, {\"y\": 146, \"x\": 24}, " + "{\"y\": 146, \"x\": 12}, {\"y\": 139, \"x\": 12}, {\"y\": 132, \"x\": 12}], " + "\"location\": {\"width\": 69, \"top\": 126, \"left\": 12, \"height\": 21}, " + "\"words\": \"O问题\"}, {\"vertexes_location\": [{\"y\": 161, \"x\": 12}, {\"y\": " + "161, \"x\": 79}, {\"y\": 182, \"x\": 79}, {\"y\": 182, \"x\": 12}], \"chars\": " + "[{\"char\": \"l\", \"location\": {\"width\": 12, \"top\": 162, \"left\": 15, " + "\"height\": 19}}, {\"char\": \"报\", \"location\": {\"width\": 11, \"top\": 162, " + "\"left\": 50, \"height\": 19}}, {\"char\": \"告\", \"location\": {\"width\": 15, " + "\"top\": 162, \"left\": 62, \"height\": 19}}], " + "\"min_finegrained_vertexes_location\": [{\"y\": 161, \"x\": 12}, {\"y\": 161, " + "\"x\": 79}, {\"y\": 182, \"x\": 79}, {\"y\": 182, \"x\": 12}], " + "\"finegrained_vertexes_location\": [{\"y\": 161, \"x\": 12}, {\"y\": 161, \"x\":" + " 30}, {\"y\": 161, \"x\": 49}, {\"y\": 161, \"x\": 67}, {\"y\": 161, \"x\": 79}," + " {\"y\": 170, \"x\": 79}, {\"y\": 179, \"x\": 79}, {\"y\": 182, \"x\": 79}, " + "{\"y\": 182, \"x\": 61}, {\"y\": 182, \"x\": 43}, {\"y\": 182, \"x\": 24}, " + "{\"y\": 182, \"x\": 12}, {\"y\": 173, \"x\": 12}, {\"y\": 163, \"x\": 12}], " + "\"location\": {\"width\": 69, \"top\": 161, \"left\": 12, \"height\": 22}, " + "\"words\": \"l报告\"}]}";
//        mModel.parse(chars);

    }

    @Override
    protected int onCreateViewResID () {
        return R.layout.activity_main;
    }

    @Override
    public void onChanged (Object mObj) {

    }

    @Override
    public void onInvalidated (Object mObj) {

    }

    @Override
    public void onPointerCaptureChanged (boolean hasCapture) {

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，通用文字识别（含位置信息）
        showWait(getString(R.string.str_waiting));
        if (requestCode == REQUEST_CODE_GENERAL && resultCode == Activity.RESULT_OK) {
            String path = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            OCRVirtualController.getVirtualDev().recGeneral(getIds(), path);
            clearRightBtnAction();
            addRightBtnAction(mAction);
            addRightBtnAction(mRecordingAction);
        }
        if (requestCode == 1) {
            Uri uri = data.getData();
            Toast.makeText(MainActivity.this, "path :" + uri.getPath().toString(),
                    Toast.LENGTH_LONG).show();
        }
        if (requestCode == KEY_REQUEST_OCR_FILE && resultCode == RESULT_OK && data != null) {
            try {
                Object ob = data.getSerializableExtra("file_paths");
                if (ob != null && ob instanceof ArrayList) {
                    ArrayList<File> list = (ArrayList<File>) ob;
                    if (list != null) {
                        mModel.doFileDirectoryAction(list, CoreMoudle.TYPE_CMD_RECOGNITION);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run () {
                                Toast.makeText(MainActivity.this, mModel.saveCharacter(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        mModel.clearWords();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (requestCode == KEY_REQUEST_FILE && resultCode == RESULT_OK && data != null) {
            try {
                Object ob = data.getSerializableExtra("file_paths");
                if (ob != null && ob instanceof ArrayList) {
                    ArrayList<File> list = (ArrayList<File>) ob;
                    if (list != null) {
                        mModel.doFileDirectoryAction(list, CoreMoudle.TYPE_CMD_INVOICE);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hideWait();
    }

    @Override
    public void onResult (String result) {

    }

    @Override
    public void onClick (View v) {
        Intent intent = null;
        int requestCode = 0;
        switch (v.getId()) {
            case R.id.btn_takephoto:
                LDChoseDialog dialog = new LDChoseDialog(this, this);
                dialog.show();
                break;
            case R.id.title_bar:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                break;
            case R.id.btn_choose_camera:
                intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                requestCode = REQUEST_CODE_GENERAL;
                break;
            case R.id.btn_choose_album:
                intent = new Intent(MainActivity.this, FileActivity.class);
                intent.putExtra("actionId", getIds());
                requestCode = KEY_REQUEST_FILE;
                break;
            case R.id.btn_choose_ocr_normal:
                intent = new Intent(MainActivity.this, FileActivity.class);
                intent.putExtra("actionId", getIds());
                requestCode = KEY_REQUEST_OCR_FILE;
                break;
        }
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, requestCode);
        }
    }

    private void alertText (final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run () {
                mDialog.setTitle(title).setMessage(message).setPositiveButton(getString(R.string.str_ensure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        mModel.doAction();
                    }
                }).setNegativeButton(R.string.str_cancle, null).show();
            }
        });
    }
}

