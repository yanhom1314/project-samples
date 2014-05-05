## JavaScript模块化开发
#### 包管理与模块加载
+ 安装配置(Nodejs)[http://nodejs.org]
+ 安装(Spm)[https://spmjs.org]是`CMD`的包管理工具，需要和(Sea.js)[http://seajs.org]配合使用。

		npm install spm -g

#### 构建项目
+ 创建：
		
		mkdir test-spm
		cd test-spm
		spm init
		spm install seajs
		spm install jquery

+ 发布：
		
		spm login
		spm publish
		
+ 使用：

		spm install [family]/test-spm

+ 查看：
	
		spm info [family]/test-spm		


+ 项目文件`package.json`

