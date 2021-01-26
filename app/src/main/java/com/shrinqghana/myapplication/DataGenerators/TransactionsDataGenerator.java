package com.shrinqghana.myapplication.DataGenerators;

import com.shrinqghana.myapplication.Models.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class TransactionsDataGenerator {


    // DECLARING THE DATA ARRAY LIST
    static List<TransactionModel> allData = new ArrayList<>();

    // SETTING/RESETTING ALL SUGGESTED LINKUPS DATA
    public static void setAllDatasAfresh(List<TransactionModel> newAllData) {
        TransactionsDataGenerator.allData = newAllData;
    }

    // ADDING ONE DATA TO ARRAY LIST
    public static boolean addOneData(TransactionModel model) {
        return allData.add(model);
    }

    // GETTING ALL DATA AS ARRAY LIST
    public static List<TransactionModel> getAllData() {
        return allData;
    }

    // ADDING ONE DATA TO A DESIRED POSITION IN ARRAY LIST
    public static void addOneDataToDesiredPosition(int i, TransactionModel model){
        allData.add(i, model);
    }
}
