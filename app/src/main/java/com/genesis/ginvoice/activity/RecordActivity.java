package com.genesis.ginvoice.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.moudel.RecordModel;
import com.genesis.ginvoice.view.RecordListView;
import com.genesis.ginvoice.view.dialog.LDFileChoseDialog;
import com.sephyioth.ldcore.model.BasicActivity;
import com.sephyioth.ldcore.widget.adapter.BasicAdapter;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-13 16:09
 * 修改人：genesis
 * 修改时间：2019-06-13 16:09
 * 修改备注：
 */
public class RecordActivity extends BasicActivity<RecordModel, RecordListView> implements BasicAdapter.ReclyerViewListener {

    @Override
    protected void initView () {
        setTitle(R.string.str_recordlist);
        mModel = new RecordModel();
        mView = findViewById(R.id.view_recordlist);
        mView.onFreshModel(mModel);
        mView.setOnItemSelectListener(this);
    }

    @Override
    protected int onCreateViewResID () {
        return R.layout.activity_recordlist;
    }

    @Override
    public void onChanged (Object mObj) {

    }

    @Override
    public void onInvalidated (Object mObj) {

    }

    @Override
    public void onItemClick (final RecyclerView.ViewHolder holder, final int pos) {
        LDFileChoseDialog dialog = new LDFileChoseDialog(this, new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                mModel.delete(pos);
                mView.removeItem(pos);
                mView.notifyDataChange();
            }
        });
        dialog.show();
    }
}
