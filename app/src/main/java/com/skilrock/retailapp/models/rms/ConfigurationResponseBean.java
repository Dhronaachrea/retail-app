package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfigurationResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public static class ResponseData {

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("statusCode")
        @Expose
        private Integer statusCode;
        @SerializedName("data")
        @Expose
        private List<Data> data = null;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    }

    public static class Data {

        @SerializedName("DEFAULT_ORG_STATUS")
        @Expose
        private String dEFAULTORGSTATUS;
        @SerializedName("AWS_SECRET_ACCESS_KEY")
        @Expose
        private String aWSSECRETACCESSKEY;
        @SerializedName("MAILBOX_PASSWORD")
        @Expose
        private String mAILBOXPASSWORD;
        @SerializedName("AWS_SENDER_ID")
        @Expose
        private String aWSSENDERID;
        @SerializedName("CURRENCY_LIST")
        @Expose
        private String cCURRENCYLIST;
        @SerializedName("DEFAULT_DATE_FORMAT")
        @Expose
        private String dEFAULTDATEFORMAT;
        @SerializedName("ALLOWED_WALLET_MODE")
        @Expose
        private String aLLOWEDWALLETMODE;
        @SerializedName("LANGUAGE_CODE")
        @Expose
        private String lANGUAGECODE;
        @SerializedName("CLIENT_DOMAIN_NAME")
        @Expose
        private String cLIENTDOMAINNAME;
        @SerializedName("COUNTRY")
        @Expose
        private String cOUNTRY;
        @SerializedName("NET_AMOUNT_CHECK")
        @Expose
        private String nETAMOUNTCHECK;
        @SerializedName("DEFAULT_DOMAIN_ID")
        @Expose
        private String dEFAULTDOMAINID;
        @SerializedName("OLA_NET_COLLECTION_COMM_ALLOWED")
        @Expose
        private String oLANETCOLLECTIONCOMMALLOWED;
        @SerializedName("ALLOWED_ORG_CODES")
        @Expose
        private String aLLOWEDORGCODES;
        @SerializedName("DUPLICATE_TXN_TIME_CHECK")
        @Expose
        private String dUPLICATETXNTIMECHECK;
        @SerializedName("MAX_CREDIT_LIMIT")
        @Expose
        private String mAXCREDITLIMIT;
        @SerializedName("THIRDPARTY_USER_REQUIRED")
        @Expose
        private String tHIRDPARTYUSERREQUIRED;
        @SerializedName("MAILBOX_USERNAME")
        @Expose
        private String mAILBOXUSERNAME;
        @SerializedName("ALLOWED_CHAIN_TYPE")
        @Expose
        private String aLLOWEDCHAINTYPE;
        @SerializedName("domainId")
        @Expose
        private Integer domainId;
        @SerializedName("IP_CHECK_FOR_USER_TOKEN")
        @Expose
        private String iPCHECKFORUSERTOKEN;
        @SerializedName("TDS_RATE")
        @Expose
        private String tDSRATE;
        @SerializedName("ZIPCODE_REGEX")
        @Expose
        private String zIPCODEREGEX;
        @SerializedName("PASSWORD_TYPE")
        @Expose
        private String pASSWORDTYPE;
        @SerializedName("LOWER_TIER_ACCESS_ALLOWED")
        @Expose
        private String lOWERTIERACCESSALLOWED;
        @SerializedName("domainName")
        @Expose
        private String domainName;
        @SerializedName("CLINET_TOKEN_GENERATION_ALLOWED")
        @Expose
        private String cLINETTOKENGENERATIONALLOWED;
        @SerializedName("BO_USER_SYNC_ALLOWED")
        @Expose
        private String bOUSERSYNCALLOWED;
        @SerializedName("OLA_GAME_ID")
        @Expose
        private String oLAGAMEID;
        @SerializedName("COUNTRY_CODE")
        @Expose
        private String cOUNTRYCODE;
        @SerializedName("COUNTRY_CODES")
        @Expose
        private String cCOUNTRY_CODES;
        @SerializedName("USE_MOBILE_AS_USERNAME")
        @Expose
        private String uUSE_MOBILE_AS_USERNAME;
        @SerializedName("AWS_REGION_VALUE")
        @Expose
        private String aWSREGIONVALUE;
        @SerializedName("CURRENCY_ID")
        @Expose
        private String cURRENCYID;
        @SerializedName("DATE_FORMAT")
        @Expose
        private String dATEFORMAT;
        @SerializedName("LOWER_TIER_USER_ACCESS_ALLOWED")
        @Expose
        private String lOWERTIERUSERACCESSALLOWED;
        @SerializedName("MAILBOX_EMAIL_ID")
        @Expose
        private String mAILBOXEMAILID;
        @SerializedName("OTP_EXPIRY")
        @Expose
        private String oTPEXPIRY;
        @SerializedName("TDS_APPLICABLE")
        @Expose
        private String tDSAPPLICABLE;
        @SerializedName("ALLOWED_DECIMAL_SIZE")
        @Expose
        private String aLLOWEDDECIMALSIZE;
        @SerializedName("PHONE_REGEX")
        @Expose
        private String pHONEREGEX;
        @SerializedName("SCRIPT_USER_ID")
        @Expose
        private String sCRIPTUSERID;
        @SerializedName("AWS_ACCESS_KEY_ID")
        @Expose
        private String aWSACCESSKEYID;
        @SerializedName("LOG_LOGIN_ACTIVITY")
        @Expose
        private String lOGLOGINACTIVITY;
        @SerializedName("WEAVER_ADMIN_LOGIN")
        @Expose
        private String wEAVERADMINLOGIN;
        @SerializedName("MOBILE_REGEX")
        @Expose
        private String mOBILEREGEX;
        @SerializedName("DEFAULT_BILL_CONFIG")
        @Expose
        private String dEFAULTBILLCONFIG;
        @SerializedName("DEFAULT_DATETIME_FORMAT")
        @Expose
        private String dEFAULTDATETIMEFORMAT;
        @SerializedName("DISPLAY_CURRENCY_SYMBOL")
        @Expose
        private String dISPLAYCURRENCYSYMBOL;
        @SerializedName("DEFAULT_RETAILER_SUBUSER_ROLE")
        @Expose
        private String dEFAULTRETAILERSUBUSERROLE;
        @SerializedName("USER_DEVICE_MAPPING")
        @Expose
        private String uSERDEVICEMAPPING;
        @SerializedName("REQUEST_TIMESTAMP_CHECK")
        @Expose
        private String rEQUESTTIMESTAMPCHECK;
        @SerializedName("DISPLAY_USER_BALANCE")
        @Expose
        private String dISPLAYUSERBALANCE;
        @SerializedName("ALLOWED_WALLET_TYPE")
        @Expose
        private String aLLOWEDWALLETTYPE;
        @SerializedName("PASSWORD_REGEX")
        @Expose
        private String pASSWORDREGEX;
        @SerializedName("IP_CHECK_FOR_CLIENT_TOKEN")
        @Expose
        private String iPCHECKFORCLIENTTOKEN;
        @SerializedName("MAX_COMMISSION_PERCENT")
        @Expose
        private String mAXCOMMISSIONPERCENT;
        @SerializedName("MASTER_WAREHOUSE_ID")
        @Expose
        private String mASTERWAREHOUSEID;
        @SerializedName("DEFAULT_CREDIT_LIMIT")
        @Expose
        private String dEFAULTCREDITLIMIT;
        @SerializedName("ORG_DEVICE_MAPPING")
        @Expose
        private String oRGDEVICEMAPPING;
        @SerializedName("RETAIL_PASSWORD_REGEX")
        @Expose
        private String RETAILPASSWORDREGEX;
        @SerializedName("CALL_US_MESSAGE")
        @Expose
        private String cALL_US_MESSAGE;

        @SerializedName("CREDIT_LIMIT_DISPLAY_ON_APP")
        @Expose
        private String CREDIT_LIMIT_DISPLAY_ON_APP;

        @SerializedName("DECIMAL_CHARACTER")
        @Expose
        private String DECIMAL_CHARACTER;

        public String getCREDIT_LIMIT_DISPLAY_ON_APP() { return CREDIT_LIMIT_DISPLAY_ON_APP;  }

        public void setCREDIT_LIMIT_DISPLAY_ON_APP(String CREDIT_LIMIT_DISPLAY_ON_APP) {
            this.CREDIT_LIMIT_DISPLAY_ON_APP = CREDIT_LIMIT_DISPLAY_ON_APP;
        }

        public String getCallUsNumber() {
            return cALL_US_MESSAGE;
        }

        public void setCallUsNumber(String cALL_US_MESSAGE) {
            this.cALL_US_MESSAGE = cALL_US_MESSAGE;
        }

        public String getDEFAULTORGSTATUS() {
            return dEFAULTORGSTATUS;
        }

        public void setDEFAULTORGSTATUS(String dEFAULTORGSTATUS) {
            this.dEFAULTORGSTATUS = dEFAULTORGSTATUS;
        }

        public String getAWSSECRETACCESSKEY() {
            return aWSSECRETACCESSKEY;
        }

        public void setAWSSECRETACCESSKEY(String aWSSECRETACCESSKEY) {
            this.aWSSECRETACCESSKEY = aWSSECRETACCESSKEY;
        }

        public String getMAILBOXPASSWORD() {
            return mAILBOXPASSWORD;
        }

        public void setMAILBOXPASSWORD(String mAILBOXPASSWORD) {
            this.mAILBOXPASSWORD = mAILBOXPASSWORD;
        }

        public String getAWSSENDERID() {
            return aWSSENDERID;
        }

        public void setAWSSENDERID(String aWSSENDERID) {
            this.aWSSENDERID = aWSSENDERID;
        }

        public String getDEFAULTDATEFORMAT() {
            return dEFAULTDATEFORMAT;
        }

        public void setDEFAULTDATEFORMAT(String dEFAULTDATEFORMAT) {
            this.dEFAULTDATEFORMAT = dEFAULTDATEFORMAT;
        }

        public String getALLOWEDWALLETMODE() {
            return aLLOWEDWALLETMODE;
        }

        public void setALLOWEDWALLETMODE(String aLLOWEDWALLETMODE) {
            this.aLLOWEDWALLETMODE = aLLOWEDWALLETMODE;
        }

        public String getLANGUAGECODE() {
            return lANGUAGECODE;
        }

        public void setLANGUAGECODE(String lANGUAGECODE) {
            this.lANGUAGECODE = lANGUAGECODE;
        }

        public String getCLIENTDOMAINNAME() {
            return cLIENTDOMAINNAME;
        }

        public void setCLIENTDOMAINNAME(String cLIENTDOMAINNAME) {
            this.cLIENTDOMAINNAME = cLIENTDOMAINNAME;
        }

        public String getCOUNTRY() {
            return cOUNTRY;
        }

        public void setCOUNTRY(String cOUNTRY) {
            this.cOUNTRY = cOUNTRY;
        }

        public String getNETAMOUNTCHECK() {
            return nETAMOUNTCHECK;
        }

        public void setNETAMOUNTCHECK(String nETAMOUNTCHECK) {
            this.nETAMOUNTCHECK = nETAMOUNTCHECK;
        }

        public String getDEFAULTDOMAINID() {
            return dEFAULTDOMAINID;
        }

        public void setDEFAULTDOMAINID(String dEFAULTDOMAINID) {
            this.dEFAULTDOMAINID = dEFAULTDOMAINID;
        }

        public String getOLANETCOLLECTIONCOMMALLOWED() {
            return oLANETCOLLECTIONCOMMALLOWED;
        }

        public void setOLANETCOLLECTIONCOMMALLOWED(String oLANETCOLLECTIONCOMMALLOWED) {
            this.oLANETCOLLECTIONCOMMALLOWED = oLANETCOLLECTIONCOMMALLOWED;
        }

        public String getALLOWEDORGCODES() {
            return aLLOWEDORGCODES;
        }

        public void setALLOWEDORGCODES(String aLLOWEDORGCODES) {
            this.aLLOWEDORGCODES = aLLOWEDORGCODES;
        }

        public String getDUPLICATETXNTIMECHECK() {
            return dUPLICATETXNTIMECHECK;
        }

        public void setDUPLICATETXNTIMECHECK(String dUPLICATETXNTIMECHECK) {
            this.dUPLICATETXNTIMECHECK = dUPLICATETXNTIMECHECK;
        }

        public String getMAXCREDITLIMIT() {
            return mAXCREDITLIMIT;
        }

        public void setMAXCREDITLIMIT(String mAXCREDITLIMIT) {
            this.mAXCREDITLIMIT = mAXCREDITLIMIT;
        }

        public String getTHIRDPARTYUSERREQUIRED() {
            return tHIRDPARTYUSERREQUIRED;
        }

        public void setTHIRDPARTYUSERREQUIRED(String tHIRDPARTYUSERREQUIRED) {
            this.tHIRDPARTYUSERREQUIRED = tHIRDPARTYUSERREQUIRED;
        }

        public String getMAILBOXUSERNAME() {
            return mAILBOXUSERNAME;
        }

        public void setMAILBOXUSERNAME(String mAILBOXUSERNAME) {
            this.mAILBOXUSERNAME = mAILBOXUSERNAME;
        }

        public String getALLOWEDCHAINTYPE() {
            return aLLOWEDCHAINTYPE;
        }

        public void setALLOWEDCHAINTYPE(String aLLOWEDCHAINTYPE) {
            this.aLLOWEDCHAINTYPE = aLLOWEDCHAINTYPE;
        }

        public Integer getDomainId() {
            return domainId;
        }

        public void setDomainId(Integer domainId) {
            this.domainId = domainId;
        }

        public String getIPCHECKFORUSERTOKEN() {
            return iPCHECKFORUSERTOKEN;
        }

        public void setIPCHECKFORUSERTOKEN(String iPCHECKFORUSERTOKEN) {
            this.iPCHECKFORUSERTOKEN = iPCHECKFORUSERTOKEN;
        }

        public String getTDSRATE() {
            return tDSRATE;
        }

        public void setTDSRATE(String tDSRATE) {
            this.tDSRATE = tDSRATE;
        }

        public String getZIPCODEREGEX() {
            return zIPCODEREGEX;
        }

        public void setZIPCODEREGEX(String zIPCODEREGEX) {
            this.zIPCODEREGEX = zIPCODEREGEX;
        }

        public String getPASSWORDTYPE() {
            return pASSWORDTYPE;
        }

        public void setPASSWORDTYPE(String pASSWORDTYPE) {
            this.pASSWORDTYPE = pASSWORDTYPE;
        }

        public String getLOWERTIERACCESSALLOWED() {
            return lOWERTIERACCESSALLOWED;
        }

        public void setLOWERTIERACCESSALLOWED(String lOWERTIERACCESSALLOWED) {
            this.lOWERTIERACCESSALLOWED = lOWERTIERACCESSALLOWED;
        }

        public String getDomainName() {
            return domainName;
        }

        public void setDomainName(String domainName) {
            this.domainName = domainName;
        }

        public String getCLINETTOKENGENERATIONALLOWED() {
            return cLINETTOKENGENERATIONALLOWED;
        }

        public void setCLINETTOKENGENERATIONALLOWED(String cLINETTOKENGENERATIONALLOWED) {
            this.cLINETTOKENGENERATIONALLOWED = cLINETTOKENGENERATIONALLOWED;
        }

        public String getBOUSERSYNCALLOWED() {
            return bOUSERSYNCALLOWED;
        }

        public void setBOUSERSYNCALLOWED(String bOUSERSYNCALLOWED) {
            this.bOUSERSYNCALLOWED = bOUSERSYNCALLOWED;
        }

        public String getOLAGAMEID() {
            return oLAGAMEID;
        }

        public void setOLAGAMEID(String oLAGAMEID) {
            this.oLAGAMEID = oLAGAMEID;
        }

        public String getCOUNTRYCODE() {
            return cOUNTRYCODE;
        }

        public void setCOUNTRYCODE(String cOUNTRYCODE) {
            this.cOUNTRYCODE = cOUNTRYCODE;
        }

        public String getAWSREGIONVALUE() {
            return aWSREGIONVALUE;
        }

        public void setAWSREGIONVALUE(String aWSREGIONVALUE) {
            this.aWSREGIONVALUE = aWSREGIONVALUE;
        }

        public String getCURRENCYID() {
            return cURRENCYID;
        }

        public void setCURRENCYID(String cURRENCYID) {
            this.cURRENCYID = cURRENCYID;
        }

        public String getDATEFORMAT() {
            return dATEFORMAT;
        }

        public void setDATEFORMAT(String dATEFORMAT) {
            this.dATEFORMAT = dATEFORMAT;
        }

        public String getLOWERTIERUSERACCESSALLOWED() {
            return lOWERTIERUSERACCESSALLOWED;
        }

        public void setLOWERTIERUSERACCESSALLOWED(String lOWERTIERUSERACCESSALLOWED) {
            this.lOWERTIERUSERACCESSALLOWED = lOWERTIERUSERACCESSALLOWED;
        }

        public String getMAILBOXEMAILID() {
            return mAILBOXEMAILID;
        }

        public void setMAILBOXEMAILID(String mAILBOXEMAILID) {
            this.mAILBOXEMAILID = mAILBOXEMAILID;
        }

        public String getOTPEXPIRY() {
            return oTPEXPIRY;
        }

        public void setOTPEXPIRY(String oTPEXPIRY) {
            this.oTPEXPIRY = oTPEXPIRY;
        }

        public String getTDSAPPLICABLE() {
            return tDSAPPLICABLE;
        }

        public void setTDSAPPLICABLE(String tDSAPPLICABLE) {
            this.tDSAPPLICABLE = tDSAPPLICABLE;
        }

        public String getALLOWEDDECIMALSIZE() {
            return aLLOWEDDECIMALSIZE;
        }

        public void setALLOWEDDECIMALSIZE(String aLLOWEDDECIMALSIZE) {
            this.aLLOWEDDECIMALSIZE = aLLOWEDDECIMALSIZE;
        }

        public String getPHONEREGEX() {
            return pHONEREGEX;
        }

        public void setPHONEREGEX(String pHONEREGEX) {
            this.pHONEREGEX = pHONEREGEX;
        }

        public String getSCRIPTUSERID() {
            return sCRIPTUSERID;
        }

        public void setSCRIPTUSERID(String sCRIPTUSERID) {
            this.sCRIPTUSERID = sCRIPTUSERID;
        }

        public String getAWSACCESSKEYID() {
            return aWSACCESSKEYID;
        }

        public void setAWSACCESSKEYID(String aWSACCESSKEYID) {
            this.aWSACCESSKEYID = aWSACCESSKEYID;
        }

        public String getLOGLOGINACTIVITY() {
            return lOGLOGINACTIVITY;
        }

        public void setLOGLOGINACTIVITY(String lOGLOGINACTIVITY) {
            this.lOGLOGINACTIVITY = lOGLOGINACTIVITY;
        }

        public String getWEAVERADMINLOGIN() {
            return wEAVERADMINLOGIN;
        }

        public void setWEAVERADMINLOGIN(String wEAVERADMINLOGIN) {
            this.wEAVERADMINLOGIN = wEAVERADMINLOGIN;
        }

        public String getMOBILEREGEX() {
            return mOBILEREGEX;
        }

        public void setMOBILEREGEX(String mOBILEREGEX) {
            this.mOBILEREGEX = mOBILEREGEX;
        }

        public String getDEFAULTBILLCONFIG() {
            return dEFAULTBILLCONFIG;
        }

        public void setDEFAULTBILLCONFIG(String dEFAULTBILLCONFIG) {
            this.dEFAULTBILLCONFIG = dEFAULTBILLCONFIG;
        }

        public String getDEFAULTDATETIMEFORMAT() {
            return dEFAULTDATETIMEFORMAT;
        }

        public void setDEFAULTDATETIMEFORMAT(String dEFAULTDATETIMEFORMAT) {
            this.dEFAULTDATETIMEFORMAT = dEFAULTDATETIMEFORMAT;
        }

        public String getDISPLAYCURRENCYSYMBOL() {
            return dISPLAYCURRENCYSYMBOL;
        }

        public void setDISPLAYCURRENCYSYMBOL(String dISPLAYCURRENCYSYMBOL) {
            this.dISPLAYCURRENCYSYMBOL = dISPLAYCURRENCYSYMBOL;
        }

        public String getDEFAULTRETAILERSUBUSERROLE() {
            return dEFAULTRETAILERSUBUSERROLE;
        }

        public void setDEFAULTRETAILERSUBUSERROLE(String dEFAULTRETAILERSUBUSERROLE) {
            this.dEFAULTRETAILERSUBUSERROLE = dEFAULTRETAILERSUBUSERROLE;
        }

        public String getUSERDEVICEMAPPING() {
            return uSERDEVICEMAPPING;
        }

        public void setUSERDEVICEMAPPING(String uSERDEVICEMAPPING) {
            this.uSERDEVICEMAPPING = uSERDEVICEMAPPING;
        }

        public String getREQUESTTIMESTAMPCHECK() {
            return rEQUESTTIMESTAMPCHECK;
        }

        public void setREQUESTTIMESTAMPCHECK(String rEQUESTTIMESTAMPCHECK) {
            this.rEQUESTTIMESTAMPCHECK = rEQUESTTIMESTAMPCHECK;
        }

        public String getDISPLAYUSERBALANCE() {
            return dISPLAYUSERBALANCE;
        }

        public void setDISPLAYUSERBALANCE(String dISPLAYUSERBALANCE) {
            this.dISPLAYUSERBALANCE = dISPLAYUSERBALANCE;
        }

        public String getALLOWEDWALLETTYPE() {
            return aLLOWEDWALLETTYPE;
        }

        public void setALLOWEDWALLETTYPE(String aLLOWEDWALLETTYPE) {
            this.aLLOWEDWALLETTYPE = aLLOWEDWALLETTYPE;
        }

        public String getPASSWORDREGEX() {
            return pASSWORDREGEX;
        }

        public void setPASSWORDREGEX(String pASSWORDREGEX) {
            this.pASSWORDREGEX = pASSWORDREGEX;
        }

        public String getIPCHECKFORCLIENTTOKEN() {
            return iPCHECKFORCLIENTTOKEN;
        }

        public void setIPCHECKFORCLIENTTOKEN(String iPCHECKFORCLIENTTOKEN) {
            this.iPCHECKFORCLIENTTOKEN = iPCHECKFORCLIENTTOKEN;
        }

        public String getMAXCOMMISSIONPERCENT() {
            return mAXCOMMISSIONPERCENT;
        }

        public void setMAXCOMMISSIONPERCENT(String mAXCOMMISSIONPERCENT) {
            this.mAXCOMMISSIONPERCENT = mAXCOMMISSIONPERCENT;
        }

        public String getMASTERWAREHOUSEID() {
            return mASTERWAREHOUSEID;
        }

        public void setMASTERWAREHOUSEID(String mASTERWAREHOUSEID) {
            this.mASTERWAREHOUSEID = mASTERWAREHOUSEID;
        }

        public String getDEFAULTCREDITLIMIT() {
            return dEFAULTCREDITLIMIT;
        }

        public void setDEFAULTCREDITLIMIT(String dEFAULTCREDITLIMIT) {
            this.dEFAULTCREDITLIMIT = dEFAULTCREDITLIMIT;
        }

        public String getORGDEVICEMAPPING() {
            return oRGDEVICEMAPPING;
        }

        public void setORGDEVICEMAPPING(String oRGDEVICEMAPPING) {
            this.oRGDEVICEMAPPING = oRGDEVICEMAPPING;
        }

        public String getcCOUNTRY_CODES() {
            return cCOUNTRY_CODES;
        }

        public void setcCOUNTRY_CODES(String cCOUNTRY_CODES) {
            this.cCOUNTRY_CODES = cCOUNTRY_CODES;
        }

        public String getDECIMAL_CHARACTER() {
            return DECIMAL_CHARACTER;
        }

        public void setDECIMAL_CHARACTER(String DECIMAL_CHARACTER) {
            this.DECIMAL_CHARACTER = DECIMAL_CHARACTER;
        }

        public String getuUSE_MOBILE_AS_USERNAME() {
            return uUSE_MOBILE_AS_USERNAME;
        }

        public void setuUSE_MOBILE_AS_USERNAME(String uUSE_MOBILE_AS_USERNAME) {
            this.uUSE_MOBILE_AS_USERNAME = uUSE_MOBILE_AS_USERNAME;
        }

        public String getcCURRENCYLIST() {
            return cCURRENCYLIST;
        }

        public void setcCURRENCYLIST(String cCURRENCYLIST) {
            this.cCURRENCYLIST = cCURRENCYLIST;
        }

        public String getRETAILPASSWORDREGEX() {
            return RETAILPASSWORDREGEX;
        }

        public void setRETAILPASSWORDREGEX(String RETAILPASSWORDREGEX) {
            this.RETAILPASSWORDREGEX = RETAILPASSWORDREGEX;
        }
    }
}