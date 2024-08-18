package com.skilrock.retailapp.models.ola;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class OperationalReportResponseBean {

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(OperationalReportResponseBean.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("responseCode");
        sb.append('=');
        sb.append(((this.responseCode == null) ? "<null>" : this.responseCode));
        sb.append(',');
        sb.append("responseMessage");
        sb.append('=');
        sb.append(((this.responseMessage == null) ? "<null>" : this.responseMessage));
        sb.append(',');
        sb.append("responseData");
        sb.append('=');
        sb.append(((this.responseData == null) ? "<null>" : this.responseData));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
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
        private Data data;

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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ResponseData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("message");
            sb.append('=');
            sb.append(((this.message == null) ? "<null>" : this.message));
            sb.append(',');
            sb.append("statusCode");
            sb.append('=');
            sb.append(((this.statusCode == null) ? "<null>" : this.statusCode));
            sb.append(',');
            sb.append("data");
            sb.append('=');
            sb.append(((this.data == null) ? "<null>" : this.data));
            sb.append(',');
            if (sb.charAt((sb.length() - 1)) == ',') {
                sb.setCharAt((sb.length() - 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }


        public class Data {

            @SerializedName("totalCashOnHand")
            @Expose
            private String totalCashOnHand;
            @SerializedName("gameWiseData")
            @Expose
            private List<GameWiseDatum> gameWiseData = null;
            @SerializedName("totalClaimTax")
            @Expose
            private String totalClaimTax;
            @SerializedName("totalCommision")
            @Expose
            private String totalCommision;
            @SerializedName("totalSale")
            @Expose
            private String totalSale;
            @SerializedName("totalClaim")
            @Expose
            private String totalClaim;
            @SerializedName("salesCommision")
            @Expose
            private String salesCommision;
            @SerializedName("winningsCommision")
            @Expose
            private String winningsCommision;

            public String getTotalCashOnHand() {
                return totalCashOnHand;
            }

            public void setTotalCashOnHand(String totalCashOnHand) {
                this.totalCashOnHand = totalCashOnHand;
            }

            public List<GameWiseDatum> getGameWiseData() {
                return gameWiseData;
            }

            public void setGameWiseData(List<GameWiseDatum> gameWiseData) {
                this.gameWiseData = gameWiseData;
            }

            public String getTotalClaimTax() {
                return totalClaimTax;
            }

            public void setTotalClaimTax(String totalClaimTax) {
                this.totalClaimTax = totalClaimTax;
            }

            public String getTotalCommision() {
                return totalCommision;
            }

            public void setTotalCommision(String totalCommision) {
                this.totalCommision = totalCommision;
            }

            public String getSalesCommision() {
                return salesCommision;
            }

            public void setSalesCommision(String salesCommision) {
                this.salesCommision = salesCommision;
            }

            public String getWinningsCommision() {
                return winningsCommision;
            }

            public void setWinningsCommision(String winningsCommision) {
                this.winningsCommision = winningsCommision;
            }

            public String getTotalSale() {
                return totalSale;
            }

            public void setTotalSale(String totalSale) {
                this.totalSale = totalSale;
            }

            public String getTotalClaim() {
                return totalClaim;
            }

            public void setTotalClaim(String totalClaim) {
                this.totalClaim = totalClaim;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(Data.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("totalCashOnHand");
                sb.append('=');
                sb.append(((this.totalCashOnHand == null) ? "<null>" : this.totalCashOnHand));
                sb.append(',');
                sb.append("gameWiseData");
                sb.append('=');
                sb.append(((this.gameWiseData == null) ? "<null>" : this.gameWiseData));
                sb.append(',');
                sb.append("totalClaimTax");
                sb.append('=');
                sb.append(((this.totalClaimTax == null) ? "<null>" : this.totalClaimTax));
                sb.append(',');
                sb.append("totalCommision");
                sb.append('=');
                sb.append(((this.totalCommision == null) ? "<null>" : this.totalCommision));
                sb.append(',');
                sb.append("totalSale");
                sb.append('=');
                sb.append(((this.totalSale == null) ? "<null>" : this.totalSale));
                sb.append(',');
                sb.append("totalClaim");
                sb.append('=');
                sb.append(((this.totalClaim == null) ? "<null>" : this.totalClaim));
                sb.append(',');
                if (sb.charAt((sb.length() - 1)) == ',') {
                    sb.setCharAt((sb.length() - 1), ']');
                } else {
                    sb.append(']');
                }
                return sb.toString();
            }

        }

        public class GameWiseDatum {

            @SerializedName("gameName")
            @Expose
            private String gameName;

            @SerializedName("claims")
            @Expose
            private String claims;

            @SerializedName("claimTax")
            @Expose
            private String claimTax;

            @SerializedName("sales")
            @Expose
            private String sales;

            public String getGameName() {
                return gameName;
            }

            public void setGameName(String gameName) {
                this.gameName = gameName;
            }

            public String getClaims() {
                return claims;
            }

            public void setClaims(String claims) {
                this.claims = claims;
            }

            public String getClaimTax() {
                return claimTax;
            }

            public void setClaimTax(String claimTax) {
                this.claimTax = claimTax;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(GameWiseDatum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
                sb.append("gameName");
                sb.append('=');
                sb.append(((this.gameName == null) ? "<null>" : this.gameName));
                sb.append(',');
                sb.append("claims");
                sb.append('=');
                sb.append(((this.claims == null) ? "<null>" : this.claims));
                sb.append(',');
                sb.append("claimTax");
                sb.append('=');
                sb.append(((this.claimTax == null) ? "<null>" : this.claimTax));
                sb.append(',');
                sb.append("sales");
                sb.append('=');
                sb.append(((this.sales == null) ? "<null>" : this.sales));
                sb.append(',');
                if (sb.charAt((sb.length() - 1)) == ',') {
                    sb.setCharAt((sb.length() - 1), ']');
                } else {
                    sb.append(']');
                }
                return sb.toString();
            }
        }
    }
}