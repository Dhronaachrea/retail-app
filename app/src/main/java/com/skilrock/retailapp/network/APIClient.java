package com.skilrock.retailapp.network;

import com.skilrock.retailapp.models.BetDeviceRequestBean;
import com.skilrock.retailapp.models.BetDeviceResponseBean;
import com.skilrock.retailapp.models.FieldX.FieldXChallanBean;
import com.skilrock.retailapp.models.FieldX.FieldXRetailerBean;
import com.skilrock.retailapp.models.FieldX.FieldxCollectionReportResponseBean;
import com.skilrock.retailapp.models.FieldX.FieldxDoPaymentBean;
import com.skilrock.retailapp.models.FieldX.FieldxDoPaymentRequestBean;
import com.skilrock.retailapp.models.FieldX.FieldxGetPayAmountBean;
import com.skilrock.retailapp.models.FieldX.FieldxRetailerResponseBean;
import com.skilrock.retailapp.models.GetPreAppVersionBean;
import com.skilrock.retailapp.models.OlaNetGamingExecutionResponseBean;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.SbsReprintResponseBean;
import com.skilrock.retailapp.models.SbsWinPayResponse;
import com.skilrock.retailapp.models.SbsWinVerifyResponse;
import com.skilrock.retailapp.models.SettleUserAccountRequestBean;
import com.skilrock.retailapp.models.SimpleRmsResponseBean;
import com.skilrock.retailapp.models.UserAccountResponseBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataRequestBean;
import com.skilrock.retailapp.models.drawgames.DrawFetchGameDataResponseBean;
import com.skilrock.retailapp.models.drawgames.DrawSchemaByGameResponseBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleRequestBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.PayPwtRequestBean;
import com.skilrock.retailapp.models.drawgames.PayPwtResponseBean;
import com.skilrock.retailapp.models.drawgames.QuickPickRequestBean;
import com.skilrock.retailapp.models.drawgames.QuickPickResponseBean;
import com.skilrock.retailapp.models.drawgames.ResultRequestBean;
import com.skilrock.retailapp.models.drawgames.ResultResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketReprintBean;
import com.skilrock.retailapp.models.drawgames.TicketVerificationGameResponseBean;
import com.skilrock.retailapp.models.drawgames.VerifyGameTicketRequest;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayRequestBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayResponseBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyRequestBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyResponseBean;
import com.skilrock.retailapp.models.ola.BalanceReportResponseBean;
import com.skilrock.retailapp.models.ola.InventoryFlowReportResponseBean;
import com.skilrock.retailapp.models.ola.OlaDepositRequestBean;
import com.skilrock.retailapp.models.ola.OlaDepositResponseBean;
import com.skilrock.retailapp.models.ola.OlaDomainResponseBean;
import com.skilrock.retailapp.models.ola.OlaMyPromoResponseBean;
import com.skilrock.retailapp.models.ola.OlaNetGamingResponseBean;
import com.skilrock.retailapp.models.ola.OlaOtpBean;
import com.skilrock.retailapp.models.ola.OlaPendingTransactionResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerDetailsResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerForgotPasswordRequestBean;
import com.skilrock.retailapp.models.ola.OlaPlayerForgotPasswordResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerSearchResponseBean;
import com.skilrock.retailapp.models.ola.OlaPlayerTransactionResponseBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarRequestBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationMyanmarResponseBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationRequestBean;
import com.skilrock.retailapp.models.ola.OlaRegistrationResponseBean;
import com.skilrock.retailapp.models.ola.OlaWithdrawalRequestBean;
import com.skilrock.retailapp.models.ola.OlaWithdrawalResponseBean;
import com.skilrock.retailapp.models.ola.OperationalReportResponseBean;
import com.skilrock.retailapp.models.ola.OrgUserResponseBeanNew;
import com.skilrock.retailapp.models.ola.PaymentReportResponseBean;
import com.skilrock.retailapp.models.ola.SettleTransactionRequestBean;
import com.skilrock.retailapp.models.rms.BillPaymentsResponseBean;
import com.skilrock.retailapp.models.rms.BillReportRequestBean;
import com.skilrock.retailapp.models.rms.BillReportResponseBean;
import com.skilrock.retailapp.models.rms.CashManagementPaymentRequestBean;
import com.skilrock.retailapp.models.rms.CashManagementPaymentResponseBean;
import com.skilrock.retailapp.models.rms.CashManagementTxnTypeResponseBean;
import com.skilrock.retailapp.models.rms.CashManagementUserRequestBean;
import com.skilrock.retailapp.models.rms.CashManagementUserResponseBeanNew;
import com.skilrock.retailapp.models.rms.ChangePasswordRequestBean;
import com.skilrock.retailapp.models.rms.ChangePasswordResponseBean;
import com.skilrock.retailapp.models.rms.CheckUserNameResponseBean;
import com.skilrock.retailapp.models.rms.CityListResponseBean;
import com.skilrock.retailapp.models.rms.ConfigurationResponseBean;
import com.skilrock.retailapp.models.rms.CountryListResponseBean;
import com.skilrock.retailapp.models.rms.DeviceRegistrationBean;
import com.skilrock.retailapp.models.rms.ForgotPasswordResetBean;
import com.skilrock.retailapp.models.rms.ForgotPasswordResponseBean;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.models.rms.GetRoleListResponseBean;
import com.skilrock.retailapp.models.rms.GetUserSearchRequestBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.rms.LedgerReportResponseBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.rms.LogoutResponseBean;
import com.skilrock.retailapp.models.rms.OlaReportResponseBean;
import com.skilrock.retailapp.models.rms.PaymentReportRequestBean;
import com.skilrock.retailapp.models.rms.QrCodeRegistrationRequestBean;
import com.skilrock.retailapp.models.rms.QrCodeRegistrationResponseBean;
import com.skilrock.retailapp.models.rms.SaleReportResponseBean;
import com.skilrock.retailapp.models.rms.SaleWinningReportRequestBean;
import com.skilrock.retailapp.models.rms.SearchUserRequestBean;
import com.skilrock.retailapp.models.rms.SearchUserResponseBeanNew;
import com.skilrock.retailapp.models.rms.ServiceListResponseBean;
import com.skilrock.retailapp.models.rms.SettlementDetailResponseBean;
import com.skilrock.retailapp.models.rms.SettlementListResponseBean;
import com.skilrock.retailapp.models.rms.StateAndCityListResponseBean;
import com.skilrock.retailapp.models.rms.StateListResponseBean;
import com.skilrock.retailapp.models.rms.SummarizedReportResponseBean;
import com.skilrock.retailapp.models.rms.TokenBean;
import com.skilrock.retailapp.models.rms.TransactionRemarksResponseBean;
import com.skilrock.retailapp.models.rms.UserDetailResponseBean;
import com.skilrock.retailapp.models.rms.UserRegistrationRequestBean;
import com.skilrock.retailapp.models.rms.UserRegistrationResponseBean;
import com.skilrock.retailapp.models.rms.VerifyPosRequestBean;
import com.skilrock.retailapp.models.rms.VerifyPosResponseBean;
import com.skilrock.retailapp.models.scratch.ActivateBookRequestBean;
import com.skilrock.retailapp.models.scratch.ChallanResponseBean;
import com.skilrock.retailapp.models.scratch.ClaimTicketRequestBean;
import com.skilrock.retailapp.models.scratch.ClaimTicketResponseNewBean;
import com.skilrock.retailapp.models.scratch.GetTicketStatusRequest;
import com.skilrock.retailapp.models.scratch.GetTicketStatusResponse;
import com.skilrock.retailapp.models.scratch.MultiClaimTicketRequest;
import com.skilrock.retailapp.models.scratch.MultiClaimTicketResponseBean;
import com.skilrock.retailapp.models.scratch.MultiSaleTicketRequestBean;
import com.skilrock.retailapp.models.scratch.QuickOrderRequestBean;
import com.skilrock.retailapp.models.scratch.QuickOrderResponseBean;
import com.skilrock.retailapp.models.scratch.ReceiveBookRequestBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseBean;
import com.skilrock.retailapp.models.scratch.ReturnChallanResponseHomeBean;
import com.skilrock.retailapp.models.scratch.SaleTicketRequestBean;
import com.skilrock.retailapp.models.scratch.ScratchReportBean;
import com.skilrock.retailapp.models.scratch.SubmitPackReturnResponseBean;
import com.skilrock.retailapp.models.scratch.SubmitReturnRequestBean;
import com.skilrock.retailapp.models.scratch.VerifyTicketRequestNewBean;
import com.skilrock.retailapp.models.scratch.VerifyTicketResponseNewBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIClient {
    @GET(APIConfig.getToken)
    Call<TokenBean> getToken(
            @Header("username") String username,
            @Header("password") String password,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Query("terminalId") String terminalId,
            @Query("modelCode") String modelCode

    );

    @GET(APIConfig.loginUrl)
    Call<LoginBean> getLogin(
            @Header("Authorization") String token
    );

    @GET
    Call<GameListBean> getGameList(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Query("request") String request
    );

    @POST
    Call<GetTicketStatusResponse> getTicketStatus(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Body GetTicketStatusRequest ticketStatusRequestBean);



    @GET
    Call<GameListBean> getNewGameList(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Query("userName") String userName,
            @Query("userSessionId") String userSessionId
    );

    @GET
    Call<ChallanResponseBean> getChallanDetails(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Query("userName") String userName,
            @Query("userSessionId") String userSessionId,
            @Query("dlChallanNumber") String dlChallanNumber
    );

    @GET
    Call<ChallanResponseBean> getChallanDetailsFieldX(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Query("userName") String userName,
            @Query("userSessionId") String userSessionId,
            @Query("dlChallanId") String dlChallanId,
            @Query("tag") String tag
    );

    @GET
    Call<ScratchReportBean> getScratchResport(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Query("userName") String userName,
            @Query("userSessionId") String userSessionId
    );

    @POST
    Call<ResponseCodeMessageBean> getTicketSold(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body SaleTicketRequestBean saleTicketRequestBean
    );

    @POST
    Call<ResponseCodeMessageBean> getMultiTicketSold(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body MultiSaleTicketRequestBean multiSaleTicketRequestBean
    );

    @POST
    Call<VerifyTicketResponseNewBean> verifyTicket(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body VerifyTicketRequestNewBean verifyTicketRequestNewBean
    );

    @POST
    Call<ClaimTicketResponseNewBean> claimTicket(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body ClaimTicketRequestBean claimTicketRequestBean
    );

    @POST
    Call<MultiClaimTicketResponseBean> multiClaimTicket(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body MultiClaimTicketRequest multiClaimTicketRequestBean
    );

    @POST
    Call<QuickOrderResponseBean> quickOrder(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body QuickOrderRequestBean quickOrderRequestBean
    );

    @POST
    Call<ResponseCodeMessageBean> bookActivation(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body ActivateBookRequestBean activateBookRequestBean
    );

    @POST
    Call<ResponseCodeMessageBean> bookReceive(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body ReceiveBookRequestBean receiveBookRequestBean
    );

    @POST
    Call<ChangePasswordResponseBean> changePassword(
            @Header("Authorization") String token,
            @Url String url,
            @Body ChangePasswordRequestBean changePasswordRequestBean
    );

    @GET(APIConfig.forgotPasswordOtpUrl)
    Call<ForgotPasswordResponseBean> forgotPasswordOtp(
            @Query("userName") String userName,
            @Query("mobileNumber") String mobileNumber
    );

    @POST(APIConfig.forgotPasswordResetUrl)
    Call<ForgotPasswordResponseBean> forgotPasswordReset(
            @Body ForgotPasswordResetBean forgotPasswordResetBean
    );

    @GET
    Call<LogoutResponseBean> logout(
            @Header("Authorization") String token,
            @Url String url
    );

    @GET(APIConfig.gameListTypeUrl)
    Call<HomeDataBean> getMainModules(
            @Header("Authorization") String token,
            @Query("userId") String userID,
            @Query("appType") String appType,
            @Query("engineCode") String engineCode,
            @Query("languageCode") String languageCode
    );

    @GET
    Call<OlaDomainResponseBean> getOlaDomain(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Query("retailDomainCode") String retailDomainCode,
            @Query("retOrgID") long retOrgID
    );

    @GET
    Call<OlaMyPromoResponseBean> getOlaPromo(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Accept") String contentType,
            @Query("retOrgID") long id
    );

    @POST
    Call<ResponseCodeMessageBean> olaRegistrationOtp(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Body OlaOtpBean olaOtpBean
    );

    @POST
    Call<OlaRegistrationResponseBean> postOlaRegistration(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Body OlaRegistrationRequestBean olaRegistrationOtpRequestBean
    );

    @POST
    Call<OlaRegistrationMyanmarResponseBean> postOlaMyanmarRegistration(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Body OlaRegistrationMyanmarRequestBean olaRegistrationOtpRequestBean
    );

    @POST
    Call<OlaDepositResponseBean> postOlaDeposit(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Body OlaDepositRequestBean olaDepositRequestBean
    );

    @POST
    Call<OlaWithdrawalResponseBean> postOlaWithdrawal(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Body OlaWithdrawalRequestBean olaDepositRequestBean
    );

    @POST
    Call<SimpleRmsResponseBean> postDeviceRegistration(
            @Url String url,
            @Header("Authorization") String token,
            @Body DeviceRegistrationBean olaDepositRequestBean
    );

    @GET
    Call<OlaReportResponseBean> getOlaReport(
            @Url String url,
            @Header("Authorization") String token,
            @Query("orgId") Long orgId,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("orgTypeCode") String orgTypeCode
    );

    @POST
    Call<PaymentReportResponseBean> getPaymentReports(
            @Url String url,
            @Header("Authorization") String token,
            @Body PaymentReportRequestBean paymentReportRequestBean
    );

    @GET
    Call<BalanceReportResponseBean> getBalanceReports(
            @Url String url,
            @Header("Authorization") String token,

            @Query("appType") String appType,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("domainId") Long domainId,
            @Query("languageCode") String languageCode,
            @Query("orgId") Long orgId
    );

    @GET
    Call<OperationalReportResponseBean> getOperationalReports(
            @Url String url,
            @Header("Authorization") String token,

            @Query("appType") String appType,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("domainId") Long domainId,
            @Query("languageCode") String languageCode,
            @Query("serviceCode") String serviceCode,
            @Query("orgId") Long orgId
    );

    @GET
    Call<InventoryFlowReportResponseBean> getInventoryFlowReport(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,

            @Query("fromDate") String startDate,
            @Query("toDate") String endDate,
            @Query("userName") String userName,
            @Query("userSessionId") String userSessionId
    );

    @GET
    Call<OlaPlayerTransactionResponseBean> getOlaPlayerLedgerReport(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,

            @Query("txnType") String txnType,
            @Query("fromDate") String fromDate,
            @Query("toDate") String toDate,
            @Query("pageIndex") Integer pageIndex,
            @Query("pageSize") Integer pageSize,
            @Query("playerDomainCode") String playerDomainCode,
            @Query("playerUserName") String playerUserName,
            @Query("retailDomainCode") String retailDomainCode,
            @Query("retOrgID") Long retOrgID
    );

    @GET
    Call<OlaPlayerDetailsResponseBean> getOlaPlayerDetailsReport(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,

            @Query("fromDate") String fromDate,
            @Query("toDate") String toDate,
            @Query("offset") Integer offset,
            @Query("limit") Integer limit,
            @Query("mobileNo") String mobileNo,
            @Query("plrDomainCode") String plrDomainCode,
            @Query("retailDomainCode") String retailDomainCode,
            @Query("retailOrgId") Long retailOrgId
    );

    @GET
    Call<OlaPlayerSearchResponseBean> getOlaPlayerSearch(
            @Url String url,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Header("username") String userName,

            @Query("retailDomainCode") String retailDomainCode,
            @Query("retailOrgID") Long retailOrgID,
            @Query("plrDomainCode") String plrDomainCode,
            @Query("pageIndex") Integer pageIndex,
            @Query("pageSize") Integer pageSize,
            @Query("firstName") String firstName,
            @Query("lastName") String lastName,
            @Query("phone") String phone
    );

    @GET
    Call<OlaPlayerSearchResponseBean> getOlaPlayerSearchMyanmar(
            @Url String url,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Header("username") String userName,

            @Query("retailDomainCode") String retailDomainCode,
            @Query("retailOrgID") Long retailOrgID,
            @Query("plrDomainCode") String plrDomainCode,
            @Query("pageIndex") Integer pageIndex,
            @Query("pageSize") Integer pageSize,
            @Query("plrUsername") String phone
    );

    @PUT
    Call<OlaPlayerForgotPasswordResponseBean> putOlaPlayerForgotPassword(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Body OlaPlayerForgotPasswordRequestBean olaPlayerForgotPasswordRequestBean
    );

    @POST
    Call<BillReportResponseBean> postBillReport(
            @Url String url,
            @Header("Authorization") String token,
            @Body BillReportRequestBean billReportRequestBean
    );

    @GET
    Call<BillPaymentsResponseBean> getBillPayments(
            @Url String url,
            @Header("Authorization") String token,
            @Query("orgId") Long orgId,
            @Query("domainId") Long domainId,
            @Query("billId") Long billId
    );

    @GET
    Call<CheckUserNameResponseBean> checkUserName(
            @Url String url,
            @Header("Authorization") String token,
            @Query("loginName") String loginName
    );

    @GET
    Call<GetRoleListResponseBean> getRoleList(
            @Url String url,
            @Header("Authorization") String token,
            @Query("orgTypeCode") String code
    );

    @GET
    Call<CountryListResponseBean> getCountryList(
            @Url String url,
            @Header("Authorization") String token
    );

    @GET
    Call<CityListResponseBean> getCityList(
            @Url String url,
            @Header("Authorization") String token,
            @Query("stateCode") String code
    );

    @GET
    Call<StateListResponseBean> getStateList(
            @Url String url,
            @Header("Authorization") String token,
            @Query("countryCode") String code
    );

    @GET
    Call<StateAndCityListResponseBean> getStateAndCityList(
            @Url String url,
            @Header("Authorization") String token,
            @Query("countryCode") String code
    );

    @POST
    Call<UserRegistrationResponseBean> userRegistration(
            @Url String url,
            @Header("Authorization") String token,
            @Body UserRegistrationRequestBean userRegistrationRequestBean
    );

    @POST
    Call<SearchUserResponseBeanNew> searchUser(
            @Url String url,
            @Header("Authorization") String token,
            @Body SearchUserRequestBean searchUserRequestBean);

    @GET
    Call<UserDetailResponseBean> getUserDetails(
            @Url String url,
            @Header("Authorization") String token,
            @Query("userId") String code
    );

    @GET
    Call<SimpleRmsResponseBean> blockUser(
            @Url String url,
            @Header("Authorization") String token,
            @Query("userId") String code
    );

    @GET
    Call<SimpleRmsResponseBean> unBlockUser(
            @Url String url,
            @Header("Authorization") String token,
            @Query("userId") String code
    );

    @POST
    Call<UserDetailResponseBean> updateUser(
            @Url String url,
            @Header("Authorization") String token,
            @Body UserRegistrationRequestBean userRegistrationRequestBean
    );

    @POST
    Call<CashManagementUserResponseBeanNew> postUserSearch(
            @Url String url,
            @Header("Authorization") String token,
            @Body CashManagementUserRequestBean cashManagementUserRequestBean
    );

    @GET
    Call<CashManagementTxnTypeResponseBean> getTransactionType(
            @Url String url,
            @Header("Authorization") String token,
            @Query("serviceCode") String serviceCode,
            @Query("isCommcheck") boolean isCommcheck
    );

    @POST
    Call<CashManagementPaymentResponseBean> postCashManagementPayment(
            @Url String url,
            @Header("Authorization") String token,
            @Body CashManagementPaymentRequestBean cashManagementPaymentRequestBean
    );

    @GET
    Call<LedgerReportResponseBean> getLedgerReport(
            @Url String url,
            @Header("Authorization") String token,
            @Query("orgId") long orgId,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("languageCode") String languageCode,
            @Query("appType") String appType
    );

    @POST
    Call<OrgUserResponseBeanNew> getOrgUser(
            @Url String url,
            @Header("Authorization") String token,
            @Body GetUserSearchRequestBean userRequestBean
    );

    @POST
    Call<QrCodeRegistrationResponseBean> postQrCodeRegistration(
            @Url String url,
            @Header("Authorization") String token,
            @Body QrCodeRegistrationRequestBean qrCodeRegistrationRequestBean
    );

    @GET
    Call<UserAccountResponseBean> getUserAccount(
            @Url String url,
            @Header("Authorization") String token,
            @Query("userId") String userId,
            @Query("languageCode") String languageCode,
            @Query("appType") String appType
    );

    @GET
    Call<SettlementListResponseBean> getSettlementList(
            @Url String url,
            @Header("Authorization") String token,
            @Query("userId") String userId,
            @Query("fromDate") String startDate,
            @Query("toDate") String endDate,
            @Query("languageCode") String languageCode,
            @Query("appType") String appType
    );

    @GET
    Call<SettlementDetailResponseBean> getSettlementDetails(
            @Url String url,
            @Header("Authorization") String token,
            @Query("settlementId") String userId,
            @Query("languageCode") String languageCode,
            @Query("appType") String appType
    );

    @GET
    Call<ConfigurationResponseBean> getConfig(
            @Url String url,
            @Header("Authorization") String token,
            @Query("domainId") Long domainId
    );

    @POST
    Call<SimpleRmsResponseBean> settleUserAccount(
            @Url String url,
            @Header("Authorization") String token,
            @Body SettleUserAccountRequestBean cashManagementPaymentRequestBean
    );

    @POST
    Call<SaleReportResponseBean> getSaleWinningReport(
            @Url String url,
            @Header("Authorization") String token,
            @Body SaleWinningReportRequestBean saleWinningReportRequestBean
    );

    @GET
    Call<ServiceListResponseBean> getServiceList(
            @Url String url,
            @Header("Authorization") String token
    );

    @GET
    Call<ReturnChallanResponseHomeBean> getReturnList(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Query("userSessionId") String userSessionId,
            @Query("userName") String userName,
            @Query("dlChallanNumber") String dlChallanNumber,
            @Query("retailerName") String retailerName
    );

    @GET
    Call<ReturnChallanResponseBean> getReturnListPacks(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Query("userSessionId") String userSessionId,
            @Query("userName") String userName,
            @Query("retailerName") String retailerName,
            @Query("dlChallanNumber") String dlChallanNumber
    );

    /*@GET(APIConfig.versionUrl)
    Call<VersionBean> getVersion(
            @Query("appType") String appType
    );*/

    @GET(APIConfig.versionUrl)
    Call<GetPreAppVersionBean> getVersion();

    @GET
    Call<OlaPendingTransactionResponseBean> getOlaPlayerPendingTransaction(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,

            @Query("txnType") String txnType,
            @Query("fromDate") String fromDate,
            @Query("toDate") String toDate,
            @Query("pageIndex") Integer pageIndex,
            @Query("pageSize") Integer pageSize,
            @Query("playerDomainCode") String playerDomainCode,
            @Query("playerUserName") String playerUserName,
            @Query("retailDomainCode") String retailDomainCode,
            @Query("retOrgID") Long retOrgID
    );

    @GET
    Call<OlaNetGamingResponseBean> getOlaNetGamingDetails(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,

            @Query("txnType") String txnType,
            @Query("fromDate") String fromDate,
            @Query("toDate") String toDate,
            @Query("pageIndex") Integer pageIndex,
            @Query("pageSize") Integer pageSize,
            @Query("playerDomainCode") String playerDomainCode,
            @Query("playerUserName") String playerUserName,
            @Query("retailDomainCode") String retailDomainCode,
            @Query("retOrgID") Long retOrgID
    );

    @POST
    Call<SimpleRmsResponseBean> settleTransaction(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Body SettleTransactionRequestBean settleTransactionRequestBean
    );

    @POST
    Call<SubmitPackReturnResponseBean> submitPackReturn(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Header("Content-Type") String contentType,
            @Body SubmitReturnRequestBean submitReturnRequestBean
    );

    @GET
    Call<SummarizedReportResponseBean> getSummarizedLedgerReport(
            @Url String url,
            @Header("Authorization") String token,
            @Query("orgId") long orgId,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("type") String type,
            @Query("languageCode") String languageCode,
            @Query("appType") String appType);

    @GET
    Call<TransactionRemarksResponseBean> getTxnRemarks(
            @Url String url,
            @Header("Authorization") String token,
            @Query("txnTypeCode") String txnTypeCode
    );

    @PUT
    Call<DrawFetchGameDataResponseBean> fetchGameData(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body DrawFetchGameDataRequestBean drawFetchGameDataRequestBean
    );

    @GET
    Call<DrawSchemaByGameResponseBean> fetchSchemaByGame(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Query("gameCode") String gameCode
    );

    @POST
    Call<QuickPickResponseBean> quickPick(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body QuickPickRequestBean quickPickRequestBean
    );

    @POST
    Call<FiveByNinetySaleResponseBean> fiveByNinetySale(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body FiveByNinetySaleRequestBean fiveByNinetySaleRequestBean
    );

    @POST
    Call<TicketVerificationGameResponseBean> ticketVerification(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body VerifyGameTicketRequest verifyGameTicketRequest
    );

    @POST
    Call<PayPwtResponseBean> peyPwt(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body PayPwtRequestBean payPwtRequestBean
    );

    @POST
    Call<FiveByNinetySaleResponseBean> reprint(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body TicketReprintBean ticketReprintBean
    );

    @POST
    Call<TicketCancelResponseBean> cancelTicket(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body TicketCancelBean ticketCancelBean
    );

    @POST
    Call<WinningClaimVerifyResponseBean> winningClaimVerify(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body WinningClaimVerifyRequestBean winningClaimVerifyRequestBean
    );

    @POST
    Call<WinningClaimPayResponseBean> winningClaimPay(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body WinningClaimPayRequestBean requestBean
    );

    @POST
    Call<ResultResponseBean> result(
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body ResultRequestBean requestBean
    );
    @POST
    Call<BetDeviceResponseBean> betDeviceVerify(
            @Url String url,
            @Header("Authorization") String token,
            @Body BetDeviceRequestBean betDeviceRequestBean
    );

    @POST(APIConfig.verifyPos)
    Call<VerifyPosResponseBean> postVerifyPos(
            @Header("Authorization") String token,
            @Body VerifyPosRequestBean verifyPosRequestBean
    );

    @GET
    Call<FieldXChallanBean> getRetailerChallan(
            @Url String url,
            @Header("clientId") String clientId,
            @Header("clientSecret") String clientSecret,
            @Query("userName") String userName,
            @Query("userSessionId") String userSessionId
    );

    @GET
    Call<FieldxGetPayAmountBean> fieldxGetPayAmount(
            @Url String url,
            @Header("Authorization") String Authorization,
            @Query("orgId") String orgId
    );

    @POST
    Call<FieldxDoPaymentBean> fieldxDoPayment(
            @Url String url,
            @Header("Authorization") String Authorization,
            @Body FieldxDoPaymentRequestBean fieldxDoPaymentRequestBean
    );

    @GET
    Call<FieldxCollectionReportResponseBean> fieldxCollectionReport(
            @Url String url,
            @Header("Authorization") String Authorization,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );

    @GET(APIConfig.retailerFieldExUrl)
    Call<FieldXRetailerBean> getFieldXRetailer(
            @Header("Authorization") String Authorization,
            @Query("fieldExecutiveId") String fieldExecutiveId,
            @Query("mode") String mode
    );

    @GET
    Call<FieldxRetailerResponseBean> getFieldXRetailer(
            @Url String url,
            @Header("Authorization") String Authorization,
            @Query("fieldExecutiveId") String fieldExecutiveId,
            @Query("mode") String mode
    );

    @PUT
    Call<DrawFetchGameDataResponseBean> fetchGameDataOther(
            @Url String url, @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Body DrawFetchGameDataRequestBean drawFetchGameDataRequestBean
    );

    @GET
    Call<WinningClaimPayResponseBean> printWinFailedReceipt (
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Query("ticketNumber") String ticketNumber,
            @Query("merchantUserId") String merchantUserId,
            @Query("modelCode") String modelCode,
            @Query("terminalId") String terminalId,
            @Query("sessionId") String sessionId

    );


    @GET
    Call<SbsWinVerifyResponse> verifyWinSbs (
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Query("deviceId") String deviceId,
            @Query("retailerId") Long retailerId,
            @Query("retailerToken") String retailerToken,
            @Query("ticketNo") String ticketNo
    );


    @GET
    Call<SbsWinPayResponse> payWinSbs (
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Query("deviceId") String deviceId,
            @Query("retailerId") Long retailerId,
            @Query("retailerToken") String retailerToken,
            @Query("ticketNo") String ticketNo

    );

    @GET
    Call<SbsReprintResponseBean> reprintSbs (
            @Url String url,
            @Header("Content-Type") String contentType,
            @Header("username") String username,
            @Header("password") String password,
            @Query("deviceId") String deviceId,
            @Query("retailerId") Long retailerId,
            @Query("retailerToken") String retailerToken,
            @Query("lastPrintedTicketNo") String lastPrintedTicketNo

    );

    @GET
    Call<OlaNetGamingExecutionResponseBean> getOlaNetGamingRangeDetail(
            @Url String url,
            @Header("username") String userName,
            @Header("password") String password,
            @Header("Content-Type") String contentType,
            @Header("Accept") String accept,
            @Query("numberOfMonths") Integer months,
            @Query("retailerId") Integer retailerId


    );
}
