package com.acelost.universalcontent.contentbased.content.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.acelost.universalcontent.contentbased.core.ContentContainer;
import com.acelost.universalcontent.mvp.BasePresenter;
import com.acelost.universalcontent.mvp.BaseView;

public abstract class MVPContent<VIEW extends BaseView, PRESENTER extends BasePresenter<VIEW>> extends BaseContent {

    protected VIEW mView;

    protected PRESENTER mPresenter;

    protected abstract int getPresenterId();

    protected abstract PRESENTER createPresenter();

    protected abstract VIEW createMVPView(@NonNull Context context, @Nullable ViewGroup parent);

    protected void inject() {
        // inject nothing
    }

    @NonNull
    @Override
    public final View createView(@NonNull Context context, @Nullable ViewGroup parent) {
        mView = createMVPView(context, parent);
        bindViewAndPresenter();
        return mView.getRoot();
    }

    @Override
    public void attachToContainer(@NonNull ContentContainer container) {
        super.attachToContainer(container);
        inject();
        initPresenter(container);
        bindViewAndPresenter();
    }

    @Override
    public void detachFromContainer() {
        super.detachFromContainer();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void destroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        mView = null;
    }

    private void initPresenter(@NonNull ContentContainer container) {
        /*ContentContainer.Persistable storage = null;
        if (container instanceof ContentContainer.Persistable) {
            storage = (ContentContainer.Persistable) container;
        }
        if (storage != null) {
            //noinspection unchecked
            mPresenter = (PRESENTER) storage.obtain(getPresenterId());
        }
        if (mPresenter == null) {
            mPresenter = createPresenter();
            if (storage != null) {
                storage.persist(getPresenterId(), mPresenter);
            }
        }*/
    }

    private void bindViewAndPresenter() {
        if (mView != null && mPresenter != null) {
            mPresenter.attachView(mView);
        }
    }

}
