package qbabor4.pl.liskwidget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import layout.MyWidgetActivity;

/**
 * Takes String parameters, Void - doesn't return state information, String - returns String
 */
public class LiskData extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // TODO: animacja ładowania danych; stop na onPostExecute
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];

        HttpHandler httpHandler = new HttpHandler();
        String json = httpHandler.makeServiceCall(url);

        Log.d("widgetBack", url);

        return json;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //Log.d("widget1", s);
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

