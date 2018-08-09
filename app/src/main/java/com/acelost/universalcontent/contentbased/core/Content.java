package com.acelost.universalcontent.contentbased.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

public interface Content {

    @NonNull
    String getContentId();

    @NonNull
    View createView(@NonNull Context context, @Nullable ViewGroup parent);

    void destroyView();

    void attachToContainer(@NonNull ContentContainer container);

    void detachFromContainer();

}
