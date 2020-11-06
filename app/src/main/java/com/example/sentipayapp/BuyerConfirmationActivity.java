package com.example.sentipayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BuyerConfirmationActivity extends AppCompatActivity {

    RequestQueue request_queue;

    String request_url = "https://glacial-thicket-16525.herokuapp.com/confirm-transaction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_confirmation);

        Intent intent = getIntent();
        String transaction_code = intent.getStringExtra("transaction_code");
        String escrow_balance = intent.getStringExtra("escrow_balance");

        TextView transaction_code_input = findViewById(R.id.transaction_code_view2);
        transaction_code_input.setText(transaction_code);

        TextView escrow_balance_view = findViewById(R.id.escrow_balance_view);
        escrow_balance_view.setText(escrow_balance);

        request_queue = Volley.newRequestQueue(this);
    }

    public void onConfirm(View view) {
        HashMap<String, String> data = new HashMap<String, String>();

        TextView transaction_code_view2 = findViewById(R.id.transaction_code_view2);
        data.put("transaction_code", transaction_code_view2.getText().toString());

        Intent intent = new Intent(this, BuyerTransactionConfirmedActivity.class);

        JsonObjectRequest json_request = new JsonObjectRequest(Request.Method.POST, request_url,
                new JSONObject(data), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String transaction_id = response.get("transaction_id").toString();

                    intent.putExtra("transaction_id", transaction_id);

                    startActivity(intent);
                } catch (
                JSONException e) {
                    String payment_address = "ERROR";

                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        json_request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                100,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request_queue.add(json_request);
    }

}