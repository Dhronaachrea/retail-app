package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.UserSelectListener;
import com.skilrock.retailapp.models.ola.OrgUserResponseBeanNew;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;

public class OrgUserListAdapter extends RecyclerView.Adapter<OrgUserListAdapter.UserViewHolder> {

    private ArrayList<OrgUserResponseBeanNew.UserSearchDatum> arrayList;
    private Context context;
    private UserSelectListener userSelectListener;

    public OrgUserListAdapter(ArrayList<OrgUserResponseBeanNew.UserSearchDatum> listBill, Context context, UserSelectListener userSelectListener) {
        this.arrayList = listBill;
        this.context = context;
        this.userSelectListener = userSelectListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_org_user, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        holder.tvUserName.setText(arrayList.get(position).getUserName());

        String name = arrayList.get(position).getName().trim();

        holder.tvName.setText(Utils.capitalizeFirstLetter(name));
        holder.tvMobile.setText(arrayList.get(position).getMobileNumber());
        holder.tvEmail.setText(arrayList.get(position).getEmailId());

        holder.tvUserBalance.setText(String.valueOf(arrayList.get(position).getDisplayBalance()));

        holder.tvUserBalanceLabel.setText(arrayList.get(position).getRawdisplayBalance() < 0.0 ? context.getString(R.string.to_be_paid) :
                context.getString(R.string.to_be_received));

        holder.tvUserBalance.setTextColor(arrayList.get(position).getRawdisplayBalance() < 0.0 ? context.getResources().getColor(R.color.colorDue) :
                context.getResources().getColor(R.color.colorGreen));
        holder.cardViewRow.setOnClickListener(v -> userSelectListener.onUserClicked(position, String.valueOf(arrayList.get(position).getUserId())));

        holder.cardViewRow.setOnClickListener(v -> userSelectListener.onUserClicked(position, String.valueOf(arrayList.get(position).getUserId())));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName, tvName, tvMobile, tvEmail, tvStatus, tvUserBalance, tvUserBalanceLabel;
        LinearLayout cardViewRow;

        UserViewHolder(@NonNull View view) {
            super(view);
            tvUserBalanceLabel  = view.findViewById(R.id.tv_user_balance_label);
            tvUserName          = view.findViewById(R.id.tv_username);
            tvStatus            = view.findViewById(R.id.tv_status);
            tvName              = view.findViewById(R.id.tv_name);
            tvMobile            = view.findViewById(R.id.tv_mobile);
            tvEmail             = view.findViewById(R.id.tv_email);
            tvUserBalance       = view.findViewById(R.id.tv_user_balance);
            cardViewRow         = view.findViewById(R.id.card_view);
        }
    }

    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }
}
