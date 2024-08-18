package com.skilrock.retailapp.landscape_draw_games.adapter;

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
import com.skilrock.retailapp.interfaces.InputListener;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.LuckySixBallColorMap;

import java.util.ArrayList;

public class QuickPickNumberAdapter extends RecyclerView.Adapter<QuickPickNumberAdapter.GameViewHolder> {

    private ArrayList<String> listNumbers;
    private Context context;
    private InputListener inputListener;

    public QuickPickNumberAdapter(ArrayList<String> listNumbers, Context context, InputListener inputListener) {
        this.context        = context;
        this.listNumbers    = listNumbers;
        this.inputListener  = inputListener;
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
            holder.tvSelectedNumber.setText(number);
        }
        holder.tvSelectedNumber.setBackgroundResource(R.drawable.five_by_ninety_numbers);
        holder.tvSelectedNumber.setTextColor(context.getResources().getColor(R.color.colorBlack));

        GradientDrawable background = (GradientDrawable) holder.tvSelectedNumber.getBackground();
        background.setColor(Color.parseColor("#FFFFFF"));

        holder.tvSelectedNumber.setOnClickListener(v -> {
            holder.tvSelectedNumber.setBackgroundResource(R.drawable.five_by_ninety_numbers);

            background.setColor(context.getResources().getColor(R.color.new_five_selection));
            inputListener.onInputReceived(number);
        });
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
            return FiveByNinetyColors.getBallColor(LuckySixBallColorMap.getMapNumberConfig().get(ballNumber));
        } catch (Exception e) {
            return "#ff0000";
        }
    }
}
