package org.cristian;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        File file;
        try (Socket socket = new Socket("localhost", 5500)) {
            DataInputStream inputStream = new DataInputStream(System.in);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            String inputString = "";
            while(!inputString.equals("exit")) {
                inputString = inputStream.readUTF();
                outputStream.writeUTF(inputString);
            }

        } catch (IOException e) {
            LOGGER.throwing(Client.class.getName(), e.getStackTrace()[0].getMethodName(), e);
        }
    }
}