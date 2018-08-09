package com.acelost.universalcontent.utils;

import android.support.annotation.Nullable;

public interface InstanceProvider<T> {

    void set(@Nullable T instance);

    @Nullable
    T get();

}
