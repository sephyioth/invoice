package com.genesis.ginvoice.moudel;

import com.genesis.ginvoice.controller.DBVirtualController;
import com.genesis.ginvoice.db.PersonalInfo;
import com.sephyioth.ldcore.model.BasicModel;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-13 16:36
 * 修改人：genesis
 * 修改时间：2019-06-13 16:36
 * 修改备注：
 */
public class SettingModel extends BasicModel {

    private PersonalInfo mPersonalInfo;

    public SettingModel () {
        queryDb();
    }

    @Override
    public void queryDb () {
        super.queryDb();
        mPersonalInfo = DBVirtualController.getVirtualDev().getPersonalInfo();
    }


    public Long getId () {
        return mPersonalInfo.getId();
    }

    public void setId (Long id) {
        mPersonalInfo.setId(id);
    }

    public String getName () {
        return mPersonalInfo.getName();
    }

    public void setName (String name) {
        mPersonalInfo.setName(name);
    }

    public String getComp () {
        return mPersonalInfo.getCompany();
    }

    public void setComp (String comp) {
        mPersonalInfo.setCompany(comp);
    }

    public String getPhone () {
        return mPersonalInfo.getPhone();
    }

    public void setPhone (String phone) {
        mPersonalInfo.setPhone(phone);
    }

    public String getBank () {
        return mPersonalInfo.getBankId();
    }

    public String getBankAddr () {
        return mPersonalInfo.getBankAddr();
    }

    public void setBank (String bank) {
        mPersonalInfo.setBankId(bank);
    }

    public void setBankAddr (String bankAddr) {
        mPersonalInfo.setBankAddr(bankAddr);
    }

    public String getEmail () {
        return mPersonalInfo.getEmail();
    }

    public void setEmail (String email) {
        mPersonalInfo.setEmail(email);
    }

    @Override
    public void save () {
        super.save();
        DBVirtualController.getVirtualDev().savePersonalInfo(mPersonalInfo);
    }
}
