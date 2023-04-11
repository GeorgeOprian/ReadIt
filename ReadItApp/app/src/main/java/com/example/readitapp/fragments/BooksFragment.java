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
import com.example.readitapp.adapters.BooksListAdapter;
import com.example.readitapp.adapters.OnBookListClickListener;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.book.input.BookDto;
import com.example.readitapp.model.webserver.book.input.BookListDto;
import com.example.readitapp.model.webserver.book.input.PageDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.readitapp.api.webserver.WebServerAPIService.DEFAULT_PAGE_SIZE;

public class BooksFragment extends Fragment implements OnBookListClickListener {

    private RecyclerView recyclerView;
    private View view;
    private BooksListAdapter booksListAdapter;
    private boolean isLoading = false;
    private List<BookListDto> books = new ArrayList<>();

    private int page = 0;
    private long totalSize = 0;

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
                        books.add(null);
                        recyclerView.post(() -> booksListAdapter.notifyItemInserted(books.size() - 1));
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            int position = books.size() - 1;
            BookListDto item = books.get(position);
            if (item == null) {
                books.remove(position);
                booksListAdapter.notifyItemRemoved(position);
            }

            if (books.size() < totalSize) {
                loadAllBooks(++page);
                booksListAdapter.notifyDataSetChanged();
            }
            isLoading = false;
        }, 2000);
    }

    private void initAdapter() {
        booksListAdapter = new BooksListAdapter(books, this);
        recyclerView.setAdapter(booksListAdapter);
        registerForContextMenu(recyclerView);
    }

    private void loadAllBooks(int page) {
        Call<PageDto<BookListDto>> call = WebServerAPIBuilder.getInstance().getBookById(page, DEFAULT_PAGE_SIZE, "title");

        call.enqueue(new Callback<PageDto<BookListDto>>() {
            @Override
            public void onResponse(Call<PageDto<BookListDto>> call, Response<PageDto<BookListDto>> response) {
                if (response.isSuccessful()) {
                    PageDto<BookListDto> body = response.body();
                    if(response.body().getTotalSize() != totalSize) {
                        totalSize = response.body().getTotalSize();
                    }
                    booksListAdapter.submitList(body.getContent());
                }
            }

            @Override
            public void onFailure(Call<PageDto<BookListDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBookClick(BookListDto book) {
        Call<BookDto> call = WebServerAPIBuilder.getInstance().getBookById(book.getBookId());

        call.enqueue(new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                if (response.isSuccessful()) {
                    BookDto book = response.body();
                    Toast.makeText(getContext(), book.getTitle(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}