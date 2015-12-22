#### Spacemacs

        http://github.com/my123/spacemacs

#### windows生成`.emacs`配置文件的方法，运行`emacs`打开`Options`->`Set Default fonts`选择字体后再打开`Options`->`Save Options`会生成`.emacs`配置文件，再编辑即可。
## 全文搜索

  <M-x> grep
  grep -nH -r [内容] [路径]

## Undo

  <C-x> u

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
