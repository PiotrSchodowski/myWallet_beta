package org.example.walletManager;

import org.example.portfolioComponents.asset.Asset;
import org.example.portfolioComponents.cash.Cash;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Wallet {
    private final List<Asset> assetList;
    private final List<Cash> cashList;
    Scanner scanner = new Scanner(System.in);

    public Wallet(List<Asset> assetList, List<Cash> cashList){
        this.assetList = assetList;
        this.cashList = cashList;

    }
    /*====================================================CASH METHODS=======================================================*/
    public void addCash(){
        System.out.println("Get value of cash: ");
        float cashValue = scanner.nextFloat();
        Cash cash = new Cash(cashValue);
        if(cashList.isEmpty()){
            cashList.add(cash);
            cashList.add(cash);
        }else{
            Cash oldCash = cashList.get(0);
            Cash newCash = new Cash(0);
            newCash.value = oldCash.value + cash.value;
            cashList.set(0,newCash);
            Cash primaryCash = cashList.get(1);
            newCash.value = primaryCash.value + cash.value;
            cashList.set(1,newCash);
        }
    }
    public void decreaseCash(float price, float amount) {
        float value = price * amount;
        Cash cashMinus = new Cash(value);
        Cash oldValue = cashList.get(0);
        Cash newValue = new Cash(0);
        newValue.value = oldValue.value - cashMinus.value;
        cashList.set(0,newValue);
    }
    public void increaseCash(float price, float amount) {
        float value = price * amount;
        Cash cashPlus = new Cash(value);
        Cash oldValue = cashList.get(0);
        Cash newValue = new Cash(0);
        newValue.value = oldValue.value + cashPlus.value;
        cashList.set(0,newValue);
    }
    public boolean haveEnoughCash(float amount, float price){
        float value = amount * price;
        float valueOfCash = cashList.stream().collect(Collectors.summingInt(cash -> (int)cash.value));
        if(valueOfCash<value){
            return false;
        }else return true;
    }

    /*====================================================ASSET METHODS=======================================================*/
    private void addNewAsset(String name) {
        String typeOfAsset = getTypeOfAsset();
        float amount = getAmountOfAsset();
        float price = getFromUserPriceOfAsset();
        if(haveEnoughCash(amount,price)) {
            decreaseCash(price, amount);
            assetList.add(new Asset(typeOfAsset, name, amount, price));
        }else System.out.println("You don't have enough money");
    }
    public void addAsset() {
        System.out.println("Write initial name: ");
        String nameOfAsset;
        nameOfAsset = scanner.next();
        boolean shouldBeNewAssetCreated = true;
        for (int i = 0; i < assetList.size(); i++) {
            Asset asset = assetList.get(i);
            String name = asset.getName();
            if (name.equalsIgnoreCase(nameOfAsset)) {
                shouldBeNewAssetCreated = false;
                float amount = getAmountOfAsset();
                float price = getFromUserPriceOfAsset();
                if(haveEnoughCash(amount,price)) {
                    decreaseCash(price, amount);
                    asset.addAmount(amount, price);
                    assetList.set(i, asset);
                }else System.out.println("You don't have enough money");
                break;
            }
        }
        if (shouldBeNewAssetCreated) {
            addNewAsset(nameOfAsset);
        }
    }
    public void sellAsset(){
        System.out.println("Write initial name: ");
        String nameOfAsset;
        nameOfAsset = scanner.next();
        boolean shouldBeAssetSell = true;
        for (int i = 0; i < assetList.size(); i++) {
            Asset asset = assetList.get(i);
            String name = asset.getName();
            if (name.equalsIgnoreCase(nameOfAsset)) {
                shouldBeAssetSell = false;
                float amount = getAmountOfAsset();
                float price = getFromUserPriceOfAsset();
                asset.removeAmount(amount, price);
                increaseCash(price,amount);
                assetList.set(i, asset);
                break;
            }
        }
        if (shouldBeAssetSell) {
            System.out.println("you don't have this asset");
        }
    }
    public float getAmountOfAsset() {
        System.out.println("Get amount: ");
        float amountOfAsset = scanner.nextFloat();
        return amountOfAsset;
    }
    public float getFromUserPriceOfAsset() {
        System.out.println("Get price: ");
        float priceOfAsset = scanner.nextFloat();
        return priceOfAsset;
    }
    public String getTypeOfAsset(){
        System.out.println("Get type of asset: ");
        String typeOfAsset = scanner.next();
        return typeOfAsset;
    }
    public void setMarketPrice(List<Asset> assetList){

        for (int i = 0; i < assetList.size(); i++) {
            Asset asset = assetList.get(i);
            System.out.println("Get actually market price: " + asset.getName() );
            asset.setMarketPrice(scanner.nextFloat());
            assetList.set(i,asset);
        }
    }
    public List<Asset> getAssetList() {
        return assetList;
    }
    public List<Cash> getCashList() {return cashList;}
}
