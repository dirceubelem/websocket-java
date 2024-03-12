package websocket;

import javax.websocket.*;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/app")
public class ChatServerEndpoint {

    public static Map<String, Session> sessions = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected, sessionID = " + session.getId());
        sessions.put(session.getId(), session);
        try {
            session.getBasicRemote().sendText("Seja bem vindo");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        if (message.equals("quit")) {
            try {
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Bye!"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            Session s = entry.getValue();
            try {
                s.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(session.getId() + " - " + message);
        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessions.remove(session.getId());
        System.out.println("Session " + session.getId() + " closed because " + closeReason);
    }
}