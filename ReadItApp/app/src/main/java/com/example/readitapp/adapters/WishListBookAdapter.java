package com.example.readitapp.adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.api.webserver.WebServerAPIBuilder;
import com.example.readitapp.model.webserver.book.response.BookListDto;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;

public class WishListBookAdapter extends RecyclerView.Adapter<WishListBookAdapter.BooksViewHolder> {

    private final List<BookListDto> mItemList;
    public static OnWishListBookClickListener bookClickListener;

    public WishListBookAdapter(List<BookListDto> mItemList, OnWishListBookClickListener listener, boolean isClickable) {
        this.mItemList = mItemList;
        this.bookClickListener = listener;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_card, parent, false);
        view.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(Menu.NONE, R.id.delete_from_wish_list, Menu.NONE, R.string.delete_from_wish_list);
        });
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        holder.bind(mItemList.get(position));
        holder.itemView.setLongClickable(true);
    }

    @Override
    public int getItemCount() {
        if (mItemList == null) {
            return 0;
        }
        return mItemList.size();
    }

    public void submitList(List<BookListDto> books) {
        this.mItemList.clear();
        this.mItemList.addAll(books);
        notifyDataSetChanged();
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

            // Add the selected option to the context menu
            menu.add(Menu.NONE, R.id.delete_from_wish_list, Menu.NONE, R.string.delete_from_wish_list).setOnMenuItemClickListener(item -> {
                removeBookFromWishlist(carte);
                return true;
            });
        }

        private void removeBookFromWishlist(BookListDto bookListDto) {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            Integer bookId = bookListDto.getBookId();
            Call<List<BookListDto>> call = WebServerAPIBuilder.getInstance().removeBookFromWishList(bookId, email);
            call.enqueue(new Callback<List<BookListDto>>() {
                @Override
                public void onResponse(Call<List<BookListDto>> call, retrofit2.Response<List<BookListDto>> response) {
                    if (response.isSuccessful()) {
                        mItemList.remove(bookListDto);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<BookListDto>> call, Throwable t) {
                }
            });
        }
    }
}
