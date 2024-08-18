package com.skilrock.retailapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.ChallanBookListAdapter;
import com.skilrock.retailapp.interfaces.CallBackListener;
import com.skilrock.retailapp.models.scratch.ChallanResponseBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ChallanBookSelectionDialog extends Dialog implements
        View.OnClickListener {

    private Context context;
    private CallBackListener listener;
    private ArrayList<ChallanResponseBean.GameWiseDetail.BookList> bookList;
    private ChallanBookListAdapter challanBookListAdapter;
    private int gameId;
    private HashMap<Integer, ArrayList<String>> mapSelectedBooks;
    private int position;
    private String bookName;

    public ChallanBookSelectionDialog(Context context, ArrayList<ChallanResponseBean.GameWiseDetail.BookList> bookList, int gameId, CallBackListener listener, HashMap<Integer, ArrayList<String>> mapSelectedBooks, int position, String bookName) {
        super(context);
        this.context            = context;
        this.bookList           = bookList;
        this.gameId             = gameId;
        this.listener           = listener;
        this.mapSelectedBooks   = mapSelectedBooks;
        this.position           = position;
        this.bookName           = bookName;
        Log.i("log", "BookList: " + bookList);
        Log.i("log", "GameId: " + gameId);
        Log.i("log", "MapSelectedBooks: " + mapSelectedBooks);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_challan);
        initializeWidgets();
    }

    private void initializeWidgets() {
        CheckBox cbSelectAll                = findViewById(R.id.cb_select_all);
        RecyclerView rvCheckBoxContainer    = findViewById(R.id.checkbox_container);
        Button btnDone                      = findViewById(R.id.btn_done);
        //Button btnCancel                    = findViewById(R.id.btn_cancel);
        TextView tvBookName                 = findViewById(R.id.tvBookName);

        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
            LinearLayout llContainer = findViewById(R.id.llContainer);
            Objects.requireNonNull(getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setDimAmount(0.7f);
            ViewGroup.LayoutParams params = llContainer.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = 400;
            llContainer.setLayoutParams(params);
        }

        tvBookName.setText(bookName);
        btnDone.setOnClickListener(this);
        if (BuildConfig.app_name.equalsIgnoreCase(context.getString(R.string.app_name_FieldX))) {
            btnDone.setVisibility(View.GONE);
            cbSelectAll.setVisibility(View.GONE);
        }
        //btnCancel.setOnClickListener(this);

        Log.d("log", "Book List: " + bookList);
        Log.d("log", "Selected Packs: " + mapSelectedBooks.get(gameId));
        boolean isAllSelected = true;
        for (ChallanResponseBean.GameWiseDetail.BookList book: bookList) {
            if (!book.isChecked()) {
                isAllSelected = false;
                break;
            }
        }
        cbSelectAll.setChecked(isAllSelected);
        challanBookListAdapter = new ChallanBookListAdapter(getContext(), this.bookList, mapSelectedBooks.get(gameId), cbSelectAll);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvCheckBoxContainer.setLayoutManager(linearLayoutManager);
        rvCheckBoxContainer.setItemAnimator(new DefaultItemAnimator());
        rvCheckBoxContainer.setAdapter(challanBookListAdapter);

        /*cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                challanBookListAdapter.selectAllCheckBox(isChecked);
            }
        });*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_done:
                ArrayList<String> list = new ArrayList<>();
                ArrayList<ChallanResponseBean.GameWiseDetail.BookList> bookLists = challanBookListAdapter.getBookList();
                /*for (int x = rvCheckBoxContainer.getChildCount(), position = 0; position < x; ++position) {
                    RecyclerView.ViewHolder viewHolder = rvCheckBoxContainer.getChildViewHolder(rvCheckBoxContainer.getChildAt(position));
                    if (viewHolder instanceof ChallanBookListAdapter.ChallanBookListViewHolder) {
                        ChallanBookListAdapter.ChallanBookListViewHolder holder = (ChallanBookListAdapter.ChallanBookListViewHolder) viewHolder;
                        if (holder.checkBox.isEnabled() && holder.checkBox.isChecked()) {
                            Log.d("log", "Game Id: " + gameId + ", Book: " + holder.checkBox.getText());
                            list.add(holder.checkBox.getText().toString());
                        }
                    }
                }*/
                for (ChallanResponseBean.GameWiseDetail.BookList book: bookLists) {
                    if(book.getStatus().trim().equalsIgnoreCase("IN_TRANSIT") && book.isChecked()) {
                        list.add(book.getBookNumber());
                    }
                }

                if (list.size() > 0)
                    listener.notifyEvent(gameId, list, position);
                else
                    listener.notifyEvent(gameId, null, position);
                dismiss();
                break;
            /*case R.id.btn_cancel:
                listener.notifyEvent();
                dismiss();
                break;*/
        }
    }
}
