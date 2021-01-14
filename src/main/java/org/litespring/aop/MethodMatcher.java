package org.litespring.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {

    /**
     * spring中还有个参数Class<?> targetClass
     * @param method
     * @return
     */
    boolean matches(Method method);
}
