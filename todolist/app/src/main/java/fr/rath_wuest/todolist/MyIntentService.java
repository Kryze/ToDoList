package fr.rath_wuest.todolist;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by Benjamin on 19/03/2016.
 */
public class MyIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        /*Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("My Titel");
        builder.setContentText("This is the Body");
        builder.setSmallIcon(R.drawable.ic_stat_interface);
        builder.setAutoCancel(true);
        builder.setLights(Color.GREEN, 500, 500);*/

        /*Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        //Notification notificationCompat = builder.build();
        //NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        //managerCompat.notify(NOTIFICATION_ID, notificationCompat);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());*/


        Intent intente = new Intent(this,MainActivity.class);
        long[] pattern = {0, 300, 0};
        PendingIntent pi = PendingIntent.getActivity(this, 01234, intente, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_interface)
                .setContentTitle("N'oubliez pas")
                .setContentText("Votre TodoList n'attend pas !")
                .setVibrate(pattern)
                .setAutoCancel(true);

        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(01234, mBuilder.build());

    }

}
