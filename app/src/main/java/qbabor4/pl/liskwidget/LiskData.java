package qbabor4.pl.liskwidget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import layout.MyWidgetActivity;

/**
 * Takes String parameters, Void - doesn't return state information, String - returns String
 */
public class LiskData extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // TODO: animacja ładowania danych; stop na onPostExecute
        //<uses-permission android:name="android.permission.INTERNET" />
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        HttpHandler httpHandler = new HttpHandler();
        String json = httpHandler.makeServiceCall(url);

        return json;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        JSONObject jsonObject = new JSONObject();
        String lastPrice = null;
        String minPrice = null;
        String maxPrice = null;

        try {
            jsonObject = new JSONObject(s);
            lastPrice = jsonObject.getString("last");
            minPrice = jsonObject.getString("min");
            maxPrice = jsonObject.getString("max");
        } catch (JSONException e){
            e.printStackTrace();
        }

        Context context = MyWidgetActivity.getAppContext();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);

        views.setTextViewText(R.id.last_price, lastPrice);
        views.setTextViewText(R.id.min_price, "Min " + minPrice);
        views.setTextViewText(R.id.max_price, "Max " +maxPrice);

        int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MyWidgetActivity.class));
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        // updates all widgets on screen
        manager.updateAppWidget(appWidgetId, views);

        Toast.makeText(context, "updating", Toast.LENGTH_SHORT).show();
    }

}

