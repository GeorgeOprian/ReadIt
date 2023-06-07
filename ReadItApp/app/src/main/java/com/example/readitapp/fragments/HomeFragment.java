package com.example.readitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.example.readitapp.R;
import com.example.readitapp.api.googlebooks.GoogleBooksAPIBuilder;
import com.example.readitapp.model.googlebooks.VolumesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView books = view.findViewById(R.id.books);
        ImageView boy = view.findViewById(R.id.boy);
        ImageView girl = view.findViewById(R.id.girl);
        TextView textView = view.findViewById(R.id.textView);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        books.setAnimation(animation);
        boy.setAnimation(animation);
        girl.setAnimation(animation);
        textView.setAnimation(animation);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new BooksFragment()).addToBackStack("tag")
                        .commit();
            }
        });

        return view;
    }

}
