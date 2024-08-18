package com.skilrock.retailapp.adapter.scratch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.ChallanBookSelectionDialog;
import com.skilrock.retailapp.interfaces.CallBackListener;
import com.skilrock.retailapp.interfaces.TotalPackReceivingNowListener;
import com.skilrock.retailapp.models.scratch.ChallanResponseBean;

import java.util.ArrayList;
import java.util.HashMap;

public class ChallanAdapter extends RecyclerView.Adapter<ChallanAdapter.ChallanViewHolder> {

    private ArrayList<ChallanResponseBean.GameWiseDetail> listGameWiseDetails;
    private Context context;
    private HashMap<Integer, ArrayList<String>> mapSelectedBooks = new HashMap<>();
    private TotalPackReceivingNowListener totalPackReceivingNowListener;
    private boolean isCardClickable;
    private ChallanBookSelectionDialog dialog;

    public ChallanAdapter(Context context, ArrayList<ChallanResponseBean.GameWiseDetail> listGameWiseDetails, TotalPackReceivingNowListener totalPackReceivingNowListener) {
        this.listGameWiseDetails            = listGameWiseDetails;
        this.context                        = context;
        this.totalPackReceivingNowListener  = totalPackReceivingNowListener;
        this.isCardClickable                = true;
    }

    @NonNull
    @Override
    public ChallanAdapter.ChallanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.challan_row, viewGroup, false);

        return new ChallanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallanAdapter.ChallanViewHolder holder, int position) {
        String gameName = listGameWiseDetails.get(position).getGameName();
        String gameNumber;
        if (listGameWiseDetails.get(position).getGameNumber() == null)
            gameNumber = "NA";
        else
            gameNumber = String.valueOf(listGameWiseDetails.get(position).getGameNumber());
        holder.tvGameName.setText(gameName == null?"NA":gameName);
        holder.tvGameNumber.setText(gameNumber);

        int numberOfBooks = listGameWiseDetails.get(position).getBookList() == null ? 0 : listGameWiseDetails.get(position).getBookList().size();
        holder.tvNumberOfBooks.setText(String.valueOf(numberOfBooks));
        holder.tvScannedBooks.setText(String.valueOf(listGameWiseDetails.get(position).getScannedBooks()));
        ArrayList<ChallanResponseBean.GameWiseDetail.BookList> bookList = listGameWiseDetails.get(position).getBookList();
        int totalReceivedBooks = 0;
        if (bookList != null) {
            for (int index = 0; index < bookList.size(); index++)
                if (!bookList.get(index).getStatus().trim().equalsIgnoreCase("MISSING") && !bookList.get(index).getStatus().trim().equalsIgnoreCase("IN_TRANSIT"))
                    totalReceivedBooks++;
        }
        holder.tvReceivedBooks.setText(String.valueOf(totalReceivedBooks));

        int totalMissedPacks = 0;
        if (bookList != null) {
            for (int index = 0; index < bookList.size(); index++)
                if (bookList.get(index).getStatus().trim().equalsIgnoreCase("MISSING"))
                    totalMissedPacks++;
        }
        holder.tvMissed.setText(String.valueOf(totalMissedPacks));
        totalPackReceivingNowListener.onPackSelected();
    }

    @Override
    public int getItemCount() {
        return listGameWiseDetails.size();
    }

    private CallBackListener listener = new CallBackListener() {
        @Override
        public void notifyEvent() {
            isCardClickable = true;
        }

        @Override
        public void notifyEvent(int num, ArrayList<String> list, int position) {
            isCardClickable = true;
            if (list == null) {
                mapSelectedBooks.remove(num);
                //tvScanned.setText(String.format("Scanned Books: %s", 0));
                listGameWiseDetails.get(position).setScannedBooks(0);
            }
            else if (list.size() < 1) {
                mapSelectedBooks.remove(num);
                //tvScanned.setText(String.format("Scanned Books: %s", 0));
                listGameWiseDetails.get(position).setScannedBooks(0);
            }
            else {
                mapSelectedBooks.put(num, list);
                //tvScanned.setText(String.format("Scanned Books: %s", list.size()));
                listGameWiseDetails.get(position).setScannedBooks(list.size());
            }
            notifyDataSetChanged();
        }
    };

    public ArrayList<ChallanResponseBean.GameWiseDetail> getListGameWiseDetails() {
        return listGameWiseDetails;
    }

    public HashMap<Integer, ArrayList<String>> getMapSelectedBooks() {
        return mapSelectedBooks;
    }

    public class ChallanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvGameName, tvGameNumber, tvNumberOfBooks, tvReceivedBooks, tvMissed;
        LinearLayout llContainer, llReceivingNow;
        public TextView tvScannedBooks;

        ChallanViewHolder(@NonNull View view) {
            super(view);
            tvGameName = view.findViewById(R.id.tvGameName);
            tvGameNumber = view.findViewById(R.id.tvGameNumber);
            tvNumberOfBooks = view.findViewById(R.id.tvNumberOfBooks);
            tvMissed = view.findViewById(R.id.tvMissed);
            tvScannedBooks = view.findViewById(R.id.tvScannedBooks);
            tvReceivedBooks = view.findViewById(R.id.tvReceivedBooks);
            llContainer = view.findViewById(R.id.ll_container);
            llReceivingNow = view.findViewById(R.id.llReceivingNow);
            llContainer.setOnClickListener(this);
            if (BuildConfig.app_name.equalsIgnoreCase("fieldx"))
                llReceivingNow.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (isCardClickable) {
                isCardClickable = false;
                ArrayList<ChallanResponseBean.GameWiseDetail.BookList> bookList = listGameWiseDetails.get(getAdapterPosition()).getBookList();
                if (bookList != null && bookList.size() > 0) {
                    dialog = new ChallanBookSelectionDialog(context, bookList, listGameWiseDetails.get(getAdapterPosition()).getGameId(), listener, mapSelectedBooks, getAdapterPosition(), listGameWiseDetails.get(getAdapterPosition()).getGameName());
                    dialog.show();
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }

                    dialog.setOnDismissListener(arg0 -> isCardClickable = true);

                } else Toast.makeText(context, context.getString(R.string.no_books_found), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void closePacksDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
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
