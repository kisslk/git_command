package com.envision.spring.cloud.stream.binder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BindingTargetFactory;
import org.springframework.cloud.stream.binding.CompositeMessageChannelConfigurer;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.PublishSubscribeChannel;

@EnableBinding(
        value = {
                HeaderProcessor.class,
                Source.class,
                Sink.class,
                BroadcastingProcessor.class
        }
)
@SpringBootApplication
public class BinderApplication {

    @Bean
    public BindingTargetFactory publishSubscribeFactory(CompositeMessageChannelConfigurer messageChannelConfigurer) {
        return new BindingTargetFactory() {
            @Override
            public boolean canCreate(Class<?> clazz) {
                return PublishSubscribeChannel.class.isAssignableFrom(clazz);
            }

            @Override
            public Object createInput(String name) {
                PublishSubscribeChannel publishSubscribeChannel = new PublishSubscribeChannel();
                messageChannelConfigurer.configureInputChannel(publishSubscribeChannel, name);
                return publishSubscribeChannel;
            }

            @Override
            public Object createOutput(String name) {
                PublishSubscribeChannel publishSubscribeChannel = new PublishSubscribeChannel();
                messageChannelConfigurer.configureOutputChannel(publishSubscribeChannel, name);
                return publishSubscribeChannel;
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(BinderApplication.class, args);
    }

}
