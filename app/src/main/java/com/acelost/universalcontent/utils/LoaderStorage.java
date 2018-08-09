package com.acelost.universalcontent.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class LoaderStorage<T> implements Storage<T>, LoaderManager.LoaderCallbacks<Void> {

    private Context mContext;
    private LoaderManager mLoaderManager;

    public LoaderStorage(@NonNull Context context, @NonNull LoaderManager manager) {
        mContext = context;
        mLoaderManager = manager;
    }

    @Override
    public void persist(int key, @NonNull T obj) {
        Loader loader = mLoaderManager.getLoader(key);
        Provider<T> provider;
        if (loader instanceof Provider) {
            //noinspection unchecked
            provider = (Provider<T>) loader;
        } else {
            //noinspection unchecked
            provider = (Provider<T>) mLoaderManager.initLoader(key, null, this);
        }
        provider.set(obj);
    }

    @Nullable
    @Override
    public T obtain(int key) {
        Loader loader = mLoaderManager.getLoader(key);
        if (loader instanceof Provider) {
            //noinspection unchecked
            return ((Provider<T>) loader).get();
        }
        return null;
    }

    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
        return new ProviderLoader<>(mContext);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) { }

    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) { }

}
