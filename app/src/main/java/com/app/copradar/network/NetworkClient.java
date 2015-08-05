package com.app.copradar.network;

import com.app.copradar.network.base.BaseRequest;
import com.app.copradar.network.base.BaseResponse;
import com.app.copradar.network.requests.GetCopPresenceRequest;
import com.app.copradar.network.responses.GetCopPresenceResponse;
import com.app.copradar.util.util.Loger;
import com.app.copradar.util.util.Tools;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Yossef on 8/4/15.
 */
public class NetworkClient {


    private final static String BASE_URL = "";
    private final static String SUFFIX_COPS = "cops";


    private final static String TAG = "NetworkClient";
    private static NetworkClient networkClient;

    private Gson gson;
    private String baseUrl;

    public NetworkClient() {
        gson = new Gson();
        baseUrl = BASE_URL;
    }

    public static NetworkClient getInstance() {
       if(networkClient == null){
           networkClient = new NetworkClient();
       }
       return networkClient;
    }


    public GetCopPresenceResponse getCopPresences(GetCopPresenceRequest request) {
        String url = baseUrl + "/" + SUFFIX_COPS;
        return (GetCopPresenceResponse) postData(url, request, GetCopPresenceResponse.class);
    }

    private BaseResponse postData(String url, BaseRequest request, Class responseClass) {

        BaseResponse result = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");

            Loger.d(TAG, "postData url: " + url);
            String params = gson.toJson(request);

            Loger.d(TAG, "postData params: " + params);
            httpPost.setEntity(new StringEntity(params, "UTF-8"));
            HttpResponse httpResponse = httpclient.execute(httpPost);

            result = parseResponse(responseClass, httpResponse);


        } catch (Exception e) {
            Loger.e(TAG, "", e);
        }

        return result;
    }

    private BaseResponse getData(String url, BaseRequest request, Class responseClass) {

        BaseResponse result = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            //
            // Loger.d(TAG, "getData url: " + url);
            //  String params = gson.toJson(request);

            //Loger.d(TAG, "postData params: " + params);
            //httpPost.setEntity(new StringEntity(params));
            HttpResponse httpResponse = httpclient.execute(httpGet);

            result = parseResponse(responseClass, httpResponse);


        } catch (Exception e) {
            Loger.e(TAG, "", e);
        }

        return result;
    }

    private BaseResponse parseResponse(Class<? extends BaseResponse> responseClass, HttpResponse httpResponse) throws
            IOException {
        BaseResponse result = null;

        InputStream inputStream;
        inputStream = httpResponse.getEntity().getContent();
        if (inputStream != null) {
            String str = Tools.convertInputStreamToString(inputStream);
            Loger.d(TAG, "response : " + str);
            result = gson.fromJson(str, responseClass);
        }
        return result;
    }
}
