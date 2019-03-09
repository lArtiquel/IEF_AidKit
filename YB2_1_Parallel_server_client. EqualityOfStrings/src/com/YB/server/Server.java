package com.YB.server;

import java.net.*;
import java.io.*;

public class Server implements Runnable{

    private Socket client;
    private int clientId;
    private DataInputStream is;
    private DataOutputStream os;


    public Server(Socket client, int clientId)
    {
        try
        {
            // allows us to invoke strings, not buffers from streams
            is = new DataInputStream(client.getInputStream());
            os = new DataOutputStream(client.getOutputStream());
        }
        catch(IOException e)
        {
            System.out.println("failed with handling input and output streams: ");
            e.printStackTrace();
            try {
                client.close();
            } catch(IOException e1) {
                System.out.println("failed to close client socket: ");
                e.printStackTrace();
            }
        }

        this.client = client;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String requestStr1, requestStr2;
                // read first request string
                requestStr1 = is.readUTF();

                // check for quit command
                if (requestStr1.compareTo("!quit") == 0) {
                    System.out.println("Terminating session with client #" + clientId);
                    client.close();
                    is.close();
                    os.close();
                    return;
                }

                // read another string
                requestStr2 = is.readUTF();

                //compare first and second strings
                if (requestStr1.equalsIgnoreCase(requestStr2)) {
                    System.out.println("Client #: " + clientId + " sent request: The strings are equals!");
                    os.writeUTF("The strings are equals!");
                } else {
                    System.out.println("Client #: " + clientId + " sent request: The strings aren't equals!");
                    os.writeUTF("The strings aren't equals!");
                }

                // give b4n for this client for 1 sec
                Thread.sleep(1000);
            }
        } catch (IOException e) {               // IOException(socket's or data stream's)
            System.out.println("IO Exception.");
            e.printStackTrace();
            // try to close client socket and data streams
            try{
                client.close();
                is.close();
                os.close();
            } catch(IOException finallyEx) {
                System.out.println("exception in close block: ");
                finallyEx.printStackTrace();
            } finally{
                return;
            }
        } catch (InterruptedException e) {      // Interrupted Exception (not so scary, we can continue receive and send data)
            System.out.println("Interrupted thread Exception.");
            e.printStackTrace();
        } catch (Exception e) {                 // Standard exception (common exception, trying to close connection also)
            System.out.println("Standard Exception.");
            e.printStackTrace();
            // try to close client socket and data streams
            try{
                client.close();
                is.close();
                os.close();
            } catch(IOException finallyEx) {
                System.out.println("exception in close block: ");
                finallyEx.printStackTrace();
            } finally{
                return;
            }
        }
    }
}