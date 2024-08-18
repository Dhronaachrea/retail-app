package com.skilrock.retailapp.portrait_draw_games.ui;

import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.skilrock.retailapp.R;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.FiveByNinetySaleResponseBean;
import com.skilrock.retailapp.models.drawgames.ResultRequestBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelBean;
import com.skilrock.retailapp.models.drawgames.TicketCancelResponseBean;
import com.skilrock.retailapp.models.drawgames.TicketReprintBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

public class FloatingDrawerActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fb_close, reprint, winning_claim, last_result, cancel_ticket;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    //private DrawGameViewModel viewModel;
    private UsbThermalPrinter usbThermalPrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_action_buton);
        setInitialParameters();
        initializeWidgets();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MENU_BEAN = bundle.getParcelable("MenuBean");
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            FloatingDrawerActivity.this.finish();
        }
    }

    private void initializeWidgets() {
        //viewModel = ViewModelProviders.of(this).get(DrawGameViewModel.class);
        //viewModel.getLiveDataReprint().observe(this, observerReprint);
        //viewModel.getLiveDataCancel().observe(this, observerCancel);

        fb_close = findViewById(R.id.fb_close);
        reprint = findViewById(R.id.reprint);
        winning_claim = findViewById(R.id.winning_claim);
        last_result = findViewById(R.id.last_result);
        cancel_ticket = findViewById(R.id.cancel_ticket);

        fb_close.setOnClickListener(this);
        reprint.setOnClickListener(this);
        winning_claim.setOnClickListener(this);
        last_result.setOnClickListener(this);
        cancel_ticket.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fb_close) {
            finish();
        } else if (view.getId() == R.id.reprint) {
            callReprint();
        } else if (view.getId() == R.id.winning_claim) {
            Intent winning=new Intent(FloatingDrawerActivity.this,WinningClaimActivity.class);
            winning.putExtra("MenuBean", MENU_BEAN);
            startActivity(winning);
            finish();
        } else if (view.getId() == R.id.last_result) {
           callResult();
        } else if (view.getId() == R.id.cancel_ticket) {
            callCancel();
        }
    }

    private void callResult() {
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "getSchemaByGame");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            ResultRequestBean resultRequestBean=new ResultRequestBean();




        }

    }

    private void callCancel() {
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "cancelTicket");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            TicketCancelBean ticketCancelBean = new TicketCancelBean();
            ticketCancelBean.setGameCode(SharedPrefUtil.getLastGameCode(this, PrintUtil.LAST_GAME_CODE));
            ticketCancelBean.setAutoCancel("AUTOCANCEL");
            ticketCancelBean.setCancelChannel("WEB");
            ticketCancelBean.setAutoCancel(true);
            ticketCancelBean.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            ticketCancelBean.setUserId(PlayerData.getInstance().getUserId());
            ticketCancelBean.setTicketNumber(SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.LAST_TICKET_NUMBER));

            //viewModel.callCancel(urlBean,ticketCancelBean);

        }

    }

    private void callReprint() {
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "reprintTicket");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            TicketReprintBean ticketReprintBean = new TicketReprintBean();
            ticketReprintBean.setGameCode(SharedPrefUtil.getLastGameCode(this, PrintUtil.LAST_GAME_CODE));
            ticketReprintBean.setPurchaseChannel("RETAIL");
            ticketReprintBean.setPwt(false);
            ticketReprintBean.setTicketNumber(SharedPrefUtil.getLastTicketNumber(this, SharedPrefUtil.LAST_TICKET_NUMBER));

            //viewModel.callReprint(urlBean, ticketReprintBean);
        }
    }


    public UsbThermalPrinter getUsbThermalPrinter() {
        if (usbThermalPrinter == null) {
            usbThermalPrinter = new UsbThermalPrinter(FloatingDrawerActivity.this);
        }
        return usbThermalPrinter;
    }

    Observer<FiveByNinetySaleResponseBean> observerReprint = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, "Reprint", getString(R.string.something_went_wrong));

        else if (response.getResponseCode() == 0) {
           /* usbThermalPrinter = getUsbThermalPrinter();
            PrintUtil.getPrintDataTelpo570(response, this, usbThermalPrinter, true);
            finish();*/
        }else{
            Intent i =new Intent();
            i.putExtra("error_msg",  response.getResponseMessage());
            i.putExtra("header","Reprint");
            setResult(01,i);
            finish();
        }

    };


    Observer<TicketCancelResponseBean> observerCancel = response -> {
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, "Cancel", getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
           /* usbThermalPrinter = getUsbThermalPrinter();
            PrintUtil.getPrintDataTelpoCancelTicket(response, this, usbThermalPrinter);
            finish();*/
        }else {
            if (Utils.checkForSessionExpiryActivity(FloatingDrawerActivity.this, response.getResponseCode(), FloatingDrawerActivity.this)) return;

          Intent i =new Intent();
          i.putExtra("error_msg",  response.getResponseMessage());
          i.putExtra("header","Reprint");
          setResult(01,i);
          finish();
        }

    };
}
