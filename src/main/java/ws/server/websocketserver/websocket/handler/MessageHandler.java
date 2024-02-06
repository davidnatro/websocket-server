package ws.server.websocketserver.websocket.handler;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import ws.server.websocketserver.websocket.storage.WebSocketSessionStorage;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler implements WebSocketHandler {

  private final WebSocketSessionStorage sessionStorage;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    String roomId = (String) session.getAttributes().get("roomId");
    log.info("Establishing connection for room -> '{}'", roomId);
    if (!sessionStorage.getSessions().containsKey(roomId)) {
      sessionStorage.getSessions().put(roomId, session);
    }

    log.info("Established connection for room -> '{}'", roomId);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {
    String acceptedMessage = (String) message.getPayload();
    log.info("Accepted -> message: '{}'", acceptedMessage);
    String messagePayload = (String) message.getPayload();

    if (messagePayload.equalsIgnoreCase("PING")) {
      session.sendMessage(new TextMessage("PONG"));
    } else if (messagePayload.equalsIgnoreCase("PONG")) {
      session.sendMessage(new TextMessage("PING"));
    } else {
      log.info("Received message -> '{}'", messagePayload);
      session.sendMessage(new TextMessage(messagePayload));
    }
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    log.error("Websocket error occured -> '{}'", exception.getMessage());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
      throws IOException {
    String roomId = (String) session.getAttributes().get("roomId");
    log.info("Closing connection for room -> '{}'", roomId);
    sessionStorage.getSessions().remove(roomId).close(closeStatus);
    log.info("Closed connection for room -> '{}'", roomId);
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }
}

