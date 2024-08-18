package com.skilrock.retailapp.portrait_draw_games.ui;


import android.Manifest;
import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.Result;
import com.skilrock.retailapp.R;
import com.skilrock.retailapp.interfaces.ErrorDialogListener;
import com.skilrock.retailapp.interfaces.WinningClaimListener;
import com.skilrock.retailapp.models.UrlDrawGameBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayRequestBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimPayResponseBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyRequestBean;
import com.skilrock.retailapp.models.drawgames.WinningClaimVerifyResponseBean;
import com.skilrock.retailapp.models.rms.HomeDataBean;
import com.skilrock.retailapp.permissions.AppPermissions;
import com.skilrock.retailapp.portrait_draw_games.viewmodels.WinningClaimViewModel;
import com.skilrock.retailapp.sle_game_portrait.BaseClassSle;
import com.skilrock.retailapp.sle_game_portrait.HttpRequest;
import com.skilrock.retailapp.sle_game_portrait.ResponseLis;
import com.skilrock.retailapp.sle_game_portrait.VerifyPayTicket;
import com.skilrock.retailapp.utils.AppConstants;
import com.skilrock.retailapp.utils.CustomSuccessDialog;
import com.skilrock.retailapp.utils.PlayerData;
import com.skilrock.retailapp.utils.PrintUtil;
import com.skilrock.retailapp.utils.ProgressBarDialog;
import com.skilrock.retailapp.utils.SharedPrefUtil;
import com.skilrock.retailapp.utils.Utils;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Objects;

import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2_s;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_SUNMI_V2s;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_T2MINI;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_TPS570;
import static com.skilrock.retailapp.utils.AppConstants.DEVICE_V2PRO;


public class WinningClaimActivity extends BaseActivity implements View.OnClickListener, WinningClaimListener, ResponseLis {

