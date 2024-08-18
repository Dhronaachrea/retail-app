package com.skilrock.retailapp.sle_game_portrait.sle_land_scape;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.BetAmountDialogSports;
import com.skilrock.retailapp.interfaces.ConfirmationListener;
import com.skilrock.retailapp.interfaces.WinningClaimListener;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.portrait_draw_games.ui.WinningClaimLandscapeActivity;
import com.skilrock.retailapp.sle_game_portrait.ActivityDraws;
import com.skilrock.retailapp.sle_game_portrait.BaseClassSle;
import com.skilrock.retailapp.sle_game_portrait.HttpRequest;
import com.skilrock.retailapp.sle_game_portrait.PrintActivityResult;
import com.skilrock.retailapp.sle_game_portrait.ResponseLis;
import com.skilrock.retailapp.sle_game_portrait.ResultPreviewActivitySle;
import com.skilrock.retailapp.sle_game_portrait.SaleBean;
import com.skilrock.retailapp.sle_game_portrait.SleFetchDataB2C;
import com.skilrock.retailapp.sle_game_portrait.SportsBetAmountBean;
import com.skilrock.retailapp.sle_game_portrait.VerifyPayTicket;
import com.skilrock.retailapp.sle_game_portrait.view_model.UpdateBalanceViewModel;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.CustomSuccessDialog;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

