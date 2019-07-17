package com.envision.spring.message;

import org.junit.Test;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static com.envision.spring.message.PrintUtils.print;

public class BroadcastingChannelTest {

    /**
     * 广播message-channel
     */
    @Test
    public  void testPublishSubscribeChannel() {

        PublishSubscribeChannel publishSubscribeChannel = new PublishSubscribeChannel();

        publishSubscribeChannel.subscribe(message -> print("publishSubscribeChannel receive payload" + message.getPayload()));

        publishSubscribeChannel.subscribe(message -> print("publishSubscribeChannel receive header" + message.getHeaders()));

        Message<String> message = MessageBuilder.withPayload("hello publishSubscribeChannel").build();

        publishSubscribeChannel.send(message);
    }

}
