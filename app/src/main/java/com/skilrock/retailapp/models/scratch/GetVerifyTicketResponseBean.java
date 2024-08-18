package com.skilrock.retailapp.models.scratch;
import java.io.Serializable;
import java.util.List;

public class GetVerifyTicketResponseBean implements Serializable {
    private String gameName;
    private List<TicketAndStatus> ticketAndStatusList;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<TicketAndStatus> getTicketAndStatusList() {
        return ticketAndStatusList;
    }

    public void setTicketAndStatusList(List<TicketAndStatus> ticketAndStatusList) {
        this.ticketAndStatusList = ticketAndStatusList;
    }

    public static class TicketAndStatus implements Serializable {
        private String ticketNumber;
        private String barCodeNumber;
        private String winAmount;
        private String taxAmount;
        private String netAmount;

        public String getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
        }

        public String getBarCodeNumber() {
            return barCodeNumber;
        }

        public void setBarCodeNumber(String barCodeNumber) {
            this.barCodeNumber = barCodeNumber;
        }

        public String getWinAmount() {
            return winAmount;
        }

        public void setWinAmount(String winAmount) {
            this.winAmount = winAmount;
        }

        public String getTaxAmount() {
            return taxAmount;
        }
        public void setTaxAmount(String taxAmount) {
            this.taxAmount = taxAmount;
        }

        public String getNetAmount() {
            return netAmount;
        }
        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }
    }
}
