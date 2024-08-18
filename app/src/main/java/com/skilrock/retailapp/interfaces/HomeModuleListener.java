package com.skilrock.retailapp.interfaces;

import com.skilrock.retailapp.models.rms.HomeDataBean;

import java.util.ArrayList;

public interface HomeModuleListener {
    void onHomeModuleClicked(String moduleCode, int index, String displayName, ArrayList<HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList> listMenuBean);
}
