package com.acelost.universalcontent.contentbased.container;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.acelost.universalcontent.BuildConfig;
import com.acelost.universalcontent.R;
import com.acelost.universalcontent.contentbased.core.Content;
import com.acelost.universalcontent.contentbased.core.ContentContainer;
import com.acelost.universalcontent.utils.RetainInstanceHelper;
import com.acelost.universalcontent.utils.StorageProvider;

public abstract class ActivityContainer extends AppCompatActivity implements ContentContainer {

    private static final int CONTENT_LOADER_ID = R.id.activity_container_content_loader_id;

    private Content mContent;

    @NonNull
    protected abstract Content createContent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StorageProvider<Content> storage = new RetainInstanceHelper<>(this, getSupportLoaderManager());
        if (savedInstanceState == null) {
            mContent = createContent();
            storage.persist(CONTENT_LOADER_ID, mContent);
        } else {
            mContent = storage.obtain(CONTENT_LOADER_ID);
        }
        if (!checkHasContent()) {
            return;
        }
        View view = mContent.createView(this, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ((ViewGroup) findViewById(android.R.id.content)).addView(view);
        mContent.attachToContainer(this);
    }

    @Override
    protected void onDestroy() {
        mContent.detachFromContainer();
        mContent.destroyView();
        super.onDestroy();
    }

    private boolean checkHasContent() {
        if (mContent == null) {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("Content not specified");
            } else {
                Log.e("ActivityContainer", getClass().getSimpleName() + ": Content not specified.");
                finish();
            }
            return false;
        }
        return true;
    }

}
