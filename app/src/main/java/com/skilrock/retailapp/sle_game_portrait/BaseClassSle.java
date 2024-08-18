package com.skilrock.retailapp.sle_game_portrait;

import android.content.Context;

import com.skilrock.retailapp.interfaces.NoPaper;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.sle_game_portrait.sle_land_scape.ActivityGamePlayLand;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.util.ArrayList;
import java.util.List;

public class BaseClassSle implements Cloneable {

    private static BaseClassSle baseClassSle;
    private UsbThermalPrinter usbThermalPrinter;
    private ActivityDraws activityDraws;
    private ActivityGamePlay activityGamePlay;
    private ActivityGamePlayLand activityGamePlayLand;
    private String BASE_URL;
    //fetchGames    //sale  //reprint   //cancel    //verifyTicket  //claimTicket   //matchList     //resultList
    private UrlDrawGameBean fetchBean;
    private UrlDrawGameBean salBean;
    private UrlDrawGameBean cancelBean;
    private UrlDrawGameBean reprintBean;
    private UrlDrawGameBean verifyBean;
    private UrlDrawGameBean claimBean;
    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public void setBASE_URL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    public UrlDrawGameBean getFetchBean() {
        return fetchBean;
    }

    public void setFetchBean(UrlDrawGameBean fetchBean) {
        this.fetchBean = fetchBean;
    }

    public UrlDrawGameBean getSalBean() {
        return salBean;
    }

    public void setSalBean(UrlDrawGameBean salBean) {
        this.salBean = salBean;
    }

    public UrlDrawGameBean getCancelBean() {
        return cancelBean;
    }

    public void setCancelBean(UrlDrawGameBean cancelBean) {
        this.cancelBean = cancelBean;
    }

    public UrlDrawGameBean getReprintBean() {
        return reprintBean;
    }

    public void setReprintBean(UrlDrawGameBean reprintBean) {
        this.reprintBean = reprintBean;
    }

    public UrlDrawGameBean getVerifyBean() {
        return verifyBean;
    }

    public void setVerifyBean(UrlDrawGameBean verifyBean) {
        this.verifyBean = verifyBean;
    }

    public UrlDrawGameBean getClaimBean() {
        return claimBean;
    }

    public void setClaimBean(UrlDrawGameBean claimBean) {
        this.claimBean = claimBean;
    }

    public UrlDrawGameBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(UrlDrawGameBean resultBean) {
        this.resultBean = resultBean;
    }

    private UrlDrawGameBean resultBean;


    public boolean isIs_b2c_sports() {
        return is_b2c_sports;
    }

    public void setIs_b2c_sports(boolean is_b2c_sports) {
        this.is_b2c_sports = is_b2c_sports;
    }

    private boolean is_b2c_sports = false;

    public NoPaper getNoPaper() {
        return noPaper;
    }

    public void setNoPaper(NoPaper noPaper) {
        this.noPaper = noPaper;
    }

    private NoPaper noPaper;

    public ActivityDraws getActivityDraws() {
        return activityDraws;
    }

    public void setActivityDraws(ActivityDraws activityDraws) {
        this.activityDraws = activityDraws;
    }

    public ActivityGamePlay getActivityGamePlay() {
        return activityGamePlay;
    }

    public void setActivityGamePlay(ActivityGamePlay activityGamePlay) {
        this.activityGamePlay = activityGamePlay;
    }


    public void setActivityGamePlayLand(ActivityGamePlayLand activityGamePlayLand) {
        this.activityGamePlayLand = activityGamePlayLand;
    }

    public ActivityGamePlayFinal getActivityGamePlayFinal() {
        return activityGamePlayFinal;
    }

    public void setActivityGamePlayFinal(ActivityGamePlayFinal activityGamePlayFinal) {
        this.activityGamePlayFinal = activityGamePlayFinal;
    }

    public void afterFetch(){
        if(activityGamePlayFinal != null){
            activityGamePlayFinal.finish();
        }
        if(activityGamePlay != null){
            activityGamePlay.finish();
        }
        if(activityDraws != null){
            activityDraws.setDrawsAfterUpdate();
        }

        if (activityGamePlayLand!=null){
            activityGamePlayLand.setdraws();
        }
    }
    private ActivityGamePlayFinal activityGamePlayFinal;

    private SaleBean saleBean;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    private int gameId;

    public List<SaleBean.DrawInfoBean.EventDataBean> getEventDataBeans() {
        return eventDataBeans;
    }

    public void setEventDataBeans(List<SaleBean.DrawInfoBean.EventDataBean> eventDataBeans) {
        this.eventDataBeans = eventDataBeans;
    }

    private List<SaleBean.DrawInfoBean.EventDataBean> eventDataBeans = new ArrayList<>();

    public List<SaleBean.DrawInfoBean> getDrawInfoBeans() {
        return drawInfoBeans;
    }

    public void setDrawInfoBeans(List<SaleBean.DrawInfoBean> drawInfoBeans) {
        this.drawInfoBeans = drawInfoBeans;
    }

    private List<SaleBean.DrawInfoBean> drawInfoBeans = new ArrayList<>();

    public SaleBean getSaleBean() {
        return saleBean;
    }

    public void setSaleBean(SaleBean saleBean) {
        this.saleBean = saleBean;
    }

    public UsbThermalPrinter getUsbThermalPrinter(Context context) {
        if (usbThermalPrinter == null) {
            usbThermalPrinter = new UsbThermalPrinter(context);
        }
        return usbThermalPrinter;
    }



    public synchronized static BaseClassSle getBaseClassSle(){
        if(baseClassSle == null){
            baseClassSle = new BaseClassSle();
        }
        return baseClassSle;
    }

    private BaseClassSle(){

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new CloneNotSupportedException();
    }
}
