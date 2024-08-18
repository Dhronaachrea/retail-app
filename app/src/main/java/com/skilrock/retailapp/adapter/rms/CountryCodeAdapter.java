package com.skilrock.retailapp.adapter.rms;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.CountryCodeListener;
import com.skilrock.retailapp.models.rms.CountryCodeBean;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class CountryCodeAdapter extends RecyclerView.Adapter<CountryCodeAdapter.CountryCodeViewHolder> {

    private Context context;
    private ArrayList<CountryCodeBean.Country> listCountries;
    private CountryCodeListener listener;
    private CountryCodeListener listenerDismiss;

    public CountryCodeAdapter(Context context, ArrayList<CountryCodeBean.Country> listCountries, CountryCodeListener listener, CountryCodeListener listenerDismiss) {
        this.context                = context;
        this.listCountries          = listCountries;
        this.listener               = listener;
        this.listenerDismiss        = listenerDismiss;
    }

    @NonNull
    @Override
    public CountryCodeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_country_code, viewGroup, false);

        return new CountryCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryCodeViewHolder holder, int position) {
        String text = listCountries.get(position).getName() + " (" + listCountries.get(position).getCode() + ")";
        holder.tvCode.setText(text);
        holder.tvCode.setTag(listCountries.get(position).getCode());
    }

    @Override
    public int getItemCount() {
        return listCountries.size();
    }

    class CountryCodeViewHolder extends RecyclerView.ViewHolder {

        TextView tvCode;

        CountryCodeViewHolder(@NonNull View viewHolder) {
            super(viewHolder);
            tvCode = viewHolder.findViewById(R.id.tvCode);

            tvCode.setOnClickListener(view -> {
                Utils.vibrate(Objects.requireNonNull(context));
                listener.onCountryCodeSelected(String.valueOf(tvCode.getTag()));
                listenerDismiss.onCountryCodeSelected(String.valueOf(tvCode.getTag()));
            });
        }
    }
}
