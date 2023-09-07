package com.med.meditationsoundapp.SoundService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import com.med.meditationsoundapp.SoundConstants.MedConstants;

import androidx.annotation.RequiresApi;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getStringExtra(MedConstants.NOTIFICATION_ACTION).equalsIgnoreCase("Pause")) {
//            for (int i = 0; i < MedConstants.mediaPlayerArrayList.size(); i++) {
//                if (MedConstants.mediaPlayerArrayList.get(i).getPlayer().isPlaying()) {
//                    MedConstants.mediaPlayerArrayList.get(i).getPlayer().stop();
//                }
//            }
//            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
//                if (MedConstants.SelectedPlayerArrayList.get(i).getPlayer().isPlaying()) {
//                    MedConstants.SelectedPlayerArrayList.get(i).getPlayer().stop();
//                }
//            }
//        } else if (intent.getStringExtra(MedConstants.NOTIFICATION_ACTION).equalsIgnoreCase("stop")) {
//            for (int i = 0; i < MedConstants.mediaPlayerArrayList.size(); i++) {
//                if (MedConstants.mediaPlayerArrayList.get(i).getPlayer().isPlaying()) {
//                    MedConstants.mediaPlayerArrayList.get(i).getPlayer().stop();
//                }
//            }
//            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
//                if (MedConstants.SelectedPlayerArrayList.get(i).getPlayer().isPlaying()) {
//                    MedConstants.SelectedPlayerArrayList.get(i).getPlayer().stop();
//                }
//            }
//            if (MedConstants.isServiceRunning(context, MediaPlayerService.class)) {
//                Intent serviceIntent = new Intent(context, MediaPlayerService.class);
//                context.stopService(serviceIntent);
//            }
//        } else {
//            new PlayPauseAsynkTask(context).execute();
//        }
//        if (MedConstants.isServiceRunning(context, MediaPlayerService.class)) {
//            Intent serviceIntent = new Intent(context, MediaPlayerService.class);
//            context.stopService(serviceIntent);
//        }
//        Intent serviceIntent = new Intent(context, MediaPlayerService.class);
//        serviceIntent.putExtra(MedConstants.IsNotificationFavoriteTitle, MedConstants.FAVOURITESONG);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(serviceIntent);
//        } else {
//            context.startService(serviceIntent);
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class PlayPauseAsynkTask extends AsyncTask<Void, Void, Void> {
        private final Context context;

        public PlayPauseAsynkTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (MedConstants.SelectedPlayerArrayList.size() > 0) {
                for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                    MedConstants.SelectedPlayerArrayList.get(i).getPlayer().prepareAsync();
                }
            } else {
                Toast.makeText(context, "No Sounds play now.", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                int finalI = i;
                MedConstants.SelectedPlayerArrayList.get(i).getPlayer().setOnPreparedListener(mp ->
                        MedConstants.SelectedPlayerArrayList.get(finalI).getPlayer().start());
                MedConstants.SelectedPlayerArrayList.get(i).getPlayer().setOnCompletionListener(mp ->
                        MedConstants.SelectedPlayerArrayList.get(finalI).getPlayer().start());
            }
            super.onPostExecute(unused);
        }
    }
}
