package com.example.moneytracker;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.BillViewHolder> {

    private List<BillModel> billList;

    public BillListAdapter() {
        billList = new ArrayList<>();
    }

    public void setBillList(List<BillModel> billList) {
        this.billList.clear();
        this.billList.addAll(billList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BillViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bill, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder billViewHolder, int i) {
        BillModel bill = billList.get(i);
        if (bill != null) {
            billViewHolder.setBillInfo(bill);
        }
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public BillModel getItem(int position) {
        return billList.get(position);
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {

        private TextView payerInfoTextView, recipientInfoTextView, billDescriptionTextView, billAmountTextView;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            payerInfoTextView = itemView.findViewById(R.id.tv_payer_info);
            recipientInfoTextView = itemView.findViewById(R.id.tv_recipient_info);
            billDescriptionTextView = itemView.findViewById(R.id.tv_bill_description);
            billAmountTextView = itemView.findViewById(R.id.tv_bill_amount);
        }

        public void setBillInfo(BillModel bill) {
            payerInfoTextView.setText("Platitelj: " + bill.getPayerInfo());
            recipientInfoTextView.setText("Primatelj: " + bill.getRecipientInfo());
            billDescriptionTextView.setText("Opis plaćanja: " + bill.getBillDescription());
            billAmountTextView.setText(String.valueOf(bill.getPrice()));
            if (bill.getPrice() > 0) {
                billAmountTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorGreen));
            } else {
                billAmountTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorRed));
            }
        }
    }
}
