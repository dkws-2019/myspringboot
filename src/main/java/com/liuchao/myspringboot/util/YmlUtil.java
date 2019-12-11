package com.liuchao.myspringboot.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class YmlUtil {

    public static Map<String,Object> load(String fileName){
        InputStream resourceAsStream = YmlUtil.class.getClassLoader().getResourceAsStream(fileName);
        Yaml yaml = new Yaml();
        Object result= yaml.load(resourceAsStream);
        return fileName==null ?null :(LinkedHashMap)result;
    }

    public static Object readValue(Map<String,Object> map,String key){
        if(map!=null && key!=null && !map.isEmpty()){
            if(!"".equals(key)){
                    if(key.contains(".")){
                        int i = key.indexOf(".");
                        String left = key.substring(0, i);
                        String reight = key.substring(i + 1);

                        return readValue((Map<String,Object>)map.get(left),reight);
                    }else if(map.containsKey(key)){
                       return map.get(key);
                    }else{
                        return null;
                    }
            }
        }

        return null;
    }
}
