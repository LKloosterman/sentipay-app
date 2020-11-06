package com.example.sentipayapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue request_queue;

    String request_url = "https://glacial-thicket-16525.herokuapp.com/get-address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request_queue = Volley.newRequestQueue(this);
    }

    public void chooseBuyer(View view) {
        Intent intent = new Intent(this, EscrowLinkActivity.class);

        startActivity(intent);
    }

    public void chooseSeller(View view) {
        Intent intent = new Intent(this, EscrowPaymentActivity.class);

        JsonObjectRequest json_request = new JsonObjectRequest(Request.Method.GET, request_url,
                null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String payment_address = response.get("payment_address").toString();
                            String transaction_code = response.get("transaction_code").toString();

                            intent.putExtra("payment_address", payment_address);
                            intent.putExtra("transaction_code", transaction_code);

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
                10000,
                100,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request_queue.add(json_request);
    }

}