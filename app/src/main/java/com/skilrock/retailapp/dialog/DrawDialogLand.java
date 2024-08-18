package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.drawgame.DrawAdapter;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;

import java.util.ArrayList;
import java.util.Objects;

public class DrawDialogLand extends Dialog implements View.OnClickListener {

    private Context context;
    private ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws;
    private DrawAdapter drawAdapter;
    private SelectedDrawsListener listener;
    private ImageView imageCancel;
    private int maxAdvanceDraws;
    private ArrayList<String> selectedIds;

    public DrawDialogLand(Context context,
                          ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws,
                          SelectedDrawsListener listener, int maxAdvanceDraws, ArrayList<String> selectedIds) {
        super(context);
        this.context            = context;
        this.listDraws          = listDraws;
        this.listener           = listener;
        this.maxAdvanceDraws    = maxAdvanceDraws;
        this.selectedIds        = selectedIds;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setDimAmount(0.7f);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimationZoom;

        setContentView(R.layout.layout_draw_dialog_land);

        initializeWidgets();
    }

    private void initializeWidgets() {
        RecyclerView recyclerView   = findViewById(R.id.rvDraw);
        Button button = findViewById(R.id.btn_done);
        imageCancel = findViewById(R.id.image_cancel);

        ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> arrayList = new ArrayList<>();
        arrayList.addAll(listDraws);

        drawAdapter = new DrawAdapter(getContext(), arrayList, maxAdvanceDraws);

        setSelected(arrayList);

        if (selectedIds.isEmpty()) setDeselected(arrayList);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(drawAdapter);
        button.setOnClickListener(this);
        imageCancel.setOnClickListener(this);
    }

    private void setDeselected(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> arrayList) {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO drawRespVO : arrayList) {
            drawRespVO.setSelected(false);
        }
    }

    private void setSelected(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> arrayList) {
        for (DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO drawRespVO : arrayList) {

            for (String id : selectedIds) {
                if (!drawRespVO.getDrawId().toString().equalsIgnoreCase(id))
                    drawRespVO.setSelected(false);
                if (drawRespVO.getDrawId().toString().equalsIgnoreCase(id)) {
                    drawRespVO.setSelected(true);
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                if (drawAdapter != null)
                    if (drawAdapter.getSelectedDraws() == null || drawAdapter.getSelectedDraws().isEmpty()) {
                        Toast.makeText(context, R.string.please_select_at_least_one_draw, Toast.LENGTH_SHORT).show();
                        return;
                    }
                listener.getSelectedAdvanceDraws(drawAdapter.getSelectedDraws());
                dismiss();
                break;
            case R.id.image_cancel:
                dismiss();
                break;
        }
    }

    public interface SelectedDrawsListener {
        void getSelectedAdvanceDraws(ArrayList<DrawFetchGameDataResponseBean.ResponseData.GameRespVO.DrawRespVO> listDraws);
    }
}
