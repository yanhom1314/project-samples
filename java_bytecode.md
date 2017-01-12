回忆一下二进制表示，Java位操作符总结。
=========
+ `原码`：符号位用0表示正号，用1表示负号，数值一般用二进制形式表示
+ `反码`：机器数的反码可由原码得到。如果机器数是正数，则该机器数的反码与原码一样；如果机器数是负数，则该机器数的反码是对它的原码（符号位除外）各位取反而得到的。
+ `补码`：机器数的补码可由原码得到。如果机器数是正数，则该机器数的补码与原码一样；如果机器数是负数，则该机器数的补码是对它的原码（除符号位外）各位取反，并在未位加1而得到的。
+ `正数`：用原码表示，原码=反码=补码；
+ `负数`：用补码表示，反码=~原码(除符号位)、补码=反码+1；
        
        比如：System.out.println(Integer.toBinaryString(-5));
        打印：1111 1111 1111 1111 1111 1111 1111 1011  不用想了肯定是32位
        过程是这样的：
        一个负数，比如-5，它的二进制在里面是这样表示
        1000 0000 0000 0000 0000 0000 0000 0101
        原码：0000 0000 0000 0000 0000 0000 0000 0101
        反码：1111 1111 1111 1111 1111 1111 1111 1010
        补码：1111 1111 1111 1111 1111 1111 1111 1011
        得到打印的内容

# 一元操作符
### 非(~)
+ 对每个二进制位的内容求反，即1变成0，0变成1
+ 测试负数：

        int a = -5;//101;
        System.out.println(~a);
        打印：4
        过程是这样的，首先表示出来这个负数
        原码：0000 0000 0000 0000 0000 0000 0000 0101
        反码：1111 1111 1111 1111 1111 1111 1111 1010
        补码：1111 1111 1111 1111 1111 1111 1111 1011   //反码+1
        ~运算：0000 0000 0000 0000 0000 0000 0000 0100
        转为10进制得到4

+ 测试正数:

        int a = 5;//101;
        System.out.println(~a);
        打印：-6
        首先表示出来这个正数：
        0000 0000 0000 0000 0000 0000 0000 0101
        各位取反得到：
        1111 1111 1111 1111 1111 1111 1111 1010
        这个代表的就是-6了，至于为什么看最上面

# 二元操作符       
### 与(&)
+ 二进制位进行`与`操作，两个都为1才为1，其他情况均为0
+ 测试：

        System.out.println(5&6);
        打印：4
        过程：
        5表示成：0000 0000 0000 0000 0000 0000 0000 0101
        6表示成：0000 0000 0000 0000 0000 0000 0000 0110
        进行 & ：0000 0000 0000 0000 0000 0000 0000 0100
        得到：4

### 或(|)
+ 二进制位进行`或`操作，两个都为0才为0，其他情况均为1
+ 测试：

        System.out.println(5|6);
        打印：7
        过程：
        5表示成：0000 0000 0000 0000 0000 0000 0000 0101
        6表示成：0000 0000 0000 0000 0000 0000 0000 0110
        进行 | ：0000 0000 0000 0000 0000 0000 0000 0111
        得到：7

### 异或(^)
+ 判断对应二进制位值是否相同为0，否则为1
+ 测试：

        System.out.println(5^6);
        打印：3
        过程：
        5表示成：0000 0000 0000 0000 0000 0000 0000 0101
        6表示成：0000 0000 0000 0000 0000 0000 0000 0110
        进行 ^ ：0000 0000 0000 0000 0000 0000 0000 0011
        得到：3
        
### 左移(<<)
+ 左移，右面填充0
+ 测试：

        System.out.println(5<<1);
        打印：10
        5表示成：0000 0000 0000 0000 0000 0000 0000 0101
        进行 <<1 操作：0000 0000 0000 0000 0000 0000 0000 1010

### 右移(>>)
+ 右移，正数左面填充0，负数左面补1
+测试：

        System.out.println(5>>1);
        打印：2
        5表示成：0000 0000 0000 0000 0000 0000 0000 0101
        进行 >>1 操作：0000 0000 0000 0000 0000 0000 0000 0010
        
        System.out.println(-5>>1);
        打印：-3
        -5表示成：1111 1111 1111 1111 1111 1111 1111 1011
        进行 >>1 操作：1111 1111 1111 1111 1111 1111 1111 1101

