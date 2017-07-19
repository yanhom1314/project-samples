package com.example;

import com.greatbit.ip.IpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class IpTest {
    IpUtil ipUtil = IpUtil.open();

    int count = 10000000;

    @Test
    public void testIP() {
        String ip_s = "192.168.10.0";
        String ip_e = "192.168.10.255";

        System.out.println("mask:" + ipUtil.netmask(ip_s, ip_e));
        Map<Integer, Set<Long>> map = new HashMap<>();

        ipUtil.store(map, "192.168.10.0-192.168.10.255", "117.8.0.0-117.15.255.255", "125.39.34.0/23", "61.136.57.0/24");
        map.forEach((k, v) -> {
            System.out.println("key:" + k);
            System.out.println(v);
            System.out.println("#################################");
        });

        System.out.println(ipUtil.in("192.168.10.1", map));
        System.out.println(ipUtil.in("192.168.10.2", map));
        System.out.println(ipUtil.in("61.136.57.0", map));
        System.out.println(ipUtil.in("61.136.56.0", map));
        System.out.println(ipUtil.in("192.168.9.255", map));



        Set<String> set = new HashSet<>();
        map.forEach((k, v) -> {
            v.forEach(vv -> set.add(k + "." + vv));
        });

        System.out.println("################################");
        set.forEach(System.out::println);
        System.out.println("################################");
        System.out.println(ipUtil.in("192.168.10.1", set,map.keySet()));
        System.out.println(ipUtil.in("192.168.10.2", set,map.keySet()));
        System.out.println(ipUtil.in("61.136.57.0", set,map.keySet()));
        System.out.println(ipUtil.in("61.136.56.0", set,map.keySet()));
        System.out.println(ipUtil.in("192.168.9.255", set,map.keySet()));
        System.out.println("################################");

        //time(count, t -> map.containsKey(23));
        time(count, t -> ipUtil.in("192.168.9.255", map));
        time(count, t -> ipUtil.in("192.168.9.255", set,map.keySet()));
    }

    //@Test
    public void testUtil() {
        System.out.println("255.255.255.255:" + ipUtil.ip2long("255.255.255.255"));

        String ipStart_1 = "117.8.0.0";
        String ipEnd_1 = "117.15.255.255";
        long v1 = ipUtil.ip2long(ipStart_1);
        long v2 = ipUtil.ip2long(ipEnd_1);
        int m1 = netmask(ipStart_1, ipEnd_1);

        System.out.println(ipStart_1 + " ip2long:" + v1 + " long2ip:" + ipUtil.long2ip(v1) + " binary:" + Long.toBinaryString(v1));
        System.out.println(ipEnd_1 + " ip2long:" + v2 + " long2ip:" + ipUtil.long2ip(v2) + " binary:" + Long.toBinaryString(v2));
        System.out.println("mask:" + m1 + " " + v1 + " key:" + ipUtil.key(v1, m1));

        time(count, t -> ipUtil.long2ip(v1));
        time(count, t -> ipUtil.ip2long("255.255.255.255"));
        time(count, t -> netmask("60.24.0.0", "60.27.255.255"));
    }

    private void time(int count, Consumer t) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            t.accept(null);
        }

        long end = System.currentTimeMillis();
        System.out.printf("Time use [%d]ms.\n", end - start);
    }

    private int netmask(String ipStart, String ipEnd) {
        long v1 = ipUtil.ip2long(ipStart);
        long v2 = ipUtil.ip2long(ipEnd);

        return (32 - Long.bitCount(v2 - v1));
    }
}
