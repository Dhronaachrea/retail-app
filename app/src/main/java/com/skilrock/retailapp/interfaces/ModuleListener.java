package com.skilrock.retailapp.interfaces;

import com.skilrock.retailapp.models.rms.HomeDataBean;

public interface ModuleListener {
    void onModuleClicked(String menuCode, int index, HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean);
}
