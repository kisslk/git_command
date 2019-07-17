package com.envision.spring.message;

import org.junit.Test;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;
import java.util.concurrent.Executors;

import static com.envision.spring.message.PrintUtils.print;

public class UnicastingChannelTest {


    /**
     * 直连message-channel
     */
    @Test
    public void testSingleDirectChannel(){

        DirectChannel directChannel = new DirectChannel();

        directChannel.subscribe(message -> print("directChannel receive payload --->" + message.getPayload()));

//        directChannel.subscribe(message -> print("directChannel receive header --->" + message.getHeaders() + "<---,receive payload --->" + message.getPayload()));

        Message<String> message = MessageBuilder.withPayload("hello directChannel").build();

        directChannel.send(message);

//        directChannel.send(message);

    }

    /**
     * 线程池message-channel
     */
    @Test
    public void testExecutorChannel(){

        ExecutorChannel executorChannel = new ExecutorChannel(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

        executorChannel.subscribe(message -> print("executorChannel receive payload --->" + message.getPayload()));

        executorChannel.subscribe(message -> print("executorChannel receive header --->" + message.getHeaders() + "<---,receive payload --->" + message.getPayload()));

        Message<String> message = MessageBuilder.withPayload("hello executorChannel").build();

        executorChannel.send(message);

        executorChannel.send(message);

    }

    /**
     * 直连message-channel + channel-interceptor
     */
    @Test
    public void testInterceptorDirectChannel() throws InterruptedException {

        DirectChannel directChannel = new DirectChannel();

        // 添加拦截器
        directChannel.setInterceptors(Collections.singletonList(new ChannelInterceptor() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                print("preSend....");
                return message;
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                print("postSend....");
            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
                print("afterSendCompletion....");
            }
        }));

        directChannel.subscribe(message -> print("directChannel receive payload --->" + message.getPayload()));

        Message<String> message = MessageBuilder.withPayload("hello interceptor").build();

        directChannel.send(message);

    }


}
