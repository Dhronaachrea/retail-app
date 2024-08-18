package com.skilrock.retailapp.models.rms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GameListBean {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("minimumBooksQuantity")
    @Expose
    private Integer minimumBooksQuantity;

    @SerializedName("maximumBooksQuantity")
    @Expose
    private Integer maximumBooksQuantity;

    @SerializedName("games")
    @Expose
    private ArrayList<Game> games = null;

    public Integer getMinimumBooksQuantity() {
        return minimumBooksQuantity;
    }

    public void setMinimumBooksQuantity(Integer minimumBooksQuantity) {
        this.minimumBooksQuantity = minimumBooksQuantity;
    }

    public Integer getMaximumBooksQuantity() {
        return maximumBooksQuantity;
    }

    public void setMaximumBooksQuantity(Integer maximumBooksQuantity) {
        this.maximumBooksQuantity = maximumBooksQuantity;
    }

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

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "GameListBean{" + "responseCode=" + responseCode + ", responseMessage='" + responseMessage + '\'' + ", games=" + games + '}';
    }

    public static class Game {

        @SerializedName("gameId")
        @Expose
        private Integer gameId;
        @SerializedName("gameName")
        @Expose
        private String gameName;
        @SerializedName("gameNumber")
        @Expose
        private Integer gameNumber;
        @SerializedName("gameStatus")
        @Expose
        private String gameStatus;
        @SerializedName("saleStartDate")
        @Expose
        private String saleStartDate;
        @SerializedName("saleEndDate")
        @Expose
        private String saleEndDate;
        @SerializedName("winningEndDate")
        @Expose
        private String winningEndDate;
        @SerializedName("gameType")
        @Expose
        private String gameType;
        @SerializedName("ticketPrice")
        @Expose
        private Double ticketPrice;
        @SerializedName("addInventoryStatus")
        @Expose
        private String addInventoryStatus;
        @SerializedName("packNumberDigits")
        @Expose
        private Integer packNumberDigits;
        @SerializedName("bookNumberDigits")
        @Expose
        private Integer bookNumberDigits;
        @SerializedName("booksPerPack")
        @Expose
        private Integer booksPerPack;
        @SerializedName("ticketNumberDigits")
        @Expose
        private Integer ticketNumberDigits;
        @SerializedName("ticketsPerBook")
        @Expose
        private Integer ticketsPerBook;
        @SerializedName("packsPerGame")
        @Expose
        private Integer packsPerGame;
        @SerializedName("commissionPercentage")
        @Expose
        private Double commissionPercentage;

        public Integer getGameId() {
            return gameId;
        }

        public void setGameId(Integer gameId) {
            this.gameId = gameId;
        }

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

        public String getGameStatus() {
            return gameStatus;
        }

        public void setGameStatus(String gameStatus) {
            this.gameStatus = gameStatus;
        }

        public String getSaleStartDate() {
            return saleStartDate;
        }

        public void setSaleStartDate(String saleStartDate) {
            this.saleStartDate = saleStartDate;
        }

        public String getSaleEndDate() {
            return saleEndDate;
        }

        public void setSaleEndDate(String saleEndDate) {
            this.saleEndDate = saleEndDate;
        }

        public String getWinningEndDate() {
            return winningEndDate;
        }

        public void setWinningEndDate(String winningEndDate) {
            this.winningEndDate = winningEndDate;
        }

        public String getGameType() {
            return gameType;
        }

        public void setGameType(String gameType) {
            this.gameType = gameType;
        }

        public Double getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(Double ticketPrice) {
            this.ticketPrice = ticketPrice;
        }

        public String getAddInventoryStatus() {
            return addInventoryStatus;
        }

        public void setAddInventoryStatus(String addInventoryStatus) {
            this.addInventoryStatus = addInventoryStatus;
        }

        public Integer getPackNumberDigits() {
            return packNumberDigits;
        }

        public void setPackNumberDigits(Integer packNumberDigits) {
            this.packNumberDigits = packNumberDigits;
        }

        public Integer getBookNumberDigits() {
            return bookNumberDigits;
        }

        public void setBookNumberDigits(Integer bookNumberDigits) {
            this.bookNumberDigits = bookNumberDigits;
        }

        public Integer getBooksPerPack() {
            return booksPerPack;
        }

        public void setBooksPerPack(Integer booksPerPack) {
            this.booksPerPack = booksPerPack;
        }

        public Integer getTicketNumberDigits() {
            return ticketNumberDigits;
        }

        public void setTicketNumberDigits(Integer ticketNumberDigits) {
            this.ticketNumberDigits = ticketNumberDigits;
        }

        public Integer getTicketsPerBook() {
            return ticketsPerBook;
        }

        public void setTicketsPerBook(Integer ticketsPerBook) {
            this.ticketsPerBook = ticketsPerBook;
        }

        public Integer getPacksPerGame() {
            return packsPerGame;
        }

        public void setPacksPerGame(Integer packsPerGame) {
            this.packsPerGame = packsPerGame;
        }

        public Double getCommissionPercentage() {
            return commissionPercentage;
        }

        public void setCommissionPercentage(Double commissionPercentage) {
            this.commissionPercentage = commissionPercentage;
        }

        @Override
        public String toString() {
            return "Games{" + "gameId=" + gameId + ", gameName='" + gameName + '\'' + ", gameNumber=" + gameNumber + ", gameStatus='" + gameStatus + '\'' + ", saleStartDate='" + saleStartDate + '\'' + ", saleEndDate='" + saleEndDate + '\'' + ", winningEndDate='" + winningEndDate + '\'' + ", gameType='" + gameType + '\'' + ", ticketPrice=" + ticketPrice + ", addInventoryStatus='" + addInventoryStatus + '\'' + ", packNumberDigits=" + packNumberDigits + ", bookNumberDigits=" + bookNumberDigits + ", booksPerPack=" + booksPerPack + ", ticketNumberDigits=" + ticketNumberDigits + ", ticketsPerBook=" + ticketsPerBook + ", packsPerGame=" + packsPerGame + ", commissionPercentage=" + commissionPercentage + '}';
        }
    }

}
