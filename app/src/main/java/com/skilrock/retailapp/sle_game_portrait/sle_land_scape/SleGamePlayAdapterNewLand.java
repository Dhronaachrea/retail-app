package com.skilrock.retailapp.sle_game_portrait.sle_land_scape;

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
import com.skilrock.retailapp.sle_game_portrait.BaseClassSle;
import com.skilrock.retailapp.sle_game_portrait.DynamicTextCheckBox;
import com.skilrock.retailapp.sle_game_portrait.ResultData;
import com.skilrock.retailapp.sle_game_portrait.SaleBean;
import com.skilrock.retailapp.sle_game_portrait.SleFetchDataB2C;

import java.util.List;

public class SleGamePlayAdapterNewLand extends RecyclerView.Adapter<SleGamePlayAdapterNewLand.SleGamePlayViewHolderNew> {

    private LayoutInflater layoutInflater;
    private Context context;
    private SleGamePlayViewHolderNew sleGamePlayViewHolder;
    private int totalNumberOfMatch;

    private List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenData;
    private List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenDataB2C;
    private boolean isB2C = false;

    public SleGamePlayAdapterNewLand(Context context, List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean.EventDataBean> evenDataB2C, int totalNumberOfMatch, boolean isB2C){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.evenDataB2C = evenDataB2C;
        this.totalNumberOfMatch = totalNumberOfMatch;
        this.isB2C = isB2C;
    }

