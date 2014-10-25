package test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Hej
 */
@WebServlet("/Hej")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.write("Hej... Det funkar väl eller??");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
//public class ChatWebSocketServlet extends WebSocketServlet {
//
//  private static final long serialVersionUID = 1L;
//
//  private static final String GUEST_PREFIX = "Guest";
//
//  private final AtomicInteger connectionIds = new AtomicInteger(0);
////  private final Set<ChatMessageInbound> connections =
////          new CopyOnWriteArraySet<ChatMessageInbound>();

//  protected StreamInbound createWebSocketInbound(String subProtocol,
//          HttpServletRequest request) {
//      return new ChatMessageInbound(connectionIds.incrementAndGet());
//  }

//  class ChatMessageInbound extends MessageInbound {
//
//      private final String nickname;
//
//      private ChatMessageInbound(int id) {
//          this.nickname = GUEST_PREFIX + id;
//      }
//
//      @Override
//      protected void onOpen(WsOutbound outbound) {
//          connections.add(this);
//          String message = String.format("* %s %s",
//                  nickname, "has joined.");
//          broadcast(message);
//      }
//
//      @Override
//      protected void onClose(int status) {
//          connections.remove(this);
//          String message = String.format("* %s %s",
//                  nickname, "has disconnected.");
//          broadcast(message);
//      }
//
//      @Override
//      protected void onBinaryMessage(ByteBuffer message) throws IOException {
//          throw new UnsupportedOperationException(
//                  "Binary message not supported.");
//      }
//
//      @Override
//      protected void onTextMessage(CharBuffer message) throws IOException {
//          // Never trust the client
//          String filteredMessage = String.format("%s: %s",
//                  nickname, HTMLFilter.filter(message.toString()));
//          broadcast(filteredMessage);
//      }
//
//      private void broadcast(String message) {
//          for (ChatMessageInbound connection : connections) {
//              try {
//                  CharBuffer buffer = CharBuffer.wrap(message);
//                  connection.getWsOutbound().writeTextMessage(buffer);
//              } catch (IOException ignore) {
//                  // Ignore
//              }
//          }
//      }
//  }
//}
