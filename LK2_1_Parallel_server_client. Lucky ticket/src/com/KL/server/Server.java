package com.KL.server;
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
                String request;
                int number, first, second;
                // read request
                request = is.readUTF();

                // check for quit code
                if (request.compareTo("987654") == 0) {
                    System.out.println("Terminating session with client #" + clientId);
                    client.close();
                    is.close();
                    os.close();
                    return;
                }

                // parse int number
                number = Integer.parseInt(request);

                // get first part and second from number
                first = (number / 100000) + ((number / 10000) % 10) + ((number / 1000) % 10);
                second = ((number % 1000) / 100) + ((number % 100) / 10) + (number % 10);

                //compare first and second parts
                if (first == second) {
                    System.out.println("Client #: " + clientId + " sent request: lucky ticket!");
                    os.writeUTF("Lucky ticket! Congratulations!\n");
                } else {
                    System.out.println("Client #: " + clientId + " sent request: unlucky ticket!");
                    os.writeUTF("Unlucky ticket! Next time it will be lucky!\n");
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
