package com.example.eloy.project24app;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Eloy on 7-7-2017.
 */

public class Connection extends Thread{
    private static String JsonURL = "http://10.0.2.2:5000/";
    private static RequestQueue requestQueue;
    private static ArrayList<String> array;

    private static String aboutMessage;
    private static String username;
    private static String email;
    private static String groupname;
    private static String description;

    public Connection(Context context){
        requestQueue = Volley.newRequestQueue(context);

         StringRequest aboutRequest = new StringRequest(Request.Method.GET, JsonURL+"about",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            aboutMessage = obj.getString("");
                        }
                        catch (JSONException e) {
                            Log.e("Volley", "Error "+e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + error);
                    }
                }
        );

        StringRequest groupsRequest = new StringRequest(Request.Method.GET, JsonURL+"groups/10",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            description = obj.getString("description");
                            groupname = obj.getString("name");

                        }
                        catch (JSONException e) {
                            Log.e("Volley", "Error "+e);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.e("Volley", "Error"+ e);
                    }
                }
        );

        StringRequest profileRequest = new StringRequest(Request.Method.GET, JsonURL+"user/10",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            username = obj.getString("username");
                            email = obj.getString("email");

                        }
                        catch (JSONException e) {
                            Log.e("Volley", "Error "+e);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.e("Volley", "Error"+ e);
                    }
                }
        );

        requestQueue.add(groupsRequest);
        requestQueue.add(aboutRequest);
        requestQueue.add(profileRequest);


    }


    public static void login(){
        //TODO
    }

    public ArrayList<String> getGroups(){
        array = new ArrayList<>();
        array.add(groupname);
        array.add(description);

        return array;
    }

    public synchronized ArrayList<String> getAbout(){
        array = new ArrayList<>();
        array.add(aboutMessage);
        return array;
    }

    public ArrayList<String> getProfile(){
        array= new ArrayList<>();
        array.add(username);
        array.add(email);
        return array;
    }

}
