package com.example.moneytracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MonthListActivity extends AppCompatActivity implements ItemClickListener {

    private List<MonthModel> databaseModels;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;

    private MonthListAdapter monthListAdapter;
    private RecyclerView monthListRecyclerView;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_list);

        setupUI();
    }

    private void setupUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());

        monthListRecyclerView = findViewById(R.id.rv_month_list);
        backButton = findViewById(R.id.iv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        databaseModels = new ArrayList<>();

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        databaseModels.add(data.getValue(MonthModel.class));
                    }

                    setAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setAdapter() {
        monthListAdapter = new MonthListAdapter();
        monthListAdapter.setMonthsList(databaseModels);
        monthListAdapter.setItemClickListener(this);

        monthListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        monthListRecyclerView.addItemDecoration(dividerItemDecoration);
        monthListRecyclerView.setAdapter(monthListAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, BillListActivity.class);
        intent.putExtra("month_info", monthListAdapter.getItem(position));
        startActivity(intent);
    }
}