== 日本
https://conoha.jp yafengli@sina.com/x**l**@2***

== Centos 7安装配置Shadowsocks
* 安装shadowsocks

yum install python-setuptools && easy_install pip
pip install shadowsocks
2. 新建shadowsocks的配置文件

mkdir -p /etc/shadowsocks
vi /etc/shadowsocks/config.json
输入以下内容：


{
 "server":"0.0.0.0",
 "server_port":8888,
 "local_address": "127.0.0.1",
 "local_port":1080,
 "password":"mypassword",
 "timeout":300,
 "method":"aes-256-cfb",
 "fast_open": false,
 "workers": 1
}
说明：服务器IP，服务端口（建议自定义），本地监听IP，本地监听端口，密码（建议自定义），超时时间，加密算法，关闭fast-open，工作进程数量为1。

3. 新建shadowsocks的.service文件

vi /etc/systemd/system/shadowsocks-server.service
输入以下内容：

[Unit]
Description=Shadowsocks Server
After=network.target

[Service]
Type=forking
PIDFile=/run/shadowsocks/server.pid
PermissionsStartOnly=true
ExecStartPre=/bin/mkdir -p /run/shadowsocks
ExecStartPre=/bin/chown root:root /run/shadowsocks
ExecStart=/usr/bin/ssserver --pid-file /var/run/shadowsocks/server.pid -c /etc/shadowsocks/config.json -d start
Restart=on-abort
User=root
Group=root
UMask=0027

[Install]
WantedBy=multi-user.target
4. 运行shadowsocks服务并设置为开机自启：

systemctl start shadowsocks-server.service
systemctl enable shadowsocks-server.service
5. 防火墙开放shadowsocks服务端口：

firewall-cmd --permanent --add-port=8888/tcp
firewall-cmd --reload
6. 常用操作

升级shadowsocks

pip install -U shadowsocks
卸载shadowsocks

pip uninstall shadowsocks
查询已安装的shadowsocks

pip search "shadowsocks"
停止shadowsocks服务

systemctl stop shadowsocks-server.service
