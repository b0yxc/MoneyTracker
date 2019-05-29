package com.example.moneytracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scanBillButton, addNewBillButton, seeBillListButton, deleteBillsButton, logoutButton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupUI();
    }

    private void setupUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getCurrentUser().getUid());

        scanBillButton = findViewById(R.id.scan_bill_button);
        scanBillButton.setOnClickListener(this);

        addNewBillButton = findViewById(R.id.add_new_bill_button);
        addNewBillButton.setOnClickListener(this);

        seeBillListButton = findViewById(R.id.see_bill_list_button);
        seeBillListButton.setOnClickListener(this);

        deleteBillsButton = findViewById(R.id.delete_db_button);
        deleteBillsButton.setOnClickListener(this);

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_bill_button:
                startActivity(new Intent(this, ScannerActivity.class));
                break;
            case R.id.add_new_bill_button:
                startActivity(new Intent(this, NewBillActivity.class));
                break;
            case R.id.see_bill_list_button:
                break;
            case R.id.delete_db_button:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.delete_message)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseDatabase.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                        Toast.makeText(HomeActivity.this, R.string.delete_completed, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.logout_button:
                firebaseAuth.signOut();
                Toast.makeText(this, R.string.sign_out, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
