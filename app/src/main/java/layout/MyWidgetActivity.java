package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import qbabor4.pl.liskwidget.LiskData;
import qbabor4.pl.liskwidget.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MyWidgetActivityConfigureActivity MyWidgetActivityConfigureActivity}
 ** TODO (Not necessary)
 * - Button updates all widgets ( when creating new intent widgetId is repacled with new widgetId and you cant get to this first id)
 **
 /* TODO
 - zmianiac co minutę kurs na widgecie
 - załozyc jakis timer zeby co 5 sec sie updatowało
 - dodac wybór czasu updatowania w konfiguracji
 - błąd jak ktos poda 0 parametrów do funkcji setLiskData()
 - wybór waluty w konfiguracji
 - jak dodaje widget na ekran to nie updatuje textviews
 - zobaczyc co sie dzieje jak sie dodaje widget na ekran
 **
 */
public class MyWidgetActivity extends AppWidgetProvider {

    private static Context context;

    // Buttons packagename and WIDGET_BUTTON
    public static final String WIDGET_BUTTON = "android.appwidget.action.WIDGET_BUTTON";


    public static Context getAppContext() {
        return MyWidgetActivity.context;
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
        createIntentForRefreshButton(context, views, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Creates intent for refresh button
     * You can get action in onReceive()
     * @param context context of widget
     * @param views view of widget
     * @param appWidgetId id of widget
     */
    private void createIntentForRefreshButton(Context context, RemoteViews views, int appWidgetId){ //TODO!: Not creating intent
        // Creating intent for button. When button is pressed you can get it in onReceive()
        Intent intent = new Intent(context, MyWidgetActivity.class);
        intent.setAction(WIDGET_BUTTON);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId); // adds id of widget to intent informations (Not used)
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.refresh_button_white, pendingIntent);
    }

    /**
     * Sets textViews with data got from json from web
     */
    public void setLiskData(Context context){
        MyWidgetActivity.context = context; // variable is becoming null in some conditions (f.e.: cleaning app from working apps)
        String liskUrl = "https://bitbay.net/API/Public/LSKPLN/ticker.json";
        String btcUrl = "https://bitbay.net/API/Public/BTCPLN/ticker.json";

        new LiskData().execute(liskUrl);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent != null) {
            // When refresh button is clicked
            if (WIDGET_BUTTON.equals(intent.getAction())) {
                setLiskData(context);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            setLiskData(context);
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

