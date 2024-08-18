package com.skilrock.fragments.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.skilrock.R;
import com.skilrock.fragments.utils.AppConstants;
import com.skilrock.fragments.utils.ProgressBarDialog;
import com.skilrock.fragments.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mozilla.geckoview.AllowOrDeny;
import org.mozilla.geckoview.GeckoResult;
import org.mozilla.geckoview.GeckoRuntime;
import org.mozilla.geckoview.GeckoSession;
import org.mozilla.geckoview.GeckoView;
import org.mozilla.geckoview.WebRequestError;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;


public class CamWin247GameFragment extends Fragment implements GeckoSession.ProgressDelegate, GeckoSession.NavigationDelegate {

    GeckoView geckoView;
    GeckoSession session;
    String allCharct = "";
    Bitmap bitmapPrint = null;
    byte[] response = null;
    GeckoRuntime runtime;
    String gamUrl;
    WebView handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camwin247, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        handler = new WebView(getActivity());
        handler.setVisibility(View.INVISIBLE);
        handler.getSettings().setJavaScriptEnabled(true);
        handler.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        handler.getSettings().setSupportZoom(true);
        handler.getSettings().setBuiltInZoomControls(true);
        handler.getSettings().setDomStorageEnabled(true);
        handler.setWebViewClient(new AppWebViewClient());

        handler.addJavascriptInterface(new MyJavaScriptInterface(getActivity()), "HtmlViewer");
        //new PrintClass().setListener(this::callHome);

