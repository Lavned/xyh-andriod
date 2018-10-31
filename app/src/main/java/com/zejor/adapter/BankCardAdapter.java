package com.zejor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zejor.R;
import com.zejor.bean.BankBean;

import java.util.List;


/**
 *
 * 银行卡适配器
 */
public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.BankViewHolder> {
    private List<BankBean> bankBeans;
    private int defaultPosition = 0;
    private Context context;
    private LayoutInflater inflater;
    OnItemClickListener onItemClickListener;

    public BankCardAdapter(List<BankBean> bankBeans, Context context, int position) {
        inflater = LayoutInflater.from(context);
        this.bankBeans = bankBeans;
        this.context = context;
        defaultPosition = position;
    }


    @NonNull
    @Override
    public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bank_card, parent, false);
        BankViewHolder viewHolder = new BankViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BankViewHolder holder, final int position) {
        if (position < bankBeans.size()) {
            holder.rlExit.setVisibility(View.VISIBLE);
            holder.tvAdd.setVisibility(View.GONE);
            holder.tvBank.setText(bankBeans.get(position).getBankName());
            holder.tvBankId.setText(bankBeans.get(position).getBankCardNo());
            if (position == defaultPosition) {
                Glide.with(context).load(context.getResources().getDrawable(R.drawable.ic_kamoren)).into(holder.ivMoren);
            } else {
                Glide.with(context).load(context.getResources().getDrawable(R.drawable.ic_moren)).into(holder.ivMoren);
            }
        } else {
            holder.rlExit.setVisibility(View.INVISIBLE);
            holder.tvAdd.setVisibility(View.VISIBLE);
            holder.tvBank.setText("中国银行");
            holder.tvBankId.setText("6666 **** 6666");
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.ic_moren)).into(holder.ivMoren);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankBeans.size() + 1;
    }  //防止下标越界


    class BankViewHolder extends RecyclerView.ViewHolder {

        private TextView tvBank, tvBankId, tvAdd;
        private ImageView ivMoren;
        private RelativeLayout rlExit;

        public BankViewHolder(View view) {
            super(view);
            tvBank = view.findViewById(R.id.tv_bank);
            tvBankId = view.findViewById(R.id.tv_bank_id);
            ivMoren = view.findViewById(R.id.iv_moren);
            tvAdd = view.findViewById(R.id.tv_add);
            rlExit = view.findViewById(R.id.rl_exit);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
