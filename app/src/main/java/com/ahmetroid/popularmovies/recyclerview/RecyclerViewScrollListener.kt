package com.ahmetroid.popularmovies.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val THRESHOLD = 2

class RecyclerViewScrollListener(
    val layoutManager: LinearLayoutManager,
    val onLoadMore: () -> Unit
) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount: Int = layoutManager.itemCount
        val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()

        if (lastVisibleItemPosition + THRESHOLD > totalItemCount) {
            onLoadMore()
        }
    }
}