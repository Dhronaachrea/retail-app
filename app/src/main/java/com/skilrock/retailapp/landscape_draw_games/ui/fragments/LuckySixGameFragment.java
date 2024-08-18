package com.skilrock.retailapp.landscape_draw_games.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.drawgame.BetAmountAdapter;
import com.skilrock.retailapp.adapter.drawgame.BetTypeAdapter;
import com.skilrock.retailapp.adapter.drawgame.ColorBallAdapter;
import com.skilrock.retailapp.adapter.drawgame.ColorBallFiveBallAdapter;
import com.skilrock.retailapp.adapter.drawgame.ColorBallLastBallAdapter;
import com.skilrock.retailapp.adapter.drawgame.ColorBarAdapter;
import com.skilrock.retailapp.adapter.drawgame.LastWinningLuckySixAdapter;
import com.skilrock.retailapp.adapter.drawgame.MultiPanelAdapter;
import com.skilrock.retailapp.adapter.drawgame.NumbersAdapter;
import com.skilrock.retailapp.adapter.drawgame.SchemaAdapter;
import com.skilrock.retailapp.adapter.drawgame.SelectedNumbersAdapter;
import com.skilrock.retailapp.dialog.BetAmountDialogLand;
import com.skilrock.retailapp.dialog.DrawDialogLand;
import com.skilrock.retailapp.interfaces.BallClickListenerLandscape;
import com.skilrock.retailapp.interfaces.FirstColorBallSelectListener;
import com.skilrock.retailapp.interfaces.FirstFiveBallColorListener;
import com.skilrock.retailapp.interfaces.FiveBallColorListener;
import com.skilrock.retailapp.interfaces.InputListener;
import com.skilrock.retailapp.interfaces.LastBallColorListener;
import com.skilrock.retailapp.interfaces.PatternClickListener;
import com.skilrock.retailapp.interfaces.RefreshGameListener;
import com.skilrock.retailapp.landscape_draw_games.adapter.LuckySixPickTypeAdapter;
import com.skilrock.retailapp.landscape_draw_games.adapter.QuickPickNumberAdapter;
import com.skilrock.retailapp.landscape_draw_games.dialog.ConfirmPurchaseDialog;
import com.skilrock.retailapp.landscape_draw_games.dialog.MoreResultDialog;
import com.skilrock.retailapp.landscape_draw_games.ui.activities.DrawGameBaseActivity;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataRequestBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.DrawRequestBean;
import com.skilrock.retailapp.models.drawgames.DrawSchemaByGameResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetyBetAmountBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleRequestBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.drawgames.QuickPickRequestBean;
import com.skilrock.retailapp.models.drawgames.QuickPickResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.portrait_draw_games.ui.PrintDrawGameActivity;
import com.skilrock.retailapp.portrait_draw_games.ui.WinningClaimLandscapeActivity;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.LuckySixBallColorMap;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import com.skilrock.retailapp.viewmodels.drawgames.LuckySixViewModel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;


