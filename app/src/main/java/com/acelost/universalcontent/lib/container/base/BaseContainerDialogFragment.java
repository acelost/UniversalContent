package com.acelost.universalcontent.lib.container.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acelost.universalcontent.R;
import com.acelost.universalcontent.lib.content.ContentCreator;

/**
 * Базовая реализация контейнера, основанного на {@link android.support.v4.app.DialogFragment}.
 */
public class BaseContainerDialogFragment extends AppCompatDialogFragment {

    /**
     * Ключ для хранения создателя контента в аргументах фрагмента-контейнера.
     */
    private static final String CONTENT_CREATOR_ARG = BaseContainerDialogFragment.class.getSimpleName().concat(":CONTENT_CREATOR_ARG");

    /**
     * Получить идентификатор макета для контейнера.
     * В макете должна присутствовать вью с идентификатором,
     * указанном в методе {@link #getContainerViewId()}.
     */
    @LayoutRes
    protected int getContainerLayoutRes() {
        return R.layout.simple_container;
    }

    /**
     * Получить идентификатор вью, выступающей в качестве
     * контейнера для контента. Вью с данным идентификатором
     * должна присутствовать в макете, указанном в методе
     * {@link #getContainerLayoutRes()}.
     */
    protected int getContainerViewId() {
        return R.id.container_view;
    }

    /**
     * Получить фрагмент с содержимым.
     *
     * @return фрагмент с содержимым, или <code>null</code> если фрагмент еще не создан
     */
    @Nullable
    protected final Fragment getContentFragment() {
        return getChildFragmentManager().findFragmentById(getContainerViewId());
    }

    /**
     * Получить фрагмент с контентом, приведенный к указанному типу.
     *
     * @param type  - тип, к которому необходимо привести контнет
     * @param <T>   - тип, к которому необходимо привести контент
     * @return экземпляр типа {@link T} или <code>null</code>, если фрагмент с контентом
     * не принадлежит данному типу
     */
    protected final <T> T getContentFragment(@NonNull Class<? extends T> type) {
        Fragment container = getContentFragment();
        if (type.isInstance(container)) {
            return type.cast(container);
        }
        return null;
    }

    /**
     * Задать экземпляр создателя контента. Этот метод необходимо вызывать
     * на новом экземпляре контейнера ДО попадания фрагмента в {@link android.support.v4.app.FragmentManager}.
     *
     * @param creator - создатель контента
     */
    @NonNull
    public BaseContainerDialogFragment setContentCreator(@NonNull ContentCreator creator) {
        Bundle arguments = getOrCreateArguments();
        arguments.putSerializable(CONTENT_CREATOR_ARG, creator);
        return this;
    }

    @SuppressLint("InflateParams")
    @CallSuper
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Создаем простой контейнер с FrameLayout для размещения внутри него контента
        return LayoutInflater.from(getContext()).inflate(getContainerLayoutRes(), null);
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Fragment contentFragment = getContentFragment();
        if (contentFragment == null) {
            // Создаем новый экземпляр фрагмента с контентом
            contentFragment = getContentCreator().createFragment();
        }
        // Вставляем фрагмент с контентом во вью-контейнер
        getChildFragmentManager().beginTransaction()
                .replace(getContainerViewId(), contentFragment)
                .commit();
    }

    /**
     * Получить экземпляр создателя контента.
     * @return экземпляр создателя контента
     */
    protected final ContentCreator getContentCreator() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalStateException("Attempt to get content creator while arguments is not defined.");
        }
        return (ContentCreator) arguments.getSerializable(CONTENT_CREATOR_ARG);
    }

    // region Utility methods

    /**
     * Получить аргументы фрагмента или создать новый Bundle для них.
     */
    @NonNull
    protected final Bundle getOrCreateArguments() {
        Bundle args = getArguments();
        if (args == null) {
            args = new Bundle();
            setArguments(args);
        }
        return args;
    }

    // endregion

}
