
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat")
public class Chatta {

    private static final String GUEST_PREFIX = "Gäst";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final Set<Chatta> connections =
            new CopyOnWriteArraySet<>();

    private final String nickname;
    private Session session;

    public Chatta() {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
    }
    @OnOpen
    public void start(Session session) {
        this.session = session;
        connections.add(this);
        broadcast("* " + nickname +" har anslutit.");
    }
    @OnClose
    public void end() {
        connections.remove(this);
        broadcast("* " + nickname + "har lämnat.");
    }
    @OnMessage
    public void incoming(String message) {
        broadcast(nickname+": "+ message);
    }
    private static void broadcast(String msg) {
        for (Chatta client : connections) {
            try {
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("* %s %s",
                        client.nickname, "har lämnat.");
                broadcast(message);
            }
        }
    }
}