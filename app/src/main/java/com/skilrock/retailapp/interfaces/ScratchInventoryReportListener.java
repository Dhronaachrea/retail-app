package com.skilrock.retailapp.interfaces;

import java.util.ArrayList;

public interface ScratchInventoryReportListener {

    void onCardClick(ArrayList<String> listInTransit, ArrayList<String> listReceived, ArrayList<String> listActivated, ArrayList<String> listInvoiced);

}
