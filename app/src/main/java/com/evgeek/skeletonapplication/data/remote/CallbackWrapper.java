package com.evgeek.skeletonapplication.data.remote;

import android.text.TextUtils;

import com.evgeek.skeletonapplication.R;
import com.evgeek.skeletonapplication.data.DataManager;
import com.evgeek.skeletonapplication.ui.base.MvpView;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.reactivestreams.Subscription;

import java.io.EOFException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by evgeek on 10/11/17.
 */

public abstract class CallbackWrapper<T> implements SingleObserver<T>, FlowableSubscriber<T> {
    @Inject
    DataManager dataManager;
    private WeakReference<MvpView> weakReference;

    public CallbackWrapper() {
        this(null);
        Logger.w("Callback wrapper without view created");
    }

    public CallbackWrapper(MvpView view) {
        this.weakReference = new WeakReference<>(view);
    }


    @Override
    public void onNext(T t) {
        MvpView view = weakReference.get();
        if (view != null) {
            view.hideLoading();
        }
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(3);
    }


    public void onHttpError(HttpException e) {
        onHttpError(e.code(), e.response().errorBody());
    }

    public void onUnauthorizedError() {
        MvpView view = weakReference.get();
        if (view != null) {
            view.onTokenExpire();
            view.showMessage(R.string.error_token_expired);
        }
    }

    public void onHttpError(int code, ResponseBody body) {
        MvpView view = weakReference.get();
        switch (code) {
            case 401:
                onUnauthorizedError();
                return;
            case 422:
                if (view != null) {
                    Map<String, List<String>> fieldErrors = new HashMap<>();
                    try {
                        JSONObject jsonBody = new JSONObject(body.string());
                        if (jsonBody.has("error")) {
                            Map<String, List<String>> err = new HashMap<>(1);
                            err.put("error", Collections.singletonList(jsonBody.getString("error")));
                            onJsonError(err);
                            return;
                        }
                        JSONObject errors = jsonBody.getJSONObject("errors");
                        Iterator<String> keys = errors.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONArray array = errors.optJSONArray(key);
                            if (array != null) {
                                List<String> list = new ArrayList<>(array.length());
                                for (int i = 0; i < array.length(); i++) {
                                    String str = array.optString(i, "");
                                    if (!str.isEmpty()) {
                                        list.add(str);
                                    }
                                }
                                fieldErrors.put(key, list);
                            }
                        }
                        onJsonError(fieldErrors);
                    } catch (Exception e) {
                        view.onError(e.getMessage());
                    }
                }
                break;
            default:
                Logger.d("HTTP Error %d --- %s", code, body.toString());
        }

    }

    public void onJsonError(Map<String, List<String>> fieldErrors) {
        Map<String, String> errors = new HashMap<>(fieldErrors.size());
        for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
            errors.put(entry.getKey(), TextUtils.join("\n", entry.getValue()));
        }
        onJsonStringError(errors);
    }

    public void onJsonStringError(Map<String, String> fieldErrors) {
        StringBuilder errorMsg = new StringBuilder();
        for (Map.Entry<String, String> entry : fieldErrors.entrySet()) {
            errorMsg.append(entry.getValue());
        }
        MvpView view = weakReference.get();
        if (view != null) {
            view.onError(errorMsg.toString());
        }
    }

    public void onNoNetwork() {
        Logger.d("No network connection");
    }

    public void onTimeout() {
        MvpView view = weakReference.get();
        if (view != null) {
            view.onError(R.string.request_timeout);
        }
        Logger.d("Timeout on api call");
    }

    public void onUnknownError(Throwable e) {
        Logger.d("Unknown error %s", e.getMessage());
    }

    @Override
    public void onError(Throwable e) {
        MvpView view = weakReference.get();
        if (view != null) {
            view.hideLoading();
        }
        if (e instanceof HttpException) {
            onHttpError((HttpException) e);
        } else if (e instanceof SocketTimeoutException) {
            onTimeout();
        } else if (e instanceof IOException) {
            if (e instanceof EOFException) {
                onSuccess(null);
                return;
            }
            onNoNetwork();
        } else {
            if (e.getMessage().startsWith(MockApi.MOCK_API_ERROR_PREFIX)) {
                int code = Integer.valueOf(e.getMessage().substring(MockApi.MOCK_API_ERROR_PREFIX.length()));
                onHttpError(code, null);
            } else {
                onUnknownError(e);
            }
        }
    }


    @Override
    public void onSuccess(@NonNull T t) {
        onNext(t);
    }

    @Override
    public void onComplete() {
        Logger.i("On complete");
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

}
