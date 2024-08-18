package com.skilrock.retailapp.adapter.ola;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ModuleListener;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

public class OlaMenuAdapter extends RecyclerView.Adapter<OlaMenuAdapter.OlaMenuViewHolder> {

    private List<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> listMenus;
    private ModuleListener moduleListener;
    private Context context;

    public OlaMenuAdapter(Context context, List<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> listMenus, ModuleListener moduleListener) {
        this.context        = context;
        this.listMenus      = listMenus;
        this.moduleListener = moduleListener;
    }

    @NonNull
    @Override
    public OlaMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_ola_menu, viewGroup, false);

        return new OlaMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OlaMenuViewHolder holder, int position) {
        holder.button.setText(listMenus.get(position).getCaption());
        if (listMenus.get(position).getMenuCode().trim().equalsIgnoreCase(AppConstants.OLA_DEPOSIT))
            holder.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_wallet_dark, 0, 0, 0);
        else if (listMenus.get(position).getMenuCode().trim().equalsIgnoreCase(AppConstants.OLA_WITHDRAW))
            holder.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_withdrawal_dark, 0, 0, 0);
        else if (listMenus.get(position).getMenuCode().trim().equalsIgnoreCase(AppConstants.OLA_REGISTER))
            holder.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_registration_dark, 0, 0, 0);
        else
            holder.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_registration_dark, 0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return listMenus.size();
    }

    public class OlaMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FrameLayout frameLayout;
        Button button;

        public OlaMenuViewHolder(@NonNull View view) {
            super(view);
            frameLayout = view.findViewById(R.id.frame_layout);
            button      = view.findViewById(R.id.button);

            frameLayout.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Utils.vibrate(context);
            frameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.button_rounded_bg));
            HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBeanList = listMenus.get(getAdapterPosition());
            moduleListener.onModuleClicked(menuBeanList.getMenuCode(), getAdapterPosition(), menuBeanList);
        }
    }
}
