
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
		stäng(session);
	}
	@OnError
	public void error(Throwable t){
		log.error(t.getMessage());
	}
	private void stäng(Session session) {
		connections.remove(session);
		utgående("* " + namn + " har lämnat.");
	}
	@OnMessage
	public void inkommande(String message) {
		if (namn==null) {
			namn = message;
			if (namn.equals("")) {
				pers++;
				namn="Okänd " + pers.toString();
			}
			utgående("* " + namn +" har anslutit.");
		}
		else {
			utgående(namn+": "+ message);
		}
	}
	private synchronized void utgående(String msg) {
		for (Session session:connections) {
			try {
				synchronized (session) {
					session.getBasicRemote().sendText(msg);
				}
			} catch (Exception e) {
				stäng(session);
			}
		}
	}
}