    private static final int REQUEST_CODE_PRINT = 1111;
    private EditText etTicketNumber;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private int api_call = 0;
    private Button buttonProceed;
    private HomeDataBean.ResponseData.ModuleBeanList.MenuBeanList MENU_BEAN;
    private TextInputLayout tilTicketNumber;
    private WinningClaimViewModel viewModel;
    private UsbThermalPrinter usbThermalPrinter;
    private JsonObject object;
    private String response;
    private boolean fromSports = false;
    EditText ed_first_no, ed_second_no, ed_third_no, ed_fourth_no;
    String headerData1 = "userName,E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6|password,p@55w0rd|Content-Type,application/x-www-form-urlencoded";
    boolean request = true;
    int length = 0;
    //String ticket_number = "";
    String responseTicketNumber = null;
    private CodeScannerView scannerView;
    private CodeScanner mCodeScanner;
    private FrameLayout contentFrame;
    String res = "{\n" +
            "  \"responseCode\": 0,\n" +
            "  \"responseMessage\": \"Success\",\n" +
            "  \"responseData\": {\n" +
            "    \"ticketNumber\": \"3003303080768452\",\n" +
            "    \"gameName\": \"Lucky 6\",\n" +
            "    \"gameCode\": \"LuckySix\",\n" +
            "    \"gameId\": 3,\n" +
            "    \"drawData\": [\n" +
            "      {\n" +
            "        \"drawId\": 5648,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-03-16\",\n" +
            "        \"drawTime\": \"13:05:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"RESULT AWAITED\",\n" +
            "        \"winningAmount\": \"0.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallColor\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 2,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallSum\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 3,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallEvenOdd\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 4,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 5,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2001\n" +
            "      },\n" +
            "      {\n" +
            "        \"drawId\": 5644,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-03-16\",\n" +
            "        \"drawTime\": \"12:05:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"WIN!!\",\n" +
            "        \"winningAmount\": \"1700.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallColor\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 2,\n" +
            "            \"status\": \"CLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallSum\",\n" +
            "            \"winningAmt\": 300.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 3,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallEvenOdd\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 4,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 5,\n" +
            "            \"status\": \"CLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 1400.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2006\n" +
            "      },\n" +
            "      {\n" +
            "        \"drawId\": 5645,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-03-16\",\n" +
            "        \"drawTime\": \"12:20:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"RESULT AWAITED\",\n" +
            "        \"winningAmount\": \"0.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallColor\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 2,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallSum\",\n" +
            "            \"winningAmt\": 300.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 3,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallEvenOdd\",\n" +
            "            \"winningAmt\": 300.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 4,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 600.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 5,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2001\n" +
            "      },\n" +
            "      {\n" +
            "        \"drawId\": 5646,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-03-16\",\n" +
            "        \"drawTime\": \"12:35:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"WIN!!\",\n" +
            "        \"winningAmount\": \"600.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallColor\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 2,\n" +
            "            \"status\": \"CLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallSum\",\n" +
            "            \"winningAmt\": 300.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 3,\n" +
            "            \"status\": \"CLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallEvenOdd\",\n" +
            "            \"winningAmt\": 300.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 4,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 5,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2006\n" +
            "      },\n" +
            "      {\n" +
            "        \"drawId\": 5647,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-03-16\",\n" +
            "        \"drawTime\": \"12:50:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"RESULT AWAITED\",\n" +
            "        \"winningAmount\": \"0.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallColor\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 2,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallSum\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 3,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"LuckySixFirstBallEvenOdd\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 4,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          },\n" +
            "          {\n" +
            "            \"panelId\": 5,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2001\n" +
            "      }\n" +
            "    ],\n" +
            "    \"prizeAmount\": \"2300.00\",\n" +
            "    \"totalPay\": \"2300.00\",\n" +
            "    \"currencySymbol\": \"CFA\",\n" +
            "    \"merchantCode\": \"LotteryRMS\",\n" +
            "    \"orgName\": \"Cameroon\",\n" +
            "    \"retailerName\": \"shelly\",\n" +
            "    \"reprintCountPwt\": \"2\",\n" +
            "    \"pwtTicketType\": \"DRAW\",\n" +
            "    \"reference\": \"\",\n" +
            "    \"winClaimAmount\": 10000.0\n" +
            "  }\n" +
            "}";

    String res2 = "{\n" +
            "  \"responseCode\": 0,\n" +
            "  \"responseMessage\": \"Success\",\n" +
            "  \"responseData\": {\n" +
            "    \"ticketNumber\": \"3017300030523541\",\n" +
            "    \"doneByUserId\": 3,\n" +
            "    \"status\": \"DONE\",\n" +
            "    \"balance\": 4.9115345581E7,\n" +
            "    \"gameName\": \"Lucky 6\",\n" +
            "    \"gameCode\": \"LuckySix\",\n" +
            "    \"drawData\": [\n" +
            "      {\n" +
            "        \"drawId\": 4328,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-02-21\",\n" +
            "        \"drawTime\": \"13:22:10\",\n" +
            "        \"isPwtCurrent\": true,\n" +
            "        \"winStatus\": \"WIN!!\",\n" +
            "        \"winningAmount\": \"600.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"CLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 600.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2006\n" +
            "      },\n" +
            "      {\n" +
            "        \"drawId\": 4329,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-02-21\",\n" +
            "        \"drawTime\": \"15:20:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"RESULT AWAITED\",\n" +
            "        \"winningAmount\": \"0.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2001\n" +
            "      },\n" +
            "      {\n" +
            "        \"drawId\": 4330,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-02-21\",\n" +
            "        \"drawTime\": \"15:35:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"RESULT AWAITED\",\n" +
            "        \"winningAmount\": \"0.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2001\n" +
            "      },\n" +
            "      {\n" +
            "        \"drawId\": 4331,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-02-21\",\n" +
            "        \"drawTime\": \"15:50:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"RESULT AWAITED\",\n" +
            "        \"winningAmount\": \"0.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2001\n" +
            "      },\n" +
            "      {\n" +
            "        \"drawId\": 4332,\n" +
            "        \"drawName\": \"Lucky Six\",\n" +
            "        \"drawDate\": \"2020-02-21\",\n" +
            "        \"drawTime\": \"16:05:00\",\n" +
            "        \"isPwtCurrent\": false,\n" +
            "        \"winStatus\": \"RESULT AWAITED\",\n" +
            "        \"winningAmount\": \"0.00\",\n" +
            "        \"panelWinList\": [\n" +
            "          {\n" +
            "            \"panelId\": 1,\n" +
            "            \"status\": \"UNCLAIMED\",\n" +
            "            \"playType\": \"Direct6\",\n" +
            "            \"winningAmt\": 0.0,\n" +
            "            \"valid\": true\n" +
            "          }\n" +
            "        ],\n" +
            "        \"winCode\": 2001\n" +
            "      }\n" +
            "    ],\n" +
            "    \"prizeAmount\": \"600.00\",\n" +
            "    \"totalPay\": \"600.00\",\n" +
            "    \"currencySymbol\": \"CFA\",\n" +
            "    \"merchantCode\": \"LotteryRMS\",\n" +
            "    \"orgName\": \"Cameroon\",\n" +
            "    \"retailerName\": \"android\",\n" +
            "    \"reprintCountPwt\": \"1\",\n" +
            "    \"drawTransMap\": {\n" +
            "      \"4328\": \"12095\"\n" +
            "    },\n" +
            "    \"panelData\": [\n" +
            "      {\n" +
            "        \"betType\": \"Direct6\",\n" +
            "        \"pickType\": \"Direct6\",\n" +
            "        \"pickConfig\": \"Number\",\n" +
            "        \"betAmountMultiple\": 1,\n" +
            "        \"quickPick\": true,\n" +
            "        \"pickedValues\": \"10,01,02,03,04,05\",\n" +
            "        \"qpPreGenerated\": true,\n" +
            "        \"numberOfLines\": 1,\n" +
            "        \"unitCost\": 100.0,\n" +
            "        \"panelPrice\": 500.0,\n" +
            "        \"playerPanelPrice\": 500.0,\n" +
            "        \"betDisplayName\": \"Pick 6\",\n" +
            "        \"pickDisplayName\": \"Manual\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"advMessages\": {\n" +
            "      \"top\": [\n" +
            "        {\n" +
            "          \"msgText\": \"Welcome To Cam Win Lotto\",\n" +
            "          \"msgMode\": \"PRINT\",\n" +
            "          \"msgType\": \"TEXT\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"bottom\": [\n" +
            "        {\n" +
            "          \"msgText\": \"Come Again\",\n" +
            "          \"msgMode\": \"PRINT\",\n" +
            "          \"msgType\": \"TEXT\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    \"merchantTxnId\": 12095,\n" +
            "    \"validationCode\": \"--k6s--i-----w------\",\n" +
            "    \"isPwtReprint\": true,\n" +
            "    \"totalPurchaseAmount\": 500.0,\n" +
            "    \"playerPurchaseAmount\": 500.0,\n" +
            "    \"purchaseTime\": \"21-02-2020 13:16:41\",\n" +
            "    \"claimTime\": \"21-02-2020 13:23:04\",\n" +
            "    \"success\": true\n" +
            "  }\n" +
            "}";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.getDeviceName().equalsIgnoreCase(AppConstants.DEVICE_T2MINI))
            setContentView(R.layout.activity_winning_claim_landscape);
        else
            setContentView(R.layout.activity_winning_claim);
        setInitialParameters();
        setToolBar();
        initializeWidgets();
    }

    @Override
    public void onPause() {
        if (mCodeScanner != null) mCodeScanner.releaseResources();
        super.onPause();
    }


    private void initializeWidgets() {
        Intent i = getIntent();
        fromSports = i.getBooleanExtra("from_sports", false);
        viewModel = ViewModelProviders.of(this).get(WinningClaimViewModel.class);
        viewModel.getLiveDataWinningVerify().observe(this, observerVerifyWinning);
        viewModel.getLiveDataWinningPay().observe(this, observerWinningPay);
        viewModel.getliveDataWinningFailedPrint().observe(this, observerWinningFailedPrint);
        etTicketNumber = findViewById(R.id.et_ticket_number);
        buttonProceed = findViewById(R.id.button_proceed);
        contentFrame = findViewById(R.id.content_frame);
        tilTicketNumber = findViewById(R.id.textInputLayoutTicketNumber);
        ed_first_no = findViewById(R.id.ed_first_no);
        ed_second_no = findViewById(R.id.ed_second_no);
        ed_third_no = findViewById(R.id.ed_third_no);
        ed_fourth_no = findViewById(R.id.ed_fourth_no);
        buttonProceed.setOnClickListener(WinningClaimActivity.this);
        scannerView = findViewById(R.id.scanner_view);

        if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_T2MINI)

                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2s)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2_s)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_V2PRO)
                || Utils.getDeviceName().equalsIgnoreCase(DEVICE_SUNMI_V2)) {
            contentFrame.setVisibility(View.VISIBLE);
            if (AppPermissions.checkPermission(this)) startScanning();
            else AppPermissions.requestPermissionWinning(this);
        } else if (Utils.getDeviceName().equalsIgnoreCase(DEVICE_TPS570)) {
            contentFrame.setVisibility(View.INVISIBLE);
        }

        setFocus(ed_first_no);
        ed_first_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                setFocus(ed_first_no);
        });
        ed_second_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                setFocus(ed_second_no);
        });
        ed_third_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                setFocus(ed_third_no);
        });
        ed_fourth_no.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                setFocus(ed_fourth_no);
        });

        ed_first_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = ed_first_no.getText().toString();
                if (text.length() >= 4) {
                    if (text.length() > 4)
                        ed_first_no.setText(text.substring(0, 4));
                    setFocus(ed_second_no);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed_second_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = ed_second_no.getText().toString();
                if (text.length() >= 4) {
                    if (text.length() > 4)
                        ed_second_no.setText(text.substring(0, 4));
                    setFocus(ed_third_no);
                } else if (ed_second_no.getText().toString().length() == 0) {
                    setFocus(ed_first_no);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed_third_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = ed_third_no.getText().toString();
                if (text.length() >= 4) {
                    if (text.length() > 4)
                        ed_third_no.setText(text.substring(0, 4));
                    setFocus(ed_fourth_no);
                } else if (ed_third_no.getText().toString().length() == 0) {
                    setFocus(ed_second_no);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed_fourth_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ed_fourth_no.getText().toString().length() == 0) {
                    setFocus(ed_third_no);
                } else if (ed_fourth_no.getText().toString().length() == 4) {
                    if (ed_first_no.getText().toString().length() == 4 && ed_second_no.getText().toString().length() == 4 &&
                            ed_third_no.getText().toString().length() == 4 && ed_fourth_no.getText().toString().length() == 4) {
                        if (fromSports)
                            verifyPayTicket(getTicketNumber());
                        else
                            callWinningVerify(getTicketNumber());
                    } else {
                        Utils.showRedToast(WinningClaimActivity.this, getString(R.string.enter_valid_ticket_number));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        scannerView.setOnClickListener(v -> {
            ed_first_no.setText("");
            ed_second_no.setText("");
            ed_third_no.setText("");
            ed_fourth_no.setText("");
            if (mCodeScanner != null)
                mCodeScanner.startPreview();
        });

    }

    private void setFocus(EditText editText) {
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit_dull));
        ed_second_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit_dull));
        ed_third_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit_dull));
        ed_fourth_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit_dull));
        editText.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit));
        editText.requestFocus();
        editText.setSelection(editText.getText().toString().length());
    }

    private void resetEditTexts() {
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit_dull));
        ed_second_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit_dull));
        ed_third_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit_dull));
        ed_fourth_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit_dull));
        ed_first_no.setText("");
        ed_second_no.setText("");
        ed_third_no.setText("");
        ed_fourth_no.setText("");
        ed_first_no.setBackground(ContextCompat.getDrawable(WinningClaimActivity.this, R.drawable.epl_bg_edit));
        ed_first_no.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCodeScanner != null) mCodeScanner.startPreview();
        refreshBalance();
    }

    public void onClickBack(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        WinningClaimActivity.this.finish();
    }

    private void setInitialParameters() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MENU_BEAN = bundle.getParcelable("MenuBean");
        } else {
            Toast.makeText(this, getString(R.string.some_internal_error), Toast.LENGTH_SHORT).show();
            WinningClaimActivity.this.finish();
        }
    }

    private void startScanning() {
        if (Camera.getNumberOfCameras() <= 0) {
            //Toast.makeText(WinningClaimActivity.this, "Camera not found", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mCodeScanner = new CodeScanner(WinningClaimActivity.this, scannerView);
            mCodeScanner.setDecodeCallback(result -> WinningClaimActivity.this.runOnUiThread(() -> onScanned(result)));
            scannerView.setOnClickListener(view1 -> {
                if (mCodeScanner != null)
                    mCodeScanner.startPreview();

                ed_first_no.setText("");
                ed_second_no.setText("");
                ed_third_no.setText("");
                ed_fourth_no.setText("");
            });
        } catch (Exception e) {
            Toast.makeText(WinningClaimActivity.this, "Unable to open scanner", Toast.LENGTH_SHORT).show();
        }
    }

    private void onScanned(Result result) {
        String scannedValue = result.getText().replaceAll("-", "");
        String number = "";
        if (scannedValue.length() == 16) {
            for (int index = 0; index < scannedValue.length(); index++) {
                if (index == 3) {
                    number = number + scannedValue.charAt(index);
                    ed_first_no.setText(number);
                    number = "";
                } else if (index == 7) {
                    number = number + scannedValue.charAt(index);
                    ed_second_no.setText(number);
                    number = "";
                } else if (index == 11) {
                    number = number + scannedValue.charAt(index);
                    ed_third_no.setText(number);
                    number = "";
                } else if (index == 15) {
                    number = number + scannedValue.charAt(index);
                    ed_fourth_no.setText(number);
                    number = "";
                } else {
                    number = number + scannedValue.charAt(index);
                }
            }
            Utils.vibrate(WinningClaimActivity.this);
            //buttonProceed.performClick();
        }
    }

    private void setToolBar() {
        ImageView ivGameIcon = findViewById(R.id.ivGameIcon);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvUserBalance = findViewById(R.id.tvBal);
        tvUsername = findViewById(R.id.tvUserName);
        llBalance = findViewById(R.id.llBalance);

        tvTitle.setText(getString(R.string.winning_claim));
        ivGameIcon.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ed_first_no) {
            setFocus(ed_first_no);
        }
        if (view.getId() == R.id.ed_second_no) {
            if (ed_first_no.getText().toString().length() == 4)
                setFocus(ed_second_no);
            else
                setFocus(ed_first_no);
        }
        if (view.getId() == R.id.ed_third_no) {
            if (ed_second_no.getText().toString().length() == 4)
                setFocus(ed_third_no);
            else {
                if (ed_first_no.getText().toString().length() == 4)
                    setFocus(ed_second_no);
                else
                    setFocus(ed_first_no);
            }
        }
        if (view.getId() == R.id.ed_fourth_no) {
            if (ed_third_no.getText().toString().length() == 4)
                setFocus(ed_fourth_no);
            else {
                if (ed_second_no.getText().toString().length() == 4)
                    setFocus(ed_third_no);
                else {
                    if (ed_first_no.getText().toString().length() == 4)
                        setFocus(ed_second_no);
                    else
                        setFocus(ed_first_no);
                }
            }
        }
        if (view.getId() == R.id.button_proceed) {
            if (validate()) {
                if (fromSports)
                    verifyPayTicket(getTicketNumber());
                else
                    callWinningVerify(getTicketNumber());
            }
        }
    }

    private String getTicketNumber() {
        return ed_first_no.getText().toString() + ed_second_no.getText().toString() + ed_third_no.getText().toString() + ed_fourth_no.getText().toString();
    }

    private void callWinningVerify(String ticket_number) {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText(this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "verifyTicket");
        if (urlBean != null) {
            ProgressBarDialog.getProgressDialog().showProgress(this);
            WinningClaimVerifyRequestBean verifyRequestBean = new WinningClaimVerifyRequestBean();
            verifyRequestBean.setUserName(PlayerData.getInstance().getUsername());
            verifyRequestBean.setMerchantCode("LotteryRMS");
            verifyRequestBean.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
            verifyRequestBean.setTicketNumber(ticket_number);
            verifyRequestBean.setLastPWTTicket(SharedPrefUtil.getLastTicketNumberWinning(this, SharedPrefUtil.getString(this, SharedPrefUtil.USERNAME)));
            viewModel.callWinningVerify(urlBean, verifyRequestBean);
        }
    }

    private boolean validate() {
        Utils.vibrate(this);
        if (ed_first_no.getText().toString().length() != 4 || ed_second_no.getText().toString().length() != 4
                || ed_third_no.getText().toString().length() != 4 || ed_fourth_no.getText().toString().length() != 4) {
            Utils.showRedToast(WinningClaimActivity.this, getString(R.string.enter_valid_ticket_number));
            return false;
        }
        return true;
    }

    Observer<WinningClaimVerifyResponseBean> observerVerifyWinning = response -> {
        //WinningClaimVerifyResponseBean response = new Gson().fromJson(res,WinningClaimVerifyResponseBean.class);
        ProgressBarDialog.getProgressDialog().dismiss();
        boolean showError = false;
        String show_error = "";
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            responseTicketNumber = response.getResponseData().getTicketNumber();
            for (int k = 0; k < response.getResponseData().getDrawData().size(); k++) {
                for (int j = 0; j < response.getResponseData().getDrawData().get(k).getPanelWinList().size(); j++) {
                    if (response.getResponseData().getDrawData().get(k).getPanelWinList().get(j).getStatus().equalsIgnoreCase("UNCLAIMED") && response.getResponseData().getWinClaimAmount() > 0) {
                        showError = false;
                        break;
                    } else {
                        showError = true;
                    }
                }
                if (!showError) {
                    break;
                }
            }
            for (int i = 0; i < response.getResponseData().getDrawData().size(); i++) {
                boolean isWinning = false;
                for (int j = 0; j < response.getResponseData().getDrawData().get(i).getPanelWinList().size(); j++) {
                    if (response.getResponseData().getDrawData().get(i).getPanelWinList().get(j).getStatus().equalsIgnoreCase("UNCLAIMED") && response.getResponseData().getWinClaimAmount() > 0) {
                        Utils.playWinningSound(this, R.raw.small_2);
                        CustomSuccessDialog.getProgressDialog().
                                showDialogClaim(this, response, this, getString(R.string.winning_claim), false, true);
                        isWinning = true;
                        break;
                    } else if (showError && ((j + 1) == response.getResponseData().getDrawData().get(i).getPanelWinList().size())) {
                        if (showError && response.getResponseData().getDrawData().get(i).getWinStatus().equalsIgnoreCase("WIN!!")) {
                            String time[] = response.getResponseData().getDrawData().get(i).getDrawTime().split(":");
                            String bold_status = "<b>" + getString(R.string.ticket_already_claimed) + "</b> ";
                            show_error = show_error + PrintUtil.printTwoStringWinningError(Utils.formatTimeWinningClaim(response.getResponseData().getDrawData().get(i).getDrawDate()) + " " + time[0] + ":" + time[1], String.valueOf(Html.fromHtml(bold_status))) + "\n";
                        } else if (response.getResponseData().getDrawData().get(i).getWinStatus().equalsIgnoreCase("NON WIN!!")) {
                            String time[] = response.getResponseData().getDrawData().get(i).getDrawTime().split(":");
                            String bold_status = "<b>" + getString(R.string.better_luck_next_time) + "</b> ";
                            show_error = show_error + PrintUtil.printTwoStringWinningError(Utils.formatTimeWinningClaim(response.getResponseData().getDrawData().get(i).getDrawDate()) + " " + time[0] + ":" + time[1], String.valueOf(Html.fromHtml(bold_status))) + "\n";
                        } else {
                            String time[] = response.getResponseData().getDrawData().get(i).getDrawTime().split(":");
                            SpannableString bold_status = new SpannableString(response.getResponseData().getDrawData().get(i).getWinStatus());
                            bold_status.setSpan(new StyleSpan(Typeface.BOLD), 0, response.getResponseData().getDrawData().get(i).getWinStatus().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            show_error = show_error + PrintUtil.printTwoStringWinningError(Utils.formatTimeWinningClaim(response.getResponseData().getDrawData().get(i).getDrawDate()) + " " + time[0] + ":" + time[1], String.valueOf(bold_status)) + "\n";
                        }

                    }
                }


                if (isWinning) {
                    break;
                }
            }
            if (showError) {
                Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), show_error);
            }

        } else if (response.getResponseCode() == 1164) {
            if (getTicketNumber() != null)
                printDuplicateWinningReceipt(response.getResponseData().getUser(), getTicketNumber());

        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimActivity.this, response.getResponseCode(), WinningClaimActivity.this))
                return;

            String errorMsg = Utils.getResponseMessage(WinningClaimActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg);
        }
    };

    private void printDuplicateWinningReceipt(String user, String ticket_number) {
        if (!Utils.isNetworkConnected(this)) {
            Toast.makeText(this, getString(R.string.check_internet_connection), Toast.LENGTH_LONG).show();
            return;
        }
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "initiatedpwt");
        if (urlBean != null) {
            viewModel.printDuplicateWinning(urlBean, user, ticket_number);
        }

    }

    Observer<WinningClaimPayResponseBean> observerWinningPay = response -> {
        //WinningClaimPayResponseBean response=new Gson().fromJson(res2,WinningClaimPayResponseBean.class);
        ProgressBarDialog.getProgressDialog().dismiss();
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (responseTicketNumber != null) {
                Intent intent = new Intent(WinningClaimActivity.this, PrintDrawGameActivity.class);
                intent.putExtra("PrintDataWinning", response);
                intent.putExtra("WinningLastTicket", responseTicketNumber);
                intent.putExtra("Category", PrintDrawGameActivity.WINNING);
                startActivityForResult(intent, REQUEST_CODE_PRINT);
            } else {
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimActivity.this, response.getResponseCode(), WinningClaimActivity.this))
                return;
            ErrorDialogListener errorDialogListener = this::resetEditTexts;
            String errorMsg = Utils.getResponseMessage(WinningClaimActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };


    Observer<WinningClaimPayResponseBean> observerWinningFailedPrint = response -> {
        //WinningClaimPayResponseBean response=new Gson().fromJson(res2,WinningClaimPayResponseBean.class);
        if (response == null)
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), getString(R.string.something_went_wrong));
        else if (response.getResponseCode() == 0) {
            if (getTicketNumber() != null) {
                Intent intent = new Intent(WinningClaimActivity.this, PrintDrawGameActivity.class);
                intent.putExtra("PrintDataWinning", response);
                intent.putExtra("WinningLastTicket", getTicketNumber());
                intent.putExtra("Category", PrintDrawGameActivity.WINNING);
                startActivityForResult(intent, REQUEST_CODE_PRINT);
            } else {
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (Utils.checkForSessionExpiryActivity(WinningClaimActivity.this, response.getResponseCode(), WinningClaimActivity.this))
                return;
            ErrorDialogListener errorDialogListener = this::resetEditTexts;
            String errorMsg = Utils.getResponseMessage(WinningClaimActivity.this, "dge", response.getResponseCode());
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg, errorDialogListener);
        }
    };

    @Override
    public void dialogButtonPress(boolean isVerify) {
        if (isVerify) {
            callPayPwt();
        }
    }

    private void verifyPayTicket(String ticketNo) {
        api_call++;
        ProgressBarDialog.getProgressDialog().showProgress(this);
        headerData1 = "userName," + BaseClassSle.getBaseClassSle().getVerifyBean().getUserName() + "|password," + BaseClassSle.getBaseClassSle().getVerifyBean().getPassword() + "|Content-Type," + BaseClassSle.getBaseClassSle().getVerifyBean().getContentType();
        object = new JsonObject();
        object.addProperty("userName", PlayerData.getInstance().getUsername() + "");
        object.addProperty("sessionId", PlayerData.getInstance().getToken().split(" ")[1]);
        object.addProperty("ticketNumber", ticketNo);
        object.addProperty("merchantCode", "NEWRMS");
        object.addProperty("modelCode", Utils.getModelCode());
        object.addProperty("terminalId", Utils.getDeviceSerialNumber());
        HttpRequest httpRequest = new HttpRequest();
        try {
            httpRequest.setAllParameter(BaseClassSle.getBaseClassSle().getBASE_URL() + BaseClassSle.getBaseClassSle().getVerifyBean().getUrl() + "?requestData=" + URLEncoder.encode(object.toString(), "UTF-8"),

                    this, "loading", WinningClaimActivity.this, "verify", headerData1);

            httpRequest.executeTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("API CALL", String.valueOf(api_call));
    }

    private void callPayPwt() {
        UrlDrawGameBean urlBean = Utils.getDrawGameUrlDetails(MENU_BEAN, this, "claimWin");
        if (urlBean != null) {
            if (responseTicketNumber != null) {
                ProgressBarDialog.getProgressDialog().showProgress(this);
                WinningClaimPayRequestBean payRequestBean = new WinningClaimPayRequestBean();
                payRequestBean.setInterfaceType("WEB");
                payRequestBean.setMerchantCode("LotteryRMS");
                payRequestBean.setSessionId(PlayerData.getInstance().getToken().split(" ")[1]);
                payRequestBean.setTicketNumber(responseTicketNumber);
                payRequestBean.setUserName(PlayerData.getInstance().getUsername());
                payRequestBean.setSaleMerCode("NDGE");
                payRequestBean.setVerificationCode("54564456415");
                payRequestBean.setModelCode(Utils.getModelCode());
                payRequestBean.setTerminalId(Utils.getDeviceSerialNumber());
                viewModel.callWinningPayPwt(urlBean, payRequestBean);
            } else
                Toast.makeText(this, getString(R.string.problem_with_ticket_number), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        ProgressBarDialog.getProgressDialog().dismiss();
        super.onDestroy();
    }

    @Override
    public void onResponse(String response, String requestedMethod) {
      /*  response="{\n" +
                "\"responseCode\": 0,\n" +
                "\"responseMsg\": \"SUCCESS\",\n" +
                "\"currDate\": \"2020-01-28|currTime:08:23:09\",\n" +
                "\"ticketNo\": \"2381102848000010\",\n" +
                "\"gameName\": \"Soccer\",\n" +
                "\"gameTypeName\": \"Soccer 13\",\n" +
                "\"drawTime\": \"2020-01-28 06:15:00\",\n" +
                "\"drawName\": \"TestSoccer\",\n" +
                "\"boardCount\": 1,\n" +
                "\"message\": \"NORMAL_PAY\",\n" +
                "\"prizeAmt\": \"16700.00\",\n" +
                "\"totalPay\": \"16700.00 CFA\",\n" +
                "\"totalClmPend\": 0,\n" +
                "\"claimStatus\": true,\n" +
                "\"messageCode\": 0,\n" +
                "\"saleMerCode\": \"NEWRMS\"\n" +
                "}";*/
        ProgressBarDialog.getProgressDialog().dismiss();
        Intent intent;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            if (jsonObject.has("errorCode")) {
                //Utils.showCustomErrorDialog(WinningClaimActivity.this, getString(R.string.winning_claim), jsonObject.optString("errorMsg"));
                String errorMsg = Utils.getResponseMessage(WinningClaimActivity.this, "sle", Integer.parseInt(jsonObject.optString("errorCode")));
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (requestedMethod.equalsIgnoreCase("verify")) {
            VerifyPayTicket verifyPayTicket = new Gson().fromJson(response, VerifyPayTicket.class);
            if (verifyPayTicket.getMessageCode() == 0 && verifyPayTicket.getResponseCode() == 0) {
                intent = new Intent();
                intent.putExtra("winning_response", response);
                setResult(03, intent);
                finish();
            } else if (jsonObject.has("responseCode") && verifyPayTicket.getResponseCode() != 0) {
                //Utils.showCustomErrorDialog(WinningClaimActivity.this, getString(R.string.winning_claim), verifyPayTicket.getResponseMsg());
                String errorMsg = Utils.getResponseMessage(WinningClaimActivity.this, "sle", verifyPayTicket.getResponseCode());
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
            } else {
                //Utils.showCustomErrorDialog(WinningClaimActivity.this, getString(R.string.winning_claim), verifyPayTicket.getMessage());
                String errorMsg = Utils.getResponseMessage(WinningClaimActivity.this, "sle", verifyPayTicket.getMessageCode());
                Utils.showCustomErrorDialog(this, getString(R.string.sports), errorMsg);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null && data.getExtras().getBoolean("isBalanceUpdate")) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("isBalanceUpdate", true);
            setResult(Activity.RESULT_OK, returnIntent);
            WinningClaimActivity.this.finish();
        } else if (data != null && data.getExtras() != null && !data.getExtras().getBoolean("isBalanceUpdate")) {
            String errorMsg = getString(R.string.insert_paper_to_print);
            Utils.showCustomErrorDialog(this, getString(R.string.winning_claim), errorMsg);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSION GRANTED
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(WinningClaimActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                        } else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            AppPermissions.showMessageOKCancel(WinningClaimActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                        } else {
                            startScanning();
                        }
                    } else {
                        // PERMISSION DENIED
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                AppPermissions.showMessageOKCancel(WinningClaimActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                            } else if (ContextCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                AppPermissions.showMessageOKCancel(WinningClaimActivity.this, getString(R.string.allow_permission), this, ed_first_no);
                            }
                        }
                        Utils.showRedToast(WinningClaimActivity.this, "Permission denied to use Storage");
                        WinningClaimActivity.this.finish();
                    }
                    break;

                }
        }
    }
}