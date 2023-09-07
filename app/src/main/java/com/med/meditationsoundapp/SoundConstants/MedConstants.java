package com.med.meditationsoundapp.SoundConstants;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.karumi.dexter.PermissionToken;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAppcation.MedApp;
import com.med.meditationsoundapp.SoundModel.PlyerModel;
import com.med.meditationsoundapp.SoundModel.SoundModel;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;

public class MedConstants {


    public static final String BROADCAST_MAIN = "BROADCAST_MAIN";
    public static final String BROADCAST_THEME = "BROADCAST_THEME";
    public static final String BROADCAST_SETTING = "BROADCAST_SETTING";
    public static final String BROADCAST_VOLUME = "BROADCAST_VOLUME";
    public static final String BROADCAST_FRAGMENT = "BROADCAST_FRAGMENT";
    public static final String FRAGMENT_CLICK = "FRAGMENT_CLICK";
    public static final String SERVICE_TITLE = "SERVICE_TITLE";
    public static final String NOTIFICATION_ACTION = "NOTIFICATION_ACTION";
    public static String NOTIFICATION_PLAYPAUSE_ICON = "NOTIFICATION_PLAYPAUSE_ICON";
    public static String FAVOURITESONG = "";
    public static String SelectedSounds = "SelectedSounds";
    public static String IsNotificationFavoriteTitle = "";
    private static String BASEURL = "https://anshinfotech.in/sound/";
    private static String BASEURLEXTENSION = ".mp3";
    public static ArrayList<PlyerModel> mediaPlayerArrayList = new ArrayList<>();
    public static ArrayList<PlyerModel> SelectedPlayerArrayList = new ArrayList<>();

