package com.example.readitapp.adapters;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.fragments.AdminBookDetailsFragment;
import com.example.readitapp.model.webserver.book.response.BookDto;
import com.example.readitapp.model.webserver.book.response.BookListDto;
import com.example.readitapp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private final List<BookListDto> mItemList;
    public static OnBookListClickListener bookClickListener;
    private StartFragment startFragment;

    public BooksListAdapter(List<BookListDto> itemList, OnBookListClickListener listener, StartFragment startFragment) {
        mItemList = itemList;
        bookClickListener = listener;
        this.startFragment = startFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
        viewHolder.itemView.setLongClickable(true);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        BookListDto item = mItemList.get(position);
        viewHolder.bind(item);
    }

    public void submitList(List<BookListDto> books) {
        this.mItemList.removeIf(bookListDto -> bookListDto == null);
        this.mItemList.addAll(books);
        notifyDataSetChanged();
    }

    public void replaceList(List<BookListDto> books) {
        this.mItemList.clear();
        this.mItemList.addAll(books);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final ImageView poster;
        private final TextView title;
        private final TextView authors;
        private final TextView category;
        private final View view;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            poster = view.findViewById(R.id.poster);
            title = view.findViewById(R.id.title);
            authors = view.findViewById(R.id.author_value);
            category = view.findViewById(R.id.category_value);
            view.setOnCreateContextMenuListener(this);
            this.view = view;
        }

        public void bind(BookListDto book) {
            if (book.getThumbnail() != null) {
                Picasso.get().load(book.getThumbnail().getSmallThumbnail()).into(poster);
            }
            title.setText(book.getTitle());
            authors.setText(book.getAuthor());

            List<String> categoriesString = book.getCategories().stream().map(categoryDto -> categoryDto.getCategoryName()).collect(Collectors.toList());
            String categories = String.join(", ", categoriesString);

            if (categories != null) {
                category.setText(categories);
            } else {
                category.setText("");
            }

            view.setOnClickListener(v -> bookClickListener.onBookClick(book));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            int position = getAdapterPosition();

            // Get the item from the list based on the position
            BookListDto carte = mItemList.get(position);

            String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            if(currentUser.equals(Utils.USER_ADMIN)) {
                // Add the selected option to the context menu
                menu.add(Menu.NONE, R.id.update, Menu.NONE, "Update").setOnMenuItemClickListener(item -> {
                    updateBook(carte.getBookId());
                    return true;
                });
                menu.add(Menu.NONE, R.id.delete, Menu.NONE, "Delete").setOnMenuItemClickListener(item -> {
                    deleteBook(carte.getBookId());
                    return true;
                });
            }
        }

        private void updateBook(int bookId) {
            final BookDto[] bookDto = new BookDto[1];

            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            Call<BookDto> call = WebServerAPIBuilder.getInstance().getBookById(bookId, email);
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
                        startFragment.startFragment(selectedFragment);
                    }
                }

                @Override
                public void onFailure(Call<BookDto> call, Throwable t) {
                }
            });
        }

        private void deleteBook(int bookId) {
            Call<Void> call = WebServerAPIBuilder.getInstance().deleteBook(bookId);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        mItemList.remove(bookId);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public interface StartFragment {
        void startFragment(Fragment fragment);
    }

}
