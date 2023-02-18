package org.example.walletManager;

import org.example.portfolioComponents.asset.Asset;
import org.example.portfolioComponents.cash.Cash;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class WalletDisplay {
    Scanner scanner = new Scanner(System.in);

    public void showAllAssets(List<Asset> assetList, List<Cash> cashList){
        System.out.println("                   -----------------> MY WALLET <---------------- \n");
        if(!cashList.isEmpty()) {

            WalletHandler walletHandler = new WalletHandler();
            DecimalFormat df = new DecimalFormat("#.##");
            Cash cashFromDeposit = cashList.get(1);
            float payments = cashFromDeposit.value;
            float valueOfAssets = walletHandler.valueInAssets(assetList);
            float totalPortfolioValue = walletHandler.totalPortfolioValue(assetList,cashList);

            /** Sort list by value **/
            assetList = walletHandler.assetListAfterSort(assetList);

            /** Show list of all assets and their share percentage in wallet **/
            for (Asset assets : (assetList)) {
                System.out.println(assets + "  percent of wallet: " + df.format(walletHandler.assetPercentShareOfWallet(assets, totalPortfolioValue)) + "%");
            }

            /** Show Percent share assets in Wallet **/
            float allAssetsPercentShareOfWallet = (valueOfAssets/totalPortfolioValue)*100 ;
            System.out.println("AssetsValue: " + valueOfAssets + "zł  percent of wallet: " + df.format(allAssetsPercentShareOfWallet) + "%");

            /** Show value of cash in Wallet **/
            System.out.println("CashValue: " +  df.format(walletHandler.valueInCash(cashList)) + "zł");

            /** Show total portfolio value - include cash and assets **/
            System.out.println("TOTAL PORTFOLIO VALUE: " + df.format(totalPortfolioValue) + "zł");

            /** Show total portfolio result in percentage - include cash and assets **/
            walletHandler.resultOfInvestment(totalPortfolioValue,payments);

            System.out.println("\n");

        }else{
            System.out.println("VALUE IN ASSETS: 0zł");
            System.out.println("VALUE IN CASH: 0zł");
        }
    }
    public void showTheTypeOfAsset(List<Asset> assetList){
        System.out.println("Write the type: ");
        String choiceType = scanner.next();
        assetList.stream().filter(str -> str.getType().startsWith(choiceType)).forEach(str -> System.out.println(str));
        System.out.println("\n");
    }
}
