package com.genesis.ginvoice.activity;

import android.content.Intent;
import android.view.View;

import com.evrencoskun.tableview.TableView;
import com.genesis.ginvoice.R;
import com.genesis.ginvoice.moudel.CardDetailModel;
import com.genesis.ginvoice.view.adapter.CardDetailAdapter;
import com.sephyioth.ldcore.model.BasicActivity;
import com.sephyioth.ldcore.model.BasicLayout;
import com.sephyioth.ldcore.widget.titlebar.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-07-03 10:45
 * 修改人：genesis
 * 修改时间：2019-07-03 10:45
 * 修改备注：
 */
public class CardDetailActivity extends BasicActivity<CardDetailModel, BasicLayout> {

    @BindView(R.id.view_table)
    TableView mTableView;
    private CardDetailAdapter mAdapter;

    @Override
    protected void initView () {
        ButterKnife.bind(this);
        mModel = new CardDetailModel(getIntent());
        mAdapter = new CardDetailAdapter(this, (CardDetailModel) mModel);
        mTableView.setAdapter(mAdapter);
        mAdapter.setAllItems(mModel.getColumnHeader(), mModel.getRowHeader(), mModel.getInvoices());
        setTitle(R.string.str_card_detail_title);
//        addRightBtnAction(new TitleBar.Action() {
//            @Override
//            public String getText () {
//                return getString(R.string.str_export_file);
//            }
//
//            @Override
//            public int getDrawable () {
//                return 0;
//            }
//
//            @Override
//            public void performAction (View view) {
////                Intent intent = new Intent(getBaseContext(),MainActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
////                intent.setAction("saveXML");
////                intent.putExtra("cardid",mModel.getCardId());
////                startActivity(intent);
//
//            }
//        });
    }

    @Override
    protected int onCreateViewResID () {
        return R.layout.activity_carddetail;
    }

    @Override
    public void onChanged (Object mObj) {

    }

    @Override
    public void onInvalidated (Object mObj) {

    }
}
