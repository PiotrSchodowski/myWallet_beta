package org.example.dataManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.portfolioComponents.asset.Asset;
import org.example.portfolioComponents.cash.Cash;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    String cashJson = "files\\cash.json";
    String assetsJson = "files\\assets.json";

    public void saveToFile(List<Asset> assetList, List<Cash> cashList){

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(new File(cashJson), cashList);
            mapper.writeValue(new File(assetsJson), assetList);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public List<Cash> readCashFromFile() {

        Gson gson = new Gson();
        Cash[] object;
        if(new File(cashJson).length() != 0) {
            try (FileReader reader = new FileReader(cashJson)) {
                object = gson.fromJson(reader, Cash[].class);
            } catch (Exception e) {
                System.out.println("You don't have money, please donate");
                return new ArrayList<>();
            }
            return Arrays.asList(object);
        }else return new ArrayList<>();
    }
    public List<Asset> readAssetsFromFile() {

        Gson gson = new Gson();
        Asset[] object ;
        if(new File(assetsJson).length() != 0) {
            try (FileReader reader = new FileReader(assetsJson)) {
                object = gson.fromJson(reader, Asset[].class);
            } catch (Exception e) {
                System.out.println("You don't have assets");
                return new ArrayList<>();
            }
            return Arrays.asList(object);
        }else return new ArrayList<>();
    }

}
