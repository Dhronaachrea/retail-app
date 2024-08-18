package com.skilrock.retailapp.models.ola


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

data class OlaRegistrationMyanmarKotlinResponseData(

    @SerializedName("responseCode")
    @Expose
    val responseCode: Int?,

    @SerializedName("responseData")
    @Expose
    val responseData: ResponseData?,

    @SerializedName("responseMessage")
    @Expose
    val responseMessage: String?
) {
    data class ResponseData(

        @SerializedName("depositResponseVO")
        @Expose
        val depositResponseVO: DepositResponseVO?,

        @SerializedName("orgBalance")
        @Expose
        val orgBalance: Double?,

        @SerializedName("orgName")
        @Expose
        val orgName: String?,

        @SerializedName("password")
        @Expose
        val password: String?,

        @SerializedName("player")
        @Expose
        val player: Player?,

        @SerializedName("regDate")
        @Expose
        val regDate: String?,

        @SerializedName("retUserName")
        @Expose
        val retUserName: String?,

        @SerializedName("username")
        @Expose
        val username: String?
    ) {
        data class DepositResponseVO(

            @SerializedName("balancePostTxn")
            @Expose
            val balancePostTxn: Double?,

            @SerializedName("balancePreTxn")
            @Expose
            val balancePreTxn: Double?,

            @SerializedName("plrDepositAmount")
            @Expose
            val plrDepositAmount: Double?,

            @SerializedName("plrTxnId")
            @Expose
            val plrTxnId: Int?,

            @SerializedName("respCode")
            @Expose
            val respCode: Int?,

            @SerializedName("respMsg")
            @Expose
            val respMsg: String?,

            @SerializedName("txnDate")
            @Expose
            val txnDate: String?,

            @SerializedName("txnId")
            @Expose
            val txnId: Int?
        )

        data class Player(

            @SerializedName("dateOfBirth")
            @Expose
            val dateOfBirth: String?,

            @SerializedName("domainId")
            @Expose
            val domainId: Int?,

            @SerializedName("estatus")
            @Expose
            val estatus: String?,

            @SerializedName("merchantOrgId")
            @Expose
            val merchantOrgId: Int?,

            @SerializedName("merchantPlayerId")
            @Expose
            val merchantPlayerId: Int?,

            @SerializedName("password")
            @Expose
            val password: String?,

            @SerializedName("playerId")
            @Expose
            val playerId: Int?,

            @SerializedName("registrationDate")
            @Expose
            val registrationDate: String?,

            @SerializedName("registrationType")
            @Expose
            val registrationType: String?,

            @SerializedName("userId")
            @Expose
            val userId: Int?,

            @SerializedName("username")
            @Expose
            val username: String?
        )
    }
}