package com.liuchao.myspringboot.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;

public class YmlRead {

    private static Map<String,String> map=new HashMap<String,String>();
    private static String NAME="application.yml";
    private static YmlRead ymlRead;

    public static synchronized YmlRead getInstance(){
            if(null==ymlRead){
                return new YmlRead();
            }
            return ymlRead;
    }

    public static String getValue(String key){
        String value = map.get(key);
        if(null==value){
            Map<String, Object> load = YmlUtil.load(NAME);
            System.out.println(load);
            Object o = YmlUtil.readValue(load, key);
            return String.valueOf(o);
        }
        return value;
    }
}
