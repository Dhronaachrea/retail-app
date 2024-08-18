package com.skilrock.retailapp.viewmodels.scratch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.skilrock.retailapp.models.scratch.ActivateBookRequestBean;
import com.skilrock.retailapp.models.rms.GameListBean;
import com.skilrock.retailapp.models.ResponseCodeMessageBean;
import com.skilrock.retailapp.models.UrlBean;
import com.skilrock.retailapp.network.APIClient;
import com.skilrock.retailapp.network.APIConfig;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.PlayerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveBookViewModel extends ViewModel {

    private APIClient client = APIConfig.getInstance().create(APIClient.class);

    private MutableLiveData<ResponseCodeMessageBean> responseData = new MutableLiveData<>();

    private MutableLiveData<GameListBean> gameListData = new MutableLiveData<>();

    public void callGameListApi(UrlBean gameListUrl, final UrlBean activateBookUrl, final String ticketNumber) {
        //String queryString = "{\"gameStatus\":\"OPEN\",\"gameType\":\"SCRATCH\"}";
        String queryString = "{\"gameType\":\"SCRATCH\"}";

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
                    ticketFormattingOperation(gameListBean, activateBookUrl, ticketNumber);
            }

            @Override
            public void onFailure(@NonNull Call<GameListBean> call, @NonNull Throwable t) {
                Log.e("log", "GameList API Response: FAILED");
                gameListData.postValue(null);
            }
        });

    }

    public void callBookActivation(UrlBean url, String bookNumber) {
        Log.i("log", "Ticket Number: " + bookNumber);
        Log.d("log", "First Index: " + bookNumber.indexOf("-"));
        Log.d("log", "Last Index: " + bookNumber.lastIndexOf("-"));

        if (bookNumber.indexOf("-") != bookNumber.lastIndexOf("-")) {
            StringBuilder sb = new StringBuilder(bookNumber);
            sb.deleteCharAt(bookNumber.lastIndexOf("-"));
            bookNumber = sb.toString();
        }

        Log.i("log", "Ticket Number: " + bookNumber);

        ActivateBookRequestBean activateBookRequestBean = new ActivateBookRequestBean();
        activateBookRequestBean.setUserSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
        activateBookRequestBean.setUserName(PlayerData.getInstance().getUsername());
        activateBookRequestBean.setGameType(AppConstants.SCRATCH);
        activateBookRequestBean.setBookNumbers(new String[]{bookNumber});
        activateBookRequestBean.setPackNumbers(new String[]{});

        Call<ResponseCodeMessageBean> callBookActivationApi = client.bookActivation(url.getUrl(), url.getClientId(), url.getClientSecret(), url.getContentType(), activateBookRequestBean);

        Log.d("log", "Active Book Request: " + callBookActivationApi.request().toString());

        callBookActivationApi.enqueue(new Callback<ResponseCodeMessageBean>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Response<ResponseCodeMessageBean> response) {
                if (response.body() == null || !response.isSuccessful()) {
                    if (response.errorBody() != null) {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("log", "Active Book API Failed: " + errorResponse);
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

                Log.i("log", "Active Book API Response: " + new Gson().toJson(response.body()));
                responseData.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCodeMessageBean> call, @NonNull Throwable throwable) {
                Log.e("log", "Active Book API failed: " + throwable.toString());
                responseData.postValue(null);
            }
        });
    }

    public MutableLiveData<ResponseCodeMessageBean> getResponseData() {
        return responseData;
    }

    public MutableLiveData<GameListBean> getGameListData() {
        return gameListData;
    }

    private void ticketFormattingOperation(GameListBean gameListBean, UrlBean activateBookurl, final String ticketNumber) {
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
            //int packAndBookNumberDigit = game.getPackNumberDigits() + game.getBookNumberDigits();
            int packAndBookNumberDigit = game.getBookNumberDigits();

            String formattedTicketNum = ticketNumber.substring(0, gameNumberDigits) + "-" + ticketNumber.substring(gameNumberDigits, (gameNumberDigits + packAndBookNumberDigit)) + "-" + ticketNumber.substring((gameNumberDigits + packAndBookNumberDigit), ticketNumber.length());
            if (formattedTicketNum.substring(formattedTicketNum.length() - 1).equalsIgnoreCase("-"))
                formattedTicketNum = formattedTicketNum.substring(0, formattedTicketNum.length() - 1);
            callBookActivation(activateBookurl, formattedTicketNum);
        } catch (Exception e) {
            gameListBean.setResponseCode(74747);
            gameListData.postValue(gameListBean);
            e.printStackTrace();
        }
    }

}
