package com.evgeek.skeletonapplication.ui.template.recyclerViewFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class ExampleRVFragment extends BaseFragment implements ExampleRVMvpView {

    @BindView(R.id.recycler_main)
    protected RecyclerView mainRecycler;

    @Inject
    ExampleRVMvpPresenter<ExampleRVMvpView> mPresenter;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_example_recyclerview;
    }

    @Override
    protected int getTitleStringID() {
        return R.string.recyclerview_fragment_title;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);

        mainRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mainRecycler.setAdapter(new ExampleRVAdapter(mPresenter));
    }


    @Override
    protected void setUp(View view) {

    }

    @Override
    public void notifyAdapterDataSetChanged() {
        mainRecycler.getAdapter().notifyDataSetChanged();
    }
}