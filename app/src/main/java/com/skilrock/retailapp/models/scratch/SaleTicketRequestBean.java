package com.skilrock.retailapp.models.scratch;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class SaleTicketRequestBean {

    private String gameType;

    private String soldChannel;

    private String[] ticketNumberList;

    private String userName;

    private String userSessionId;

    private String modelCode;

    private String terminalId;

    public String getGameType ()
    {
        return gameType;
    }

    public void setGameType (String gameType)
    {
        this.gameType = gameType;
    }

    public String getSoldChannel ()
    {
        return soldChannel;
    }

    public void setSoldChannel (String soldChannel)
    {
        this.soldChannel = soldChannel;
    }

    public String[] getTicketNumberList ()
    {
        return ticketNumberList;
    }

    public void setTicketNumberList (String[] ticketNumberList)
    {
        this.ticketNumberList = ticketNumberList;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getUserSessionId ()
    {
        return userSessionId;
    }

    public void setUserSessionId (String userSessionId)
    {
        this.userSessionId = userSessionId;
    }

    public String getModelCode()
    {
        return modelCode;
    }

    public void setModelCode (String modelCode)
    {
        this.modelCode = modelCode;
    }

    public String getTerminalId()
    {
        return terminalId;
    }

    public void setTerminalId (String terminalId)
    {
        this.terminalId = terminalId;
    }
    @NonNull
    @Override
    public String toString()
    {
        return "SaleTicketRequestBean [gameType: " + gameType + ", soldChannel: " + soldChannel +
                ", ticketNumberList: " + Arrays.toString(ticketNumberList) + ", userName: " + userName + ", userSessionId: " + userSessionId + "]";
    }

}
