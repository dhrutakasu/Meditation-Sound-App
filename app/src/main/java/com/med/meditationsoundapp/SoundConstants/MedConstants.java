package com.med.meditationsoundapp.SoundConstants;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;

import com.karumi.dexter.PermissionToken;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.SoundModel;

import java.util.ArrayList;

public class MedConstants {


    public static final String BROADCAST_MAIN = "BROADCAST_MAIN";
    private static String BASEURL = "https://anshinfotech.in/sound/";
    private static String BASEURLEXTENSION = ".mp3";

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
        SoundModel soundModel = new SoundModel(R.drawable.ic_rain, BASEURL + "med_rain" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_car, BASEURL + "med_rain_on_car" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_umbrella, BASEURL + "med_rain_on_umbrella_sound" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_thunder, BASEURL + "med_thunder_sound" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_light_rain, BASEURL + "med_light_rain" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_heavy_rain, BASEURL + "med_heavy_rain" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_distant_thunder, BASEURL + "med_distant_thunder" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_tent, BASEURL + "med_rain_on_tent_sound" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_window, BASEURL + "med_rain_on_window_sound" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_roof, BASEURL + "med_rain_roof" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_plant, BASEURL + "med_rain_on_metaplates" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_rain_leaves, BASEURL + "med_rain_leaves" + BASEURLEXTENSION, 0);
        rainSoundsList.add(soundModel);
        return rainSoundsList;
    }

    public static ArrayList<SoundModel> CountrySounds() {
        ArrayList<SoundModel> countrySoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_country_blackbirds, BASEURL + "med_birds" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_birds2, BASEURL + "med_birds2" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_birds3, BASEURL + "med_birds_3" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cat_purring, BASEURL + "med_cat_purring" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cat, BASEURL + "med_cat" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cats, BASEURL + "med_cat2" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_dogs, BASEURL + "med_dogs" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_seagulls, BASEURL + "med_seagulls" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_ducks, BASEURL + "med_duck" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_chicken, BASEURL + "med_chicken_coop" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_cicadas, BASEURL + "med_cicada_chirping" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_ocean_whale, BASEURL + "med_whale_sound" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_country_elephant, BASEURL + "med_elephant" + BASEURLEXTENSION, 0);
        countrySoundsList.add(soundModel);
        return countrySoundsList;
    }

    public static ArrayList<SoundModel> NatureSounds() {
        ArrayList<SoundModel> natureSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_nature_snow_falling, BASEURL + "med_snow_falling_sound" + BASEURLEXTENSION, 0);
        natureSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_nature_snow_walking, BASEURL + "med_snow_walking" + BASEURLEXTENSION, 0);
        natureSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_nature_strom, BASEURL + "med_storm" + BASEURLEXTENSION, 0);
        natureSoundsList.add(soundModel);
        return natureSoundsList;
    }

    public static ArrayList<SoundModel> WaterSounds() {
        ArrayList<SoundModel> waterSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_water_underwater, BASEURL + "med_underwater" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_river, BASEURL + "med_flowing_water_sound" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_bubble, BASEURL + "med_bubble" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_ocean_wave, BASEURL + "med_ocean_waves_sound" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_seagull, BASEURL + "med_seagull_sound" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_creek, BASEURL + "med_creek" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_waterfall, BASEURL + "med_waterfall_sound" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_drops, BASEURL + "med_dripping_water_sound" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_water_sea, BASEURL + "med_sea_waves_sound" + BASEURLEXTENSION, 0);
        waterSoundsList.add(soundModel);
        return waterSoundsList;
    }

    public static ArrayList<SoundModel> NightSounds() {
        ArrayList<SoundModel> nightSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_night_crickets, BASEURL + "med_cricket_chirping" + BASEURLEXTENSION, 0);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_owls, BASEURL + "med_owl_hooting" + BASEURLEXTENSION, 0);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_wolves, BASEURL + "med_wolf_howling" + BASEURLEXTENSION, 0);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_frogs, BASEURL + "med_frog_croaking" + BASEURLEXTENSION, 0);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_whippoorwill, BASEURL + "med_birds3" + BASEURLEXTENSION, 0);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_forest_night, BASEURL + "med_forest_nightfall" + BASEURLEXTENSION, 0);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_goodnight, BASEURL + "med_goodnight" + BASEURLEXTENSION, 0);
        nightSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_night_grasswalk, BASEURL + "med_grassland_ambience" + BASEURLEXTENSION, 0);
        nightSoundsList.add(soundModel);
        return nightSoundsList;
    }

    public static ArrayList<SoundModel> WindSounds() {
        ArrayList<SoundModel> windSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_wind_light, BASEURL + "med_air_light_wind" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_breeze, BASEURL + "med_breeze" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_gale, BASEURL + "med_gale" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_strong, BASEURL + "med_air_strong" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_howling, BASEURL + "med_wind_howling" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_polar, BASEURL + "med_wind_polar" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_sound, BASEURL + "med_wind_sound" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_mountain, BASEURL + "med_air_mountain" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_under_door, BASEURL + "med_air_door" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_trees, BASEURL + "med_air_tree" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_bamboo, BASEURL + "med_air_bamboo" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_dunes, BASEURL + "med_air_dune" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_dessert, BASEURL + "med_air_desert" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_flag, BASEURL + "med_air_flag" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_airplane, BASEURL + "med_airplane" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_wind_campfire, BASEURL + "med_firewood_crackling" + BASEURLEXTENSION, 0);
        windSoundsList.add(soundModel);
        return windSoundsList;
    }

    public static ArrayList<SoundModel> RelaxingSounds() {
        ArrayList<SoundModel> relaxingSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_relax_piano, BASEURL + "med_piano" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_guitar, BASEURL + "med_guitar" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_violin, BASEURL + "med_violin" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_harp, BASEURL + "med_harp_music" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_flute, BASEURL + "med_flute" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_sax, BASEURL + "med_sax" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_native, BASEURL + "med_native" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_relax_qulet_time, BASEURL + "med_quiet" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_om, BASEURL + "med_om" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_guzheng, BASEURL + "med_guzheng" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_sitar, BASEURL + "med_sitar" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_taiko, BASEURL + "med_taiko" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_temple, BASEURL + "med_temple" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_gong, BASEURL + "med_gong" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_traditional_bowls, BASEURL + "med_bowl" + BASEURLEXTENSION, 0);
        relaxingSoundsList.add(soundModel);
        return relaxingSoundsList;
    }

    public static ArrayList<SoundModel> CitySounds() {
        ArrayList<SoundModel> citySoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_city_musician, BASEURL + "med_oasis_of_calm" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_kids_playground, BASEURL + "med_kids_playground" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_forest, BASEURL + "med_forest" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_cars, BASEURL + "med_cars_passing_by" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_firework, BASEURL + "med_distant_fireworks" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_firework2, BASEURL + "med_distant_fireworks2" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_firework3, BASEURL + "med_firework3" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_traffic, BASEURL + "med_traffic" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_subway, BASEURL + "med_cafe" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_crowd, BASEURL + "med_crowd_noise" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_drive, BASEURL + "med_drive" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_farm, BASEURL + "med_farm" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_electric, BASEURL + "med_electric" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_wiper, BASEURL + "med_windshield_wiper_sound" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_turn_signal, BASEURL + "med_turn_signal" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_construction_site, BASEURL + "med_construction_site_ambience" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_truck, BASEURL + "med_truckengine" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_train, BASEURL + "med_train" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_city_train_station, BASEURL + "med_train_station" + BASEURLEXTENSION, 0);
        citySoundsList.add(soundModel);
        return citySoundsList;
    }

    public static ArrayList<SoundModel> HomeSounds() {
        ArrayList<SoundModel> homeSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_home_clock, BASEURL + "med_clock_tick" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_fan, BASEURL + "med_fan_sound" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_air_conditioner, BASEURL + "med_air_conditioner" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_hair_dryer, BASEURL + "med_hair_dryer_noise" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_vaccume, BASEURL + "med_vacuum_cleaner_noise" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_wind_chime, BASEURL + "med_wind_chime_sound" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_washing, BASEURL + "med_washing_machine" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_washing_dryer, BASEURL + "med_dryer" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_faucet, BASEURL + "med_faucet" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_frying_pan, BASEURL + "med_frying_pan" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_mechanical_clock, BASEURL + "med_mechanical_clock" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_reading, BASEURL + "med_reading" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_keyboard, BASEURL + "med_keyboard_sound" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_radio, BASEURL + "med_radio" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_soup_pot, BASEURL + "med_soup_pot" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_home_musica_box, BASEURL + "med_music_box" + BASEURLEXTENSION, 0);
        homeSoundsList.add(soundModel);
        return homeSoundsList;
    }

    public static ArrayList<SoundModel> NoiseSounds() {
        ArrayList<SoundModel> noiseSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_noise_heartbeat, BASEURL + "med_heartbeat" + BASEURLEXTENSION, 0);
        noiseSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_noise_white, BASEURL + "med_white_noise" + BASEURLEXTENSION, 0);
        noiseSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_noise_pink, BASEURL + "med_pink_noise" + BASEURLEXTENSION, 0);
        noiseSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_noise_red, BASEURL + "med_brown_noise" + BASEURLEXTENSION, 0);
        noiseSoundsList.add(soundModel);
        return noiseSoundsList;
    }

    public static ArrayList<SoundModel> BinauralSounds() {
        ArrayList<SoundModel> binauralSoundsList = new ArrayList<>();
        SoundModel soundModel = new SoundModel(R.drawable.ic_binaural_headache, BASEURL + "med_binaural_05" + BASEURLEXTENSION, 0);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_relaxation, BASEURL + "med_binaural_25" + BASEURLEXTENSION, 0);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_anxiety, BASEURL + "med_binaural_35" + BASEURLEXTENSION, 0);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_meditation, BASEURL + "med_binaural_45" + BASEURLEXTENSION, 0);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_intuition, BASEURL + "med_binaural_55" + BASEURLEXTENSION, 0);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_creativity, BASEURL + "med_binaural_75" + BASEURLEXTENSION, 0);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_engery, BASEURL + "med_binaural_8" + BASEURLEXTENSION, 0);
        binauralSoundsList.add(soundModel);
        soundModel = new SoundModel(R.drawable.ic_binaural_love, BASEURL + "med_binaural_105" + BASEURLEXTENSION, 0);
        binauralSoundsList.add(soundModel);
        return binauralSoundsList;
    }
}
