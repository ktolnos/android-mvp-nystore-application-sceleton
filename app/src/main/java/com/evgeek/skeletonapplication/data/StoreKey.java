package com.evgeek.skeletonapplication.data;

import android.support.annotation.NonNull;

import com.nytimes.android.external.cache3.Preconditions;

/**
 * Created by evgeek on 10/13/17.
 */

public class StoreKey<T> {

    private static final StoreKey EMPTY_STORE_KEY = new StoreKey("");

    private final T key;
    @NonNull
    private final String type;
    @NonNull
    private final String token;

    public StoreKey(@NonNull String userToken) {
        key = null;
        this.type = "";
        this.token = Preconditions.checkNotNull(userToken);
    }

    public StoreKey(T key, @NonNull String userToken) {
        this.key = key;
        this.type = "";
        this.token = Preconditions.checkNotNull(userToken);
    }

    public StoreKey(@NonNull String type, T key, @NonNull String userToken) {
        this.key = key;
        this.type = Preconditions.checkNotNull(type);
        this.token = Preconditions.checkNotNull(userToken);
    }

    public StoreKey(T key) {
        this.key = key;
        this.type = "";
        this.token = "";
    }

    @NonNull
    public static StoreKey empty() {
        return EMPTY_STORE_KEY;
    }

    public T getKey() {
        return key;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    public String getUserToken() {
        return token;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof StoreKey)) {
            return false;
        }

        StoreKey storeKey = (StoreKey) object;

        if (key == null) {
            return storeKey.key == null;
        }

        return key.equals(storeKey.key) && type.equals(storeKey.type) && token.equals(storeKey.token);

    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        if (key != null) {
            result = 17 * result + key.hashCode();
        }
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BarCode{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", tiken='" + token + '\'' +
                '}';
    }
}
