package com.example.readitapp.adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.BooksViewHolder> {

    private final List<BookRentResponseDto> mItemList;
    public static OnMyBooksCLickListener bookClickListener;
    private boolean isClickable;

    public MyBooksAdapter(List<BookRentResponseDto> mItemList, OnMyBooksCLickListener listener, boolean isClickable) {
        this.mItemList = mItemList;
        bookClickListener = listener;
        this.isClickable = isClickable;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_card, parent, false);
        view.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(Menu.NONE, R.id.returnn, Menu.NONE, "Return");
        });
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.bind(mItemList.get(position));
        holder.itemView.setLongClickable(isClickable);
    }

    @Override
    public int getItemCount() {
        if (mItemList == null) {
            return 0;
        }
        return mItemList.size();
    }

    public void submitList(List<BookRentResponseDto> books) {
        this.mItemList.clear();
        this.mItemList.addAll(books);
        notifyDataSetChanged();
    }

    public BookRentResponseDto getItem(int position) {
        return mItemList.get(position);
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private final ImageView poster;
        private final TextView title;
        private final TextView authors;
        private final TextView category;
        private final View view;

        public BooksViewHolder(@NonNull View view) {
            super(view);
            poster = view.findViewById(R.id.poster);
            title = view.findViewById(R.id.title);
            authors = view.findViewById(R.id.author_value);
            category = view.findViewById(R.id.category_value);
            view.setOnCreateContextMenuListener(this);
            this.view = view;

        }

        public void bind(BookRentResponseDto book) {
            if (book.getBook().getThumbnail() != null) {
                Picasso.get().load(book.getBook().getThumbnail().getSmallThumbnail()).into(poster);
            }
            title.setText(book.getBook().getTitle());
            authors.setText(book.getBook().getAuthor());

            List<String> categoriesString = book.getBook().getCategories().stream().map(categoryDto -> categoryDto.getCategoryName()).collect(Collectors.toList());
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
            BookRentResponseDto carte = mItemList.get(position);

            // Add the selected option to the context menu
            menu.add(Menu.NONE, R.id.returnn, Menu.NONE, "Return").setOnMenuItemClickListener(item -> {
                returnBook(carte.getRentId());
                return true;
            });
        }

        private void returnBook(int rentId) {
            Call<List<BookRentResponseDto>> call = WebServerAPIBuilder.getInstance().returnBook(rentId);

            call.enqueue(new Callback<List<BookRentResponseDto>>() {
                @Override
                public void onResponse(Call<List<BookRentResponseDto>> call, Response<List<BookRentResponseDto>> response) {
                    if (response.isSuccessful()) {
                        mItemList.remove(rentId);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<BookRentResponseDto>> call, Throwable t) {
                }
            });
        }
    }
}
