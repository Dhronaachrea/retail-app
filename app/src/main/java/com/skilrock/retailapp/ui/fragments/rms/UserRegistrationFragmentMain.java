package com.skilrock.retailapp.ui.fragments.rms;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.rms.CheckUserNameResponseBean;
import com.skilrock.retailapp.models.rms.GetRoleListResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.RoleListData;
import com.skilrock.retailapp.models.rms.StateAndCityListResponseBean;
import com.skilrock.retailapp.models.rms.UserRegistrationRequestBean;
import com.skilrock.retailapp.models.rms.UserRegistrationResponseBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.UserRegistrationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRegistrationFragmentMain extends BaseFragment implements View.OnClickListener {

    private UserRegistrationViewModel viewModel;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private Button buttonProceed;
    private List<RoleListData> mRoleListData = new ArrayList<>();
    public int mRoleId = -1;
    public EditText etMobile, etEmail, etAltMobile;
    public TextView tvCountryCode, tvCountryCodeAltMobile;
    private TextView textRole;
    private TextInputLayout tilEmail;
    private LinearLayout tilMobile, tilAltMobile;
    private Spinner spinnerRole;
    public String mCountryCode;
    private RelativeLayout roleContainer, rlUserName;
    private boolean isUserVerified = false;
    public EditText etFirstName, etLastName, etMiddleName, etUserName, etContactPerson;
    public TextView tvValidUser, tvMobileAsUserName;
    private Button btnCheckUser;
    private TextInputLayout tilFirstName, tilLastName, tilUserName;
    private List<StateAndCityListResponseBean.City> mCityListData = new ArrayList<>();
    private boolean mRequiredUsername = true;
    private final String RMS = "rms";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_registration_main, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(UserRegistrationViewModel.class);
        viewModel.getLiveData().observe(this, checkUserNameObserver);
        viewModel.getRoleListLiveData().observe(this, roleListObserver);
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

        etMobile                = view.findViewById(R.id.et_mobile_number);
        etAltMobile             = view.findViewById(R.id.et_alt_mobile);
        rlUserName              = view.findViewById(R.id.view_username);
        etEmail                 = view.findViewById(R.id.et_email);
        textRole                = view.findViewById(R.id.tv_role);
        spinnerRole             = view.findViewById(R.id.spinnerRole);
        tilMobile               = view.findViewById(R.id.llMobile);
        tilAltMobile            = view.findViewById(R.id.til_alt_mobile);
        tilEmail                = view.findViewById(R.id.til_email);
        roleContainer           = view.findViewById(R.id.spinnerContainer);
        tvCountryCode           = view.findViewById(R.id.tvCountryCode);
        tvCountryCodeAltMobile  = view.findViewById(R.id.tvCountryCode_alt_mobile);
        buttonProceed           = view.findViewById(R.id.button_proceed);
        etFirstName             = view.findViewById(R.id.et_first_name);
        etMiddleName            = view.findViewById(R.id.et_middle_name);
        etLastName              = view.findViewById(R.id.et_last_name);
        etUserName              = view.findViewById(R.id.et_user_name);
        etContactPerson         = view.findViewById(R.id.et_contact_person);
        textRole                = view.findViewById(R.id.tv_role);
        tvValidUser             = view.findViewById(R.id.tv_valid_user_name);
        buttonProceed           = view.findViewById(R.id.button_proceed);
        tvMobileAsUserName      = view.findViewById(R.id.tv_mobile_status);

        tilFirstName            = view.findViewById(R.id.til_first_name);
        tilLastName             = view.findViewById(R.id.til_last_name);
        tilUserName             = view.findViewById(R.id.til_user_name);

        btnCheckUser            = view.findViewById(R.id.btn_check_user);

        btnCheckUser.setOnClickListener(this);
        tvCountryCode.setOnClickListener(this);
        tvCountryCodeAltMobile.setOnClickListener(this);
        buttonProceed.setOnClickListener(this);

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR)) {
            setEditTextMaxLength(etMobile, 11);
            setEditTextMaxLength(etAltMobile, 11);
        }

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();

        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }

        refreshBalance();

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRoleId = mRoleListData.get(position).getRoleId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mCountryCode = ConfigData.getInstance().getConfigData().getCOUNTRYCODE();

