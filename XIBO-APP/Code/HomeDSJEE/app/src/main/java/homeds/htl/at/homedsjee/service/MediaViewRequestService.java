package homeds.htl.at.homedsjee.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import homeds.htl.at.homedsjee.apiClient.RequestHelper;

/**
 * Created by Felix on 12.03.2018.
 */

public class MediaViewRequestService extends IntentService {
    public MediaViewRequestService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RequestHelper rh = new RequestHelper();

    }
}
