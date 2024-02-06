package ws.server.websocketserver.websocket.interceptor;

import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriTemplate;
import ws.server.websocketserver.websocket.constants.PathVariableNames;

public class PathVariableExtractor implements HandshakeInterceptor {

  private final UriTemplate uriTemplate;

  public PathVariableExtractor(String uriPath) {
    this.uriTemplate = new UriTemplate(uriPath);
  }

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                 WebSocketHandler wsHandler, Map<String, Object> attributes) {
    Map<String, String> match = uriTemplate.match(request.getURI().toString());

    match.computeIfPresent(PathVariableNames.ROOM_PATH_VARIABLE_NAME, (key, roomId) -> {
      attributes.put(PathVariableNames.ROOM_PATH_VARIABLE_NAME, roomId);
      return roomId;
    });

    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                             WebSocketHandler wsHandler, Exception exception) { }
}

