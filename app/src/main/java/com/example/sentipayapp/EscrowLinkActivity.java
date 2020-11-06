package com.example.sentipayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class EscrowLinkActivity extends AppCompatActivity {

    RequestQueue request_queue;

    String request_url = "https://glacial-thicket-16525.herokuapp.com/link-recipient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escrow_link);

        request_queue = Volley.newRequestQueue(this);
    }

    public void submitData(View view) {
        HashMap<String, String> data = new HashMap<String, String>();

        EditText transaction_code_input = findViewById(R.id.transaction_code_input);
        EditText address_input = findViewById(R.id.address_input);

        data.put("transaction_code", transaction_code_input.getText().toString());
        data.put("recipient_address", address_input.getText().toString());

        Intent intent = new Intent(this, BuyerConfirmationActivity.class);
        intent.putExtra("transaction_code", transaction_code_input.getText().toString());

        JsonObjectRequest json_request = new JsonObjectRequest(Request.Method.POST, request_url,
                new JSONObject(data), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String escrow_balance = response.get("escrow_balance").toString();

                        intent.putExtra("escrow_balance", escrow_balance);

                        startActivity(intent);
                    } catch (JSONException e) {
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
                6000,
                100,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request_queue.add(json_request);
    }

}