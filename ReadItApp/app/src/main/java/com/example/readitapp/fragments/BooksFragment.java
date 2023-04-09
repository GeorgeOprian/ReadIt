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
import com.example.readitapp.adapters.AdminBooksAdapter;
import com.example.readitapp.adapters.OnAdminBookClickListener;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.googlebooks.Item;
import com.example.readitapp.model.webserver.book.output.BookDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment implements OnAdminBookClickListener {

    private RecyclerView recyclerView;
    private View view;
//    private AdminBooksAdapter adapter;

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
//        initAdapter();
//        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        loadAllBooks();
    }

//    private void initAdapter() {
//        adapter = new AdminBooksAdapter(this);
//    }

    @Override
    public void onItemClick(Item item) {

    }

    private void loadAllBooks() {
//        Call<List<BookDto>> call = WebServerAPIBuilder.getInstance().getAllBooks();
//
//        call.enqueue(new Callback<List<BookDto>>() {
//            @Override
//            public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
//                if (response.isSuccessful()) {
////                    adapter.submitList(response.body());
//                    Toast.makeText(getContext(), response.body().get(0).getTitle(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<BookDto>> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }
}