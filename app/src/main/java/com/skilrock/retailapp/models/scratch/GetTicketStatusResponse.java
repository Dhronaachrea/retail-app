package com.skilrock.retailapp.models.scratch;
import java.io.Serializable;
import java.util.List;

public class GetTicketStatusResponse implements Serializable {
    private int responseCode;
    private String responseMessage;
    private Data response;

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

    public Data getResponse() {
        return response;
    }

    public void setResponse(Data response) {
        this.response = response;
    }

    public static class Data implements Serializable {
        private List<Game> data;

        public List<Game> getData() {
            return data;
        }

        public void setData(List<Game> data) {
            this.data = data;
        }
    }

    public static class Game implements Serializable {
        private List<Ticket> ticketAndStatusList;
        private double ticketPrice;
        private String gameName;

        public List<Ticket> getTicketAndStatusList() {
            return ticketAndStatusList;
        }

        public void setTicketAndStatusList(List<Ticket> ticketAndStatusList) {
            this.ticketAndStatusList = ticketAndStatusList;
        }

        public double getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(double ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }
    }

    public static class Ticket implements Serializable {
        private String ticketNumber;
        private String ticketStatus;
        private String winAmount; // only for claim

        public String getTicketNumber() {
            return ticketNumber;
        }

        public void setTicketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
        }

        public String getTicketStatus() {
            return ticketStatus;
        }

        public void setTicketStatus(String ticketStatus) {
            this.ticketStatus = ticketStatus;
        }

        public String getWinAmount() {
            return winAmount;
        }

        public void setWinAmount(String winAmount) {
            this.winAmount = winAmount;
        }
    }
}
