package com.example.readitapp.fragments;

import android.os.Bundle;

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
import com.example.readitapp.model.webserver.book.response.BookDto;
import com.example.readitapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBookDetailsFragment extends Fragment {

    private View view;
    private BookDto bookDto;
    private ImageView poster;
    private TextView title;
    private TextView authors;
    private TextView rating;
    private Button saveButton;
    private TextInputEditText inStockTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book_details_adm, container, false);

        initView();

        Bundle bundle = getArguments();
        if(bundle != null) {
            bookDto = (BookDto) bundle.getSerializable(Utils.BOOK_ID);
            fillBookCardView();
        }

        if (bundle.getSerializable(Utils.UPDATE) != null) {
            saveButton.setOnClickListener(view -> updateBook());
        }
        if (bundle.getSerializable(Utils.INSERT) != null) {
            saveButton.setOnClickListener(view -> insertBook());
        }

        return view;
    }

    private void updateBook() {
        String inStock = inStockTextView.getText().toString();
        int inStockValue;
        if (inStock != null) {
            inStockValue = Integer.valueOf(inStock);
        } else {
            inStockValue = 0;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("inStock", inStockValue);
        Call<BookDto> call = WebServerAPIBuilder.getInstance().updateBook(bookDto.getBookId(), map);
        Utils.hideKeyboard(AdminBookDetailsFragment.this);

        call.enqueue(new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Book updated", Toast.LENGTH_LONG).show();
                }
                getFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillBookCardView() {
        Picasso.get().load(bookDto.getThumbnail().getSmallThumbnail()).into(poster);
        title.setText(bookDto.getTitle());
        if (bookDto.getAuthor() != null && !bookDto.getAuthor().isEmpty()) {
            authors.setText( bookDto.getAuthor());
        } else {
            authors.setText("");
        }
        if (bookDto.getRatingsCount() != null && bookDto.getRatingsCount() != 0) {
            rating.setText(bookDto.getRatingsCount().toString());
        } else {
            rating.setText("");
        }

        if (bookDto.getInStock() != null) {
            inStockTextView.setText(bookDto.getInStock().toString());
        }
    }

    private void initView() {
        poster = view.findViewById(R.id.poster);
        title = view.findViewById(R.id.title);
        authors = view.findViewById(R.id.author_value);
        rating = view.findViewById(R.id.category_value);
        saveButton = view.findViewById(R.id.save_button);
        inStockTextView = view.findViewById(R.id.textInputEditTextStock);
    }

    private void insertBook() {

        String inStock = inStockTextView.getText().toString();
        int inStockValue;
        if (inStock != null) {
            inStockValue = Integer.valueOf(inStock);
        } else {
            inStockValue = 0;
        }

        bookDto.setInStock(inStockValue);

        Call<BookDto> call = WebServerAPIBuilder.getInstance().addBook(bookDto);
        Utils.hideKeyboard(AdminBookDetailsFragment.this);

        call.enqueue(new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), response.body().getTitle(), Toast.LENGTH_LONG).show();
                } else if (response.code() == 409) {
                    Toast.makeText(getContext(), "A book with the same ISBN already exists.", Toast.LENGTH_LONG).show();
                } else if(response.code() == 500) {
                    Toast.makeText(getContext(), "The book cannot be inserted", Toast.LENGTH_LONG).show();
                }
                getFragmentManager().popBackStack();
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}