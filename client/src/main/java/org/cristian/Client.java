package org.cristian;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5500)) {
            LOGGER.info("Successfully connected to " + socket.getInetAddress() + ":" + socket.getPort());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());

            LOGGER.info("Server message: " + inputStream.readObject());

            Scanner scanner = new Scanner(System.in);
            String read = "";
            while (!read.equals("exit")) {
                read = scanner.nextLine();
                switch (read) {
                    case "send": {
                        System.out.print("File path: ");
                        read = scanner.nextLine();
                        Path path = Path.of(read);
                        while (!Files.exists(path) && !read.equals("cancel")) {
                            System.out.println("File on path '" + path + "' doesn't exist, try again or type 'cancel'.");
                            System.out.print("File path: ");
                            read = scanner.nextLine();
                            path = Path.of(read);
                        }
                        if (read.equals("cancel")) break;
                        walk(path);
                        break;
                    }
                    default: {
                        System.out.println("Unknown command '" + read + "' please use one of these:");
                        System.out.println("send, download, list, exit");
                        break;
                    }
                }
            }

            outputStream.close();
            LOGGER.info("Disconnected from server");
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.throwing(Client.class.getName(), e.getStackTrace()[0].getMethodName(), e);
        }
    }

    private static void walk(Path path) {
        try (Stream<Path> w = Files.walk(path)) {
            w.forEach(f -> {
                String filename = f.getFileName().toString();
                byte[] fileContent = null;
                boolean hasErrors = false;
                try {
                    fileContent = Files.readAllBytes(f);
                } catch (IOException e) {
                    hasErrors = true;
                    LOGGER.info("Couldn't read file: " + e.getMessage());
                }
                if (!hasErrors) {
                    try {
                        outputStream.writeObject(new FileData("send", filename, fileContent));
                    } catch (IOException e) {
                        LOGGER.info("Couldn't write object to output stream: " + e.getMessage());
                    }
                }
            });
        } catch (SecurityException e) {
            LOGGER.info("Couldn't access file: " + e.getMessage());
        } catch (IOException ignored) {
        }
    }
}