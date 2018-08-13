package com.acelost.universalcontent.fragmentbased.container;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.acelost.universalcontent.R;

public class PopupContainer extends DialogContainer {

    @Nullable
    private View mAnchor;

    public static PopupContainer withContent(@NonNull Fragment content) {
        PopupContainer container = new PopupContainer();
        container.setContent(content);
        return container;
    }

    public PopupContainer setAnchor(@NonNull View anchor) {
        mAnchor = anchor;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Отключаем заголовок
        setStyle(STYLE_NO_TITLE, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            // Отключаем затемнение фона
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            if (mAnchor != null) {
                // Размещаем диалоговое окно около вью-якоря
                int[] coordinates = new int[2];
                mAnchor.getLocationInWindow(coordinates);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.gravity = Gravity.START | Gravity.TOP;
                lp.x = coordinates[0];
                lp.y = coordinates[1];
                window.setAttributes(lp);
            }
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Создаем контейнер для отображения фрагмента с контентом
        return inflater.inflate(R.layout.simple_container, container, false);
    }

}
