#### color-theme包

        http://download.gna.org/color-theme/

#### 在C:/Users/<name>/AppData/Roming/.emacs文件中添加

        (add-to-list 'load-path "~/.emacs.d/")
         (require 'color-theme)
        (color-theme-initialize)
        (color-theme-matrix)

#### Windows7生成`.emacs`配置文件的方法，运行`emacs`打开`Options`->`Set Default fonts`选择字体后再打开`Options`->`Save Options`会生成`.emacs`配置文件，再编辑即可。
##### 文件 #####
打开文件: C-x C-f
保存文件: C-x C-s
打开buffer里的文件: C-x b [filename]
buffer list: C-x C-b
保存buffer: C-x s
恢复文件: M-x recover file
关闭buffer: C-x k
改变文件的只读/可写状态: C-x C-q

##### 帮助 #####
简单说明: C-h c [command]
详细说明: C-h k [command]
在线文档: C-h i


CEDET安装：
安装：
步骤1
下载http://cedet.sourceforge.net/下载cedet安装包并解压缩。
步骤2
在基于Unix的系统（例如Linux）使用make安装
make EMACS=emacs
windows：
cd cedet-1.0pre7
emacs –Q –l cedet-build.el –f cedet-build
配置（.emacs[linux:/LOGINNAME/.emacs]/_emacs[windows:c:/Documents and Settings/LOGINNAME/Application Data/_emacs]）
;;不显示Message
(setq inhibit-startup-message t)


快捷键：
##### 常规 #####
退出Emacs: C-x C-c
撤销命令: C-g
垂直分割当前窗口：C-x 2   
水平分割当前窗口：C-x 3
仅保留当前窗口：C-x 1
关闭当前窗口：C-X 0
活动窗口切换：C-x o
跳到指定行数:M-g M-g或者M-x goto-line [enter] <line_number>
##### 移动 #####
下一个页面: C-v
上一个页面: M-v
光标所在页面: C-l
前一个文字: C-b
后一个文字: C-f
前一个单词: M-b
后一个单词: M-f
下一行: C-n
上一行: C-p
行头部: C-a
行尾部: C-e
句子头部: M-a
句子尾部: M-e
文件头部: M-<
文件尾部: M->
数字参数: C-u 8 [command]
向前搜索: C-s
向后搜索: C-r

##### 编辑 #####
delete光标处的一个文字: C-d
delete光标后的一个单词: M-d
剪切光标后的一行: C-k
剪切光标后的一个句子: M-k
剪切指定范围的文本: C-SPC or C-@, C-w
复制被选中的文本: C-SPC or C-@, M-w
插入最后一次kill的文字: C-y
遍历kill的文字: M-y
撤销: C-x u
替换文本: M-%

##### 文件 #####
打开文件: C-x C-f
保存文件: C-x C-s
打开buffer里的文件: C-x b [filename]
buffer list: C-x C-b
保存buffer: C-x s
恢复文件: M-x recover file
关闭buffer: C-x k
改变文件的只读/可写状态: C-x C-q

##### 帮助 #####
简单说明: C-h c [command]
详细说明: C-h k [command]
在线文档: C-h i


CEDET安装：
安装：
步骤1
下载http://cedet.sourceforge.net/下载cedet安装包并解压缩。
步骤2
在基于Unix的系统（例如Linux）使用make安装
make EMACS=emacs
windows：
cd cedet-1.0pre7
emacs –Q –l cedet-build.el –f cedet-build
配置（.emacs[linux:/LOGINNAME/.emacs]/_emacs[windows:c:/Documents and Settings/LOGINNAME/Application Data/_emacs]）
;;不显示Message
(setq inhibit-startup-message t)


远程调用EMACS编辑器。

首先启动emacs server。在配置文件中加入。
;; .emacs  
(start-server)  
这样启动一个emacs之后，server也随之启动。
如果要在此server中编辑，请使用
emacsclient FILE  
emacsclientw FILE (for windows)  
退出使用C-x #，而不是C-x C-c，否则会关闭server。


使emacs和x11下的应用程序可以互相粘贴，从网上找了如下代码，写入.emacs
(setq x-select-enable-clipboard t)
(setq interprogram-paste-function 'x-cut-buffer-or-selection-value)
