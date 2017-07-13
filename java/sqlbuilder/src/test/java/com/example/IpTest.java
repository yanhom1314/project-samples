package com.example;

import com.greatbit.ip.IpUtil;
import org.junit.Test;

import java.util.function.Consumer;

public class IpTest {
    IpUtil ipUtil = IpUtil.open();

    @Test
    public void testUtil() {


        System.out.println("255.255.255.255:" + ipUtil.ip2long("255.255.255.255"));
        System.out.println("255.255.255.255:" + ipUtil.ips2long("255.255.255.255"));



        netmask("117.8.0.0", "117.15.255.255");
        netmask("60.24.0.0", "60.27.255.255");

        time(10000000, t -> ipUtil.ip2long("255.255.255.255"));
        time(10000000, t -> ipUtil.ips2long("255.255.255.255"));
        time(10000000, t ->  netmask("60.24.0.0", "60.27.255.255"));
    }


    private void time(int count, Consumer t) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            t.accept(null);
        }

        long end = System.currentTimeMillis();
        System.out.printf("Time use [%d]ms.\n", end - start);
    }

    private void netmask(String ipStart, String ipEnd) {
        long v1 = ipUtil.ip2long(ipStart);
        long v2 = ipUtil.ip2long(ipEnd);

        int mask = (32 - Long.bitCount(v2 - v1));

        long key = v1 >> (32 - mask);
        //ipUtil.key("60.24.3.0", 14);

//        System.out.println(ipStart + ":" + v1 + ":" + Long.toBinaryString(v1));
//        System.out.println(ipEnd + ":" + v2 + ":" + Long.toBinaryString(v2));
//        System.out.println(Long.toBinaryString(v2 - v1));
//
//        System.out.println("mask:" + mask + " " + key + " key:" + Long.toBinaryString(key));
//
//        System.out.println(ipUtil.key("60.24.3.0", 14));
    }
}
