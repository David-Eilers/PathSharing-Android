package com.example.eloy.project24app;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue requestQueue;
    TextView results;
    String JsonURL = "http://10.0.2.2:5000/";
    Bundle bundle = new Bundle();

    GroupsFragment groupsFragment = new GroupsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    AboutFragment aboutFragment = new AboutFragment();

    android.app.FragmentManager fragmanager = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        //StringRequest voor de about pagina

        StringRequest aboutRequest = new StringRequest(Request.Method.GET, JsonURL+"about",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);

                            String message = obj.getString("message:");
                            Log.d("Message: ", message);



                            Log.d("String", message);

                            bundle.putString("aboutMessage",message);
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

        //StringRequest voor de profiel pagina

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

                            bundle.putString("username",username);
                            bundle.putString("email", email);
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

        //StringRequest voor de groepen pagina

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

                            bundle.putString("groupname",groupname);
                            bundle.putString("description", description);
                            //bundle.putString("groups", response);
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




        aboutRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(aboutRequest);
        requestQueue.add(profileRequest);
        requestQueue.add(groupsRequest);

        //groupsFragment.setArguments(bundle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fragmanager.beginTransaction().replace(R.id.content_frame, groupsFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_groups_layout) {
            groupsFragment.setArguments(bundle);
            fragmanager.beginTransaction().replace(R.id.content_frame, groupsFragment).commit();
        } else if (id == R.id.nav_profile_layout) {
            fragmanager.beginTransaction().replace(R.id.content_frame, profileFragment).commit();
            profileFragment.setArguments(bundle);
        } else if (id == R.id.nav_about_layout) {
            aboutFragment.setArguments(bundle);
            fragmanager.beginTransaction().replace(R.id.content_frame, aboutFragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
