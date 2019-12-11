package com.liuchao.myspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menus {
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Integer parentId;
    private String href;
    private String icon;
    private String name;
    private Meta meta;
    private List<Menus> children;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Integer getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getHref() {
        return href;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public Meta getMeta() {
        return meta;
    }

    public List<Menus> getChildren() {
        return children;
    }

    public void setChildren(List<Menus> children) {
        this.children = children;
    }
}
