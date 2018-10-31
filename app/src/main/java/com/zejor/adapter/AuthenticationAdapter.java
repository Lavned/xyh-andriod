package com.zejor.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zejor.R;
import com.zejor.bean.AuthenticationBean;

import java.util.List;

/**
 * 认证
 */
public class AuthenticationAdapter extends MyBaseAdapter<AuthenticationBean> {
    private List<AuthenticationBean> data;
    private Context context;

    public AuthenticationAdapter(Context context, List<AuthenticationBean> data, int... layouts) {
        super(context, data, layouts);
        this.context = context;
        this.data = data;
    }

    @Override
    public void bindData(ViewHolder holder, AuthenticationBean bean) {
        TextView tvAuthenticationItem = (TextView) holder.getView(R.id.tvAuthenticationItem);
        TextView tvAuthenticationState = ((TextView) holder.getView(R.id.tvAuthenticationState));
        View splitView = holder.getView(R.id.splitView);

        String authItem = bean.getAuthItem();//名称
        String authStatus = bean.getAuthStatus();//状态
        tvAuthenticationItem.setText(authItem);
        tvAuthenticationState.setText(authStatus);
        if ("已完善".equals(authStatus)) {
            tvAuthenticationState.setTextColor(context.getResources().getColor(R.color.color27));
        } else {
            tvAuthenticationState.setTextColor(context.getResources().getColor(R.color.color136));
        }

        if ("支付宝".equals(authItem)) {
            splitView.setVisibility(View.VISIBLE);
        }

    }
}
