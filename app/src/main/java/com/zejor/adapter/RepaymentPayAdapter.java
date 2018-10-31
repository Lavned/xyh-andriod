package com.zejor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zejor.R;
import com.zejor.bean.RepaymentPayBean;

import java.util.List;

public class RepaymentPayAdapter extends RecyclerView.Adapter<RepaymentPayAdapter.MyViewHolder> {

    Context context;
    LayoutInflater inflater;
    List<RepaymentPayBean.PmBean.RepayWayBean> mData;
    int checkPosition = 0;
    OnItemClickListener onItemClickListener;

    public RepaymentPayAdapter(Context context, List<RepaymentPayBean.PmBean.RepayWayBean> mData) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pay, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvPayname.setText(mData.get(position).getPayName());
        Glide.with(context).load(mData.get(position).getLogoUrl()).into(holder.ivLogo);
        Glide.with(context).load(position == checkPosition ? R.drawable.ic_huankuan_xueze : R.drawable.ic_huankuan_choose).into(holder.ivChecked);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    public void update(List<RepaymentPayBean.PmBean.RepayWayBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }


    public void changeCheck(int position) {
        checkPosition = position;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPayname;
        ImageView ivLogo, ivChecked;

        public MyViewHolder(View view) {
            super(view);
            tvPayname = view.findViewById(R.id.tv_payname);
            ivLogo = view.findViewById(R.id.iv_logo);
            ivChecked = view.findViewById(R.id.iv_checked);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
