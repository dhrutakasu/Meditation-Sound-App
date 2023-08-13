package com.med.meditationsoundapp.SoundConstants;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;

import com.karumi.dexter.PermissionToken;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.SoundModel;

import java.util.ArrayList;

public class MedConstants {


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
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogColor);
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
                activity.onBackPressed();
            }
        });
        builder.show();
    }

    public static void showPermissionDialog(final Activity activity, final PermissionToken permissionToken) {
        new AlertDialog.Builder(activity, R.style.DialogColor
        ).setMessage((int) R.string.MSG_ASK_PERMISSION).setNegativeButton("Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permissionToken.cancelPermissionRequest();
                activity.onBackPressed();
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

    public static void RainSounds() {
        ArrayList<SoundModel> rainSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_rain_morning, R.raw.rain_morning_rain);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_umbrella, R.raw.rain_umbrella);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_thunder, R.raw.rain_thunders);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_light_rain, R.raw.rain_light);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_heavy_rain, R.raw.rain_heavy);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_distant_thunder, R.raw.rain_distant_thunder);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_tent, R.raw.rain_tent);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_window, R.raw.rain_window);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_roof, R.raw.rain_roof);
        rainSoundsList.add(soundModel);
    }
    public static void OceanSounds() {
        ArrayList<SoundModel> oceanSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_ocean_clam_waves, R.raw.ocean_calm_waves);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_waves, R.raw.ocean_waves);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_seagulls, R.raw.ocean_seagulls);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_scuba_diver, R.raw.ocean_diver);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_dolphines, R.raw.ocean_dolphins);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_sailboat, R.raw.ocean_sailboat);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_whale, R.raw.ocean_whale);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_foghorn, R.raw.ocean_foghorn);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_harbor, R.raw.ocean_harbor);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_rough, R.raw.ocean_rough);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_ferry, R.raw.ocean_ferry);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_cabin, R.raw.ocean_cabin);
        oceanSoundsList.add(soundModel);
    }

    public static void WaterSounds() {
        ArrayList<SoundModel> waterSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, R.raw.water_river);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_brook, R.raw.water_brook);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_creek, R.raw.water_creek);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_waterfall, R.raw.water_waterfall);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_drops, R.raw.water_drops);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_canoe, R.raw.water_canoe);
        waterSoundsList.add(soundModel);
    }

    public static void NightSounds() {
        ArrayList<SoundModel> nightSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_night_crickets, R.raw.nature_night_crickets);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_grassshoppers, R.raw.nature_night_grasshoppers);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_owls, R.raw.nature_night_owls);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_wolves, R.raw.nature_night_wolves);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_loon, R.raw.nature_night_loons);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_frogs, R.raw.nature_night_frogs);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_coyote, R.raw.nature_night_coyote);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_coqui, R.raw.nature_night_coqui);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, R.raw.nature_night_whippoorwill);
        nightSoundsList.add(soundModel);
    }

    public static void CountrySounds() {
        ArrayList<SoundModel> countrySoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_country_blackbirds, R.raw.nature_day_blackbirds);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_nightingale, R.raw.nature_day_nightingale);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_swallows, R.raw.nature_day_swallows);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_tutlrdove, R.raw.nature_day_turtledove);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_eagle, R.raw.nature_day_eagle);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_crows, R.raw.nature_day_crows);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_farm, R.raw.nature_day_farm);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cowbells, R.raw.nature_day_cowbells);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_sheep, R.raw.nature_day_sheep);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_horse, R.raw.nature_day_horse);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_ducks, R.raw.nature_day_duck);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cicadas, R.raw.nature_day_cicadas);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_tarin_horn, R.raw.nature_day_train_horn);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_carriage, R.raw.nature_day_carriage);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cow, R.raw.nature_day_cow);
        countrySoundsList.add(soundModel);
    }
}
