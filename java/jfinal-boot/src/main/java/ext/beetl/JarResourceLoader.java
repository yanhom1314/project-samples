package ext.beetl;


import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;
import org.beetl.core.misc.BeetlUtil;

import java.util.Map;

public class JarResourceLoader implements ResourceLoader {
    String suffix = ".html";
    String root;
    String charset = "UTF-8";

    public Resource getResource(String key) {
        String path = this.suffix != null ? this.root + key + this.suffix : this.root + key;
        JarResource resource = new JarResource(key, path, this);
        return resource;
    }

    public void close() {
    }

    public boolean isModified(Resource key) {
        return false;
    }


    public void init(GroupTemplate gt) {
        Map resourceMap = gt.getConf().getResourceMap();

        resourceMap.keySet().forEach(k -> System.out.println("key:" + k + " " + resourceMap.get(k)));

        if (resourceMap.containsKey("root")) this.root = (String) resourceMap.get("root");
        if (resourceMap.containsKey("suffix")) this.suffix = (String) resourceMap.get("suffix");
        if (resourceMap.containsKey("charset")) this.charset = (String) resourceMap.get("charset");
    }


    public boolean exist(String key) {
        return Thread.currentThread().getContextClassLoader().getResource(key) != null;
    }


    public String getResourceId(Resource resource, String id) {
        return resource == null ? id : BeetlUtil.getRelPath(resource.getId(), id);
    }
}