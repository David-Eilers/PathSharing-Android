package com.example.eloy.project24app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eloy.project24app.R;

import java.util.ArrayList;

/**
 * Created by Eloy on 23-6-2017.
 */

public class ProfileFragment extends Fragment {

    View view;
    String username;
    String email;
    ArrayList<String> array;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_layout, container, false);
        array = this.getArguments().getStringArrayList("profile");
        username = array.get(0);
        email = array.get(1);
        TextView nameText = (TextView) view.findViewById(R.id.profileName);
        TextView emailText = (TextView) view.findViewById(R.id.profileEmail);
        nameText.setText(username);
        emailText.setText(email);
        return view;
    }
}
