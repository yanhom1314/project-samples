#include "demo.h"
#include "hello.h"

char http_buf[4000];

int get_get(char *data, char *buff) {
    int i = 0;
    if (data == NULL) return 0;

    while (*data != '\r') {
        if (*data == ' ')
            i++;
        if (i != 2) {
            *buff++ = *data++;
        }
        else {
            *buff = '\0';
            return 1;
        }
    }
}

int get_url_page(char *data, char *buff) {
    if (data == NULL) return 0;
    data += 4;//去掉前面"get "

    while (*data != '\0') {
        if (*data != '?') {
            *buff++ = *data++;
        }
        else {
            *buff = '\0';
            return 1;
        }
    }
    *buff = '\0';
    return 1;
}

int is_real_page(char *data) {
    if (data == NULL)
        return -1;
    while (*data != '\0') {
        data++;
    }
    while (*--data != '/') {
        if (*data == '.') {
            switch (*++data) {
                case 'a':
                    if ((*++data == 's') && (*++data == 'p'))
                        return 1;//asp,aspx
                    return 0;
                case 'h':
                    if ((*++data == 't') && (*++data == 'm')) return 1;
                    return 0;//htm,html
                case 'j':
                    if ((*++data == 's') && (*++data == 'p')) return 1;
                    return 0;//jsp
                case 'p':
                    if ((*++data == 'h') && (*++data == 'p')) return 1;
                    return 0;//php
                default:
                    return 0;
            }
        }
    }
    return 1;
}

int get_host(char *data, char *buff) {
    if (data == NULL) return 0;
    while (data != '\0') {
        if ((*data == 'H') && (*++data == 'o') && (*++data == 's') && (*++data == 't')) {
            data++;//去掉http请求头标中键值对的‘:’
            while (*data != '\r')//while((*data!='\r')&&(*++data!='\n'))
            {
                *buff++ = *++data;
            }
            *buff = '\0';
            return 1;
        }

        data++;

    }
    return 0;
}

void packet_info(u_char *arg, const struct pcap_pkthdr *pkthdr, const u_char *packet) {
    struct ether_header *_eth; //以太网帧
    struct iphdr *_iphdr;    //ip head
    struct tcphdr *_tcphdr;//tcp
    struct in_addr addr;
    char *data;
    int http_len;
    char hostBuf[500];
    char getBuf[100];
    char path[100];
    char src[20], dst[20];
    u_int16_t src_port;
    u_int16_t dst_port;
    //detail packet
    _eth = (struct ether_header *) packet;
    switch (ntohs(_eth->ether_type)) {
        case ETHERTYPE_IP:
            _iphdr = (struct iphdr *) (packet + sizeof(struct ether_header));
            addr.s_addr = _iphdr->saddr;
            strcpy(src, inet_ntoa(addr));
            addr.s_addr = _iphdr->daddr;
            strcpy(dst, inet_ntoa(addr));

            if (_iphdr->protocol == 6) { //filter tcp packet
                _tcphdr = (struct tcphdr *) (packet + sizeof(struct ether_header) + sizeof(struct iphdr));
                src_port = htons(_tcphdr->source);
                dst_port = htons(_tcphdr->dest);

                if (dst_port == 80) {//match port 80
                    data = (char *) (packet + sizeof(struct ether_header) + sizeof(struct iphdr) +
                                     sizeof(struct tcphdr));
                    http_len = ntohs(_iphdr->tot_len) - (_iphdr->ihl) * 4 - sizeof(struct tcphdr);
                    if (http_len > 0) if ((*data == 'G') && (*++data == 'E') && (*++data == 'T')) {
                        memset(http_buf, 0, 4000);
                        memcpy(http_buf, data - 2, http_len);
                        get_host(http_buf, hostBuf);
                        get_get(http_buf, getBuf);
                        get_url_page(getBuf, path);

                        //print time or send packet
                        printf("%s:%d->%s:%d len:%d\n", src, src_port, dst, dst_port, http_len);
                        printf("Request Host:%s\n", hostBuf);
                        printf("%s\n\n", getBuf);
                        //printf("path:%s\n\n", path);
                    }
                }
            }
            break;
        case ETHERTYPE_ARP:
            puts("ARP");
            break;
        default:
            break;
    }
}

void call(char *devStr, char *errBuf, char *exp, pcap_handler callback) {
    /* open a device, wait until a packet arrives */
    pcap_t *device = pcap_open_live(devStr, 65535, 1, 0, errBuf);

    if (!device) {
        printf("error: pcap_open_live(): %s\n", errBuf);
        exit(1);
    }

    struct bpf_program filter;
    pcap_compile(device, &filter, exp, 1, 0);
    pcap_setfilter(device, &filter);

    /* wait loop forever */
    int id = 0;
    pcap_loop(device, -1, callback, (u_char * ) & id);
    pcap_close(device);
}

void loop_dev(char *errBuf, char *dev, char *exp, pcap_handler callback) {
    pcap_if_t *alldevs;

    if (pcap_findalldevs(&alldevs, errBuf) == 0) {
        int i = 0;
        for (; alldevs != NULL; alldevs = alldevs->next) {
            if (strcmp(alldevs->name, dev) == 0) {
                bpf_u_int32 net_ip;
                bpf_u_int32 mask_ip;
                int ret = pcap_lookupnet(dev, &net_ip, &mask_ip, errBuf);
                if (ret == 0) call(alldevs->name, errBuf, exp, callback);
                else printf("ERROR:%d", ret);
            }
        }
    }
}

int main(int argc, char *argv[]) {
    char exp[OPTION_BUF_LEN];
    memset(exp, 0, OPTION_BUF_LEN);
    char *eth_in = malloc(OPTION_BUF_LEN);
    char *eth_out = malloc(OPTION_BUF_LEN);
    char errBuf[PCAP_ERRBUF_SIZE];

    int in_pos = -1;
    int out_pos = -1;
    int i;
    for (i = 1; i < argc; i++) {
        if (argv[i][0] == '-' || argv[i][0] == '/') {
            switch (tolower(argv[i][1])) {
                case 'i':
                    trim(argv[++i], eth_in);
                    in_pos = i;
                    break;
                case 'o':
                    trim(argv[++i], eth_out);
                    out_pos = i;
            }
        }
    }
    if (in_pos >= 1 && out_pos >= 1) {
        int pos = in_pos > out_pos ? in_pos : out_pos + 1;
        int k;
        for (k = pos; k < argc; k++) {
            strcat(exp, argv[k]);
            strcat(exp, " ");
        }
        printf("in:%s out:%s in_pos:%d out_pos:%d pos:%d argc:%d exp:%s\r\n", eth_in, eth_out, in_pos, out_pos, pos,
               argc, exp);
        loop_dev(errBuf, eth_in, exp, packet_info);
    }
    else {
        puts("Usage:command -i [in eth name] -o [out eth name] [expression].");
    }
    free(eth_in);
    free(eth_out);

    //test hello
    printf("Hello count:%d.", add(12, 21));
    return 0;
}
