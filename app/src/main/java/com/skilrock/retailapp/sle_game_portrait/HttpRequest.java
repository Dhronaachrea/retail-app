package com.skilrock.retailapp.sle_game_portrait;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//importdemo.stpl.com.tpsmergedbuild.baseClass.TpsGamesClass;
//import tpsgames.interfaces.ResponseLis;


/**
 * Created by stpl on 9/5/2016. network request class
 */
public class HttpRequest {

    private String url;
    private ResponseLis responseLis;
    private String loadingMessage;
    private Context context;
    private URL urlRequest;
    private HttpURLConnection connection;
    private HttpsURLConnection connectionS;
    private int requestTime = 60000;
    private String responseString;
    private String requestMethod;
    private boolean isContentChange = false;
    private String httpRequestMethod = "POST";
    private String setRequestProperty = "";

    private boolean isConnectToInternet = false;
    private boolean isServerConnected = false;

    private String userName;

    private String headerData;
    private boolean isParams;
    private String params = "";
    long startTime;


    public void setAllParameter(String url, ResponseLis responseLis, String loadingMessage, Context context, String requestMethod) {
        this.responseLis = responseLis;
        this.url = url;
        this.loadingMessage = loadingMessage;
        this.context = context;
        this.requestMethod = requestMethod;
    }

    public void setAllParameter(String url, ResponseLis responseLis, String loadingMessage, Context context, String requestMethod, String headerData) {
        this.responseLis = responseLis;
        this.url = url;
        this.loadingMessage = loadingMessage;
        this.context = context;
        this.requestMethod = requestMethod;
        this.headerData = headerData;
    }

    public void setIsParams(boolean isParams, String params) {

        this.isParams = isParams;
        this.params = params;
    }

    public void setHttpRequestMethod(String httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    public void setSetRequestProperty(String requestProperty) {
        this.setRequestProperty = requestProperty;
    }

    public void executeTask() {
        new UrlRequest().execute();
    }

    public boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }

    private static SSLSocketFactory createSslSocketFactory() throws Exception {
        TrustManager[] byPassTrustManagers = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        }};
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, byPassTrustManagers, new SecureRandom());
        return sslContext.getSocketFactory();
    }

    // asynctask for netwrok request
    private class UrlRequest extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
