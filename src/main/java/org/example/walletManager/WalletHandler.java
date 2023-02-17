package org.example.walletManager;

import org.example.portfolioComponents.asset.Asset;
import org.example.portfolioComponents.cash.Cash;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WalletHandler {


    public void clearWallet(List assetList,List cashList){
        assetList.clear();
        cashList.clear();
    }
    public float valueInAssets(List<Asset> assetList){
        float value = 0;
        for (Asset assets : (assetList)) {
            value += (assets.getAmount() * assets.getMarketPrice());
        }
        return value;
    }
    public float valueInCash(List<Cash> cashList){
        float value;
        Cash cash = cashList.get(0);
        value = cash.getValue();
        return value;
    }
    public float totalPortfolioValue(List<Asset> assetList,List<Cash> cashList){
        float value = 0;
        value = valueInAssets(assetList) + valueInCash(cashList);
        return value;
    }
    public float assetPercentShareOfWallet(Asset asset, float totalPortfolioValue){
        float value;
        value = ((asset.getAmount() * asset.getMarketPrice())/totalPortfolioValue)*100;
        return value;
    }
    public void resultOfInvestment(float totalPortfolioValue, float payments){
        DecimalFormat df = new DecimalFormat("#.##");
        if(totalPortfolioValue >= payments){
            float profit = totalPortfolioValue - payments;
            float profitInPercent = ((profit/payments)*100) ;
            System.out.println("PROFIT: +" + df.format(profit) + "zł -> +" + df.format(profitInPercent) + "%" );
        }else{
            float loss = payments -totalPortfolioValue;
            float lossInPercent = ((loss/payments)*100);
            System.out.println("LOSS: -" + df.format(loss) + "zł  > -" + df.format(lossInPercent) + "%" );
        }
    }
    public List assetListAfterSort(List<Asset> assetList){

        Collections.sort(assetList, new Comparator<Asset>() {
            public int compare(Asset asset1, Asset asset2) {
                if (asset1.getValue() < asset2.getValue()) {
                    return -1;
                } else if (asset1.getValue() == asset2.getValue()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        return assetList;
    }
}
