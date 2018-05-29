package com.jmengxy.utillib.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by jiemeng on 12/08/2017.
 */

public class LoadMoreRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ViewHolderManager {
        RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);

        RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent);

        RecyclerView.ViewHolder onCreateNoMoreViewHolder(ViewGroup parent);

        void onBindItemViewHolder(RecyclerView.ViewHolder holder, Object data);

        void onBindLoadingViewHolder(RecyclerView.ViewHolder holder);

        void onBindNoMoreViewHolder(RecyclerView.ViewHolder holder);

        int getViewType(Object data);
    }

    private static final int VISIBLE_THRESHOLD = 1;

    private final int VIEW_TYPE_LOADING = 10001;
    private final int VIEW_TYPE_NO_MORE = 10002;

    private ViewHolderManager viewHolderManager;

    private Context context = null;
    private RecyclerView recyclerView;

    private List<T> dataList = new ArrayList<>();
    private boolean loading = false;
    private boolean noMore = false;

    private Subject<Object> loadMoreSubject;

    public LoadMoreRecyclerAdapter(@NonNull Context context, @NonNull RecyclerView recyclerView, @NonNull ViewHolderManager viewHolderManager) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.viewHolderManager = viewHolderManager;

        initLoadMoreListener();
        recyclerView.setAdapter(this);
        loadMoreSubject = PublishSubject.create();
    }

    public Observable<Object> onLoadMore() {
        return loadMoreSubject.throttleFirst(2, TimeUnit.SECONDS);
    }

    public void setData(@NonNull List<T> dataList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new DiffCallback<>(this.dataList, dataList), true);
        diffResult.dispatchUpdatesTo(this);

        this.dataList = dataList;
    }

    public void addData(@NonNull List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        List<T> tmpList = new ArrayList<>();
        tmpList.addAll(this.dataList);
        tmpList.addAll(dataList);

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new DiffCallback<>(this.dataList, tmpList), true);
        diffResult.dispatchUpdatesTo(this);

        this.dataList = tmpList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            return viewHolderManager.onCreateLoadingViewHolder(parent);
        } else if (viewType == VIEW_TYPE_NO_MORE) {
            return viewHolderManager.onCreateNoMoreViewHolder(parent);
        } else {
            return viewHolderManager.onCreateItemViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            viewHolderManager.onBindLoadingViewHolder(holder);
        } else if (getItemViewType(position) == VIEW_TYPE_NO_MORE) {
            viewHolderManager.onBindNoMoreViewHolder(holder);
        } else {
            viewHolderManager.onBindItemViewHolder(holder, dataList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (noMore) {
            return position < dataList.size() ? viewHolderManager.getViewType(dataList.get(position)) : VIEW_TYPE_NO_MORE;
        } else {
            return position < dataList.size() ? viewHolderManager.getViewType(dataList.get(position)) : VIEW_TYPE_LOADING;
        }
    }

    @Override
    public int getItemCount() {
        if (noMore) {
            return dataList.size() + 1;
        } else {
            int loadingCount = loading ? 1 : 0;
            return dataList.size() + loadingCount;
        }
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
                    if (!noMore && !loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                        loadMoreSubject.onNext(new Object());
                    }
                }
            });
        }
    }

    public void finishLoading() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (loading) {
                    loading = false;
                    notifyItemRemoved(dataList.size());
                }
            }
        });
    }

    public void startLoading() {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (!loading) {
                    loading = true;
                    notifyItemInserted(dataList.size());
                }
            }
        });
    }

    public void setNoMore(boolean noMore) {
        if (this.noMore != noMore) {
            this.noMore = noMore;
            if (noMore) {
                notifyItemInserted(dataList.size());
            } else {
                notifyItemRemoved(dataList.size());
            }
        }
    }
}