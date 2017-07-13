package com.greatbit.jdbc;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import net.sf.cglib.beans.BeanMap;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlBuilder {
    private static final String NAME_SPACE_S = ";([\\w.-]+);";
    private static final String SQL_S = "([^;]+)";

    public static final String TEMPLATE_SUFFIX = ".mustache";
    public static final String TEMPLATE_PATH_PREFIX = "/";

    private static final String QUESTION_MARK = "?";
    private static Pattern NS_PATTERN = Pattern.compile(NAME_SPACE_S + SQL_S);
    private static final MustacheFactory mf = new DefaultMustacheFactory();
    private static final Pattern ARG_PATTERN = Pattern.compile(":([\\w]+)");

    private static final ThreadLocal<SqlBuilder> builder = new ThreadLocal<>();

    private static volatile Map<String, String> sqlMap = new HashMap<>();

    public static SqlBuilder open() {
        SqlBuilder s = builder.get();
        if (s == null) {
            s = new SqlBuilder();
            builder.set(s);
        }
        return s;
    }

    public static void initResources(String... resourceNamePrefixes) {
        Arrays.stream(resourceNamePrefixes).forEach(n -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getClass().getResourceAsStream(TEMPLATE_PATH_PREFIX + n + TEMPLATE_SUFFIX)))) {
                StringBuffer buffer = new StringBuffer();
                reader.lines().forEach(buffer::append);

                Matcher m = NS_PATTERN.matcher(buffer);

                while (m.find()) {
                    sqlMap.put(n + "." + m.group(1).trim().toLowerCase(), m.group(2).trim().toLowerCase());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void initFiles(File... files) {

    }

    public static Set<String> keys() {
        return sqlMap.keySet();
    }

    public <T> SqlWrapper build(String key, T t) {
        SqlWrapper sw = new SqlWrapper();
        if (sqlMap.containsKey(key)) {
            Mustache mustache = mf.compile(new StringReader(sqlMap.get(key)), key);
            StringWriter writer = new StringWriter();
            try {
                BeanMap beanMap = BeanMap.create(t);
                mustache.execute(writer, t).flush();
                Matcher m = ARG_PATTERN.matcher(writer.toString());
                List<SqlParameter> paramList = new ArrayList<>();

                while (m.find()) {
                    paramList.add(new SqlParameter(m.group(1), beanMap.get(m.group(1))));
                }

                sw.setKey(key);
                sw.setSql(m.replaceAll(QUESTION_MARK).trim());
                sw.setParams(paramList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sw;
    }

    public <T> String log(String key, T t) {
        SqlWrapper wrapper = build(key, t);
        StringBuffer buffer = new StringBuffer();
        buffer.append("[").append(key).append("]");
        buffer.append(wrapper.getSql());
        List<SqlParameter> parameters = wrapper.getParams();

        for (int i = 0; i < parameters.size(); i++) {
            SqlParameter p = parameters.get(i);
            buffer.append(i + ":").append(p.getName()).append(":").append(p.getValue());
        }

        return buffer.toString();
    }
}
