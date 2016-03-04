package demo;

import message.Tip;

public class HelloMain {
    public static void main(String[] args) {
        Tip tip = new Tip("hello", System.currentTimeMillis());
        System.out.printf("tip name:%s time:%s\n", tip.name(), tip.time());
    }
}
