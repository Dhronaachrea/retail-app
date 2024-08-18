package com.skilrock.retailapp.adapter.drawgame;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.FiveByNinetyColors;

import java.util.ArrayList;

public class ColorBarAdapter extends RecyclerView.Adapter<ColorBarAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<String> listColor;
    private Context context;

    public ColorBarAdapter(ArrayList<String> listColor, Context context) {
        this.listColor          = listColor;
        this.context            = context;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_color_bar, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        String strColor = listColor.get(position);
        holder.tvBar.setBackgroundColor(Color.parseColor(FiveByNinetyColors.getBallColor(strColor)));
    }

    @Override
    public int getItemCount() {
        return listColor.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        CardView tvBar;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvBar = view.findViewById(R.id.tvBar);
        }
    }

}
