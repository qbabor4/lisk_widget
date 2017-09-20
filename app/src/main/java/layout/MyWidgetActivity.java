package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


import qbabor4.pl.liskwidget.LiskData;
import qbabor4.pl.liskwidget.R;

import static android.R.attr.action;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MyWidgetActivityConfigureActivity MyWidgetActivityConfigureActivity}
 */
public class MyWidgetActivity extends AppWidgetProvider {
    /**
     * główne błędy:
     * updatuje ostatnio dodany widget a nie ten na którym sie klika
     * ? dodac do nazwy intenta id widgeta i tak sprawdzac w onReceive
     * id w intence jest takie jak ostatniego widgeta ( nie ma 2 osobnych intentow tylko jeden)
     */

    private static int num1 = 0;

    // Buttons packagename and WIDGET_BUTTON
    public static final String WIDGET_BUTTON = "android.appwidget.action.WIDGET_BUTTON";

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
            /* TODO
             - dostac kurs liska na pln
             - zmianiac co minutę kurs na widgecie
             - dać alarm zamiast updatowania w jakimś okresie
             - wywalić konfigurację
             - guzik z refresh, żeby updatowac ręcznie kurs
             - załozyc jakis timer zeby co 5 sec sie updatowało
             - dodac wybór czasu updatowania w konfiguracji
             - asynchronicznie pobierac dane jak trzeba bedzie
             - zwracac jsona (przerobic funkcje
             - dodadc do LiskData funkcje zwracajace poszczególne dane w Stringu
              */
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);

        // Creating intent for button. When button is pressed you can get it in onReceive()
        //Intent intent = new Intent(WIDGET_BUTTON);
        Intent intent = new Intent(context, MyWidgetActivity.class);
        intent.setAction(WIDGET_BUTTON);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId); // adds id of widget to intent informations
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT); // update
        views.setOnClickPendingIntent(R.id.refresh_button, pendingIntent);

        //Toast toast = Toast.makeText(context, "update", Toast.LENGTH_SHORT);
        //toast.show();

        Toast toast2 = Toast.makeText(context, String.valueOf(appWidgetId), Toast.LENGTH_SHORT);
        toast2.show();

        String liskData = getLiskData();

        num1 += 1;
        String widgetText = String.valueOf(num1);
        // Changes text of widget
        views.setTextViewText(R.id.appwidget_text, widgetText);

        //Updates widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    public String getLiskData(){
        //LiskData liskData = new LiskData();
        //String liskString = liskData.doInBackground("https://bitbay.net/API/Public/" + "LSK" + "PLN" + "/" + "ticker" + ".json");
        String liskString = "lol";
        num1 += 1;
        return liskString;

        // TODO! napisac klasę pobierającą dane z adresu url
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent != null) {
            // Runs when refresh button is clicked
            if (WIDGET_BUTTON.equals(intent.getAction())) {
                //Do when button is clicked

                int idd = 0;
                Bundle extras = intent.getExtras();

                if(extras != null) {
                    idd = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
                    Toast toast2 = Toast.makeText(context, String.valueOf(idd), Toast.LENGTH_SHORT);
                    toast2.show();
                }
                SharedPreferences prefs = context.getSharedPreferences("prefs", 0);
                int id = prefs.getInt("id:"+idd, 0);

                num1 += 1;
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
                views.setTextViewText(R.id.appwidget_text, String.valueOf(num1) + id);
                ComponentName componentName = new ComponentName(context, MyWidgetActivity.class);

                //int [] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(componentName);
                views.setTextViewText(R.id.appwidget_text, String.valueOf(num1));
                //Log.d("ids", String.valueOf(ids));
                //int widgetId = Integer.parseInt(intent.getAction().substring(WIDGET_BUTTON.length()));
                int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(
                        new ComponentName(context, MyWidgetActivity.class)); // updatuje wsztstkie widgety jak sie klika button

                // dostac id od widgeta na którym byl klikniety guzik. ...albo olać to
                Toast toast1 = Toast.makeText(context, "refreshing" + idd, Toast.LENGTH_SHORT);
                toast1.show();
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                manager.updateAppWidget(appWidgetId, views); // idd
                //TODO: Zmianiac tylko na jednym widgecie a nie na wszystkich
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
        //this.appWidgetManager = appWidgetManager;
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

