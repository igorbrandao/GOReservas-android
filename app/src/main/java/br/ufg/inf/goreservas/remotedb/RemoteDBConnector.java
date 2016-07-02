package br.ufg.inf.goreservas.remotedb;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Leonardo on 02/07/2016.
 */
class RemoteDBConnector {
    private final String serverURL = "http://192.168.0.10:3030";
    private static RemoteDBConnector instance = null;
    private static RequestQueue queue = null;

    private RemoteDBConnector(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public static synchronized RemoteDBConnector getInstance(Context context) {
        if (instance == null) {
            instance = new RemoteDBConnector(context);

        }
        return instance;
    }

    public void sendRequest(int method, String path, JSONObject data, Response.Listener<JSONObject> onSuccess, Response.ErrorListener onError){
        JsonObjectRequest jsonRequest = new JsonObjectRequest(method, this.serverURL + path, data, onSuccess, onError);
        queue.add(jsonRequest);
    }
}
