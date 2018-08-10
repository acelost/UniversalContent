package com.acelost.universalcontent.fragmentbased.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acelost.universalcontent.R;
import com.acelost.universalcontent.contentbased.content.impl.ProfileContent;
import com.acelost.universalcontent.contentbased.core.ContentContainer;
import com.acelost.universalcontent.fragmentbased.container.properties.Appearing;
import com.acelost.universalcontent.fragmentbased.container.properties.Disappearing;
import com.acelost.universalcontent.fragmentbased.container.properties.HasTitle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ProfileContentFragment extends Fragment {

    private static final String LOADED_STATE_KEY = ProfileContent.class.getSimpleName() + ":LOADED_STATE_KEY";

    @Nullable
    private ViewGroup mRoot;

    private final SerialDisposable mLoadProfileDisposable = new SerialDisposable();

    private boolean mLoaded;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LOADED_STATE_KEY, mLoaded);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLoaded = savedInstanceState.getBoolean(LOADED_STATE_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = (ViewGroup) inflater.inflate(R.layout.content_profile, container, false);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HasTitle hasTitle = getContainer(HasTitle.class);
        if (hasTitle != null) {
            hasTitle.setContentTitle("Профиль сотрудника");
        }
        if (mLoaded) {
            showProfile();
        } else {
            if (isContainer(Appearing.class)) {
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
                                            Appearing appearing = getContainer(Appearing.class);
                                            if (appearing != null) {
                                                appearing.requestAppearance();
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
                    Disappearing disappearing = getContainer(Disappearing.class);
                    if (disappearing != null) {
                        disappearing.requestDisappearance();
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        Disposable disposable = mLoadProfileDisposable.get();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        if (mRoot != null) {
            mRoot.setOnClickListener(null);
            mRoot = null;
        }
        super.onDestroyView();
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
            if (!isContainer(ContentContainer.Appearing.class)) {
                Transition hideProgress = new Fade().setDuration(500).addTarget(progress);
                Transition showContent = new Fade().setDuration(350).addTarget(content);
                TransitionManager.beginDelayedTransition(mRoot, new TransitionSet().addTransition(hideProgress).addTransition(showContent));
            }
            content.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }
    }

    // region Utility methods for casting container

    @Nullable
    protected <T> T getContainer(@NonNull Class<? extends T> clazz) {
        Object parent = getParentFragment();
        if (parent == null) {
            // todo bad logic
            parent = getActivity();
        }
        if (parent != null) {
            if (clazz.isInstance(parent)) {
                return clazz.cast(parent);
            }
        }
        return null;
    }

    @NonNull
    protected <T> T requestContainer(@NonNull Class<? extends T> clazz) {
        T container = getContainer(clazz);
        if (container == null) {
            throw new RuntimeException("Failed attempt to request parent as " + clazz.getCanonicalName());
        }
        return container;
    }

    protected boolean isContainer(@NonNull Class clazz) {
        Object parent = getParentFragment();
        if (parent == null) {
            // todo bad logic
            parent = getActivity();
        }
        if (parent == null) {
            return false;
        }
        return clazz.isInstance(parent);
    }

    // endregion

}
