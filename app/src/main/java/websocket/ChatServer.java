package websocket;

import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatServer {

    public static void main(String[] args) {
        Server server = new Server("ws.evolua.chat", 5000, "/folder", null, ChatServerEndpoint.class);
        try {
            server.start();
            System.out.println("--- server is running");
            System.out.println("--- press any key to stop the server");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            bufferRead.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }

}