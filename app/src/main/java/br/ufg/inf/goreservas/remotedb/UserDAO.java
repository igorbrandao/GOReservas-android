package br.ufg.inf.goreservas.remotedb;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufg.inf.goreservas.utils.JavaPromise;


/**
 * Created by Leonardo on 02/07/2016.
 */
public class UserDAO {
    private static final String path = "/api/users";
    private static RemoteDBConnector remotedb = null;
    private static UserDAO instance = null;

    private UserDAO(Context context) {
        this.remotedb = RemoteDBConnector.getInstance(context);
    }

    public static synchronized UserDAO getInstance(Context context) {
        if (instance == null) {
            instance = new UserDAO(context);

        }
        return instance;
    }

    public void authenticateUser(String email, String password, final JavaPromise promise){
        JSONObject data = new JSONObject();
        try {
            data.put("email", email);
            data.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        remotedb.sendRequest(Request.Method.POST, path, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                promise.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                promise.onError(error.toString());
            }
        });
    }
    public void createUser(String name, String email, String password, final JavaPromise promise){
        JSONObject data = new JSONObject();
        try {
            data.put("name", name);
            data.put("email", email);
            data.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        remotedb.sendRequest(Request.Method.PUT, path, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                promise.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                promise.onError(error.toString());
            }
        });
    }
    public void updateUser(String name, String email, String password, final JavaPromise promise){
        JSONObject data = new JSONObject();
        try {
            data.put("name", name);
            data.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        remotedb.sendRequest(Request.Method.PUT, path + "/" + email, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                promise.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                promise.onError(error.toString());
            }
        });
    }
}
