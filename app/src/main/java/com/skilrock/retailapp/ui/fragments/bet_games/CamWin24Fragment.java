package com.skilrock.retailapp.ui.fragments.bet_games;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;


public class CamWin24Fragment extends BaseFragment {

    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private String BASEURL;
    private static final String TAG = "MyActivity";
    private UsbThermalPrinter usbThermalPrinter = null;
    private  boolean isImageDownLoaded=false;
    private WebView webView;
    String print_url=null;
    public String getPrint_url() {
        return print_url;
    }

    public void setPrint_url(String print_url) {
        this.print_url = print_url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camwin_24, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        usbThermalPrinter = getUsbThermalPrinter();
        isImageDownLoaded = false;
        if (bundle != null) {
            String language = bundle.getString("language");
            try { updateLanguage(master, language); } catch (Exception e) { e.printStackTrace(); }
            menuBean = bundle.getParcelable("MenuBean");
            BASEURL = menuBean.getBasePath();
        }

        Log.v(TAG, "baserurl:" + BASEURL);
        initializeWidgets(view);
    }

    void updateLanguage(Activity context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public UsbThermalPrinter getUsbThermalPrinter() {
        if (usbThermalPrinter == null) {
            usbThermalPrinter = new UsbThermalPrinter(getActivity());
        }
        return usbThermalPrinter;
    }

    /*prak24*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        webView = view.findViewById(R.id.webView);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        if (getActivity() != null) {
            Bundle bundle = getArguments();
            getActivity().setTitle(bundle.getString("title"));
            tvTitle.setText(bundle.getString("title"));
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.setWebViewClient(new AppWebViewClients());

        webView.loadUrl(BASEURL);

        ProgressBarDialog.getProgressDialog().showProgress(getActivity());
        refreshBalance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            trimCache(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }

            }
            return dir.delete();
        } else return false;
    }

    public class AppWebViewClients extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("user_agent1", webView.getSettings().getUserAgentString());
           /* if (url.contains("checkformobile") && url.contains("urlrefbid")) {
                    getHtmlToString(url);
            }*/
            Log.d("htmlStarted", url);


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("user_agent2", webView.getSettings().getUserAgentString());
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("user_agent3", webView.getSettings().getUserAgentString());
            super.onPageFinished(view, url);
            ProgressBarDialog.getProgressDialog().dismiss();

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            Log.d("user_agent4", url);
            super.onLoadResource(view, url);
           //url = "https://www.camwin247.com/themes/camwin247.com/content/mobile/img/logo-bw.bmp";
            if(url.contains("mobile") && url.contains("img") && url.contains("logo-bw.bmp")){
                downloadImage(url);
            }

            if (url.contains("checkformobile") && url.contains("urlrefbid")) {
              setPrint_url(url);
            }
        }
    }

    String allCharct = "";
    Bitmap bitmapPrint = null;
    byte[] response = null;
    private void downloadImage(final String imageUrl){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(imageUrl);
                    InputStream in = new BufferedInputStream(url.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1!=(n=in.read(buf)))
                    {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    response = out.toByteArray();
                    bitmapPrint = BitmapFactory.decodeByteArray(response, 0, response.length);
                    isImageDownLoaded = true;
                    Log.i("imagedownload","yes");
                }catch (Exception e){
                    Log.e("download image",e.getLocalizedMessage());
                }

            }
        }).start();

        if (getPrint_url()!=null&&!getPrint_url().equalsIgnoreCase("")){
            getHtmlToString(getPrint_url());
        }
    }

    private void getHtmlToString(final String url1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder stringBuilder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect(url1).get();
                    String title = doc.title();
                    Element body = doc.body();
                    String two_val = "";
                    String one_val = "";
                    Element printArea = body.getElementById("printArea");
                    stringBuilder.append(title).append("\n");
                    for (Element table : doc.select("table[class=betprint table table-bordered]")) {
                        for (Element row : table.select("tr")) {
                            two_val = "";
                            one_val = "";
                            Elements tds = row.select("td");
                            one_val = one_val + row.text();
                            if (tds.size()>1) {
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
                } catch (IOException e) {
                    stringBuilder.append("Error : ").append(e.getMessage()).append("\n");
                }

                /*if (allCharct.length() > 0) {
                    getActivity().runOnUiThread(() -> {
                        //Print here using all character string
                        Log.d("Game_res", allCharct);
                        String[] printData = allCharct.split("\n");
                        try {
                            if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
                                PrintUtil.printCamWinTicket(printData, getActivity(),bitmapPrint);
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_TPS570)) {
                            PrintUtil.printCamWinTicketTps(printData, getActivity(), usbThermalPrinter,bitmapPrint);
                        }
                    });
                }*/

            }
        }).start();
    }
    public interface HomeCallback{
        void callHome();
    }
    public  void callHome(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new AppWebViewClients());
        allCharct = " ";
        print_url = "";
        webView.loadUrl(BASEURL);
    }
}


