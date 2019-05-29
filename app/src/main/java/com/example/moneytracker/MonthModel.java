package com.example.moneytracker;

import java.util.ArrayList;
import java.util.List;

public class MonthModel {

    private double amount;
    private List<BillModel> billList = new ArrayList<>();

    public MonthModel() {
    }

    public MonthModel(double amount, BillModel bill) {
        this.amount = amount;
        this.billList.add(bill);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBill(BillModel bill) {
        billList.add(bill);
    }

    public List<BillModel> getBillList() {
        return billList;
    }

    public void setBillList(List<BillModel> billList) {
        this.billList = billList;
    }
}
