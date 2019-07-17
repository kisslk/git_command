package com.envision.spring.cloud.stream.binder.producer;

import com.envision.spring.cloud.stream.binder.BroadcastingProcessor;
import com.envision.spring.cloud.stream.binder.HeaderProcessor;
import com.envision.spring.cloud.stream.binder.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BinderProducerController {

    @Autowired
    private Source source;

    @Autowired
    private HeaderProcessor headerProcessor;

    @Autowired
    private BroadcastingProcessor broadcastingProcessor;

    @GetMapping("/simple")
    public String simple(@RequestParam String msg) {
        source.output().send(MessageBuilder.withPayload(msg).build());
        return msg;
    }

    @GetMapping("/header")
    public User headerUser(@RequestParam Integer age, @RequestParam String name, @RequestParam String type) {

        User user = new User();
        user.setName(name);
        user.setAge(age);

        Message<User> message = MessageBuilder.withPayload(user).setHeader("type", type).build();
        headerProcessor.headerOutput().send(message);
        return user;
    }

    @GetMapping("/broadcasting")
    public String broadcasting(@RequestParam String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        broadcastingProcessor.broadcastingOutput().send(message);
        return msg;
    }

}
