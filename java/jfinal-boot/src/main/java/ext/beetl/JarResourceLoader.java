package ext.beetl;


import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;
import org.beetl.core.misc.BeetlUtil;

import java.util.Map;

public class JarResourceLoader implements ResourceLoader {
    String root;
    boolean autoCheck;
    protected String charset;
    String functionRoot;
    String functionSuffix;
    ClassLoader classLoader;


    public Resource getResource(String key) {
        JarResource resource = new JarResource(key, this.root + key, this);
        return resource;
    }

    public void close() {
    }

    public boolean isModified(Resource key) {
        return this.autoCheck ? key.isModified() : false;
    }


    public void init(GroupTemplate gt) {
        Map resourceMap = gt.getConf().getResourceMap();

        if (this.root == null && resourceMap.containsKey("root"))
            this.root = (String) resourceMap.get("root");

        if (this.charset == null && resourceMap.containsKey("charset"))
            this.charset = (String) resourceMap.get("charset");

        this.functionSuffix = (String) resourceMap.get("functionSuffix");
        this.autoCheck = Boolean.parseBoolean((String) resourceMap.get("autoCheck"));
        this.functionRoot = (String) resourceMap.get("functionRoot");
    }


    public boolean exist(String key) {
        return this.classLoader.getClass().getResource(key) != null;
    }


    public String getResourceId(Resource resource, String id) {
        return resource == null ? id : BeetlUtil.getRelPath(resource.getId(), id);
    }
}