package com.zejor.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zejor.R;
import com.zejor.adapter.StringDialogAdapter;

import java.util.List;

public class DataPopupWindow extends PopupWindow implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context mContext;
    private Activity activity;
    private LayoutInflater inflater;
    private PopupWindow popupWindow;
    private View inflaterView;
    private TextView tvLeft;
    private TextView tvTitle;
    private TextView tvRight;
    private ListView listView;
    private StringDialogAdapter adapter;
    private List<String> list;
    private LinearLayout dialogTitle;

    public DataPopupWindow(Activity context, List<String> list) {
        super(context);

        mContext = context;
        activity = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflaterView = inflater.inflate(R.layout.dialog_fee_layout, null);
        initView(inflaterView);
        setContentView(inflaterView);
    }


    /**
     * 初始化dialog中的控件
     */
    private void initView(View inflaterView) {
        dialogTitle = ((LinearLayout) inflaterView.findViewById(R.id.dialogTop));
        dialogTitle.setVisibility(View.GONE);
        tvLeft = ((TextView) inflaterView.findViewById(R.id.dialogLeft));
        tvTitle = ((TextView) inflaterView.findViewById(R.id.dialogTitle));
        tvRight = ((TextView) inflaterView.findViewById(R.id.dialogRight));

        tvLeft.setVisibility(View.VISIBLE);
        tvTitle.setText("请选择银行卡");

        listView = ((ListView) inflaterView.findViewById(R.id.dialogFeeListView));
        adapter = new StringDialogAdapter(mContext, list, R.layout.dialog_fee_item2);
        listView.setAdapter(adapter);

        tvRight.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    /**
     * zhanshi弹窗
     * @param view
     */
    public void showPop(View view) {

        //设置宽与高
        setWidth(LinearLayoutCompat.LayoutParams.MATCH_PARENT);

        setHeight(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        setAnimationStyle(R.style.popwindow_anim_style);
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.4f;
        activity.getWindow().setAttributes(params);
        showAtLocation(view.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 1f;
        activity.getWindow().setAttributes(params);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != onDialogItemClickListener) {
            onDialogItemClickListener.onDialogItemClick(list.get(position));
        }
        this.dismiss();
    }

    private OnDialogItemClickListener onDialogItemClickListener;

    public void setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener) {
        this.onDialogItemClickListener = onDialogItemClickListener;
    }

    public interface OnDialogItemClickListener {

        void onDialogItemClick(String str);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogRight:
                this.dismiss();
                break;
        }

    }
}
