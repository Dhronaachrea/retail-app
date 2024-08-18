package com.skilrock.retailapp.adapter.scratch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.scratch.ChallanResponseBean;

import java.util.ArrayList;

public class ChallanBookListAdapter extends RecyclerView.Adapter<ChallanBookListAdapter.ChallanBookListViewHolder> {

    private Context context;
    private ArrayList<ChallanResponseBean.GameWiseDetail.BookList> bookList;
    private ArrayList<String> listPreSelectedBooks;
    private CheckBox cbSelectAll;

    public ChallanBookListAdapter(Context context, ArrayList<ChallanResponseBean.GameWiseDetail.BookList> bookList, ArrayList<String> listPreSelectedBooks, CheckBox cbSelectAll) {
        this.bookList               = bookList;
        this.context                = context;
        this.listPreSelectedBooks   = listPreSelectedBooks;
        this.cbSelectAll            = cbSelectAll;

        cbSelectAll.setOnClickListener(v -> selectAllCheckBox(cbSelectAll.isChecked()));
    }

    @NonNull
    @Override
    public ChallanBookListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.challan_check_cox_row, viewGroup, false);

        return new ChallanBookListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallanBookListViewHolder holder, int position) {
        holder.checkBox.setText(bookList.get(position).getBookNumber());

        if (!bookList.get(position).getStatus().trim().equalsIgnoreCase("IN_TRANSIT")) {
            holder.checkBox.setChecked(true);
            holder.checkBox.setEnabled(false);
            holder.checkBox.setTextColor(context.getResources().getColor(R.color.colorLightGrey));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.checkBox.setButtonTintList(ColorStateList.valueOf(Color.LTGRAY));
            }

            if (bookList.get(position).getStatus().trim().equalsIgnoreCase("MISSING")) {
                holder.checkBox.setTextColor(context.getResources().getColor(R.color.colorAppOrange));
                holder.checkBox.setButtonDrawable(null);
            }
        }
        else {
            holder.checkBox.setChecked(bookList.get(position).isChecked());
            /*if (listPreSelectedBooks != null && listPreSelectedBooks.contains(holder.checkBox.getText().toString().trim()))
                holder.checkBox.setChecked(true);*/

        }

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //hashSet.add(bookList.get(getAdapterPosition()).getBookNumber());
            bookList.get(position).setChecked(isChecked);

            if (!isChecked)
                cbSelectAll.setChecked(false);
            //notifyItemChanged(position);
        });

        if(BuildConfig.app_name.equalsIgnoreCase(context.getString(R.string.app_name_FieldX))){
            holder.checkBox.setButtonDrawable(null);
        }

    }

    public ArrayList<ChallanResponseBean.GameWiseDetail.BookList> getBookList() {
        return bookList;
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    private void selectAllCheckBox(boolean isSelectAll) {
        for (ChallanResponseBean.GameWiseDetail.BookList book : bookList)
            book.setChecked(isSelectAll);
        notifyDataSetChanged();
    }

    class ChallanBookListViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        ChallanBookListViewHolder(@NonNull View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
