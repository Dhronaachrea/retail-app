package com.skilrock.retailapp.adapter.scratch;

import static com.skilrock.retailapp.utils.Utils.getNewFormattedValue;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.InputDialog;
import com.skilrock.retailapp.interfaces.InputListener;
import com.skilrock.retailapp.interfaces.QuickOrderListener;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class PackOrderAdapter extends RecyclerView.Adapter<PackOrderAdapter.QuickOrderViewHolder> {

    private ArrayList<GameListBean.Game> listGame;
    private QuickOrderListener listener;
    private Context context;
    private long mLastClickTime = 0;
    private BaseFragment baseFragment;

    public PackOrderAdapter(Context context, ArrayList<GameListBean.Game> listGame, QuickOrderListener listener, BaseFragment baseFragment) {
        this.listGame   = listGame;
        this.listener   = listener;
        this.context    = context;
        this.baseFragment = baseFragment;
    }

    @NonNull
    @Override
    public QuickOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_quick_order, viewGroup, false);

        return new QuickOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickOrderViewHolder holder, int position) {
        //ll_container
        if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(2, 0, 0, 2);
            holder.llContainer.setLayoutParams(params);
        }


        //String gameNumber = context.getString(R.string.game_number) + " #" + listGame.get(position).getGameNumber();
        String gameNumber = listGame.get(position).getGameName() + " #" + listGame.get(position).getGameNumber();
        holder.tvCaption.setText(gameNumber);
        /*String subCaption = context.getString(R.string.ticket_price_colon)
                + " " + Utils.getFormattedValue(listGame.get(position).getTicketPrice() * listGame.get(position).getTicketsPerBook()) + "\n" + context.getString(R.string.commission_colon) + " "
                + listGame.get(position).getCommissionPercentage() + "%";*/
        String comm = String.valueOf(listGame.get(position).getCommissionPercentage());
        if (comm != null && comm.contains("."))
            comm = comm.replace(".", ",");

        String bal = Utils.getBalanceToView(listGame.get(position).getTicketPrice() * listGame.get(position).getTicketsPerBook(), ",", " ", 0);

        String subCaption = context.getString(R.string.ticket_price_colon)
                + " "+ getNewFormattedValue(bal) + "\n" + context.getString(R.string.commission_colon) + " "
                + comm + "%";
        holder.tvSubCaption.setText(subCaption);


        holder.tvNoOfBooks.setOnClickListener(v -> {
            InputListener listener = input -> { holder.tvNoOfBooks.setText(String.valueOf(Integer.parseInt(input))); onInputReceived(input, position); holder.tvCaption.setTag(Integer.parseInt(input) <= 0); };
            InputDialog dialog = new InputDialog(context, listener, context.getString(R.string.books), context.getString(R.string.number_of_packs), 4, InputType.TYPE_CLASS_NUMBER, holder.tvNoOfBooks.getText().toString().trim());
            dialog.show();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    private void onInputReceived(String input, int position) {
        Log.d("log", "Adapter Position: " + position);
        GameListBean.Game game = listGame.get(position);
        double commissionPercentage = ((game.getTicketPrice() * game.getTicketsPerBook()) - ((game.getTicketPrice() * game.getTicketsPerBook() * game.getCommissionPercentage()) / 100));
        listener.onGameSelected(listGame.get(position).getGameId(), Integer.parseInt(input), listGame.get(position).getTicketPrice() * listGame.get(position).getTicketsPerBook(), commissionPercentage);
    }

    @Override
    public int getItemCount() {
        return listGame.size();
    }

    public class QuickOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvCaption, tvSubCaption, tvNoOfBooks;
        ImageView btnMinus, btnPlus;
        LinearLayout llContainer, llDataContainer;

        public QuickOrderViewHolder(@NonNull View view) {
            super(view);
            tvCaption       = view.findViewById(R.id.tvCaption);
            tvSubCaption    = view.findViewById(R.id.tvSubCaption);
            tvNoOfBooks     = view.findViewById(R.id.tvNoOfBooks);
            btnMinus        = view.findViewById(R.id.btMinus);
            btnPlus         = view.findViewById(R.id.btPlus);
            llContainer     = view.findViewById(R.id.ll_container);
            llDataContainer = view.findViewById(R.id.ll_data_container);

            tvCaption.setTag(true);
            llDataContainer.setOnClickListener(this);
            btnMinus.setOnClickListener(this);
            btnPlus.setOnClickListener(this);
            tvNoOfBooks.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 300 || !baseFragment.isAddPack()) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            switch (view.getId()) {
                case R.id.ll_data_container:
                    //setUnselected();
                    /*if ((Boolean)tvCaption.getTag()) setSelected();
                    else setUnselected();*/
                    break;
                case R.id.btMinus:
                    if (!(Boolean)tvCaption.getTag()) {
                        int count = Integer.parseInt(tvNoOfBooks.getText().toString().trim());
                        if (count > 0) {
                            tvNoOfBooks.setText(String.valueOf(--count));
                            GameListBean.Game game = listGame.get(getAdapterPosition());
                            //double commissionPercentage = (game.getTicketPrice() * game.getTicketsPerBook() * game.getCommissionPercentage())/100;
                            double commissionPercentage = ((game.getTicketPrice() * game.getTicketsPerBook()) - ((game.getTicketPrice() * game.getTicketsPerBook() * game.getCommissionPercentage())/100));
                            listener.onGameSelected(listGame.get(getAdapterPosition()).getGameId(), count, listGame.get(getAdapterPosition()).getTicketPrice() * listGame.get(getAdapterPosition()).getTicketsPerBook(), commissionPercentage);
                        }
                        if (count < 1)
                            setUnselected();
                    }
                    break;
                case R.id.btPlus:
                    if (!(Boolean)tvCaption.getTag()) {
                        GameListBean.Game game = listGame.get(getAdapterPosition());
                        int count = Integer.parseInt(tvNoOfBooks.getText().toString().trim());
                        tvNoOfBooks.setText(String.valueOf(++count));
                        //double commissionPercentage = (game.getTicketPrice() * game.getTicketsPerBook() * game.getCommissionPercentage())/100;
                        double commissionPercentage = ((game.getTicketPrice() * game.getTicketsPerBook()) - ((game.getTicketPrice() * game.getTicketsPerBook() * game.getCommissionPercentage())/100));
                        listener.onGameSelected(listGame.get(getAdapterPosition()).getGameId(), count, listGame.get(getAdapterPosition()).getTicketPrice() * listGame.get(getAdapterPosition()).getTicketsPerBook(), commissionPercentage);
                    }
                    else
                        setSelected();
                    break;
                /*case R.id.tvNoOfBooks:
                    InputListener listener = input -> { tvNoOfBooks.setText(String.valueOf(Integer.parseInt(input))); onInputReceived(input); tvCaption.setTag(Integer.parseInt(input) <= 0); };
                    InputDialog dialog = new InputDialog(context, listener, context.getString(R.string.books), context.getString(R.string.number_of_packs), 4, InputType.TYPE_CLASS_NUMBER, tvNoOfBooks.getText().toString().trim());
                    dialog.show();
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }
                    break;*/
            }
        }

        /*private void onInputReceived(String input) {
            Log.d("log", "Adapter Position: " + getAdapterPosition());
            GameListBean.Game game = listGame.get(getAdapterPosition());
            double commissionPercentage = ((game.getTicketPrice() * game.getTicketsPerBook()) - ((game.getTicketPrice() * game.getTicketsPerBook() * game.getCommissionPercentage()) / 100));
            listener.onGameSelected(listGame.get(getAdapterPosition()).getGameId(), Integer.parseInt(input), listGame.get(getAdapterPosition()).getTicketPrice() * listGame.get(getAdapterPosition()).getTicketsPerBook(), commissionPercentage);
        }*/

        private void setSelected() {
            GameListBean.Game game = listGame.get(getAdapterPosition());
            double commissionPercentage = ((game.getTicketPrice() * game.getTicketsPerBook()) - ((game.getTicketPrice() * game.getTicketsPerBook() * game.getCommissionPercentage())/100));
            tvCaption.setTag(false);
            tvNoOfBooks.setText("1");
            listener.onGameSelected(listGame.get(getAdapterPosition()).getGameId(), 1, listGame.get(getAdapterPosition()).getTicketPrice() * listGame.get(getAdapterPosition()).getTicketsPerBook(), commissionPercentage);
        }

        private void setUnselected() {
            GameListBean.Game game = listGame.get(getAdapterPosition());
            double commissionPercentage = ((game.getTicketPrice() * game.getTicketsPerBook()) - ((game.getTicketPrice() * game.getTicketsPerBook() * game.getCommissionPercentage())/100));
            tvCaption.setTag(true);
            tvNoOfBooks.setText("0");
            listener.onGameSelected(listGame.get(getAdapterPosition()).getGameId(), 0, listGame.get(getAdapterPosition()).getTicketPrice() * listGame.get(getAdapterPosition()).getTicketsPerBook(), commissionPercentage);
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

    private String getFormattedAmount(String amt) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMANY);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###,###.##", otherSymbols);

        return df.format(Double.parseDouble(amt));
    }
}
