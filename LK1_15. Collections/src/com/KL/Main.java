package com.KL;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonBar.ButtonData;
import java.lang.String;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Orientation;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.xml.soap.Text;

import static java.util.Comparator.naturalOrder;


public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    private ObservableList<Transportation> trList;           // create instance of transportation list
    private boolean ascDescCostOrder = true;
    private boolean ascDescDestOrder = true;

    public void start(Stage stage) throws Exception{
        trList = FXCollections.observableArrayList(          // create initial items manually
                new Transportation(1117, "Riga mall", "5 big boxes", 50.05, "MAN Truck", "Minsk", 500, "16.02.19"),
                new Transportation(1112, "Corona mall", "2 containers", 40.78, "Volvo Truck", "Minsk", 100, "16.02.19"),
                new Transportation(1116, "Ashan mall", "10 boxes", 80.28, "MAN Truck", "Moscow", 1000, "17.02.19"),
                new Transportation(1114, "Glendale mall", "5 packets", 10.08, "BLR Airlines", "California", 5000, "19.07.19")
        );
        Comparator<Transportation> comparator = Comparator.comparingInt(Transportation::getNumber);        // sort by number
        trList.sort(comparator);                                                                           // sort in ascending order

        TableView<Transportation> table = new TableView<>(trList);                          // create table view of trList elements

        TableColumn<Transportation, Integer> idColumn = new TableColumn<>("ID");        // create table column ID Column
        idColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        idColumn.setPrefWidth(50);
        table.getColumns().add(idColumn);

        TableColumn<Transportation, String> customerColumn = new TableColumn<>("Customer");        // create customer column ID Column
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));
        customerColumn.setPrefWidth(120);
        table.getColumns().add(customerColumn);

        TableColumn<Transportation, String> viewColumn = new TableColumn<>("View");                // create view column ID Column
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("view"));
        viewColumn.setPrefWidth(120);
        table.getColumns().add(viewColumn);

        TableColumn<Transportation, Double> massColumn = new TableColumn<>("Mass");                 // create mass column ID Column
        massColumn.setCellValueFactory(new PropertyValueFactory<>("mass"));
        massColumn.setPrefWidth(80);
        table.getColumns().add(massColumn);

        TableColumn<Transportation, String> trColumn = new TableColumn<>("Transport");        // create transport column ID Column
        trColumn.setCellValueFactory(new PropertyValueFactory<>("transport"));
        trColumn.setPrefWidth(100);
        table.getColumns().add(trColumn);

        TableColumn<Transportation, String> destColumn = new TableColumn<>("Destination");        // create destination country column ID Column
        destColumn.setCellValueFactory(new PropertyValueFactory<>("destinationCountry"));
        destColumn.setPrefWidth(120);
        table.getColumns().add(destColumn);

        TableColumn<Transportation, Double> costColumn = new TableColumn<>("Cost");        // create cost column ID Column
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        costColumn.setPrefWidth(90);
        table.getColumns().add(costColumn);

        TableColumn<Transportation, String> dateColumn = new TableColumn<>("Date");        // create date column ID Column
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(100);
        table.getColumns().add(dateColumn);

        TableView.TableViewSelectionModel<Transportation> selectionModel = table.getSelectionModel();       // get current selection model
        selectionModel.setSelectionMode(SelectionMode.SINGLE);                                              // set selection Mode
        selectionModel.select(0);                                                                      // select 0 by default
