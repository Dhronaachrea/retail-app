package com.skilrock.retailapp.landscape_draw_games.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.drawgame.DrawAdapter;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.MoreResultViewPager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator;


public class MoreResultDialog extends Dialog implements View.OnClickListener, AppConstants {

    private Context context;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> listDraws;
    private DrawAdapter drawAdapter;
    private ImageView imageCancel, imageLogo, imageLeft, imageRight;
    private int maxAdvanceDraws;
    private MoreResultViewPager viewPagerAdapter;
    private CircleIndicator circleIndicator;
    private int CURRENT_TAB = 0;
    private int LAST_TAB = 0;
    private ViewPager viewPager;
    private DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO;

    public MoreResultDialog(Context context, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> listDraws, int maxAdvanceDraws,
                            DrawFetchGameDataResponseBean.ResponseData.GameRespVO gameRespVO) {
        super(context);
        this.context = context;
        this.listDraws = listDraws;
        this.maxAdvanceDraws = maxAdvanceDraws;
        this.gameRespVO = gameRespVO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setDimAmount(0.7f);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;

        setContentView(R.layout.layout_more_result);

        initializeWidgets();
    }

    private void initializeWidgets() {
        imageCancel = findViewById(R.id.image_cancel);
        imageLeft = findViewById(R.id.image_left);
        imageRight = findViewById(R.id.image_right);
        imageLogo = findViewById(R.id.image_logo);
        viewPager = findViewById(R.id.view_pager);

        circleIndicator = findViewById(R.id.circle);

        circleIndicator.setBackgroundColor(Color.parseColor("#90000000"));

        viewPagerAdapter = new MoreResultViewPager(context, listDraws, gameRespVO.getGameCode().toLowerCase());
        viewPager.setAdapter(viewPagerAdapter);
        LAST_TAB = listDraws.size();
        circleIndicator.setViewPager(viewPager);
        Drawable drawable = null;

        switch (gameRespVO.getGameCode().toLowerCase()) {
            case LUCKY_SIX:
                //drawable = context.getResources().getDrawable(R.drawable.lucky_6_large);
                Picasso.with(context).load(R.drawable.lucky_6).into(imageLogo);
                viewPager.setPageMargin(10);
                break;
            case FULL_ROULETTE:
                //  Picasso.with(context).load(R.drawable.roulette).into(imageLogo);
                // imageLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.roulette));
                break;
            case FIVE_BY_NINETY:
                imageLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.five_by_ninety));
                RelativeLayout.LayoutParams prams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 230);
                prams.addRule(RelativeLayout.END_OF, R.id.image_left);
                prams.setMargins(15, 0, 15, 0);
                prams.addRule(RelativeLayout.START_OF, R.id.image_right);
                viewPager.setLayoutParams(prams);
                viewPager.setPageMargin(5);
                break;
            case THAI_LOTTERY:
               // imageLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.thai_lottery));
                break;
            case SUPER_KENO:
               // Picasso.with(context).load(R.drawable.super_keno).into(imageLogo);
                //imageLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.super_keno));
                break;
        }

        imageCancel.setOnClickListener(this);
        imageLeft.setOnClickListener(this);
        imageRight.setOnClickListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                CURRENT_TAB = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_cancel:
                dismiss();

                break;
            case R.id.image_right:
                if (CURRENT_TAB == LAST_TAB) return;
                viewPager.setCurrentItem(CURRENT_TAB + 1, true);

                break;
            case R.id.image_left:
                if (CURRENT_TAB == 0) return;
                viewPager.setCurrentItem(CURRENT_TAB - 1, true);

                break;
        }
    }
}
