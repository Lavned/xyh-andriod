package com.zejor.adapter;

import android.content.Context;
import android.widget.TextView;


import com.zejor.R;

import java.util.List;

/**
 */

public class StringDialogAdapter extends MyBaseAdapter<String> {

    @Override
    public void bindData(ViewHolder holder, String tring) {
        TextView tv1 = (TextView) holder.getView(R.id.tv1);
        tv1.setText(tring);
    }

    public StringDialogAdapter(Context context, List<String> data, int... layouts) {
        super(context, data, layouts);
    }
}
