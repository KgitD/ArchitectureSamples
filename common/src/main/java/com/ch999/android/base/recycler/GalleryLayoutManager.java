package com.ch999.android.base.recycler;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * author: KID
 * created on: 2020/5/29 13:44
 * description: 画廊效果布局管理器。
 */
public class GalleryLayoutManager extends RecyclerView.LayoutManager {
    private float mWidthPercent   = 0.60f;
    private float mHeightPercent  = 0.95f;
    private float mScaleYFactor   = 0.15f;
    private float mRotationDegree = 10;
    private float mAlphaFactor    = 0.5f;

    private int               mTotalWidth;
    private int               mScrollOffset;
    private SparseArray<Rect> mAllRects;

    public GalleryLayoutManager() {
        mAllRects = new SparseArray<>();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        calculateChildrenSize(recycler);
        recyclerAndFillView(recycler, state);
    }

    private void calculateChildrenSize(RecyclerView.Recycler recycler) {
        mTotalWidth = (int) (getWidth() * (1.0f - mWidthPercent));
        final Rect decorationRect = new Rect();
        for (int i = 0; i < getItemCount(); i++) {
            View itemView = recycler.getViewForPosition(i);
            measureChildWithMargins(itemView, (int) (getWidth() * (1.0f - mWidthPercent)),
                    (int) (getHeight() * (1.0f - mHeightPercent)));

            decorationRect.set(0, 0, 0, 0);
            calculateItemDecorationsForChild(itemView, decorationRect);
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(itemView);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(itemView);

            Rect childRect = mAllRects.get(i);
            if (null == childRect) childRect = new Rect();
            childRect.set(mTotalWidth, (int) ((getHeight() - decoratedMeasuredHeight) / 2.0f), mTotalWidth +
                    decoratedMeasuredWidth, (int) ((getHeight() + decoratedMeasuredHeight) / 2.0f));
            mAllRects.put(i, childRect);
            mTotalWidth += decoratedMeasuredWidth;
        }
        mTotalWidth += (int) (getWidth() * (1.0f - mWidthPercent));
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (mScrollOffset + dx < 0) {
            dx = -mScrollOffset;
        } else if (mScrollOffset + dx > mTotalWidth - getWidth()) {
            dx = mTotalWidth - getWidth() - mScrollOffset;
        }
        offsetChildrenHorizontal(-dx);
        recyclerAndFillView(recycler, state);
        mScrollOffset += dx;
        return dx;
    }

    private void recyclerAndFillView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0 || state.isPreLayout()) return;
        int w = getWidth();
        int h = getHeight();
        detachAndScrapAttachedViews(recycler);
        Rect displayRect = new Rect(mScrollOffset, 0, mScrollOffset + w, h);

        float scale;
        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayRect, mAllRects.get(i))) {
                View itemView = recycler.getViewForPosition(i);

                Rect rect = mAllRects.get(i);
                scale = (rect.left + getWidth() * (mWidthPercent * 0.5f) - getWidth() * 0.5f - mScrollOffset);
                scale /= translate();
                itemView.setRotationY(mRotationDegree * (1 - scale * 0.5f) - mRotationDegree);
                scale = Math.abs(scale);
                itemView.setAlpha(1 - scale * mAlphaFactor);
                scale *= mScaleYFactor;
                scale = 1 - scale;
                itemView.setScaleX(scale);
                itemView.setScaleY(scale);

                measureChildWithMargins(itemView, (int) (getWidth() * (1.0f - mWidthPercent)),
                        (int) (getHeight() * (1.0f - mHeightPercent)));
                addView(itemView);

                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                layoutDecorated(itemView, rect.left - mScrollOffset - lp.leftMargin, rect.top + lp.topMargin,
                        rect.right - mScrollOffset - lp.rightMargin, rect.bottom + lp.bottomMargin);
            }
        }
    }

    private float translate() {
        return (getWidth() * mWidthPercent);
    }

    public float getWidthPercent() {
        return mWidthPercent;
    }

    public void setWidthPercent(float widthPercent) {
        mWidthPercent = widthPercent;
    }

    public float getHeightPercent() {
        return mHeightPercent;
    }

    public void setHeightPercent(float heightPercent) {
        mHeightPercent = heightPercent;
    }

    public float getScaleYFactor() {
        return mScaleYFactor;
    }

    public void setScaleYFactor(float scaleYFactor) {
        mScaleYFactor = scaleYFactor;
    }

    public float getRotationDegree() {
        return mRotationDegree;
    }

    public void setRotationDegree(float rotationDegree) {
        mRotationDegree = rotationDegree;
    }

    public float getAlphaFactor() {
        return mAlphaFactor;
    }

    public void setAlphaFactor(float alphaFactor) {
        mAlphaFactor = alphaFactor;
    }
}