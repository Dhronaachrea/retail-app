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
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetyBetAmountBean;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.drawgames.QuickPickBean;
import com.skilrock.retailapp.models.drawgames.QuickPickRequestBean;
import com.skilrock.retailapp.models.drawgames.QuickPickResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.portrait_draw_games.adapter.BetAmountPortraitLuckySixAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.ColorBarPortraitAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.NumbersPortraitLuckySixAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.PickTypePortraitLuckySixAdapter;
import com.skilrock.retailapp.portrait_draw_games.adapter.QpNumbersPortraitAdapter;
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
import java.util.HashSet;
import java.util.Objects;

public class LuckySixMainBetActivity extends BaseActivity implements View.OnClickListener, PickTypePortraitLuckySixAdapter.OnPickTypeClickListener, BetAmountPortraitLuckySixAdapter.OnBetAmountClickListener, QpNumbersPortraitAdapter.OnQpNumberSelection, BetAmountDialog.OnAmountUpdatedListener {

    //private DrawFetchGameDataResponseBean.ResponseData.GameRespVO GAME_DATA;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO SELECTED_BET_TYPE_DATA;
    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType SELECTED_PICK_TYPE_OBJECT;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypeWithQpList;
    private RecyclerView rvColorBar, rvNumbers, rvPickType, rvBetAmount, rvQpNumbers;
    private TextView tvBetValue, tvInstructions, tvQpName, tvQpRange, tvBetTypeOther, tvBetType5, tvBetType4, tvBetType3, tvBetType2, tvBetType1;
    private LinearLayout llBottomBar, llParent, llQpSelectionBar, llOther;
    private int MAX_SELECTION_LIMIT = 0, MIN_SELECTION_LIMIT= 0, SELECTED_BET_AMOUNT;
    private ArrayList<String> LIST_SELECTED_NUMBERS = new ArrayList<>();
    private NumbersPortraitLuckySixAdapter numbersAdapter;
    private boolean isFirstTimeNumberAdapter = true, isFirstTimePickTypeAdapter = true, isFirstTimeBetAmountAdapter = true;
    private PickTypePortraitLuckySixAdapter pickTypeAdapter;
    private HashMap<String, String> MAP_NUMBER_COLOR = new HashMap<>();
    private LuckySixPortraitViewModel viewModel;
    public static long LAST_CLICK_TIME = 0;
    public static int CLICK_GAP = 200;
    private boolean isOtherAmountAvailable;
    private ArrayList<FiveByNinetyBetAmountBean> LIST_BET_AMOUNT = new ArrayList<>();
    private BetAmountDialog betAmountDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            setContentView(R.layout.activity_lucky_six_v2pro);
        else
            setContentView(R.layout.activity_lucky_six);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        initializeWidgets();
        setColorBarAdapter();
        setNumbersAdapter(null);
        setPickTypeAdapter(SELECTED_BET_TYPE_DATA);
        setBetAmountAdapter(SELECTED_BET_TYPE_DATA.getUnitPrice(), SELECTED_BET_TYPE_DATA.getMaxBetAmtMul());
        betValueCalculation();
        Log.d("TAg", "44444444444444444444");
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
            LuckySixMainBetActivity.this.finish();
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
        viewModel               = ViewModelProviders.of(this).get(LuckySixPortraitViewModel.class);
        LinearLayout llReset    = findViewById(R.id.llReset);
        RelativeLayout rlBuy    = findViewById(R.id.rlBuy);
        ImageView ivQpBarClose  = findViewById(R.id.ivQpBarClose);
        CircularTextView ctv1   = findViewById(R.id.circle_1);
        CircularTextView ctv2   = findViewById(R.id.circle_2);
        TextView tvBetValueTag  = findViewById(R.id.tvBetValueTag);
        TextView tvBetAmount    = findViewById(R.id.tvBetAmount);
        tvInstructions          = findViewById(R.id.tvInstructions);
        tvQpName                = findViewById(R.id.tvQpName);
        rvColorBar              = findViewById(R.id.rvColorBar);
        rvNumbers               = findViewById(R.id.rvNumbers);
        rvQpNumbers             = findViewById(R.id.rvQpNumbers);
        rvPickType              = findViewById(R.id.rvPickType);
        rvBetAmount             = findViewById(R.id.rvBetAmount);
        tvBetValue              = findViewById(R.id.tvBetValue);
        llBottomBar             = findViewById(R.id.llBottomBar);
        llParent                = findViewById(R.id.parent);
        llQpSelectionBar        = findViewById(R.id.llQpSelectionBar);
        tvQpRange               = findViewById(R.id.tvQpRange);
        llOther                 = findViewById(R.id.llOther);
        tvBetTypeOther          = findViewById(R.id.tvBetTypeOther);
        tvBetType5              = findViewById(R.id.tvBetType5);
        tvBetType4              = findViewById(R.id.tvBetType4);
        tvBetType3              = findViewById(R.id.tvBetType3);
        tvBetType2              = findViewById(R.id.tvBetType2);
        tvBetType1              = findViewById(R.id.tvBetType1);

