package com.jmengxy.utildroid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiemeng on 12/08/2017.
 */

public class LoadMoreRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface ViewHolderManger {
        RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent);

        RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent);

        void onBindItemViewHolder(RecyclerView.ViewHolder holder, Object data);

        void onBindLoadingViewHolder(RecyclerView.ViewHolder holder);
    }

    private static final int VISIBLE_THRESHOLD = 1;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private ViewHolderManger viewHolderManger;
    private OnLoadMoreListener onLoadMoreListener;

    private Context context = null;
    private RecyclerView recyclerView;

    private List<T> dataList = new ArrayList<>();
    private boolean loading = false;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public LoadMoreRecyclerAdapter(@NonNull Context context, @NonNull RecyclerView recyclerView, @NonNull ViewHolderManger viewHolderManger) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.viewHolderManger = viewHolderManger;

        initLoadMoreListener();
        recyclerView.setAdapter(this);
    }

    public void setData(@NonNull List<T> dataList) {
        finishLoading();

        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void addData(@NonNull List<T> dataList) {
        finishLoading();

        int previousItemsCount = this.dataList.size();
        this.dataList.addAll(dataList);
        if (dataList.size() > 0) {
            notifyItemRangeInserted(previousItemsCount, dataList.size());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return viewHolderManger.onCreateLoadingViewHolder(parent);
        } else {
            return viewHolderManger.onCreateItemViewHolder(parent);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            viewHolderManger.onBindLoadingViewHolder(holder);
        } else {
            viewHolderManger.onBindItemViewHolder(holder, dataList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position < dataList.size() ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public int getItemCount() {
        int loadingCount = loading ? 1 : 0;
        return dataList.size() + loadingCount;
    }

    protected Context getContext() {
        return this.context;
    }

    private void initLoadMoreListener() {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                        if (onLoadMoreListener != null) {
                            startLoading();
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            });
        }
    }

    private void finishLoading() {
        if (loading) {
            loading = false;
            notifyItemRemoved(dataList.size());
        }
    }

    private void startLoading() {
        if (!loading) {
            loading = true;
            notifyItemInserted(dataList.size());
        }
    }
}