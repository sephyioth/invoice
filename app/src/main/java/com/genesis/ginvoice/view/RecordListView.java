package com.genesis.ginvoice.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.moudel.RecordModel;
import com.genesis.ginvoice.view.adapter.CardAdapter;
import com.sephyioth.ldcore.model.BasicLayout;
import com.sephyioth.ldcore.widget.adapter.BasicAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-13 16:11
 * 修改人：genesis
 * 修改时间：2019-06-13 16:11
 * 修改备注：
 */
public class RecordListView extends BasicLayout<RecordModel> {

    @BindView(R.id.view_card_list)
    RecyclerView mRecyclerView;
    private CardAdapter         mCardAdapter;
    private LinearLayoutManager mLayoutManager;


    public RecordListView (Context context) {
        super(context);
    }

    public RecordListView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordListView (Context context, AttributeSet attrs, int defStyleAttr) {
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
        mCardAdapter = new CardAdapter(getContext());
        mRecyclerView.setAdapter(mCardAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onFreshModel (RecordModel model) {
        if (model != null) {
            mCardAdapter.setChildItemsLV(model.getCardList());
        }
    }

    public void removeItem(int pos)
    {
        if (mCardAdapter != null)
        {
            mCardAdapter.removeChildLV(pos);
        }
    }

    public void notifyDataChange () {
        if (mCardAdapter != null) {
            mCardAdapter.notifyDataSetChanged();
        }
    }

    public void setOnItemSelectListener (BasicAdapter.ReclyerViewListener listener) {
        mCardAdapter.setOnItemListener(listener);
    }
}
