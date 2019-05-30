package com.example.moneytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BillListActivity extends AppCompatActivity {

    private MonthModel month;

    private BillListAdapter billListAdapter;
    private RecyclerView billRecyclerView;

    private TextView monthTextView;
    private ImageView backButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);

        getExtras();
        setupUI();
        setAdapter();
    }

    private void getExtras() {
        if (getIntent().hasExtra("month_info")) {
            month = getIntent().getParcelableExtra("month_info");
        }
    }

    private void setupUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());

        billRecyclerView = findViewById(R.id.rv_bill_list);
        monthTextView = findViewById(R.id.tv_month_name);

        backButton = findViewById(R.id.iv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        monthTextView.setText(getResources().getStringArray(R.array.month_array)[month.getBillList().get(0).getMonth() - 1]);
    }

    private void setAdapter() {
        billListAdapter = new BillListAdapter();
        billListAdapter.setBillList(month.getBillList());

        billRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        billRecyclerView.addItemDecoration(dividerItemDecoration);
        billRecyclerView.setAdapter(billListAdapter);
    }
}
