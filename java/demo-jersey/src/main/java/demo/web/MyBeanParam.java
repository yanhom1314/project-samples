package demo.web;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public class MyBeanParam {

    @PathParam("id")
    private String id;
    @HeaderParam("header")
    private String headerParam;
    private String queryParam;

    public MyBeanParam(@QueryParam("q") String queryParam) {
        this.queryParam = queryParam;
    }

    public String getId() {
        return id;
    }

    public String getHeaderParam() {
        return headerParam;
    }

    public String getQueryParam() {
        return queryParam;
    }
}
