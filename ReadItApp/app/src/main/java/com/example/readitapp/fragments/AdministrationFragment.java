package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.readitapp.R;
import com.example.readitapp.api.googlebooks.GoogleBooksAPIBuilder;
import com.example.readitapp.model.googlebooks.VolumesResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;

public class AdministrationFragment extends Fragment {

    private View view;
    private Button searchButton;
    private TextInputEditText title;
    private TextInputEditText author;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_administration, container, false);

        initView();
        searchButton.setOnClickListener(view1 -> callGoogleAPI());

        return view;
    }

    private void initView() {
        searchButton = view.findViewById(R.id.search_button);
        title = view.findViewById(R.id.textInputEditTextTitle);
        author = view.findViewById(R.id.textInputEditTextAuthor);
    }

    private void callGoogleAPI() {

        String searchValue = title.getText() + "+inauthor:" + author.getText();

        Call<VolumesResponse> call = GoogleBooksAPIBuilder.getInstance().getVolumes(searchValue, GoogleBooksAPIBuilder.API_KEY);

        call.enqueue(new Callback<VolumesResponse>() {
            @Override
            public void onResponse(Call<VolumesResponse> call, retrofit2.Response<VolumesResponse> response) {

                if (response.isSuccessful()) {


                }
            }

            @Override
            public void onFailure(Call<VolumesResponse> call, Throwable t) {

            }
        });
    }
}