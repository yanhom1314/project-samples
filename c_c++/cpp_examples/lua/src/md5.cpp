#include "test.h"
void md5(){
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
