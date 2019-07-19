# Spring Cloud Stream

## Prerequisite outline

### Relation About Spring Framework Spring Boot Spring Cloud 

- Footstone - Spring Framework
- The Preference of Constructing Micro Services -Spring Boot
-  Spring Cloud provides tools for developers to quickly build some of the common patterns in distributed systems
  - Spring Cloud focuses on providing good out of box experience for typical use cases and extensibility mechanism to cover others.
    - Distributed/versioned configuration
    - Service registration and discovery
    - Routing
    - Service-to-service calls
    - Load balancing
    - Circuit Breakers
    - Distributed messaging

## Relation About SC SCN SCA 

- SC是一套规范，SCN ,SCA都是对SC规范的实现
- SCN VS SCA

![Spring Cloud 技术栈对比](C:\Users\X\Desktop\Spring Cloud 技术栈对比.png)

## Spring Cloud Alibaba DingTalk 2群

- Spring Cloud Alibab PMC & Commiter

![SCA钉钉群](C:\Users\X\Desktop\SCA钉钉群.png)

## Background

### RabbitMQ

- Producer Send Message

  ```java
  String message = "Hello RabbitMQ";
  channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
  ```

- Consumer consume Message

  ```java
   Consumer consumer = new DefaultConsumer(channel) {
              public void handleDelivery(String consumerTag, Envelope envelope,
                                         AMQP.BasicProperties properties, byte[] body)
                      throws IOException {
                  String message = new String(body, "UTF-8");
                  System.out.println("Customer Received '" + message + "'");
              }
          };
    //自动回复队列应答 -- RabbitMQ中的消息确认机制
    channel.basicConsume(QUEUE_NAME, true, consumer);
  ```

### Kafka

- Producer Send Message

  ```java
  Producer<String, String> producer = new KafkaProducer<String, String>(props);	
  producer.send(new ProducerRecord<String, String>("topic0", "message 4");
  
  ```

- Consumer consume Message

  ```java
  ConsumerConfig config = new ConsumerConfig(props);
  ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
  // 构建订阅配置
  Map<String, Integer> topicCountMap = new HashMap<String, Integer>();  
  topicCountMap.put(KafkaProducer.TOPIC, new Integer(1));  
  StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());  
  StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
  
  // 订阅消息
  Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);  
  KafkaStream<String, String> stream = consumerMap.get(KafkaProducer.TOPIC).get(0);  
  
  // 拉取消息
  ConsumerIterator<String, String> it = stream.iterator();  
  while (it.hasNext())  
        System.out.println(it.next().message());  
  
  ```

## RocketMQ

- Producer Send Message

```java
//Instantiate with a producer group name.
DefaultMQProducer producer = new
DefaultMQProducer("please_rename_unique_group_name");
// Specify name server addresses.
producer.setNamesrvAddr("localhost:9876");
//Launch the instance.
producer.start();    
//Create a message instance, specifying topic, tag and message body.
Message msg = new Message("TopicTest" /* Topic */,"TagA" /* Tag */,("Hello RocketMQ " ).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */ );
//Call send message to deliver message to one of brokers.
SendResult sendResult = producer.send(msg);
System.out.printf("%s%n", sendResult);       
```

- Consumer consume Message

  ```java
  // Instantiate with specified consumer group name.
  DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");   
  // Specify name server addresses.
  consumer.setNamesrvAddr("localhost:9876");
  // Subscribe one more more topics to consume.
  consumer.subscribe("TopicTest", "*");
  // Register callback to execute on arrival of messages fetched from brokers.
  consumer.registerMessageListener(new MessageListenerConcurrently() {
  		@Override
              public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                  ConsumeConcurrentlyContext context) {
                  System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                  return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
              }
          });
  
  ```


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

  |          | Spring Message                    | Spring MVC                        |
  | -------- | --------------------------------- | --------------------------------- |
  | Header   | `@Header`                         | `@Payload`                        |
  | Body     | `@RequestHeader`                  | `@RequestBody`                    |
  | Argument | `HandlerMethodArgumentResolver`   | `HandlerMethodArgumentResolver`   |
  | Return   | `HandlerMethodReturnValueHandler` | `HandlerMethodReturnValueHandler` |
  | Invoke   | `InvocableHandlerMethod`          | `ServletInvocableHandlerMethod`   |



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

