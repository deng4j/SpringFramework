package com.dengzhihong.Anno;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DRequestParam {
    String value() default "";
}
