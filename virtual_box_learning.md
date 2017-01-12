#### Virtual Box安装

		yum install VirtualBox-X.Y.Z
#### 启动图形界面

		VirtualBox		

#### VBoxManage命令

		VBoxManage --help		
+ 命令

		VBoxManager list vms # 列出所有虚拟机
		VBoxManager startvm <name> --type headless # 启动虚拟机
		VBoxManager controlvm <name> poweroff # 关闭虚拟机，等价于直接关闭电源，非正常关机
		VBoxManage list runningvms # 列出运行中的虚拟机
		VBoxManage controlvm <name> acpipowerbutton # 关闭虚拟机，等价于点击系统关闭按钮，正常关机
		VBoxManage controlvm <name> pause # 暂停虚拟机的运行
		VBoxManage controlvm <name> resume # 恢复暂停的虚拟机
		VBoxManage controlvm <name> savestate # 保存当前虚拟机的运行状态

#### Vncserver安装

		yum install tigervnc tigervnc-server -y 
		yum install fontforge -y 
		yum groupinstall Desktop -y
#### 启动Vncserver
	
		vncserver -name vnc_1		

