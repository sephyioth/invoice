package com.genesis.ginvoice.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.moudel.SettingModel;
import com.genesis.ginvoice.view.SettingView;
import com.sephyioth.ldcore.model.BasicActivity;
import com.sephyioth.ldcore.widget.titlebar.TitleBar;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-13 16:35
 * 修改人：genesis
 * 修改时间：2019-06-13 16:35
 * 修改备注：
 */
public class SettingActivity extends BasicActivity<SettingModel, SettingView> implements TextWatcher, View.OnClickListener {
    private ArrayList<ArrayList<String>> recordList;

    @Override
    protected void initView () {
        mModel = new SettingModel();
        mView = findViewById(R.id.view_setting);
        setTitle(R.string.str_setting_title);
        addRightBtnAction(new TitleBar.Action() {
            @Override
            public String getText () {
                return getString(R.string.str_save);
            }

            @Override
            public int getDrawable () {
                return 0;
            }

            @Override
            public void performAction (View view) {
                mModel.setBank(mView.getBankText());
                mModel.setBankAddr(mView.getBankAddrText());
                mModel.setComp(mView.getCompanyText());
                mModel.setEmail(mView.getEmailText());
                mModel.setName(mView.getNameText());
                mModel.setPhone(mView.getPhoneText());
                mModel.save();
                finish();
            }
        });
        mView.addTextChangedListener(this);
        mView.setOnClickListener(this);
        mView.onFreshModel(mModel);
    }

    @Override
    protected int onCreateViewResID () {
        return R.layout.activity_setting;
    }

    @Override
    public void onChanged (Object mObj) {

    }

    @Override
    public void onInvalidated (Object mObj) {

    }

    @Override
    public void beforeTextChanged (CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged (CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged (Editable s) {

    }

    @Override
    public void onClick (View v) {

    }
}
