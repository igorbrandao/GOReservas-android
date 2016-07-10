package br.ufg.inf.goreservas.remotedb;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import br.ufg.inf.goreservas.utils.JavaPromise;


/**
 * Created by Leonardo on 02/07/2016.
 */
public class BusinessDAO {
    private static final String path = "/api/business";
    private static RemoteDBConnector remotedb = null;
    private static BusinessDAO instance = null;

    private BusinessDAO(Context context) {
        this.remotedb = RemoteDBConnector.getInstance(context);
    }

    public static synchronized BusinessDAO getInstance(Context context) {
        if (instance == null) {
            instance = new BusinessDAO(context);

        }
        return instance;
    }


    public void getBestBusiness(final JavaPromise promise){
        remotedb.sendRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success"))
                        promise.onSuccess(response.getJSONObject("data"));
                    else
                        promise.onError(response.getString("reason"));
                } catch (JSONException e) {
                    promise.onError("error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                promise.onError("error");
            }
        });
    }

    public void getBusiness(String business, final JavaPromise promise){
        remotedb.sendRequest(Request.Method.GET, path + "/" + business, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success"))
                        promise.onSuccess(response.getJSONObject("data"));
                    else
                        promise.onError(response.getString("reason"));
                } catch (JSONException e) {
                    promise.onError("error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                promise.onError("error");
            }
        });
    }

    public void searchBusiness(String client, String search, String filter, final JavaPromise promise){
        remotedb.sendRequest(Request.Method.GET, path + "/" + client + "/" + search + "/" + filter, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success"))
                        promise.onSuccess(response.getJSONObject("data"));
                    else
                        promise.onError(response.getString("reason"));
                } catch (JSONException e) {
                    promise.onError("error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                promise.onError("error");
            }
        });
    }
}
