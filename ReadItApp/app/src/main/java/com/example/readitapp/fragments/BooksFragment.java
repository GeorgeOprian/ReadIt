package com.example.readitapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.readitapp.R;
import com.example.readitapp.adapters.BooksListAdapter;
import com.example.readitapp.adapters.OnBookListClickListener;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.book.response.BookDto;
import com.example.readitapp.model.webserver.book.response.BookListDto;
import com.example.readitapp.model.webserver.book.response.PageDto;
import com.example.readitapp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

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
    private SearchView searchView;
    private boolean isLoading = false;
    private List<BookListDto> books = new ArrayList<>();

    private int page = 0;
    private long totalSize = 0;
    private long loadedElements = 0;
    private boolean searchedByTitle = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books, container, false);

        initView();

        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.container);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        searchView = view.findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                loadBooks(query, 0, 100);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        initAdapter();
        loadBooks("", page, DEFAULT_PAGE_SIZE);
        initScrollListener();
    }

    class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "meet a IOOBE in RecyclerView");
            }
        }
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

                if (searchedByTitle) {
                    return;
                }

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

            if (loadedElements < totalSize) {
                loadBooks("", ++page, DEFAULT_PAGE_SIZE);
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

    private void loadBooks(String title, int pageNumber, int pageSize) {
        Call<PageDto<BookListDto>> call = WebServerAPIBuilder.getInstance().getBooks(title, pageNumber, pageSize, "title");;

        call.enqueue(new Callback<PageDto<BookListDto>>() {
            @Override
            public void onResponse(Call<PageDto<BookListDto>> call, Response<PageDto<BookListDto>> response) {
                if (response.isSuccessful()) {
                    PageDto<BookListDto> body = response.body();
                    if(body.getTotalSize() != totalSize) {
                        totalSize = body.getTotalSize();
                    }

                    boolean isTitleEmpty = title.trim().isEmpty();
                    if (!isTitleEmpty) { //book was searched by title
                        booksListAdapter.replaceList(body.getContent());
                        searchedByTitle = true;
                    } else {
                        booksListAdapter.submitList(body.getContent());
                        searchedByTitle = false;
                        loadedElements = body.getPageNumber() * DEFAULT_PAGE_SIZE + body.getPageSize();
                    }
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

        Bundle bundle = new Bundle();

        bundle.putSerializable(Utils.BOOK_ID, book.getBookId());

        Fragment selectedFragment = new BookDetailsTabbedFragment();
        selectedFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .addToBackStack("tag")
                .commit();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        if(currentUser.equals(Utils.USER_ADMIN)) {
            super.onCreateContextMenu(menu, v, menuInfo);
            getActivity().getMenuInflater().inflate(R.menu.menu_floating_context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                updateBook(item.getOrder());
                return true;
            case R.id.delete:
                deleteBook(item.getOrder());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateBook(int bookId) {
        final BookDto[] bookDto = new BookDto[1];

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Call<BookDto> call = WebServerAPIBuilder.getInstance().getBookById(books.get(bookId).getBookId(), email);
        call.enqueue(new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                if (response.isSuccessful()) {
                    bookDto[0] = response.body();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Utils.BOOK_ID, bookDto[0]);
                    bundle.putSerializable(Utils.UPDATE, 0);
                    Fragment selectedFragment = new AdminBookDetailsFragment();
                    selectedFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .addToBackStack("tag")
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable t) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void deleteBook(int bookId) {
        Call<Void> call = WebServerAPIBuilder.getInstance().deleteBook(books.get(bookId).getBookId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    books.remove(bookId);
                    booksListAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Book deleted", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}