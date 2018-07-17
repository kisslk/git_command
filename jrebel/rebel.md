
## jrebel配置方法

- Setting -> Compiler -> 勾选Build project automatically

- Ctrl + Shift + Alt + /  -> Registry -> 勾选compiler.automarke.allow.when.app.running

- rebel.xml配置文件 dir路径配置对应的class路径，main方法启动和tomcat启动对应的路径不一样的
  + main方法启动，class路径应该在/项目/target/classes目录
  + tomcat启动，class路径应该在tomcat/webapps目录下
