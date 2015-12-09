package com.tian.zhihu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tian.zhihu.R;
import com.tian.zhihu.base.BitmapCache;
import com.tian.zhihu.network.VolleyQueueController;
import com.tian.zhihu.network.bean.LongComments;
import com.tian.zhihu.utils.ValueUtils;

import java.util.ArrayList;

/**
 * Created by tianshuguang on 15/12/9.
 */
public class LongCommentsAdapter extends RecyclerView.Adapter<LongCommentsAdapter.MViewHolder>{

    private Context mContext;
    public ArrayList<LongComments> mList=new ArrayList<LongComments>();
    private RequestQueue mQueue;

    public LongCommentsAdapter(Context context, ArrayList<LongComments> list) {
        this.mContext = context;
        this.mList = list;
        this.mQueue= VolleyQueueController.getInstance(mContext).getRequestQueue();
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_long_comments, parent, false);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        LongComments comments = getItem(position);
        holder.comments_author.setText(""+comments.author);
        holder.comments_content.setText(""+comments.content);
        holder.comments_likes.setText(""+comments.likes);

        if (ValueUtils.isStrNotEmpty(comments.avatar)) {
            holder.comments_avatar.setImageUrl(comments.avatar, new ImageLoader(mQueue, new BitmapCache()));
        }else{
            holder.comments_avatar.setImageResource(R.mipmap.comment_avatar);
        }
    }

    private LongComments getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    // 重写的自定义ViewHolder
    public class MViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView comments_avatar;
        public TextView comments_author;
        public TextView comments_content;
        public TextView comments_likes;

        public MViewHolder(View itemView) {
            super(itemView);
            comments_avatar = (NetworkImageView) itemView.findViewById(R.id.comments_avatar);
            comments_author = (TextView) itemView.findViewById(R.id.comments_author);
            comments_content = (TextView) itemView.findViewById(R.id.comments_content);
            comments_likes = (TextView) itemView.findViewById(R.id.comments_likes);
        }
    }
}
