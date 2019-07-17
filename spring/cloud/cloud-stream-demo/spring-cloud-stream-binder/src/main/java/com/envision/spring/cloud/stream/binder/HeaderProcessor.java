package com.envision.spring.cloud.stream.binder;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface HeaderProcessor {

    String HEADER_INPUT = "headerInput";

    String HEADER_OUTPUT = "headerOutput";

    @Input(HEADER_INPUT)
    SubscribableChannel headerInput();

    @Output(HEADER_OUTPUT)
    SubscribableChannel headerOutput();


}
