package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.PackReturnAdapter;
import com.skilrock.retailapp.interfaces.PackReturnListener;
import com.skilrock.retailapp.ui.fragments.scratch.PackReturnMultiScanningFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.LinkedHashMap;
import java.util.Objects;

public class PackReturnDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private LinkedHashMap<String, Boolean> hashMapSelectedPacks, mapForCancel;
    private PackReturnListener listener;
    private PackReturnMultiScanningFragment fragment;

    public PackReturnDialog(Context context, LinkedHashMap<String, Boolean> hashMapSelectedPacks, PackReturnListener listener, PackReturnMultiScanningFragment fragment) {
        super(context);
        this.context                = context;
        this.hashMapSelectedPacks   = hashMapSelectedPacks;
        this.listener               = listener;
        this.fragment               = fragment;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_pack_return);
        initializeWidgets();
    }

    private void initializeWidgets() {
        Button btnCancel            = findViewById(R.id.btn_cancel);
        Button btnReturn            = findViewById(R.id.btn_submit);
        RecyclerView recyclerView   = findViewById(R.id.recyclerView);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        btnCancel.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
        //recyclerView.setHasFixedSize(true);

        /*recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);*/

        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        mapForCancel = new LinkedHashMap<>(hashMapSelectedPacks);
        PackReturnAdapter adapter = new PackReturnAdapter(context, hashMapSelectedPacks, fragment);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Utils.vibrate(context);
        switch (view.getId()) {
            case R.id.btn_submit:
                listener.getUpdatedMap(hashMapSelectedPacks);
                dismiss();
                break;
            case R.id.btn_cancel:
                listener.getUpdatedMap(mapForCancel);
                dismiss();
                break;
        }
    }
}
