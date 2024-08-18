package com.skilrock.retailapp.sle_game_portrait.sle_land_scape;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.skilrock.retailapp.R;
import com.skilrock.retailapp.sle_game_portrait.BaseClassSle;
import com.skilrock.retailapp.sle_game_portrait.DynamicTextCheckBox;
import com.skilrock.retailapp.sle_game_portrait.ResultData;
import com.skilrock.retailapp.sle_game_portrait.SleFetchDataB2C;

import java.util.List;

import androidx.annotation.NonNull;


public class SleGamePlayAdapterNewFinalLand extends RecyclerView.Adapter<SleGamePlayAdapterNewFinalLand.SleGamePlayViewHolderNew> {

    private LayoutInflater layoutInflater;
    private Context context;
    private SleGamePlayViewHolderNew sleGamePlayViewHolder;
    private int totalNumberOfMatch;

    private List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenData;
    private List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenDataB2C;
    private boolean isB2C = false;

    public SleGamePlayAdapterNewFinalLand(Context context, List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenData, int totalNumberOfMatch){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.evenData = evenData;
        this.totalNumberOfMatch = totalNumberOfMatch;
    }
    public SleGamePlayAdapterNewFinalLand(Context context, List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenDataB2C, int totalNumberOfMatch, boolean isB2C){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.evenDataB2C = evenDataB2C;
        this.totalNumberOfMatch = totalNumberOfMatch;
        this.isB2C = isB2C;
    }

    @NonNull
    @Override
    public SleGamePlayViewHolderNew onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_item_five,null,false);
        return new SleGamePlayViewHolderNew(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SleGamePlayViewHolderNew holder, int position) {
        this.sleGamePlayViewHolder = holder;
        String[] events = null;
        sleGamePlayViewHolder.numbers.setText(""+(position+1));
        events =  evenDataB2C.get(position).getEventFullName().toLowerCase().split("vs");
        sleGamePlayViewHolder.txt_home.setText(evenDataB2C.get(position).getEventCodeHome());
        sleGamePlayViewHolder.txt_away.setText(evenDataB2C.get(position).getEventCodeAway());
//        sleGamePlayViewHolder.txt_venue.setText(evenDataB2C.get(position).getEventLeague() + ","
//                + evenDataB2C.get(position).getEventVenue() + "\n" +evenDataB2C.get(position).getEventDate().split(" ")[1]);
//        SaleBean.DrawInfoBean.EventDataBean eventDataBean = BaseClassSle.getBaseClassSle().getEventDataBeans().get(position);
        String numberSelectedValue = "";
        if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isHomePlusSelected()){
            numberSelectedValue = numberSelectedValue + "h+,";
        }
        if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isHomeSelected()){
            numberSelectedValue = numberSelectedValue + "h,";
        }
        if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isDrawSelected()){
            numberSelectedValue = numberSelectedValue + "d,";
        }
        if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isAwaySelected()){
            numberSelectedValue = numberSelectedValue + "a,";
        }
        if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isAwayPlusSelected()){
            numberSelectedValue = numberSelectedValue + "a+,";
        }
        if(numberSelectedValue.trim().length() > 0){
            numberSelectedValue = numberSelectedValue.substring(0,numberSelectedValue.lastIndexOf(","));

        }
        sleGamePlayViewHolder.selected_number.setText(numberSelectedValue);

    }


    @Override
    public int getItemCount() {
        if(isB2C){
            return BaseClassSle.getBaseClassSle().getEventDataBeans().size();
        }
        return evenData.size();
    }

    private void showToast(int value, String position){
        Toast.makeText(context,"value = "+value +"=position="+position, Toast.LENGTH_LONG).show();
    }


    public class SleGamePlayViewHolderNew extends RecyclerView.ViewHolder{

//        public DynamicTextCheckBox cb_home_extra,cb_home,cb_draw,cb_away,cb_away_extra;
        private TextView txt_home,txt_away,numbers,selected_number;

        public SleGamePlayViewHolderNew(@NonNull View itemView) {
            super(itemView);
//            txt_venue = itemView.findViewById(R.id.txt_venue);
            txt_home = itemView.findViewById(R.id.txt_home);
            txt_away = itemView.findViewById(R.id.txt_away);
            selected_number = itemView.findViewById(R.id.selected_number);
            numbers = itemView.findViewById(R.id.numbers);

        }
    }

    public void resetTotal(){

    }

    class OnClickSleSelection implements View.OnClickListener {
        private int position;
        private int gamePlay;
        private DynamicTextCheckBox cb_home_extra,cb_home,cb_draw,cb_away,cb_away_extra;
        public OnClickSleSelection(int position, int gamePlay, Object object){
            this.position = position;
            this.gamePlay = gamePlay;
            if(this.gamePlay == 1){
                cb_home_extra = (DynamicTextCheckBox) object;
            }else if(this.gamePlay == 2){
                cb_home = (DynamicTextCheckBox) object;
            }else if(this.gamePlay == 3){
                cb_draw = (DynamicTextCheckBox) object;
            }else if(this.gamePlay == 4){
                cb_away = (DynamicTextCheckBox) object;
            }else if(this.gamePlay == 5){
                cb_away_extra = (DynamicTextCheckBox) object;
            }
        }
        public boolean calculateNoOfLines(){
            int totalNoOfLines = 1;
            for(SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean eventDataBean:evenDataB2C){
                totalNoOfLines = totalNoOfLines * eventDataBean.getTotalSize();
            }
            return ((ActivityGamePlayLand)context).setNoOfLines(totalNoOfLines);
        }

        @Override
        public void onClick(View v) {
            Log.i("position",position+"");
            Log.i("gamePlay", gamePlay+"");
            switch (gamePlay){
                case 1:
                    if(evenDataB2C.get(position).isHomePlusSelected()){
                        evenDataB2C.get(position).setHomePlusSelected(false,-1);
                    }else{
                        evenDataB2C.get(position).setHomePlusSelected(true,1);
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setHomePlusSelected(false,-1);
                        cb_home_extra.setChecked(false);
                    }
                    break;
                case 2:
                    if(evenDataB2C.get(position).isHomeSelected()){
                        evenDataB2C.get(position).setHomeSelected(false,-1);
                    }else{
                        evenDataB2C.get(position).setHomeSelected(true,1);
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setHomeSelected(false,-1);
                        cb_home.setChecked(false);
                    }
                    break;
                case 3:
                    if(evenDataB2C.get(position).isDrawSelected()){
                        evenDataB2C.get(position).setDrawSelected(false,-1);
                    }else{
                        evenDataB2C.get(position).setDrawSelected(true,1);
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setDrawSelected(false,-1);
                        cb_draw.setChecked(false);
                    }
                    break;
                case 4:
                    if(evenDataB2C.get(position).isAwaySelected()){
                        evenDataB2C.get(position).setAwaySelected(false,-1);
                    }else{
                        evenDataB2C.get(position).setAwaySelected(true,1);
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setAwaySelected(false,-1);
                        cb_away.setChecked(false);
                    }
                    break;
                case 5:
                    if(evenDataB2C.get(position).isAwayPlusSelected()){
                        evenDataB2C.get(position).setAwayPlusSelected(false,-1);
                    }else{
                        evenDataB2C.get(position).setAwayPlusSelected(true,1);
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setAwayPlusSelected(false,-1);
                        cb_away_extra.setChecked(false);
                    }
                    break;
            }

        }
    }
}
