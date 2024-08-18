package com.skilrock.retailapp.adapter.scratch;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ReturnPackListener;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseBean;

import java.util.ArrayList;

public class ReturnBookListAdapter extends RecyclerView.Adapter<ReturnBookListAdapter.QuickOrderViewHolder> {

    private ArrayList<ReturnChallanResponseBean> listGame;
    private Context context;
    private ReturnPackListener listener;

    public ReturnBookListAdapter(Context context, ArrayList<ReturnChallanResponseBean> listGame, ReturnPackListener listener) {
        this.listGame   = listGame;
        this.context    = context;
        this.listener   = listener;
    }

    @NonNull
    @Override
    public QuickOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_return_book, viewGroup, false);

        return new QuickOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickOrderViewHolder holder, int position) {
        /*ReturnChallanResponseBean.Game game = listGame.get(position);

        holder.tvGameName.setText(game.getGameName());
        holder.tvNumberOfPacks.setText(String.valueOf(game.getBooksQuantity()));
        holder.tvGameNumber.setText(String.valueOf(game.getGameNumber()));*/
    }

    @Override
    public int getItemCount() {
        return listGame.size();
    }

    public class QuickOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvGameNumber, tvGameName, tvNumberOfPacks;
        LinearLayout llContainer;

        public QuickOrderViewHolder(@NonNull View view) {
            super(view);
            tvGameNumber            = view.findViewById(R.id.tvGameNumber);
            tvGameName              = view.findViewById(R.id.tvGameName);
            tvNumberOfPacks         = view.findViewById(R.id.tvNumberOfPacks);
            llContainer             = view.findViewById(R.id.ll_container);

            //llContainer.setOnClickListener(v -> listener.onGameSelected(listGame.get(getAdapterPosition())));
        }

        @Override
        public void onClick(View view) {

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
}
