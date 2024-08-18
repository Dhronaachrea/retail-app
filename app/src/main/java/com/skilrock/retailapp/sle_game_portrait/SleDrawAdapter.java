package com.skilrock.retailapp.sle_game_portrait;

import android.content.Context;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.time.LocalDate;
import java.util.List;

public class SleDrawAdapter extends RecyclerView.Adapter<SleDrawAdapter.SleDrawViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private SleDrawViewHolder sleGamePlayViewHolder;
    private int totalNumberOfMatch;
    String amount=" ";

    LocalDate ld ;

    private List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeans;
    private List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeansB2C;
    private boolean isB2C = false;

    public SleDrawAdapter(Context context, List<ResultData.DrawResultDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeans){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.drawDataBeans = drawDataBeans;
    }
    public SleDrawAdapter(Context context, List<SleFetchDataB2C.SleDataBean.GameDataBean.GameTypeDataBean.DrawDataBean> drawDataBeansB2C, boolean isB2C){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.drawDataBeansB2C = drawDataBeansB2C;
        this.isB2C = isB2C;
    }

    @NonNull
    @Override
    public SleDrawViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO))
           view = layoutInflater.inflate(R.layout.cards_sports_view_v2pro,null,false);
        else
         view = layoutInflater.inflate(R.layout.cards_sports_view,null,false);
        return new SleDrawViewHolder(view);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SleDrawViewHolder holder, int position) {
        this.sleGamePlayViewHolder = holder;

        if(isB2C){
             String time=drawDataBeansB2C.get(position).getDrawDateTime().split(" ")[1];
             String time_edit[]= time.split(":");
            sleGamePlayViewHolder.draw_date.setText(getDateMonthWithTime(drawDataBeansB2C.get(position).getDrawDateTime().split(" ")[0],"\n",time_edit[0]+":"+time_edit[1]));
            sleGamePlayViewHolder.main_layout.setOnClickListener(new OnClickSleSelection(position));
        }else{
            sleGamePlayViewHolder.draw_date.setText(getDateMonth(drawDataBeans.get(position).getDrawDateTime().split(" ")[0],"\n"));
            sleGamePlayViewHolder.main_layout.setOnClickListener(new OnClickSleSelection(position));
        }
        sleGamePlayViewHolder.draw_event.setText(drawDataBeansB2C.get(position).getDrawDisplayString());
        amount= Utils.getBalanceToView(drawDataBeansB2C.get(position).getPrizePoolAmount(), ",", " ", 0);
        if(ActivityDraws.sleFetchDataB2C.getSleData().getCurrencyConversionRates() != null){

            sleGamePlayViewHolder.draw_price.setText(ActivityDraws.sleFetchDataB2C.getSleData().getCurrencyConversionRates().get(0).getCurrencyCode()+amount+"");
        }else{
            sleGamePlayViewHolder.draw_price.setText(""+amount);
        }


    }


    @Override
    public int getItemCount() {
        if(isB2C){
            return drawDataBeansB2C.size();
        }
        return drawDataBeans.size();
    }

    private void showToast(int value, String position){
        Toast.makeText(context,"value = "+value +"=position="+position, Toast.LENGTH_LONG).show();
    }


    public class SleDrawViewHolder extends RecyclerView.ViewHolder{

        private TextView draw_date,draw_event,draw_price;
        private RelativeLayout main_layout;

        public SleDrawViewHolder(@NonNull View itemView) {
            super(itemView);
            main_layout = itemView.findViewById(R.id.main_layout);
            draw_date = itemView.findViewById(R.id.draw_date);
            draw_event = itemView.findViewById(R.id.draw_event);
            draw_price = itemView.findViewById(R.id.draw_price);


        }
    }

    public static String getDateMonth(String date, String line){
        String[] data = date.split("-");
        String returnData = data[0];
        if(data[1].equals("01")){
            returnData = returnData+line+"JAN";
        }else if(data[1].equals("02")){
            returnData = returnData+line+"FEB";
        }else if(data[1].equals("03")){
            returnData = returnData+line+"MAR";
        }else if(data[1].equals("04")){
            returnData = returnData+line+"APR";
        }else if(data[1].equals("05")){
            returnData = returnData+line+"MAY";
        }else if(data[1].equals("06")){
            returnData = returnData+line+"JUN";
        }else if(data[1].equals("07")){
            returnData = returnData+line+"JUL";
        }else if(data[1].equals("08")){
            returnData = returnData+line+"AUG";
        }else if(data[1].equals("09")){
            returnData = returnData+line+"SEP";
        }else if(data[1].equals("10")){
            returnData = returnData+line+"OCT";
        }else if(data[1].equals("11")){
            returnData = returnData+line+"NOV";
        }else if(data[1].equals("12")){
            returnData = returnData+line+"DEC";
        }
        return returnData;
    }

    public static String getDateMonthWithTime(String date, String line,String time){
        String[] data = date.split("-");
        String returnData = data[0];
        if(data[1].equals("01")){
            returnData = "JAN"+line+returnData+line+time;
        }else if(data[1].equals("02")){
            returnData = "FEB"+line+returnData+line+time;
        }else if(data[1].equals("03")){
            returnData = "MAR"+line+returnData+line+time;
        }else if(data[1].equals("04")){
            returnData = "APR"+line+returnData+line+time;
        }else if(data[1].equals("05")){
            returnData = "MAY"+line+returnData+line+time;
        }else if(data[1].equals("06")){
            returnData = "JUN"+line+returnData+line+time;
        }else if(data[1].equals("07")){
            returnData = "JUL"+line+returnData+line+time;
        }else if(data[1].equals("08")){
            returnData = "AUG"+line+returnData+line+time;
        }else if(data[1].equals("09")){
            returnData = "SEP"+line+returnData+line+time;
        }else if(data[1].equals("10")){
            returnData = "OCT"+line+returnData+line+time;
        }else if(data[1].equals("11")){
            returnData = "NOV"+line+returnData+line+time;
        }else if(data[1].equals("12")){
            returnData = "DEC"+line+returnData+line+time;
        }
        return returnData;
    }

    class OnClickSleSelection implements View.OnClickListener {
        private int position;
        public OnClickSleSelection(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Log.i("position",position+"");
            ((ActivityDraws)context).startGamePlay(position,isB2C);

        }
    }
}
