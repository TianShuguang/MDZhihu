package com.tian.zhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tian.zhihu.R;
import com.tian.zhihu.base.BitmapCache;
import com.tian.zhihu.network.VolleyQueueController;
import com.tian.zhihu.network.bean.ThemeContent;
import com.tian.zhihu.network.bean.ThemeStory;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/3.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private ThemeContent mContent;
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    private RecyclerItemClickListener mListener;

    public NewsAdapter(Context context, ThemeContent content) {
        this.mContext = context;
        this.mContent = content;
        this.mQueue= VolleyQueueController.getInstance(mContext).getRequestQueue();
        mImageLoader=new ImageLoader(mQueue,new BitmapCache());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 给ViewHolder设置布局文件
        View v = null;
        if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
            return new MViewHolder(v);
        } else if (viewType == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_header, parent, false);
            return new VHHeader(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MViewHolder) {
            ThemeStory story = getItem(position);
            //cast holder to MViewHolder and set data
            MViewHolder itemHolder= (MViewHolder) holder;
            itemHolder.mTextView.setText(story.title);
            if (ValueUtils.isListNotEmpty(story.images)) {
                itemHolder.mImageView.setVisibility(View.VISIBLE);
                //使用volley自带ImageLoader显示图片
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(itemHolder.mImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                mImageLoader.get(story.images.get(0), listener);
            }else{
                itemHolder.mImageView.setVisibility(View.GONE);
            }
        } else if (holder instanceof VHHeader) {
            //cast holder to VHHeader and set data for header.
            VHHeader headerHolder= (VHHeader) holder;
            headerHolder.theme_desc.setText(""+mContent.description);
            if (ValueUtils.isNotEmpty(mContent.background)) {
                headerHolder.theme_image.setImageUrl(mContent.background, new ImageLoader(mQueue, new BitmapCache()));
            }
        }

    }

    private ThemeStory getItem(int position) {
        return mContent.stories.get(position);
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return mContent.stories == null ? 0 : mContent.stories.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (0==position){
            return TYPE_HEADER;
        }else{
            return TYPE_ITEM;
        }
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

    class VHHeader extends RecyclerView.ViewHolder {
        NetworkImageView theme_image;
        TextView theme_desc;

        public VHHeader(View itemView) {
            super(itemView);
            theme_image= (NetworkImageView) itemView.findViewById(R.id.theme_image);
            theme_desc= (TextView) itemView.findViewById(R.id.theme_desc);
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
        public void onItemClick(View view,int postion);
    }
}
