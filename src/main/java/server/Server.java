package server;

import logger.Level;
import logger.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private static Socket client;
    private static int serverPort;

    public static Socket getClient() {
        return client;
    }

    public static int getPort() { return serverPort; }

    private void startServer() {
        startServer(KumoSettings.PORT);
    }

    private void startServer(int port) {
        while (true) {
            try {
                ServerSocket server = new ServerSocket(port);
                serverPort = port;
                Listeners.addListener(server, port);
                client = server.accept();
                Runnable clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
                Logger.log(Level.INFO, "Listening server started on " + KumoSettings.CONNECTION_IP + ":" + port);
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void run() {
        startServer();
    }
}