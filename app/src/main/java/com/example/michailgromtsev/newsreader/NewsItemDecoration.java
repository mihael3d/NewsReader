package com.example.michailgromtsev.newsreader;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class NewsItemDecoration extends RecyclerView.ItemDecoration {

    private final int offset;

    public NewsItemDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
       final int position = parent.getChildLayoutPosition(view);

       if (position != RecyclerView.NO_POSITION) {
           outRect.set(offset,offset,offset,offset);
       } else {
           outRect.set(0,0,0,0);
       }

    }
}