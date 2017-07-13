package com.greatbit.ip;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IpUtil {
    public static String IPV4_STR = "";

    private static ThreadLocal<IpUtil> threadLocal = new ThreadLocal<>();


    private IpUtil() {
    }

    public static IpUtil open() {
        IpUtil util = threadLocal.get();
        if (util == null) {
            util = new IpUtil();
            threadLocal.set(util);
        }
        return util;
    }


    public long ip2long(String ipAddr) {
        long[] ips = new long[4];

        int p1 = ipAddr.indexOf(".");
        int p2 = ipAddr.indexOf(".", p1 + 1);
        int p3 = ipAddr.indexOf(".", p2 + 1);

        ips[0] = Long.parseLong(ipAddr.substring(0, p1));
        ips[1] = Long.parseLong(ipAddr.substring(p1 + 1, p2));
        ips[2] = Long.parseLong(ipAddr.substring(p2 + 1, p3));
        ips[3] = Long.parseLong(ipAddr.substring(p3 + 1));
        return (ips[0] << 24) + (ips[1] << 16) + (ips[2] << 8) + ips[3];//must quote
    }

    @Deprecated
    public long ips2long(String ipAddr) {
        String[] ips = ipAddr.split("\\.");
        return (Long.parseLong(ips[0]) << 24) + (Long.parseLong(ips[1]) << 16) + (Long.parseLong(ips[2]) << 8) + Long.parseLong(ips[3]);//must quote
    }


    public Map<Integer, Map<Long, String>> store(String... ipStrs) {
        Map<Integer, Map<Long, String>> map = new HashMap<>();
        Arrays.stream(ipStrs).forEach(t -> {

        });
        return map;
    }

    public String key(long ip, int mask) {
        StringBuilder builder = new StringBuilder();
        builder.append(mask).append(ip);
        return builder.toString();
    }

    public String key(String ipAddr, int mask) {
        long ip = ip2long(ipAddr);
        StringBuilder builder = new StringBuilder();
        builder.append(mask).append(ip >> (32 - mask));
        return builder.toString();
    }
}
