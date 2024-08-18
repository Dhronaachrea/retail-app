package com.skilrock.retailapp.utils;

public class FetchResponse {

   public static String res = "{\n" +
           "  \"responseCode\": 0,\n" +
           "  \"responseData\": {\n" +
           "    \"message\": \"Success\",\n" +
           "    \"moduleBeanLst\": [\n" +
           "      {\n" +
           "        \"displayName\": \"Lottery\",\n" +
           "        \"menuBeanList\": [\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"getSchemaByGame\\\":{\\\"url\\\":\\\"/DMS/draw/result\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Result\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"DGE_RESULT_LIST\",\n" +
           "            \"menuId\": 121,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 1\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"getSchemaByGame\\\":{\\\"url\\\":\\\"/DMS/dataMgmt/getSchemaByGame\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Prize Schema\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"DGE_PRIZE_LIST\",\n" +
           "            \"menuId\": 106,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 1\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"fetchGames\\\":{\\\"url\\\":\\\"/DMS/dataMgmt/fetchGameData\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"getSchemaByGame\\\":{\\\"url\\\":\\\"/DMS/dataMgmt/getSchemaByGame\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"quickPick\\\":{\\\"url\\\":\\\"/DMS/ticket/pickednumber\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"buy\\\":{\\\"url\\\":\\\"/DMS/ticket/buy\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"cancelTicket\\\":{\\\"url\\\":\\\"/DMS/ticket/cancelTicket\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"verifyTicket\\\":{\\\"url\\\":\\\"/DMS/ticket/verify\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"claimWin\\\":{\\\"url\\\":\\\"/DMS/win/payPwt\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"reprintTicket\\\":{\\\"url\\\":\\\"/DMS/ticket/reprint\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"getResult\\\":{\\\"url\\\":\\\"/DMS/draw/result\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"initiatedpwt\\\":{\\\"url\\\":\\\"/DMS/pwtMgmt/initiatedpwt\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"spinAndWin\\\":{\\\"url\\\":\\\"https://uat-dge.camwinlotto.com/spinwin/index.php\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"spinAndWinDomain\\\":{\\\"url\\\":\\\"https://uat-dge.camwinlotto.com/spinwin/index.php\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Game List\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"DGE_GAME_LIST\",\n" +
           "            \"menuId\": 12,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 1\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"buy\\\":{\\\"url\\\":\\\"/DMS/ticket/buy\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Draw Game Sale\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"DGE_SALE\",\n" +
           "            \"menuId\": 17,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"CAN_DO_SALE_DGE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 2\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"quickPick\\\":{\\\"url\\\":\\\"/DMS/ticket/pickednumber/\\u003cgamecode\\u003e\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Draw Game Quick Pick\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"DGE_QUICK_PICK\",\n" +
           "            \"menuId\": 18,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 3\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"reprintTicket\\\":{\\\"url\\\":\\\"/DMS/ticket/reprint\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Draw Game Reprint\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"DGE_REPRINT\",\n" +
           "            \"menuId\": 19,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 4\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"cancelTicket\\\":{\\\"url\\\":\\\"/DMS/ticket/cancelTicket\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Cancel Tiket\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"DGE_CANCEL_TICKET\",\n" +
           "            \"menuId\": 21,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 5\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"verifyTicket\\\":{\\\"url\\\":\\\"/DMS/ticket/verify\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"claimWin\\\":{\\\"url\\\":\\\"/DMS/win/payPwt\\\",\\\"headers\\\":{\\\"username\\\":\\\"LotteryRMS\\\",\\\"password\\\":\\\"password\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Winning Claim\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"DGE_WIN_CLAIM\",\n" +
           "            \"menuId\": 22,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"CAN_CLAIM_WINNING_DGE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 6\n" +
           "          }\n" +
           "        ],\n" +
           "        \"moduleCode\": \"DRAW_GAME\",\n" +
           "        \"moduleId\": 5,\n" +
           "        \"sequence\": 1\n" +
           "      },\n" +
           "      {\n" +
           "        \"displayName\": \"Scratch\",\n" +
           "        \"menuBeanList\": [\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"verifyTicket\\\":{\\\"url\\\":\\\"/Winning/verifyWinning\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"claimWin\\\":{\\\"url\\\":\\\"/Winning/claimWinning\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"gameList\\\":{\\\"url\\\":\\\"/game/gameList\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com/PPL\",\n" +
           "            \"caption\": \"Ticket Validation \\u0026 Claim\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SCRATCH_WIN_CLAIM\",\n" +
           "            \"menuId\": 28,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 2\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"quickOrder\\\":{\\\"url\\\":\\\"/order/quickOrder\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"gameList\\\":{\\\"url\\\":\\\"/game/gameDetailsForQuickOrder\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com/PPL\",\n" +
           "            \"caption\": \"Quick Order\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SCRATCH_ORDER_BOOK\",\n" +
           "            \"menuId\": 29,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 3\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"receiveBook\\\":{\\\"url\\\":\\\"/inventory/bookReceive\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"dlDetails\\\":{\\\"url\\\":\\\"/inventory/dlDetails\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com/PPL\",\n" +
           "            \"caption\": \"Book Receive\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SCRATCH_RECEIVE_BOOK\",\n" +
           "            \"menuId\": 30,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 4\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"inventoryReport\\\":{\\\"url\\\":\\\"/reports/getInventoryDetailsForRetailer\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com/PPL\",\n" +
           "            \"caption\": \"Inventory Report\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"M_SCRATCH_INV_REPORT\",\n" +
           "            \"menuId\": 110,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 5\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"activateBook\\\":{\\\"url\\\":\\\"/inventory/activateBooks\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}},\\\"gameList\\\":{\\\"url\\\":\\\"/game/gameList\\\",\\\"headers\\\":{\\\"clientId\\\":\\\"RMS\\\",\\\"clientSecret\\\":\\\"13f1JiFyWSZ0XI/3Plxr3mv7gbNObpU2\\\",\\\"Content-Type\\\":\\\"application/json\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com/PPL\",\n" +
           "            \"caption\": \"Book Activation\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SCRATCH_ACTIVATE_BOOK\",\n" +
           "            \"menuId\": 31,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 5\n" +
           "          }\n" +
           "        ],\n" +
           "        \"moduleCode\": \"SCRATCH\",\n" +
           "        \"moduleId\": 6,\n" +
           "        \"sequence\": 2\n" +
           "      },\n" +
           "      {\n" +
           "        \"displayName\": \"Reports\",\n" +
           "        \"menuBeanList\": [\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"getSaleReport\\\":{\\\"url\\\":\\\"/v1.0/getSaleReport\\\"},\\\"getServiceList\\\":{\\\"url\\\":\\\"/v1.0/getServiceList\\\"}}\",\n" +
           "            \"basePath\": \"https://uat-rms.camwinlotto.com/RMS\",\n" +
           "            \"caption\": \"Sale Win Txn. Report\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"M_SALE_REPORT\",\n" +
           "            \"menuId\": 14,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"CAN_FETCH_SALES_REPORT\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 2\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"\",\n" +
           "            \"basePath\": \"https://uat-rms.camwinlotto.com/RMS\",\n" +
           "            \"caption\": \"Ledger Report\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"M_LEDGER\",\n" +
           "            \"menuId\": 15,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"CAN_FETCH_LEDGER\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"/v1.0/getLedger\",\n" +
           "            \"sequence\": 3\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"\",\n" +
           "            \"basePath\": \"https://uat-rms.camwinlotto.com/RMS\",\n" +
           "            \"caption\": \"Summarized Ledger Report\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"M_SUMMARIZE_LEDGER\",\n" +
           "            \"menuId\": 113,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"CAN_FETCH_SUMMARIZE_LEDGER\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"/v1.0/getSummarizedLedger\",\n" +
           "            \"sequence\": 6\n" +
           "          }\n" +
           "        ],\n" +
           "        \"moduleCode\": \"REPORTS\",\n" +
           "        \"moduleId\": 3,\n" +
           "        \"sequence\": 4\n" +
           "      },\n" +
           "      {\n" +
           "        \"displayName\": \"Sports Lottery\",\n" +
           "        \"menuBeanList\": [\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"matchList\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/drawMgmt/Action/fetchSleMatchList.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Match List\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SLE_23\",\n" +
           "            \"menuId\": 152,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 1\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"resultList\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/drawMgmt/Action/fetchSleDrawWiseGameWiseResultData.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Result\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SLE_24\",\n" +
           "            \"menuId\": 153,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 1\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"fetchGames\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/drawMgmt/Action/fetchSLEDrawData.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}},\\\"sale\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/playMgmt/action/sportsLotteryPurchaseTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}},\\\"reprint\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/playMgmt/action/reprintTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}},\\\"cancel\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/playMgmt/action/cancelTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}},\\\"verifyTicket\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/pwtMgmt/Action/verifyWebTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}},\\\"claimTicket\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/pwtMgmt/Action/payPwtWebTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}},\\\"matchList\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/drawMgmt/Action/fetchSleMatchList.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}},\\\"resultList\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/drawMgmt/Action/fetchSleDrawWiseGameWiseResultData.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Sale\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SLE_19\",\n" +
           "            \"menuId\": 148,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 2\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"reprint\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/playMgmt/action/reprintTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Reprint\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SLE_20\",\n" +
           "            \"menuId\": 149,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 4\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"cancel\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/playMgmt/action/cancelTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Cancel Tiket\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SLE_21\",\n" +
           "            \"menuId\": 150,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 5\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"verifyTicket\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/pwtMgmt/Action/verifyWebTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}},\\\"claimTicket\\\":{\\\"url\\\":\\\"/SportsLottery/com/skilrock/sle/web/merchantUser/pwtMgmt/Action/payPwtWebTicket.action\\\",\\\"headers\\\":{\\\"userName\\\":\\\"E49B4EF3-1511-4B8B-86D2-CE78DA0F74D6\\\",\\\"password\\\":\\\"p@55w0rd\\\",\\\"Content-Type\\\":\\\"application/x-www-form-urlencoded\\\"}}}\",\n" +
           "            \"basePath\": \"https://uat.camwinlotto.com\",\n" +
           "            \"caption\": \"Winning Claim\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"SLE_22\",\n" +
           "            \"menuId\": 151,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 6\n" +
           "          }\n" +
           "        ],\n" +
           "        \"moduleCode\": \"SLE\",\n" +
           "        \"moduleId\": 9,\n" +
           "        \"sequence\": 5\n" +
           "      },\n" +
           "      {\n" +
           "        \"displayName\": \"Camwin247\",\n" +
           "        \"menuBeanList\": [\n" +
           "          {\n" +
           "            \"basePath\": \"https://www.camwin247.com\",\n" +
           "            \"caption\": \"Camwin247\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"CAMWIN247\",\n" +
           "            \"menuId\": 209,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 1\n" +
           "          }\n" +
           "        ],\n" +
           "        \"moduleCode\": \"CAMWIN247\",\n" +
           "        \"moduleId\": 15,\n" +
           "        \"sequence\": 6\n" +
           "      },\n" +
           "      {\n" +
           "        \"displayName\": \"SBS\",\n" +
           "        \"menuBeanList\": [\n" +
           "          {\n" +
           "            \"basePath\": \"https://www.camwin247.com\",\n" +
           "            \"caption\": \"SBS\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"Sports Bet\",\n" +
           "            \"menuId\": 209,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"NONE\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 1\n" +
           "          }\n" +
           "        ],\n" +
           "        \"moduleCode\": \"SBS\",\n" +
           "        \"moduleId\": 15,\n" +
           "        \"sequence\": 6\n" +
           "      },\n" +
           "      {\n" +
           "        \"displayName\": \"Organizations\",\n" +
           "        \"menuBeanList\": [\n" +
           "          {\n" +
           "            \"apiDetails\": \"\",\n" +
           "            \"basePath\": \"https://uat-rms.camwinlotto.com/RMS\",\n" +
           "            \"caption\": \"Change PIN\",\n" +
           "            \"checkForPermissions\": 0,\n" +
           "            \"menuCode\": \"M_CHANGE_PASS\",\n" +
           "            \"menuId\": 16,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"CAN_CHANGE_PASSWORD\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"/v1.0/changePassword\",\n" +
           "            \"sequence\": 9\n" +
           "          },\n" +
           "          {\n" +
           "            \"apiDetails\": \"\",\n" +
           "            \"basePath\": \"https://uat-rms.camwinlotto.com/RMS\",\n" +
           "            \"caption\": \"Logout\",\n" +
           "            \"checkForPermissions\": 0,\n" +
           "            \"menuCode\": \"M_LOGOUT\",\n" +
           "            \"menuId\": 20,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"CAN_LOGOUT\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"/v1.0/logOut\",\n" +
           "            \"sequence\": 10\n" +
           "          }\n" +
           "        ],\n" +
           "        \"moduleCode\": \"USERS\",\n" +
           "        \"moduleId\": 14,\n" +
           "        \"sequence\": 10\n" +
           "      },\n" +
           "      {\n" +
           "        \"displayName\": \"Live Bet\",\n" +
           "        \"menuBeanList\": [\n" +
           "          {\n" +
           "            \"apiDetails\": \"{\\\"getAuthUrl\\\":{\\\"url\\\":\\\"/v1.0/getBetGamesAuthUrl\\\"}}\",\n" +
           "            \"basePath\": \"https://uat-rms.camwinlotto.com/RMS\",\n" +
           "            \"caption\": \"Bet Games\",\n" +
           "            \"checkForPermissions\": 1,\n" +
           "            \"menuCode\": \"BET_GAME\",\n" +
           "            \"menuId\": 196,\n" +
           "            \"permissionCodeList\": [\n" +
           "              \"GET_BETGAMES_AUTH_URL\"\n" +
           "            ],\n" +
           "            \"relativePath\": \"\",\n" +
           "            \"sequence\": 1\n" +
           "          }\n" +
           "        ],\n" +
           "        \"moduleCode\": \"BETGAME\",\n" +
           "        \"moduleId\": 12,\n" +
           "        \"sequence\": 21\n" +
           "      }\n" +
           "    ],\n" +
           "    \"statusCode\": 0\n" +
           "  },\n" +
           "  \"responseMessage\": \"Success\"\n" +
           "}";

    public static String getRes() {
        return res;
    }
}
