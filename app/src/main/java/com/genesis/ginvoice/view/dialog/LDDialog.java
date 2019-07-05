package com.genesis.ginvoice.view.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.genesis.ginvoice.R;

public class LDDialog {

    private Activity mContext;

    private ProgressDialog mDialog;

    private int MY_DIALOG_STATE = DIALOG_DIALOG_BASED;

    public final static int DIALOG_PROGRESS_BASED = 1;
    public final static int DIALOG_DIALOG_BASED = 2;
    public final static int DIALOG_DIALOG_LOGIN = 3;

    private basedLoginDialogInterface mBasedInterface;
    private basedEditDialogInterface mBasedEditInterface;
    private DialogInterface.OnClickListener mEnsureDialogInterface;
    private DialogInterface.OnClickListener mCancleDialogInterface;

    public LDDialog(Activity context) {
        this.mContext = context;
    }

    /**
     * simple Dialog
     *
     * @param title
     * @param msg
     * @param listener
     */
    public void showBasedDialog(String title, String msg,
                                DialogInterface.OnClickListener listener) {

        if (mContext == null) {
            return;
        }
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(
                        mContext.getResources().getText(
                                R.string.str_dialog_ensure), listener)
                .setCancelable(false).create().show();
    }

    /**
     * simple dialog
     *
     * @param title
     * @param msg
     * @param listener
     */
    public void showBasedDialog(String title, String msg,
                                DialogInterface.OnClickListener listener,
                                DialogInterface.OnClickListener canclelistener) {

        if (mContext == null) {
            return;
        }
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(
                        mContext.getResources().getText(
                                R.string.str_dialog_ensure), listener)
                .setNeutralButton(
                        mContext.getResources().getText(
                                R.string.str_dialog_cancle), canclelistener)
                .setCancelable(false).create().show();
    }

    /**
     * progress dialog based with title and message
     *
     * @param title
     * @param msg
     */
    public void showBasedProgressDialog(String title, String msg) {

        if (mContext == null) {
            return;
        }
        MY_DIALOG_STATE = DIALOG_PROGRESS_BASED;

        mDialog = new ProgressDialog(mContext);
        mDialog.setTitle(title);
        mDialog.setMessage(msg);
        mDialog.show();

    }

    public void setOnDialogListener(
            DialogInterface.OnClickListener dialogInterface) {
        this.mEnsureDialogInterface = dialogInterface;
    }

    public void setOnCancleDialogListener(
            DialogInterface.OnClickListener dialogInterface) {
        this.mCancleDialogInterface = dialogInterface;
    }

    public void setEditDialogListener(basedEditDialogInterface editDialogInterface) {
        this.mBasedEditInterface = editDialogInterface;
    }

    public void dialogdismiss() {
        switch (MY_DIALOG_STATE) {
            case DIALOG_PROGRESS_BASED:
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                break;

            default:
                break;
        }
    }

    public void setBasedLoginDialogInterface(
            basedLoginDialogInterface interface1) {
        this.mBasedInterface = interface1;
    }

    public interface basedLoginDialogInterface {
        void onClick (String name, String pwd, boolean isAuto, boolean isSave);
    }

    public interface basedEditDialogInterface {
        void onClick (String name);
    }
}