/*        if (mCountryCode == null || mCountryCode.isEmpty()) {
            Utils.countryCodeFocusOperation(master, BuildConfig.app_name);
            Utils.countryCodeFocusOperationForAltMobile(master);

            tvCountryCode.setOnClickListener(this);
            tvCountryCodeAltMobile.setOnClickListener(this);
        } else {
            tvCountryCode.setText(mCountryCode);
            tvCountryCodeAltMobile.setText(mCountryCode);
        }*/
        Utils.countryCodeFocusOperation(master, BuildConfig.app_name);
        Utils.countryCodeFocusOperationForAltMobile(master, BuildConfig.app_name);

        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "getRoleList");
        viewModel.getRoleList(url, PlayerData.getInstance().getToken());

        ProgressBarDialog.getProgressDialog().showProgress(master);

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
                tvValidUser.setVisibility(View.GONE);
                etUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });

        if (ConfigData.getInstance().getConfigData().getuUSE_MOBILE_AS_USERNAME().equalsIgnoreCase("yes")) {
            rlUserName.setVisibility(View.GONE);
            mRequiredUsername = false;
            tvMobileAsUserName.setVisibility(View.VISIBLE);
        }

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.ACDC) || BuildConfig.app_name.equalsIgnoreCase(AppConstants.OLA_MYANMAR))
            tvMobileAsUserName.setVisibility(View.GONE);

        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC)) {
            if (etMobile.hasFocus()) {
                tilMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_right_yellow_arabic));
                tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_left_yellow_arabic));
            } else {
                tilMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_right_arabic));
                tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_left_arabic));
            }
            if (etAltMobile.hasFocus()) {
                tilAltMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_right_yellow_arabic));
                tvCountryCodeAltMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_left_yellow_arabic));
            } else {
                tilAltMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_right_arabic));
                tvCountryCodeAltMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_left_arabic));
            }
        }
    }

    Observer<CheckUserNameResponseBean> checkUserNameObserver = new Observer<CheckUserNameResponseBean>() {
        @Override
        public void onChanged(@Nullable CheckUserNameResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() == 0) {

                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                setUserView(response);

            } else {
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.reset_password), errorMsg);
            }
        }
    };

    Observer<GetRoleListResponseBean> roleListObserver = new Observer<GetRoleListResponseBean>() {
        @Override
        public void onChanged(@Nullable GetRoleListResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.role), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.role), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.role), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() == 0) {
                mRoleListData = response.getResponseData().getData();
                setRoleListAdapter(mRoleListData);
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.role), getString(R.string.something_went_wrong));
        }
    };

    Observer<UserRegistrationResponseBean> userRegistrationObserver = new Observer<UserRegistrationResponseBean>() {
        @Override
        public void onChanged(@Nullable UserRegistrationResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user_registration), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage()) ? getString(R.string.some_internal_error) : response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user_registration), errorMsg);
            } else if (response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user_registration), errorMsg);
            } else if (response.getResponseData().getStatusCode() == 0) {
                String msg = getString(R.string.user_registered_successfully);
                Utils.showCustomSuccessDialog(getContext(), getFragmentManager(), "", msg, 1);
            } else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.user_registration), getString(R.string.something_went_wrong));
        }
    };

    public void setEditTextMaxLength(EditText editText, int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(filterArray);
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_proceed:
                if (!Utils.isNetworkConnected(master)) {
                    Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                if (validate()) {
                    String url1 = Utils.getRmsUrlDetails(menuBean, getActivity(), "doUserRegistration");
                    UserRegistrationRequestBean userDetails = getUserRegistrationDetails();
                    if (userDetails != null) {
                        ProgressBarDialog.getProgressDialog().showProgress(master);
                        viewModel.callUserRegistration(url1, userDetails);
                    } else
                        Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvCountryCode:
                Utils.openCountryCodeDialogFromConfig(getActivity(),ConfigData.getInstance().getConfigData().getcCOUNTRY_CODES());
                break;
            case R.id.tvCountryCode_alt_mobile:
                Utils.openCountryCodeDialogForAltMobile(getActivity(), ConfigData.getInstance().getConfigData().getcCOUNTRY_CODES());
                break;
            case R.id.btn_check_user:
                isUserVerified = false;
                if (validateUserName()) {
                    tvValidUser.setVisibility(View.GONE);
                    checkUserName(etUserName.getText().toString().trim());
                }
        }
    }

    public boolean validateUserName() {
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

    public void checkUserName(String userName) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        String url = Utils.getRmsUrlDetails(menuBean, getContext(), "checkLoginName");
        ProgressBarDialog.getProgressDialog().showProgress(master);
        viewModel.checkUserNameApi(url, PlayerData.getInstance().getToken(), userName);
    }

    public void callStateListApi(String mCountryId) {
        ProgressBarDialog.getProgressDialog().showProgress(master);

        viewModel.getStateAndCityList(Utils.getRmsUrlDetails(menuBean, getContext(),
                "getStateAndCityList"), PlayerData.getInstance().getToken(), mCountryId);
    }

    public void setRoleListAdapter(List<RoleListData> listData) {
        mRoleListData = listData;

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

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));
        if (mRoleId == -1) {
            Toast.makeText(master, getString(R.string.select_role_id), Toast.LENGTH_SHORT).show();
            roleContainer.startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(etFirstName.getText().toString().trim())) {
            etFirstName.setError(getString(R.string.this_field_cannot_be_empty));
            etFirstName.requestFocus();
            tilFirstName.startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(etLastName.getText().toString().trim())) {
            etLastName.setError(getString(R.string.this_field_cannot_be_empty));
            etLastName.requestFocus();
            tilLastName.startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError(getString(R.string.this_field_cannot_be_empty));
            etEmail.requestFocus();
            tilEmail.startAnimation(Utils.shakeError());
            return false;
        }

        if (!Utils.isEmailValid(etEmail.getText().toString().trim())) {
            etEmail.setError(getString(R.string.peovide_valid_email));
            etEmail.requestFocus();
            tilEmail.startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(etMobile.getText().toString().trim())) {
            etMobile.setError(getString(R.string.this_field_cannot_be_empty));
            etMobile.requestFocus();
            tilMobile.startAnimation(Utils.shakeError());
            return false;
        }

        if (tvCountryCode.getText().toString().equalsIgnoreCase("+91")) {
            if (etMobile.getText().toString().trim().length() < 10 || etMobile.getText().toString().trim().length() > 10) {
                etMobile.setError(getString(R.string.invalid_mobile_number));
                etMobile.requestFocus();
                tilMobile.startAnimation(Utils.shakeError());
                return false;
            }
        } else if (tvCountryCode.getText().toString().equalsIgnoreCase("+95")) {
            if (etMobile.getText().toString().trim().length() < 8 || etMobile.getText().toString().trim().length() > 11) {
                etMobile.setError(getString(R.string.invalid_mobile_number));
                etMobile.requestFocus();
                tilMobile.startAnimation(Utils.shakeError());
                return false;
            }
        } else if (tvCountryCode.getText().toString().equalsIgnoreCase("+237")) {
            if (etMobile.getText().toString().trim().length() < 9 || etMobile.getText().toString().trim().length() > 9) {
                etMobile.setError(getString(R.string.invalid_mobile_number));
                etMobile.requestFocus();
                tilMobile.startAnimation(Utils.shakeError());
                return false;
            }
        } else {
            if (etMobile.getText().toString().trim().length() < 9) {
                etMobile.setError(getString(R.string.invalid_mobile_number));
                etMobile.requestFocus();
                tilMobile.startAnimation(Utils.shakeError());
                return false;
            }
        }

        if (tvCountryCodeAltMobile.getText().toString().equalsIgnoreCase("+91")) {
            if (etAltMobile.getText().toString().trim().length() < 10 || etAltMobile.getText().toString().trim().length() > 10) {
                etAltMobile.setError(getString(R.string.invalid_mobile_number));
                etAltMobile.requestFocus();
                tilAltMobile.startAnimation(Utils.shakeError());
                return false;
            }
        } else if (tvCountryCodeAltMobile.getText().toString().equalsIgnoreCase("+95")) {
            if (etAltMobile.getText().toString().trim().length() < 8 || etAltMobile.getText().toString().trim().length() > 11) {
                etAltMobile.setError(getString(R.string.invalid_mobile_number));
                etAltMobile.requestFocus();
                tilAltMobile.startAnimation(Utils.shakeError());
                return false;
            }
        } else if (tvCountryCodeAltMobile.getText().toString().equalsIgnoreCase("+237 ")) {
            if (etAltMobile.getText().toString().trim().length() < 9 || etAltMobile.getText().toString().trim().length() > 9) {
                etAltMobile.setError(getString(R.string.invalid_mobile_number));
                etAltMobile.requestFocus();
                tilAltMobile.startAnimation(Utils.shakeError());
                return false;
            }
        } else {
            if (etAltMobile.getText().toString().trim().length() < 9) {
                etAltMobile.setError(getString(R.string.invalid_mobile_number));
                etAltMobile.requestFocus();
                tilAltMobile.startAnimation(Utils.shakeError());
                return false;
            }
        }

        if (mRequiredUsername) {
            if (TextUtils.isEmpty(etUserName.getText().toString().trim())) {
                tilUserName.setError(getString(R.string.this_field_cannot_be_empty));
                etUserName.requestFocus();
                tilUserName.startAnimation(Utils.shakeError());
                return false;
            }

            if (!isUserVerified) {
                tilUserName.setError(getString(R.string.verify_user_name));
                etUserName.requestFocus();
                tilUserName.startAnimation(Utils.shakeError());
                return false;
            }
        }
        if (getContext() != null) {
            if (!Utils.isNetworkConnected(getContext())) {
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    public void setUserView(CheckUserNameResponseBean responseBean) {
        if (responseBean.getResponseData().getStatusCode() == 0) {
            etUserName.setError(null);
            isUserVerified = false;
            etUserName.requestFocus();
            // etUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_cross, 0);

            tilUserName.setError(getString(R.string.user_name_already_exists));
        } else {
            tilUserName.setError(null);
            isUserVerified = true;
            etUserName.clearFocus();
            etUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_check, 0);
          /*  Drawable image = getResources().getDrawable(R.drawable.icon_check);
            int h = image.getIntrinsicHeight();
            int w = image.getIntrinsicWidth();
            image.setBounds(0, 0, w, h);
            etUserName.setCompoundDrawables(null, null, image, null);*/
            // etUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_check, 0);
        }
    }

    private UserRegistrationRequestBean getUserRegistrationDetails() {
        Integer domain = (int) PlayerData.getInstance().getLoginData().getResponseData().getData().getDomainId();

        UserRegistrationRequestBean userRegistrationRequestBean = new UserRegistrationRequestBean();
        userRegistrationRequestBean.setContactPerson(etFirstName.getText().toString() + etLastName.getText().toString());
        userRegistrationRequestBean.setMobileNumber(getText(etMobile));
        userRegistrationRequestBean.setMobileCode(tvCountryCode.getText().toString());
        if (!TextUtils.isEmpty(etAltMobile.getText().toString()))
            userRegistrationRequestBean.setAltMobileNumber(tvCountryCodeAltMobile.getText().toString() + etAltMobile.getText().toString());

        userRegistrationRequestBean.setEmailId(etEmail.getText().toString());
        userRegistrationRequestBean.setMiddleName("");
        userRegistrationRequestBean.setFirstName(etFirstName.getText().toString());

        if (mRequiredUsername)
            userRegistrationRequestBean.setUserName(etUserName.getText().toString());
        else
            userRegistrationRequestBean.setUserName(tvCountryCode.getText().toString().replace("+", "") + getText(etMobile));

        userRegistrationRequestBean.setLastName(etLastName.getText().toString());
        userRegistrationRequestBean.setRoleId(mRoleId);
        userRegistrationRequestBean.setDomainId(domain);
        userRegistrationRequestBean.setOrgId((int) PlayerData.getInstance().getOrgId());

        return userRegistrationRequestBean;
    }

    public void clearError() {
        etEmail.setError(null);
        etMobile.setError(null);
        etUserName.setError(null);
        etLastName.setError(null);
        etFirstName.setError(null);
    }
}
