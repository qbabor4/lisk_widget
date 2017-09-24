package qbabor4.pl.liskwidget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import layout.MyWidgetActivity;

/**
 * Takes String parameters, Void - doesn't return state information, String - returns String
 */
public class LiskData extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String liskData = null;
        for (String url: params){
            liskData = "lisk23";
            // zaoytanie http. pobranie jsona
            Log.d("widgetBack", liskData);
        }
        return liskData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("widget1", s);
        Context context = MyWidgetActivity.getAppContext();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
        views.setTextViewText(R.id.appwidget_text, String.valueOf(s));
        int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MyWidgetActivity.class));
        AppWidgetManager manager = AppWidgetManager.getInstance(context);

        // updates all widgets on screen
        manager.updateAppWidget(appWidgetId, views);
        // tu zmienic textView
    }
}

