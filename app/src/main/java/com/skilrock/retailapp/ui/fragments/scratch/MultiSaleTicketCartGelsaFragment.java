package com.skilrock.retailapp.ui.fragments.scratch;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.adapter.scratch.MultiSaleTicketAdapter;
import com.skilrock.retailapp.dialog.MultiSaleTicketInvalidDialog;
import com.skilrock.retailapp.dialog.MultiSaleTicketSuccessDialog;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.models.scratch.GetTicketStatusResponse;
import com.skilrock.retailapp.ui.fragments.BaseFragment;
import com.skilrock.retailapp.utils.MethodCallback;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.StringMapOla;
import com.skilrock.retailapp.utils.Utils;
import com.skilrock.retailapp.viewmodels.scratch.SaleTicketViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MultiSaleTicketCartGelsaFragment extends BaseFragment implements View.OnClickListener, MethodCallback {

    private SaleTicketViewModel saleTicketViewModel;
    private Button buttonProcced;
    private Button resetBtn;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList menuBean;
    private final String SCRATCH = "scratch";
    private TextInputLayout tilTicketNumber;
    private TextView amount;
    private MultiSaleTicketAdapter multiSaleAdapter;
    private RecyclerView recyclerView;
    private TextView totalNoOFTickets;
    private ArrayList<String> deletedTicketList = new ArrayList<String>();

    private MultiSaleTicketInvalidDialog multiSaleTicketInvalidDialog;
    private MultiSaleTicketSuccessDialog multiSaleTicketSuccessDialog;

    private GetTicketStatusResponse.Data ticketsData;
    private GetTicketStatusResponse.Data invalidTicketsData;
    private Handler handler;

    private float totalAmount = 0;
    private int noOfTickets = 0;

    List<GetTicketStatusResponse.Game> filteredData2 = Collections.emptyList();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanning_mutli_sale_cart, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            //initialise view model
            saleTicketViewModel = ViewModelProviders.of(this).get(SaleTicketViewModel.class);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeWidgets(view);

        saleTicketViewModel.getGameListData().observe(getViewLifecycleOwner(), gameListBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();

            if (gameListBean == null)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            else if (gameListBean.getResponseCode() == 57575)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket));
            else if (gameListBean.getResponseCode() == 75757)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.no_games_found));
            else if (gameListBean.getResponseCode() == 74747)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.invalid_ticket_length));
            else {
                //String errorMsg = TextUtils.isEmpty(gameListBean.getResponseMessage()) ? getString(R.string.error_fetching_gamelist) : gameListBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, gameListBean.getResponseCode());
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, errorMsg);
            }
        });

        saleTicketViewModel.getResponseData().observe(getViewLifecycleOwner(), responseCodeMessageBean -> {
            ProgressBarDialog.getProgressDialog().dismiss();
            if (responseCodeMessageBean == null)
                Utils.showMessageDialog(getContext(), BuildConfig.app_name, getString(R.string.something_went_wrong));
            else if (responseCodeMessageBean.getResponseCode() == 1000) {
                (master).callBalRefreshForSubUser(); // for balance update
                if (multiSaleTicketSuccessDialog == null || !multiSaleTicketSuccessDialog.isShowing()) {
                    multiSaleTicketSuccessDialog = new MultiSaleTicketSuccessDialog(master, ticketsData, () -> {
                        master.getSupportFragmentManager().popBackStack();
                        master.getSupportFragmentManager().popBackStack();
                    });
                    multiSaleTicketSuccessDialog.show();
                    if (multiSaleTicketSuccessDialog.getWindow() != null) {
                        multiSaleTicketSuccessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        multiSaleTicketSuccessDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        multiSaleTicketSuccessDialog.setCancelable(false);
                    }
                }
               /* String successMsg = getString(R.string.ticket_is_marked_as_sold);
                SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), 1, "", successMsg, responseCodeMessageBean.getSaleTicketDetails().get(0).getGameName(), responseCodeMessageBean.getSaleTicketDetails().get(0).getTicketPrice().toString(), responseCodeMessageBean.getSaleTicketDetails().get(0).getTicketNumbers().toString());
                dialog.show();*/
                //String successMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.successfully_ticket_sold) : responseCodeMessageBean.getResponseMessage();
                //Utils.showSuccessDialog(getContext(), getString(R.string.app_name), successMsg, getFragmentManager());
                /*SuccessDialog dialog = new SuccessDialog(master, getFragmentManager(), "", successMsg, 1);
                dialog.show();*/
               /* if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }*/
            } else {

                if (Utils.checkForSessionExpiry(master, responseCodeMessageBean.getResponseCode()))
                    return;
                //String errorMsg = TextUtils.isEmpty(responseCodeMessageBean.getResponseMessage()) ? getString(R.string.some_internal_error) : responseCodeMessageBean.getResponseMessage();
                String errorMsg = Utils.getResponseMessage(master, SCRATCH, responseCodeMessageBean.getResponseCode());
                Log.d("TAg", "responseCodeMessageBean: " + responseCodeMessageBean);
                ErrorDialogListener errorDialogListener = this::callBack;
                if (responseCodeMessageBean.getSoldTickets() == null && responseCodeMessageBean.getInvalidTickets() == null) {
                    Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                } else if (responseCodeMessageBean.getSoldTickets() != null) {
                    Log.d("TAg", "responseCodeMessageBean.getSoldTickets(): " + responseCodeMessageBean.getSoldTickets());
                    if (responseCodeMessageBean.getSoldTickets().get(0) == null) {
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                    } else {
                        Log.d("TAg", "responseCodeMessageBean.getSoldTickets(): " + responseCodeMessageBean.getSoldTickets());
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, responseCodeMessageBean.getSoldTickets().get(0) + " " + getString(R.string.is_already_sold), errorDialogListener);
                    }
                } else if (responseCodeMessageBean.getInvalidTickets() != null) {
                    if (responseCodeMessageBean.getInvalidTickets().get(0) == null) {
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
                    } else
                        Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, responseCodeMessageBean.getInvalidTickets().get(0) + " " + getString(R.string.is_already_sold), errorDialogListener);
                } else
                    Utils.showCustomErrorDialog(getContext(), BuildConfig.app_name, errorMsg, errorDialogListener);
            }
        });
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

        multiSaleAdapter = new MultiSaleTicketAdapter(getContext(), Collections.emptyList(), mTicketNo -> {
            Log.i("TaG","cross btn click 3333333======" + mTicketNo);

            try {
                deletedTicketList.add(mTicketNo);
                for (GetTicketStatusResponse.Game gameData : ticketsData.getData()) {
                    List<GetTicketStatusResponse.Ticket> ticketList = gameData.getTicketAndStatusList();
                    if (ticketList != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            ticketList.removeIf(ticket -> ticket.getTicketNumber().equals(mTicketNo));
                        }
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ticketsData.getData().removeIf(a-> a.getTicketAndStatusList().isEmpty());
                }
                if(ticketsData.getData().isEmpty()) {
                    master.getSupportFragmentManager().popBackStack();
                    master.getSupportFragmentManager().popBackStack();
                }

                noOfTickets = 0;
                totalAmount = 0;
           /* for (GetTicketStatusResponse.Game ticketData : ticketsData.getData()) {
                noOfTickets += ticketData.getTicketAndStatusList().size();
                totalAmount += (float) (ticketData.getTicketPrice() * ticketData.getTicketAndStatusList().size());
            }
            totalNoOFTickets.setText(getString(R.string.total_no_of_tickets) + " " + noOfTickets);
            amount.setText(totalAmount + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));
*/
                setViewData();
                multiSaleAdapter.setTicketsData(ticketsData.getData());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TaG","get error on delete cart ticket ---->" + e.getMessage());
            }



        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(multiSaleAdapter);



        Bundle bundle = getArguments();
        Log.i("TaG","bundle value -------------->"+ bundle.getSerializable("ticketInfo"));
        if (bundle != null) {
            menuBean = bundle.getParcelable("MenuBean");
            tvTitle.setText(StringMapOla.getCaption(menuBean.getMenuCode(), menuBean.getCaption()));
            ticketsData = (GetTicketStatusResponse.Data) bundle.getSerializable("ticketInfo");

            List<GetTicketStatusResponse.Ticket> filteredInvalidTickets = Collections.emptyList();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                filteredInvalidTickets = Utils.deepCopy(ticketsData).getData().stream()
                        .flatMap(data -> data.getTicketAndStatusList().stream())
                        .filter(ticket -> !ticket.getTicketStatus().equals("ACTIVE") && !ticket.getTicketStatus().equals("RECIEVED_BY_RET"))
                        .collect(Collectors.toList());

                Log.i("TaG","invalid ticket list =====================> " + filteredInvalidTickets);

                filteredData2 = Utils.deepCopy(ticketsData).getData().stream()
                        .map(data -> {
                            List<GetTicketStatusResponse.Ticket> filteredTickets = data.getTicketAndStatusList().stream()
                                    .filter(ticket -> !ticket.getTicketStatus().equals("ACTIVE") && !ticket.getTicketStatus().equals("RECIEVED_BY_RET"))
                                    .collect(Collectors.toList());

                            // Sort the filtered tickets by ticketNumber
                            List<GetTicketStatusResponse.Ticket> sortedFilteredTickets = filteredTickets.stream()
                                    .sorted(Comparator.comparing(GetTicketStatusResponse.Ticket::getTicketNumber))
                                    .collect(Collectors.toList());

                            data.setTicketAndStatusList(sortedFilteredTickets);
                            return data;
                        })
                        .collect(Collectors.toList());
                invalidTicketsData = Utils.deepCopy(ticketsData);
                invalidTicketsData.setData(filteredData2);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    invalidTicketsData.getData().removeIf(a-> a.getTicketAndStatusList().isEmpty());
                }

                Log.i("TaG","filter data2-------------------------->" + invalidTicketsData);
                Log.i("TaG","invalid ticket size-------------------------->" + invalidTicketsData.getData().size());
                // show invalid tickets dialog for info


                List<GetTicketStatusResponse.Game> validTicketData = ticketsData.getData().stream()
                        .map(data -> {
                            List<GetTicketStatusResponse.Ticket> filteredTickets = data.getTicketAndStatusList().stream()
                                    .filter(ticket -> ticket.getTicketStatus().equals("ACTIVE") || ticket.getTicketStatus().equals("RECIEVED_BY_RET"))
                                    .collect(Collectors.toList());

                            // Sort the filtered tickets by ticketNumber
                            List<GetTicketStatusResponse.Ticket> sortedFilteredTickets = filteredTickets.stream()
                                    .sorted(Comparator.comparing(GetTicketStatusResponse.Ticket::getTicketNumber))
                                    .collect(Collectors.toList());

                            data.setTicketAndStatusList(sortedFilteredTickets);
                            return data;
                        })
                        .filter(data -> !data.getTicketAndStatusList().isEmpty())
                        .collect(Collectors.toList());
                ticketsData.setData(validTicketData);

                if(!filteredData2.isEmpty() && !filteredInvalidTickets.isEmpty()) {

                    Log.i("TaG","Method...-----------have some invalid tickets -------------->");
                    if (multiSaleTicketInvalidDialog == null || !multiSaleTicketInvalidDialog.isShowing()) {
                        multiSaleTicketInvalidDialog = new MultiSaleTicketInvalidDialog(master,
                                invalidTicketsData.getData(),
                                filteredInvalidTickets.size(),
                                () -> {
                                        if (ticketsData.getData().isEmpty()) {
                                            master.getSupportFragmentManager().popBackStack();
                                            master.getSupportFragmentManager().popBackStack();
                                        }
                                    filteredData2.clear();
                                }

                                );
                        multiSaleTicketInvalidDialog.show();

                        if (multiSaleTicketInvalidDialog.getWindow() != null) {
                            multiSaleTicketInvalidDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            multiSaleTicketInvalidDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        }
                    }

                } else {


                    // show nothing

                }

            }

            setViewData();
            multiSaleAdapter.setTicketsData(ticketsData.getData());

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
        for (GetTicketStatusResponse.Game ticketData : ticketsData.getData()) {
            noOfTickets += ticketData.getTicketAndStatusList().size();
            totalAmount += (float) (ticketData.getTicketPrice() * ticketData.getTicketAndStatusList().size());
        }
        totalNoOFTickets.setText(getString(R.string.total_no_of_tickets) + " " + noOfTickets);
        amount.setText(Utils.getUkFormat(totalAmount) + " " + Utils.getDefaultCurrency(SharedPrefUtil.getLanguage(getContext())));

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

                ProgressBarDialog.getProgressDialog().showProgress(getContext());
                UrlBean urlSale = Utils.getUrlDetails(menuBean, getContext(), "sale");

                List<String> ticketArray = new ArrayList<String>();
                for(GetTicketStatusResponse.Game ticketData : ticketsData.getData()) {
                    for (GetTicketStatusResponse.Ticket ticket : ticketData.getTicketAndStatusList()) {
                        ticketArray.add(ticket.getTicketNumber());
                    }
                }

                saleTicketViewModel.callMultiSaleTicket(urlSale, ticketArray);

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