package com.acelost.universalcontent.lib.container.property;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Интерфейс, декларирующий возможность обработки какого-либо действия с контентом.
 */
public interface ContentActionHandler {

    /**
     * Обработать действие с контентом.
     *
     * @param contentId     - идентификатор контента
     * @param actionCode    - код действия
     * @param data          - данные по действию
     */
    void onContentAction(@NonNull String contentId, int actionCode, @NonNull Bundle data);

    /**
     * Вспомогательные класс для реализации обработки действий.
     */
    class Helper {

        /**
         * Получить обработчик действий с контентом.
         *
         * @param content - фрагмент с контентом
         * @return контейнер, приведенный к типа {@link ContentActionHandler}
         * либо null, если не найден контейнер, реализующий данный тип.
         */
        @Nullable
        public static ContentActionHandler getActionHandler(@NonNull Fragment content) {
            Object parent = content.getTargetFragment();
            if (parent instanceof ContentActionHandler) {
                return (ContentActionHandler) parent;
            }
            parent = content.getParentFragment();
            if (parent instanceof ContentActionHandler) {
                return (ContentActionHandler) parent;
            }
            parent = content.getActivity();
            if (parent instanceof ContentActionHandler) {
                return (ContentActionHandler) parent;
            }
            return null;
        }
    }

}
