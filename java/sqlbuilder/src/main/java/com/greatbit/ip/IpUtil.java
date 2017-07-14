package com.greatbit.ip;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {
    public static String IPV4_STR = "([\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3})";
    public static String KEY_CHAR = ".";

    public static final Pattern IP_PATTERN = Pattern.compile("^" + IPV4_STR + "$");
    public static final Pattern IP_MASK_PATTERN = Pattern.compile("^" + IPV4_STR + "/([\\d]{1,2})" + "$");
    public static final Pattern IP_LINE_PATTERN = Pattern.compile("^" + IPV4_STR + "-" + IPV4_STR + "$");

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

    public String long2ip(long ip) {
        StringBuilder ipBuffer = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            ipBuffer.append((ip >> (i * 8)) & 0xFF);
            if (i != 0) ipBuffer.append(".");
        }
        return ipBuffer.toString();
    }

    @Deprecated
    public long ips2long(String ipAddr) {
        String[] ips = ipAddr.split("\\.");
        return (Long.parseLong(ips[0]) << 24) + (Long.parseLong(ips[1]) << 16) + (Long.parseLong(ips[2]) << 8) + Long.parseLong(ips[3]);//must quote
    }


    public void store(Map<Integer, Set<Long>> map, String... ipStrs) {
        Arrays.stream(ipStrs).forEach(t -> {
            String ip = null;
            Integer mask = Integer.valueOf(32);
            Matcher m = IP_PATTERN.matcher(t);
            if (m.find()) {
                ip = t;
            } else {
                m = IP_MASK_PATTERN.matcher(t);
                if (m.find()) {
                    ip = m.group(1);
                    mask = Integer.valueOf(m.group(2));

                } else {
                    m = IP_LINE_PATTERN.matcher(t);
                    if (m.find()) {
                        ip = m.group(1);
                        String _ip = m.group(2);

                        mask = netmask(ip, _ip);
                    }
                }
            }

            if (ip != null) {
                if (!map.containsKey(mask)) map.put(mask, new HashSet<>());
                map.get(mask).add(prefix(ip, mask));
            }
        });
    }

    public long prefix(long ip, int mask) {
        return ip >> (32 - mask);
    }

    public long prefix(String ip, int mask) {
        return prefix(ip2long(ip), mask);
    }

    public String key(long ip, int mask) {
        StringBuilder builder = new StringBuilder();
        builder.append(mask).append(KEY_CHAR).append(ip >> (32 - mask));
        return builder.toString();
    }

    public String key(String ipAddr, int mask) {
        long ip = ip2long(ipAddr);
        return key(ip, mask);
    }

    public Integer netmask(String ipStart, String ipEnd) {
        long v1 = ip2long(ipStart);
        long v2 = ip2long(ipEnd);

        return netmask(v1, v2);
    }

    public Integer netmask(Long ipStart, Long ipEnd) {
        return (32 - Long.bitCount(ipEnd - ipStart));
    }

    public boolean in(String ipStr, Map<Integer, Set<Long>> map) {
        long ip = ip2long(ipStr);

        return map.keySet().stream().filter(k -> map.get(k).contains(ip >> (32 - k))).findFirst().isPresent();
    }

    @Deprecated
    public boolean in(String ipStr, Set<String> set, Set<Integer> masks) {
        long ip = ip2long(ipStr);
        return masks.stream().map(m -> m + "." + (ip >> (32 - m))).filter(k -> set.contains(k)).findFirst().isPresent();
    }
}
