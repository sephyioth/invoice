package com.genesis.ginvoice.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.genesis.ginvoice.R;
import com.sephyioth.ldcore.widget.adapter.BasicAdapter;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-27 15:15
 * 修改人：genesis
 * 修改时间：2019-06-27 15:15
 * 修改备注：
 */
public class FileAdapter extends BasicAdapter<File, FileAdapter.ViewHolder> {

    private              ArrayList<File>   mChoseFile     = new ArrayList<>();
    private static final int               TYPE_HEADER    = 1;
    private static final int               TYPE_FILE_ITEM = 2;
    private              CheckboxLinstener mCheckListener;

    public FileAdapter (Context context) {
        super(context);
    }

    @Override
    public int getItemViewType (int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_FILE_ITEM;
        }
    }

    @Override
    public int getItemCount () {
        return super.getItemCount() + 1;
    }

    @Override
    protected void setViewHolderBean (ViewHolder holder, final File file) {
        holder.mTVFileName.setText(file.getName());
        if (isChoseFile(file)) {
            holder.mCheckChose.setChecked(true);
        } else {
            holder.mCheckChose.setChecked(false);
        }
        holder.mCheckChose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mChoseFile.add(file);
                } else {
                    mChoseFile.remove(file);
                }
                if (mCheckListener != null) {
                    mCheckListener.onCheck(buttonView, isChecked, mChoseFile);
                }
            }
        });
        if (file.isDirectory()) {
            holder.mFileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 20;
            Bitmap bitmap = BitmapFactory.decodeFile(file.toString(), options);
            holder.mFileImage.setImageBitmap(bitmap);
        }
    }

    public ArrayList<File> getChoseFile () {
        return mChoseFile;
    }

    @Override
    protected int getAnimationItemCount () {
        int dim =
                (int) mContext.getResources().getDimension(com.sephyioth.ldcore.R.dimen.default_titlebar_height);
        return (int) ((float) getScreenHeight() / dim - 0.5f);
    }

    private boolean isChoseFile (File file) {
        for (File currentFile : mChoseFile) {
            if (currentFile.getPath().equals(file.getPath())) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        View v = null;
        ViewHolder vh = null;
        if (i == TYPE_HEADER) {
            v = LayoutInflater.from(mContext).inflate(R.layout.layout_file_item, viewGroup, false);
            vh = new ViewHolder(v, i);
            vh.mFileImage.setImageResource(R.mipmap.ic_arrow_back_black);
            vh.mTVFileName.setText("...");
            vh.mCheckChose.setVisibility(View.GONE);
        } else {
            v = LayoutInflater.from(mContext).inflate(R.layout.layout_file_item, viewGroup, false);
            vh = new ViewHolder(v, i);
        }
        return vh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mFileImage;
        private TextView        mTVFileName;
        private CheckBox        mCheckChose;

        public ViewHolder (View v, int viewType) {
            super(v);
            mFileImage = v.findViewById(R.id.image_file);
            mTVFileName = v.findViewById(R.id.view_file_name);
            mCheckChose = v.findViewById(R.id.view_check_item);

        }
    }

    public void setOnCheckboxListener (CheckboxLinstener linstener) {
        mCheckListener = linstener;
    }

    public interface CheckboxLinstener {
        void onCheck (View view, boolean isCheck, ArrayList<File> list);
    }
}
