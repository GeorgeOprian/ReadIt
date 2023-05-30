package com.example.readitapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.model.webserver.book.response.BookListDto;
import com.squareup.picasso.Picasso;

import java.util.List;

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

    public class BooksViewHolder extends RecyclerView.ViewHolder {

        private final ImageView poster;
        private final TextView title;
        private final TextView authors;
        private final TextView rating;
        private final View view;

        public BooksViewHolder(@NonNull View view) {
            super(view);
            poster = view.findViewById(R.id.poster);
            title = view.findViewById(R.id.title);
            authors = view.findViewById(R.id.author_value);
            rating = view.findViewById(R.id.category_value);

            this.view = view;

        }

        public void bind(BookListDto book) {
            if (book.getThumbnail() != null) {
                Picasso.get().load(book.getThumbnail().getSmallThumbnail()).into(poster);
            }
            title.setText(book.getTitle());
            authors.setText(book.getAuthor());

            if (book.getRatingsCount() != null && book.getRatingsCount() != 0) {
                rating.setText(book.getRatingsCount().toString());
            } else {
                rating.setText("");
            }

            view.setOnClickListener(v -> bookClickListener.onBookClick(book));

        }

    }
}
