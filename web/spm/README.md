#### 创建模块
+ `git clone git@github.com:zTree/zTree_v3.git`创建本地仓库；
+ 根据最新Release的`Tag`创建分支`spm_branch`，然后编辑`package.json`；
+ 构建/发布到(Spmjs.io)[http://spmjs.io]：

		spm publish		

#### 编译
+ 进入模块，例如：`cd jquery-ui`
+ 编译：`spm build --with-deps`

#### 测试
+ 进入模块，例如：`cd jquery-ui`
+ 将当前目录作为http根目录，例如`anywhere`不可使用`spm doc watch`
