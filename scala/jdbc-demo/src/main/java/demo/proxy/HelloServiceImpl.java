package demo.proxy;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        String result = "Hello " + name + " ,welcome to Proxy World!";
        //System.out.println(result);
        return result;
    }
}
