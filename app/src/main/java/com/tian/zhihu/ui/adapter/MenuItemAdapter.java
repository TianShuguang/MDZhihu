package com.tian.zhihu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tian.zhihu.R;
import com.tian.zhihu.network.bean.ZhihuTheme;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/1.
 */
public class MenuItemAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ZhihuTheme> mList=new ArrayList<ZhihuTheme>();

    public MenuItemAdapter(Context context,ArrayList<ZhihuTheme> list)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mList=list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.menu_item,null,false);
            holder.menu_item_tv= (TextView) convertView.findViewById(R.id.menu_item_tv);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        ZhihuTheme theme=mList.get(position);
        if (ValueUtils.isNotEmpty(theme)){
            holder.menu_item_tv.setText(""+theme.name);
        }

        return convertView;
    }

    class ViewHolder{
        TextView menu_item_tv;
    }
}
