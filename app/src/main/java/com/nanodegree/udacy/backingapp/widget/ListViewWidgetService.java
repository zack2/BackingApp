package com.nanodegree.udacy.backingapp.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by root on 17/09/2017.
 */

public class ListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.e("BALog_service", "action: "+intent.getAction());
        return new ListViewRemoteViewsFactory(this.getApplicationContext());
    }
}