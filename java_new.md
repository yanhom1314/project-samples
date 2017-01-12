## JAVA5.0新特性
#### 1.  Annotation注解
以@interface定义
使用枚举类型java.lang.annotation.RetentionPolicy定义@Retention
使用枚举类型java.lang.annotation.ElementType定义@Target
例如：
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UserAnnotation {
   ...
}
Annotation在AOP编程中有非常重要的作用，在Hibernate，Spring、Seam、EJB3，Struts2等项目中有非常广泛的应用。
AOP
源代码级
Java Dynamic Proxy
ASM

字节码级
直接修改Class字节文件
编写hook程序

AOP框架
Aspectj

#### 2.  Enum枚举
+ a.  构造函数为私有
+ b.  可以有属性和方法,例如：

    public enum GernationType {
        China(1), English(2);
        Integer s;
        GernationType(Integer value) {s = value;}
        public Integer value() { return s;}
        public static GernationType create(Integer s) throws EnumConstantNotPresentException {
            if (China.value().equals(s)) {return China;}
     else if (English.value().equals(s)) {
                return English;
            } else
                throw new EnumConstantNotPresentException(GernationType.class,"create failed!");
        }
    }

与switch结合使用的范例：

    GernationType flag = GernationType.create(2);
    switch (flag)
    {
        case China:
            System.out.println("It's China.");
            break;
        case English:
            System.out.println("It's English.");
            break;
        default: {
            System.out.println("It's is default.");
        }
    }

#### 3.  Generic泛型 
获取泛型，最主要就是使用ParameterizedType的getActualTypeArguments()方法，
getActualTypeArguments方法中可以指定获取的泛型类型所在的索引；
一种是使用父类为泛型类通过继承的方式获取泛型的类型
ParameterizedType pt =(ParameterizedType) getClass().getGenericSuperclass();
return pt.getActualTypeArguments()[0];
一种是获取域的参数泛型类型
ParameterizedType pt=(ParameterizedType) getClass().getField("name").getGenericType();
return pt. .getActualTypeArguments()[0];

#### 4.  修改中文与英文
重命名$JDK_HOME/jre/lib/fontconfig.properties.src为fontconfig.properties，编辑其内容：
`增加中文字体映射`
filename.YaHei=msyh.ttf
filename.YaHei_Bold=msyhbd.ttf
filename.YaHei_Italic=msyh.ttf
filename.YaHei_Bold_Italic=msyhbd.ttf
`增加英文体映射`
filename.Monaco=MONACO.TTF
filename.Monaco_Bold=MONACO.TTF
filename.Monaco_Italic=MONACO.TTF
filename.Monaco_Bold_Italic=MONACO.TTF

`修改字体映射`
monospaced.plain.alphabetic=Monaco 
monospaced.plain.chinese-ms950= YaHei
#修改search优先级
#sequence.monospaced.GBK=chinese-ms936,alphabetic,dingbats,symbol,chinese-ms936-extb
sequence.monospaced.GBK=alphabetic,chinese-ms936,dingbats,symbol,chinese-ms936-extb
重启Netbeans选择monospaced字体后，代码中就会以雅黑(YaHei)显示中文以Monaco显示英文。



## Java7

#### 新特性Fork/Join

整型的支持
int i = 0b0011;
int j = 0b0011_0101;
以0B或者0b开头的二进制代码可以给整型赋值，并且也支持以下划线_为分割的二进制整型支持，为的是代码有更好的可读写。

switch支持String
switch(name){
  case "1":break;
  case "2":break;
}
泛型支持深化
Map<String,String> map=new HashMap<>();

多层次catch
try{…}catch(Exception | IOException e){e.printStackTrace();}

自动资源管理
凡是继承了Closeable这个类，系统在方法退出的时候都会自动的关闭资源。

FileSystem的API支持
Java7对文件系统支持较为广泛，无论是copy， move，delete等操作，还是文件系统的监视，递归，获取文件的元数据都有了大大的提高。


## Java8
#### 方法引用
+ ObjectReference::methodName
+ 静态方法：ClassName::methodName。如 Object ::equals
+ 实例方法：Instance::methodName。如Object obj=new Object();obj::equals;
+ 构造函数：ClassName::new

#### Lamdba
+ 类似：(parameters) -> expression 或者 (parameters) -> { statements; } 
+ Collection集合的改进
* 内部迭代：

    Arrays.asList(1,2,3,4,5,6).forEach(System.out::println);//使用内部迭代，不再使用for(T:Collection<T>)方式

* Stream API:
    
    //Filter
    Arrays.asList(1, 2, 3, 4, 5, 6).stream().filter(i -> i > 3 && i < 6);
    //Map
    Arrays.asList(1, 2, 3, 4, 5, 6).stream().map(i -> i + 100).forEach(System.out::println);
    //Count
    long count=Arrays.asList(1, 2, 3, 4, 5, 6).stream().map(i -> String.valueOf(i + 100)).count();
    //Collect
    List<String> list = Arrays.asList(1, 2, 3, 4, 5, 6).stream().map(i -> String.valueOf(i + 100)).collect(Collectors.toCollection(ArrayList<String>::new));    

* Stream 顺序执行与并行执行：
    
    list.stream().forEach(System.out::println);
    list.stream().parallel().forEach(System.out::println);   

* Java 8 Stream可以比较Java 7中提供的Fork/Join应用硬件并行的分解框架API。


#### 函数式接口
+ 使用`@FunctionalInterface作为注解`，注解不是必须的，只要接口符合函数式接口的标准（即只包含一个方法的接口），虚拟机会自动判断，但是建议使用。

#### 默认方法
+ 接口可以有实现方法，以关键字`default`定义。

    public interface A {
        public default void say() { System.out.println("Hi A!");}
    }

    public interface B extends A{
        public default void say() { System.out.println("Hi B!");}
    }

    public class C implements A,B {

        public static void main(String[] args){
            new C().say();//B -> "Hi B!"
        }
    }
* 如果A与B没有继承关系，则C必须提供`say()`方法的实现，以消除二义性，例如:

    public class C implements A,B {
        public void say() { A.super.say();}

        public static void main(String[] args){
            new C().say();//A -> "Hi A!"
        }
    }
* 多重继承，由于接口默认方法的引入，则在接口多重继承时的二义冲突，就可以以上例来解决。   

#### 类型注解
+ 在java 8之前，注解只能是在声明的地方所使用，比如类，方法，属性；java 8里面，注解可以应用在任何地方，比如：

    /*@NonNull*/ Object obj=null;




