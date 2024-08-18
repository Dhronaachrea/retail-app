package com.skilrock.retailapp.adapter.scratch;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.ui.fragments.scratch.PackReturnMultiScanningFragment;
import com.skilrock.retailapp.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class PackReturnAdapter extends RecyclerView.Adapter<PackReturnAdapter.PackReturnViewHolder> {

    private Context context;
    private HashMap<String, Boolean> mapPacks;
    private ArrayList<String> listPacks;
    private PackReturnMultiScanningFragment fragment;

    public PackReturnAdapter(Context context, HashMap<String, Boolean> mapPacks, PackReturnMultiScanningFragment fragment) {
        this.context = context;
        this.mapPacks = mapPacks;
        this.fragment = fragment;
        this.listPacks = new ArrayList<>(this.mapPacks.keySet());
    }

    @NonNull
    @Override
    public PackReturnAdapter.PackReturnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_pack_return, viewGroup, false);

        return new PackReturnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackReturnAdapter.PackReturnViewHolder holder, int position) {
        String packNumber = listPacks.get(position);
        holder.tvPack.setText(packNumber);

        Boolean flag = mapPacks.get(packNumber);

        if (flag != null) {
            if (flag) {
                holder.tvPack.setTextColor(Color.parseColor("#ffffff"));
                holder.tvPack.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_grey_solid));
                holder.tvPack.setTag(1);
            } else {
                holder.tvPack.setTextColor(Color.parseColor("#606060"));
                holder.tvPack.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_grey));
                holder.tvPack.setTag(0);
            }
        }

        holder.tvPack.setOnClickListener(v -> {
            Utils.vibrate(context);
            int tagValue = Integer.parseInt(holder.tvPack.getTag().toString());
            if (tagValue == 1) {
                fragment.isMaxLimitReached = false;
                mapPacks.put(holder.tvPack.getText().toString(), false);
                notifyDataSetChanged();
            } else if (tagValue == 0) {
                if (fragment.isMaxLimitReached) {
                    fragment.showToast(context.getString(R.string.max_pack_limit_reached));
                    return;
                }
                /* mapPacks.put(holder.tvPack.getText().toString(), true);*/
                fragment.selectPack(holder.tvPack.getText().toString());
                notifyDataSetChanged();
            }
        });

        /*holder.tvPack.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
        Utils.vibrate(context);
        int tagValue = Integer.parseInt(holder.tvPack.getTag().toString());
        if (tagValue == 1) {
        mapPacks.put(holder.tvPack.getText().toString(), false);
        }
        else if (tagValue == 0) {
        fragment.selectPack(holder.tvPack.getText().toString());
        }
        notifyDataSetChanged();
        return false;
        }
        });*/
    }

    @Override
    public int getItemCount() {
        return listPacks.size();
    }

    public class PackReturnViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvPack;

        PackReturnViewHolder(@NonNull View view) {
            super(view);
            tvPack = view.findViewById(R.id.tvPack);
//this.setIsRecyclable(false);
//tvPack.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Utils.vibrate(context);
            int tagValue = Integer.parseInt(tvPack.getTag().toString());
            if (tagValue == 1) {
/*tvPack.setTextColor(Color.parseColor("#606060"));
tvPack.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_grey));
tvPack.setTag(0);*/
                mapPacks.put(tvPack.getText().toString(), false);
            } else if (tagValue == 0) {
/*tvPack.setTextColor(Color.parseColor("#ffffff"));
tvPack.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_grey_solid));
tvPack.setTag(1);*/
//mapPacks.put(tvPack.getText().toString(), true);
                fragment.selectPack(tvPack.getText().toString());
            }
            notifyDataSetChanged();
//notifyItemChanged(getAdapterPosition());
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