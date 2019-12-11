package com.liuchao.myspringboot.service.serviceImpl;

import com.liuchao.myspringboot.entity.Menu;
import com.liuchao.myspringboot.entity.Menus;
import com.liuchao.myspringboot.service.IMenuService;
import com.liuchao.myspringboot.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MenuAllServiceImpl {
    @Resource
    private IMenuService iMenuService;

    public  List<Menus> findAllMenu(int parentId){
        List<Map<String, String>> parentList = iMenuService.findByParentId(parentId);
        List<Menu> menusParent = BaseUtil.MapToMenu(parentList);
        List<Menus> listMenus=new ArrayList<Menus>();
        for(Menu menu:menusParent){
            Menus menus=new Menus();
            menus.setHref(menu.getHref());
            menus.setIcon(menu.getIcon());
            menus.setMeta(menu.getMeta());
            menus.setParentId(menu.getParentId());
            menus.setId(menu.getId());
            menus.setName(menu.getName());
            listMenus.add(menus);

        }

        for(Menus menus:listMenus){
            Integer parentId1 = menus.getId();
            List<Menus> childListMenus = findAllMenu(parentId1);
            if(childListMenus==null || childListMenus.size()==0){
                menus.setChildren(null);
            }else{
                menus.setChildren(childListMenus);

            }


        }

        return listMenus;
    }
}
