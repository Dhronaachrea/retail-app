package com.skilrock.retailapp.ui.fragments.rms;

import android.app.ProgressDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.CheckUserNameResponseBean;
import com.skilrock.retailapp.models.rms.CountryListResponseBean;
import com.skilrock.retailapp.models.rms.GetRoleListResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.RoleListData;
import com.skilrock.retailapp.models.rms.StateAndCityListResponseBean;
import com.skilrock.retailapp.models.rms.UserRegistrationRequestBean;
import com.skilrock.retailapp.models.rms.UserRegistrationResponseBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.UserRegistrationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRegistrationFragment extends BaseFragment implements View.OnClickListener {

    private UserRegistrationViewModel viewModel;
    private ProgressDialog progressDialog;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private int mRoleId = -1;
    private ExpandableRelativeLayout expLayoutUserDetails, expLayoutContactDetails, expLayoutAddDetails, expLayoutOrgDetails;
    private boolean isUserVerified = false;
    private String mCountryId, mStateId, mCityId;
    private EditText etFirstName, etLastName, etMiddleName, etUserName, etMobile, etEmail, etAltMobile, etContactPerson, etZipCode;
    private EditText etAddressOne, etAddressTwo;
    private TextView tvCountryCode, tvCountryCodeAltMobile;
    private Button btnCheckUser, buttonProceed;
    private TextView textRole;
    private TextInputLayout tilFirstName, tilLastName, tilUserName;
    private TextInputLayout tilAltMobile, tilEmail, tilAddressOne, tilContactPerson, tilAddressTwo, tilZipCode;
    private LinearLayout tilMobile;
    private ImageView imageExpandUserDetails, imageExpandContactDetails, imageExpandAddDetails, imageExpandOrgDetails;
    private Spinner spinnerCountry, spinnerState, spinnerCity, spinnerRole;
    private RelativeLayout cityContainer, stateContainer, countryContainer, roleContainer;
    private List<RoleListData> mRoleListData = new ArrayList<>();
    private List<CountryListResponseBean.CountryListBean> mCountryListData = new ArrayList<>();
    private List<StateAndCityListResponseBean.StateAndCityData> mStateListData = new ArrayList<>();
    private List<StateAndCityListResponseBean.City> mCityListData = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_registration, container, false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(UserRegistrationViewModel.class);
        viewModel.getLiveData().observe(this, checkUserNameObserver);
        viewModel.getRoleListLiveData().observe(this, roleListObserver);
        viewModel.getCountryList().observe(this, countryListObserver);
        viewModel.getStateAndCityListLiveData().observe(this, stateListObserver);
        viewModel.getUserRegistrationLiveData().observe(this, userRegistrationObserver);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);
    }
    private void initializeWidgets(View view) {
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUsername              = view.findViewById(R.id.tvUserName);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);

        tvCountryCode           = view.findViewById(R.id.tvCountryCode);
        tvCountryCodeAltMobile  = view.findViewById(R.id.tvCountryCode_alt_mobile);
        buttonProceed           = view.findViewById(R.id.button_proceed);
        etFirstName             = view.findViewById(R.id.et_first_name);
        etMiddleName            = view.findViewById(R.id.et_middle_name);
        etLastName              = view.findViewById(R.id.et_last_name);
        etUserName              = view.findViewById(R.id.et_user_name);
        etMobile                = view.findViewById(R.id.et_mobile_number);
        etAltMobile             = view.findViewById(R.id.et_alt_mobile);
        etEmail                 = view.findViewById(R.id.et_email);
        etAddressOne            = view.findViewById(R.id.et_address_one);
        etAddressTwo            = view.findViewById(R.id.et_address_two);
        etContactPerson         = view.findViewById(R.id.et_contact_person);
        textRole                = view.findViewById(R.id.tv_role);
        etZipCode               = view.findViewById(R.id.et_zip_code);

        spinnerRole             = view.findViewById(R.id.spinnerRole);
        spinnerCountry          = view.findViewById(R.id.spinnerCountry);
        spinnerState            = view.findViewById(R.id.spinnerState);
        spinnerCity             = view.findViewById(R.id.spinnerCity);

        tilFirstName            = view.findViewById(R.id.til_first_name);
        tilLastName             = view.findViewById(R.id.til_last_name);
        tilUserName             = view.findViewById(R.id.til_user_name);
        tilMobile               = view.findViewById(R.id.llMobile);
        tilEmail                = view.findViewById(R.id.til_email);
        tilAddressOne           = view.findViewById(R.id.til_address_one);
        tilAddressTwo           = view.findViewById(R.id.til_address_two);
        tilAddressTwo           = view.findViewById(R.id.til_address_two);
        tilContactPerson        = view.findViewById(R.id.til_contact_person);
        tilZipCode              = view.findViewById(R.id.til_zip_code);
        roleContainer           = view.findViewById(R.id.spinnerContainer);

        countryContainer        = view.findViewById(R.id.lt_spinner_country);
        stateContainer          = view.findViewById(R.id.lt_spinner_state);
        cityContainer           = view.findViewById(R.id.lt_spinner_city);

        expLayoutUserDetails    = view.findViewById(R.id.expandableLayout_user_details);
        expLayoutContactDetails = view.findViewById(R.id.expandableLayout_contact_details);
        expLayoutAddDetails     = view.findViewById(R.id.expandableLayout_address_details);
        expLayoutOrgDetails     = view.findViewById(R.id.expandableLayout_org_details);

        imageExpandUserDetails     = view.findViewById(R.id.img_expand_userdetails);
        imageExpandContactDetails  = view.findViewById(R.id.img_expand_contactdetails);
        imageExpandAddDetails      = view.findViewById(R.id.img_expand_addressdetails);
        imageExpandOrgDetails      = view.findViewById(R.id.img_expand_orgdetails);

        Utils.makeRequiredField(tilFirstName, master);
        Utils.makeRequiredField(tilLastName, master);
        Utils.makeRequiredField(tilUserName, master);
        Utils.makeRequiredField(textRole, master);
        Utils.makeRequiredField(tilContactPerson, master);
        Utils.makeRequiredField(etMobile, master);
        Utils.makeRequiredField(tilEmail, master);
        Utils.makeRequiredField(tilAddressOne, master);
        Utils.makeRequiredField(tilAddressTwo, master);
        Utils.makeRequiredField(tilZipCode, master);

        view.findViewById(R.id.rl_expand_userdetails).setOnClickListener(v -> {
            if (expLayoutUserDetails.isExpanded()) {
                expLayoutUserDetails.collapse();
                imageExpandUserDetails.setImageResource(R.drawable.icon_black_down_arror);
            } else {
                expLayoutUserDetails.expand();
                imageExpandUserDetails.setImageResource(R.drawable.icon_black_up_arror);
            }
            clearError();
        });

        view.findViewById(R.id.rl_expand_contactdetails).setOnClickListener(v -> {
            if (expLayoutContactDetails.isExpanded()) {
                expLayoutContactDetails.collapse();
                imageExpandContactDetails.setImageResource(R.drawable.icon_black_down_arror);
            } else {
                expLayoutContactDetails.expand();
                imageExpandContactDetails.setImageResource(R.drawable.icon_black_up_arror);
            }
            clearError();
        });

        view.findViewById(R.id.rl_expand_addressdetails).setOnClickListener(v -> {
            if (expLayoutAddDetails.isExpanded()) {
                imageExpandAddDetails.setImageResource(R.drawable.icon_black_down_arror);
            } else {
                imageExpandAddDetails.setImageResource(R.drawable.icon_black_up_arror);
            }
            expLayoutAddDetails.toggle();
            clearError();
        });

        view.findViewById(R.id.rl_expand_orgdetails).setOnClickListener(v -> {
            if (expLayoutOrgDetails.isExpanded()) {
                imageExpandOrgDetails.setImageResource(R.drawable.icon_black_down_arror);
            } else {
                imageExpandOrgDetails.setImageResource(R.drawable.icon_black_up_arror);
            }
            expLayoutOrgDetails.toggle();
            clearError();
        });

        btnCheckUser = view.findViewById(R.id.btn_check_user);

        refreshBalance();
        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();

        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        btnCheckUser.setOnClickListener(this);
        tvCountryCode.setOnClickListener(this);
        buttonProceed.setOnClickListener(this);
        tvCountryCodeAltMobile.setOnClickListener(this);

        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getRoleList");
        viewModel.getRoleList(url, PlayerData.getInstance().getToken());
        viewModel.getCountryList(Utils.getRmsUrlDetails(menuBean, getContext(), "getCountryList"), PlayerData.getInstance().getToken());
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRoleId = mRoleListData.get(position).getRoleId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                isUserVerified = false;
            }
        });

        Utils.countryCodeFocusOperation(master, BuildConfig.app_name);
        Utils.countryCodeFocusOperationForAltMobile(master, BuildConfig.app_name);
    }

    Observer<CheckUserNameResponseBean> checkUserNameObserver = new Observer<CheckUserNameResponseBean>() {
        @Override
        public void onChanged(@Nullable CheckUserNameResponseBean response) {
            if (progressDialog != null) progressDialog.dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {
                //String msg = response.getResponseData().getResponseMessage();
                String msg = getString(R.string.user_name_already_exists);
                if (response.getResponseData().getStatusCode() == 0) {
                    isUserVerified = false;
                    Drawable myIcon = getResources().getDrawable(R.drawable.icon_cross);
                    myIcon.setBounds(0, 0, myIcon.getIntrinsicWidth(), myIcon.getIntrinsicHeight());
                    etUserName.setError(msg, myIcon);
                } else {
                    isUserVerified = true;
                    Drawable myIcon = getResources().getDrawable(R.drawable.icon_check);
                    myIcon.setBounds(0, 0, myIcon.getIntrinsicWidth(), myIcon.getIntrinsicHeight());
                    etUserName.setError(getString(R.string.valid_user_name), myIcon);
                }
            } else {
                if (Utils.checkForSessionExpiry(master, response.getResponseCode()))
                    return;
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), errorMsg);
            }
        }
    };

    Observer<GetRoleListResponseBean> roleListObserver = new Observer<GetRoleListResponseBean>() {
        @Override
        public void onChanged(@Nullable GetRoleListResponseBean response) {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.role), getString(R.string.something_went_wrong), 1, getFragmentManager());

            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                mRoleListData = response.getResponseData().getData();
                setRoleListAdapter(response.getResponseData().getData());
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), getString(R.string.something_went_wrong));
        }
    };

    Observer<CountryListResponseBean> countryListObserver = new Observer<CountryListResponseBean>() {
        @Override
        public void onChanged(@Nullable CountryListResponseBean response) {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                mCountryListData = response.getResponseData().getData();
                setCountryListAdapter(response.getResponseData().getData());
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), getString(R.string.something_went_wrong));
        }
    };

    Observer<StateAndCityListResponseBean> stateListObserver = new Observer<StateAndCityListResponseBean>() {
        @Override
        public void onChanged(@Nullable StateAndCityListResponseBean response) {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                mStateListData = response.getResponseData().getData();
                setStateListAdapter(response.getResponseData().getData());
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), getString(R.string.something_went_wrong));
        }
    };

    Observer<UserRegistrationResponseBean> userRegistrationObserver = new Observer<UserRegistrationResponseBean>() {
        @Override
        public void onChanged(@Nullable UserRegistrationResponseBean response) {
            if (progressDialog != null)
                progressDialog.dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user_registration), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user_registration), errorMsg);
            }

            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user_registration), errorMsg);
            }

            else if (response.getResponseData().getStatusCode() == 0) {
                String msg = getString(R.string.user_registered_successfully);
                Utils.showCustomSuccessDialog(getContext(), getFragmentManager(), "", msg, 1);
            }

            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user_registration), getString(R.string.something_went_wrong));
        }
    };

    private void setRoleListAdapter(List<RoleListData> listData) {
        List<String> listSpinner = new ArrayList<>();

        for (RoleListData roleListData : listData) {
            listSpinner.add(roleListData.getRoleDisplayName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        if (listSpinner.size() == 1) {
            spinnerRole.setBackgroundResource(0);
            spinnerRole.setClickable(false);
            spinnerRole.setEnabled(false);
        }
    }

    private void setCountryListAdapter(List<CountryListResponseBean.CountryListBean> listData) {
        List<String> listSpinner = new ArrayList<>();

        for (CountryListResponseBean.CountryListBean roleListData : listData) {
            listSpinner.add(roleListData.getCountryName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);
    }

    private void setStateListAdapter(List<StateAndCityListResponseBean.StateAndCityData> listData) {
        List<String> listSpinner = new ArrayList<>();
        for (StateAndCityListResponseBean.StateAndCityData roleListData : listData) {
            listSpinner.add(roleListData.getStateName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(adapter);
    }

    private void setCityListAdapter(List<StateAndCityListResponseBean.City> listData) {
        List<String> listSpinner = new ArrayList<>();
        for (StateAndCityListResponseBean.City cityData : listData) {
            listSpinner.add(cityData.getCityName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, listSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCountryCode:
                Utils.openCountryCodeDialog(getActivity());
                break;
            case R.id.tvCountryCode_alt_mobile:
                //Utils.openCountryCodeDialogForAltMobile(getActivity());
                break;
            case R.id.button_proceed:
                if (!Utils.isNetworkConnected(master)) {
                    Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                if (validate()) {
                    String url1 = Utils.getRmsUrlDetails(menuBean, getActivity(), "doUserRegistration");
                    UserRegistrationRequestBean userDetails = getUserRegistrationDetails();
                    if (userDetails != null) {
                        progressDialog = Utils.getProgressDialog(getContext());
                        viewModel.callUserRegistration(url1, userDetails);
                    } else
                        Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_check_user:
                if (!Utils.isNetworkConnected(master)) {
                    Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                isUserVerified = false;
                if (validateUserName()) {
                    String url = Utils.getRmsUrlDetails(menuBean, getContext(), "checkLoginName");
                    progressDialog = Utils.getProgressDialog(getContext());
                    viewModel.checkUserNameApi(url, PlayerData.getInstance().getToken(), etUserName.getText().toString());
                }
                break;
        }
    }

    private boolean validateUserName() {
        Utils.vibrate(Objects.requireNonNull(getContext()));

        if (TextUtils.isEmpty(etUserName.getText().toString().trim())) {
            etUserName.setError(getString(R.string.this_field_cannot_be_empty));
            etUserName.requestFocus();
            tilUserName.startAnimation(Utils.shakeError());
            return false;
        }
        if (getContext() != null) {
            if (!Utils.isNetworkConnected(getContext())) {
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (mRoleId == -1) {
            Toast.makeText(master, getString(R.string.select_role_id), Toast.LENGTH_SHORT).show();
            roleContainer.startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError(getString(R.string.this_field_cannot_be_empty));
            etEmail.requestFocus();
            tilEmail.startAnimation(Utils.shakeError());
            expLayoutContactDetails.expand();
            return false;
        }

        if (!Utils.isEmailValid(etEmail.getText().toString().trim())) {
            Toast.makeText(master, getString(R.string.peovide_valid_email), Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            tilEmail.startAnimation(Utils.shakeError());
            expLayoutContactDetails.expand();
            return false;
        }

        if (TextUtils.isEmpty(etMobile.getText().toString().trim())) {
            etMobile.setError(getString(R.string.this_field_cannot_be_empty));
            etMobile.requestFocus();
            tilMobile.startAnimation(Utils.shakeError());
            expLayoutContactDetails.expand();
            return false;
        }

        if (etMobile.getText().toString().trim().length() < 10) {
            etMobile.setError(getString(R.string.invalid_mobile_number));
            etMobile.requestFocus();
            tilMobile.startAnimation(Utils.shakeError());
            expLayoutContactDetails.expand();
            return false;
        }

        if (TextUtils.isEmpty(etAddressOne.getText().toString().trim())) {
            etAddressOne.setError(getString(R.string.this_field_cannot_be_empty));
            etAddressOne.requestFocus();
            tilAddressOne.startAnimation(Utils.shakeError());
            expLayoutAddDetails.expand();
            return false;
        }

        if (TextUtils.isEmpty(etAddressTwo.getText().toString().trim())) {
            etAddressTwo.setError(getString(R.string.this_field_cannot_be_empty));
            etAddressTwo.requestFocus();
            tilAddressTwo.startAnimation(Utils.shakeError());
            expLayoutAddDetails.expand();
            return false;
        }

        if (TextUtils.isEmpty(mCountryId)) {
            Toast.makeText(master, getString(R.string.select_valid_country), Toast.LENGTH_SHORT).show();
            countryContainer.startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(mStateId)) {
            Toast.makeText(master, getString(R.string.select_valid_state), Toast.LENGTH_SHORT).show();
            stateContainer.startAnimation(Utils.shakeError());
            expLayoutAddDetails.expand();
            return false;
        }

        if (TextUtils.isEmpty(mCityId)) {
            Toast.makeText(master, getString(R.string.select_valid_city), Toast.LENGTH_SHORT).show();
            cityContainer.startAnimation(Utils.shakeError());
            expLayoutAddDetails.expand();
            return false;
        }

        if (TextUtils.isEmpty(etZipCode.getText().toString().trim())) {
            etZipCode.setError(getString(R.string.this_field_cannot_be_empty));
            etZipCode.requestFocus();
            tilZipCode.startAnimation(Utils.shakeError());
            expLayoutAddDetails.expand();
            return false;
        }

        if (TextUtils.isEmpty(etFirstName.getText().toString().trim())) {
            etFirstName.setError(getString(R.string.this_field_cannot_be_empty));
            etFirstName.requestFocus();
            expLayoutUserDetails.expand();
            tilFirstName.startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(etLastName.getText().toString().trim())) {
            etLastName.setError(getString(R.string.this_field_cannot_be_empty));
            etLastName.requestFocus();
            tilLastName.startAnimation(Utils.shakeError());
            expLayoutUserDetails.expand();
            return false;
        }

        if (TextUtils.isEmpty(etUserName.getText().toString().trim())) {
            etUserName.setError(getString(R.string.this_field_cannot_be_empty));
            etUserName.requestFocus();
            tilUserName.startAnimation(Utils.shakeError());
            expLayoutUserDetails.expand();
            return false;
        }

        if (!isUserVerified) {
            etUserName.setError(getString(R.string.verify_user_name));
            etUserName.requestFocus();
            tilUserName.startAnimation(Utils.shakeError());
            Toast.makeText(master, getString(R.string.verify_user_name), Toast.LENGTH_SHORT).show();
            expLayoutUserDetails.expand();
            return false;
        }

        if (getContext() != null) {
            if (!Utils.isNetworkConnected(getContext())) {
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    private void clearError() {
        etEmail.setError(null);
        etMobile.setError(null);
        etUserName.setError(null);
        etLastName.setError(null);
        etFirstName.setError(null);
        etZipCode.setError(null);
        etAddressTwo.setError(null);
        etAddressOne.setError(null);
    }

    private UserRegistrationRequestBean getUserRegistrationDetails() {
        Integer domain = (int) PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId();

        UserRegistrationRequestBean userRegistrationRequestBean = new UserRegistrationRequestBean();
        userRegistrationRequestBean.setContactPerson(etFirstName.getText().toString() + " "+etLastName.getText().toString());
        userRegistrationRequestBean.setMobileNumber(tvCountryCode.getText().toString() + getText(etMobile));

        if (!TextUtils.isEmpty(etAltMobile.getText().toString()))
            userRegistrationRequestBean.setAltMobileNumber(tvCountryCodeAltMobile.getText().toString() + etAltMobile.getText().toString());

        userRegistrationRequestBean.setAddressOne(etAddressOne.getText().toString());
        userRegistrationRequestBean.setAddressTwo(etAddressTwo.getText().toString());
        userRegistrationRequestBean.setEmailId(etEmail.getText().toString());
        userRegistrationRequestBean.setCountryCode(mCountryId);
        userRegistrationRequestBean.setCityCode(mCityId);
        userRegistrationRequestBean.setStateCode(mStateId);
        userRegistrationRequestBean.setMiddleName(etMiddleName.getText().toString());
        userRegistrationRequestBean.setFirstName(etFirstName.getText().toString());
        userRegistrationRequestBean.setUserName(etUserName.getText().toString());
        userRegistrationRequestBean.setLastName(etLastName.getText().toString());
        userRegistrationRequestBean.setRoleId(mRoleId);
        userRegistrationRequestBean.setDomainId(domain);
        userRegistrationRequestBean.setOrgId((int)PlayerData.getInstance().getOrgId());
        userRegistrationRequestBean.setZipCode(etZipCode.getText().toString());

        return userRegistrationRequestBean;
    }
}
