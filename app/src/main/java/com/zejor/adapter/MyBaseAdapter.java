package com.zejor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * listView加载多布局 或者 单布局 的基类Adapter
 *
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> data;
    private LayoutInflater inflater;
    private int [] layouts;

    public MyBaseAdapter(Context context, List<T> data, int...layouts){
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layouts=layouts;
        if (data==null){
            this.data=new ArrayList<>();
        }else{
            this.data=data;
        }
    }

    public void updateRes(List<T> data){
        if (data!=null&&data.size()>0){
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addRes(List<T> data){
        if (data!=null&&data.size()>0){
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
//---------------------------------
    @Override
    public int getViewTypeCount() {
        return layouts.length;
    }

    @Override
    public int getItemViewType(int position) {
        int type=0;
        T t = getItem(position);
        Class<?> cls = t.getClass();
        try {
            Field field = cls.getDeclaredField("type");
            field.setAccessible(true);
            type=field.getInt(t);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return type;
    }
//------------------------------
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            int viewType=getItemViewType(position);
            int layoutRes=layouts[viewType];
            convertView=inflater.inflate(layoutRes,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }


        bindData(holder,getItem(position));

        return convertView;
    }

    public abstract void bindData(ViewHolder holder,T t);

    public  class ViewHolder{
        private View layout;
        private Map<Integer,View> cacheView;

        public ViewHolder(View convertView){
            layout=convertView;
            cacheView=new HashMap<>();
        }

        public View getView(int resId){
            View view=null;
            if (cacheView.containsKey(resId)){
                view=cacheView.get(resId);
            }else {
                view=layout.findViewById(resId);
                cacheView.put(resId,view);
            }
            return view;
        }
    }

}
