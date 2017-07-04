package com.example.eloy.project24app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Eloy on 23-6-2017.
 */

public class ProfileFragment extends Fragment {

    View view;
    String username;
    String email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_layout, container, false);
        username = this.getArguments().getString("username");
        email = this.getArguments().getString("email");
        TextView nameText = (TextView) view.findViewById(R.id.profileName);
        TextView emailText = (TextView) view.findViewById(R.id.profileEmail);
        nameText.setText(username);
        emailText.setText(email);
        return view;
    }
}