### 无符号右移(>>>)
+ 无符号算数右移，左面补0
+ 测试：

        System.out.println(5>>>1);
        打印：2
        5表示成：0000 0000 0000 0000 0000 0000 0000 0101
        进行 >>1 操作：0000 0000 0000 0000 0000 0000 0000 0010
        
        System.out.println(-5>>>1);
        打印：2147483645
        -5表示成：1111 1111 1111 1111 1111 1111 1111 1101
        进行 >>1 操作：0111 1111 1111 1111 1111 1111 1111 1101

## Java字节码指令集  

#### 字节码指令集
　　Java虚拟机的指令由一个字节长度的、代表着某种特定操作含义的操作码（Opcode）以及跟随其后的零至多个代表此操作所需参数的操作数（Operands）所构成。
　　对于大部分为与数据类型相关的字节码指令，他们的操作码助记符中都有特殊的字符来表明专门为哪种数据类型服务：i代表对int类型的数据操作，l代表long，s代表short，b代表byte，c代表char，f代表float，d代表double，a代表reference。

#### 加载和存储指令：
+ 将一个局部变量加载到操作栈的指令包括有：`iload、iload_<n>、lload、lload_<n>、fload、fload_<n>、dload、dload_<n>、aload、aload_<n>`
+ 将一个数值从操作数栈存储到局部变量表的指令包括有：`istore、istore_<n>、lstore、lstore_<n>、fstore、fstore_<n>、dstore、dstore_<n>、astore、astore_<n>`
+ 将一个常量加载到操作数栈的指令包括有：`bipush、sipush、ldc、ldc_w、ldc2_w、aconst_null、iconst_m1、iconst_<i>、lconst_<l>、fconst_<f>、dconst_<d>`
扩充局部变量表的访问索引的指令：wide
#### 运算指令：
加法指令：`iadd、ladd、fadd、dadd`
减法指令：`isub、lsub、fsub、dsub`
乘法指令：imul、lmul、fmul、dmul
除法指令：idiv、ldiv、fdiv、ddiv
求余指令：irem、lrem、frem、drem
取反指令：ineg、lneg、fneg、dneg
位移指令：ishl、ishr、iushr、lshl、lshr、lushr
按位或指令：ior、lor
按位与指令：iand、land
按位异或指令：ixor、lxor
局部变量自增指令：iinc
比较指令：dcmpg、dcmpl、fcmpg、fcmpl、lcmp
　　类型转换指令：
　　Java虚拟机对于宽化类型转换直接支持，并不需要指令执行，包括：
int类型到long、float或者double类型
long类型到float、double类型
float类型到double类型
　　窄化类型转换指令包括有：i2b、i2c、i2s、l2i、f2i、f2l、d2i、d2l和d2f。但是窄化类型转换很可能会造成精度丢失。
　　对象创建与操作指令：
创建类实例的指令：new
创建数组的指令：newarray，anewarray，multianewarray
访问类字段（static字段，或者称为类变量）和实例字段（非static字段，或者成为实例变量）的指令：getfield、putfield、getstatic、putstatic
把一个数组元素加载到操作数栈的指令：baload、caload、saload、iaload、laload、faload、daload、aaload
将一个操作数栈的值储存到数组元素中的指令：bastore、castore、sastore、iastore、fastore、dastore、aastore
取数组长度的指令：arraylength
检查类实例类型的指令：instanceof、checkcast
　　操作数栈管理指令：
　　Java虚拟机提供了一些用于直接操作操作数栈的指令，包括：pop、pop2、dup、dup2、dup_x1、dup2_x1、dup_x2、dup2_x2和swap；
　　控制转移指令：
条件分支：ifeq、iflt、ifle、ifne、ifgt、ifge、ifnull、ifnonnull、if_icmpeq、if_icmpne、if_icmplt, if_icmpgt、if_icmple、if_icmpge、if_acmpeq和if_acmpne。
复合条件分支：tableswitch、lookupswitch
无条件分支：goto、goto_w、jsr、jsr_w、ret
　　方法调用和返回指令：
invokevirtual指令用于调用对象的实例方法，根据对象的实际类型进行分派（虚方法分派），这也是Java语言中最常见的方法分派方式。
invokeinterface指令用于调用接口方法，它会在运行时搜索一个实现了这个接口方法的对象，找出适合的方法进行调用。
invokespecial指令用于调用一些需要特殊处理的实例方法，包括实例初始化方法（§2.9）、私有方法和父类方法。
invokestatic指令用于调用类方法（static方法）。
而方法返回指令则是根据返回值的类型区分的，包括有ireturn（当返回值是boolean、byte、char、short和int类型时使用）、lreturn、freturn、dreturn和areturn，另外还有一条return指令供声明为void的方法、实例初始化方法、类和接口的类初始化方法使用
　　抛出异常指令：
athrow
 关于Java虚拟机中的字节码指令
