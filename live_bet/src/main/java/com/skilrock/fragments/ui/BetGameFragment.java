package com.skilrock.fragments.ui;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.skilrock.R;
import com.skilrock.fragments.utils.ProgressBarDialog;

import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;

public class BetGameFragment extends Fragment implements GeckoSession.ProgressDelegate, GeckoSession.NavigationDelegate {

    private static final String TAG = "BetGameFragment";
    GeckoView geckoView;
    GeckoSession session;
    String allCharct = "";
    Bitmap bitmapPrint = null;
    byte[] response = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bet_game, container, false);
        //geckoView = (GeckoView) view.findViewById(R.id.geko_view);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        geckoView = view.findViewById(R.id.geko_view);
        session = new GeckoSession();
        GeckoRuntime runtime = GeckoRuntime.getDefault(getContext());
        Log.d("log", "onCreateView describeContents: " + runtime.describeContents());
        Log.d("log", "onCreateView getDefaultUserAgent: " + GeckoSession.getDefaultUserAgent());
        session.open(runtime);
        geckoView.setSession(session);
        this.session.setProgressDelegate(this.createProgressDelegate());

        Log.d("log", "onViewCreated: " + getArguments().getString("gameUrl"));
        initializeWidgets(view);
        ProgressBarDialog.getProgressDialog().showProgress(getActivity());
        session.loadUri(getArguments().getString("gameUrl"));

    }


    /*log*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvUsername = view.findViewById(R.id.tvUserName);
        TextView tvUserBalance = view.findViewById(R.id.tvUserBalance);
        if (getActivity() != null) {
            Bundle bundle = getArguments();
            getActivity().setTitle(bundle.getString("title"));
            tvTitle.setText(bundle.getString("title"));
        }
//        refreshBalance();
    }

    private final GeckoSession.ProgressDelegate createProgressDelegate() {
        return new GeckoSession.ProgressDelegate() {

            @Override
            public void onPageStop(@NonNull GeckoSession geckoSession, boolean b) {
                Log.d("log", "onPageStop: ");
                ProgressBarDialog.getProgressDialog().dismiss();
            }

            @Override
            public void onPageStart(@NonNull GeckoSession geckoSession, @NonNull String s) {
                Log.d("log", "onPageStart: ");
                ProgressBarDialog.getProgressDialog().showProgress(getActivity());
            }

            @Override
            public void onProgressChange(@NonNull GeckoSession geckoSession, int i) {
                Log.d("log", "onProgressChange: ");
            }

            @Override
            public void onSecurityChange(@NonNull GeckoSession geckoSession, @NonNull SecurityInformation securityInformation) {
                Log.d("log", "onSecurityChange: ");
            }

            @Override
            public void onSessionStateChange(@NonNull GeckoSession geckoSession, @NonNull GeckoSession.SessionState sessionState) {
                Log.d("log", "onSessionStateChange: ");
            }
        };

    }


}


