package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class NumbersCartPortraitAdapter extends RecyclerView.Adapter<NumbersCartPortraitAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<String> listNumber;
    private Context context;
    private HashMap<String, String> mapNumberColor;

    public NumbersCartPortraitAdapter(ArrayList<String> listNumber, Context context, HashMap<String, String> mapNumberColor) {
        this.listNumber         = listNumber;
        this.context            = context;
        this.mapNumberColor     = mapNumberColor;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dg_number_box_cart, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        String strNumber = listNumber.get(position);
        holder.tvNumber.setText(strNumber);

        holder.tvNumber.setBackgroundResource(R.drawable.bg_numbers_cart);
        GradientDrawable background = (GradientDrawable) holder.tvNumber.getBackground();
        background.setColor(Color.parseColor(mapNumberColor.get(strNumber)));
        holder.tvNumber.setTypeface(null, Typeface.BOLD);
        holder.tvNumber.setTextColor(Color.parseColor("#ffffff"));
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

}
