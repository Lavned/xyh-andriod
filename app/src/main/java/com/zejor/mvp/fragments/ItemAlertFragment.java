package com.zejor.mvp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.zejor.R;
import com.zejor.adapter.BankCardAdapter;
import com.zejor.bean.BankBean;

import java.util.List;

/**
 * Created by mango on 2018/7/12.
 */

public class ItemAlertFragment extends DialogFragment {

    private List<BankBean> bankBeans;
    private BankCardAdapter adapter;
    private RecyclerView rvBank;
    public OnItemClickListener onItemClickListener;
    private int checkPosition = 0;

    public void show(FragmentManager fragmentManager, List<BankBean> bankBeans, int position) {
        this.bankBeans = bankBeans;
        show(fragmentManager, "ItemsDialogFragment");
        checkPosition = position;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_bank, null);
        rvBank = view.findViewById(R.id.rv_bank);
        rvBank.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new BankCardAdapter(bankBeans, getActivity(), checkPosition);
        rvBank.setAdapter(adapter);
        adapter.setItemClickListener(new BankCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onItemClickListener.onDialogItemClick(position);
            }
        });
        builder.setView(view);
        return builder.create();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onDialogItemClick(int position);
    }
}
