package com.skilrock.retailapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skilrock.retailapp.R;

public class DrawGameFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.draw_game_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*setMenuListViewHolder(view);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //scratchGameViewModel = ViewModelProviders.of(this).get(ScratchGameViewModel.class);
    }
}
