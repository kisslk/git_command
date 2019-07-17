package com.envision.spring.cloud.stream.binder;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.channel.PublishSubscribeChannel;

public interface BroadcastingProcessor {

    String BROADCASTING_INPUT = "broadcastingInput";

    String BROADCASTING_OUTPUT = "broadcastingOutput";

    @Input(BROADCASTING_INPUT)
    PublishSubscribeChannel broadcastingInput();

    @Output(BROADCASTING_OUTPUT)
    PublishSubscribeChannel broadcastingOutput();

}
