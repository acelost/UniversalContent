package com.acelost.universalcontent.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface StorageProvider<T> {

    void persist(int key, @NonNull T obj);

    @Nullable
    T obtain(int key);

}
