package com.skilrock.retailapp.portrait_draw_games.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.DrawGameData;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FiveByNinetySideBetActivity extends BaseActivity implements View.OnClickListener {

    //private DrawFetchGameDataResponseBean.ResponseData.GameRespVO GAME_DATA;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private HashMap<String, BetRespVO> MAP_SELECTED_SIDE_BET = new HashMap<>();
    public static long LAST_CLICK_TIME = 0;
    public static int CLICK_GAP = 200;
    private CircularTextView tvRed, tvGreen, tvBlue, tvMagenta, tvBrown, tvCyan, tvOrange, tvGrey, tvPink;
    private ArrayList<CircularTextView> listCircularTextView = new ArrayList<>();
    private LinearLayout llReset, llLessThan, llGreaterThan, llMoreEven, llMoreOdd, llColorBallContainer, llValueContainer, llEvenOddContainer, llParent, llPatternContainer;
    private LinearLayout llIncrease, llDecrease, llHill, llValley, llRandom;
    private RelativeLayout rlAddBet;
    private String displayName;
    private int gameCategory;
    //private ImageView ivIncreasing, ivDecreasing, ivHill, ivValley, ivRandom;
    private TextView tvColorHeading, tvValueHeading, tvEvenOddHeading, tvColorOdds, tvColorBetAmount, tvValueBetAmount, tvLessThan, tvLessThanOdds, tvPatternHeading, tvPatternBetAmount;
    private TextView tvGreaterThan, tvGreaterThanOdds, tvEvenOddBetAmount, tvMoreEven, tvMoreEvenOdds, tvMoreOdd, tvMoreOddOdds, tvNoOfBets, tvBetValue, tvIncrease;
    private TextView tvIncreaseOdds, tvDecrease, tvDecreaseOdds, tvHill, tvHillOdds, tvValley, tvValleyOdds, tvRandom, tvRandomOdds;
    private PanelBean panelColor;
    private PanelBean panelValue;
    private PanelBean panelEvenOdd;
    private PanelBean panelPattern;
    private int previousPanelSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            setContentView(R.layout.activity_five_by_ninety_side_bet_v2pro);
        else
            setContentView(R.layout.activity_five_by_ninety_side_bet);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        initializeWidgets();
        calculateTotalAmount();
        Log.d("TAg", "onCreate: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBalance();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //GAME_DATA               = getIntent().getExtras().getParcelable("GameResponse");
            MAP_SELECTED_SIDE_BET   = (HashMap<String, BetRespVO>) bundle.getSerializable("SelectedBet");
            MENU_BEAN               = bundle.getParcelable("MenuBean");
            displayName             = bundle.getString("Title");
            gameCategory            = bundle.getInt("GameCategory");
            previousPanelSize       = bundle.getInt("PanelSize");
        }
        else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            FiveByNinetySideBetActivity.this.finish();
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);

        tvTitle.setText(displayName);
        ivGameIcon.setVisibility(View.GONE);
    }

    private void initializeWidgets() {
        CircularTextView ctv1       = findViewById(R.id.circle_1);
        CircularTextView ctv2       = findViewById(R.id.circle_2);
        CircularTextView ctv3       = findViewById(R.id.circle_3);
        TextView tvBetValueTag      = findViewById(R.id.tvBetValueTag);
        tvColorOdds                 = findViewById(R.id.tvColorOdds);
        tvColorBetAmount            = findViewById(R.id.tvColorBetAmount);
        tvValueBetAmount            = findViewById(R.id.tvValueBetAmount);
        tvLessThan                  = findViewById(R.id.tvLessThan);
        tvLessThanOdds              = findViewById(R.id.tvLessThanOdds);
        tvGreaterThan               = findViewById(R.id.tvGreaterThan);
        tvGreaterThanOdds           = findViewById(R.id.tvGreaterThanOdds);
        tvEvenOddBetAmount          = findViewById(R.id.tvEvenOddBetAmount);
        tvMoreEven                  = findViewById(R.id.tvMoreEven);
        tvMoreEvenOdds              = findViewById(R.id.tvMoreEvenOdds);
        tvMoreOdd                   = findViewById(R.id.tvMoreOdd);
        tvMoreOddOdds               = findViewById(R.id.tvMoreOddOdds);
        tvNoOfBets                  = findViewById(R.id.tvNoOfBets);
        tvBetValue                  = findViewById(R.id.tvBetValue);
        tvDecrease                  = findViewById(R.id.tvDecrease);
        tvDecreaseOdds              = findViewById(R.id.tvDecreaseOdds);
        tvHill                      = findViewById(R.id.tvHill);
        tvHillOdds                  = findViewById(R.id.tvHillOdds);
        //ivHill                      = findViewById(R.id.ivHill);
        tvRed                       = findViewById(R.id.tvRed);
        tvGreen                     = findViewById(R.id.tvGreen);
        tvBlue                      = findViewById(R.id.tvBlue);
        tvMagenta                   = findViewById(R.id.tvMagenta);
        tvBrown                     = findViewById(R.id.tvBrown);
        tvCyan                      = findViewById(R.id.tvCyan);
        tvOrange                    = findViewById(R.id.tvOrange);
        tvGrey                      = findViewById(R.id.tvGrey);
        tvPink                      = findViewById(R.id.tvPink);
        tvIncreaseOdds              = findViewById(R.id.tvIncreaseOdds);
        tvIncrease                  = findViewById(R.id.tvIncrease);
        tvRandom                    = findViewById(R.id.tvRandom);
        tvRandomOdds                = findViewById(R.id.tvRandomOdds);
        tvPatternHeading            = findViewById(R.id.tvPatternHeading);
        tvPatternBetAmount          = findViewById(R.id.tvPatternBetAmount);
        tvValley                    = findViewById(R.id.tvValley);
        tvValleyOdds                = findViewById(R.id.tvValleyOdds);
        llDecrease                  = findViewById(R.id.llDecrease);
        llReset                     = findViewById(R.id.llReset);
        llLessThan                  = findViewById(R.id.llLessThan);
        llGreaterThan               = findViewById(R.id.llGreaterThan);
        llMoreEven                  = findViewById(R.id.llMoreEven);
        llMoreOdd                   = findViewById(R.id.llMoreOdd);
        llIncrease                  = findViewById(R.id.llIncrease);
        rlAddBet                    = findViewById(R.id.rlAddBet);
        tvColorHeading              = findViewById(R.id.tvColorHeading);
        tvEvenOddHeading            = findViewById(R.id.tvEvenOddHeading);
        tvValueHeading              = findViewById(R.id.tvValueHeading);
        /*ivIncreasing                = findViewById(R.id.ivIncreasing);
        ivDecreasing                = findViewById(R.id.ivDecreasing);
        ivValley                    = findViewById(R.id.ivValley);
        ivRandom                    = findViewById(R.id.ivRandom);*/
        llColorBallContainer        = findViewById(R.id.llColorBallContainer);
        llValueContainer            = findViewById(R.id.llValueContainer);
        llEvenOddContainer          = findViewById(R.id.llEvenOddContainer);
        llPatternContainer          = findViewById(R.id.llPatternContainer);
        llParent                    = findViewById(R.id.llParent);
        llHill                      = findViewById(R.id.llHill);
        llValley                    = findViewById(R.id.llValley);
        llRandom                    = findViewById(R.id.llRandom);

        ctv1.setStrokeColor("#e3e3e3");
        ctv2.setStrokeColor("#f5f5f5");
        ctv3.setStrokeColor("#e3e3e3");
        String tag = getString(R.string.bet_value) + " (" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(FiveByNinetySideBetActivity.this)) + ")";
        tvBetValueTag.setText(tag);
        addCircularTextViewsInList();
        setColors();
        setWidgetsOnClickListener();
        setGameValues();
    }

    private void addCircularTextViewsInList() {
        listCircularTextView.add(tvRed);
        listCircularTextView.add(tvGreen);
        listCircularTextView.add(tvBlue);
        listCircularTextView.add(tvMagenta);
        listCircularTextView.add(tvBrown);
        listCircularTextView.add(tvCyan);
        listCircularTextView.add(tvOrange);
        listCircularTextView.add(tvGrey);
        listCircularTextView.add(tvPink);
    }

    private void setColors() {
        for (CircularTextView circularTextView: listCircularTextView) {
            circularTextView.setStrokeWidth(4);
            circularTextView.setSolidColor("#ffffff");
        }

        tvRed.setStrokeColor(FiveByNinetyColors.getBallColor("RED"));
        tvPink.setStrokeColor(FiveByNinetyColors.getBallColor("PINK"));
        tvGreen.setStrokeColor(FiveByNinetyColors.getBallColor("GREEN"));
        tvBlue.setStrokeColor(FiveByNinetyColors.getBallColor("BLUE"));
        tvMagenta.setStrokeColor(FiveByNinetyColors.getBallColor("MAGENTA"));
        tvBrown.setStrokeColor(FiveByNinetyColors.getBallColor("BROWN"));
        tvCyan.setStrokeColor(FiveByNinetyColors.getBallColor("CYAN"));
        tvOrange.setStrokeColor(FiveByNinetyColors.getBallColor("ORANGE"));
        tvGrey.setStrokeColor(FiveByNinetyColors.getBallColor("GREY"));
    }

    private void setWidgetsOnClickListener() {
        llLessThan.setOnClickListener(this);
        llGreaterThan.setOnClickListener(this);
        llMoreEven.setOnClickListener(this);
        llMoreOdd.setOnClickListener(this);
        llIncrease.setOnClickListener(this);
        llDecrease.setOnClickListener(this);
        llValley.setOnClickListener(this);
        llHill.setOnClickListener(this);
        llRandom.setOnClickListener(this);
        rlAddBet.setOnClickListener(this);
        llReset.setOnClickListener(this);
        tvRed.setOnClickListener(this);
        tvPink.setOnClickListener(this);
        tvGreen.setOnClickListener(this);
        tvBlue.setOnClickListener(this);
        tvMagenta.setOnClickListener(this);
        tvBrown.setOnClickListener(this);
        tvCyan.setOnClickListener(this);
        tvOrange.setOnClickListener(this);
        tvGrey.setOnClickListener(this);
        llIncrease.setOnClickListener(this);
        llDecrease.setOnClickListener(this);
        llHill.setOnClickListener(this);
        llValley.setOnClickListener(this);
    }

    public void onClickBack(View view) {
        if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < CLICK_GAP) {
            return;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        FiveByNinetySideBetActivity.this.finish();
    }

    private void setGameValues() {
        BetRespVO betColor      = null;
        BetRespVO betValue      = null;
        BetRespVO betEvenOdd    = null;
        if (gameCategory == 1) {
            try { betColor      = DrawGameData.getSelectedBet("FirstBallColor"); } catch (Exception e) {e.printStackTrace();}
            try { betValue      = DrawGameData.getSelectedBet("5/90FirstBallSum"); } catch (Exception e) {e.printStackTrace();}
            try { betEvenOdd    = DrawGameData.getSelectedBet("5/90FirstBallOdd/Even"); } catch (Exception e) {e.printStackTrace();}
        }
        else if (gameCategory == 2) {
            try { betColor      = DrawGameData.getSelectedBet("LastBallColor"); } catch (Exception e) {e.printStackTrace();}
            try { betValue      = DrawGameData.getSelectedBet("5/90LastBallSum"); } catch (Exception e) {e.printStackTrace();}
            try { betEvenOdd    = DrawGameData.getSelectedBet("5/90LastBallOdd/Even"); } catch (Exception e) {e.printStackTrace();}
        }
        else if (gameCategory == 3) {
            try { betColor      = DrawGameData.getSelectedBet("FiveBallColor"); } catch (Exception e) {e.printStackTrace();}
            try { betValue      = DrawGameData.getSelectedBet("FirstFiveBallSum"); } catch (Exception e) {e.printStackTrace();}
            try { betEvenOdd    = DrawGameData.getSelectedBet("MoreOddEven"); } catch (Exception e) {e.printStackTrace();}
        }

        if (betColor != null) {
            try { tvColorHeading.setText(Objects.requireNonNull(betColor).getBetDispName()); } catch (Exception e) {e.printStackTrace();}
        }
        if (betValue != null) {
            try { tvValueHeading.setText(Objects.requireNonNull(betValue).getBetDispName()); } catch (Exception e) {e.printStackTrace();}
        }
        if (betEvenOdd != null) {
            try { tvEvenOddHeading.setText(Objects.requireNonNull(betEvenOdd).getBetDispName()); } catch (Exception e) {e.printStackTrace();}
        }

        if (gameCategory == 1)
            setAllParametersForFirstBall();
        else if (gameCategory == 2)
            setAllParametersForLastBall();
        else if (gameCategory == 3)
            setAllParametersForFirstFiveBall();

    }

    private void setAllParametersForFirstBall() {
        if (MAP_SELECTED_SIDE_BET.containsKey("FirstBallColor")) {
            llColorBallContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixFirstBallColor = MAP_SELECTED_SIDE_BET.get("FirstBallColor");
            //tvColorBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixFirstBallColor.getUnitPrice())));
            tvColorBetAmount.setText(getFormatterAmount(luckySixFirstBallColor));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixFirstBallColor.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("RED"))
                    tvRed.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("RED")));
                else if (model.getCode().equalsIgnoreCase("GREEN"))
                    tvGreen.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("GREEN")));
                else if (model.getCode().equalsIgnoreCase("BLUE"))
                    tvBlue.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("BLUE")));
                else if (model.getCode().equalsIgnoreCase("MAGENTA"))
                    tvMagenta.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("MAGENTA")));
                else if (model.getCode().equalsIgnoreCase("BROWN"))
                    tvBrown.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("BROWN")));
                else if (model.getCode().equalsIgnoreCase("CYAN"))
                    tvCyan.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("CYAN")));
                else if (model.getCode().equalsIgnoreCase("ORANGE"))
                    tvOrange.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("ORANGE")));
                else if (model.getCode().equalsIgnoreCase("GREY"))
                    tvGrey.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("GREY")));
                else if (model.getCode().equalsIgnoreCase("PINK"))
                    tvPink.setTag(getPanelBean(luckySixFirstBallColor, model, FiveByNinetyColors.getBallColor("PINK")));
            }

            for (CircularTextView circularTextView: listCircularTextView)
                if (circularTextView.getTag() == null)
                    circularTextView.setVisibility(View.GONE);
        }
        else
            llColorBallContainer.setVisibility(View.GONE);

        //--------------------------------------------------------------------------

        if (MAP_SELECTED_SIDE_BET.containsKey("5/90FirstBallSum")) {
            llValueContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixFirstBallValue = MAP_SELECTED_SIDE_BET.get("5/90FirstBallSum");
            //tvValueBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixFirstBallValue.getUnitPrice())));
            tvValueBetAmount.setText(getFormatterAmount(luckySixFirstBallValue));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixFirstBallValue.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("First_Ball_Lesser")) {
                    tvLessThan.setText(model.getName());
                    llLessThan.setTag(getPanelBean(luckySixFirstBallValue, model, null));
                }
                else if (model.getCode().equalsIgnoreCase("First_Ball_Greater")) {
                    tvGreaterThan.setText(model.getName());
                    llGreaterThan.setTag(getPanelBean(luckySixFirstBallValue, model, null));
                }
            }

            llLessThan.setVisibility(llLessThan.getTag() == null ? View.GONE : View.VISIBLE);
            llGreaterThan.setVisibility(llGreaterThan.getTag() == null ? View.GONE : View.VISIBLE);
        }
        else
            llValueContainer.setVisibility(View.GONE);

        //--------------------------------------------------------------------------

        if (MAP_SELECTED_SIDE_BET.containsKey("5/90FirstBallOdd/Even")) {
            llEvenOddContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixFirstBallEvenOdd = MAP_SELECTED_SIDE_BET.get("5/90FirstBallOdd/Even");
            //tvEvenOddBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixFirstBallEvenOdd.getUnitPrice())));
            tvEvenOddBetAmount.setText(getFormatterAmount(luckySixFirstBallEvenOdd));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixFirstBallEvenOdd.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("First_Ball_ODD")) {
                    tvMoreOdd.setText(model.getName());
                    llMoreOdd.setTag(getPanelBean(luckySixFirstBallEvenOdd, model, null));
                }
                else if (model.getCode().equalsIgnoreCase("First_Ball_EVEN")) {
                    tvMoreEven.setText(model.getName());
                    llMoreEven.setTag(getPanelBean(luckySixFirstBallEvenOdd, model, null));
                }
            }

            llMoreOdd.setVisibility(llLessThan.getTag() == null ? View.GONE : View.VISIBLE);
            llMoreEven.setVisibility(llGreaterThan.getTag() == null ? View.GONE : View.VISIBLE);
        }
        else
            llEvenOddContainer.setVisibility(View.GONE);

        llPatternContainer.setVisibility(View.GONE);

        //FirstBallColor
        //5/90FirstBallSum
        //5/90FirstBallOdd/Even
        tvColorOdds.setText(DrawGameData.getPriceForSideBet("FirstBallColor", this) != null ? DrawGameData.getPriceForSideBet("FirstBallColor", this) : getString(R.string.na));

        if (DrawGameData.getPriceForSideBet("5/90FirstBallSum", this) != null) {
            tvLessThanOdds.setText(DrawGameData.getPriceForSideBet("5/90FirstBallSum", this));
            tvGreaterThanOdds.setText(DrawGameData.getPriceForSideBet("5/90FirstBallSum", this));
        } else {
            tvLessThanOdds.setText(getString(R.string.na));
            tvGreaterThanOdds.setText(getString(R.string.na));
        }

        if (DrawGameData.getPriceForSideBet("5/90FirstBallOdd/Even", this) != null) {
            tvMoreEvenOdds.setText(DrawGameData.getPriceForSideBet("5/90FirstBallOdd/Even", this));
            tvMoreOddOdds.setText(DrawGameData.getPriceForSideBet("5/90FirstBallOdd/Even", this));
        } else {
            tvMoreEvenOdds.setText(getString(R.string.na));
            tvMoreOddOdds.setText(getString(R.string.na));
        }
    }

    private void setAllParametersForLastBall() {
        if (MAP_SELECTED_SIDE_BET.containsKey("LastBallColor")) {
            llColorBallContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixLastBallColor = MAP_SELECTED_SIDE_BET.get("LastBallColor");
            //tvColorBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixLastBallColor.getUnitPrice())));
            tvColorBetAmount.setText(getFormatterAmount(luckySixLastBallColor));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixLastBallColor.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("LASTRED"))
                    tvRed.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("RED")));
                else if (model.getCode().equalsIgnoreCase("LASTGREEN"))
                    tvGreen.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("GREEN")));
                else if (model.getCode().equalsIgnoreCase("LASTBLUE"))
                    tvBlue.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("BLUE")));
                else if (model.getCode().equalsIgnoreCase("LASTMAGENTA"))
                    tvMagenta.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("MAGENTA")));
                else if (model.getCode().equalsIgnoreCase("LASTBROWN"))
                    tvBrown.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("BROWN")));
                else if (model.getCode().equalsIgnoreCase("LASTCYAN"))
                    tvCyan.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("CYAN")));
                else if (model.getCode().equalsIgnoreCase("LASTORANGE"))
                    tvOrange.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("ORANGE")));
                else if (model.getCode().equalsIgnoreCase("LASTGREY"))
                    tvGrey.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("GREY")));
                else if (model.getCode().equalsIgnoreCase("LASTPINK"))
                    tvPink.setTag(getPanelBean(luckySixLastBallColor, model, FiveByNinetyColors.getBallColor("PINK")));

            }

            for (CircularTextView circularTextView: listCircularTextView)
                if (circularTextView.getTag() == null)
                    circularTextView.setVisibility(View.GONE);
        }
        else
            llColorBallContainer.setVisibility(View.GONE);

        //--------------------------------------------------------------------------

        if (MAP_SELECTED_SIDE_BET.containsKey("5/90LastBallSum")) {
            llValueContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixLastBallValue = MAP_SELECTED_SIDE_BET.get("5/90LastBallSum");
            //tvValueBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixLastBallValue.getUnitPrice())));
            tvValueBetAmount.setText(getFormatterAmount(luckySixLastBallValue));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixLastBallValue.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("Last_Ball_Lesser")) {
                    tvLessThan.setText(model.getName());
                    llLessThan.setTag(getPanelBean(luckySixLastBallValue, model, null));
                }
                else if (model.getCode().equalsIgnoreCase("Last_Ball_Greater")) {
                    tvGreaterThan.setText(model.getName());
                    llGreaterThan.setTag(getPanelBean(luckySixLastBallValue, model, null));
                }
            }

            llLessThan.setVisibility(llLessThan.getTag() == null ? View.GONE : View.VISIBLE);
            llGreaterThan.setVisibility(llGreaterThan.getTag() == null ? View.GONE : View.VISIBLE);
        }
        else
            llValueContainer.setVisibility(View.GONE);

        //--------------------------------------------------------------------------

        if (MAP_SELECTED_SIDE_BET.containsKey("5/90LastBallOdd/Even")) {
            llEvenOddContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixLastBallEvenOdd = MAP_SELECTED_SIDE_BET.get("5/90LastBallOdd/Even");
            //tvEvenOddBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixLastBallEvenOdd.getUnitPrice())));
            tvEvenOddBetAmount.setText(getFormatterAmount(luckySixLastBallEvenOdd));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixLastBallEvenOdd.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("Last_Ball_ODD")) {
                    tvMoreOdd.setText(model.getName());
                    llMoreOdd.setTag(getPanelBean(luckySixLastBallEvenOdd, model, null));
                }
                else if (model.getCode().equalsIgnoreCase("Last_Ball_EVEN")) {
                    tvMoreEven.setText(model.getName());
                    llMoreEven.setTag(getPanelBean(luckySixLastBallEvenOdd, model, null));
                }
            }

            llMoreOdd.setVisibility(llLessThan.getTag() == null ? View.GONE : View.VISIBLE);
            llMoreEven.setVisibility(llGreaterThan.getTag() == null ? View.GONE : View.VISIBLE);
        }
        else
            llEvenOddContainer.setVisibility(View.GONE);

        llPatternContainer.setVisibility(View.GONE);
        //LastBallColor
        //5/90LastBallSum
        //5/90LastBallOdd/Even
        tvColorOdds.setText(DrawGameData.getPriceForSideBet("LastBallColor", this) != null ? DrawGameData.getPriceForSideBet("LastBallColor", this) : getString(R.string.na));

        if (DrawGameData.getPriceForSideBet("5/90LastBallSum", this) != null) {
            tvLessThanOdds.setText(DrawGameData.getPriceForSideBet("5/90LastBallSum", this));
            tvGreaterThanOdds.setText(DrawGameData.getPriceForSideBet("5/90LastBallSum", this));
        } else {
            tvLessThanOdds.setText(getString(R.string.na));
            tvGreaterThanOdds.setText(getString(R.string.na));
        }

        if (DrawGameData.getPriceForSideBet("5/90LastBallOdd/Even", this) != null) {
            tvMoreEvenOdds.setText(DrawGameData.getPriceForSideBet("5/90LastBallOdd/Even", this));
            tvMoreOddOdds.setText(DrawGameData.getPriceForSideBet("5/90LastBallOdd/Even", this));
        } else {
            tvMoreEvenOdds.setText(getString(R.string.na));
            tvMoreOddOdds.setText(getString(R.string.na));
        }
    }

    private void setAllParametersForFirstFiveBall() {
        if (MAP_SELECTED_SIDE_BET.containsKey("FiveBallColor")) {
            llColorBallContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixFirstFiveBallColor = MAP_SELECTED_SIDE_BET.get("FiveBallColor");
            //tvColorBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixFirstFiveBallColor.getUnitPrice())));
            tvColorBetAmount.setText(getFormatterAmount(luckySixFirstFiveBallColor));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixFirstFiveBallColor.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("RED"))
                    tvRed.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("RED")));
                else if (model.getCode().equalsIgnoreCase("GREEN"))
                    tvGreen.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("GREEN")));
                else if (model.getCode().equalsIgnoreCase("BLUE"))
                    tvBlue.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("BLUE")));
                else if (model.getCode().equalsIgnoreCase("MAGENTA"))
                    tvMagenta.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("MAGENTA")));
                else if (model.getCode().equalsIgnoreCase("BROWN"))
                    tvBrown.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("BROWN")));
                else if (model.getCode().equalsIgnoreCase("CYAN"))
                    tvCyan.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("CYAN")));
                else if (model.getCode().equalsIgnoreCase("ORANGE"))
                    tvOrange.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("ORANGE")));
                else if (model.getCode().equalsIgnoreCase("GREY"))
                    tvGrey.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("GREY")));
                else if (model.getCode().equalsIgnoreCase("PINK"))
                    tvPink.setTag(getPanelBean(luckySixFirstFiveBallColor, model, FiveByNinetyColors.getBallColor("PINK")));
            }

            for (CircularTextView circularTextView: listCircularTextView)
                if (circularTextView.getTag() == null)
                    circularTextView.setVisibility(View.GONE);
        }
        else
            llColorBallContainer.setVisibility(View.GONE);

        //--------------------------------------------------------------------------

        if (MAP_SELECTED_SIDE_BET.containsKey("FirstFiveBallSum")) {
            llValueContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixFirstFiveBallValue = MAP_SELECTED_SIDE_BET.get("FirstFiveBallSum");
            //tvValueBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixFirstFiveBallValue.getUnitPrice())));
            tvValueBetAmount.setText(getFormatterAmount(luckySixFirstFiveBallValue));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixFirstFiveBallValue.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("First_Five_Lesser")) {
                    tvLessThan.setText(model.getName());
                    llLessThan.setTag(getPanelBean(luckySixFirstFiveBallValue, model, null));
                }
                else if (model.getCode().equalsIgnoreCase("First_Five_Greater")) {
                    tvGreaterThan.setText(model.getName());
                    llGreaterThan.setTag(getPanelBean(luckySixFirstFiveBallValue, model, null));
                }
            }

            llLessThan.setVisibility(llLessThan.getTag() == null ? View.GONE : View.VISIBLE);
            llGreaterThan.setVisibility(llGreaterThan.getTag() == null ? View.GONE : View.VISIBLE);
        }
        else
            llValueContainer.setVisibility(View.GONE);

        //--------------------------------------------------------------------------

        if (MAP_SELECTED_SIDE_BET.containsKey("MoreOddEven")) {
            llEvenOddContainer.setVisibility(View.VISIBLE);
            BetRespVO luckySixFirstFiveBallEvenOdd = MAP_SELECTED_SIDE_BET.get("MoreOddEven");
            //tvEvenOddBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(luckySixFirstFiveBallEvenOdd.getUnitPrice())));
            tvEvenOddBetAmount.setText(getFormatterAmount(luckySixFirstFiveBallEvenOdd));
            ArrayList<BetRespVO.PickTypeData.PickType> list = luckySixFirstFiveBallEvenOdd.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("More_ODD")) {
                    tvMoreOdd.setText(model.getName());
                    llMoreOdd.setTag(getPanelBean(luckySixFirstFiveBallEvenOdd, model, null));
                }
                else if (model.getCode().equalsIgnoreCase("More_EVEN")) {
                    tvMoreEven.setText(model.getName());
                    llMoreEven.setTag(getPanelBean(luckySixFirstFiveBallEvenOdd, model, null));
                }
            }

            llMoreOdd.setVisibility(llLessThan.getTag() == null ? View.GONE : View.VISIBLE);
            llMoreEven.setVisibility(llGreaterThan.getTag() == null ? View.GONE : View.VISIBLE);
        }
        else
            llEvenOddContainer.setVisibility(View.GONE);

        //--------------------------------------------------------------------------

        if (MAP_SELECTED_SIDE_BET.containsKey("Increasing/Decreasing")) {
            llPatternContainer.setVisibility(View.VISIBLE);
            BetRespVO pattern = MAP_SELECTED_SIDE_BET.get("Increasing/Decreasing");
            //tvPatternBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(pattern.getUnitPrice())));
            tvPatternBetAmount.setText(getFormatterAmount(pattern));
            ArrayList<BetRespVO.PickTypeData.PickType> list = pattern.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("Increasing")) {
                    tvIncrease.setText(model.getName());
                    llIncrease.setTag(getPanelBean(pattern, model, null));
                }
                else if (model.getCode().equalsIgnoreCase("Decreasing")) {
                    tvDecrease.setText(model.getName());
                    llDecrease.setTag(getPanelBean(pattern, model, null));
                }
            }

            llMoreOdd.setVisibility(llLessThan.getTag() == null ? View.GONE : View.VISIBLE);
            llMoreEven.setVisibility(llGreaterThan.getTag() == null ? View.GONE : View.VISIBLE);
        }
        else {
            llIncrease.setVisibility(View.GONE);
            llDecrease.setVisibility(View.GONE);
        }

        if (MAP_SELECTED_SIDE_BET.containsKey("Hill/Valley")) {
            llPatternContainer.setVisibility(View.VISIBLE);
            BetRespVO pattern = MAP_SELECTED_SIDE_BET.get("Hill/Valley");
            //tvPatternBetAmount.setText(String.format("Bet $%s", Objects.requireNonNull(pattern.getUnitPrice())));
            tvPatternBetAmount.setText(getFormatterAmount(pattern));
            ArrayList<BetRespVO.PickTypeData.PickType> list = pattern.getPickTypeData().getPickType();
            for (BetRespVO.PickTypeData.PickType model: list) {
                if (model.getCode().equalsIgnoreCase("HillPattern")) {
                    tvHill.setText(model.getName());
                    llHill.setTag(getPanelBean(pattern, model, null));
                }
                else if (model.getCode().equalsIgnoreCase("Valley")) {
                    tvValley.setText(model.getName());
                    llValley.setTag(getPanelBean(pattern, model, null));
                }
            }

            llMoreOdd.setVisibility(llLessThan.getTag() == null ? View.GONE : View.VISIBLE);
            llMoreEven.setVisibility(llGreaterThan.getTag() == null ? View.GONE : View.VISIBLE);
        }
        else {
            llHill.setVisibility(View.GONE);
            llValley.setVisibility(View.GONE);
        }

        if (!MAP_SELECTED_SIDE_BET.containsKey("Increasing/Decreasing") && !MAP_SELECTED_SIDE_BET.containsKey("Hill/Valley")) {
            llPatternContainer.setVisibility(View.GONE);
        }

        //FiveBallColor
        //FirstFiveBallSum
        //MoreOddEven
        //5/90Pattern
        tvColorOdds.setText(DrawGameData.getPriceForSideBet("FiveBallColor", this) != null ? DrawGameData.getPriceForSideBet("FiveBallColor", this) : getString(R.string.na));

        if (DrawGameData.getPriceForSideBet("FirstFiveBallSum", this) != null) {
            tvLessThanOdds.setText(DrawGameData.getPriceForSideBet("FirstFiveBallSum", this));
            tvGreaterThanOdds.setText(DrawGameData.getPriceForSideBet("FirstFiveBallSum", this));
        } else {
            tvLessThanOdds.setText(getString(R.string.na));
            tvGreaterThanOdds.setText(getString(R.string.na));
        }

        if (DrawGameData.getPriceForSideBet("MoreOddEven", this) != null) {
            tvMoreEvenOdds.setText(DrawGameData.getPriceForSideBet("MoreOddEven", this));
            tvMoreOddOdds.setText(DrawGameData.getPriceForSideBet("MoreOddEven", this));
        } else {
            tvMoreEvenOdds.setText(getString(R.string.na));
            tvMoreOddOdds.setText(getString(R.string.na));
        }

        if (DrawGameData.getPriceForSideBet("Increasing/Decreasing", this) != null) {
            tvIncreaseOdds.setText(DrawGameData.getPriceForSideBet("Increasing/Decreasing", this));
            tvDecreaseOdds.setText(DrawGameData.getPriceForSideBet("Increasing/Decreasing", this));
        } else {
            tvIncreaseOdds.setText(getString(R.string.na));
            tvDecreaseOdds.setText(getString(R.string.na));
        }

        if (DrawGameData.getPriceForSideBet("Hill/Valley", this) != null) {
            tvHillOdds.setText(DrawGameData.getPriceForSideBet("Hill/Valley", this));
            tvValleyOdds.setText(DrawGameData.getPriceForSideBet("Hill/Valley", this));
        } else {
            tvHillOdds.setText(getString(R.string.na));
            tvValleyOdds.setText(getString(R.string.na));
        }
    }

    private PanelBean getPanelBean(BetRespVO selectedBetTypeData, BetRespVO.PickTypeData.PickType selectedPickTypeObject, String colorCode) {
        PanelBean model = new PanelBean();
        model.setPickedValues(selectedPickTypeObject.getRange().get(0).getPickValue());
        //model.setTotalNumbers(1);

        double selectedPrice = selectedBetTypeData.getUnitPrice();
        model.setGameName(DrawGameData.getSelectedGame().getGameName());
        model.setAmount((int) selectedPrice);
        model.setWinMode(selectedBetTypeData.getWinMode());
        model.setBetName(selectedBetTypeData.getBetDispName());
        model.setPickName(selectedPickTypeObject.getName());
        model.setBetCode(selectedBetTypeData.getBetCode());
        model.setPickCode(selectedPickTypeObject.getCode());
        model.setPickConfig(selectedPickTypeObject.getRange().get(0).getPickConfig());
        model.setBetAmountMultiple(1);
        model.setSelectedBetAmount((int) selectedPrice);
        model.setNumberOfDraws(1);
        model.setNumberOfLines(1);
        model.setQuickPick(false);
        model.setQpPreGenerated(false);
        model.setMainBet(false);
        model.setColorCode(colorCode);

        return model;
    }

    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < CLICK_GAP) {
            return;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();

        switch (view.getId()) {
            case R.id.llReset:
                resetGame();
                break;
            case R.id.rlAddBet:
                addBet();
                break;
            case R.id.llLessThan:
                Log.d("TAg", "llLessThan ---> : ");
                if (llLessThan.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.rounded_red_outline_low_radius).getConstantState())) {
                    /*if(panelValue == null && !checkLimit())
                        return;*/
                    Log.d("TAg", "llLessThan ---> If : ");
                    llLessThan.setBackground(getResources().getDrawable(R.drawable.solid_rounded_red_outline_low_radius));
                    tvLessThan.setTextColor(Color.parseColor("#FFFFFF"));
                    tvLessThanOdds.setTextColor(Color.parseColor("#ffcf00"));
                    panelValue = (PanelBean) llLessThan.getTag();

                    llGreaterThan.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
                    tvGreaterThan.setTextColor(Color.parseColor("#606060"));
                    tvGreaterThanOdds.setTextColor(Color.parseColor("#ce1127"));
                }
                else if (llLessThan.getBackground().getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.solid_rounded_red_outline_low_radius).getConstantState())) {
                    Log.d("TAg", "llLessThan ---> Else If : ");
                    llLessThan.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
                    tvLessThan.setTextColor(Color.parseColor("#606060"));
                    tvLessThanOdds.setTextColor(Color.parseColor("#ce1127"));
                    panelValue = null;
                }
                break;
            case R.id.llGreaterThan:
                if (llGreaterThan.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.rounded_red_outline_low_radius).getConstantState())) {
                    if(panelValue == null && !checkLimit())
                        return;
                    llGreaterThan.setBackground(getResources().getDrawable(R.drawable.solid_rounded_red_outline_low_radius));
                    tvGreaterThan.setTextColor(Color.parseColor("#FFFFFF"));
                    tvGreaterThanOdds.setTextColor(Color.parseColor("#ffcf00"));
                    panelValue = (PanelBean) llGreaterThan.getTag();

                    llLessThan.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
                    tvLessThan.setTextColor(Color.parseColor("#606060"));
                    tvLessThanOdds.setTextColor(Color.parseColor("#ce1127"));
                }
                else if (llGreaterThan.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.solid_rounded_red_outline_low_radius).getConstantState())) {
                    llGreaterThan.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
                    tvGreaterThan.setTextColor(Color.parseColor("#606060"));
                    tvGreaterThanOdds.setTextColor(Color.parseColor("#ce1127"));
                    panelValue = null;
                }
                break;
            case R.id.llMoreEven:
                if (llMoreEven.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.rounded_red_outline_low_radius).getConstantState())) {
                    if(panelEvenOdd == null && !checkLimit())
                        return;
                    llMoreEven.setBackground(getResources().getDrawable(R.drawable.solid_rounded_red_outline_low_radius));
                    tvMoreEven.setTextColor(Color.parseColor("#FFFFFF"));
                    tvMoreEvenOdds.setTextColor(Color.parseColor("#ffcf00"));
                    panelEvenOdd = (PanelBean) llMoreEven.getTag();

                    llMoreOdd.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
                    tvMoreOdd.setTextColor(Color.parseColor("#606060"));
                    tvMoreOddOdds.setTextColor(Color.parseColor("#ce1127"));
                }
                else if (llMoreEven.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.solid_rounded_red_outline_low_radius).getConstantState())) {
                    llMoreEven.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
                    tvMoreEven.setTextColor(Color.parseColor("#606060"));
                    tvMoreEvenOdds.setTextColor(Color.parseColor("#ce1127"));
                    panelEvenOdd = null;
                }
                break;
            case R.id.llMoreOdd:
                if (llMoreOdd.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.rounded_red_outline_low_radius).getConstantState())) {
                    if(panelEvenOdd == null && !checkLimit())
                        return;
                    llMoreOdd.setBackground(getResources().getDrawable(R.drawable.solid_rounded_red_outline_low_radius));
                    tvMoreOdd.setTextColor(Color.parseColor("#FFFFFF"));
                    tvMoreOddOdds.setTextColor(Color.parseColor("#ffcf00"));
                    panelEvenOdd = (PanelBean) llMoreOdd.getTag();

                    llMoreEven.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
                    tvMoreEven.setTextColor(Color.parseColor("#606060"));
                    tvMoreEvenOdds.setTextColor(Color.parseColor("#ce1127"));
                }
                else if (llMoreOdd.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.solid_rounded_red_outline_low_radius).getConstantState())) {
                    llMoreOdd.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
                    tvMoreOdd.setTextColor(Color.parseColor("#606060"));
                    tvMoreOddOdds.setTextColor(Color.parseColor("#ce1127"));
                    panelEvenOdd = null;
                }
                break;
            case R.id.llIncrease:
                onClickPatternIncrease();
                break;
            case R.id.llDecrease:
                onClickPatternDecrease();
                break;
            case R.id.llHill:
                onClickPatternHill();
                break;
            case R.id.llValley:
                onClickPatternValley();
                break;
            case R.id.tvPink:
                resetAllCircularTextViews(R.id.tvPink);
                if (tvPink.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvPink.setSolidColor(FiveByNinetyColors.getBallColor("PINK"));
                    tvPink.setText("✔\nPink\n ");
                    panelColor = (PanelBean) tvPink.getTag();
                }
                else {
                    tvPink.setSolidColor("#FFFFFF");
                    tvPink.setText("");
                    panelColor = null;
                }
                break;
            case R.id.tvRed:
                resetAllCircularTextViews(R.id.tvRed);
                if (tvRed.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvRed.setSolidColor(FiveByNinetyColors.getBallColor("RED"));
                    tvRed.setText("✔\nRed\n ");
                    panelColor = (PanelBean) tvRed.getTag();
                }
                else {
                    tvRed.setSolidColor("#FFFFFF");
                    tvRed.setText("");
                    panelColor = null;
                }
                break;
            case R.id.tvGreen:
                resetAllCircularTextViews(R.id.tvGreen);
                if (tvGreen.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvGreen.setSolidColor(FiveByNinetyColors.getBallColor("GREEN"));
                    tvGreen.setText("✔\nGreen\n ");
                    panelColor = (PanelBean) tvGreen.getTag();
                }
                else {
                    tvGreen.setSolidColor("#FFFFFF");
                    tvGreen.setText("");
                    panelColor = null;
                }
                break;
            case R.id.tvBlue:
                resetAllCircularTextViews(R.id.tvBlue);
                if (tvBlue.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvBlue.setSolidColor(FiveByNinetyColors.getBallColor("BLUE"));
                    tvBlue.setText("✔\nBlue\n ");
                    panelColor = (PanelBean) tvBlue.getTag();
                }
                else {
                    tvBlue.setSolidColor("#FFFFFF");
                    tvBlue.setText("");
                    panelColor = null;
                }
                break;
            case R.id.tvMagenta:
                resetAllCircularTextViews(R.id.tvMagenta);
                if (tvMagenta.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvMagenta.setSolidColor(FiveByNinetyColors.getBallColor("MAGENTA"));
                    tvMagenta.setText("✔\nMagenta");
                    panelColor = (PanelBean) tvMagenta.getTag();
                }
                else {
                    tvMagenta.setSolidColor("#FFFFFF");
                    tvMagenta.setText("");
                    panelColor = null;
                }
                break;
            case R.id.tvBrown:
                resetAllCircularTextViews(R.id.tvBrown);
                if (tvBrown.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvBrown.setSolidColor(FiveByNinetyColors.getBallColor("BROWN"));
                    tvBrown.setText("✔\nBrown");
                    panelColor = (PanelBean) tvBrown.getTag();
                }
                else {
                    tvBrown.setSolidColor("#FFFFFF");
                    tvBrown.setText("");
                    panelColor = null;
                }
                break;
            case R.id.tvCyan:
                resetAllCircularTextViews(R.id.tvCyan);
                if (tvCyan.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvCyan.setSolidColor(FiveByNinetyColors.getBallColor("CYAN"));
                    tvCyan.setText("✔\nCyan");
                    panelColor = (PanelBean) tvCyan.getTag();
                }
                else {
                    tvCyan.setSolidColor("#FFFFFF");
                    tvCyan.setText("");
                    panelColor = null;
                }
                break;
            case R.id.tvOrange:
                resetAllCircularTextViews(R.id.tvOrange);
                if (tvOrange.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvOrange.setSolidColor(FiveByNinetyColors.getBallColor("ORANGE"));
                    tvOrange.setText("✔\nOrange");
                    panelColor = (PanelBean) tvOrange.getTag();
                }
                else {
                    tvOrange.setSolidColor("#FFFFFF");
                    tvOrange.setText("");
                    panelColor = null;
                }
                break;
            case R.id.tvGrey:
                resetAllCircularTextViews(R.id.tvGrey);
                if (tvGrey.getText().toString().isEmpty()) {
                    if(panelColor == null && !checkLimit())
                        return;
                    tvGrey.setSolidColor(FiveByNinetyColors.getBallColor("GREY"));
                    tvGrey.setText("✔\nGrey");
                    panelColor = (PanelBean) tvGrey.getTag();
                }
                else {
                    tvGrey.setSolidColor("#FFFFFF");
                    tvGrey.setText("");
                    panelColor = null;
                }
                break;
        }
        calculateTotalAmount();
        setNumberOfBets();
    }

    private void calculateTotalAmount() {
        double amount = 0;
        if (panelColor != null)
            amount = amount + panelColor.getAmount();
        if (panelEvenOdd != null)
            amount = amount + panelEvenOdd.getAmount();
        if (panelValue != null)
            amount = amount + panelValue.getAmount();
        if (panelPattern != null)
            amount = amount + panelPattern.getAmount();
        //String strAmount = "$" + amount;
        int amt = (int) amount;
        //String strAmount = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(FiveByNinetySideBetActivity.this)) + amt;
        String strAmount = String.valueOf(amt);
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount), Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE()));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(amt);
        }
        tvBetValue.setText(formattedAmount);
    }

    private void setNumberOfBets() {
        int count = 0;
        if (panelColor != null)
            count++;
        if (panelEvenOdd != null)
            count++;
        if (panelValue != null)
            count++;
        if (panelPattern != null)
            count++;
        String strCount = "" + count;
        tvNoOfBets.setText(strCount);
    }

    private void resetAllCircularTextViews(int id) {
        for (CircularTextView circularTextView: listCircularTextView) {
            if (circularTextView.getId() != id) {
                circularTextView.setText("");
                circularTextView.setSolidColor("#ffffff");
            }
        }
    }

    private void onClickPatternIncrease() {
        if (llIncrease.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.rounded_red_outline_low_radius).getConstantState())) {
            if(panelPattern == null && !checkLimit())
                return;
            llIncrease.setBackground(getResources().getDrawable(R.drawable.solid_rounded_red_outline_low_radius));
            tvIncrease.setTextColor(Color.parseColor("#FFFFFF"));
            tvIncreaseOdds.setTextColor(Color.parseColor("#ffcf00"));
            panelPattern = (PanelBean) llIncrease.getTag();

            llDecrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvDecrease.setTextColor(Color.parseColor("#606060"));
            tvDecreaseOdds.setTextColor(Color.parseColor("#ce1127"));

            llHill.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvHill.setTextColor(Color.parseColor("#606060"));
            tvHillOdds.setTextColor(Color.parseColor("#ce1127"));

            llValley.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvValley.setTextColor(Color.parseColor("#606060"));
            tvValleyOdds.setTextColor(Color.parseColor("#ce1127"));
        }
        else if (llIncrease.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.solid_rounded_red_outline_low_radius).getConstantState())) {
            llIncrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvIncrease.setTextColor(Color.parseColor("#606060"));
            tvIncreaseOdds.setTextColor(Color.parseColor("#ce1127"));
            panelPattern = null;
        }
    }

    private void onClickPatternDecrease() {
        if (llDecrease.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.rounded_red_outline_low_radius).getConstantState())) {
            if(panelPattern == null && !checkLimit())
                return;
            llDecrease.setBackground(getResources().getDrawable(R.drawable.solid_rounded_red_outline_low_radius));
            tvDecrease.setTextColor(Color.parseColor("#FFFFFF"));
            tvDecreaseOdds.setTextColor(Color.parseColor("#ffcf00"));
            panelPattern = (PanelBean) llDecrease.getTag();

            llIncrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvIncrease.setTextColor(Color.parseColor("#606060"));
            tvIncreaseOdds.setTextColor(Color.parseColor("#ce1127"));

            llHill.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvHill.setTextColor(Color.parseColor("#606060"));
            tvHillOdds.setTextColor(Color.parseColor("#ce1127"));

            llValley.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvValley.setTextColor(Color.parseColor("#606060"));
            tvValleyOdds.setTextColor(Color.parseColor("#ce1127"));
        }
        else if (llDecrease.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.solid_rounded_red_outline_low_radius).getConstantState())) {
            llDecrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvDecrease.setTextColor(Color.parseColor("#606060"));
            tvDecreaseOdds.setTextColor(Color.parseColor("#ce1127"));
            panelPattern = null;
        }
    }

    private void onClickPatternHill() {
        if (llHill.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.rounded_red_outline_low_radius).getConstantState())) {
            if(panelPattern == null && !checkLimit())
                return;
            llHill.setBackground(getResources().getDrawable(R.drawable.solid_rounded_red_outline_low_radius));
            tvHill.setTextColor(Color.parseColor("#FFFFFF"));
            tvHillOdds.setTextColor(Color.parseColor("#ffcf00"));
            panelPattern = (PanelBean) llHill.getTag();

            llIncrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvIncrease.setTextColor(Color.parseColor("#606060"));
            tvIncreaseOdds.setTextColor(Color.parseColor("#ce1127"));

            llDecrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvDecrease.setTextColor(Color.parseColor("#606060"));
            tvDecreaseOdds.setTextColor(Color.parseColor("#ce1127"));

            llValley.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvValley.setTextColor(Color.parseColor("#606060"));
            tvValleyOdds.setTextColor(Color.parseColor("#ce1127"));
        }
        else if (llHill.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.solid_rounded_red_outline_low_radius).getConstantState())) {
            llHill.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvHill.setTextColor(Color.parseColor("#606060"));
            tvHillOdds.setTextColor(Color.parseColor("#ce1127"));
            panelPattern = null;
        }
    }

    private void onClickPatternValley() {
        if (llValley.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.rounded_red_outline_low_radius).getConstantState())) {
            if(panelPattern == null && !checkLimit())
                return;
            llValley.setBackground(getResources().getDrawable(R.drawable.solid_rounded_red_outline_low_radius));
            tvValley.setTextColor(Color.parseColor("#FFFFFF"));
            tvValleyOdds.setTextColor(Color.parseColor("#ffcf00"));
            panelPattern = (PanelBean) llValley.getTag();

            llIncrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvIncrease.setTextColor(Color.parseColor("#606060"));
            tvIncreaseOdds.setTextColor(Color.parseColor("#ce1127"));

            llDecrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvDecrease.setTextColor(Color.parseColor("#606060"));
            tvDecreaseOdds.setTextColor(Color.parseColor("#ce1127"));

            llHill.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvHill.setTextColor(Color.parseColor("#606060"));
            tvHillOdds.setTextColor(Color.parseColor("#ce1127"));
        }
        else if (llValley.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.solid_rounded_red_outline_low_radius).getConstantState())) {
            llValley.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
            tvValley.setTextColor(Color.parseColor("#606060"));
            tvValleyOdds.setTextColor(Color.parseColor("#ce1127"));
            panelPattern = null;
        }
    }

    private void resetGame() {
        panelEvenOdd    = null;
        panelValue      = null;
        panelColor      = null;
        panelPattern    = null;

        for (CircularTextView circularTextView: listCircularTextView) {
            circularTextView.setText("");
            circularTextView.setSolidColor("#ffffff");
        }

        llLessThan.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
        tvLessThan.setTextColor(Color.parseColor("#606060"));
        tvLessThanOdds.setTextColor(Color.parseColor("#ce1127"));

        llGreaterThan.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
        tvGreaterThan.setTextColor(Color.parseColor("#606060"));
        tvGreaterThanOdds.setTextColor(Color.parseColor("#ce1127"));

        llMoreEven.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
        tvMoreEven.setTextColor(Color.parseColor("#606060"));
        tvMoreEvenOdds.setTextColor(Color.parseColor("#ce1127"));

        llMoreOdd.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
        tvMoreOdd.setTextColor(Color.parseColor("#606060"));
        tvMoreOddOdds.setTextColor(Color.parseColor("#ce1127"));

        llIncrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
        tvIncrease.setTextColor(Color.parseColor("#606060"));
        tvIncreaseOdds.setTextColor(Color.parseColor("#ce1127"));

        llDecrease.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
        tvDecrease.setTextColor(Color.parseColor("#606060"));
        tvDecreaseOdds.setTextColor(Color.parseColor("#ce1127"));

        llHill.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
        tvHill.setTextColor(Color.parseColor("#606060"));
        tvHillOdds.setTextColor(Color.parseColor("#ce1127"));

        llValley.setBackground(getResources().getDrawable(R.drawable.rounded_red_outline_low_radius));
        tvValley.setTextColor(Color.parseColor("#606060"));
        tvValleyOdds.setTextColor(Color.parseColor("#ce1127"));

        calculateTotalAmount();
    }

    private void addBet() {
        if (panelColor == null && panelValue == null && panelEvenOdd == null && panelPattern == null) {
            showToast(getString(R.string.no_bet_selected));
            return;
        }
        ArrayList<PanelBean> list = new ArrayList<>();
        if (panelColor != null)
            list.add(panelColor);
        if (panelValue != null)
            list.add(panelValue);
        if (panelEvenOdd != null)
            list.add(panelEvenOdd);
        if (panelPattern != null)
            list.add(panelPattern);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("ListBetData", list);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void showToast(String text) {
        try {
            Snackbar snackbar = Snackbar.make(llParent, text, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkLimit() {
        int count = 0;
        if (panelEvenOdd != null)
            count++;
        if (panelValue != null)
            count++;
        if (panelColor != null)
            count++;
        if (panelPattern != null)
            count++;

        if ((count + previousPanelSize) >= DrawGameData.getSelectedGame().getMaxPanelAllowed()) {
            Utils.showRedToast(FiveByNinetySideBetActivity.this, getString(R.string.you_have_reached_max_panel_limit));
            return false;
        }
        return true;
    }

    private String getFormatterAmount(BetRespVO betRespVO) {
        if (betRespVO != null) {
            int amt = betRespVO.getUnitPrice().intValue();
            return getString(R.string.bet) + " " + amt + " " +  Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(FiveByNinetySideBetActivity.this));
        }
        return getString(R.string.bet) + " 0";
    }

}
