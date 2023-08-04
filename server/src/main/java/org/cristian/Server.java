package org.cristian;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final Path filesPath = Path.of("./files");

    public static void main(String[] args) {
        int port = 5500;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        try {
            Files.createDirectory(filesPath);
        } catch (FileAlreadyExistsException e) {
           LOGGER.info("Files directory already exists");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                LOGGER.info("Server started on " + serverSocket.getInetAddress() + ":" + port);

                LOGGER.info("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("Client connected: " + clientSocket.getInetAddress());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream((clientSocket.getOutputStream()));

                ClientHandler handler = new ClientHandler(clientSocket, outputStream, inputStream);
                handler.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void save(FileData fileData) throws IOException {
        Path filePath = Path.of(filesPath + "/" + fileData.getFilename());
        Files.write(filePath, fileData.getFileContent());
    }
}