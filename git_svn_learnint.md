##Git

+ 查看所有配置属性
		
		$ git config --list                         
+ 设置用户信息

		$ git config --global user.name "John Doe"     			//设置用户名
		$ git config --global user.email johndoe@example.com  	//设置用户邮件
		$ git config --global core.editor vim   				//设置编辑器

+ 新建项目

		$ git clone [git_url] [local_project_name]   			//从提供的git_url地址clone内容到本地
或者
		
		$ git init 
		$ git remote add [repo_name] [git_url]                  //增加远程仓库git_url，并命名为repo_name
		$ git pull 												//也可以分开使用fetch与merge

####常用命令：

		$ git commit -a   									//提交修改至本地仓库
		$ git tag  [标签]									//标签为空时是创建标签，否则为查看标签

远程仓库
$ git remote -v    				//查看远程仓库的信息，Git在clone之后会缺省建立一个origin的远程仓库
$ git remote add [git_url]		//如果使用init新建项目，可以用此命令新增远程仓库

分支处理
$ git branch 					//列出本地所有分支
$ git branch -r   				//列出远程所有分支
$ git branch new-branch-name    //分支new-branch-name不为空时是查看分支列表，否则创建new-branch-name分支
$ git checkout new-branch-name  //切换到new-branche-name分支
$ git push origin [分支名]      //推送当前分支至远程仓库分支名，如省略分支名这缺省使用本地分支名


Pull命令使用
//注意pull包含两个动作fetch、merge谨慎使用
$ git pull 						//pull远程仓库git pull [远程仓库名(如缺省的origin) 或者 远程仓库地址] [源分支:目标分支]
$ git pull . branch_1 			//将本地branch_1分支合并到当前分支

冲突处理
$ git reset --hard 				//忽略本地的修改
$ git commit -a 
//或者
$ git pull  					//修改本地冲突文件
$ git commit -a  				//解决冲突后提交

恢复或者回滚
$ git reset --hard <has_value>  
//恢复到哈希值版本，查看哈希值
$ git log --oneline

更新或者查看合作伙伴开发
$ git fetch [git_url] [源分支]:[本地分支]  			//获取远程的源分支到本地分支
$ git whatchanged -p [分支1] [分支2]           		//查看本地分支中的改变

补充
checkout命令有两个作用：
其一是在不同的branch之间进行切换，例如'git checkout new_branch'就会切换到new_branch的分支上去；
其二是还原代码的作用，例如'git checkout app/model/user.rb'就会将user.rb文件从上一个已提交的版本中更新回来，未提交的内容全部会回滚。

fetch与pull的区别
fetch是从远程获取最新版本到本地，但不会自动merge
pull是从远程获取最新版本并merge到本地

##branch tag区别
tag主要用来记录代码的版本状态；
branch主要是用来声明一个单独开发进程与其他分支并行；

##Tag

		$ git tag -a v0.1  			//增加一个v0.1的tag
		$ git tag -d v0.1 			//删除一个v0.1的tag
		$ git tag -l 	 			//查看所有tag，支持名称过滤，如：'v0.*'
		$ git push origin --tag  	//想origin push所有的tags

##Branch		

		$ git branch <new_branch_name> 			//建立本地 local branch
		$ git branch -m <old_name> <new_name> 	//改名字 (如果有同名會失敗，改用 -M 可以強制覆蓋)
		$ git branch 							//列出本地所有branch以及目前在那個branch,加`-a`参数，列出所有远程+本地的branch
		$ git checkout <branch_name> 			//切換 branch (注意到如果你有檔案修改了卻還沒 commit，會不能切換 branch，解法稍後會談)
		$ git checkout -b <new_branch_name> 	//本地建立 branch 並立即 checkout 切換過去
		$ git branch -d <branch_name> 			//刪除 local branch
##比较分支
		
		$ git diff master branch-0.1 			//比较master与branch-0.1分支的区别		
##Merge
+ 切换当前分支是master
		
		$ git checkout master
+ 把issueFix中的内容Merge进来：
		
		$ git merge issueFix

+ 如果没有冲突的话，merge完成。有冲突的话，git会提示那个文件中有冲突，比如有如下冲突：

		<<<<<<< HEAD:test.c
			printf (“test1″);
		=======		
		printf (“test2″);
		>>>>>>> issueFix:test.c

+ 这个解决方案各采纳了两个分支中的一部分内容，而且删除了 <<<<<<<，=======，和>>>>>>> 这些行。在解决了所有文件里的所有冲突后，运行`git add`将把它们标记为已解决（resolved）。因为一旦暂存，就表示冲突已经解决。如果你想用一个有图形界面的工具来解决这些问题，不妨运行 `git mergetool`，它会调用一个可视化的合并工具并引导你解决所有冲突：		
