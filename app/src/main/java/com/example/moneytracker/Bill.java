package com.example.moneytracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Bill implements Parcelable {

    private double price;
    private String payerInfo;
    private String recipientInfo;
    private String recipientIBAN;
    private String billModel;
    private String referenceNumber;
    private String purposeCode;
    private String billDescription;
    private int month;
    private boolean billType;

    public Bill(double price, String payerInfo, String recipientInfo, String billDescription) {
        this.price = price;
        this.payerInfo = payerInfo;
        this.recipientInfo = recipientInfo;
        this.billDescription = billDescription;
    }

    public Bill(double price, String payerInfo, String recipientInfo, String recipientIBAN, String billModel, String referenceNumber, String purposeCode, String billDescription, boolean billType) {
        this.price = price;
        this.payerInfo = payerInfo;
        this.recipientInfo = recipientInfo;
        this.recipientIBAN = recipientIBAN;
        this.billModel = billModel;
        this.referenceNumber = referenceNumber;
        this.purposeCode = purposeCode;
        this.billDescription = billDescription;
        this.billType = billType;
    }

    protected Bill(Parcel in) {
        price = in.readDouble();
        payerInfo = in.readString();
        recipientInfo = in.readString();
        recipientIBAN = in.readString();
        billModel = in.readString();
        referenceNumber = in.readString();
        purposeCode = in.readString();
        billDescription = in.readString();
        month = in.readInt();
        billType = in.readByte() != 0;
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPayerInfo() {
        return payerInfo;
    }

    public void setPayerInfo(String payerInfo) {
        this.payerInfo = payerInfo;
    }

    public String getRecipientInfo() {
        return recipientInfo;
    }

    public void setRecipientInfo(String recipientInfo) {
        this.recipientInfo = recipientInfo;
    }

    public String getRecipientIBAN() {
        return recipientIBAN;
    }

    public void setRecipientIBAN(String recipientIBAN) {
        this.recipientIBAN = recipientIBAN;
    }

    public String getBillModel() {
        return billModel;
    }

    public void setBillModel(String billModel) {
        this.billModel = billModel;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getPurposeCode() {
        return purposeCode;
    }

    public void setPurposeCode(String purposeCode) {
        this.purposeCode = purposeCode;
    }

    public String getBillDescription() {
        return billDescription;
    }

    public void setBillDescription(String billDescription) {
        this.billDescription = billDescription;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean isBillType() {
        return billType;
    }

    public void setBillType(boolean billType) {
        this.billType = billType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(price);
        dest.writeString(payerInfo);
        dest.writeString(recipientInfo);
        dest.writeString(recipientIBAN);
        dest.writeString(billModel);
        dest.writeString(referenceNumber);
        dest.writeString(purposeCode);
        dest.writeString(billDescription);
        dest.writeInt(month);
        dest.writeByte((byte) (billType ? 1 : 0));
    }
}
