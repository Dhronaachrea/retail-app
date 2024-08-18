package com.skilrock.retailapp.portrait_draw_games.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.interfaces.GameName;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.ResultRequestBean;
import com.skilrock.retailapp.models.drawgames.ResultResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.portrait_draw_games.adapter.ResultDrawGameAdapter;
import com.skilrock.retailapp.portrait_draw_games.viewmodels.ResultViewModel;
import com.skilrock.retailapp.ui.activities.drawgames.ResultPreviewActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import java.util.ArrayList;

import static com.skilrock.retailapp.utils.Utils.formatTimeResult;
import static com.skilrock.retailapp.utils.Utils.setResultDate;

public class ResultDrawDialogActivity extends AppCompatActivity implements View.OnClickListener, GameName {
    Button btn_cancel, btn_print;
    RecyclerView rv_draw_game;
    ResultDrawGameAdapter resultDrawGameAdapter;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    EditText et_date_selection, et_from_time, et_to_time;
    TextView resultSelection;
    ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO> games;
    public int lastSelectedPosition = 0;
    private ResultViewModel resultViewModel;
    private ResultResponseBean resultResponseBean;
    private final int REQUEST_CODE_PRINT = 03;
    String bold = "<string></string>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO)) {
            setContentView(R.layout.activity_result_draw_v2pro);
        }
        else if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)){
            setContentView(R.layout.activity_result_draw_t2mini);
        }
        else{
            setContentView(R.layout.activity_result_draw);
        }
        ResultDrawDialogActivity.this.setFinishOnTouchOutside(false);
        setInitialParameters();
        initializeWidgets();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MENU_BEAN = bundle.getParcelable("MenuBean");
            games = bundle.getParcelableArrayList("GameResponse");
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            ResultDrawDialogActivity.this.finish();
        }
    }

    private void initializeWidgets() {
        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        resultViewModel.getLiveDataResult().observe(this, observerResult);
        btn_cancel = findViewById(R.id.btn_cancel);
        rv_draw_game = findViewById(R.id.rv_draw_game);
        et_date_selection = findViewById(R.id.et_date_selection);
        et_from_time = findViewById(R.id.et_from_time);
        et_to_time = findViewById(R.id.et_to_time);
        resultSelection = findViewById(R.id.resultSelection);
        btn_print = findViewById(R.id.btn_print);
        et_date_selection.setFocusable(false);
        et_from_time.setFocusable(false);
        et_to_time.setFocusable(false);
        rv_draw_game.setHasFixedSize(true);
        btn_cancel.setOnClickListener(this);
        btn_print.setOnClickListener(this);
        et_date_selection.setText(getString(R.string.date_selection));
        resultSelection.setText(getString(R.string.select_date_to_print) + " " + games.get(0).getGameName().toUpperCase() + " " + getString(R.string.result_small));
        rv_draw_game.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        resultDrawGameAdapter = new ResultDrawGameAdapter(this, games, this);
        rv_draw_game.setAdapter(resultDrawGameAdapter);
        resultDrawGameAdapter.notifyDataSetChanged();
        et_date_selection.setOnClickListener(view -> setResultDate(this, et_date_selection));
        et_from_time.setOnClickListener(this);
        et_to_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_cancel) {
            this.finish();
        } else if (view.getId() == R.id.btn_print) {
                printResult();
        }
        else if (view.getId() == R.id.et_from_time) {
            Utils.setTimeFrom(ResultDrawDialogActivity.this, et_from_time);
        }
        else if (view.getId() == R.id.et_to_time) {
            Utils.setTimeTo(ResultDrawDialogActivity.this, et_to_time);
        }
    }

    private void printResult() {
        if (validate()) {
            if (Utils.compareTime(et_from_time.getText().toString(), et_to_time.getText().toString(),this)) {
                if (resultResponseBean != null) {
                    resultResponseBean = getMatch(resultResponseBean, et_from_time.getText().toString(), et_to_time.getText().toString());
                }else {
                    Utils.showCustomErrorDialog(this,getString(R.string.result),getString(R.string.no_result_found));
                }
                if (resultResponseBean.getResponseData().get(0).getResultData().get(0).getResultInfo() != null && resultResponseBean.getResponseData().get(0).getResultData().get(0).getResultInfo().size() == 0) {
                    Utils.showCustomErrorDialog(this, getString(R.string.result), getString(R.string.no_result_provided));
                } else {
                    Intent intent = new Intent(ResultDrawDialogActivity.this, ResultPreviewActivity.class);
                    intent.putExtra("Result", resultResponseBean);
                    startActivityForResult(intent,REQUEST_CODE_PRINT);
                }
            }
        }
    }

    private ResultResponseBean getMatch(ResultResponseBean resultResponseBean, String fromTime, String toTime) {
        ResultResponseBean result;
        ArrayList<ResultResponseBean.ResponseDatum.ResultDatum.ResultInfo> resultInfoArrayList = new ArrayList<>();

        result = resultResponseBean;

        for (int index = 0; index < resultResponseBean.getResponseData().get(0).getResultData().get(0).getResultInfo().size(); index++) {

            ResultResponseBean.ResponseDatum.ResultDatum.ResultInfo resultInfo = resultResponseBean.getResponseData().get(0).getResultData().get(0).getResultInfo().get(index);
            String actualTime = resultInfo.getDrawTime();

            if (Utils.isValidTime(fromTime, toTime, actualTime)) {
                resultInfoArrayList.add(resultInfo);
            }
        }

        result.getResponseData().get(0).getResultData().get(0).setResultInfo(null);
        result.getResponseData().get(0).getResultData().get(0).setResultInfo(resultInfoArrayList);

        return result;
    }

    public void callResult() {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText(this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "getResult");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            ResultRequestBean resultRequestBean = new ResultRequestBean();
            resultRequestBean.setFromDate(formatTimeResult(et_date_selection.getText().toString()) + " 00:00:00");
            resultRequestBean.setToDate(formatTimeResult(et_date_selection.getText().toString()) + " 23:59:59");
            resultRequestBean.setGameCode(games.get(lastSelectedPosition).getGameCode());
            resultRequestBean.setMerchantCode("LotteryRMS");
            resultRequestBean.setOrderByOperator("DESC");
            resultRequestBean.setOrderByType("draw_datetime");
            resultRequestBean.setPage(1);
            resultRequestBean.setSize(5);
            resultViewModel.callResult(urlBean, resultRequestBean);
        }
    }

    private boolean validate() {
        if (et_date_selection.getText().toString().equalsIgnoreCase(getString(R.string.date_selection))) {
            Utils.showRedToast(ResultDrawDialogActivity.this,getString(R.string.select_date));
            return false;
        }
        if (et_from_time.getText().toString().equalsIgnoreCase(getString(R.string.from_time)) || et_to_time.getText().toString().equalsIgnoreCase(getString(R.string.to_time))) {
            Utils.showRedToast(ResultDrawDialogActivity.this,getString(R.string.slelect_time));
            return false;
        }
        return true;
    }

    @Override
    public void getGameName(String game_code, String game_name) {
        resultSelection.setText(getString(R.string.select_date_to_print) + " " + game_name.toUpperCase() + " " + getString(R.string.result_small));
        resultDrawGameAdapter.notifyDataSetChanged();
        resultResponseBean = null;
        resetValue();
    }

    Observer<ResultResponseBean> observerResult = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        ErrorDialogListener listener = ResultDrawDialogActivity.this::onDialogClosed;
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.result), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            resultResponseBean = response;

        } else {
            if (Utils.checkForSessionExpiryActivity(ResultDrawDialogActivity.this, response.getResponseCode(), ResultDrawDialogActivity.this))
                return;

            String errorMsg = Utils.getResponseMessage(ResultDrawDialogActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.result), errorMsg,listener);
        }
    };

    private void onDialogClosed() {
        resetValue();
    }

    private void resetValue() {
        et_date_selection.setText(getString(R.string.date_selection));
        et_to_time.setText(getString(R.string.to_time));
        et_from_time.setText(getString(R.string.from_time));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isPrintSuccess")) {
            ResultDrawDialogActivity.this.finish();
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }


}