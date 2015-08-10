package ext.beetl;


import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class JarResource extends Resource {
    private String path;

    public JarResource(String key, String path, ResourceLoader resourceLoader) {
        super(key, resourceLoader);
        this.path = path;
    }

    public Reader openReader() {
        try {
            return resourceLoader.getClass().getResource(this.path) != null ?
                    new BufferedReader(new InputStreamReader(this.resourceLoader.getClass().getResourceAsStream(this.path), ((JarResourceLoader) resourceLoader).charset)) : null;
        } catch (Exception e) {
            System.out.println("path: " + this.path);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isModified() {
        return false;
    }
}
