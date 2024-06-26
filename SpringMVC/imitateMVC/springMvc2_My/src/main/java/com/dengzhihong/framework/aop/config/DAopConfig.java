package com.dengzhihong.framework.aop.config;

import lombok.Data;

@Data
public class DAopConfig {
    private String pointCut;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectClass;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;

}
