package com.tian.zhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.tian.zhihu.R;
import com.tian.zhihu.base.BitmapCache;
import com.tian.zhihu.network.VolleyQueueController;
import com.tian.zhihu.network.bean.HotNews;
import com.tian.zhihu.network.bean.ThemeStory;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/3.
 */
public class HotNewsAdapter extends RecyclerView.Adapter<HotNewsAdapter.MViewHolder>{

    private Context mContext;
    private ArrayList<HotNews> mList=new ArrayList<HotNews>();
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    private RecyclerItemClickListener mListener;


    public HotNewsAdapter(Context context, ArrayList<HotNews> list) {
        this.mContext = context;
        this.mList = list;
        this.mQueue= VolleyQueueController.getInstance(mContext).getRequestQueue();
        mImageLoader=new ImageLoader(mQueue,new BitmapCache());
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        HotNews news = mList.get(position);
        holder.mTextView.setText(news.title);
        if (ValueUtils.isStrNotEmpty(news.thumbnail)) {
            holder.mImageView.setVisibility(View.VISIBLE);
            //使用volley自带ImageLoader显示图片
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.mImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            mImageLoader.get(news.thumbnail, listener);
        }else{
            holder.mImageView.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        // 返回数据总数
        return mList == null ? 0 : mList.size();
    }

    // 重写的自定义ViewHolder
    public class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView;

        public ImageView mImageView;

        public MViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.name);
            mImageView = (ImageView) itemView.findViewById(R.id.pic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }
    }


    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(RecyclerItemClickListener listener){
        this.mListener = listener;
    }

    public interface RecyclerItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
