package com.nanodegree.udacy.backingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.nanodegree.udacy.backingapp.R;


/**
 * Created by root on 27/08/2017.
 */

public class ListViewWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_TOAST = "com.nanodegree.udacy.backingapp.widget.TOAST_ACTION";
    public static final String ACTION_DETAILS = "com.nanodegree.udacy.backingapp.widget.DETAILS_ACTION";
    public static final String ACTION_WIDGET_CLICK = "com.nanodegree.udacy.backingapp.widget.CLICK_ACTION";
    public static final String EXTRA_ITEM = "com.nanodegree.udacy.backingapp.widget.EXTRA_ITEM";
    public static final String EXTRA_ITEM_STEP_ID = "com.nanodegree.udacy.backingapp.widget.EXTRA_ITEM_STEP_ID";
    public static final String EXTRA_ITEM_RECIPE_ID = "com.nanodegree.udacy.backingapp.widget.EXTRA_ITEM_RECIPE_ID";

    // Called when the BroadcastReceiver receives an Intent broadcast.
    // Checks to see whether the intent's action is TOAST_ACTION. If it is, the app widget
    // displays a Toast message for the current item.
    @Override
    public void onReceive(Context context, Intent intent) {

        //AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(ACTION_TOAST)) {
            Log.e("BALog", "clicked on e item, action:" + intent.getAction());
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }

        if (intent.getAction().equals(ACTION_WIDGET_CLICK)) {
            Log.e("BALog", "clicked on widget, action:" + intent.getAction());
            Toast.makeText(context,"clicked on widget, action:", Toast.LENGTH_SHORT).show();
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // update each of the app widgets with the remote adapter
        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @SuppressWarnings("deprecation")
    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);
        // Set up the RemoteViews object to use a RemoteViews adapter.
        // This adapter connects
        // to a RemoteViewsService  through the specified intent.
        // This is how you populate the data.

        //For ListView
        Intent svcIntent = new Intent(context, ListViewWidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        rv.setRemoteAdapter(appWidgetId, R.id.widgetListView, svcIntent);

        rv.setEmptyView(R.id.widgetListView, R.id.empty_view);
        //When item is clicked on ListView

        Intent toastIntent = new Intent(context, ListViewWidgetService.class);
        toastIntent.setAction(ACTION_TOAST);
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        toastIntent.setData(Uri.parse(toastIntent.toUri(Intent.URI_INTENT_SCHEME)));
        rv.setPendingIntentTemplate(R.id.widgetListView, toastPendingIntent);



        Intent clickIntent = new Intent(context, ListViewWidgetService.class);
        clickIntent.setAction(ACTION_WIDGET_CLICK);
        clickIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent clickPI = PendingIntent
                .getActivity(context, appWidgetId,
                        clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        rv.setPendingIntentTemplate(R.id.widgetListView, clickPI);



        return rv;
    }

}
