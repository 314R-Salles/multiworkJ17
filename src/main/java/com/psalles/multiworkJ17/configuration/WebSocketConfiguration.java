package com.psalles.multiworkJ17.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Here we define the endpoint, that our clients will use to connect to the server.
        // So, in our case the URL for connection will be http://localhost:8080/socket/.

        registry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // app prefix. So, when our client will send message through socket, the URL to send will look approximately
        // like this: http://localhost:8080/app/…
        //we will have just one subscription — /chat. So clients will subscribe to this subscription
        // and will wait from messages from the server.

        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/scrabble");
    }
}
