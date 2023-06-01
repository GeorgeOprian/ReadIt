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
import com.example.readitapp.adapters.MyBooksAdapter;
import com.example.readitapp.adapters.OnMyBooksCLickListener;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingHistoryFragment extends Fragment implements OnMyBooksCLickListener {

    private View view;
    private MyBooksAdapter myBooksHistoryAdapter;
    private RecyclerView recyclerViewHistory;
    private List<BookRentResponseDto> booksHistory = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reading_history, container, false);

        initView();

        return view;
    }

    private void initView() {
        initAdapterHistory();
        loadHistoryBooks();
    }

    private void initAdapterHistory() {
        recyclerViewHistory = view.findViewById(R.id.container_history);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        myBooksHistoryAdapter = new MyBooksAdapter(booksHistory, this, false);
        recyclerViewHistory.setAdapter(myBooksHistoryAdapter);
        registerForContextMenu(recyclerViewHistory);
    }

    private void loadHistoryBooks() {
        Call<List<BookRentResponseDto>> call = WebServerAPIBuilder.getInstance().loadReturnedBooks(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        call.enqueue(new Callback<List<BookRentResponseDto>>() {
            @Override
            public void onResponse(Call<List<BookRentResponseDto>> call, Response<List<BookRentResponseDto>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getContext(), "You did not returned any book yet.", Toast.LENGTH_LONG).show();
                    }
                    myBooksHistoryAdapter.submitList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BookRentResponseDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHistoryBooks();
    }

    @Override
    public void onBookClick(BookRentResponseDto item) {

    }

}