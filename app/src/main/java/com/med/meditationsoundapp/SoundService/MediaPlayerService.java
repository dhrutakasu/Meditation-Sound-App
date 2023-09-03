package com.med.meditationsoundapp.SoundService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;

public class MediaPlayerService extends Service {
    public MediaPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
// Create an intent for the notification action
        Intent intent = new Intent(this, MedMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// Create the custom layout
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
        remoteViews.setImageViewResource(R.id.notification_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notification_title, getApplicationContext().getString(R.string.app_name));
        if (MedConstants.IsNotificationFavorite) {
            remoteViews.setTextViewText(R.id.notification_sub_title, MedConstants.IsNotificationFavoriteTitle);
        } else {
            remoteViews.setTextViewText(R.id.notification_sub_title, "Tap to open again");
        }

// Create the notification builder with custom layout
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews); // Set the custom layout

// Build the notification
        Notification notification = builder.build();

// Show the notification
        NotificationManager notificationManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = getSystemService(NotificationManager.class);
        }
        notificationManager.notify(1, notification); // Use a unique ID for notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            String channelName = "Channel Name";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                notificationManager = getSystemService(NotificationManager.class);
            }
            notificationManager.notify(1, notification);
        }

    }
}