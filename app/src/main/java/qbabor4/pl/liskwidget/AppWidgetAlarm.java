package qbabor4.pl.liskwidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import layout.MyWidgetActivity;

/**
 * Created by Jakub on 03-Oct-17.
 */
public class AppWidgetAlarm
{
    private final int ALARM_ID = 0;
    private final int INTERVAL_MILLIS = 1000*5;
    public static final String ACTION_AUTO_UPDATE = "android.appwidget.action.ACTION_AUTO_UPDATE"; //TODO to usunac (ma byc samo w manifescie

    private Context mContext;


    public AppWidgetAlarm(Context context)
    {
        mContext = context;
    }


    public void startAlarm()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, INTERVAL_MILLIS);

        Intent alarmIntent = new Intent(ACTION_AUTO_UPDATE); // TODO czemu nie czyta jak sie da MyWidgetActivity.ACTION_AUTO_UPDATE (Dobrze dac w manifecie)
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        // RTC does not wake the device up
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), INTERVAL_MILLIS, pendingIntent);
    }


    public void stopAlarm()
    {
        Intent alarmIntent = new Intent(ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}