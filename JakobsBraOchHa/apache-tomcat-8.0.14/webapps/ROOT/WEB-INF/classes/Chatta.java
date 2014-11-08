
import java.util.ArrayList;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.apache.juli.logging.*;

@ServerEndpoint(value = "/chat")
public class Chatta {

	static final Log log = LogFactory.getLog(Chatta.class);
	private static final ArrayList<Session> connections = new ArrayList<Session>();

	private String namn;
	private Session session;
	private static Integer pers = 0;
	
	@OnOpen
	public void start(Session session) {
		this.session=session;
		connections.add(session);
	}
	@OnClose
	public void end() {
		st�ng(session);
	}
	@OnError
	public void error(Throwable t){
		log.error(t.getMessage());
	}
	private void st�ng(Session session) {
		connections.remove(session);
		utg�ende("* " + namn + " har l�mnat.");
	}
	@OnMessage
	public void inkommande(String message) {
		if (namn==null) {
			namn = message;
			if (namn.equals("")) {
				pers++;
				namn="Ok�nd " + pers.toString();
			}
			utg�ende("* " + namn +" har anslutit.");
		}
		else {
			utg�ende(namn+": "+ message);
		}
	}
	private synchronized void utg�ende(String msg) {
		for (Session session:connections) {
			try {
				synchronized (session) {
					session.getBasicRemote().sendText(msg);
				}
			} catch (Exception e) {
				st�ng(session);
			}
		}
	}
}