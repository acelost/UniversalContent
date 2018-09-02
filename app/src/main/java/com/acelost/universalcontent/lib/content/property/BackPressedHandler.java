package com.acelost.universalcontent.lib.content.property;

/**
 * Интерфейс, указывающий на возможность использования объекта
 * в качестве обработчика нажатия на кнопку "назад".
 */
public interface BackPressedHandler {

    /**
     * Обработать нажатие на кнопку "назад".
     */
    boolean onBackPressed();

}
