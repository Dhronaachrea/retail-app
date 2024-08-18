package com.skilrock.retailapp.ui.fragments.ola

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.skilrock.retailapp.BuildConfig
import com.skilrock.retailapp.R
import com.skilrock.retailapp.dialog.SuccessDialog
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarKotlinResponseData
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarRequestBean
import com.skilrock.retailapp.models.rms.HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList
import com.skilrock.retailapp.models.rms.LoginBean
import com.skilrock.retailapp.ui.fragments.BaseFragment
import com.skilrock.retailapp.utils.*
import com.skilrock.retailapp.viewmodels.ola.OlaRegistrationFragmentMyanmarViewModel
import kotlinx.android.synthetic.main.layout_app_bar.*
import kotlinx.android.synthetic.main.layout_country_code_edittext.*
import kotlinx.android.synthetic.main.ola_registration_fragment_myanmar_fragment.*


class OlaRegistrationFragmentMyanmar : BaseFragment() {

    private lateinit var viewModel: OlaRegistrationFragmentMyanmarViewModel
    private var depositErrorMessage: String = ""
    private var registrationResponse: OlaRegistrationMyanmarKotlinResponseData? = null
    private var menuBean: MenuBeanList? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(OlaRegistrationFragmentMyanmarViewModel::class.java)
        viewModel.getRegistrationLiveData().observe(this, observerRegistration)
        viewModel.getLoginLiveData().observe(this, observerBalance)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ola_registration_fragment_myanmar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialSetups(view)
    }

    private fun initialSetups(view: View) {
        tvUsername      = view.findViewById(R.id.tvUserName)
        tvUserBalance   = view.findViewById(R.id.tvUserBalance)
        refreshBalance()

        val bundle = arguments
        val activity = activity
        if (bundle != null) {
            if (activity != null) {
                activity.title = bundle.getString("title")
                tvTitle.text = bundle.getString("title")
            }
            menuBean = bundle.getParcelable("MenuBean")
        }

        et_mobile_number.filters = arrayOf<InputFilter>(LengthFilter(11))
        Utils.countryCodeFocusOperation(master, BuildConfig.app_name)
        tvCountryCode.setOnClickListener { Utils.openCountryCodeDialog(getActivity(), tvCountryCode, ConfigData.getInstance().configData.getcCOUNTRY_CODES()) }
        button.setOnClickListener { if (validate()) register() }
    }

    private fun validate(): Boolean {
        if (context != null) {
            if (!Utils.isNetworkConnected(context)) {
                Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show()
                return false
            }
        }

        if (et_mobile_number.text.toString().trim().isEmpty()) {
            et_mobile_number.error = getString(R.string.this_field_cannot_be_empty)
            et_mobile_number.requestFocus()
            llMobile.startAnimation(Utils.shakeError())
            return false
        }

        if (et_mobile_number.text.toString().trim().length < 8 || et_mobile_number.text.toString().trim().length > 11) {
            et_mobile_number.error = getString(R.string.invalid_mobile_number)
            et_mobile_number.requestFocus()
            llMobile.startAnimation(Utils.shakeError())
            return false
        }

        if (etEmail.text.toString().trim().isNotEmpty() && !Utils.isEmailValid(etEmail.text.toString().trim())) {
            etEmail.error = getString(R.string.peovide_valid_email)
            etEmail.requestFocus()
            tilEmail.startAnimation(Utils.shakeError())
            return false
        }

        if (etAmount.text.toString().trim().isNotEmpty()) {
            try {
                val amt = etAmount.text.toString().trim().toDouble()
                if (amt <= 0) {
                    etAmount.error = getString(R.string.enter_valid_amount)
                    etAmount.requestFocus()
                    tilAmount.startAnimation(Utils.shakeError())
                    return false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                etAmount.error = getString(R.string.enter_valid_amount)
                etAmount.requestFocus()
                tilAmount.startAnimation(Utils.shakeError())
                return false
            }
        }

        return true
    }

    private fun register() {
        val urlBean = Utils.getOlaUrlDetails(menuBean, context, "registration")
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(master)
            val amount  = if (etAmount.text.toString().trim().isEmpty()) 0.0 else etAmount.text.toString().trim().toDouble()
            val email   = if (etEmail.text.toString().trim().isEmpty()) "" else etEmail.text.toString().trim()

            val model = OlaRegistrationMyanmarRequestBean()
            model.appType = AppConstants.APP_TYPE
            model.depositAmt = amount
            model.deviceType = AppConstants.DEVICE_TYPE
            model.paymentType = AppConstants.PAYMENT_TYPE
            model.plrMerchantCode = AppConstants.PLR_MERCHANT_CODE
            model.retOrgId = PlayerData.getInstance().loginData.responseData.data.orgId
            model.retUserId = PlayerData.getInstance().userId.toInt()
            model.retailDomainCode = urlBean.retailDomainCode
            model.retailMerchantCode = urlBean.retailMerchantCode
            model.terminalId = ""
            model.token = PlayerData.getInstance().token.split(" ").toTypedArray()[1]
            model.phone = et_mobile_number.text.toString().trim()
            model.playerUserName = et_mobile_number.text.toString().trim()

            model.plrDomainCode = if (BuildConfig.FLAVOR.equals("uAT_OLA_MYANMAR", ignoreCase = true)) "www.bet2winasia.com" else "www.igamew.com"

            viewModel.callRegistrationApi(urlBean, model)
        }
    }

    private val observerRegistration = Observer<OlaRegistrationMyanmarKotlinResponseData> {
        depositErrorMessage = ""
        ProgressBarDialog.getProgressDialog().dismiss()

        if (it == null)
            Utils.showCustomErrorDialog(context, getString(R.string.registration_error), getString(R.string.something_went_wrong))
        else if (it.responseCode == 0) {
            registrationResponse = it
            val depositResponse = it.responseData?.depositResponseVO
            if (depositResponse != null) {
                if (depositResponse.respCode != 0 && depositResponse.respMsg != null)
                    depositErrorMessage = "\n\n(${getString(R.string.deposit_failed)} ${depositResponse.respMsg})"
            }

            ProgressBarDialog.getProgressDialog().showProgress(master)
            viewModel.callBalanceApi(PlayerData.getInstance().token)
        } else {
            val resCode: Int = it.responseCode ?: -1

            if (!Utils.checkForSessionExpiry(master, resCode)) {
                val errorMsg = if (TextUtils.isEmpty(it.responseMessage)) getString(R.string.some_internal_error) else it.responseMessage
                Utils.showCustomErrorDialog(context, getString(R.string.registration_error), errorMsg)
            }
        }
    }

    private val observerBalance = Observer<LoginBean> {
        ProgressBarDialog.getProgressDialog().dismiss()

        val errorMsg = getString(R.string.registration_success_error_fetch_balance)

        if (it == null)
            Utils.showCustomErrorDialogPop(context, getString(R.string.registration), errorMsg, 1, fragmentManager)
        else if (it.responseCode == 0) {
            val responseData = it.responseData
            if (responseData.statusCode == 0) {
                PlayerData.getInstance().setLoginData(master, it)
                refreshBalance()
                val successMsg = getString(R.string.player_registered_success_with_info) + depositErrorMessage
                val dialog = SuccessDialog(master, fragmentManager, "", successMsg, 1)
                dialog.show()
                if (dialog.window != null) {
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                }
            } else {
                Utils.showCustomErrorDialogPop(context, getString(R.string.registration), errorMsg, 1, fragmentManager)
            }
        } else {
            Utils.showCustomErrorDialogPop(context, getString(R.string.registration), errorMsg, 1, fragmentManager)
        }

    }

}
