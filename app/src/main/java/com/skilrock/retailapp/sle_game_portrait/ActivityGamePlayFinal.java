package com.skilrock.retailapp.sle_game_portrait;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

import androidx.annotation.Nullable;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

public class ActivityGamePlayFinal extends BaseActivity implements View.OnClickListener,ResponseLis {
    private RecyclerView rv_numbers;
    SleGamePlayAdapterNewFinal sleGamePlayAdapter;
    public static BigDecimal thresHoldAmount;
    public static BigDecimal tktMaxAmount;
    private int currentBetSelected = 1;
    private ImageView image_delete;
    int currentDraw;
    private LinearLayout reset_data,buy_final,back_click;
    private Intent intent;
    private List<TextView> textViews;
    private TextView userName, balance;
    private TextView pool_price,bet_amount_one,bet_amount_two,bet_amount_three,bet_amount_four,bet_amount_five,bet_amount_other,no_of_lines,
            value,bet_value;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            setContentView(R.layout.layout_game_play_final_v2pro);
        else
        setContentView(R.layout.layout_game_play_final);
        setToolBar();
        rv_numbers = findViewById(R.id.sports_recycle_view);

        BaseClassSle.getBaseClassSle().setActivityGamePlayFinal(this);

        no_of_lines = findViewById(R.id.no_of_lines);

        bet_value = findViewById(R.id.value);


        //back_click = findViewById(R.id.back_click);

        buy_final = findViewById(R.id.buy_ticket);
        CircularTextView ctv2       = findViewById(R.id.circle_2);
        ctv2.setStrokeColor("#f5f5f5");
        image_delete = findViewById(R.id.image_delete);

        image_delete.setOnClickListener(this);

        buy_final.setOnClickListener(this);

        pool_price = findViewById(R.id.date_format);

        //userName = findViewById(R.id.userName);
        //balance = findViewById(R.id.balance);

        //userName.setText(PlayerData.getInstance().getUsername());
        //balance.setText(PlayerData.getInstance().getUserBalance());


        no_of_lines.setText(""+getIntent().getExtras().get("noOfLines"));

        currentBetSelected = getIntent().getIntExtra("currentBet",1);


        currentDraw = getIntent().getExtras().getInt("currentDraw");
        pool_price.setText(getIntent().getStringExtra("display"));
        DividerItemDecoration decorator = new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot_line));
        rv_numbers.addItemDecoration(decorator);

        rv_numbers.setLayoutManager(new GridLayoutManager(this,1));
        if(getIntent().getExtras().getBoolean("isB2C")){
            //pool_price.setText(( ActivityDraws.sleFetchDataB2C.getSleData().getCurrencyConversionRates()!= null ?ActivityDraws.sleFetchDataB2C.getSleData().getCurrencyConversionRates().get(0).getCurrencyCode():"$")  + ActivityDraws.drawDataBeansB2C.get(currentDraw).getPrizePoolAmount()+"");
            sleGamePlayAdapter = new SleGamePlayAdapterNewFinal(this,ActivityDraws.drawDataBeansB2C.get(currentDraw).getEventData(),getIntent().getExtras().getInt("totalNoOfMatch"),true);
            rv_numbers.setAdapter(sleGamePlayAdapter);
        }else{

        }



        bet_value.setText(""+getFormattedAmount(Integer.parseInt(getIntent().getExtras().get("noOfLines").toString())*(BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice()*currentBetSelected))+"");

