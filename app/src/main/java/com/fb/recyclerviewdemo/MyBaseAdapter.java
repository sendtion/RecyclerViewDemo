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

    public MyBaseAdapter(){
        datas = new ArrayList<>();
    }

    public void setDatas(List<Article.DataBean.DatasBean> datas) {
        this.datas = datas;
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
        private TextView tv_item_article_title;
        private TextView tv_item_article_date;
        private TextView tv_item_article_author;
        private TextView tv_item_article_classify;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_item_article_title = (TextView) itemView.findViewById(R.id.tv_item_article_title);
            tv_item_article_date = (TextView) itemView.findViewById(R.id.tv_item_article_date);
            tv_item_article_author = (TextView) itemView.findViewById(R.id.tv_item_article_author);
            tv_item_article_classify = (TextView) itemView.findViewById(R.id.tv_item_article_classify);
        }

        public void setData(int position){
            Article.DataBean.DatasBean datasBean = datas.get(position);

            tv_item_article_title.setText(datasBean.getTitle());
            tv_item_article_date.setText(datasBean.getNiceDate());
            tv_item_article_author.setText("作者："+datasBean.getAuthor());
            tv_item_article_classify.setText("分类："+datasBean.getChapterName());
        }
    }
}
