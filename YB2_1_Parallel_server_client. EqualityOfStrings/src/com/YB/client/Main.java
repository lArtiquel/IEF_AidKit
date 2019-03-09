package com.YB.client;

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
import java.lang.*;
import java.net.*;
import java.io.*;
import javafx.scene.control.TextFormatter;


public class Main extends Application{

    private Socket sock;
    private DataInputStream is;
    private DataOutputStream os;


    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage stage){

        // create conn/disconn buttons
        Button connectButton = new Button("Connect");
        connectButton.setDisable(false);
        //connectButton.setAlignment(Pos.CENTER);
        Button disconnectButton = new Button("Disconnect");
        disconnectButton.setDisable(true);
        //disconnectButton.setAlignment(Pos.CENTER);

        // tip label instance
        Label lb = new Label("Enter strings and send them to server...");
        lb.setStyle("-fx-font-weight: bold");                   // set font for label
        //lb.setAlignment(Pos.CENTER);                            // set allignment to the center

        // create instance of first text field
        TextField textField1 = new TextField("");
        textField1.setPromptText("Enter first string...");
        textField1.setMaxWidth(Double.MAX_VALUE);
        textField1.setDisable(true);

        // create instance of first text field
        TextField textField2 = new TextField("");
        textField2.setPromptText("Enter second string...");
        textField2.setMaxWidth(Double.MAX_VALUE);
        textField2.setDisable(true);


        // create instance of button
        Button bsend = new Button("Send to server");
        bsend.setDisable(true);
        bsend.setAlignment(Pos.CENTER);
        bsend.setOnAction(e -> {
            // send ticket to the server
            try {
                final String requestStr1 = textField1.getText();
                final String requestStr2 = textField2.getText();

                // send first string to server
                os.writeUTF(requestStr1);

                // send second string to server
                os.writeUTF(requestStr2);

                // receive response from server
                String response = is.readUTF();

                // display result into display
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Response from server: ");
                alert.setHeaderText(null);
                alert.setContentText(response);
                // show and wait
                alert.showAndWait();
            } catch(IOException exc){
                System.out.println("failed with IOException to write data: ");
                exc.printStackTrace();
                // try to close socket and data streams
                try{
                    sock.close();
                    is.close();
                    os.close();
                } catch(IOException finallyEx) {
                    System.out.println("exception in close block: ");
                    finallyEx.printStackTrace();
                } finally {
                    // change element states
                    textField1.setDisable(true);
                    textField2.setDisable(true);
                    bsend.setDisable(true);
                    disconnectButton.setDisable(true);
                    connectButton.setDisable(false);
                }
            } catch(Exception exc2){        // catching general exceptions
                System.out.println("failed with Exception to write data: ");
                exc2.printStackTrace();
                // try to close socket and data streams
                try{
                    sock.close();
                    is.close();
                    os.close();
                } catch(IOException finallyEx) {
                    System.out.println("exception in close block: ");
                    finallyEx.printStackTrace();
                } finally {
                    // change element states
                    textField1.setDisable(true);
                    textField2.setDisable(true);
                    bsend.setDisable(true);
                    disconnectButton.setDisable(true);
                    connectButton.setDisable(false);
                }
            }
        });

        // set content to vertical box container
        VBox verticalContent = new VBox(lb, textField1, textField2, bsend);
        verticalContent.setAlignment(Pos.CENTER);
        verticalContent.setSpacing(10);

        connectButton.setOnAction(e -> {
            try{
                // init socket and data streams
                sock = new Socket("localhost", 55001);
                is = new DataInputStream(sock.getInputStream());
                os = new DataOutputStream(sock.getOutputStream());

                // change element states
                textField1.setDisable(false);
                textField2.setDisable(false);
                bsend.setDisable(false);
                disconnectButton.setDisable(false);
                connectButton.setDisable(true);
            } catch(IOException exc){
                System.out.println("failed to create socket and data stream: ");
                exc.printStackTrace();
            } catch(Exception excgen) {
                // change element states back
                textField1.setDisable(true);
                textField2.setDisable(true);
                bsend.setDisable(true);
                disconnectButton.setDisable(true);
                connectButton.setDisable(false);

                System.out.println("caught general exception: ");
                excgen.printStackTrace();
            }
        });

        disconnectButton.setOnAction(e -> {
            try{
                // send command to the server to disconnect
                os.writeUTF("!quit");
                // and close the sockets
                sock.close();
                is.close();
                os.close();
            } catch(IOException ioEx) {
                System.out.println("exception in close block: ");
                ioEx.printStackTrace();
            } finally {
                // change element states
                textField1.setDisable(true);
                textField2.setDisable(true);
                bsend.setDisable(true);
                disconnectButton.setDisable(true);
                connectButton.setDisable(false);
            }
        });

        // set conn/disconn buttons to vertical box container
        VBox verticalMangeButtons = new VBox(connectButton, disconnectButton);
        verticalMangeButtons.setAlignment(Pos.CENTER);
        verticalMangeButtons.setSpacing(10);

        HBox rootBox = new HBox(verticalMangeButtons, verticalContent);
        rootBox.setAlignment(Pos.CENTER);
        rootBox.setSpacing(30);

        BorderPane root = new BorderPane(rootBox);     // add to the root vBox with textField, lb, bSend

        Scene scene = new Scene(root, 400, 400);    // crete scene with root

        stage.setTitle("Client");
        stage.setScene(scene);                                   // set scene to the current stare
        stage.show();                                            // and show stage(window)
    }
}
