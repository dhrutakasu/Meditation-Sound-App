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
        SoundModel soundModel = new SoundModel(R.drawable.ic_rain_morning, R.raw.sound_rain_morning_rain);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_umbrella, R.raw.sound_rain_umbrella);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_thunder, R.raw.sound_rain_thunders);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_light_rain, R.raw.sound_rain_light);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_heavy_rain, R.raw.sound_rain_heavy);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_distant_thunder, R.raw.sound_rain_distant_thunder);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_tent, R.raw.sound_rain_tent);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_window, R.raw.sound_rain_window);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_roof, R.raw.sound_rain_roof);
        rainSoundsList.add(soundModel);
    }
    public static void OceanSounds() {
        ArrayList<SoundModel> oceanSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_ocean_clam_waves, R.raw.sound_ocean_calm_waves);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_waves, R.raw.sound_ocean_waves);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_seagulls, R.raw.sound_ocean_seagulls);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_scuba_diver, R.raw.sound_ocean_diver);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_dolphines, R.raw.sound_ocean_dolphins);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_sailboat, R.raw.sound_ocean_sailboat);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_whale, R.raw.sound_ocean_whale);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_foghorn, R.raw.sound_ocean_foghorn);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_harbor, R.raw.sound_ocean_harbor);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_rough, R.raw.sound_ocean_rough);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_ferry, R.raw.sound_ocean_ferry);
        oceanSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_cabin, R.raw.sound_ocean_cabin);
        oceanSoundsList.add(soundModel);
    }

    public static void WaterSounds() {
        ArrayList<SoundModel> waterSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, R.raw.sound_water_river);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_brook, R.raw.sound_water_brook);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_creek, R.raw.sound_water_creek);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_waterfall, R.raw.sound_water_waterfall);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_drops, R.raw.sound_water_drops);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_canoe, R.raw.sound_water_canoe);
        waterSoundsList.add(soundModel);
    }

    public static void NightSounds() {
        ArrayList<SoundModel> nightSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_night_crickets, R.raw.sound_nature_night_crickets);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_grassshoppers, R.raw.sound_nature_night_grasshoppers);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_owls, R.raw.sound_nature_night_owls);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_wolves, R.raw.sound_nature_night_wolves);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_loon, R.raw.sound_nature_night_loons);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_frogs, R.raw.sound_nature_night_frogs);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_coyote, R.raw.sound_nature_night_coyote);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_coqui, R.raw.sound_nature_night_coqui);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, R.raw.sound_nature_night_whippoorwill);
        nightSoundsList.add(soundModel);
    }

    public static void CountrySounds() {
        ArrayList<SoundModel> countrySoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_country_blackbirds, R.raw.sound_nature_day_blackbirds);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_nightingale, R.raw.sound_nature_day_nightingale);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_swallows, R.raw.sound_nature_day_swallows);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_tutlrdove, R.raw.sound_nature_day_turtledove);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_eagle, R.raw.sound_nature_day_eagle);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_crows, R.raw.sound_nature_day_crows);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_farm, R.raw.sound_nature_day_farm);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cowbells, R.raw.sound_nature_day_cowbells);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_sheep, R.raw.sound_nature_day_sheep);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_horse, R.raw.sound_nature_day_horse);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_ducks, R.raw.sound_nature_day_duck);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cicadas, R.raw.sound_nature_day_cicadas);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_tarin_horn, R.raw.sound_nature_day_train_horn);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_carriage, R.raw.sound_nature_day_carriage);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cow, R.raw.sound_nature_day_cow);
        countrySoundsList.add(soundModel);
    }
    public static void WindSounds() {
        ArrayList<SoundModel> windSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_wind_light, R.raw.sound_nature_day_blackbirds);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_strong, R.raw.sound_nature_day_nightingale);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_mountain, R.raw.sound_nature_day_swallows);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_under_door, R.raw.sound_nature_day_turtledove);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_trees, R.raw.sound_nature_day_eagle);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_bamboo, R.raw.sound_nature_day_crows);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_sock, R.raw.sound_nature_day_farm);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_dunes, R.raw.sound_nature_day_cowbells);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_dessert, R.raw.sound_nature_day_sheep);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_oil_stove, R.raw.sound_nature_day_horse);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_campfire, R.raw.sound_nature_day_duck);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_fire_place, R.raw.sound_nature_day_cicadas);
        windSoundsList.add(soundModel);
    }
    public static void RelaxingSounds() {
        ArrayList<SoundModel> relaxingSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_relax_piano, R.raw.sound_nature_day_blackbirds);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_guitar, R.raw.sound_nature_day_nightingale);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_violin, R.raw.sound_nature_day_swallows);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_harp, R.raw.sound_nature_day_turtledove);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_flute, R.raw.sound_nature_day_eagle);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_sax, R.raw.sound_nature_day_crows);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_native, R.raw.sound_nature_day_farm);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_qulet_time, R.raw.sound_nature_day_cowbells);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_tranquility, R.raw.sound_nature_day_sheep);
        relaxingSoundsList.add(soundModel);
    }
    public static void TraditionalSounds() {
        ArrayList<SoundModel> traditionalSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_traditional_bowls, R.raw.sound_nature_day_blackbirds);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_gong, R.raw.sound_nature_day_nightingale);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_bells, R.raw.sound_nature_day_swallows);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_om, R.raw.sound_nature_day_turtledove);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_flute, R.raw.sound_nature_day_eagle);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_didgeridoo, R.raw.sound_nature_day_crows);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_chimes, R.raw.sound_nature_day_farm);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_guzheng, R.raw.sound_nature_day_cowbells);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_temple, R.raw.sound_nature_day_sheep);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_sitar, R.raw.sound_nature_day_horse);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_taiko, R.raw.sound_nature_day_duck);
        traditionalSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_shamisen, R.raw.sound_nature_day_cicadas);
        traditionalSoundsList.add(soundModel);
    }
    public static void GardenSounds() {
        ArrayList<SoundModel> gardenSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_garden_pond, R.raw.sound_nature_day_blackbirds);
        gardenSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_garden_tukubai, R.raw.sound_nature_day_nightingale);
        gardenSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_garden_waterflow, R.raw.sound_nature_day_swallows);
        gardenSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_garden_broom, R.raw.sound_nature_day_turtledove);
        gardenSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_garden_rake, R.raw.sound_nature_day_eagle);
        gardenSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_garden_odoshi, R.raw.sound_nature_day_crows);
        gardenSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_garden_tea_ceremony, R.raw.sound_nature_day_farm);
        gardenSoundsList.add(soundModel);
    }
    public static void CitySounds() {
        ArrayList<SoundModel> citySoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_city_musician, R.raw.sound_nature_day_blackbirds);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_coffee, R.raw.sound_nature_day_nightingale);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_fountain, R.raw.sound_nature_day_swallows);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_children, R.raw.sound_nature_day_turtledove);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_traffic, R.raw.sound_nature_day_eagle);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_subway, R.raw.sound_nature_day_crows);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_road_work, R.raw.sound_nature_day_farm);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_train_station, R.raw.sound_nature_day_cowbells);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_market, R.raw.sound_nature_day_sheep);
        citySoundsList.add(soundModel);
    }
    public static void HomeSounds() {
        ArrayList<SoundModel> homeSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_home_fan, R.raw.sound_nature_day_blackbirds);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_air_conditioner, R.raw.sound_nature_day_nightingale);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_hair_dryer, R.raw.sound_nature_day_swallows);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_vaccume, R.raw.sound_nature_day_turtledove);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_cat_purring, R.raw.sound_nature_day_eagle);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_shower, R.raw.sound_nature_day_crows);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_washing, R.raw.sound_nature_day_farm);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_jacuzzi, R.raw.sound_nature_day_cowbells);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_fridge, R.raw.sound_nature_day_sheep);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_vintage, R.raw.sound_nature_day_horse);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_gas_stove, R.raw.sound_nature_day_duck);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_heater, R.raw.sound_nature_day_cicadas);
        homeSoundsList.add(soundModel);
    }
    public static void NoiseSounds() {
        ArrayList<SoundModel> noiseSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_noise_white, R.raw.sound_nature_day_blackbirds);
        noiseSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_noise_pink, R.raw.sound_nature_day_nightingale);
        noiseSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_noise_red, R.raw.sound_nature_day_swallows);
        noiseSoundsList.add(soundModel);
    }

    public static void BinauralSounds() {
        ArrayList<SoundModel> binauralSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_binaural_headache, R.raw.sound_nature_day_blackbirds);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_insomnia, R.raw.sound_nature_day_nightingale);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_relaxation, R.raw.sound_nature_day_swallows);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_anxiety, R.raw.sound_nature_day_turtledove);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_meditation, R.raw.sound_nature_day_eagle);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_intuition, R.raw.sound_nature_day_crows);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_creativity, R.raw.sound_nature_day_farm);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_engery, R.raw.sound_nature_day_cowbells);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_love, R.raw.sound_nature_day_sheep);
        binauralSoundsList.add(soundModel);
    }
}
