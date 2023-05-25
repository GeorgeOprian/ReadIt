package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.book.response.BookListDto;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.example.readitapp.model.webserver.book.response.PageDto;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.readitapp.api.webserver.WebServerAPIService.DEFAULT_PAGE_SIZE;

public class MyBooksFragment extends Fragment {

    private View view;
    private RecyclerView recyclerViewReading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_books, container, false);

        initView();
        loadReadingBooks();

        return view;
    }

    private void initView() {
        recyclerViewReading = view.findViewById(R.id.container);
        recyclerViewReading.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadReadingBooks() {
        Call<List<BookRentResponseDto>> call = WebServerAPIBuilder.getInstance().loadNotReturnedBooks(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        call.enqueue(new Callback<List<BookRentResponseDto>>() {
            @Override
            public void onResponse(Call<List<BookRentResponseDto>> call, Response<List<BookRentResponseDto>> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<List<BookRentResponseDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}