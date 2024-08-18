package com.skilrock.retailapp.sle_game_portrait;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.skilrock.retailapp.R;

import java.util.List;

public class SleGamePlayAdapter extends RecyclerView.Adapter<SleGamePlayAdapter.SleGamePlayViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private SleGamePlayViewHolder sleGamePlayViewHolder;
    private int totalNumberOfMatch;

    private List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenData;

    public SleGamePlayAdapter(Context context, List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenData, int totalNumberOfMatch){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.evenData = evenData;
        this.totalNumberOfMatch = totalNumberOfMatch;
    }

    @NonNull
    @Override
    public SleGamePlayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_five,null,false);
        return new SleGamePlayViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SleGamePlayViewHolder holder, int position) {
        this.sleGamePlayViewHolder = holder;
        String[] events = evenData.get(position).getEventDiscription().split("vs");
        sleGamePlayViewHolder.txt_home.setText((events!= null && events.length > 0)?events[0].replace("-",""):"");
        sleGamePlayViewHolder.txt_away.setText((events!= null && events.length > 1)?events[1].replace("-",""):"");
        sleGamePlayViewHolder.txt_venue.setText(evenData.get(position).getEventLeague() + "," + evenData.get(position).getEventVenue());

        if(totalNumberOfMatch == 3){
            sleGamePlayViewHolder.cb_home_extra.setOnClickListener(new OnClickSleSelection(position,2));
            sleGamePlayViewHolder.cb_draw.setOnClickListener(new OnClickSleSelection(position,3));
            sleGamePlayViewHolder.cb_away_extra.setOnClickListener(new OnClickSleSelection(position,4));

            if(evenData.get(position).isHomeSelected()){
                sleGamePlayViewHolder.cb_home_extra.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_home_extra.setChecked(false);
            }

            if(evenData.get(position).isDrawSelected()){
                sleGamePlayViewHolder.cb_draw.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_draw.setChecked(false);
            }

            if(evenData.get(position).isAwaySelected()){
                sleGamePlayViewHolder.cb_away_extra.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_away_extra.setChecked(false);
            }

        }else{
            sleGamePlayViewHolder.cb_home_extra.setOnClickListener(new OnClickSleSelection(position,1));
            sleGamePlayViewHolder.cb_home.setOnClickListener(new OnClickSleSelection(position,2));
            sleGamePlayViewHolder.cb_draw.setOnClickListener(new OnClickSleSelection(position,3));
            sleGamePlayViewHolder.cb_away.setOnClickListener(new OnClickSleSelection(position,4));
            sleGamePlayViewHolder.cb_away_extra.setOnClickListener(new OnClickSleSelection(position,5));

            if(evenData.get(position).isHomePlusSelected()){
                sleGamePlayViewHolder.cb_home_extra.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_home_extra.setChecked(false);
            }

            if(evenData.get(position).isHomeSelected()){
                sleGamePlayViewHolder.cb_home.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_home.setChecked(false);
            }

            if(evenData.get(position).isDrawSelected()){
                sleGamePlayViewHolder.cb_draw.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_draw.setChecked(false);
            }

            if(evenData.get(position).isAwaySelected()){
                sleGamePlayViewHolder.cb_away.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_away.setChecked(false);
            }

            if(evenData.get(position).isAwayPlusSelected()){
                sleGamePlayViewHolder.cb_away_extra.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_away_extra.setChecked(false);
            }
        }


    }


    @Override
    public int getItemCount() {
        return evenData.size();
    }

    private void showToast(int value, String position){
        Toast.makeText(context,"value = "+value +"=position="+position, Toast.LENGTH_LONG).show();
    }


    public class SleGamePlayViewHolder extends RecyclerView.ViewHolder{

        private DynamicTextCheckBox cb_home_extra,cb_home,cb_draw,cb_away,cb_away_extra;
        private TextView txt_home,txt_away,txt_venue;

        public SleGamePlayViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_home_extra = itemView.findViewById(R.id.cb_home_extra);
            cb_home = itemView.findViewById(R.id.cb_home);
            cb_draw = itemView.findViewById(R.id.cb_draw);
            cb_away = itemView.findViewById(R.id.cb_away);
            cb_away_extra = itemView.findViewById(R.id.cb_away_extra);
            txt_home = itemView.findViewById(R.id.txt_home);
            txt_away = itemView.findViewById(R.id.txt_away);
            txt_venue = itemView.findViewById(R.id.txt_venue);
            if(totalNumberOfMatch == 3){
                cb_home.setVisibility(View.GONE);
                cb_away.setVisibility(View.GONE);
                cb_home_extra.setText("H");
                cb_away_extra.setText("A");
            }

        }
    }

    class OnClickSleSelection implements View.OnClickListener {
        private int position;
        private int gamePlay;
        public OnClickSleSelection(int position, int gamePlay){
            this.position = position;
            this.gamePlay = gamePlay;
        }

        @Override
        public void onClick(View v) {
            Log.i("position",position+"");
            Log.i("gamePlay", gamePlay+"");
            switch (gamePlay){
                case 1:
                    if(evenData.get(position).isHomePlusSelected()){
                        evenData.get(position).setHomePlusSelected(false);
                    }else{
                        evenData.get(position).setHomePlusSelected(true);
                    }
                    break;
                case 2:
                    if(evenData.get(position).isHomeSelected()){
                        evenData.get(position).setHomeSelected(false);
                    }else{
                        evenData.get(position).setHomeSelected(true);
                    }
                    break;
                case 3:
                    if(evenData.get(position).isDrawSelected()){
                        evenData.get(position).setDrawSelected(false);
                    }else{
                        evenData.get(position).setDrawSelected(true);
                    }
                    break;
                case 4:
                    if(evenData.get(position).isAwaySelected()){
                        evenData.get(position).setAwaySelected(false);
                    }else{
                        evenData.get(position).setAwaySelected(true);
                    }
                    break;
                case 5:
                    if(evenData.get(position).isAwayPlusSelected()){
                        evenData.get(position).setAwayPlusSelected(false);
                    }else{
                        evenData.get(position).setAwayPlusSelected(true);
                    }
                    break;
            }
        }
    }
}
