package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.CountryCodeAdapter;
import com.skilrock.retailapp.interfaces.CountryCodeListener;
import com.skilrock.retailapp.models.rms.CountryCodeBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CountryCode;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class CountryCodeDialog extends Dialog {

    private Context context;
    private CountryCodeListener listener;
    private ArrayList<String> arrayList;

    public CountryCodeDialog(Context context, CountryCodeListener listener, ArrayList<String> arrayList) {
        super(context);
        this.context            = context;
        this.listener           = listener;
        this.arrayList          = arrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_country_code);
        initializeWidgets();
    }

    private void initializeWidgets() {

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        CountryCodeAdapter adapter = null;

        CountryCodeListener listenerDismiss = countryCode -> dismiss();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Gson gson = new Gson();
        CountryCodeBean model = gson.fromJson(CountryCode.COUNTRY_CODE_JSON, CountryCodeBean.class);

        if (arrayList != null) {
            ArrayList<CountryCodeBean.Country> countries = new ArrayList<>();

            for (String code : arrayList) {
                CountryCodeBean.Country country = new CountryCodeBean.Country();
                country.setCode(code);
                country.setName(Utils.getCountryName(code));
                countries.add(country);
            }

            adapter = new CountryCodeAdapter(getContext(), countries, listener, listenerDismiss);
        } else {
            adapter = new CountryCodeAdapter(getContext(), model.getCountries(), listener, listenerDismiss);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
