package org.cristian;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) {
        int port = 5500;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Server started on " + serverSocket.getInetAddress() + ":" + port);

            LOGGER.info("Waiting for client connection...");
            Socket clientSocket = serverSocket.accept();
            LOGGER.info("Client connected: " + clientSocket.getInetAddress());
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

            String line = "";
            while(!line.equals("exit")) {
                line = dataInputStream.readUTF();
                System.out.println(line);
            }

            clientSocket.close();
            LOGGER.info("Client " + clientSocket.getInetAddress() + " disconnected");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}