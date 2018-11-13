package com.example.littlewolf_pc.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.littlewolf_pc.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFriendFragment extends Fragment {

    LinearLayout moldura;


    public ProfileFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_friend, container, false);
        moldura = view.findViewById(R.id.containerCards);

        return view;
    }

}
