package com.skilrock.retailapp.models.ola;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class InventoryFlowReportResponseBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("booksClosingBalance")
    @Expose
    private Integer booksClosingBalance;
    @SerializedName("booksOpeningBalance")
    @Expose
    private Integer booksOpeningBalance;
    @SerializedName("gameWiseData")
    @Expose
    private List<GameWiseDatum> gameWiseData = null;
    @SerializedName("receivedBooks")
    @Expose
    private Integer receivedBooks;
    @SerializedName("receivedTickets")
    @Expose
    private Integer receivedTickets;
    @SerializedName("returnedBooks")
    @Expose
    private Integer returnedBooks;
    @SerializedName("returnedTickets")
    @Expose
    private Integer returnedTickets;
    @SerializedName("soldBooks")
    @Expose
    private Integer soldBooks;
    @SerializedName("soldTickets")
    @Expose
    private Integer soldTickets;
    @SerializedName("ticketsClosingBalance")
    @Expose
    private Integer ticketsClosingBalance;
    @SerializedName("ticketsOpeningBalance")
    @Expose
    private Integer ticketsOpeningBalance;

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

    public Integer getBooksClosingBalance() {
        return booksClosingBalance;
    }

    public void setBooksClosingBalance(Integer booksClosingBalance) {
        this.booksClosingBalance = booksClosingBalance;
    }

    public Integer getBooksOpeningBalance() {
        return booksOpeningBalance;
    }

    public void setBooksOpeningBalance(Integer booksOpeningBalance) {
        this.booksOpeningBalance = booksOpeningBalance;
    }

    public List<GameWiseDatum> getGameWiseData() {
        return gameWiseData;
    }

    public void setGameWiseData(List<GameWiseDatum> gameWiseData) {
        this.gameWiseData = gameWiseData;
    }

    @SerializedName("gameWiseOpeningBalanceData")
    @Expose
    private List<GameWiseOpeningBalanceDatum> gameWiseOpeningBalanceData = null;

    public void setGameWiseOpeningBalanceData(List<GameWiseOpeningBalanceDatum> gameWiseOpeningBalanceData) {
        this.gameWiseOpeningBalanceData = gameWiseOpeningBalanceData;
    }

    public List<GameWiseOpeningBalanceDatum> getGameWiseOpeningBalanceData() {
        return gameWiseOpeningBalanceData;
    }

    @SerializedName("gameWiseClosingBalanceData")
    @Expose
    private List<GameWiseClosingBalanceDatum> gameWiseClosingBalanceData = null;

    public void setGameWiseClosingBalanceData(List<GameWiseClosingBalanceDatum> gameWiseClosingBalanceData) {
        this.gameWiseClosingBalanceData = gameWiseClosingBalanceData;
    }

    public List<GameWiseClosingBalanceDatum> getGameWiseClosingBalanceData() {
        return gameWiseClosingBalanceData;
    }

    public Integer getReceivedBooks() {
        return receivedBooks;
    }

    public void setReceivedBooks(Integer receivedBooks) {
        this.receivedBooks = receivedBooks;
    }

    public Integer getReceivedTickets() {
        return receivedTickets;
    }

    public void setReceivedTickets(Integer receivedTickets) {
        this.receivedTickets = receivedTickets;
    }

    public Integer getReturnedBooks() {
        return returnedBooks;
    }

    public void setReturnedBooks(Integer returnedBooks) {
        this.returnedBooks = returnedBooks;
    }

    public Integer getReturnedTickets() {
        return returnedTickets;
    }

    public void setReturnedTickets(Integer returnedTickets) {
        this.returnedTickets = returnedTickets;
    }

    public Integer getSoldBooks() {
        return soldBooks;
    }

    public void setSoldBooks(Integer soldBooks) {
        this.soldBooks = soldBooks;
    }

    public Integer getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(Integer soldTickets) {
        this.soldTickets = soldTickets;
    }

    public Integer getTicketsClosingBalance() {
        return ticketsClosingBalance;
    }

    public void setTicketsClosingBalance(Integer ticketsClosingBalance) {
        this.ticketsClosingBalance = ticketsClosingBalance;
    }

    public Integer getTicketsOpeningBalance() {
        return ticketsOpeningBalance;
    }

    public void setTicketsOpeningBalance(Integer ticketsOpeningBalance) {
        this.ticketsOpeningBalance = ticketsOpeningBalance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(InventoryFlowReportResponseBean.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("responseCode");
        sb.append('=');
        sb.append(((this.responseCode == null) ? "<null>" : this.responseCode));
        sb.append(',');
        sb.append("responseMessage");
        sb.append('=');
        sb.append(((this.responseMessage == null) ? "<null>" : this.responseMessage));
        sb.append(',');
        sb.append("booksClosingBalance");
        sb.append('=');
        sb.append(((this.booksClosingBalance == null) ? "<null>" : this.booksClosingBalance));
        sb.append(',');
        sb.append("booksOpeningBalance");
        sb.append('=');
        sb.append(((this.booksOpeningBalance == null) ? "<null>" : this.booksOpeningBalance));
        sb.append(',');
        sb.append("gameWiseData");
        sb.append('=');
        sb.append(((this.gameWiseData == null) ? "<null>" : this.gameWiseData));
        sb.append(',');
        sb.append("gameWiseOpeningBalanceData");
        sb.append('=');
        sb.append(((this.gameWiseOpeningBalanceData == null)?"<null>":this.gameWiseOpeningBalanceData));
        sb.append(',');
        sb.append("gameWiseClosingBalanceData");
        sb.append('=');
        sb.append(((this.gameWiseClosingBalanceData == null)?"<null>":this.gameWiseClosingBalanceData));
        sb.append(',');
        sb.append("receivedBooks");
        sb.append('=');
        sb.append(((this.receivedBooks == null) ? "<null>" : this.receivedBooks));
        sb.append(',');
        sb.append("receivedTickets");
        sb.append('=');
        sb.append(((this.receivedTickets == null) ? "<null>" : this.receivedTickets));
        sb.append(',');
        sb.append("returnedBooks");
        sb.append('=');
        sb.append(((this.returnedBooks == null) ? "<null>" : this.returnedBooks));
        sb.append(',');
        sb.append("returnedTickets");
        sb.append('=');
        sb.append(((this.returnedTickets == null) ? "<null>" : this.returnedTickets));
        sb.append(',');
        sb.append("soldBooks");
        sb.append('=');
        sb.append(((this.soldBooks == null) ? "<null>" : this.soldBooks));
        sb.append(',');
        sb.append("soldTickets");
        sb.append('=');
        sb.append(((this.soldTickets == null) ? "<null>" : this.soldTickets));
        sb.append(',');
        sb.append("ticketsClosingBalance");
        sb.append('=');
        sb.append(((this.ticketsClosingBalance == null) ? "<null>" : this.ticketsClosingBalance));
        sb.append(',');
        sb.append("ticketsOpeningBalance");
        sb.append('=');
        sb.append(((this.ticketsOpeningBalance == null) ? "<null>" : this.ticketsOpeningBalance));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public class GameWiseDatum {

        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("gameNumber")
        @Expose
        private Integer gameNumber;
        @SerializedName("receivedBooks")
        @Expose
        private Integer receivedBooks;
        @SerializedName("receivedTickets")
        @Expose
        private Integer receivedTickets;
        @SerializedName("returnedBooks")
        @Expose
        private Integer returnedBooks;
        @SerializedName("returnedTickets")
        @Expose
        private Integer returnedTickets;
        @SerializedName("soldBooks")
        @Expose
        private Integer soldBooks;
        @SerializedName("soldTickets")
        @Expose
        private Integer soldTickets;

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public Integer getGameNumber() {
            return gameNumber;
        }

        public void setGameNumber(Integer gameNumber) {
            this.gameNumber = gameNumber;
        }

        public Integer getReceivedBooks() {
            return receivedBooks;
        }

        public void setReceivedBooks(Integer receivedBooks) {
            this.receivedBooks = receivedBooks;
        }

        public Integer getReceivedTickets() {
            return receivedTickets;
        }

        public void setReceivedTickets(Integer receivedTickets) {
            this.receivedTickets = receivedTickets;
        }

        public Integer getReturnedBooks() {
            return returnedBooks;
        }

        public void setReturnedBooks(Integer returnedBooks) {
            this.returnedBooks = returnedBooks;
        }

        public Integer getReturnedTickets() {
            return returnedTickets;
        }

        public void setReturnedTickets(Integer returnedTickets) {
            this.returnedTickets = returnedTickets;
        }

        public Integer getSoldBooks() {
            return soldBooks;
        }

        public void setSoldBooks(Integer soldBooks) {
            this.soldBooks = soldBooks;
        }

        public Integer getSoldTickets() {
            return soldTickets;
        }

        public void setSoldTickets(Integer soldTickets) {
            this.soldTickets = soldTickets;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(GameWiseDatum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("gameName");
            sb.append('=');
            sb.append(((this.gameName == null) ? "<null>" : this.gameName));
            sb.append(',');
            sb.append("gameNumber");
            sb.append('=');
            sb.append(((this.gameNumber == null) ? "<null>" : this.gameNumber));
            sb.append(',');
            sb.append("receivedBooks");
            sb.append('=');
            sb.append(((this.receivedBooks == null) ? "<null>" : this.receivedBooks));
            sb.append(',');
            sb.append("receivedTickets");
            sb.append('=');
            sb.append(((this.receivedTickets == null) ? "<null>" : this.receivedTickets));
            sb.append(',');
            sb.append("returnedBooks");
            sb.append('=');
            sb.append(((this.returnedBooks == null) ? "<null>" : this.returnedBooks));
            sb.append(',');
            sb.append("returnedTickets");
            sb.append('=');
            sb.append(((this.returnedTickets == null) ? "<null>" : this.returnedTickets));
            sb.append(',');
            sb.append("soldBooks");
            sb.append('=');
            sb.append(((this.soldBooks == null) ? "<null>" : this.soldBooks));
            sb.append(',');
            sb.append("soldTickets");
            sb.append('=');
            sb.append(((this.soldTickets == null) ? "<null>" : this.soldTickets));
            sb.append(',');
            if (sb.charAt((sb.length() - 1)) == ',') {
                sb.setCharAt((sb.length() - 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

    public class GameWiseOpeningBalanceDatum {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("gameNumber")
        @Expose
        private Integer gameNumber;
        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("totalBooks")
        @Expose
        private Integer totalBooks;
        @SerializedName("totalTickets")
        @Expose
        private Integer totalTickets;

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

        public Integer getGameNumber() {
            return gameNumber;
        }

        public void setGameNumber(Integer gameNumber) {
            this.gameNumber = gameNumber;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public Integer getTotalBooks() {
            return totalBooks;
        }

        public void setTotalBooks(Integer totalBooks) {
            this.totalBooks = totalBooks;
        }

        public Integer getTotalTickets() {
            return totalTickets;
        }

        public void setTotalTickets(Integer totalTickets) {
            this.totalTickets = totalTickets;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(GameWiseOpeningBalanceDatum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
            sb.append("gameId");
            sb.append('=');
            sb.append(((this.gameId == null)?"<null>":this.gameId));
            sb.append(',');
            sb.append("gameNumber");
            sb.append('=');
            sb.append(((this.gameNumber == null)?"<null>":this.gameNumber));
            sb.append(',');
            sb.append("gameName");
            sb.append('=');
            sb.append(((this.gameName == null)?"<null>":this.gameName));
            sb.append(',');
            sb.append("totalBooks");
            sb.append('=');
            sb.append(((this.totalBooks == null)?"<null>":this.totalBooks));
            sb.append(',');
            sb.append("totalTickets");
            sb.append('=');
            sb.append(((this.totalTickets == null)?"<null>":this.totalTickets));
            sb.append(',');
            if (sb.charAt((sb.length()- 1)) == ',') {
                sb.setCharAt((sb.length()- 1), ']');
            } else {
                sb.append(']');
            }
            return sb.toString();
        }

    }

    public class GameWiseClosingBalanceDatum {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("gameNumber")
        @Expose
        private Integer gameNumber;
        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("totalBooks")
        @Expose
        private Integer totalBooks;
        @SerializedName("totalTickets")
        @Expose
        private Integer totalTickets;

        public Integer getGameId() {
            return gameId;
        }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(Integer gameNumber) {
        this.gameNumber = gameNumber;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(Integer totalBooks) {
        this.totalBooks = totalBooks;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GameWiseClosingBalanceDatum.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("gameId");
        sb.append('=');
        sb.append(((this.gameId == null)?"<null>":this.gameId));
        sb.append(',');
        sb.append("gameNumber");
        sb.append('=');
        sb.append(((this.gameNumber == null)?"<null>":this.gameNumber));
        sb.append(',');
        sb.append("gameName");
        sb.append('=');
        sb.append(((this.gameName == null)?"<null>":this.gameName));
        sb.append(',');
        sb.append("totalBooks");
        sb.append('=');
        sb.append(((this.totalBooks == null)?"<null>":this.totalBooks));
        sb.append(',');
        sb.append("totalTickets");
        sb.append('=');
        sb.append(((this.totalTickets == null)?"<null>":this.totalTickets));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
      }
    }
}