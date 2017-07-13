package com.greatbit.sqlbuilder;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.PropertyAccessorFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlBuilder {
    public static final String QUESTION_MARK = "?";
    public static final MustacheFactory mf = new DefaultMustacheFactory();
    private static final ThreadLocal<SqlBuilder> builder = new ThreadLocal<>();

    public static final Pattern pattern = Pattern.compile(":(\\w+)");

    public static SqlBuilder currentSqlBuilder() {
        SqlBuilder s = builder.get();
        if (s == null) {
            s = new SqlBuilder();
            builder.set(s);
        }
        return s;
    }

    public <T> SqlWrapper buildSql(String key, String template, T t) {
        SqlWrapper sw = new SqlWrapper();
        Mustache mustache = mf.compile(new StringReader(template), key);
        StringWriter writer = new StringWriter();
        try {
//            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(t);
//            mustache.execute(writer, t).flush();
//            Matcher m = pattern.matcher(writer.toString());
//            List<SqlParam> para = new ArrayList<>();
//
//            while (m.find()) {
//                para.add(new SqlParam(m.group(1), wrapper.getPropertyValue(m.group(1))));
////            }
//
//            sw.setKey(key);
//            sw.setSql(m.replaceAll(QUESTION_MARK).trim());
//            sw.setParams(para);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw;
    }

    public <T> String buildSql(String key, String template, T... ts) {
        Mustache mustache = mf.compile(new StringReader(template), key);
        StringWriter writer = new StringWriter();
        try {
            mustache.execute(writer, ts).flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public <T> String buildSql(String key, String template, List<T> list) {
        Mustache mustache = mf.compile(new StringReader(template), key);
        StringWriter writer = new StringWriter();
        try {
            mustache.execute(writer, list).flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
