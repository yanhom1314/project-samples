## Haskell笔记
#### 安装
#### CentOS
+ GHC

		tar jxf ghc-x.x.x.tar.bz2
		cd ghc-x.x.x
		./configure
		make install

+ 

+ Haskell Platform

		tar zxf haskell-x.x.x.tar.gz
		cd haskell-x.x.x
		./configure --prefix=/usr/haskell
		make install			

+ OpenGL:`yum install freeglut-devel`

#### GHCI配置
+ Windows 7配置文件是Users\[用户名]\AppData\Roaming\ghc\ghci.conf:`:set prompt "λ>"`
* `ghci.conf`文件的编码需要采用`GBK`


##### Haskell缩进
+ 作为表达式的一部分代码，一定要比表达式的开头再缩进一些。
+ 切勿使用制表符TAB(\t)来缩进，尽量使用等宽字体编程。
#### List
+ 保存相同元素
+ 操作函数
+ head:返回第一个元素；
+ tail：返回去掉头部的其他部分（尾部）；
+ last：返回最后一个元素；
+ init：返回除了最后一个元素的其他部分；
+ length：返回长度；
+ null：检测是否为空；
+ reverse：反转序列；
+ take n list:从list中取前n个元素组成list，n>=0,如果n=0，得到一个空list；
+ maximum/minimum：得到最大/小元素（list中的元素必然可以比较）；
+ sum：求和；
+ elem x list:判断x是否存在于list中；
+ 例如：

        let a = [1,2,3,4,5]
        head a
        2 \`elem\` a
        + 参数为2个的前缀函数可以`\'`符号包裹后作为中缀函数使用，类似：5 * 6，2  \`elem\` a

#### Tuple
+ 保存有序元素
+ 操作函数
+ fst：返回首项；
+ snd：返回次项；
+ zip list1 list2：将两个list中的元素进行交叉配对，返回一个由pair组成的list；如果两个list中的元素个数不一致，以较短的那个为界；
+ 例：

        zip [x |  x <- [1..10],x /= 3, x /= 5] [x | x <- ['a'..'z']]
#### Range
+ 表示连续的值
+ 可以设定步长
+ 例:

    [1..5] //表示1到5之间的所有数[1,2,3,4,5]
    [1,3..5]//表示1到5之间步长为2的数[1,3,5]

####  Typeclass
+ 类型的类，l类似于java中的Interface
+ Eq判断相等，函数==和/= ，例如：5 == 2
+ Ord可比较，包含函数>，>=，<，<=，l例如：5 >= 3
+ Show将参数转换成字符串，函数为show，例如：show True
+ Read将字符串读取成目标Class，函数为read "4" + 2    read "4" ::Int

#### Guard
+ 由跟在函数名及参数后面的`|`表示，一个guard表达式就是一个布尔表达式，为True使用其对应函数体，为False就计算下一个guard表达式。
+ 一般`otherwise`作为最后一个guard表达式，它的定义就是`otherwize = True`。

#### where let
+ where 绑定是在函数底部定义名字，对包括所有 guard 在内的整个函数可见。let 绑定则是个表达式，允许你在任何位置定义局部变量，而对不同的 guard 不可见。
 

#### . $ () operaters

+ ."符号是函数组合符号。Haskell中，函数调用后的空格从语义上来讲，就是一个函数调用的功能。

		f x=x

f 3 -- return 3,f和3之间的空格可以理解成一个类似infix运算符的东西。(" " f 3)，当然，实际上不能这样调用

--也可以用 `f $ 3`
f $ 3 -- return 3，$是一个真正的infix运算符（函数），可以写成(($) f 3)，注意，要加括号！！！

--而$和空格的区别在于优先级
--在ghci里，输入:info $
--得到

($) :: (a -> b) -> a -> b       -- Defined in `GHC.Base'
infixr 0 $  --可以看到,"$"作为右结合infix运算符，其优先级是最低的0
--所以，在使用"$"的场合，都是为了让右边的表达式先求值，作为"$"的第二个参数
--因此，下面的代码
ltree $ mapTree (+2) tree
--等同于
ltree (mapTree (+2) tree)

--再看看"."，它是一个infixl运算符，优先级为9(Haskell中infix level只有0-9，9已经是最高的)，并且是左结合
--在ghci中输入":info ."的结果
(.) :: (b -> c) -> (a -> b) -> a -> c   -- Defined in `GHC.Base'
infixr 9 .

--所以下面的代码：

ltree . mapTree (+2) tree
--等同于
(ltree.mapTree) (+2) tree
--即
((.) ltree mapTree) (+2) tree
--看到了吧，看"."的类型声明
--ltree为b->c是对的，但mapTree不符合，所以类型不匹配报错

--顺便，给个"."的正确用法，这样应该就对了
(ltree . (mapTree (+2))) tree



#### Data定义
+ 普通定义：

		--Book Bid Price BookName Authors
		data Book = Book Int Int String [String]
		            deriving (Show,Eq)
+ 使用`type`别名定义左边为别名右边卫类型名

		type Bid = Int
		type Price = Int
		type BookName = String
		type Authors = [String]
		data Book = Book Bid Price BookName Authors
				    deriving (Show,Eq)
+ 使用函数名

		data Book = Book {bid::Int,price::Int,name::String,authors::[String]}				    
		            deriving (Show,Eq)

+ 枚举

		data Color = Red 
		           | Blue
		           | Black
		           | White
		           | Yellow
		           | Indigo
		           | Green
		             deriving (Show,Eq)

