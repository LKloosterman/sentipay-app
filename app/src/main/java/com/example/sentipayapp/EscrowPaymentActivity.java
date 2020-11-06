package com.example.sentipayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class EscrowPaymentActivity extends AppCompatActivity {

    RequestQueue request_queue;

    String request_url = "https://glacial-thicket-16525.herokuapp.com/get-transaction-code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escrow_payment);

        Intent intent = getIntent();
        String payment_address = intent.getStringExtra("payment_address");

        TextView escrow_address = findViewById(R.id.escrow_address);
        escrow_address.setText(payment_address);

        request_queue = Volley.newRequestQueue(this);
    }

    public void paymentSent(View view) {
        Intent current_intent = getIntent();
        String transaction_code = current_intent.getStringExtra("transaction_code");

        Intent intent = new Intent(this, SellerConfirmationActivity.class);
        intent.putExtra("transaction_code", transaction_code);

        startActivity(intent);
    }

}