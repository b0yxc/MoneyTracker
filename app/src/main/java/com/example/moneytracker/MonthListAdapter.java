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

public class MonthListAdapter extends RecyclerView.Adapter<MonthListAdapter.MonthViewHolder> {

    private List<MonthModel> monthsList;
    private ItemClickListener itemClickListener;

    public MonthListAdapter() {
        monthsList = new ArrayList<>();
    }

    public void setMonthsList(List<MonthModel> monthsList) {
        this.monthsList.clear();
        this.monthsList.addAll(monthsList);
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MonthViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_month, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder monthViewHolder, int i) {
        MonthModel month = monthsList.get(i);
        if (month != null) {
            monthViewHolder.setMonthInfo(month);
        }
    }

    @Override
    public int getItemCount() {
        return monthsList.size();
    }

    public MonthModel getItem(int position) {
        return monthsList.get(position);
    }

    public class MonthViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView monthNameTextView, monthTotalAmountTextView, numberOfBillsTextView;

        public MonthViewHolder(@NonNull View itemView) {
            super(itemView);
            monthNameTextView = itemView.findViewById(R.id.tv_month_name);
            monthTotalAmountTextView = itemView.findViewById(R.id.tv_month_total_amount);
            numberOfBillsTextView = itemView.findViewById(R.id.tv_bill_number);

            itemView.setOnClickListener(this);
        }

        public void setMonthInfo(MonthModel month) {
            monthNameTextView.setText(itemView.getResources().getStringArray(R.array.month_array)[month.getBillList().get(0).getMonth() - 1]);
            monthTotalAmountTextView.setText(month.getAmount() + " kn");
            if (month.getAmount() > 0) {
                monthTotalAmountTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorGreen));
            } else {
                monthTotalAmountTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorRed));
            }
            numberOfBillsTextView.setText(String.format(itemView.getResources().getString(R.string.bill_number_formats), month.getBillList().size()));
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
