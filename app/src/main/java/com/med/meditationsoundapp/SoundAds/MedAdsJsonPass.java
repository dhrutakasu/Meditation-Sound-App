package com.med.meditationsoundapp.SoundAds;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MedAdsJsonPass extends Application {
    private static MedAdsJsonPass medAdsJsonPass;
    private RequestQueue requestQueue;
    private static Context context;

    private MedAdsJsonPass(Context context) {
        MedAdsJsonPass.context = context;
        requestQueue = getMedRequestQueue();
    }

    public static synchronized MedAdsJsonPass getMedInstance(Context context) {
        if (medAdsJsonPass == null) {
            medAdsJsonPass = new MedAdsJsonPass(context);
        }
        return medAdsJsonPass;
    }

    public RequestQueue getMedRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToMedRequestQueue(Request<T> req) {
        getMedRequestQueue().add(req);
    }
}
