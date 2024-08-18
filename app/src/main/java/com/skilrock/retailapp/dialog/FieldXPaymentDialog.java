package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.DialogCloseListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.fieldx.FieldXHomeFragment;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FieldXPaymentDialog extends Dialog {
    private EditText amount;
    private Button proceed;
    private RequestQueue requestQueue;
    private String id;
    private Context context;
    private final String FIELDX = "fieldx";
    private String rawMinimumDueAmount, minimumDueAmount, orgName;
    private TextView headerMsg;
    private FieldXHomeFragment fieldXHomeFragment;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;

    public FieldXPaymentDialog(@NonNull Context context, String rawMinimumDueAmount, String minimumDueAmount, String id, String orgName, FieldXHomeFragment fieldXHomeFragment, HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean) {
        super(context);
        this.context             = context;
        this.id                  = id;
        this.rawMinimumDueAmount = rawMinimumDueAmount;
        this.minimumDueAmount    = minimumDueAmount;
        this.orgName             =orgName;
        this.fieldXHomeFragment  = fieldXHomeFragment;
        this.menuBean            = menuBean;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fieldx_payment_dialog_layout);
        headerMsg = findViewById(R.id.headerMsg);
        int indexOfDecimal = minimumDueAmount.indexOf(".");
        if(indexOfDecimal >=0) {
            String afterDecimal = minimumDueAmount.substring(indexOfDecimal + 1);
            if (afterDecimal.length() > 0 && Integer.parseInt(afterDecimal) > 0)
                headerMsg.setText(context.getString(R.string.collection_header_msg)+" "+orgName+"["+id+"] "+context.getString(R.string.minimum_due_amount)+getAmountWithCurrency(String.valueOf(Integer.parseInt(minimumDueAmount) + 1)));
        }else
            headerMsg.setText(context.getString(R.string.collection_header_msg)+" "+orgName+"["+id+"] "+context.getString(R.string.minimum_due_amount)+getAmountWithCurrency(String.valueOf(minimumDueAmount)));
        amount = findViewById(R.id.pay);
        if(rawMinimumDueAmount.replaceAll(" ", "").equalsIgnoreCase("0"))
            amount.setText("1");
        else
            amount.setText(rawMinimumDueAmount);
        proceed = findViewById(R.id.make_payment);
        requestQueue = Volley.newRequestQueue(context);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBarDialog.getProgressDialog().showProgress(context);
                if (!Utils.isNetworkConnected(context)) {
                    ProgressBarDialog.getProgressDialog().dismiss();
                    Toast.makeText(context, context.getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                doPayment();
            }
        });
    }

    private void doPayment() {
        UrlBean urlBean = Utils.getDoPayAmountDetailUrl(menuBean, "doPayment");
        String url = urlBean.getUrl();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("payment_Response", response.toString());
                ProgressBarDialog.getProgressDialog().dismiss();
                try {
                    if (response == null)
                        Utils.showErrorMessage(context, context.getString(R.string.server_error));
                    else {
                        if (response.getInt("responseCode") == 0) {
                            JSONObject respData = response.getJSONObject("responseData");
                            if (respData.getInt("statusCode") == 0) {
                                Utils.showCustomSuccessDialog(context, ((MainActivity) context).getSupportFragmentManager(), context.getString(R.string.payment), respData.getString("message"), 1, listener);
                            } else {
                                if (Utils.checkForSessionExpiry(context, respData.getInt("statusCode")))
                                    return;

                                String errorMsg = Utils.getResponseMessage(context, FIELDX, respData.getInt("statusCode"));
                                Utils.showCustomErrorDialog(context, context.getString(R.string.collect), errorMsg);
                            }
                        } else {
                            if (Utils.checkForSessionExpiry(context, response.getInt("responseCode")))
                                return;

                            String errorMsg = Utils.getResponseMessage(context, FIELDX, response.getInt("responseCode"));
                            Utils.showCustomErrorDialog(context, context.getString(R.string.collect), errorMsg);
                        }
                    }
                    dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProgressBarDialog.getProgressDialog().dismiss();
                    Utils.showCustomErrorDialog(context, context.getString(R.string.collect), context.getString(R.string.some_technical_issue));                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.showCustomErrorDialog(context, context.getString(R.string.collect), context.getString(R.string.some_technical_issue));
                ProgressBarDialog.getProgressDialog().dismiss();
                dismiss();
            }
        }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("amount", amount.getText());
                    jsonObject.put("appType", "Android_Mobile");
                    jsonObject.put("clientIp", "3.125.210.138:8083");
                    jsonObject.put("device", "MOBILE");
                    jsonObject.put("orgId", id);
                    jsonObject.put("remarks", "Payment");
                    Log.e("payment_body", jsonObject.toString());
                    return jsonObject.toString().getBytes();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProgressBarDialog.getProgressDialog().dismiss();
                    Utils.showCustomErrorDialog(context, context.getString(R.string.collect), e.getMessage().toString());
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
        requestQueue.add(jsonObjectRequest);
    }

    DialogCloseListener listener = new DialogCloseListener() {
        @Override
        public void onDialogClosed() {
            ((MainActivity)context).callBalanceApi();
            fieldXHomeFragment.getFieldxRetailerData();
        }
    };

    private String getAmountWithCurrency(String strAmount) {
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount),
                    Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)),
                    Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)),
                    Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(strAmount);
        }
        return formattedAmount;
    }
}
