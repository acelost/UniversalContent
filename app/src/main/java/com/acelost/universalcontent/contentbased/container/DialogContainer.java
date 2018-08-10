package com.acelost.universalcontent.contentbased.container;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

import com.acelost.universalcontent.BuildConfig;
import com.acelost.universalcontent.R;
import com.acelost.universalcontent.contentbased.core.Content;
import com.acelost.universalcontent.contentbased.core.ContentContainer;
import com.acelost.universalcontent.utils.RetainInstanceHelper;
import com.acelost.universalcontent.utils.StorageProvider;

public class DialogContainer extends AppCompatDialogFragment implements ContentContainer {

    private static final int CONTENT_LOADER_ID = R.id.alert_dialog_container_content_loader_id;

    private Content mContent;

    protected Content getContent() {
        return mContent;
    }

    protected void setContent(@Nullable Content content) {
        mContent = content;
    }

    @NonNull
    protected Bundle getOrCreateArgs() {
        Bundle args = getArguments();
        if (args == null) {
            args = new Bundle();
            setArguments(args);
        }
        return args;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        if (context == null) {
            dismiss();
            return;
        }
        StorageProvider<Content> storage = new RetainInstanceHelper<>(context, getLoaderManager());
        if (savedInstanceState == null) {
            storage.persist(CONTENT_LOADER_ID, mContent);
        } else {
            mContent = storage.obtain(CONTENT_LOADER_ID);
        }
        checkHasContent();
    }

    @Override
    public void onDestroyView() {
        mContent.detachFromContainer();
        mContent.destroyView();
        super.onDestroyView();
    }

    private void checkHasContent() {
        if (mContent == null) {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("Content not specified.");
            } else {
                Log.e("DialogContainer", getClass().getSimpleName() + ": Content not specified.");
                dismiss();
            }
        }
    }

}
