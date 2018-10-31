package com.zejor.mvp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.List;

public class ChooseBankFragment extends DialogFragment {

    private List<String> yilinPmBean;
    OnItemClickListener onItemClickListener;
    String[] strings;

    public void show(FragmentManager fragmentManager, List<String> yilinPmBean) {
        this.yilinPmBean = yilinPmBean;
        strings = new String[yilinPmBean.size()];
        show(fragmentManager, "SupportBankFragment");
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("选择银行卡").setItems(yilinPmBean.toArray(strings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onItemClickListener.onDialogItemClick(which);
            }
        });
        return builder.create();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onDialogItemClick(int position);
    }

}
