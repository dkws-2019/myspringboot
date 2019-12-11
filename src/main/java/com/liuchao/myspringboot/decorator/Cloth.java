package com.liuchao.myspringboot.decorator;

public class Cloth extends DecoratorWare{
    private  Person person;
    public Cloth(Person person) {
        super(person);
        this.person=person;
    }

    @Override
    public String doWare() {
        return person.doWare()+"穿上了衣服";
    }
}
