package com.acelost.universalcontent.lib.container.property;

/**
 * Интерфейс, декларирующий возможность указания момента
 * для отображения контента. Отображение контента инициируется
 * вызовом метода {@link #showContent()}.
 */
public interface Showable {

    /**
     * Отобразить контент внутри контнейнера.
     */
    void showContent();

}
