package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.readitapp.R;
import com.example.readitapp.adapters.OnAdminBookClickListener;
import com.example.readitapp.adapters.RecyclerViewAdapter;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.googlebooks.Item;
import com.example.readitapp.model.webserver.book.output.BookDto;
import com.example.readitapp.model.webserver.book.output.PageDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment implements OnAdminBookClickListener {

    private RecyclerView recyclerView;
    private View view;
    private int page = 0;
    private RecyclerViewAdapter recyclerViewAdapter;
    private boolean isLoading = false;
    private List<BookDto> books = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books, container, false);

        initView();

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initAdapter();
        loadAllBooks(page);
        initScrollListener();
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == books.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        books.add(null);
        recyclerViewAdapter.notifyItemInserted(books.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                books.remove(books.size() - 1);
                int scrollPosition = books.size();
                recyclerViewAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                loadAllBooks(++page);

                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    private void populateData() {
        loadAllBooks(page);
    }

    private void initAdapter() {
        recyclerViewAdapter = new RecyclerViewAdapter(books);
        recyclerView.setAdapter(recyclerViewAdapter);
        registerForContextMenu(recyclerView);
    }

    @Override
    public void onItemClick(Item item) {

    }

    private void loadAllBooks(int page) {
        Call<PageDto<BookDto>> call = WebServerAPIBuilder.getInstance().getAllBooks(page, 4, "title");

        call.enqueue(new Callback<PageDto<BookDto>>() {
            @Override
            public void onResponse(Call<PageDto<BookDto>> call, Response<PageDto<BookDto>> response) {
                if (response.isSuccessful()) {
                    PageDto<BookDto> body = response.body();
                    recyclerViewAdapter.submitList(body.getContent());
                }
            }

            @Override
            public void onFailure(Call<PageDto<BookDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}