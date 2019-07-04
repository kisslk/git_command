Linux sed 命令是利用脚本来处理文本文件。

sed 可依照脚本的指令来处理、编辑文本文件。

Sed 主要用来自动编辑一个或多个文件、简化对文件的反复操作、编写转换程序等。

#### 语法
> sed [-hnV][-e<script>][-f<script文件>][文本文件]

#### 参数说明：

- -e<script>或--expression=<script> 以选项中指定的script来处理输入的文本文件。
- -f<script文件>或--file=<script文件> 以选项中指定的script文件来处理输入的文本文件。
- -h或--help 显示帮助。
- -n或--quiet或--silent 仅显示script处理后的结果。
- -V或--version 显示版本信息。


#### 动作说明：

- a ：新增， a 的后面可以接字串，而这些字串会在新的一行出现(目前的下一行)～
- c ：取代， c 的后面可以接字串，这些字串可以取代 n1,n2 之间的行！
- d ：删除，因为是删除啊，所以 d 后面通常不接任何咚咚；
- i ：插入， i 的后面可以接字串，而这些字串会在新的一行出现(目前的上一行)；
- p ：打印，亦即将某个选择的数据印出。通常 p 会与参数 sed -n 一起运行～
- s ：取代，可以直接进行取代的工作哩！通常这个 s 的动作可以搭配正规表示法！例如 1,20s/old/new/g 就是啦！


#### 实例
在testfile文件的第四行后添加一行，并将结果输出到标准输出，在命令行提示符下输入如下命令：

> sed -e 4a\newLine testfile 

首先查看testfile中的内容如下：

> $ cat testfile #查看testfile 中的内容  
> HELLO LINUX!  
> Linux is a free unix-type opterating system.  
> This is a linux testfile!  
> Linux test 
使用sed命令后，输出结果如下：

> $ sed -e 4a\newline testfile #使用sed 在第四行后添加新字符串  
> HELLO LINUX! #testfile文件原有的内容  
> Linux is a free unix-type opterating system.  
> This is a linux testfile!  
> Linux test  
> newline 

摘自：[菜鸟教程](https://www.runoob.com/linux/linux-comm-sed.html)
