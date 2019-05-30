package com.example.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewBillActivity extends AppCompatActivity implements View.OnClickListener {

    private BillModel bill;
    private MonthModel monthModel;

    private TextInputEditText priceEditText, payerInfoEditText, recipientInfoEditText, recipientIBANEditText, billModelEditText, referenceModelEditText, purposeCodeEditText, billDescriptionEditText;
    private Spinner monthSpinner, billTypeSpinner;
    private Button saveBillButton;
    private ProgressBar progressBar;
    private ImageView backButton;

    private boolean dataSaved = false;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);

        getExtras();
        setupUI();
    }

    private void getExtras() {
        if (getIntent().hasExtra("bill_object")) {
            bill = getIntent().getParcelableExtra("bill_object");
        }

    }

    private void setupUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());

        saveBillButton = findViewById(R.id.btn_save_bill);
        saveBillButton.setOnClickListener(this);

        monthSpinner = findViewById(R.id.sp_month);
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this, R.array.month_array, R.layout.item_spinner);
        monthSpinner.setAdapter(monthAdapter);

        billTypeSpinner = findViewById(R.id.sp_bill_type);
        ArrayAdapter<CharSequence> billTypeAdapter = ArrayAdapter.createFromResource(this, R.array.bill_type_array, R.layout.item_spinner);
        billTypeSpinner.setAdapter(billTypeAdapter);

        priceEditText = findViewById(R.id.et_price);
        payerInfoEditText = findViewById(R.id.et_payer_info);
        recipientInfoEditText = findViewById(R.id.et_recipient_info);
        recipientIBANEditText = findViewById(R.id.et_recipient_iban);
        billModelEditText = findViewById(R.id.et_bill_model);
        referenceModelEditText = findViewById(R.id.et_reference_number);
        purposeCodeEditText = findViewById(R.id.et_purpose_code);
        billDescriptionEditText = findViewById(R.id.et_bill_description);
        progressBar = findViewById(R.id.pb_loading);

        backButton = findViewById(R.id.iv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (bill != null) {
            priceEditText.setText(String.valueOf(bill.getPrice()));
            payerInfoEditText.setText(bill.getPayerInfo());
            recipientInfoEditText.setText(bill.getRecipientInfo());
            recipientIBANEditText.setText(bill.getRecipientIBAN());
            billModelEditText.setText(bill.getBillModel());
            referenceModelEditText.setText(bill.getReferenceNumber());
            purposeCodeEditText.setText(bill.getPurposeCode());
            billDescriptionEditText.setText(bill.getBillDescription());
        }
    }

    @Override
    public void onClick(View v) {
        if (checkUserInput()) {
            progressBar.setVisibility(View.VISIBLE);
            if (v.getId() == R.id.btn_save_bill) {
                firebaseDatabase.child(String.valueOf(monthSpinner.getSelectedItemPosition() + 1)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSaved) {
                            if (dataSnapshot.getValue() != null) {
                                monthModel = dataSnapshot.getValue(MonthModel.class);
                            } else {
                                monthModel = new MonthModel();
                            }

                            int month = monthSpinner.getSelectedItemPosition() + 1;
                            boolean billType;
                            double price = 0;
                            if (billTypeSpinner.getSelectedItemPosition() == 0) {
                                billType = true;
                                price = Double.valueOf(priceEditText.getText().toString()) * -1;
                            } else {
                                billType = false;
                                price = Double.valueOf(priceEditText.getText().toString());
                            }
                            BillModel billToSave = new BillModel(price, payerInfoEditText.getText().toString(),
                                    recipientInfoEditText.getText().toString(), recipientIBANEditText.getText().toString(),
                                    billModelEditText.getText().toString(), referenceModelEditText.getText().toString(),
                                    purposeCodeEditText.getText().toString(), billDescriptionEditText.getText().toString(),
                                    month, billType, String.valueOf(System.currentTimeMillis() / 1000));
                            monthModel.setBill(billToSave);
                            monthModel.setAmount(monthModel.getAmount() + price);
                            firebaseDatabase.child(String.valueOf(month)).setValue(monthModel);

                            dataSaved = true;

                            Toast.makeText(NewBillActivity.this, R.string.new_bill_added, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NewBillActivity.this, HomeActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(NewBillActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewBillActivity.this, HomeActivity.class));
                        finish();
                    }
                });
            }
        }
    }

    private boolean checkUserInput() {
        boolean areInputsValid = true;

        if (priceEditText.getText().toString().isEmpty()) {
            priceEditText.setError(getString(R.string.mandatory_field));
            areInputsValid = false;
        }

        if (payerInfoEditText.getText().toString().isEmpty()) {
            payerInfoEditText.setError(getString(R.string.mandatory_field));
            areInputsValid = false;
        }

        if (recipientInfoEditText.getText().toString().isEmpty()) {
            recipientInfoEditText.setError(getString(R.string.mandatory_field));
            areInputsValid = false;
        }

        if (billDescriptionEditText.getText().toString().isEmpty()) {
            billDescriptionEditText.setError(getString(R.string.mandatory_field));
            areInputsValid = false;
        }

        return areInputsValid;
    }
}
