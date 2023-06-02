package com.example.readitapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.adapters.OnReviewClickListener;
import com.example.readitapp.adapters.ReviewsAdapter;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.review.BookReviewDto;
import com.example.readitapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsFragment extends Fragment implements OnReviewClickListener {

    private View view;

    private RecyclerView recyclerView;
    private ReviewsAdapter adapter;
    private List<BookReviewDto> reviews = new ArrayList<>();

    private Integer bookId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_reviews, container, false);

        initView();

        return view;
    }

    private void initView() {
        initAdapter();

        Bundle bundle = getArguments();
        bookId = (Integer) bundle.get(Utils.BOOK_ID);

        loadReviews();
    }

    private void loadReviews() {
        Call<List<BookReviewDto>> call = WebServerAPIBuilder.getInstance().loadReviews(bookId);

        call.enqueue(new Callback<List<BookReviewDto>>() {
            @Override
            public void onResponse(Call<List<BookReviewDto>> call, Response<List<BookReviewDto>> response) {
                if (response.isSuccessful()) {
                    adapter.submitList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BookReviewDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initAdapter() {
        recyclerView = view.findViewById(R.id.container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReviewsAdapter(reviews, this, true);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(BookReviewDto review) {

    }
}