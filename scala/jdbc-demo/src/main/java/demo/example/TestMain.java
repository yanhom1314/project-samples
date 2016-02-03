package demo.example;

import demo.db.Person;
import demo.proxy.HelloService;
import demo.proxy.HelloServiceImpl;
import demo.proxy.HelloWorldHandler;
import io.sinq.annotation.Column;
import io.sinq.annotation.Table;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class TestMain {
    public static void main(String[] margs) {
        System.out.println("Hello World!");
        Class<Person> personClass = Person.class;

        if (personClass.isAnnotationPresent(Table.class)) {
            Table table = personClass.getAnnotation(Table.class);
            System.out.println("name:" + table.name());

            Arrays.asList(personClass.getDeclaredFields()).forEach(f -> {
                if (f.isAnnotationPresent(Column.class)) {
                    Column column = f.getAnnotation(Column.class);
                    System.out.println("field:" + f.getName() + " column:" + column.name());
                } else {
                    System.out.println("field:" + f.getName() + " not found annotation.");
                }
            });
        } else {
            System.err.println("Person::Class is not annotation present Table::class");
        }

        time(() -> {
            for (int i = 0; i < 500000; i++) {
                HelloService helloService = new HelloServiceImpl();
                helloService.sayHello("Nice Local");
            }
        });

        //java dynmic proxy
        jdkProxy();
        //cglib
        cglibProxy();
    }


    public static void time(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println("Time use " + (end - start) + "ms.");
    }

    public static void jdkProxy() {
        try {
            HelloService helloService = new HelloServiceImpl();
            InvocationHandler handler = new HelloWorldHandler(helloService);

            time(() -> {
                for (int i = 0; i < 500000; i++) {
                    HelloService proxy = (HelloService) Proxy.newProxyInstance(helloService.getClass().getClassLoader(), helloService.getClass().getInterfaces(), handler);
                    proxy.sayHello("Nice");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cglibProxy() {
        final HelloService t1 = new HelloServiceImpl();
        Enhancer en = new Enhancer();
        en.setSuperclass(HelloServiceImpl.class);
        MethodInterceptor mi = (target, method, args, proxy) -> {
            Object o = method.invoke(t1, args);
            return o;
        };
        en.setCallback(mi);

        time(() -> {
            for (int i = 0; i < 500000; i++) {
                HelloService t = (HelloService) en.create();
                t.sayHello("Nice1");
            }
        });
    }
}
