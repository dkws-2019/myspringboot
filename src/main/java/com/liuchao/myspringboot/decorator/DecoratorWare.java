package com.liuchao.myspringboot.decorator;

/**
 * 装饰者抽像类
 */
public abstract class DecoratorWare implements Person{
    private Person person;

    public DecoratorWare(Person person){
        this.person=person;
    }

    @Override
    public String doWare() {
        return person.doWare();
    }
}
