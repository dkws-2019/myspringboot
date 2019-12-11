package com.liuchao.myspringboot.decorator;

public class DecoratorTest {
    public static void main(String[] args) {
        Person person=new WuShi();
        Shoes shoes=new Shoes(person);
        Cloth cloth=new Cloth(shoes);
        System.out.println(cloth.doWare());
    }
}