//        selectionModel.selectedItemProperty().addListener(new ChangeListener<Transportation>() {            // add listener
//            public void changed(ObservableValue<? extends Transportation> trList, Transportation oldVal, Transportation newVal){
//
//            }
//        });

        Button addbtn = new Button("Add");                                                              // create instance of add button
        addbtn.setMaxWidth(Double.MAX_VALUE);
        addbtn.setOnAction(new EventHandler<ActionEvent>(){                                                 // set Event Handler
            public void handle(ActionEvent event) {

                Label idLabel = new Label("ID");
                Label customerLabel = new Label("Customer");
                Label viewLabel = new Label("View");
                Label massLabel = new Label("Mass");
                Label trLabel = new Label("Transport");
                Label destLabel = new Label("Destination Country");
                Label costLabel = new Label("Cost");
                Label dateLabel = new Label("Date");
                VBox labelLayout = new VBox(30, idLabel, customerLabel, viewLabel,
                                        massLabel, trLabel, destLabel,
                                        costLabel, dateLabel);                                                // vbox for label stuff
                labelLayout.setPadding(new Insets(30, 20, 20, 20));
                labelLayout.setMaxWidth(Double.MAX_VALUE);

                TextField idText = new TextField();
                TextField customerText = new TextField();
                TextField viewText = new TextField();
                TextField massText = new TextField();
                TextField trText = new TextField();
                TextField destText = new TextField();
                TextField costText = new TextField();
                TextField dateText = new TextField();
                VBox textLayout = new VBox(20, idText, customerText,                                         // vbox for text stuff
                                            viewText, massText, trText,
                                            destText, costText, dateText);
                textLayout.setPadding(new Insets(20, 20, 20, 20));
                textLayout.setMaxWidth(Double.MAX_VALUE);

                HBox mainLayout = new HBox(20, labelLayout, textLayout);

                DialogPane pane = new DialogPane();                                                             // create pane for mainLayout
                pane.setContent(mainLayout);

                Dialog<ButtonType> addDialog = new Dialog<>();                                                   // create new add dialog
                addDialog.setDialogPane(pane);                                                                   // set pane
                addDialog.setTitle("Add new transportation");                                                    // set title
                ButtonType addButton = new ButtonType("Add", ButtonData.APPLY);                             // add "add" button/ or pane.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                addDialog.getDialogPane().getButtonTypes().add(addButton);
                ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);                // add "close" button
                addDialog.getDialogPane().getButtonTypes().add(cancelButton);

                addDialog.setWidth(500);
                addDialog.setHeight(500);
                addDialog.initOwner(stage);                                                                 // init owner
                addDialog.initModality(Modality.APPLICATION_MODAL);                                         // create application modal
                Optional<ButtonType> response = addDialog.showAndWait();
                if(response.isPresent() && response.get() == addButton)
                {
                    int id = 0;
                    double mass = 0, cost = 0;
                    // parse int and double
                    try{
                        id = Integer.parseInt(idText.getText());
                    } catch(NumberFormatException e) {
                        Alert alert = new Alert(AlertType.ERROR, "Error parsing ID", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                    try{
                        mass = Double.parseDouble(massText.getText());
                        cost = Double.parseDouble(costText.getText());
                    } catch(NumberFormatException e) {
                        Alert alert = new Alert(AlertType.ERROR, "Error parsing mass or/and cost", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                    // add new transportation
                    trList.add(new Transportation(id, customerText.getText(), viewText.getText(), mass,
                                                   trText.getText(), destText.getText(), cost, dateText.getText()));
                    addDialog.close();
                } else {
                    addDialog.close();
                }
            }
        });

        Button editbtn = new Button("Edit");                                                            // create edit button
        editbtn.setMaxWidth(Double.MAX_VALUE);
        editbtn.setOnAction(new EventHandler<ActionEvent>() {                                                // set Event Handler
            public void handle(ActionEvent event) {
                Label idLabel = new Label("ID");
                Label customerLabel = new Label("Customer");
                Label viewLabel = new Label("View");
                Label massLabel = new Label("Mass");
                Label trLabel = new Label("Transport");
                Label destLabel = new Label("Destination Country");
                Label costLabel = new Label("Cost");
                Label dateLabel = new Label("Date");
                VBox labelLayout = new VBox(30, idLabel, customerLabel, viewLabel,
                        massLabel, trLabel, destLabel,
                        costLabel, dateLabel);                                                // vbox for label stuff
                labelLayout.setPadding(new Insets(30, 20, 20, 20));
                labelLayout.setMaxWidth(Double.MAX_VALUE);

                TextField idText = new TextField();
                TextField customerText = new TextField();
                TextField viewText = new TextField();
                TextField massText = new TextField();
                TextField trText = new TextField();
                TextField destText = new TextField();
                TextField costText = new TextField();
                TextField dateText = new TextField();
                VBox textLayout = new VBox(20, idText, customerText,                                      // vbox for text stuff
                        viewText, massText, trText,
                        destText, costText, dateText);
                textLayout.setPadding(new Insets(20, 20, 20, 20));
                textLayout.setMaxWidth(Double.MAX_VALUE);

                HBox mainLayout = new HBox(20, labelLayout, textLayout);

                DialogPane pane = new DialogPane();                                                              // create pane for mainLayout
                pane.setContent(mainLayout);

                Dialog<ButtonType> addDialog = new Dialog<>();                                                   // create new add dialog
                addDialog.setDialogPane(pane);                                                                   // set pane
                addDialog.setTitle("Edit transportation");                                                    // set title
                ButtonType addButton = new ButtonType("Edit", ButtonData.APPLY);                            // add "add" button/ or pane.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
                addDialog.getDialogPane().getButtonTypes().add(addButton);
                ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);                // add "close" button
                addDialog.getDialogPane().getButtonTypes().add(cancelButton);

                addDialog.setWidth(500);
                addDialog.setHeight(500);
                addDialog.initOwner(stage);                                                                 // init owner
                addDialog.initModality(Modality.APPLICATION_MODAL);                                         // create application modal
                Optional<ButtonType> response = addDialog.showAndWait();
                if(response.isPresent() && response.get() == addButton)
                {
                    int id = 0;
                    double mass = 0, cost = 0;
                    // parse int and double
                    try{
                        id = Integer.parseInt(idText.getText());
                    } catch(NumberFormatException e) {
                        Alert alert = new Alert(AlertType.ERROR, "Error parsing ID", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                    try{
                        mass = Double.parseDouble(massText.getText());
                        cost = Double.parseDouble(costText.getText());
                    } catch(NumberFormatException e) {
                        Alert alert = new Alert(AlertType.ERROR, "Error parsing mass or/and cost", ButtonType.OK);
                        alert.showAndWait();
                        return;
                    }
                    // swap info if not empty list and add if empty
                    if(!trList.isEmpty()) {
                        table.getSelectionModel().getSelectedItem().setNumber(id);
                        table.getSelectionModel().getSelectedItem().setCustomer(customerText.getText());
                        table.getSelectionModel().getSelectedItem().setView(viewText.getText());
                        table.getSelectionModel().getSelectedItem().setMass(mass);
                        table.getSelectionModel().getSelectedItem().setTransport(trText.getText());
                        table.getSelectionModel().getSelectedItem().setDestinationCountry(destText.getText());
                        table.getSelectionModel().getSelectedItem().setCost(cost);
                        table.getSelectionModel().getSelectedItem().setDate(dateText.getText());
                    } else{
                        trList.add(new Transportation(id, customerText.getText(), viewText.getText(), mass,
                                trText.getText(), destText.getText(), cost, dateText.getText()));
                    }

                    addDialog.close();
                } else {
                    addDialog.close();
                }
            }
        });

        Button deletebtn = new Button("Delete");                                                        // create delete button
        deletebtn.setMaxWidth(Double.MAX_VALUE);
        deletebtn.setOnAction(new EventHandler<ActionEvent>() {                                              // set Event Handler
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.WARNING, "Do u really want to delete?", ButtonType.YES, ButtonType.NO);  // ask user for deletion
                Optional<ButtonType> response = alert.showAndWait();
                if(response.isPresent() && response.get() == ButtonType.YES) {
                    trList.removeAll(table.getSelectionModel().getSelectedItem());
                } else
                    return;
            }
        });

        Button sortByCost = new Button("Sort by cost");                                                  // create sort by cost button
        sortByCost.setMaxWidth(Double.MAX_VALUE);
        sortByCost.setOnAction(new EventHandler<ActionEvent>() {                                              // set Event Handler
            public void handle(ActionEvent event) {
                Comparator<Transportation> comparator = Comparator.comparingDouble(Transportation::getCost);
                if (!ascDescCostOrder) {
                    comparator = comparator.reversed();
                }
                FXCollections.sort(trList, comparator);
                ascDescCostOrder = !ascDescCostOrder;
            }
        });

        Button sortByDest =  new Button("Sort by destination");                                         // set sort by destination
        sortByDest.setMaxWidth(Double.MAX_VALUE);
        sortByDest.setOnAction(new EventHandler<ActionEvent>() {                                                 // set Event Handler
            public void handle(ActionEvent event) {
                if(!ascDescDestOrder)
                {
                    FXCollections.sort(trList, (a, b) -> b.getDestinationCountry().compareToIgnoreCase(a.getDestinationCountry()));
                } else{
                    FXCollections.sort(trList, (a, b) -> a.getDestinationCountry().compareToIgnoreCase(b.getDestinationCountry())); // sort by destination in ascending order
                }
                ascDescDestOrder = !ascDescDestOrder;
            }
        });

        VBox buttonLayout = new VBox(20, addbtn, editbtn, deletebtn, sortByCost, sortByDest);            // layout with buttons
        buttonLayout.setPadding(new Insets(50, 20, 10, 20));
        HBox root = new HBox(20, table, buttonLayout);                                                   // and hbox layout for table and button layouts
        root.setHgrow(table, Priority.ALWAYS);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Laba 1. Collections");
        stage.show();
    }
}


