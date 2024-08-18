package com.skilrock.retailapp.models.rms


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

data class LoginResponseData(

    @SerializedName("responseCode")
    @Expose
    val responseCode: Int,

    @SerializedName("responseData")
    @Expose
    val responseData: ResponseData,

    @SerializedName("responseMessage")
    @Expose
    val responseMessage: String
) {
    data class ResponseData(

        @SerializedName("data")
        @Expose
        val `data`: Data,

        @SerializedName("message")
        @Expose
        val message: String,

        @SerializedName("statusCode")
        @Expose
        val statusCode: Int
    ) {
        data class Data(

            @SerializedName("accessSelfDomainOnly")
            @Expose
            val accessSelfDomainOnly: String,

            @SerializedName("balance")
            @Expose
            val balance: String,

            @SerializedName("creditLimit")
            @Expose
            val creditLimit: String,

            @SerializedName("domainId")
            @Expose
            val domainId: Int,

            @SerializedName("firstName")
            @Expose
            val firstName: String,

            @SerializedName("isHead")
            @Expose
            val isHead: String,

            @SerializedName("lastName")
            @Expose
            val lastName: String,

            @SerializedName("orgCode")
            @Expose
            val orgCode: String,

            @SerializedName("orgId")
            @Expose
            val orgId: Int,

            @SerializedName("orgName")
            @Expose
            val orgName: String,

            @SerializedName("orgStatus")
            @Expose
            val orgStatus: String,

            @SerializedName("orgTypeCode")
            @Expose
            val orgTypeCode: String,

            @SerializedName("parentAgtOrgId")
            @Expose
            val parentAgtOrgId: String,

            @SerializedName("parentMagtOrgId")
            @Expose
            val parentMagtOrgId: String,

            @SerializedName("parentSagtOrgId")
            @Expose
            val parentSagtOrgId: String,

            @SerializedName("rawUserBalance")
            @Expose
            val rawUserBalance: Double,

            @SerializedName("regionBinding")
            @Expose
            val regionBinding: String,

            @SerializedName("userBalance")
            @Expose
            val userBalance: String,

            @SerializedName("userId")
            @Expose
            val userId: String,

            @SerializedName("userStatus")
            @Expose
            val userStatus: String,

            @SerializedName("username")
            @Expose
            val username: String,

            @SerializedName("walletMode")
            @Expose
            val walletMode: String,

            @SerializedName("walletType")
            @Expose
            val walletType: String
        )
    }
}