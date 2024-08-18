package com.skilrock.retailapp.utils;

import android.content.Context;

import com.skilrock.retailapp.R;

import java.util.HashMap;

public class StringMapOla {

    public static HashMap<String, String> map = new HashMap<>();

    public StringMapOla(Context context) {
        map.put("M_LEDGER", context.getString(R.string.ledger_report_caption));
        map.put("M_OLA_REPORT", context.getString(R.string.m_ola_report_caption));
        map.put("M_SUMMARIZE_LEDGER", context.getString(R.string.summarize_ledger_report_caption));
        map.put("OLA", context.getString(R.string.player_management_caption));
        map.put("REPORTS", context.getString(R.string.reports_caption));
        map.put("M_OLA_DEPOSIT", context.getString(R.string.deposit_caption));
        map.put("M_OLA_WITHDRAWAL", context.getString(R.string.withdrawal_caption));
        map.put("M_OLA_REGISTRATION", context.getString(R.string.registration_caption));
        map.put("USERS", context.getString(R.string.organisation_caption));
        map.put("M_USER_REG", context.getString(R.string.user_registration_caption));
        map.put("M_USER_SEARCH", context.getString(R.string.search_user_caption));
        map.put("M_INTRA_ORG_CASH_MGMT", context.getString(R.string.cash_management_caption));
        map.put("M_INTRA_ORG_SETTLEMENT", context.getString(R.string.account_settlement_caption));
        map.put("M_SETTLEMENT_REPORT", context.getString(R.string.settlement_report_caption));
        map.put("M_CHANGE_PASS", context.getString(R.string.change_pin_caption));
        map.put("M_LOGOUT", context.getString(R.string.logout_caption));
        map.put("M_OLA_PENDING_TXN", context.getString(R.string.pending_transaction));
        map.put("M_NET_GAMING_DETAILS", context.getString(R.string.net_gaming_details));
        map.put("M_OLA_PLR_SEARCH", context.getString(R.string.player_search));
        map.put("M_OLA_PLR_LEDGER", context.getString(R.string.player_transaction));
        map.put("M_OLA_PROMO_CODE", context.getString(R.string.my_promo_code));
        //map.put("M_BALANCE_INVOICE_REPORT", context.getString(R.string.balance_invoice_report));
        //map.put("M_OPERATIONAL_CASH_REPORT", context.getString(R.string.operational_cash_report));
    }

    public static String getCaption(String code, String caption) {
        if (!map.containsKey(code)) return caption;

        return map.get(code);
    }
}