package com.skilrock.retailapp.sle_game_portrait;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skilrock.retailapp.R;

public class DialogBet extends Dialog {
    private Context context;
    private RecyclerView rv_numbers;
    private int totalView = 0;
    private EditText input_number;
    private OnBetClick onBetClick;
    public DialogBet(@NonNull Context context, int totalView, final OnBetClick onBetClick) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.context = context;
        this.totalView = totalView;
        setContentView(R.layout.layout_error_dialog_sle);
        input_number = findViewById(R.id.input_number);
        rv_numbers = findViewById(R.id.rv_numbers);
        this.onBetClick = onBetClick;
        rv_numbers.setLayoutManager(new GridLayoutManager(context,5));
        BetAdapter betAdapter = new BetAdapter();
        rv_numbers.setAdapter(betAdapter);

        input_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(input_number.getText().toString().length() > 0){
//                        Double.parseDouble(ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktMaxAmt().substring(0,))
//                        int currBetSelected = Integer.parseInt();
//                        onBetClick.onBetClick(-1,Integer.parseInt(input_number.getText().toString()));
                    }
                }
                return false;
            }
        });
    }

    public interface OnBetClick{
        public void onBetClick(int currentBetSelected, int amount);
    }

    public class BetAdapter extends RecyclerView.Adapter<BetAdapter.BetViewHolder>{
        public LayoutInflater layoutInflater;
        @NonNull
        @Override
        public BetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog,null);
            return new BetViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BetViewHolder holder, int position) {
            holder.bet_amount_one.setText(ActivityDraws.sleFetchDataB2C.getSleData().getCurrencyConversionRates().get(0).getCurrencyCode() +
                    ActivityDraws.sleFetchDataB2C.getSleData().getGameData().get(0).getTktThresholdAmt()*(position+6));

        }

        @Override
        public int getItemCount() {
            return totalView;
        }

        public class BetViewHolder extends RecyclerView.ViewHolder{

            private TextView bet_amount_one;
            public BetViewHolder(@NonNull View itemView) {
                super(itemView);
                bet_amount_one = itemView.findViewById(R.id.bet_amount_one);
            }
        }
    }

}
