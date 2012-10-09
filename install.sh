#安装webalizer http://www.mrunix.net/webalizer/download.html
wget ftp://ftp.mrunix.net/pub/webalizer/webalizer-2.21-02-src.tgz
tar zxvf webalizer-2.21-02-src.tgz 
cd webalizer-2.21-02
./configure 
make
make install

#安装squid http://www.squid-cache.org
wget http://www.squid-cache.org/Versions/v2/2.7/squid-2.7.STABLE6.tar.gz
tar zxvf squid-2.7.STABLE6.tar.gz 
cd squid-2.7.STABLE6
./configure --help
./configure --prefix=/phd/bin/squid
make
make install
#run server
/phd/bin/squid/bin/squid -V
/phd/bin/squid/bin/squid -k reconfigure



