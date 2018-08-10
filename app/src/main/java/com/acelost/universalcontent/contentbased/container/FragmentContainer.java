package com.acelost.universalcontent.contentbased.container;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acelost.universalcontent.BuildConfig;
import com.acelost.universalcontent.R;
import com.acelost.universalcontent.contentbased.core.Content;
import com.acelost.universalcontent.contentbased.core.ContentContainer;
import com.acelost.universalcontent.utils.RetainInstanceHelper;
import com.acelost.universalcontent.utils.StorageProvider;

public class FragmentContainer extends Fragment implements ContentContainer {

    private static final int CONTENT_LOADER_ID = R.id.fragment_container_content_loader_id;

    private Content mContent;

    public static FragmentContainer withContent(@NonNull Content content) {
        FragmentContainer container = new FragmentContainer();
        container.mContent = content;
        return container;
    }

    protected Content getContent() {
        return mContent;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = getContext();
        if (context == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        StorageProvider<Content> storage = new RetainInstanceHelper<>(context, getLoaderManager());
        if (savedInstanceState == null) {
            storage.persist(CONTENT_LOADER_ID, mContent);
        } else {
            mContent = storage.obtain(CONTENT_LOADER_ID);
        }
        if (!checkHasContent()) {
            return null;
        }
        return mContent.createView(getContext(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContent.attachToContainer(this);
    }

    @Override
    public void onDestroyView() {
        mContent.detachFromContainer();
        mContent.destroyView();
        super.onDestroyView();
    }

    private boolean checkHasContent() {
        if (mContent == null) {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("Content not specified.");
            } else {
                Log.e("FragmentContainer", getClass().getSimpleName() + ": Content not specified.");
            }
            return false;
        }
        return true;
    }

}
