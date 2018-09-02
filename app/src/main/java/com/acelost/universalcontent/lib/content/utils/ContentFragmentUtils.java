package com.acelost.universalcontent.lib.content.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Вспомогательные методы для реализации фрагментов с контентом и их взаимодействия с контейнерами.
 */
public class ContentFragmentUtils {

    /**
     * Получение контейнера контента, приведенного к указанному типу.
     *
     * @param content   - фрагмент с конетнтом
     * @param type      - тип, к которому необходимо привести фрагмент
     * @param <T>       - тип, к которому необходимо привести фрагмент
     * @return контейнер, приведенный к нужному типу либо null
     */
    @Nullable
    public static <T> T getContainer(@NonNull Fragment content, @NonNull Class<? extends T> type) {
        Object parent = content.getParentFragment();
        if (parent == null) {
            parent = content.getActivity();
        }
        if (parent != null) {
            if (type.isInstance(parent)) {
                return type.cast(parent);
            }
        }
        return null;
    }

    /**
     * Получение контейнера, приведенного к указанному типу. В случае,
     * если родитель не имплементирует указанный тип - будет выброшено
     * исключение {@link IllegalStateException}.
     *
     * @param content   - фрагмент с контентом
     * @param type      - тип, к которому необходимо привести фрагмент
     * @param <T>       - тип, к которому необходимо привести фрагмент
     * @return контейнер, приведенный к нужному типу
     */
    public static <T> T requestContainer(@NonNull Fragment content, @NonNull Class<? extends T> type) {
        T container = getContainer(content, type);
        if (container == null) {
            throw new IllegalStateException("Failed attempt to request parent as " + type.getCanonicalName());
        }
        return container;
    }

    /**
     * Проверить контейнер на принадлежность указанному типу.
     *
     * @param content   - фрагмент с контентом
     * @param type      - тип, к которому необходимо проверить принадлежность
     * @return true если контейнер принадлежит указанному типу, false - иначе
     */
    public static boolean isContainer(@NonNull Fragment content, @NonNull Class type) {
        return getContainer(content, type) != null;
    }

}
