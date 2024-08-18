package com.skilrock.retailapp.portrait_draw_games.ui;

import android.app.Activity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.skilrock.retailapp.dialog.DrawRefreshPortraitDialog;
import com.skilrock.retailapp.dialog.PanelDeletePortraitDialog;
import com.skilrock.retailapp.interfaces.GameChangeListener;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleRequestBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.portrait_draw_games.adapter.PurchaseDetailsAdapter;
import com.skilrock.retailapp.portrait_draw_games.dialog.DrawDialog;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.DrawGameData;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.RouletteColors;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.drawgames.DrawGamePurchaseViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class PurchaseDetailsActivity extends BaseActivity implements PurchaseDetailsAdapter.OnDeleteButtonClickedListener, View.OnClickListener, DrawDialog.SelectedDrawsListener, PanelDeletePortraitDialog.OnCartItemDelete {

    //private DrawFetchGameDataResponseBean.ResponseData.GameRespVO GAME_DATA;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private ArrayList<PanelBean> LIST_PANEL;

    private String LAST_GAME_CODE = "lastGameCode";
    private RecyclerView rvDetails;
    private TextView tvGameName, tvHeaderNoOfDraw, tvDrawCount, tvDrawCountAdvance, tvBetValue, tvNoOfBets;
    private ImageView ivMinus, ivPlus, ivAdvanceDraw;
    public static long LAST_CLICK_TIME = 0;
    public static int CLICK_GAP = 200;
    private boolean IS_ADVANCE_PLAY;
    private ArrayList<String> LIST_ADVANCE_DRAWS = new ArrayList<>();
    private PurchaseDetailsAdapter adapter;
    private int NUMBER_OF_DRAWS = 1, INDEX_CONSECUTIVE_DRAWS_LIST = 0;
    private ArrayList<String> LIST_CONSECUTIVE_DRAWS = new ArrayList<>();
    private DrawDialog drawDialog;
    private DrawGamePurchaseViewModel viewModel;
    private final int REQUEST_CODE_PRINT = 1111;
    private boolean IS_SALE_RESPONSE_SUCCESS = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
            setContentView(R.layout.activity_purchase_details_v2pro);
        else
            setContentView(R.layout.activity_purchase_details);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setInitialParameters();
        setToolBar();
        initializeWidgets();
        calculateTotalAmount();
        if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FullRoulette"))
            setPurchaseRouletteAdapter();
        else
            setPurchaseAdapter();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("DrawListener"));
        super.onResume();
        refreshBalance();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //GAME_DATA       = bundle.getParcelable("GameResponse");
            MENU_BEAN       = bundle.getParcelable("MenuBean");
            LIST_PANEL      = bundle.getParcelableArrayList("ListPanel");
        }
        else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            PurchaseDetailsActivity.this.finish();
        }
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

    private void initializeWidgets() {
        viewModel                   = ViewModelProviders.of(this).get(DrawGamePurchaseViewModel.class);
        CircularTextView ctv1       = findViewById(R.id.circle_1);
        CircularTextView ctv2       = findViewById(R.id.circle_2);
        CircularTextView ctvOr      = findViewById(R.id.circle_or);
        RelativeLayout rlBuy        = findViewById(R.id.rlBuy);
        TextView tvBetValueTag      = findViewById(R.id.tvBetValueTag);
        LinearLayout llAdvanceDraw  = findViewById(R.id.llAdvanceDraw);
        tvGameName                  = findViewById(R.id.tvGameName);
        tvHeaderNoOfDraw            = findViewById(R.id.tvHeaderNoOfDraw);
        tvDrawCount                 = findViewById(R.id.tvDrawCount);
        ivMinus                     = findViewById(R.id.ivMinus);
        ivPlus                      = findViewById(R.id.ivPlus);
        ivAdvanceDraw               = findViewById(R.id.ivAdvanceDraw);
        rvDetails                   = findViewById(R.id.rvDetails);
        tvDrawCountAdvance          = findViewById(R.id.tvDrawCountAdvance);
        tvBetValue                  = findViewById(R.id.tvBetValue);
        tvNoOfBets                  = findViewById(R.id.tvNoOfBets);

        viewModel.getLiveDataSale().observe(this, observerSale);
        String tag = getString(R.string.total_bet_value) + " (" + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PurchaseDetailsActivity.this)) + ")";
        tvBetValueTag.setText(tag);
        rlBuy.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
        //ivAdvanceDraw.setOnClickListener(this);
        llAdvanceDraw.setOnClickListener(this);
        ctv1.setStrokeColor("#e3e3e3");
        ctv2.setStrokeColor("#f5f5f5");
        ctvOr.setStrokeWidth(1);
        ctvOr.setStrokeColor("#94707070");
        ctvOr.setSolidColor("#e8e8e8");
        tvGameName.setText(LIST_PANEL.get(0).getGameName());
        tvHeaderNoOfDraw.setText(getString(R.string.one_draw));

        if (DrawGameData.getSelectedGame() != null && !TextUtils.isEmpty(DrawGameData.getSelectedGame().getConsecutiveDraw())) {
            LIST_CONSECUTIVE_DRAWS = new ArrayList<>(Arrays.asList(DrawGameData.getSelectedGame().getConsecutiveDraw().split(",")));
            if (LIST_CONSECUTIVE_DRAWS.size() > 0)
                tvDrawCount.setText(LIST_CONSECUTIVE_DRAWS.get(0));
        }

        calculateTotalAmount();
    }

    private void setPurchaseAdapter() {
        HashMap<String, String> mapNumberColor = new HashMap<>();
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> ball = DrawGameData.getSelectedGame().getNumberConfig().getRange().get(0).getBall();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball data: ball)
            mapNumberColor.put(data.getNumber(), FiveByNinetyColors.getBallColor(data.getColor()));
        adapter = new PurchaseDetailsAdapter(LIST_PANEL, this, this, mapNumberColor);
        rvDetails.setHasFixedSize(true);
        rvDetails.setItemAnimator(new DefaultItemAnimator());
        rvDetails.setLayoutManager((new LinearLayoutManager(this)));
        rvDetails.setAdapter(adapter);
    }

    private void setPurchaseRouletteAdapter() {
        HashMap<String, String> mapNumberColor = new HashMap<>();
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball> ball = DrawGameData.getSelectedGame().getNumberConfig().getRange().get(0).getBall();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.NumberConfig.Range.Ball data: ball)
            mapNumberColor.put(data.getNumber(), RouletteColors.getBallColor(data.getColor()));
        adapter = new PurchaseDetailsAdapter(LIST_PANEL, this, this, mapNumberColor);
        rvDetails.setHasFixedSize(true);
        rvDetails.setItemAnimator(new DefaultItemAnimator());
        rvDetails.setLayoutManager((new LinearLayoutManager(this)));
        rvDetails.setAdapter(adapter);
    }

    @Override
    public void onDeleteClicked(PanelBean panelBean, int index, String amount, String footer) {
        /*String info;
        if (!panelBean.getWinMode().equalsIgnoreCase("MAIN"))
            info = panelBean.getPickedValues() + " - " + amount + "\n\n" + footer;
        else
            info = panelBean.getPickedValues() + " - " + amount + "\n\n" + footer;*/
        String value = panelBean.isMainBet() ? panelBean.getPickedValues() : panelBean.getPickName();

        PanelDeletePortraitDialog dialog = new PanelDeletePortraitDialog(this, this, panelBean.getGameName(),
                value, footer, amount, index);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < CLICK_GAP) {
            return;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();

        switch(view.getId()) {
            case R.id.rlBuy:
                buyTicket();
                break;
            case R.id.ivMinus:
                onClickMinus();
                break;
            case R.id.ivPlus:
                onClickPlus();
                break;
            case R.id.llAdvanceDraw:
                showAdvanceDrawDialog();
                break;
        }
    }

    private void onClickPlus() {
        resetAdvanceDraws();

        IS_ADVANCE_PLAY = false;
        Log.w("draw", "INDEX (BEFORE): " + INDEX_CONSECUTIVE_DRAWS_LIST);
        try {
            if (INDEX_CONSECUTIVE_DRAWS_LIST < DrawGameData.getSelectedGame().getDrawRespVOs().size()) {
                NUMBER_OF_DRAWS = Integer.parseInt(LIST_CONSECUTIVE_DRAWS.get(++INDEX_CONSECUTIVE_DRAWS_LIST));
                String text = NUMBER_OF_DRAWS + "";
                tvDrawCount.setText(text);
                Log.w("draw", "INDEX (AFTER): " + INDEX_CONSECUTIVE_DRAWS_LIST);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        setPlusMinusButton();
        recalculatePanelAmount();
    }

    private void onClickMinus() {
        resetAdvanceDraws();
        IS_ADVANCE_PLAY = false;
        Log.i("draw", "INDEX (BEFORE): " + INDEX_CONSECUTIVE_DRAWS_LIST);
        if (INDEX_CONSECUTIVE_DRAWS_LIST > 0) {
            NUMBER_OF_DRAWS = Integer.parseInt(LIST_CONSECUTIVE_DRAWS.get(--INDEX_CONSECUTIVE_DRAWS_LIST));
            String text = NUMBER_OF_DRAWS + "";
            tvDrawCount.setText(text);
            Log.i("draw", "INDEX (AFTER): " + INDEX_CONSECUTIVE_DRAWS_LIST);
        }
        setPlusMinusButton();
        recalculatePanelAmount();
    }

    private void setPlusMinusButton() {
        ivMinus.setBackgroundResource(INDEX_CONSECUTIVE_DRAWS_LIST == 0 ? R.drawable.minus_d : R.drawable.minus_e);
        ivPlus.setBackgroundResource(INDEX_CONSECUTIVE_DRAWS_LIST == DrawGameData.getSelectedGame().getDrawRespVOs().size() - 1 ? R.drawable.plus_d : R.drawable.plus_e);
        ivPlus.setEnabled(INDEX_CONSECUTIVE_DRAWS_LIST != DrawGameData.getSelectedGame().getDrawRespVOs().size() - 1);
        ivMinus.setEnabled(INDEX_CONSECUTIVE_DRAWS_LIST != 0);
    }

    private void recalculatePanelAmount() {
        for (int index = 0; index < LIST_PANEL.size(); index++) {
            LIST_PANEL.get(index).setNumberOfDraws(NUMBER_OF_DRAWS);
            double amt = LIST_PANEL.get(index).getSelectedBetAmount() * LIST_PANEL.get(index).getNumberOfDraws() * LIST_PANEL.get(index).getNumberOfLines();
            LIST_PANEL.get(index).setAmount(amt);
        }
        if (adapter != null)
            adapter.notifyDataSetChanged();
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        double amount = 0;
        for (PanelBean model : LIST_PANEL)
            amount = amount + model.getAmount();
        //String strAmount = "$" + amount;
        int amt = (int) amount;
        //String strAmount = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(PurchaseDetailsActivity.this)) + amt;
        String strAmount = String.valueOf(amt);
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount), Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(this)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(this)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE()));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(amt);
        }
        tvBetValue.setText(formattedAmount);
        calculateNumberOfBets();
    }

    private void calculateNumberOfBets() {
        String number = LIST_PANEL.size() + "";
        tvNoOfBets.setText(number);
    }

    private void resetAdvanceDraws() {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO data : DrawGameData.getSelectedGame().getDrawRespVOs())
            data.setSelected(false);
        tvDrawCountAdvance.setText("0");
        LIST_ADVANCE_DRAWS.clear();
        ivMinus.setBackgroundResource(R.drawable.minus_d);
        ivMinus.setEnabled(false);
    }

    private void showAdvanceDrawDialog() {
        if (drawDialog != null)
            drawDialog.cancel();

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> drawRespVOs = DrawGameData.getSelectedGame().getDrawRespVOs();
        drawDialog = new DrawDialog(this, drawRespVOs, this, DrawGameData.getSelectedGame().getMaxAdvanceDraws());
        drawDialog.show();
        if (drawDialog.getWindow() != null) {
            drawDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            drawDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void getSelectedAdvanceDraws(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws) {
        Log.d("log", "SIZE: " + listDraws.size());
        if (listDraws.size() > 0) {
            LIST_ADVANCE_DRAWS.clear();
            tvDrawCount.setText("0");
            Log.e("draw", "Selected Advance Draws: " + listDraws);
            String numOfDraws = listDraws.size() + "";
            NUMBER_OF_DRAWS = listDraws.size();
            tvDrawCountAdvance.setText(numOfDraws);
            INDEX_CONSECUTIVE_DRAWS_LIST = -1;

            setPlusMinusButton();

            ivMinus.setBackgroundResource(R.drawable.minus_d);
            ivMinus.setEnabled(false);
            if (NUMBER_OF_DRAWS > 0) IS_ADVANCE_PLAY = true;
            for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO draw : listDraws)
                LIST_ADVANCE_DRAWS.add(draw.getDrawId() + "");
            recalculatePanelAmount();

            //tvDrawCount.setText(listDraws.size() == 1  ? listDraws.size() + " Draw" : listDraws.size() + " Draws");
        }
        else {
            //resetAdvanceDraws();
            onClickPlus();
        }
    }

    private void buyTicket() {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText( this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "buy");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            FiveByNinetySaleRequestBean model = new FiveByNinetySaleRequestBean();
            model.setMerchantCode(urlBean.getUserName());

            FiveByNinetySaleRequestBean.MerchantData merchantData = new FiveByNinetySaleRequestBean.MerchantData();
            merchantData.setUserId(Integer.parseInt(PlayerData.getInstance().getUserId()));
            merchantData.setUserName(PlayerData.getInstance().getUsername());
            merchantData.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            model.setMerchantData(merchantData);

            model.setGameCode(DrawGameData.getSelectedGame().getGameCode());
            model.setCurrencyCode(Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)));
            model.setPurchaseDeviceId(Utils.getDeviceSerialNumber());
            model.setPurchaseDeviceType(AppConstants.PURCHASE_TYPE);
            model.setLastTicketNumber(SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)));
            model.setModelCode(Utils.getModelCode());

            model.setIsAdvancePlay(IS_ADVANCE_PLAY);
            model.setNoOfDraws(NUMBER_OF_DRAWS);

            ArrayList<FiveByNinetySaleRequestBean.DrawData> list = new ArrayList<>();
            if (IS_ADVANCE_PLAY && LIST_ADVANCE_DRAWS.size() > 0) {
                for (String drawId: LIST_ADVANCE_DRAWS) {
                    FiveByNinetySaleRequestBean.DrawData drawData = new FiveByNinetySaleRequestBean.DrawData();
                    drawData.setDrawId(drawId);
                    list.add(drawData);
                }
            }
            model.setDrawData(list);

            ArrayList<FiveByNinetySaleRequestBean.PanelData> listPanel = new ArrayList<>();
            for (PanelBean panelData: LIST_PANEL) {
                FiveByNinetySaleRequestBean.PanelData modelPanel = new FiveByNinetySaleRequestBean.PanelData();
                //modelPanel.setPickType(panelData.getPickName().replace("QP", "").trim());
                modelPanel.setPickType(panelData.getPickCode());
                modelPanel.setBetType(panelData.getBetCode());
                //modelPanel.setPickType(panelData.getPickName());
                modelPanel.setPickConfig(panelData.getPickConfig());
                modelPanel.setBetAmountMultiple(panelData.getBetAmountMultiple());
                modelPanel.setQuickPick(panelData.getQuickPick());
                modelPanel.setQpPreGenerated(panelData.getQpPreGenerated());
                modelPanel.setPickedValues(panelData.getPickedValues());
                modelPanel.setTotalNumbers(panelData.getTotalNumbers());
                listPanel.add(modelPanel);
            }
            model.setPanelData(listPanel);

            viewModel.callSaleApi(urlBean, model);
        }
    }

    Observer<FiveByNinetySaleResponseBean> observerSale = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        IS_SALE_RESPONSE_SUCCESS = false;
        if (response == null)
            Utils.showCustomErrorDialog(this, DrawGameData.getSelectedGame().getGameName(), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            IS_SALE_RESPONSE_SUCCESS = true;
            Intent intent = new Intent(PurchaseDetailsActivity.this, PrintDrawGameActivity.class);
            intent.putExtra("PrintData", response);
            intent.putExtra("Category", PrintDrawGameActivity.SALE);
            startActivityForResult(intent, REQUEST_CODE_PRINT);
            /*print(response, "");
            LIST_PANEL.clear();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("ListBetData", LIST_PANEL);
            setResult(Activity.RESULT_OK, returnIntent);
            PurchaseDetailsActivity.this.finish();*/
        } else {
            if (response.getResponseCode() != 1004 && response.getResponseCode() != 1015) {
                if (Utils.checkForSessionExpiryActivity(PurchaseDetailsActivity.this, response.getResponseCode(), PurchaseDetailsActivity.this))
                    return;
            }

            String errorMsg = Utils.getResponseMessage(PurchaseDetailsActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, DrawGameData.getSelectedGame().getGameName(), errorMsg);
        }
    };

    @Override
    public void onCartItemDelete(boolean flag, int index) {
        if (flag) {
            LIST_PANEL.remove(index);
            if (LIST_PANEL.isEmpty()) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("ListBetData", LIST_PANEL);
                returnIntent.putExtra("doBalanceUpdate", false);
                setResult(Activity.RESULT_OK, returnIntent);
                PurchaseDetailsActivity.this.finish();
            }
            else {
                if (adapter != null)
                    adapter.notifyDataSetChanged();
                calculateTotalAmount();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        performBackOperation();
    }

    public void onClickBack(View view) {
        if (SystemClock.elapsedRealtime() - LAST_CLICK_TIME < CLICK_GAP) {
            return;
        }
        LAST_CLICK_TIME = SystemClock.elapsedRealtime();

        performBackOperation();
    }

    private void performBackOperation() {
        if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FullRoulette")) {
            if (!LIST_PANEL.isEmpty()) {
                GameChangeListener listener = super::onBackPressed;
                Utils.showGameSwitchDialog(this, R.drawable.spin_win, getString(R.string.items_in_cart_lost_msg_back), getString(R.string.do_you_want_to_leave), getString(R.string.stay), getString(R.string.leave), listener);
            }
            else {
                resetAdvanceDraws();
                IS_ADVANCE_PLAY = false;
                tvDrawCount.setText(LIST_CONSECUTIVE_DRAWS.get(0));
                NUMBER_OF_DRAWS = Integer.parseInt(LIST_CONSECUTIVE_DRAWS.get(0));
                recalculatePanelAmount();
                Intent returnIntent = new Intent();
                LIST_PANEL.clear();
                returnIntent.putExtra("ListBetData", LIST_PANEL);
                returnIntent.putExtra("doBalanceUpdate", false);
                returnIntent.putExtra("isSaleResponseSuccess", false);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
        else {
            resetAdvanceDraws();
            IS_ADVANCE_PLAY = false;
            tvDrawCount.setText(LIST_CONSECUTIVE_DRAWS.get(0));
            NUMBER_OF_DRAWS = Integer.parseInt(LIST_CONSECUTIVE_DRAWS.get(0));
            recalculatePanelAmount();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("ListBetData", LIST_PANEL);
            returnIntent.putExtra("doBalanceUpdate", false);
            returnIntent.putExtra("isSaleResponseSuccess", IS_SALE_RESPONSE_SUCCESS);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO data : DrawGameData.getSelectedGame().getDrawRespVOs())
            data.setSelected(false);
        super.onDestroy();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //LoaderFlag 1, means start loader
            //LoaderFlag 0, means stop loader
            String gameCode     = intent.getStringExtra("GameCode");
            int loaderFlag      = intent.getIntExtra("LoaderFlag", -1);
            Log.e("draw", "GameCode: " + gameCode);
            Log.e("draw", "LoaderFlag: " + loaderFlag);

            if (gameCode.equalsIgnoreCase(DrawGameData.getSelectedGame().getGameCode())) {
                if (loaderFlag == 1) {
                    //Toast.makeText(PurchaseDetailsActivity.this, "Updating Draws", Toast.LENGTH_LONG).show();
                    showColoredToast(getString(R.string.updating_draws));
                    ProgressBarDialog.getProgressDialog().showProgress(PurchaseDetailsActivity.this);
                }
                else {
                    ProgressBarDialog.getProgressDialog().dismiss();
                    if (IS_ADVANCE_PLAY && !LIST_ADVANCE_DRAWS.isEmpty()) {
                        ivPlus.performClick();
                        //Toast.makeText(PurchaseDetailsActivity.this, "Your draws has been updated, please reselect your draw!", Toast.LENGTH_SHORT).show();
                        DrawRefreshPortraitDialog dialog = new DrawRefreshPortraitDialog(PurchaseDetailsActivity.this, DrawGameData.getSelectedGame().getGameName());
                        dialog.show();
                        if (dialog.getWindow() != null) {
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("log", "Request Code: " + requestCode + ", Result Code: " + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_PRINT:
                Log.d("TAg", "data: " + data);
                if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isPrintSuccess")) {
                    LIST_PANEL.clear();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("ListBetData", LIST_PANEL);
                    returnIntent.putExtra("doBalanceUpdate", true);
                    returnIntent.putExtra("isSaleResponseSuccess", IS_SALE_RESPONSE_SUCCESS);
                    setResult(Activity.RESULT_OK, returnIntent);
                    PurchaseDetailsActivity.this.finish();
                }
                else  {
                    String errorMsg = getString(R.string.insert_paper_to_print);
                    Utils.showCustomErrorDialog(this, DrawGameData.getSelectedGame().getGameName(), errorMsg);
                }
                break;
        }
    }

    private void showColoredToast(String message) {
        Toast toast = Toast.makeText(PurchaseDetailsActivity.this, message, Toast.LENGTH_SHORT);
        View view = toast.getView();

        view.getBackground().setColorFilter(Color.parseColor("#012161"), PorterDuff.Mode.SRC_IN);

        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }
}
