package com.jmengxy.utillib.views.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private final Context context;
    private final int size;
    private final Rect mBounds = new Rect();
    private final int padding;
    private Paint paint;

    public DividerItemDecoration(Context context, int color, int padding) {
        this.context = context;
        this.size = 1;
        this.padding = sizeInPixel(padding);
        this.paint = new Paint();
        paint.setColor(color);
    }

    private int sizeInPixel(int size) {
        return (int) (size * context.getResources().getDisplayMetrics().density);
    }

    @Override
    @SuppressLint("NewApi")
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        canvas.save();
        int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        left += padding;

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
            final int top = bottom - sizeInPixel(size);
            canvas.drawRect(left, top, right, bottom, paint);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position != state.getItemCount()) {
            outRect.bottom = size;
        }
    }
}