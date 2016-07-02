package br.ufg.inf.goreservas.utils;

import org.json.JSONObject;

/**
 * Created by Leonardo on 02/07/2016.
 */
public interface JavaPromise {

    public void onSuccess(JSONObject response);

    public void onError(String error);
}
