package com.genesis.ginvoice.view.adapter;

import android.content.Context;
import android.graphics.Camera;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.evrencoskun.tableview.sort.SortState;
import com.genesis.ginvoice.R;
import com.genesis.ginvoice.beans.table.Cell;
import com.genesis.ginvoice.beans.table.ColumnHeader;
import com.genesis.ginvoice.beans.table.RowHeader;
import com.genesis.ginvoice.db.Invoice;
import com.genesis.ginvoice.moudel.CardDetailModel;
import com.genesis.ginvoice.view.adapter.holder.ColumnHeaderViewHolder;
import com.genesis.ginvoice.view.adapter.holder.RowHeaderViewHolder;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-07-03 15:32
 * 修改人：genesis
 * 修改时间：2019-07-03 15:32
 * 修改备注：
 */
public class CardDetailAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {


    private CardDetailModel mModel;
    private final LayoutInflater  mInflater;

    public CardDetailAdapter (Context context, CardDetailModel model) {
        super(context);
        this.mModel = model;
        this.mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getColumnHeaderItemViewType (int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType (int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType (int position) {
        return 0;
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder (ViewGroup parent, int viewType) {
        View layout = mInflater.inflate(R.layout.table_view_cell_layout, parent, false);
        MoodCellViewHolder holder = new MoodCellViewHolder(layout);
        return holder;
    }
    
    @Override
    public void onBindCellViewHolder (AbstractViewHolder holder, Object cellItemModel,
                                      int columnPosition, int rowPosition) {
        MoodCellViewHolder holder1= (MoodCellViewHolder) holder;
        Cell cell = (Cell) cellItemModel;

        ((MoodCellViewHolder) holder).setData((String) cell.getData());
    }

    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder (ViewGroup parent, int viewType) {
        View layout = mInflater.inflate(R.layout.table_view_column_header_layout, parent, false);
        return new ColumnHeaderViewHolder(layout, getTableView());
    }

    @Override
    public void onBindColumnHeaderViewHolder (AbstractViewHolder holder,
                                              Object columnHeaderItemModel, int columnPosition) {
        ColumnHeader columnHeader = (ColumnHeader) columnHeaderItemModel;

        // Get the holder to update cell item text
        ColumnHeaderViewHolder columnHeaderViewHolder = (ColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.setColumnHeader(columnHeader);
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder (ViewGroup parent, int viewType) {
        View layout = mInflater.inflate(R.layout.table_view_row_header_layout, parent, false);
        return new RowHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder (AbstractViewHolder holder, Object rowHeaderItemModel,
                                           int rowPosition) {
        RowHeader rowHeader = (RowHeader) rowHeaderItemModel;

        // Get the holder to update row header item text
        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(String.valueOf(rowHeader.getData()));
    }

    @Override
    public View onCreateCornerView () {
        View corner = mInflater.inflate(R.layout.table_view_corner_layout, null);
        corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortState sortState = getTableView()
                        .getRowHeaderSortingStatus();
                if (sortState != SortState.ASCENDING) {
                    Log.d("TableViewAdapter", "Order Ascending");getTableView().sortRowHeader(SortState.ASCENDING);
                } else {
                    Log.d("TableViewAdapter", "Order Descending");
                    getTableView().sortRowHeader(SortState.DESCENDING);
                }
            }
        });
        return corner;
    }


    protected class MoodCellViewHolder extends AbstractViewHolder {
        public TextView mTextView;

        public MoodCellViewHolder (View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.cell_text);
        }

        public void setData (String string) {
            mTextView.setText(string);
        }
    }
}
