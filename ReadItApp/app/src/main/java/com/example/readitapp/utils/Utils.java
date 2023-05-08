package com.example.readitapp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;

public class Utils {

    public static final String USER_ADMIN = "readitapp.adm@gmail.com";
    public static final String ITEM = "item";
    public static final String BOOK_LIST_DTO= "BookListDto";

    public static void hideKeyboard(Fragment fragment) {
        View view = fragment.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
