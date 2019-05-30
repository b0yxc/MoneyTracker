package com.example.moneytracker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MonthModel implements Parcelable {

    private double amount;
    private List<BillModel> billList = new ArrayList<>();

    public MonthModel() {
    }

    public MonthModel(double amount, BillModel bill) {
        this.amount = amount;
        this.billList.add(bill);
    }

    protected MonthModel(Parcel in) {
        amount = in.readDouble();
        billList = in.createTypedArrayList(BillModel.CREATOR);
    }

    public static final Creator<MonthModel> CREATOR = new Creator<MonthModel>() {
        @Override
        public MonthModel createFromParcel(Parcel in) {
            return new MonthModel(in);
        }

        @Override
        public MonthModel[] newArray(int size) {
            return new MonthModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeTypedList(billList);
    }
}
