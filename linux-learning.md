1.ͳ��һ���ļ����ַ����ֵĴ���
perl -e 'while(<>){$count+= s/[str]//g;} print "$count\n";' [filename]



ƽ̨: RHEL5.4

ִ��rpm -q �������һֱhang�����ﲻ��, ps��һ�·���n���rpmq���̹�ס��. 

��Щ������ÿ��crontabִ��/etc/cron.daily/rpm��, ���±���rpm�⵽ /var/lib/rpm/__db.* �ļ���. �������� /var Ŀ¼��������, ���Բų����������, ����Ҫ��������:

# rm -f /var/lib/rpm/__db.*

Ȼ����ִ��rpm�����û��������. ������ؽ�һ��rpm��

# rpm -vv --rebuilddb
# /etc/cron.daily/rpm

#### ��ֹ�ض�IP���� 
$ iptables -A INPUT -p tcp --dport $port -s $ip/32 -d 0.0.0.0/0 -j REJECT
����$ip���ʱ���$port�˿ڵķ��ʱ���ֹ��
2.	ͳ��һ���ļ�($filename)��ĳ���ַ����ֵĴ���
$ perl -e 'while(<>){$count+= s/[$string]//g; } print "$count\n";'  $filename
$ grep -c ��$string��$filename
3.	ϵͳ����
$ crontab [-u user] file
$ crontab [-u user] { -e | -l | -r }
4.	�ڴ�ͳ��
$ ps aux|awk \
'BEGIN {CPU=0;MEM=0;MEMN=0} /httpd/ {CPU=CPU+$3;MEM=MEM+$4;MEMN=MEMN+$5} END {print "cpu used " CPU "|mem used " MEM "|mem all size is " MEMN}'

##### ɾ�������ļ�
+ ʹ��`rsync`

		mkdir /tmp/blankdir
		rsync --delete-before  --progress --stats /tmp/blankdir/ /target/ 
+ ʹ��`find -exec`

		find . -name *.txt -exec ls -l {} \;

+ ʹ��`find xargs`

		find . -name *.txt | xargs rm -f


#### Vncserver

+ ��װ����(����ʹ��xfce��������ʹ��`yum -y groupinstall kde`����`yum -y groupinstall gnome`)��

		yum -y groupinstall xfce   

+ ��װvncserver��

		yum -y install vnc vnc-server

+ �༭`/etc/sysconfig/vncservers`

		VNCSERVERS="1:root" 
		VNCSERVERARGS[1]="-geometry 1600x900"

+ �༭`/root/.vnc/xstartup`

		#!/bin/sh 
		/usr/bin/startxfce4

+ �趨���룺

		vncpasswd

+ ������

		service vncserver restart


