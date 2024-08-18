package com.skilrock.retailapp.viewmodels.scratch;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.models.rms.LoginBean;
import com.skilrock.retailapp.models.scratch.ClaimTicketRequestBean;
import com.skilrock.retailapp.models.scratch.ClaimTicketResponseNewBean;
import com.skilrock.retailapp.models.scratch.MultiClaimTicketRequest;
import com.skilrock.retailapp.models.scratch.MultiClaimTicketResponseBean;
import com.skilrock.retailapp.models.scratch.VerifyTicketRequestNewBean;
import com.skilrock.retailapp.models.scratch.VerifyTicketResponseNewBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WinningClaimViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<VerifyTicketResponseNewBean> verifyTicketResponseData = new MutableLiveData<>();

    private MutableLiveData<GameListBean> gameListData = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataBalance = new MutableLiveData<>();
    private MutableLiveData<LoginBean> liveDataBalanceTerminal = new MutableLiveData<>();

    private MutableLiveData<ClaimTicketResponseNewBean> claimTicketResponseData = new MutableLiveData<>();
    private MutableLiveData<MultiClaimTicketResponseBean> multiClaimTicketResponseData = new MutableLiveData<>();

    public MutableLiveData<LoginBean> getLiveDataBalance() { return liveDataBalance; }

    public MutableLiveData<LoginBean> getLiveDataBalanceTerminal() { return liveDataBalanceTerminal; }

    public void callVerifyWinningTicket(UrlBean urlBean, String ticketNumber) {
        VerifyTicketRequestNewBean model = new VerifyTicketRequestNewBean();
        model.setUserName(PlayerData.getInstance().getUsername());
        model.setBarcodeNumber(ticketNumber);
        model.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
        model.setModelCode(Utils.getDeviceModelCode());
        model.setTerminalId(Utils.getDeviceSerialNumber());

        Call<VerifyTicketResponseNewBean> callSaleTicketApi = client.verifyTicket(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), model);

        Log.d("log", "Verify Ticket Request: " + callSaleTicketApi.request().toString());

        /*String dummyResponse = "{\n" +
                "  \"responseCode\": 1000,\n" +
                "  \"responseMessage\": \"Success\",\n" +
                "  \"winningAmount\": 99.38,\n" +
                "  \"taxAmount\": 19.38,\n" +
                "  \"netWinningAmount\": 80,\n" +
                "  \"ticketNumber\": \"163-050108-061\",\n" +
                "  \"virnNumber\": \"16322823774\",\n" +
                "  \"gameName\": \"FZZY ВІП Мільйонер\",\n" +
                "  \"soldByOrg\": \"0000001_shaileshret\"\n" +
                "}";*/

        /*String dummyResponse = "{" +
                "\"responseCode\": 1401,\n"+
                "\"responseMessage\": \"The ticket has no winning\"\n"+
                "}";*/





        /*Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> map = gson.fromJson(dummyResponse, type);
        VerifyTicketResponseNewBean callTicketStatusApi2 = gson.fromJson(dummyResponse,VerifyTicketResponseNewBean.class);
        verifyTicketResponseData.postValue(callTicketStatusApi2);// user for dummy data only*/

        callSaleTicketApi.enqueue(new Callback<VerifyTicketResponseNewBean>() {
            @Override
            public void onResponse(@NonNull Call<VerifyTicketResponseNewBean> call, @NonNull Response<VerifyTicketResponseNewBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Verify Ticket API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            VerifyTicketResponseNewBean model;
                            model= gson.fromJson(errorResponse, VerifyTicketResponseNewBean.class);
                            verifyTicketResponseData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            verifyTicketResponseData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    verifyTicketResponseData.postValue(null);
                    return;
                }

                Log.i("log", "Verify Ticket API Response: " + new Gson().toJson(response.body()));
                verifyTicketResponseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<VerifyTicketResponseNewBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Verify Ticket API failed: " + throwable.toString());
                verifyTicketResponseData.postValue(null);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void callClaimTicket(UrlBean urlBean, String ticketNumber) {
        Random random = new Random();
        ClaimTicketRequestBean model = new ClaimTicketRequestBean();
        model.setUserName(PlayerData.getInstance().getUsername());
        model.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
        model.setRequestId(String.format("%04d", random.nextInt(10000)));
        model.setBarcodeNumber(ticketNumber);
        model.setModelCode(Utils.getDeviceModelCode());
        model.setTerminalId(Utils.getDeviceSerialNumber());

        Call<ClaimTicketResponseNewBean> callSaleTicketApi = client.claimTicket(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), model);

        Log.d("log", "Claim Ticket Request: " + callSaleTicketApi.request().toString());

        callSaleTicketApi.enqueue(new Callback<ClaimTicketResponseNewBean>() {
            @Override
            public void onResponse(@NonNull Call<ClaimTicketResponseNewBean> call, @NonNull Response<ClaimTicketResponseNewBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Claim Ticket API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ClaimTicketResponseNewBean model;
                            model= gson.fromJson(errorResponse, ClaimTicketResponseNewBean.class);
                            claimTicketResponseData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            claimTicketResponseData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    claimTicketResponseData.postValue(null);
                    return;
                }

                Log.i("log", "Claim Ticket API Response: " + new Gson().toJson(response.body()));
                claimTicketResponseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ClaimTicketResponseNewBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Claim Ticket API failed: " + throwable.toString());
                claimTicketResponseData.postValue(null);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public void callMultiClaimTicket(UrlBean urlBean, List<String> ticketNumber) {
        Random random = new Random();
        MultiClaimTicketRequest model = new MultiClaimTicketRequest();
        model.setUserName(PlayerData.getInstance().getUsername());
        model.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
        model.setBarcodeNumberList(ticketNumber);
        // later set url for api
        Log.i("TaG","clientID==========================>" + urlBean.getClientId());
        Log.i("TaG","getClientSecret==========================>" + urlBean.getClientSecret());
        Call<MultiClaimTicketResponseBean> callSaleTicketApi = client.multiClaimTicket("https://uat-rms.unl.ua/PPL/Winning/claimWinning", urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), model);

       /* String responseString = "{\n" +
                "  \"responseCode\": 1000,\n" +
                "  \"responseMessage\": \"Success\",\n" +
                "  \"response\": {\n" +
                "    \"data\": [\n" +
                "      {\n" +
                "        \"barCode\": \"502912549001234\",\n" +
                "        \"message\": \"The ticket has already been claimed\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"barCode\": \"502413600981234\",\n" +
                "        \"message\": \"Invalid ticket\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"barCode\": \"502357765321234\",\n" +
                "        \"message\": \"The ticket has already been claimed\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"barCode\": \"502418500981234\",\n" +
                "        \"message\": \"The ticket has already been claimed\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"barCode\": \"502395415851234\",\n" +
                "        \"response\": {\n" +
                "          \"transactionId\": \"1383\",\n" +
                "          \"transactionNumber\": \"WCRR24070001383\",\n" +
                "          \"transactionDate\": \"2024-07-30 14:15:13\",\n" +
                "          \"ticketNumber\": \"502-002022-029\",\n" +
                "          \"virnNumber\": \"50239541585\",\n" +
                "          \"winningAmount\": 2500,\n" +
                "          \"taxAmount\": 487.5,\n" +
                "          \"commissionAmount\": 0,\n" +
                "          \"tdsAmount\": 0,\n" +
                "          \"netWinningAmount\": 2012.5,\n" +
                "          \"soldByOrg\": \"ORG21121709AAAC_mridulret\",\n" +
                "          \"claimedLocation\": \"Skilrock, Cybercity, Gurugram, Hary\",\n" +
                "          \"claimedByOrg\": \"ORG21121709AAAC_mridulret\",\n" +
                "          \"txnStatus\": \"DONE\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        Log.d("log", "Claim Ticket Request: " + callSaleTicketApi.request().toString());

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> map = gson.fromJson(responseString, type);
        MultiClaimTicketResponseBean callTicketStatusApi2 = gson.fromJson(responseString,MultiClaimTicketResponseBean.class);
        multiClaimTicketResponseData.postValue(callTicketStatusApi2);// user for dummy data only
*/
        callSaleTicketApi.enqueue(new Callback<MultiClaimTicketResponseBean>() {
            @Override
            public void onResponse(@NonNull Call<MultiClaimTicketResponseBean> call, @NonNull Response<MultiClaimTicketResponseBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Claim Ticket API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            MultiClaimTicketResponseBean model;
                            model= gson.fromJson(errorResponse, MultiClaimTicketResponseBean.class);
                            multiClaimTicketResponseData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            multiClaimTicketResponseData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    multiClaimTicketResponseData.postValue(null);
                    return;
                }

                Log.i("log", "multi Claim Ticket API Response: " + new Gson().toJson(response.body()));
                multiClaimTicketResponseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MultiClaimTicketResponseBean> call, @NonNull Throwable throwable) {
                Log.e("log", "multi Claim Ticket API failed: " + throwable.toString());
//                setDataEvent(null);
                multiClaimTicketResponseData.postValue(null);;
            }
        });
    }

    public MutableLiveData<MultiClaimTicketResponseBean> getMultiClaimTicketResponseData() {
        return multiClaimTicketResponseData;
    }


    public MutableLiveData<VerifyTicketResponseNewBean> getVerifyTicketResponseData() {
        return verifyTicketResponseData;
    }

    public MutableLiveData<GameListBean> getGameListData() {
        return gameListData;
    }

    public MutableLiveData<ClaimTicketResponseNewBean> getClaimTicketResponseData() {
        return claimTicketResponseData;
    }

    public void getUpdatedBalance(final String authToken) {
        Call<LoginBean> loginCall = client.getLogin(authToken);

        Log.d("log", "Login Request: " + loginCall.request().toString());

        loginCall.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(@NonNull Call<LoginBean> call, @NonNull Response<LoginBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Login API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            LoginBean model;
                            model= gson.fromJson(errorResponse, LoginBean.class);
                            liveDataBalance.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataBalance.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataBalance.postValue(null);
                    return;
                }

                Log.i("log", "Login API Response: " + new Gson().toJson(response.body()));
                if (response.body().getResponseCode() == 0 && response.body().getResponseData().getStatusCode() == 0)
                    response.body().setToken(authToken);
                liveDataBalance.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataBalance.postValue(null);
            }
        });
    }

    public void getUpdatedBalanceTerminal(final String authToken) {
        Call<LoginBean> loginCall = client.getLogin(authToken);

        Log.d("log", "Login Request: " + loginCall.request().toString());

        loginCall.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(@NonNull Call<LoginBean> call, @NonNull Response<LoginBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Login API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            LoginBean model;
                            model= gson.fromJson(errorResponse, LoginBean.class);
                            liveDataBalanceTerminal.postValue(model);
                        } catch (JsonSyntaxException e) {
                            liveDataBalanceTerminal.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    liveDataBalanceTerminal.postValue(null);
                    return;
                }

                Log.i("log", "Login API Response: " + new Gson().toJson(response.body()));
                if (response.body().getResponseCode() == 0 && response.body().getResponseData().getStatusCode() == 0)
                    response.body().setToken(authToken);
                liveDataBalanceTerminal.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<LoginBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Login API failed: " + throwable.toString());
                liveDataBalanceTerminal.postValue(null);
            }
        });
    }

}
