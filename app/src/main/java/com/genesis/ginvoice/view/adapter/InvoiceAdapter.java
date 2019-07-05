package com.genesis.ginvoice.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.beans.InVoiceBean;
import com.sephyioth.ldcore.widget.adapter.BasicAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-19 20:12
 * 修改人：genesis
 * 修改时间：2019-06-19 20:12
 * 修改备注：
 */
public class InvoiceAdapter extends BasicAdapter<InVoiceBean, InvoiceAdapter.ViewHolder> {


    public InvoiceAdapter (Context context) {
        super(context);
    }

    @Override
    protected void setViewHolderBean (ViewHolder holder, final InVoiceBean inVoiceBean) {
        holder.mCheckAdd.setChecked(inVoiceBean.isCheck());
        holder.mTvName.setText(inVoiceBean.getSellerName());
        holder.mTvPrice.setText("" + inVoiceBean.getAmountInFiguers());
        holder.mTvNum.setText(inVoiceBean.getInvoiceNum());
        holder.mCheckAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                inVoiceBean.setCheck(isChecked);
            }
        });
//        holder.mIvInvoice.setImageBitmap(BitmapFactory.decodeFile(inVoiceBean.getPath()));
    }

    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_invoice_item, viewGroup
                , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public  TextView        mTvName;
        public  TextView        mTvNum;
        public  CircleImageView mIvInvoice;
        public  TextView        mTvPrice;
        private CheckBox        mCheckAdd;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            mTvNum = itemView.findViewById(R.id.view_invoice_num);
            mTvName = itemView.findViewById(R.id.view_seller_name);
            mIvInvoice = itemView.findViewById(R.id.view_invoice_pic);
            mTvPrice = itemView.findViewById(R.id.view_invoice_price);
            mCheckAdd = itemView.findViewById(R.id.view_check_item);
        }
    }
}
