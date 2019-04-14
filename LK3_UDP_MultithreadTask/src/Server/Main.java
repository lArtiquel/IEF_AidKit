package Server;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;


public class Main {

    public static void main(String args[])
    {
        try {
            DatagramSocket serverSocket = new DatagramSocket(50001);
            byte[] buffer = new byte[8];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            DatagramPacket sendPacket;

            while(true)
            {
                // receive a
                serverSocket.receive(receivePacket);
                final double a = makeDoubleFromBuffer(receivePacket.getData());

                // receive b
                serverSocket.receive(receivePacket);
                final double b = makeDoubleFromBuffer(receivePacket.getData());

                // receive c
                serverSocket.receive(receivePacket);
                final double c = makeDoubleFromBuffer(receivePacket.getData());

                final InetAddress IPAddress = receivePacket.getAddress();
                final int port = receivePacket.getPort();
                System.out.println("RECEIVED FROM IP: " + IPAddress.toString() + " Port: " + port);
                System.out.println("a = " + a + " b = " + b + " c = " + c);

                CountSum counter1 = new CountSum(a, b, false);
                CountSum counter2 = new CountSum(b, c, true);
                Thread t1 = new Thread(counter1);
                Thread t2 = new Thread(counter2);
                t1.start(); // do multithreading tasks
                t2.start();
                t1.join();  // then join
                t2.join();
                double sum = counter1.getSum() - counter2.getSum(); // and get result
                String str = Double.toString(sum);
                System.out.println("Final sum: " + str);
                makeBufferFromDouble(sum, buffer);

                sendPacket = new DatagramPacket(buffer, buffer.length, IPAddress, port);
                serverSocket.send(sendPacket);
                System.out.println("Response sended\n");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("server terminated!\n");
        }
    }

    private static double makeDoubleFromBuffer(byte[] buffer){
        if(buffer != null && buffer.length == 8){
            return ByteBuffer.wrap(buffer).getDouble();
        } else {
            System.out.println("buffer not 8 bytes length!\n");
            return 0;
        }
    }

    private static void makeBufferFromDouble(double num, byte[] buffer){
        if(buffer != null && buffer.length == 8){
            ByteBuffer.wrap(buffer).putDouble(num);
        } else{
            System.out.println("buffer not 8 chars length\n");
        }
    }
}