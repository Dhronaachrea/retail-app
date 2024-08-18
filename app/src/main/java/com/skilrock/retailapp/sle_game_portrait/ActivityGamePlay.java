package com.skilrock.retailapp.sle_game_portrait;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.BetAmountDialogSports;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.sle_game_portrait.view_model.UpdateBalanceViewModel;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class ActivityGamePlay extends BaseActivity implements View.OnClickListener, BetAmountDialogSports.OnAmountUpdatedListener {
    private RecyclerView rv_numbers;
    SleGamePlayAdapterNew sleGamePlayAdapter;
    public static BigDecimal thresHoldAmount;
    public static BigDecimal tktMaxAmount;
    int totalNumberOfMatches = 0;
    private int currentBetSelected = 1;
    private UpdateBalanceViewModel viewModel;
    int currentDraw;
    private LinearLayout reset_data, buy_ticket, play_option;
    private ArrayList<SportsBetAmountBean> sportsBetAmountBeans = new ArrayList<>();
    private String textToDisplay;
    private BetAmountDialogSports dialogSports;
    //private TextView userName, balance;
    private boolean isBetOtherBetAmount = false;
    private int maxBetAmount;
    private List<TextView> textViews;
    private TextView pool_price, bet_amount_one, bet_amount_two, bet_amount_three, bet_amount_four, bet_amount_five, bet_amount_other, no_of_lines,
            value, draw_pool;
    private static String dotFormatter;
    private static String amountFormatter;
    private static int decimalDegits = 0;
    private LinearLayout llOther;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            setContentView(R.layout.layout_game_play_sports_v2pro);
        else
            setContentView(R.layout.layout_game_play_sports);
        rv_numbers = findViewById(R.id.sports_recycle_view);
        viewModel = ViewModelProviders.of(this).get(UpdateBalanceViewModel.class);
        BaseClassSle.getBaseClassSle().setActivityGamePlay(this);

        no_of_lines = findViewById(R.id.no_of_lines);


        buy_ticket = findViewById(R.id.buy_ticket);

        value = findViewById(R.id.value);

        reset_data = findViewById(R.id.reset_data);

        reset_data.setOnClickListener(this);

        buy_ticket.setOnClickListener(this);

        /*userName = findViewById(R.id.userName);
        balance = findViewById(R.id.balance);*/

        play_option = findViewById(R.id.play_option);
        CircularTextView ctv2 = findViewById(R.id.circle_2);
        ctv2.setStrokeColor("#f5f5f5");
        /*balance.setText(PlayerData.getInstance().getOrgBalance());
        userName.setText(PlayerData.getInstance().getUsername());*/


        viewModel.getLiveDataBalance().observe(this, observerBalance);

        bet_amount_one = findViewById(R.id.bet_amount_one);
        bet_amount_two = findViewById(R.id.bet_amount_two);
        bet_amount_three = findViewById(R.id.bet_amount_three);
        bet_amount_four = findViewById(R.id.bet_amount_four);
        bet_amount_five = findViewById(R.id.bet_amount_five);
        bet_amount_other = findViewById(R.id.bet_amount_other);
        llOther = findViewById(R.id.other_amount);
        textViews = new ArrayList<>();

        textViews.add(bet_amount_one);
        textViews.add(bet_amount_two);
        textViews.add(bet_amount_three);
        textViews.add(bet_amount_four);
        textViews.add(bet_amount_five);
        textViews.add(bet_amount_other);
        bet_amount_one.setOnClickListener(this);
        bet_amount_two.setOnClickListener(this);
        bet_amount_three.setOnClickListener(this);
        bet_amount_four.setOnClickListener(this);
        bet_amount_five.setOnClickListener(this);
        llOther.setOnClickListener(this);

        totalNumberOfMatches = getIntent().getExtras().getInt("totalNoOfMatch");

        int totalAmount = ActivityDraws.gameTypeDataBean.getGameTypeMaxBetAmtMultiple() * ActivityDraws.gameTypeDataBean.getGameTypeUnitPrice();
        maxBetAmount = totalAmount;
        setBetAmount(ActivityDraws.gameTypeDataBean.getGameTypeMaxBetAmtMultiple(), ActivityDraws.gameTypeDataBean.getGameTypeUnitPrice());
        currentDraw = getIntent().getExtras().getInt("currentDraw");
        //draw_pool = findViewById(R.id.draw_pool);
        String time = ActivityDraws.drawDataBeansB2C.get(currentDraw).getDrawDateTime().split(" ")[1];
        String[] edit_time = time.split(":");
        textToDisplay = ActivityDraws.drawDataBeansB2C.get(currentDraw).getDrawDisplayString() + " - " + getIntent().getStringExtra("drawDate") + "  " + edit_time[0] + ":" + edit_time[1];
        //draw_pool.setText(textToDisplay);
        pool_price = findViewById(R.id.pool_price);
        DividerItemDecoration decorator = new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot_line));
        rv_numbers.addItemDecoration(decorator);

        rv_numbers.setLayoutManager(new GridLayoutManager(this, 1));
        if (getIntent().getExtras().getBoolean("isB2C")) {
            String amount = Utils.getBalanceToView(ActivityDraws.drawDataBeansB2C.get(currentDraw).getPrizePoolAmount(), ",", " ", decimalDegits);
            pool_price.setText("" + amount + "");
            sleGamePlayAdapter = new SleGamePlayAdapterNew(this, ActivityDraws.drawDataBeansB2C.get(currentDraw).getEventData(), getIntent().getExtras().getInt("totalNoOfMatch"), true);
            rv_numbers.setAdapter(sleGamePlayAdapter);
        } else {
            sleGamePlayAdapter = new SleGamePlayAdapterNew(this, ActivityDraws.drawDataBeans.get(currentDraw).getEventData(), getIntent().getExtras().getInt("totalNoOfMatch"));
            rv_numbers.setAdapter(sleGamePlayAdapter);
        }
        value.setText("" + (Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected) + "");
        // setBetSelected();

        //back_click.setOnClickListener(this);
        setToolBar();
    }

    @SuppressLint("SetTextI18n")
    private void setBetAmount(int gameTypeMaxBetAmtMultiple, int gameTypeUnitPrice) {
        for (int i = 1; i <= gameTypeMaxBetAmtMultiple; i++) {
            SportsBetAmountBean sportsBetAmountBean = new SportsBetAmountBean();
            sportsBetAmountBean.setAmount(gameTypeUnitPrice * i);
            sportsBetAmountBean.setSelected(false);
            sportsBetAmountBeans.add(sportsBetAmountBean);
        }
        bet_amount_other.setText(sportsBetAmountBeans.get(0).getAmount() + "");
        if (sportsBetAmountBeans != null && sportsBetAmountBeans.size() > 0) {
            if (sportsBetAmountBeans.size() > 5) {
                setSelectedBetAmountForHighlight(0);
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.VISIBLE);
                bet_amount_three.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                bet_amount_four.setVisibility(View.VISIBLE);
                bet_amount_four.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(3).getAmount()));
                bet_amount_five.setVisibility(View.VISIBLE);
                bet_amount_five.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(4).getAmount()));
                isBetOtherBetAmount = true;
            } else if (sportsBetAmountBeans.size() == 5) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.VISIBLE);
                bet_amount_three.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                bet_amount_four.setVisibility(View.VISIBLE);
                bet_amount_four.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(3).getAmount()));
                bet_amount_five.setVisibility(View.VISIBLE);
                bet_amount_five.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(4).getAmount()));
                isBetOtherBetAmount = false;
            } else if (sportsBetAmountBeans.size() == 4) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.VISIBLE);
                bet_amount_three.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                bet_amount_four.setVisibility(View.VISIBLE);
                bet_amount_four.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(3).getAmount()));
                bet_amount_five.setVisibility(View.GONE);
                isBetOtherBetAmount = false;
            } else if (sportsBetAmountBeans.size() == 3) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.VISIBLE);
                bet_amount_three.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                bet_amount_four.setVisibility(View.GONE);
                bet_amount_five.setVisibility(View.GONE);
                isBetOtherBetAmount = false;
            } else if (sportsBetAmountBeans.size() == 2) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.VISIBLE);
                bet_amount_two.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                bet_amount_three.setVisibility(View.GONE);
                bet_amount_four.setVisibility(View.GONE);
                bet_amount_five.setVisibility(View.GONE);
                isBetOtherBetAmount = false;
            } else if (sportsBetAmountBeans.size() == 1) {
                bet_amount_one.setVisibility(View.VISIBLE);
                bet_amount_one.setText(
                        getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                bet_amount_two.setVisibility(View.GONE);
                bet_amount_three.setVisibility(View.GONE);
                bet_amount_four.setVisibility(View.GONE);
                bet_amount_five.setVisibility(View.GONE);
                isBetOtherBetAmount = false;
            }

        } else {
            Toast.makeText(this, getString(R.string.some_technical_issue), Toast.LENGTH_SHORT).show();
        }


    }

    private void setSelectedBetAmountForHighlight(int position) {
        for (int index = 0; index < sportsBetAmountBeans.size(); index++) {
            sportsBetAmountBeans.get(index).setSelected(position == index);
        }
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

        tvTitle.setText(textToDisplay);
        ivGameIcon.setVisibility(View.GONE);
    }

    private void setUpadteAfterFetch(){
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(textToDisplay);
    }

    public boolean setNoOfLines(int noOfLines) {
        if ((noOfLines * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected) > Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt())) {
            String amount = Utils.getBalanceToView(Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt()), ",", " ", decimalDegits);
            Utils.showCustomErrorDialog(this, getString(R.string.sports), getString(R.string.bet_val_greater_than) + amount);
            return false;
        }
        if (noOfLines > 0) {
            play_option.setVisibility(View.GONE);
        } else {
            play_option.setVisibility(View.VISIBLE);
        }
        no_of_lines.setText(noOfLines + "");
        value.setText("" + getFormattedAmount(Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected) + "");
        return true;
    }

    /*
     */
    private void setBetSelected() {
         /* for(int i = 0; i<6;i++){
              if(i == (currentBetSelected - 1)){
                  textViews.get(i).setBackground(getResources().getDrawable(R.drawable.buy_rectangle_color_selection));
                  textViews.get(i).setTextColor(getResources().getColor(R.color.colorWhite));
              }else{
                  textViews.get(i).setBackground(getResources().getDrawable(R.drawable.buy_rectangle_color));
                  textViews.get(i).setTextColor(getResources().getColor(R.color.new_five_selection));
              }
          }*/
        value.setText("" + getFormattedAmount(Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected));
    }

    public boolean setNoOfLines(int noOfLines, int currentBet) {
        if ((noOfLines * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBet) > Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt())) {
            String amount = Utils.getBalanceToView(Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt()), ",", " ", decimalDegits);
            Utils.showCustomErrorDialog(this, getString(R.string.sports), getString(R.string.bet_val_greater_than) + amount);
            return false;
        }
        this.currentBetSelected = currentBet;
        sleGamePlayAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onDestroy() {
        resetData();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        resetData();
        super.onBackPressed();
    }

    private void openOtherDialog() {
        DialogBet.OnBetClick onBetClick = new DialogBet.OnBetClick() {
            @Override
            public void onBetClick(int currentBetSelected, int amount) {

            }
        };
        int totalNumber = (ActivityDraws.gameTypeDataBean.getGameTypeMaxBetAmtMultiple() / BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice()) - 5;
        DialogBet dialogBet = new DialogBet(this, totalNumber, onBetClick);
        dialogBet.show();

    }

    private void resetData() {
        for (SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean eventDataBean : ActivityDraws.drawDataBeansB2C.get(currentDraw).getEventData()) {
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
        bet_amount_other.setText("" + getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reset_data) {
            currentBetSelected = 1;
            resetData();
            sleGamePlayAdapter.resetTotal();
            sleGamePlayAdapter.notifyDataSetChanged();
            value.setText("" + getFormattedAmount(Integer.parseInt(no_of_lines.getText().toString()) * BaseClassSle.getBaseClassSle().getSaleBean().getUnitePrice() * currentBetSelected));
        } else if (v.getId() == R.id.bet_amount_one) {
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 1)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    setSelectedBetAmountForHighlight(0);
                    bet_amount_other.setText(getFormattedAmount(sportsBetAmountBeans.get(0).getAmount()));
                    currentBetSelected = 1;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.bet_amount_two) {
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 2)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    setSelectedBetAmountForHighlight(1);
                    bet_amount_other.setText(getFormattedAmount(sportsBetAmountBeans.get(1).getAmount()));
                    currentBetSelected = 2;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.bet_amount_three) {
            setSelectedBetAmountForHighlight(2);
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 3)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    bet_amount_other.setText(getFormattedAmount(sportsBetAmountBeans.get(2).getAmount()));
                    currentBetSelected = 3;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.bet_amount_four) {
            setSelectedBetAmountForHighlight(3);
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 4)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    bet_amount_other.setText(getFormattedAmount(sportsBetAmountBeans.get(3).getAmount()));
                    currentBetSelected = 4;
                    setBetSelected();
                }
            }

        } else if (v.getId() == R.id.bet_amount_five) {
            setSelectedBetAmountForHighlight(4);
            if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()), 5)) {
                if (setNoOfLines(Integer.parseInt(no_of_lines.getText().toString()))) {
                    bet_amount_other.setText(getFormattedAmount(sportsBetAmountBeans.get(4).getAmount()));
                    currentBetSelected = 5;
                    setBetSelected();
                }
            }
        } else if (v.getId() == R.id.buy_ticket) {
            if (Integer.parseInt(no_of_lines.getText().toString()) > 0) {
                Intent intent = new Intent(ActivityGamePlay.this, ActivityGamePlayFinal.class);
                intent.putExtra("currentDraw", currentDraw);
                intent.putExtra("isB2C", true);
                intent.putExtra("totalNoOfMatch", totalNumberOfMatches);
                intent.putExtra("currentBet", currentBetSelected);
                intent.putExtra("noOfLines", no_of_lines.getText().toString());
                intent.putExtra("display", textToDisplay);
                startActivityForResult(intent, 0012);
                BaseClassSle.getBaseClassSle().getDrawInfoBeans().get(0).setEventData(BaseClassSle.getBaseClassSle().getEventDataBeans());
//                resetData();
//                finish();
            }
        } else if (v.getId() == R.id.other_amount) {
            if (isBetOtherBetAmount) {
                if (dialogSports == null || !dialogSports.isShowing()) {
                    dialogSports = new BetAmountDialogSports(ActivityGamePlay.this, sportsBetAmountBeans, this);
                    dialogSports.show();
                    if (dialogSports.getWindow() != null) {
                        dialogSports.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogSports.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                }
            }


        }

        /*else if(v.getId() == R.id.back_click){
            onBackPressed();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0012 || requestCode == 0012) {
            if (data != null && data.hasExtra("reset") && data.getBooleanExtra("reset", false)) {
                resetData();
            }
            if (data != null && data.hasExtra("isBalanceUpdate") && data.getBooleanExtra("isBalanceUpdate", false)) {
                updateBalance();
            }


        }
    }

    private void updateBalance() {
        ProgressBarDialog.getProgressDialog().showProgress(ActivityGamePlay.this);
        viewModel.getUpdatedBalance(PlayerData.getInstance().getToken());

    }

    private void createSaleRequest() {
        SaleBean saleBean;
        if (BaseClassSle.getBaseClassSle().getSaleBean() != null) {
            saleBean = BaseClassSle.getBaseClassSle().getSaleBean();
        } else {
            saleBean = new SaleBean();
        }
    }

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        ActivityGamePlay.this.finish();
    }

    Observer<LoginBean> observerBalance = loginBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        finish();
        if (loginBean == null)
            Toast.makeText(ActivityGamePlay.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
        else if (loginBean.getResponseCode() == 0) {
            LoginBean.ResponseData responseData = loginBean.getResponseData();
            if (responseData.getStatusCode() == 0) {
                Utils.hideKeyboard(ActivityGamePlay.this);
                PlayerData.getInstance().setLoginData(ActivityGamePlay.this, loginBean);
                PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
                Toast.makeText(ActivityGamePlay.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();
                refreshBalance();
            } else {
                if (Utils.checkForSessionExpiryActivity(ActivityGamePlay.this, loginBean.getResponseData().getStatusCode(), ActivityGamePlay.this))
                    return;
                String errorMsg = Utils.getResponseMessage(ActivityGamePlay.this, "RMS", responseData.getStatusCode());
                Toast.makeText(ActivityGamePlay.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        } else {
            String errorMsg = Utils.getResponseMessage(ActivityGamePlay.this, "RMS", loginBean.getResponseCode());
            Toast.makeText(ActivityGamePlay.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

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
