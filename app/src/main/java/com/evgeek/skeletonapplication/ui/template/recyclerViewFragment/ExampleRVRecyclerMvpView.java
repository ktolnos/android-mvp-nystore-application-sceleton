package com.evgeek.skeletonapplication.ui.template.recyclerViewFragment;

import com.evgeek.skeletonapplication.ui.base.RecyclerMvpView;


public interface ExampleRVRecyclerMvpView extends RecyclerMvpView {

    int getPosition();

    void setField1(String text);

    void setField2(String text);

}
