package com.liuchao.myspringboot.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IMenuService {

    @Select("select * from menu where parent_id=#{parentId}")
    public List<Map<String,String>> findByParentId(@Param("parentId")int parentId);
}
