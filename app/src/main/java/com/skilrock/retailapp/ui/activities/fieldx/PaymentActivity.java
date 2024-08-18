package com.skilrock.retailapp.ui.activities.fieldx;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    public static int position;
    private EditText amount;
    private Button proceed;
    private RequestQueue requestQueue;
    private String id;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_payment);
        amount = findViewById(R.id.pay);
        proceed = findViewById(R.id.make_payment);
        progressBar = findViewById(R.id.progress_bar);
        requestQueue = Volley.newRequestQueue(PaymentActivity.this);
        Intent bundle = getIntent();
        id = bundle.getStringExtra("id");
        amount.setText(bundle.getStringExtra("amount").replaceAll("[-$,]", ""));
        position = Integer.parseInt(bundle.getStringExtra("position"));
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                doPayment();
            }
        });
    }
    private void doPayment() {
        String url = BuildConfig.BASE_URL + "v1.0/doPayment";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getInt("responseCode") == 0) {
                        JSONObject respData = response.getJSONObject("responseData");
                        if (respData.getInt("statusCode") == 0) {
                            Log.e("position", position + "");
//                            final Handler handler=new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
                           // AllTaskFragment.getInstance().getFieldExOrganizations(getApplicationContext());
//                                }
//                            },3000);
                        } else
                            Utils.showCustomErrorDialogAndFinish(PaymentActivity.this, "server error", respData.getString("message"));
                    } else
                        Utils.showCustomErrorDialogAndFinish(PaymentActivity.this, "server error", response.getString("responseMessage"));
                    Log.e("payment_Response", response.toString());
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Utils.showCustomErrorDialogAndFinish(PaymentActivity.this, "Server Error", e.getMessage().toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showCustomErrorDialogAndFinish(PaymentActivity.this, "Server Error", error.getMessage().toString());
            }
        }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("amount", amount.getText());
                    jsonObject.put("appType", "Android_Mobile");
                    jsonObject.put("clientIp", "192.168.124.193:8083");
                    jsonObject.put("device", "MOBILE");
                    jsonObject.put("orgId", id.substring(id.indexOf(':') + 2).trim());
                    jsonObject.put("remarks", "Payment");
                    Log.e("payment_body", jsonObject.toString());
                    return jsonObject.toString().getBytes();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Utils.showCustomErrorDialogAndFinish(PaymentActivity.this, getString(R.string.collect), e.getMessage().toString());
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", PlayerData.getInstance().getToken());
                Log.e("payment_header", header.toString());
                return header;
            }
        };
//        final Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
        requestQueue.add(jsonObjectRequest);
//            }
//        },3000);


    }

}
