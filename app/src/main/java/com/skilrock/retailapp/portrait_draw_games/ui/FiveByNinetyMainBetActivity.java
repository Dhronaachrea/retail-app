package com.skilrock.retailapp.portrait_draw_games.ui;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.BetAmountDialog;
import com.skilrock.retailapp.interfaces.BallClickListener;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.BankerBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetyBetAmountBean;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.drawgames.QuickPickBean;
import com.skilrock.retailapp.models.drawgames.QuickPickRequestBean;
import com.skilrock.retailapp.models.drawgames.QuickPickResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.portrait_draw_games.adapter.BetAmountPortraitFiveByNinetyAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.ColorBarPortraitAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.LowerLinePortraitAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.NumbersPortraitFiveByNinetyAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.PickTypePortraitFiveByNinetyAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.QpNumbersPortraitAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.UpperLinePortraitAdapter;
import com.skilrock.retailapp.portrait_draw_games.viewmodels.LuckySixPortraitViewModel;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.DrawGameData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.SpacesItemDecoration;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class FiveByNinetyMainBetActivity extends BaseActivity implements View.OnClickListener, PickTypePortraitFiveByNinetyAdapter.OnPickTypeClickListener, BetAmountPortraitFiveByNinetyAdapter.OnBetAmountClickListener, QpNumbersPortraitAdapter.OnQpNumberSelection, BetAmountDialog.OnAmountUpdatedListener {

    //private DrawFetchGameDataResponseBean.ResponseData.GameRespVO GAME_DATA;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO SELECTED_BET_TYPE_DATA;
    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType SELECTED_PICK_TYPE_OBJECT;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypeWithQpList;
    private RecyclerView rvColorBar, rvNumbers, rvPickType, rvBetAmount, rvQpNumbers, rvUpperLine, rvLowerLine;
    private TextView tvBetValue, tvInstructions, tvQpName, tvQpRange, tvBetValueTag, tvBetTypeOther, tvBetType5, tvBetType4, tvBetType3, tvBetType2, tvBetType1;
    private LinearLayout llBottomBar, llParent, llQpSelectionBar, llBankerBar, llUpperLine, llLowerLine, llOther;
    private int MAX_SELECTION_LIMIT = 0, MIN_SELECTION_LIMIT= 0, SELECTED_BET_AMOUNT;
    private ArrayList<String> LIST_SELECTED_NUMBERS = new ArrayList<>();
    private ArrayList<String> LIST_SELECTED_UPPER_LINE_NUMBERS = new ArrayList<>();
    private ArrayList<String> LIST_SELECTED_LOWER_LINE_NUMBERS = new ArrayList<>();
    private NumbersPortraitFiveByNinetyAdapter numbersAdapter;
    private boolean isFirstTimeNumberAdapter = true, isFirstTimePickTypeAdapter = true, isFirstTimeBetAmountAdapter = true;
    private PickTypePortraitFiveByNinetyAdapter pickTypeAdapter;
    private HashMap<String, String> MAP_NUMBER_COLOR = new HashMap<>();
    private LuckySixPortraitViewModel viewModel;
    public static long LAST_CLICK_TIME = 0;
    public static int CLICK_GAP = 200;
    private ArrayList<FiveByNinetyBetAmountBean> LIST_BET_AMOUNT = new ArrayList<>();
    private BetAmountDialog betAmountDialog;
    private boolean isOtherAmountAvailable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            setContentView(R.layout.activity_five_by_ninety_v2pro);
        else
            setContentView(R.layout.activity_five_by_ninety);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        initializeWidgets();
        setColorBarAdapter();
        setNumbersAdapter(null, false, null, null);
        setPickTypeAdapter(SELECTED_BET_TYPE_DATA);
        setBetAmountAdapter(SELECTED_BET_TYPE_DATA.getUnitPrice(), SELECTED_BET_TYPE_DATA.getMaxBetAmtMul());
        betValueCalculation();
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
            SELECTED_BET_TYPE_DATA  = getIntent().getExtras().getParcelable("SelectedBet");
            MENU_BEAN               = bundle.getParcelable("MenuBean");
            setColorMap();
        }
        else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            FiveByNinetyMainBetActivity.this.finish();
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);

        try {
            String displayName = SELECTED_BET_TYPE_DATA.getBetDispName();
            if (displayName.contains("Lucky 6"))
                displayName = displayName.replace("Lucky 6", "").trim();
            tvTitle.setText(displayName);
        } catch (Exception e) {
            e.printStackTrace();
            tvTitle.setText(SELECTED_BET_TYPE_DATA.getBetDispName());
        }

        ivGameIcon.setVisibility(View.GONE);
    }

    private void initializeWidgets() {
        viewModel                   = ViewModelProviders.of(this).get(LuckySixPortraitViewModel.class);
        LinearLayout llReset        = findViewById(R.id.llReset);
        RelativeLayout rlBuy        = findViewById(R.id.rlBuy);
        ImageView ivQpBarClose      = findViewById(R.id.ivQpBarClose);
        ImageView ivBankerBarClose  = findViewById(R.id.ivBankerBarClose);
        CircularTextView ctv1       = findViewById(R.id.circle_1);
        CircularTextView ctv2       = findViewById(R.id.circle_2);
        TextView tvBetAmount        = findViewById(R.id.tvBetAmount);
        tvInstructions              = findViewById(R.id.tvInstructions);
        tvQpName                    = findViewById(R.id.tvQpName);
        rvColorBar                  = findViewById(R.id.rvColorBar);
        rvNumbers                   = findViewById(R.id.rvNumbers);
        rvQpNumbers                 = findViewById(R.id.rvQpNumbers);
        rvPickType                  = findViewById(R.id.rvPickType);
        rvBetAmount                 = findViewById(R.id.rvBetAmount);
        tvBetValueTag               = findViewById(R.id.tvBetValueTag);
        rvUpperLine                 = findViewById(R.id.rvUpperLine);
        rvLowerLine                 = findViewById(R.id.rvLowerLine);
        tvBetValue                  = findViewById(R.id.tvBetValue);
        llBottomBar                 = findViewById(R.id.llBottomBar);
        llParent                    = findViewById(R.id.parent);
        llQpSelectionBar            = findViewById(R.id.llQpSelectionBar);
        tvQpRange                   = findViewById(R.id.tvQpRange);
        llBankerBar                 = findViewById(R.id.llBankerBar);
        llUpperLine                 = findViewById(R.id.llUpperLine);
        llLowerLine                 = findViewById(R.id.llLowerLine);
        llOther                     = findViewById(R.id.llOther);
        tvBetTypeOther              = findViewById(R.id.tvBetTypeOther);
        tvBetType5                  = findViewById(R.id.tvBetType5);
        tvBetType4                  = findViewById(R.id.tvBetType4);
        tvBetType3                  = findViewById(R.id.tvBetType3);
        tvBetType2                  = findViewById(R.id.tvBetType2);
        tvBetType1                  = findViewById(R.id.tvBetType1);

        ctv1.setStrokeColor("#e3e3e3");
        ctv2.setStrokeColor("#f5f5f5");
        viewModel.getLiveData().observe(this, qpObserver);
        String tag = getString(R.string.bet_value) + " (" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(FiveByNinetyMainBetActivity.this)) + ")";
        tvBetValueTag.setText(tag);
        String tagAmt = getString(R.string.bet_amount) + " (" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(FiveByNinetyMainBetActivity.this)) + ")";
        tvBetAmount.setText(tagAmt);
        llReset.setOnClickListener(this);
        rlBuy.setOnClickListener(this);
        ivQpBarClose.setOnClickListener(this);
        ivBankerBarClose.setOnClickListener(this);
        llUpperLine.setOnClickListener(this);
        llLowerLine.setOnClickListener(this);
        tvBetType1.setOnClickListener(this);
        tvBetType2.setOnClickListener(this);
        tvBetType3.setOnClickListener(this);
        tvBetType4.setOnClickListener(this);
        tvBetType5.setOnClickListener(this);
        llOther.setOnClickListener(this);
    }

    private void setColorMap() {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> ball = DrawGameData.getSelectedGame().getNumberConfig().getRange().get(0).getBall();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball data: ball) {
            MAP_NUMBER_COLOR.put(data.getNumber(), data.getColor());
        }
    }

    public void onClickBack(View view) {
        if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < CLICK_GAP) {
            return;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        FiveByNinetyMainBetActivity.this.finish();
    }

    private void setColorBarAdapter() {
        ArrayList<String> listColor = new ArrayList<>();
        for(int index=0; index<9; index++)
            listColor.add(DrawGameData.getSelectedGame().getNumberConfig().getRange().get(0).getBall().get(index).getColor());
        ColorBarPortraitAdapter numbersAdapter = new ColorBarPortraitAdapter(listColor, this);
        rvColorBar.setLayoutManager(new GridLayoutManager(this, 9));
        rvColorBar.setHasFixedSize(true);
        rvColorBar.setItemAnimator(new DefaultItemAnimator());
        rvColorBar.addItemDecoration(new SpacesItemDecoration(9, getResources().getDimensionPixelSize(R.dimen.space_2), false));
        rvColorBar.setAdapter(numbersAdapter);
    }

    private void setNumbersAdapter(ArrayList<String> listQpNumbers, boolean isBanker, ArrayList<String> listBankerActive, ArrayList<String> listBankerInActive) {
        if (listQpNumbers != null) {
            tvInstructions.setVisibility(View.GONE);
            llBottomBar.setVisibility(View.VISIBLE);
        }
        BallClickListener listener = FiveByNinetyMainBetActivity.this::onBallClicked;
        numbersAdapter = new NumbersPortraitFiveByNinetyAdapter(DrawGameData.getSelectedGame().getNumberConfig().getRange().get(0).getBall(), this, listener, listQpNumbers, isBanker, listBankerActive, listBankerInActive);

        rvNumbers.setLayoutManager(new GridLayoutManager(this, 9));
        rvNumbers.setHasFixedSize(true);
        rvNumbers.setItemAnimator(new DefaultItemAnimator());
        if (isFirstTimeNumberAdapter) {
            rvNumbers.addItemDecoration(new SpacesItemDecoration(9, getResources().getDimensionPixelSize(R.dimen.space_2), false));
            isFirstTimeNumberAdapter = false;
        }
        rvNumbers.setAdapter(numbersAdapter);
    }

    private void setPickTypeAdapter(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO) {
        pickTypeAdapter = new PickTypePortraitFiveByNinetyAdapter(getPickTypeWithQp(betRespVO), this, this);

        rvPickType.setHasFixedSize(true);
        rvPickType.setItemAnimator(new DefaultItemAnimator());
        rvPickType.setLayoutManager(betRespVO.getBetCode().equalsIgnoreCase("Direct2") ? new GridLayoutManager(this, 6) : new GridLayoutManager(this, 5));
        if (isFirstTimePickTypeAdapter) {
            if (betRespVO.getBetCode().equalsIgnoreCase("Direct2"))
                rvPickType.addItemDecoration(new SpacesItemDecoration(6, getResources().getDimensionPixelSize(R.dimen.space_5), false));
            else
                rvPickType.addItemDecoration(new SpacesItemDecoration(5, getResources().getDimensionPixelSize(R.dimen.space_5), false));
            isFirstTimePickTypeAdapter = false;
        }
        rvPickType.setAdapter(pickTypeAdapter);
        pickTypeAdapter.onClick(0, true);
    }

    private void setBetAmountAdapter(double unitPrice, int maxBetAmtMul) {
        SELECTED_BET_AMOUNT = 0;
        int count = (int) (maxBetAmtMul/unitPrice);
        Log.d("draw", "maxBetAmtMul: " + maxBetAmtMul);
        Log.d("draw", "unitPrice: " + unitPrice);

        for (int index=1; index<=count; index++) {
            int amount = (int) (index * unitPrice);
            FiveByNinetyBetAmountBean model = new FiveByNinetyBetAmountBean();
            model.setAmount(amount);
            model.setSelected(false);
            LIST_BET_AMOUNT.add(model);
        }

        if (LIST_BET_AMOUNT.size() > 0) {
            onBetAmountClick(LIST_BET_AMOUNT.get(0).getAmount());
            setSelectedBetAmountForHighlight(0);
            tvBetTypeOther.setText(getFormattedAmount(LIST_BET_AMOUNT.get(0).getAmount()));
            if (LIST_BET_AMOUNT.size() > 5) {
                tvBetType1.setText(getFormattedAmount(LIST_BET_AMOUNT.get(0).getAmount()));
                tvBetType2.setText(getFormattedAmount(LIST_BET_AMOUNT.get(1).getAmount()));
                tvBetType3.setText(getFormattedAmount(LIST_BET_AMOUNT.get(2).getAmount()));
                tvBetType4.setText(getFormattedAmount(LIST_BET_AMOUNT.get(3).getAmount()));
                tvBetType5.setText(getFormattedAmount(LIST_BET_AMOUNT.get(4).getAmount()));
                isOtherAmountAvailable = true;
            }
            else if (LIST_BET_AMOUNT.size() == 5) {
                tvBetType1.setText(getFormattedAmount(LIST_BET_AMOUNT.get(0).getAmount()));
                tvBetType2.setText(getFormattedAmount(LIST_BET_AMOUNT.get(1).getAmount()));
                tvBetType3.setText(getFormattedAmount(LIST_BET_AMOUNT.get(2).getAmount()));
                tvBetType4.setText(getFormattedAmount(LIST_BET_AMOUNT.get(3).getAmount()));
                tvBetType5.setText(getFormattedAmount(LIST_BET_AMOUNT.get(4).getAmount()));
                isOtherAmountAvailable = false;
            }
            else if (LIST_BET_AMOUNT.size() == 4) {
                tvBetType1.setText(getFormattedAmount(LIST_BET_AMOUNT.get(0).getAmount()));
                tvBetType2.setText(getFormattedAmount(LIST_BET_AMOUNT.get(1).getAmount()));
                tvBetType3.setText(getFormattedAmount(LIST_BET_AMOUNT.get(2).getAmount()));
                tvBetType4.setText(getFormattedAmount(LIST_BET_AMOUNT.get(3).getAmount()));
                tvBetType5.setVisibility(View.GONE);
                isOtherAmountAvailable = false;
            }
            else if (LIST_BET_AMOUNT.size() == 3) {
                tvBetType1.setText(getFormattedAmount(LIST_BET_AMOUNT.get(0).getAmount()));
                tvBetType2.setText(getFormattedAmount(LIST_BET_AMOUNT.get(1).getAmount()));
                tvBetType3.setText(getFormattedAmount(LIST_BET_AMOUNT.get(2).getAmount()));
                tvBetType4.setVisibility(View.GONE);
                tvBetType5.setVisibility(View.GONE);
                isOtherAmountAvailable = false;
            }
            else if (LIST_BET_AMOUNT.size() == 2) {
                tvBetType1.setText(getFormattedAmount(LIST_BET_AMOUNT.get(0).getAmount()));
                tvBetType2.setText(getFormattedAmount(LIST_BET_AMOUNT.get(1).getAmount()));
                tvBetType3.setVisibility(View.GONE);
                tvBetType4.setVisibility(View.GONE);
                tvBetType5.setVisibility(View.GONE);
                isOtherAmountAvailable = false;
            }
            else if (LIST_BET_AMOUNT.size() == 1) {
                tvBetType1.setText(getFormattedAmount(LIST_BET_AMOUNT.get(0).getAmount()));
                tvBetType2.setVisibility(View.GONE);
                tvBetType3.setVisibility(View.GONE);
                tvBetType4.setVisibility(View.GONE);
                tvBetType5.setVisibility(View.GONE);
                isOtherAmountAvailable = false;
            }
            /*list.get(0).setSelected(true);
            onBetAmountClick(list.get(0).getAmount());
            BetAmountPortraitFiveByNinetyAdapter betAmountAdapter = new BetAmountPortraitFiveByNinetyAdapter(list, this, this);
            rvBetAmount.setLayoutManager(new GridLayoutManager(this, 7));
            rvBetAmount.setHasFixedSize(true);
            rvBetAmount.setItemAnimator(new DefaultItemAnimator());
            rvBetAmount.setNestedScrollingEnabled(false);
            if (isFirstTimeBetAmountAdapter) {
                rvBetAmount.addItemDecoration(new SpacesItemDecoration(7, getResources().getDimensionPixelSize(R.dimen.space_3), false));
            }
            rvBetAmount.setAdapter(betAmountAdapter);*/
        }
        else Utils.showRedToast(FiveByNinetyMainBetActivity.this, getString(R.string.some_technical_issue));
    }

    private void setQpNumbersAdapter(ArrayList<QuickPickBean> listQpNumbers) {
        QpNumbersPortraitAdapter adapter = new QpNumbersPortraitAdapter(listQpNumbers, this, this);
        rvQpNumbers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvQpNumbers.setHasFixedSize(true);
        rvQpNumbers.setItemAnimator(new DefaultItemAnimator());
        rvQpNumbers.setAdapter(adapter);
    }

    private void onBallClicked(TextView textView, String ballNumber, String ballColor) {
        betValueCalculation();
        if (MAX_SELECTION_LIMIT > 0) {
            Utils.vibrate(this);
            if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker")) {
                if (LIST_SELECTED_UPPER_LINE_NUMBERS.contains(ballNumber)) {
                    if (llUpperLine.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.outline_red).getConstantState())) {
                        resetNumberTextView(textView);
                        LIST_SELECTED_UPPER_LINE_NUMBERS.remove(ballNumber);
                        setUpperLine();
                        setBankerNumber(true);
                    } else
                        setUpperLine();
                }
                else if (LIST_SELECTED_LOWER_LINE_NUMBERS.contains(ballNumber)) {
                    if (!llUpperLine.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.outline_red).getConstantState())) {
                        resetNumberTextView(textView);
                        LIST_SELECTED_LOWER_LINE_NUMBERS.remove(ballNumber);
                        setLowerLine();
                        setBankerNumber(false);
                    } else
                        setLowerLine();
                }
                else {
                    if (llUpperLine.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,R.drawable.outline_red).getConstantState())) {
                        if (LIST_SELECTED_UPPER_LINE_NUMBERS.size() < MAX_SELECTION_LIMIT) {
                            setColoredNumberTextView(textView, ballColor);
                            LIST_SELECTED_UPPER_LINE_NUMBERS.add(ballNumber);
                            setBankerNumber(true);
                        }
                        else
                            showToast(getString(R.string.max_selection_limit_upper_line));
                    }
                    else {
                        if (LIST_SELECTED_LOWER_LINE_NUMBERS.size() < MAX_SELECTION_LIMIT) {
                            setColoredNumberTextView(textView, ballColor);
                            LIST_SELECTED_LOWER_LINE_NUMBERS.add(ballNumber);
                            setBankerNumber(false);
                        }
                        else
                            showToast(getString(R.string.max_selection_limit_lower_line));
                    }
                }
            }
            else if (LIST_SELECTED_NUMBERS.contains(ballNumber)) {
                if (llQpSelectionBar.getVisibility() == View.VISIBLE) {
                    llQpSelectionBar.setVisibility(View.GONE);
                    rvPickType.setVisibility(View.VISIBLE);
                }
                resetNumberTextView(textView);
                LIST_SELECTED_NUMBERS.remove(ballNumber);
                if (SELECTED_BET_TYPE_DATA.getBetCode().equalsIgnoreCase("DirectFirst"))
                    switchToPickType("Direct1");
                else if (SELECTED_BET_TYPE_DATA.getBetCode().equalsIgnoreCase("Direct2")) {
                    if (SELECTED_PICK_TYPE_OBJECT.getName().equalsIgnoreCase("QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                        switchToPickType("Direct2");
                    if (SELECTED_PICK_TYPE_OBJECT.getName().contains("Perm QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                        switchToPickType("Perm2");
                    if (SELECTED_PICK_TYPE_OBJECT.getName().contains("Banker1AgainstAll QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                        switchToPickType("Banker1AgainstAll");
                } else if (SELECTED_BET_TYPE_DATA.getBetCode().equalsIgnoreCase("Direct3")) {
                    if (SELECTED_PICK_TYPE_OBJECT.getName().equalsIgnoreCase("QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                        switchToPickType("Direct3");
                    if (SELECTED_PICK_TYPE_OBJECT.getName().contains("Perm QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                        switchToPickType("Perm3");
                } else if (SELECTED_BET_TYPE_DATA.getBetCode().equalsIgnoreCase("Direct4"))
                    switchToPickType("Direct4");
                else if (SELECTED_BET_TYPE_DATA.getBetCode().equalsIgnoreCase("Direct5"))
                    switchToPickType("Direct5");
            }
            else {
                if (LIST_SELECTED_NUMBERS.size() < MAX_SELECTION_LIMIT) {
                    if (llQpSelectionBar.getVisibility() == View.VISIBLE) {
                        llQpSelectionBar.setVisibility(View.GONE);
                        rvPickType.setVisibility(View.VISIBLE);
                    }
                    setColoredNumberTextView(textView, ballColor);
                    LIST_SELECTED_NUMBERS.add(ballNumber);
                    if (SELECTED_BET_TYPE_DATA.getBetCode().equalsIgnoreCase("Direct2") && SELECTED_PICK_TYPE_OBJECT.getName().contains("Perm QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                        switchToPickType("Perm2");
                    if (SELECTED_BET_TYPE_DATA.getBetCode().equalsIgnoreCase("Direct3") && SELECTED_PICK_TYPE_OBJECT.getName().contains("Perm QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                        switchToPickType("Perm3");

                }
                else {
                    String msg = MAX_SELECTION_LIMIT > 1 ? getString(R.string.cannot_select_more) + " " + MAX_SELECTION_LIMIT + " " + getString(R.string.numbers_for) + " " + SELECTED_PICK_TYPE_OBJECT.getName() + "!" : getString(R.string.cannot_select_more) + " " + MAX_SELECTION_LIMIT + " " + getString(R.string.number_for) + " " + SELECTED_PICK_TYPE_OBJECT.getName() + "!";
                    showToast(msg);
                }
            }
            showInstructionBar();
            betValueCalculation();
        }
        else {
            showToast(getString(R.string.issue_with_pick_type));
        }
    }

    private void setBankerNumber(boolean isUpperLine) {
        ArrayList<BankerBean> list = new ArrayList<>();
        for (String number: isUpperLine?LIST_SELECTED_UPPER_LINE_NUMBERS:LIST_SELECTED_LOWER_LINE_NUMBERS) {
            BankerBean model = new BankerBean();
            model.setNumber(number);
            model.setColor(MAP_NUMBER_COLOR.get(number));
            list.add(model);
        }
        if (list.size() < MAX_SELECTION_LIMIT) {
            int diff = MAX_SELECTION_LIMIT - list.size();
            for (int i=1; i<=diff; i++) {
                BankerBean model = new BankerBean();
                model.setNumber("");
                list.add(model);
            }
        }
        if (isUpperLine)
            setUpperLineAdapter(list);
        else
            setLowerLineAdapter(list);
    }

    private void resetNumberTextView(TextView textView) {
        GradientDrawable background = (GradientDrawable) textView.getBackground();
        background.setColor(Color.parseColor("#ffffff"));
        background.setStroke(2, Color.parseColor("#aaaaaa"));
        textView.setTypeface(null, Typeface.NORMAL);
        textView.setTextColor(Color.parseColor("#aaaaaa"));
    }

    private void setColoredNumberTextView(TextView textView, String ballColor) {
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTypeface(null, Typeface.BOLD);
        GradientDrawable background = (GradientDrawable) textView.getBackground();
        background.setColor(Color.parseColor(ballColor));
        background.setStroke(2, Color.parseColor(ballColor));
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
            case R.id.rlBuy:
                if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker")) {

                }
                else if (LIST_SELECTED_NUMBERS.size() < MIN_SELECTION_LIMIT) {
                    String msg = MIN_SELECTION_LIMIT > 1 ? getString(R.string.selecet_at_least) + " " + MIN_SELECTION_LIMIT + " " + getString(R.string.numbers_for) + " " + SELECTED_PICK_TYPE_OBJECT.getName() + "." : getString(R.string.selecet_at_least) + " " + MIN_SELECTION_LIMIT + " " + getString(R.string.number_for) + " " + SELECTED_PICK_TYPE_OBJECT.getName() + ".";
                    showToast(msg);
                    return;
                }
                if (Double.parseDouble(tvBetValue.getTag().toString()) == 0) {
                    showToast(getString(R.string.bet_value_cannot_be_zero));
                    return;
                }

                addBet();
                break;
            case R.id.ivQpBarClose:
                llQpSelectionBar.setVisibility(View.GONE);
                rvPickType.setVisibility(View.VISIBLE);
                if (LIST_SELECTED_NUMBERS.isEmpty())
                    switchToPickType("Direct2");
                break;
            case R.id.ivBankerBarClose:
                llBankerBar.setVisibility(View.GONE);
                rvPickType.setVisibility(View.VISIBLE);
                llUpperLine.setBackgroundResource(R.drawable.outline_red);
                llLowerLine.setBackgroundResource(R.drawable.outline_grey);
                resetSelectedNumberList();
                switchToPickType("Direct2");
                /*if (LIST_SELECTED_UPPER_LINE_NUMBERS.isEmpty() && LIST_SELECTED_LOWER_LINE_NUMBERS.isEmpty())
                    switchToPickType("Direct2");*/
                break;
            case R.id.llUpperLine:
                setUpperLine();
                break;
            case R.id.llLowerLine:
                setLowerLine();
                break;
            case R.id.tvBetType1:
                setSelectedBetAmountForHighlight(0);
                onBetAmountClick(LIST_BET_AMOUNT.get(0).getAmount());
                tvBetTypeOther.setText((getFormattedAmount(LIST_BET_AMOUNT.get(0).getAmount())));
                break;
            case R.id.tvBetType2:
                setSelectedBetAmountForHighlight(1);
                onBetAmountClick(LIST_BET_AMOUNT.get(1).getAmount());
                tvBetTypeOther.setText((getFormattedAmount(LIST_BET_AMOUNT.get(1).getAmount())));
                break;
            case R.id.tvBetType3:
                setSelectedBetAmountForHighlight(2);
                onBetAmountClick(LIST_BET_AMOUNT.get(2).getAmount());
                tvBetTypeOther.setText((getFormattedAmount(LIST_BET_AMOUNT.get(2).getAmount())));
                break;
            case R.id.tvBetType4:
                setSelectedBetAmountForHighlight(3);
                onBetAmountClick(LIST_BET_AMOUNT.get(3).getAmount());
                tvBetTypeOther.setText((getFormattedAmount(LIST_BET_AMOUNT.get(3).getAmount())));
                break;
            case R.id.tvBetType5:
                setSelectedBetAmountForHighlight(4);
                onBetAmountClick(LIST_BET_AMOUNT.get(4).getAmount());
                tvBetTypeOther.setText((getFormattedAmount(LIST_BET_AMOUNT.get(4).getAmount())));
                break;
            case R.id.llOther:
                if (isOtherAmountAvailable) {
                    if (betAmountDialog == null || !betAmountDialog.isShowing()) {
                        betAmountDialog = new BetAmountDialog(FiveByNinetyMainBetActivity.this, LIST_BET_AMOUNT,this);
                        betAmountDialog.show();
                        if (betAmountDialog.getWindow() != null) {
                            betAmountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            betAmountDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        }
                    }
                }
                break;
        }
    }

    private void setSelectedBetAmountForHighlight(int position) {
        for (int index = 0; index < LIST_BET_AMOUNT.size(); index++) {
            LIST_BET_AMOUNT.get(index).setSelected(position == index);
        }
    }

    @Override
    public void onAmountChange(int amount) {
        if (amount != -1) {
            onBetAmountClick(amount);
            tvBetTypeOther.setText(getFormattedAmount(amount));
        }
    }

    private void setUpperLine() {
        llUpperLine.setBackgroundResource(R.drawable.outline_red);
        llLowerLine.setBackgroundResource(R.drawable.outline_grey);
        setNumbersAdapter(null, true, LIST_SELECTED_UPPER_LINE_NUMBERS, LIST_SELECTED_LOWER_LINE_NUMBERS);
        getSelectionLimitsForBanker(true);
    }

    private void setLowerLine() {
        llUpperLine.setBackgroundResource(R.drawable.outline_grey);
        llLowerLine.setBackgroundResource(R.drawable.outline_red);
        setNumbersAdapter(null, true, LIST_SELECTED_LOWER_LINE_NUMBERS, LIST_SELECTED_UPPER_LINE_NUMBERS);
        getSelectionLimitsForBanker(false);
    }

    @Override
    public void onPickTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType) {
        tvInstructions.setText(pickType.getDescription());
        SELECTED_PICK_TYPE_OBJECT = pickType;
        resetSelectedNumberList();
        if (pickType.getCode().equalsIgnoreCase("Banker")) {
            getSelectionLimitsForBanker(true);
            bankerSetUp();
        }
        else {
            getSelectionLimits();
            //if (pickType.getName().contains("QP") && pickType.getRange().get(0).getPickMode().equalsIgnoreCase("QP")) {
            if (pickType.getRange().get(0).getPickMode().equalsIgnoreCase("QP")) {
                qpSetup();
            }
        }
        //if (SELECTED_BET_TYPE_OBJECT != null)
        //    setBetAmountAdapter(SELECTED_BET_TYPE_OBJECT.getUnitPrice(), SELECTED_BET_TYPE_OBJECT.getMaxBetAmtMul());
        /*if (pickType.getName().contains("QP") && pickType.getRange().get(0).getPickMode().equalsIgnoreCase("QP")) {
            qpSetup();
        } else {
            pickTypeMainColor();
        }*/

    }

    private void qpSetup() {
        if (MIN_SELECTION_LIMIT == MAX_SELECTION_LIMIT) {
            callQuickPickApi(Integer.toString(MAX_SELECTION_LIMIT));
        }
        else {
            llQpSelectionBar.setVisibility(View.VISIBLE);
            rvPickType.setVisibility(View.GONE);
            tvQpName.setText(String.format("%s:", SELECTED_PICK_TYPE_OBJECT.getName()));
            ArrayList<QuickPickBean> list = new ArrayList<>();
            for (int count=MIN_SELECTION_LIMIT; count<=MAX_SELECTION_LIMIT; count++) {
                QuickPickBean model = new QuickPickBean();
                model.setNumber(count);
                model.setSelected(false);
                list.add(model);
            }
            String range = "(" + MIN_SELECTION_LIMIT + "-" + MAX_SELECTION_LIMIT + ")";
            tvQpRange.setText(range);
            setQpNumbersAdapter(list);
        }
    }

    private void bankerSetUp() {
        llBankerBar.setVisibility(View.VISIBLE);
        rvPickType.setVisibility(View.GONE);
        int minUl = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[0].split(",")[0]);
        int maxUl = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[0].split(",")[1]);
        ArrayList<BankerBean> listUpperLine = new ArrayList<>();
        for (int i=1; i<=maxUl; i++) {
            BankerBean model = new BankerBean();
            model.setNumber("");
            listUpperLine.add(model);
        }
        setUpperLineAdapter(listUpperLine);

        int minLl = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[1].split(",")[0]);
        int maxLl = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[1].split(",")[1]);
        ArrayList<BankerBean> listLowerLine = new ArrayList<>();
        for (int i=1; i<=maxLl; i++) {
            BankerBean model = new BankerBean();
            model.setNumber("");
            listLowerLine.add(model);
        }
        setLowerLineAdapter(listLowerLine);
    }

    private void setUpperLineAdapter(ArrayList<BankerBean> listQpNumbers) {
        UpperLinePortraitAdapter adapter = new UpperLinePortraitAdapter(listQpNumbers, this);
        rvUpperLine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvUpperLine.setHasFixedSize(true);
        rvUpperLine.setItemAnimator(new DefaultItemAnimator());
        rvUpperLine.setAdapter(adapter);
        rvUpperLine.setLayoutFrozen(true);
    }

    private void setLowerLineAdapter(ArrayList<BankerBean> listQpNumbers) {
        LowerLinePortraitAdapter adapter = new LowerLinePortraitAdapter(listQpNumbers, this);
        rvLowerLine.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvLowerLine.setHasFixedSize(true);
        rvLowerLine.setItemAnimator(new DefaultItemAnimator());
        rvLowerLine.setAdapter(adapter);
        rvLowerLine.setLayoutFrozen(true);
    }

    private void switchToPickType(String pickCode) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> type = getPickTypeWithQp(SELECTED_BET_TYPE_DATA);
        for (int index = 0; index < type.size(); index++) {
            DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType = type.get(index);
            if (pickType.getCode().equalsIgnoreCase(pickCode)) {
                tvInstructions.setText(pickType.getDescription());
                SELECTED_PICK_TYPE_OBJECT = pickType;
                getSelectionLimits();
                if (pickTypeAdapter != null)
                    pickTypeAdapter.onClick(index, false);
                break;
            }
        }
    }

    private void showToast(String text) {
        try {
            Snackbar snackbar = Snackbar.make(llParent, text, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBetAmountClick(int amount) {
        SELECTED_BET_AMOUNT = amount;
        betValueCalculation();
    }

    private void betValueCalculation() {
        //(unitPrice*bet_amt_multiple) * noOfDraws * noOfLines
        /*DecimalFormat df = new DecimalFormat("##.#");
        String amt = "$" + df.format(amount);*/
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker")) {
            int minUl = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[0].split(",")[0]);
            int minLl = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[1].split(",")[0]);
            if (LIST_SELECTED_UPPER_LINE_NUMBERS.size() >= minUl && LIST_SELECTED_LOWER_LINE_NUMBERS.size() >= minLl) {
                double amount = SELECTED_BET_AMOUNT * getNumberOfLines();
                int amt = (int) amount;
                //String strAmount = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(FiveByNinetyMainBetActivity.this)) + amt;
                String strAmount = String.valueOf(amt);
                String formattedAmount;
                try {
                    formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount), Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE()));
                } catch (Exception e) {
                    e.printStackTrace();
                    formattedAmount = String.valueOf(amt);
                }
                tvBetValue.setText(formattedAmount);
                tvBetValue.setTag(amount);
            } else {
                String strAmount = "0";
                tvBetValue.setText(strAmount);
                tvBetValue.setTag(0);
            }
        } else {
            if (LIST_SELECTED_NUMBERS.size() >= MIN_SELECTION_LIMIT) {
                double amount = SELECTED_BET_AMOUNT * getNumberOfLines();
                int amt = (int) amount;
                //String strAmount = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(FiveByNinetyMainBetActivity.this)) + amt;
                String strAmount = String.valueOf(amt);
                String formattedAmount;
                try {
                    formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount), Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE()));
                } catch (Exception e) {
                    e.printStackTrace();
                    formattedAmount = String.valueOf(amt);
                }
                tvBetValue.setText(formattedAmount);
                tvBetValue.setTag(amount);
            } else {
                String strAmount = "0";
                tvBetValue.setText(strAmount);
                tvBetValue.setTag(0);
            }
        }
    }

    private int getNumberOfLines() {
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm2"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 2);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm3"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 3);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm4"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 4);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm5"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 5);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm6"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 6);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm7"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 7);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm8"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 8);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm9"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 9);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker"))
            return LIST_SELECTED_UPPER_LINE_NUMBERS.size() * LIST_SELECTED_LOWER_LINE_NUMBERS.size();
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker1AgainstAll"))
            return 89;
        return 1;
    }

    private void getSelectionLimitsForBanker(boolean isUpperLine) {
        if (SELECTED_PICK_TYPE_OBJECT != null) {
            try {
                MIN_SELECTION_LIMIT = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[isUpperLine?0:1].split(",")[0]);
                MAX_SELECTION_LIMIT = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[isUpperLine?0:1].split(",")[1]);
            }
            catch (Exception e) {
                MIN_SELECTION_LIMIT = 0; MAX_SELECTION_LIMIT = 0; e.printStackTrace();
            }
        } else {
            MIN_SELECTION_LIMIT = 0;
            MAX_SELECTION_LIMIT = 0;
        }

        Log.i("draw", "MIN LIMIT: " + MIN_SELECTION_LIMIT);
        Log.i("draw", "MAX LIMIT: " + MAX_SELECTION_LIMIT);
    }

    private void getSelectionLimits() {
        if (SELECTED_PICK_TYPE_OBJECT != null) {
            try {
                MIN_SELECTION_LIMIT = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split(",")[0]);
                MAX_SELECTION_LIMIT = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split(",")[1]);
            }
            catch (Exception e) {
                MIN_SELECTION_LIMIT = 0; MAX_SELECTION_LIMIT = 0; e.printStackTrace();
            }
        } else {
            MIN_SELECTION_LIMIT = 0;
            MAX_SELECTION_LIMIT = 0;
        }

        Log.i("draw", "MIN LIMIT: " + MIN_SELECTION_LIMIT);
        Log.i("draw", "MAX LIMIT: " + MAX_SELECTION_LIMIT);
    }

    private void resetSelectedNumberList() {
        LIST_SELECTED_NUMBERS.clear();
        LIST_SELECTED_UPPER_LINE_NUMBERS.clear();
        LIST_SELECTED_LOWER_LINE_NUMBERS.clear();
        setNumbersAdapter(null, false, null, null);
        tvBetValue.setText("0");
        tvBetValue.setTag(0);
        showInstructionBar();
    }

    /*private void pickTypeMainColor() {
        if (SELECTED_BET_TYPE_DATA != null && SELECTED_BET_TYPE_DATA.getBetCode().trim().equalsIgnoreCase("Direct6")) {
            String code = SELECTED_PICK_TYPE_OBJECT.getCode().trim();
            if (code.equalsIgnoreCase("RedBalls") || code.equalsIgnoreCase("GreenBalls") ||
                    code.equalsIgnoreCase("BlueBalls") || code.equalsIgnoreCase("MagentaBalls") ||
                    code.equalsIgnoreCase("BrownBalls") || code.equalsIgnoreCase("CyanBalls") ||
                    code.equalsIgnoreCase("OrangeBalls") || code.equalsIgnoreCase("GreyBalls")) {
                setPreSelectedNumbers(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickValue());
            }
            else if (code.equalsIgnoreCase("Hot6")) {
                String number = GAME_DATA.getHotNumbers();
                number = number.replace("[", "");
                number = number.replace("]", "");
                number = number.replaceAll(" ", "");
                Log.d("draw", "Hot6 Numbers: " + number);
                setPreSelectedNumbers(number);
            }
            else if (code.equalsIgnoreCase("Cold6")) {
                String number = GAME_DATA.getColdNumbers();
                number = number.replace("[", "");
                number = number.replace("]", "");
                number = number.replaceAll(" ", "");
                number = number.replaceAll(" ", "");
                Log.d("draw", "Cold6 Numbers: " + number);
                setPreSelectedNumbers(number);
            }
        }
    }*/

    /*private void setPreSelectedNumbers(String strNumber) {
        LIST_SELECTED_NUMBERS.clear();
        tvBetValue.setText("$0.0");
        tvBetValue.setTag(0);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(strNumber.split(",")));
        for (String num: list)
            LIST_SELECTED_NUMBERS.add(num.length() == 1 ? "0" + num : num);
        setNumbersAdapter(new ArrayList<>(LIST_SELECTED_NUMBERS));
        betValueCalculation();
        showInstructionBar();
    }*/

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> getPickTypeWithQp(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> originalPickType = betRespVO.getPickTypeData().getPickType();
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> newPickType = new ArrayList<>();

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType model: originalPickType) {
            newPickType.add(model);
            if (model.getRange().get(0).getQpAllowed().equalsIgnoreCase("YES")) {
                DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType qpData = new DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType();
                qpData.setCode(model.getCode());
                qpData.setDescription(model.getDescription());
                if (model.getName().equalsIgnoreCase("Manual"))
                    qpData.setName("QP");
                else
                    qpData.setName(model.getName() + " " + "QP");
                DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType.Range range
                        = new DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType.Range();
                range.setPickConfig(model.getRange().get(0).getPickConfig());
                range.setPickCount(model.getRange().get(0).getPickCount());
                range.setPickMode("QP");
                range.setPickValue(model.getRange().get(0).getPickValue());
                range.setQpAllowed(model.getRange().get(0).getQpAllowed());
                ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType.Range> lst = new ArrayList<>();
                lst.add(range);
                qpData.setRange(lst);
                newPickType.add(qpData);
            }
        }
        return newPickType;
    }

    private void showInstructionBar() {
        /*if (LIST_SELECTED_NUMBERS.isEmpty()) {
            tvInstructions.setVisibility(View.VISIBLE);
            llBottomBar.setVisibility(View.GONE);
        } else if (LIST_SELECTED_NUMBERS.isEmpty()) {

        } else {
            tvInstructions.setVisibility(View.GONE);
            llBottomBar.setVisibility(View.VISIBLE);
        }*/

        if (!LIST_SELECTED_NUMBERS.isEmpty()) {
            tvInstructions.setVisibility(View.GONE);
            llBottomBar.setVisibility(View.VISIBLE);
        }
        else if (!LIST_SELECTED_UPPER_LINE_NUMBERS.isEmpty() || !LIST_SELECTED_LOWER_LINE_NUMBERS.isEmpty()) {
            tvInstructions.setVisibility(View.GONE);
            llBottomBar.setVisibility(View.VISIBLE);
        }
        else {
            tvInstructions.setVisibility(View.VISIBLE);
            llBottomBar.setVisibility(View.GONE);
        }
    }

    private void resetGame() {
        tvBetValue.setText("0");
        resetSelectedNumberList();
        setPickTypeAdapter(SELECTED_BET_TYPE_DATA);
        llQpSelectionBar.setVisibility(View.GONE);
        rvPickType.setVisibility(View.VISIBLE);
        llBankerBar.setVisibility(View.GONE);
        rvPickType.setVisibility(View.VISIBLE);
        llUpperLine.setBackgroundResource(R.drawable.outline_red);
        llLowerLine.setBackgroundResource(R.drawable.outline_grey);
    }

    @Override
    public void onQpNumberSelection(int count) {
        callQuickPickApi(Integer.toString(count));
    }

    private void callQuickPickApi(String numbersPicked) {
        if (!Utils.isNetworkConnected(FiveByNinetyMainBetActivity.this)) {
            Toast.makeText( FiveByNinetyMainBetActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
        } else {
            UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "quickPick");
            if (urlBean != null) {
                ProgressBarDialog.getProgressDialog().showProgress(this);
                QuickPickRequestBean model = new QuickPickRequestBean();
                model.setNoOfLines(1);
                model.setNumbersPicked(numbersPicked);
                viewModel.callQPApi(urlBean, model, DrawGameData.getSelectedGame().getGameCode());
            }
        }
    }

    Observer<QuickPickResponseBean> qpObserver = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null) {
            showToast(getString(R.string.something_went_wrong));
        }
        else if (response.getResponseCode() == 0) {
            LIST_SELECTED_NUMBERS.clear();
            tvBetValue.setText("0");
            tvBetValue.setTag(0);
            String strNumber = response.getResponseData().get(0);
            ArrayList<String> list = new ArrayList<>(Arrays.asList(strNumber.split(",")));
            for (String num: list)
                LIST_SELECTED_NUMBERS.add(num.length() == 1 ? "0" + num : num);
            setNumbersAdapter(new ArrayList<>(LIST_SELECTED_NUMBERS), false, null, null);
            betValueCalculation();
        } else {
            if (Utils.checkForSessionExpiryActivity(FiveByNinetyMainBetActivity.this, response.getResponseCode(), FiveByNinetyMainBetActivity.this)) return;

            String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
            showToast(errorMsg);
        }
    };

    private void addBet() {
        PanelBean model = new PanelBean();
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker")) {
            String upperLineNumbers = "", lowerLineNumbers = "";
            for (String num : LIST_SELECTED_UPPER_LINE_NUMBERS)
                upperLineNumbers = upperLineNumbers + num + ",";
            upperLineNumbers = upperLineNumbers.trim();
            if (upperLineNumbers.length() > 0 && upperLineNumbers.charAt(upperLineNumbers.length() - 1) == ',')
                upperLineNumbers = upperLineNumbers.substring(0, upperLineNumbers.length() - 1);

            for (String num : LIST_SELECTED_LOWER_LINE_NUMBERS)
                lowerLineNumbers = lowerLineNumbers + num + ",";
            lowerLineNumbers = lowerLineNumbers.trim();
            if (lowerLineNumbers.length() > 0 && lowerLineNumbers.charAt(lowerLineNumbers.length() - 1) == ',')
                lowerLineNumbers = lowerLineNumbers.substring(0, lowerLineNumbers.length() - 1);

            String pickedValues = upperLineNumbers + "-" + lowerLineNumbers;
            model.setPickedValues(pickedValues);
            model.setListSelectedNumberUpperLine(LIST_SELECTED_UPPER_LINE_NUMBERS);
            model.setListSelectedNumberLowerLine(LIST_SELECTED_LOWER_LINE_NUMBERS);
            //model.setTotalNumbers(LIST_SELECTED_NUMBERS.size());
        } else {
            String pickedValues = "";
            for (String num : LIST_SELECTED_NUMBERS)
                pickedValues = pickedValues + num + ",";
            pickedValues = pickedValues.trim();
            if (pickedValues.length() > 0 && pickedValues.charAt(pickedValues.length() - 1) == ',')
                pickedValues = pickedValues.substring(0, pickedValues.length() - 1);

            model.setListSelectedNumber(LIST_SELECTED_NUMBERS);
            model.setPickedValues(pickedValues);
            //model.setTotalNumbers(LIST_SELECTED_NUMBERS.size());
        }

        model.setGameName(DrawGameData.getSelectedGame().getGameName());
        model.setAmount(Double.parseDouble(tvBetValue.getTag().toString().trim()));
        model.setWinMode(SELECTED_BET_TYPE_DATA.getWinMode());
        model.setBetName(SELECTED_BET_TYPE_DATA.getBetDispName());
        model.setPickName(SELECTED_PICK_TYPE_OBJECT.getName());
        model.setBetCode(SELECTED_BET_TYPE_DATA.getBetCode());
        model.setPickCode(SELECTED_PICK_TYPE_OBJECT.getCode());
        model.setPickConfig(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickConfig());
        model.setBetAmountMultiple((int) (SELECTED_BET_AMOUNT / SELECTED_BET_TYPE_DATA.getUnitPrice()));
        model.setSelectedBetAmount(SELECTED_BET_AMOUNT);
        model.setNumberOfDraws(1);
        model.setNumberOfLines(getNumberOfLines());
        model.setMainBet(true);

        if (SELECTED_PICK_TYPE_OBJECT.getName().contains("QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP")) {
            model.setQuickPick(true);
            model.setQpPreGenerated(true);
        }
        else {
            model.setQuickPick(false);
            model.setQpPreGenerated(false);
        }

        Log.d("draw", "Panel Data: " + model);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("BetDataFiveByNinety", model);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
            /*LIST_MULTI_PANEL.add(model);
            calculateTotalAmount();
            setMultiPanelAdapter();*/
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

}
