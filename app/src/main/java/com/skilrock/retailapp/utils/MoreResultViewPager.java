package com.skilrock.retailapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.landscape_draw_games.adapter.ViewMoreResultBallsAdapter;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class MoreResultViewPager extends PagerAdapter implements AppConstants {
    private Context context;
    private String gameCode;
    private HashMap<String, String> mapNumberConfig = new HashMap<>();
    private int columnCount = 1;
    private ViewMoreResultBallsAdapter lastWinningLuckySixAdapter;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> listDraws;

    public MoreResultViewPager(Context context, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.LastDrawWinningResultVO> listDraws, String gameCode) {
        this.context = context;
        this.listDraws = listDraws;
        this.gameCode = gameCode;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (listDraws.get(position).getWinningNumber() == null || !listDraws.get(position).getWinningNumber().contains(",")) {
            return null;
        }
        ArrayList<String> ballsArray = new ArrayList<>(Arrays.asList(listDraws.get(position).getWinningNumber().split(",")));

        View view = LayoutInflater.from(context).inflate(R.layout.item_more_result, null);
        RecyclerView recyclerViewBalls = view.findViewById(R.id.rvBalls);
        RecyclerView recyclerViewFiveBalls = view.findViewById(R.id.rv_five_balls);
        View view_ = view.findViewById(R.id.view);

        TextView tvTime = view.findViewById(R.id.tv_date);

        ArrayList<String> listDrawsTopFive = new ArrayList<>();
        ArrayList<String> listDrawsOther = new ArrayList<>();

        if (gameCode.equalsIgnoreCase(LUCKY_SIX) || gameCode.equalsIgnoreCase(FIVE_BY_NINETY)) {
            columnCount = 15;
            if (gameCode.equalsIgnoreCase(LUCKY_SIX))
                mapNumberConfig = LuckySixBallColorMap.getMapNumberConfig();
            else {
                mapNumberConfig = LuckySixBallColorMap.getMapNumberConfigFiveByNinty();
                view_.setVisibility(View.GONE);
            }
            for (int index = 0; index < 5; index++) {
                listDrawsTopFive.add(ballsArray.get(index));
            }

            ViewMoreResultBallsAdapter lastWinningLuckySixAdapterTop = new ViewMoreResultBallsAdapter(listDrawsTopFive, mapNumberConfig, context, "bigger", gameCode, listDraws.get(position).getRunTimeFlagInfo());
            recyclerViewFiveBalls.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewFiveBalls.setHasFixedSize(true);
            recyclerViewFiveBalls.setItemAnimator(new DefaultItemAnimator());
            recyclerViewFiveBalls.setAdapter(lastWinningLuckySixAdapterTop);
            for (int index = 5; index < ballsArray.size(); index++) {
                listDrawsOther.add(ballsArray.get(index));
            }

        } else {
            columnCount = 10;
            mapNumberConfig = LuckySixBallColorMap.getMapNumberConfigFiveByNinty();

            recyclerViewFiveBalls.setVisibility(View.GONE);
            listDrawsOther.addAll(ballsArray);
        }

        if (!gameCode.equalsIgnoreCase(FIVE_BY_NINETY)) {
            ViewMoreResultBallsAdapter lastWinningLuckySixAdapter = new ViewMoreResultBallsAdapter(listDrawsOther, mapNumberConfig, context, "small", gameCode,listDraws.get(position).getRunTimeFlagInfo());
            recyclerViewBalls.setLayoutManager(new GridLayoutManager(context, columnCount));
            recyclerViewBalls.setHasFixedSize(true);
            recyclerViewBalls.setItemAnimator(new DefaultItemAnimator());
            recyclerViewBalls.setAdapter(lastWinningLuckySixAdapter);
        }
        tvTime.setText(formatTime(listDraws.get(position).getLastDrawDateTime()));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return listDraws.size();
    }

    @Override
    public float getPageWidth(int position) {
        if (gameCode.equalsIgnoreCase(FIVE_BY_NINETY))
            return 0.498f;
        else
            return 1f;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    private String formatTime(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat(" EEEE, dd MMM yyyy\n HH:mm");
        Date date;
        try {
            date = originalFormat.parse(dateTime);

        } catch (ParseException ex) {
            ex.printStackTrace();
            return "";
        }
        return targetFormat.format(date);
    }
}