package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;


import qbabor4.pl.liskwidget.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MyWidgetActivityConfigureActivity MyWidgetActivityConfigureActivity}
 */
public class MyWidgetActivity extends AppWidgetProvider {

    private static int num1 = 0;

    private static final String SYNC_CLICKED = "automaticWidgetSyncButtonClick";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
            /* TODO
             - dostac kurs liska na pln
             - zmianiac co minutę kurs na widgecie
             - dać alarm zamiast updatowania w jakimś okresie
             - wywalić konfigurację
             - guzik z refresh, żeby updatowac ręcznie kurs
             - załozyc jakis timer zeby co 5 sec sie updatowało
             - dodac wybór czasu updatowania w konfiguracji
              */
        num1 += 1;
        String widgetText = String.valueOf(num1);
        //TOAST
        Toast toast = Toast.makeText(context, "update", Toast.LENGTH_SHORT);
        toast.show();
        //CharSequence widgetText = MyWidgetActivityConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
        views.setTextViewText(R.id.appwidget_text, widgetText);


        ComponentName watchWidget;


        watchWidget = new ComponentName(context, MyWidgetActivity.class);

        views.setOnClickPendingIntent(R.id.refresh_button, getPendingSelfIntent(context, SYNC_CLICKED));

        //appWidgetManager.updateAppWidget(watchWidget, views);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        if (SYNC_CLICKED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
            watchWidget = new ComponentName(context, MyWidgetActivity.class);

            remoteViews.setTextViewText(R.id.refresh_button, "TESTING");

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            MyWidgetActivityConfigureActivity.deleteTitlePref(context, appWidgetId);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

