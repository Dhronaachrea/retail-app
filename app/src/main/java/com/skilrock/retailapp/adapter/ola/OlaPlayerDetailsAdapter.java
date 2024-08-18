package com.skilrock.retailapp.adapter.ola;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.PlayerSelectionListener;
import com.skilrock.retailapp.models.ola.OlaPlayerDetailsResponseBean;

import java.util.ArrayList;

public class OlaPlayerDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<OlaPlayerDetailsResponseBean.ResponseData> listData;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private PlayerSelectionListener listener;

    public OlaPlayerDetailsAdapter(Context context, ArrayList<OlaPlayerDetailsResponseBean.ResponseData> listData, PlayerSelectionListener listener) {
        this.context     = context;
        this.listData    = listData;
        this.listener    = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ola_player_details, parent, false);
            return new OlaPlayerDetailsAdapter.OlaReportViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new OlaPlayerDetailsAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OlaPlayerDetailsAdapter.OlaReportViewHolder)
            populateItemRows((OlaPlayerDetailsAdapter.OlaReportViewHolder) holder, position);
        else if (holder instanceof OlaPlayerDetailsAdapter.LoadingViewHolder)
            showLoadingView((OlaPlayerDetailsAdapter.LoadingViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class OlaReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvName, tvMobile, tvAmount;
        LinearLayout llContainer;

        OlaReportViewHolder(@NonNull View view) {
            super(view);
            tvTitle         = view.findViewById(R.id.tvTitle);
            tvAmount        = view.findViewById(R.id.tvAmount);
            tvName          = view.findViewById(R.id.tvName);
            tvMobile        = view.findViewById(R.id.tvMobile);
            llContainer     = view.findViewById(R.id.container);

            llContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onPlayerSelected(listData.get(getAdapterPosition()).getMobileNo());
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void populateItemRows(OlaPlayerDetailsAdapter.OlaReportViewHolder holder, int position) {
        OlaPlayerDetailsResponseBean.ResponseData data = listData.get(position);

        String fillName = data.getFirstName() + " " + data.getLastName();
        holder.tvTitle.setText(String.valueOf(data.getFirstName().charAt(0)));
        holder.tvName.setText(fillName);

        holder.tvAmount.setText(String.valueOf(data.getBalance()));
        holder.tvMobile.setText(data.getMobileNo());
    }

    private void showLoadingView(OlaPlayerDetailsAdapter.LoadingViewHolder holder, int position) {
        //ProgressBar would be displayed
    }

    public void clear() {
        listData.clear();
        notifyDataSetChanged();
    }

}
