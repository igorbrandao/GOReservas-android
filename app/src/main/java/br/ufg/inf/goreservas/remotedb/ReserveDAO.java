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
public class ReserveDAO {
    private static final String path = "/api/reserve";
    private static RemoteDBConnector remotedb = null;
    private static ReserveDAO instance = null;

    private ReserveDAO(Context context) {
        this.remotedb = RemoteDBConnector.getInstance(context);
    }

    public static synchronized ReserveDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ReserveDAO(context);

        }
        return instance;
    }


    public void createReserve(String client,
                              String business,
                              Date date,
                              String observation,
                              int quantity,
                              double totalValue,
                              final JavaPromise promise){
        JSONObject data = new JSONObject();
        try {
            data.put("client", client);
            data.put("business", business);
            data.put("date", date.toString());
            data.put("observation", observation);
            data.put("quatity", quantity);
            data.put("totalValue", totalValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        remotedb.sendRequest(Request.Method.POST, path, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success"))
                        promise.onSuccess(response);
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
    public void rateReserve(String reserve,
                              String business,
                              int rating,
                              final JavaPromise promise){
        JSONObject data = new JSONObject();
        try {
            data.put("rating", rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        remotedb.sendRequest(Request.Method.POST, path + "/" + reserve + "/" + business, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success"))
                        promise.onSuccess(null);
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

    public void deleteReserve(String reserveID, Date date,
                              final JavaPromise promise){

        remotedb.sendRequest(Request.Method.DELETE, path + "/" + reserveID + "/" + date.getTime(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success"))
                        promise.onSuccess(null);
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

    public void getReserves(String client,
                              final JavaPromise promise){

        remotedb.sendRequest(Request.Method.GET, path + "/" + client, null, new Response.Listener<JSONObject>() {
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

    public void getOneReserve(String id, String client,
                            final JavaPromise promise){

        remotedb.sendRequest(Request.Method.GET, path + "/" + id + "/" + client, null, new Response.Listener<JSONObject>() {
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

    public void getLastReserves(String client,
                              final JavaPromise promise){

        remotedb.sendRequest(Request.Method.PUT, path + "/" + client, null, new Response.Listener<JSONObject>() {
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
