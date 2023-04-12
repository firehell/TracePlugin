package com.zz.traceplugin;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by yiyonghao on 2020-08-20
 * Email: yiyonghao@bytedance.com
 */
@Target(METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ASM {
}
