package me.bakumon.gank.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.AbsListView;

/**
 * 简单上拉加载
 * Created by bakumon on 2016/12/8 11:41.
 */
public class LoadMore extends RecyclerView.OnScrollListener {

    private OnLoadMoreListener loadMoreListener;
    private boolean isLastPage;
    private boolean isGrid;

    public LoadMore(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(this);
    }

    public LoadMore(RecyclerView recyclerView, boolean isGrid) {
        this.isGrid = isGrid;
        recyclerView.addOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (isLastPage) {
            return;
        }
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            int lastVisiblePosition;
            if (isGrid) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] lastVisible = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
                lastVisiblePosition = lastVisible[lastVisible.length - 1];
            } else {
                lastVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
            if (lastVisiblePosition == recyclerView.getAdapter().getItemCount() - 1) {
                if (loadMoreListener != null) {
                    loadMoreListener.onLoadMore();
                }
            }
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
}
