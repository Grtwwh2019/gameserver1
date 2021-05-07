package com.grtwwh.gameserver.base.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事件接受注解
 *
 * @author Grtwwh2019
 * @version 1.0
 * @since 2021/4/26 15:23
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface EventReceiver {
}
