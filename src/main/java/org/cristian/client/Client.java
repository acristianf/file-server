package org.cristian.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class Client {

    private static ObjectOutputStream oos;
    private static ObjectInputStream iis;
    private static FileInputStream fis;
    private final String serverIp;
    private final Integer serverPort;

    public Client(String serverIp, Integer serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void send(File file) {
        Path filePath = file.toPath();
        String filename = file.getName();
        try (Socket socket = new Socket(serverIp, serverPort)) {

            long fileSize = Files.size(filePath);
            String mimeType = Files.probeContentType(filePath);
            System.out.println("File type: " + mimeType);
            System.out.println("File size: " + fileSize);
            System.out.println("File name: " + filename);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(fileSize);
            oos.flush();
            oos.writeObject(mimeType);
            oos.flush();
            oos.writeObject(filename);
            oos.flush();

            fis = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int count;
            int totalCount = 0;
            float percentage;
            float lastPercentage = 0.f;
            while ((count = fis.read(buffer)) > 0) {
                totalCount += count;
                percentage = (float)totalCount / fileSize;
                if (percentage > lastPercentage) {
                    System.out.print("\r\033[KSending... " + percentage * 100 + "%");
                    lastPercentage = percentage;
                }
                oos.write(buffer);
                oos.flush();
            }

            fis.close();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
