package com.liuchao.myspringboot.service;

import com.liuchao.myspringboot.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IUserService {
    @Select("select * from user where id=#{userId}")
    public User findByid(String userId);
    @Select("select * from user")
    public List<User> findAll();
}
