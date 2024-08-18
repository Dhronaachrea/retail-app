package com.skilrock.retailapp.sle_game_portrait;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FloatingButtonActivity extends AppCompatActivity implements View.OnClickListener,ResponseLis {

    private FloatingActionButton fb_close, reprint, winning_claim, last_result, cancel_ticket;
    private TextView last_result_text,reprint_text,winning_text,cancel_text;
    private JsonObject object;
    String headerData1 = "userName,E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6|password,p@55w0rd|Content-Type,application/x-www-form-urlencoded";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_action_buton);
        fb_close = findViewById(R.id.fb_close);
        reprint = findViewById(R.id.reprint);
        winning_claim = findViewById(R.id.winning_claim);
        last_result = findViewById(R.id.last_result);
        cancel_ticket = findViewById(R.id.cancel_ticket);

        last_result_text = findViewById(R.id.last_result_text);
        reprint_text = findViewById(R.id.reprint_text);
        winning_text = findViewById(R.id.winning_text);
        cancel_text = findViewById(R.id.cancel_text);

        last_result_text.setOnClickListener(this);
        reprint_text.setOnClickListener(this);
        winning_text.setOnClickListener(this);
        cancel_text.setOnClickListener(this);

        fb_close.setOnClickListener(this);
        reprint.setOnClickListener(this);
        winning_claim.setOnClickListener(this);
        last_result.setOnClickListener(this);
        cancel_ticket.setOnClickListener(this);



    }

    private void callReprint(){
        ProgressBarDialog.getProgressDialog().showProgress(this);
        object = new JsonObject();
        object.addProperty("userName",PlayerData.getInstance().getUsername()+"");
        object.addProperty("sessionId",PlayerData.getInstance().getToken().split(" ")[1]);
        object.addProperty("interfaceType","WEB");
        object.addProperty("merchantCode", "NEWRMS");
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter("http://192.168.124.52:8082/SportsLottery/com/skilrock/sle/web/merchantUser/playMgmt/action/reprintTicket.action?requestData="+ URLEncoder.encode(object.toString(),"UTF-8"),
                    this,"loading",FloatingButtonActivity.this,"reprint",headerData1);
            httpRequest.executeTask();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void callCancel(){
        ProgressBarDialog.getProgressDialog().showProgress(this);
        object = new JsonObject();
        object.addProperty("userName",PlayerData.getInstance().getUsername()+"");
        object.addProperty("sessionId",PlayerData.getInstance().getToken().split(" ")[1]);
        object.addProperty("interfaceType","WEB");
        object.addProperty("merchantCode", "NEWRMS");
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter("http://192.168.124.52:8082/SportsLottery/com/skilrock/sle/web/merchantUser/playMgmt/action/cancelTicket.action?requestData="+ URLEncoder.encode(object.toString(),"UTF-8")+"&autoCancel=false&cancelType=CANCEL_MANUAL",
                    this,"loading",FloatingButtonActivity.this,"cancel",headerData1);
            httpRequest.executeTask();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void dateWiseResult(){
        ProgressBarDialog.getProgressDialog().showProgress(this);
        object = new JsonObject();
        object.addProperty("userName",PlayerData.getInstance().getUsername()+"");
        object.addProperty("sessionId",PlayerData.getInstance().getToken().split(" ")[1]);
        object.addProperty("interfaceType","WEB");
        object.addProperty("gameTypeId", "1");
        object.addProperty("gameId", "1");
        object.addProperty("merchantCode","NEWRMS");
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter("http://192.168.124.52:8082/SportsLottery/com/skilrock/sle/web/merchantUser/drawMgmt/Action/fetchSleDrawWiseGameWiseResultData.action?requestData="+ URLEncoder.encode(object.toString(),"UTF-8"),
                    this,"loading",FloatingButtonActivity.this,"result",headerData1);
            httpRequest.setHttpRequestMethod("GET");
            httpRequest.executeTask();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fb_close){
            finish();
        }else if(v.getId() == R.id.reprint || v.getId() == R.id.reprint_text){
            callReprint();
//            finish();
        }else if(v.getId() == R.id.winning_claim || v.getId() == R.id.winning_text){
            finish();
        }else if(v.getId() == R.id.last_result || v.getId() == R.id.last_result_text){
            dateWiseResult();
//            finish();
        }else if(v.getId() == R.id.cancel_ticket || v.getId() == R.id.cancel_text){
            callCancel();
        }
    }

    @Override
    public void onResponse(String response, String requestedMethod) {
        ProgressBarDialog.getProgressDialog().dismiss();
        Intent intent;
        if(requestedMethod.equalsIgnoreCase("result")){
            intent = new Intent(this,PrintActivityResult.class);
            intent.putExtra("print","printResult");
            intent.putExtra("response",response);
            startActivity(intent);
        }else if(requestedMethod.equalsIgnoreCase("reprint")){
            intent = new Intent(this,PrintActivityResult.class);
            intent.putExtra("print","printReprint");
            intent.putExtra("response",response);
            startActivity(intent);
        }else if(requestedMethod.equalsIgnoreCase("cancel")){
            intent = new Intent(this,PrintActivityResult.class);
            intent.putExtra("print","printCancel");
            intent.putExtra("response",response);
            startActivity(intent);
        }

    }
}
