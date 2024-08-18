package com.skilrock.retailapp.ui.fragments.scratch;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.MultiTicketClaimAdapter;
import com.skilrock.retailapp.dialog.MultiSaleTicketInvalidDialog;
import com.skilrock.retailapp.dialog.MultiSaleTicketSuccessDialog;
import com.skilrock.retailapp.dialog.MultiWinTicketClaimSuccessDialog;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.GetTicketStatusResponse;
import com.skilrock.retailapp.models.scratch.GetVerifyTicketResponseBean;
import com.skilrock.retailapp.models.scratch.MultiClaimTicketResponseBean;
import com.skilrock.retailapp.models.scratch.WinningDisplayDialogBean;
import com.skilrock.retailapp.portrait_draw_games.ui.PrintDrawGameActivity;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.MethodCallback;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.StringMapOla;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.WinningClaimViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiWinClaimTicketCartGelsaFragment extends BaseFragment implements View.OnClickListener, MethodCallback {

    private WinningClaimViewModel winningClaimViewModel;
    private Button buttonProcced;
    private Button resetBtn;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private WinningDisplayDialogBean winningModel;
    private MultiWinTicketClaimSuccessDialog multiWinSuccessDialog;
    private final String SCRATCH = "scratch";
    private TextInputLayout tilTicketNumber;
    private TextView amount;
    private MultiTicketClaimAdapter multiTicketClaimAdapter;
    private RecyclerView recyclerView;
    private TextView totalNoOFTickets;
    private ArrayList<String> deletedTicketList = new ArrayList<String>();
    private static final int REQUEST_CODE_WINNING = 1111;
    private MultiSaleTicketInvalidDialog multiSaleTicketInvalidDialog;
    private MultiSaleTicketSuccessDialog multiSaleTicketSuccessDialog;

    private  ArrayList<GetVerifyTicketResponseBean> ticketsData;
    private GetTicketStatusResponse.Data invalidTicketsData;
    private Handler handler;

    private float totalAmount = 0;
    private int noOfTickets = 0;

    List<GetTicketStatusResponse.Game> filteredData2 = Collections.emptyList();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanning_mutli_win_claim_cart, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            winningClaimViewModel = ViewModelProviders.of(this).get(WinningClaimViewModel.class);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);

        ErrorDialogListener errorDialogListener = this::callBack;

        // for single claim not call now
        winningClaimViewModel.getClaimTicketResponseData().observe(this, claimTicketResponseBean -> {
                /*{
                      "responseCode": 1000,
                      "responseMessage": "Success",
                      "response": {
                        "data": [
                          {
                            "barCode": "502912549001234",
                            "message": "The ticket has already been claimed"
                          },
                          {
                            "barCode": "502413600981234",
                            "message": "Invalid ticket"
                          },
                          {
                            "barCode": "502357765321234",
                            "message": "The ticket has already been claimed"
                          },
                          {
                            "barCode": "502418500981234",
                            "message": "The ticket has already been claimed"
                          },
                          {
                            "barCode": "502395415851234",
                            "message": {
                              "transactionId": "1383",
                              "transactionNumber": "WCRR24070001383",
                              "transactionDate": "2024-07-30 14:15:13",
                              "ticketNumber": "502-002022-029",
                              "virnNumber": "50239541585",
                              "winningAmount": 2500,
                              "taxAmount": 487.5,
                              "commissionAmount": 0,
                              "tdsAmount": 0,
                              "netWinningAmount": 2012.5,
                              "soldByOrg": "ORG21121709AAAC_mridulret",
                              "claimedLocation": "Skilrock, Cybercity, Gurugram, Hary",
                              "claimedByOrg": "ORG21121709AAAC_mridulret",
                              "txnStatus": "DONE"
                            }
                          }
                        ]
                      }
                    }
                    */
            //ClaimTicketResponseNewBean claimTicketResponseBean = new Gson().fromJson(res2, ClaimTicketResponseNewBean.class);*/
            ProgressBarDialog.getProgressDialog().dismiss();

            if (claimTicketResponseBean == null)
                Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), getString(R.string.something_went_wrong), errorDialogListener);
            else if (claimTicketResponseBean.getResponseCode() == 1000) {
                if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO) || Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
                    Intent intent = new Intent(getActivity(), PrintDrawGameActivity.class);
                    intent.putExtra("PrintDataScratch", claimTicketResponseBean);
                    intent.putExtra("Category", PrintDrawGameActivity.WINNING_SCRATCH);
                    startActivityForResult(intent, REQUEST_CODE_WINNING);
                } else {

                    (master).callBalRefreshForSubUser();
                    winningClaimViewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
                    winningModel = new WinningDisplayDialogBean();
                    winningModel.setTransactionNumber(claimTicketResponseBean.getTransactionNumber());
                    winningModel.setTransactionDate(claimTicketResponseBean.getTransactionDate());

                    String tdsAmount = claimTicketResponseBean.getTdsAmount() == null ? "0" : String.valueOf(claimTicketResponseBean.getTdsAmount());
                    winningModel.setTicketNumber(claimTicketResponseBean.getTicketNumber());
                    winningModel.setWinningAmount(String.valueOf(claimTicketResponseBean.getWinningAmount()));
                    winningModel.setTdsAmount(tdsAmount);
                    winningModel.setNetWinningAmount(String.valueOf(claimTicketResponseBean.getNetWinningAmount()));
                    winningModel.setCommissionAmount(String.valueOf(claimTicketResponseBean.getCommissionAmount()));
                    winningModel.setTaxAmount(String.valueOf(claimTicketResponseBean.getTaxAmount()));
                    /*ClaimDialog dialog = new ClaimDialog(master, getFragmentManager(), listener, "", false, winningModel);
                    dialog.show();
                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }*/
                }
            } else {
                if (Utils.checkForSessionExpiry(master, claimTicketResponseBean.getResponseCode()))
                    return;

                String errorMsg = Utils.getResponseMessage(master, SCRATCH, claimTicketResponseBean.getResponseCode());
                Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), errorMsg, errorDialogListener);
            }
            allowBackAction(true);
        });

        // for multi claim
        winningClaimViewModel.getMultiClaimTicketResponseData().observe(this,
                new Observer<MultiClaimTicketResponseBean>() {
                    @Override
                    public void onChanged(MultiClaimTicketResponseBean multiClaimTicketResponseData) {
                        winningClaimViewModel.getMultiClaimTicketResponseData().removeObserver(this);
                /*{
                      "responseCode": 1000,
                      "responseMessage": "Success",
                      "response": {
                        "data": [
                         {
                            "barCode": "502418500981234",
                            "message": "The ticket has already been claimed"
                          },
                          {
                            "barCode": "502395415851234",
                            "response": {
                              "transactionId": "1383",
                              "transactionNumber": "WCRR24070001383",
                              "transactionDate": "2024-07-30 14:15:13",
                              "ticketNumber": "502-002022-029",
                              "virnNumber": "50239541585",
                              "winningAmount": 2500,
                              "taxAmount": 487.5,
                              "commissionAmount": 0,
                              "tdsAmount": 0,
                              "netWinningAmount": 2012.5,
                              "soldByOrg": "ORG21121709AAAC_mridulret",
                              "claimedLocation": "Skilrock, Cybercity, Gurugram, Hary",
                              "claimedByOrg": "ORG21121709AAAC_mridulret",
                              "txnStatus": "DONE"
                            }
                          }
                        ]
                      }
                    }
                    */
                        //ClaimTicketResponseNewBean claimTicketResponseBean = new Gson().fromJson(res2, ClaimTicketResponseNewBean.class);*/
                        ProgressBarDialog.getProgressDialog().dismiss();

                        if (multiClaimTicketResponseData == null)
                            Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), getString(R.string.something_went_wrong), errorDialogListener);
                        else if (multiClaimTicketResponseData.getResponseCode() == 1000) {
                            (master).callBalRefreshForSubUser();
                            winningClaimViewModel.getUpdatedBalance(PlayerData.getInstance().getToken());
                            // after success response
                            if (multiWinSuccessDialog == null || !multiWinSuccessDialog.isShowing()) {
                                multiWinSuccessDialog = new MultiWinTicketClaimSuccessDialog(master, ticketsData,() -> {
                                    master.getSupportFragmentManager().popBackStack();
                                    master.getSupportFragmentManager().popBackStack();
                                });
                                multiWinSuccessDialog.show();
                                if (multiWinSuccessDialog.getWindow() != null) {
                                    multiWinSuccessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    multiWinSuccessDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                }
                            }

                        } else {
                            if (Utils.checkForSessionExpiry(master, multiClaimTicketResponseData.getResponseCode()))
                                return;

                            String errorMsg = Utils.getResponseMessage(master, SCRATCH, multiClaimTicketResponseData.getResponseCode());
                            Utils.showCustomErrorDialog(getContext(), getString(R.string.tickey_claim), errorMsg, errorDialogListener);
                        }
                        allowBackAction(true);

                    }

                }
        );


    }

    private void callBack() {
        final Handler handler1 = new Handler();
        final int delay = 1000; // 1000 milliseconds == 1 second
        handler1.postDelayed(new Runnable() {
            public void run() {
                if (isAdded() && isVisible()) {
                    Log.e("----------------", "mein call huaa hu");

                    //handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeWidgets(View view) {

        buttonProcced = view.findViewById(R.id.button_proceed);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvUsername = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        recyclerView = view.findViewById(R.id.rvTicketList);
        resetBtn = view.findViewById(R.id.resetBtn);
        amount = view.findViewById(R.id.amount);
        totalNoOFTickets = view.findViewById(R.id.tvTotalNoOfTickets);

        refreshBalance();
        resetBtn.setOnClickListener(this);
        buttonProcced.setOnClickListener(this);

        /*if (AppPermissions.checkPermission(getActivity())) startScanning();
        else AppPermissions.requestPermission(getFragmentManager());*/

        multiTicketClaimAdapter = new MultiTicketClaimAdapter(getContext(), Collections.emptyList(), mTicketNo -> {
            Log.i("TaG","cross btn click 3333333======" + mTicketNo);
            try {
                deletedTicketList.add(mTicketNo);
                for (GetVerifyTicketResponseBean gameData : ticketsData) {
                    List<GetVerifyTicketResponseBean.TicketAndStatus> ticketList = gameData.getTicketAndStatusList();
                    if (ticketList != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            ticketList.removeIf(ticket -> ticket.getTicketNumber().equals(mTicketNo));
                        }
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ticketsData.removeIf(a-> a.getTicketAndStatusList().isEmpty());
                }
                if(ticketsData.isEmpty()) {
                    master.getSupportFragmentManager().popBackStack();
                    master.getSupportFragmentManager().popBackStack();
                }

                noOfTickets = 0;
                totalAmount = 0;
                for (GetVerifyTicketResponseBean ticketData : ticketsData) {
                    noOfTickets += ticketData.getTicketAndStatusList().size();
                    for (GetVerifyTicketResponseBean.TicketAndStatus ticket : ticketData.getTicketAndStatusList()) {
                        totalAmount += Double.parseDouble(ticket.getWinAmount());
                    }
                }
                totalNoOFTickets.setText(getString(R.string.total_no_of_tickets) + " " + noOfTickets);
                amount.setText(totalAmount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));

//                setViewData();
                multiTicketClaimAdapter.setTicketsData(ticketsData);
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(multiTicketClaimAdapter);



        Bundle bundle = getArguments();
        Log.i("TaG","bundle value -------------->"+ bundle.getSerializable("ticketInfo"));
        if (bundle != null) {
            menuBean = bundle.getParcelable("MenuBean");
            tvTitle.setText(StringMapOla.getCaption(menuBean.getMenuCode(), menuBean.getCaption()));
            ticketsData = ( ArrayList<GetVerifyTicketResponseBean>) bundle.getSerializable("ticketInfo");


            setViewData();
            multiTicketClaimAdapter.setTicketsData(ticketsData);

        } else {
            ticketsData = null;
        }

        FragmentActivity activity = getActivity();
        /*if (bundle != null) {
            if (activity != null) {
                activity.setTitle(bundle.getString("title"));
                tvTitle.setText(bundle.getString("title"));
            }
            menuBean = bundle.getParcelable("MenuBean");
        }*/

    }


    public void setViewData() {
        for (GetVerifyTicketResponseBean ticketData : ticketsData) {
            noOfTickets += ticketData.getTicketAndStatusList().size();
            for(GetVerifyTicketResponseBean.TicketAndStatus ticketAndStatus : ticketData.getTicketAndStatusList()) {
                totalAmount += Double.parseDouble(ticketAndStatus.getWinAmount());
            }
        }
        totalNoOFTickets.setText(getString(R.string.total_no_of_tickets) + " " + noOfTickets);
        amount.setText(Utils.getUkFormat(totalAmount)+ " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_proceed: {
                if (!Utils.isNetworkConnected(master)) {
                    Toast.makeText(master, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
                    return;
                }
                UrlBean urlBeanClaimWin = Utils.getUrlDetails(menuBean, getContext(), "claimWin");
                if (urlBeanClaimWin != null) {
                    allowBackAction(false);
                    ProgressBarDialog.getProgressDialog().showProgress(master);
                    // call multiclaim ticket Api
                    ArrayList<String> ticketList = new ArrayList<>();
                    for(GetVerifyTicketResponseBean tickets : ticketsData) {
                        for (GetVerifyTicketResponseBean.TicketAndStatus ticket : tickets.getTicketAndStatusList()) {
                            ticketList.add(ticket.getBarCodeNumber());
                        }
                    }
                    Log.i("TaG","final response data------------->>>>" + ticketList);
                    winningClaimViewModel.callMultiClaimTicket(urlBeanClaimWin, ticketList);
                }

                break;
            }
            case R.id.resetBtn: {
                /*Bundle result = new Bundle();
                result.putBoolean("cleanData", true);
                // Set the result
                getParentFragmentManager().setFragmentResult("cleanData", result);*/

                master.getSupportFragmentManager().popBackStack();
                master.getSupportFragmentManager().popBackStack();

                break;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendDataToFragmentA(deletedTicketList);
        try{
            if (handler != null) handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            Log.i("TaG","Exception Occured");
        }
    }


    private void sendDataToFragmentA(ArrayList<String> ticketList) {
        // Create a bundle to hold the data
        Bundle result = new Bundle();
        result.putStringArrayList("ticket", ticketList); // Replace "key" and "value" with your actual key-value pair

        // Use FragmentManager to set the result
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.setFragmentResult("delete", result);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onClick() {

    }

    @FunctionalInterface
    public interface InvalidTicketDialogCallback {
        void onResult();
    }
    @FunctionalInterface
    public interface SuccessTicketDialogCallback {
        void onResult();
    }

    @FunctionalInterface
    public interface DeleteTicketCallBack {
        void onResult(String ticketNo);
    }

}