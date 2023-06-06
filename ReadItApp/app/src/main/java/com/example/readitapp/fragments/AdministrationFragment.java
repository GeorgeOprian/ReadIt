package com.example.readitapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.readitapp.R;
import com.example.readitapp.adapters.AdminBooksAdapter;
import com.example.readitapp.adapters.OnAdminBookClickListener;
import com.example.readitapp.api.googlebooks.GoogleBooksAPIBuilder;
import com.example.readitapp.model.googlebooks.ImageLinks;
import com.example.readitapp.model.googlebooks.Item;
import com.example.readitapp.model.googlebooks.VolumeInfo;
import com.example.readitapp.model.googlebooks.VolumesResponse;
import com.example.readitapp.model.webserver.book.response.BookDto;
import com.example.readitapp.model.webserver.book.response.CategoryDto;
import com.example.readitapp.model.webserver.book.response.ThumbnailDto;
import com.example.readitapp.model.webserver.book.request.OutputBookModel;
import com.example.readitapp.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.stream.Collectors;

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
        registerForContextMenu(recyclerView);
    }

    private void initAdapter() {
        adapter = new AdminBooksAdapter(this);
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
                Utils.hideKeyboard(AdministrationFragment.this);

                if (response.isSuccessful()) {

                    if (response.body().getItems() == null || response.body().getItems().isEmpty()) {
                        Toast.makeText(getContext(), "No book was found.", Toast.LENGTH_LONG).show();
                    }

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
        Bundle bundle = new Bundle();
        BookDto book = createBook(new OutputBookModel(item, 0));

        bundle.putSerializable(Utils.BOOK_ID, book);
        bundle.putSerializable(Utils.INSERT, 1);
        Fragment selectedFragment = new AdminBookDetailsFragment();
        selectedFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .addToBackStack("tag")
                .commit();
    }

    private static BookDto createBook(OutputBookModel outputBookModel) {
        BookDto book = new BookDto();

        VolumeInfo volumeInfo = outputBookModel.getItem().getVolumeInfo();

        book.setTitle(volumeInfo.getTitle());

        if (volumeInfo.getAuthors() != null && !volumeInfo.getAuthors().isEmpty()) {
            book.setAuthor(String.join(", ", volumeInfo.getAuthors()));
        } else {
            book.setAuthor("");
        }

        book.setPublisher(volumeInfo.getPublisher());
        book.setPublishedDate(volumeInfo.getPublishedDate());
        book.setDescription(volumeInfo.getDescription());

        if (volumeInfo.getIndustryIdentifiers() != null && !volumeInfo.getIndustryIdentifiers().isEmpty()) {
            book.setIsbn(volumeInfo.getIndustryIdentifiers().get(0).getIdentifier());
        } else {
            book.setIsbn(volumeInfo.getTitle());
        }

        book.setPageCount(volumeInfo.getPageCount());
        book.setAverageRating(volumeInfo.getAverageRating());
        book.setRatingsCount(volumeInfo.getRatingsCount());
        book.setMaturityRating(volumeInfo.getMaturityRating());
        book.setLanguage(volumeInfo.getLanguage());
        book.setInStock(outputBookModel.getInStock());

        ImageLinks imageLinks = volumeInfo.getImageLinks();
        if (imageLinks != null) {
            book.setThumbnail(new ThumbnailDto(imageLinks.getSmallThumbnail(), imageLinks.getThumbnail()));
        }

        List<CategoryDto> categories = volumeInfo.getCategories().stream().map(CategoryDto::new).collect(Collectors.toList());
        book.setCategories(categories);

        return book;
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