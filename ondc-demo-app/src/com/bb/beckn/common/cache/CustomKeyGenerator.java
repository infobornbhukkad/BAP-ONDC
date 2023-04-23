// 
// Decompiled by Procyon v0.5.36
// 

package com.bb.beckn.common.cache;

import org.springframework.util.StringUtils;
import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;

public class CustomKeyGenerator implements KeyGenerator
{
    public Object generate(final Object target, final Method method, final Object... params) {
        return target.getClass().getSimpleName() + "_" + method.getName() + "_" + StringUtils.arrayToDelimitedString(params, "_");
    }
}
