package ext.beetl;

import org.beetl.core.ResourceLoader;
import org.beetl.ext.jfinal.BeetlRenderFactory;

public class DemoBeetlRenderFactory extends BeetlRenderFactory {
    public DemoBeetlRenderFactory(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public String getViewExtension() {
        return ".html";
    }
}
