package com.liuchao.myspringboot.util;

import com.liuchao.myspringboot.entity.Menu;
import com.liuchao.myspringboot.entity.Meta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseUtil {

    public static String RemoveUnderline(String str) {
        int index = 0;

        while (true) {
            index = str.indexOf("_", index);
            if (index < 0) {
                break;
            }
            String rep = String.valueOf(str.substring(index, index + 2).charAt(1));
            str = str.replace("_" + rep, rep.toUpperCase());


            index++;

        }
        return str;
    }
    
   public static List<Menu> MapToMenu(List<Map<String,String>> listMap){
        List<Menu> listmenu=new ArrayList<Menu>();
        for (Map<String,String> map:listMap){
            Set<String> set = map.keySet();
            Meta meta=new Meta();

            Menu menu=new Menu();
            menu.setMeta(meta);
            for(String key:set){
                System.out.println(key.startsWith("meta_"));
                if(key.startsWith("meta_")){
                    insertValue(meta,remoMeta(key),map.get(key));
                }else{
                    String s = BaseUtil.RemoveUnderline(key);
                    String s1 = map.get(key)instanceof String?map.get(key):String.valueOf(map.get(key));

                    insertValue(menu,s,s1);
                }
            }
            listmenu.add(menu);

        }

        return listmenu;

    }

    public static <T>void insertValue (T t,String fileName,String value){
        try {
            Class<?> cla = t.getClass();
            Field declaredField = cla.getDeclaredField(fileName);
            if(declaredField!=null){


            declaredField.setAccessible(true);

            Class<?> type = declaredField.getType();

            if(type==Integer.class){
                declaredField.set(t,Integer.valueOf(value));

            }else{
                declaredField.set(t,value);
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String remoMeta(String str){
        String substring = str.substring("meta_".length());
        return BaseUtil.RemoveUnderline(substring);

    }

    public static void main(String[] args) {
        System.out.println(remoMeta("meta_hide_in_bread"));
    }
}