        ctv1.setStrokeColor("#e3e3e3");
        ctv2.setStrokeColor("#f5f5f5");
        String tag = getString(R.string.bet_value) + " (" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(LuckySixMainBetActivity.this)) + ")";
        tvBetValueTag.setText(tag);
        String tagAmt = getString(R.string.bet_value) + " (" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(LuckySixMainBetActivity.this)) + ")";
        tvBetAmount.setText(tagAmt);
        viewModel.getLiveData().observe(this, qpObserver);
        //tvBetTypeOther.setOnClickListener(this);
        tvBetType1.setOnClickListener(this);
        tvBetType2.setOnClickListener(this);
        tvBetType3.setOnClickListener(this);
        tvBetType4.setOnClickListener(this);
        tvBetType5.setOnClickListener(this);
        llReset.setOnClickListener(this);
        llOther.setOnClickListener(this);
        rlBuy.setOnClickListener(this);
        ivQpBarClose.setOnClickListener(this);
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
        LuckySixMainBetActivity.this.finish();
    }

    private void setColorBarAdapter() {
        ArrayList<String> listColor = new ArrayList<>();
        for(int index=0; index<8; index++)
            listColor.add(DrawGameData.getSelectedGame().getNumberConfig().getRange().get(0).getBall().get(index).getColor());
        ColorBarPortraitAdapter numbersAdapter = new ColorBarPortraitAdapter(listColor, this);
        rvColorBar.setLayoutManager(new GridLayoutManager(this, 8));
        rvColorBar.setHasFixedSize(true);
        rvColorBar.setItemAnimator(new DefaultItemAnimator());
        rvColorBar.addItemDecoration(new SpacesItemDecoration(8, getResources().getDimensionPixelSize(R.dimen.space_2), false));
        rvColorBar.setAdapter(numbersAdapter);
    }

    private void setNumbersAdapter(ArrayList<String> listQpNumbers) {
        if (listQpNumbers != null) {
            tvInstructions.setVisibility(View.GONE);
            llBottomBar.setVisibility(View.VISIBLE);
        }
        BallClickListener listener = LuckySixMainBetActivity.this::onBallClicked;
        numbersAdapter = new NumbersPortraitLuckySixAdapter(DrawGameData.getSelectedGame().getNumberConfig().getRange().get(0).getBall(), this, listener, listQpNumbers);

        rvNumbers.setLayoutManager(new GridLayoutManager(this, 8));
        rvNumbers.setHasFixedSize(true);
        rvNumbers.setItemAnimator(new DefaultItemAnimator());
        if (isFirstTimeNumberAdapter) {
            rvNumbers.addItemDecoration(new SpacesItemDecoration(8, getResources().getDimensionPixelSize(R.dimen.space_2), false));
            isFirstTimeNumberAdapter = false;
        }
        rvNumbers.setAdapter(numbersAdapter);
    }

    private void setPickTypeAdapter(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO) {
        pickTypeAdapter = new PickTypePortraitLuckySixAdapter(getPickTypeWithQp(betRespVO), this, this);

        rvPickType.setHasFixedSize(true);
        rvPickType.setItemAnimator(new DefaultItemAnimator());
        rvPickType.setLayoutManager(new GridLayoutManager(this, 5));
        if (isFirstTimePickTypeAdapter) {
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
            BetAmountPortraitLuckySixAdapter betAmountAdapter = new BetAmountPortraitLuckySixAdapter(list, this, this);
            rvBetAmount.setLayoutManager(new GridLayoutManager(this, 7));
            rvBetAmount.setHasFixedSize(true);
            rvBetAmount.setItemAnimator(new DefaultItemAnimator());
            rvBetAmount.setNestedScrollingEnabled(false);
            if (isFirstTimeBetAmountAdapter) {
                rvBetAmount.addItemDecoration(new SpacesItemDecoration(7, getResources().getDimensionPixelSize(R.dimen.space_3), false));
            }
            rvBetAmount.setAdapter(betAmountAdapter);*/
        }
        else showToast(getString(R.string.issue_with_bet_amount));

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
            if (LIST_SELECTED_NUMBERS.contains(ballNumber)) {
                if (llQpSelectionBar.getVisibility() == View.VISIBLE) {
                    llQpSelectionBar.setVisibility(View.GONE);
                    rvPickType.setVisibility(View.VISIBLE);
                }
                GradientDrawable background = (GradientDrawable) textView.getBackground();
                background.setColor(Color.parseColor("#ffffff"));
                background.setStroke(2, Color.parseColor("#aaaaaa"));
                textView.setTypeface(null, Typeface.NORMAL);
                textView.setTextColor(Color.parseColor("#aaaaaa"));
                LIST_SELECTED_NUMBERS.remove(ballNumber);
                String selectedPickTypeCode = SELECTED_PICK_TYPE_OBJECT.getCode();
                if (!(selectedPickTypeCode.equalsIgnoreCase("Direct6") || selectedPickTypeCode.equalsIgnoreCase("Perm6")))
                    switchToPickType("Direct6");
                if (SELECTED_PICK_TYPE_OBJECT.getName().equalsIgnoreCase("QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                    switchToPickType("Direct6");
                if (SELECTED_PICK_TYPE_OBJECT.getName().contains("Perm QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                    switchToPickType("Perm6");
            }
            else {
                if (LIST_SELECTED_NUMBERS.size() < MAX_SELECTION_LIMIT) {
                    if (llQpSelectionBar.getVisibility() == View.VISIBLE) {
                        llQpSelectionBar.setVisibility(View.GONE);
                        rvPickType.setVisibility(View.VISIBLE);
                    }
                    textView.setTextColor(Color.parseColor("#ffffff"));
                    textView.setTypeface(null, Typeface.BOLD);
                    GradientDrawable background = (GradientDrawable) textView.getBackground();
                    background.setColor(Color.parseColor(ballColor));
                    background.setStroke(2, Color.parseColor(ballColor));
                    LIST_SELECTED_NUMBERS.add(ballNumber);
                    if (LIST_SELECTED_NUMBERS.size() == MAX_SELECTION_LIMIT)
                        checkIfColorPickTypeIsMade();
                    if (SELECTED_PICK_TYPE_OBJECT.getName().contains("Perm QP") && SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"))
                        switchToPickType("Perm6");
                }
                else {
                    String msg = MAX_SELECTION_LIMIT > 1 ? getString(R.string.cannot_select_more) + " " + MAX_SELECTION_LIMIT + " " + getString(R.string.numbers_for) + " " + SELECTED_PICK_TYPE_OBJECT.getName() + "!" : getString(R.string.cannot_select_more) + " " + MAX_SELECTION_LIMIT + " " +  getString(R.string.number_for) + " " + SELECTED_PICK_TYPE_OBJECT.getName() + "!";
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
                if (LIST_SELECTED_NUMBERS.size() < MIN_SELECTION_LIMIT) {
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
                    switchToPickType("Direct6");
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
                        betAmountDialog = new BetAmountDialog(LuckySixMainBetActivity.this, LIST_BET_AMOUNT,this);
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

    @Override
    public void onPickTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType) {
        tvInstructions.setText(pickType.getDescription());
        SELECTED_PICK_TYPE_OBJECT = pickType;
        getSelectionLimits();
        resetSelectedNumberList();
        //if (SELECTED_BET_TYPE_OBJECT != null)
        //    setBetAmountAdapter(SELECTED_BET_TYPE_OBJECT.getUnitPrice(), SELECTED_BET_TYPE_OBJECT.getMaxBetAmtMul());
        //if ((pickType.getName().equalsIgnoreCase("Direct6 QP") || pickType.getName().equalsIgnoreCase("Perm6 QP")) && pickType.getRange().get(0).getPickMode().equalsIgnoreCase("QP")) {
        if (pickType.getRange().get(0).getPickMode().equalsIgnoreCase("QP")) {
            qpSetup();
        } else {
            pickTypeMainColor();
        }
    }

    private void switchToPickType(String gameCode) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> type = getPickTypeWithQp(SELECTED_BET_TYPE_DATA);
        for (int index = 0; index < type.size(); index++) {
            DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType = type.get(index);
            if (pickType.getCode().equalsIgnoreCase(gameCode)) {
                tvInstructions.setText(pickType.getDescription());
                SELECTED_PICK_TYPE_OBJECT = pickType;
                getSelectionLimits();
                if (pickTypeAdapter != null)
                    pickTypeAdapter.onClick(index, false);
                break;
            }
        }
    }

    private void switchToColorPickType(String color) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> type = getPickTypeWithQp(SELECTED_BET_TYPE_DATA);
        for (int index = 0; index < type.size(); index++) {
            DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType = type.get(index);
            if (pickType.getCode().toLowerCase().contains(color.toLowerCase())) {
                tvInstructions.setText(pickType.getDescription());
                SELECTED_PICK_TYPE_OBJECT = pickType;
                getSelectionLimits();
                if (pickTypeAdapter != null)
                    pickTypeAdapter.onClick(index, false);
                break;
            }
        }
    }

    private void checkIfColorPickTypeIsMade() {
        HashSet<String> hashSet = new HashSet<>();
        for (String number: LIST_SELECTED_NUMBERS)
            hashSet.add(MAP_NUMBER_COLOR.get(number));

        if (hashSet.size() == 1) {
            ArrayList<String> list = new ArrayList<>(hashSet);
            switchToColorPickType(list.get(0));
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
        if (LIST_SELECTED_NUMBERS.size() >= MIN_SELECTION_LIMIT) {
            double amount = SELECTED_BET_AMOUNT * getNumberOfLines();
            int amt = (int) amount;
            //String strAmount = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(LuckySixMainBetActivity.this)) + amt;
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

        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker1AgainstAll"))
            return 89;
        return 1;
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
        setNumbersAdapter(null);
        tvBetValue.setText("0");
        tvBetValue.setTag(0);
        showInstructionBar();
    }

    private void pickTypeMainColor() {
        if (SELECTED_BET_TYPE_DATA != null && SELECTED_BET_TYPE_DATA.getBetCode().trim().equalsIgnoreCase("Direct6")) {
            String code = SELECTED_PICK_TYPE_OBJECT.getCode().trim();
            if (code.equalsIgnoreCase("RedBalls") || code.equalsIgnoreCase("GreenBalls") ||
                    code.equalsIgnoreCase("BlueBalls") || code.equalsIgnoreCase("MagentaBalls") ||
                    code.equalsIgnoreCase("BrownBalls") || code.equalsIgnoreCase("CyanBalls") ||
                    code.equalsIgnoreCase("OrangeBalls") || code.equalsIgnoreCase("GreyBalls")) {
                setPreSelectedNumbers(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickValue());
            }
            else if (code.equalsIgnoreCase("Hot6")) {
                String number = DrawGameData.getSelectedGame().getHotNumbers();
                number = number.replace("[", "");
                number = number.replace("]", "");
                number = number.replaceAll(" ", "");
                Log.d("draw", "Hot6 Numbers: " + number);
                setPreSelectedNumbers(number);
            }
            else if (code.equalsIgnoreCase("Cold6")) {
                String number = DrawGameData.getSelectedGame().getColdNumbers();
                number = number.replace("[", "");
                number = number.replace("]", "");
                number = number.replaceAll(" ", "");
                number = number.replaceAll(" ", "");
                Log.d("draw", "Cold6 Numbers: " + number);
                setPreSelectedNumbers(number);
            }
        }
    }

    private void setPreSelectedNumbers(String strNumber) {
        LIST_SELECTED_NUMBERS.clear();
        tvBetValue.setText("0");
        tvBetValue.setTag(0);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(strNumber.split(",")));
        for (String num: list)
            LIST_SELECTED_NUMBERS.add(num.length() == 1 ? "0" + num : num);
        setNumbersAdapter(new ArrayList<>(LIST_SELECTED_NUMBERS));
        betValueCalculation();
        showInstructionBar();
    }

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
        if (LIST_SELECTED_NUMBERS.isEmpty()) {
            tvInstructions.setVisibility(View.VISIBLE);
            llBottomBar.setVisibility(View.GONE);
        } else {
            tvInstructions.setVisibility(View.GONE);
            llBottomBar.setVisibility(View.VISIBLE);
        }
    }

    private void resetGame() {
        tvBetValue.setText("0");
        resetSelectedNumberList();
        setPickTypeAdapter(SELECTED_BET_TYPE_DATA);
        llQpSelectionBar.setVisibility(View.GONE);
        rvPickType.setVisibility(View.VISIBLE);
    }

    @Override
    public void onQpNumberSelection(int count) {
        callQuickPickApi(Integer.toString(count));
    }

    private void callQuickPickApi(String numbersPicked) {
        if (!Utils.isNetworkConnected(LuckySixMainBetActivity.this)) {
            Toast.makeText( LuckySixMainBetActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
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
            setNumbersAdapter(new ArrayList<>(LIST_SELECTED_NUMBERS));
            betValueCalculation();
        } else {
            if (Utils.checkForSessionExpiryActivity(LuckySixMainBetActivity.this, response.getResponseCode(), LuckySixMainBetActivity.this)) return;

            String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
            showToast(errorMsg);
        }
    };

    private void addBet() {
        PanelBean model = new PanelBean();
        String pickedValues = "";
        for (String num : LIST_SELECTED_NUMBERS)
            pickedValues = pickedValues + num + ",";
        pickedValues = pickedValues.trim();
        if (pickedValues.length() > 0 && pickedValues.charAt(pickedValues.length() - 1) == ',')
            pickedValues = pickedValues.substring(0, pickedValues.length() - 1);

        model.setListSelectedNumber(LIST_SELECTED_NUMBERS);
        model.setPickedValues(pickedValues);
        //model.setTotalNumbers(LIST_SELECTED_NUMBERS.size());

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
        returnIntent.putExtra("BetData", model);
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
