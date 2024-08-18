package com.skilrock.retailapp.portrait_draw_games.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.drawgame.DrawAdapterPortrait;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class DrawDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws;
    private DrawAdapterPortrait drawAdapter;
    private ArrayList<Integer> LIST_PRE_SELECTED_INDEX = new ArrayList<>();
    private SelectedDrawsListener listener;
    private int maxAdvanceDraws;

    public DrawDialog(Context context, ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws, SelectedDrawsListener listener, int maxAdvanceDraws) {
        super(context);
        this.context            = context;
        this.listDraws          = listDraws;
        this.listener           = listener;
        this.maxAdvanceDraws    = maxAdvanceDraws;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_draw_dialog);
        setPreSelectedIndex();
        initializeWidgets();
    }

    private void setPreSelectedIndex() {
        LIST_PRE_SELECTED_INDEX.clear();
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO data: listDraws) {
            if (data.isSelected())
                LIST_PRE_SELECTED_INDEX.add(data.getDrawId());
        }
    }

    private void initializeWidgets() {
        RecyclerView recyclerView   = findViewById(R.id.rvDraw);
        Button btnOk                = findViewById(R.id.btn_done);
        Button btnCancel            = findViewById(R.id.btn_cancel);
        drawAdapter                 = new DrawAdapterPortrait(getContext(), listDraws, maxAdvanceDraws);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(drawAdapter);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                if (drawAdapter != null)
                    listener.getSelectedAdvanceDraws(drawAdapter.getSelectedDraws());
                dismiss();
                break;

            case R.id.btn_cancel:
                for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO data: listDraws) {
                    if (LIST_PRE_SELECTED_INDEX.contains(data.getDrawId()))
                        data.setSelected(true);
                    else
                        data.setSelected(false);
                }
                dismiss();
                break;
        }
    }

    public interface SelectedDrawsListener {
        void getSelectedAdvanceDraws(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws);
    }
}
