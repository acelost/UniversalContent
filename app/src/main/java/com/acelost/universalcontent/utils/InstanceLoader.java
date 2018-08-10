package com.acelost.universalcontent.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

public class InstanceLoader<T> extends Loader<Void> implements InstanceProvider<T> {

    protected T mObject;

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public InstanceLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        deliverResult(null);
    }

    @Override
    protected void onReset() {
        mObject = null; // todo onDestroy
    }

    @Nullable
    @Override
    public T get() {
        return mObject;
    }

    @Override
    public void set(@Nullable T instance) {
        mObject = instance;
    }

}
