package com.example.sentipayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class SellerConfirmationActivity extends AppCompatActivity {

    RequestQueue request_queue;

    String request_url = "https://glacial-thicket-16525.herokuapp.com/confirm-transaction";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_confirmation);

        Intent intent = getIntent();
        String transaction_code = intent.getStringExtra("transaction_code");

        TextView transaction_code_input = findViewById(R.id.transaction_code_view);
        transaction_code_input.setText(transaction_code);

        request_queue = Volley.newRequestQueue(this);
    }

    public void onConfirm(View view) {
        HashMap<String, String> data = new HashMap<String, String>();

        TextView transaction_code_view = findViewById(R.id.transaction_code_view);

        data.put("transaction_code", transaction_code_view.getText().toString());

        Intent intent = new Intent(this, SellerTransactionConfirmedActivity.class);

        JsonObjectRequest json_request = new JsonObjectRequest(Request.Method.POST, request_url,
                new JSONObject(data), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        json_request.setRetryPolicy(new DefaultRetryPolicy(
                6000,
                100,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}