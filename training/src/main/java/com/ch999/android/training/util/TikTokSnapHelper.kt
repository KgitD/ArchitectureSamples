package com.ch999.android.training.util

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class TikTokSnapHelper : PagerSnapHelper() {
    override fun onFling(velocityX: Int, velocityY: Int): Boolean {
        return super.onFling(velocityX, velocityY)
    }
    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        layoutManager ?: return RecyclerView.NO_POSITION
        val centerView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val position = layoutManager.getPosition(centerView)
        var targetPosition = RecyclerView.NO_POSITION
        if (layoutManager.canScrollHorizontally()) {
            targetPosition = if (velocityX < 0) (position - 1) else position + 1
        }

        if (layoutManager.canScrollVertically()) {
            targetPosition = if (velocityY > 0) (position + 1) else (position - 1)
        }

        val firstItem = 0
        val lastItem = layoutManager.itemCount - 1
        return lastItem.coerceAtMost(targetPosition.coerceAtLeast(firstItem))
    }
}