package com.evgeek.skeletonapplication.ui.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by evgeek on 10/13/17.
 */

public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder & RecyclerMvpView> extends RecyclerView.Adapter<VH> {

    private final RecyclerMvpPresenter<RecyclerMvpView> presenter;

    public BaseRecyclerAdapter(RecyclerMvpPresenter presenter) {
        this.presenter = presenter;
    }

    protected abstract @LayoutRes
    int getViewLayoutID();

    protected abstract VH newViewHolder(View v);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return newViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(getViewLayoutID(), parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        presenter.onBindRecyclerRowViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getRepositoriesRowsCount();
    }
}
