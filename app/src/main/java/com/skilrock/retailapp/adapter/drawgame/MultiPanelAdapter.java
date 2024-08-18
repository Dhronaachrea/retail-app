package com.skilrock.retailapp.adapter.drawgame;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.PanelDeleteDialog;
import com.skilrock.retailapp.interfaces.InputListener;
import com.skilrock.retailapp.landscape_draw_games.adapter.PurchaseMainNumberAdapter;
import com.skilrock.retailapp.models.drawgames.PanelBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CircularTextView;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.FiveByNinetyColors;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiPanelAdapter extends RecyclerView.Adapter<MultiPanelAdapter.PanelViewHolder> implements AppConstants {

    private ArrayList<PanelBean> listPanel;
    private Activity context;
    private String gameCode;
    private OnPanelDeleteListener deleteListener;
    private String pictTypeName = "";
    private PanelDeleteDialog panelDeleteDialog;

    public MultiPanelAdapter(Activity context, ArrayList<PanelBean> listPanel, OnPanelDeleteListener deleteListener, String gameCode) {
        this.listPanel = listPanel;
        this.context = context;
        this.deleteListener = deleteListener;
        this.gameCode = gameCode;
    }

    @NonNull
    @Override
    public PanelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.multiple_panel_row, viewGroup, false);
        return new PanelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PanelViewHolder holder, int position) {
        PanelBean panelData = listPanel.get(position);

        if (!panelData.getWinMode().equalsIgnoreCase("MAIN")) {
            pictTypeName = context.getString(R.string.side_bet);
            holder.tvSideBetHeader.setText(panelData.getSideBetHeader());
            holder.tvHeader.setVisibility(View.GONE);
            holder.rvMainNumbers.setVisibility(View.GONE);
            holder.viewSideBet.setVisibility(View.VISIBLE);
            holder.viewBanker.setVisibility(View.GONE);

            switch (panelData.getBetCode()) {
                case "5/90FirstBallOdd/Even":
                case "5/90LastBallOdd/Even":
                case "MoreOddEven":
                case "LuckySixFirstBallEvenOdd":
                case "LuckySixLastBallEvenOdd":
                case "LuckySixFirstFiveMoreEvenOdd":
                    holder.circularTextView.setVisibility(View.GONE);
                    holder.imageSideBetType.setVisibility(View.GONE);

                    break;
                case "FirstBallColor":
                case "LastBallColor":
                case "FiveBallColor":
                case "LuckySixFirstBallColor":
                case "LuckySixLastBallColor":
                case "LuckySixAllBallColor":
                    holder.circularTextView.setVisibility(View.VISIBLE);
                    holder.imageSideBetType.setVisibility(View.GONE);

                    holder.circularTextView.setText("");
                    holder.circularTextView.setStrokeColor(getStringBetween(panelData.getPickedValues()));
                    holder.circularTextView.setSolidColor(getStringBetween(panelData.getPickedValues()));

                    break;
                case "5/90FirstBallSum":
                case "FirstFiveBallSum":
                case "5/90LastBallSum":
                case "LuckySixFirstBallSum":
                case "LuckySixLastBallSum":
                case "LuckySixFirstFiveBallSum":
                    holder.circularTextView.setVisibility(View.GONE);
                    holder.imageSideBetType.setVisibility(View.VISIBLE);

                    if (panelData.getPickName().toLowerCase().contains("greater")) {
                        holder.imageSideBetType.setImageResource(R.drawable.chevron_left);
                    } else {
                        holder.imageSideBetType.setImageResource(R.drawable.chevron_right);
                    }

                    break;
                case "Increasing/Decreasing":
                case "Hill/Valley":
                    holder.circularTextView.setVisibility(View.GONE);
                    holder.imageSideBetType.setVisibility(View.VISIBLE);
                    switch (panelData.getPickCode().toLowerCase()) {
                        case "increasing":
                            holder.imageSideBetType.setImageDrawable(context.getResources().getDrawable(R.drawable.increasing));
                            break;
                        case "decreasing":
                            holder.imageSideBetType.setImageDrawable(context.getResources().getDrawable(R.drawable.decreasing));
                            break;
                        case "hillpattern":
                            holder.imageSideBetType.setImageDrawable(context.getResources().getDrawable(R.drawable.hill));
                            break;
                        case "valley":
                            holder.imageSideBetType.setImageDrawable(context.getResources().getDrawable(R.drawable.random));
                            break;
                    }

                    break;
            }

        } else {
            pictTypeName = context.getString(R.string.main_bet);
            if (panelData.getPickCode().equalsIgnoreCase("BANKER")) {
                holder.tvHeader.setVisibility(View.GONE);
                holder.rvMainNumbers.setVisibility(View.GONE);
                holder.viewSideBet.setVisibility(View.GONE);
                holder.viewBanker.setVisibility(View.VISIBLE);

                PurchaseMainNumberAdapter adapterUpperLine = new PurchaseMainNumberAdapter(panelData.getListSelectedNumberUpperLine(), context, gameCode);
                holder.rvUpperLine.setAdapter(adapterUpperLine);
                //holder.rvMainNumbers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

                holder.rvUpperLine.setLayoutManager(new GridLayoutManager(context, 6));
                holder.rvUpperLine.setItemAnimator(new DefaultItemAnimator());

                PurchaseMainNumberAdapter adapterLowerLine = new PurchaseMainNumberAdapter(panelData.getListSelectedNumberLowerLine(), context, gameCode);
                holder.rvLowerLine.setAdapter(adapterLowerLine);
                //holder.rvMainNumbers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

                holder.rvLowerLine.setLayoutManager(new GridLayoutManager(context, 6));
                holder.rvLowerLine.setItemAnimator(new DefaultItemAnimator());

            } else {
                holder.tvHeader.setVisibility(View.GONE);
                holder.rvMainNumbers.setVisibility(View.VISIBLE);
                holder.viewSideBet.setVisibility(View.GONE);
                holder.viewBanker.setVisibility(View.GONE);

                PurchaseMainNumberAdapter schemaAdapter = new PurchaseMainNumberAdapter(getBallsArray(panelData.getPickedValues()), context, gameCode);
                holder.rvMainNumbers.setAdapter(schemaAdapter);
                //holder.rvMainNumbers.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

                holder.rvMainNumbers.setLayoutManager(new GridLayoutManager(context, 6));
                holder.rvMainNumbers.setItemAnimator(new DefaultItemAnimator());
            }
        }

        String amount = getAmountWithCurrency(String.valueOf(panelData.getAmount()));
        holder.tvPrice.setText(amount);
        String pickType = "";
        if (panelData.getQuickPick()) {
            pickType = panelData.getNumberOfLines() > 1 ? "QuickPick-" + panelData.getNumberOfLines() + " Lines" : "QuickPick-" + panelData.getNumberOfLines() + " Line";
        } else {
            pickType = panelData.getNumberOfLines() > 1 ? "Manual-" + panelData.getNumberOfLines() + " Lines" : "Manual-" + panelData.getNumberOfLines() + " Line";
        }
        //String footer = panelData.getWinMode() + " / " + panelData.getPickName() + " / " + pickType;

        String numberOfLines = panelData.getNumberOfLines() > 1 ?
                " | " + context.getString(R.string.no_of_lines) + " : " + panelData.getNumberOfLines() :
                " | " + context.getString(R.string.no_of_line) + " " + panelData.getNumberOfLines();

        String footer = pictTypeName + " | " + panelData.getPickName() + numberOfLines;

        holder.tvFooter.setText(footer);

        holder.ivDelete.setOnClickListener(v -> {
            String info = "";
            if (!panelData.getWinMode().equalsIgnoreCase("MAIN")) {
                info = panelData.getSideBetHeader() + " - " + amount + "\n" + footer;
            } else {
                info = panelData.getPickedValues() + " - " + amount + "\n" + footer;
            }

            InputListener listener = input -> deleteListener.onPanelDeleted(position);

            if (panelDeleteDialog != null && panelDeleteDialog.isShowing())
                panelDeleteDialog.dismiss();

            panelDeleteDialog = new PanelDeleteDialog(context, listener, panelData.getGameName(), info);
            panelDeleteDialog.show();
            if (panelDeleteDialog.getWindow() != null) {
                panelDeleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                panelDeleteDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPanel.size();
    }

    class PanelViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeader, tvPrice, tvFooter, tvSideBetHeader;
        ImageView ivDelete;
        RecyclerView rvMainNumbers, rvUpperLine, rvLowerLine;
        LinearLayout viewSideBet, viewBanker;
        CircularTextView circularTextView;
        ImageView imageSideBetType;

        PanelViewHolder(@NonNull View view) {
            super(view);
            tvHeader = view.findViewById(R.id.tvHeader);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvFooter = view.findViewById(R.id.tvFooter);
            ivDelete = view.findViewById(R.id.image_delete);
            rvMainNumbers = view.findViewById(R.id.rv_main_numbers);
            rvUpperLine = view.findViewById(R.id.rv_upper_line);
            rvLowerLine = view.findViewById(R.id.rv_lower_line);
            viewSideBet = view.findViewById(R.id.view_side_bet);
            viewBanker = view.findViewById(R.id.view_banker);
            circularTextView = view.findViewById(R.id.colorBall);
            tvSideBetHeader = view.findViewById(R.id.tvSideHeader);
            imageSideBetType = view.findViewById(R.id.image_side_bet_type);
        }
    }

    public interface OnPanelDeleteListener {
        void onPanelDeleted(int index);
    }

    private ArrayList<String> getBallsArray(String balls) {
        String csv = balls;
        String[] elements = csv.split(",");
        List<String> fixedLenghtList = Arrays.asList(elements);

        ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);

        return listOfString;
    }

    private String getStringBetween(String input){
        input = input.substring(input.indexOf("<")+1, input.lastIndexOf("<"));

        return FiveByNinetyColors.getBallColor(input);
        //return input;
    }

    private String getAmountWithCurrency(String strAmount) {
        String formattedAmount;
        try {
            formattedAmount = Utils.getBalanceToView(Double.parseDouble(strAmount),
                    Utils.getCurrencyFormatter(SharedPrefUtil.getLanguage(context)),
                    Utils.getAmountFormatter(SharedPrefUtil.getLanguage(context)),
                    Integer.parseInt(ConfigData.getInstance().getConfigData().getALLOWEDDECIMALSIZE())) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(context));
        } catch (Exception e) {
            e.printStackTrace();
            formattedAmount = String.valueOf(strAmount);
        }
        return formattedAmount;
    }
}
