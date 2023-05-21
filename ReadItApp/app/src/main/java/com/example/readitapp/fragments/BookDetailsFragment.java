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
import com.example.readitapp.api.googlebooks.GoogleBooksAPIBuilder;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.googlebooks.VolumesResponse;
import com.example.readitapp.model.webserver.book.request.BookRentRequestDto;
import com.example.readitapp.model.webserver.book.response.BookDto;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.example.readitapp.model.webserver.book.response.CategoryDto;
import com.example.readitapp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;

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

        rentButton.setOnClickListener(view -> rentBook());

        return view;
    }

    private void rentBook() {

        if (bookDto == null) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            return;
        }

        if (false) { //validare pe adresa
            Toast.makeText(getContext(), "Fill the address", Toast.LENGTH_LONG).show();
            return;
        }

        BookRentRequestDto bookRentRequestDto = getBookRentRequestDto();
        Call<BookRentResponseDto> call = WebServerAPIBuilder.getInstance().createSubscription(bookRentRequestDto);
        call.enqueue(new Callback<BookRentResponseDto>() {
            @Override
            public void onResponse(Call<BookRentResponseDto> call, retrofit2.Response<BookRentResponseDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Book rented", Toast.LENGTH_LONG).show();
                } else if (response.code() == 404) {
                    Toast.makeText(getContext(), "There are no volumes available in stock for this book. Sorry to inform you.", Toast.LENGTH_LONG).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getContext(), "You have already rented this book", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookRentResponseDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @NotNull
    private BookRentRequestDto getBookRentRequestDto() {
        BookRentRequestDto bookRentRequestDto = new BookRentRequestDto();
        bookRentRequestDto.setBookId(bookDto.getBookId());
        bookRentRequestDto.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        LocalDate futureDate = LocalDate.now().plusMonths(1);
        bookRentRequestDto.setReturnDate(futureDate.toString());

        return bookRentRequestDto;
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