/**
 * filename:log.c
 * version:0.1
 * author:YaFengLi
 * 
 * */
#include <stdio.h>
#include <string.h>
#include <time.h>
#define LEN 30
typedef struct
{
	char number[LEN];
	char datestr[LEN];
} log_t;

int  
read(const char *filename){
	FILE *fp=fopen(filename,"r");
	if(fp==NULL){
		perror("The file read is NULL.");
		return 0;
	}
	if(fseek(fp,-60,SEEK_END)==0){
		log_t log[1];
		memset(log[0].number,'\0',LEN);
		memset(log[0].datestr,'\0',LEN);
		fread(log,sizeof(log_t),1,fp);
		printf("num:%s,%d\nstr:%s\n",log[0].number,atoi(log[0].number),log[0].datestr);
		return atoi(log[0].number);
	}	
	else{
		return 0;
	}
}
	void
write (const char *filename,int count)
{
	FILE *fp = fopen (filename, "a+");
	int i=count;
	while (1)
	{
		time_t t;
		t = time (NULL);

		struct tm *tt = localtime (&t);

		log_t log[1];
		memset(log[0].number,'\0',LEN) ;
		memset(log[0].number,'\0',LEN) ;
		sprintf (log[0].number,"%d",++i);
		sprintf (log[0].datestr, " %d-%d-%d %d:%d:%d\n", 1900+tt->tm_year, tt->tm_mon,
				tt->tm_mday, tt->tm_hour,tt->tm_min,tt->tm_sec);
		fwrite (log, sizeof (log_t), 1, fp);
		fflush (fp);
		sleep (1);
	}
	fclose (fp);
}

	int
main ()
{
	char filename[] = "/tmp/lyf.log";
	int count= read(filename);
	write (filename,count);
	return 0;
}
