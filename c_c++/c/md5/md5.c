#include <stdio.h>
#include <openssl/md5.h>
#include <string.h>

void md5_1()
{
    MD5_CTX ctx;
    unsigned char *data="123";
    unsigned char md[16];
    char buf[33]={'\0'};
    char tmp[3]={'\0'};
    int i;

    MD5_Init(&ctx);
    MD5_Update(&ctx,data,strlen(data));
    MD5_Final(md,&ctx);

    for( i=0; i<16; i++ ){
        sprintf(tmp,"%02X",md[i]);
        strcat(buf,tmp);
    }
    printf("%s\n",buf);    
}

void md5_2(){
        unsigned char* data =(unsigned char*) "123";
        unsigned char md[16];
        int i;
        char tmp[3]={'\0'},buf[33]={'\0'};
        MD5((const unsigned char*)data,strlen((const char*)data),md);
        for (i = 0; i < 16; i++){
                sprintf(tmp,"%2.2x",md[i]);
                strcat(buf,tmp);
        }
        printf("%s\n",buf);
}