package com.acelost.universalcontent.lib.container.bottomsheet;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import com.acelost.universalcontent.lib.container.base.BaseContainerDialogFragment;
import com.acelost.universalcontent.lib.container.property.Closeable;
import com.acelost.universalcontent.lib.container.property.Showable;

/**
 * Реализация bottom sheet, представляющая собой контейнер для отображения контента.
 */
public class ContainerBottomSheet extends BaseContainerDialogFragment implements
        Showable, Closeable {

    /**
     * Ключ для хранения параметра о необходимости сразу отображать контент.
     */
    private static final String INSTANT_SHOW_CONTENT_ARG = ContainerBottomSheet.class.getSimpleName().concat(":INSTANT_SHOW_CONTENT_ARG");

    /**
     * Ключ для хранения состояния флага, отображен ли контент.
     */
    private static final String CONTENT_SHOWN_STATE_KEY = ContainerBottomSheet.class.getSimpleName().concat(":CONTENT_SHOWN_STATE_KEY");

    /**
     * По умолчанию контент отображается сразу.
     */
    private static final boolean DEFAULT_INSTANT_SHOW_CONTENT_VALUE = true;

    /**
     * Экземпляр {@link BottomSheetBehavior} для управления поведением bottom sheet.
     */
    private BottomSheetBehavior mBehavior;

    /**
     * Отображен ли контент на данный момент.
     */
    private boolean mContentShown;

    /**
     * Задать режим отображения контента: мгновенно после появления
     * панели на экране или по сигналу через интерфейс {@link Showable}.
     *
     * @param instant - режим отображения контента
     */
    public ContainerBottomSheet instant(boolean instant) {
        getOrCreateArguments().putBoolean(INSTANT_SHOW_CONTENT_ARG, instant);
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        if (context == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        // Указываем BottomSheetDialog в качестве диалогового окна
        return new BottomSheetDialog(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setOnShowListener(
                new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        onShowDialog(dialog, savedInstanceState);
                    }
                }
        );
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(CONTENT_SHOWN_STATE_KEY, mContentShown);
    }

    @Override
    public void dismiss() {
        mContentShown = false;
        super.dismiss();
    }

    /**
     * Обрабатываем отображение диалогового окна.
     *
     * @param dialog                - отображенное диалоговое окно
     * @param savedInstanceState    - сохраненное состояние фрагмента
     */
    protected void onShowDialog(DialogInterface dialog, @Nullable Bundle savedInstanceState) {
        if (!(dialog instanceof BottomSheetDialog)) {
            return;
        }
        View sheet = ((BottomSheetDialog) dialog).findViewById(android.support.design.R.id.design_bottom_sheet);
        if (sheet != null) {
            mBehavior = BottomSheetBehavior.from(sheet);
            if (isInstantShowContent()) {
                // Отображаем контент сразу
                showContent(true);
            } else if (savedInstanceState != null && savedInstanceState.getBoolean(CONTENT_SHOWN_STATE_KEY)) {
                // Контент уже был отображен ранее
                showContent(false);
            } else {
                mBehavior.setPeekHeight(0); // Мгновенное скрытие панели
            }
        }
    }

    // region Check configuration methods

    /**
     * Нужно ли отображать контент сразу.
     */
    protected final boolean isInstantShowContent() {
        Bundle args = getArguments();
        if (args != null) {
            return args.getBoolean(INSTANT_SHOW_CONTENT_ARG, DEFAULT_INSTANT_SHOW_CONTENT_VALUE);
        } else {
            return DEFAULT_INSTANT_SHOW_CONTENT_VALUE;
        }
    }

    // endregion

    // region Internal methods

    /**
     * Отобразить контент.
     */
    private void showContent(boolean animate) {
        if (mBehavior != null) {
            mContentShown = true;
            if (!animate) {
                mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO); // Мгновенное отображение панели
            }
            // Валидируем состояние behavior
            if (mBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            mBehavior.setSkipCollapsed(true); // Игнорируем состояние неполного отображения панели
        }
    }

    // endregion

    // region Showable property impl

    @Override
    public void showContent() {
        showContent(true);
    }

    // endregion

    // region Closeable property impl

    @Override
    public void closeContainer() {
        dismiss();
    }

    // endregion

}
