#### 相关内容
+ [Clojure](http://clojure.org) Clojure的主站;
+ [Leiningen](http://leiningen.org) Clojure的构建工具;
+ [Clojars](http://clojars.org) Clojure的新仓库;

#### Leiningen
+ 创建一个项目:`lein new clj-hello`
+ 帮助:`lein help`
+ 编译:`lein compile`
+ 打包成一个单独可以执行的Jar:`lein uberjar`会在target目录下生成*-standelon-*.jar，可以运行`java -jar *-standelone-*.jar`

#### Clojars
+ 部署文件到仓库：

		lein pom
		lein deploy clojars

#### 注释
使用`;`来注释，`;`的个数代表注释的重要性。
;      单行注释
;;     函数注释
;;;    macro或者defmulti的注释
;;;;   ns注释
多行注释使用宏`comment`:`(comment "
111
222
333
")`

#### 

#### 迭代与递归
+ doseq执行操作后返回nil

	;;打印0-4
	(doseq [x (range 5)]
	  (println x))
	>0
	>1
	>2
	>3
	>4
	>nil
	
+ for返回vector

	(for [x (range 5)]
	  (println x))
	>0
	>1
	>2
	>3
	>4
	>(0 1 2 3 4)

+ loop递归处理

	(defn fac [n]
	  (loop [cnt n res 1]
	    (if (<= cnt 0) res
		  (recur (dec cnt) (* cnt res)))))	
	(fac 10)

#### 函数与变量
+ 匿名函数:`(fn [n] (println n))`
+ 绑定变量:`(def b-n "bind value.")`
+ 绑定函数:`(def b-n-len (fn [] (count b-n)))`与`(defn b-n-len [] (count b-n))`相同
+ 绑定函数也可以使用`#(...)`的方式:`(def b-n-len #(count b-n))`
+ 函数中可以使用`%`表示一个参数，也可以用`%[序列号]`表示第几个参数:`(def ex-len #(+ (count %1) (count %2))`
+ 固定参数:

	(defn add [v1 v2 v3 v4]
	  (+ v1 v2 (if v3 v3 0) (if v4 v4 0)))
	;;建议使用下面的模式匹配方式定义
	(defn add
	  [v1 v2] (+ v1 v2)
	  [v1 v2 v3] (+ v1 v2 v3)
	  [v1 v2 v3 v4] (+ v1 v2 v3 v4))

+ 可变参数:

	(defn add [v1 v2 & others]
	  (+ v1 v2 (if others (reduce + 0 others) 0)))


#### 宏(macro)
+ `->`:将多个形式串练成一个表达式，例如：

    (-> "a b c d" .toUpperCase (.replace "A" "X") (.split " ") first)
	>"X"    ;;"a b c d"字母转成大写然后将A用X替换然后以空格(\s)分割然后返回第一个字母

	(use '[clojure.walk :only [macroexpand-all]])
	(macroexpand-all '(-> "a b c d" .toUpperCase (.replace "A" "X") (.split " ") first))
	>(first (. (. (. "a b c d" toUpperCase) replace "A" "X") split " "))   
	;;与(first (.split (.replace (.toUpperCase "a b c d") "A" "X") " "))是相同的
	;;如果列表中有多个表达式，则第一个表达式作为第二个表达式的第二项插入到形式中，以此类推；

+  [->](http://clojuredocs.org/clojure.core/->)文档
