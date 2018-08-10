package com.acelost.universalcontent.contentbased.container;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acelost.universalcontent.contentbased.core.Content;

public class AlertDialogContainer extends DialogContainer {

    @NonNull
    public static AlertDialogContainer withContent(@NonNull Content content) {
        AlertDialogContainer container = new AlertDialogContainer();
        container.setContent(content);
        return container;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        if (context == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        View contentView = getContent().createView(context, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(contentView);
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getContent().attachToContainer(this);
        return view;
    }

}
