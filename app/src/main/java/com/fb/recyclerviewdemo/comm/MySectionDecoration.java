package com.fb.recyclerviewdemo.comm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.fb.recyclerviewdemo.R;
import com.fb.recyclerviewdemo.entry.TagBean;

import java.util.List;

/**
 * Description: 实现标签吸顶效果
 * CreateTime: 2018/6/15 16:06
 * Author: ShengDecheng
 */

public class MySectionDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "MySectionDecoration";

    private List<TagBean> dataList;
    private DecorationCallback callback;

    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private int alignBottom;
    private Paint.FontMetrics fontMetrics;

    public MySectionDecoration(Context context, List<TagBean> dataList, DecorationCallback callback){
        Resources resources = context.getResources();
        this.dataList = dataList;
        this.callback = callback;

        // 设置悬浮栏的画笔
        paint = new Paint();
        paint.setColor(resources.getColor(R.color.colorAccent));

        // 设置悬浮栏中文本的画笔
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(resources.getDimensionPixelSize(R.dimen.text_size_16));
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);

        fontMetrics = new Paint.FontMetrics();
        // 决定悬浮栏的高度
        topGap = resources.getDimensionPixelSize(R.dimen.section_height);
        // 决定文本显示位置
        alignBottom = resources.getDimensionPixelSize(R.dimen.section_bottom);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        //Log.e(TAG, "getItemOffsets: " + position );

        String groupId = callback.getGroupId(position);
        if (groupId.equals("-1")) return;

        // 只有是同一组的第一个才会显示悬浮栏
        if (position == 0 || isFirstInGroup(position)){
            outRect.top = topGap;
            if (dataList.get(position).getName() == ""){
                outRect.top = 0;
            }
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            String groupId = callback.getGroupId(position);
            if (groupId.equals("-1")) return;

            String textLine = callback.getGroupFirstLine(position);
            if (textLine == "") {
                float top = view.getTop();
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom, paint);
                return;
            } else {
                if (position == 0 || isFirstInGroup(position)) {
                    float top = view.getTop() - topGap;
                    float bottom = view.getTop();
                    //绘制悬浮栏
                    c.drawRect(left, top - topGap, right, bottom, paint);
                    //绘制文本
                    c.drawText(textLine, left, bottom, textPaint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;

        String preGroupId = "";
        String groupId = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if (groupId.equals("-1") || groupId.equals(preGroupId)) continue;

            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                String nextGroupId = callback.getGroupId(position + 1);
                //组内最后一个view进入了header
                if (nextGroupId != groupId && viewBottom < textY) {
                    textY = viewBottom;
                }
            }
            //textY - topGap决定了悬浮栏绘制的高度和位置
            c.drawRect(left, textY - topGap, right, textY, paint);
            //left+2*alignBottom 决定了文本往左偏移的多少（加-->向左移）
            //textY-alignBottom  决定了文本往右偏移的多少  (减-->向上移)
            c.drawText(textLine, left + 2 * alignBottom, textY - alignBottom, textPaint);
        }
    }

    private boolean isFirstInGroup(int position){
        if (position == 0){
            return true;
        } else {
            // 因为是根据 字符串内容的相同与否 来判断是不是同一组的，所以此处的标记id 要是String类型
            // 如果你只是做联系人列表，悬浮框里显示的只是一个字母，则标记id直接用 int 类型就行了
            String preGroupId = callback.getGroupId(position - 1);
            String groupId = callback.getGroupId(position);
            // 判断前一个字符串 与 当前字符串 是否相同
            if (preGroupId.equals(groupId)){
                return false;
            } else {
                return true;
            }
        }
    }

    public interface DecorationCallback{
        String getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}
