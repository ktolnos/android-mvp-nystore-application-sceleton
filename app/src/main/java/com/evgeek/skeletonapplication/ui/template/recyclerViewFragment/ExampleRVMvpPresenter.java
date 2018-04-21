package com.evgeek.skeletonapplication.ui.template.recyclerViewFragment;


import com.evgeek.skeletonapplication.ui.base.MvpPresenter;

public interface ExampleRVMvpPresenter<V extends ExampleRVMvpView> extends MvpPresenter<V>,
        ExampleRVRecyclerMvpPresenter<ExampleRVRecyclerMvpView> {

}
