package org.cristian;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.currentThread().getName());
    Socket client;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    ClientHandler(Socket client, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.client = client;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            outputStream.writeObject("Connected successfully");
            FileData received = new FileData();
            received.setCommand("");
            while (!received.getCommand().equals("exit")) {
                received = (FileData) inputStream.readObject();
                switch (received.getCommand()) {
                    case "send": {
                        LOGGER.info("Client sent: " + received.getCommand() + "," + received.getFilename());
                        Server.save(received);
                        break;
                    }
                    default: {
                        LOGGER.info("Client sent unknown command: " + received.getCommand());
                        break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.throwing(LOGGER.getClass().getName(), e.getStackTrace()[0].getMethodName(), e);
        }
    }
}
