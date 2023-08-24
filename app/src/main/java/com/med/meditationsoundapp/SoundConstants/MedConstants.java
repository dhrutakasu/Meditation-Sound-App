package com.med.meditationsoundapp.SoundConstants;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;

import com.karumi.dexter.PermissionToken;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.PlyerModel;
import com.med.meditationsoundapp.SoundModel.SoundModel;

import java.util.ArrayList;

public class MedConstants {


    public static final String BROADCAST_MAIN = "BROADCAST_MAIN";
    public static final String BROADCAST_FRAGMENT = "BROADCAST_FRAGMENT";
    public static final String FRAGMENT_CLICK = "FRAGMENT_CLICK";
    public static String SelectedSounds = "SelectedSounds";
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

    public static ArrayList<SoundModel> RainSounds() {
        ArrayList<SoundModel> rainSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_rain, BASEURL + "med_rain" + BASEURLEXTENSION, "Rain", 0, 0, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_car, BASEURL + "med_rain_on_car" + BASEURLEXTENSION, "Rain On Car", 0, 1, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_umbrella, BASEURL + "med_rain_on_umbrella_sound" + BASEURLEXTENSION, "Umbrella", 0, 2, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_thunder, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_light_rain, BASEURL + "med_light_rain" + BASEURLEXTENSION, "Light Rain", 0, 4, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_heavy_rain, BASEURL + "med_heavy_rain" + BASEURLEXTENSION, "Heavy Rain", 0, 5, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_distant_thunder, BASEURL + "med_distant_thunder" + BASEURLEXTENSION, "Distant Thunder", 0, 6, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_tent, BASEURL + "med_rain_on_tent_sound" + BASEURLEXTENSION, "Tent", 0, 7, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_window, BASEURL + "med_rain_on_window_sound" + BASEURLEXTENSION, "Rain On Window", 0, 8, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_roof, BASEURL + "med_rain_roof" + BASEURLEXTENSION, "Roof", 0, 9, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_plant, BASEURL + "med_rain_on_metaplates" + BASEURLEXTENSION, "Metaplants", 0, 10, 50);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_leaves, BASEURL + "med_rain_leaves" + BASEURLEXTENSION, "Rain Leaves", 0, 11, 50);
        rainSoundsList.add(soundModel);
        return rainSoundsList;
    }

    public static ArrayList<SoundModel> CountrySounds() {
        ArrayList<SoundModel> countrySoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_birds2, BASEURL + "med_birds2" + BASEURLEXTENSION, "Birds", 0, 13, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_birds3, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cat_purring, BASEURL + "med_cat_purring" + BASEURLEXTENSION, "Cat Purring", 0, 15, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cat, BASEURL + "med_cat" + BASEURLEXTENSION, "Cat", 0, 16, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cats, BASEURL + "med_cat2" + BASEURLEXTENSION, "Cats", 0, 17, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_dogs, BASEURL + "med_dogs" + BASEURLEXTENSION, "Dogs", 0, 18, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_seagulls, BASEURL + "med_seagulls" + BASEURLEXTENSION, "Seagulls", 0, 19, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_ducks, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_chicken, BASEURL + "med_chicken_coop" + BASEURLEXTENSION, "Chicken", 0, 21, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cicadas, BASEURL + "med_cicada_chirping" + BASEURLEXTENSION, "Cicada", 0, 22, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_whale, BASEURL + "med_whale_sound" + BASEURLEXTENSION, "Whale", 0, 23, 50);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_elephant, BASEURL + "med_elephant" + BASEURLEXTENSION, "Elephant", 0, 24, 50);
        countrySoundsList.add(soundModel);
        return countrySoundsList;
    }

    public static ArrayList<SoundModel> NatureSounds() {
        ArrayList<SoundModel> natureSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_nature_snow_falling, BASEURL + "med_snow_falling_sound" + BASEURLEXTENSION, "Snow Falling", 0, 25, 50);
        natureSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_nature_snow_walking, BASEURL + "med_snow_walking" + BASEURLEXTENSION, "Snow Walking", 0, 26, 50);
        natureSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_nature_strom, BASEURL + "med_storm" + BASEURLEXTENSION, "Storm", 0, 27, 50);
        natureSoundsList.add(soundModel);
        return natureSoundsList;
    }

    public static ArrayList<SoundModel> WaterSounds() {
        ArrayList<SoundModel> waterSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_underwater, BASEURL + "med_underwater" + BASEURLEXTENSION, "UnderWater", 0, 28, 50);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 50);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_bubble, BASEURL + "med_bubble" + BASEURLEXTENSION, "Bubble", 0, 30, 50);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 50);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_seagull, BASEURL + "med_seagull_sound" + BASEURLEXTENSION, "Seagull", 0, 32, 50);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_creek, BASEURL + "med_creek" + BASEURLEXTENSION, "Creek", 0, 33, 50);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_waterfall, BASEURL + "med_waterfall_sound" + BASEURLEXTENSION, "Waterfall", 0, 34, 50);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_drops, BASEURL + "med_dripping_water_sound" + BASEURLEXTENSION, "Dripping", 0, 35, 50);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_sea, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, 50);
        waterSoundsList.add(soundModel);
        return waterSoundsList;
    }

    public static ArrayList<SoundModel> NightSounds() {
        ArrayList<SoundModel> nightSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_night_crickets, BASEURL + "med_cricket_chirping" + BASEURLEXTENSION, "Crickets", 0, 37, 50);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_owls, BASEURL + "med_owl_hooting" + BASEURLEXTENSION, "Owl", 0, 38, 50);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_wolves, BASEURL + "med_wolf_howling" + BASEURLEXTENSION, "Wolf", 0, 39, 50);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_frogs, BASEURL + "med_frog_croaking" + BASEURLEXTENSION, "Frog", 0, 40, 50);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, "Birds", 0, 41, 50);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_forest_night, BASEURL + "med_forest_nightfall" + BASEURLEXTENSION, "Forest", 0, 42, 50);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_goodnight, BASEURL + "med_goodnight" + BASEURLEXTENSION, "Good Night", 0, 43, 50);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_grasswalk, BASEURL + "med_grassland_ambience" + BASEURLEXTENSION, "Grassland", 0, 44, 50);
        nightSoundsList.add(soundModel);
        return nightSoundsList;
    }

    public static ArrayList<SoundModel> WindSounds() {
        ArrayList<SoundModel> windSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_wind_light, BASEURL + "med_air_light_wind" + BASEURLEXTENSION, "Light Wind", 0, 45, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_breeze, BASEURL + "med_breeze" + BASEURLEXTENSION, "Breeze", 0, 46, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_gale, BASEURL + "med_gale" + BASEURLEXTENSION, "Gale", 0, 47, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_strong, BASEURL + "med_air_strong" + BASEURLEXTENSION, "Strong", 0, 48, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_howling, BASEURL + "med_wind_howling" + BASEURLEXTENSION, "Howling", 0, 49, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_polar, BASEURL + "med_wind_polar" + BASEURLEXTENSION, "Polar", 0, 50, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_sound, BASEURL + "med_wind_sound" + BASEURLEXTENSION, "Wind", 0, 51, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_mountain, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_under_door, BASEURL + "med_air_door" + BASEURLEXTENSION, "Door", 0, 53, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_trees, BASEURL + "med_air_tree" + BASEURLEXTENSION, "Tree", 0, 54, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_bamboo, BASEURL + "med_air_bamboo" + BASEURLEXTENSION, "Bamboo", 0, 55, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_dunes, BASEURL + "med_air_dune" + BASEURLEXTENSION, "Dune", 0, 56, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_dessert, BASEURL + "med_air_desert" + BASEURLEXTENSION, "Dessert", 0, 57, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_flag, BASEURL + "med_air_flag" + BASEURLEXTENSION, "Flag", 0, 58, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_airplane, BASEURL + "med_airplane" + BASEURLEXTENSION, "Airplane", 0, 59, 50);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_campfire, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, 50);
        windSoundsList.add(soundModel);
        return windSoundsList;
    }

    public static ArrayList<SoundModel> RelaxingSounds() {
        ArrayList<SoundModel> relaxingSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_relax_piano, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_guitar, BASEURL + "med_guitar" + BASEURLEXTENSION, "Guitar", 0, 62, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_violin, BASEURL + "med_violin" + BASEURLEXTENSION, "Violin", 0, 63, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_harp, BASEURL + "med_harp_music" + BASEURLEXTENSION, "Harp", 0, 64, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_flute, BASEURL + "med_flute" + BASEURLEXTENSION, "Flute", 0, 65, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_sax, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_native, BASEURL + "med_native" + BASEURLEXTENSION, "Native", 0, 67, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_qulet_time, BASEURL + "med_quiet" + BASEURLEXTENSION, "Quiet", 0, 68, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_om, BASEURL + "med_om" + BASEURLEXTENSION, "Om", 0, 69, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_guzheng, BASEURL + "med_guzheng" + BASEURLEXTENSION, "Guzheng", 0, 70, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_sitar, BASEURL + "med_sitar" + BASEURLEXTENSION, "Sitar", 0, 71, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_taiko, BASEURL + "med_taiko" + BASEURLEXTENSION, "Taiko", 0, 72, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_temple, BASEURL + "med_temple" + BASEURLEXTENSION, "Temple", 0, 73, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_gong, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, 50);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_bowls, BASEURL + "med_bowl" + BASEURLEXTENSION, "Bowl", 0, 75, 50);
        relaxingSoundsList.add(soundModel);
        return relaxingSoundsList;
    }

    public static ArrayList<SoundModel> CitySounds() {
        ArrayList<SoundModel> citySoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_city_musician, BASEURL + "med_oasis_of_calm" + BASEURLEXTENSION, "Musician", 0, 76, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_kids_playground, BASEURL + "med_kids_playground" + BASEURLEXTENSION, "Kids Playground", 0, 77, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_forest, BASEURL + "med_forest" + BASEURLEXTENSION, "Forest", 0, 78, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_cars, BASEURL + "med_cars_passing_by" + BASEURLEXTENSION, "Cars Passing", 0, 79, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_firework, BASEURL + "med_distant_fireworks" + BASEURLEXTENSION, "Distant Firework", 0, 80, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_firework2, BASEURL + "med_distant_fireworks2" + BASEURLEXTENSION, "Distant Fireworks", 0, 81, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_firework3, BASEURL + "med_firework3" + BASEURLEXTENSION, "Fireworks", 0, 82, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_traffic, BASEURL + "med_traffic" + BASEURLEXTENSION, "Traffic", 0, 83, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_subway, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_crowd, BASEURL + "med_crowd_noise" + BASEURLEXTENSION, "Crowd", 0, 85, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_drive, BASEURL + "med_drive" + BASEURLEXTENSION, "Drive", 0, 86, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_farm, BASEURL + "med_farm" + BASEURLEXTENSION, "Farm", 0, 87, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_electric, BASEURL + "med_electric" + BASEURLEXTENSION, "Electric", 0, 88, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_wiper, BASEURL + "med_windshield_wiper_sound" + BASEURLEXTENSION, "Wiper", 0, 89, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_turn_signal, BASEURL + "med_turn_signal" + BASEURLEXTENSION, "Turn Signal", 0, 90, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_construction_site, BASEURL + "med_construction_site_ambience" + BASEURLEXTENSION, "Construction", 0, 91, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_truck, BASEURL + "med_truckengine" + BASEURLEXTENSION, "Truck", 0, 92, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_train, BASEURL + "med_train" + BASEURLEXTENSION, "Train", 0, 93, 50);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_train_station, BASEURL + "med_train_station" + BASEURLEXTENSION, "Train Station", 0, 94, 50);
        citySoundsList.add(soundModel);
        return citySoundsList;
    }

    public static ArrayList<SoundModel> HomeSounds() {
        ArrayList<SoundModel> homeSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_home_clock, BASEURL + "med_clock_tick" + BASEURLEXTENSION, "Clock", 0, 95, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_fan, BASEURL + "med_fan_sound" + BASEURLEXTENSION, "Fan", 0, 96, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_air_conditioner, BASEURL + "med_air_conditioner" + BASEURLEXTENSION, "Air Conditioner", 0, 97, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_hair_dryer, BASEURL + "med_hair_dryer_noise" + BASEURLEXTENSION, "Hair Dryer", 0, 98, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_vaccume, BASEURL + "med_vacuum_cleaner_noise" + BASEURLEXTENSION, "Vaccume Cleaner", 0, 99, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_wind_chime, BASEURL + "med_wind_chime_sound" + BASEURLEXTENSION, "Wind", 0, 100, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_washing, BASEURL + "med_washing_machine" + BASEURLEXTENSION, "Washing Machine", 0, 101, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_washing_dryer, BASEURL + "med_dryer" + BASEURLEXTENSION, "Washing Dryer", 0, 102, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_faucet, BASEURL + "med_faucet" + BASEURLEXTENSION, "Faucet", 0, 103, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_frying_pan, BASEURL + "med_frying_pan" + BASEURLEXTENSION, "Frying Pan", 0, 104, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_mechanical_clock, BASEURL + "med_mechanical_clock" + BASEURLEXTENSION, "Mechanical CLock", 0, 105, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_reading, BASEURL + "med_reading" + BASEURLEXTENSION, "Reading", 0, 106, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_keyboard, BASEURL + "med_keyboard_sound" + BASEURLEXTENSION, "Keyboard", 0, 107, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_radio, BASEURL + "med_radio" + BASEURLEXTENSION, "Radio", 0, 108, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_soup_pot, BASEURL + "med_soup_pot" + BASEURLEXTENSION, "Soup Pot", 0, 109, 50);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_musica_box, BASEURL + "med_music_box" + BASEURLEXTENSION, "Music Box", 0, 110, 50);
        homeSoundsList.add(soundModel);
        return homeSoundsList;
    }

    public static ArrayList<SoundModel> NoiseSounds() {
        ArrayList<SoundModel> noiseSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_noise_heartbeat, BASEURL + "med_heartbeat" + BASEURLEXTENSION, "Heartbeat", 0, 111, 50);
        noiseSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_noise_white, BASEURL + "med_white_noise" + BASEURLEXTENSION, "White Noise", 0, 112, 50);
        noiseSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_noise_pink, BASEURL + "med_pink_noise" + BASEURLEXTENSION, "Pink Noise", 0, 113, 50);
        noiseSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_noise_red, BASEURL + "med_brown_noise" + BASEURLEXTENSION, "Red Noise", 0, 114, 50);
        noiseSoundsList.add(soundModel);
        return noiseSoundsList;
    }

    public static ArrayList<SoundModel> BinauralSounds() {
        ArrayList<SoundModel> binauralSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_binaural_headache, BASEURL + "med_binaural_05" + BASEURLEXTENSION, "Binaural Beat 0.5HZ", 0, 115, 50);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_relaxation, BASEURL + "med_binaural_25" + BASEURLEXTENSION, "Binaural Beat 2.5HZ", 0, 116, 50);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_anxiety, BASEURL + "med_binaural_35" + BASEURLEXTENSION, "Binaural Beat 3.5HZ", 0, 117, 50);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_meditation, BASEURL + "med_binaural_45" + BASEURLEXTENSION, "Binaural Beat 4.5HZ", 0, 118, 50);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_intuition, BASEURL + "med_binaural_55" + BASEURLEXTENSION, "Binaural Beat 5.5HZ", 0, 119, 50);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_creativity, BASEURL + "med_binaural_75" + BASEURLEXTENSION, "Binaural Beat 7.5HZ", 0, 120, 50);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_engery, BASEURL + "med_binaural_8" + BASEURLEXTENSION, "Binaural Beat 8HZ", 0, 121, 50);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_love, BASEURL + "med_binaural_105" + BASEURLEXTENSION, "Binaural Beat 10 .5HZ", 0, 122, 50);
        binauralSoundsList.add(soundModel);
        return binauralSoundsList;
    }

    public static ArrayList<SoundModel> RelaxingCoffeeSounds() {
        ArrayList<SoundModel> coffeeSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_city_musician, BASEURL + "med_oasis_of_calm" + BASEURLEXTENSION, "Musician", 0, 76, 85);
        coffeeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_subway, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, 45);
        coffeeSoundsList.add(soundModel);
        return coffeeSoundsList;
    }
    public static ArrayList<SoundModel> JourneySounds() {
        ArrayList<SoundModel> coffeeSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 50);
        coffeeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 35);
        coffeeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_guzheng, BASEURL + "med_guzheng" + BASEURLEXTENSION, "Guzheng", 0, 70, 60);
        coffeeSoundsList.add(soundModel);
        return coffeeSoundsList;
    }
    public static ArrayList<SoundModel> BeachSounds() {
        ArrayList<SoundModel> beachSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_sea, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, 70);
        beachSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_subway, BASEURL + "med_cafe" + BASEURLEXTENSION, "Cafe", 0, 84, 45);
        beachSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_ducks, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, 60);
        beachSoundsList.add(soundModel);
        return beachSoundsList;
    }
    public static ArrayList<SoundModel> StormSounds() {
        ArrayList<SoundModel> stormSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_nature_strom, BASEURL + "med_storm" + BASEURLEXTENSION, "Storm", 0, 27, 80);
        stormSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, "Bird", 0, 41, 35);
        stormSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_thunder, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, 75);
        stormSoundsList.add(soundModel);
        return stormSoundsList;
    }
    public static ArrayList<SoundModel> ChildrenSounds() {
        ArrayList<SoundModel> childSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_city_farm, BASEURL + "med_farm" + BASEURLEXTENSION, "Farm", 0, 87, 35);
        childSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_kids_playground, BASEURL + "med_kids_playground" + BASEURLEXTENSION, "Kids Playground", 0, 77, 65);
        childSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_birds3, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, 60);
        childSoundsList.add(soundModel);
        return childSoundsList;
    }
    public static ArrayList<SoundModel> ContactSounds() {
        ArrayList<SoundModel> contactSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 30);
        contactSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 10);
        contactSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_harp, BASEURL + "med_harp_music" + BASEURLEXTENSION, "Harp", 0, 64, 65);
        contactSoundsList.add(soundModel);
        return contactSoundsList;
    }
    public static ArrayList<SoundModel> AsianSounds() {
        ArrayList<SoundModel> asianSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 40);
        asianSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_ducks, BASEURL + "med_duck" + BASEURLEXTENSION, "Duck", 0, 20, 25);
        asianSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_violin, BASEURL + "med_violin" + BASEURLEXTENSION, "Violin", 0, 63, 50);
        asianSoundsList.add(soundModel);
        return asianSoundsList;
    }
    public static ArrayList<SoundModel> CaveSounds() {
        ArrayList<SoundModel> caveSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_drops, BASEURL + "med_dripping_water_sound" + BASEURLEXTENSION, "Dripping", 0, 35, 90);
        caveSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_forest, BASEURL + "med_forest" + BASEURLEXTENSION, "Forest", 0, 78, 40);
        caveSoundsList.add(soundModel);
        return caveSoundsList;
    }
    public static ArrayList<SoundModel> SpringSounds() {
        ArrayList<SoundModel> springSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 50);
        springSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_birds3, BASEURL + "med_birds_3" + BASEURLEXTENSION, "Many Birds", 0, 14, 65);
        springSoundsList.add(soundModel);
        return springSoundsList;
    }
    public static ArrayList<SoundModel> DreamSounds() {
        ArrayList<SoundModel> dreamSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 50);
        dreamSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_campfire, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, 25);
        dreamSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_piano, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, 50);
        dreamSoundsList.add(soundModel);
        return dreamSoundsList;
    }
    public static ArrayList<SoundModel> MeditationNatureSounds() {
        ArrayList<SoundModel> meditationNatureSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_waterfall, BASEURL + "med_waterfall_sound" + BASEURLEXTENSION, "Waterfall", 0, 34, 50);
        meditationNatureSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_taiko, BASEURL + "med_taiko" + BASEURLEXTENSION, "Taiko", 0, 72, 65);
        meditationNatureSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_sax, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, 27);
        meditationNatureSoundsList.add(soundModel);
        return meditationNatureSoundsList;
    }
    public static ArrayList<SoundModel> MeditationTempleSounds() {
        ArrayList<SoundModel> meditationTempleSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_traditional_bowls, BASEURL + "med_bowl" + BASEURLEXTENSION, "Bowl", 0, 75, 50);
        meditationTempleSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_temple, BASEURL + "med_temple" + BASEURLEXTENSION, "Temple", 0, 72, 60);
        meditationTempleSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_gong, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, 80);
        meditationTempleSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_om, BASEURL + "med_om" + BASEURLEXTENSION, "Om", 0, 69, 10);
        meditationTempleSoundsList.add(soundModel);
        return meditationTempleSoundsList;
    }
    public static ArrayList<SoundModel> CabinSounds() {
        ArrayList<SoundModel> cabinSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_wind_mountain, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, 20);
        cabinSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_campfire, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, "Firewood Cracking", 0, 60, 100);
        cabinSoundsList.add(soundModel);
        return cabinSoundsList;
    }
    public static ArrayList<SoundModel> DockSounds() {
        ArrayList<SoundModel> dockSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_relax_sax, BASEURL + "med_sax" + BASEURLEXTENSION, "Sax", 0, 66, 80);
        dockSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 40);
        dockSoundsList.add(soundModel);
        return dockSoundsList;
    }
    public static ArrayList<SoundModel> PeaceSounds() {
        ArrayList<SoundModel> peaceSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, "Nightingale", 0, 41, 65);
        peaceSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_gong, BASEURL + "med_gong" + BASEURLEXTENSION, "Gong", 0, 74, 40);
        peaceSoundsList.add(soundModel);
        return peaceSoundsList;
    }
    public static ArrayList<SoundModel> RainySounds() {
        ArrayList<SoundModel> rainySoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_heavy_rain, BASEURL + "med_heavy_rain" + BASEURLEXTENSION, "Heavy Rain", 0, 5, 50);
        rainySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_thunder, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, "Thunder Sound", 0, 3, 80);
        rainySoundsList.add(soundModel);
        return rainySoundsList;
    }
    public static ArrayList<SoundModel> RelaxRainSounds() {
        ArrayList<SoundModel> relaxRainSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_light_rain, BASEURL + "med_light_rain" + BASEURLEXTENSION, "Light Rain", 0, 4, 80);
        relaxRainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_piano, BASEURL + "med_piano" + BASEURLEXTENSION, "Piano", 0, 61, 50);
        relaxRainSoundsList.add(soundModel);
        return relaxRainSoundsList;
    }
    public static ArrayList<SoundModel> RealxBeachSounds() {
        ArrayList<SoundModel> realxBeachSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, "Ocean  Waves", 0, 31, 65);
        realxBeachSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_seagulls, BASEURL + "med_seagulls" + BASEURLEXTENSION, "Seagulls", 0, 19, 30);
        realxBeachSoundsList.add(soundModel);
        return realxBeachSoundsList;
    }
    public static ArrayList<SoundModel> RuralSounds() {
        ArrayList<SoundModel> ruralSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_country_birds2, BASEURL + "med_birds2" + BASEURLEXTENSION, "Birds", 0, 13, 10);
        ruralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_forest, BASEURL + "med_forest" + BASEURLEXTENSION, "med_forest", 0, 78, 50);
        ruralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, "Bird", 0, 41, 60);
        ruralSoundsList.add(soundModel);
        return ruralSoundsList;
    }
    public static ArrayList<SoundModel> DolphinsSounds() {
        ArrayList<SoundModel> dolphinsSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_ocean_whale, BASEURL + "med_whale_sound" + BASEURLEXTENSION, "med_whale_sound", 0, 23, 30);
        dolphinsSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_sea, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, "Sea Waves", 0, 36, 65);
        dolphinsSoundsList.add(soundModel);
        return dolphinsSoundsList;
    }
    public static ArrayList<SoundModel> IndiaSounds() {
        ArrayList<SoundModel> indiaSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_traditional_sitar, BASEURL + "med_sitar" + BASEURLEXTENSION, "Sitar", 0, 71, 75);
        indiaSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, "River", 0, 29, 20);
        indiaSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, "Bird", 0, 12, 40);
        indiaSoundsList.add(soundModel);
        return indiaSoundsList;
    }
    public static ArrayList<SoundModel> WildSounds() {
        ArrayList<SoundModel> wildSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_relax_native, BASEURL + "med_native" + BASEURLEXTENSION, "Native", 0, 68, 70);
        wildSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_mountain, BASEURL + "med_air_mountain" + BASEURLEXTENSION, "Mountain", 0, 52, 10);
        wildSoundsList.add(soundModel);
        return wildSoundsList;
    }
}
