package com.example;

import net.sf.cglib.beans.BeanMap;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
    Pattern p = Pattern.compile("(\\d{3,5})([a-z]{2})");
    Pattern p2 = Pattern.compile("\\[([\\w]+)]([^\\[]+)");

    //@Test
    public void test1() {
        User u = new User("hello", 11);
        BeanMap beanMap = BeanMap.create(u);
        beanMap.keySet().forEach(k -> {
            System.out.println(k + ":" + beanMap.get(k));
        });
        System.out.println("test");
        Matcher m = p.matcher("123aa-34345bb-234cc-00");
        while (m.find()) {
            System.out.println("m.group():" + m.group()); //打印所有

            System.out.println("m.group(1):" + m.group(1)); //打印数字的

            System.out.println("m.group(2):" + m.group(2)); //打印字母的
            System.out.println("#############################");
        }
    }

//    @Test
//    public void test2() {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/sql.mustache")))) {
//            StringBuffer buffer = new StringBuffer();
//            reader.lines().forEach(buffer::append);
//
//            Matcher m = p2.matcher(buffer);
//            Map<String, String> sqlMap = new HashMap<>();
//            while (m.find()) {
//                sqlMap.put(m.group(1).trim().toLowerCase(), m.group(2).trim().toLowerCase());
//            }
//            sqlMap.forEach((k, v) -> {
//                SqlWrapper s1 = SqlBuilder.currentSqlBuilder().buildSql(k, v, new User(12));
//                System.out.println("----------------------------------");
//                ptfn("%s:[%s]", s1.getKey(), s1.getSql());
//                System.out.println(Arrays.deepToString(s1.getParams().toArray()));
//                System.out.println("#########################");
//                SqlWrapper s2 = SqlBuilder.currentSqlBuilder().buildSql(k, v, new User("demo", 12));
//                ptfn("%s:[%s]", s2.getKey(), s2.getSql());
//                System.out.println(Arrays.deepToString(s2.getParams().toArray()));
//                System.out.println("----------------------------------");
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void ptfn(String fmt, Object... ts) {
        System.out.printf("%s:[%s]" + System.lineSeparator(), ts);
    }
}

