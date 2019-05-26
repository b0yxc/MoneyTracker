package com.example.moneytracker;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewBillActivity extends AppCompatActivity implements View.OnClickListener {

    private Bill bill;
    private TextInputEditText priceEditText, payerInfoEditText, recipientInfoEditText, recipientIBANEditText, billModelEditText, referenceModelEditText, purposeCodeEditText, billDescriptionEditText;
    private Spinner monthSpinner, billTypeSpinner;
    private Button saveBillButton;

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
            if (v.getId() == R.id.btn_save_bill) {

            }
        }
    }

    private boolean checkUserInput() {
        boolean areInputsValid = true;

        if (priceEditText.getText().toString().isEmpty()) {
            priceEditText.setError("Obavezno polje");
            areInputsValid = false;
        }

        if (payerInfoEditText.getText().toString().isEmpty()) {
            payerInfoEditText.setError("Obavezno polje");
            areInputsValid = false;
        }

        if (recipientInfoEditText.getText().toString().isEmpty()) {
            recipientInfoEditText.setError("Obavezno polje");
            areInputsValid = false;
        }

        if (billDescriptionEditText.getText().toString().isEmpty()) {
            billDescriptionEditText.setError("Obavezno polje");
            areInputsValid = false;
        }

        return areInputsValid;
    }
}
