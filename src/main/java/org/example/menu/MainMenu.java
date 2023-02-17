package org.example.menu;

import org.example.dataManager.databaseHandling.DatabaseAccess;
import org.example.dataManager.jsonHandling.FileManager;
import org.example.portfolioComponents.asset.Asset;
import org.example.portfolioComponents.cash.Cash;
import org.example.walletManager.Wallet;
import org.example.walletManager.WalletDisplay;
import org.example.walletManager.WalletHandler;

import java.util.List;
import java.util.Scanner;

public class MainMenu {

    OptionsPrinter optionsPrinter = new OptionsPrinter();
    Scanner scanner = new Scanner(System.in);
    FileManager fileManager = new FileManager();
    DatabaseAccess databaseAccess = new DatabaseAccess();



    public void runApp(List<Asset> assetList, List<Cash> cashList,boolean isConnectionWithDatabase){

        Wallet wallet = new Wallet(assetList, cashList);
        WalletHandler walletHandler = new WalletHandler();
        WalletDisplay walletDisplay = new WalletDisplay();

        int choice;
        do{
            optionsPrinter.showMainMenuOptions();
            choice = getChoice();
            switch (choice) {
                case (1):   wallet.addAsset(); break;
                case (2):   wallet.sellAsset(); break;
                case (3):   walletDisplay.showAllAssets(wallet.getAssetList(),wallet.getCashList()); break;
                case (4):   walletDisplay.showTheTypeOfAsset(wallet.getAssetList()); break;
                case (5):   wallet.setMarketPrice(wallet.getAssetList());break;
                case (6):   walletHandler.clearWallet(assetList,cashList); break;
                case (7):   wallet.addCash(); break;
                case (0):     if(isConnectionWithDatabase == true) {
                    System.out.println("save to database final");databaseAccess.updateDatabaseWallet(assetList,cashList); }
                                 else fileManager.saveToFile(assetList,cashList);break;
                default:    System.out.println("Wrong command, try again");break;
            }
        }while (choice != 0);

    }
    protected int getChoice() {
        int choice;
        choice = scanner.nextInt();
        return choice;
    }
}
