package com.YB.server;

import java.net.*;


public class Main {

    private static int cNumber = 0;

    public static void main(String[] args) {

        try {
            // Create server socket
            ServerSocket server = new ServerSocket(55001);

            // create client socket
            Socket client;

            while (true) {
                // taking clients here
                client = server.accept();

                System.out.println("Client  " + ++cNumber + "  connected!");

                new Thread(new Server(client, cNumber)).start();
            }
        } catch (Exception e) {
            System.out.println("Error " + e.toString());
        }
    }
}
