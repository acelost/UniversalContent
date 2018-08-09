package com.acelost.universalcontent.contentbased.content.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.acelost.universalcontent.contentbased.core.Content;
import com.acelost.universalcontent.contentbased.core.ContentContainer;

public abstract class BaseContent implements Content {

    @Nullable
    private ContentContainer mContentContainer;

    @Nullable
    public ContentContainer getContainer() {
        return mContentContainer;
    }

    @Override
    public void attachToContainer(@NonNull ContentContainer container) {
        mContentContainer = container;
    }

    @Override
    public void detachFromContainer() {
        mContentContainer = null;
    }

    // region Utility methods for casting container

    @Nullable
    protected <T> T getContainer(@NonNull Class<? extends T> clazz) {
        if (mContentContainer != null) {
            if (clazz.isInstance(mContentContainer)) {
                return clazz.cast(mContentContainer);
            }
        }
        return null;
    }

    @NonNull
    protected <T> T requestContainer(@NonNull Class<? extends T> clazz) {
        T container = getContainer(clazz);
        if (container == null) {
            throw new RuntimeException("Failed attempt to request " + mContentContainer + " as " + clazz.getCanonicalName());
        }
        return container;
    }

    protected boolean isContainer(@NonNull Class clazz) {
        if (mContentContainer == null) {
            return false;
        }
        return clazz.isInstance(mContentContainer);
    }

    // endregion

}
