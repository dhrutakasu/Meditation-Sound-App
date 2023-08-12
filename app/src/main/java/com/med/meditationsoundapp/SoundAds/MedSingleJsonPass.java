package com.med.meditationsoundapp.SoundAds;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MedSingleJsonPass extends Application {
    private static MedSingleJsonPass medSingleJsonPass;
    private RequestQueue requestQueue;
    private static Context context;

    private MedSingleJsonPass(Context context) {
        MedSingleJsonPass.context = context;
        requestQueue = getMedRequestQueue();
    }

    public static synchronized MedSingleJsonPass getMedInstance(Context context) {
        if (medSingleJsonPass == null) {
            medSingleJsonPass = new MedSingleJsonPass(context);
        }
        return medSingleJsonPass;
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
