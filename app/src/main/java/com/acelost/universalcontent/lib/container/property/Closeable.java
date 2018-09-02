package com.acelost.universalcontent.lib.container.property;

/**
 * Интерфейс, декларирующий возможность закрытия контейнера.
 * Закрытие конейнера инициируется вызовом метода {@link #closeContainer()}.
 */
public interface Closeable {

    /**
     * Закрыть контейнер с контентом.
     */
    void closeContainer();

}
