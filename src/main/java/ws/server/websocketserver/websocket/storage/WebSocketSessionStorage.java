package ws.server.websocketserver.websocket.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Component
public class WebSocketSessionStorage {

  private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
}

