package com.fb.recyclerviewdemo;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Description: 通过ItemDecoration模拟Padding效果，如果只设置底部divider，可实现分割线效果
 * CreateTime: 2018/6/15 15:53
 * Author: ShengDecheng
 */

public class MyPaddingDecoration extends RecyclerView.ItemDecoration {
    private int divider;

    public MyPaddingDecoration(Context context){
        divider = context.getResources().getDimensionPixelSize(R.dimen.divider_1);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

//        outRect.left = divider;  //相当于 设置 left padding
//        outRect.top = divider;   //相当于 设置 top padding
//        outRect.right = divider; //相当于 设置 right padding
        outRect.bottom = divider;  //相当于 设置 bottom padding
    }
}
