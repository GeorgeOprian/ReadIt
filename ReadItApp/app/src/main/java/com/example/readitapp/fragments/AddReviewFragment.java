package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.readitapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddReviewFragment extends Fragment {

    private View view;
    private RatingBar ratingBar;
    private TextInputEditText content;
    private Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_review, container, false);

        initView();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void initView() {
        ratingBar = view.findViewById(R.id.rating_bar);
        content = view.findViewById(R.id.textInputEditTextLike);
        send = view.findViewById(R.id.sendbtn);
    }
}