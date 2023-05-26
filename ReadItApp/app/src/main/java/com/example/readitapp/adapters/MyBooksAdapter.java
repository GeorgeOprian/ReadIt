package com.example.readitapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        this.mItemList.removeIf(bookListDto -> bookListDto == null);
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

        public void bind(BookRentResponseDto book) {
            if (book.getBook().getThumbnail() != null) {
                Picasso.get().load(book.getBook().getThumbnail().getSmallThumbnail()).into(poster);
            }
            title.setText(book.getBook().getTitle());
            authors.setText(book.getBook().getAuthor());

            if (book.getBook().getRatingsCount() != null && book.getBook().getRatingsCount() != 0) {
                rating.setText(book.getBook().getRatingsCount().toString());
            } else {
                rating.setText("");
            }

            view.setOnClickListener(v -> bookClickListener.onBookClick(book));
        }

    }
}
