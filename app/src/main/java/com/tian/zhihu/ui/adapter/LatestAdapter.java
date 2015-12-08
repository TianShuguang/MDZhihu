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
import com.tian.zhihu.network.bean.LatestBean;
import com.tian.zhihu.network.bean.LatestStory;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/3.
 */
public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.MViewHolder>{

    private Context mContext;
    private LatestBean mBean;
    private ArrayList<LatestStory> stories=new ArrayList<LatestStory>();
    private ArrayList<LatestStory> top_stories=new ArrayList<LatestStory>();
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    private RecyclerItemClickListener mListener;


    public LatestAdapter(Context context, LatestBean bean) {
        this.mContext = context;
        this.mBean = bean;
        stories=mBean.stories;
        top_stories=mBean.top_stories;
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
        LatestStory latest = stories.get(position);
        holder.mTextView.setText(latest.title);
        if (ValueUtils.isListNotEmpty(latest.images)) {
            holder.mImageView.setVisibility(View.VISIBLE);
            //使用volley自带ImageLoader显示图片
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.mImageView, R.mipmap.default_pic, R.mipmap.default_pic);
            mImageLoader.get(latest.images.get(0), listener);
        }else{
            holder.mImageView.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        // 返回数据总数
        return stories == null ? 0 : stories.size();
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
