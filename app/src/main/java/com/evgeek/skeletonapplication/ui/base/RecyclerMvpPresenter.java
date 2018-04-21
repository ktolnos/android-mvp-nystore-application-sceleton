package com.evgeek.skeletonapplication.ui.base;

/**
 * Created by evgeek on 10/13/17.
 */

public interface RecyclerMvpPresenter<VH extends RecyclerMvpView> {

    void onBindRecyclerRowViewAtPosition(VH holder, int position);

    int getRepositoriesRowsCount();
}
