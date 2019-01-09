
### maven常用命令

- 设置新的版本号  
$mvn versions:set -DnewVersion=1.1.0  
- 当新版本号设置不正确时可以撤销新版本号的设置  
$mvn versions:revert  
- 确认新版本号无误后提交新版本号的设置  
$mvn versions:commit  

- 创建maven的普通Java项目  
$mvn archetype:create -DgroupId=packageName -DartifactId=projectName
- 创建Maven的web项目  
$mvn archetype:create -DgroupId=packageName -DartifactId=projectName -DarchetypeArtifactId=maven-archetype-webapp
- 反向生成maven项目的骨架  
$mvn archetype=generate
$mvn archetype:generate -DgroupId=otowa.user.dao -DartifactId=user-dao -Dversion=0.01-SNAPSHOT
- 编译源代码  
$mvn compile
- 编译测试代码  
$mvn test-compile
- 运行测试  
$mvn test
- 产生site  
$mvn site
- 打包  
$mvn package
- 在本地Repository中安装jar  
$mvn install
- 清除产生的项目  
$mvn clean
- 生成eclipse项目  
$mvn eclipse:eclipse
- 生成idea项目
$mvn idea:idea
- 组合使用goal命令，如只打包不测试  
$mvn -Dtest package
- 编译测试的内容  
$mvn test-compile
- 只打jar包  
$mvn jar:jar
- 只测试而不编译，也不测试编译  
$mvn test -skipping compile -skipping test-compile
- 清除eclipse的一些系统设置  
$mvn eclipse:clean
- 查看当前项目已被解析的依赖  
$mvn dependency:list
- 上传到私服  
$mvn deploy
- 强制检查更新，由于快照版本的更新策略（一天更新几次、隔断时间更新一次）存在，如果想强制更新就会用到此命令  
$mvn clean install-U
- 源码打包  
$mvn source:jar
$mvn source:jar-no-fork

- mvn compile与mvn install、mvn deploy的区别
>- mvn compile 编译类文件
>- mvn install 包含mvn compile、mvn package 然后上传到本地仓库
>- mvn deploy 包含mvn install 然后上传到私服

>> 一般使用情况是这样，首先通过cvs或svn、git下载代码到本机，然后执行mvn eclipse:eclipse生成eclipse项目文件，然后到处eclipse就行了，修改代码后执行mvn compile或mvn test检验，也可以下载eclipse的maven插件 

### 其他命令

- 显示版本信息  
$mvn -version/-v
- 创建mvn项目
$mvn archetype:create -DgroupId=com.odin -DartifactId=my-app
- 生成target目录，编译、测试代码，生成测试报告、生成jar/war文件  
$mvn package
- 运行项目于jetty上  
$mvn jetty:run
- 显示详细错误信息  
$mvn -e
- 验证工程是否正确，所有需要的资源是否可用  
$mvn validate
- 在集成测试可以运行的环境中处理和发布包  
$mvn integration-test
- 运行任何检查，验证包是否有效且达到质量标准  
$mvn verify
- 产生应用需要的任何额外的源代码，如xdoclet  
$vn denerate-sources
- 使用help插件的describe目标来输出maven help插件的信息  
$mvn help:describe -Dplugin=help
- 使用help插件输出完整的带有参数的目标列  
$mvn help:describe -Dplugin=help -Dfull
- 获取单个目标的信息，设置Mojo参数和plugin参数，此命令列出了compiler插件的compile目标的所有信息  
$mvn help:describe -Dplugin=exec -Dfull
- 看这个有效的（efective）POM，它暴露了maven的默认配置  
$mvn help:effective-pom
- 想要查看完整的依赖踪迹，包含哪些因为冲突或者其他原因被拒绝引入的构件，打开mven的调试标记运行  
$mvn install -X
- 给任何目标添加maven.test.skip属性就能跳过测试  
$mvn install -Dmaven.test.skip=true
- 构建装配maven assembly插件是一个用来创建你应用程序特有分发包的插件  
$mvn install assembly:assembly
- 生成wtp插件的web项目  
$mvn -Dwtpversion=1.0 eclipse:eclipse
- 清除eclipse项目的配置信息(web项目)  
$mvn -Dwtpversion=1.0 eclipse:clean
- 将项目转化为eclipse项目  
$mvn eclipse:eclipse
- mvn exec 命令可以执行项目中的main函数
>- 首先需要编译java工程：mvn compile
>- 不存在参数的情况下：mvn exec:java -Dexec.mainClass="***.Main"
>- 存在参数: mvn exec:java -Dexec.mainClass="***.Main" -Dexec.args="arg0 arg1 arg2"
>- 指定运行时库: mvn exec:java -Dexec.mainClass="***.Main" -Dexec.classpathScope=runtime

- 打印出已解决依赖的列表  
$mvn dependency:resolve
- 打印出整个依赖树  
$mvn depnedency:tree
- 在应用程序中使用多个存储库  
$mvn deploy:deploy-file -DgroupId=com -DartifactId=client -Dversion=0.1.0 -Dpackaging=jar -Dfile=d:\client-0.1.0.jar -DrepositoryId=maven-repository-inner -Durl=ftp://xxxxxxxx/opt/maven/repository/
```
<repositories>    
    <repository>     
        <id>Ibiblio</id>     
        <name>Ibiblio</name>     
        <url>http://www.ibiblio.org/maven/</url>   
    </repository>   
    <repository>     
        <id>PlanetMirror</id>     
        <name>Planet Mirror</name>     
        <url>http://public.planetmirror.com/pub/maven/</url>   
    </repository>  
</repositories>
```

- 发布第三方jar到本地库中  
$mvn install:install-file -DgroupId=com -DartifactId=client -Dversion=0.1.0 -Dpackaging=jar -Dfile=d:\client-0.1.0.jar  
-DdownloadSources=true  
-DdownloadJavadocs=true  


- 查看冲突
  - mvn dependency:tree -Dverbose -Dincludes=${name}:${name}  


