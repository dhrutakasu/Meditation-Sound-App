package com.med.meditationsoundapp.SoundService;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.O;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;

public class MediaPlayerService extends Service {
    private String Titles = "";
    private static String CHANNEL_ID = "alarm_channel";
    private Context context;
    private String IconAction;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;

    public MediaPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("============= : " + intent.getStringExtra(MedConstants.IsNotificationFavoriteTitle));
        Titles = intent.getStringExtra(MedConstants.IsNotificationFavoriteTitle);
//        IconAction = intent.getStringExtra(MedConstants.NOTIFICATION_PLAYPAUSE_ICON);
        IconAction = MedConstants.NOTIFICATION_PLAYPAUSE_ICON;
        context = this;
        if (SDK_INT > O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @RequiresApi(O)
    private void startMyOwnForeground() {
        createNotificationChannel(context);


        if (SDK_INT >= O) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
        remoteViews.setImageViewResource(R.id.notification_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notification_title, getApplicationContext().getString(R.string.app_name));
        if (!Titles.equalsIgnoreCase("")) {
            remoteViews.setTextViewText(R.id.notification_sub_title, Titles);
        } else {
            remoteViews.setTextViewText(R.id.notification_sub_title, "Tap to open again");
        }
        Intent contentIntent = new Intent(context, MedMainActivity.class);
        PendingIntent contentPendingIntent;
        if (SDK_INT >= Build.VERSION_CODES.S) {
            contentPendingIntent = PendingIntent.getBroadcast(context, 100, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            contentPendingIntent = PendingIntent.getBroadcast(context, 100, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        Intent PlayPauseIntent = new Intent(context, NotificationReceiver.class);
        PlayPauseIntent.putExtra(MedConstants.IsNotificationFavoriteTitle, Titles);
        if (IconAction.equalsIgnoreCase("Pause")) {
            PlayPauseIntent.putExtra(MedConstants.NOTIFICATION_ACTION, "Pause");
            remoteViews.setImageViewResource(R.id.notification_PlayPause, R.drawable.ic_pause);
        } else {
            PlayPauseIntent.putExtra(MedConstants.NOTIFICATION_ACTION, "Play");
            remoteViews.setImageViewResource(R.id.notification_PlayPause, R.drawable.ic_play);
        }
        PendingIntent PlayPausePendingIntent;
        if (SDK_INT >= Build.VERSION_CODES.S) {
            PlayPausePendingIntent = PendingIntent.getBroadcast(context, 101, PlayPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            PlayPausePendingIntent = PendingIntent.getBroadcast(context, 101, PlayPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteViews.setOnClickPendingIntent(R.id.notification_PlayPause, PlayPausePendingIntent);

        Intent StopIntent = new Intent(context, NotificationReceiver.class);
        PlayPauseIntent.putExtra(MedConstants.IsNotificationFavoriteTitle, Titles);
        StopIntent.putExtra(MedConstants.NOTIFICATION_ACTION, "stop");
        PendingIntent StopPendingIntent;
        if (SDK_INT >= Build.VERSION_CODES.S) {
            StopPendingIntent = PendingIntent.getBroadcast(context, 102, StopIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            StopPendingIntent = PendingIntent.getBroadcast(context, 102, StopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        remoteViews.setOnClickPendingIntent(R.id.notification_Stop, StopPendingIntent);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setCustomBigContentView(remoteViews);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(contentPendingIntent);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setVibrate(new long[]{1000, 500, 1000, 500, 1000, 500});
        builder.setSilent(true);
        mNotificationManager.notify(1000, builder.build());

    }

    private static void createNotificationChannel(Context ctx) {
        if (SDK_INT < O) return;

        final NotificationManager mgr = ctx.getSystemService(NotificationManager.class);
        if (mgr == null) return;

        final String name = "Reminder Notification";
        if (mgr.getNotificationChannel(name) == null) {
            final NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 500, 1000, 500, 1000, 500});
            channel.setBypassDnd(true);
            mgr.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("==== = = = =destrouy ");
         mNotificationManager.cancelAll();
        stopForeground(true);
        stopSelf();
    }
}