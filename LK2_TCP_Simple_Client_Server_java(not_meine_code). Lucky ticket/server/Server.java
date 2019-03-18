
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int port = 8081;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private StringParser stringParser;

    public Server(){
        startServer();
    }

    private void startServer() {
        try {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Server starts...");

                try {
                    clientSocket = serverSocket.accept();

                    try {
                        in = new DataInputStream(clientSocket.getInputStream());
                        out = new DataOutputStream(clientSocket.getOutputStream());

                        String message = "";
                        try{
                            while(!(message = in.readUTF()).equals("--exit--")) {
                                System.out.println("Ticket number: " + message + " accepted");
                                stringParser = new StringParser(message);
                                if (stringParser.isSumEquals()) {
                                    out.writeUTF("Lucky");
                                    delay(5000);
                                    System.out.println("\nResult: lucky");

                                } else {
                                    out.writeUTF("Casual");
                                    delay(5000);
                                    System.out.println("\nResult: casual");
                                }
                                out.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverSocket.close();
                clientSocket.close();
                in.close();
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void delay(int millis){
        for(int i = 0; i < 10; i++){
            System.out.print(".");
            try {
                Thread.sleep(millis/10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
