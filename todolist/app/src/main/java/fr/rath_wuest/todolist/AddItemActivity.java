package fr.rath_wuest.todolist;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddItemActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        tp.setIs24HourView(true);
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        dp.setMinDate(System.currentTimeMillis() - 1000);






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    public void addItem(View view){
        EditText itemForm = ((EditText) findViewById(R.id.itemLabel));
        DatePicker dp= ((DatePicker) findViewById(R.id.datePicker));
        TimePicker tp= ((TimePicker)findViewById(R.id.timePicker));
        Item item = new Item(itemForm.getText().toString(),getDateFromDatePicker(dp, tp));
        item.save(this);



       /* Calendar now = Calendar.getInstance();
        int day = dp.getDayOfMonth();
        int month = dp.getMonth();
        int year = dp.getYear();
        int hours = tp.getCurrentHour();
        int minutes = tp.getCurrentMinute();
        now.set(year, month, day,hours , minutes+2);
            Intent notifyIntent = new Intent(this, Receiver.class);
            //notifyIntent.putExtra("title",item.getText());
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (this, 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC,now.getTimeInMillis(), pendingIntent);*/



        Intent intent = new Intent(this,MainActivity.class);
        long[] pattern = {0, 300, 0};
        PendingIntent pi = PendingIntent.getActivity(this, 01234, intent, 0);
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

        setResult(RESULT_OK);
        finish();
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker,TimePicker timePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        int hours = timePicker.getCurrentHour();
        int minutes = timePicker.getCurrentMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hours, minutes);
        return calendar.getTime();
    }
}