public class LuckySixGameFragment extends Fragment implements BetTypeAdapter.OnBetTypeClickListener, LuckySixPickTypeAdapter.OnPickTypeClickListener, View.OnClickListener, BetAmountAdapter.OnBetAmountClickListener,
        DrawDialogLand.SelectedDrawsListener, FirstColorBallSelectListener, LastBallColorListener, PatternClickListener, MultiPanelAdapter.OnPanelDeleteListener,
        FiveBallColorListener, FirstFiveBallColorListener, InputListener, BallClickListenerLandscape, RefreshGameListener,
        ConfirmPurchaseDialog.ConfirmPurchaseListener, BetAmountDialogLand.OnAmountUpdatedListener {

    private ConfirmPurchaseDialog confirmPurchaseDialog;
    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameResponse;
    byte[] bytes = null;
    private static final int WHITE = 0xFFFFFFFF;
    private AlertDialog alertDialog;
    private static final int BLACK = 0xFF000000;
    private final int REQUEST_CODE_PRINT = 1111;
    private HashMap<String, String> mapNumberConfig = new HashMap<>();
    private RecyclerView rvBetTypes, rvNumbers, rvLastWinningResult, rvPickType, rvSelectedNumbers, rvBetAmount, rvColorBar, rvLastDraws;
    private TextView tvNoOfDraw, tvNoOfAdvanceDraw, tvTotalAmount, tvSelectionNumberTitle;
    private Button btnPlus, btnMinus;
    private MoreResultDialog moreResultDialog;
    private TextView tvDescription, tvDescriptionTop;
    private TextView tvBetValue, tvDrawCount;
    private TextView tvAddBet;
    private ImageView imageSwitchBets;
    private TextView tvFirstBallGreater;
    private TextView tvFirstBallLesser;
    private TextView tvFirstBallEven;
    private TextView tvFirstBallOdd;
    private TextView tvLastBallGreater;
    private TextView tvLastBallLesser;
    private TextView tvLastBallEven;
    private TextView tvLastBallOdd, tvBetAmountTitle, tvBetValueLabel, tvResetSideBet, tvAllBallHeading;
    private TextView tvFirstFiveBallGreater;
    private TextView tvFirstFiveBallLesser;
    private TextView tvMoreOdd;
    private TextView tvMoreEven;
    private TextView tvTitlePattern;
    private TextView tvFirstBallColor;
    private TextView tvFirstBallSum;
    private TextView tvFirstBallEvenOdd;
    private TextView tvLastBallColor;
    private TextView tvLastBallSum;
    private TextView tvLastBallEvenOdd, tvViewMore;
    public boolean isQPSelected = false;
    private TextView tvFiveBallColor, loadingText;
    private TextView tvFiveBallSum;
    private TextView tvFiveBallEvenOdd, tvSchemaLoading;
    //private TextView tvPayout;
    public int MAX_SELECTION_LIMIT = 0, MIN_SELECTION_LIMIT = 0, SELECTED_BET_AMOUNT = 0, NUMBER_OF_DRAWS = 1, INDEX_CONSECUTIVE_DRAWS_LIST = 0;
    private int MAX_SELECTION_LOWER_LIMIT_BANKER = 0, MAX_SELECTION_UPPER_LIMIT_BANKER = 0;
    private int MIN_SELECTION_LOWER_LIMIT_BANKER = 0, MIN_SELECTION_UPPER_LIMIT_BANKER = 0;
    private LinearLayout llWinningClaim;
    private RelativeLayout tvTitleAddTpPurchase;
    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO SELECTED_BET_TYPE_OBJECT;
    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType SELECTED_PICK_TYPE_OBJECT;
    private LuckySixViewModel viewModel;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private ArrayList<String> LIST_SELECTED_NUMBERS = new ArrayList<>();
    private ArrayList<String> LIST_CONSECUTIVE_DRAWS = new ArrayList<>();
    private ArrayList<PanelBean> LIST_MULTI_PANEL = new ArrayList<>();
    private SelectedNumbersAdapter selectedNumbersAdapter;
    private NumbersAdapter numbersAdapter;
    private QuickPickNumberAdapter quickPickNumberAdapter;
    private BetTypeAdapter betTypeAdapter;
    private CardView cardBet, cardSchema;
    private RecyclerView rvSchema, rvColorBallsFirstBall, rvColorBallsLastBall, rvColorBallFirstFive, rvPurchase, rvQuickPickNumber;
    private ArrayList<CardView> colorBarList = new ArrayList<>();
    private LinearLayout viewSideBet, viewPickType, viewBetType, viewSelectedNumbers,
            firstBallGreater, firstBallLesser, firstBallEven, firstBallOdd, lastBallLesser, lastBallGreater, lastBallEven,
            lastBallOdd, firstFiveBallGreater, firstFiveBallLesser, moreOdd, moreEven, viewPayout, viewPatternContainer;
    private Toast mToast = null;
    private TextView tvOtherAmount;
    private boolean IS_ADVANCE_PLAY = false;
    private HashMap<String, PanelBean> sideBetMap = new HashMap<>();
    private ArrayList<DrawRequestBean> mainBetMap = new ArrayList<>();
    private MultiPanelAdapter multiPanelAdapter;
    private ArrayList<String> LIST_ADVANCE_DRAWS = new ArrayList<>();
    private boolean isSideBetSelected = false;
    private RelativeLayout progressContainer, viewDescription;
    private CircularTextView tvOrTitle;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> schemaArray = new ArrayList<>();
    private View VIEW, vieweSideBetAllBall;
    private DrawSchemaByGameResponseBean SCHEMA_RESPONSE = null;
    private TextView tvFirstBallColorValue, tvFirstBallSumValue, tvFirstBallEvenOddValue,
            tvLastBallColorValue, tvLastBallSumValue, tvLastBallEvenOddValue, tvFirstFiveBallColorValue,
            tvFirstFiveBallSumValue, tvMoreEvenOddValue, firstBallColorName, lastBallColorName,
            allBallColorname;
    private ProgressBar progressBar;
    private Space space;
    private View viewSaperator, viewSaperator2;
    private DrawDialogLand drawDialog;
    private CardView cardBetAmount;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> matchDetailList = new ArrayList<>();
    private SchemaAdapter schemaAdapter;
    private LinearLayout viewOtherAmount, viewReprint, viewCancel, viewResult;
    private BetAmountAdapter betAmountAdapter;
    private RecyclerView.OnScrollListener onScrollListener = null;
    private boolean isScrollable = false;
    private double UNIT_PRICE = 0;
    private int BET_AMOUNT_MULTIPLE = 0;
    private LuckySixPickTypeAdapter pickTypeAdapter;
    private LinearLayoutManager linearLayoutManager;

    public LuckySixGameFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(LuckySixViewModel.class);
        viewModel.getLiveDataQP().observe(this, qpObserver);
        viewModel.getLiveDataSale().observe(this, observerSale);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lucky_six_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VIEW = view;
        initializeWidgets(view);
        schemaArray = gameResponse.getGameSchemas().getMatchDetail();

        setWinModeInSchema(gameResponse.getBetRespVOs());
        setBetTypeAdapter();
        setLastWinningAdapter();
        new Handler().postDelayed(() -> setNumbersAdapter(null), 300);
        setColorBarAdapter();
        setColorBallFirstBallAdapter();
        setColorBallLastBallAdapter();
        //setColorBallFiveBallAdapter();
        setSideBetUI();
    }

    private void callSchemaApi() {
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(menuBean, getActivity(), "getSchemaByGame");
        if (urlBean != null) {
            DrawFetchGameDataRequestBean model = new DrawFetchGameDataRequestBean();
            model.setRetailerId(PlayerData.getInstance().getUserId());
            model.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            viewModel.callSchemaByGame(urlBean, model, gameResponse.getGameCode());
        }
    }

    private void callQuickPickApi(String numbersPicked) {
        //UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(menuBean, getActivity(), "quickPick");
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(menuBean, getActivity(), "quickPick");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(getActivity());
            QuickPickRequestBean model = new QuickPickRequestBean();
            model.setNoOfLines(1);
            model.setNumbersPicked(numbersPicked);
            viewModel.callQPApi(urlBean, model, gameResponse.getGameCode());
        }
    }

    private void callSaleApi() {
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(menuBean, getActivity(), "buy");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(getActivity());
            FiveByNinetySaleRequestBean model = new FiveByNinetySaleRequestBean();
            model.setMerchantCode(urlBean.getUserName());

            FiveByNinetySaleRequestBean.MerchantData merchantData = new FiveByNinetySaleRequestBean.MerchantData();
            merchantData.setUserId(Integer.parseInt(PlayerData.getInstance().getUserId()));
            merchantData.setUserName(PlayerData.getInstance().getUsername());
            merchantData.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setMerchantData(merchantData);

            model.setGameCode(gameResponse.getGameCode());
            model.setCurrencyCode(gameResponse.getCurrencyRespVOs().get(0).getCurrencyCode());
            model.setPurchaseDeviceId(Utils.getDeviceSerialNumber());
            model.setPurchaseDeviceType("ANDROID_TERMINAL");
            if (getContext() != null)
                model.setLastTicketNumber(SharedPrefUtil.getLastTicketNumber(getContext(), SharedPrefUtil.getString(getContext(), SharedPrefUtil.USERNAME)));
            else
                model.setLastTicketNumber("0");

            model.setIsAdvancePlay(IS_ADVANCE_PLAY);
            model.setNoOfDraws(NUMBER_OF_DRAWS);

            ArrayList<FiveByNinetySaleRequestBean.DrawData> list = new ArrayList<>();
            if (IS_ADVANCE_PLAY && LIST_ADVANCE_DRAWS.size() > 0) {
                for (String drawId : LIST_ADVANCE_DRAWS) {
                    FiveByNinetySaleRequestBean.DrawData drawData = new FiveByNinetySaleRequestBean.DrawData();
                    drawData.setDrawId(drawId);
                    list.add(drawData);
                }
            }
            model.setDrawData(list);

            ArrayList<FiveByNinetySaleRequestBean.PanelData> listPanel = new ArrayList<>();
            for (PanelBean panelData : LIST_MULTI_PANEL) {
                FiveByNinetySaleRequestBean.PanelData modelPanel = new FiveByNinetySaleRequestBean.PanelData();
                modelPanel.setBetType(panelData.getBetCode());
                modelPanel.setPickType(panelData.getPickCode());
                modelPanel.setPickConfig(panelData.getPickConfig());
                modelPanel.setBetAmountMultiple(panelData.getBetAmountMultiple());
                modelPanel.setQuickPick(panelData.getQuickPick());
                modelPanel.setQpPreGenerated(panelData.getQpPreGenerated());
                modelPanel.setPickedValues(panelData.getPickedValues());
                modelPanel.setTotalNumbers(panelData.getTotalNumbers());
                listPanel.add(modelPanel);
            }
            model.setPanelData(listPanel);
            model.setModelCode(Utils.getModelCode());
            viewModel.callSaleApi(urlBean, model);
        }
    }

    private void initializeWidgets(View view) {
        tvNoOfDraw = view.findViewById(R.id.tvNoOfDraw);
        TextView tvBuyTicket = view.findViewById(R.id.tvBuyTicket);
        tvTitleAddTpPurchase = view.findViewById(R.id.title_add_to_purchase);
        rvNumbers = view.findViewById(R.id.rv_numbers);
        rvColorBar = view.findViewById(R.id.rv_color_bar);
        rvPickType = view.findViewById(R.id.rv_pick_types);
        rvSelectedNumbers = view.findViewById(R.id.rv_selected_numbers_box);
        rvSchema = view.findViewById(R.id.rv_rules);
        cardBet = view.findViewById(R.id.card_bet);
        cardSchema = view.findViewById(R.id.card_rules);
        viewSideBet = view.findViewById(R.id.view_side_bet);
        imageSwitchBets = view.findViewById(R.id.image_switch_bets);
        viewPickType = view.findViewById(R.id.view_pick_type);
        viewBetType = view.findViewById(R.id.view_bet_type);
        progressBar = view.findViewById(R.id.progress);
        space = view.findViewById(R.id.space);
        viewDescription = view.findViewById(R.id.view_description);
        viewPatternContainer = view.findViewById(R.id.view_pattern_container);
        viewSaperator = view.findViewById(R.id.view_saperator);
        viewSaperator2 = view.findViewById(R.id.view_saperator_2);
        viewSaperator2.setVisibility(View.GONE);
        TextView tvPayoutGameName = view.findViewById(R.id.tvPayoutGameName);
        TextView tvGameName = view.findViewById(R.id.tvGameName);
        rvLastDraws = view.findViewById(R.id.rv_balls);
        tvTotalAmount = view.findViewById(R.id.tv_total_bet_amount);
        viewSelectedNumbers = view.findViewById(R.id.view_selected_numbers);
        tvNoOfAdvanceDraw = view.findViewById(R.id.tvNoOfAdvanceDraw);
        RelativeLayout ivAdvanceDraw = view.findViewById(R.id.ivAdvanceDraw);
        TextView tvReset = view.findViewById(R.id.tvReset);
        tvResetSideBet = view.findViewById(R.id.tvReset_sidebet);
        tvAllBallHeading = view.findViewById(R.id.tv_all_ball_heading);
        tvAllBallHeading.setText(getString(R.string.first_five_balls));
        tvResetSideBet.setVisibility(View.GONE);
        viewPatternContainer.setVisibility(View.GONE);
        viewOtherAmount = view.findViewById(R.id.llOther);
        viewReprint = view.findViewById(R.id.llReprint);
        viewCancel = view.findViewById(R.id.llCancel);
        viewResult = view.findViewById(R.id.llResult);
        tvOtherAmount = view.findViewById(R.id.tvBetTypeOther);

        btnPlus = view.findViewById(R.id.btnPlus);
        btnMinus = view.findViewById(R.id.btnMinus);
        llWinningClaim = view.findViewById(R.id.llWinningClaim);
        rvColorBallsFirstBall = view.findViewById(R.id.rv_color_balls_first_ball);
        progressContainer = view.findViewById(R.id.progress_container);

        rvBetTypes = view.findViewById(R.id.rv_bet_types);
        rvPurchase = view.findViewById(R.id.rv_purchase);
        rvLastWinningResult = view.findViewById(R.id.rv_balls);
        rvBetAmount = view.findViewById(R.id.rv_bet_amount);
        rvQuickPickNumber = view.findViewById(R.id.rv_quick_pick_numbers);
        tvViewMore = view.findViewById(R.id.tv_view_more);
        loadingText = view.findViewById(R.id.loading_text);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvDescriptionTop = view.findViewById(R.id.tvDescription_top);
        tvBetValue = view.findViewById(R.id.tvBetValue);
        tvBetValueLabel = view.findViewById(R.id.tv_bet_value_label);
        tvAddBet = view.findViewById(R.id.tvAddBet);
        tvFirstBallGreater = view.findViewById(R.id.tv_first_ball_greater);
        tvFirstBallLesser = view.findViewById(R.id.tv_first_ball_lesser);
        tvLastBallGreater = view.findViewById(R.id.tv_last_ball_greater);
        tvLastBallLesser = view.findViewById(R.id.tv_last_ball_lesser);
        tvFirstBallEven = view.findViewById(R.id.tv_first_ball_even);
        tvFirstBallOdd = view.findViewById(R.id.tv_first_ball_odd);
        tvLastBallEven = view.findViewById(R.id.tv_last_ball_even);
        tvLastBallOdd = view.findViewById(R.id.tv_last_ball_odd);
        tvMoreEven = view.findViewById(R.id.tv_more_even);
        tvMoreOdd = view.findViewById(R.id.tv_more_odd);
        tvFirstFiveBallGreater = view.findViewById(R.id.tv_first_five_ball_greater);
        tvFirstFiveBallLesser = view.findViewById(R.id.tv_first_five_ball_lesser);
        tvTitlePattern = view.findViewById(R.id.tv_title_pattern);
        tvTitlePattern.setVisibility(View.GONE);
        tvBetAmountTitle = view.findViewById(R.id.tv_bet_amount_title);
        tvOrTitle = view.findViewById(R.id.tv_or_title);
        tvFirstBallColor = view.findViewById(R.id.tv_first_ball_color);
        tvFirstBallSum = view.findViewById(R.id.tv_first_ball_sum);
        tvFirstBallEvenOdd = view.findViewById(R.id.tv_first_ball_even_odd);
        tvLastBallColor = view.findViewById(R.id.tv_last_ball_color);
        tvLastBallSum = view.findViewById(R.id.tv_last_ball_sum);
        tvLastBallEvenOdd = view.findViewById(R.id.tv_last_ball_even_odd);
        tvFiveBallColor = view.findViewById(R.id.tv_five_ball_color);
        tvFiveBallSum = view.findViewById(R.id.tv_five_ball_sum);
        tvFiveBallEvenOdd = view.findViewById(R.id.tv_five_ball_even_odd);
        tvSchemaLoading = view.findViewById(R.id.tv_schema_loading);
        tvSelectionNumberTitle = view.findViewById(R.id.tv_pick_selection_title);
        tvDrawCount = view.findViewById(R.id.tv_draw_count);
        tvFiveBallColor.setVisibility(View.GONE);

        tvFirstBallColorValue = view.findViewById(R.id.tv_first_color_ball_value);
        tvFirstBallSumValue = view.findViewById(R.id.tv_first_ball_sum_value);
        tvFirstBallEvenOddValue = view.findViewById(R.id.tv_first_ball_even_odd_value);
        tvLastBallColorValue = view.findViewById(R.id.tv_last_ball_color_value);
        tvLastBallSumValue = view.findViewById(R.id.tv_last_ball_sum_value);
        tvLastBallEvenOddValue = view.findViewById(R.id.tv_last_ball_even_odd_value);
        tvFirstFiveBallColorValue = view.findViewById(R.id.tv_first_five_ball_color_value);
        tvFirstFiveBallSumValue = view.findViewById(R.id.tv_first_five_ball_sum_value);
        tvMoreEvenOddValue = view.findViewById(R.id.tv_more_even_odd_value);
        firstBallColorName = view.findViewById(R.id.tv_first_ball_color_name);
        lastBallColorName = view.findViewById(R.id.tv_last_ball_color_name);
        allBallColorname = view.findViewById(R.id.tv_all_ball_color_name);

        allBallColorname.setVisibility(View.GONE);

        RelativeLayout rlBuyNowContainer = view.findViewById(R.id.rlBuyNowContainer);

        tvOrTitle.setSolidColor("#606060");
        tvOrTitle.setStrokeColor("#606060");

        firstBallGreater = view.findViewById(R.id.first_ball_greater);
        firstBallLesser = view.findViewById(R.id.first_ball_lesser);
        lastBallLesser = view.findViewById(R.id.last_ball_lesser);
        lastBallGreater = view.findViewById(R.id.last_ball_greater);
        firstBallEven = view.findViewById(R.id.first_ball_even);
        firstBallOdd = view.findViewById(R.id.first_ball_odd);
        lastBallEven = view.findViewById(R.id.last_ball_even);
        lastBallOdd = view.findViewById(R.id.last_ball_odd);
        moreEven = view.findViewById(R.id.more_even);
        moreOdd = view.findViewById(R.id.more_odd);
        firstFiveBallGreater = view.findViewById(R.id.first_five_ball_greater);
        firstFiveBallLesser = view.findViewById(R.id.first_five_ball_lesser);
        viewSelectedNumbers = view.findViewById(R.id.view_selected_numbers);
        //tvPayout = view.findViewById(R.id.tv_payout);
        viewPayout = view.findViewById(R.id.view_payout);
        rvColorBallsFirstBall = view.findViewById(R.id.rv_color_balls_first_ball);
        rvColorBallsLastBall = view.findViewById(R.id.rv_color_balls_last);
        rvColorBallFirstFive = view.findViewById(R.id.rv_five_ball_color);
        cardBetAmount = view.findViewById(R.id.card_bet_amount);

        tvReset.setOnClickListener(this);
        tvAddBet.setOnClickListener(this);
        imageSwitchBets.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        tvResetSideBet.setOnClickListener(this);
        ivAdvanceDraw.setOnClickListener(this);
        firstBallGreater.setOnClickListener(this);
        firstBallLesser.setOnClickListener(this);
        firstBallEven.setOnClickListener(this);
        firstBallOdd.setOnClickListener(this);
        lastBallLesser.setOnClickListener(this);
        lastBallGreater.setOnClickListener(this);
        lastBallEven.setOnClickListener(this);
        lastBallOdd.setOnClickListener(this);
        tvViewMore.setOnClickListener(this);
        firstFiveBallLesser.setOnClickListener(this);
        firstFiveBallGreater.setOnClickListener(this);
        moreOdd.setOnClickListener(this);
        moreEven.setOnClickListener(this);
        llWinningClaim.setOnClickListener(this);
        rlBuyNowContainer.setOnClickListener(this);
        viewPayout.setOnClickListener(this);
        tvSchemaLoading.setOnClickListener(this);
        viewOtherAmount.setOnClickListener(this);
        viewReprint.setOnClickListener(this);
        viewResult.setOnClickListener(this);
        viewCancel.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            gameResponse = bundle.getParcelable("GameResponse");
            menuBean = bundle.getParcelable("MenuBean");
            if (gameResponse != null && gameResponse.getNumberConfig().getRange().size() > 0) {
                DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range ballDataList = gameResponse.getNumberConfig().getRange().get(0);
                for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball : ballDataList.getBall())
                    mapNumberConfig.put(ball.getNumber(), ball.getColor());

                LuckySixBallColorMap.setMapNumberConfig(mapNumberConfig);
            }

            if (gameResponse != null && !TextUtils.isEmpty(gameResponse.getConsecutiveDraw())) {
                tvPayoutGameName.setText(String.format("%s %s", getString(R.string.payout_s), gameResponse.getGameName()));
                tvGameName.setText(gameResponse.getGameName());
                LIST_CONSECUTIVE_DRAWS = new ArrayList<>(Arrays.asList(gameResponse.getConsecutiveDraw().split(",")));
                if (LIST_CONSECUTIVE_DRAWS.size() > 0)
                    tvNoOfDraw.setText(LIST_CONSECUTIVE_DRAWS.get(0));
            }
        }

        if (!LIST_MULTI_PANEL.isEmpty()) {
            cardSchema.setVisibility(cardSchema.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            cardBet.setVisibility(cardBet.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            setMultiPanelAdapter();
        }

        imageSwitchBets.setBackground(getActivity().getResources().getDrawable(R.drawable.b_1));
        ((DrawGameBaseActivity) getActivity()).registerListener(this);
        if (((DrawGameBaseActivity) getActivity()).isLoadingFragment(gameResponse.getGameCode())) {
            showLoader(true, getString(R.string.loading));
        }
        btnMinus.setBackgroundResource(R.drawable.minus_grey);
        cardBetAmount.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        tvDrawCount.setText("1 "+getString(R.string.draw));
    }

    Observer<QuickPickResponseBean> qpObserver = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null) {
            Utils.showCustomErrorDialog(getActivity(), "Draw Games", getString(R.string.something_went_wrong));
        } else if (response.getResponseCode() == 0) {
            LIST_SELECTED_NUMBERS.clear();
            tvBetValue.setText(Utils.getAmountWithCurrency("0", getActivity()));
            tvBetValue.setTag(0);
            String strNumber = response.getResponseData().get(0);
            ArrayList<String> list = new ArrayList<>(Arrays.asList(strNumber.split(",")));
            for (String num : list)
                LIST_SELECTED_NUMBERS.add(num.length() == 1 ? "0" + num : num);
            setNumbersAdapter(new ArrayList<>(LIST_SELECTED_NUMBERS));
            if (selectedNumbersAdapter != null)
                setSelectedNumberAdapter();
            betValueCalculation();

            tvDescription.setText(SELECTED_PICK_TYPE_OBJECT.getDescription());
            tvDescriptionTop.setVisibility(View.GONE);
            tvDescription.setVisibility(View.VISIBLE);

        } else {
            if (Utils.checkForSessionExpiryActivity(getActivity(), response.getResponseCode(), getActivity())) return;

            String errorMsg = Utils.getResponseMessage(getActivity(), "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(getActivity(), "Draw Games", errorMsg);
        }
    };

    private void setLastWinningAdapter() {
        LastWinningLuckySixAdapter lastWinningAdapter = new LastWinningLuckySixAdapter(getLastWinning(gameResponse.getLastDrawWinningResultVOs()),
                mapNumberConfig, getContext(), gameResponse.getGameCode());

        rvLastDraws.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvLastDraws.setHasFixedSize(true);
        rvLastDraws.setItemAnimator(new DefaultItemAnimator());
        rvLastDraws.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.HORIZONTAL));

        rvLastDraws.setAdapter(lastWinningAdapter);
    }

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> getLastWinning(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> gameRespVO) {
        Comparator<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> compareByOrder =
                (o1, o2) -> o2.getLastDrawDateTime().compareTo(o1.getLastDrawDateTime());
        Collections.sort(gameRespVO, compareByOrder);
        return gameRespVO;
    }

    private void setColorBarAdapter() {
        ArrayList<String> listColor = new ArrayList<>();
        ColorBarAdapter numbersAdapter = new ColorBarAdapter(listColor, getContext());
        for (int index = 0; index < 16; index++)
            listColor.add(gameResponse.getNumberConfig().getRange().get(0).getBall().get(index).getColor());
        rvColorBar.setLayoutManager(new GridLayoutManager(getContext(), 16));
        rvColorBar.setHasFixedSize(true);
        rvColorBar.setItemAnimator(new DefaultItemAnimator());
        rvColorBar.setAdapter(numbersAdapter);
    }

    private void setNumbersAdapter(ArrayList<String> listQpNumbers) {
        numbersAdapter = new NumbersAdapter(gameResponse.getNumberConfig().getRange().get(0).getBall(), getActivity(), this, listQpNumbers, rvNumbers.getHeight(), 3, 88, MAX_SELECTION_LIMIT, gameResponse.getGameCode());

        rvNumbers.setLayoutManager(new GridLayoutManager(getContext(), 16));
        rvNumbers.setHasFixedSize(true);
        rvNumbers.setItemAnimator(new DefaultItemAnimator());
        rvNumbers.setAdapter(numbersAdapter);
    }

    @Override
    public ArrayList<String> getSelectedList() {
        return LIST_SELECTED_NUMBERS;
    }

    @Override
    public ArrayList<String> getBankerList() {
        return null;
    }

    @Override
    public boolean isQpSelected() {
        return isQPSelected;
    }

    @Override
    public void onBallClicked(TextView textView, String ballNumber, String ballColor, DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball) {
        betValueCalculation();
        if (MAX_SELECTION_LIMIT > 0) {
            Utils.vibrate(Objects.requireNonNull(getContext()));
            if (LIST_SELECTED_NUMBERS.contains(ballNumber)) {
                if (isQpSelected()) {
                    switchToPickType(SELECTED_PICK_TYPE_OBJECT.getCode(), false);
                } else if (!SELECTED_PICK_TYPE_OBJECT.getCode().toLowerCase().contains("direct") &&
                        !SELECTED_PICK_TYPE_OBJECT.getCode().toLowerCase().contains("perm")) {
                    switchToPickType(SELECTED_PICK_TYPE_OBJECT.getCode(), true);
                }

                LIST_SELECTED_NUMBERS.remove(ballNumber);
                deselectNumber(ball, textView);
            } else {
                if (isQpSelected()) {
                    switchToPickType(SELECTED_PICK_TYPE_OBJECT.getCode(), false);
                } else if (!SELECTED_PICK_TYPE_OBJECT.getCode().toLowerCase().contains("direct") &&
                        !SELECTED_PICK_TYPE_OBJECT.getCode().toLowerCase().contains("perm")) {
                    switchToPickType(SELECTED_PICK_TYPE_OBJECT.getCode(), true);
                }

                if (LIST_SELECTED_NUMBERS.size() < MAX_SELECTION_LIMIT) {
                    GradientDrawable background = (GradientDrawable) textView.getBackground();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Utils.animateColor(textView, Color.parseColor("#FFFFFF"), Color.parseColor(ballColor), getActivity());
                    } else {
                        background.setColor(Color.parseColor(ballColor));
                    }
                    ball.setSelected(true);
                    LIST_SELECTED_NUMBERS.add(ballNumber);

                    textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

                    if (LIST_SELECTED_NUMBERS.size() == MAX_SELECTION_LIMIT)
                        checkIfColorPickTypeIsMade();

                    if (selectedNumbersAdapter != null)
                        setSelectedNumberAdapter();
                } else {
                    String msg = MAX_SELECTION_LIMIT > 1 ? getString(R.string.cannot_select_more) + " " + MAX_SELECTION_LIMIT + " " + getString(R.string.numbers_for) + " " + SELECTED_PICK_TYPE_OBJECT.getName() + "!" : getString(R.string.cannot_select_more) + " " + MAX_SELECTION_LIMIT + " " + getString(R.string.number_for) + " " + SELECTED_PICK_TYPE_OBJECT.getName() + "!";
                    showToast(msg, Toast.LENGTH_LONG);
                }
            }
            new Handler().postDelayed(() -> numbersAdapter.notifyDataSetChanged(), 400);
            betValueCalculation();
        } else {
            showToast(getString(R.string.some_technical_issue), Toast.LENGTH_SHORT);
        }
    }

    private void deselectNumber(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball, TextView textView){
        ball.setSelected(false);
        GradientDrawable background = (GradientDrawable) textView.getBackground();
        background.setColor(Color.parseColor("#FFFFFF"));

        textView.setTypeface(null, Typeface.NORMAL);
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        if (selectedNumbersAdapter != null)
            setSelectedNumberAdapter();
    }

    private void setSelectedNumberAdapter() {
        rvQuickPickNumber.setVisibility(View.GONE);
        rvSelectedNumbers.setVisibility(View.GONE);
        tvSelectionNumberTitle.setVisibility(View.GONE);

        try {
            ArrayList<String> list = new ArrayList<>(LIST_SELECTED_NUMBERS);
            if (list.size() < MAX_SELECTION_LIMIT) {
                int repetition = MAX_SELECTION_LIMIT - list.size();
                for (int count = 1; count <= repetition; count++)
                    list.add(null);
            }

            selectedNumbersAdapter = new SelectedNumbersAdapter(list, LuckySixBallColorMap.getMapNumberConfig(), getContext());
            rvSelectedNumbers.setLayoutManager(new GridLayoutManager(getContext(), list.size()));
            rvSelectedNumbers.setItemAnimator(new DefaultItemAnimator());
            rvSelectedNumbers.setAdapter(selectedNumbersAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            ArrayList<String> list = new ArrayList<>();
            list.add(null);
            selectedNumbersAdapter = new SelectedNumbersAdapter(list, LuckySixBallColorMap.getMapNumberConfig(), getContext());
            rvSelectedNumbers.setLayoutManager(new GridLayoutManager(getContext(), list.size()));
            rvSelectedNumbers.setItemAnimator(new DefaultItemAnimator());
            rvSelectedNumbers.setAdapter(selectedNumbersAdapter);
        }
    }

    private void setQuickPickNumberAdapter(ArrayList<String> list) {
        String text = "<font color=#606060> " + getString(R.string.number_to_be_picked) + " </font> <font color=#FF0000> " + getString(R.string.quick_pick) + " </font>";
        tvSelectionNumberTitle.setText(Html.fromHtml(text));
        tvSelectionNumberTitle.setVisibility(View.VISIBLE);

        rvQuickPickNumber.setVisibility(View.VISIBLE);
        rvSelectedNumbers.setVisibility(View.GONE);

        quickPickNumberAdapter = new QuickPickNumberAdapter(list, getContext(), this);
        rvQuickPickNumber.setLayoutManager(new GridLayoutManager(getContext(), 10));
        rvQuickPickNumber.setItemAnimator(new DefaultItemAnimator());
        rvQuickPickNumber.setAdapter(quickPickNumberAdapter);
    }

    private void setBetTypeAdapter() {
        setClickFlag();
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> list = new ArrayList<>();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO model : gameResponse.getBetRespVOs()) {
            if (model.getWinMode() != null && model.getWinMode().trim().equalsIgnoreCase("MAIN"))
                list.add(model);
        }
        betTypeAdapter = new BetTypeAdapter(list, getContext(), this);

        rvBetTypes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvBetTypes.setHasFixedSize(true);
        rvBetTypes.setItemAnimator(new DefaultItemAnimator());

        rvBetTypes.setAdapter(betTypeAdapter);

        betTypeAdapter.onClick(0);
    }

    private void setClickFlag() {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO : gameResponse.getBetRespVOs()) {
            if (betRespVO != null)
                betRespVO.setIsSelected(0);
        }
    }

    private void setPickTypeAdapter(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO) {
        if (betRespVO.getPickTypeData() == null) {
            rvPickType.setVisibility(View.INVISIBLE);
            return;
        } else {
            rvPickType.setVisibility(View.VISIBLE);
        }

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypeArrayList
                = setQPinPickType(betRespVO.getPickTypeData().getPickType());

        pickTypeAdapter = new LuckySixPickTypeAdapter(pickTypeArrayList, getContext(), this);

        rvPickType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvPickType.setHasFixedSize(true);
        rvPickType.setItemAnimator(new DefaultItemAnimator());

        rvPickType.setAdapter(pickTypeAdapter);
        pickTypeAdapter.onClick(0, true);
    }

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> setQPinPickType(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypes) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypeArrayList = new ArrayList<>();

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType : pickTypes) {
            pickTypeArrayList.add(pickType);

            if (pickType.getRange().get(0).getQpAllowed().equalsIgnoreCase("YES")) {
                DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickTypeObject = new DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType();
                DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType.Range range = new DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType.Range();
                range.setPickMode("QP");
                range.setPickValue(pickType.getRange().get(0).getPickValue());
                range.setPickConfig(pickType.getRange().get(0).getPickConfig());
                range.setPickCount(pickType.getRange().get(0).getPickCount());
                pickTypeObject.setCode(pickType.getCode());

                ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType.Range> ranges = new ArrayList<>();
                ranges.add(range);

                pickTypeObject.setRange(ranges);
                pickTypeObject.setDescription(pickType.getDescription());
                if (pickType.getName().equalsIgnoreCase("manual"))
                    pickTypeObject.setName("QP");
                else
                    pickTypeObject.setName(pickType.getName() + " QP");

                pickTypeObject.setIsSelected(0);
                pickTypeArrayList.add(pickTypeObject);
            }
        }

        return pickTypeArrayList;
    }

    private void setBetAmountAdapter(double unitPrice, int maxBetAmtMul) {
        SELECTED_BET_AMOUNT = 0;
        int count = (int) (maxBetAmtMul / unitPrice);
        Log.d("draw", "maxBetAmtMul: " + maxBetAmtMul);
        Log.d("draw", "unitPrice: " + unitPrice);
        ArrayList<FiveByNinetyBetAmountBean> list = new ArrayList<>();

        for (int index = 1; index <= count; index++) {
            int amount = (int) (index * unitPrice);
            FiveByNinetyBetAmountBean model = new FiveByNinetyBetAmountBean();
            model.setAmount(amount);
            model.setSelected(false);
            list.add(model);
        }

        if (list.size() > 0) {
            list.get(0).setSelected(true);
            onBetAmountClick(list.get(0).getAmount());
            betAmountAdapter = new BetAmountAdapter(list, getContext(), this);
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            rvBetAmount.setLayoutManager(linearLayoutManager);
            rvBetAmount.setHasFixedSize(true);
            rvBetAmount.setItemAnimator(new DefaultItemAnimator());
            rvBetAmount.setAdapter(betAmountAdapter);

            if (onScrollListener == null)
                rvBetAmount.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        onScrollListener = this;
                        isScrollable = recyclerView.computeHorizontalScrollRange() > recyclerView.getWidth();

                        new Handler().postDelayed(() -> {
                            if (isScrollable && !isSideBetSelected) viewOtherAmount.setVisibility(View.VISIBLE);
                            else viewOtherAmount.setVisibility(View.GONE);
                        }, 100);
                    }
                });

        } else
            showToast(getString(R.string.some_technical_issue), Toast.LENGTH_SHORT);

    }

    public String getBallColor(String ballNumber) {
        try {
            return FiveByNinetyColors.getBallColor(mapNumberConfig.get(ballNumber.trim()));
        } catch (Exception e) {
            return "#ff0000";
        }
    }

    private void setSchemaAdapter(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> matchDetail) {
        matchDetailList.clear();
        matchDetailList.addAll(matchDetail);
        Collections.sort(matchDetailList, (o1, o2) -> o1.getRank().compareTo(o2.getRank()));

        if (schemaAdapter == null) {
            schemaAdapter = new SchemaAdapter(matchDetailList, getContext());

            rvSchema.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rvSchema.setHasFixedSize(true);
            rvSchema.setItemAnimator(new DefaultItemAnimator());
            rvSchema.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.HORIZONTAL));
            rvSchema.setAdapter(schemaAdapter);
        } else {
            schemaAdapter.notifyDataSetChanged();
        }
    }

    private void setWinModeInSchema(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> betRespVOArrayList) {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail matchDetail : schemaArray) {

            String winmode = getWinMode(matchDetail.getBetType());
            matchDetail.setWinMode(winmode);
        }
    }

    private String getWinMode(String betType) {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO : gameResponse.getBetRespVOs()) {
            if (betType.equalsIgnoreCase(betRespVO.getBetCode()))
                return betRespVO.getWinMode();
        }

        return "";
    }

    Observer<DrawSchemaByGameResponseBean> observer = response -> {
        SCHEMA_RESPONSE = response;
        if (response == null) {
            tvSchemaLoading.setText(getString(R.string.refresh));
            tvSchemaLoading.setCompoundDrawables(getActivity().getResources().getDrawable(R.drawable.refresh), null, null, null);
            Utils.showCustomErrorDialog(getActivity(), "Draw Games", getString(R.string.something_went_wrong));
        } else if (response.getResponseCode() == 0) {

            if (SELECTED_BET_TYPE_OBJECT != null) {
                tvSchemaLoading.setVisibility(View.GONE);
                rvSchema.setVisibility(View.VISIBLE);
                setSchemaAdapter(getSchemaByBetType(SELECTED_BET_TYPE_OBJECT.getBetCode()));
            }
        } else {
            //if (Utils.checkForSessionExpiry(getActivity(), response.getResponseCode())) return;
            tvSchemaLoading.setText(getString(R.string.refresh));
            tvSchemaLoading.setCompoundDrawables(getActivity().getResources().getDrawable(R.drawable.refresh), null, null, null);
            String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
            Utils.showCustomErrorDialog(getActivity(), "Draw Games", errorMsg);
        }
    };

    @Override
    public void onBetTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO) {
        setPickTypeAdapter(betRespVO);
        SELECTED_BET_TYPE_OBJECT = betRespVO;
        UNIT_PRICE = SELECTED_BET_TYPE_OBJECT.getUnitPrice();
        BET_AMOUNT_MULTIPLE = SELECTED_BET_TYPE_OBJECT.getMaxBetAmtMul();

        setBetAmountAdapter(betRespVO.getUnitPrice(), betRespVO.getMaxBetAmtMul());

        if (!schemaArray.isEmpty())
            setSchemaAdapter(getSchemaByBetType(SELECTED_BET_TYPE_OBJECT.getBetCode()));
    }

    @Override
    public void onPickTypeClick(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType, boolean isManualClick) {
        SELECTED_PICK_TYPE_OBJECT = pickType;
        getSelectionLimits();

        isQPSelected = pickType.getRange().get(0).getPickMode().toUpperCase().contains("QP");

        if (pickType.getRange().get(0).getPickMode().equalsIgnoreCase("QP")) {
            if (MIN_SELECTION_LIMIT == MAX_SELECTION_LIMIT) {
                callQuickPickApi(MIN_SELECTION_LIMIT + "");
            } else {
                resetSelectedNumberList();

                ArrayList<String> arrayListQP = new ArrayList<>();

                for (int i = MIN_SELECTION_LIMIT; i <= MAX_SELECTION_LIMIT; i++) {
                    arrayListQP.add(i + "");
                }
                setQuickPickNumberAdapter(arrayListQP);
            }

            tvDescriptionTop.setText(MIN_SELECTION_LIMIT != MAX_SELECTION_LIMIT ? pickType.getDescription() : "");
            tvDescription.setText(MIN_SELECTION_LIMIT == MAX_SELECTION_LIMIT ? pickType.getDescription() : "");
            tvDescriptionTop.setVisibility(MIN_SELECTION_LIMIT != MAX_SELECTION_LIMIT ? View.VISIBLE : View.GONE);
            tvDescription.setVisibility(MIN_SELECTION_LIMIT != MAX_SELECTION_LIMIT ? View.GONE : View.VISIBLE);

            return;
        }

        tvDescription.setVisibility(View.VISIBLE);
        tvDescription.setText(pickType.getDescription());
        tvDescriptionTop.setVisibility(View.GONE);

        if (!isManualClick) {
            resetSelectedNumberList();
            setSelectedNumberAdapter();
        }

        pickTypeMainColor();
    }

    private void switchToColorPickType(String color) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> type
                = setQPinPickType(SELECTED_BET_TYPE_OBJECT.getPickTypeData().getPickType());
        for (int index = 0; index < type.size(); index++) {
            DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType = type.get(index);
            if (pickType.getCode().toLowerCase().contains(color.toLowerCase())) {
                tvDescription.setText(pickType.getDescription());
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
            hashSet.add(mapNumberConfig.get(number));

        if (hashSet.size() == 1) {
            ArrayList<String> list = new ArrayList<>(hashSet);
            switchToColorPickType(list.get(0));
        }
    }

    private void setPreviousPickType(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType
                                     pickType){
        SELECTED_PICK_TYPE_OBJECT = pickType;
        getSelectionLimits();

        isQPSelected = pickType.getRange().get(0).getPickMode().toUpperCase().contains("QP");

        if (pickType.getRange().get(0).getPickMode().equalsIgnoreCase("QP")) {
            if (MIN_SELECTION_LIMIT == MAX_SELECTION_LIMIT) {
                callQuickPickApi(MIN_SELECTION_LIMIT + "");
            } else {
                resetSelectedNumberList();

                ArrayList<String> arrayListQP = new ArrayList<>();

                for (int i = MIN_SELECTION_LIMIT; i <= MAX_SELECTION_LIMIT; i++) {
                    arrayListQP.add(i + "");
                }
                setQuickPickNumberAdapter(arrayListQP);
            }

            tvDescriptionTop.setText(MIN_SELECTION_LIMIT != MAX_SELECTION_LIMIT ? pickType.getDescription() : "");
            tvDescription.setText(MIN_SELECTION_LIMIT == MAX_SELECTION_LIMIT ? pickType.getDescription() : "");
            tvDescriptionTop.setVisibility(MIN_SELECTION_LIMIT != MAX_SELECTION_LIMIT ? View.VISIBLE : View.GONE);
            tvDescription.setVisibility(MIN_SELECTION_LIMIT != MAX_SELECTION_LIMIT ? View.GONE : View.VISIBLE);

            return;
        }

        tvDescription.setVisibility(View.VISIBLE);
        tvDescription.setText(pickType.getDescription());
        tvDescriptionTop.setVisibility(View.GONE);

        //resetSelectedNumberList();
        //setSelectedNumberAdapter();

        pickTypeMainColor();
    }

    private void pickTypeMainColor() {
        if (SELECTED_BET_TYPE_OBJECT != null && SELECTED_BET_TYPE_OBJECT.getBetCode().trim().equalsIgnoreCase("Direct6")) {
            String code = SELECTED_PICK_TYPE_OBJECT.getCode().trim();
            if (code.equalsIgnoreCase("RedBalls") || code.equalsIgnoreCase("GreenBalls") ||
                    code.equalsIgnoreCase("BlueBalls") || code.equalsIgnoreCase("MagentaBalls") ||
                    code.equalsIgnoreCase("BrownBalls") || code.equalsIgnoreCase("CyanBalls") ||
                    code.equalsIgnoreCase("OrangeBalls") || code.equalsIgnoreCase("GreyBalls")) {
                setPreSelectedNumbers(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickValue());
            } else if (code.equalsIgnoreCase("Hot6")) {
                String number = gameResponse.getHotNumbers();
                number = number.replace("[", "");
                number = number.replace("]", "");
                number = number.replaceAll(" ", "");
                Log.d("draw", "Hot6 Numbers: " + number);
                setPreSelectedNumbers(number);
            } else if (code.equalsIgnoreCase("Cold6")) {
                String number = gameResponse.getColdNumbers();
                number = number.replace("[", "");
                number = number.replace("]", "");
                number = number.replaceAll(" ", "");
                number = number.replaceAll(" ", "");
                Log.d("draw", "Cold6 Numbers: " + number);
                setPreSelectedNumbers(number);
            }
        }
    }

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> getSchemaByBetType(String betType) {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail> schema = new ArrayList<>();

        if (betType.equalsIgnoreCase("sidebet")) {


            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail matchDetail : schemaArray) {

                if (!matchDetail.getWinMode().equalsIgnoreCase("main"))
                    schema.add(matchDetail);
            }

            return schema;
        }

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.GameSchemas.MatchDetail matchDetail : schemaArray) {

            if (matchDetail.getBetType().equalsIgnoreCase(betType))
                schema.add(matchDetail);
        }

        return schema;
    }

    private void setPreSelectedNumbers(String strNumber) {
        LIST_SELECTED_NUMBERS.clear();
        tvBetValue.setText(Utils.getAmountWithCurrency("0", getActivity()));
        tvBetValue.setTag(0);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(strNumber.split(",")));
        for (String num : list)
            LIST_SELECTED_NUMBERS.add(num.length() == 1 ? "0" + num : num);
        setNumbersAdapter(new ArrayList<>(LIST_SELECTED_NUMBERS));
        if (selectedNumbersAdapter != null)
            setSelectedNumberAdapter();
        betValueCalculation();
    }

    private void resetSelectedNumberList() {
        LIST_SELECTED_NUMBERS.clear();
        setNumbersAdapter(null);
        tvBetValue.setText(Utils.getAmountWithCurrency("0", getActivity()));
        clearSelectedNumberRecyclerView();
        tvBetValue.setTag(0);
    }

    private void clearSelectedNumberRecyclerView() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        selectedNumbersAdapter = new SelectedNumbersAdapter(list, LuckySixBallColorMap.getMapNumberConfig(), getContext());
        rvSelectedNumbers.setLayoutManager(new GridLayoutManager(getContext(), 18));
        rvSelectedNumbers.setHasFixedSize(true);
        rvSelectedNumbers.setItemAnimator(new DefaultItemAnimator());
        rvSelectedNumbers.setAdapter(selectedNumbersAdapter);
    }

    private void resetGame() {
        rvQuickPickNumber.setVisibility(View.GONE);
        rvSelectedNumbers.setVisibility(View.GONE);
        tvSelectionNumberTitle.setText(getString(R.string.manual_pick_type));
        tvBetValue.setText(Utils.getAmountWithCurrency("0", getActivity()));
        resetSelectedNumberList();
        if (betTypeAdapter != null)
            betTypeAdapter.onClick(0);

    }

    private void getSelectionLimits() {
        if (SELECTED_PICK_TYPE_OBJECT != null) {
            if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker")) {
                try {
                    MIN_SELECTION_LOWER_LIMIT_BANKER = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[0].split(",")[0]);
                    MAX_SELECTION_LOWER_LIMIT_BANKER = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[0].split(",")[1]);
                    MIN_SELECTION_UPPER_LIMIT_BANKER = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[1].split(",")[0]);
                    MAX_SELECTION_UPPER_LIMIT_BANKER = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split("-")[1].split(",")[1]);
                } catch (Exception e) {
                    MIN_SELECTION_LOWER_LIMIT_BANKER = 0;
                    MAX_SELECTION_LOWER_LIMIT_BANKER = 0;
                    MIN_SELECTION_UPPER_LIMIT_BANKER = 0;
                    MAX_SELECTION_UPPER_LIMIT_BANKER = 0;
                    e.printStackTrace();
                }
            } else {
                try {
                    MIN_SELECTION_LIMIT = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split(",")[0]);
                    MAX_SELECTION_LIMIT = Integer.parseInt(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickCount().split(",")[1]);
                } catch (Exception e) {
                    MIN_SELECTION_LIMIT = 0;
                    MAX_SELECTION_LIMIT = 0;
                    e.printStackTrace();
                }
            }
        } else {
            MIN_SELECTION_LIMIT = 0;
            MAX_SELECTION_LIMIT = 0;
        }

        Log.i("draw", "MIN LIMIT: " + MIN_SELECTION_LIMIT);
        Log.i("draw", "MAX LIMIT: " + MAX_SELECTION_LIMIT);
    }

    private void setColorBar() {
        int position = 0;
        String ballNumber = "";
        for (int index = 0; index < colorBarList.size(); index++) {
            position = index + 1;
            ballNumber = position < 10 ? "0" + position : "" + position;
            CardView view = colorBarList.get(index);
            view.setCardBackgroundColor(Color.parseColor(getBallColor(ballNumber)));
        }
    }

    private void setColorBallFirstBallAdapter() {
        if (getPickTypeArray("LuckySixFirstBallColor", gameResponse.getBetRespVOs()) == null)
            return;

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypes =
                setColorClickFlag(getPickTypeArray("LuckySixFirstBallColor", gameResponse.getBetRespVOs()));

        tvFirstBallColor.setText(getBetObject("LuckySixFirstBallColor").getBetDispName());

        ColorBallAdapter colorBallAdapter = new ColorBallAdapter(pickTypes, getContext(), this);

        rvColorBallsFirstBall.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rvColorBallsFirstBall.setHasFixedSize(true);
        rvColorBallsFirstBall.setItemAnimator(new DefaultItemAnimator());
        rvColorBallsFirstBall.setAdapter(colorBallAdapter);
    }

    private void setColorBallLastBallAdapter() {
        if (getPickTypeArray("LuckySixLastBallColor", gameResponse.getBetRespVOs()) == null)
            return;
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypes =
                setColorClickFlag(getPickTypeArray("LuckySixLastBallColor", gameResponse.getBetRespVOs()));

        tvLastBallColor.setText(getBetObject("LuckySixLastBallColor").getBetDispName());

        ColorBallLastBallAdapter colorBallLastBallAdapter = new ColorBallLastBallAdapter(pickTypes, getContext(), this);

        rvColorBallsLastBall.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rvColorBallsLastBall.setHasFixedSize(true);
        rvColorBallsLastBall.setItemAnimator(new DefaultItemAnimator());
        rvColorBallsLastBall.setAdapter(colorBallLastBallAdapter);
    }

    private void setColorBallFiveBallAdapter() {
        if (getPickTypeArray("LuckySixAllBallColor", gameResponse.getBetRespVOs()) == null)
            return;
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypes =
                setColorClickFlag(getPickTypeArray("LuckySixAllBallColor", gameResponse.getBetRespVOs()));

        tvFiveBallColor.setText(getBetObject("LuckySixAllBallColor").getBetDispName());

        ColorBallFiveBallAdapter colorBallFiveBallAdapter = new ColorBallFiveBallAdapter(pickTypes, getContext(), this);

        rvColorBallFirstFive.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rvColorBallFirstFive.setHasFixedSize(true);
        rvColorBallFirstFive.setItemAnimator(new DefaultItemAnimator());
        rvColorBallFirstFive.setAdapter(colorBallFiveBallAdapter);
    }

    private void setPatternAdapter() {
     /*   if (getPickTypeArray("5/90Pattern", gameResponse.getBetRespVOs()) == null)
            return;
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypes =
                setColorClickFlag(getPickTypeArray("5/90Pattern", gameResponse.getBetRespVOs()));

        PatternAdapter schemaAdapter = new PatternAdapter(pickTypes, getContext(), this);

        rvPattern.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvPattern.setHasFixedSize(true);
        rvPattern.setItemAnimator(new DefaultItemAnimator());
        rvPattern.setAdapter(schemaAdapter);*/
    }

    private void setMultiPanelAdapter() {
        if (LIST_MULTI_PANEL != null && !LIST_MULTI_PANEL.isEmpty()) {
            multiPanelAdapter = new MultiPanelAdapter(getActivity(), LIST_MULTI_PANEL, this, gameResponse.getGameCode());
            rvPurchase.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rvPurchase.setHasFixedSize(true);
            rvPurchase.setItemAnimator(new DefaultItemAnimator());
            //rvPurchase.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
            rvPurchase.setAdapter(multiPanelAdapter);
            recalculatePanelAmount();
        } else
            showToast(getString(R.string.some_technical_issue), Toast.LENGTH_SHORT);
    }

    @Override
    public void onPanelDeleted(int index) {
        LIST_MULTI_PANEL.remove(index);
        if (multiPanelAdapter != null)
            multiPanelAdapter.notifyDataSetChanged();
        calculateTotalAmount();
        if (LIST_MULTI_PANEL.isEmpty()) {
            viewPayout.setVisibility(View.VISIBLE);

            tvBetValue.setTag(0.0);
            tvBetValue.setText(Utils.getAmountWithCurrency("0", getActivity()));
            resetNoOfDraws();
            resetAdvanceDraws();
            if (cardBet.getVisibility() == View.VISIBLE) {
                cardSchema.setVisibility(cardSchema.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                tvTitleAddTpPurchase.setVisibility(cardBet.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                cardBet.setVisibility(cardBet.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        }
        //enableAddBetButton(LIST_MULTI_PANEL.size() < 8);
    }

    private void deleteAllPanel() {
        LIST_MULTI_PANEL.clear();
        if (multiPanelAdapter != null)
            multiPanelAdapter.notifyDataSetChanged();
        calculateTotalAmount();
        if (LIST_MULTI_PANEL.isEmpty()) {
            if (cardBet.getVisibility() == View.VISIBLE) {
                cardSchema.setVisibility(cardSchema.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                tvTitleAddTpPurchase.setVisibility(cardBet.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                cardBet.setVisibility(cardBet.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        }
    }

    private void setSideBetUI() {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> fistBallSum
                = getPickTypeArray("LuckySixFirstBallSum", gameResponse.getBetRespVOs());
        tvFirstBallLesser.setText(fistBallSum.get(0).getName());
        tvFirstBallLesser.setTag(fistBallSum.get(0).getCode());
        tvFirstBallGreater.setText(fistBallSum.get(1).getName());
        tvFirstBallGreater.setTag(fistBallSum.get(1).getCode());
        tvFirstBallSum.setText(getBetObject("LuckySixFirstBallSum").getBetDispName());

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> fistBallEvenOdd
                = getPickTypeArray("LuckySixFirstBallEvenOdd", gameResponse.getBetRespVOs());
        tvFirstBallEven.setText(fistBallEvenOdd.get(0).getName().replace("First Ball", ""));
        tvFirstBallEven.setTag(fistBallEvenOdd.get(0).getCode());
        tvFirstBallOdd.setText(fistBallEvenOdd.get(1).getName().replace("First Ball", ""));
        tvFirstBallOdd.setTag(fistBallEvenOdd.get(1).getCode());
        tvFirstBallEvenOdd.setText(getBetObject("LuckySixFirstBallEvenOdd").getBetDispName());

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> lastBallSum
                = getPickTypeArray("LuckySixLastBallSum", gameResponse.getBetRespVOs());
        tvLastBallLesser.setText(lastBallSum.get(0).getName());
        tvLastBallLesser.setTag(lastBallSum.get(0).getCode());
        tvLastBallGreater.setText(lastBallSum.get(1).getName());
        tvLastBallGreater.setTag(lastBallSum.get(1).getCode());
        tvLastBallSum.setText(getBetObject("LuckySixLastBallSum").getBetDispName());

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> lastBallEvenOdd
                = getPickTypeArray("LuckySixLastBallEvenOdd", gameResponse.getBetRespVOs());
        tvLastBallEven.setText(lastBallEvenOdd.get(0).getName().replace("Last Ball", ""));
        tvLastBallEven.setTag(lastBallEvenOdd.get(0).getCode());
        tvLastBallOdd.setText(lastBallEvenOdd.get(1).getName().replace("Last Ball", ""));
        tvLastBallOdd.setTag(lastBallEvenOdd.get(1).getCode());
        tvLastBallEvenOdd.setText(getBetObject("LuckySixLastBallEvenOdd").getBetDispName());

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> fistFiveBallSum
                = getPickTypeArray("LuckySixFirstFiveBallSum", gameResponse.getBetRespVOs());
        tvFirstFiveBallLesser.setText(fistFiveBallSum.get(0).getName());
        tvFirstFiveBallLesser.setTag(fistFiveBallSum.get(0).getCode());
        tvFirstFiveBallGreater.setText(fistFiveBallSum.get(1).getName());
        tvFirstFiveBallGreater.setTag(fistFiveBallSum.get(1).getCode());
        tvFiveBallSum.setText(getBetObject("LuckySixFirstFiveBallSum").getBetDispName());

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> moreEvenOdd
                = getPickTypeArray("LuckySixFirstFiveMoreEvenOdd", gameResponse.getBetRespVOs());
        tvMoreEven.setText(moreEvenOdd.get(0).getName());
        tvMoreEven.setTag(moreEvenOdd.get(0).getCode());
        tvMoreOdd.setText(moreEvenOdd.get(1).getName());
        tvMoreOdd.setTag(moreEvenOdd.get(1).getCode());

        tvFiveBallEvenOdd.setText(getBetObject("LuckySixFirstFiveMoreEvenOdd").getBetDispName());
        tvFirstBallColorValue.setText(getAmountWithCurrency(String.valueOf(getBetObject("LuckySixFirstBallColor").getUnitPrice())));
        tvFirstBallSumValue.setText(getAmountWithCurrency(String.valueOf(getBetObject("LuckySixFirstBallSum").getUnitPrice())));
        tvFirstBallEvenOddValue.setText(getAmountWithCurrency(String.valueOf(getBetObject("LuckySixFirstBallEvenOdd").getUnitPrice())));
        tvLastBallColorValue.setText(getAmountWithCurrency(String.valueOf(getBetObject("LuckySixLastBallColor").getUnitPrice())));
        tvLastBallSumValue.setText(getAmountWithCurrency(String.valueOf(getBetObject("LuckySixLastBallSum").getUnitPrice())));
        tvLastBallEvenOddValue.setText(getAmountWithCurrency(String.valueOf(getBetObject("LuckySixLastBallEvenOdd").getUnitPrice())));
        //  tvFirstFiveBallColorValue.setText("$" + getBetObject("LuckySixAllBallColor").getUnitPrice());
        tvFirstFiveBallSumValue.setText(getAmountWithCurrency(String.valueOf(getBetObject("LuckySixFirstFiveBallSum").getUnitPrice())));
        tvMoreEvenOddValue.setText(getAmountWithCurrency(String.valueOf(getBetObject("LuckySixFirstFiveMoreEvenOdd").getUnitPrice())));
    }

    private void firstBallGreaterOrLesser(String tag) {
        //resetSideBets();
        firstBallGreater.setBackground(tag.equalsIgnoreCase(tvFirstBallGreater.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        firstBallLesser.setBackground(!tag.equalsIgnoreCase(tvFirstBallLesser.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded));

        tvFirstBallGreater.setTextColor(tag.equalsIgnoreCase(tvFirstBallGreater.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorWhite)
                : getActivity().getResources().getColor(R.color.colorBlack));

        tvFirstBallLesser.setTextColor(!tag.equalsIgnoreCase(tvFirstBallLesser.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorBlack)
                : getActivity().getResources().getColor(R.color.colorWhite));

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> fistBallSum
                = getPickTypeArray("LuckySixFirstBallSum", gameResponse.getBetRespVOs());
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType =
                getPickType(tag, fistBallSum);

        setSideBetData(pickType, "LuckySixFirstBallSum");
    }

    private void lastBallGreaterOrLesser(String tag) {
        //resetSideBets();
        lastBallGreater.setBackground(tag.equalsIgnoreCase(tvLastBallGreater.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        lastBallLesser.setBackground(!tag.equalsIgnoreCase(tvLastBallLesser.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded));

        tvLastBallGreater.setTextColor(tag.equalsIgnoreCase(tvLastBallGreater.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorWhite)
                : getActivity().getResources().getColor(R.color.colorBlack));

        tvLastBallLesser.setTextColor(!tag.equalsIgnoreCase(tvLastBallLesser.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorBlack)
                : getActivity().getResources().getColor(R.color.colorWhite));

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> lastBallSum
                = getPickTypeArray("LuckySixLastBallSum", gameResponse.getBetRespVOs());
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType =
                getPickType(tag, lastBallSum);
        setSideBetData(pickType, "LuckySixLastBallSum");
    }

    private void firstFiveBallGreaterOrLesser(String tag) {
        //resetSideBets();

        firstFiveBallGreater.setBackground(tag.equalsIgnoreCase(tvFirstFiveBallGreater.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        firstFiveBallLesser.setBackground(!tag.equalsIgnoreCase(tvFirstFiveBallLesser.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded));

        tvFirstFiveBallGreater.setTextColor(tag.equalsIgnoreCase(tvFirstFiveBallGreater.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorWhite)
                : getActivity().getResources().getColor(R.color.colorBlack));

        tvFirstFiveBallLesser.setTextColor(!tag.equalsIgnoreCase(tvFirstFiveBallLesser.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorBlack)
                : getActivity().getResources().getColor(R.color.colorWhite));

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> lastBallSum
                = getPickTypeArray("LuckySixFirstFiveBallSum", gameResponse.getBetRespVOs());

        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType =
                getPickType(tag, lastBallSum);
        setSideBetData(pickType, "LuckySixFirstFiveBallSum");
    }

    private void firstBallEvenOrOdd(String tag) {
        //resetSideBets();
        firstBallEven.setBackground(tvFirstBallEven.getTag().toString().equalsIgnoreCase(tag) ? getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        firstBallOdd.setBackground(!tag.equalsIgnoreCase(tvFirstBallOdd.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded));

        tvFirstBallEven.setTextColor(tag.equalsIgnoreCase(tvFirstBallEven.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorWhite)
                : getActivity().getResources().getColor(R.color.colorBlack));

        tvFirstBallOdd.setTextColor(!tag.equalsIgnoreCase(tvFirstBallOdd.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorBlack)
                : getActivity().getResources().getColor(R.color.colorWhite));

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> lastBallSum
                = getPickTypeArray("LuckySixFirstBallEvenOdd", gameResponse.getBetRespVOs());

        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType =
                getPickType(tag, lastBallSum);
        setSideBetData(pickType, "LuckySixFirstBallEvenOdd");
    }

    private void lastBallEvenOrOdd(String tag) {
        //resetSideBets();
        lastBallEven.setBackground(tvLastBallEven.getTag().toString().equalsIgnoreCase(tag) ? getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        lastBallOdd.setBackground(!tag.equalsIgnoreCase(tvLastBallOdd.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded));

        tvLastBallEven.setTextColor(tvLastBallEven.getTag().toString().equalsIgnoreCase(tag) ? getActivity().getResources().getColor(R.color.colorWhite)
                : getActivity().getResources().getColor(R.color.colorBlack));

        tvLastBallOdd.setTextColor(!tag.equalsIgnoreCase(tvLastBallOdd.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorBlack)
                : getActivity().getResources().getColor(R.color.colorWhite));

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> lastBallSum
                = getPickTypeArray("LuckySixLastBallEvenOdd", gameResponse.getBetRespVOs());

        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType =
                getPickType(tag, lastBallSum);
        setSideBetData(pickType, "LuckySixLastBallEvenOdd");
    }

    private void firstFiveEvenOrOdd(String tag) {
        //resetSideBets();
        moreEven.setBackground(tag.equalsIgnoreCase(tvMoreEven.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        moreOdd.setBackground(!tag.equalsIgnoreCase(tvMoreOdd.getTag().toString()) ? getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded)
                : getActivity().getResources().getDrawable(R.drawable.background_bet_selected_rounded));

        tvMoreEven.setTextColor(tag.equalsIgnoreCase(tvMoreEven.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorWhite)
                : getActivity().getResources().getColor(R.color.colorBlack));

        tvMoreOdd.setTextColor(!tag.equalsIgnoreCase(tvMoreOdd.getTag().toString()) ? getActivity().getResources().getColor(R.color.colorBlack)
                : getActivity().getResources().getColor(R.color.colorWhite));

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> lastBallSum
                = getPickTypeArray("LuckySixFirstFiveMoreEvenOdd", gameResponse.getBetRespVOs());

        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType = getPickType(tag, lastBallSum);
        setSideBetData(pickType, "LuckySixFirstFiveMoreEvenOdd");
    }

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> getPickTypeArray(String tag, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO> arrayList) {

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO : arrayList) {

            if (betRespVO.getBetCode().equalsIgnoreCase(tag))
                return betRespVO.getPickTypeData().getPickType();
        }

        return null;
    }

    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType getPickType(String tag, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> arrayList) {

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType : arrayList) {

            if (pickType.getCode().equalsIgnoreCase(tag))
                return pickType;
        }

        return null;
    }

    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO getBetObject(String tag) {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO betRespVO : gameResponse.getBetRespVOs()) {

            if (betRespVO.getBetCode().equalsIgnoreCase(tag))
                return betRespVO;
        }

        return null;
    }

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> setColorClickFlag(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypes) {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType betRespVO : pickTypes) {
            if (betRespVO != null)
                betRespVO.setIsSelected(0);
        }

        return pickTypes;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvReset:
                resetGame();
                break;
           /* case R.id.view_more_result:
                showViewMoreResults();
                break;*/
            case R.id.tvAddBet:
                if (!isSideBetSelected && LIST_SELECTED_NUMBERS.size() < MIN_SELECTION_LIMIT) {
                    String msg = MIN_SELECTION_LIMIT > 1 ? getString(R.string.please_select_at_least) + MIN_SELECTION_LIMIT + getString(R.string.number_for) + SELECTED_PICK_TYPE_OBJECT.getName() + "." : getString(R.string.please_select_at_least) + MIN_SELECTION_LIMIT + getString(R.string.number_for) + SELECTED_PICK_TYPE_OBJECT.getName() + ".";
                    showToast(msg, Toast.LENGTH_LONG);
                    return;
                }

                if (!sideBetMap.isEmpty()) {
                    if (LIST_MULTI_PANEL.size() + sideBetMap.size() > gameResponse.getMaxPanelAllowed()) {
                        showToast(getString(R.string.you_have_reached_the_max_panel_limit_of) + gameResponse.getMaxPanelAllowed(), Toast.LENGTH_LONG);
                        return;
                    }
                } else {
                    if (LIST_MULTI_PANEL.size() + sideBetMap.size() >= gameResponse.getMaxPanelAllowed()) {
                        showToast(getString(R.string.you_have_reached_the_max_panel_limit_of) + gameResponse.getMaxPanelAllowed(), Toast.LENGTH_LONG);
                        return;
                    }
                }

                if (isSideBetSelected && sideBetMap.isEmpty()) {
                    showToast(getString(R.string.please_play_a_side_bet), Toast.LENGTH_LONG);
                    return;
                }

                if (Double.parseDouble(tvBetValue.getTag().toString()) == 0) {
                    showToast(getString(R.string.bet_value_cannot_be_zero), Toast.LENGTH_LONG);
                    return;
                }

                if (cardBet.getVisibility() == View.GONE) {
                    cardSchema.setVisibility(cardSchema.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    tvTitleAddTpPurchase.setVisibility(cardBet.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                    cardBet.setVisibility(cardBet.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    viewPayout.setVisibility(cardBet.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }

                addPanel();
                break;
            case R.id.image_switch_bets:
                rvNumbers.setVisibility(rvNumbers.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                space.setVisibility(rvNumbers.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                viewDescription.setVisibility(rvNumbers.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                viewSaperator.setVisibility(rvNumbers.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                viewSideBet.setVisibility(viewSideBet.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                tvResetSideBet.setVisibility(viewSideBet.getVisibility() == View.VISIBLE ? View.VISIBLE : View.GONE);
                rvColorBar.setVisibility(rvNumbers.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                viewPickType.setVisibility(rvNumbers.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                // viewBetType.setVisibility(rvNumbers.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                viewSelectedNumbers.setVisibility(rvNumbers.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
                tvDescription.setVisibility(rvNumbers.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);

                imageSwitchBets.setBackground(viewSideBet.getVisibility() == View.VISIBLE ? getActivity().getResources().getDrawable(R.drawable.b_2_2) :
                        getActivity().getResources().getDrawable(R.drawable.b_1));

                tvBetValue.setTextColor(viewSideBet.getVisibility() == View.VISIBLE ? getResources().getColor(R.color.colorDrawSelectedPickYellow) :
                        getResources().getColor(R.color.colorDrawButtonRed));

                tvBetValueLabel.setTextColor(viewSideBet.getVisibility() == View.VISIBLE ? getResources().getColor(R.color.colorWhite) :
                        getResources().getColor(R.color.colorBlack));

                resetSideBets();
                if (viewSideBet.getVisibility() == View.VISIBLE) {
                    isSideBetSelected = true;
                    viewOtherAmount.setVisibility(View.GONE);
                    rvBetAmount.setVisibility(View.INVISIBLE);
                    tvBetAmountTitle.setVisibility(View.INVISIBLE);
                    setSchemaAdapter(getSchemaByBetType("sidebet"));
                    viewSaperator2.setVisibility(View.VISIBLE);
                    cardBetAmount.setBackgroundColor(getResources().getColor(R.color.colorDrawHeaderGreen));
                } else {
                    if(isScrollable)
                        viewOtherAmount.setVisibility(View.VISIBLE);
                    isSideBetSelected = false;
                    rvBetAmount.setVisibility(View.VISIBLE);
                    tvBetAmountTitle.setVisibility(View.VISIBLE);
                    setSchemaAdapter(getSchemaByBetType(SELECTED_BET_TYPE_OBJECT.getBetCode()));
                    viewSaperator2.setVisibility(View.GONE);
                    cardBetAmount.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    resetGame();
                }

                break;
            case R.id.first_ball_greater:
                firstBallGreaterOrLesser(tvFirstBallGreater.getTag().toString());

                break;
            case R.id.first_ball_lesser:
                firstBallGreaterOrLesser(tvFirstBallLesser.getTag().toString());

                break;
            case R.id.last_ball_greater:
                lastBallGreaterOrLesser(tvLastBallGreater.getTag().toString());

                break;
            case R.id.last_ball_lesser:
                lastBallGreaterOrLesser(tvLastBallLesser.getTag().toString());

                break;
            case R.id.first_ball_even:
                firstBallEvenOrOdd(tvFirstBallEven.getTag().toString());

                break;
            case R.id.first_ball_odd:
                firstBallEvenOrOdd(tvFirstBallOdd.getTag().toString());
                break;
            case R.id.last_ball_even:
                lastBallEvenOrOdd(tvLastBallEven.getTag().toString());
                break;
            case R.id.last_ball_odd:
                lastBallEvenOrOdd(tvLastBallOdd.getTag().toString());
                break;
            case R.id.first_five_ball_greater:
                firstFiveBallGreaterOrLesser(tvFirstFiveBallGreater.getTag().toString());
                break;
            case R.id.first_five_ball_lesser:
                firstFiveBallGreaterOrLesser(tvFirstFiveBallLesser.getTag().toString());
                break;
            case R.id.more_even:
                firstFiveEvenOrOdd(tvMoreEven.getTag().toString());
                break;
            case R.id.more_odd:
                firstFiveEvenOrOdd(tvMoreOdd.getTag().toString());
                break;
            case R.id.btnPlus:
                resetAdvanceDraws();

                IS_ADVANCE_PLAY = false;
                Log.w("draw", "INDEX (BEFORE): " + INDEX_CONSECUTIVE_DRAWS_LIST);
                if (INDEX_CONSECUTIVE_DRAWS_LIST < gameResponse.getDrawRespVOs().size()) {
                    NUMBER_OF_DRAWS = Integer.parseInt(LIST_CONSECUTIVE_DRAWS.get(++INDEX_CONSECUTIVE_DRAWS_LIST));
                    String text = NUMBER_OF_DRAWS + "";
                    tvNoOfDraw.setText(text);
                    Log.w("draw", "INDEX (AFTER): " + INDEX_CONSECUTIVE_DRAWS_LIST);
                    tvDrawCount.setText(NUMBER_OF_DRAWS == 1 ? NUMBER_OF_DRAWS + " Draw" : NUMBER_OF_DRAWS + " Draws");
                }
                setPlusMinusButton();

                betValueCalculation();
                recalculatePanelAmount();
                break;
            case R.id.btnMinus:
                resetAdvanceDraws();

                IS_ADVANCE_PLAY = false;
                Log.i("draw", "INDEX (BEFORE): " + INDEX_CONSECUTIVE_DRAWS_LIST);
                if (INDEX_CONSECUTIVE_DRAWS_LIST > 0) {
                    NUMBER_OF_DRAWS = Integer.parseInt(LIST_CONSECUTIVE_DRAWS.get(--INDEX_CONSECUTIVE_DRAWS_LIST));
                    String text = NUMBER_OF_DRAWS + "";
                    tvNoOfDraw.setText(text);
                    Log.i("draw", "INDEX (AFTER): " + INDEX_CONSECUTIVE_DRAWS_LIST);

                    tvDrawCount.setText(NUMBER_OF_DRAWS == 1 ? NUMBER_OF_DRAWS + " Draw" : NUMBER_OF_DRAWS + " Draws");
                }

                setPlusMinusButton();

                betValueCalculation();
                recalculatePanelAmount();
                break;
            case R.id.ivAdvanceDraw://dohere
                if (drawDialog != null) {
                    drawDialog.cancel();
                }
                ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> drawRespVOs = gameResponse.getDrawRespVOs();
                drawDialog = new DrawDialogLand(getContext(), drawRespVOs, this, gameResponse.getMaxAdvanceDraws(), LIST_ADVANCE_DRAWS);
                drawDialog.show();
                if (drawDialog.getWindow() != null) {
                    drawDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    drawDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }
                break;

            case R.id.rlBuyNowContainer:
                double amount = 0;
                for (PanelBean model : LIST_MULTI_PANEL)
                    amount = amount + model.getAmount();
                if (amount == 0)
                    showToast(getString(R.string.your_cart_is_empty), Toast.LENGTH_LONG);
                else showBuyNowConfirmation();
                break;
            case R.id.llWinningClaim:
                Intent winning = new Intent(getActivity(), WinningClaimLandscapeActivity.class);
                winning.putExtra("MenuBean", menuBean);
                startActivityForResult(winning, REQUEST_CODE_PRINT);
                break;
            /*case R.id.view_payout:
                if (tvPayout.getText().toString().equalsIgnoreCase("PAYOUT AND PAYING RULES")) {
                    cardSchema.setVisibility(View.VISIBLE);
                    tvTitleAddTpPurchase.setVisibility(View.VISIBLE);
                    cardBet.setVisibility(View.GONE);
                    tvPayout.setText("PURCHASE LIST");
                } else {
                    if (LIST_MULTI_PANEL.isEmpty())
                        return;
                    cardSchema.setVisibility(View.GONE);
                    tvTitleAddTpPurchase.setVisibility(View.GONE);
                    cardBet.setVisibility(View.VISIBLE);
                    tvPayout.setText("PAYOUT AND PAYING RULES");
                }
                break;*/
            case R.id.tv_schema_loading:
                if (tvSchemaLoading.getText().toString().equalsIgnoreCase("refresh"))
                    callSchemaApi();
                break;
            case R.id.tvReset_sidebet:
                resetSideBets();
                break;
            case R.id.tv_view_more:
                showViewMoreResults();
                break;
            case R.id.llOther:
                showOtherBetAmountDialog(UNIT_PRICE, BET_AMOUNT_MULTIPLE);
                break;
            case R.id.llReprint:
                ((DrawGameBaseActivity) getActivity()).callReprint(menuBean);
                break;
            case R.id.llCancel:
                ((DrawGameBaseActivity) getActivity()).callCancel(menuBean);
                break;
            case R.id.llResult:
                ((DrawGameBaseActivity) getActivity()).callResult(menuBean);
                break;
        }
    }

    private void showOtherBetAmountDialog(double unitPrice, int maxBetAmtMul){
        int count = (int) (maxBetAmtMul / unitPrice);
        // ((DrawGameBaseActivity) getActivity()).showOtherAmountDialog(this);
        ArrayList<FiveByNinetyBetAmountBean> list = new ArrayList<>();

        for (int index = 1; index <= count; index++) {
            int amount = (int) (index * unitPrice);
            FiveByNinetyBetAmountBean model = new FiveByNinetyBetAmountBean();
            model.setAmount(amount);
            model.setSelected(false);
            list.add(model);
        }

        if (list.size() > 0) {
            ((DrawGameBaseActivity) getActivity()).showOtherAmountDialog(list, this);
        } else
            showToast(getString(R.string.some_technical_issue), Toast.LENGTH_SHORT);

    }


    private void showViewMoreResults() {
        if (moreResultDialog != null) moreResultDialog.cancel();

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> drawRespVOs = gameResponse.getDrawRespVOs();
        moreResultDialog = new MoreResultDialog(getContext(), gameResponse.getLastDrawWinningResultVOs(), gameResponse.getMaxAdvanceDraws(), gameResponse);
        moreResultDialog.show();
        if (moreResultDialog.getWindow() != null) {
            moreResultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            moreResultDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void showBuyNowConfirmation() {
        if (confirmPurchaseDialog != null)
            confirmPurchaseDialog.cancel();
        confirmPurchaseDialog = new ConfirmPurchaseDialog(getContext(), this);
        confirmPurchaseDialog.show();
        if (confirmPurchaseDialog.getWindow() != null) {
            confirmPurchaseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmPurchaseDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void setPlusMinusButton() {
        btnMinus.setBackgroundResource(INDEX_CONSECUTIVE_DRAWS_LIST == 0 ? R.drawable.minus_grey : R.drawable.minus);
        btnPlus.setBackgroundResource(INDEX_CONSECUTIVE_DRAWS_LIST == gameResponse.getDrawRespVOs().size() - 1 ? R.drawable.plus_grey : R.drawable.plus_round);
        btnPlus.setEnabled(INDEX_CONSECUTIVE_DRAWS_LIST == gameResponse.getDrawRespVOs().size() - 1 ? false : true);
        btnMinus.setEnabled(INDEX_CONSECUTIVE_DRAWS_LIST == 0 ? false : true);
    }

    private void recalculatePanelAmount() {
        for (int index = 0; index < LIST_MULTI_PANEL.size(); index++) {
            LIST_MULTI_PANEL.get(index).setNumberOfDraws(NUMBER_OF_DRAWS);
            double amt = LIST_MULTI_PANEL.get(index).getSelectedBetAmount() * LIST_MULTI_PANEL.get(index).getNumberOfDraws() * LIST_MULTI_PANEL.get(index).getNumberOfLines();
            LIST_MULTI_PANEL.get(index).setAmount(amt);
        }

        if (multiPanelAdapter != null)
            multiPanelAdapter.notifyDataSetChanged();

        calculateTotalAmount();
    }

    @Override
    public void onBetAmountClick(int amount) {
        SELECTED_BET_AMOUNT = amount;
        if (isSideBetSelected) {
            SELECTED_BET_AMOUNT = 0;
        }
        tvOtherAmount.setText(getAmountWithCurrency(String.valueOf(amount)));
        betValueCalculation();
    }

    private ArrayList<String> getAllColors() {
        HashSet<String> hashSet = new HashSet<>();
        if (gameResponse != null) {
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball ball : gameResponse.getNumberConfig().getRange().get(0).getBall())
                hashSet.add(ball.getColor());
            return new ArrayList<>(hashSet);
        }
        return null;
    }

    private void betValueCalculation() {
        //(unitPrice*bet_amt_multiple) * noOfDraws * noOfLines
        /*DecimalFormat df = new DecimalFormat("##.#");
        String amt = "$" + df.format(amount);*/
        if (!isSideBetSelected && LIST_SELECTED_NUMBERS.size() >= MIN_SELECTION_LIMIT) {
            double amount = SELECTED_BET_AMOUNT * NUMBER_OF_DRAWS * getNumberOfLines();
            String amt = getAmountWithCurrency(String.valueOf(amount));
            tvBetValue.setText(amt);
            tvBetValue.setTag(amount);
        } else if (isSideBetSelected) {
            if (!sideBetMap.isEmpty()) {
                double amount = 0.0;
                for (Map.Entry entry : sideBetMap.entrySet()) {
                    entry.getKey();
                    PanelBean panelBean = (PanelBean) entry.getValue();
                    amount = amount + panelBean.getUnitPrice() * NUMBER_OF_DRAWS * getNumberOfLines();
                }
                double finalAmount = amount;
                String amt = getAmountWithCurrency(String.valueOf(finalAmount));
                tvBetValue.setText(amt);
                tvBetValue.setTag(amount);
            }
        } else {
            tvBetValue.setText(Utils.getAmountWithCurrency("0", getActivity()));
            tvBetValue.setTag(0);
        }
    }

    private void betValueCalculation(double unitPriceAmount) {
        double amount = 0.0;
        for (Map.Entry entry : sideBetMap.entrySet()) {
            entry.getKey();
            PanelBean panelBean = (PanelBean) entry.getValue();
            amount = amount + unitPriceAmount * NUMBER_OF_DRAWS * getNumberOfLines();
        }

        String amt = getAmountWithCurrency(String.valueOf(amount));
        tvBetValue.setText(amt);
        tvBetValue.setTag(amount);
    }

    private double getBetValueAmount(double unitPriceAmount) {
        double amount = unitPriceAmount * NUMBER_OF_DRAWS * getNumberOfLines();

        return amount;
    }

    private int getNumberOfLines() {
        if (isSideBetSelected) return 1;

        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm2"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 2);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm3"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 3);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Perm6"))
            return Utils.nCr(LIST_SELECTED_NUMBERS.size(), 6);
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker")) {
            //TODO
        }
        if (SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("Banker1AgainstAll"))
            return 89;
        return 1;
    }

    private void showToast(String text, int duration) {
        try {
            Snackbar snackbar = Snackbar.make(VIEW, text, Snackbar.LENGTH_SHORT);
            (snackbar.getView()).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            View mView = snackbar.getView();
            TextView mTextView = mView.findViewById(R.id.snackbar_text);
            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetSideBets() {
        firstBallGreater.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        firstBallLesser.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        lastBallGreater.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        lastBallLesser.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        firstFiveBallGreater.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        firstFiveBallLesser.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        firstBallEven.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        firstBallOdd.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        lastBallEven.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        lastBallOdd.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        moreOdd.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));
        moreEven.setBackground(getActivity().getResources().getDrawable(R.drawable.background_bet_deselected_rounded));

        tvFirstBallGreater.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvFirstBallLesser.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvLastBallGreater.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvLastBallLesser.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvFirstBallEven.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvFirstBallOdd.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvLastBallEven.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvLastBallOdd.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvFirstFiveBallGreater.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvFirstFiveBallLesser.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvMoreOdd.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));
        tvMoreEven.setTextColor(getActivity().getResources().getColor(R.color.colorBlack));

        firstBallColorName.setText("");
        lastBallColorName.setText("");
        allBallColorname.setText("");

        sideBetMap.clear();

        setColorBallFirstBallAdapter();
        setColorBallLastBallAdapter();
        //setColorBallFiveBallAdapter();
        setSideBetUI();
        setPatternAdapter();
        tvBetValue.setText(Utils.getAmountWithCurrency("0", getActivity()));
        tvBetValue.setTag(0);
    }

    @Override
    public void getSelectedAdvanceDraws(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws) {
        LIST_ADVANCE_DRAWS.clear();
        tvNoOfDraw.setText("0");
        Log.e("draw", "Selected Advance Draws: " + listDraws);
        String numOfDraws = listDraws.size() + "";
        NUMBER_OF_DRAWS = listDraws.size();
        INDEX_CONSECUTIVE_DRAWS_LIST = -1;

        setPlusMinusButton();

        btnMinus.setBackgroundResource(R.drawable.minus_grey);
        btnMinus.setEnabled(false);

        IS_ADVANCE_PLAY = NUMBER_OF_DRAWS > 0;

        tvNoOfAdvanceDraw.setText(numOfDraws);

        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO draw : listDraws)
            LIST_ADVANCE_DRAWS.add(draw.getDrawId() + "");
        betValueCalculation();
        recalculatePanelAmount();

        tvDrawCount.setText(listDraws.size() == 1 ? listDraws.size() + " " + getString(R.string.draw) : listDraws.size() + " " + getString(R.string.draws));
    }

    private void resetAdvanceDraws() {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO data : gameResponse.getDrawRespVOs())
            data.setSelected(false);
        tvNoOfAdvanceDraw.setText("0");
        LIST_ADVANCE_DRAWS.clear();
        btnMinus.setBackgroundResource(R.drawable.minus_grey);
        btnMinus.setEnabled(false);
    }

    private void addPanel() {
        if (!isSideBetSelected) {
            PanelBean model = new PanelBean();
            if (SELECTED_BET_TYPE_OBJECT.getWinMode().equalsIgnoreCase("MAIN") && !SELECTED_PICK_TYPE_OBJECT.getCode().equalsIgnoreCase("BANKER")) {
                String pickedValues = "";
                for (String num : LIST_SELECTED_NUMBERS)
                    pickedValues = pickedValues + num + ",";
                pickedValues = pickedValues.trim();
                if (pickedValues.length() > 0 && pickedValues.charAt(pickedValues.length() - 1) == ',')
                    pickedValues = pickedValues.substring(0, pickedValues.length() - 1);

                model.setPickedValues(pickedValues);
                model.setTotalNumbers(LIST_SELECTED_NUMBERS.size());
            }

            model.setGameName(gameResponse.getGameName());
            model.setAmount(Double.parseDouble(tvBetValue.getTag().toString().trim()));
            model.setWinMode(SELECTED_BET_TYPE_OBJECT.getWinMode());
            model.setBetName(SELECTED_BET_TYPE_OBJECT.getBetDispName());
            model.setPickName(SELECTED_PICK_TYPE_OBJECT.getName());
            model.setBetCode(SELECTED_BET_TYPE_OBJECT.getBetCode());
            model.setPickCode(SELECTED_PICK_TYPE_OBJECT.getCode());
            model.setQuickPick(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"));
            model.setQpPreGenerated(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickMode().equalsIgnoreCase("QP"));
            model.setPickConfig(SELECTED_PICK_TYPE_OBJECT.getRange().get(0).getPickConfig());
            model.setBetAmountMultiple((int) (SELECTED_BET_AMOUNT / SELECTED_BET_TYPE_OBJECT.getUnitPrice()));
            model.setSelectedBetAmount(SELECTED_BET_AMOUNT);
            model.setNumberOfDraws(NUMBER_OF_DRAWS);
            model.setNumberOfLines(getNumberOfLines());

            Log.d("draw", "Panel Data: " + model);
            LIST_MULTI_PANEL.add(model);
            calculateTotalAmount();
            setMultiPanelAdapter();
        } else {
            if (sideBetMap.isEmpty())
                return;

            for (Map.Entry entry : sideBetMap.entrySet()) {
                entry.getKey();
                PanelBean panelBean = (PanelBean) entry.getValue();
                panelBean.setBetAmountMultiple(1);
                double amt = getBetObject(panelBean.getBetCode()).getUnitPrice();
                panelBean.setSelectedBetAmount((int) amt);
                panelBean.setNumberOfDraws(NUMBER_OF_DRAWS);
                panelBean.setNumberOfLines(getNumberOfLines());
                LIST_MULTI_PANEL.add(panelBean);
            }
            calculateTotalAmount();
            setMultiPanelAdapter();
            resetSideBets();
        }

        resetGame();
        //enableAddBetButton(LIST_MULTI_PANEL.size() < 8);

        if (cardBet.getVisibility() == View.GONE) {
            cardSchema.setVisibility(cardSchema.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            tvTitleAddTpPurchase.setVisibility(cardBet.getVisibility() == View.GONE ? View.GONE : View.VISIBLE);
            cardBet.setVisibility(cardBet.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
    }

    private void enableAddBetButton(boolean enable) {
        tvAddBet.setBackground(enable ? getResources().getDrawable(R.drawable.background_add_bet_red) :
                getResources().getDrawable(R.drawable.button_backgroud_disabled));
        tvAddBet.setTextColor(enable ? getResources().getColor(R.color.colorWhite) : getResources().getColor(R.color.colorWhite));
        tvAddBet.setClickable(enable);
        tvAddBet.setEnabled(enable);
    }

    private void calculateTotalAmount() {
        double amount = 0;
        for (PanelBean model : LIST_MULTI_PANEL)
            amount = amount + model.getAmount();
        String strAmount = getAmountWithCurrency(String.valueOf(amount));
        tvTotalAmount.setText(strAmount);
    }

    private void setSideBetData(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType, String TAG) {
        //betValueCalculation();
        PanelBean panelBean = new PanelBean();

        panelBean.setBetCode(TAG);
        panelBean.setPickConfig(pickType.getRange().get(0).getPickConfig());
        panelBean.setPickedValues(pickType.getRange().get(0).getPickValue());
        panelBean.setPickCode(pickType.getCode());
        panelBean.setQuickPick(false);
        panelBean.setQpPreGenerated(false);
        panelBean.setPickConfig(pickType.getRange().get(0).getPickConfig());
        panelBean.setBetAmountMultiple((int) 1);
        panelBean.setPickName(pickType.getName());
        panelBean.setGameName(gameResponse.getGameName());
        panelBean.setUnitPrice(getBetObject(TAG).getUnitPrice());
        panelBean.setWinMode(getBetObject(TAG).getWinMode());
        panelBean.setBetName(getBetObject(TAG).getBetDispName());
        panelBean.setTotalNumbers(1);
        panelBean.setSideBetHeader(pickType.getName());
        panelBean.setAmount(getBetValueAmount(getBetObject(TAG).getUnitPrice()));

        sideBetMap.put(TAG, panelBean);

        betValueCalculation(getBetObject(TAG).getUnitPrice());
    }

    @Override
    public void onLastBallClicked(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType) {
        //resetSideBets();
        setSideBetData(pickType, "LuckySixLastBallColor");
        lastBallColorName.setText("(" + getStringBetween(pickType.getRange().get(0).getPickValue()) + ")");
    }

    @Override
    public void onFirstBallClicked(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType) {
        //resetSideBets();
        setSideBetData(pickType, "LuckySixFirstBallColor");
        firstBallColorName.setText("(" + getStringBetween(pickType.getRange().get(0).getPickValue()) + ")");
    }

    @Override
    public void onPatternClicked(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType) {
        //resetSideBets();
        setSideBetData(pickType, "5/90Pattern");
    }

    public void print(FiveByNinetySaleResponseBean bean) {
        Intent intent = new Intent(getActivity(), PrintDrawGameActivity.class);
        intent.putExtra("PrintData", bean);
        intent.putExtra("Category", PrintDrawGameActivity.SALE);
        startActivityForResult(intent, REQUEST_CODE_PRINT);
    }

    private void sendData(final byte[] send, FiveByNinetySaleResponseBean bean) {
        AidlUtil.getInstance().sendRawData(send);
        int encode = 8;
        int position = 1;
        AidlUtil.getInstance().sendRawData(nextLine(1));
        String barcode = bean.getResponseData().getTicketNumber();
        AidlUtil.getInstance().printBitmap(getBitmap(barcode, 1, 384, 90));
        byte[] breakPartial = PrintUtil.feedPaperCutPartial();
        try {
            byte[] ticknumber = getStringByte(bean.getResponseData().getTicketNumber());
            byte[][] cmdBytes = {ticknumber, breakPartial};
            byte[] data = byteMerger(cmdBytes);
            AidlUtil.getInstance().sendRawData(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    Observer<FiveByNinetySaleResponseBean> observerSale = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null)
            Utils.showCustomErrorDialog(getActivity(), "Draw Games", getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            /*resetGame();
            deleteAllPanel();
            print(response);*/
            //DialogCloseListener listener = () -> { resetGame(); deleteAllPanel(); };
            resetGame();
            deleteAllPanel();
            //Utils.showCustomSuccessDialog(getContext(), getFragmentManager(), "", "TICKET SOLD", 0, listener);
            //String saleTime = LuckySixViewModel.getSaleResonseTime();
            print(response);
            saveTicket(response);
            resetNoOfDraws();
            resetAdvanceDraws();
            ((DrawGameBaseActivity) getActivity()).refreshBalance();

            if (viewSideBet.getVisibility() == View.VISIBLE)
                imageSwitchBets.callOnClick();

        } else {
            if (Utils.checkForSessionExpiryActivity(getActivity(), response.getResponseCode(), getActivity())) return;

            String errorMsg = Utils.getResponseMessage(getActivity(), "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(getActivity(), "Draw Games", errorMsg);
        }
    };

    private void resetNoOfDraws() {
        NUMBER_OF_DRAWS = 1;
        INDEX_CONSECUTIVE_DRAWS_LIST = 0;
        tvNoOfDraw.setText(LIST_CONSECUTIVE_DRAWS.get(0));
        tvDrawCount.setText(NUMBER_OF_DRAWS == 1 ? NUMBER_OF_DRAWS + " Draw" : NUMBER_OF_DRAWS + " Draws");
    }

    public Bitmap getBitmap(String barcode, int barcodeType, int width, int height) {
        Bitmap barcodeBitmap = null;
        BarcodeFormat barcodeFormat = convertToZXingFormat(barcodeType);
        try {
            barcodeBitmap = encodeAsBitmap(barcode, barcodeFormat, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return barcodeBitmap;
    }

    private static BarcodeFormat convertToZXingFormat(int format) {
        switch (format) {
            case 8:
                return BarcodeFormat.CODABAR;
            case 1:
                return BarcodeFormat.CODE_128;
            case 2:
                return BarcodeFormat.CODE_39;
            case 4:
                return BarcodeFormat.CODE_93;
            case 32:
                return BarcodeFormat.EAN_13;
            case 64:
                return BarcodeFormat.EAN_8;
            case 128:
                return BarcodeFormat.ITF;
            case 512:
                return BarcodeFormat.UPC_A;
            case 1024:
                return BarcodeFormat.UPC_E;
            //default 128?
            default:
                return BarcodeFormat.CODE_128;
        }
    }


    private static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        if (contents == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contents, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private static byte[] getStringByte(String byteString) throws UnsupportedEncodingException {
        return byteString.getBytes("gb2312");
    }

    public static byte[] nextLine(int lineNum) {
        byte[] result = new byte[lineNum];
        for (int i = 0; i < lineNum; i++) {
            //result[i] = LF;
        }

        return result;
    }

    public static byte[] byteMerger(byte[][] byteList) {

        int length = 0;
        for (int i = 0; i < byteList.length; i++) {
            length += byteList[i].length;
        }
        byte[] result = new byte[length];

        int index = 0;
        for (int i = 0; i < byteList.length; i++) {
            byte[] nowByte = byteList[i];
            for (int k = 0; k < byteList[i].length; k++) {
                result[index] = nowByte[k];
                index++;
            }
        }
        for (int i = 0; i < index; i++) {
            // CommonUtils.LogWuwei("", "result[" + i + "] is " + result[i]);
        }
        return result;
    }

    @Override
    public void onFirstFiveBallClicked(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType> pickTypes) {

    }

    @Override
    public void onFiveBallClicked(DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType) {
        //resetSideBets();
        setSideBetData(pickType, "LuckySixAllBallColor");
        allBallColorname.setText("(" + getStringBetween(pickType.getRange().get(0).getPickValue()) + ")");
    }

    private void saveTicket(FiveByNinetySaleResponseBean responseBean) {
        SharedPrefUtil.putLastTicketNumber(getActivity(), SharedPrefUtil.getString(getContext(), SharedPrefUtil.USERNAME),
                responseBean.getResponseData().getTicketNumber());
    }

    public int getCartSize() {
        if (LIST_MULTI_PANEL == null)
            return 0;
        else
            return LIST_MULTI_PANEL.size();
    }

    @Override
    public void onInputReceived(String input) {
        callQuickPickApi(input);
    }

    public void showLoader(boolean show, String message) {
        if (progressContainer != null) {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            setMessage(message);

            if (show) {
                if (drawDialog != null)
                    drawDialog.cancel();
                if (alertDialog != null)
                    alertDialog.cancel();
            }
        }
    }

    public void setMessage(String message) {
        if (loadingText != null && getActivity() != null)
            loadingText.setText(message != null && !message.equals("") ? message : getActivity().getString(R.string.loading));

        if (progressBar != null && getActivity() != null)
            progressBar.getIndeterminateDrawable().setColorFilter(getActivity().getResources().getColor(R.color.dialog_progress_dialog_color), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onShowLoader(String gameCode, boolean show) {
        showLoader(show, "");
    }

    @Override
    public void onUpdateGame(DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO) {
        gameResponse = gameRespVO;

        setLastWinningAdapter();
        if (drawDialog != null)
            drawDialog.cancel();
        if (alertDialog != null)
            alertDialog.cancel();
        if (confirmPurchaseDialog != null)
            confirmPurchaseDialog.cancel();
    }

    private String getStringBetween(String input) {
        input = input.substring(input.indexOf("<") + 1, input.lastIndexOf("<"));

        return input.toUpperCase();
    }

    private void switchToPickType(String pickCode, boolean isMaual) {
        if (isMaual) {
            pickTypeAdapter.onClick(0, true);
            return;
        }
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType>  type
                = setQPinPickType(SELECTED_BET_TYPE_OBJECT.getPickTypeData().getPickType());

        for (int index = 0; index < type.size(); index++) {
            DrawFetchGameDataResponseBean.ResponseData.GameRespVO.BetRespVO.PickTypeData.PickType pickType = type.get(index);
            if (pickType.getCode().equalsIgnoreCase(pickCode)) {
                tvDescription.setText(pickType.getDescription());
                SELECTED_PICK_TYPE_OBJECT = pickType;
                getSelectionLimits();
                setPreviousPickType(SELECTED_PICK_TYPE_OBJECT);
                if (pickTypeAdapter != null)
                    pickTypeAdapter.onClick(index, true);
                break;
            }
        }

    }

    @Override
    public void onPurchase() {
        callSaleApi();
    }

    @Override
    public void resetGameToManual() {
    }

    @Override
    public void onAmountChange(int amount, int position) {
        if (amount != -1) {
            betAmountAdapter.setSelected(position);
           // rvBetAmount.smoothScrollToPosition(position);
            linearLayoutManager.scrollToPositionWithOffset(position, 0);
            onBetAmountClick(amount);
            tvOtherAmount.setText(getAmountWithCurrency(String.valueOf(amount)));
        }
    }

    private String getAmountWithCurrency(String strAmount) {
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount),
                    Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(getContext())),
                    Utils.getAmountFormatter(SharedPrefUtil.getLanguage(getContext())),
                    Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(strAmount);
        }
        return formattedAmount;
    }
}
