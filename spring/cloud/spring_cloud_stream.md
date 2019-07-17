# Spring Cloud Stream

## What Are Spring Cloud Stream

[官网](https://spring.io/projects/spring-cloud-stream#overview)

> Spring Cloud Stream is a framework for building highly scalable event-driven microservices connected with shared messaging systems.

- 用于构建连接消息系统、事件驱动、高扩展的微服务的框架

> The framework provides flexible programming model built on already established and familiar Spring idioms and best practices, including support for persistent pub/sub semantics, consumer groups, and stateful partitions.

- 提供了灵活的编程模型
  - 类似Spring MVC 的编程模型
- 构建在`Spring Message`和`Sprign Integration`上



## Spring Message

[官网](<https://docs.spring.io/spring-integration/docs/5.1.6.RELEASE/reference/html/#spring-integration-core-messaging>)

### Message Core Concepts 

- `Message`

  - 一致的消息编程模型

  - ```java
    public interface Message<T> {
    	T getPayload();
    	MessageHeaders getHeaders();
    }
    
    ```

  - 

  ![Message](<https://docs.spring.io/spring-integration/docs/5.1.6.RELEASE/reference/html/images/message.jpg>)

- `MessageChannel`

  - 消息通道

  ```java
  public interface MessageChannel {
  	boolean send(Message<?> message, long timeout);
  }
  
  ```

  

  - ![Message](<https://docs.spring.io/spring-integration/docs/5.1.6.RELEASE/reference/html/images/channel.jpg>)

- `MessageHandler`

  - 消息处理器

  ```java
  public interface MessageHandler {
  	void handleMessage(Message<?> message) throws MessagingException;
  }
  ```

  

- 具有和Spring MVC似的编程模型

  |                | Header           | Body           |
  | -------------- | ---------------- | -------------- |
  | Spring Message | `@Header`        | `@Payload`     |
  | Spring MVC     | `@RequestHeader` | `@RequestBody` |
  |                |                  |                |

  > Header有什么作用？
  >
  > - 过滤非法请求
  >   - `Content-Type : application/json`
  >   - `Accept: application/json`
  > - Rest一定是JSON吗？ 

## Spring Integration

[官网](<https://spring.io/projects/spring-integration#overview>)

- Martin Fowler 书籍 **Enterprise Integration Patterns**
- 继承、扩展`Spring Message`

### Integration Core Concepts 

- `MessageChannel`
  - `SubscribableChannel`
    - `PublishSubscribeChannel`
    - `DirectChannel`
    - `ExecutorChannel`
  - `PollableChannel`
    - `QueueChannel`
      - `PriorityChannel`
- `MessageDispatcher` 消息分发器
  - `BroadcastingDispatcher`
  - `UnicastingDispatcher`

## Spring Integration & Spring Message Demo

- 单播负载Demo 演示
- 广播Demo演示

## Spring Cloud Stream Corn 

- **Destination Binders**
  -  负责和外部的消息系统进行集成
  - `Binder`
- **Destination Bindings**
  - 桥接外部的消息系统和应用
  - 由Destination Binders创建
  - `Binding<MessageChannel>`
- **Message**:
  - 一致的消息编程模型
  - `Message`

- **Programming Model**

  ![Message](<https://raw.githubusercontent.com/spring-cloud/spring-cloud-stream/master/docs/src/main/asciidoc/images/SCSt-overview.png>)

## Spring Cloud Stream Demo

- 简单Demo
- `Header`过滤Demo
- `Payload` SPEL表达式、校验Demo
- 广播Demo



## Key Code

- 核心类

  - `BindingBeansRegistrar`
  - `BindingTargetFactory`
  - `BindableProxyFactory`
    - `BindingService`
  - `SendingHandler`
  - `StreamListenerMessageHandler`
  - `StreamListenerAnnotationBeanPostProcessor`
  
- 创建`MessageChannle` - input 和 output

  ```java
  	@Override
  	public SubscribableChannel createInput(String name) {
  		DirectWithAttributesChannel subscribableChannel = new DirectWithAttributesChannel();
  		subscribableChannel.setAttribute("type", Sink.INPUT);
  		this.messageChannelConfigurer.configureInputChannel(subscribableChannel, name);
  		return subscribableChannel;
  	}
  
  	@Override
  	public SubscribableChannel createOutput(String name) {
  		DirectWithAttributesChannel subscribableChannel = new DirectWithAttributesChannel();
  		subscribableChannel.setAttribute("type", Source.OUTPUT);
  		this.messageChannelConfigurer.configureOutputChannel(subscribableChannel, name);
  		return subscribableChannel;
  	}
  ```

  

- 为`MessageChannle`绑定`MessageHandler`

  - out put channel绑定

    ```java
    public final Binding<MessageChannel> doBindProducer(final String destination,
    			MessageChannel outputChannel, final P producerProperties)
    		...
    		((SubscribableChannel) outputChannel)
    				.subscribe(new SendingHandler(producerMessageHandler,
    						HeaderMode.embeddedHeaders
    								.equals(producerProperties.getHeaderMode()),
    						this.headersToEmbed, useNativeEncoding(producerProperties)));
    
    		Binding<MessageChannel> binding = new DefaultBinding<MessageChannel>(destination,
    				outputChannel, producerMessageHandler instanceof Lifecycle
    						? (Lifecycle) producerMessageHandler : null) {
    		...
    		};
    		...
    		return binding;
    	}
    
    ```

  - input channel绑定

    ```java
    @Override
    	public final void afterSingletonsInstantiated() {
    				...
    				StreamListenerMessageHandler streamListenerMessageHandler = new StreamListenerMessageHandler(
    						invocableHandlerMethod,
    						resolveExpressionAsBoolean(mapping.getCopyHeaders(),
    								"copyHeaders"),
    						this.springIntegrationProperties
    								.getMessageHandlerNotPropagatedHeaders());
    			...
    			this.applicationContext
    					.getBean(mappedBindingEntry.getKey(), SubscribableChannel.class)
    					.subscribe(handler);
    		}
    		...
    	}
    
    ```

    
