package com.skilrock.retailapp.landscape_draw_games.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.BankerBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;

import java.util.ArrayList;

public class LowerLineAdapter extends RecyclerView.Adapter<LowerLineAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<BankerBean> listNumber;
    private Context context;

    public LowerLineAdapter(ArrayList<BankerBean> listNumber, Context context) {
        this.listNumber         = listNumber;
        this.context            = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dg_banker_number_box_landscape, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        BankerBean ball = listNumber.get(position);
        holder.tvNumber.setText(ball.getNumber());
        //holder.tvNumber.setTag(ball.getColor());

        holder.tvNumber.setBackgroundResource(R.drawable.bg_purchase_main_numbers);
        GradientDrawable background = (GradientDrawable) holder.tvNumber.getBackground();
        background.setColor(Color.parseColor("#ffffff"));

        if (!ball.getNumber().isEmpty()) {
            background.setColor(Color.parseColor(FiveByNinetyColors.getBallColor(ball.getColor())));
            holder.tvNumber.setTypeface(null, Typeface.BOLD);
            holder.tvNumber.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            background.setColor(Color.parseColor("#ffffff"));
            holder.tvNumber.setTypeface(null, Typeface.NORMAL);
            holder.tvNumber.setTextColor(Color.parseColor("#aaaaaa"));
        }
    }

    private void setSelected(int position) {
        for (int index = 0; index < listNumber.size(); index++) {
            if (position == index)
                listNumber.get(index).setSelected(true);
            else
                listNumber.get(index).setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return listNumber.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumber;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvNumber = view.findViewById(R.id.tvNumber);
        }
    }

    public interface OnQpNumberSelection {
        void onQpNumberSelection(int count);
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
