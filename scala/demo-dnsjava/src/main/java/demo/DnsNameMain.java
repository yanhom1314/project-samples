package demo;

import com.google.common.net.InternetDomainName;
import org.xbill.DNS.Master;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DnsNameMain {
    public static void main(String[] args) throws Exception {

        System.out.println("ytptt.sd.cn:"+InternetDomainName.from("ytptt.sd.cn").topPrivateDomain().toString());
        System.out.println("a.sd.cn.net:"+InternetDomainName.from("a.sd.cn.net").topPrivateDomain().toString());


        System.out.println("ytptt.sd.cn:"+isTop(Name.fromString("ytptt.sd.cn.")));
        System.out.println("cn.net:"+isTop(Name.fromString("cn.net.")));
        System.out.println("sd.cn.net:"+isTop(Name.fromString("sd.cn.net.")));
        System.out.println("a.sd.cn.net:"+isTop(Name.fromString("a.sd.cn.net.")));

        String domain = "a.b.www.baidu.com.cn.";
        List<String> l = new ArrayList<>();
        //time(10000, () -> domain(domain, l));
        System.out.println("length:" + args.length);
        if (args.length >= 1) {
            try {
                File f = new File(args[0]);
                Pattern pt = Pattern.compile("([^\\s]+\\.)hosts");
                Matcher matcher = pt.matcher(f.getName());
                if (matcher.find()) {
                    String dn = matcher.group(1); // domain name
                    System.out.println("dn:");
                    Master master = new Master(f.getAbsolutePath(), Name.fromString(dn));

                    Set<String> dl = new HashSet<>(Arrays.asList(Name.fromString(dn).toString(true)));
                    List<Record> rl = new ArrayList<>();

                    Record record;
                    while ((record = master.nextRecord()) != null) {
                        dl.add(parent(record.getName()).toString(true));
                        rl.add(record);
                    }
                    List<String> al = new ArrayList<>(dl);
                    al.sort((o1, o2) -> o1.length() - o2.length());
                    al.forEach(d -> {
                                try {
                                    System.out.println(d + ":" + parent(Name.fromString(d)));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                    // rl.forEach(r -> System.out.printf("%s\t%s\t%s\t%s\n", r.getName(), "", r.getTTL(), DClass.string(r.getDClass()), Type.string(r.getType()), r.rdataToString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
        try {
            Name name = Name.fromString(dn + ".");
            System.out.println("domain:" + dn);
            String prefix = name.getLabelString(0);
            System.out.println("prefix:" + prefix);
            Name subName = Name.fromString(name.toString().substring(name.toString().indexOf(".") + 1));
            System.out.println("check parent:" + InternetDomainName.from(subName.toString()).parent().equals(InternetDomainName.from(dn)));
            System.out.println("parent domain:" + subName.toString(true));
            System.out.println("parent domain:" + InternetDomainName.from(dn).parent().toString());
            System.out.println("top domain:" + InternetDomainName.from(dn).topPrivateDomain().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    public static void time(int count, Runnable runnable) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            runnable.run();
        }
        long end = System.currentTimeMillis();
        System.out.println("time use:" + (end - start) + "ms.");
    }

    public static void domain(String dn, List<String> list) {
        list.add(dn);
        if (!InternetDomainName.from(dn).isTopPrivateDomain()) {
            domain(dn.substring(dn.indexOf(".") + 1), list);
        } else Collections.sort(list, (o1, o2) -> o1.length() - o2.length());
    }

    public static Name parent(Name name) throws Exception {
        if (name.isWild()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < name.labels() - 1; i++) {
                builder.append(name.getLabelString(i)).append(".");
            }
            return Name.fromString(builder.toString());
        } else {
            InternetDomainName d = InternetDomainName.from(name.toString());
            if (d.isTopPrivateDomain()) return Name.fromString(d.toString());
            else return Name.fromString(d.parent().toString());
        }
    }

    public static boolean isTop(Name name) {
        boolean isTop = false;
        try {
            if (name.isWild()) {
                isTop = InternetDomainName.from(parent(name).toString()).isTopPrivateDomain();
            } else isTop = InternetDomainName.from(name.toString()).isTopPrivateDomain();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTop;
    }
}
