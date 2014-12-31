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