public class ActivityGamePlayLand extends BaseActivity implements View.OnClickListener, ResponseLis, SleDrawAdapterLand.GetDraws, BetAmountDialogSports.OnAmountUpdatedListener, WinningClaimListener {
    private RecyclerView rv_numbers, rv_purchase, rv_draws;
    private SleGamePlayAdapterNewLand sleGamePlayAdapter;
    private SleDrawAdapterLand sleDrawAdapter;
    private Intent intent;
    String response;
    private SleGamePlayAdapterNewFinalLand sleGamePlayAdapterNewFinalLand;
    private int currentBetSelected = 1;
    public int lastSelectedPosition = 0;
    private UpdateBalanceViewModel viewModel;
    private JsonObject object;
    VerifyPayTicket verifyPayTicket = null;
    String headerData1 = "userName,E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6|password,p@55w0rd|Content-Type,application/x-www-form-urlencoded";
    LinearLayout layout_winning_claim, layout_reprint, layout_cancel, layout_get_result;
    private int currentDraw = 0;
    private List<TextView> textViews;
    private LinearLayout rlBuyNowContainer;
    private BetAmountDialogSports dialogSports;
    LinearLayout other_amount;
    private boolean isBetOtherBetAmount = false;
    private ArrayList<SportsBetAmountBean> sportsBetAmountBeans = new ArrayList<>();
    private RelativeLayout delete;
    private TextView tvAddBet;
    int game_id;
    private TextView date_format, value, bet_amount_one, bet_amount_two, bet_amount_three, bet_amount_four, bet_amount_five, bet_amount_other, no_of_lines;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.layout_sports_game_play_land);
        viewModel = ViewModelProviders.of(this).get(UpdateBalanceViewModel.class);
        BaseClassSle.getBaseClassSle().setActivityGamePlayLand(this);
        setToolBar();
        Intent intent = getIntent();
        game_id = intent.getIntExtra("game_id", 0);
        AidlUtil.getInstance().connectPrinterService(ActivityGamePlayLand.this);
        rv_numbers = findViewById(R.id.rv_numbers);
        rv_purchase = findViewById(R.id.rv_purchase);
        rv_draws = findViewById(R.id.rv_draws_sle);
        bet_amount_one = findViewById(R.id.bet_amount_one);
        bet_amount_two = findViewById(R.id.bet_amount_two);
        bet_amount_three = findViewById(R.id.bet_amount_three);
        bet_amount_four = findViewById(R.id.bet_amount_four);
        bet_amount_five = findViewById(R.id.bet_amount_five);
        bet_amount_other = findViewById(R.id.bet_amount_other);
        no_of_lines = findViewById(R.id.tvBetValue);
        other_amount = findViewById(R.id.other_amount);
        value = findViewById(R.id.tv_total_bet_amount);
        date_format = findViewById(R.id.date_format);
        tvAddBet = findViewById(R.id.tvAddBet);
        delete = findViewById(R.id.delete);
        rlBuyNowContainer = findViewById(R.id.rlBuyNowContainer);
        layout_winning_claim = findViewById(R.id.layout_winning_claim);
        layout_reprint = findViewById(R.id.layout_reprint);
        layout_cancel = findViewById(R.id.layout_cancel);
        layout_get_result = findViewById(R.id.layout_get_result);

        bet_amount_one.setOnClickListener(this);
        bet_amount_two.setOnClickListener(this);
        bet_amount_three.setOnClickListener(this);
        bet_amount_four.setOnClickListener(this);
        bet_amount_five.setOnClickListener(this);
        other_amount.setOnClickListener(this);
        tvAddBet.setOnClickListener(this);
        rlBuyNowContainer.setOnClickListener(this);
        delete.setOnClickListener(this);

        layout_winning_claim.setOnClickListener(this);
        layout_reprint.setOnClickListener(this);
        layout_cancel.setOnClickListener(this);
        layout_get_result.setOnClickListener(this);
        textViews = new ArrayList<>();

        DividerItemDecoration decorator = new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot_line_land));
        rv_purchase.addItemDecoration(decorator);
        rv_purchase.setLayoutManager(new GridLayoutManager(this, 1));
        sleGamePlayAdapterNewFinalLand = new SleGamePlayAdapterNewFinalLand(this, ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getEventData(), 3, true);
        rv_purchase.setAdapter(sleGamePlayAdapterNewFinalLand);
        setBetAmount(ActivityDraws.gameTypeDataBean.getGameTypeMaxBetAmtMultiple(), ActivityDraws.gameTypeDataBean.getGameTypeUnitPrice());
        rv_draws.setHasFixedSize(true);
        rv_draws.setLayoutManager((new GridLayoutManager(this, ActivityDraws.drawDataBeansB2C.size())));
        sleDrawAdapter = new SleDrawAdapterLand(this, ActivityDraws.drawDataBeansB2C, this);
        rv_draws.setAdapter(sleDrawAdapter);

        rv_numbers.setLayoutManager(new GridLayoutManager(this, (BaseClassSle.getBaseClassSle().getTotalCount() == 13 ? 3 : 2)));
        sleGamePlayAdapter = new SleGamePlayAdapterNewLand(this, ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getEventData(), (BaseClassSle.getBaseClassSle().getTotalCount() == 13 ? 3 : 5), true);
        rv_numbers.setAdapter(sleGamePlayAdapter);
        //setBetSelected();
        viewModel.getLiveDataBalance().observe(this, observerBalance);
        date_format.setText(getString(R.string.no_of_lines_colon) + "  0");
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBalance();
    }

    private void setToolBar() {
        ImageView ivGameIcon = findViewById(R.id.ivGameIcon);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvUserBalance = findViewById(R.id.tvBal);
        tvUsername = findViewById(R.id.tvUserName);
        llBalance = findViewById(R.id.llBalance);
        tvTitle.setText(getString(R.string.soccer_13));
        ivGameIcon.setVisibility(View.GONE);
    }

    private void buyTicket() {
        ProgressBarDialog.getProgressDialog().showProgress(this);
        String headerData1 = "userName," + BaseClassSle.getBaseClassSle().getSalBean().getUserName() + "|password," + BaseClassSle.getBaseClassSle().getSalBean().getPassword() + "|Content-Type," + BaseClassSle.getBaseClassSle().getSalBean().getContentType();
        JsonObject finalPurch = new JsonObject();
        finalPurch.addProperty("gameId", BaseClassSle.getBaseClassSle().getSaleBean().getGameId() + "");
        finalPurch.addProperty("gameTypeId", BaseClassSle.getBaseClassSle().getSaleBean().getGameTypeId());
        finalPurch.addProperty("noOfBoard", BaseClassSle.getBaseClassSle().getSaleBean().getNoOfBoard() + "");
        finalPurch.addProperty("userName", PlayerData.getInstance().getUsername());
        finalPurch.addProperty("merchantCode", BaseClassSle.getBaseClassSle().getSaleBean().getMerchantCode());
        finalPurch.addProperty("totalPurchaseAmt", (Integer.parseInt(no_of_lines.getText().toString() + "") * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected) + "");
        finalPurch.addProperty("sessionId", BaseClassSle.getBaseClassSle().getSaleBean().getSessionId());
        finalPurch.addProperty("modelCode", Utils.getModelCode());
        finalPurch.addProperty("terminalId", Utils.getDeviceSerialNumber());
        finalPurch.addProperty("interfaceType", AppConstants.INTERFACE_TYPE);
        JsonArray drawInfo = new JsonArray();

        //for (SaleBean.DrawInfoBean drawInfoBean : BaseClassSle.getBaseClassSle().getDrawInfoBeans()) {
        for (int i = 0; i < ActivityDraws.drawDataBeansB2C.size(); i++) {
            if (lastSelectedPosition == i) {
                JsonObject draw = new JsonObject();
                draw.addProperty("drawId", ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getDrawId() + "");
                draw.addProperty("betAmtMul", (currentBetSelected) + "");
                draw.addProperty("drawStatus", ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getDrawStatus());
                draw.addProperty("drawFreezeTime", ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getFtg());
                draw.addProperty("drawDateTime", ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getDrawDateTime());
                draw.addProperty("saleStartTime", ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getSaleStartTime());
                JsonArray eventData = new JsonArray();
                JsonObject event;
                for (SaleBean.DrawInfoBean.EventDataBean eventDataBean : BaseClassSle.getBaseClassSle().getEventDataBeans()) {
                    String numberSelectedValue = "";
                    event = new JsonObject();
                    if (eventDataBean.isHomePlusSelected()) {
                        numberSelectedValue = numberSelectedValue + "H%2B,";
                    }
                    if (eventDataBean.isHomeSelected()) {
                        numberSelectedValue = numberSelectedValue + "H,";
                    }
                    if (eventDataBean.isDrawSelected()) {
                        numberSelectedValue = numberSelectedValue + "D,";
                    }
                    if (eventDataBean.isAwaySelected()) {
                        numberSelectedValue = numberSelectedValue + "A,";
                    }
                    if (eventDataBean.isAwayPlusSelected()) {
                        numberSelectedValue = numberSelectedValue + "A%2B,";
                    }
                    numberSelectedValue = numberSelectedValue.substring(0, numberSelectedValue.lastIndexOf(","));
                    event.addProperty("eventId", eventDataBean.getEventId() + "");
                    event.addProperty("eventSelected", numberSelectedValue);
                    eventData.add(event);
                }

//        eventData.add(event);

                draw.add("eventData", eventData);

                drawInfo.add(draw);
            }

        }

        finalPurch.add("drawInfo", drawInfo);
        Log.i("purhcaseJson", finalPurch.toString());

        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getSalBean().getUrl() + "?requestData=" + URLEncoder.encode(finalPurch.toString(), "UTF-8"),
                    this, "loading", ActivityGamePlayLand.this, "purchase", headerData1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        httpRequest.setIsParams(true,finalPurch.toString());
        httpRequest.executeTask();


    }
    private void updateBalance() {
        ProgressBarDialog.getProgressDialog().showProgress(ActivityGamePlayLand.this);
        viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());

    }
    public boolean setNoOfLines(int noOfLines, int currentBet) {
        if ((noOfLines * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBet) > Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt())) {
            String amount = Utils.getBalanceToView(Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt()), ",", " ", 0);
            Utils.showCustomErrorDialog(this, getString(R.string.sports), getString(R.string.bet_val_greater_than) + amount);
            return false;
        }
        this.currentBetSelected = currentBet;
        sleGamePlayAdapterNewFinalLand.notifyDataSetChanged();
        return true;
    }

    public boolean setNoOfLines(int noOfLines) {
        if ((noOfLines * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected) > Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt())) {
            String amount = Utils.getBalanceToView(Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt()), ",", " ", 0);
            Utils.showCustomErrorDialog(this, getString(R.string.sports), getString(R.string.bet_val_greater_than) + amount);
            return false;
        }

        if (noOfLines >= 0)
            no_of_lines.setText(noOfLines + "");
        date_format.setText(getString(R.string.no_of_lines_colon) +" "+ noOfLines);
        //value.setText("$" + (Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected) + "");
        value.setText("" + getFormattedAmount(Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected));
        sleGamePlayAdapterNewFinalLand.notifyDataSetChanged();
        return true;

    }

    private void resetData() {
        for (SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean eventDataBean : ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getEventData()) {
            eventDataBean.setHomePlusSelected(false, -1);
            eventDataBean.setHomeSelected(false, -1);
            eventDataBean.setDrawSelected(false, -1);
            eventDataBean.setAwaySelected(false, -1);
            eventDataBean.setAwayPlusSelected(false, -1);
            eventDataBean.setTotalSizeToZero();

        }
        for (SaleBean.DrawInfoBean.EventDataBean eventDataBean : BaseClassSle.getBaseClassSle().getEventDataBeans()) {
            eventDataBean.setHomePlusSelected(false);
            eventDataBean.setHomeSelected(false);
            eventDataBean.setDrawSelected(false);
            eventDataBean.setAwaySelected(false);
            eventDataBean.setAwayPlusSelected(false);
        }
        setNoOfLines(0);
        if (sleGamePlayAdapter != null) {
            sleGamePlayAdapter.notifyDataSetChanged();
        }
        currentBetSelected = 1;
        value.setText("" + getFormattedAmount(Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected));
        bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
    }


    private void resetDataOnDrawChange() {
        for (SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean eventDataBean : ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getEventData()) {
            eventDataBean.setHomePlusSelected(false, -1);
            eventDataBean.setHomeSelected(false, -1);
            eventDataBean.setDrawSelected(false, -1);
            eventDataBean.setAwaySelected(false, -1);
            eventDataBean.setAwayPlusSelected(false, -1);
            eventDataBean.setTotalSizeToZero();

        }
        for (SaleBean.DrawInfoBean.EventDataBean eventDataBean : BaseClassSle.getBaseClassSle().getEventDataBeans()) {
            eventDataBean.setHomePlusSelected(false);
            eventDataBean.setHomeSelected(false);
            eventDataBean.setDrawSelected(false);
            eventDataBean.setAwaySelected(false);
            eventDataBean.setAwayPlusSelected(false);
        }
        setNoOfLines(0);
        if (sleGamePlayAdapter != null) {
            sleGamePlayAdapter.notifyDataSetChanged();
        }
        currentBetSelected = 1;
        value.setText("" + getFormattedAmount(Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected));
        bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        resetData();
    }

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        ActivityGamePlayLand.this.finish();
    }

    @SuppressLint("NewApi")
    private void setBetSelected() {
        for (int i = 0; i < 6; i++) {
            if (i == (currentBetSelected - 1)) {
                //textViews.get(i).setBackground(getResources().getDrawable(R.drawable.buy_rectangle_color_bet_land_selection));
                //textViews.get(i).setTextColor(getResources().getColor(R.color.colorWhite));
            } else {
                //textViews.get(i).setBackground(getResources().getDrawable(R.drawable.buy_rectangle_color_bet_land_normal));
                //textViews.get(i).setTextColor(getResources().getColor(R.color.bet_color_land));
            }
        }
        value.setText("" + getFormattedAmount(Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvAddBet) {
            resetData();
            setBetSelected();
            sleGamePlayAdapter.resetTotal();
            sleGamePlayAdapter.notifyDataSetChanged();
        } else if (v.getId() == R.id.bet_amount_one) {
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 1)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    setSelectedBetAmountForHighlight(0);
                    bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                    currentBetSelected = 1;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.bet_amount_two) {
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 2)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    setSelectedBetAmountForHighlight(1);
                    bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                    currentBetSelected = 2;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.bet_amount_three) {
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 3)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    setSelectedBetAmountForHighlight(2);
                    bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                    currentBetSelected = 3;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.bet_amount_four) {
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 4)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    setSelectedBetAmountForHighlight(3);
                    bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(3).getAmount()));
                    currentBetSelected = 4;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.bet_amount_five) {
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 5)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    setSelectedBetAmountForHighlight(4);
                    bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(4).getAmount()));
                    currentBetSelected = 5;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.other_amount) {
            if (isBetOtherBetAmount) {
                if (dialogSports == null || !dialogSports.isShowing()) {
                    dialogSports = new BetAmountDialogSports(ActivityGamePlayLand.this, sportsBetAmountBeans, this);
                    dialogSports.show();
                    if (dialogSports.getWindow() != null) {
                        dialogSports.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogSports.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                }
            }

        } else if (v.getId() == R.id.tvAddBet || v.getId() == R.id.delete) {
            resetData();
        } else if (v.getId() == R.id.rlBuyNowContainer) {
            if (Integer.parseInt(no_of_lines.getText().toString()) > 0) {
                BaseClassSle.getBaseClassSle().getDrawInfoBeans().get(0).setEventData(BaseClassSle.getBaseClassSle().getEventDataBeans());
                buyTicket();
            }
        } else if (v.getId() == R.id.layout_winning_claim) {
            Intent intent = new Intent(ActivityGamePlayLand.this, WinningClaimLandscapeActivity.class);
            intent.putExtra("from_sports", true);
            startActivityForResult(intent, 03);
        } else if (v.getId() == R.id.layout_reprint) {
            callReprint();

        } else if (v.getId() == R.id.layout_cancel) {
             callCancel();
            //printCamWin();

        } else if (v.getId() == R.id.layout_get_result) {
            dateWiseResult();

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
                    this, "loading", ActivityGamePlayLand.this, "reprint", headerData1);
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
                        this, "loading", ActivityGamePlayLand.this, "cancel", headerData1);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpRequest.executeTask();
        };

        Utils.showCancelTicketConfirmationDialog(ActivityGamePlayLand.this, confirmationListener);
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
                    this, "loading", ActivityGamePlayLand.this, "result", headerData1);
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
        object.addProperty("modelCode", Utils.getModelCode());
        object.addProperty("terminalId", Utils.getDeviceSerialNumber());
        object.addProperty("saleMerCode", "NEWRMS");
        object.addProperty("interfaceType", AppConstants.INTERFACE_TYPE);
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getClaimBean().getUrl() + "?requestData=" + URLEncoder.encode(object.toString(), "UTF-8"),
                    this, "loading", ActivityGamePlayLand.this, "pay", headerData1);
            httpRequest.executeTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(String response, String requestedMethod) {
        ProgressBarDialog.getProgressDialog().dismiss();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            if (Utils.checkForSessionExpiryActivity(ActivityGamePlayLand.this, jsonObject.optInt("responseCode"), ActivityGamePlayLand.this)) {
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (response == null || (response != null && response.equalsIgnoreCase("Failed"))) {
            Utils.showCustomErrorDialog(this, getString(R.string.sports), getString(R.string.some_technical_issue));
            return;
        }


        try {
            jsonObject = new JSONObject(response);
            if (jsonObject != null && jsonObject.has("responseCode") && jsonObject.optInt("responseCode") != 0) {
//                Toast.makeText(this,jsonObject.optString("responseMsg"),Toast.LENGTH_LONG).show();
                //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                if (jsonObject.optString("responseMsg").toLowerCase().contains("login") && jsonObject.optString("responseMsg").toLowerCase().contains("again")) {

                }
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("errorCode")) {
                //Toast.makeText(this,jsonObject.optString("errorMsg"),Toast.LENGTH_LONG).show();
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle", jsonObject.optInt("errorCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("errorMsg"));
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (requestedMethod.equalsIgnoreCase("purchase")) {
            if (jsonObject.optInt("responseCode") == 0) {

                if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                    intent = new Intent(ActivityGamePlayLand.this, PrintActivityResult.class);
                    intent.putExtra("print", "printPurchase");
                    intent.putExtra("response", response);
                    updateBalance();
                    startActivityForResult(intent, 0012);

                }
            } else {
                //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
            }
        }

        if (requestedMethod.equalsIgnoreCase("result")) {
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (jsonObject.optInt("responseCode") == 0) {
//                    intent = new Intent(this, PrintActivityResult.class);
//                    intent.putExtra("print", "printResult");
//                    intent.putExtra("response", response);
//                    startActivityForResult(intent, 0011);
                    intent = new Intent(this, ResultPreviewActivitySle.class);
                    intent.putExtra("response", response);
                    startActivityForResult(intent,0011);
                } else {
                    //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                    String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
                    Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                if (jsonObject.optInt("responseCode") == 0) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printResult");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0011);

                }
            } else {
                //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
            }
        } else if (requestedMethod.equalsIgnoreCase("reprint")) {
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (jsonObject.optInt("responseCode") == 0) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printReprint");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0013);
                } else {
                    //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                    String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
                    Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                }
            } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                if (jsonObject.optInt("responseCode") == 0) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printReprint");
                    intent.putExtra("response", response);
                    startActivityForResult(intent, 0013);
                } else {
                   // Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                    String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
                    Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                }
            }
        } else if (requestedMethod.equalsIgnoreCase("cancel")) {
            if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
                if (jsonObject.optInt("responseCode") == 0) {
                    intent = new Intent(this, PrintActivityResult.class);
                    intent.putExtra("print", "printCancel");
                    intent.putExtra("response", response);
                    updateBalance();
                    startActivityForResult(intent, 0015);

                } else {
                    //Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                    String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
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
                    String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
                    Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                }
            }
        } else if (requestedMethod.equalsIgnoreCase("pay")) {
            if (jsonObject.optInt("responseCode") == 0) {
                Intent winning_intent = new Intent(this, PrintActivityResult.class);
                winning_intent.putExtra("print", "winning");
                winning_intent.putExtra("winningResponse", response);
                updateBalance();
                startActivityForResult(winning_intent, 0014);
            } else {
                ///Utils.showCustomErrorDialog(this, getString(R.string.sports), jsonObject.optString("responseMsg"));
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle",  jsonObject.optInt("responseCode"));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
            }
        }

    }


    @Override
    public void onItemClick(int pos) {
        lastSelectedPosition = pos;
        resetDataOnDrawChange();
        rv_numbers.setLayoutManager(new GridLayoutManager(this, (BaseClassSle.getBaseClassSle().getTotalCount() == 13 ? 3 : 2)));
        sleGamePlayAdapter = new SleGamePlayAdapterNewLand(this, ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getEventData(), (BaseClassSle.getBaseClassSle().getTotalCount() == 13 ? 3 : 5), true);
        rv_numbers.setAdapter(sleGamePlayAdapter);
        sleGamePlayAdapter.notifyDataSetChanged();

        rv_purchase.setLayoutManager(new GridLayoutManager(this, 1));
        sleGamePlayAdapterNewFinalLand = new SleGamePlayAdapterNewFinalLand(this, ActivityDraws.drawDataBeansB2C.get(lastSelectedPosition).getEventData(), 3, true);
        rv_purchase.setAdapter(sleGamePlayAdapterNewFinalLand);
        sleGamePlayAdapterNewFinalLand.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    private void setBetAmount(int gameTypeMaxBetAmtMultiple, int gameTypeUnitPrice) {
        for (int i = 1; i <= gameTypeMaxBetAmtMultiple; i++) {
            SportsBetAmountBean sportsBetAmountBean = new SportsBetAmountBean();
            sportsBetAmountBean.setAmount(gameTypeUnitPrice * i);
            sportsBetAmountBean.setSelected(false);
            sportsBetAmountBeans.add(sportsBetAmountBean);
        }
        bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
        if (sportsBetAmountBeans != null && sportsBetAmountBeans.size() > 0) {
            if (sportsBetAmountBeans.size() > 5) {
                setSelectedBetAmountForHighlight(0);
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText("" +
                        getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.VISIBLE);
                bet_amount_three.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                bet_amount_four.setVisibility(View.VISIBLE);
                bet_amount_four.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(3).getAmount()));
                bet_amount_five.setVisibility(View.VISIBLE);
                bet_amount_five.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(4).getAmount()));
                isBetOtherBetAmount = true;
            } else if (sportsBetAmountBeans.size() == 5) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText("" +
                        getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.VISIBLE);
                bet_amount_three.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                bet_amount_four.setVisibility(View.VISIBLE);
                bet_amount_four.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(3).getAmount()));
                bet_amount_five.setVisibility(View.VISIBLE);
                bet_amount_five.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(4).getAmount()));
                isBetOtherBetAmount = false;
            } else if (sportsBetAmountBeans.size() == 4) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.VISIBLE);
                bet_amount_three.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                bet_amount_four.setVisibility(View.VISIBLE);
                bet_amount_four.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(3).getAmount()));
                bet_amount_five.setVisibility(View.GONE);
                isBetOtherBetAmount = false;
            } else if (sportsBetAmountBeans.size() == 3) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.VISIBLE);
                bet_amount_three.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                bet_amount_four.setVisibility(View.GONE);
                bet_amount_five.setVisibility(View.GONE);
                isBetOtherBetAmount = false;
            } else if (sportsBetAmountBeans.size() == 2) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.GONE);
                bet_amount_four.setVisibility(View.GONE);
                bet_amount_five.setVisibility(View.GONE);
                isBetOtherBetAmount = false;
            } else if (sportsBetAmountBeans.size() == 1) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        "" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.GONE);
                bet_amount_three.setVisibility(View.GONE);
                bet_amount_four.setVisibility(View.GONE);
                bet_amount_five.setVisibility(View.GONE);
                isBetOtherBetAmount = false;
            }

        } else {
            //Toast.makeText(this, getString(R.string.some_error_try_later), Toast.LENGTH_SHORT).show();
        }


    }

    private void setSelectedBetAmountForHighlight(int position) {
        for (int index = 0; index < sportsBetAmountBeans.size(); index++) {
            sportsBetAmountBeans.get(index).setSelected(position == index);
        }
    }


    @Override
    public void onAmountChange(int amount, int pos) {
        if (amount != -1) {

            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), pos)) {
                bet_amount_other.setText("" + getFormattedAmount(amount));
                value.setText("" + getFormattedAmount(Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected));
            } else {
                if (sportsBetAmountBeans != null && sportsBetAmountBeans.size() > 0) {
                    for (int i = 0; i < sportsBetAmountBeans.size(); i++) {
                        sportsBetAmountBeans.get(i).setSelected(false);
                    }
                    sportsBetAmountBeans.get(currentBetSelected - 1).setSelected(true);
                }
            }
            //value.setText("$" + Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.hasExtra("winning_response")) {
            response = data.getStringExtra("winning_response");
            verifyPayTicket = new Gson().fromJson(response, VerifyPayTicket.class);
            if (verifyPayTicket.getMessageCode() == 0) {
                Utils.playWinningSound(this, R.raw.small_2);
                CustomSuccessDialog.getProgressDialog().showDialogClaimSports(this, verifyPayTicket, ActivityGamePlayLand.this, getString(R.string.claim_ticket), false, true);
            } else {
                //Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), verifyPayTicket.getMessage());
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "sle", verifyPayTicket.getMessageCode());
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
            }
        } else if (data != null && data.hasExtra("show_error")) {
            response = data.getStringExtra("show_error");
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), response);
        } else if (data != null && data.hasExtra("isPrintSuccess") && data.getBooleanExtra("isPrintSuccess", false)) {
            resetData();
        } /*else if (data != null && data.hasExtra("update_balance") && data.getBooleanExtra("update_balance", false)) {
            updateBalance();
        }*/

    }

    private String getFormattedAmount(int amt) {
        String amount;
        try {
            amount = Utils.getBalanceToView(amt, Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(ActivityGamePlayLand.this)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(ActivityGamePlayLand.this)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this));
        } catch (Exception e) {
            e.printStackTrace();
            amount = String.valueOf(amt);
        }
        return amount;
    }

    @Override
    public void dialogButtonPress(boolean isVerify) {
        if (isVerify) {
            if (verifyPayTicket != null && verifyPayTicket.getMessageCode() == 0) {
                payVerifyTicket(verifyPayTicket);
            }
        }
    }

    private String getAmountWithCurrency(String strAmount) {
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount),
                    Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this)),
                    Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this)),
                    Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(strAmount);
        }
        return formattedAmount;
    }
