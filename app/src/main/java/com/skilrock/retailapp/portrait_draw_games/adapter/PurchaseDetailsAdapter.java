package com.skilrock.retailapp.portrait_draw_games.adapter;

import android.content.Context;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.portrait_draw_games.ui.PurchaseDetailsActivity;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.DrawGameData;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class PurchaseDetailsAdapter extends RecyclerView.Adapter<PurchaseDetailsAdapter.GameViewHolder> implements AppConstants {

    private ArrayList<PanelBean> listPanelBean;
    private Context context;
    private OnDeleteButtonClickedListener deleteListener;
    private HashMap<String, String> mapNumberColor;

    public PurchaseDetailsAdapter(ArrayList<PanelBean> listPanelBean, Context context, OnDeleteButtonClickedListener deleteListener, HashMap<String, String> mapNumberColor) {
        this.listPanelBean  = listPanelBean;
        this.deleteListener = deleteListener;
        this.context        = context;
        this.mapNumberColor = mapNumberColor;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_purchase_details_dg, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        PanelBean model = listPanelBean.get(position);
        String detail = null;
        if (DrawGameData.getSelectedGame().getGameCode().equalsIgnoreCase("FullRoulette")) {
            holder.rvDetails.setVisibility(View.VISIBLE);
            holder.llBanker.setVisibility(View.GONE);
            holder.llSideBet.setVisibility(View.GONE);
            NumbersCartPortraitAdapter adapter = new NumbersCartPortraitAdapter(model.getListSelectedNumber(), context, mapNumberColor);
            holder.rvDetails.setHasFixedSize(true);
            holder.rvDetails.setItemAnimator(new DefaultItemAnimator());
            holder.rvDetails.setLayoutManager(new GridLayoutManager(context, 10));
            holder.rvDetails.setAdapter(adapter);

            if (model.getNumberOfLines() > 2)
                detail = model.getWinMode().equalsIgnoreCase("MAIN") ? context.getString(R.string.main_bet) + " | " + model.getPickName() : context.getString(R.string.side_bet) + " | " + model.getPickName() + " | " + context.getString(R.string.no_of_lines) + ": " + model.getNumberOfLines();
            else
                detail = model.getWinMode().equalsIgnoreCase("MAIN") ? context.getString(R.string.main_bet) + " | " + model.getPickName() : context.getString(R.string.side_bet) + " | " + model.getPickName() + " | " + context.getString(R.string.no_of_line) + " " + model.getNumberOfLines();
            holder.tvDetails.setText(detail);
        } else {
            if (model.isMainBet()) {
                holder.llSideBet.setVisibility(View.GONE);
                if (model.getPickCode().equalsIgnoreCase("Banker")) {
                    holder.rvDetails.setVisibility(View.GONE);
                    holder.llBanker.setVisibility(View.VISIBLE);

                    NumbersCartPortraitAdapter upperLineAdapter = new NumbersCartPortraitAdapter(model.getListSelectedNumberUpperLine(), context, mapNumberColor);
                    holder.rvUpperLine.setHasFixedSize(true);
                    holder.rvUpperLine.setItemAnimator(new DefaultItemAnimator());
                    holder.rvUpperLine.setLayoutManager(new GridLayoutManager(context, 8));
                    holder.rvUpperLine.setAdapter(upperLineAdapter);

                    NumbersCartPortraitAdapter lowerLineAdapter = new NumbersCartPortraitAdapter(model.getListSelectedNumberLowerLine(), context, mapNumberColor);
                    holder.rvLowerLine.setHasFixedSize(true);
                    holder.rvLowerLine.setItemAnimator(new DefaultItemAnimator());
                    holder.rvLowerLine.setLayoutManager(new GridLayoutManager(context, 8));
                    holder.rvLowerLine.setAdapter(lowerLineAdapter);

                } else {
                    holder.rvDetails.setVisibility(View.VISIBLE);
                    holder.llBanker.setVisibility(View.GONE);

                    NumbersCartPortraitAdapter adapter = new NumbersCartPortraitAdapter(model.getListSelectedNumber(), context, mapNumberColor);
                    holder.rvDetails.setHasFixedSize(true);
                    holder.rvDetails.setItemAnimator(new DefaultItemAnimator());
                    holder.rvDetails.setLayoutManager(new GridLayoutManager(context, 10));
                    holder.rvDetails.setAdapter(adapter);
                }
                //MainBet or SideBet | PickType | Manual or Quick Pick
                //String pickName =  (model.getPickName().contains("QP") ? model.getPickName().replace("QP", "").trim() : model.getPickName());
                //detail = "Main Bet | " + pickName;
                //detail = model.getQuickPick() ? detail + " | " + "Quick Pick" : detail + " | " + "Manual";
                if (model.getNumberOfLines() > 2)
                    detail = context.getString(R.string.main_bet) + " | " + model.getPickName() + " | " + context.getString(R.string.no_of_lines) + ": " + model.getNumberOfLines();
                else
                    detail = context.getString(R.string.main_bet) + " | " + model.getPickName() + " | " + context.getString(R.string.no_of_line) + " " + model.getNumberOfLines();
                holder.tvDetails.setText(detail);
            } else {
                holder.rvDetails.setVisibility(View.GONE);
                holder.llSideBet.setVisibility(View.VISIBLE);
                holder.llBanker.setVisibility(View.GONE);

                if (model.getColorCode() == null) holder.tvColorBall.setVisibility(View.GONE);
                else {
                    holder.tvColorBall.setVisibility(View.VISIBLE);
                    holder.tvColorBall.setSolidColor(model.getColorCode());
                }

                holder.tvSideBetText.setText(model.getPickName());
                //MainBet or SideBet | PickType | Manual or Quick Pick
                //detail = "Side Bet | " + model.getPickName() + " | Manual";
                if (model.getNumberOfLines() > 2)
                    detail = context.getString(R.string.side_bet) + " | " + model.getPickName() + " | " + context.getString(R.string.no_of_lines) + ": " + model.getNumberOfLines();
                else
                    detail = context.getString(R.string.side_bet) + " | " + model.getPickName() + " | " + context.getString(R.string.no_of_line) + " " + model.getNumberOfLines();
                holder.tvDetails.setText(detail);
            }
        }

        //String amount = "$" + model.getAmount();
        int amt = (int) model.getAmount();
        String strAmount = amt + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(String.valueOf(amt)), Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)), Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)), Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = amt + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        }
        holder.tvBetAmount.setText(formattedAmount);

        String finalDetail = detail;
        String finalFormattedAmount = formattedAmount;
        //holder.rlDelete.setOnClickListener(v
        holder.rlDelete.setOnClickListener(v -> {
            if (SystemClock.elapsedRealtime() - PurchaseDetailsActivity.LAST_CLICK_TIME < PurchaseDetailsActivity.CLICK_GAP) {
                return;
            }
            PurchaseDetailsActivity.LAST_CLICK_TIME = SystemClock.elapsedRealtime();
            deleteListener.onDeleteClicked(model, position, finalFormattedAmount, finalDetail);
        });
    }

    @Override
    public int getItemCount() {
        return listPanelBean.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        TextView tvDetails, tvBetAmount, tvSideBetText;
        CircularTextView tvColorBall;
        //ImageView ivTrash;
        LinearLayout rlDelete, llContainer;
        RecyclerView rvDetails, rvUpperLine, rvLowerLine;
        LinearLayout llSideBet, llBanker;

        GameViewHolder(@NonNull View view) {
            super(view);
            tvDetails       = view.findViewById(R.id.tvDetails);
            tvBetAmount     = view.findViewById(R.id.tvBetAmount);
            rvDetails       = view.findViewById(R.id.rvDetails);
            rvUpperLine     = view.findViewById(R.id.rvUpperLine);
            rvLowerLine     = view.findViewById(R.id.rvLowerLine);
            llSideBet       = view.findViewById(R.id.llSideBet);
            llBanker        = view.findViewById(R.id.llBanker);
            tvColorBall     = view.findViewById(R.id.tvColorBall);
            tvSideBetText   = view.findViewById(R.id.tvSideBetText);
            rlDelete        = view.findViewById(R.id.rlDelete);
            llContainer     = view.findViewById(R.id.llContainer);
            //ivTrash         = view.findViewById(R.id.ivTrash);
        }
    }

    public interface OnDeleteButtonClickedListener {
        void onDeleteClicked(PanelBean panelBean, int index, String amount, String footer);
    }
}
