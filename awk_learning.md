## [AWK笔记](http://sebug.net/paper/books/awk/)
+ 任何awk语句都由模式和动作组成。
+ 域：每一行为一个域。
+ 记录：将域以分隔符切割后获得的元素项为记录。

## 命令执行
+ 直接执行命令：`awk -F分隔符 /模式/ '动作' 输入文件`
+ 例如：

		awk -F, $1 ~/^[a-z]/ 'BEGIN{print "Begig to start"} {printf "%s|%s",$1,$2} END{}' test.txt

+ 以,号分割域，第1条记录以字母开头，按格式打印内容处理text.txt文件。



		

