package com.genesis.ginvoice.view.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.genesis.ginvoice.R;

import java.util.Vector;

public class LDToastDialog extends LDBasicDialog implements OnTouchListener,
        OnItemClickListener, OnItemLongClickListener {

    private static final String TAG = "STOCKTRAIN_DIALOG_ERROR";

    private static final String ERROR_INFORMATION_NOPARAM = "error no param";

    private Window mWindow = null;
    private Vector<String> mVector;
    private Context mContext;

    private ListView mListView;


    private DialogItemAdapter mAdapter;

    public LDToastDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public LDToastDialog(Context context, Vector<String> vector) {
        super(context);
        if (vector == null || context == null) {
            Log.e(TAG, ERROR_INFORMATION_NOPARAM);
            return;
        }
        this.mVector = vector;
        this.mVector.add(context.getResources()
                .getText(R.string.str_dialog_cancle).toString());
        this.mContext = context;
    }


    public void initDialog() {
        setCanceledOnTouchOutside(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog);
        setCanceledOnTouchOutside(true);
        windowDeploy();

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_dialog;
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.listView1);
        findViewById(R.id.m_Layout_Inside).setOnTouchListener(this);
        findViewById(R.id.m_Layout_Outside).setOnTouchListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        mListView.setOnTouchListener(this);
        mListView.setDivider(null);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    @Override
    protected void initData() {
        mAdapter = new DialogItemAdapter(mContext, mVector);
        mListView.setAdapter(mAdapter);
    }


    protected void windowDeploy() {
        mWindow = getWindow();
        mWindow.setWindowAnimations(R.style.dialogWindowAnim);
        mWindow.setBackgroundDrawableResource(R.color.colorTrans);
        WindowManager.LayoutParams wl = mWindow.getAttributes();
        mWindow.setGravity(getGravity());
        mWindow.setAttributes(wl);
    }

    protected int getGravity()
    {
        return Gravity.BOTTOM;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   int position, long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        if (position != mVector.size() - 1) {
            if (mDialogItemsClickListener != null) {
                mDialogItemsClickListener.onItemClick(position, id, view);
            }
        }
        dismiss();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        if (v.getId() != R.id.listView1) {
            dismiss();
        }

        return false;
    }


    private class DialogItemAdapter extends BaseAdapter {

        private Vector<String> mVector;
        private Context mContext;

        private LayoutInflater mLayoutInflater;

        @SuppressWarnings("static-access")
        public DialogItemAdapter(Context context, Vector<String> vector) {
            this.mContext = context;
            this.mVector = vector;

            mLayoutInflater = getLayoutInflater().from(mContext);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mVector.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mVector.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mLayoutInflater.inflate(
                        R.layout.layout_dialog_items, null);

                holder.mTextView = (TextView) convertView
                        .findViewById(R.id.m_Text_Show);
                holder.mTextView.setText("" + mVector.get(position));
                if (position < mVector.size() - 1) {
                    convertView.findViewById(R.id.m_Layout_Padding)
                            .setVisibility(View.GONE);
                }
                if (position == 2) {
                    convertView.findViewById(R.id.m_Text_Show).setBackgroundResource(R.color.colorTrans);
                }

                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();// ȡ��ViewHolder����

            }

            holder.mTextView.setText("" + mVector.get(position));
            return convertView;
        }

        private final class ViewHolder {
            private TextView mTextView;
        }
    }
}
