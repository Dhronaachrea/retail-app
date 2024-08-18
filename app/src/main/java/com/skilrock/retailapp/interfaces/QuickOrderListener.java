package com.skilrock.retailapp.interfaces;

public interface QuickOrderListener {
    void onGameSelected(int gameId, int numberOfBooks, double amount, double commission);
}
