package com.envision.spring.cloud.stream.binder.consumer;

import com.envision.spring.cloud.stream.binder.BroadcastingProcessor;
import com.envision.spring.cloud.stream.binder.HeaderProcessor;
import com.envision.spring.cloud.stream.binder.domain.User;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class BinderConsumerReceiveService {

    /*单播负载*/
    @StreamListener(Sink.INPUT)
    public void onSimpleStr1(String msg) {
        System.out.println("input channel receive message: " + msg);
    }

    @StreamListener(Sink.INPUT)
    public void onSimpleStr2(byte[] bytes) {
        System.out.println("input channel receive message: " + bytes);
    }
    /*单播负载*/


    /* header */
    @StreamListener(value = HeaderProcessor.HEADER_INPUT, condition = "headers['type']=='raw'")
    public void onRawUser(Message<User> message) {
        System.out.println("onRawUser receive message: " + message);
    }

    @StreamListener(value = HeaderProcessor.HEADER_INPUT, condition = "headers['type']=='user'")
    public void onUser(User user) {
        System.out.println("onUser receive message: " + user);
    }
    /* header end */


    /* broadcasting */
    @StreamListener(value = BroadcastingProcessor.BROADCASTING_INPUT)
    public void onBroadcasting1(Message<String> message) {
        System.out.println("onBroadcasting1 receive message: " + message);
    }

    @StreamListener(value = BroadcastingProcessor.BROADCASTING_INPUT)
    public void onBroadcasting2(byte[] msg) {
        System.out.println("onBroadcasting2 receive message: " + msg);
    }

    @StreamListener(value = BroadcastingProcessor.BROADCASTING_INPUT)
    public void onBroadcasting3(String msg) {
        System.out.println("onBroadcasting3 receive message: " + msg);
    }
    /* broadcasting end */

}
