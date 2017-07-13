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

    public Connection(Context context){
        requestQueue = Volley.newRequestQueue(context);

        final StringRequest aboutRequest = new StringRequest(Request.Method.GET, JsonURL+"about",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            aboutMessage = obj.getString("");

                            Log.d("Message: ", aboutMessage);

                            Log.d("String", aboutMessage);

                            //array.add(message);
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

                            String description = obj.getString("Description:");
                            String groupname = obj.getString("Group name:");



                            Log.d("groupname: ", groupname);
                            Log.d("description: ", description);

                            //bundle.putString("groupname",groupname);
                            //bundle.putString("description", description);
                            //bundle.putString("groups", response);
                            //array = new String[]{groupname, description};
                            //array.add(groupname);
                            //array.add(description);
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

        StringRequest profileRequest = new StringRequest(Request.Method.GET, JsonURL+"user",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            String username = obj.getString("username:");
                            String email = obj.getString("email:");

                            Log.d("Username: ", username);
                            Log.d("Email: ", email);

                            //bundle.putString("username",username);
                            //bundle.putString("email", email);
                            //array.add(username);
                            //array.add(email);
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
        array.removeAll(array);


        return array;
    }

    public synchronized ArrayList<String> getAbout(){
        //array.removeAll(array);
        //RequestFuture<String> future = RequestFuture.newFuture();
        array = new ArrayList<>();
        array.add(aboutMessage);
        Log.d("Array",array.toString());
        return array;
    }

    public ArrayList<String> getProfile(){
        array.removeAll(array);


        return array;
    }

    private synchronized ArrayList<String> getArray(StringRequest  request){
        requestQueue.add(request);
        Log.d("array", array.toString());
        return array;
    }
}
