package com.fb.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fb.recyclerviewdemo.entry.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * CreateTime: 2018/6/11 10:30
 * Author: ShengDecheng
 */

public class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.ViewHolder> {
    private List<Article.DataBean.DatasBean> datas;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public MyBaseAdapter(){
        datas = new ArrayList<>();
    }

    public void setDatas(List<Article.DataBean.DatasBean> datas) {
        this.datas = datas;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        private TextView tv_item_article_title;
        private TextView tv_item_article_date;
        private TextView tv_item_article_author;
        private TextView tv_item_article_classify;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;

            tv_item_article_title = (TextView) itemView.findViewById(R.id.tv_item_article_title);
            tv_item_article_date = (TextView) itemView.findViewById(R.id.tv_item_article_date);
            tv_item_article_author = (TextView) itemView.findViewById(R.id.tv_item_article_author);
            tv_item_article_classify = (TextView) itemView.findViewById(R.id.tv_item_article_classify);
        }

        public void setData(final int position){
            Article.DataBean.DatasBean datasBean = datas.get(position);

            tv_item_article_title.setText(datasBean.getTitle());
            tv_item_article_date.setText(datasBean.getNiceDate());
            tv_item_article_author.setText("作者："+datasBean.getAuthor());
            tv_item_article_classify.setText("分类："+datasBean.getChapterName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(position);
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null){
                        onItemLongClickListener.onItemLongClick(position);
                    }
                    return false;
                }
            });
        }
    }
}
