package com.example.eloy.project24app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eloy.project24app.R;

/**
 * Created by Eloy on 23-6-2017.
 */

public class GroupsFragment extends Fragment {

    View view;
    String groupname;
    String discription;
    //String groupList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.groups_layout, container,false);
        groupname = this.getArguments().getString("groupname");
        discription = this.getArguments().getString("description");

        //groupList = this.getArguments().getString("groups");

        TextView text = (TextView) view.findViewById(R.id.groupText);
        text.setText(groupname);
        return view;
    }
}
