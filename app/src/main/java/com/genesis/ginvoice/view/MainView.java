package com.genesis.ginvoice.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.moudel.CoreMoudle;
import com.genesis.ginvoice.view.adapter.InvoiceAdapter;
import com.sephyioth.ldcore.model.BasicLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 项目名称：gInVoice
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-11 15:07
 * 修改人：genesis
 * 修改时间：2019-06-11 15:07
 * 修改备注：
 */
public class MainView extends BasicLayout<CoreMoudle> {

    @BindView(R.id.btn_takephoto)
    CircleImageView mBtnPhoto;
    //    @BindView(R.id.btn_jump_record)
//    Button          mBtnFile;
//    @BindView(R.id.btn_setting)
//    Button          mBtnSetting;
    @BindView(R.id.view_invoice_list)
    RecyclerView    mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private InvoiceAdapter      mInvoiceAdapter;

    public MainView (Context context) {
        super(context);
    }

    public MainView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate () {
        super.onFinishInflate();
        ButterKnife.bind(this);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mInvoiceAdapter = new InvoiceAdapter(getContext());
        mRecyclerView.setAdapter(mInvoiceAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onFreshModel (CoreMoudle model) {
        mInvoiceAdapter.setChildItemsLV(model.getInvoices());
    }

    @Override
    public void setOnClickListener (View.OnClickListener l) {
        super.setOnClickListener(l);
        if (mBtnPhoto != null) {
            mBtnPhoto.setOnClickListener(l);
        }
//        if (mBtnFile != null) {
//            mBtnFile.setOnClickListener(l);
//        }
//        if (mBtnSetting != null) {
//            mBtnSetting.setOnClickListener(l);
//        }
    }
}
