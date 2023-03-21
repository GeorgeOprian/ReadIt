package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.readitapp.R;
import com.example.readitapp.adapters.AdminBooksAdapter;
import com.example.readitapp.adapters.OnAdminBookClickListener;
import com.example.readitapp.api.googlebooks.GoogleBooksAPIBuilder;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.googlebooks.Item;
import com.example.readitapp.model.googlebooks.VolumesResponse;
import com.example.readitapp.model.webserver.WebServerModel;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;

public class AdministrationFragment extends Fragment implements OnAdminBookClickListener {

    private View view;
    private Button searchButton;
    private TextInputEditText title;
    private TextInputEditText author;

    private RecyclerView recyclerView;

    private AdminBooksAdapter adapter;

    private static final String IN_TITLE_SEARCH_QUERY = "intitle:";
    private static final String IN_AUTHOR_SEARCH_QUERY = "inauthor:";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_administration, container, false);

        initView();
        searchButton.setOnClickListener(view -> callGoogleAPI());
//        searchButton.setOnClickListener(view1 -> callWebServerAPI());
        return view;
    }

    private void initView() {
        searchButton = view.findViewById(R.id.search_button);
        title = view.findViewById(R.id.textInputEditTextTitle);
        author = view.findViewById(R.id.textInputEditTextAuthor);

        recyclerView = view.findViewById(R.id.container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initAdapter();

        recyclerView.setAdapter(adapter);
    }

    private void initAdapter() {
        adapter = new AdminBooksAdapter(this);
    }

    private void callWebServerAPI() {
        String searchValue = title.getText() + "+inauthor:" + author.getText();

        Call<WebServerModel> call = WebServerAPIBuilder.getInstance().test();

        call.enqueue(new Callback<WebServerModel>() {
            @Override
            public void onResponse(Call<WebServerModel> call, retrofit2.Response<WebServerModel> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), response.body().getTest(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WebServerModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callGoogleAPI() {

        String query = createSearchQuery();

        if (query == null) {
            Toast.makeText(getContext(), "You must provide a value to search.", Toast.LENGTH_LONG).show();
            return;
        }

        Call<VolumesResponse> call = GoogleBooksAPIBuilder.getInstance().getVolumes(query, GoogleBooksAPIBuilder.API_KEY);

        call.enqueue(new Callback<VolumesResponse>() {
            @Override
            public void onResponse(Call<VolumesResponse> call, retrofit2.Response<VolumesResponse> response) {
                if (response.isSuccessful()) {
                    adapter.submitList(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<VolumesResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(Item item) {
        Toast.makeText(getContext(), item.getVolumeInfo().getTitle(), Toast.LENGTH_SHORT).show();
    }

    private String createSearchQuery() {
        String titleRequest = getSearchValue(title.getText(), IN_TITLE_SEARCH_QUERY);
        String authorRequest = getSearchValue(author.getText(), IN_AUTHOR_SEARCH_QUERY);

        if (titleRequest != null && authorRequest != null) {
            return titleRequest + "+" + authorRequest;
        }

        if (titleRequest != null) {
            return titleRequest;
        }

        return authorRequest;
    }

    private String getSearchValue(Editable titleText, String queryParam) {

        if (titleText == null) {
            return null;
        }

        String titleString = titleText.toString();
        if (titleString.isEmpty() && titleString.trim().isEmpty()) {
            return null;
        }

        return queryParam + titleString;
    }

}