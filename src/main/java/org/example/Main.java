package org.example;
import org.example.dataManager.DataConnection;

public class Main {
    public static void main(String[] args) {

        DataConnection dataConnection = new DataConnection();
        dataConnection.checkTheConnectionWithDatabase();
    }
}