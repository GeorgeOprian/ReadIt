package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.SubscriptionDto;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;

public class SubscriptionFragment extends Fragment {

    private View view;
    private TextView statusValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_subscription, container, false);

        initView();

        return view;
    }

    private void initView() {
        statusValue = view.findViewById(R.id.status_value);
    }

    private void getSubscription() {
        Call<SubscriptionDto> call = WebServerAPIBuilder.getInstance().getAvailability(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        call.enqueue(new Callback<SubscriptionDto>() {
            @Override
            public void onResponse(Call<SubscriptionDto> call, retrofit2.Response<SubscriptionDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}