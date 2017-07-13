package com.example.eloy.project24app;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eloy.project24app.controllers.LoginHandler;
import com.example.eloy.project24app.fragments.AboutFragment;
import com.example.eloy.project24app.fragments.GroupsFragment;
import com.example.eloy.project24app.fragments.ProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {
    private static EditText usernameField;
    private static EditText passwordField;
    private static Context context;
    private static JSONObject token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        context = this;


        usernameField = (EditText) findViewById(R.id.login_username);
        passwordField = (EditText) findViewById(R.id.login_password);
        Button login = (Button) findViewById(R.id.login_button);
        Button register = (Button) findViewById(R.id.register_button);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LoginHandler login = new LoginHandler(usernameField.getText().toString(), passwordField.getText().toString(), context);
                Thread t = new Thread(){
                    public void run(){
                        try {
                            Thread.sleep(2000);
                            token = LoginHandler.getToken();
                            Log.d("Token Main",token.toString());
                            Looper.prepare();
                            createDrawer();
                            //TODO
                        } catch (Exception e){
                            Log.e("Token", "Error: Couldn't log in " + e);
                        }
                    }
                };
                t.start();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

    }

    public static void createDrawer(){
        new Drawer();
    }



}
