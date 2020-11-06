package com.example.sentipayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BuyerTransactionConfirmedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_transaction_confirmed);

        Intent intent = getIntent();
        String transaction_id = intent.getStringExtra("transaction_id");

        TextView transaction_id_view = findViewById(R.id.transaction_id_view);
        transaction_id_view.setText(transaction_id);
    }
}