package com.example.littlewolf_pc.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.littlewolf_pc.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermoFragment extends Fragment {


    public TermoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_termo, container, false);
    }

}
