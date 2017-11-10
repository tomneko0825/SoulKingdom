package com.tomneko.soulkingdom.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * インジェクト対象のフィールドに付与するアノテーション
 *
 * Created by toyama on 2017/09/10.
 */
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
}
