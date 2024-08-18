package com.skilrock.retailapp.ui.activities;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.dialog.InternetSpeedDialog;
import com.skilrock.retailapp.interfaces.EventListener;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.ui.activities.rms.LoginActivity;
import com.skilrock.retailapp.ui.activities.rms.LoginActivityFieldX;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.ui.fragments.fieldx.BaseFragmentFieldx;
import com.skilrock.retailapp.ui.fragments.fieldx.FieldXAllRetailerFragment;
import com.skilrock.retailapp.ui.fragments.fieldx.FieldXCollectionReport;
import com.skilrock.retailapp.ui.fragments.fieldx.FieldXHomeFragment;
import com.skilrock.retailapp.ui.fragments.rms.AccountSettlementFragment;
import com.skilrock.retailapp.ui.fragments.rms.BalanceReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.BillReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.CashManagementFragment;
import com.skilrock.retailapp.ui.fragments.rms.ChangePasswordFragment;
import com.skilrock.retailapp.ui.fragments.rms.DeviceRegistrationFragment;
import com.skilrock.retailapp.ui.fragments.rms.DisplayQRFragment;
import com.skilrock.retailapp.ui.fragments.rms.HomeFragment;
import com.skilrock.retailapp.ui.fragments.rms.LedgerReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.OlaReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.OperationalReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.PaymentReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.QrCodeRegistrationFragment;
import com.skilrock.retailapp.ui.fragments.rms.SaleWinningReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.ScratchAndDrawGameFragment;
import com.skilrock.retailapp.ui.fragments.rms.SearchUserFragment;
import com.skilrock.retailapp.ui.fragments.rms.SettlementDetailFragment;
import com.skilrock.retailapp.ui.fragments.rms.SettlementReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.SummarizedLedgerReportFragment;
import com.skilrock.retailapp.ui.fragments.rms.UserDetailFragment;
import com.skilrock.retailapp.ui.fragments.rms.UserDetailMyanmarFragment;
import com.skilrock.retailapp.ui.fragments.rms.UserListFragment;
import com.skilrock.retailapp.ui.fragments.rms.UserRegistrationFragmentMain;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.LocaleHelper;
import com.skilrock.retailapp.utils.LockNotificationView;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.StringMapOla;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.MainViewModel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final int CALL_REQUEST_CODE = 5757;
    private final String RMS = "rms";
    public boolean isBackAllowed = true;
    public boolean isClickAllowed = true;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ArrayList<HomeDataBean.ResponseData.ModuleBeanList> drawerList;
    private MainViewModel mainViewModel;
    private ProgressDialog progressDialog;
    private TextView navBalance;
    private LinearLayout llDrawerContainer;
    private TextView navUserName, navUserId, navOrg, tvVersion, navLimit, navMobileNo;
    private ImageView imageLogo;
    private boolean allowDrawerItemClick = true;
    private long mLastClickTime = 0;
    private boolean doubleBackToExitPressedOnce;
    private LockNotificationView lockNotificationView;
    WindowManager windowManager;
    final static int REQUEST_LOCATION = 199;
    public LocationManager manager;
    private final int LOCATION_REQUEST_CODE = 2;
    public Location currentLocation;
    private FusedLocationProviderClient fusedLocationClient;
    public StringMapOla stringMapOla;
    LinearLayout internet_drawer;
    ImageView ivIconConnection;
    TextView tvSpeed, link_status;
    boolean isOnline = false;
    private InternetSpeedDialog internetSpeedDialog;
    public boolean IS_SINGLE_MENU = false;

    DialogInterface.OnClickListener logoutListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            progressDialog = Utils.getProgressDialog(MainActivity.this);
            HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean = Utils.getMenuBean(drawerList, AppConstants.LOGOUT);
            mainViewModel.callLogoutApi(menuBean.getBasePath() + menuBean.getRelativePath());
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        if (SharedPrefUtil.getLanguage(MainActivity.this).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC))
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setContentView(R.layout.activity_main);
        lockNotificationView = new LockNotificationView(this);
        stringMapOla = new StringMapOla(MainActivity.this);

        lockNotificationBar();
        initializeWidgets();

        mainViewModel.getLogoutLiveData().observe(this, logoutResponseBean -> {
            if (progressDialog != null)
                progressDialog.dismiss();
            if (logoutResponseBean == null)
                Utils.showCustomErrorDialogAndLogout(MainActivity.this, BuildConfig.app_name, getString(R.string.something_went_wrong), true);
            else if (logoutResponseBean.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(logoutResponseBean.getResponseMessage())?getString(R.string.some_internal_error):logoutResponseBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(MainActivity.this, RMS, logoutResponseBean.getResponseCode());
                Utils.showCustomErrorDialogAndLogout(MainActivity.this, BuildConfig.app_name, errorMsg, true);
            } else if (logoutResponseBean.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(MainActivity.this, logoutResponseBean.getResponseData().getStatusCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(logoutResponseBean.getResponseData().getMessage())?getString(R.string.some_internal_error):logoutResponseBean.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(MainActivity.this, RMS, logoutResponseBean.getResponseData().getStatusCode());
                Utils.showCustomErrorDialogAndLogout(MainActivity.this, BuildConfig.app_name, errorMsg, true);
            } else if (logoutResponseBean.getResponseData().getStatusCode() == 0)
                Utils.performLogoutCleanUp(MainActivity.this);
            else
                Utils.showCustomErrorDialogAndLogout(MainActivity.this, BuildConfig.app_name, getString(R.string.something_went_wrong), true);
        });

        mainViewModel.getLiveDataBalance().observe(this, observerBalance);
        if (SharedPrefUtil.getString(this, SharedPrefUtil.AUTH_TOKEN).isEmpty()) {
            openLoginActivity();
        } else {
            if (BuildConfig.app_name.equalsIgnoreCase(getString(R.string.app_name_FieldX))) {
                PlayerData.getInstance().setLoginData(new Gson().fromJson(SharedPrefUtil.getString(this, SharedPrefUtil.LOGIN_BEAN), LoginBean.class));
                openFragment(new FieldXHomeFragment(), "FieldXHomeFragment", false);
            } else {
                PlayerData.getInstance().setLoginData(new Gson().fromJson(SharedPrefUtil.getString(this, SharedPrefUtil.LOGIN_BEAN), LoginBean.class));
                openFragment(new HomeFragment(), "HomeFragment", true);
            }
        }
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                Utils.hideKeyboard(MainActivity.this);
                if (navBalance != null) {
                    String balance;
                    if (ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO")) {

                        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS)) {

                            balance = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this)) + " " + PlayerData.getInstance().getOrgBalance();
                        } else {
                            balance = PlayerData.getInstance().getOrgBalance() + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this));
                        }
                    } else{
                        balance = PlayerData.getInstance().getOrgBalance();
                    }
                    String text = getString(R.string.balance) + " " + balance;
                    navBalance.setText(text);

                    //navBalance.setText(String.format(getString(R.string.balance_place_holder), balance));
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                allowDrawerItemClick = true;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                super.onFragmentResumed(fm, f);
                isClickAllowed = true;
                // Log.v("FragXX7 Resumed", f.getTag());
            }

            @Override
            public void onFragmentPaused(FragmentManager fm, Fragment f) {
                super.onFragmentPaused(fm, f);
                isClickAllowed = false;
            }

            @Override
            public void onFragmentDetached(FragmentManager fm, Fragment f) {
                super.onFragmentDetached(fm, f);
                isClickAllowed = true;
            }
        }, true);
    }

    private void lockNotificationBar() {
        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.CAMEROON)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Show alert dialog to the user saying a separate permission is needed
                // Launch the settings activity if the user prefers
                if (!Settings.canDrawOverlays(this)) {
                    Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivityForResult(myIntent, 001);
                } else {
                    preventStatusBarExpansion(this);
                }
            } else {
                preventStatusBarExpansion(this);
            }
        }
    }

    private void initializeWidgets() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        drawerLayout = findViewById(R.id.main_drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        llDrawerContainer = findViewById(R.id.llDrawerContainer);
        navUserName = findViewById(R.id.tv_drawer_user_name);
        navUserId = findViewById(R.id.tv_drawer_user_id);
        navOrg = findViewById(R.id.tv_drawer_organization);
        navLimit = findViewById(R.id.tv_drawer_limit);
        navMobileNo = findViewById(R.id.tv_drawer_mobile_no);
        tvVersion = findViewById(R.id.tvVersion);
        imageLogo = findViewById(R.id.image_logo);
        navBalance = findViewById(R.id.tv_balance);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //imageLogo.setImageDrawable(Utils.getLogo(this, BuildConfig.app_name));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.navigation_view);

        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            ViewGroup.LayoutParams layoutParams = navigationView.getLayoutParams();
            layoutParams.width = 510;
            navigationView.setLayoutParams(layoutParams);
        }

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS)) {
            navigationView.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 310, getResources().getDisplayMetrics());
        }
        //checkIsLocationEnabled();

    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        fusedLocationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SharedPrefUtil.putString(MainActivity.this, "LastLocation",
                            currentLocation.getLatitude() + "," + currentLocation.getLongitude());
                }
            }
        });
    }

    public void enableLoc() {
        GoogleApiClient googleApiClient = null;
        /*if (googleApiClient == null) {
            GoogleApiClient finalGoogleApiClient = googleApiClient;
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            Toast.makeText(MainActivity.this, "onConnected in enableLoc", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            finalGoogleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();

            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                            }
                            break;
                    }
                }
            });
        }*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, SharedPrefUtil.getLanguage(base)));
    }

    private void checkIsLocationEnabled() {
        if (BuildConfig.app_name.equalsIgnoreCase("Fieldx")) {
            manager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
                return;
            } else {
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
                    enableLoc();
                } else {
                    getCurrentLocation();
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(MainActivity.this,
                                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                2000,
                                10, locationListenerGPS);
                    }
                }
            }
        }
    }

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            if (location != null) {
                currentLocation = location;
                SharedPrefUtil.putString(MainActivity.this, "LastLocation",
                        location.getLatitude() + "," + location.getLongitude());

                if (((FieldXHomeFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag("FieldXHomeFragment")) != null)
                    ((FieldXHomeFragment) MainActivity.this.getSupportFragmentManager().findFragmentByTag("FieldXHomeFragment")).filterFieldxRetailerData();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                getCurrentLocation();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };


    public boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    public void openFragment(Fragment fragment, String fragmentTag, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.content_frame, fragment, fragmentTag);
        drawerLayout.setDrawerLockMode(fragmentTag.equalsIgnoreCase("HomeFragment") ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (addToBackStack)
            transaction.addToBackStack(fragmentTag);
        transaction.commit();
    }

    public void openFragment(Fragment fragment, String fragmentTag, boolean addToBackStack, Bundle args) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//ScratchFragment
        if (!(BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS) && fragmentTag.equalsIgnoreCase("ScratchFragment"))) {
            transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        }
        fragment.setArguments(args);
        transaction.replace(R.id.content_frame, fragment, fragmentTag);
        drawerLayout.setDrawerLockMode(fragmentTag.equalsIgnoreCase("HomeFragment") ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        if (addToBackStack)
            transaction.addToBackStack(fragmentTag);
        transaction.commit();
    }

    public void unLockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void enableDrawer(boolean setEnable) {
        if (setEnable)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        else
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @SuppressLint("SetTextI18n")
    public void setDrawerElements(List<HomeDataBean.ResponseData.ModuleBeanList> drawerList) {
        this.drawerList = new ArrayList<>(drawerList);
        enableDrawer(true);
        tvVersion.setText(getAppVersion());
        String userName = PlayerData.getInstance().getUsername().toUpperCase();
        String userId = getString(R.string.your_user_id) + " " + PlayerData.getInstance().getUserId();
        String organization = getString(R.string.organization) + " " + PlayerData.getInstance().getLoginData().getResponseData().getData().getOrgName().toUpperCase();
        navUserName.setText(userName);
        navUserId.setText(userId);
        navOrg.setText(organization);
        String limit;
        if (ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO")) {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS))
                limit = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this)) + " " + PlayerData.getInstance().getCreditLimit();
            else
                limit = PlayerData.getInstance().getCreditLimit() + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this));
        } else
            limit = PlayerData.getInstance().getCreditLimit();
        String limitText = getString(R.string.limit) + " " + limit;
        navLimit.setText(limitText);
        try {
            if (ConfigData.getInstance().getConfigData().getCREDIT_LIMIT_DISPLAY_ON_APP().equalsIgnoreCase("YES")) {
                navLimit.setVisibility(View.VISIBLE);
            } else {
                navLimit.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e("log", "Exception: " + e);
        }
        if (PlayerData.getInstance().getMobileCode() != null && PlayerData.getInstance().getMobileNumber() != null) {
            navMobileNo.setText(getString(R.string.mobile_number) + ": " + PlayerData.getInstance().getMobileCode()+PlayerData.getInstance().getMobileNumber());
        } else {
            navMobileNo.setVisibility(View.GONE);
        }

        String balance;
        if (ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO")) {
            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS))
                balance = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this)) + " " + PlayerData.getInstance().getOrgBalance();
            else
                balance = PlayerData.getInstance().getOrgBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this));
        }
        else
            balance = PlayerData.getInstance().getOrgBalance();
        //balance = ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO") ?  PlayerData.getInstance().getUserBalance() + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(this)) : PlayerData.getInstance().getUserBalance();
        String text = getString(R.string.balance) + " " + balance;
        navBalance.setText(text);
        //navBalance.setText(String.format(getString(R.string.balance_place_holder), balance));
        int index = 0;
        for (; index < drawerList.size(); index++) {
            HomeDataBean.ResponseData.ModuleBeanList moduleBean = drawerList.get(index);
            TextView tvHeader = new TextView(this);
            tvHeader.setTextColor(Color.parseColor("#a5a5a5"));
            tvHeader.setText(stringMapOla.getCaption(moduleBean.getModuleCode(), moduleBean.getDisplayName()));
            tvHeader.setTextSize(15);
            tvHeader.setTypeface(null, Typeface.BOLD);
            tvHeader.setPadding(10, 10, 10, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 0, 10, 20);
            tvHeader.setLayoutParams(params);
            llDrawerContainer.addView(tvHeader);
            ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> listSubMenu = moduleBean.getMenuBeanList();
            for (int subIndex = 0; subIndex < listSubMenu.size(); subIndex++) {
                HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean = listSubMenu.get(subIndex);
                LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.row_drawar, null);
                TextView tvMenu = view.findViewById(R.id.tvCaption);
                tvMenu.setText(stringMapOla.getCaption(menuBean.getMenuCode(), menuBean.getCaption()));
                ImageView ivIcon = view.findViewById(R.id.ivIcon);
                view.setTag(menuBean.getMenuId());
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.PAYMENT_REPORT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_payment_report));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.OLA_REPORT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_deposit_withdraw_report));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.CHANGE_PASSWORD))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_change_password));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.DEVICE_REGISTRATION))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_device_register));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.LOGOUT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_logout));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.BILL_REPORT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_invoice));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.M_LEDGER))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_ledger_report));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.USER_REGISTRATION))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_user_registration));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.USER_SEARCH))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_search_user));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.ACCOUNT_SETTLEMENT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_account_settlement));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.SETTLEMENT_REPORT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_settlement_report));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.SALE_WINNING_REPORT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_sale_winning_report));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.INTRA_ORG_CASH_MGMT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_cash_management));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.M_SUMMARIZE_LEDGER))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ledger));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.COLLECTION_REPORT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_ledger_report));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.ALL_RETAILERS))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_search_user));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.QR_CODE_REGISTRATION))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_qr));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.NATIVE_DISPLAY_QR))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_qr));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.BALANCE_REPORT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.balance));
                if (menuBean.getMenuCode().trim().equalsIgnoreCase(AppConstants.OPERATIONAL_REPORT))
                    ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.statistics));

                llDrawerContainer.addView(view);
                view.setOnClickListener(v -> {
                    drawerLayout.closeDrawers();
                    if (allowDrawerItemClick) {
                        allowDrawerItemClick = false;
                        int id = Integer.parseInt(String.valueOf(v.getTag()));
                        switch (id) {
                            case AppConstants.MENU_ID_BALANCE_REPORT:
                                BalanceReportFragment balanceReportFragment = (BalanceReportFragment) getSupportFragmentManager().findFragmentByTag("BalanceReportFragment");
                                fragmentTransaction(balanceReportFragment, "BalanceReportFragment", AppConstants.BALANCE_REPORT, new BalanceReportFragment());
                                break;
                            case AppConstants.MENU_ID_OPERATIONAL_REPORT:
                                OperationalReportFragment operationalReportFragment = (OperationalReportFragment) getSupportFragmentManager().findFragmentByTag("OperationalReportFragment");
                                fragmentTransaction(operationalReportFragment, "OperationalReportFragment", AppConstants.OPERATIONAL_REPORT, new OperationalReportFragment());
                                break;
                            case AppConstants.MENU_ID_PAYMENT_REPORT:
                                PaymentReportFragment paymentReportFragment = (PaymentReportFragment) getSupportFragmentManager().findFragmentByTag("PaymentReportFragment");
                                fragmentTransaction(paymentReportFragment, "PaymentReportFragment", AppConstants.PAYMENT_REPORT, new PaymentReportFragment());
                                break;
                            case AppConstants.MENU_ID_SALE_REPORT:
                                SaleWinningReportFragment saleWinningReportFragment = (SaleWinningReportFragment) getSupportFragmentManager().findFragmentByTag("SaleWinningReportFragment");
                                fragmentTransaction(saleWinningReportFragment, "SaleWinningReportFragment", AppConstants.SALE_WINNING_REPORT, new SaleWinningReportFragment());
                                break;
                            case AppConstants.MENU_ID_LEDGER_REPORT:
                                LedgerReportFragment reportFragment = (LedgerReportFragment) getSupportFragmentManager().findFragmentByTag("LedgerReportFragment");
                                fragmentTransaction(reportFragment, "LedgerReportFragment", AppConstants.M_LEDGER, new LedgerReportFragment());
                                break;
                            case AppConstants.MENU_ID_CHANGE_PASSWORD:
                                ChangePasswordFragment changePasswordFragment = (ChangePasswordFragment) getSupportFragmentManager().findFragmentByTag("ChangePassword");
                                fragmentTransaction(changePasswordFragment, "ChangePassword", AppConstants.CHANGE_PASSWORD, new ChangePasswordFragment());
                                break;
                            case AppConstants.MENU_ID_LOGOUT:
                                Utils.showMessageDialog(MainActivity.this, getString(R.string.logout), getString(R.string.logout_confirmation), logoutListener, null);
                                break;
                            case AppConstants.MENU_ID_DEVICE_REGISTER:
                                try {
                                    if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                                        Toast.makeText(MainActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                                    else {
                                        DeviceRegistrationFragment deviceRegistrationFragment = (DeviceRegistrationFragment) getSupportFragmentManager().findFragmentByTag("DeviceRegistrationFragment");

                                        fragmentTransaction(deviceRegistrationFragment, "DeviceRegistrationFragment", AppConstants.DEVICE_REGISTRATION, new DeviceRegistrationFragment());
                                    }
                                } catch (Exception e) {
                                    DeviceRegistrationFragment deviceRegistrationFragment = (DeviceRegistrationFragment) getSupportFragmentManager().findFragmentByTag("DeviceRegistrationFragment");
                                    fragmentTransaction(deviceRegistrationFragment, "DeviceRegistrationFragment", AppConstants.DEVICE_REGISTRATION, new DeviceRegistrationFragment());
                                }
                                break;
                            case AppConstants.MENU_ID_OLA_REPORT:
                                OlaReportFragment olaReportFragment = (OlaReportFragment) getSupportFragmentManager().findFragmentByTag("OlaReportFragment");
                                fragmentTransaction(olaReportFragment, "OlaReportFragment", AppConstants.OLA_REPORT, new OlaReportFragment());
                                break;
                            case AppConstants.MENU_ID_BILL_REPORT:
                                BillReportFragment billReportFragment = (BillReportFragment) getSupportFragmentManager().findFragmentByTag("BillReportFragment");
                                fragmentTransaction(billReportFragment, "BillReportFragment", AppConstants.BILL_REPORT, new BillReportFragment());
                                break;
                            case AppConstants.MENU_ID_USER_REGISTRATION:
                                try {
                                    if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                                        Toast.makeText(MainActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                                    else {
                                        UserRegistrationFragmentMain userRegistrationFragment = (UserRegistrationFragmentMain) getSupportFragmentManager().findFragmentByTag("UserRegistrationFragment");
                                        fragmentTransaction(userRegistrationFragment, "UserRegistrationFragment", AppConstants.USER_REGISTRATION, new UserRegistrationFragmentMain());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case AppConstants.MENU_ID_SEARCH_USER:
                                if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                                    Toast.makeText(MainActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                                else {
                                    SearchUserFragment searchUserFragment = (SearchUserFragment) getSupportFragmentManager().findFragmentByTag("SearchUserFragment");
                                    fragmentTransaction(searchUserFragment, "SearchUserFragment", AppConstants.USER_SEARCH, new SearchUserFragment());
                                }
                                break;
                            case AppConstants.MENU_ID_CASH_MANAGEMENT:
                                if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                                    Toast.makeText(MainActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                                else {
                                    CashManagementFragment cashManagementFragment = (CashManagementFragment) getSupportFragmentManager().findFragmentByTag("CashManagementFragment");
                                    fragmentTransaction(cashManagementFragment, "CashManagementFragment", AppConstants.INTRA_ORG_CASH_MGMT, new CashManagementFragment());
                                }
                                break;
                            case AppConstants.MENU_ID_SETTLEMENT_REPORT:
                                if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                                    Toast.makeText(MainActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                                else {
                                    SettlementReportFragment settlementReportFragment = (SettlementReportFragment) getSupportFragmentManager().findFragmentByTag("SettlementReportFragment");
                                    fragmentTransaction(settlementReportFragment, "SettlementReportFragment", AppConstants.SETTLEMENT_REPORT, new SettlementReportFragment());
                                }
                                break;
                            case AppConstants.MENU_ID_ACCOUNT_SETTLEMENT:
                                if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                                    Toast.makeText(MainActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                                else {
                                    UserListFragment userListFragment = (UserListFragment) getSupportFragmentManager().findFragmentByTag("UserListFragment");
                                    fragmentTransaction(userListFragment, "UserListFragment", AppConstants.ACCOUNT_SETTLEMENT, new UserListFragment());
                                }
                                break;
                            case AppConstants.MENU_ID_SUMMARIZED_REPORT:
                            /*if (PlayerData.getInstance().getUserStatus().trim().equalsIgnoreCase("INACTIVE"))
                                Toast.makeText(MainActivity.this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
                            else {
                                SummarizedLedgerReportFragment userListFragment = (SummarizedLedgerReportFragment) getSupportFragmentManager().findFragmentByTag("SummarizedLedgerReportFragment");
                                fragmentTransaction(userListFragment, "SummarizedLedgerReportFragment", AppConstants.M_SUMMARIZE_LEDGER, new SummarizedLedgerReportFragment());
                            }*/
                                SummarizedLedgerReportFragment userListFragment = (SummarizedLedgerReportFragment) getSupportFragmentManager().findFragmentByTag("SummarizedLedgerReportFragment");
                                fragmentTransaction(userListFragment, "SummarizedLedgerReportFragment", AppConstants.M_SUMMARIZE_LEDGER, new SummarizedLedgerReportFragment());
                                break;
                            case AppConstants.MENU_ID_COLLECTION_REPORT:
                                FieldXCollectionReport collectionReport = (FieldXCollectionReport) getSupportFragmentManager().findFragmentByTag("FieldxCollectionReport");
                                fragmentTransaction(collectionReport, "ChangePassword", AppConstants.COLLECTION_REPORT, new FieldXCollectionReport());
                                break;
                            case AppConstants.MENU_ID_ALL_RETAILER:
                                FieldXAllRetailerFragment allRetailerFragment = (FieldXAllRetailerFragment) getSupportFragmentManager().findFragmentByTag("FieldXAllRetailerFragment");
                                fragmentTransaction(allRetailerFragment, "FieldXAllRetailerFragment", AppConstants.ALL_RETAILERS, new FieldXAllRetailerFragment());
                                break;
                            case AppConstants.MENU_ID_QR_CODE_REGISTER:
                                QrCodeRegistrationFragment qrCodeRegistrationFragment = (QrCodeRegistrationFragment) getSupportFragmentManager().findFragmentByTag("QrCodeRegistrationFragment");
                                fragmentTransaction(qrCodeRegistrationFragment, "QrCodeRegistrationFragment", AppConstants.QR_CODE_REGISTRATION, new QrCodeRegistrationFragment());
                                break;
                            case AppConstants.MENU_ID_DISPLAY_QR:
                                DisplayQRFragment displayQRFragment = (DisplayQRFragment) getSupportFragmentManager().findFragmentByTag("DisplayQRFragment");
                                fragmentTransaction(displayQRFragment, "DisplayQRFragment", AppConstants.NATIVE_DISPLAY_QR, new DisplayQRFragment());
                                break;
                        }
                    }
                });
            }

            View line = new View(this);
            line.setBackgroundColor(Color.parseColor("#a5a5a5"));
            LinearLayout.LayoutParams paramsLine = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            paramsLine.setMargins(40, 20, 20, 20);
            line.setLayoutParams(paramsLine);
            llDrawerContainer.addView(line);
        }
        LinearLayout changeLanguage = (LinearLayout) getLayoutInflater().inflate(R.layout.row_drawar, null);

        TextView tvChangeLanguage = changeLanguage.findViewById(R.id.tvCaption);
        tvChangeLanguage.setText(getString(R.string.check_network));
        ImageView ivIconChangeLanguage = changeLanguage.findViewById(R.id.ivIcon);
        ivIconChangeLanguage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_connection_icon));
        llDrawerContainer.addView(changeLanguage);

        LinearLayout callUs = (LinearLayout) getLayoutInflater().inflate(R.layout.row_drawar, null);
        TextView tvMenu = callUs.findViewById(R.id.tvCaption);
        if (!TextUtils.isEmpty(SharedPrefUtil.getString(MainActivity.this, SharedPrefUtil.CONTACT_US_NUMBER)))
            tvMenu.setText((getString(R.string.call_us_at) + "\n" + SharedPrefUtil.getString(MainActivity.this, SharedPrefUtil.CONTACT_US_NUMBER)));
        else
            callUs.setVisibility(View.GONE);
        ImageView ivIcon = callUs.findViewById(R.id.ivIcon);
        ivIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_drawer_call_us));
        llDrawerContainer.addView(callUs);
        /*if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)) {
            callUs.setVisibility(View.GONE);
        }else{
            callUs.setVisibility(View.VISIBLE);
        }*/
        callUs.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(SharedPrefUtil.getString(MainActivity.this, SharedPrefUtil.CONTACT_US_NUMBER))) {
                /*Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + SharedPrefUtil.getString(MainActivity.this, SharedPrefUtil.CONTACT_US_NUMBER)));
                startActivity(intent);*/
                if (!(Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_TPS570)
                        || Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))) {
                    onCall();
                }
            }
        });

        changeLanguage.setOnClickListener(v -> {
            onInternetSpeedDialog();
            drawerLayout.closeDrawer(Gravity.LEFT);
        });
        navigationView.invalidate();
        navBalance.setOnClickListener(v -> {
            if (!Utils.isNetworkConnected(MainActivity.this)) {
                Toast.makeText(MainActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return;
            }
            ProgressBarDialog.getProgressDialog().showProgress(MainActivity.this);
            mainViewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
        });
        String isDisplayUserBalance = ConfigData.getInstance().getConfigData().getDISPLAYUSERBALANCE();
        String isHead = PlayerData.getInstance().getLoginData().getResponseData().getData().getIsHead();
        if (isDisplayUserBalance.equalsIgnoreCase("YES") && isHead.equalsIgnoreCase("NO")) {
            navLimit.setVisibility(View.GONE);
            navBalance.setVisibility(View.GONE);
        }
    }

    public void callBalRefreshForSubUser() {
        if (!Utils.isNetworkConnected(MainActivity.this)) {
            Toast.makeText(MainActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        ProgressBarDialog.getProgressDialog().showProgress(MainActivity.this);
        mainViewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    public void fragmentTransaction(Fragment fragment, String tag, String menuTag, Fragment targetFragment) {
        if (fragment != null && fragment.isVisible())
            return;
        HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean = Utils.getMenuBean(drawerList, menuTag);
        if (menuBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString("title", stringMapOla.getCaption(menuBean.getMenuCode(), menuBean.getCaption()));
            if (tag.equalsIgnoreCase("UserListFragment"))
                bundle.putString("title", getString(R.string.user_list));
            bundle.putString("url", menuBean.getBasePath() + menuBean.getRelativePath());
            bundle.putParcelable("MenuBean", menuBean);
            openFragment(targetFragment, tag, true, bundle);
        } else
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
    }

    public void openUserDetailsFragment(String userId) {
        UserDetailFragment searchUserFragment = (UserDetailFragment) getSupportFragmentManager().findFragmentByTag("UserDetailFragment");
        if (searchUserFragment != null && searchUserFragment.isVisible())
            return;
        HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBeanUserRegistration = Utils.getMenuBean(drawerList, AppConstants.USER_REGISTRATION);
        HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean = Utils.getMenuBean(drawerList, AppConstants.USER_SEARCH);
        if (menuBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString("title", getString(R.string.user_details));
            bundle.putString("url", menuBean.getBasePath() + menuBean.getRelativePath());
            bundle.putParcelable("MenuBean", menuBean);
            bundle.putParcelable("MenuBean_UserRegistration", menuBeanUserRegistration);
            bundle.putString("user_id", userId);
            openFragment(new UserDetailFragment(), "UserDetailFragment", true, bundle);
        } else
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
    }

    public void openUserDetailsMyanmarFragment(String userId) {
        UserDetailMyanmarFragment searchUserFragment = (UserDetailMyanmarFragment) getSupportFragmentManager().findFragmentByTag("UserDetailMyanmarFragment");
        if (searchUserFragment != null && searchUserFragment.isVisible())
            return;
        HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBeanUserRegistration = Utils.getMenuBean(drawerList, AppConstants.USER_REGISTRATION);
        HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean = Utils.getMenuBean(drawerList, AppConstants.USER_SEARCH);
        if (menuBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString("title", getString(R.string.user_details));
            bundle.putString("url", menuBean.getBasePath() + menuBean.getRelativePath());
            bundle.putParcelable("MenuBean", menuBean);
            bundle.putParcelable("MenuBean_UserRegistration", menuBeanUserRegistration);
            bundle.putString("user_id", userId);
            openFragment(new UserDetailMyanmarFragment(), "UserDetailMyanmarFragment", true, bundle);
        } else
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
    }

    public void openSettlementDetails(String settlementId) {
        SettlementDetailFragment reportFragment = (SettlementDetailFragment) getSupportFragmentManager().findFragmentByTag("SettlementDetailFragment");
        if (reportFragment != null && reportFragment.isVisible())
            return;
        HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean = Utils.getMenuBean(drawerList, AppConstants.SETTLEMENT_REPORT);
        if (menuBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString("title", stringMapOla.getCaption(menuBean.getMenuCode(), menuBean.getCaption()));
            bundle.putString("url", menuBean.getBasePath() + menuBean.getRelativePath());
            bundle.putString("settlement_id", settlementId);
            bundle.putParcelable("MenuBean", menuBean);
            openFragment(new SettlementDetailFragment(), "SettlementDetailFragment", true, bundle);
        } else
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
    }

    public void openAccountSettlement(String userId, String userName, String name) {
        AccountSettlementFragment reportFragment = (AccountSettlementFragment) getSupportFragmentManager().findFragmentByTag("AccountSettlementFragment");
        if (reportFragment != null && reportFragment.isVisible())
            return;
        HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean = Utils.getMenuBean(drawerList, AppConstants.ACCOUNT_SETTLEMENT);
        if (menuBean != null) {
            Bundle bundle = new Bundle();
            bundle.putString("title", stringMapOla.getCaption(menuBean.getMenuCode(), menuBean.getCaption()));
            bundle.putString("url", menuBean.getBasePath() + menuBean.getRelativePath());
            bundle.putString("user_id", userId);
            bundle.putString("user_name", userName);
            bundle.putString("name", name);
            bundle.putParcelable("MenuBean", menuBean);
            openFragment(new AccountSettlementFragment(), "AccountSettlementFragment", true, bundle);
        } else
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (fragment != null && fragment instanceof HomeFragment) {
                MainActivity.this.finish();
            }
            else if (fragment != null && fragment instanceof ScratchAndDrawGameFragment) {
                if (IS_SINGLE_MENU)
                    MainActivity.this.finish();
            }
            else {
                if (fragment != null && fragment instanceof AccountSettlementFragment) {
                    ((BaseFragment) fragment).onBackPressed();
                }
                Fragment fragment1 = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                boolean showHomeScreen = SharedPrefUtil.getBoolean(this, SharedPrefUtil.SHOW_HOME_SCREEN);
                if (!showHomeScreen && fragment1 instanceof HomeFragment) {
                    finish();
                    System.exit(0);
                    return;
                } else if (fragment1 instanceof FieldXHomeFragment) {
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        return;
                    }

                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, this.getString(R.string.please_click_back_again_to_exit), Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                }
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                if (supportFragmentManager.getBackStackEntryCount() > 0) {
                    supportFragmentManager.popBackStack();
                }
            }
        }
    }

    public void onClickOpenDrawer(View view) {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void onClickBack(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        if (isBackAllowed && isClickAllowed) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (fragment != null && fragment instanceof AccountSettlementFragment) {
                ((BaseFragment) fragment).onBackPressed();
            }
            Utils.hideKeyboard(MainActivity.this);
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            if (supportFragmentManager.getBackStackEntryCount() > 0) {
                if (fragment != null && fragment instanceof ScratchAndDrawGameFragment) {
                    return;
                }
                supportFragmentManager.popBackStack();
            }
        }
    }

    private String getAppVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return getString(R.string.version) + " " + pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void onClickHome(View view) {

    }

    private void openLoginActivity() {
        Intent intent;
        if (BuildConfig.app_name.equalsIgnoreCase(getString(R.string.app_name_FieldX)))
            intent = new Intent(MainActivity.this, LoginActivityFieldX.class);
        else
            intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    Observer<LoginBean> observerBalance = new Observer<LoginBean>() {
        @Override
        public void onChanged(@Nullable LoginBean loginBean) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (loginBean == null)
                Toast.makeText(MainActivity.this, getString(R.string.there_was_problem_connecting_to_server), Toast.LENGTH_SHORT).show();
            else if (loginBean.getResponseCode() == 0) {
                LoginBean.ResponseData responseData = loginBean.getResponseData();
                if (responseData.getStatusCode() == 0) {
                    Utils.hideKeyboard(MainActivity.this);
                    PlayerData.getInstance().setLoginData(MainActivity.this, loginBean);
                    //refreshBalance();
                    //PlayerData.getInstance().setUserBalance(String.valueOf(loginBean.getResponseData().getData().getBalance()));
                    if (navBalance != null) {
                        String balance;
                        if (ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO")) {
                            if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.PAYPR_BUSINESS)) {
                                balance = Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this)) + " " + PlayerData.getInstance().getOrgBalance();
                            } else
                                balance = PlayerData.getInstance().getOrgBalance() + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this));
                        } else
                            balance = PlayerData.getInstance().getOrgBalance();
                        String text = getString(R.string.balance) + " " + balance;
                        navBalance.setText(text);
                        //navBalance.setText(String.format(getString(R.string.balance_place_holder), balance));

                        String limit;
                        if (ConfigData.getInstance().getConfigData().getDISPLAYCURRENCYSYMBOL().equalsIgnoreCase("NO"))
                            limit = PlayerData.getInstance().getCreditLimit() + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(MainActivity.this));
                        else
                            limit = PlayerData.getInstance().getCreditLimit();
                        String limitText = getString(R.string.limit) + " " + limit;
                        navLimit.setText(limitText);
                    }
                    // this line remove due to client requirement
                    //Toast.makeText(MainActivity.this, getString(R.string.your_balance_has_been_updated), Toast.LENGTH_SHORT).show();
                    updateBalanceInFragments();
                } else {
                    if (Utils.checkForSessionExpiry(MainActivity.this, loginBean.getResponseData().getStatusCode()))
                        return;
                    String errorMsg = Utils.getResponseMessage(MainActivity.this, RMS, responseData.getStatusCode());
                    Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                }
            } else {
                String errorMsg = Utils.getResponseMessage(MainActivity.this, RMS, loginBean.getResponseCode());
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void updateBalanceInFragments() {
        if (BuildConfig.app_name.equalsIgnoreCase("fieldx")) {
            BaseFragmentFieldx baseFragment = (BaseFragmentFieldx) getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (baseFragment != null)
                baseFragment.refreshBalance();
        } else {
            BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (baseFragment != null)
                baseFragment.refreshBalance();
        }
    }

    private void onInternetSpeedDialog() {
        Log.w("log", "App Name: " + BuildConfig.app_name);
        Log.w("log", "App Language: " + SharedPrefUtil.getString(this, SharedPrefUtil.APP_LANGUAGE));
        EventListener listener = this::proceedToLogin;
        internetSpeedDialog = new InternetSpeedDialog(MainActivity.this, listener);
        internetSpeedDialog.show();
        if (internetSpeedDialog.getWindow() != null) {
            internetSpeedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            internetSpeedDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    private void proceedToLogin() {

    }

    private void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
        } else {
            //String contactNumber;
            /*if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.SISAL))
                contactNumber = AppConstants.CONTACT_NUMBER_SISAL;
            else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.CAMEROON))
                contactNumber = AppConstants.CONTACT_NUMBER_CAMEROON;
            else if(BuildConfig.app_name.equalsIgnoreCase(AppConstants.FIELDX))
                contactNumber = AppConstants.CONTACT_NUMBER_FIELDX;
            else
                contactNumber = AppConstants.CONTACT_NUMBER_ACDC;*/
            if (!TextUtils.isEmpty(SharedPrefUtil.getString(MainActivity.this, SharedPrefUtil.CONTACT_US_NUMBER)))
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + SharedPrefUtil.getString(MainActivity.this, SharedPrefUtil.CONTACT_US_NUMBER))));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_REQUEST_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", getString(R.string.call_permission_error));
                }
                break;
            case LOCATION_REQUEST_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
                        enableLoc();
                    } else {
                        getCurrentLocation();
                    }
                }
                break;
        }
    }

    public void callBalanceApi(View view) {
        if (!Utils.isNetworkConnected(MainActivity.this)) {
            Toast.makeText(MainActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        ProgressBarDialog.getProgressDialog().showProgress(MainActivity.this);
        mainViewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    public void callBalanceApi() {
        if (!Utils.isNetworkConnected(MainActivity.this)) {
            Toast.makeText(MainActivity.this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        ProgressBarDialog.getProgressDialog().showProgress(MainActivity.this);
        mainViewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
    }

    @Override
    protected void onResume() {
        try {
            updateBalanceInFragments();
            if (internetSpeedDialog != null && internetSpeedDialog.isShowing())
                internetSpeedDialog.updateDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    public void preventStatusBarExpansion(Context context) {
        //Utils.showRedToast(this, getString(R.string.notification_bar_activated));
        windowManager = ((WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));

        Activity activity = (Activity) context;
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |

                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        int resId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int result = 0;
        if (resId > 0) {
            result = activity.getResources().getDimensionPixelSize(resId);
        }

        localLayoutParams.height = result;
        localLayoutParams.format = PixelFormat.TRANSPARENT;

        try {
            windowManager.addView(lockNotificationView, localLayoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            windowManager.removeView(lockNotificationView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ConnectionTask extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL("https://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    isOnline =true;
                    return isOnline;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                isOnline = true;
            }else{
                isOnline = false;
            }
        }
    }
}
