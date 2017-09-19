package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;


import qbabor4.pl.liskwidget.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MyWidgetActivityConfigureActivity MyWidgetActivityConfigureActivity}
 */
public class MyWidgetActivity extends AppWidgetProvider {

    private static int num1 = 0;

    // Buttons packagename and WIDGET_BUTTON
    public static String WIDGET_BUTTON = "android.appwidget.action.WIDGET_BUTTON";

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
              */
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);

        // Listener for refresh_button
        Intent intent = new Intent(WIDGET_BUTTON);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.refresh_button, pendingIntent);

        Toast toast = Toast.makeText(context, "update", Toast.LENGTH_SHORT);
        toast.show();

        num1 += 1;
        String widgetText = String.valueOf(num1);
        // Changes text of widget
        views.setTextViewText(R.id.appwidget_text, widgetText);

        //Updates widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        // Runs when refresh button is clicked
        if (WIDGET_BUTTON.equals(intent.getAction())){
            //Do when button is clicked
            Toast toast1 = Toast.makeText(context, "refreshing", Toast.LENGTH_SHORT);
            toast1.show();
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

