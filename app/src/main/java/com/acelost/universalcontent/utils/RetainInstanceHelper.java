package com.acelost.universalcontent.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

public class RetainInstanceHelper<T> implements StorageProvider<T>, LoaderManager.LoaderCallbacks<Void> {

    private Context mContext;
    private LoaderManager mLoaderManager;

    public RetainInstanceHelper(@NonNull Context context, @NonNull LoaderManager manager) {
        mContext = context;
        mLoaderManager = manager;
    }

    @Override
    public void persist(int key, @NonNull T instance) {
        Loader loader = mLoaderManager.getLoader(key);
        InstanceProvider<T> provider;
        if (loader instanceof InstanceProvider) {
            //noinspection unchecked
            provider = (InstanceProvider<T>) loader;
        } else {
            //noinspection unchecked
            provider = (InstanceProvider<T>) mLoaderManager.initLoader(key, null, this);
        }
        provider.set(instance);
    }

    @Nullable
    @Override
    public T obtain(int key) {
        Loader loader = mLoaderManager.getLoader(key);
        if (loader instanceof InstanceProvider) {
            //noinspection unchecked
            return ((InstanceProvider<T>) loader).get();
        }
        return null;
    }

    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
        return new InstanceLoader<>(mContext);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) { }

    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) {
        Log.e("RetainInstance", "reset loader");
    }

}
