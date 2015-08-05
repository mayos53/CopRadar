package com.app.copradar.network.base;

/**
 * Created by Yossef on 8/4/15.
 */
public class BaseResponse {

    private final static int STATUS_OK = 1;
    private int status;

    public boolean isSuccessful(){
        return status == STATUS_OK;
    }

    public int getStatus(){
        return status;
    }
}
