package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.readitapp.R;
import com.example.readitapp.model.googlebooks.Item;
import com.example.readitapp.utils.Utils;

public class BookFragment extends Fragment {

    private View view;
    private TextView textView;
    private Item item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book, container, false);

        initView();

        Bundle bundle = getArguments();
        if(bundle != null) {
            item = (Item) bundle.getSerializable(Utils.ITEM);
            textView.setText(item.getVolumeInfo().getTitle());
        }

        return view;
    }

    private void initView() {
        textView = view.findViewById(R.id.textView);
    }
}