package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.googlebooks.ImageLinks;
import com.example.readitapp.model.googlebooks.Item;
import com.example.readitapp.model.googlebooks.VolumeInfo;
import com.example.readitapp.model.webserver.InputBookModel;
import com.example.readitapp.model.webserver.WebServerModel;
import com.example.readitapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;

public class BookFragment extends Fragment {

    private View view;
    private Item item;
    private ImageView poster;
    private TextView title;
    private TextView authors;
    private TextView rating;
    private Button addButton;
    private TextInputEditText inStockTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book, container, false);

        initView();

        Bundle bundle = getArguments();
        if(bundle != null) {
            item = (Item) bundle.getSerializable(Utils.ITEM);
            fillBookCardView();
        }

        addButton.setOnClickListener(view -> callWebServerAPI());

        return view;
    }

    private void fillBookCardView() {
        VolumeInfo volumeInfo = item.getVolumeInfo();
        ImageLinks imageLinks = volumeInfo.getImageLinks();
        if (imageLinks != null) {
            Picasso.get().load(imageLinks.getSmallThumbnail()).into(poster);
        }
        title.setText(volumeInfo.getTitle());

        if (volumeInfo.getAuthors() != null && !volumeInfo.getAuthors().isEmpty()) {
            authors.setText(String.join(",", volumeInfo.getAuthors()));
        } else {
            authors.setText("");
        }

        if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() != 0) {
            rating.setText(volumeInfo.getRatingsCount().toString());
        } else {
            rating.setText("");
        }
    }

    private void initView() {
        poster = view.findViewById(R.id.poster);
        title = view.findViewById(R.id.title);
        authors = view.findViewById(R.id.author_value);
        rating = view.findViewById(R.id.rating_value);
        addButton = view.findViewById(R.id.add_button);
        inStockTextView = view.findViewById(R.id.textInputLayoutStock);
    }

    private void callWebServerAPI() {

        InputBookModel inputBookModel = createInputBookModel();
        Call<WebServerModel> call = WebServerAPIBuilder.getInstance().test();

        call.enqueue(new Callback<InputBookModel>() {
            @Override
            public void onResponse(Call<InputBookModel> call, retrofit2.Response<InputBookModel> response) {

//                if (response.isSuccessful()) {
//                    Utils.hideKeyboard(BookFragment.this);
//                    Toast.makeText(getContext(), response.body().getTest(), Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onFailure(Call<InputBookModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    private InputBookModel createInputBookModel() {
        String inStock = inStockTextView.getText().toString();
        int inStockValue;
        if (inStock != null) {
            inStockValue = Integer.valueOf(inStock);
        } else {
            inStockValue = 0;
        }
        InputBookModel inputBookModel = new InputBookModel(item, inStockValue);
        return inputBookModel;
    }

}