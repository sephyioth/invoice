package com.genesis.ginvoice.view;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.moudel.SettingModel;
import com.sephyioth.ldcore.model.BasicLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-13 16:37
 * 修改人：genesis
 * 修改时间：2019-06-13 16:37
 * 修改备注：
 */
public class SettingView extends BasicLayout<SettingModel> {

    @BindView(R.id.layout_bank_id)
    RelativeLayout mLayoutBankID;
    @BindView(R.id.layout_bank_addr)
    RelativeLayout mLayoutBankAddr;
    @BindView(R.id.layout_email)
    RelativeLayout mLayoutEmail;
    @BindView(R.id.layout_comp)
    RelativeLayout mLayoutComp;
    @BindView(R.id.layout_name)
    RelativeLayout mLayoutName;
    @BindView(R.id.layout_phone)
    RelativeLayout mLayoutPhone;
    @BindView(R.id.edit_personal_email)
    EditText       mEditEmail;
    @BindView(R.id.edit_personal_phone)
    EditText       mEditPhone;
    @BindView(R.id.edit_personal_bank)
    EditText       mEditBank;
    @BindView(R.id.edit_personal_bank_addr)
    EditText       mEditBankAddr;
    @BindView(R.id.edit_personal_comp)
    EditText       mEditComp;
    @BindView(R.id.edit_personal_name)
    EditText       mEditName;

    public SettingView (Context context) {
        super(context);
    }

    public SettingView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate () {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void onFreshModel (SettingModel model) {
        if (model == null) {
            return;
        }
        mEditBank.setText(model.getBank());
        mEditBankAddr.setText(model.getBankAddr());
        mEditEmail.setText(model.getEmail());
        mEditName.setText(model.getName());
        mEditComp.setText(model.getComp());
        mEditPhone.setText(model.getPhone());
    }

    public void addTextChangedListener (TextWatcher textWatcher) {
        mEditBank.addTextChangedListener(textWatcher);
        mEditBankAddr.addTextChangedListener(textWatcher);
        mEditComp.addTextChangedListener(textWatcher);
        mEditName.addTextChangedListener(textWatcher);
        mEditPhone.addTextChangedListener(textWatcher);
        mEditEmail.addTextChangedListener(textWatcher);
    }

    @Override
    public void setOnClickListener (View.OnClickListener l) {
        super.setOnClickListener(l);
        mLayoutBankID.setOnClickListener(l);
        mLayoutBankAddr.setOnClickListener(l);
        mLayoutComp.setOnClickListener(l);
        mLayoutName.setOnClickListener(l);
        mLayoutPhone.setOnClickListener(l);
        mLayoutEmail.setOnClickListener(l);
    }

    public String getBankText () {
        return mEditBank.getText().toString();
    }


    public String getCompanyText () {
        return mEditComp.getText().toString();
    }

    public String getPhoneText () {
        return mEditPhone.getText().toString();
    }

    public String getEmailText () {
        return mEditEmail.getText().toString();
    }

    public String getBankAddrText () {
        return mEditBankAddr.getText().toString();
    }

    public String getNameText () {
        return mEditName.getText().toString();
    }
}
