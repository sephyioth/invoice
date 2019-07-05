package com.genesis.ginvoice.moudel;


import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.genesis.ginvoice.R;
import com.genesis.ginvoice.beans.InVoiceBean;
import com.genesis.ginvoice.controller.DBVirtualController;
import com.genesis.ginvoice.controller.OCRVirtualController;
import com.genesis.ginvoice.db.Card;
import com.genesis.ginvoice.db.Invoice;
import com.genesis.ginvoice.db.PersonalInfo;
import com.genesis.liteutills.utils.GeneralUtil;
import com.sephyioth.ldcore.model.BasicApplication;
import com.sephyioth.ldcore.model.BasicModel;
import com.sephyioth.ldcore.params.LDParam;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import static com.genesis.ginvoice.controller.OCRVirtualController.REQUEST_OCR_INVOICE;
import static com.genesis.ginvoice.controller.OCRVirtualController.REQUEST_OCR_RECOGNITION;

/**
 * 项目名称：gInVoice
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-11 14:07
 * 修改人：genesis
 * 修改时间：2019-06-11 14:07
 * 修改备注：
 */
public class CoreMoudle extends BasicModel {

    public static final  int               TYPE_CMD_RECOGNITION           = 1;
    public static final  int               TYPE_CMD_INVOICE               = 2;
    private static final String            TYPE_FILE_PATH                 =
            Environment.getExternalStorageDirectory() + "/ginvoice/";
    private static final String            TYPE_FILE_CHARS_PATH           =
            Environment.getExternalStorageDirectory() + "/ginvoice/chars/";
    private static final String            TYPE_FILE_SUFFIX               = ".xls";
    private static final String            TYPE_FILE_CHARS_SUFFIX         = ".txt";
    private static final int               TYPE_XLS_HEADER_SIZE           = 2;
    private static final int               TYPE_ID_INVOICE_NUM            = 0;
    private static final int               TYPE_ID_TIME                   = 1;
    private static final int               TYPE_ID_PRICE                  = 2;
    private static final int               TYPE_ID_SELLER_NAME            = 3;
    private static final int               TYPE_ID_SELLER_ADDR            = 4;
    private static final int               TYPE_ID_SELLER_REGISTER_NUM    = 5;
    private static final int               TYPE_ID_SELLER_PHONE           = 6;
    private static final int               TYPE_ID_PURCHASER_NAME         = 7;
    private static final int               TYPE_ID_PURCHASER_ADDR         = 8;
    private static final int               TYPE_ID_PURCHASER_REGISTER_NUM = 9;
    private static final int               TYPE_ID_PURCHASER_PHONE        = 10;
    private static final int               TYPE_ID_DEATAIL                = 11;
    private static final int               TYPE_ID_CHECKER                = 12;
    private              InvoiceModel      mInVoiceModel;
    private              CharacterRecModel mCharacterModel;
    private              OCRThread         mThread;
    private              Object            mObj                           = new Object();

    public CoreMoudle () {
        mInVoiceModel = new InvoiceModel();
        mCharacterModel = new CharacterRecModel();
        mThread = new OCRThread();
        mThread.start();
    }

    @Override
    public void parseParam (LDParam param) {
        super.parseParam(param);
        if (param == null) {
            return;
        }
        int resultCode = param.getResultCode();
        switch (resultCode) {
            case REQUEST_OCR_RECOGNITION:
                mCharacterModel.parse((String) param.getObj());
                break;
            case REQUEST_OCR_INVOICE:
                mInVoiceModel.parse((String) param.getObj());
                break;
            default:
                break;
        }
        synchronized (mObj)
        {
            mObj.notify();
        }
    }

    @Override
    public Object parse (String json) {
        mInVoiceModel.parse(json);
        return null;
    }

    public void removeInvoice (int i) {
        if (mInVoiceModel.size() > i) {
            mInVoiceModel.remove(i);
        }
    }

    public ArrayList<InVoiceBean> getInvoices () {
        return mInVoiceModel.getInvoices();
    }

    public void clearInvoices () {
        mInVoiceModel.clear();
    }

    public void clearWords () {
        mCharacterModel.clear();
    }

    public String getCharacterString () {
        return mCharacterModel.toString();
    }

