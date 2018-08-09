package com.acelost.universalcontent.contentbased.container;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acelost.universalcontent.contentbased.core.Content;
import com.acelost.universalcontent.contentbased.core.ContentContainer;

public class AlertDialogContainer extends DialogFragment implements ContentContainer {

    private Content mContent;

    @NonNull
    public static AlertDialogContainer withContent(@NonNull Content content) {
        AlertDialogContainer container = new AlertDialogContainer();
        container.mContent = content;
        return container;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        View contentView = mContent.createView(getContext(), null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
