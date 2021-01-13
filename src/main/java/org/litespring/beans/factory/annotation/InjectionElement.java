package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.AutowiredCapableBeanFactory;

import java.lang.reflect.Member;

public abstract class InjectionElement {
    protected Member member;
    protected AutowiredCapableBeanFactory factory;

    InjectionElement(Member member, AutowiredCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}
