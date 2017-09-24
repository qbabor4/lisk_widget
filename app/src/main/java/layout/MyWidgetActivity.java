package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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
 - dostac kurs liska na pln
 - zmianiac co minutę kurs na widgecie
 - dać alarm zamiast updatowania w jakimś okresie
 - wywalić konfigurację (albo do niej cos dopisac)
 - guzik z refresh, żeby updatowac ręcznie kurs
 - załozyc jakis timer zeby co 5 sec sie updatowało
 - dodac wybór czasu updatowania w konfiguracji
 - asynchronicznie pobierac dane jak trzeba bedzie
 - zwracac jsona (przerobic funkcje
 - dodadc do LiskData funkcje zwracajace poszczególne dane w Stringu
 - zmiana tylko na jednym widgecie
 - zmienic layout
 - dodac ikonkę refresh na guziku
 - sprawdzic czy jest sieć
 **
 */
public class MyWidgetActivity extends AppWidgetProvider {

    // number to increment and show in textView
    private static int num1 = 0;
    // Buttons packagename and WIDGET_BUTTON
    public static final String WIDGET_BUTTON = "android.appwidget.action.WIDGET_BUTTON";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);

        createIntentForRefreshButton(context, views, appWidgetId);

        // sets text on textView
        String widgetText = String.valueOf(num1);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        //Updates widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Creates intent for refresh button
     * You can get action in onReceive()
     * @param context
     * @param views
     * @param appWidgetId
     */
    private void createIntentForRefreshButton(Context context, RemoteViews views, int appWidgetId){
        // Creating intent for button. When button is pressed you can get it in onReceive()
        Intent intent = new Intent(context, MyWidgetActivity.class);
        intent.setAction(WIDGET_BUTTON);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId); // adds id of widget to intent informations (Not used)
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.refresh_button, pendingIntent);
    }

    public void setLiskData(){
        // TODO! napisac klasę pobierającą dane z adresu url
        String jsonUrl = "https://bitbay.net/API/Public/" + "LSK" + "PLN" + "/" + "ticker" + ".json";

        new LiskData().execute(jsonUrl);
        // w ayncTasc zmienic tekst na widgecie

         //TODO: sprawdzic czy działa; Jak get() to moze nie byc w tle, tylko bedzie czekał i nie bedzie asynchronicznie.
        // TODO: mozna od razu nadpisac textview w asynchronicznym (moze byc kurwa ciezko z tym updatowaniem widgeta)
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent != null) {
            // When refresh button is clicked
            if (WIDGET_BUTTON.equals(intent.getAction())) {

                num1 += 1;
                setLiskData();
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
                // sets text on textView
                views.setTextViewText(R.id.appwidget_text, String.valueOf(num1));
                //Toast.makeText(context, jsonLisk, Toast.LENGTH_LONG).show();
                int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MyWidgetActivity.class));
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                // updates all widgets on screen
                manager.updateAppWidget(appWidgetId, views); // idd
            }
        }
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

