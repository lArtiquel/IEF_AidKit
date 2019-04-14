package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.*;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import java.nio.ByteBuffer;


public class Main extends Application{

    private DatagramSocket clientSocket;
    byte[] buffer;
    private DatagramPacket packet;
    private InetAddress ip;


    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage stage){

        // init socket data packets
        try {
            clientSocket = new DatagramSocket();
            buffer = new byte[8];
            ip = InetAddress.getLocalHost();
            packet = new DatagramPacket(buffer, buffer.length, ip, 50001);
        }
        catch(SocketException ex){
            ex.printStackTrace();
        }
        catch(SecurityException ex){
            ex.printStackTrace();
        }
        catch(UnknownHostException ex){
            ex.printStackTrace();
        }

        // create instances of text field
        TextField textFieldA = new TextField("");
        textFieldA.setPromptText("A number...");
        textFieldA.setMaxWidth(Double.MAX_VALUE);
        TextField textFieldB = new TextField("");
        textFieldB.setPromptText("B number...");
        textFieldB.setMaxWidth(Double.MAX_VALUE);
        TextField textFieldC = new TextField("");
        textFieldC.setPromptText("C number...");
        textFieldC.setMaxWidth(Double.MAX_VALUE);
        // set text formatter
        TextFormatter<String> textFormatterA = getTextFormatter();   // get text formatter
        TextFormatter<String> textFormatterB = getTextFormatter();
        TextFormatter<String> textFormatterC = getTextFormatter();
        textFieldA.setTextFormatter(textFormatterA);                  // set text formatters
        textFieldB.setTextFormatter(textFormatterB);
        textFieldC.setTextFormatter(textFormatterC);

        // create instance of button
        Button bsend = new Button("Send to server");
        bsend.setOnAction(e -> {
            // send numbers to the server
            String a = textFieldA.getText();
            String b = textFieldB.getText();
            String c = textFieldC.getText();

            if(a.isEmpty() || b.isEmpty() || c.isEmpty()){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Empty text field!");
                // show and wait
                alert.showAndWait();
                return ;
            }

            if(Double.parseDouble(a) <= Double.parseDouble(b) && Double.parseDouble(b) <= Double.parseDouble(c)){
                // start to fill and send packets
                try{
                    makeBufferFromDouble(Double.parseDouble(a), buffer);
                    clientSocket.send(packet);
                    // send another packet
                    makeBufferFromDouble(Double.parseDouble(b), buffer);
                    clientSocket.send(packet);
                    // and send last packet
                    makeBufferFromDouble(Double.parseDouble(c), buffer);
                    clientSocket.send(packet);

                    // now get back response
                    clientSocket.receive(packet);
                } catch(IOException ex){
                    ex.printStackTrace();
                }

                double sum = makeDoubleFromBuffer(packet.getData());  // get int sum from buffer
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Response");
                alert.setHeaderText(null);
                alert.setContentText("Amount row is: " + sum);
                // show and wait
                alert.showAndWait();
            } else{
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("This should be rigth -> a <= b && b <= c");
                // show and wait
                alert.showAndWait();
            }
        });
        bsend.setAlignment(Pos.CENTER);

        Label lb = new Label("Enter numbers to send them to server...");
        lb.setStyle("-fx-font-weight: bold");                   // set font for label
        lb.setAlignment(Pos.CENTER);                            // set allignment to the center

        ImageView imageView = null;
        try {
            Image image = new Image("Client/form.jpg", true);
            imageView = new ImageView(image);
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }
        catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

        VBox tfBox = new VBox(textFieldA, textFieldB, textFieldC);
        tfBox.setSpacing(10);
        HBox hBox = new HBox(tfBox, bsend);
        hBox.setSpacing(30);
        hBox.setAlignment(Pos.CENTER);
        VBox mainBox = new VBox(lb, imageView, hBox);
        mainBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane(mainBox);     // add to the root vBox
        //root.setCenter();                         // set it to the center

        Scene scene = new Scene(root, 400, 400);    // crete scene with root

        stage.setTitle("Client");
        stage.setScene(scene);                                   // set scene to the current stare
        stage.show();                                            // and show stage(window)
    }

    private TextFormatter<String> getTextFormatter() {
        UnaryOperator<Change> filter = getFilter();
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        return textFormatter;
    }

    private UnaryOperator<Change> getFilter() {
        return change -> {
            String text = change.getText();

            if (!change.isContentChange()) {
                return change;
            }

            if (text.matches("[0-9]*") || text.isEmpty()) {
                return change;
            }

            return null;
        };
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

    // deprecated
    private static int makeIntFromBuffer(byte[] buffer){
        if(buffer != null && buffer.length == 4){
            return ((0xff & buffer[0]) << 24  |
                    (0xff & buffer[1]) << 16  |
                    (0xff & buffer[2]) << 8   |
                    (0xff & buffer[3]));
        } else {
            System.out.println("buffer not 4 bytes length!\n");
            return 0;
        }
    }

    // deprecated
    private static void makeBufferFromInt(int num, byte[] buffer){
        if(buffer.length == 4){
            buffer[0] = (byte)((num >> 24) & 0xff);
            buffer[1] = (byte)((num >> 16) & 0xff);
            buffer[2] = (byte)((num >> 8) & 0xff);
            buffer[3] = (byte)(num & 0xff);
        } else{
            System.out.println("buffer not 4 chars length\n");
        }
    }
}