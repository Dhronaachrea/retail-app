package com.skilrock.retailapp.sle_game_portrait;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.portrait_draw_games.ui.BaseActivity;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.SharedPrefUtil;

import java.util.List;

import androidx.annotation.Nullable;

public class ActivityDraws extends BaseActivity {
    private RecyclerView sports_recycle_view;
    public static List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeans;
    public static List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeansB2C;
    public static SleFetchDataB2C sleFetchDataB2C;
    public static SleFetchDataB2C cloneSle;
    private int totalNoOfMatch;
    SleDrawAdapter sleDrawAdapter=null;
    public static SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean gameTypeDataBean;
    //private LinearLayout back_click;
    //private TextView userName,balance;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_design);
        setToolBar();

        BaseClassSle.getBaseClassSle().setActivityDraws(this);

        /*back_click = findViewById(R.id.back_click);
        back_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userName = findViewById(R.id.userName);
        balance = findViewById(R.id.balance);
        userName.setText(PlayerData.getInstance().getUsername());
        balance.setText(PlayerData.getInstance().getUserBalance());*/

        totalNoOfMatch = getIntent().getIntExtra("totalNoOfMatch",3);


//        DialogBet dialogBet = new DialogBet(this);
//        dialogBet.show();

        sports_recycle_view = (RecyclerView) findViewById(R.id.sports_recycle_view);
        sports_recycle_view.setLayoutManager(new GridLayoutManager(this,1));
        setDraws();
        getScreenResolution(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBalance();
    }

    private void setToolBar() {
        ImageView ivGameIcon    = findViewById(R.id.ivGameIcon);
        TextView tvTitle        = findViewById(R.id.tvTitle);
        tvUserBalance           = findViewById(R.id.tvBal);
        tvUsername              = findViewById(R.id.tvUserName);
        llBalance               = findViewById(R.id.llBalance);

        tvTitle.setText(getString(R.string.soccer_13));
        ivGameIcon.setVisibility(View.GONE);
    }

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        ActivityDraws.this.finish();
    }

    public void setDraws(){
        sleDrawAdapter = new SleDrawAdapter(this,drawDataBeansB2C,true);
        sports_recycle_view.setAdapter(sleDrawAdapter);
        sleDrawAdapter.notifyDataSetChanged();
    }
    public void setDrawsAfterUpdate(){
        if (sleDrawAdapter!=null) {
            Toast.makeText(this, getString(R.string.updating_draws), Toast.LENGTH_SHORT).show();
            sleDrawAdapter = new SleDrawAdapter(this, drawDataBeansB2C, true);
            sports_recycle_view.setAdapter(sleDrawAdapter);
            sleDrawAdapter.notifyDataSetChanged();
        }
    }

    private static String getScreenResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return "{" + width + "," + height + "}";
    }

    public void startGamePlay(int position, boolean isB2C){
        Intent intent = new Intent(this, ActivityGamePlay.class);
        intent.putExtra("currentDraw",position);
        intent.putExtra("isB2C",isB2C);
        intent.putExtra("totalNoOfMatch",totalNoOfMatch);
        intent.putExtra("drawDate",SleDrawAdapter.getDateMonth(drawDataBeansB2C.get(position).getDrawDateTime().split(" ")[0]," "));
        BaseClassSle.getBaseClassSle().getDrawInfoBeans().clear();
        boolean isDrawPreset = false;
        for(SaleBean.DrawInfoBean drawInfoBean : BaseClassSle.getBaseClassSle().getDrawInfoBeans()){
            if(drawDataBeansB2C.get(position).getDrawId() == drawInfoBean.getDrawId()){
                isDrawPreset = true;
            }
        }
        if(!isDrawPreset){
            SaleBean.DrawInfoBean drawInfoBean = new SaleBean.DrawInfoBean();
            drawInfoBean.setDrawId(drawDataBeansB2C.get(position).getDrawId());
            drawInfoBean.setBetAmtMul(1);
            drawInfoBean.setDrawStatus(drawDataBeansB2C.get(position).getDrawStatus());
            drawInfoBean.setDrawFreezeTime(drawDataBeansB2C.get(position).getFtg());
            drawInfoBean.setDrawDateTime(drawDataBeansB2C.get(position).getDrawDateTime());
            drawInfoBean.setSaleStartTime(drawDataBeansB2C.get(position).getSaleStartTime());
            BaseClassSle.getBaseClassSle().getDrawInfoBeans().add(drawInfoBean);
        }

        startActivity(intent);

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

}
