package com.acelost.universalcontent.mvp;

import android.support.annotation.NonNull;

public interface BasePresenter<V> {

    void attachView(@NonNull V view);
    void detachView();
    void onDestroy();

}
