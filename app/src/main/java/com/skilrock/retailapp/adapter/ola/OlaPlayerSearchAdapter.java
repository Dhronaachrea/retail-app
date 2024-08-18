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

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.PlayerSelectionListener;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchResponseBean;
import com.skilrock.retailapp.utils.AppConstants;

import java.util.ArrayList;

public class OlaPlayerSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<OlaPlayerSearchResponseBean.ResponseData.Player> listPlayers;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private PlayerSelectionListener listener;

    public OlaPlayerSearchAdapter(Context context, ArrayList<OlaPlayerSearchResponseBean.ResponseData.Player> listPlayers, PlayerSelectionListener listener) {
        this.context        = context;
        this.listPlayers    = listPlayers;
        this.listener       = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ola_player_search, parent, false);
            return new OlaPlayerSearchAdapter.OlaReportViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new OlaPlayerSearchAdapter.LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OlaPlayerSearchAdapter.OlaReportViewHolder)
            populateItemRows((OlaPlayerSearchAdapter.OlaReportViewHolder) holder, position);
        else if (holder instanceof OlaPlayerSearchAdapter.LoadingViewHolder)
            showLoadingView((OlaPlayerSearchAdapter.LoadingViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return listPlayers == null ? 0 : listPlayers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listPlayers.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    class OlaReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvName, tvAddress, tvMobile, tvStatus;
        LinearLayout llContainer;

        OlaReportViewHolder(@NonNull View view) {
            super(view);
            tvTitle         = view.findViewById(R.id.tvTitle);
            tvStatus        = view.findViewById(R.id.tvStatus);
            tvAddress       = view.findViewById(R.id.tvAddress);
            tvName          = view.findViewById(R.id.tvName);
            tvMobile        = view.findViewById(R.id.tvMobile);
            llContainer     = view.findViewById(R.id.llContainer);

            llContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                listener.onPlayerSelected(listPlayers.get(getAdapterPosition()).getUsername());
            else
                listener.onPlayerSelected(listPlayers.get(getAdapterPosition()).getPhone());
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void populateItemRows(OlaPlayerSearchAdapter.OlaReportViewHolder holder, int position) {
        OlaPlayerSearchResponseBean.ResponseData.Player player = listPlayers.get(position);

        String fillName = player.getFirstName() + " " + player.getLastName();
        holder.tvTitle.setText(player.getFirstName() == null || player.getFirstName().isEmpty() ? String.valueOf(player.getUsername().charAt(0)) : String.valueOf(player.getFirstName().charAt(0)));
        holder.tvName.setText(player.getFirstName() == null || player.getFirstName().isEmpty() ? player.getUsername() : fillName);
        holder.tvAddress.setText(player.getAddress());
        holder.tvMobile.setText(player.getPhone());
        //holder.tvStatus.setText(player.getEstatus());
    }

    private void showLoadingView(OlaPlayerSearchAdapter.LoadingViewHolder holder, int position) {
        //ProgressBar would be displayed
    }

    public void clear() {
        listPlayers.clear();
        notifyDataSetChanged();
    }

}
