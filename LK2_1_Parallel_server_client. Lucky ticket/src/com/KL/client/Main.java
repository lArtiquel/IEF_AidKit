package com.KL.client;

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
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;


public class Main extends Application{

    private Socket sock;
    private DataInputStream is;
    private DataOutputStream os;


    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage stage){

        // init socket and data streams
        try{
            sock = new Socket("localhost", 55001);
            // allows us to invoke strings, not buffers from streams
            is = new DataInputStream(sock.getInputStream());
            os = new DataOutputStream(sock.getOutputStream());
        } catch(IOException e){
            System.out.println("failed to create socket and data stream: ");
            e.printStackTrace();
        }

        // create instance of text field
        TextField textField = new TextField("987654");
        textField.setPromptText("Enter number of ticket...");
        textField.setMaxWidth(Double.MAX_VALUE);
        // set text formatter
        TextFormatter<String> textFormatter = getTextFormatter();   // get text formatter
        textField.setTextFormatter(textFormatter);                  // set text formatter

        // create instance of button
        Button bsend = new Button("Send to server");
        bsend.setOnAction(e -> {
                if(textField.getText().length() != 6){
                    // if it's not a proper ticket number
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Ticket should have 6 numbers only!!!");
                    // show and wait
                    alert.showAndWait();
                } else {
                    // send ticket to the server
                    try {
                        final String request = textField.getText();
                        // send ticket number to server
                        os.writeUTF(request);
                        // check for quit code
                        if(request.compareTo("987654") == 0){        // exit code
                            sock.close();
                            is.close();
                            os.close();

                            // set on close application
                            Platform.exit();        // to call stop()
                            System.exit(0);   // to close JVM
                        }
                        // receive response from server
                        String response = is.readUTF();

                        // display result into display
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Result Dialog");
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
                            // set on close application
                            Platform.exit();        // to call stop()
                            System.exit(0);   // to close JVM
                        }
                    }
                    catch(Exception exc2){
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
                            // set on close application
                            Platform.exit();        // to call stop()
                            System.exit(0);   // to close JVM
                        }
                    }
                }
        });


        HBox hBox = new HBox(textField, bsend);
        hBox.setAlignment(Pos.CENTER);

        Label lb = new Label("Enter number of ticket...");
        lb.setStyle("-fx-font-weight: bold");                   // set font for label
        lb.setAlignment(Pos.CENTER);                            // set allignment to the center

        VBox vBox = new VBox(lb, hBox);
        vBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane(vBox);     // add to the root vBox with textField, lb, bSend
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
}

