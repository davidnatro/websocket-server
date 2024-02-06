package ws.server.websocketserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ws.server.websocketserver.config.property.WebSocketProperty;
import ws.server.websocketserver.websocket.handler.MessageHandler;
import ws.server.websocketserver.websocket.interceptor.PathVariableExtractor;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  private final MessageHandler handler;
  private final WebSocketProperty property;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(handler, property.getRoomPath())
        .setHandshakeHandler(handshakeHandler())
        .addInterceptors(new PathVariableExtractor(property.getRoomPath()))
        .setAllowedOrigins("*");
  }

  private DefaultHandshakeHandler handshakeHandler() {
    return new DefaultHandshakeHandler(new TomcatRequestUpgradeStrategy());
  }
}
