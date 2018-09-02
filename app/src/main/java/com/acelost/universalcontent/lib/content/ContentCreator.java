package com.acelost.universalcontent.lib.content;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Интерфейс создателя экземпляра контента. Используется контейнером для
 * создания экземпляра фрагмента, содержащего отображаемый контент.
 */
public interface ContentCreator extends Serializable {

    /**
     * Создать экземпляр фрагмента с контентом
     * для отображения внутри контейнера.
     * @return экземпляр фрагмента с контентом
     */
    @NonNull
    Fragment createFragment();

}