    public static boolean isConnectingToInternet(Context con) {
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo state : info) {
                    if (state.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void showSettingsDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle((CharSequence) "Need Permissions");
        builder.setMessage((CharSequence) "This app needs permissions to use this feature. You can grant them in app settings.");
        builder.setPositiveButton((CharSequence) "GOTO SETTINGS", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings(activity);
            }
        });
        builder.setNegativeButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showPermissionDialog(final Activity activity, final PermissionToken permissionToken) {
        new AlertDialog.Builder(activity
        ).setMessage((int) R.string.MSG_ASK_PERMISSION).setNegativeButton("Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permissionToken.cancelPermissionRequest();
            }
        }).setPositiveButton("Ok", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permissionToken.continuePermissionRequest();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                permissionToken.cancelPermissionRequest();
            }
        }).show();
    }

    private static void openSettings(Activity activity) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), (String) null));
        activity.startActivityForResult(intent, 101);
    }

    public static int SoundDefaultVolume() {
        int Volume = 50;
        int pref = new MedPref(MedApp.getInstance()).getInt(MedPref.INT_DEAFULT_VOLUME, 1);
        switch (pref) {
            case 0:
                Volume = 25;
                break;
            case 1:
                Volume = 50;
                break;
            case 2:
                Volume = 75;
                break;
            case 3:
                Volume = 100;
                break;
        }
        return Volume;
    }

    public static ArrayList<SoundModel> getSoundDefault(Context context) {
        ArrayList<SoundModel> audioLists = new ArrayList<>();
        String[] strings = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.AudioColumns.DATA};
        Cursor cursor = MedApp.getInstance().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, strings, null, null, null);
        int pos = 122;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String path = cursor.getString(2);
                    String name = cursor.getString(1);
                    SoundModel audioModel = new SoundModel(0, path, name, 0, pos++, MedConstants.SoundDefaultVolume());

                    audioLists.add(audioModel);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();

        for (int i = 0; i < audioLists.size(); i++) {
            Log.e(" ", "******Audio Path ---->>>  " + audioLists.get(i).toString());
        }
        return audioLists;
    }

    public static ArrayList<SoundModel> RainSounds(Context context) {
        ArrayList<SoundModel> rainSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_rain_dark, BASEURL + "med_rain" + BASEURLEXTENSION, "Rain", 0, 0, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_rain_car_dark, BASEURL + "med_rain_on_car" + BASEURLEXTENSION, "Rain On Car", 0, 1, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_rain_umbrella_dark, BASEURL + "med_rain_on_umbrella_sound" + BASEURLEXTENSION, "Umbrella", 0, 2, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_thunder_dark, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_light_rain_dark, BASEURL + "med_light_rain" + BASEURLEXTENSION, "Light Rain", 0, 4, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_heavy_rain_dark, BASEURL + "med_heavy_rain" + BASEURLEXTENSION, "Heavy Rain", 0, 5, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_distant_thunder_dark, BASEURL + "med_distant_thunder" + BASEURLEXTENSION, "Distant Thunder", 0, 6, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_tent_dark, BASEURL + "med_rain_on_tent_sound" + BASEURLEXTENSION, "Tent", 0, 7, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_window_dark, BASEURL + "med_rain_on_window_sound" + BASEURLEXTENSION, "Rain On Window", 0, 8, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_roof_dark, BASEURL + "med_rain_roof" + BASEURLEXTENSION, "Roof", 0, 9, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_rain_plant_dark, BASEURL + "med_rain_on_metaplates" + BASEURLEXTENSION, "Metaplants", 0, 10, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_rain_leaves_dark, BASEURL + "med_rain_leaves" + BASEURLEXTENSION, "Rain Leaves", 0, 11, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_rain, BASEURL + "med_rain" + BASEURLEXTENSION, "Rain", 0, 0, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_rain_car, BASEURL + "med_rain_on_car" + BASEURLEXTENSION, "Rain On Car", 0, 1, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_rain_umbrella, BASEURL + "med_rain_on_umbrella_sound" + BASEURLEXTENSION, "Umbrella", 0, 2, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_thunder, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_light_rain, BASEURL + "med_light_rain" + BASEURLEXTENSION, "Light Rain", 0, 4, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_heavy_rain, BASEURL + "med_heavy_rain" + BASEURLEXTENSION, "Heavy Rain", 0, 5, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_distant_thunder, BASEURL + "med_distant_thunder" + BASEURLEXTENSION, "Distant Thunder", 0, 6, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_tent, BASEURL + "med_rain_on_tent_sound" + BASEURLEXTENSION, "Tent", 0, 7, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_window, BASEURL + "med_rain_on_window_sound" + BASEURLEXTENSION, "Rain On Window", 0, 8, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_roof, BASEURL + "med_rain_roof" + BASEURLEXTENSION, "Roof", 0, 9, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_rain_plant, BASEURL + "med_rain_on_metaplates" + BASEURLEXTENSION, "Metaplants", 0, 10, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_rain_leaves, BASEURL + "med_rain_leaves" + BASEURLEXTENSION, "Rain Leaves", 0, 11, SoundDefaultVolume());
            rainSoundsList.add(soundModel);
        }
        return rainSoundsList;
    }

    public static ArrayList<SoundModel> CountrySounds(Context context) {
        ArrayList<SoundModel> countrySoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_country_blackbirds_dark, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_birds2_dark, BASEURL + "med_birds2" + BASEURLEXTENSION, "Birds", 0, 13, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_birds3_dark, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_cat_purring_dark, BASEURL + "med_cat_purring" + BASEURLEXTENSION, "Cat Purring", 0, 15, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_cat_dark, BASEURL + "med_cat" + BASEURLEXTENSION, "Cat", 0, 16, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_cats_dark, BASEURL + "med_cat2" + BASEURLEXTENSION, "Cats", 0, 17, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_dogs_dark, BASEURL + "med_dogs" + BASEURLEXTENSION, "Dogs", 0, 18, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_seagulls_dark, BASEURL + "med_seagulls" + BASEURLEXTENSION, "Seagulls", 0, 19, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_ducks_dark, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_chicken_dark, BASEURL + "med_chicken_coop" + BASEURLEXTENSION, "Chicken", 0, 21, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_cicadas_dark, BASEURL + "med_cicada_chirping" + BASEURLEXTENSION, "Cicada", 0, 22, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_ocean_whale_dark, BASEURL + "med_whale_sound" + BASEURLEXTENSION, "Whale", 0, 23, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_elephant_dark, BASEURL + "med_elephant" + BASEURLEXTENSION, "Elephant", 0, 24, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_birds2, BASEURL + "med_birds2" + BASEURLEXTENSION, "Birds", 0, 13, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_birds3, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_cat_purring, BASEURL + "med_cat_purring" + BASEURLEXTENSION, "Cat Purring", 0, 15, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_cat, BASEURL + "med_cat" + BASEURLEXTENSION, "Cat", 0, 16, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_cats, BASEURL + "med_cat2" + BASEURLEXTENSION, "Cats", 0, 17, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_dogs, BASEURL + "med_dogs" + BASEURLEXTENSION, "Dogs", 0, 18, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_seagulls, BASEURL + "med_seagulls" + BASEURLEXTENSION, "Seagulls", 0, 19, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_ducks, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_chicken, BASEURL + "med_chicken_coop" + BASEURLEXTENSION, "Chicken", 0, 21, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_cicadas, BASEURL + "med_cicada_chirping" + BASEURLEXTENSION, "Cicada", 0, 22, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_ocean_whale, BASEURL + "med_whale_sound" + BASEURLEXTENSION, "Whale", 0, 23, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_elephant, BASEURL + "med_elephant" + BASEURLEXTENSION, "Elephant", 0, 24, SoundDefaultVolume());
            countrySoundsList.add(soundModel);
        }
        return countrySoundsList;
    }

    public static ArrayList<SoundModel> NatureSounds(Context context) {
        ArrayList<SoundModel> natureSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_nature_snow_falling_dark, BASEURL + "med_snow_falling_sound" + BASEURLEXTENSION, "Snow Falling", 0, 25, SoundDefaultVolume());
            natureSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_nature_snow_walking_dark, BASEURL + "med_snow_walking" + BASEURLEXTENSION, "Snow Walking", 0, 26, SoundDefaultVolume());
            natureSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_nature_strom_dark, BASEURL + "med_storm" + BASEURLEXTENSION, "Storm", 0, 27, SoundDefaultVolume());
            natureSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_nature_snow_falling, BASEURL + "med_snow_falling_sound" + BASEURLEXTENSION, "Snow Falling", 0, 25, SoundDefaultVolume());
            natureSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_nature_snow_walking, BASEURL + "med_snow_walking" + BASEURLEXTENSION, "Snow Walking", 0, 26, SoundDefaultVolume());
            natureSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_nature_strom, BASEURL + "med_storm" + BASEURLEXTENSION, "Storm", 0, 27, SoundDefaultVolume());
            natureSoundsList.add(soundModel);
        }
        return natureSoundsList;
    }

    public static ArrayList<SoundModel> WaterSounds(Context context) {
        ArrayList<SoundModel> waterSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_underwater_dark, BASEURL + "med_underwater" + BASEURLEXTENSION, "UnderWater", 0, 28, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_river_dark, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_bubble_dark, BASEURL + "med_bubble" + BASEURLEXTENSION, "Bubble", 0, 30, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_ocean_wave_dark, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_seagull_dark, BASEURL + "med_seagull_sound" + BASEURLEXTENSION, "Seagull", 0, 32, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_creek_dark, BASEURL + "med_creek" + BASEURLEXTENSION, "Creek", 0, 33, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_waterfall_dark, BASEURL + "med_waterfall_sound" + BASEURLEXTENSION, "Waterfall", 0, 34, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_drops_dark, BASEURL + "med_dripping_water_sound" + BASEURLEXTENSION, "Dripping", 0, 35, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_sea_dark, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_underwater, BASEURL + "med_underwater" + BASEURLEXTENSION, "UnderWater", 0, 28, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_bubble, BASEURL + "med_bubble" + BASEURLEXTENSION, "Bubble", 0, 30, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_seagull, BASEURL + "med_seagull_sound" + BASEURLEXTENSION, "Seagull", 0, 32, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_creek, BASEURL + "med_creek" + BASEURLEXTENSION, "Creek", 0, 33, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_waterfall, BASEURL + "med_waterfall_sound" + BASEURLEXTENSION, "Waterfall", 0, 34, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_drops, BASEURL + "med_dripping_water_sound" + BASEURLEXTENSION, "Dripping", 0, 35, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_sea, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, SoundDefaultVolume());
            waterSoundsList.add(soundModel);
        }
        return waterSoundsList;
    }

    public static ArrayList<SoundModel> NightSounds(Context context) {
        ArrayList<SoundModel> nightSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_night_crickets_dark, BASEURL + "med_cricket_chirping" + BASEURLEXTENSION, "Crickets", 0, 37, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_owls_dark, BASEURL + "med_owl_hooting" + BASEURLEXTENSION, "Owl", 0, 38, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_wolves_dark, BASEURL + "med_wolf_howling" + BASEURLEXTENSION, "Wolf", 0, 39, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_frogs_dark, BASEURL + "med_frog_croaking" + BASEURLEXTENSION, "Frog", 0, 40, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_whippoorwill_dark, BASEURL + "med_birds3" + BASEURLEXTENSION, "Birds", 0, 41, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_forest_night_dark, BASEURL + "med_forest_nightfall" + BASEURLEXTENSION, "Forest", 0, 42, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_goodnight_dark, BASEURL + "med_goodnight" + BASEURLEXTENSION, "Good Night", 0, 43, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_grasswalk_dark, BASEURL + "med_grassland_ambience" + BASEURLEXTENSION, "Grassland", 0, 44, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_night_crickets, BASEURL + "med_cricket_chirping" + BASEURLEXTENSION, "Crickets", 0, 37, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_owls, BASEURL + "med_owl_hooting" + BASEURLEXTENSION, "Owl", 0, 38, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_wolves, BASEURL + "med_wolf_howling" + BASEURLEXTENSION, "Wolf", 0, 39, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_frogs, BASEURL + "med_frog_croaking" + BASEURLEXTENSION, "Frog", 0, 40, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, "Birds", 0, 41, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_forest_night, BASEURL + "med_forest_nightfall" + BASEURLEXTENSION, "Forest", 0, 42, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_goodnight, BASEURL + "med_goodnight" + BASEURLEXTENSION, "Good Night", 0, 43, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_grasswalk, BASEURL + "med_grassland_ambience" + BASEURLEXTENSION, "Grassland", 0, 44, SoundDefaultVolume());
            nightSoundsList.add(soundModel);
        }
        return nightSoundsList;
    }

    public static ArrayList<SoundModel> WindSounds(Context context) {
        ArrayList<SoundModel> windSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_wind_light_dark, BASEURL + "med_air_light_wind" + BASEURLEXTENSION, "Light Wind", 0, 45, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_breeze_dark, BASEURL + "med_breeze" + BASEURLEXTENSION, "Breeze", 0, 46, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_gale_dark, BASEURL + "med_gale" + BASEURLEXTENSION, "Gale", 0, 47, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_strong_dark, BASEURL + "med_air_strong" + BASEURLEXTENSION, "Strong", 0, 48, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_howling_dark, BASEURL + "med_wind_howling" + BASEURLEXTENSION, "Howling", 0, 49, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_polar_dark, BASEURL + "med_wind_polar" + BASEURLEXTENSION, "Polar", 0, 50, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_sound_dark, BASEURL + "med_wind_sound" + BASEURLEXTENSION, "Wind", 0, 51, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_mountain_dark, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_under_door_dark, BASEURL + "med_air_door" + BASEURLEXTENSION, "Door", 0, 53, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_trees_dark, BASEURL + "med_air_tree" + BASEURLEXTENSION, "Tree", 0, 54, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_bamboo_dark, BASEURL + "med_air_bamboo" + BASEURLEXTENSION, "Bamboo", 0, 55, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_dunes_dark, BASEURL + "med_air_dune" + BASEURLEXTENSION, "Dune", 0, 56, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_dessert_dark, BASEURL + "med_air_desert" + BASEURLEXTENSION, "Dessert", 0, 57, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_flag_dark, BASEURL + "med_air_flag" + BASEURLEXTENSION, "Flag", 0, 58, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_airplane_dark, BASEURL + "med_airplane" + BASEURLEXTENSION, "Airplane", 0, 59, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_campfire_dark, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, SoundDefaultVolume());
            windSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_wind_light, BASEURL + "med_air_light_wind" + BASEURLEXTENSION, "Light Wind", 0, 45, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_breeze, BASEURL + "med_breeze" + BASEURLEXTENSION, "Breeze", 0, 46, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_gale, BASEURL + "med_gale" + BASEURLEXTENSION, "Gale", 0, 47, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_strong, BASEURL + "med_air_strong" + BASEURLEXTENSION, "Strong", 0, 48, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_howling, BASEURL + "med_wind_howling" + BASEURLEXTENSION, "Howling", 0, 49, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_polar, BASEURL + "med_wind_polar" + BASEURLEXTENSION, "Polar", 0, 50, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_sound, BASEURL + "med_wind_sound" + BASEURLEXTENSION, "Wind", 0, 51, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_mountain, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_under_door, BASEURL + "med_air_door" + BASEURLEXTENSION, "Door", 0, 53, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_trees, BASEURL + "med_air_tree" + BASEURLEXTENSION, "Tree", 0, 54, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_bamboo, BASEURL + "med_air_bamboo" + BASEURLEXTENSION, "Bamboo", 0, 55, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_dunes, BASEURL + "med_air_dune" + BASEURLEXTENSION, "Dune", 0, 56, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_dessert, BASEURL + "med_air_desert" + BASEURLEXTENSION, "Dessert", 0, 57, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_flag, BASEURL + "med_air_flag" + BASEURLEXTENSION, "Flag", 0, 58, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_airplane, BASEURL + "med_airplane" + BASEURLEXTENSION, "Airplane", 0, 59, SoundDefaultVolume());
            windSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_campfire, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, SoundDefaultVolume());
            windSoundsList.add(soundModel);
        }
        return windSoundsList;
    }

    public static ArrayList<SoundModel> RelaxingSounds(Context context) {
        ArrayList<SoundModel> relaxingSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_relax_piano_dark, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_guitar_dark, BASEURL + "med_guitar" + BASEURLEXTENSION, "Guitar", 0, 62, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_violin_dark, BASEURL + "med_violin" + BASEURLEXTENSION, "Violin", 0, 63, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_harp_dark, BASEURL + "med_harp_music" + BASEURLEXTENSION, "Harp", 0, 64, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_flute_dark, BASEURL + "med_flute" + BASEURLEXTENSION, "Flute", 0, 65, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_sax_dark, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_native_dark, BASEURL + "med_native" + BASEURLEXTENSION, "Native", 0, 67, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_qulet_time_dark, BASEURL + "med_quiet" + BASEURLEXTENSION, "Quiet", 0, 68, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_om_dark, BASEURL + "med_om" + BASEURLEXTENSION, "Om", 0, 69, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_guzheng_dark, BASEURL + "med_guzheng" + BASEURLEXTENSION, "Guzheng", 0, 70, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_sitar_dark, BASEURL + "med_sitar" + BASEURLEXTENSION, "Sitar", 0, 71, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_taiko_dark, BASEURL + "med_taiko" + BASEURLEXTENSION, "Taiko", 0, 72, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_temple_dark, BASEURL + "med_temple" + BASEURLEXTENSION, "Temple", 0, 73, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_gong_dark, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_bowls_dark, BASEURL + "med_bowl" + BASEURLEXTENSION, "Bowl", 0, 75, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_relax_piano, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_guitar, BASEURL + "med_guitar" + BASEURLEXTENSION, "Guitar", 0, 62, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_violin, BASEURL + "med_violin" + BASEURLEXTENSION, "Violin", 0, 63, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_harp, BASEURL + "med_harp_music" + BASEURLEXTENSION, "Harp", 0, 64, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_flute, BASEURL + "med_flute" + BASEURLEXTENSION, "Flute", 0, 65, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_sax, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_native, BASEURL + "med_native" + BASEURLEXTENSION, "Native", 0, 67, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_qulet_time, BASEURL + "med_quiet" + BASEURLEXTENSION, "Quiet", 0, 68, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_om, BASEURL + "med_om" + BASEURLEXTENSION, "Om", 0, 69, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_guzheng, BASEURL + "med_guzheng" + BASEURLEXTENSION, "Guzheng", 0, 70, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_sitar, BASEURL + "med_sitar" + BASEURLEXTENSION, "Sitar", 0, 71, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_taiko, BASEURL + "med_taiko" + BASEURLEXTENSION, "Taiko", 0, 72, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_temple, BASEURL + "med_temple" + BASEURLEXTENSION, "Temple", 0, 73, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_gong, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_bowls, BASEURL + "med_bowl" + BASEURLEXTENSION, "Bowl", 0, 75, SoundDefaultVolume());
            relaxingSoundsList.add(soundModel);
        }
        return relaxingSoundsList;
    }

    public static ArrayList<SoundModel> CitySounds(Context context) {
        ArrayList<SoundModel> citySoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_city_musician_dark, BASEURL + "med_oasis_of_calm" + BASEURLEXTENSION, "Musician", 0, 76, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_kids_playground_dark, BASEURL + "med_kids_playground" + BASEURLEXTENSION, "Kids Playground", 0, 77, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_forest_dark, BASEURL + "med_forest" + BASEURLEXTENSION, "Forest", 0, 78, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_cars_dark, BASEURL + "med_cars_passing_by" + BASEURLEXTENSION, "Cars Passing", 0, 79, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_firework_dark, BASEURL + "med_distant_fireworks" + BASEURLEXTENSION, "Distant Firework", 0, 80, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_firework2_dark, BASEURL + "med_distant_fireworks2" + BASEURLEXTENSION, "Distant Fireworks", 0, 81, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_firework3_dark, BASEURL + "med_firework3" + BASEURLEXTENSION, "Fireworks", 0, 82, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_traffic_dark, BASEURL + "med_traffic" + BASEURLEXTENSION, "Traffic", 0, 83, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_subway_dark, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_crowd_dark, BASEURL + "med_crowd_noise" + BASEURLEXTENSION, "Crowd", 0, 85, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_drive_dark, BASEURL + "med_drive" + BASEURLEXTENSION, "Drive", 0, 86, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_farm_dark, BASEURL + "med_farm" + BASEURLEXTENSION, "Farm", 0, 87, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_electric_dark, BASEURL + "med_electric" + BASEURLEXTENSION, "Electric", 0, 88, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_wiper_dark, BASEURL + "med_windshield_wiper_sound" + BASEURLEXTENSION, "Wiper", 0, 89, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_turn_signal_dark, BASEURL + "med_turn_signal" + BASEURLEXTENSION, "Turn Signal", 0, 90, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_construction_site_dark, BASEURL + "med_construction_site_ambience" + BASEURLEXTENSION, "Construction", 0, 91, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_truck_dark, BASEURL + "med_truckengine" + BASEURLEXTENSION, "Truck", 0, 92, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_train_dark, BASEURL + "med_train" + BASEURLEXTENSION, "Train", 0, 93, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_train_station_dark, BASEURL + "med_train_station" + BASEURLEXTENSION, "Train Station", 0, 94, SoundDefaultVolume());
            citySoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_city_musician, BASEURL + "med_oasis_of_calm" + BASEURLEXTENSION, "Musician", 0, 76, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_kids_playground, BASEURL + "med_kids_playground" + BASEURLEXTENSION, "Kids Playground", 0, 77, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_forest, BASEURL + "med_forest" + BASEURLEXTENSION, "Forest", 0, 78, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_cars, BASEURL + "med_cars_passing_by" + BASEURLEXTENSION, "Cars Passing", 0, 79, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_firework, BASEURL + "med_distant_fireworks" + BASEURLEXTENSION, "Distant Firework", 0, 80, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_firework2, BASEURL + "med_distant_fireworks2" + BASEURLEXTENSION, "Distant Fireworks", 0, 81, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_firework3, BASEURL + "med_firework3" + BASEURLEXTENSION, "Fireworks", 0, 82, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_traffic, BASEURL + "med_traffic" + BASEURLEXTENSION, "Traffic", 0, 83, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_subway, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_crowd, BASEURL + "med_crowd_noise" + BASEURLEXTENSION, "Crowd", 0, 85, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_drive, BASEURL + "med_drive" + BASEURLEXTENSION, "Drive", 0, 86, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_farm, BASEURL + "med_farm" + BASEURLEXTENSION, "Farm", 0, 87, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_electric, BASEURL + "med_electric" + BASEURLEXTENSION, "Electric", 0, 88, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_wiper, BASEURL + "med_windshield_wiper_sound" + BASEURLEXTENSION, "Wiper", 0, 89, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_turn_signal, BASEURL + "med_turn_signal" + BASEURLEXTENSION, "Turn Signal", 0, 90, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_construction_site, BASEURL + "med_construction_site_ambience" + BASEURLEXTENSION, "Construction", 0, 91, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_truck, BASEURL + "med_truckengine" + BASEURLEXTENSION, "Truck", 0, 92, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_train, BASEURL + "med_train" + BASEURLEXTENSION, "Train", 0, 93, SoundDefaultVolume());
            citySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_train_station, BASEURL + "med_train_station" + BASEURLEXTENSION, "Train Station", 0, 94, SoundDefaultVolume());
            citySoundsList.add(soundModel);
        }
        return citySoundsList;
    }

    public static ArrayList<SoundModel> HomeSounds(Context context) {
        ArrayList<SoundModel> homeSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_home_clock_dark, BASEURL + "med_clock_tick" + BASEURLEXTENSION, "Clock", 0, 95, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_fan_dark, BASEURL + "med_fan_sound" + BASEURLEXTENSION, "Fan", 0, 96, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_air_conditioner_dark, BASEURL + "med_air_conditioner" + BASEURLEXTENSION, "Air Conditioner", 0, 97, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_hair_dryer_dark, BASEURL + "med_hair_dryer_noise" + BASEURLEXTENSION, "Hair Dryer", 0, 98, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_vaccume_dark, BASEURL + "med_vacuum_cleaner_noise" + BASEURLEXTENSION, "Vaccume Cleaner", 0, 99, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_wind_chime_dark, BASEURL + "med_wind_chime_sound" + BASEURLEXTENSION, "Wind", 0, 100, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_washing_dark, BASEURL + "med_washing_machine" + BASEURLEXTENSION, "Washing Machine", 0, 101, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_washing_dryer_dark, BASEURL + "med_dryer" + BASEURLEXTENSION, "Washing Dryer", 0, 102, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_faucet_dark, BASEURL + "med_faucet" + BASEURLEXTENSION, "Faucet", 0, 103, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_frying_pan_dark, BASEURL + "med_frying_pan" + BASEURLEXTENSION, "Frying Pan", 0, 104, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_mechanical_clock_dark, BASEURL + "med_mechanical_clock" + BASEURLEXTENSION, "Mechanical CLock", 0, 105, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_reading_dark, BASEURL + "med_reading" + BASEURLEXTENSION, "Reading", 0, 106, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_keyboard_dark, BASEURL + "med_keyboard_sound" + BASEURLEXTENSION, "Keyboard", 0, 107, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_radio_dark, BASEURL + "med_radio" + BASEURLEXTENSION, "Radio", 0, 108, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_soup_pot_dark, BASEURL + "med_soup_pot" + BASEURLEXTENSION, "Soup Pot", 0, 109, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_musica_box_dark, BASEURL + "med_music_box" + BASEURLEXTENSION, "Music Box", 0, 110, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_home_clock, BASEURL + "med_clock_tick" + BASEURLEXTENSION, "Clock", 0, 95, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_fan, BASEURL + "med_fan_sound" + BASEURLEXTENSION, "Fan", 0, 96, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_air_conditioner, BASEURL + "med_air_conditioner" + BASEURLEXTENSION, "Air Conditioner", 0, 97, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_hair_dryer, BASEURL + "med_hair_dryer_noise" + BASEURLEXTENSION, "Hair Dryer", 0, 98, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_vaccume, BASEURL + "med_vacuum_cleaner_noise" + BASEURLEXTENSION, "Vaccume Cleaner", 0, 99, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_wind_chime, BASEURL + "med_wind_chime_sound" + BASEURLEXTENSION, "Wind", 0, 100, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_washing, BASEURL + "med_washing_machine" + BASEURLEXTENSION, "Washing Machine", 0, 101, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_washing_dryer, BASEURL + "med_dryer" + BASEURLEXTENSION, "Washing Dryer", 0, 102, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_faucet, BASEURL + "med_faucet" + BASEURLEXTENSION, "Faucet", 0, 103, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_frying_pan, BASEURL + "med_frying_pan" + BASEURLEXTENSION, "Frying Pan", 0, 104, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_mechanical_clock, BASEURL + "med_mechanical_clock" + BASEURLEXTENSION, "Mechanical CLock", 0, 105, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_reading, BASEURL + "med_reading" + BASEURLEXTENSION, "Reading", 0, 106, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_keyboard, BASEURL + "med_keyboard_sound" + BASEURLEXTENSION, "Keyboard", 0, 107, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_radio, BASEURL + "med_radio" + BASEURLEXTENSION, "Radio", 0, 108, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_soup_pot, BASEURL + "med_soup_pot" + BASEURLEXTENSION, "Soup Pot", 0, 109, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_home_musica_box, BASEURL + "med_music_box" + BASEURLEXTENSION, "Music Box", 0, 110, SoundDefaultVolume());
            homeSoundsList.add(soundModel);
        }
        return homeSoundsList;
    }

    public static ArrayList<SoundModel> NoiseSounds(Context context) {
        ArrayList<SoundModel> noiseSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_noise_heartbeat_dark, BASEURL + "med_heartbeat" + BASEURLEXTENSION, "Heartbeat", 0, 111, SoundDefaultVolume());
            noiseSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_noise_white_dark, BASEURL + "med_white_noise" + BASEURLEXTENSION, "White Noise", 0, 112, SoundDefaultVolume());
            noiseSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_noise_pink_dark, BASEURL + "med_pink_noise" + BASEURLEXTENSION, "Pink Noise", 0, 113, SoundDefaultVolume());
            noiseSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_noise_red_dark, BASEURL + "med_brown_noise" + BASEURLEXTENSION, "Red Noise", 0, 114, SoundDefaultVolume());
            noiseSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_noise_heartbeat, BASEURL + "med_heartbeat" + BASEURLEXTENSION, "Heartbeat", 0, 111, SoundDefaultVolume());
            noiseSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_noise_white, BASEURL + "med_white_noise" + BASEURLEXTENSION, "White Noise", 0, 112, SoundDefaultVolume());
            noiseSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_noise_pink, BASEURL + "med_pink_noise" + BASEURLEXTENSION, "Pink Noise", 0, 113, SoundDefaultVolume());
            noiseSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_noise_red, BASEURL + "med_brown_noise" + BASEURLEXTENSION, "Red Noise", 0, 114, SoundDefaultVolume());
            noiseSoundsList.add(soundModel);
        }
        return noiseSoundsList;
    }

    public static ArrayList<SoundModel> BinauralSounds(Context context) {
        ArrayList<SoundModel> binauralSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_binaural_headache_dark, BASEURL + "med_binaural_05" + BASEURLEXTENSION, "Binaural Beat 0.5HZ", 0, 115, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_relaxation_dark, BASEURL + "med_binaural_25" + BASEURLEXTENSION, "Binaural Beat 2.5HZ", 0, 116, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_anxiety_dark, BASEURL + "med_binaural_35" + BASEURLEXTENSION, "Binaural Beat 3.5HZ", 0, 117, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_meditation_dark, BASEURL + "med_binaural_45" + BASEURLEXTENSION, "Binaural Beat 4.5HZ", 0, 118, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_intuition_dark, BASEURL + "med_binaural_55" + BASEURLEXTENSION, "Binaural Beat 5.5HZ", 0, 119, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_creativity_dark, BASEURL + "med_binaural_75" + BASEURLEXTENSION, "Binaural Beat 7.5HZ", 0, 120, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_engery_dark, BASEURL + "med_binaural_8" + BASEURLEXTENSION, "Binaural Beat 8HZ", 0, 121, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_love_dark, BASEURL + "med_binaural_105" + BASEURLEXTENSION, "Binaural Beat 10 .5HZ", 0, 122, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_binaural_headache, BASEURL + "med_binaural_05" + BASEURLEXTENSION, "Binaural Beat 0.5HZ", 0, 115, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_relaxation, BASEURL + "med_binaural_25" + BASEURLEXTENSION, "Binaural Beat 2.5HZ", 0, 116, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_anxiety, BASEURL + "med_binaural_35" + BASEURLEXTENSION, "Binaural Beat 3.5HZ", 0, 117, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_meditation, BASEURL + "med_binaural_45" + BASEURLEXTENSION, "Binaural Beat 4.5HZ", 0, 118, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_intuition, BASEURL + "med_binaural_55" + BASEURLEXTENSION, "Binaural Beat 5.5HZ", 0, 119, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_creativity, BASEURL + "med_binaural_75" + BASEURLEXTENSION, "Binaural Beat 7.5HZ", 0, 120, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_engery, BASEURL + "med_binaural_8" + BASEURLEXTENSION, "Binaural Beat 8HZ", 0, 121, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_binaural_love, BASEURL + "med_binaural_105" + BASEURLEXTENSION, "Binaural Beat 10 .5HZ", 0, 122, SoundDefaultVolume());
            binauralSoundsList.add(soundModel);
        }
        return binauralSoundsList;
    }

    public static ArrayList<String> PlaylistSounds() {
        ArrayList<String> playSoundsList = new ArrayList<>();
        playSoundsList.add("Relaxing Coffee");
        playSoundsList.add("Journey");
        playSoundsList.add("Beach bar");
        playSoundsList.add("Before the storm");
        playSoundsList.add("Children in the Garden");
        playSoundsList.add("Contact with nature");
        playSoundsList.add("Asia area");
        playSoundsList.add("Dropping in the cave");
        playSoundsList.add("Spring time");
        playSoundsList.add("Marine dream");
        playSoundsList.add("Meditation nature");
        playSoundsList.add("Meditation in the temple");
        playSoundsList.add("Mountain cabin");
        playSoundsList.add("Night on the docks");
        playSoundsList.add("peace of the night");
        playSoundsList.add("peaceful dawn");
        playSoundsList.add("Rainy day");
        playSoundsList.add("Relaxing rain");
        playSoundsList.add("Relaxing on the beach");
        playSoundsList.add("Rural atmosphere");
        playSoundsList.add("Surfing among dolphins");
        playSoundsList.add("The spirit of india");
        playSoundsList.add("Wild spirit");
        return playSoundsList;
    }

    public static ArrayList<SoundModel> RelaxingCoffeeSounds(Context context) {
        ArrayList<SoundModel> coffeeSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_city_musician_dark, BASEURL + "med_oasis_of_calm" + BASEURLEXTENSION, "Musician", 0, 76, 75);
            coffeeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_subway_dark, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, 22);
            coffeeSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_city_musician, BASEURL + "med_oasis_of_calm" + BASEURLEXTENSION, "Musician", 0, 76, 75);
            coffeeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_subway, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, 22);
            coffeeSoundsList.add(soundModel);
        }
        return coffeeSoundsList;
    }

    public static ArrayList<SoundModel> JourneySounds(Context context) {
        ArrayList<SoundModel> coffeeSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_river_dark, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 50);
            coffeeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_blackbirds_dark, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 35);
            coffeeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_guzheng_dark, BASEURL + "med_guzheng" + BASEURLEXTENSION, "Guzheng", 0, 70, 60);
            coffeeSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 50);
            coffeeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 35);
            coffeeSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_guzheng, BASEURL + "med_guzheng" + BASEURLEXTENSION, "Guzheng", 0, 70, 60);
            coffeeSoundsList.add(soundModel);
        }
        return coffeeSoundsList;
    }

    public static ArrayList<SoundModel> BeachSounds(Context context) {
        ArrayList<SoundModel> beachSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_sea_dark, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, 70);
            beachSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_subway_dark, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, 45);
            beachSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_ducks_dark, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, 60);
            beachSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_sea, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, 70);
            beachSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_subway, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, 45);
            beachSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_ducks, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, 60);
            beachSoundsList.add(soundModel);
        }
        return beachSoundsList;
    }

    public static ArrayList<SoundModel> StormSounds(Context context) {
        ArrayList<SoundModel> stormSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_nature_strom_dark, BASEURL + "med_storm" + BASEURLEXTENSION, "Storm", 0, 27, 80);
            stormSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_whippoorwill_dark, BASEURL + "med_birds3" + BASEURLEXTENSION, "Bird", 0, 41, 35);
            stormSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_thunder_dark, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, 75);
            stormSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_nature_strom, BASEURL + "med_storm" + BASEURLEXTENSION, "Storm", 0, 27, 80);
            stormSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, "Bird", 0, 41, 35);
            stormSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_thunder, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, 75);
            stormSoundsList.add(soundModel);
        }
        return stormSoundsList;
    }

    public static ArrayList<SoundModel> ChildrenSounds(Context context) {
        ArrayList<SoundModel> childSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_city_farm_dark, BASEURL + "med_farm" + BASEURLEXTENSION, "Farm", 0, 87, 35);
            childSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_kids_playground_dark, BASEURL + "med_kids_playground" + BASEURLEXTENSION, "Kids Playground", 0, 77, 65);
            childSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_birds3_dark, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, 60);
            childSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_city_farm, BASEURL + "med_farm" + BASEURLEXTENSION, "Farm", 0, 87, 35);
            childSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_kids_playground, BASEURL + "med_kids_playground" + BASEURLEXTENSION, "Kids Playground", 0, 77, 65);
            childSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_birds3, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, 60);
            childSoundsList.add(soundModel);
        }
        return childSoundsList;
    }

    public static ArrayList<SoundModel> ContactSounds(Context context) {
        ArrayList<SoundModel> contactSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_river_dark, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 30);
            contactSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_blackbirds_dark, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 10);
            contactSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_harp_dark, BASEURL + "med_harp_music" + BASEURLEXTENSION, "Harp", 0, 64, 65);
            contactSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 30);
            contactSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 10);
            contactSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_harp, BASEURL + "med_harp_music" + BASEURLEXTENSION, "Harp", 0, 64, 65);
            contactSoundsList.add(soundModel);
        }
        return contactSoundsList;
    }

    public static ArrayList<SoundModel> AsianSounds(Context context) {
        ArrayList<SoundModel> asianSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_river_dark, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 40);
            asianSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_ducks_dark, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, 25);
            asianSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_violin_dark, BASEURL + "med_violin" + BASEURLEXTENSION, "Violin", 0, 63, 50);
            asianSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 40);
            asianSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_ducks, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, 25);
            asianSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_violin, BASEURL + "med_violin" + BASEURLEXTENSION, "Violin", 0, 63, 50);
            asianSoundsList.add(soundModel);
        }
        return asianSoundsList;
    }

    public static ArrayList<SoundModel> CaveSounds(Context context) {
        ArrayList<SoundModel> caveSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_drops_dark, BASEURL + "med_dripping_water_sound" + BASEURLEXTENSION, "Dripping", 0, 35, 90);
            caveSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_forest_dark, BASEURL + "med_forest" + BASEURLEXTENSION, "Forest", 0, 78, 40);
            caveSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_drops, BASEURL + "med_dripping_water_sound" + BASEURLEXTENSION, "Dripping", 0, 35, 90);
            caveSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_forest, BASEURL + "med_forest" + BASEURLEXTENSION, "Forest", 0, 78, 40);
            caveSoundsList.add(soundModel);
        }
        return caveSoundsList;
    }

    public static ArrayList<SoundModel> SpringSounds(Context context) {
        ArrayList<SoundModel> springSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_river_dark, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 50);
            springSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_birds3_dark, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, 65);
            springSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 50);
            springSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_birds3, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, 65);
            springSoundsList.add(soundModel);
        }
        return springSoundsList;
    }

    public static ArrayList<SoundModel> DreamSounds(Context context) {
        ArrayList<SoundModel> dreamSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_ocean_wave_dark, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 50);
            dreamSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_campfire_dark, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, 25);
            dreamSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_piano_dark, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, 50);
            dreamSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 50);
            dreamSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_campfire, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, 25);
            dreamSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_piano, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, 50);
            dreamSoundsList.add(soundModel);
        }
        return dreamSoundsList;
    }

    public static ArrayList<SoundModel> MeditationNatureSounds(Context context) {
        ArrayList<SoundModel> meditationNatureSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_waterfall_dark, BASEURL + "med_waterfall_sound" + BASEURLEXTENSION, "Waterfall", 0, 34, 50);
            meditationNatureSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_taiko_dark, BASEURL + "med_taiko" + BASEURLEXTENSION, "Taiko", 0, 72, 65);
            meditationNatureSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_sax_dark, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, 27);
            meditationNatureSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_waterfall, BASEURL + "med_waterfall_sound" + BASEURLEXTENSION, "Waterfall", 0, 34, 50);
            meditationNatureSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_taiko, BASEURL + "med_taiko" + BASEURLEXTENSION, "Taiko", 0, 72, 65);
            meditationNatureSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_sax, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, 27);
            meditationNatureSoundsList.add(soundModel);
        }
        return meditationNatureSoundsList;
    }

    public static ArrayList<SoundModel> MeditationTempleSounds(Context context) {
        ArrayList<SoundModel> meditationTempleSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_traditional_bowls_dark, BASEURL + "med_bowl" + BASEURLEXTENSION, "Bowl", 0, 75, 50);
            meditationTempleSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_temple_dark, BASEURL + "med_temple" + BASEURLEXTENSION, "Temple", 0, 72, 60);
            meditationTempleSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_gong_dark, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, 80);
            meditationTempleSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_om_dark, BASEURL + "med_om" + BASEURLEXTENSION, "Om", 0, 69, 10);
            meditationTempleSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_traditional_bowls, BASEURL + "med_bowl" + BASEURLEXTENSION, "Bowl", 0, 75, 50);
            meditationTempleSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_temple, BASEURL + "med_temple" + BASEURLEXTENSION, "Temple", 0, 72, 60);
            meditationTempleSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_gong, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, 80);
            meditationTempleSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_om, BASEURL + "med_om" + BASEURLEXTENSION, "Om", 0, 69, 10);
            meditationTempleSoundsList.add(soundModel);
        }
        return meditationTempleSoundsList;
    }

    public static ArrayList<SoundModel> CabinSounds(Context context) {
        ArrayList<SoundModel> cabinSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_wind_mountain_dark, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, 20);
            cabinSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_campfire_dark, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, 100);
            cabinSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_wind_mountain, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, 20);
            cabinSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_campfire, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, 100);
            cabinSoundsList.add(soundModel);
        }
        return cabinSoundsList;
    }

    public static ArrayList<SoundModel> DockSounds(Context context) {
        ArrayList<SoundModel> dockSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_relax_sax_dark, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, 80);
            dockSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_ocean_wave_dark, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 40);
            dockSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_relax_sax, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, 80);
            dockSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 40);
            dockSoundsList.add(soundModel);
        }
        return dockSoundsList;
    }

    public static ArrayList<SoundModel> PeaceSounds(Context context) {
        ArrayList<SoundModel> peaceSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_night_whippoorwill_dark, BASEURL + "med_birds3" + BASEURLEXTENSION, "Nightingale", 0, 41, 65);
            peaceSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_gong_dark, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, 40);
            peaceSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, "Nightingale", 0, 41, 65);
            peaceSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_traditional_gong, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, 40);
            peaceSoundsList.add(soundModel);
        }
        return peaceSoundsList;
    }

    public static ArrayList<SoundModel> RainySounds(Context context) {
        ArrayList<SoundModel> rainySoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_heavy_rain_dark, BASEURL + "med_heavy_rain" + BASEURLEXTENSION, "Heavy Rain", 0, 5, 50);
            rainySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_thunder_dark, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, 80);
            rainySoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_heavy_rain, BASEURL + "med_heavy_rain" + BASEURLEXTENSION, "Heavy Rain", 0, 5, 50);
            rainySoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_thunder, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, 80);
            rainySoundsList.add(soundModel);
        }
        return rainySoundsList;
    }

    public static ArrayList<SoundModel> RelaxRainSounds(Context context) {
        ArrayList<SoundModel> relaxRainSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_light_rain_dark, BASEURL + "med_light_rain" + BASEURLEXTENSION, "Light Rain", 0, 4, 80);
            relaxRainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_piano_dark, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, 50);
            relaxRainSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_light_rain, BASEURL + "med_light_rain" + BASEURLEXTENSION, "Light Rain", 0, 4, 80);
            relaxRainSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_relax_piano, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, 50);
            relaxRainSoundsList.add(soundModel);
        }
        return relaxRainSoundsList;
    }

    public static ArrayList<SoundModel> RealxBeachSounds(Context context) {
        ArrayList<SoundModel> realxBeachSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_ocean_wave_dark, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 65);
            realxBeachSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_seagulls_dark, BASEURL + "med_seagulls" + BASEURLEXTENSION, "Seagulls", 0, 19, 30);
            realxBeachSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 65);
            realxBeachSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_seagulls, BASEURL + "med_seagulls" + BASEURLEXTENSION, "Seagulls", 0, 19, 30);
            realxBeachSoundsList.add(soundModel);
        }
        return realxBeachSoundsList;
    }

    public static ArrayList<SoundModel> RuralSounds(Context context) {
        ArrayList<SoundModel> ruralSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_country_birds2_dark, BASEURL + "med_birds2" + BASEURLEXTENSION, "Birds", 0, 13, 10);
            ruralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_forest_dark, BASEURL + "med_forest" + BASEURLEXTENSION, "med_forest", 0, 78, 50);
            ruralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_whippoorwill_dark, BASEURL + "med_birds3" + BASEURLEXTENSION, "Bird", 0, 41, 60);
            ruralSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_country_birds2, BASEURL + "med_birds2" + BASEURLEXTENSION, "Birds", 0, 13, 10);
            ruralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_city_forest, BASEURL + "med_forest" + BASEURLEXTENSION, "med_forest", 0, 78, 50);
            ruralSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, "Bird", 0, 41, 60);
            ruralSoundsList.add(soundModel);
        }
        return ruralSoundsList;
    }

    public static ArrayList<SoundModel> DolphinsSounds(Context context) {
        ArrayList<SoundModel> dolphinsSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_ocean_whale_dark, BASEURL + "med_whale_sound" + BASEURLEXTENSION, "med_whale_sound", 0, 23, 30);
            dolphinsSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_sea_dark, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, 65);
            dolphinsSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_ocean_whale, BASEURL + "med_whale_sound" + BASEURLEXTENSION, "med_whale_sound", 0, 23, 30);
            dolphinsSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_sea, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, 65);
            dolphinsSoundsList.add(soundModel);
        }
        return dolphinsSoundsList;
    }

    public static ArrayList<SoundModel> IndiaSounds(Context context) {
        ArrayList<SoundModel> indiaSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_traditional_sitar_dark, BASEURL + "med_sitar" + BASEURLEXTENSION, "Sitar", 0, 71, 75);
            indiaSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_river_dark, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 20);
            indiaSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_blackbirds_dark, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 40);
            indiaSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_traditional_sitar, BASEURL + "med_sitar" + BASEURLEXTENSION, "Sitar", 0, 71, 75);
            indiaSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 20);
            indiaSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 40);
            indiaSoundsList.add(soundModel);
        }
        return indiaSoundsList;
    }

    public static ArrayList<SoundModel> WildSounds(Context context) {
        ArrayList<SoundModel> wildSoundsList = new ArrayList<>();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            SoundModel soundModel = new SoundModel(R.drawable.ic_relax_native_dark, BASEURL + "med_native" + BASEURLEXTENSION, "Native", 0, 68, 70);
            wildSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_mountain_dark, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, 10);
            wildSoundsList.add(soundModel);
        } else {
            SoundModel soundModel = new SoundModel(R.drawable.ic_relax_native, BASEURL + "med_native" + BASEURLEXTENSION, "Native", 0, 68, 70);
            wildSoundsList.add(soundModel);
            soundModel = new SoundModel(R.drawable.ic_wind_mountain, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, 10);
            wildSoundsList.add(soundModel);
        }
        return wildSoundsList;
    }

    public static String convertToTimeString(String minutes) {
        int totalMinutes = Integer.parseInt(minutes);

        int hours = totalMinutes / 60;
        int remainingMinutes = totalMinutes % 60;

        String formattedTime = String.format(Locale.US, "%02d:%02d", hours, remainingMinutes);

        return formattedTime;
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
