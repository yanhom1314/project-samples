#### �������
+ [Clojure](http://clojure.org) Clojure����վ;
+ [Leiningen](http://leiningen.org) Clojure�Ĺ�������;
+ [Clojars](http://clojars.org) Clojure���²ֿ�;

#### Leiningen
+ ����һ����Ŀ:`lein new clj-hello`
+ ����:`lein help`
+ ����:`lein compile`
+ �����һ����������ִ�е�Jar:`lein uberjar`����targetĿ¼������*-standelon-*.jar����������`java -jar *-standelone-*.jar`

#### Clojars
+ �����ļ����ֿ⣺

		lein pom
		lein deploy clojars

#### ע��
ʹ��`;`��ע�ͣ�`;`�ĸ�������ע�͵���Ҫ�ԡ�
;      ����ע��
;;     ����ע��
;;;    macro����defmulti��ע��
;;;;   nsע��
����ע��ʹ�ú�`comment`:`(comment "
111
222
333
")`

#### 

#### ������ݹ�
+ doseqִ�в����󷵻�nil

	;;��ӡ0-4
	(doseq [x (range 5)]
	  (println x))
	>0
	>1
	>2
	>3
	>4
	>nil
	
+ for����vector

	(for [x (range 5)]
	  (println x))
	>0
	>1
	>2
	>3
	>4
	>(0 1 2 3 4)

+ loop�ݹ鴦��

	(defn fac [n]
	  (loop [cnt n res 1]
	    (if (<= cnt 0) res
		  (recur (dec cnt) (* cnt res)))))	
	(fac 10)

#### ���������
+ ��������:`(fn [n] (println n))`
+ �󶨱���:`(def b-n "bind value.")`
+ �󶨺���:`(def b-n-len (fn [] (count b-n)))`��`(defn b-n-len [] (count b-n))`��ͬ
+ �󶨺���Ҳ����ʹ��`#(...)`�ķ�ʽ:`(def b-n-len #(count b-n))`
+ �����п���ʹ��`%`��ʾһ��������Ҳ������`%[���к�]`��ʾ�ڼ�������:`(def ex-len #(+ (count %1) (count %2))`
+ �̶�����:

	(defn add [v1 v2 v3 v4]
	  (+ v1 v2 (if v3 v3 0) (if v4 v4 0)))
	;;����ʹ�������ģʽƥ�䷽ʽ����
	(defn add
	  [v1 v2] (+ v1 v2)
	  [v1 v2 v3] (+ v1 v2 v3)
	  [v1 v2 v3 v4] (+ v1 v2 v3 v4))

+ �ɱ����:

	(defn add [v1 v2 & others]
	  (+ v1 v2 (if others (reduce + 0 others) 0)))


#### ��(macro)
+ `->`:�������ʽ������һ�����ʽ�����磺

    (-> "a b c d" .toUpperCase (.replace "A" "X") (.split " ") first)
	>"X"    ;;"a b c d"��ĸת�ɴ�дȻ��A��X�滻Ȼ���Կո�(\s)�ָ�Ȼ�󷵻ص�һ����ĸ

	(use '[clojure.walk :only [macroexpand-all]])
	(macroexpand-all '(-> "a b c d" .toUpperCase (.replace "A" "X") (.split " ") first))
	>(first (. (. (. "a b c d" toUpperCase) replace "A" "X") split " "))   
	;;��(first (.split (.replace (.toUpperCase "a b c d") "A" "X") " "))����ͬ��
	;;����б����ж�����ʽ�����һ�����ʽ��Ϊ�ڶ������ʽ�ĵڶ�����뵽��ʽ�У��Դ����ƣ�

+  [->](http://clojuredocs.org/clojure.core/->)�ĵ�
