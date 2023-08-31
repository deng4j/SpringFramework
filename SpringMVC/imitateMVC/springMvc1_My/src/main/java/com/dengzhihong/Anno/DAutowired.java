package com.dengzhihong.Anno;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DAutowired {
    String value() default "";
}
