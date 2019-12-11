package com.liuchao.myspringboot.decorator;

/**
 * 装饰都类
 */
public class Shoes extends DecoratorWare {
    private Person person;
    public Shoes(Person person) {
        super(person);
        this.person=person;
    }

    @Override
    public String doWare() {
        return person.doWare()+"穿上了鞋子";
    }
}