/*
    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        finish();
        if (loginBean == null)
            Toast.makeText(ActivityGamePlayLand.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(ActivityGamePlayLand.this);
                PlayerData.getInstance().setLoginData(ActivityGamePlayLand.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
                Toast.makeText(ActivityGamePlayLand.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();
                refreshBalance();
            } else {
                if (Utils.checkForSessionExpiryActivity(ActivityGamePlayLand.this, loginBean.getResponseData().getStatusCode(), ActivityGamePlayLand.this))
                    return;
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "RMS", responseData.getStatusCode());
                Toast.makeText(ActivityGamePlayLand.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "RMS", loginBean.getResponseCode());
            Toast.makeText(ActivityGamePlayLand.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };*/

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (loginBean == null)
            Toast.makeText(ActivityGamePlayLand.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(ActivityGamePlayLand.this);
                PlayerData.getInstance().setLoginData(ActivityGamePlayLand.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
                // Toast.makeText(DrawGameBaseActivity.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();
                super.refreshBalance();
            } else {
                if (Utils.checkForSessionExpiryActivity(ActivityGamePlayLand.this, loginBean.getResponseData().getStatusCode(), ActivityGamePlayLand.this))
                    return;
                String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "RMS", responseData.getStatusCode());
                Toast.makeText(ActivityGamePlayLand.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            String errorMsg = Utils.getResponseMessage(ActivityGamePlayLand.this, "RMS", loginBean.getResponseCode());
            Toast.makeText(ActivityGamePlayLand.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    public void setdraws() {
        if (sleDrawAdapter!=null) {
            Toast.makeText(this, getString(R.string.updating_draws), Toast.LENGTH_SHORT).show();
            rv_draws.setHasFixedSize(true);
            rv_draws.setLayoutManager((new GridLayoutManager(this, ActivityDraws.drawDataBeansB2C.size())));
            sleDrawAdapter = new SleDrawAdapterLand(this, ActivityDraws.drawDataBeansB2C, this);
            rv_draws.setAdapter(sleDrawAdapter);
            sleDrawAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

}
