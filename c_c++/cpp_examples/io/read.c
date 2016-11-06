#include <stdio.h>
#include <stdlib.h>

#define  MAX_LENGTH 1024

void read_write(char *in_file, char *out_file) {
    char buffer[MAX_LENGTH];
    int num = 0;
    FILE *fp1 = fopen(in_file, "r");//打开输入文件
    FILE *fp2 = fopen(out_file, "w");//打开输出文件
    if (fp1 == NULL || fp2 == NULL) {//若打开文件失败则退出
        perror("不能打开文件！");
    }
    while (fgets(buffer, MAX_LENGTH, fp1) != NULL) {
        printf("No.%d.%s\n", num, buffer);
        fputs(buffer, fp2);
        num++;
    }

    fclose(fp1);//关闭输入文件
    fclose(fp2);//关闭输出文件，相当于保存
}

int main(void) {
    read_write("F:\\Projects\\demo\\hello-cpp\\main.cpp","F:\\Projects\\demo\\hello-cpp\\main_out.cpp");
    return EXIT_SUCCESS;
}

