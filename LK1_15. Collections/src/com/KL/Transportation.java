package com.KL;
import javafx.beans.property.*;
import java.lang.String;

public class Transportation {
    private SimpleIntegerProperty number;
    private SimpleStringProperty customer;
    private SimpleStringProperty view;
    private SimpleDoubleProperty mass;
    private SimpleStringProperty transport;
    private SimpleStringProperty destinationCountry;
    private SimpleDoubleProperty cost;
    private SimpleStringProperty date;

    public Transportation(int number, String customer,
                          String view, double mass,
                          String transport, String destinationCountry,
                          double cost, String date){
        this.number = new SimpleIntegerProperty(number);
        this.customer = new SimpleStringProperty(customer);
        this.view = new SimpleStringProperty(view);
        this.mass = new SimpleDoubleProperty(mass);
        this.transport = new SimpleStringProperty(transport);
        this.destinationCountry = new SimpleStringProperty(destinationCountry);
        this.cost = new SimpleDoubleProperty(cost);
        this.date = new SimpleStringProperty(date);
    }

    public static void sortByCost(){

    }

    public static void findByDestination(String destination){

    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getCustomer() {
        return customer.get();
    }

    public SimpleStringProperty customerProperty() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer.set(customer);
    }

    public String getView() {
        return view.get();
    }

    public SimpleStringProperty viewProperty() {
        return view;
    }

    public void setView(String view) {
        this.view.set(view);
    }

    public double getMass() {
        return mass.get();
    }

    public SimpleDoubleProperty massProperty() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass.set(mass);
    }

    public String getTransport() {
        return transport.get();
    }

    public SimpleStringProperty transportProperty() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport.set(transport);
    }

    public String getDestinationCountry() {
        return destinationCountry.get();
    }

    public SimpleStringProperty destinationCountryProperty() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry.set(destinationCountry);
    }

    public double getCost() {
        return cost.get();
    }

    public SimpleDoubleProperty costProperty() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
