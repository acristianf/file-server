package org.cristian;

import org.cristian.client.Client;
import org.cristian.server.Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) throws IOException {
        LogManager.getLogManager()
                .readConfiguration(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/logging.properties"));

        if (args.length < 1) {
            System.out.println("USAGE: fileserver [start | connect] [ip] [port]");
            return;
        }


        if (args[0].equalsIgnoreCase("start")) {
            Server server = new Server();
            server.start();
        } else if (args[0].equalsIgnoreCase("connect")) {
            if (args.length < 4) {
                System.out.println("USAGE: fileserver connect [ip] [port] [service]");
                return;
            }
            Client client = new Client(args[1], Integer.parseInt(args[2]));

            switch(args[3].toLowerCase()) {
                case "send" -> client.send(new File(args[4]));
            }
        }
    }

}