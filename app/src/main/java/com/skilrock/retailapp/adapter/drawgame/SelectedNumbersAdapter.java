package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.LuckySixBallColorMap;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectedNumbersAdapter extends RecyclerView.Adapter<SelectedNumbersAdapter.GameViewHolder> {

    private ArrayList<String> listNumbers;
    private HashMap<String, String> map;
    private Context context;

    public SelectedNumbersAdapter(ArrayList<String> listNumbers, HashMap<String, String> map, Context context) {
        this.context = context;
        this.listNumbers = listNumbers;
        this.map = map;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_five_by_ninety_selected_numbers, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        String number = listNumbers.get(position);
        if (number != null) {
            holder.tvSelectedNumber.setBackgroundResource(R.drawable.five_by_ninety_numbers);

            GradientDrawable background = (GradientDrawable) holder.tvSelectedNumber.getBackground();
            background.setColor(Color.parseColor(getBallColor(number)));
            holder.tvSelectedNumber.setText(number);
        } else {
            holder.tvSelectedNumber.setBackgroundResource(R.drawable.five_by_ninety_numbers);

            GradientDrawable background = (GradientDrawable) holder.tvSelectedNumber.getBackground();
            background.setColor(Color.parseColor("#FFFFFF"));
            holder.tvSelectedNumber.setText("");
        }
    }

    public void updateSelectedNumbers(ArrayList<String> list) {
        listNumbers = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listNumbers.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvSelectedNumber;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvSelectedNumber = view.findViewById(R.id.tvSelectedNumber);
        }
    }

    public String getBallColor(String ballNumber) {
        try {
            return FiveByNinetyColors.getBallColor(LuckySixBallColorMap.getMapNumberConfigFiveByNinty().get(ballNumber));
        } catch (Exception e) {
            return "#ff0000";
        }
    }
}