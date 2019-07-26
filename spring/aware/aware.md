### Aware简介

- Spring中提供一些Aware相关接口，像是BeanFactoryAware、ApplicationContextAware、ResourceLoaderAware、ServletContextAware等等，
实作这些 Aware接口的Bean在被初始之后，可以取得一些相对应的资源，例如实作BeanFactoryAware的Bean在初始后，Spring容器将会 注入BeanFactory的实例，
而实作ApplicationContextAware的Bean，在Bean被初始后，将会被注入 ApplicationContext的实例等等。
Bean取得BeanFactory、ApplicationContextAware的目的，一般的目的就是要取得一些档案资源的存取、相关讯息资源或是那些被注入的实例所提供的机制，
例如ApplicationContextAware提供了publishEvent()方法，可以支持基 于Observer模式的事件传播机制。

### Environment
- 比如Environment. 实现EnvironmentAware可以获取Environment, 然后可以获取属性。
- 也可以解析属性
```java
   //Feign#FeignClientsRegistrar.java
   private String resolve(String value) {
	if (StringUtils.hasText(value)) {
		return this.environment.resolvePlaceholders(value);
	}
	return value;
   }

   //Sentinel#SentinelConfiguration.java
   @PostConstruct
   public void init() {
        String logDir = this.environment.getProperty("csp.sentinel.log.dir", "");
        if (StringUtils.hasText(logDir)) {
            System.setProperty("csp.sentinel.log.dir", logDir);
        }

   }	
```
