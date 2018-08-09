package com.acelost.universalcontent.contentbased.container;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AlertDialogContainer extends DialogFragment implements ContentContainer {

    private static final int CONTENT_LOADER_ID = R.id.alert_dialog_container_content_loader_id;

    private Content mContent;

    @NonNull
    public static AlertDialogContainer withContent(@NonNull Content content) {
        AlertDialogContainer container = new AlertDialogContainer();
        container.mContent = content;
        return container;
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
        if (mContent == null) {
            if (BuildConfig.DEBUG) {
                throw new IllegalStateException("Content not specified.");
            } else {
                Log.e("AlertDialogContainer", "Content not specified.");
                dismiss();
                return;
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        if (context == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        View contentView = mContent.createView(context, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(contentView);
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mContent.attachToContainer(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContent.detachFromContainer();
        mContent.destroyView();
    }

}
