package demo;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

public class Organization implements Serializable {
    @QuerySqlField(index = true)
    private Long id;
    @QuerySqlField(index = true)
    private String name;

    public Organization(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
