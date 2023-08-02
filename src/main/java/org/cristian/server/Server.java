package org.cristian.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int port = 7001;

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Server listening on port " + port);

            DataInputStream iis = null;
            FileOutputStream fos = null;
            ObjectInputStream ois = null;
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                LOGGER.info("Waiting for client connection");
                Socket socket = serverSocket.accept();

                ois = new ObjectInputStream(socket.getInputStream());
                Long size = (Long) ois.readObject();
                String mimeType = (String) ois.readObject();
                String filename = (String) ois.readObject();
                stringBuilder
                        .append("file-size:")
                        .append(size)
                        .append(",")
                        .append("mime-type:")
                        .append(mimeType)
                        .append(",")
                        .append("filename")
                        .append(filename);
                LOGGER.info("Receiving file: " + stringBuilder);

                iis = new DataInputStream(socket.getInputStream());
                fos = new FileOutputStream("./files/" + filename);
                byte[] buffer = new byte[4096];
                while (iis.read(buffer) > 0) {
                    fos.write(buffer);
                    fos.flush();
                }

                ois.close();
                iis.close();
                fos.close();

                socket.close();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
