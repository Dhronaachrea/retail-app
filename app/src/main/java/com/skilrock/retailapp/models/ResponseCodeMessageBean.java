package com.skilrock.retailapp.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCodeMessageBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("orgName")
    @Expose
    private String orgName;
    @SerializedName("saleTicketDetails")
    @Expose
    private List<SaleTicketDetail> saleTicketDetails = null;
    @SerializedName("soldTickets")
    @Expose
    private List<String> soldTickets = null;
    @SerializedName("invalidTickets")
    @Expose
    private List<String> invalidTickets = null;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<SaleTicketDetail> getSaleTicketDetails() {
        return saleTicketDetails;
    }

    public void setSaleTicketDetails(List<SaleTicketDetail> saleTicketDetails) {
        this.saleTicketDetails = saleTicketDetails;
    }

    public List<String> getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(List<String> soldTickets) {
        this.soldTickets = soldTickets;
    }

    public List<String> getInvalidTickets() {
        return invalidTickets;
    }

    public void setInvalidTickets(List<String> invalidTickets) {
        this.invalidTickets = invalidTickets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ResponseCodeMessageBean.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("responseCode");
        sb.append('=');
        sb.append(((this.responseCode == null) ? "<null>" : this.responseCode));
        sb.append(',');
        sb.append("responseMessage");
        sb.append('=');
        sb.append(((this.responseMessage == null) ? "<null>" : this.responseMessage));
        sb.append(',');
        sb.append("orgName");
        sb.append('=');
        sb.append(((this.orgName == null) ? "<null>" : this.orgName));
        sb.append(',');
        sb.append("saleTicketDetails");
        sb.append('=');
        sb.append(((this.saleTicketDetails == null) ? "<null>" : this.saleTicketDetails));
        sb.append(',');
        sb.append("soldTickets");
        sb.append('=');
        sb.append(((this.soldTickets == null) ? "<null>" : this.soldTickets));
        sb.append(',');
        sb.append("invalidTickets");
        sb.append('=');
        sb.append(((this.invalidTickets == null) ? "<null>" : this.invalidTickets));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public class SaleTicketDetail {

        @SerializedName("ticketPrice")
        @Expose
        private Double ticketPrice;
        @SerializedName("ticketNumbers")
        @Expose
        private List<String> ticketNumbers = null;

        @SerializedName("gameName")
        @Expose
        private String gameName = null;

        public Double getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(Double ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public List<String> getTicketNumbers() {
            return ticketNumbers;
        }

        public void setTicketNumbers(List<String> ticketNumbers) {
            this.ticketNumbers = ticketNumbers;
        }

        public String getGameName() { return gameName; }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(SaleTicketDetail.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("ticketPrice");
            sb.append('=');
            sb.append(((this.ticketPrice == null) ? "<null>" : this.ticketPrice));
            sb.append(',');
            sb.append("ticketNumbers");
            sb.append('=');
            sb.append(((this.ticketNumbers == null) ? "<null>" : this.ticketNumbers));
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















/*
public class ResponseCodeMessageBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("orgName")
    @Expose
    private String orgName;
    @SerializedName("saleTicketDetails")
    @Expose
    private List<SaleTicketDetail> saleTicketDetails = null;
    @SerializedName("soldTickets")
    @Expose
    private List<String> soldTickets = null;
    @SerializedName("invalidTickets")
    @Expose
    private List<String> invalidTickets = null;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<SaleTicketDetail> getSaleTicketDetails() {
        return saleTicketDetails;
    }

    public void setSaleTicketDetails(List<SaleTicketDetail> saleTicketDetails) {
        this.saleTicketDetails = saleTicketDetails;
    }

    public List<String> getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(List<String> soldTickets) {
        this.soldTickets = soldTickets;
    }

    public List<String> getInvalidTickets() {
        return invalidTickets;
    }

    public void setInvalidTickets(List<String> invalidTickets) {
        this.invalidTickets = invalidTickets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ResponseCodeMessageBean.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("responseCode");
        sb.append('=');
        sb.append(((this.responseCode == null) ? "<null>" : this.responseCode));
        sb.append(',');
        sb.append("responseMessage");
        sb.append('=');
        sb.append(((this.responseMessage == null) ? "<null>" : this.responseMessage));
        sb.append(',');
        sb.append("orgName");
        sb.append('=');
        sb.append(((this.orgName == null) ? "<null>" : this.orgName));
        sb.append(',');
        sb.append("saleTicketDetails");
        sb.append('=');
        sb.append(((this.saleTicketDetails == null) ? "<null>" : this.saleTicketDetails));
        sb.append(',');
        sb.append("soldTickets");
        sb.append('=');
        sb.append(((this.soldTickets == null) ? "<null>" : this.soldTickets));
        sb.append(',');
        sb.append("invalidTickets");
        sb.append('=');
        sb.append(((this.invalidTickets == null) ? "<null>" : this.invalidTickets));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public class SaleTicketDetail {

        @SerializedName("ticketPrice")
        @Expose
        private Double ticketPrice;
        @SerializedName("ticketNumbers")
        @Expose
        private List<String> ticketNumbers = null;

        public Double getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(Double ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public List<String> getTicketNumbers() {
            return ticketNumbers;
        }

        public void setTicketNumbers(List<String> ticketNumbers) {
            this.ticketNumbers = ticketNumbers;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(SaleTicketDetail.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("ticketPrice");
            sb.append('=');
            sb.append(((this.ticketPrice == null) ? "<null>" : this.ticketPrice));
            sb.append(',');
            sb.append("ticketNumbers");
            sb.append('=');
            sb.append(((this.ticketNumbers == null) ? "<null>" : this.ticketNumbers));
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
*/

/*
public class ResponseCodeMessageBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("response")
    @Expose
    private Response response;

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

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("soldTickets")
        @Expose
        private List<String> soldTickets = null;
        @SerializedName("invalidTickets")
        @Expose
        private List<String> invalidTickets = null;

        public List<String> getSoldTickets() {
            return soldTickets;
        }

        public void setSoldTickets(List<String> soldTickets) {
            this.soldTickets = soldTickets;
        }

        public List<String> getInvalidTickets() {
            return invalidTickets;
        }

        public void setInvalidTickets(List<String> invalidTickets) {
            this.invalidTickets = invalidTickets;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Response.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("soldTickets");
            sb.append('=');
            sb.append(((this.soldTickets == null)?"<null>":this.soldTickets));
            sb.append(',');
            sb.append("invalidTickets");
            sb.append('=');
            sb.append(((this.invalidTickets == null)?"<null>":this.invalidTickets));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ResponseCodeMessageBean.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("responseCode");
        sb.append('=');
        sb.append(((this.responseCode == null)?"<null>":this.responseCode));
        sb.append(',');
        sb.append("responseMessage");
        sb.append('=');
        sb.append(((this.responseMessage == null)?"<null>":this.responseMessage));
        sb.append(',');
        sb.append("response");
        sb.append('=');
        sb.append(((this.response == null)?"<null>":this.response));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
*/



/*public class ResponseCodeMessageBean {

    private int responseCode;
    private String responseMessage;
    private String respMsg;

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseCodeMessageBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", respMsg='" + respMsg + '\'' + '}';
    }*/