    @NonNull
    @Override
    public SleGamePlayViewHolderNew onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_five_new_final_land,null,false);
        return new SleGamePlayViewHolderNew(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SleGamePlayViewHolderNew holder, int position) {
        this.sleGamePlayViewHolder = holder;
        String[] events = null;
        if(isB2C){
            events =  evenDataB2C.get(position).getEventFullName().toLowerCase().split("vs");
            sleGamePlayViewHolder.txt_home.setText(evenDataB2C.get(position).getEventDisplayHome());
            sleGamePlayViewHolder.txt_away.setText(evenDataB2C.get(position).getEventDisplayAway());
            if (evenDataB2C.get(position).getEventVenue().length()<8) {
                sleGamePlayViewHolder.txt_venue.setText(evenDataB2C.get(position).getEventLeague() + ", " + evenDataB2C.get(position).getEventVenue());
            }else{
                sleGamePlayViewHolder.txt_venue.setText(evenDataB2C.get(position).getEventLeague() + ", " + evenDataB2C.get(position).getEventVenue().substring(0,7)+"..");
            }
            sleGamePlayViewHolder.numbers.setText((position+1)+"");
            String min[]=evenDataB2C.get(position).getEventDate().split(" ")[1].split(":");
            sleGamePlayViewHolder.txt_time.setText(min[0]+":"+min[1]);
        }else{
            events =  evenData.get(position).getEventDiscription().toLowerCase().split("vs");
            sleGamePlayViewHolder.txt_home.setText((events!= null && events.length > 0)?events[0].replace("-",""):"");
            sleGamePlayViewHolder.txt_away.setText((events!= null && events.length > 1)?events[1].replace("-",""):"");
            sleGamePlayViewHolder.txt_venue.setText(evenData.get(position).getEventLeague()+", "+evenData.get(position).getEventVenue());
            sleGamePlayViewHolder.numbers.setText((position+1)+"");
        }

        if(totalNumberOfMatch == 3){
            sleGamePlayViewHolder.cb_home_extra.setOnClickListener(new OnClickSleSelection(position,2,sleGamePlayViewHolder.cb_home_extra));
            sleGamePlayViewHolder.cb_draw.setOnClickListener(new OnClickSleSelection(position,3,sleGamePlayViewHolder.cb_draw));
            sleGamePlayViewHolder.cb_away_extra.setOnClickListener(new OnClickSleSelection(position,4,sleGamePlayViewHolder.cb_away_extra));

            if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isHomeSelected()){
                sleGamePlayViewHolder.cb_home_extra.setTextColor(context.getResources().getColor(R.color.white));
                sleGamePlayViewHolder.cb_home_extra.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_home_extra.setTextColor(context.getResources().getColor(R.color.item_five_color));
                sleGamePlayViewHolder.cb_home_extra.setChecked(false);
            }

            if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isDrawSelected()){
                sleGamePlayViewHolder.cb_draw.setTextColor(context.getResources().getColor(R.color.white));
                sleGamePlayViewHolder.cb_draw.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_draw.setTextColor(context.getResources().getColor(R.color.item_five_color));
                sleGamePlayViewHolder.cb_draw.setChecked(false);
            }

            if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isAwaySelected()){
                sleGamePlayViewHolder.cb_away_extra.setTextColor(context.getResources().getColor(R.color.white));
                sleGamePlayViewHolder.cb_away_extra.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_away_extra.setTextColor(context.getResources().getColor(R.color.item_five_color));
                sleGamePlayViewHolder.cb_away_extra.setChecked(false);
            }

        }else{
            sleGamePlayViewHolder.cb_home_extra.setOnClickListener(new OnClickSleSelection(position,1,sleGamePlayViewHolder.cb_home_extra));
            sleGamePlayViewHolder.cb_home.setOnClickListener(new OnClickSleSelection(position,2,sleGamePlayViewHolder.cb_home));
            sleGamePlayViewHolder.cb_draw.setOnClickListener(new OnClickSleSelection(position,3,sleGamePlayViewHolder.cb_draw));
            sleGamePlayViewHolder.cb_away.setOnClickListener(new OnClickSleSelection(position,4,sleGamePlayViewHolder.cb_away));
            sleGamePlayViewHolder.cb_away_extra.setOnClickListener(new OnClickSleSelection(position,5,sleGamePlayViewHolder.cb_away_extra));

            if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isHomePlusSelected()){
                sleGamePlayViewHolder.cb_home_extra.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_home_extra.setChecked(false);
            }

            if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isHomeSelected()){
                sleGamePlayViewHolder.cb_home.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_home.setChecked(false);
            }

            if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isDrawSelected()){
                sleGamePlayViewHolder.cb_draw.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_draw.setChecked(false);
            }

            if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isAwaySelected()){
                sleGamePlayViewHolder.cb_away.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_away.setChecked(false);
            }

            if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isAwayPlusSelected()){
                sleGamePlayViewHolder.cb_away_extra.setChecked(true);
            }else{
                sleGamePlayViewHolder.cb_away_extra.setChecked(false);
            }
        }

    }

    @Override
    public int getItemCount() {
        if(isB2C){
            return evenDataB2C.size();
        }
        return evenData.size();
    }

    private void showToast(int value, String position){
        Toast.makeText(context,"value = "+value +"=position="+position, Toast.LENGTH_LONG).show();
    }


    public class SleGamePlayViewHolderNew extends RecyclerView.ViewHolder{

        public DynamicTextCheckBox cb_home_extra,cb_home,cb_draw,cb_away,cb_away_extra;
        private TextView txt_home,txt_away,txt_venue,numbers,txt_time,txt_venue1;

        public SleGamePlayViewHolderNew(@NonNull View itemView) {
            super(itemView);
            cb_home_extra = itemView.findViewById(R.id.cb_home_extra);
            cb_home = itemView.findViewById(R.id.cb_home);
            cb_draw = itemView.findViewById(R.id.cb_draw);
            cb_away = itemView.findViewById(R.id.cb_away);
            cb_away_extra = itemView.findViewById(R.id.cb_away_extra);
            txt_home = itemView.findViewById(R.id.txt_home);
            txt_away = itemView.findViewById(R.id.txt_away);
            txt_venue = itemView.findViewById(R.id.txt_venue);
            numbers = itemView.findViewById(R.id.numbers);
            txt_time = itemView.findViewById(R.id.txt_time);
            if(totalNumberOfMatch == 3){
                cb_home.setVisibility(View.GONE);
                cb_away.setVisibility(View.GONE);
                cb_home_extra.setTextSize(context.getResources().getDimension(R.dimen._6sdp));
                cb_draw.setTextSize(context.getResources().getDimension(R.dimen._6sdp));
                cb_away_extra.setTextSize(context.getResources().getDimension(R.dimen._6sdp));
            }

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
            for(SaleBean.DrawInfoBean.EventDataBean eventDataBean:BaseClassSle.getBaseClassSle().getEventDataBeans()){
                int totalSelectionsInAView = 0;
                if(eventDataBean.isAwayPlusSelected()){
                    totalSelectionsInAView = totalSelectionsInAView + 1;
                }
                if(eventDataBean.isAwaySelected()){
                    totalSelectionsInAView = totalSelectionsInAView + 1;
                }
                if(eventDataBean.isDrawSelected()){
                    totalSelectionsInAView = totalSelectionsInAView + 1;
                }
                if(eventDataBean.isHomePlusSelected()){
                    totalSelectionsInAView = totalSelectionsInAView + 1;
                }
                if(eventDataBean.isHomeSelected()){
                    totalSelectionsInAView = totalSelectionsInAView + 1;
                }
                totalNoOfLines = totalNoOfLines * totalSelectionsInAView;
            }
            return ((ActivityGamePlayLand)context).setNoOfLines(totalNoOfLines);
        }

        @Override
        public void onClick(View v) {
            Log.i("position",position+"");
            Log.i("gamePlay", gamePlay+"");
            switch (gamePlay){
                case 1:
                    if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isHomePlusSelected()){
//                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setHomePlusSelected(false);
                        cb_home_extra.setTextColor(context.getResources().getColor(R.color.item_five_color));

                        evenDataB2C.get(position).setHomePlusSelected(false,-1);
                    }else{
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(evenDataB2C.get(position).getEventId());
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setHomePlusSelected(true);
                        cb_home_extra.setTextColor(context.getResources().getColor(R.color.white));

                        evenDataB2C.get(position).setHomePlusSelected(true,1);
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setHomePlusSelected(false,-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setHomePlusSelected(false);
                        cb_home_extra.setChecked(false);
                        cb_home_extra.setTextColor(context.getResources().getColor(R.color.item_five_color));
                    }

                    break;
                case 2:
                    if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isHomeSelected()){
//                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setHomeSelected(false);
                        cb_home.setTextColor(context.getResources().getColor(R.color.item_five_color));
                        evenDataB2C.get(position).setHomeSelected(false,-1);
                    }else{
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(evenDataB2C.get(position).getEventId());
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setHomeSelected(true);
                        evenDataB2C.get(position).setHomeSelected(true,1);
                        cb_home.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if(!calculateNoOfLines()){
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setHomeSelected(false);
                        cb_home.setChecked(false);
                        cb_home.setTextColor(context.getResources().getColor(R.color.item_five_color));
                    }
                    break;
                case 3:
                    if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isDrawSelected()){
//                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setDrawSelected(false);
                        cb_draw.setTextColor(context.getResources().getColor(R.color.item_five_color));
                        evenDataB2C.get(position).setDrawSelected(false,-1);
                    }else{
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(evenDataB2C.get(position).getEventId());
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setDrawSelected(true);
                        evenDataB2C.get(position).setDrawSelected(true,1);
                        cb_draw.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setDrawSelected(false,-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setDrawSelected(false);
                        cb_draw.setChecked(false);
                        cb_draw.setTextColor(context.getResources().getColor(R.color.item_five_color));
                    }
                    break;
                case 4:
                    if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isAwaySelected()){
//                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setAwaySelected(false);
                        cb_away.setTextColor(context.getResources().getColor(R.color.item_five_color));
                        evenDataB2C.get(position).setAwaySelected(false,-1);
                    }else{

                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(evenDataB2C.get(position).getEventId());
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setAwaySelected(true);
                        evenDataB2C.get(position).setAwaySelected(true,1);
                        cb_away.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setAwaySelected(false,-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setAwaySelected(false);
                        cb_away.setChecked(false);
                        cb_away.setTextColor(context.getResources().getColor(R.color.item_five_color));
                    }
                    break;
                case 5:
                    if(BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).isAwayPlusSelected()){

//                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setAwayPlusSelected(false);
                        evenDataB2C.get(position).setAwayPlusSelected(false,-1);
                        cb_away_extra.setTextColor(context.getResources().getColor(R.color.item_five_color));
                    }else{

                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setEventId(evenDataB2C.get(position).getEventId());
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setAwayPlusSelected(true);
                        evenDataB2C.get(position).setAwayPlusSelected(true,1);
                        cb_away_extra.setTextColor(context.getResources().getColor(R.color.white));
                    }
                    if(!calculateNoOfLines()){
                        evenDataB2C.get(position).setAwayPlusSelected(false,-1);
                        BaseClassSle.getBaseClassSle().getEventDataBeans().get(position).setAwayPlusSelected(false);
                        cb_away_extra.setChecked(false);
                        cb_away_extra.setTextColor(context.getResources().getColor(R.color.item_five_color));
                    }
                    break;
            }

        }
    }
}
