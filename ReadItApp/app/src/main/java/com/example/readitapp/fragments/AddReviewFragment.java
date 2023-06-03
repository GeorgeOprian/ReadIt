package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.book.request.BookUserRequestDto;
import com.example.readitapp.model.webserver.book.response.BookDto;
import com.example.readitapp.model.webserver.review.BookReviewCreateDto;
import com.example.readitapp.model.webserver.review.BookReviewDto;
import com.example.readitapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;

public class AddReviewFragment extends Fragment {

    private View view;
    private RatingBar ratingBar;
    private TextInputEditText content;
    private Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_review, container, false);

        initView();
        send.setOnClickListener(v -> addReview());

        return view;
    }

    private void initView() {
        ratingBar = view.findViewById(R.id.rating_bar);
        content = view.findViewById(R.id.textInputEditTextLike);
        send = view.findViewById(R.id.sendbtn);
    }

    private void addReview() {
        BookReviewCreateDto bookReviewCreateDto = new BookReviewCreateDto();
        bookReviewCreateDto.setNbrStars((double) ratingBar.getRating());
        bookReviewCreateDto.setContent(content.getText().toString());
        bookReviewCreateDto.setReviewDate(LocalDate.now().toString());
        bookReviewCreateDto.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Bundle bundle = getArguments();
        Integer bookId = (Integer) bundle.get(Utils.BOOK_ID);
        bookReviewCreateDto.setBookId(bookId);

        Call<BookReviewDto> call = WebServerAPIBuilder.getInstance().addReview(bookReviewCreateDto);
        call.enqueue(new Callback<BookReviewDto>() {
            @Override
            public void onResponse(Call<BookReviewDto> call, retrofit2.Response<BookReviewDto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Review added", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookReviewDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}