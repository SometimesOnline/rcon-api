package kr.sometimesonline.rconapi.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/rconWebSocket").setAllowedOrigins("http://rcon.tareun.kr","http://rcon.sometimesonline.kr","http://localhost:8100");
        registry.addEndpoint("/testToEpic").setAllowedOrigins("https://apic.app/"); // 테스트용. https://apic.app/online/#/tester 에서 사용.
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // /app 으로 시작하는 경로는 api 서버의 컨트롤러에서 받음.
        registry.enableSimpleBroker("/topic", "/queue"); // 클라이언트 측에서 구독할 경로.  /topic = 다수가 구독, /queue = 한명만 구독.
    }
}
