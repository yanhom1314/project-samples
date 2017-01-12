## 编译安装

#### openssl
	
	./config --shared
	make
	make test
	make install

#### openssh

	./configure --with-ssl-dir=[openssl install dir] --without-openssl-header-check
	make 
	make install