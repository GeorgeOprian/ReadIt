package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

public class CurrentlyReadingFragment extends Fragment implements OnMyBooksCLickListener  {

    private View view;
    private MyBooksAdapter myBooksAdapter;
    private RecyclerView recyclerViewReading;
    private List<BookRentResponseDto> books = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_currently_reading, container, false);

        initView();

        return view;
    }

    private void initView() {
        initReadingAdapter();
        loadReadingBooks();
    }

    private void initReadingAdapter() {
        recyclerViewReading = view.findViewById(R.id.container);
        recyclerViewReading.setLayoutManager(new LinearLayoutManager(getContext()));
        myBooksAdapter = new MyBooksAdapter(books, this, true);
        recyclerViewReading.setAdapter(myBooksAdapter);
        registerForContextMenu(recyclerViewReading);
    }

    private void loadReadingBooks() {
        Call<List<BookRentResponseDto>> call = WebServerAPIBuilder.getInstance().loadNotReturnedBooks(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        call.enqueue(new Callback<List<BookRentResponseDto>>() {
            @Override
            public void onResponse(Call<List<BookRentResponseDto>> call, Response<List<BookRentResponseDto>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        Toast.makeText(getContext(), "You are not reading any books currently.", Toast.LENGTH_LONG).show();
                    }
                    myBooksAdapter.submitList(response.body());
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
        loadReadingBooks();
    }

    @Override
    public void onBookClick(BookRentResponseDto item) {

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_my_books, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.returnn:
                returnBook(item.getOrder());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void returnBook(int bookId) {
        Call<List<BookRentResponseDto>> call = WebServerAPIBuilder.getInstance().returnBook(books.get(bookId).getRentId());

        call.enqueue(new Callback<List<BookRentResponseDto>>() {
            @Override
            public void onResponse(Call<List<BookRentResponseDto>> call, Response<List<BookRentResponseDto>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Book returned", Toast.LENGTH_LONG).show();
                    books.remove(bookId);
                    myBooksAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<BookRentResponseDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}