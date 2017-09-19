package qbabor4.pl.liskwidget;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import layout.MyWidgetActivity;


/**
 * Created by Jakub on 19-Sep-17.
 */

public class LiskData extends AsyncTask<String, String, String> {

    //ProgressDialog pd;
    String txtJson;

    /*protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(R.layout.my_widget_activity);
        pd.setMessage("Please wait");
        pd.setCancelable(false);
        pd.show();
    } */

    protected String doInBackground(String... params){
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        /*if (pd.isShowing()){
            pd.dismiss();
        } */
        txtJson = result;
    }
}

