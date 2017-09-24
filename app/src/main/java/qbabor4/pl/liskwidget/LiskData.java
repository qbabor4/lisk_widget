package qbabor4.pl.liskwidget;


import android.os.AsyncTask;
import android.util.Log;

/**
 * Takes String parameters, Void - doesn't return state information, String - returns String
 */
public class LiskData extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String liskData = null;
        for (String url: params){
            liskData = "lisk23";
            Log.d("widgetBack", liskData);
        }
        return liskData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("widget1", s);
        // tu zmienic textView
    }
}

