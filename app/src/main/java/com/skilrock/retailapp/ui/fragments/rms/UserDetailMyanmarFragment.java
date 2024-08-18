package com.skilrock.retailapp.ui.fragments.rms;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.SwitchCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.UserDetailResponseBean;
import com.skilrock.retailapp.models.rms.UserRegistrationRequestBean;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.ConfigData;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.rms.UserDetailViewModel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDetailMyanmarFragment extends BaseFragment implements View.OnClickListener {

    private UserDetailViewModel viewModel;
    private int mRoleId = -1;
    private boolean isEnabled = false;
    private  int selectedPostion = 0;
    private String passType;

    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean, menuBeanUserRegistration;
    private UserDetailResponseBean.Data userData;
    private String mUserId, mCountryCode;
    private TextInputLayout tilFirstName, tilLastName, tilUserName, tilNewPassword, tilConPassword;
    private TextInputLayout tilAltMobile, tilEmail;
    private RelativeLayout cityContainer, stateContainer, countryContainer, roleContainer;
    private String mCountryId, mStateId, mCityId, mEmailId, mMobileNumber;
    private SwitchCompat switchStatus;
    private int minPassLength = 0;
    private String digits;
    private Button buttonProceed;
    private int pwdType = 2;
    LinearLayout llMobile;
    private boolean isEdited = false;
    private TextView tvStatus, tvCountryCode;
    private LinearLayout tilMobile;
    private EditText etNewPassword, etConfirmPassword, etFirstName, etLastName, etMiddleName, etUserName, etMobile, etEmail;
    private final String RMS = "rms";

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!isEdited && !s.toString().isEmpty()) {
                enableUpdateButton(true);
                isEdited = true;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_details_mayanmar, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(this).get(UserDetailViewModel.class);
            viewModel.getUserDetailLiveData().observe(this, observer);
            viewModel.getBlockUserLiveData().observe(this, blockUserObserver);
            viewModel.getUnblockUserLiveData().observe(this, unBlockUserObserver);
            viewModel.getUpdateUserLiveData().observe(this, updateUserObserver);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeWidgets(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {
        TextView tvTitle        = view.findViewById(R.id.tvTitle);
        tvUserBalance           = view.findViewById(R.id.tvUserBalance);
        tvUsername              = view.findViewById(R.id.tvUserName);

        etUserName              = view.findViewById(R.id.et_user_name);
        etMobile                = view.findViewById(R.id.et_mobile_number);
        etEmail                 = view.findViewById(R.id.et_email);

        tilNewPassword          = view.findViewById(R.id.til_new_password);
        tilConPassword          = view.findViewById(R.id.til_con_password);
        etNewPassword           = view.findViewById(R.id.et_new_password);
        etConfirmPassword       = view.findViewById(R.id.et_confirm_password);
        tilUserName             = view.findViewById(R.id.til_user_name);
        tilMobile               = view.findViewById(R.id.til_mobile);
        tilEmail                = view.findViewById(R.id.til_email);

        switchStatus            = view.findViewById(R.id.switch_status);
        buttonProceed           = view.findViewById(R.id.button_update);
        tvStatus                = view.findViewById(R.id.tv_user_status);
        tvCountryCode           = view.findViewById(R.id.tvCountryCode);

        buttonProceed.setOnClickListener(this);

        Bundle bundle = getArguments();
        FragmentActivity activity = getActivity();
        if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
                mUserId = bundle.getString("user_id");
            }
            menuBean = bundle.getParcelable("MenuBean");
            menuBeanUserRegistration = bundle.getParcelable("MenuBean_UserRegistration");
        }

        refreshBalance();

        String url = Utils.getRmsUrlDetails(menuBean, getActivity(), "getUserDetails");

        ProgressBarDialog.getProgressDialog().showProgress(master);
        viewModel.userDetailApi(url, PlayerData.getInstance().getToken(), mUserId);

        etMobile.setOnFocusChangeListener((v, hasFocus) -> {
            changeMobileNumberBackground(hasFocus);
        });

        changeMobileNumberBackground(etMobile.hasFocus());
    }

    @Override
    public void onClick(View view) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        switch (view.getId()) {
            case R.id.button_update:
                if (validate()) {
                    String url1 = Utils.getRmsUrlDetails(menuBean, getActivity(), "updateUserDetails");
                    UserRegistrationRequestBean userDetails = getUserRegistrationDetails();

                    if (userDetails != null) {
                        ProgressBarDialog.getProgressDialog().showProgress(master);
                        viewModel.callUpdateUserApi(url1, userDetails);
                    } else
                        Toast.makeText(master, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch_status:
                blockUser(switchStatus.isChecked());
                break;
        }
    }

    private void blockUser(boolean block) {
        if (!Utils.isNetworkConnected(master)) {
            Toast.makeText( master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        String url = "";
        ProgressBarDialog.getProgressDialog().showProgress(master);
        if (block) {
            url = Utils.getRmsUrlDetails(menuBean, getActivity(), "unblockUser");
            viewModel.unBlockUserApi(url, PlayerData.getInstance().getToken(), mUserId);
        } else {
            url = Utils.getRmsUrlDetails(menuBean, getActivity(), "blockUser");
            viewModel.blockUserApi(url, PlayerData.getInstance().getToken(), mUserId);
        }
    }

    private void changeMobileNumberBackground(boolean hasFocus){
        if (SharedPrefUtil.getLanguage(master).equalsIgnoreCase(AppConstants.LANGUAGE_ARABIC)) {
            if (hasFocus) {
                tilMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_right_yellow_arabic));
                tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_left_yellow_arabic));
            } else {
                tilMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_right_arabic));
                tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_left_arabic));
            }
        } else {
            if (hasFocus) {
                tilMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_right_yellow));
                tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_left_yellow));
            } else {
                tilMobile.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_right));
                tvCountryCode.setBackground(ContextCompat.getDrawable(Objects.requireNonNull(master), R.drawable.border_rounded_left));
            }
        }
    }

    Observer<UserDetailResponseBean> updateUserObserver = new Observer<UserDetailResponseBean>() {
        @Override
        public void onChanged(@Nullable UserDetailResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.update_user), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.update_user), errorMsg, 1, getFragmentManager());
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.update_user), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                String msg = getString(R.string.user_updated_successfully);
                Utils.showCustomSuccessDialog(getContext(), getFragmentManager(), "", msg, 1);
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.update_user), getString(R.string.something_went_wrong));
        }
    };

    Observer<UserDetailResponseBean> observer = new Observer<UserDetailResponseBean>() {
        @Override
        public void onChanged(@Nullable UserDetailResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.search_user), getString(R.string.something_went_wrong), 1, getFragmentManager());
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.search_user), errorMsg, 1, getFragmentManager());
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage()) ? getString(R.string.some_internal_error) : response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.search_user), errorMsg, 1, getFragmentManager());
            } else if (response.getResponseData().getStatusCode() == 0) {
                userData = response.getResponseData().getData();
                setUserData(userData);
            } else
                Utils.showCustomErrorDialogPop(getContext(), getString(R.string.search_user), getString(R.string.something_went_wrong), 1, getFragmentManager());
        }
    };

    Observer<SimpleRmsResponseBean> blockUserObserver = new Observer<SimpleRmsResponseBean>() {
        @Override
        public void onChanged(@Nullable SimpleRmsResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.block_user), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.block_user), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.block_user), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                String msg = getString(R.string.user_blocked_success);
                Toast.makeText(master, msg, Toast.LENGTH_SHORT).show();
                setUserStatus(false);
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.block_user), getString(R.string.something_went_wrong));
        }
    };

    Observer<SimpleRmsResponseBean> unBlockUserObserver = new Observer<SimpleRmsResponseBean>() {
        @Override
        public void onChanged(@Nullable SimpleRmsResponseBean response) {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (response == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.unblock_user), getString(R.string.something_went_wrong));
            else if (response.getResponseCode() != 0) {
                //String errorMsg = TextUtils.isEmpty(response.getResponseMessage())?getString(R.string.some_internal_error):response.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.unblock_user), errorMsg);
            }
            else if(response.getResponseData().getStatusCode() != 0) {
                if (Utils.checkForSessionExpiry(master, response.getResponseData().getStatusCode()))
                    return;

                //String errorMsg = TextUtils.isEmpty(response.getResponseData().getMessage())?getString(R.string.some_internal_error):response.getResponseData().getMessage();
                String errorMsg = Utils.getResponseMessage(master, RMS, response.getResponseData().getStatusCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.unblock_user), errorMsg);
            }
            else if (response.getResponseData().getStatusCode() == 0) {
                String msg = getString(R.string.user_unblocked_success);
                Toast.makeText(master, msg, Toast.LENGTH_SHORT).show();
                setUserStatus(true);
            }
            else
                Utils.showCustomErrorDialog(getContext(), getString(R.string.unblock_user), getString(R.string.something_went_wrong));
        }
    };

    private void setUserData(UserDetailResponseBean.Data data) {
        if (!TextUtils.isEmpty(data.getMobileNumber())) {

            if (!TextUtils.isEmpty(ConfigData.getInstance().getConfigData().getcCOUNTRY_CODES())) {
                String[] arrayCountryCode = ConfigData.getInstance().getConfigData().getcCOUNTRY_CODES().split(",");

                if (arrayCountryCode.length > 0) {
                    String countryCode = "";
                    int index = 0;
                    for (; index<arrayCountryCode.length; index++) {
                        if (arrayCountryCode[index].equalsIgnoreCase(data.getMobileNumber().substring(0, arrayCountryCode[index].length()))) {
                            countryCode = arrayCountryCode[index];
                            break;
                        }
                    }

                    if (index == arrayCountryCode.length) {
                        Utils.showRedToast(master, getString(R.string.something_went_wrong));
                        master.getSupportFragmentManager().popBackStack();
                    } else {
                        //String mobileNumber = BuildConfig.app_name.equalsIgnoreCase(AppConstants.CAMEROON) ? data.getMobileNumber().substring(data.getMobileNumber().length() - 9) : data.getMobileNumber().substring(data.getMobileNumber().length() - 10);

                        //String countryCode = data.getMobileNumber().replace(mobileNumber, "");
                        String mobileNumber = data.getMobileNumber().replace(countryCode, "");

                        etUserName.setText(data.getUsername());
                        etMobile.setText(mobileNumber);

                        mCountryCode = data.getMobileCode();
                        mMobileNumber = mobileNumber;

                        if (mCountryCode == null || mCountryCode.isEmpty())
                            tvCountryCode.setText(ConfigData.getInstance().getConfigData().getCOUNTRYCODE());
                        else
                            tvCountryCode.setText(mCountryCode);

                        etEmail.setText(data.getEmailId());
                        mCountryId = data.getCountryCode();

                        switchStatus.setChecked(data.getStatus() != 0);
                        switchStatus.setOnClickListener(this);
                        mEmailId = data.getEmailId();

                        setUserStatus(data.getStatus() == 1);
                        enableUpdateButton(false);

                        etMobile.addTextChangedListener(watcher);
                        etEmail.addTextChangedListener(watcher);
                        etNewPassword.addTextChangedListener(watcher);


                        String pwdRegex = ConfigData.getInstance().getConfigData().getRETAILPASSWORDREGEX();

                        if (pwdRegex.equals("^[0-9]{5,5}$")) {
                            minPassLength = 5;
                            pwdType = InputType.TYPE_CLASS_NUMBER;
                            etNewPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                            tilNewPassword.setHint(getString(R.string.new_pin));
                            tilConPassword.setHint(getString(R.string.confirm_pin));
                            digits = getString(R.string.number_only_digits);
                            passType = getString(R.string.pin);
                        } else if (pwdRegex.equals("^[0-9]{8,16}$")) {
                            minPassLength = 8;
                            pwdType = InputType.TYPE_CLASS_NUMBER;
                            etNewPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                            digits = getString(R.string.number_only_digits);
                            passType = getString(R.string.password_);
                        } else if (pwdRegex.equals("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,16}$")) {
                            minPassLength = 8;
                            pwdType = InputType.TYPE_CLASS_TEXT;
                            etNewPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                            digits = getString(R.string.alpha_numeric);
                            passType = getString(R.string.password_);
                        }

                        etNewPassword.setInputType(pwdType | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                        etConfirmPassword.setInputType(pwdType | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                        etNewPassword.setKeyListener(DigitsKeyListener.getInstance(digits));
                        etConfirmPassword.setKeyListener(DigitsKeyListener.getInstance(digits));
                    }
                } else {
                    Utils.showRedToast(master, getString(R.string.some_technical_issue));
                    master.getSupportFragmentManager().popBackStack();
                }
            } else {
                Utils.showRedToast(master, getString(R.string.some_internal_error));
                master.getSupportFragmentManager().popBackStack();
            }
        } else {
            Utils.showRedToast(master, getString(R.string.some_internal_error));
            master.getSupportFragmentManager().popBackStack();
        }
    }

    private boolean validate() {
        Utils.vibrate(Objects.requireNonNull(getContext()));

        if (TextUtils.isEmpty(etUserName.getText().toString().trim())) {
            etUserName.setError(getString(R.string.this_field_cannot_be_empty));
            etUserName.requestFocus();
            tilUserName.startAnimation(Utils.shakeError());
            return false;
        }

        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError(getString(R.string.this_field_cannot_be_empty));
            etEmail.requestFocus();
            tilEmail.startAnimation(Utils.shakeError());
            return false;
        }

        if (!Utils.isEmailValid(etEmail.getText().toString().trim())) {
            Toast.makeText(master, getString(R.string.peovide_valid_email), Toast.LENGTH_SHORT).show();
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
            if (etMobile.getText().toString().trim().length() < 10) {
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
        } else {
            if (etMobile.getText().toString().trim().length() < 10) {
                etMobile.setError(getString(R.string.invalid_mobile_number));
                etMobile.requestFocus();
                tilMobile.startAnimation(Utils.shakeError());
                return false;
            }
        }

        if (!etNewPassword.getText().toString().isEmpty() || !etConfirmPassword.getText().toString().isEmpty()) {

            if (etNewPassword.getText().toString().isEmpty()) {
                etNewPassword.setError(getString(R.string.required_filed));
                etNewPassword.requestFocus();
                tilNewPassword.startAnimation(Utils.shakeError());
                return false;
            } else if (etConfirmPassword.getText().toString().isEmpty()) {
                etConfirmPassword.setError(getString(R.string.required_filed), null);
                etConfirmPassword.requestFocus();
                tilConPassword.startAnimation(Utils.shakeError());
                return false;
            }
            if (getText(etNewPassword).length() < minPassLength) {
                etNewPassword.setError(getString(R.string.password_min_length, passType, minPassLength));
                etNewPassword.requestFocus();
                tilNewPassword.startAnimation(Utils.shakeError());
                return false;
            }

            if (getText(etConfirmPassword).length() < minPassLength) {
                etConfirmPassword.setError(getString(R.string.password_min_length, passType, minPassLength));
                etConfirmPassword.requestFocus();
                tilConPassword.startAnimation(Utils.shakeError());
                return false;
            }

            if (!validatePassword(etNewPassword.getText().toString())) {
                etNewPassword.setError(getString(R.string.invalid_new_password));
                etNewPassword.requestFocus();
                tilNewPassword.startAnimation(Utils.shakeError());
                return false;
            }

            if (!validatePassword(etConfirmPassword.getText().toString())) {
                etConfirmPassword.setError(getString(R.string.invalid_new_password));
                etConfirmPassword.requestFocus();
                etConfirmPassword.startAnimation(Utils.shakeError());
                return false;
            }

            if (!getText(etNewPassword).equalsIgnoreCase(getText(etConfirmPassword))) {
                if (passType.equalsIgnoreCase(getString(R.string.pin)))
                    etConfirmPassword.setError(getString(R.string.pin_mismatch));
                else
                    etConfirmPassword.setError(getString(R.string.password_mismatch));

                etConfirmPassword.requestFocus();
                tilNewPassword.startAnimation(Utils.shakeError());
                tilConPassword.startAnimation(Utils.shakeError());
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

    private boolean validatePassword(String text) {
        String pwdRegex = ConfigData.getInstance().getConfigData().getRETAILPASSWORDREGEX();
        Pattern pattern = Pattern.compile(pwdRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }

    private UserRegistrationRequestBean getUserRegistrationDetails() {
        UserRegistrationRequestBean userRegistrationRequestBean = new UserRegistrationRequestBean();

        if (!mEmailId.trim().equalsIgnoreCase(etEmail.getText().toString().trim()))
            userRegistrationRequestBean.setEmailId(etEmail.getText().toString());

        userRegistrationRequestBean.setCountryCode(mCountryId);
        userRegistrationRequestBean.setCityCode(mCityId);
        userRegistrationRequestBean.setUserId(mUserId);
        userRegistrationRequestBean.setStateCode(mStateId);

        if (!mMobileNumber.equalsIgnoreCase(etMobile.getText().toString().trim())) {
            userRegistrationRequestBean.setMobileNumber(etMobile.getText().toString());
            userRegistrationRequestBean.setMobileCode(mCountryCode);
        }

        if (!etNewPassword.getText().toString().isEmpty() || !etConfirmPassword.getText().toString().isEmpty())
            userRegistrationRequestBean.setPassword(etNewPassword.getText().toString());

        userRegistrationRequestBean.setUserName(etUserName.getText().toString());

        return userRegistrationRequestBean;
    }
    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void setUserStatus(boolean isActive) {
        tvStatus.setText(isActive ? getString(R.string.active) : getString(R.string.inactive));

        buttonProceed.setBackground(isActive && isEdited ? master.getResources().getDrawable(R.drawable.button_rounded_bg) :
                master.getResources().getDrawable(R.drawable.button_backgroud_disabled));
        buttonProceed.setTextColor(isActive && isEdited ? getResources().getColor(R.color.colorWhite) : getResources().getColor(R.color.colorWhite));
        buttonProceed.setClickable(isActive && isEdited);
        buttonProceed.setEnabled(isActive & isEdited);

        etMobile.setFocusable(isActive);
        etEmail.setFocusable(isActive);
        etNewPassword.setFocusable(isActive);
        etConfirmPassword.setFocusable(isActive);

        etMobile.setFocusableInTouchMode(isActive);
        etEmail.setFocusableInTouchMode(isActive);
        etNewPassword.setFocusableInTouchMode(isActive);
        etConfirmPassword.setFocusableInTouchMode(isActive);

        etMobile.setEnabled(isActive);
        etEmail.setEnabled(isActive);
        etNewPassword.setEnabled(isActive);
        etConfirmPassword.setEnabled(isActive);
    }

    private void enableUpdateButton(boolean enable) {
        if (!tvStatus.getText().toString().equalsIgnoreCase(getString(R.string.active))) return;
        buttonProceed.setBackground(enable ? master.getResources().getDrawable(R.drawable.button_rounded_bg) :
                master.getResources().getDrawable(R.drawable.button_backgroud_disabled));
        buttonProceed.setTextColor(enable ? getResources().getColor(R.color.colorWhite) : getResources().getColor(R.color.colorWhite));
        buttonProceed.setClickable(enable);
        buttonProceed.setEnabled(enable);
    }
}
