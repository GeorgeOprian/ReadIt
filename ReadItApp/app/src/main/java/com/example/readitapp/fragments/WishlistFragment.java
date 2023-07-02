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
import com.example.readitapp.adapters.OnWishListBookClickListener;
import com.example.readitapp.adapters.WishListBookAdapter;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.book.response.BookListDto;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment implements OnWishListBookClickListener {

    private View view;

    private RecyclerView recyclerViewReading;
    private WishListBookAdapter wishListBookAdapter;
    private List<BookListDto> books = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        initView();

        return view;
    }

    private void initView() {
        initAdapter();
        loadWishListBooks();
    }

    private void initAdapter() {
        recyclerViewReading = view.findViewById(R.id.container);
        recyclerViewReading.setLayoutManager(new LinearLayoutManager(getContext()));
        wishListBookAdapter = new WishListBookAdapter(books, this, true);
        recyclerViewReading.setAdapter(wishListBookAdapter);
        registerForContextMenu(recyclerViewReading);
    }

    private void loadWishListBooks() {
        Call<List<BookListDto>> call = WebServerAPIBuilder.getInstance().getWishList(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        call.enqueue(new Callback<List<BookListDto>>() {
            @Override
            public void onResponse(Call<List<BookListDto>> call, Response<List<BookListDto>> response) {
                if (response.isSuccessful()) {
                    wishListBookAdapter.submitList(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BookListDto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBookClick(BookListDto book) {

    }

}