    private void createTitle (Sheet sheet) {
        if (sheet == null) {
            return;
        }
        PersonalInfo personalInfo = DBVirtualController.getVirtualDev().getPersonalInfo();
        String[] arrayPersonal =
                BasicApplication.getCoreApplication().getResources().getStringArray(R.array.array_personal);

        Row rowPersonal = sheet.createRow(0);
        rowPersonal.createCell(0).setCellValue(arrayPersonal[0]);
        rowPersonal.createCell(1).setCellValue(personalInfo.getName());
        rowPersonal.createCell(2).setCellValue(arrayPersonal[1]);
        rowPersonal.createCell(3).setCellValue(personalInfo.getCompany());
        rowPersonal.createCell(4).setCellValue(arrayPersonal[2]);
        rowPersonal.createCell(5).setCellValue(personalInfo.getEmail());
        rowPersonal.createCell(6).setCellValue(arrayPersonal[3]);
        rowPersonal.createCell(7).setCellValue(personalInfo.getBankId());
        rowPersonal.createCell(8).setCellValue(arrayPersonal[4]);
        rowPersonal.createCell(9).setCellValue(personalInfo.getBankAddr());
        rowPersonal.createCell(10).setCellValue(arrayPersonal[5]);
        rowPersonal.createCell(11).setCellValue(personalInfo.getPhone());

        Row row = sheet.createRow(1);
        String[] arrary =
                BasicApplication.getCoreApplication().getResources().getStringArray(R.array.array_invoice);
        if (arrary != null) {
            for (int i = 0; i < arrary.length; i++) {
                row.createCell(i).setCellValue(arrary[i]);
            }
        }
    }

