package com.skilrock.retailapp.sle_game_portrait;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.Objects;

public class ResultPreviewActivitySle extends BaseActivity implements ResultPreviewAdapterSle.OnPrintClickListener{
    private RecyclerView rvResultPreview;
    private ResultPreviewAdapterSle previewAdapter;
    private ResultPrintBean resultResponseBean;
    private final int REQUEST_CODE_PRINT = 03;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            setContentView(R.layout.activity_result_preview_t2mini);
        }else{
            setContentView(R.layout.activity_result_preview);
        }
        setInitialParameters();
        setToolBar();
        initializeWidgets();
    }

    private void setToolBar() {
        ImageView ivGameIcon = findViewById(R.id.ivGameIcon);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvUserBalance = findViewById(R.id.tvBal);
        tvUsername = findViewById(R.id.tvUserName);
        llBalance = findViewById(R.id.llBalance);

        tvTitle.setText(getString(R.string.result));
        ivGameIcon.setVisibility(View.GONE);

    }


    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            resultResponseBean = new Gson().fromJson(getIntent().getExtras().getString("response"), ResultPrintBean.class);
        }

    }

    private void initializeWidgets() {
        rvResultPreview = findViewById(R.id.rv_result);
        rvResultPreview.setHasFixedSize(true);
        rvResultPreview.setLayoutManager(new GridLayoutManager(this, 1));
        previewAdapter = new ResultPreviewAdapterSle(this, resultResponseBean.getDrawWiseResultList(), this);
        rvResultPreview.setAdapter(previewAdapter);

    }

    public void onClickBack(View view) {
        ResultPreviewActivitySle.this.finish();
    }

    @Override
    public void onPrintClick(int position) {
        if (position != -1) {
            Intent intent = new Intent(ResultPreviewActivitySle.this, PrintActivityResult.class);
            intent.putExtra("PrintDataResult", resultResponseBean);
            intent.putExtra("print", "Result");
            intent.putExtra("item", position);
            startActivityForResult(intent, 0011);
        }else {
            Utils.showErrorMessage(ResultPreviewActivitySle.this,getString(R.string.no_result_print));
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isPrintSuccess")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isPrintSuccess", true);
            setResult(Activity.RESULT_OK, returnIntent);
            ResultPreviewActivitySle.this.finish();
        } else {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(this, getString(R.string.result), errorMsg);
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }
}