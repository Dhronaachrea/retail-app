package com.skilrock.retailapp.ui.fragments.fieldx;

import androidx.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.FieldX.TabAdapterForFieldx;
import com.skilrock.retailapp.interfaces.HomeModuleListener;
import com.skilrock.retailapp.models.FieldX.AllTaskItem;
import com.skilrock.retailapp.models.FieldX.RetailerLocationBean;
import com.skilrock.retailapp.models.rms.ConfigurationResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.portrait_draw_games.ui.DrawGameHomeActivity;
import com.skilrock.retailapp.sle_game_portrait.ActivitySleGame;
import com.skilrock.retailapp.sle_game_portrait.HttpRequest;
import com.skilrock.retailapp.sle_game_portrait.ResponseLis;
import com.skilrock.retailapp.ui.activities.MainActivity;
import com.skilrock.retailapp.ui.fragments.rms.ScratchAndDrawGameFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FieldXHomeFragment extends BaseFragmentFieldx implements View.OnClickListener, ResponseLis {

    private static HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBeanScratch;
    public static ArrayList<AllTaskItem> delivery = new ArrayList<>();
    public static ArrayList<AllTaskItem> pickup = new ArrayList<>();
    public static ArrayList<AllTaskItem> payment = new ArrayList<>();
    private final String RMS = "rms";
    //private HomeViewModel homeViewModel;
    Observer<ConfigurationResponseBean> observerConfigBean = new Observer<ConfigurationResponseBean>() {
        @Override
        public void onChanged(@Nullable ConfigurationResponseBean configurationResponseBean) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (configurationResponseBean == null) {
                Utils.showCustomErrorDialogAndLogout(getContext(), getString(R.string.config_error), getString(R.string.something_went_wrong), true);
            } else if (configurationResponseBean.getResponseCode() == 0) {
                if (configurationResponseBean.getResponseData().getStatusCode() == 0) {
                    ConfigData.getInstance().setConfigData(master, configurationResponseBean.getResponseData().getData().get(0));
                    refreshBalance();
                    SharedPrefUtil.putString(master, SharedPrefUtil.CONTACT_US_NUMBER, configurationResponseBean.getResponseData().getData().get(0).getCallUsNumber());
                    homeViewModel.getHomeModuleList(PlayerData.getInstance().getToken(), PlayerData.getInstance().getUserId(), master);
//                    ProgressBarDialog.getProgressDialog().showProgress(getActivity());
//                   getFieldExOrganizations(getActivity());
                   /* rvHomeModules.setVisibility(View.GONE);
                    alltask.setVisibility(View.VISIBLE);
                    AllTaskFragment allTask=new AllTaskFragment();
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.all_task,allTask).addToBackStack(null).commit();
*/
                } else {
                    if (Utils.checkForSessionExpiryFieldX(master, configurationResponseBean.getResponseData().getStatusCode())) {
                        ProgressBarDialog.getProgressDialog().dismiss();
                        return;
                    }
                    String errorMsg = Utils.getResponseMessage(master, RMS, configurationResponseBean.getResponseData().getStatusCode());
                    Utils.showCustomErrorDialogAndLogout(getContext(), getString(R.string.config_error), errorMsg, true);
                }
            } else {
                String errorMsg = Utils.getResponseMessage(master, RMS, configurationResponseBean.getResponseCode());
                Utils.showCustomErrorDialogAndLogout(getContext(), getString(R.string.config_error), errorMsg, true);
            }
        }
    };

    private RelativeLayout llAppBar;
    private ArrayList<HomeDataBean.ResponseData.ModuleBeanList> moduleBeanLists = new ArrayList<>();

    public ArrayList<HomeDataBean.ResponseData.ModuleBeanList> getModuleBeanLists() {
        return moduleBeanLists;
    }

    public static HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList getMenuBean() {
        return menuBeanScratch;
    }

    HomeModuleListener homeModuleListener = (moduleCode, index, displayName, listMenuBean) -> {
        Bundle bundle = new Bundle();
        switch (moduleCode.trim()) {
            case AppConstants.DRAW_GAME:
                bundle.putInt("index", index);
                bundle.putParcelableArrayList("ListScratchGameModule", listMenuBean);
                bundle.putString("title", displayName.toUpperCase());
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(getContext(), DrawGameHomeActivity.class);
                    intent.putExtra("DrawGamesModule", listMenuBean);
                    intent.putExtra("title", displayName);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    master.openFragment(new ScratchAndDrawGameFragment(), "ScratchFragment", true, bundle);
                }
                break;

            case AppConstants.SLE:
                bundle.putInt("index", index);
                bundle.putParcelableArrayList("SportGamesModule", listMenuBean);
                bundle.putString("title", displayName.toUpperCase());
                gameList();
                break;

            case AppConstants.SCRATCH:
                bundle.putInt("index", index);
                bundle.putParcelableArrayList("ListScratchGameModule", listMenuBean);
                bundle.putString("title", displayName);
                master.openFragment(new ScratchAndDrawGameFragment(), "ScratchFragment", true, bundle);
                break;

            case AppConstants.OLA:
                bundle.putInt("index", index);
                /*bundle.putParcelableArrayList("ListOlaModule", listMenuBean);
                bundle.putString("title", displayName.toUpperCase());
                master.openFragment(new OlaMenuFragment(), "OlaMenuFragment", true, bundle);*/

                bundle.putParcelableArrayList("ListScratchGameModule", listMenuBean);
                bundle.putString("title", moduleBeanLists.get(0).getDisplayName());
                master.openFragment(new ScratchAndDrawGameFragment(), "ScratchFragment", true, bundle);
                break;

            case AppConstants.BET_GAME:
                /*bundle.putInt("index", index);
                bundle.putString("title", listMenuBean.get(0).getCaption());
                bundle.putParcelable("MenuBean", listMenuBean.get(0));
                master.openFragment(new BetGameFragment(), "BetGameFragment", true, bundle);*/
                break;
        }
    };
    Observer<HomeDataBean> observer = homeDataBean -> {
        ProgressBarDialog.getProgressDialog().dismiss();

        if (homeDataBean == null) {
            Utils.showCustomErrorDialogAndLogout(getContext(), "", getString(R.string.something_went_login_again), true);
        } else if (homeDataBean.getResponseCode() == 0) {
            if (homeDataBean.getResponseData().getStatusCode() == 0) {
                moduleBeanLists = homeDataBean.getResponseData().getModuleBeanLists();
                if (moduleBeanLists == null || moduleBeanLists.isEmpty()) {
                    Utils.showCustomErrorDialogAndLogout(getContext(), "", getString(R.string.some_technical_issue), true);
                } else {
                    boolean flag = false;
                    HomeDataBean.ResponseData.ModuleBeanList b = null;
                    for (HomeDataBean.ResponseData.ModuleBeanList obj : moduleBeanLists)
                        if (obj.getModuleCode().equalsIgnoreCase("SCRATCH")) {
                            b = obj;
                            break;
                        }
                    if (b == null)
                        return;

                    for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList model : b.getMenuBeanList()) {
                        if (model.getMenuCode().equalsIgnoreCase("F_RETURN_BOOK")) {
                            menuBeanScratch = model;
                            break;
                        }
                    }
                    b = null;
                    for (HomeDataBean.ResponseData.ModuleBeanList obj : moduleBeanLists) {
                        if (obj.getModuleCode().equalsIgnoreCase("FIELDEX")) {
                            b = obj;
                            break;
                        }

                    }
                    if (b == null)
                        return;
                    for (HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList model : b.getMenuBeanList()) {
                        if (model.getMenuCode().equalsIgnoreCase("FIELDEX_HOME")) {
                            menuBeanFieldx = model;
                            break;
                        }
                    }
                    getFieldxRetailerData();
                    //menuBean = b.getMenuBeanList().get(2);
                    for (HomeDataBean.ResponseData.ModuleBeanList module : moduleBeanLists) {
                        String moduleCode = module.getModuleCode();
                        if (moduleCode.equalsIgnoreCase(AppConstants.SCRATCH) || moduleCode.equalsIgnoreCase(AppConstants.OLA) || moduleCode.equalsIgnoreCase(AppConstants.DRAW_GAME) || moduleCode.equalsIgnoreCase(AppConstants.FIELDEX))
                            flag = true;
                    }

                    if (flag) {
                        removeNonGameModules(moduleBeanLists);
                        checkForSingleMenu(moduleBeanLists);
                    } else {
                        Utils.showCustomErrorDialogAndLogout(getContext(), "", getString(R.string.some_technical_issue), true);
                    }
                }
            } else {
                if (homeDataBean.getResponseData().getStatusCode() == 3021) {
                    String errorMsg = Utils.getResponseMessage(master, RMS, homeDataBean.getResponseData().getStatusCode());
                    Utils.showCustomErrorDialogAndLogout(getContext(), "", errorMsg, true);
                } else {
                    if (Utils.checkForSessionExpiryFieldX(master, homeDataBean.getResponseData().getStatusCode()))
                        return;
                    //String errorMsg = Utils.getResponseMessage(master, RMS, homeDataBean.getResponseData().getStatusCode());
                    //Utils.showCustomErrorDialog(getContext(), "", errorMsg);
                    Utils.showCustomErrorDialogAndLogout(getContext(), "", /*errorMsg*/getString(R.string.unauthorized_user_msg), true);
                }
            }
        } else {
            //String errorMsg = TextUtils.isEmpty(homeDataBean.getResponseMessage()) ? getString(R.string.some_internal_error) : homeDataBean.getResponseMessage();
            String errorMsg = Utils.getResponseMessage(master, RMS, homeDataBean.getResponseCode());
            Utils.showCustomErrorDialog(getContext(), "", errorMsg);
        }
    };
    private ImageView imageLogo;
    private FrameLayout alltask;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(master, getActivity().getString(R.string.exit_toast_msg), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fiedx_home_layout, container, false);
        clearRetailerData();
        return view;
    }

    private void clearRetailerData() {
        allTask.clear();
        challans.clear();
        delivery.clear();
        pickup.clear();
        payment.clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getLiveHomeData().observe(this, observer);
        homeViewModel.getLiveDataConfig().observe(this, observerConfigBean);
    }

    private void gameList() {
        String headerData1 = "userName,E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6|password,p@55w0rd|Content-Type,application/x-www-form-urlencoded";
        ProgressBarDialog.getProgressDialog().showProgress(getActivity());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("merchantCode", "Weaver");
            jsonObject.put("listType", "dayWise");
            jsonObject.put("fromDate", "2015-07-09");
            jsonObject.put("toDate", "2019-12-16");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject();
            jsonObject.put("userName", PlayerData.getInstance().getUsername());
            jsonObject.put("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
            jsonObject.put("interfaceType", "WEB");
            jsonObject.put("merchantCode", "NEWRMS");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setHttpRequestMethod("GET");
        try {
            httpRequest.setAllParameter("http://192.168.124.52:8082/SportsLottery/com/skilrock/sle/web/merchantUser/drawMgmt/Action/fetchSLEDrawData.action?requestData=" + URLEncoder.encode(jsonObject.toString(), "UTF-8"),
                    this, "loading", master, "sele", headerData1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequest.executeTask();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBarDialog.getProgressDialog().showProgress(getActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("FXHome", "onview Created");
        initializeWidgets(view);
        master.unLockDrawer();
    }

    public void filterFieldxRetailerData() {
        if (allTask.isEmpty()) return;
        delivery.clear();
        pickup.clear();
        payment.clear();

        allTask = sort(allTask, master.currentLocation);

        for (int i = 0; i < allTask.size(); i++) {
            AllTaskItem data = allTask.get(i);
            if (data.isDelivery() == View.VISIBLE)
                delivery.add(data);
            if (data.isPickup() == View.VISIBLE)
                pickup.add(data);
            if (data.isCollect() == View.VISIBLE)
                payment.add(data);
        }

        new AllTaskFragment().notifyAllTaskRetailer();
        new Delivery().notifyRetailer();
        //new PaymentFragment().notifyRetailer();
        new PickupFragment().notifyRetailer();
    }

    private ArrayList<AllTaskItem> sort(ArrayList<AllTaskItem> alltask, Location userLocation) {
        ArrayList<AllTaskItem> retailerWithTask = new ArrayList<>();
        ArrayList<AllTaskItem> retailerWithoutTask = new ArrayList<>();
        for (AllTaskItem item : alltask)
            if (item.isPickup() == View.VISIBLE || item.isDelivery() == View.VISIBLE || item.isCollect() == View.VISIBLE)
                retailerWithTask.add(item);
            else
                retailerWithoutTask.add(item);

        /*  setRetailerLocation(alltask);

      if (!SharedPrefUtil.getString(getActivity(), "LastLocation").isEmpty()) {
            setRetailerDistance(alltask);

            Collections.sort(retailerWithTask, new Comparator<AllTaskItem>() {
                @Override
                public int compare(AllTaskItem t1, AllTaskItem t2) {
                    return t1.getDistnace().compareTo(t2.getDistnace());
                }
            });
        }
*/
        alltask = sortByCollection(retailerWithTask);

        for (AllTaskItem item : retailerWithoutTask)
            alltask.add(item);
        return alltask;
    }

    private void setRetailerDistance(ArrayList<AllTaskItem> alltask){
        for (AllTaskItem item : alltask){
            item.setDistnace((double)getLocation(SharedPrefUtil.getString(getActivity(), "LastLocation"))
                            .distanceTo(SharedPrefUtil.getRetailerLocation(getActivity(), item.getOrgId())));
        }
    }

    private ArrayList<AllTaskItem> sortByCollection(ArrayList<AllTaskItem> alltask) {
        ArrayList<AllTaskItem> sortedArray = new ArrayList<>();
        ArrayList<AllTaskItem> nonCollectionArray = new ArrayList<>();
        for (AllTaskItem item : alltask) {
            if (item.isCollect() == View.VISIBLE) {
                sortedArray.add(item);
            } else
                nonCollectionArray.add(item);
        }
        for (AllTaskItem data : nonCollectionArray)
            sortedArray.add(data);
        return sortedArray;
    }

    private Location getRetailerLoation(String address) {
        Geocoder geocoder = new Geocoder(master);
        List<Address> add;
        try {
            add = geocoder.getFromLocationName(address, 5);
            if (add == null)
                return null;
            Location location = new Location("retailer location");
            location.setLongitude(add.get(0).getLongitude());
            location.setLatitude(add.get(0).getLatitude());
            return location;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setRetailerLocation(ArrayList<AllTaskItem> alltask) {
        ProgressBarDialog.getProgressDialog().showProgressWithText(getActivity(), "Sorting Data..");
        if (SharedPrefUtil.getRetailerLocationArray(getActivity()) != null) {
            //item.setDistnace((double) master.currentLocation.distanceTo(getRetailerLoation(item.getAddress())));
            ArrayList<RetailerLocationBean> arrayList = SharedPrefUtil.getRetailerLocationArray(getActivity());

            for (AllTaskItem item : alltask) {

                if (!SharedPrefUtil.checkIfRetailerExists(item.getOrgId(), arrayList))
                    SharedPrefUtil.addRetailerLocation(getActivity(),
                            item.getOrgId(), getRetailerLoation(item.getFullAddress()).getLatitude(),
                            getRetailerLoation(item.getFullAddress()).getLongitude());
            }
        } else {
            ArrayList<RetailerLocationBean> arrayListLoc = new ArrayList<>();

            for (AllTaskItem item : alltask) {
                RetailerLocationBean retailerLocationBean = new RetailerLocationBean();
                retailerLocationBean.setId(item.getOrgId());
                retailerLocationBean.setLat(getRetailerLoation(item.getFullAddress()).getLatitude());
                retailerLocationBean.setLon(getRetailerLoation(item.getFullAddress()).getLongitude());

                arrayListLoc.add(retailerLocationBean);
            }

            SharedPrefUtil.setRetailerLocation(getActivity(), arrayListLoc);
        }
        ProgressBarDialog.getProgressDialog().dismiss();
    }
    private void initializeWidgets(View view) {
        alltask = view.findViewById(R.id.all_task);
        //rvHomeModules               = view.findViewById(R.id.rv_home_modules);
        //tvUserBalance               = view.findViewById(R.id.tvUserBalance);
        imageLogo = view.findViewById(R.id.image_logo);
        tvUserBalance = view.findViewById(R.id.tvBal);
        tvUsername = view.findViewById(R.id.tvUserName);
        llAppBar = view.findViewById(R.id.ll_aap_bar);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvBal);
        //Bundle bundle=getArguments();
//        menuBean = bundle.getParcelable("MenuBean");
        /*tvBalance.setText(PlayerData.getInstance().getOrgBalance());
        tvUserName.setText(PlayerData.getInstance().getUsername());*/
        //tvUsername                  = view.findViewById(R.id.tvUserName);
        //imageLogo.setImageDrawable(Utils.getLogo(master, BuildConfig.app_name));

        FragmentActivity activity = getActivity();
        if (activity != null)
            activity.setTitle(R.string.home);

        if (moduleBeanLists != null && !moduleBeanLists.isEmpty()) {
            getFieldxRetailerData();
            try {
                refreshBalance();
            } catch (Exception e) {
                refreshBalance();
            }
//            ProgressBarDialog.getProgressDialog().showProgress(getActivity());
//            getFieldExOrganizations(getActivity());
            // setAdapter(moduleBeanLists);
        } else
            callConfigDataApi();
        refreshBalance();
        tabLayout.addTab(tabLayout.newTab().setText("ALL TASK"));
        tabLayout.addTab(tabLayout.newTab().setText("B"));
        tabLayout.addTab(tabLayout.newTab().setText("C"));
        //tabLayout.addTab(tabLayout.newTab().setText("PAYMENT"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setAdapter(new TabAdapterForFieldx(getChildFragmentManager(), tabLayout.getTabCount(), getResources().getStringArray(R.array.mFragmentTitleList)));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//    private void openHomeForFieldX() {
//        SharedPrefUtil.putBoolean(Objects.requireNonNull(getContext()), SharedPrefUtil.SHOW_HOME_SCREEN, false);
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("ListScratchGameModule", moduleBeanLists.get(0).getMenuBeanList());
//        bundle.putParcelable("MenuBean", moduleBeanLists.get(0).getMenuBeanList().get(0));
//
//        master.openFragment(new AllTaskFragment(), "alltask", true, bundle);
//    }

    private void callConfigDataApi() {
        try {
            String url = APIConfig.getInstance().baseUrl() + APIConfig.rmsConfigUrl;
            Integer domain = (int) PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId();

            homeViewModel.getConfigApi(url, PlayerData.getInstance().getToken(), domain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeNonGameModules(List<HomeDataBean.ResponseData.ModuleBeanList> moduleBeanLists) {
        List<HomeDataBean.ResponseData.ModuleBeanList> drawerList = new ArrayList<>();
        if (moduleBeanLists != null && moduleBeanLists.size() > 0) {
            for (int index = 0; index < moduleBeanLists.size(); index++) {
                if (moduleBeanLists.size() > 0 && moduleBeanLists.get(index).getModuleCode().trim().equalsIgnoreCase(AppConstants.USERS)) {
                    drawerList.add(moduleBeanLists.get(index));
                }
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

        drawerList = addAllRetailerMenu(drawerList);

        if (getActivity() != null && drawerList.size() > 0)
            ((MainActivity) getActivity()).setDrawerElements(drawerList);
    }

    private List<HomeDataBean.ResponseData.ModuleBeanList> addAllRetailerMenu(List<HomeDataBean.ResponseData.ModuleBeanList> drawerList) {
        for (int index = 0; index < drawerList.size(); index++) {

            if (drawerList.get(index).getModuleCode().trim().equalsIgnoreCase(AppConstants.USERS)) {

                HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBeanList = new HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList();
                menuBeanList.setApiDetails(menuBeanFieldx.getApiDetails());
                menuBeanList.setBasePath(menuBeanFieldx.getBasePath());
                menuBeanList.setCaption(getString(R.string.all_retailers));
                menuBeanList.setMenuCode(AppConstants.ALL_RETAILERS);
                menuBeanList.setMenuId(AppConstants.MENU_ID_ALL_RETAILER);

                ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> dataMenu =
                        drawerList.get(index).getMenuBeanList();
                dataMenu.add(0 , menuBeanList);

                drawerList.get(index).setMenuBeanList(dataMenu);
            }
        }

        return drawerList;
    }

    @Override
    public void onClick(View view) {

    }

    private void checkForSingleMenu(ArrayList<HomeDataBean.ResponseData.ModuleBeanList> moduleBeanLists) {
        try {
            if (moduleBeanLists.size() == 1) {
                //llAppBar.setVisibility(View.INVISIBLE);
                //  rvHomeModules.setVisibility(View.INVISIBLE);
                SharedPrefUtil.putBoolean(Objects.requireNonNull(getContext()), SharedPrefUtil.SHOW_HOME_SCREEN, false);

//                ProgressBarDialog.getProgressDialog().showProgress(getActivity());
//                getFieldExOrganizations(getActivity());
                //openHomeForFieldX();
//                AllTaskFragment allTask=new AllTaskFragment();
//                allTask.setArguments(bundle);
//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.content_frame, allTask).addToBackStack(null).commit();

                //Objects.requireNonNull(getFragmentManager()).popBackStack();
                //master.openFragment(new ScratchAndDrawGameFragment(), "ScratchFragment", true, bundle);
            } else {
//                ProgressBarDialog.getProgressDialog().showProgress(getActivity());
//                getFieldExOrganizations(getActivity());
                // setAdapter(moduleBeanLists);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResponse(String response, String requestedMethod) {
        ProgressBarDialog.getProgressDialog().dismiss();
        JSONObject jsonObject;
        if (response == null || (response != null && response.equalsIgnoreCase("Failed"))) {
            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject != null && jsonObject.has("responseCode") && jsonObject.optInt("responseCode") != 0) {
                Toast.makeText(getContext(), jsonObject.optString("responseMsg"), Toast.LENGTH_LONG).show();
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("errorCode")) {
                if (jsonObject.has("errorCode") && Utils.checkForSessionExpiryFieldX(master, jsonObject.optInt("errorCode")))
                    return;
                Toast.makeText(master, jsonObject.optString("errorMsg"), Toast.LENGTH_LONG).show();
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

    public Location getLocation(String loc){
        Location object = new Location("service Provider");
        object.setLatitude(Double.parseDouble(loc.split(",")[0]));
        object.setLongitude(Double.parseDouble(loc.split(",")[1]));

        return object;
    }
}