//            userName = TpsGamesClass.getInstance().getLoginResponse().getData().getRetailerName();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected String doInBackground(String... params) {
            if (true) {
                try {
//
//                    int port = 8085;
//
//                    String hostname = "rms.mlottcongo.com";
//
//                    SSLSocketFactory factory = HttpsURLConnection
//                            .getDefaultSSLSocketFactory();
//
//                    System.out.println("Creating a SSL Socket For "+hostname+" on port "+port);
//
//                    SSLSocket socket = (SSLSocket) factory.createSocket(hostname, port);
//
///**
// * Starts an SSL handshake on this connection. Common reasons include a
// * need to use new encryption keys, to change cipher suites, or to
// * initiate a new session. To force complete reauthentication, the
// * current session could be invalidated before starting this handshake.
// * If data has already been sent on the connection, it continues to flow
// * during this handshake. When the handshake completes, this will be
// * signaled with an event. This method is synchronous for the initial
// * handshake on a connection and returns when the negotiated handshake
// * is complete. Some protocols may not support multiple handshakes on an
// * existing socket and may throw an IOException.
// */
//
//                    socket.startHandshake();
//                    System.out.println("Handshaking Complete");
                    boolean isConnectionSecure = false;
                    startTime = System.currentTimeMillis();
                    isConnectToInternet = true;
                        urlRequest = new URL(url);
                    Log.i("request url", url);
                    Object connectionObject = urlRequest.openConnection();
                    if ("NO".equals("NO")) {
                        connection = (HttpURLConnection) connectionObject;
                        isConnectionSecure = false;
                    } else if ("no".equals("YES")) {
//                        HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
//                        SSLContext context = SSLContext.getInstance("TLS");
//                        context.init(null, new X509TrustManager[]{new X509TrustManager() {
//                            @Override
//                            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//                            }
//
//                            @Override
//                            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//                            }
//
//                            @Override
//                            public X509Certificate[] getAcceptedIssuers() {
//                                return new X509Certificate[0];
//                            }
//                        }}, new SecureRandom());
//                        connectionS.setDefaultSSLSocketFactory(context.getSocketFactory());
                        connectionS = (HttpsURLConnection) connectionObject;
                        SSLSocketFactory sslSocketFactory = createSslSocketFactory();
//
                        connectionS.setSSLSocketFactory(sslSocketFactory);
                        isConnectionSecure = true;
                    }

//
                    if (requestMethod.equals("sports") || requestMethod.equals("dgeSale")) {
                        if (requestTime <= 0) {
                            throw new SocketTimeoutException();
                        }

                        if (isConnectionSecure) {

                            connectionS.setConnectTimeout(requestTime);
                            connectionS.setReadTimeout(requestTime);
                        } else {
                            connection.setConnectTimeout(requestTime);
                            connection.setReadTimeout(requestTime);
                        }

                    } else {
                        if (isConnectionSecure) {
                            connectionS.setConnectTimeout(60000);
                            connectionS.setReadTimeout(60000);
                        } else {
                            connection.setConnectTimeout(60000);
                            connection.setReadTimeout(60000);
                        }

                    }

                    if (isParams) {
                        if (isConnectionSecure) {
                            connectionS.setRequestProperty("Content-Type", "application/json");
                            connectionS.setRequestProperty("Accept", "application/json");
                        } else {
                            connection.setRequestProperty("content-Type", "application/x-www-form-urlencoded");
                            connection.setRequestProperty("Accept", "application/json");

                        }

                    }
                    if (isConnectionSecure) {
                        connectionS.setDoOutput(true);
                        connectionS.setDoInput(true);
//                    connection.setInstanceFollowRedirects(false);
//                    connection.addRequestProperty();
                        connectionS.setRequestMethod(httpRequestMethod);
                    } else {
                        if(httpRequestMethod.equals("GET")){
                            connection.setUseCaches(false);
                            connection.setAllowUserInteraction(false);
                        }else{
                            connection.setDoOutput(true);
                            connection.setDoInput(true);
                        }

//                    connection.setInstanceFollowRedirects(false);
//                    connection.addRequestProperty();
                        connection.setRequestMethod(httpRequestMethod);
                    }


                    if (headerData != null && headerData.trim().length() > 0) {
                        String[] header = headerData.split("[|]");
                        for (String data : header) {
                            Log.i("Header", "Header: " + data);
                            String[] dataHeader = data.split("[,]");
                            if (isConnectionSecure) {
                                connectionS.setRequestProperty(dataHeader[0], dataHeader[1]);
                            } else {
                                connection.setRequestProperty(dataHeader[0], dataHeader[1]);
                            }

                        }
                        OutputStreamWriter writer = null;
                        if (isParams) {
                            if (isConnectionSecure) {
                                writer = new OutputStreamWriter(connectionS.getOutputStream());
                            } else {
                                writer = new OutputStreamWriter(connection.getOutputStream());
                            }
                            Log.i("request params", HttpRequest.this.params);
                            writer.write(HttpRequest.this.params);
                            writer.flush();
                            writer.close();
                        } else {
                            if(httpRequestMethod.equals("POST")){
                                JSONObject jsonParam = new JSONObject();
//                                jsonParam.put("username", TpsGamesClass.getInstance().getLoginResponse().getData().getRetailerName());
                                if (isConnectionSecure) {
                                    writer = new OutputStreamWriter(connectionS.getOutputStream());
                                } else {
                                    writer = new OutputStreamWriter(connection.getOutputStream());
                                }
                                writer.write(jsonParam.toString());
                                writer.flush();
                                writer.close();
                            }

                        }

                    } else if (setRequestProperty != null && setRequestProperty.length() > 0) {
                        OutputStreamWriter writer;


                        if (isConnectionSecure) {
                            connectionS.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            writer = new OutputStreamWriter(connectionS.getOutputStream(), "UTF-8");
                        } else {
                            if(!requestMethod.equalsIgnoreCase("gameData"))
                            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                        }
                        Log.i("request params", HttpRequest.this.params);
                        writer.write(HttpRequest.this.params);
                        writer.flush();
                        writer.close();
                    } else if (isParams) {
                        OutputStreamWriter writer;
                        if (isConnectionSecure) {
                            writer = new OutputStreamWriter(connectionS.getOutputStream(), "UTF-8");
                        } else {
                            writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                        }
                        Log.i("request params", HttpRequest.this.params);
                        writer.write(HttpRequest.this.params);
                        writer.flush();
                        writer.close();
                    }


//                    connection.setUseCaches(false);
                    if (isConnectionSecure) {
                        connectionS.connect();
                    } else {
                        connection.connect();
                    }


                    long start = System.currentTimeMillis();
                    BufferedReader in;
                    String inputLine;
                    if (isConnectionSecure) {
                        in = new BufferedReader(
                                new InputStreamReader(connectionS.getInputStream()));
                    } else {
                        in = new BufferedReader(
                                new InputStreamReader(connection.getInputStream()));

                    }
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    responseString = response.toString();
                    isServerConnected = true;
                    Log.i("response", responseString);


                } catch (SocketTimeoutException e) {
                    isServerConnected = false;
                    if (requestMethod.equals("sports") || requestMethod.equals("dgeSale"))
                        responseString = "socketTimeOut";
                    else {
                        responseString = "Failed";
                    }
                    e.printStackTrace();
                } catch (IOException e) {
                    isServerConnected = false;
                    responseString = "Failed";
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    responseString = "Failed";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    setSetRequestProperty("");

                }
            } else {
                isConnectToInternet = false;
            }
            if (!isConnectToInternet || responseString == null || (responseString != null && (responseString.equalsIgnoreCase("null") || responseString.trim().length() == 0))) {
                responseString = "Failed";
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            if(!isConnectToInternet){
//                responseLis.onResponse(result, requestMethod);
//                return;
//            }
//            if (!isServerConnected || responseString.equalsIgnoreCase("Failed")) {
//                if (requestMethod.equalsIgnoreCase("login")) {
//                    result = TpsGamesClass.getInstance().getLoginResponseOffLine();
//                } else if (requestMethod.equalsIgnoreCase("gameData")) {
//                    result = TpsGamesClass.getInstance().getDrawGameFetchDataOffLine();
//                }
//
////                result = result.replaceAll("\\s+", "");
////                result = result.replaceAll("[\t\n]", "");
//
//            }

            headerData = null;
            params = null;
            isParams = false;
            httpRequestMethod = "POST";
//            TpsGamesClass.getInstance().setPlayerVerified(false, "");


            if (result != null) {
                responseLis.onResponse(result, requestMethod);
            }
        }

    }


    public class NullHostNameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            Log.i("RestUtilImpl", "Approving certificate for " + hostname);
            return true;
        }

    }

}
