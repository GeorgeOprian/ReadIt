package com.example.readitapp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.readitapp.activities.MainActivity;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.user.output.UserDto;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;

public class Utils {

    public static final String USER_ADMIN = "readitapp.adm@gmail.com";
    public static final String BOOK = "book";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static UserDto currentUser = new UserDto();

    public static void hideKeyboard(Fragment fragment) {
        View view = fragment.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void getUserDetails() {
        Call<UserDto> call = WebServerAPIBuilder.getInstance().getUserDetails(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, retrofit2.Response<UserDto> response) {
                if (response.isSuccessful()) {
                    Utils.currentUser.setUserId(response.body().getUserId());
                    Utils.currentUser.setAddressDto(response.body().getAddressDto());
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
            }
        });
    }
}
