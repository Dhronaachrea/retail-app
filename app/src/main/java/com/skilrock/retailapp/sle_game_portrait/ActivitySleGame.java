package com.skilrock.retailapp.sle_game_portrait;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.interfaces.WinningClaimListener;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.portrait_draw_games.ui.WinningClaimActivity;
import com.skilrock.retailapp.sle_game_portrait.sle_land_scape.ActivityGamePlayLand;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CustomSuccessDialog;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

public class ActivitySleGame extends BaseActivity implements ResponseLis, View.OnClickListener, WinningClaimListener {
    private SleViewModel viewModel;
    FragmentManager fragmentManager;
    private boolean isPortrait = false;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private SportsGamesAdapter homeModuleAdapter;
    List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeans;
    List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeansB2C;
    private SleFetchDataB2C sleFetchDataB2C;
    private RecyclerView rvGames;
    int totalNumberOfMatch = 3;
    VerifyPayTicket verifyPayTicket = null;
    private Button btnViewMyTicket;
    private LinearLayout layout_winning_claim, layout_result, layout_cancel, layout_reprint,bottom_layout;
    private JsonObject object;
    private String response;
    private LinearLayout llBack;
    String headerData1 = "userName,E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6|password,p@55w0rd|Content-Type,application/x-www-form-urlencoded";
    private String currentGame;
    private final String RMS = "RMS";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            setContentView(R.layout.activity_sle_home_landscape);
        else
            setContentView(R.layout.activity_sle_home);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        initializeWidgets();
        format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        isPortrait = getIntent().getExtras().getBoolean("isPortrait");
        btnViewMyTicket = findViewById(R.id.btnViewMyTicket);
        btnViewMyTicket.setVisibility(View.INVISIBLE);
        llBalance = findViewById(R.id.llBalance);
        bottom_layout=findViewById(R.id.bottom_layout);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            bottom_layout.setVisibility(View.GONE);
        }else {
            bottom_layout.setVisibility(View.VISIBLE);
        }

        llBack = findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        layout_cancel = findViewById(R.id.layout_cancel);
        layout_winning_claim = findViewById(R.id.layout_winning_claim);
        layout_result = findViewById(R.id.layout_result);
        layout_reprint = findViewById(R.id.layout_reprint);

        layout_cancel.setOnClickListener(this);
        layout_reprint.setOnClickListener(this);
        layout_result.setOnClickListener(this);
        layout_winning_claim.setOnClickListener(this);

        response = getIntent().getExtras().getString("bean");
        parseData(response);


    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBalance();
    }

    private void parseData(String response) {
        sleFetchDataB2C = new Gson().fromJson(response, SleFetchDataB2C.class);
        if (sleFetchDataB2C != null && sleFetchDataB2C.getSleData() != null && sleFetchDataB2C.getSleData().getGameData() != null) {
            BaseClassSle.getBaseClassSle().setGameId(sleFetchDataB2C.getSleData().getGameData().get(0).getGameId());
            loadGames();
            callTimer();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerCountDown != null) {
            timerCountDown.cancel();
        }

    }

    long timer = -1;

    private void callTimer() {
        try {

            for (SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean drawDataBean : sleFetchDataB2C.getSleData().getGameData().get(0).getGameTypeData().get(0).getDrawData()) {
               long value = checkTimeDifference(sleFetchDataB2C.getSleData().getCurrentTime(),
                        drawDataBean.getFtg());
               /* long value=8000;*/
                if (timer == -1) {
                    timer = value;
                } else if (value < timer) {
                    timer = value;
                }
            }


            //long timer = Long.parseLong("40000");
//            setCountDownTimer(checkTimeDifference(currentTimerServer,
//                    TpsGamesClass.getInstance().getCurrentGameNewDge(0).getDrawRespVOs().get(0).getDrawFreezeTime()),
//                    TpsGamesClass.getInstance().getCurrentGameNewDge(0).getGameName());

//            setCountDownTimer(checkTimeDifference(currentTimerServer,
//                    TpsGamesClass.getInstance().getCurrentGameNewDge(1).getDrawRespVOs().get(0).getDrawFreezeTime()),
//                    TpsGamesClass.getInstance().getCurrentGameNewDge(1).getGameName());

            setCountDownTimer(timer);
        } catch (IndexOutOfBoundsException e) {
            Log.v("gameNotFound", e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (timerCountDown != null) {
            timerCountDown.cancel();
            timerCountDown = null;
        }
    }

    private boolean setTimer;
    private CountDownTimer timerCountDown;
    Handler handler1;

    private void setCountDownTimer(long total) {

        if (setTimer || total == -1) {
            Log.v("timer", "timer is negative");

            return;
        }
        setTimer = true;
        timerCountDown = new CountDownTimer(total, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.v("secondsMini: ", "" + (millisUntilFinished / 1000));

                int progress = (int) (millisUntilFinished / 1000);
                int mins = progress / 60;
                int hours = mins / 60;
                int days = hours / 24;

                String textMins = (mins % 60) + " min ";
                String textSeconds = (progress % 60) + " sec";
                String textHours = hours > 1 ?  ((hours % 24) + " Hrs ") : ((hours % 24) + " Hr ");
                String textDays = days > 1 ? (days + " Days ") : (days + " Day ");

                handler1 = new Handler();
                handler1.post(() -> {
                    if (days > 0)
                        homeModuleAdapter.setTimer(textDays);
                    else if (hours > 0)
                        homeModuleAdapter.setTimer(mins > 0 ? (textHours + textMins) : (textHours));
                    else if (mins > 0)
                        homeModuleAdapter.setTimer((textMins + textSeconds));
                    else
                        homeModuleAdapter.setTimer(textSeconds);
                });
            }

            public void onFinish() {

                /*handler1 = new Handler();
                handler1.postDelayed(() -> {
                    if (timerCountDown != null) {
                        gameList();
                    }
                }, 1000 * 30);*/
              gameList();
            }
        };
        timerCountDown.start();

    }

    SimpleDateFormat format = null;

    private long checkTimeDifference(String dateStart, String dateStop) {
        Log.v("currentDAte", dateStart);
        Log.v("stoptimeDate", dateStop);
        Date d1 = null;
        Date d2 = null;
        long diffMinutes = 0;
        long diffSeconds = 0;
        long diffHours = 0;
        long diff=0;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
             diff = d2.getTime() - d1.getTime();

          /*  diffSeconds = diff / 1000 % 60;
            diffMinutes = diff / (60 * 1000) % 60;
            diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("minutes", "min" + diffMinutes + "Sec" + diffSeconds);
        Log.d("Diff", "TimeDiff:" + ((diffMinutes * 60) + diffSeconds));

        //return( (diffMinutes * 60) + diffSeconds) * 1000;
        return diff;
    }

    private void setListEvents(String gameName) {
        int total = (gameName.equalsIgnoreCase("soccer13") ? 13 : (gameName.equalsIgnoreCase("soccer6") ? 6 : 4));
        BaseClassSle.getBaseClassSle().getEventDataBeans().clear();
        BaseClassSle.getBaseClassSle().setTotalCount(total);
        for (int i = 0; i < total; i++) {
            BaseClassSle.getBaseClassSle().getEventDataBeans().add(new SaleBean.DrawInfoBean.EventDataBean());
        }
    }

    private void setDrawData(String gameName) {
        for (SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean gameTypeDataBean : sleFetchDataB2C.getSleData().getGameData().get(0).getGameTypeData()) {
            if (gameTypeDataBean.getGameTypeDevName().equalsIgnoreCase(gameName)) {
                drawDataBeansB2C = gameTypeDataBean.getDrawData();
                BaseClassSle.getBaseClassSle().setSaleBean(new SaleBean());
                BaseClassSle.getBaseClassSle().getSaleBean().setGameId(BaseClassSle.getBaseClassSle().getGameId());
                BaseClassSle.getBaseClassSle().getSaleBean().setGameName(gameTypeDataBean.getGameTypeDevName());
                BaseClassSle.getBaseClassSle().getSaleBean().setGameTypeId(gameTypeDataBean.getGameTypeId());
                BaseClassSle.getBaseClassSle().getSaleBean().setUnitePrice(gameTypeDataBean.getGameTypeUnitPrice());
                BaseClassSle.getBaseClassSle().getSaleBean().setNoOfBoard(1);
                BaseClassSle.getBaseClassSle().getSaleBean().setUserId(PlayerData.getInstance().getUserId());
                BaseClassSle.getBaseClassSle().getSaleBean().setPlayerName(PlayerData.getInstance().getUsername());
                BaseClassSle.getBaseClassSle().getSaleBean().setMerchantCode("NEWRMS");
                BaseClassSle.getBaseClassSle().getSaleBean().setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
                String match = gameTypeDataBean.getEventType().replace("[", "");
                match = match.replace("]", "");
                totalNumberOfMatch = match.split(",").length;
                ActivityDraws.gameTypeDataBean = gameTypeDataBean;
                setListEvents(gameTypeDataBean.getGameTypeDevName());
                break;
            }
        }

        ActivityDraws.sleFetchDataB2C = sleFetchDataB2C;
//        fragmentManager = getSupportFragmentManager();
//        Fragment gameFragment;
//
//        if(isPortrait){
//            gameFragment = new GameSleFragment(this,2, drawDataBeans,totalNumberOfMatch);
//        }else{
//            gameFragment = new GameSleFragment(this,2, drawDataBeans,totalNumberOfMatch);
//        }
//        ;
//        fragmentManager.beginTransaction().replace(R.id.main_view,gameFragment).commit();
//        Log.i("print value",getIntent().getExtras().getString("bean"));

        ActivityDraws.drawDataBeans = drawDataBeans;
        ActivityDraws.drawDataBeansB2C = drawDataBeansB2C;
        BaseClassSle.getBaseClassSle().getDrawInfoBeans().clear();
        if (drawDataBeansB2C == null || (drawDataBeansB2C != null && drawDataBeansB2C.size() == 0)) {
            if (BaseClassSle.getBaseClassSle().getActivityDraws() != null) {
                BaseClassSle.getBaseClassSle().getActivityDraws().finish();
            }
        }
    }


    private void startGame(String gameName) {
        this.currentGame = gameName;
        setDrawData(gameName);
        Intent intent;
        if (!Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
        if (drawDataBeansB2C.size() > 1 && !BaseClassSle.getBaseClassSle().isIs_b2c_sports()) {
            intent = new Intent(this, ActivityDraws.class);
            intent.putExtra("totalNoOfMatch", totalNumberOfMatch);
            startActivity(intent);
        }else if (drawDataBeansB2C.size() > 0 && !BaseClassSle.getBaseClassSle().isIs_b2c_sports()){
            intent = new Intent(this, ActivityGamePlay.class);
            intent.putExtra("totalNoOfMatch", totalNumberOfMatch);
            intent.putExtra("isB2C", true);
            intent.putExtra("currentDraw", 0);
            intent.putExtra("drawDate", SleDrawAdapter.getDateMonth(drawDataBeansB2C.get(0).getDrawDateTime().split(" ")[0], " "));
            SaleBean.DrawInfoBean drawInfoBean = new SaleBean.DrawInfoBean();
            drawInfoBean.setDrawId(drawDataBeansB2C.get(0).getDrawId());
            drawInfoBean.setBetAmtMul(1);
            drawInfoBean.setDrawStatus(drawDataBeansB2C.get(0).getDrawStatus());
            drawInfoBean.setDrawFreezeTime(drawDataBeansB2C.get(0).getFtg());
            drawInfoBean.setDrawDateTime(drawDataBeansB2C.get(0).getDrawDateTime());
            drawInfoBean.setSaleStartTime(drawDataBeansB2C.get(0).getSaleStartTime());
            BaseClassSle.getBaseClassSle().getDrawInfoBeans().add(drawInfoBean);
            startActivity(intent);
        }else{
            Toast.makeText(this, getString(R.string.no_draw_available), Toast.LENGTH_LONG).show();
            }
        } else if (ActivityDraws.drawDataBeansB2C != null && ActivityDraws.drawDataBeansB2C.size() > 0 && !BaseClassSle.getBaseClassSle().isIs_b2c_sports()) {
           if (!Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
               intent = new Intent(this, ActivityGamePlay.class);
               intent.putExtra("totalNoOfMatch", totalNumberOfMatch);
               intent.putExtra("isB2C", true);
               intent.putExtra("currentDraw", 0);
               intent.putExtra("drawDate", SleDrawAdapter.getDateMonth(drawDataBeansB2C.get(0).getDrawDateTime().split(" ")[0], " "));
               SaleBean.DrawInfoBean drawInfoBean = new SaleBean.DrawInfoBean();
               drawInfoBean.setDrawId(drawDataBeansB2C.get(0).getDrawId());
               drawInfoBean.setBetAmtMul(1);
               drawInfoBean.setDrawStatus(drawDataBeansB2C.get(0).getDrawStatus());
               drawInfoBean.setDrawFreezeTime(drawDataBeansB2C.get(0).getFtg());
               drawInfoBean.setDrawDateTime(drawDataBeansB2C.get(0).getDrawDateTime());
               drawInfoBean.setSaleStartTime(drawDataBeansB2C.get(0).getSaleStartTime());
               BaseClassSle.getBaseClassSle().getDrawInfoBeans().add(drawInfoBean);
               startActivity(intent);
           }else{
               intent = new Intent(this, ActivityGamePlayLand.class);
               intent.putExtra("totalNoOfMatch", totalNumberOfMatch);
               intent.putExtra("isB2C", true);
               intent.putExtra("currentDraw", 0);
               intent.putExtra("drawDate", SleDrawAdapter.getDateMonth(drawDataBeansB2C.get(0).getDrawDateTime().split(" ")[0], " "));
               SaleBean.DrawInfoBean drawInfoBean = new SaleBean.DrawInfoBean();
               drawInfoBean.setDrawId(drawDataBeansB2C.get(0).getDrawId());
               drawInfoBean.setBetAmtMul(1);
               drawInfoBean.setDrawStatus(drawDataBeansB2C.get(0).getDrawStatus());
               drawInfoBean.setDrawFreezeTime(drawDataBeansB2C.get(0).getFtg());
               drawInfoBean.setDrawDateTime(drawDataBeansB2C.get(0).getDrawDateTime());
               drawInfoBean.setSaleStartTime(drawDataBeansB2C.get(0).getSaleStartTime());
               BaseClassSle.getBaseClassSle().getDrawInfoBeans().add(drawInfoBean);
               startActivity(intent);
           }
        } else if (BaseClassSle.getBaseClassSle().isIs_b2c_sports()) {
            intent = new Intent(this, ActivityGamePlay.class);
            intent.putExtra("totalNoOfMatch", totalNumberOfMatch);
            intent.putExtra("isB2C", true);
            intent.putExtra("currentDraw", 0);
            intent.putExtra("drawDate", SleDrawAdapter.getDateMonth(drawDataBeansB2C.get(0).getDrawDateTime().split(" ")[0], " "));
            SaleBean.DrawInfoBean drawInfoBean = new SaleBean.DrawInfoBean();
            drawInfoBean.setDrawId(drawDataBeansB2C.get(0).getDrawId());
            drawInfoBean.setBetAmtMul(1);
            drawInfoBean.setDrawStatus(drawDataBeansB2C.get(0).getDrawStatus());
            drawInfoBean.setDrawFreezeTime(drawDataBeansB2C.get(0).getFtg());
            drawInfoBean.setDrawDateTime(drawDataBeansB2C.get(0).getDrawDateTime());
            drawInfoBean.setSaleStartTime(drawDataBeansB2C.get(0).getSaleStartTime());
            BaseClassSle.getBaseClassSle().getDrawInfoBeans().add(drawInfoBean);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.no_draw_available), Toast.LENGTH_LONG).show();
        }
    }

    private void gameList() {
        String headerData1 = "userName," + BaseClassSle.getBaseClassSle().getFetchBean().getUserName() + "|password," + BaseClassSle.getBaseClassSle().getFetchBean().getPassword() + "|Content-Type," + BaseClassSle.getBaseClassSle().getFetchBean().getContentType();
        ProgressBarDialog.getProgressDialog().showProgress(this);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("merchantCode", "Weaver");
            jsonObject.put("listType", "dayWise");
            jsonObject.put("fromDate", "2015-07-09");
            jsonObject.put("toDate", "2019-12-31");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject();
            jsonObject.put("userName", PlayerData.getInstance().getUsername());
            jsonObject.put("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
            jsonObject.put("interfaceType", "WEB");
            jsonObject.put("merchantCode", "NEWRMS");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setHttpRequestMethod("GET");
        if (!BaseClassSle.getBaseClassSle().isIs_b2c_sports()) {
            try {
                httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getFetchBean().getUrl() + "?requestData=" + URLEncoder.encode(jsonObject.toString(), "UTF-8"), this, "loading", this, "sele", headerData1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getFetchBean().getUrl(), this, "loading", this, "sele", headerData1);
            httpRequest.setIsParams(true, jsonObject.toString());
        }
        httpRequest.executeTask();
    }

    private void loadGames() {
        SportsGamesAdapter.SportsListSelectionLis sportsListSelectionLis = new SportsGamesAdapter.SportsListSelectionLis() {
            @Override
            public void gameName(String gameName) {
                startGame(gameName);
            }
        };
        rvGames.setHasFixedSize(true);
        if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            rvGames.setLayoutManager(new GridLayoutManager(this, 4));
        else
            rvGames.setLayoutManager(new GridLayoutManager(this, 2));

        rvGames.setVisibility(View.VISIBLE);
        homeModuleAdapter = new SportsGamesAdapter(this, sleFetchDataB2C.getSleData().getGameData().get(0).getGameTypeData(), sportsListSelectionLis);
        rvGames.setAdapter(homeModuleAdapter);
        homeModuleAdapter.notifyDataSetChanged();
    }

    private void initializeWidgets() {
        /*TextView tvBalance      = findViewById(R.id.tvBal);
        TextView tvUserName     = findViewById(R.id.tvUserName);*/
        tvUserBalance = findViewById(R.id.tvBal);
        tvUsername = findViewById(R.id.tvUserName);
        Button btnViewMyTicket = findViewById(R.id.btnViewMyTicket);
        rvGames = findViewById(R.id.rvGames);
        /*tvBalance.setText(PlayerData.getInstance().getOrgBalance());
        tvUserName.setText(PlayerData.getInstance().getUsername());*/
        viewModel = ViewModelProviders.of(this).get(SleViewModel.class);
        viewModel.getLiveDataBalance().observe(this, observerBalance);
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TextView tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText(bundle.getString("title"));
            ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> menuBeanList = bundle.getParcelableArrayList("DrawGamesModule");
            if (menuBeanList != null) {
                for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList model : menuBeanList) {
                    if (model.getMenuCode().equalsIgnoreCase(AppConstants.DGE_GAME_LIST)) {
                        MENU_BEAN = model;
                        break;
                    }
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            ActivitySleGame.this.finish();
        }
    }

    private void callReprint() {
        ProgressBarDialog.getProgressDialog().showProgress(this);
        headerData1 = "userName," + BaseClassSle.getBaseClassSle().getReprintBean().getUserName() + "|password," + BaseClassSle.getBaseClassSle().getReprintBean().getPassword() + "|Content-Type," + BaseClassSle.getBaseClassSle().getReprintBean().getContentType();
        object = new JsonObject();
        object.addProperty("userName", PlayerData.getInstance().getUsername() + "");
        object.addProperty("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
        object.addProperty("interfaceType", AppConstants.INTERFACE_TYPE);
        object.addProperty("merchantCode", "NEWRMS");
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getReprintBean().getUrl() + "?requestData=" + URLEncoder.encode(object.toString(), "UTF-8"),
                    this, "loading", ActivitySleGame.this, "reprint", headerData1);
            httpRequest.executeTask();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void callCancel() {
        ConfirmationListener confirmationListener = () -> {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            headerData1 = "userName," + BaseClassSle.getBaseClassSle().getCancelBean().getUserName() + "|password," + BaseClassSle.getBaseClassSle().getCancelBean().getPassword() + "|Content-Type," + BaseClassSle.getBaseClassSle().getCancelBean().getContentType();
            object = new JsonObject();
            object.addProperty("userName", PlayerData.getInstance().getUsername() + "");
            object.addProperty("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
            object.addProperty("interfaceType", AppConstants.INTERFACE_TYPE);
            object.addProperty("merchantCode", "NEWRMS");
            HttpRequest httpRequest = new HttpRequest();
            try {
                httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getCancelBean().getUrl() + "?requestData=" + URLEncoder.encode(object.toString(), "UTF-8") + "&autoCancel=false&cancelType=CANCEL_MANUAL",
                        this, "loading", ActivitySleGame.this, "cancel", headerData1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpRequest.executeTask();
        };

        Utils.showCancelTicketConfirmationDialog(ActivitySleGame.this, confirmationListener);
    }

    private void dateWiseResult() {
        ProgressBarDialog.getProgressDialog().showProgress(this);
        headerData1 = "userName," + BaseClassSle.getBaseClassSle().getResultBean().getUserName() + "|password," + BaseClassSle.getBaseClassSle().getResultBean().getPassword() + "|Content-Type," + BaseClassSle.getBaseClassSle().getResultBean().getContentType();
        object = new JsonObject();
        object.addProperty("userName", PlayerData.getInstance().getUsername() + "");
        object.addProperty("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
        object.addProperty("interfaceType", AppConstants.INTERFACE_TYPE);
        object.addProperty("gameTypeId", "1");
        object.addProperty("gameId", "1");
        object.addProperty("merchantCode", "NEWRMS");
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getResultBean().getUrl() + "?requestData=" + URLEncoder.encode(object.toString(), "UTF-8"),
                    this, "loading", ActivitySleGame.this, "result", headerData1);
            httpRequest.setHttpRequestMethod("GET");
            httpRequest.executeTask();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void payVerifyTicket(VerifyPayTicket verifyPayTicket) {
        ProgressBarDialog.getProgressDialog().showProgress(this);
        headerData1 = "userName," + BaseClassSle.getBaseClassSle().getClaimBean().getUserName() + "|password," + BaseClassSle.getBaseClassSle().getClaimBean().getPassword() + "|Content-Type," + BaseClassSle.getBaseClassSle().getClaimBean().getContentType();
        object = new JsonObject();
        object.addProperty("userName", PlayerData.getInstance().getUsername() + "");
        object.addProperty("ticketNumber", verifyPayTicket.getTicketNo());
        object.addProperty("merchantCode", "NEWRMS");
        object.addProperty("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
        object.addProperty("modelCode",Utils.getModelCode());
        object.addProperty("terminalId",Utils.getDeviceSerialNumber());
        object.addProperty("saleMerCode", "NEWRMS");
        object.addProperty("interfaceType", AppConstants.INTERFACE_TYPE);
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getClaimBean().getUrl() + "?requestData=" + URLEncoder.encode(object.toString(), "UTF-8"),
                    this, "loading", ActivitySleGame.this, "pay", headerData1);
            httpRequest.executeTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_reprint) {
            callReprint();
//            finish();
        } else if (v.getId() == R.id.layout_winning_claim) {
            Intent intent = new Intent(ActivitySleGame.this, WinningClaimActivity.class);
            intent.putExtra("from_sports", true);
            startActivityForResult(intent, 03);
        } else if (v.getId() == R.id.layout_result) {
            dateWiseResult();
        } else if (v.getId() == R.id.layout_cancel) {
            callCancel();
        }
    }

    @Override
    public void onResponse(String response, String requestedMethod) {
        ProgressBarDialog.getProgressDialog().dismiss();
        Intent intent;
        //response="{\"responseCode\":10011,\"responseMsg\":\"No Draw/Result Available!!\"}";
        JSONObject jsonObject = null;
        if (response == null || (response != null && response.equalsIgnoreCase("Failed"))) {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            jsonObject = new JSONObject(response);
            if (Utils.checkForSessionExpiryActivity(ActivitySleGame.this, jsonObject.optInt("responseCode"), ActivitySleGame.this)) {
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("errorCode")) {
                //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("errorMsg"));
                String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, "sle", jsonObject.optInt("errorCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (requestedMethod.equalsIgnoreCase("result")) {

            if (jsonObject.optInt("responseCode") == 0) {
//                intent = new Intent(this, PrintActivityResult.class);
//                intent.putExtra("print", "printResult");
//                intent.putExtra("response", response);
//                startActivityForResult(intent, 0011);
                intent = new Intent(this, ResultPreviewActivitySle.class);
                intent.putExtra("response", response);
                startActivityForResult(intent,0011);

            } else {

                String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, "sle",jsonObject.optInt("responseCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
            }
        } else if (requestedMethod.equalsIgnoreCase("reprint")) {
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (jsonObject.optInt("responseCode") == 0) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printReprint");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0013);
                } else {
                   // Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                    String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, "sle",jsonObject.optInt("responseCode"));
                    Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                }

            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                if (jsonObject.optInt("responseCode") == 0) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printReprint");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0013);
                }else {
                    //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));

                    String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, "sle",jsonObject.optInt("responseCode"));
                    Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                }
            }
        } else if (requestedMethod.equalsIgnoreCase("cancel")) {
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (jsonObject.optInt("responseCode") == 0) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printCancel");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0015);
                } else {
                    //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));

                    String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, "sle",jsonObject.optInt("responseCode"));
                    Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                if (jsonObject.optInt("responseCode") == 0) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printCancel");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0015);
                } else {
                    //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                    String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, "sle",jsonObject.optInt("responseCode"));
                    Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                }
            }
        } else if (requestedMethod.equalsIgnoreCase("sele")) {
            timer = -1;
            setTimer = false;
            parseData(response);
            setDrawData(currentGame);
            BaseClassSle.getBaseClassSle().afterFetch();
        } else if (requestedMethod.equalsIgnoreCase("pay")) {
            if (jsonObject.optInt("responseCode") == 0) {
                Intent winning_intent = new Intent(this, PrintActivityResult.class);
                winning_intent.putExtra("print", "winning");
                winning_intent.putExtra("winningResponse", response);
                startActivityForResult(winning_intent, 0014);
            } else {
                //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, "sle",jsonObject.optInt("responseCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.hasExtra("winning_response")) {
            response = data.getStringExtra("winning_response");
            verifyPayTicket = new Gson().fromJson(response, VerifyPayTicket.class);
            if (verifyPayTicket.getMessageCode() == 0) {
                Utils.playWinningSound(this, R.raw.small_2);
                CustomSuccessDialog.getProgressDialog().showDialogClaimSports(this, verifyPayTicket, this, getString(R.string.claim_ticket), false, true);
            } else {
                //Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), verifyPayTicket.getMessage());
                String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, "sle", verifyPayTicket.getMessageCode());
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
            }
        } else if (data != null && data.hasExtra("show_error")) {
            response = data.getStringExtra("show_error");
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), response);
        } else if (data != null && data.getExtras() != null && !data.getExtras().getBoolean("isPrintSuccess")) {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(this, getString(R.string.reprint_ticket), errorMsg);
        } else if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isUpdateBalance")) {
            upadateBalance();

        }
    }

    private void upadateBalance() {
        ProgressBarDialog.getProgressDialog().showProgress(ActivitySleGame.this);
        viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    @Override
    public void dialogButtonPress(boolean isVerify) {
        if (isVerify) {
            if (verifyPayTicket != null && verifyPayTicket.getMessageCode() == 0) {
                payVerifyTicket(verifyPayTicket);
            }
        }
    }

    public void callBalanceApi(View view) {
        ProgressBarDialog.getProgressDialog().showProgress(ActivitySleGame.this);
        viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (loginBean == null)
            Toast.makeText(ActivitySleGame.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(ActivitySleGame.this);
                PlayerData.getInstance().setLoginData(ActivitySleGame.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
                Toast.makeText(ActivitySleGame.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();
                refreshBalance();
            } else {
                if (Utils.checkForSessionExpiryActivity(ActivitySleGame.this, loginBean.getResponseData().getStatusCode(), ActivitySleGame.this))
                    return;
                String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, RMS, responseData.getStatusCode());
                Toast.makeText(ActivitySleGame.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            String errorMsg = Utils.getResponseMessage(ActivitySleGame.this, RMS, loginBean.getResponseCode());
            Toast.makeText(ActivitySleGame.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }
}
