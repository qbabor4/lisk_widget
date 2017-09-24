package qbabor4.pl.liskwidget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.Toast;


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

        setTextViewTextWidget(R.id.appwidget_text, s);

        Context context = MyWidgetActivity.getAppContext();
        Toast.makeText(context, "updating", Toast.LENGTH_SHORT).show();
    }

    /**
     * Changes TextView and updates widget
     * @param id id of TextView
     * @param text text to be set in TextView
     */
    private void setTextViewTextWidget(int id, String text){
        Context context = MyWidgetActivity.getAppContext();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
        views.setTextViewText(id, String.valueOf(text));

        int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, MyWidgetActivity.class));
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        // updates all widgets on screen
        manager.updateAppWidget(appWidgetId, views);
    }
}

