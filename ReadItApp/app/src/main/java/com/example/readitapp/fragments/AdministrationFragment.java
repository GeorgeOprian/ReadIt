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

import retrofit2.Call;
import retrofit2.Callback;

public class AdministrationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_administration, container, false);

        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(view1 -> callGoogleAPI());

        return view;
    }

    private void callGoogleAPI() {

        Call<VolumesResponse> call = GoogleBooksAPIBuilder.getInstance().getVolumes("flowers+inauthor:keyes", GoogleBooksAPIBuilder.API_KEY);

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