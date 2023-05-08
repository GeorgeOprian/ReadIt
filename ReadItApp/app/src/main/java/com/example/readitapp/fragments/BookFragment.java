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
import com.example.readitapp.model.webserver.book.input.BookListDto;
import com.example.readitapp.model.webserver.book.output.OutputBookModel;
import com.example.readitapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            if (bundle.getSerializable(Utils.ITEM) != null) {
                item = (Item) bundle.getSerializable(Utils.ITEM);
                fillBookCardView(item);
            } else if (bundle.getSerializable(Utils.BOOK_LIST_DTO) != null) {
                BookListDto bookListDto = (BookListDto) bundle.getSerializable(Utils.BOOK_LIST_DTO);
                fillBookCardView(bookListDto);
            }
        }

        addButton.setOnClickListener(view -> callWebServerAPI());

        return view;
    }

    private void fillBookCardView(BookListDto bookListDto) {
        Picasso.get().load(bookListDto.getThumbnail().getSmallThumbnail()).into(poster);
        title.setText(bookListDto.getTitle());
        if (bookListDto.getAuthor() != null && !bookListDto.getAuthor().isEmpty()) {
            authors.setText( bookListDto.getAuthor());
        } else {
            authors.setText("");
        }
        if (bookListDto.getRatingsCount() != null && bookListDto.getRatingsCount() != 0) {
            rating.setText(bookListDto.getRatingsCount().toString());
        } else {
            rating.setText("");
        }
//        inStockTextView.setText(bookListDto.);
    }

    private void fillBookCardView(Item item) {
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
        inStockTextView = view.findViewById(R.id.textInputEditTextStock);
    }

    private void callWebServerAPI() {

        OutputBookModel inputBookModel = createInputBookModel();
        Call<BookListDto> call = WebServerAPIBuilder.getInstance().addBook(inputBookModel);
        Utils.hideKeyboard(BookFragment.this);

        call.enqueue(new Callback<BookListDto>() {
            @Override
            public void onResponse(Call<BookListDto> call, Response<BookListDto> response) {
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
            public void onFailure(Call<BookListDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    private OutputBookModel createInputBookModel() {
        String inStock = inStockTextView.getText().toString();
        int inStockValue;
        if (inStock != null) {
            inStockValue = Integer.valueOf(inStock);
        } else {
            inStockValue = 0;
        }
        OutputBookModel inputBookModel = new OutputBookModel(item, inStockValue);
        return inputBookModel;
    }

}