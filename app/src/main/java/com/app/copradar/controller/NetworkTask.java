package com.app.copradar.controller;

import android.os.AsyncTask;

import com.app.copradar.network.base.BaseRequest;
import com.app.copradar.network.base.BaseResponse;
import com.app.copradar.network.NetworkClient;
import com.app.copradar.network.requests.GetCopPresenceRequest;


/**
 * Created by Yossef on 5/18/15.
 */
public class NetworkTask extends AsyncTask<BaseRequest, Integer, BaseResponse>  {

    private final NetworkTaskListener listener;
    private NetworkClient networkClient;

    public NetworkTask(NetworkTaskListener listener){
        this.listener = listener;
        networkClient = NetworkClient.getInstance();
    }

    @Override
    protected BaseResponse doInBackground(BaseRequest... params) {
        BaseRequest request = params[0];
        BaseResponse response = null;
        if(request instanceof GetCopPresenceRequest){
            response = networkClient.getCopPresences((GetCopPresenceRequest)request);
        }
        return response;

    }

    @Override
    protected void onPostExecute(BaseResponse response) {
        super.onPostExecute(response);
        if(listener != null){
            if(response.isSuccessful()){
                listener.onSuccess(response);
            }else {
                listener.onError(response.getStatus());
            }
        }
    }

    public interface NetworkTaskListener{
        public void onSuccess(BaseResponse response);
        public void onError(int errorCode);
    }
}
