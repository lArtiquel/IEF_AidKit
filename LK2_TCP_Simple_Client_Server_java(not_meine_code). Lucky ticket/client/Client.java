package lab2client;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final int port = 8081;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader consoleReader;

    public Client(){
        startClient();
    }

    public void startClient() {
        try {
            try {
                socket = new Socket("localhost", port);
                System.out.print("Client starts...\nWrite your ticket number: ");
                try {
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    consoleReader = new BufferedReader(new InputStreamReader(System.in));
                    String message = "";

                    while (!(message= consoleReader.readLine()).equals("--exit--")) {

                        try {
                            if(testString(message)) {
                                out.writeUTF(message);
                                out.flush();
                                delay(5000);
                                System.out.println("\nTicket status: " + in.readUTF());
                                System.out.print("Write your ticket number: ");
                            }
                            else{
                                System.out.println("Incorrect input, please try again");
                                System.out.print("Write your ticket number: ");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                in.close();
                out.close();
                consoleReader.close();
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delay(int millis){
        String word = "Processing";
        for(int i = 0; i < word.length(); i++){
            System.out.print(word.charAt(i));
            try {
                Thread.sleep(millis/10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean testString(String message){
        if(message.length() != 6){
            return false;
        }
        for (int i = 0; i < message.length(); i++) {
            if(message.charAt(i)>='0' && message.charAt(i) <= '9'){
                continue;
            }
            else{
                return false;
            }
        }
        return true;
    }
}
