package com.liuchao.myspringboot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liuchao.myspringboot.util.BaseUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {
    public static void main(String[] args) {
        Meta meta=new Meta();
        try {
            Enumeration<URL> resources = meta.getClass().getClassLoader().getResources("com/liuchao/myspringboot/entity");
            URL resource = meta.getClass().getClassLoader().getResource("com");
            System.out.println(resource.getPath()+"  "+resource.getProtocol());
            while (resources.hasMoreElements()){
                URL url = resources.nextElement();
                System.out.println(url.getPath()+"   "+url.getProtocol());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        private String href;
        private String icon;
        private String title;
        private String name;
        private String hideInBread;

    public String getHref() {
        return href;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getHideInBread() {
        return hideInBread;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHideInBread(String hideInBread) {
        this.hideInBread = hideInBread;
    }
}
