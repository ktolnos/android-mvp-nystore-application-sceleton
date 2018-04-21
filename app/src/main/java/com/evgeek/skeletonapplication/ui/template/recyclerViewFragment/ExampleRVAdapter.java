package com.evgeek.skeletonapplication.ui.template.recyclerViewFragment;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.ui.base.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ExampleRVAdapter extends BaseRecyclerAdapter<ExampleRVAdapter.ViewHolder> {

    private ExampleRVRecyclerMvpPresenter<ExampleRVRecyclerMvpView> mPresenter;

    public ExampleRVAdapter(ExampleRVRecyclerMvpPresenter<ExampleRVRecyclerMvpView> presenter) {
        super(presenter);
        mPresenter = presenter;
    }

    @Override
    public @LayoutRes
    int getViewLayoutID() {
        return R.layout.adapter_item_template;
    }

    @Override
    protected ViewHolder newViewHolder(View v) {
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ExampleRVRecyclerMvpView {

        @BindView(R.id.adapter_item_parent)
        ViewGroup mParent;

        @BindView(R.id.adapter_item_field1)
        TextView field1;
        @BindView(R.id.adapter_item_field2)
        TextView field2;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setField1(String text) {
            field1.setText(text);
        }

        @Override
        public void setField2(String text) {
            field2.setText(text);
        }
    }
}