        if (bundle != null) {
            String language = bundle.getString("language");
            try {
                updateLanguage(getActivity(), language);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        geckoView = view.findViewById(R.id.geko_view);
        session = new GeckoSession();
        runtime = GeckoRuntime.getDefault(getContext());
        Log.d("log", "onCreateView describeContents: " + runtime.describeContents());
        Log.d("log", "onCreateView getDefaultUserAgent: " + GeckoSession.getDefaultUserAgent());
        session.open(runtime);
        geckoView.setSession(session);
        this.session.setProgressDelegate(this.createProgressDelegate());
        this.session.setNavigationDelegate(this.nagivateDelicate());

        Log.d("log", "onViewCreated: " + getArguments().getString("gameUrl"));
        initializeWidgets(view);
        ProgressBarDialog.getProgressDialog().showProgress(getActivity());

    }

    public String getGamUrl() {
        return gamUrl;
    }

    public void setGamUrl(String gamUrl) {
        this.gamUrl = gamUrl;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvUsername = view.findViewById(R.id.tvUserName);
        TextView tvUserBalance = view.findViewById(R.id.tvUserBalance);
        if (getActivity() != null) {
            Bundle bundle = getArguments();

            session.loadUri(bundle.getString("gameUrl"));
            setGamUrl(bundle.getString("gameUrl"));
            getActivity().setTitle(bundle.getString("title"));
            tvTitle.setText(bundle.getString("title"));
            String language = bundle.getString("language");
            try {
                updateLanguage(getActivity(), language);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        refreshBalance();
    }

    private GeckoSession.NavigationDelegate nagivateDelicate() {
        return new GeckoSession.NavigationDelegate() {
            @Override
            public void onLocationChange(@NonNull GeckoSession geckoSession, @Nullable String s) {
                Log.d("log", "onLocationChange: " + s);

            }

            @Override
            public void onCanGoBack(@NonNull GeckoSession geckoSession, boolean b) {

            }

            @Override
            public void onCanGoForward(@NonNull GeckoSession geckoSession, boolean b) {

            }

            @Nullable
            @Override
            public GeckoResult<AllowOrDeny> onLoadRequest(@NonNull GeckoSession geckoSession, @NonNull LoadRequest loadRequest) {
                return null;
            }

            @Nullable
            @Override
            public GeckoResult<GeckoSession> onNewSession(@NonNull GeckoSession geckoSession, @NonNull String s) {
                if (s.contains("checkformobile") && s.contains("urlrefbid")) {
                    ProgressBarDialog.getProgressDialog().showProgress(getActivity());
                    //getHtmlToString(s);
                    handler.loadUrl(s);
                }
                return null;
            }

            @Nullable
            @Override
            public GeckoResult<String> onLoadError(@NonNull GeckoSession geckoSession, @Nullable String s, @NonNull WebRequestError webRequestError) {
                Log.d("log", "onLoadError: " + s);
                return null;
            }
        };
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


    private void getHtmlToString(final String url1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder stringBuilder = new StringBuilder();
                Document doc = Jsoup.parse(url1);
                String title = doc.title();
                Element body = doc.body();
                String two_val = "";
                String one_val = "";
                Element printArea = body.getElementById("printArea");
                String printAre = printArea.toString();
                if (printAre.contains("href")) {
                    String image = printAre.substring(printAre.indexOf("href"), printAre.indexOf("bmp") + 3);
                    image = "https://camwin247.com/" + image.substring(image.indexOf("themes"));
                    Log.i("image", image);
                    try {
                        URL url = new URL(image);
                        InputStream in = new BufferedInputStream(url.openStream());
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        int n = 0;
                        while (-1 != (n = in.read(buf))) {
                            out.write(buf, 0, n);
                        }
                        out.close();
                        in.close();
                        byte[] response = out.toByteArray();
                        bitmapPrint = BitmapFactory.decodeByteArray(response, 0, response.length);
                        Log.i("imagedownload", "yes");

                    } catch (Exception e) {
                        Log.e("download image", e.getLocalizedMessage());
                    }
                }
                stringBuilder.append(title).append("\n");
                for (Element table : doc.select("table[class=betprint table table-bordered]")) {
                    for (Element row : table.select("tr")) {
                        two_val = "";
                        one_val = "";
                        Elements tds = row.select("td");
                        one_val = one_val + row.text();
                        if (tds.size() > 1) {
                            for (int i = 0; i < tds.size(); i++) {
                                if (i != tds.size() - 1) {
                                    two_val = two_val + tds.get(i).text() + "##";
                                } else {
                                    two_val = two_val + tds.get(i).text();
                                }

                            }
                        }
                        if (!two_val.equalsIgnoreCase("")) {
                            allCharct = allCharct + " " + two_val + "\n";
                        } else {
                            allCharct = allCharct + one_val + "\n";
                        }
                    }

                }
                for (Element table : doc.select("table[class=table betprint]")) {
                    for (Element row : table.select("tr")) {
                        Elements tds = row.select("td");
                        allCharct = allCharct + row.text() + "\n";
                    }
                }


                Log.d("end", "end");

                if (allCharct.length() > 0) {
                    getActivity().runOnUiThread(() -> {
                        //Print here using all character string
                        Log.d("Game_res", allCharct);
                        String[] printData = allCharct.split("\n");
                        try {
                            if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
                                //BaseClass.getInstance().getPrintCall().callPrint(printData, getActivity(), bitmapPrint);
                                sendData(printData,bitmapPrint);
                                callHome();


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_TPS570)) {
                            sendData(printData,bitmapPrint);
                            callHome();
                        }
                    });
                }

            }
        }).start();

    }


    void updateLanguage(Activity context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public interface HomeCallback {
        void callHome();
    }


    public void callHome() {
        ProgressBarDialog.getProgressDialog().dismiss();
        allCharct = " ";
        getActivity().onBackPressed();

    }

    public class AppWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            handler.loadUrl("javascript:HtmlViewer.showHTML" +
                    "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
        }
    }

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            System.out.println(html);
            getHtmlToString(html);
        }

    }

    private void sendData(String response [],Bitmap bitmap) {
        // 1 means start loader
        // 0 means stop loader
        Intent intent = new Intent("CamwinData");
        intent.putExtra("data", response);
        intent.putExtra("bitmap", bitmap);
        intent.putExtra("action",true);

        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }


}
