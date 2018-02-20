package com.jmengxy.utillib.adapters;

import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Objects;

public final class DiffCallback<T> extends DiffUtil.Callback {
    private List<T> oldList;
    private List<T> newList;

    public DiffCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldList.size() > oldItemPosition ? oldList.get(oldItemPosition) : null;
        T newItem = newList.size() > newItemPosition ? newList.get(newItemPosition) : null;
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = oldList.size() > oldItemPosition ? oldList.get(oldItemPosition) : null;
        T newItem = newList.size() > newItemPosition ? newList.get(newItemPosition) : null;
        return Objects.equals(oldItem, newItem);
    }
}