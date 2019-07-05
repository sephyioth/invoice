package com.sephyioth.ldcore.widget.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-18 10:39
 * 修改人：genesis
 * 修改时间：2019-06-18 10:39
 * 修改备注：
 */
public abstract class BasicAdapter<M, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    private static final int                 ANIMATED_ITEMS_COUNT       = 6;
    private static final int                 ANIMATED_ITEMS_REMOVE_TIME = 500;
    private static final int                 ANIMATED_ITEMS_ENTER_TIME  = 700;
    protected            ArrayList<M>        mLists;
    protected            int                 mAnimationTime             = ANIMATED_ITEMS_ENTER_TIME;
    protected            Context             mContext;
    private              ReclyerViewListener mListener;
    private              int                 mLastAnimatedPosition      = -1;
    private              int                 mAnimationItemCount        = ANIMATED_ITEMS_COUNT;
    private              boolean             isAnimateItems             = true;

    public BasicAdapter (Context context) {
        mContext = context;
    }

    public void setChildItemsLV (ArrayList<M> mArrayList) {
        synchronized (this) {
            mLists = mArrayList;
            notifyDataSetChanged();
        }
    }

    public int getAnimationTime () {
        return mAnimationTime;
    }

    public void setAnimationTime (int mAnimationTime) {
        this.mAnimationTime = mAnimationTime;
    }

    protected int getAnimationItemCount () {
        return mAnimationItemCount;
    }

    public void setAnimationItemCount (int mAnimationItemCount) {
        this.mAnimationItemCount = mAnimationItemCount;
    }

    public void addChildLV (M m) {
        synchronized (this) {
            if (mLists == null) {
                mLists = new ArrayList<>();
            }
            mLists.add(m);
            notifyDataSetChanged();
        }
    }

    public void removeChildLV (M m) {
        synchronized (this) {
            if (mLists == null) {
                mLists = new ArrayList<>();
            }
            mLists.remove(m);
            notifyDataSetChanged();
        }
    }

    public void removeChildLV (int pos) {
        synchronized (this) {
            if (mLists == null) {
                mLists = new ArrayList<>();
            }
            if (pos < mLists.size()) {
                mLists.remove(pos);
            }
            notifyDataSetChanged();
        }
    }

    public int getScreenHeight () {
        int screenHeight = 0;
        if (mContext != null) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }
        return screenHeight;
    }


    @Override
    public int getItemCount () {
        if (mLists != null) {
            return mLists.size();
        }
        return 0;
    }

    protected void runEnterAnimation (View view, int position) {
        if (mLists != null && position == mLists.size() - 1) {
            isAnimateItems = false;
            return;
        }
        if (!isAnimateItems || position >= getAnimationItemCount() - 1) {
            isAnimateItems = false;
            return;
        }

        if (position > mLastAnimatedPosition) {
            mLastAnimatedPosition = position;
            view.setTranslationY(getScreenHeight());
            view.animate().translationY(0).setStartDelay(100 * position).setInterpolator(new DecelerateInterpolator(3.f)).setDuration(ANIMATED_ITEMS_ENTER_TIME).start();
        }
        view.setTranslationY(getScreenHeight());
        view.animate().translationY(0).setStartDelay(100 * position).setInterpolator(new DecelerateInterpolator(3.f)).setDuration(getAnimationTime()).start();
    }

    @Override
    public void onBindViewHolder (final H holder, final int position) {
        runEnterAnimation(holder.itemView, position);
        if (mLists == null) {
            return;
        }
        int pos = 0;
        pos = position - (getItemCount() - mLists.size());
        if (mLists.size() > pos && pos >= 0) {
            setViewHolderBean(holder, mLists.get(pos));
        }
        final int finalPos = pos;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (mListener != null) {
                    mListener.onItemClick(holder, finalPos);
                }
            }
        });
    }


    public ReclyerViewListener getListener () {
        return mListener;
    }

    public void setOnItemListener (ReclyerViewListener mListener) {
        this.mListener = mListener;
    }

    protected abstract void setViewHolderBean (H holder, M m);

    public interface ReclyerViewListener {
        void onItemClick (RecyclerView.ViewHolder holder, int pos);
    }
}
