## 安装EPEL源
+ 检测操作系统版本：`cat /etc/redhat-release`
+ [Centos 5](http://download.fedoraproject.org/pub/epel/5/i386/repoview/epel-release.html)
+ [Centos 6](http://download.fedoraproject.org/pub/epel/6/i386/repoview/epel-release.html)


## 安装

    sudo rpm -ivh epel-release*


## 检查源

    sudo yum repolist


## 安装Docker

    sudo yum list docker*
    sudo yum install -y docker-io