源代码经过编译器编译之后便会生成一个字节码文件，字节码是一种二进制的类文件，它的内容是JVM的指令，而不像C、C++经由编译器直接生成机器码。我们不用担心生成的字节码文件的兼容性，因为所有的JVM全部遵守Java虚拟机规范，也就是说所有的JVM环境都是一样的，这样一来字节码文件可以在各种JVM上运行。 当然也包括KVM。
每一个线程都有一个保存帧的栈。在每一个方法调用的时候创建一个帧。一个帧包括了三个部分：操作栈，局部变量数组，和一个对当前方法所属类的常量池的引用。
局部变量数组也被称之为局部变量表，它包含了方法的参数，也用于保存一些局部变量的值。参数值得存放总是在局部变量数组的index0开始的。如果当前帧是由构造函数或者实例方法创建的，那么该对象引用将会存放在location0处，然后才开始存放其余的参数。
局部变量表的大小由编译时决定，同时也依赖于局部变量的数量和一些方法的大小。操作栈是一个（LIFO）栈，用于压入和取出值，其大小也在编译时决定。某些opcode指令将值压入操作栈，其余的opcode指令将操作数取出栈。使用它们后再把结果压入栈。操作栈也用于接收从方法中返回的值。
以HelloWorld程序为例，经过命令：
    `E:\JavaExe>javap -c HelloWorld>HelloWorld.bytecode`
就会在目录下生成一个字节码文件，用编辑器打开后
 
    Compiled from "HelloWorld.java"
    class HelloWorld extends java.lang.Object{
    public HelloWorld(java.lang.String,int);
      Code:
       0:      aload_0
       1:      invokespecial #1; //Method java/lang/Object."<init>":()V
       4:      aload_0
       5:      ldc   #2; //String
       7:      putfield   #3; //Field name:Ljava/lang/String;
       10:    aload_0
       11:    iconst_0
       12:    putfield   #4; //Field idNumber:I
       15:    aload_0
       16:    aload_1
       17:    putfield   #3; //Field name:Ljava/lang/String;
       20:    aload_0
       21:    iload_2
       22:    putfield   #4; //Field idNumber:I
       25:    aload_0
       26:    aload_1
       27:    iload_2
       28:    invokevirtual  #5; //Method StoreData:(Ljava/lang/String;I)V
       31:    return
     
    public void StoreData(java.lang.String,int);
      Code:
       0:      bipush    90
       2:      istore_2
       3:      return
     
    void print(AnotherClass);
      Code:
       0:      aload_1
       1:      bipush    10
       3:      putfield   #6; //Field AnotherClass.a:I
       6:      new #7; //class AnotherClass
       9:      dup
       10:    invokespecial #8; //Method AnotherClass."<init>":()V
       13:    astore_1
       14:    aload_1
       15:    bipush    20
       17:    putfield   #6; //Field AnotherClass.a:I
       20:    return
     
    }
 
