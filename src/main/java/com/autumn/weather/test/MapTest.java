package com.autumn.weather.test;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapTest {

    @Test
    void test1() {
        HashMap map = new HashMap();
        map.put("1","1");
        map.put("2","2");
        map.put("3","3");
        map.put("4","4");
        map.put("5","5");
        map.put("6","6");

        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String,String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            boolean b = iterator.hasNext();
            System.out.println(key+"-"+value+"-"+b);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("1\n");
        sb.append("");
        sb.append("2");
        System.out.println(sb);
    }
}
