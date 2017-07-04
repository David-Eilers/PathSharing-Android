package com.example.eloy.project24app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue requestQueue;
    TextView results;
    String JsonURL = "http://10.0.2.2:5000/";
    String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creeer request queue
        requestQueue = Volley.newRequestQueue(this);
        //zet resultaat in een textview (op de about page)
        results = (TextView) findViewById(R.id.testtext);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, JsonURL+"user",
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            JSONObject obj = response;
                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            String username = obj.getString("username");
                            String email = obj.getString("email");

                            data = "Het werkt";

                            /*
                            data += "Username: " + username +
                                    "Email: " + email;
                            */
                            results.setText(data);
                        }
                        catch (JSONException e) {
                            Log.e("Volley", "Error "+e);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error"+ error);
                    }
                }
        );

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonRequest);

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
        android.app.FragmentManager fragmanager = getFragmentManager();

        if (id == R.id.nav_groups_layout) {
            fragmanager.beginTransaction().replace(R.id.content_frame, new GroupsFragment()).commit();
        } else if (id == R.id.nav_profile_layout) {
            fragmanager.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
        } else if (id == R.id.nav_about_layout) {
            fragmanager.beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
