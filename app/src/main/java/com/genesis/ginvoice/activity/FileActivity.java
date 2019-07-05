package com.genesis.ginvoice.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.genesis.ginvoice.R;
import com.genesis.ginvoice.view.adapter.FileAdapter;
import com.sephyioth.ldcore.model.BasicActivity;
import com.sephyioth.ldcore.widget.adapter.BasicAdapter;
import com.sephyioth.ldcore.widget.titlebar.TitleBar;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.genesis.liteutills.UtillManager.getContext;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-27 14:22
 * 修改人：genesis
 * 修改时间：2019-06-27 14:22
 * 修改备注：
 */
public class FileActivity extends BasicActivity implements BasicAdapter.ReclyerViewListener,
        FileAdapter.CheckboxLinstener {

    private File            mSDCardDir = Environment.getExternalStorageDirectory();
    private ArrayList<File> mPathList  = new ArrayList<>();
    private int             mRequestId;
    private File            mCurrentFile;
    @BindView(R.id.view_file)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FileAdapter         mAdapter;
    private TitleBar.Action     mAction = new TitleBar.Action() {
        @Override
        public String getText () {
            return getString(R.string.str_ensure);
        }

        @Override
        public int getDrawable () {
            return 0;
        }

        @Override
        public void performAction (View view) {
            ArrayList<File> files = mAdapter.getChoseFile();
            Intent intent = new Intent();
            intent.putExtra("file_paths", files);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    @Override
    protected void initView () {
        ButterKnife.bind(this);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCurrentFile = mSDCardDir;
        loadFile(mCurrentFile);
        mAdapter = new FileAdapter(this);
        mAdapter.setChildItemsLV(mPathList);
        mRecyclerView.setAdapter(mAdapter);
        setTitle(R.string.str_file_explore);
        mAdapter.setOnCheckboxListener(this);
        mAdapter.setOnItemListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            mRequestId = intent.getIntExtra("actionId", 0);
        }
    }


    private void loadFile (File dir) {
        if (dir == null) {
            return;
        }
        mPathList.clear();
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File currentFile : files) {
            String fileName = currentFile.getName();
            String end =
                    fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
            if (end.equals("bat") || end.equals("jpg") || end.equals("png")) {
                mPathList.add(currentFile);
            } else if (currentFile.isDirectory()) {
                mPathList.add(currentFile);
            }
        }
    }

    @Override
    protected int onCreateViewResID () {
        return R.layout.activity_file;
    }

    @Override
    public void onChanged (Object mObj) {

    }

    @Override
    public void onInvalidated (Object mObj) {

    }

    @Override
    public void onItemClick (RecyclerView.ViewHolder holder, int pos) {
        File file = null;
        if (pos < 0) {
            file = mCurrentFile;
        } else {
            file = mPathList.get(pos);
        }
        if (file.isDirectory()) {
            loadFile(file);
            mAdapter.setChildItemsLV(mPathList);
        }
    }

    @Override
    public void onCheck (View view, boolean isCheck, ArrayList<File> list) {
        clearRightBtnAction();
        if (list.size() > 0) {
            String title = getString(R.string.str_chose_file_size, list.size());
            setTitle(title);
            addRightBtnAction(mAction);
        } else {
            clearRightBtnAction();
            setTitle(R.string.str_file_explore);
        }
    }
}
