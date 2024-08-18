package com.skilrock.retailapp.viewmodels.scratch;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.BuildConfig;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.models.scratch.GetTicketStatusRequest;
import com.skilrock.retailapp.models.scratch.GetTicketStatusResponse;
import com.skilrock.retailapp.models.scratch.MultiSaleTicketRequestBean;
import com.skilrock.retailapp.models.scratch.SaleTicketRequestBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleTicketViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);
    private MutableLiveData<ResponseCodeMessageBean> responseData = new MutableLiveData<>();
    private MutableLiveData<GetTicketStatusResponse> ticketStatusData = new MutableLiveData<>();

    private final MutableLiveData<GameListBean> gameListData = new MutableLiveData<>();

    public void callGameListApi(UrlBean gameListUrl, final UrlBean saleUrl, final String ticketNumber) {
        String queryString = "{\"gameStatus\":\"OPEN\",\"gameType\":\"SCRATCH\"}";

        Call<GameListBean> callGameListApi = client.getGameList(gameListUrl.getUrl(), gameListUrl.getClientId(), gameListUrl.getClientSecret(), gameListUrl.getContentType(), queryString);

        Log.d("log", "GameList Request: " + callGameListApi.request().toString());

        callGameListApi.enqueue(new Callback<GameListBean>() {
            @Override
            public void onResponse(@NonNull Call<GameListBean> call, @NonNull Response<GameListBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "GameList API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            GameListBean model;
                            model= gson.fromJson(errorResponse, GameListBean.class);
                            gameListData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            gameListData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    gameListData.postValue(null);
                    return;
                }

                Log.i("log", "GameList API Response: " + new Gson().toJson(response.body()));
                GameListBean gameListBean = response.body();
                if (gameListBean == null)
                    gameListData.postValue(null);
                else if (gameListBean.getResponseCode() != 1000)
                    gameListData.postValue(gameListBean);
                else
                    ticketFormattingOperation(gameListBean, saleUrl, ticketNumber);
            }

            @Override
            public void onFailure(@NonNull Call<GameListBean> call, @NonNull Throwable t) {
                Log.e("log", "GameList API Response: FAILED");
                gameListData.postValue(null);
            }
        });
    }

    public void callSaleTicket(UrlBean urlBean, String ticketNumber) {
        Log.i("log", "Ticket Number: " + ticketNumber);
        Log.i("TaG", "Ticket-------------------------------------");

        SaleTicketRequestBean saleTicketRequestBean = new SaleTicketRequestBean();
        saleTicketRequestBean.setUserName(PlayerData.getInstance().getUsername());
        saleTicketRequestBean.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
        saleTicketRequestBean.setGameType(AppConstants.SCRATCH);
        saleTicketRequestBean.setModelCode(Utils.getDeviceModelCode());
        saleTicketRequestBean.setTerminalId(Utils.getDeviceSerialNumber());

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.GELSA_RETAIL)) {
            saleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM_WEB);
        } else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
            saleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM_WEB);
        } else {
            saleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM);
        }
        saleTicketRequestBean.setTicketNumberList(new String[]{ticketNumber});

        Call<ResponseCodeMessageBean> callSaleTicketApi = client.getTicketSold(urlBean.getUrl(), urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), saleTicketRequestBean);

        Log.d("log", "SaleTicket Request: " + callSaleTicketApi.request().toString());

        callSaleTicketApi.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "SaleTicket API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCodeMessageBean model;
                            model= gson.fromJson(errorResponse, ResponseCodeMessageBean.class);
                            responseData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            responseData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    responseData.postValue(null);
                    return;
                }

                Log.i("log", "SaleTicket API Response: " + new Gson().toJson(response.body()));
                responseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SaleTicket API failed: " + throwable.toString());
                responseData.postValue(null);
            }
        });
    }

    public void callMultiSaleTicket(UrlBean urlBean, List<String> ticketNumber) {
        Log.i("log", "Ticket Number: " + ticketNumber);
        Log.i("TaG", "Ticket-------------------------------------");

        MultiSaleTicketRequestBean multiSaleTicketRequestBean = new MultiSaleTicketRequestBean();
        multiSaleTicketRequestBean.setUserName(PlayerData.getInstance().getUsername());
        multiSaleTicketRequestBean.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
        multiSaleTicketRequestBean.setGameType(AppConstants.SCRATCH);
        multiSaleTicketRequestBean.setModelCode(Utils.getDeviceModelCode());
        multiSaleTicketRequestBean.setTerminalId(Utils.getDeviceSerialNumber());

        if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.GELSA_RETAIL)) {
            multiSaleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM_WEB);
        } else if (BuildConfig.app_name.equalsIgnoreCase(AppConstants.UNL_RETAIL)) {
            multiSaleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM_WEB);
        } else {
            multiSaleTicketRequestBean.setSoldChannel(AppConstants.PLATFORM);
        }
        multiSaleTicketRequestBean.setTicketNumberList(ticketNumber);

        Call<ResponseCodeMessageBean> callMultiSaleTicketApi = client.getMultiTicketSold("https://uat-rms.unl.ua/PPL/sale/soldTickets", urlBean.getClientId(), urlBean.getClientSecret(), urlBean.getContentType(), multiSaleTicketRequestBean);

        Log.d("log", "MultiSaleTicket Request: " + callMultiSaleTicketApi.request().toString());

        callMultiSaleTicketApi.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "SaleTicket API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            ResponseCodeMessageBean model;
                            model= gson.fromJson(errorResponse, ResponseCodeMessageBean.class);
                            responseData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            Log.i("TaG","error showing : " + e.getMessage());
                            responseData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            Log.i("TaG","error showing 2: " + e.getMessage());
                            e.printStackTrace();
                        }
                        return;
                    }
                    responseData.postValue(null);
                    return;
                }

                Log.i("log", "SaleTicket API Response: " + new Gson().toJson(response.body()));
                responseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "SaleTicket API failed: " + throwable.toString());
                responseData.postValue(null);
            }
        });
    }

    public void callTicketStatus(UrlBean urlBean, List<String> ticketNumbers) {
        Log.i("TaG", "Ticket Number: " + ticketNumbers);
        Log.i("TaG", "Ticket-------------------------------------");

        GetTicketStatusRequest TicketStatusRequestBean = new GetTicketStatusRequest();
        TicketStatusRequestBean.setUserName(PlayerData.getInstance().getUsername());
        TicketStatusRequestBean.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);


        TicketStatusRequestBean.setTicketNumberList(ticketNumbers);

        Call<GetTicketStatusResponse> callTicketStatusApi = client.getTicketStatus( "https://uat-rms.unl.ua/PPL/sale/getTicketStatusForSold",urlBean.getClientId(), urlBean.getClientSecret(), TicketStatusRequestBean);

        Log.d("log", "Ticket Status Request: " + callTicketStatusApi.request().toString());


      /*  String dummyResponse = "{\n" +
                "  \"responseCode\": 1000,\n" +
                "  \"responseMessage\": \"Success\",\n" +
                "  \"response\": {\n" +
                "    \"data\": [\n" +
                "      {\n" +
                "        \"ticketAndStatusList\": [\n" +
                "          {\n" +
                "            \"ticketNumber\": \"103-001002-001\",\n" +
                "            \"ticketStatus\": \"INVALID\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"ticketNumber\": \"103-001001-001\",\n" +
                "            \"ticketStatus\": \"CLAIMED\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"ticketNumber\": \"103-002034-001\",\n" +
                "            \"ticketStatus\": \"RECIEVED_BY_RET\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"ticketPrice\": 2500.0,\n" +
                "        \"gameName\": \"Scratch N Win\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ticketAndStatusList\": [\n" +
                "          {\n" +
                "            \"ticketNumber\": \"401-002002-003\",\n" +
                "            \"ticketStatus\": \"SOLD\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"ticketPrice\": 5.0,\n" +
                "        \"gameName\": \"Demo_Scratch\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ticketAndStatusList\": [\n" +
                "          {\n" +
                "            \"ticketNumber\": \"173-000101-002\",\n" +
                "            \"ticketStatus\": \"INVALID\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"ticketPrice\": 30.0,\n" +
                "        \"gameName\": \"FZZY Твоя Гра\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> map = gson.fromJson(dummyResponse, type);
        GetTicketStatusResponse callTicketStatusApi2 = gson.fromJson(dummyResponse,GetTicketStatusResponse.class);*/