以上是经过编译后的HelloWorld的字节码文件。我们可以对照源文件来查看一些重要的指令。
class HelloWorld
{
     private   String name = "";
     private  int idNumber = 0;
     public HelloWorld(String strName, int num)
     {
      
        name = strName;
        idNumber = num;
        StoreData(strName,num);
     }
  public  void StoreData(String str,int i)
  {
        i = 90;
  }
  void print(AnotherClass another)
  {
         another.a=10;
         another=new AnotherClass();
         another.a=20;
   }
 
}
class AnotherClass
{
    public int a = 0;
   
}
void print(AnotherClass);
  Code:
   0:      aload_1
   1:      bipush    10
   3:      putfield   #6; //Field AnotherClass.a:I
   6:      new #7; //class AnotherClass
   9:      dup
   10:    invokespecial #8; //Method AnotherClass."<init>":()V
   13:    astore_1
   14:    aload_1
   15:    bipush    20
   17:    putfield   #6; //Field AnotherClass.a:I
   20:    return
 
aload_1 把存放在局部变量表中索引1位置的对象引用压入操作栈
bipush 10 把整数10压入栈
putfield #2 把成员变量a的值设置成栈中的10，#2代表2号常量项
new #3 创建AnotherClass的对象，把引用放入栈
dup 复制刚放入的引用（这时存在着两个相同的引用）
invokespecial #4 通过其中的一个引用调用AnotherClass的构造器，初始化对象，让另一个相同引用指向初始化的对象，然后前一个引用（this）弹出栈
asstore_1 把引用保存到局部变量表中的索引1位置中，然后引用弹出栈
aload_1 把局部变量表中索引1处的值压入操作栈。
bipush 20 把整数20压入栈
putfield #2 把成员变量a的值设置成栈中的10
return 执行完毕退出
 
我们继续看构造函数中的一段代码：
public HelloWorld(java.lang.String,int);
  Code:
将该（this）对象压入操作栈，对于实例方法和构造函数的局部变量表来说第一个入口总是这个“this”。因为你需要访问一些实例中的方法和变量。
   0:      aload_0 
   调用该类的超类构造函数，因为所有类都继承与Java.lang.Object。而该类(HelloWorld)没有new函数操作，所以编译器提供必要的字节码来调用这些基类构造器。
   1:      invokespecial #1; //Method java/lang/Object."<init>":()V
   将该（this）对象压入操作栈
4:    aload_0
字符串
    5:    ldc   #2; //String abc
把栈中的name的值置为栈中的”abc”
   7:      putfield   #3; //Field name:Ljava/lang/String;
   同样，将this压入栈
   10:    aload_0
   将0压入栈。
   11:    iconst_0
   将idNumber置为栈中的0，就是上一句指令中的操作
   12:    putfield   #4; //Field idNumber:I
   将this压入栈
   15:    aload_0
   将位于局部变量表中位置1处的方法的形参strName压入栈
   16:    aload_1
   将name的值置为栈中的strName
   17:    putfield   #3; //Field name:Ljava/lang/String;
   将this压入栈，this总是位于局部变量表的index0处！
   20:    aload_0
   将位于局部变量表中位置2处的方法形参num压入栈
   21:    iload_2
   同17号操作，赋值
   22:    putfield   #4; //Field idNumber:I
   将this压入操作栈
   25:    aload_0
   将strName压入栈
   26:    aload_1
将num压入栈
   27:    iload_2
   调用方法StoreData
   28:    invokevirtual  #5; //Method StoreData:(Ljava/lang/String;I)V
   31:    return
  如果有()V 标志方法没有参数列表
我们观察发现，在每一个opcode指令的左边的位置序号都不是连续的。０，１，４，５，７，１０……为什么？
每一个方法都有一个对应得ByteCode序列，这些值对应着每一个opcode和其参数存放的序列中的某一个索引值。为什么这些索引不是顺序的？既然每一个指令占据一个字节，那索引为什么不是0，1，2呢？原因是：一些指令的参数占据了一些bytecode数组空间。比如：
Aload_0指令没有参数，所以占有一个字节，第二个指令invokespecial，由于它本身带有参数，结果它本身和参数分别就占据了一个位置，所以，上面的１过了就不是４。
 
    Aload_0 invokespecial 00 05 return
