package qbabor4.pl.liskwidget;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Takes String parameters, Void - doesnt return state information, String - returns this
 */
public class LiskData extends AsyncTask<String, Void, String> {
    String url = "https://bitbay.net/API/Public/" + "LSK" + "PLN" + "/" + "ticker" + ".json";

    @Override
    protected String doInBackground(String... params) {
        String jsonResult = null;
        try {
            for (String url : params) {
                jsonResult = getJsonFromServer(url);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return jsonResult;
    }

    private static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(5000);
        dc.setReadTimeout(5000);

        inputStream = new BufferedReader(new InputStreamReader(dc.getInputStream()));

        // read the JSON results into a string
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }


}