//        ticketStatusData.postValue(callTicketStatusApi2);// user for dummy data only
        callTicketStatusApi.enqueue(new Callback<GetTicketStatusResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetTicketStatusResponse> call, @NonNull Response<GetTicketStatusResponse> response) {

                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("TaG", "ticketStatus API Failed: " + errorResponse);
                            Gson gson = new GsonBuilder().create();
                            GetTicketStatusResponse model;
                            model= gson.fromJson(errorResponse, GetTicketStatusResponse.class);
                            ticketStatusData.postValue(model);
                        } catch (JsonSyntaxException e) {
                            ticketStatusData.postValue(null);
                            e.printStackTrace();
                        } catch (Exception e) {
                            ticketStatusData.postValue(null);
                            e.printStackTrace();
                        }
                        return;
                    }
                    ticketStatusData.postValue(null);
                    return;
                }

                Log.i("log", "ticketStatus API Response: " + new Gson().toJson(response.body()));
                ticketStatusData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<GetTicketStatusResponse> call, @NonNull Throwable throwable) {
                Log.e("log", "ticketStatus API failed: " + throwable.toString());
                ticketStatusData.postValue(null);
            }
        });
    }

    public MutableLiveData<ResponseCodeMessageBean> getResponseData() {
        return responseData;
    }
    public MutableLiveData<GetTicketStatusResponse> getTicketStatusData() {return ticketStatusData;}
    public void setTicketStatusData(GetTicketStatusResponse data) {
        ticketStatusData.postValue(data);
    }

    public MutableLiveData<GameListBean> getGameListData() {
        return gameListData;
    }

    private void ticketFormattingOperation(GameListBean gameListBean, final UrlBean urlBean, final String ticketNumber) {
        int gameListSize = gameListBean.getGames().size();
        if (gameListSize < 1) {
            gameListBean.setResponseCode(75757);
            gameListData.postValue(gameListBean);
            return;
        }
        int index = 0;
        for (; index<gameListSize; index++) {
            String gameNumber = String.valueOf(gameListBean.getGames().get(index).getGameNumber());
            if (ticketNumber.indexOf(gameNumber) == 0)
                break;
        }
        if (index == gameListSize) {
            gameListBean.setResponseCode(57575);
            gameListData.postValue(gameListBean);
            return;
        }
        try {
            GameListBean.Game game = gameListBean.getGames().get(index);
            int gameNumberDigits = 0;
            int temp = game.getGameNumber();
            while (temp != 0) {
                temp /= 10;
                ++gameNumberDigits;
            }
            int packAndBookNumberDigit = game.getPackNumberDigits() + game.getBookNumberDigits();

            String formattedTicketNum = ticketNumber.substring(0, gameNumberDigits) + "-" + ticketNumber.substring(gameNumberDigits, (gameNumberDigits + packAndBookNumberDigit)) + "-" + ticketNumber.substring((gameNumberDigits + packAndBookNumberDigit), ticketNumber.length());
            callSaleTicket(urlBean, formattedTicketNum);
        } catch (Exception e) {
            gameListBean.setResponseCode(74747);
            gameListData.postValue(gameListBean);
            e.printStackTrace();
        }
    }


}
