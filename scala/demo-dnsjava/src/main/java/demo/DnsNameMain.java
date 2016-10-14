package demo;

import com.google.common.net.InternetDomainName;
import org.xbill.DNS.Name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DnsNameMain {
    public static void main(String[] args) {
        String dn = "a.b.www.baidu.com.cn";
        List<String> list = domain(dn);
        //Collections.reverse(list);
        list.forEach(System.out::println);

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
    }

    public static List<String> domain(String dn) {
        List<String> list = new ArrayList<>(Arrays.asList(dn));
        if (!InternetDomainName.from(dn).isTopPrivateDomain()) {
            list.addAll(domain(dn.substring(dn.indexOf(".") + 1)));
        }
        Collections.sort(list, (o1, o2) -> o1.length() - o2.length());
        return list;
    }

    public static void domain_1(String dn, List<String> list) {
        list.add(dn);
        if (!InternetDomainName.from(dn).isTopPrivateDomain()) {
            domain_1(dn.substring(dn.indexOf(".") + 1), list);
        } else Collections.reverse(list);
    }
}
