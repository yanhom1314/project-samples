#ifndef _INCLUDE_DEMO_H
#define _INCLUDE_DEMO_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <pcap.h>
#include <netinet/in.h>
#include <net/ethernet.h>
#include <netinet/ip.h>
#include <netinet/tcp.h>
#include <netinet/if_ether.h>
#include <arpa/inet.h>

#define IS_SPACE(x) ((x)==' '||(x)=='\r'||(x)=='\n'||(x)=='f'||(x)=='\b'||(x)=='\t')
#define OPTION_BUF_LEN 1024

void trim(const char *str, char *buffer);

#endif
