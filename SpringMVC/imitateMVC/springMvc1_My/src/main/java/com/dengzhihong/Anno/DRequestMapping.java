package com.dengzhihong.Anno;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DRequestMapping {
    String value() default "";
}
