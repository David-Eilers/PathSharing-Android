package com.example.eloy.project24app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eloy.project24app.fragments.AboutFragment;
import com.example.eloy.project24app.fragments.GroupsFragment;
import com.example.eloy.project24app.fragments.ProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Connection connection;
    Bundle bundle = new Bundle();

    GroupsFragment groupsFragment = new GroupsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    AboutFragment aboutFragment = new AboutFragment();

    android.app.FragmentManager fragmanager = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        connection = new Connection(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmanager.beginTransaction().show(groupsFragment).commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        if (id == R.id.nav_groups_layout) {
            bundle.putStringArrayList("groups",connection.getGroups());
            groupsFragment.setArguments(bundle);
            fragmanager.beginTransaction().replace(R.id.content_frame, groupsFragment).commit();
        } else if (id == R.id.nav_profile_layout) {
            bundle.putStringArrayList("profile",connection.getProfile());
            fragmanager.beginTransaction().replace(R.id.content_frame, profileFragment).commit();
            profileFragment.setArguments(bundle);
        } else if (id == R.id.nav_about_layout) {
            bundle.putStringArrayList("about",connection.getAbout());
            aboutFragment.setArguments(bundle);
            fragmanager.beginTransaction().replace(R.id.content_frame, aboutFragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
