package com.skilrock.retailapp.ui.fragments.rms;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.skilrock.retailapp.sbs.ui.ActivitySbsLandScape;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.rms.HomeModuleAdapter;
import com.skilrock.retailapp.interfaces.HomeModuleListener;
import com.skilrock.retailapp.interfaces.NoPaper;
import com.skilrock.retailapp.landscape_draw_games.ui.activities.DrawGameBaseActivity;
import com.skilrock.retailapp.models.BetDeviceResponseBean;
import com.skilrock.retailapp.models.rms.ConfigurationResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.portrait_draw_games.ui.DrawGameHomeActivity;
import com.skilrock.retailapp.sle_game_portrait.ActivitySleGame;
import com.skilrock.retailapp.sle_game_portrait.BaseClassSle;
import com.skilrock.retailapp.sle_game_portrait.HttpRequest;
import com.skilrock.retailapp.sle_game_portrait.ResponseLis;
import com.skilrock.retailapp.sle_game_portrait.SleFetchDataB2C;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.utils.printer.AidlUtil;
import com.skilrock.retailapp.viewmodels.rms.HomeViewModel;
import com.skilrock.retailapp.virtual_sports.ui.VirtualSportsActivity;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends BaseFragment implements View.OnClickListener, ResponseLis, NoPaper {

    private RecyclerView rvHomeModules;
    private HomeViewModel homeViewModel;
    private RelativeLayout llAppBar;
    private ArrayList<HomeDataBean.ResponseData.ModuleBeanList> moduleBeanLists = new ArrayList<>();
    private ImageView imageLogo, ivApp;
    private final String RMS = "rms";
    private SleFetchDataB2C sleFetchDataB2C;
    private UsbThermalPrinter usbThermalPrinter = null;
    String gameData[] = null;
    Bitmap bitmap = null;
    Bundle bundle;
    private String game_url;
    private String title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        BaseClassSle.getBaseClassSle().setIs_b2c_sports(true);
        usbThermalPrinter = getUsbThermalPrinter();
        AidlUtil.getInstance().connectPrinterService(getActivity());
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            return inflater.inflate(R.layout.game_list_type_fragment_landscape, container, false);
        else {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
                return inflater.inflate(R.layout.ola_home_myanmar_fragment, container, false);
            else
                return inflater.inflate(R.layout.game_list_type_fragment, container, false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getLiveHomeData().observe(this, observer);
        homeViewModel.getLiveDataConfig().observe(this, observerConfigBean);
        homeViewModel.getLiveDataBetGame().observe(this, observerBetGame);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBarDialog.getProgressDialog().showProgress(master);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
        master.unLockDrawer();
    }

    private void initializeWidgets(View view) {
        rvHomeModules               = view.findViewById(R.id.rv_home_modules);
        imageLogo                   = view.findViewById(R.id.image_logo);
        tvUserBalance               = view.findViewById(R.id.tvBal);
        tvUsername                  = view.findViewById(R.id.tvUserName);
        llAppBar                    = view.findViewById(R.id.ll_aap_bar);
        ivApp                       = view.findViewById(R.id.ivApp);

        try {
            refreshBalance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentActivity activity = getActivity();
        if (activity != null)
            activity.setTitle(R.string.home);

        if (moduleBeanLists != null && !moduleBeanLists.isEmpty())
            setAdapter(moduleBeanLists);
        else
            callConfigDataApi();

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS)) {
            ivApp.setVisibility(View.GONE);
        } else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)){
            ivApp.setVisibility(View.GONE);
        }
    }

    HomeModuleListener homeModuleListener = (moduleCode, index, displayName, listMenuBean) -> {
        bundle = new Bundle();
        switch (moduleCode.trim()) {
            case AppConstants.DRAW_GAME:
                bundle.putInt("index", index);
                bundle.putParcelableArrayList("ListScratchGameModule", listMenuBean);
                bundle.putString("title", displayName.toUpperCase());
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    Intent intent = new Intent(getContext(), DrawGameHomeActivity.class);
                    intent.putExtra("DrawGamesModule", listMenuBean);
                    intent.putExtra("title", displayName);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                }
                else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    Intent intent = new Intent(getContext(), DrawGameBaseActivity.class);
                    intent.putExtra("DrawGamesModule", listMenuBean);
                    intent.putExtra("title", displayName);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                }
                break;

            case AppConstants.SLE:
                bundle.putInt("index", index);
                bundle.putParcelableArrayList("SportGamesModule", listMenuBean);
                bundle.putString("title", displayName.toUpperCase());
                gameList(listMenuBean);
                break;

            case AppConstants.SCRATCH:
                bundle.putInt("index", index);
                bundle.putParcelableArrayList("ListScratchGameModule", listMenuBean);
                bundle.putString("title", displayName);
                master.openFragment(new ScratchAndDrawGameFragment(), "ScratchFragment", true, bundle);
                break;

            case AppConstants.OLA:
                if (PlayerData.getInstance().getIsAffiliate().equalsIgnoreCase(AppConstants.NO))
                    removeMyPromoCode(listMenuBean);
                bundle.putInt("index", index);
                bundle.putParcelableArrayList("ListScratchGameModule", listMenuBean);
                bundle.putString("title", displayName);
                master.openFragment(new ScratchAndDrawGameFragment(), "ScratchFragment", true, bundle);
                break;

            case AppConstants.BET_GAME:
                bundle.putParcelable("MenuBean", listMenuBean.get(0));
                try {
                    Class betFragmentClazz = Class.forName("com.skilrock.fragments.BetGameFragment");
                    Fragment betGameFragment = (Fragment)betFragmentClazz.newInstance();
                    master.openFragment(betGameFragment, "BetGameFragment", true, bundle);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case AppConstants.CAMWIN247:
                gameData = null;
                bitmap   = null;
                BaseClassSle.getBaseClassSle().setNoPaper(this::noPaper);
                bundle.putString("language", Locale.getDefault().getLanguage());
                bundle.putString("gameUrl", listMenuBean.get(0).getBasePath());
                bundle.putString("title", listMenuBean.get(0).getCaption());
                game_url = listMenuBean.get(0).getBasePath();
                title = listMenuBean.get(0).getCaption();
                try {
                    Class betFragmentClazz = Class.forName("com.skilrock.fragments.ui.CamWin247GameFragment");
                    Fragment camwin = (Fragment) betFragmentClazz.newInstance();

                    master.openFragment(camwin, "CamWin247GameFragment", true, bundle);
                } catch (Exception e) {
                    Log.d("log", e.getLocalizedMessage());
                }
                break;
            case AppConstants.SBS:
                Intent intent = new Intent(getContext(), ActivitySbsLandScape.class);
                intent.putExtra("SbsModule", listMenuBean);
                intent.putExtra("language", Locale.getDefault().getLanguage());
                intent.putExtra("title", displayName);
                Objects.requireNonNull(getActivity()).startActivity(intent);
                break;
            case AppConstants.VIRTUAL_SPORTS:
                //Intent virtual_sports_intent = new Intent(getContext(), ActivityVirtualSports.class);

                Intent virtual_sports_intent = new Intent(getContext(), VirtualSportsActivity.class);
                virtual_sports_intent.putExtra("VirtualModule", listMenuBean);
                virtual_sports_intent.putExtra("title", displayName);
                Objects.requireNonNull(getActivity()).startActivity(virtual_sports_intent);
                break;
        }
    };

    private void removeMyPromoCode(@NotNull ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> listMenuBean) {

        Iterator<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> iterator = listMenuBean.iterator();
        while (iterator.hasNext()){
            HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList name = iterator.next();
            if(name.getMenuCode().equalsIgnoreCase("M_OLA_PROMO_CODE") || name.getMenuCode().equalsIgnoreCase("M_NET_GAMING_DETAILS")){
                iterator.remove();
            }
        }
    }

    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList getUrlForBetGames(ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> list) {
        for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBeanList : list) {
            if (menuBeanList.getMenuCode().equalsIgnoreCase("BET_GAME")) {
                return menuBeanList;
            }
        }
        return null;
    }

    private void callConfigDataApi(){
        try {
            String url = APIConfig.getInstance().baseUrl() + APIConfig.rmsConfigUrl;
            Integer domain = (int) PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId();

            homeViewModel.getConfigApi(url, PlayerData.getInstance().getToken(), domain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Observer<ConfigurationResponseBean> observerConfigBean = new Observer<ConfigurationResponseBean>() {
        @Override
        public void onChanged(@Nullable ConfigurationResponseBean configurationResponseBean) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (configurationResponseBean == null) {
                Utils.showCustomErrorDialogAndLogout(getContext(), getString(R.string.config_error), getString(R.string.something_went_wrong), true);
            }
            else if (configurationResponseBean.getResponseCode() == 0) {
                if (configurationResponseBean.getResponseData().getStatusCode() == 0) {
                    ConfigData.getInstance().setConfigData(master, configurationResponseBean.getResponseData().getData().get(0));
                    homeViewModel.getHomeModuleList(PlayerData.getInstance().getToken(), PlayerData.getInstance().getUserId(), master);
                    refreshBalance();
                    SharedPrefUtil.putString(master, SharedPrefUtil.CONTACT_US_NUMBER, configurationResponseBean.getResponseData().getData().get(0).getCallUsNumber());
                } else {
                    if (Utils.checkForSessionExpiry(master, configurationResponseBean.getResponseData().getStatusCode()))
                        return;
                    String errorMsg = Utils.getResponseMessage(master, RMS, configurationResponseBean.getResponseData().getStatusCode());
                    Utils.showCustomErrorDialogAndLogout(getContext(), getString(R.string.config_error), errorMsg, true);
                }
            } else {
                String errorMsg = Utils.getResponseMessage(master, RMS, configurationResponseBean.getResponseCode());
                Utils.showCustomErrorDialogAndLogout(getContext(), getString(R.string.config_error), errorMsg, true);
            }
        }
    };

    Observer<HomeDataBean> observer = homeDataBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (homeDataBean == null) {
            //for testing
            Utils.showCustomErrorDialogAndLogout(getContext(), "", getString(R.string.something_went_login_again), true);
        }
        else if (homeDataBean.getResponseCode() == 0) {
            if (homeDataBean.getResponseData().getStatusCode() == 0) {
                moduleBeanLists = homeDataBean.getResponseData().getModuleBeanLists();
                if (moduleBeanLists == null || moduleBeanLists.isEmpty()) {
                    Utils.showCustomErrorDialogAndLogout(getContext(), "", getString(R.string.some_technical_issue), true);
                }
                else {
                    boolean flag = false;
                    for (HomeDataBean.ResponseData.ModuleBeanList module: moduleBeanLists) {
                        String moduleCode = module.getModuleCode();
                        if (moduleCode.equalsIgnoreCase(AppConstants.SCRATCH) || moduleCode.equalsIgnoreCase(AppConstants.OLA) || moduleCode.equalsIgnoreCase(AppConstants.DRAW_GAME))
                            flag = true;
                    }

                    if (flag) {
                        removeNonGameModules(moduleBeanLists);
                        checkForSingleMenu(moduleBeanLists);
                    }
                    else {
                        Utils.showCustomErrorDialogAndLogout(getContext(), "", getString(R.string.some_technical_issue), true);
                    }
                }
            } else {
                if (homeDataBean.getResponseData().getStatusCode() == 3021) {
                    String errorMsg = Utils.getResponseMessage(master, RMS, homeDataBean.getResponseData().getStatusCode());
                    Utils.showCustomErrorDialogAndLogout(getContext(), "", errorMsg, true);
                }
                else {
                    if (Utils.checkForSessionExpiry(master, homeDataBean.getResponseData().getStatusCode()))
                        return;
                    String errorMsg = Utils.getResponseMessage(master, RMS, homeDataBean.getResponseData().getStatusCode());
                    //Utils.showCustomErrorDialog(getContext(), "", errorMsg);
                    Utils.showCustomErrorDialogAndLogout(getContext(), "", errorMsg, true);
                }
            }
        } else {
            //String errorMsg = TextUtils.isEmpty(homeDataBean.getResponseMessage()) ? getString(R.string.some_internal_error) : homeDataBean.getResponseMessage();
            String errorMsg = Utils.getResponseMessage(master, RMS, homeDataBean.getResponseCode());
            Utils.showCustomErrorDialog(getContext(), "", errorMsg);
        }
    };

    Observer<BetDeviceResponseBean> observerBetGame = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (response == null) {
            Utils.showCustomErrorDialogAndLogout(getContext(), "", getString(R.string.something_went_wrong), true);
        }
        else if (response.getResponseCode() == 0) {
            if (response.getResponseData().getStatusCode() == 0) {
                String url      = response.getResponseData().getData();
                String title    = response.getDisplayName();

                Bundle bundle=new Bundle();
                bundle.putString("gameUrl", url);
                bundle.putString("title", title);
                try {
                    Class betFragmentClazz = Class.forName("com.skilrock.fragments.BetGameFragment");
                    Fragment betGameFragment = (Fragment)betFragmentClazz.newInstance();
                    master.openFragment(betGameFragment, "BetGameFragment", true, bundle);
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                Utils.showRedToast(master, getString(R.string .some_technical_issue));
            }
        }
        else {
            String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
            Utils.showCustomErrorDialog(getContext(), "", errorMsg);
        }

    };

    private void removeNonGameModules(List<HomeDataBean.ResponseData.ModuleBeanList> moduleBeanLists) {
        List<HomeDataBean.ResponseData.ModuleBeanList> drawerList = new ArrayList<>();
        if (moduleBeanLists != null && moduleBeanLists.size() > 0) {
            for (int index = 0; index < moduleBeanLists.size(); index++) {
                if (moduleBeanLists.size() > 0 && moduleBeanLists.get(index).getModuleCode().trim().equalsIgnoreCase(AppConstants.USERS))
                    drawerList.add(moduleBeanLists.get(index));
                if (moduleBeanLists.size() > 0 && moduleBeanLists.get(index).getModuleCode().trim().equalsIgnoreCase(AppConstants.REPORTS))
                    drawerList.add(moduleBeanLists.get(index));
            }

            for (int index = 0; index < drawerList.size(); index++) {
                if (drawerList.size() > 0 && drawerList.get(index).getModuleCode().trim().equalsIgnoreCase(AppConstants.USERS))
                    moduleBeanLists.remove(drawerList.get(index));
                if (drawerList.size() > 0 && drawerList.get(index).getModuleCode().trim().equalsIgnoreCase(AppConstants.REPORTS))
                    moduleBeanLists.remove(drawerList.get(index));
            }
        }

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS) && PlayerData.getInstance().getLoginData().getResponseData().getData().getQrCode() != null) {
            for (int index = 0; index < drawerList.size(); index++) {
                if (drawerList.size() > 0 && drawerList.get(index).getModuleCode().trim().equalsIgnoreCase(AppConstants.USERS)) {
                    ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> menuList = drawerList.get(index).getMenuBeanList();
                    for (int i = 0; i < menuList.size(); i++) {
                        if (menuList.get(i).getMenuCode().equalsIgnoreCase(AppConstants.QR_CODE_REGISTRATION)) {
                            HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menu = menuList.get(i);
                            menu.setMenuCode(AppConstants.NATIVE_DISPLAY_QR);
                            menu.setCaption(getString(R.string.display_qr));
                            menu.setMenuId(AppConstants.MENU_ID_DISPLAY_QR);
                        }
                    }
                }
            }
        }

        if (getActivity() != null && drawerList.size() > 0)
            ((MainActivity) getActivity()).setDrawerElements(drawerList);
    }

    @Override
    public void onClick(View view) {

    }

    private void checkForSingleMenu(ArrayList<HomeDataBean.ResponseData.ModuleBeanList> moduleBeanLists) {
        try {
            setAdapter(moduleBeanLists);
            if (moduleBeanLists.size() == 1) {
                llAppBar.setVisibility(View.INVISIBLE);
                rvHomeModules.setVisibility(View.INVISIBLE);
                SharedPrefUtil.putBoolean(Objects.requireNonNull(getContext()), SharedPrefUtil.SHOW_HOME_SCREEN, false);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("ListScratchGameModule", moduleBeanLists.get(0).getMenuBeanList());
                bundle.putString("title", moduleBeanLists.get(0).getDisplayName());
                bundle.putBoolean("isSingleMenu", true);
                //Objects.requireNonNull(getFragmentManager()).popBackStack();
                master.openFragment(new ScratchAndDrawGameFragment(), "ScratchFragment", true, bundle);
            } else {
                setAdapter(moduleBeanLists);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(ArrayList<HomeDataBean.ResponseData.ModuleBeanList> moduleBeanLists) {
        llAppBar.setVisibility(View.VISIBLE);
        rvHomeModules.setHasFixedSize(true);
        if(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            rvHomeModules.setLayoutManager(new GridLayoutManager(master, 4));
        else
            rvHomeModules.setLayoutManager(new GridLayoutManager(master, 2));
        rvHomeModules.setVisibility(View.VISIBLE);
        HomeModuleAdapter homeModuleAdapter = new HomeModuleAdapter(getContext(), moduleBeanLists, homeModuleListener);
        rvHomeModules.setAdapter(homeModuleAdapter);
    }

    @Override
    public void onResponse(String response, String requestedMethod) {
/*
        response="{\n" +
                "  \"responseMsg\": \"success\",\n" +
                "  \"responseCode\": 0,\n" +
                "  \"sleData\": {\n" +
                "    \"gameData\": [\n" +
                "      {\n" +
                "        \"gameId\": 1,\n" +
                "        \"gameDevname\": \"SOCCER\",\n" +
                "        \"gameDisplayName\": \"Soccer\",\n" +
                "        \"tktThresholdAmt\": 100,\n" +
                "        \"tktMaxAmt\": \"15000.00\",\n" +
                "        \"minEventCount\": 1,\n" +
                "        \"maxEventCount\": 1,\n" +
                "        \"gameTypeData\": [\n" +
                "          {\n" +
                "            \"gameTypeId\": 1,\n" +
                "            \"gameTypeDevName\": \"soccer13\",\n" +
                "            \"gameTypeDisplayName\": \"Soccer 13\",\n" +
                "            \"gameTypeUnitPrice\": 300,\n" +
                "            \"gameTypeMaxBetAmtMultiple\": 50,\n" +
                "            \"eventType\": \"\",\n" +
                "            \"eventSelectionType\": \"MULTIPLE\",\n" +
                "            \"upcomingDrawStartTime\": \"\",\n" +
                "            \"areEventsMappedForUpcomingDraw\": false,\n" +
                "            \"drawData\": [\n" +
                "              \n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ],\n" +
                "    \"currentTime\": \"24-02-2020 06:44:21\"\n" +
                "  }\n" +
                "}";
*/
        ProgressBarDialog.getProgressDialog().dismiss();
        JSONObject jsonObject;
        if (response == null || (response != null && response.equalsIgnoreCase("Failed"))) {
            Toast.makeText(getContext(), getString(R.string.some_technical_issue), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject != null && jsonObject.has("responseCode") && jsonObject.optInt("responseCode") != 0) {

                String errorMsg = Utils.getResponseMessage(getActivity(), "sle", jsonObject.optInt("responseCode"));
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("errorCode")) {
                if (jsonObject.has("errorCode") && Utils.checkForSessionExpiry(master, jsonObject.optInt("errorCode")))
                    return;
                //Toast.makeText(master, jsonObject.optString("errorMsg"), Toast.LENGTH_LONG).show();
                String errorMsg = Utils.getResponseMessage(getActivity(), "sle", jsonObject.optInt("errorCode"));
                Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(master, ActivitySleGame.class);
        intent.putExtra("title", "Sports Lottery");
        intent.putExtra("bean", response);
        startActivity(intent);
    }

    private void gameList(ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> listMenuBean){
        HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList SELECTED_BEAN = null;
        for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList model: listMenuBean) {
            if (model.getMenuCode().equalsIgnoreCase("SLE_19")) {
                SELECTED_BEAN = model;
                break;
            }
        }
        BaseClassSle.getBaseClassSle().setIs_b2c_sports(false);

        if (SELECTED_BEAN != null) {
            //fetchGames    //sale  //reprint   //cancel    //verifyTicket  //claimTicket   //matchList     //resultList
            BaseClassSle.getBaseClassSle().setBASE_URL(SELECTED_BEAN.getBasePath());
            BaseClassSle.getBaseClassSle().setFetchBean(Utils.getSleGameUrlDetails(SELECTED_BEAN, master, "fetchGames"));
            BaseClassSle.getBaseClassSle().setSalBean(Utils.getSleGameUrlDetails(SELECTED_BEAN, master, "sale"));
            BaseClassSle.getBaseClassSle().setReprintBean(Utils.getSleGameUrlDetails(SELECTED_BEAN, master, "reprint"));
            BaseClassSle.getBaseClassSle().setCancelBean(Utils.getSleGameUrlDetails(SELECTED_BEAN, master, "cancel"));
            BaseClassSle.getBaseClassSle().setVerifyBean(Utils.getSleGameUrlDetails(SELECTED_BEAN, master, "verifyTicket"));
            BaseClassSle.getBaseClassSle().setClaimBean(Utils.getSleGameUrlDetails(SELECTED_BEAN, master, "claimTicket"));
            BaseClassSle.getBaseClassSle().setResultBean(Utils.getSleGameUrlDetails(SELECTED_BEAN, master, "resultList"));

            String headerData1 = "userName,"+BaseClassSle.getBaseClassSle().getFetchBean().getUserName()+"|password,"+BaseClassSle.getBaseClassSle().getFetchBean().getPassword()+"|Content-Type,"+BaseClassSle.getBaseClassSle().getFetchBean().getContentType();
            String headerData = "userName,"+BaseClassSle.getBaseClassSle().getFetchBean().getUserName()+"|password,"+BaseClassSle.getBaseClassSle().getFetchBean().getPassword()+"|Content-Type,"+BaseClassSle.getBaseClassSle().getFetchBean().getContentType();
            ProgressBarDialog.getProgressDialog().showProgress(getActivity());
            JSONObject jsonObject = null;
            if (BaseClassSle.getBaseClassSle().isIs_b2c_sports()) {
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("merchantCode", "Weaver");
                    jsonObject.put("listType", "dayWise");
                    jsonObject.put("fromDate", "2015-07-09");
                    jsonObject.put("toDate", "2019-12-16");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("userName", PlayerData.getInstance().getUsername());
                    jsonObject.put("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
                    jsonObject.put("interfaceType", AppConstants.INTERFACE_TYPE);
                    jsonObject.put("merchantCode", "NEWRMS");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            HttpRequest httpRequest = new HttpRequest();
            if (!BaseClassSle.getBaseClassSle().isIs_b2c_sports()) {
                try {
                    httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL()+BaseClassSle.getBaseClassSle().getFetchBean().getUrl()+"?requestData=" + URLEncoder.encode(jsonObject.toString(), "UTF-8"), this, "loading", master, "sele", headerData1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL()+BaseClassSle.getBaseClassSle().getFetchBean().getUrl(), this, "loading", master, "sele", headerData);
                httpRequest.setIsParams(true, jsonObject.toString());
            }


            httpRequest.executeTask();
        }
        else Toast.makeText(master, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
    }
    public UsbThermalPrinter getUsbThermalPrinter() {
        if (usbThermalPrinter == null) {
            usbThermalPrinter = new UsbThermalPrinter(getActivity());
        }
        return usbThermalPrinter;
    }


    /*public void clearBackStack() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(this);
        trans.commit();
        manager.popBackStack();
    }*/

    /*public void updateBalance() {
        tvUserBalance.setText(PlayerData.getInstance().getOrgBalance());
    }*/
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
            gameData = intent.getStringArrayExtra("data");
            bitmap = (Bitmap) intent.getParcelableExtra("bitmap");
            if (gameData != null) {
                if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI)) {
                    try {
                        printSunmi();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_TPS570)) {
                    printTelpo();
                }

            }
        }

    };

    private void printSunmi() {
        try {
            if (gameData != null && bitmap != null)
                PrintUtil.printCamWinTicket(gameData, getActivity(), bitmap, this);
            else
                Utils.showCustomErrorDialog(getActivity(), getString(R.string.camwin), getString(R.string.something_went_wrong));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void printTelpo() {
        if (gameData != null && bitmap != null)
            PrintUtil.printCamWinTicketTps(gameData, getActivity(), usbThermalPrinter, bitmap, this);
        else
            Utils.showCustomErrorDialog(getActivity(), getString(R.string.camwin), getString(R.string.something_went_wrong));


    }


    @Override
    public void noPaper(String msg) {
        if (msg.equalsIgnoreCase("SUCCESS")) {
            gameData = null;
            bitmap   = null;

            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("CamwinData"));
            BaseClassSle.getBaseClassSle().setNoPaper(this::noPaper);
            bundle.putString("language", Locale.getDefault().getLanguage());
            bundle.putString("gameUrl", game_url);
            bundle.putString("title", title);
            try {
                Class betFragmentClazz = Class.forName("com.skilrock.fragments.ui.CamWin247GameFragment");
                Fragment camwin = (Fragment) betFragmentClazz.newInstance();

                master.openFragment(camwin, "CamWin247GameFragment", true, bundle);
            } catch (Exception e) {
                Log.d("log", e.getLocalizedMessage());
            }
        } else {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(getActivity(), getString(R.string.camwin), errorMsg);
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("CamwinData"));
        }

    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("CamwinData"));
        super.onResume();
    }
}