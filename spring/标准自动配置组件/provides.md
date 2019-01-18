### META-INF下spring.provides

- Spring Boot 旨在简化应用程序创建入门的过程。 Spring Boot 模块是引导库，其中包含启动特定功能所需的所有相关传递依赖关系的集合。
 每个启动器都有一个特定文件，其中包含所有提供的依赖关系的列表—— spring.provides
 
- 比如spring-boot-starter-test定义的provides:
  ```properties
    provides: spring-test, spring-boot, junit, mockito, hamcrest-library

  ```

- 这告诉我们，通过在我们的构建中包含 spring-boot-starter-test 作为依赖，我们将自动获得 spring-test，spring-boot，junit，mockito
 和 hamcrest-library。 这些库将为我们提供所有必要的事情，以便开始为我们开发的软件编写应用程序测试，而无需手动将这些依赖关系手动添加到构建文件中。