    public void saveWorkbook (String filepath, Workbook wb) {
        if (filepath == null || wb == null) {
            return;
        }
        try {
            File file = new File(filepath);
            if (!file.getParentFile().exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filepath);
            wb.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doAction () {
        try {
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();
            createTitle(sheet);
            dealInvoices(sheet);
            saveWorkbook(TYPE_FILE_PATH + "temp.xls", wb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Card createNewCard () {
        Card card = new Card();
        long cardId = DBVirtualController.getVirtualDev().queryMaxCardID();
        card.setId(cardId);
        card.setTime(String.valueOf(System.currentTimeMillis()));
        return card;
    }

    private void dealInvoices (Sheet sheet) {
        if (sheet == null) {
            return;
        }
        Card card = createNewCard();
        int count = 0;
        double price = 0.0f;
        String name = "";
        for (int n = 0; n < mInVoiceModel.size(); n++) {
            InVoiceBean bean = mInVoiceModel.get(n);
            if (!bean.isCheck()) {
                continue;
            }
            count++;
            Invoice invoice = new Invoice();
            invoice.setCard_id(card.getId().longValue());
            invoice.setSellerName(bean.getSellerName());
            invoice.setSellerBank(bean.getSellerBank());
            invoice.setSellerAddress(bean.getSellerAddress());
            invoice.setInvoiceNum(bean.getInvoiceNum());
            invoice.setPurchaserAddress(bean.getPurchaserAddr());
            invoice.setPurchaserName(bean.getPurchaserName());
            invoice.setPurchaserRegisterNum(bean.getPurchaserRegisterNum());
            invoice.setAmountInFiguers(bean.getAmountInFiguers());
            invoice.setAmountInWords(bean.getAmountInWords());
            invoice.setPhoto_file_path(bean.getPhotoPath());
            Date date = new Date(System.currentTimeMillis());
            invoice.setCreate_time(date);
            invoice.setChecker(bean.getChecker());
            Row row = sheet.createRow(count + TYPE_XLS_HEADER_SIZE);
            write2XLS(row, bean);
            name = bean.getInvoiceNum();
            saveInvoice2DB(invoice);
            price += bean.getAmountInFiguers();
        }
        String fileName =
                BasicApplication.getCoreApplication().getString(R.string.str_file_name_format,
                        name, count, price);
        card.setNum(count);
        card.setPrice((float) price);
        card.setDoc_path(TYPE_FILE_PATH + fileName + TYPE_FILE_SUFFIX);
        if (count > 0) {
            card.setIntroduction(BasicApplication.getCoreApplication().getString(R.string.str_card_name_format, name, count));
        }
        saveCard2DB(card);
    }

    public void setInvoices(ArrayList<Invoice> invoices)
    {
//        this.mInVoiceModel.setInvoices(invoices);
    }

    private void saveCard2DB (Card card) {
        DBVirtualController.getVirtualDev().addCard(card);
    }

    private void saveInvoice2DB (Invoice invoice) {
        DBVirtualController.getVirtualDev().addInvoice(invoice);
    }

    private void write2XLS (Row row, InVoiceBean invoice) {
        if (row == null || invoice == null) {
            return;
        }
        row.createCell(TYPE_ID_CHECKER).setCellValue(invoice.getChecker());
        row.createCell(TYPE_ID_INVOICE_NUM).setCellValue(invoice.getInvoiceNum());
        row.createCell(TYPE_ID_DEATAIL).setCellValue(invoice.getInVoiceData());
        row.createCell(TYPE_ID_PRICE).setCellValue(invoice.getAmountInFiguers());
        row.createCell(TYPE_ID_PURCHASER_ADDR).setCellValue(invoice.getPurchaserAddr());
        row.createCell(TYPE_ID_PURCHASER_NAME).setCellValue(invoice.getPurchaserName());
        row.createCell(TYPE_ID_PURCHASER_REGISTER_NUM).setCellValue(invoice.getPurchaserRegisterNum());
        row.createCell(TYPE_ID_PURCHASER_PHONE).setCellValue("");
        row.createCell(TYPE_ID_SELLER_ADDR).setCellValue(invoice.getSellerAddress());
        row.createCell(TYPE_ID_SELLER_NAME).setCellValue(invoice.getSellerName());
        row.createCell(TYPE_ID_SELLER_REGISTER_NUM).setCellValue(invoice.getSellerRegistNum());
        Date date = new Date(System.currentTimeMillis());
        row.createCell(TYPE_ID_TIME).setCellValue(date);

    }

    @Override
    public void parseIntent (Intent intent) {
        super.parseIntent(intent);
        if (intent == null) {
            return;
        }
        if (intent.getAction() == "saveXML")
        {

        }
    }

    private void doActionByFile (File file, int cmd) {
        OCRTask task = new OCRTask(file, cmd);
        mThread.put(task);
    }

    public void doFileDirectoryAction (ArrayList<File> list, int cmd) {
        if (list == null) {
            return;
        }
        for (File file : list) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                ArrayList<File> filesList = new ArrayList<>();
                for (File file1 : files) {
                    filesList.add(file1);

                }
                doFileDirectoryAction(filesList, cmd);
            } else {
                doActionByFile(file, cmd);
            }
        }
    }

    public String saveCharacter () {
        File file =
                new File(TYPE_FILE_CHARS_PATH + System.currentTimeMillis() + TYPE_FILE_CHARS_SUFFIX);
        GeneralUtil.writeStringCache(file, getCharacterString());
        return file.toString();
    }

    private class OCRThread extends Thread {
        private volatile LinkedBlockingQueue<OCRTask> mQune;

        public OCRThread () {
            mQune = new LinkedBlockingQueue<>();
        }

        public void put (OCRTask task) {
            try {
                mQune.put(task);
                synchronized (mObj) {
                    mObj.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public OCRTask take () {
            try {
                return mQune.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void run () {
            super.run();
            synchronized (mObj) {
                while (true) {
                    OCRTask task = take();
                    Log.i("genesis","start done ");
                    try {
                        if (task != null) {
                            int cmd = task.type;
                            File file = task.file;
                            if (cmd == TYPE_CMD_INVOICE) {
                                doInVoiceAction(file);
                            } else if (cmd == TYPE_CMD_RECOGNITION) {
                                doFileAction(file);
                            }
                            mObj.wait();
                        } else {
                            mObj.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }


        public void doFileAction (File file) {
            if (file == null) {
                return;
            }
            OCRVirtualController.getVirtualDev().recGeneral(getControllerID(), file.getPath());
        }

        public void doInVoiceAction (File file) {
            if (file == null) {
                return;
            }
            OCRVirtualController.getVirtualDev().recGeneral(getControllerID(), file.getPath());
        }
    }

    protected class OCRTask {
        public File file;
        public int  type;

        public OCRTask (File file, int type) {
            this.file = file;
            this.type = type;
        }
    }
}