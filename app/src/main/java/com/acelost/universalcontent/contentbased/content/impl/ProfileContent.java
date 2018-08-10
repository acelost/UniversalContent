package com.acelost.universalcontent.contentbased.content.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acelost.universalcontent.R;
import com.acelost.universalcontent.contentbased.content.base.BaseContent;
import com.acelost.universalcontent.contentbased.core.ContentContainer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ProfileContent extends BaseContent {

    private static final Boolean ENABLE_LOGGING = true;

    @Nullable
    private ViewGroup mRoot;

    @NonNull
    private final SerialDisposable mLoadProfileDisposable = new SerialDisposable();

    private boolean mLoaded;

    @NonNull
    @Override
    public String getContentId() {
        return this.getClass().getSimpleName();
    }

    @NonNull
    @Override
    public View createView(@NonNull Context context, @Nullable ViewGroup parent) {
        if (ENABLE_LOGGING) {
            log("lifecycle -> createView");
        }
        mRoot = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.content_profile, parent);
        final TextView name = mRoot.findViewById(R.id.name);
        mRoot.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = name.getText();
                if (!TextUtils.isEmpty(text)) {
                    name.setText(text.subSequence(0, text.length()-1));
                }
            }
        });
        return mRoot;
    }

    @Override
    public void destroyView() {
        if (ENABLE_LOGGING) {
            log("lifecycle -> destroyView");
        }
        mRoot = null;
    }

    @Override
    public void attachToContainer(@NonNull ContentContainer container) {
        if (ENABLE_LOGGING) {
            log("lifecycle -> attachToContainer");
        }
        super.attachToContainer(container);
        ContentContainer.HasTitle hasTitle = getContainer(ContentContainer.HasTitle.class);
        if (hasTitle != null) {
            hasTitle.setContentTitle("Профиль сотрудника");
        }
        if (mLoaded) {
            showProfile();
        } else {
            if (isContainer(ContentContainer.Appearing.class)) {
                hideView();
            } else {
                showLoading();
            }
            mLoadProfileDisposable.set(
                    Completable.timer(500, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Action() {
                                        @Override
                                        public void run() throws Exception {
                                            mLoaded = true;
                                            showProfile();
                                            ContentContainer.Appearing appearing = getContainer(ContentContainer.Appearing.class);
                                            if (appearing != null) {
                                                appearing.requestAppearance(ProfileContent.this);
                                            }
                                        }
                                    }
                            )
            );
        }
        if (mRoot != null) {
            mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentContainer.Disappearing disappearing = getContainer(ContentContainer.Disappearing.class);
                    if (disappearing != null) {
                        disappearing.requestDisappearance(ProfileContent.this);
                    }
                }
            });
        }
    }

    @Override
    public void detachFromContainer() {
        if (ENABLE_LOGGING) {
            log("lifecycle -> detachFromContainer");
        }
        Disposable disposable = mLoadProfileDisposable.get();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (mRoot != null) {
            mRoot.setOnClickListener(null);
        }
        super.detachFromContainer();
    }

    private void hideView() {
        if (mRoot != null) {
            View content = mRoot.findViewById(R.id.profile_content);
            if (content != null) {
                content.setVisibility(View.GONE);
            }
            View progress = mRoot.findViewById(R.id.progress_bar);
            if (progress != null) {
                progress.setVisibility(View.GONE);
            }
        }
    }

    private void showLoading() {
        if (mRoot != null) {
            View content = mRoot.findViewById(R.id.profile_content);
            if (content != null) {
                content.setVisibility(View.GONE);
            }
            View progress = mRoot.findViewById(R.id.progress_bar);
            if (progress != null) {
                progress.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showProfile() {
        if (mRoot != null) {
            View content = mRoot.findViewById(R.id.profile_content);
            View progress = mRoot.findViewById(R.id.progress_bar);
            if (!isContainer(ContentContainer.Appearing.class)) { // todo возможно это конфигурация
                Transition hideProgress = new Fade().setDuration(500).addTarget(progress);
                Transition showContent = new Fade().setDuration(350).addTarget(content);
                TransitionManager.beginDelayedTransition(mRoot, new TransitionSet().addTransition(hideProgress).addTransition(showContent));
            }
            content.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }
    }

    private void log(Object message) {
        Log.e("ProfileContent", this.toString() + ": " + message.toString());
    }

}
