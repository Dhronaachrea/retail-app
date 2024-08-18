package com.skilrock.retailapp.adapter.drawgame;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DrawAdapter extends RecyclerView.Adapter<DrawAdapter.DrawAdapterViewHolder> {

    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws;
    private Context context;
    private int maxAdvanceDraws;
    private Toast mToast = null;

    public DrawAdapter(Context context, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws, int maxAdvanceDraws) {
        this.listDraws          = listDraws;
        this.context            = context;
        this.maxAdvanceDraws    = maxAdvanceDraws;
    }

    @NonNull
    @Override
    public DrawAdapter.DrawAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.draw_check_cox_row_portrait, viewGroup, false);

        return new DrawAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawAdapter.DrawAdapterViewHolder holder, int position) {
        DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO drawData = listDraws.get(position);

        holder.tvDraw.setText(formatTime(drawData.getDrawDateTime()));
        holder.tvDraw.setBackground(!drawData.isSelected() ? context.getResources().getDrawable(R.drawable.background_bet_deselected_rounded) :
                context.getResources().getDrawable(R.drawable.solid_red_rounded));

        holder.tvDraw.setTextColor(!drawData.isSelected() ? context.getResources().getColor(R.color.colorDarkGrey) :
                context.getResources().getColor(R.color.colorWhite));
        holder.tvDraw.setOnClickListener(v -> {
            if(drawData.isSelected()){
                drawData.setSelected(false);
            }else {
                drawData.setSelected(true);
            }
            notifyDataSetChanged();

        });
    }

    private int getSelectedDrawsCount() {
        int count = 0;
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO data: listDraws) {
            if (data.isSelected())
                count++;
        }
        return count;
    }

    @Override
    public int getItemCount() {
        return listDraws.size();
    }

    public ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> getSelectedDraws() {
        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> list = new ArrayList<>();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO data: listDraws) {
            if (data.isSelected())
                list.add(data);
        }
        return list;
    }

    public class DrawAdapterViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkbox;
        TextView tvDraw;

        DrawAdapterViewHolder(@NonNull View view) {
            super(view);
            checkbox = view.findViewById(R.id.checkbox);
            tvDraw = view.findViewById(R.id.tvDraw);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private String formatTime(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd, HH:mm");
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
