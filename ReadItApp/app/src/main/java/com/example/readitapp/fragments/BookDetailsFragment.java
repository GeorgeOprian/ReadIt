package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.readitapp.R;
import com.example.readitapp.model.webserver.book.input.BookDto;
import com.example.readitapp.model.webserver.book.input.CategoryDto;
import com.example.readitapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.stream.Collectors;

public class BookDetailsFragment extends Fragment {

    private View view;
    private ImageView poster;
    private TextView title;
    private TextView authors;
    private TextView rating;
    private TextView category;
    private TextView description;
    private Button rentButton;
    private BookDto bookDto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book_details, container, false);

        initView();
        Bundle bundle = getArguments();
        if(bundle != null) {
            bookDto = (BookDto) bundle.getSerializable(Utils.BOOK);
            fillBookView();
        }

        return view;
    }

    private void fillBookView() {
        if (bookDto.getThumbnail() != null) {
            Picasso.get().load(bookDto.getThumbnail().getSmallThumbnail()).into(poster);
        }

        title.setText(bookDto.getTitle());
        if (bookDto.getAuthor() != null && !bookDto.getAuthor().isEmpty()) {
            authors.setText(bookDto.getAuthor());
        } else {
            authors.setText("");
        }
        if (bookDto.getRatingsCount() != null && bookDto.getRatingsCount() != 0) {
            rating.setText(bookDto.getRatingsCount().toString());
        } else {
            rating.setText("");
        }

        description.setText(bookDto.getDescription());
        category.setText(bookDto.getCategories().stream().map(CategoryDto::getCategoryName).collect(Collectors.joining(",")));
    }

    private void initView() {
        poster = view.findViewById(R.id.poster);
        title = view.findViewById(R.id.title);
        authors = view.findViewById(R.id.author_value);
        rating = view.findViewById(R.id.rating_value);
        category = view.findViewById(R.id.category_value);
        description = view.findViewById(R.id.description_value);
        rentButton = view.findViewById(R.id.rent_button);
    }
}