package com.genesis.ginvoice.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.activity.CardDetailActivity;
import com.genesis.ginvoice.db.Card;
import com.genesis.liteutills.utils.GeneralUtil;
import com.sephyioth.ldcore.widget.adapter.BasicAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-14 14:10
 * 修改人：genesis
 * 修改时间：2019-06-14 14:10
 * 修改备注：
 */
public class CardAdapter extends BasicAdapter<Card, CardAdapter.ViewHolder> {
    public CardAdapter (Context context) {
        super(context);
    }

    @Override
    public int getItemViewType (int position) {
        return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        // create a new view
        View v = null;
        v = LayoutInflater.from(mContext).inflate(R.layout.layout_card_item, parent, false);
        ViewHolder vh = new ViewHolder(v, viewType);
        return vh;
    }

    @Override
    protected void setViewHolderBean (ViewHolder holder, final Card card) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;
        String cardIntroduction = card.getIntroduction();
        if (TextUtils.isEmpty(cardIntroduction)) {
            holder.mTvCardNum.setText(R.string.str_unknow_name);
        } else {
            holder.mTvCardName.setText(card.getIntroduction());
        }
        holder.mTvCardPrice.setText("" + card.getPrice());
        holder.mTvCardPath.setText(card.getDoc_path());
        holder.mTvCardNum.setText("" + card.getNum());
        double time = Double.valueOf(card.getTime());
        date = simpleDateFormat.format(time);
        if (date != null) {
            holder.mTvCardData.setText(date);
        }
        holder.mTvCardNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(mContext, CardDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("cardID", card.getId());
                mContext.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvCardName;
        private TextView mTvCardData;
        private TextView mTvCardPrice;
        private TextView mTvCardPath;
        private TextView mTvCardNum;

        public ViewHolder (View v, int viewType) {
            super(v);
            mTvCardData = (TextView) v.findViewById(R.id.view_card_data);
            mTvCardName = (TextView) v.findViewById(R.id.view_card_name);
            mTvCardPath = (TextView) v.findViewById(R.id.view_card_path);
            mTvCardPrice = (TextView) v.findViewById(R.id.view_price);
            mTvCardNum = (TextView) v.findViewById(R.id.view_num);

        }
    }
}
