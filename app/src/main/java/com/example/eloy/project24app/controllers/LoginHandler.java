package com.example.eloy.project24app.controllers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eloy.project24app.JWTUtils;

import android.util.Base64;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eloy on 12-7-2017.
 */

public class LoginHandler {
    private static String JsonURL = "http://10.0.2.2:5000/";
    private static RequestQueue requestQueue;
    private static ArrayList<String> array;
    private static String username;
    private static String password;
    private static int id;
    private static String token;
    private static JSONObject tokenDecoded;


    public LoginHandler(String usernameS, String passwordS, Context context){
        requestQueue = Volley.newRequestQueue(context);
        username = usernameS;
        password = passwordS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        JsonObjectRequest loginRequest = new JsonObjectRequest(JsonURL+"login", new JSONObject(params),

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Login Response", response.toString());
                            token = response.getString("token");
                            try {
                                tokenDecoded = JWTUtils.decoded(token);
                                //tokenDecoded teruggeven aan MainActivity
                            } catch(Exception e){
                                Log.e("JWTUtils", "Error: "+e);
                            }
                            Log.d("Login Token", tokenDecoded.toString());
                        }
                        catch (JSONException e) {
                            Log.e("Volley", "Error "+e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Login", "Error: " + error);
                    }
                }
        );

        requestQueue.add(loginRequest);

    }

    public static JSONObject getToken(){
        return tokenDecoded;
    }


}
