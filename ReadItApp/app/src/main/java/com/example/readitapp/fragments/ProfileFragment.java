package com.example.readitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.readitapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private View view;
    private TextInputEditText nume;
    private TextInputEditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView();
        fillControls();

        return view;
    }

    private void initView() {
        nume = view.findViewById(R.id.textInputEditTextNume);
        email = view.findViewById(R.id.textInputEditTextEmail);
    }

    private void fillControls() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        nume.setText(user.getDisplayName());
        email.setText(user.getEmail());
    }
}
