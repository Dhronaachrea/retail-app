package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.QuickPickBean;
import com.skilrock.retailapp.utils.AppConstants;

import java.util.ArrayList;

public class QpNumbersPortraitAdapter extends RecyclerView.Adapter<QpNumbersPortraitAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<QuickPickBean> listNumber;
    private Context context;
    private OnQpNumberSelection listener;

    public QpNumbersPortraitAdapter(ArrayList<QuickPickBean> listNumber, Context context, OnQpNumberSelection listener) {
        this.listNumber         = listNumber;
        this.context            = context;
        this.listener           = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dg_qp_number_box, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        QuickPickBean model = listNumber.get(position);
        String text = Integer.toString(model.getNumber());
        holder.tvNumber.setText(text);

        if (model.isSelected()) {
            holder.tvNumber.setBackground(context.getResources().getDrawable(R.drawable.solid_red_rounded));
            holder.tvNumber.setTextColor(Color.parseColor("#ffffff"));
        }
        else {
            holder.tvNumber.setBackground(context.getResources().getDrawable(R.drawable.bg_qp_number_unselected));
            holder.tvNumber.setTextColor(Color.parseColor("#606060"));
        }

        holder.tvNumber.setOnClickListener(v -> {
            setSelected(position);
            listener.onQpNumberSelection(model.getNumber());
            notifyDataSetChanged();
        });
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

}
