package com.example;


import java.util.Collections;
import java.util.List;

public class SqlWrapper {
    private String key;
    private String sql;
    private List<SqlParam> params = Collections.emptyList();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<SqlParam> getParams() {
        return params;
    }

    public void setParams(List<SqlParam> params) {
        this.params = params;
    }
}