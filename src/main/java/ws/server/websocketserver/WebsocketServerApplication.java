package ws.server.websocketserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
public class WebsocketServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebsocketServerApplication.class, args);
  }
}
