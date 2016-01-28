package demo;

import com.hazelcast.config.*;
import com.hazelcast.core.*;

import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class JavaGettingStarted {
    public static void main(String[] args) {
        Config cfg = new Config();
        final HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        Map<Integer, String> mapCustomers = instance.getMap("customers");
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");

        System.out.println("Customer with key 1: " + mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());

        Queue<String> queueCustomers = instance.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");
        System.out.println("First customer: " + queueCustomers.poll());
        System.out.println("Second customer: " + queueCustomers.peek());
        System.out.println("Queue size: " + queueCustomers.size());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    quit(new Scanner(System.in), instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void quit(Scanner scan, HazelcastInstance instance) {
                String line = scan.nextLine();
                if (line.trim().equalsIgnoreCase("quit")) {
                    instance.shutdown();
                    System.out.println("Shutdown...");
                } else {
                    System.out.println(line);
                    quit(scan, instance);
                }

            }
        }).start();
    }
}
