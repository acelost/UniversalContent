package com.acelost.universalcontent.fragmentbased.container;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.acelost.universalcontent.R;

public class AlertDialogContainer extends DialogContainer {

    public static AlertDialogContainer withContent(@NonNull Fragment content) {
        AlertDialogContainer container = new AlertDialogContainer();
        container.setContent(content);
        return container;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Light_Dialog_Alert);
    }

}