package com.acelost.universalcontent.contentbased.container;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acelost.universalcontent.contentbased.core.Content;
import com.acelost.universalcontent.contentbased.core.ContentContainer;

public class BottomSheetContainer extends DialogContainer implements ContentContainer.Appearing {

    private static final String INSTANT_ARG = BottomSheetContainer.class.getSimpleName() + ":INSTANT_ARG";
    private static final String SHOWN_STATE_KEY = BottomSheetContainer.class.getSimpleName() + ":SHOWN_STATE_KEY";
    private static final boolean DEFAULT_INSTANT_VALUE = true;

    private BottomSheetBehavior mBehavior;

    private boolean mShown;

    @NonNull
    public static BottomSheetContainer withContent(@NonNull Content content) {
        BottomSheetContainer container = new BottomSheetContainer();
        container.setContent(content);
        return container;
    }

    public BottomSheetContainer instant(boolean instant) {
        getOrCreateArgs().putBoolean(INSTANT_ARG, instant);
        return this;
    }

    protected boolean isInstant() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getBoolean(INSTANT_ARG, DEFAULT_INSTANT_VALUE);
        } else {
            return DEFAULT_INSTANT_VALUE;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        if (context == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = getContext();
        if (context == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return getContent().createView(context, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getContent().attachToContainer(this);
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (dialog instanceof BottomSheetDialog) {
                    View sheet = ((BottomSheetDialog) dialog).findViewById(android.support.design.R.id.design_bottom_sheet);
                    if (sheet != null) {
                        mBehavior = BottomSheetBehavior.from(sheet);
                        if (isInstant()) {
                            showContent();
                        } else {
                            if (savedInstanceState != null && savedInstanceState.getBoolean(SHOWN_STATE_KEY)) {
                                mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
                                showContent();
                            } else {
                                mBehavior.setPeekHeight(0);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOWN_STATE_KEY, mShown);
    }

    @Override
    public void requestAppearance(@NonNull Content requesting) {
        showContent();
    }

    private void showContent() {
        if (mBehavior != null) {
            mShown = true;
            if (mBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            mBehavior.setSkipCollapsed(true);
        }
    }

    @Override
    public void dismiss() {
        mShown = false;
        super.dismiss();
    }

}
