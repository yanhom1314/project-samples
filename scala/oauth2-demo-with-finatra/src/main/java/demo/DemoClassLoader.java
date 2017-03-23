package demo;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Vector;

public class DemoClassLoader {

    public static Vector<Class> loader() {
        Vector<Class> classes = null;
        try {
            Field f = ClassLoader.class.getDeclaredField("classes");
            f.setAccessible(true);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            classes = (Vector<Class>) f.get(classLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static void listLoadedClasses(ClassLoader byClassLoader) {
        Class clKlass = byClassLoader.getClass();
        System.out.println("Classloader: " + clKlass.getCanonicalName());
        while (clKlass != java.lang.ClassLoader.class) {
            clKlass = clKlass.getSuperclass();
        }
        try {
            java.lang.reflect.Field fldClasses = clKlass
                    .getDeclaredField("classes");
            fldClasses.setAccessible(true);
            Vector classes = (Vector) fldClasses.get(byClassLoader);
            for (Iterator iter = classes.iterator(); iter.hasNext(); ) {
                System.out.println("   Loaded " + iter.next());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
