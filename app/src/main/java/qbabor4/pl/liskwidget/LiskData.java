package qbabor4.pl.liskwidget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
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
        Context context = MyWidgetActivity.getAppContext();
        if (s != null) {
            JSONObject jsonObject;
            String lastPrice = null;
            String minPrice = null;
            String maxPrice = null;

            try {
                jsonObject = new JSONObject(s);
                lastPrice = jsonObject.getString("last");
                lastPrice = addZeros(lastPrice);
                //Toast.makeText(context, String.valueOf(lastPrice.length()), Toast.LENGTH_SHORT).show();
                minPrice = jsonObject.getString("min");
                minPrice = addZeros(minPrice);
                maxPrice = jsonObject.getString("max");
                maxPrice = addZeros(maxPrice);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);

            views.setTextViewText(R.id.last_price, lastPrice + "zł");
            views.setTextViewText(R.id.min_price, "Min " + minPrice);
            views.setTextViewText(R.id.max_price, "Max " + maxPrice);

            int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MyWidgetActivity.class));
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            // updates all widgets on screen
            manager.updateAppWidget(appWidgetId, views);

            Toast.makeText(context, "updating", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private static String addZeros(String price){
        int dot = price.indexOf(".");
        Log.d("dot", String.valueOf(dot));
        if (price.length() != dot +3){
            price += "0";
        } else if (dot == -1){
            price += ".00";
        }
        
        return price;
        // zobaczy na którym miejscu jest kropka
    }

}

