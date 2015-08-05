package com.app.copradar.network.responses;

import com.app.copradar.model.CopPresence;
import com.app.copradar.network.base.BaseResponse;

import java.util.List;

/**
 * Created by Yossef on 8/4/15.
 */
public class GetCopPresenceResponse extends BaseResponse{
    private List<CopPresence> copPresences;
}
