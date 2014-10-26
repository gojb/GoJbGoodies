
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat")
public class Chatta {

	private static final Set<Chatta> connections =
			new CopyOnWriteArraySet<>();

	private String nickname;
	private Session session;
	private static Integer pers = new Integer(0);

	@OnOpen
	public void start(Session session) {
		this.session = session;
		connections.add(this);

	}
	@OnClose
	public void end() {
		connections.remove(this);
		broadcast("* " + nickname + " har lämnat.");
	}
	@OnMessage
	public void incoming(String message) {
		if (message.startsWith("NAME:")&&nickname==null) {
			nickname = message.substring(5);
			if (nickname.equals("")) {
				pers++;
				nickname="Okänd " + pers.toString();
			}
			broadcast("* " + nickname +" har anslutit.");
		}
		else {
			broadcast(nickname+": "+ message);
		}
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
						client.nickname, " har lämnat.");
				broadcast(message);
			}
		}
	}
}