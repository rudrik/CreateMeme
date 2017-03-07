package com.apptechx.lib.util;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface VisibleForTesting {}