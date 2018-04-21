package com.evgeek.skeletonapplication.ui.template.recyclerViewFragment;

import com.evgeek.skeletonapplication.data.DataManager;
import com.evgeek.skeletonapplication.data.model.DataExample;
import com.evgeek.skeletonapplication.data.remote.CallbackWrapper;
import com.evgeek.skeletonapplication.di.PerView;
import com.evgeek.skeletonapplication.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@PerView
public class ExampleRVPresenter<V extends ExampleRVMvpView> extends BasePresenter<V>
        implements ExampleRVMvpPresenter<V> {
    @Inject
    DataManager dataManager;

    private Map<Integer, DataExample> mData = new LinkedHashMap<>();

    private ArrayList<ExampleRVRecyclerMvpView> holders = new ArrayList<>();
    private int currPage = 1;
    private boolean keepLoading = true;

    @Inject
    public ExampleRVPresenter() {
        super();
    }

    @Override
    public void onAttach(V mvpView) {
        currPage = 1;
        super.onAttach(mvpView);
        getMvpView().showLoading();
        keepLoading = true;
        dataManager.getExampleData(currPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CallbackWrapper<List<DataExample>>(getMvpView()) {
                    @Override
                    public void onNext(List<DataExample> list) {
                        super.onNext(list);
                        setList(list);
                    }
                });
    }

    void setList(List<DataExample> list) {
        for (int i = 0; i < list.size(); i++) {
            mData.put(i, list.get(i));
        }
        holders = new ArrayList<>(Collections.nCopies(list.size(), null));
        if (getMvpView() == null) {
            return;
        }
        getMvpView().notifyAdapterDataSetChanged();
    }

    void addItems(List<DataExample> list, int startIndex) {

        int prevSize = mData.size();
        if (list == null || list.isEmpty())
            return;
        for (int i = 0; i < list.size(); i++) {
            mData.put(i + startIndex, list.get(i));
        }
        holders.addAll(new ArrayList<>(Collections.nCopies(mData.size() - prevSize, null)));
        if (getMvpView() == null) {
            return;
        }
        getMvpView().notifyAdapterDataSetChanged();
    }


    @Override
    public void onBindRecyclerRowViewAtPosition(ExampleRVRecyclerMvpView holder, int position) {
        if (mData != null && mData.size() > position) {

            if (position >= mData.size() - 1) {
                if (keepLoading) {
                    getMvpView().showLoading();
                    int dataSize = mData.size();
                    int page = currPage;
                    dataManager.getExampleData(++currPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new CallbackWrapper<List<DataExample>>(getMvpView()) {
                                @Override
                                public void onNext(List<DataExample> list) {
                                    super.onNext(list);
                                    keepLoading = !list.isEmpty();
                                    addItems(list, dataSize);
                                }
                            });
                }
            }


            DataExample item = mData.get(position);

            holder.setField1(item.getSomeString());
            holder.setField2(String.valueOf(item.getSomeDouble()));
        }
    }


    @Override
    public int getRepositoriesRowsCount() {
        return mData.size();
    }


}
