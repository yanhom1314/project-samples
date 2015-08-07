package test;


import com.jfinal.kit.PathKit;
import org.junit.Test;

import java.net.URL;

public class PathTest {

    @Test
    public void testPath() {
        try {
            URL url = PathKit.class.getResource("/");

            System.out.println("url:" + url);
            if (url != null) {
                System.out.println("uri:" + url.toURI().getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
