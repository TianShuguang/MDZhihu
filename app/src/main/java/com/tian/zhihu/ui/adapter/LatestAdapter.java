package com.tian.zhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tian.zhihu.R;
import com.tian.zhihu.base.BitmapCache;
import com.tian.zhihu.network.VolleyQueueController;
import com.tian.zhihu.network.bean.LatestBean;
import com.tian.zhihu.network.bean.LatestStory;
import com.tian.zhihu.network.bean.LatestTopStory;
import com.tian.zhihu.network.bean.ThemeStory;
import com.tian.zhihu.utils.ValueUtils;
import com.tian.zhihu.widget.viewpager.IndexViewPager;
import com.tian.zhihu.widget.viewpager.OnChildClickListener;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/3.
 */
public class LatestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private LatestBean mBean;
    private ArrayList<LatestStory> stories=new ArrayList<LatestStory>();
    private ArrayList<LatestTopStory> top_stories=new ArrayList<LatestTopStory>();
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    private RecyclerItemClickListener mListener;
    private OnChildClickListener childClickListener;


    public LatestAdapter(Context context, LatestBean bean,OnChildClickListener childListener) {
        this.mContext = context;
        this.mBean = bean;
        stories=mBean.stories;
        top_stories=mBean.top_stories;
        this.childClickListener=childListener;
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
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.index_header_layout, parent, false);
            return new VHHeader(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MViewHolder) {
            //cast holder to MViewHolder and set data
            MViewHolder itemHolder= (MViewHolder) holder;
            LatestStory latest = getItem(position);
            itemHolder.mTextView.setText(latest.title);
            if (ValueUtils.isListNotEmpty(latest.images)) {
                itemHolder.mImageView.setVisibility(View.VISIBLE);
                //使用volley自带ImageLoader显示图片
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(itemHolder.mImageView, R.mipmap.default_pic, R.mipmap.default_pic);
                mImageLoader.get(latest.images.get(0), listener);
            }else{
                itemHolder.mImageView.setVisibility(View.GONE);
            }
        }else if (holder instanceof VHHeader){
            VHHeader headerHolder= (VHHeader) holder;
            headerHolder.index_viewpager_content.removeAllViews();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            IndexViewPager bunnerPager = new IndexViewPager(mContext);
            headerHolder.index_viewpager_content.addView(bunnerPager, params);
            bunnerPager.setOnChildClickListener(childClickListener);
            bunnerPager.setData(top_stories);
        }

    }

    private LatestStory getItem(int position) {
        return stories.get(position-1);
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return stories == null ? 0 : stories.size()+1;
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
        LinearLayout index_viewpager_content;

        public VHHeader(View itemView) {
            super(itemView);
            index_viewpager_content= (LinearLayout) itemView.findViewById(R.id.index_viewpager_content);
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
