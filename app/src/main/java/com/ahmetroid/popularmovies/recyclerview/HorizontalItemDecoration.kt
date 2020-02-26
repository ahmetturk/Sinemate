package com.ahmetroid.popularmovies.recyclerview

import android.content.res.Resources
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.ahmetroid.popularmovies.R

class HorizontalItemDecoration(private val resources: Resources) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        if (itemPosition == RecyclerView.NO_POSITION) {
            outRect.set(0, 0, 0, 0)
        } else {
            val dividerNormal = resources.getDimensionPixelOffset(R.dimen.divider_normal)
            val dividerSmall = resources.getDimensionPixelOffset(R.dimen.divider_small)

            val leftDivider =
                if (itemPosition == 0) dividerNormal else dividerSmall
            val rightDivider =
                if (itemPosition == parent.layoutManager!!.itemCount - 1)
                    dividerNormal
                else
                    dividerSmall

            outRect.set(leftDivider, dividerSmall, rightDivider, dividerSmall)
        }
    }
}