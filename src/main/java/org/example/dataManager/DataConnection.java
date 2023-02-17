package org.example.dataManager;

import org.example.dataManager.databaseHandling.DatabaseAccess;
import org.example.dataManager.jsonHandling.FileManager;
import org.example.menu.MainMenu;

import java.util.List;
import java.util.Scanner;

public class DataConnection {

    private List assetList;
    private List cashList;
    private boolean isConnectionWithDatabase;
    private FileManager fileManager = new FileManager();
    private MainMenu mainMenu = new MainMenu();
    Scanner scanner = new Scanner(System.in);

    public void checkTheConnectionWithDatabase() {

        System.out.println("Want you work on database or local files ?");
        System.out.println("1 : DATABASE");
        System.out.println("2 : LOCAL FILES");
        int choice = scanner.nextInt();
        switch (choice){
            case (1): {
                try {
                    DatabaseAccess databaseAccess = new DatabaseAccess();
                    assetList = databaseAccess.getAssets();
                    cashList = databaseAccess.getCash();
                    System.out.println("DOWNLOAD BY DATABASE WAS FINAL");
                    isConnectionWithDatabase = true;
                } catch (Exception e) {
                    System.out.println("DON'T CONNECTION WITH DATABASE, WE'LL WORK ON LOCAL FILE");
                    assetList = fileManager.readAssetsFromFile();
                    cashList = fileManager.readCashFromFile();
                    isConnectionWithDatabase = false;
                } finally {
                    mainMenu.runApp(assetList, cashList, isConnectionWithDatabase);
                }
            }break;
            case(2):{
                assetList = fileManager.readAssetsFromFile();
                cashList = fileManager.readCashFromFile();
                isConnectionWithDatabase = false;
                mainMenu.runApp(assetList, cashList, isConnectionWithDatabase);
            }break;
            default:
                System.out.println("WRONG COMMAND , BYE..");break;
            }
    }
}
