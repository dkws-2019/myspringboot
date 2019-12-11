package com.liuchao.myspringboot.controller;

import com.liuchao.myspringboot.entity.Menu;
import com.liuchao.myspringboot.entity.Menus;
import com.liuchao.myspringboot.service.IMenuService;
import com.liuchao.myspringboot.service.serviceImpl.MenuAllServiceImpl;
import com.liuchao.myspringboot.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("/menu")
@Controller
public class MenuController {
    @Autowired
    IMenuService iMenuService;

    @Autowired
    MenuAllServiceImpl menuAllService;

    @RequestMapping("/findAllmenu")
    @ResponseBody
    public List<Menus> findMenu(){
        List<Menus> allMenu = menuAllService.findAllMenu(0);
        return allMenu;
    }

}