//        final_amount.setText(( ActivityDraws.sleFetchDataB2C.getSleData().getCurrencyConversionRates()!= null ?ActivityDraws.sleFetchDataB2C.getSleData().getCurrencyConversionRates().get(0).getCurrencyCode():"$") +(Integer.parseInt(getIntent().getExtras().get("noOfLines")+"")*BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice()*currentBetSelected)+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBalance();
    }

    private void setToolBar() {
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);

        tvTitle.setText(getString(R.string.purchase_details));
        ivGameIcon.setVisibility(View.GONE);
    }

    private void buyTicket(){
        ProgressBarDialog.getProgressDialog().showProgress(this);
        String headerData1 = "userName,"+BaseClassSle.getBaseClassSle().getSalBean().getUserName()+"|password,"+BaseClassSle.getBaseClassSle().getSalBean().getPassword()+"|Content-Type,"+BaseClassSle.getBaseClassSle().getSalBean().getContentType();
        JsonObject finalPurch = new JsonObject();
        finalPurch.addProperty("gameId",BaseClassSle.getBaseClassSle().getSaleBean().getGameId()+"");
        finalPurch.addProperty("gameTypeId",BaseClassSle.getBaseClassSle().getSaleBean().getGameTypeId());
        finalPurch.addProperty("noOfBoard",BaseClassSle.getBaseClassSle().getSaleBean().getNoOfBoard()+"");
        finalPurch.addProperty("userName", PlayerData.getInstance().getUsername());
        finalPurch.addProperty("merchantCode",BaseClassSle.getBaseClassSle().getSaleBean().getMerchantCode());
        finalPurch.addProperty("totalPurchaseAmt",(Integer.parseInt(getIntent().getExtras().get("noOfLines")+"")*BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice()*currentBetSelected)+"");
        finalPurch.addProperty("sessionId",BaseClassSle.getBaseClassSle().getSaleBean().getSessionId());
        finalPurch.addProperty("modelCode",Utils.getModelCode());
        finalPurch.addProperty("terminalId",Utils.getDeviceSerialNumber());
        finalPurch.addProperty("interfaceType", AppConstants.INTERFACE_TYPE);
        JsonArray drawInfo = new JsonArray();


        for(SaleBean.DrawInfoBean drawInfoBean :BaseClassSle.getBaseClassSle().getDrawInfoBeans()){
            JsonObject draw = new JsonObject();
            draw.addProperty("drawId",drawInfoBean.getDrawId()+"");
            draw.addProperty("betAmtMul",(currentBetSelected)+"");
            draw.addProperty("drawStatus",drawInfoBean.getDrawStatus());
            draw.addProperty("drawFreezeTime",drawInfoBean.getDrawFreezeTime());
            draw.addProperty("drawDateTime",drawInfoBean.getDrawDateTime());
            draw.addProperty("saleStartTime",drawInfoBean.getSaleStartTime());

            JsonArray eventData = new JsonArray();
            JsonObject event;


            for(SaleBean.DrawInfoBean.EventDataBean eventDataBean:drawInfoBean.getEventData()){
                String numberSelectedValue = "";
                event = new JsonObject();
                if(eventDataBean.isHomePlusSelected()){
                    numberSelectedValue = numberSelectedValue + "H%2B,";
                }
                if(eventDataBean.isHomeSelected()){
                    numberSelectedValue = numberSelectedValue + "H,";
                }
                if(eventDataBean.isDrawSelected()){
                    numberSelectedValue = numberSelectedValue + "D,";
                }
                if(eventDataBean.isAwaySelected()){
                    numberSelectedValue = numberSelectedValue + "A,";
                }
                if(eventDataBean.isAwayPlusSelected()){
                    numberSelectedValue = numberSelectedValue + "A%2B,";
                }
                numberSelectedValue = numberSelectedValue.substring(0,numberSelectedValue.lastIndexOf(","));
                event.addProperty("eventId",eventDataBean.getEventId()+"");
                event.addProperty("eventSelected",numberSelectedValue);
                eventData.add(event);
            }

//        eventData.add(event);

            draw.add("eventData",eventData);

            drawInfo.add(draw);
        }

        finalPurch.add("drawInfo",drawInfo);
        Log.i("purhcaseJson",finalPurch.toString());

        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL()+BaseClassSle.getBaseClassSle().getSalBean().getUrl()+"?requestData="+ URLEncoder.encode(finalPurch.toString(),"UTF-8"),
                    this,"loading",ActivityGamePlayFinal.this,"purchase",headerData1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        httpRequest.setIsParams(true,finalPurch.toString());
        httpRequest.executeTask();


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buy_ticket){
            buyTicket();
        }/*else if(v.getId() == R.id.back_click){
            onBackPressed();
        }*/else if(v.getId() == R.id.image_delete){
            intent = new Intent();
            intent.putExtra("reset",true);
            setResult(0012,intent);
            ActivityGamePlayFinal.this.finish();
        }
    }

    @Override
    public void onResponse(String response, String requestedMethod) {
        ProgressBarDialog.getProgressDialog().dismiss();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            if (Utils.checkForSessionExpiryActivity(ActivityGamePlayFinal.this, jsonObject.optInt("responseCode"), ActivityGamePlayFinal.this)){
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(response == null || (response != null && response.equalsIgnoreCase("Failed"))){
            Utils.showCustomErrorDialog(this,getString(R.string.sports),getString(R.string.some_technical_issue));
            return;
        }


        try {
            jsonObject = new JSONObject(response);
            if(jsonObject != null && jsonObject.has("responseCode") && jsonObject.optInt("responseCode") != 0){
                //Utils.showCustomErrorDialog(this,getString(R.string.sports),jsonObject.optString("responseMsg"));
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayFinal.this, "sle",  jsonObject.optInt("responseCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);

                if(jsonObject.optString("responseMsg").toLowerCase().contains("login") && jsonObject.optString("responseMsg").toLowerCase().contains("again")){

                }
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(response);
            if(jsonObject.has("errorCode")){
                //Utils.showCustomErrorDialog(this,getString(R.string.sports),jsonObject.optString("errorMsg"));
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayFinal.this, "sle",  jsonObject.optInt("errorCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(requestedMethod.equalsIgnoreCase("purchase")){
            if (jsonObject.optInt("responseCode")==0) {
                if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printPurchase");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0012);
                } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printPurchase");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0012);

                }


            }else {
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayFinal.this, "sle",jsonObject.optInt("responseCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                //Utils.showCustomErrorDialog(this,getString(R.string.sports),jsonObject.optString("responseMsg"));
            }
        }


    }

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        ActivityGamePlayFinal.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isPrintSuccess")){
            Intent intent = new Intent();
            intent.putExtra("reset",true);
            intent.putExtra("isBalanceUpdate",true);
            setResult(0012,intent);
            ActivityGamePlayFinal.this.finish();
        }else {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(this, getString(R.string.sale), errorMsg);
        }
    }
    private String getFormattedAmount(int amt) {
        String amount;
        try {
            amount = Utils.getBalanceToView(amt, Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE()));
        } catch (Exception e) {
            e.printStackTrace();
            amount = String.valueOf(amt);
        }
        return amount;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

}
