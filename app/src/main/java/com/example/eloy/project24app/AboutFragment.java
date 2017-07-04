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

public class AboutFragment extends Fragment {

    View view;
    String string;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.about_layout, container,false);
        string = this.getArguments().getString("text");
        TextView text = (TextView) container.findViewById(R.id.aboutText);
        text.setText(string);
        return view;
    }
}
