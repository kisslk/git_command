
##### 启动
- 加载spring.factories
- 读取SpringApplicationRunListeners.class
- 实例化所有的listener, 构造参数是SpringApplication.class和String.class
- 对listener进行排序
- 把所有listener（SpringApplicationRunListener.class）放入读取SpringApplicationRunListeners
- 发布ApplicationStartingEvent事件

##### 准备Environment
- 配置propertySources
- 配置profiles
- listenter监听ApplicationEnvironmentPreparedEvent事件
- 配置ignoreBeanInfo

##### 准备Context上下文
- 设置environment
- 设置resourceLoader和classLoader
- 启动ApplicationContextInitializer初始化器
  - ApplicationContextInitializer.initializa(context) Apollo在这里处理了Environment
  - 装载BeanFactoryPostProcesssor 
- listener发布ConfigurableApplicationContext时
- 注册springApplicationArguments到容器
- 装载context
  - 获取BeanDefinitionLoader
    - 获取BeanDefinitionReader(xml,annotatin)
  - 给loader设置resourceLoader和environment
- 发布ApplicationPreparedEvent事件
  - 获取application的listener
  - 如果listener继承了ApplicationContextAware
    - 给aware设置context上下文
    - 同时context添加listener
  - 发布事件

##### 刷新Context上下文
- ServletWebServerApplicationContext
  - prepareRefresh
    - 初始化propertySource(replace重新装配)
    - 设置environment缺失数据
    - context上下文用earlyApplicationEvents记录已发布的事件
  - 重新获取Context
    >- Tell the subclass to refresh the internal bean factory. 
  - prepareBeanFactory
    - beanFactory设置ClassLoader、BeanPostProcessor
    - 忽略EnvirommentAware，EmbeddedValueResolverAware, ResourceLoaderAware, ApplicationEventPublisherAware, MessageSourceAware, ApplicationContextAware
      - isExcludedFromDependencyCheck方法会使用这个忽略集合 todo什么过程调用的
    - 注册BeanFactory, ResourceLoader、ApplicationContext、ApplicationEnventPulisher
    - 注册Environment、SystemProperties、SysetemEnvironment
  - postProcessBeanFactory
    >-  Allows post-processing of the bean factory in context subclasses.
    >- Modify the application context's internal bean factory after its standard initialization.
    > All bean definitions will have been loaded, but no beans  will have been instantiated yet. 
    > This allows for registering special BeanPostProcessors etc in certain ApplicationContext implementations.  

    - 需子类实现
      - ServletWebServerApplicationContext
        - beanFacotry添加WebApplicationContextServletContextAwareProcessor 
        - 忽略ServletContextAware
  - invokeBeanFactoryPostProcessors
    >- Invoke factory processors registered as beans in the context.
    >- Instantiate and invoke all registered BeanFactoryPostProcessor beans,
  - registerBeanPostProcessors
    >- Register bean processors that intercept bean creation.
    >- Instantiate and invoke all registered BeanPostProcessor beans,
  - initMessageSource
  - initApplicationEventMulticaster
  - onRefresh
    >- Initialize other special beans in specific context subclasses.
  
    - ServletWebServerApplicationContext
      - createWebServer
        - TomcatWebServer 
        - 注册actuator-endpoint 
  - registerListeners
    >- Check for listener beans and register them.
    >- Publish early application events now that we finally have a multicaster... earlyApplicationEvents
  - finishBeanFactoryInitialization
    >- Finish the initialization of this context's bean factory, initializing all remaining singleton beans.
    
    - 

