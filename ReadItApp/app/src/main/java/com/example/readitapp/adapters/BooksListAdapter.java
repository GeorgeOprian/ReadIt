package com.example.readitapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.model.webserver.book.input.BookListDto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private final List<BookListDto> mItemList;
    public static OnBookListClickListener bookClickListener;

    public BooksListAdapter(List<BookListDto> itemList, OnBookListClickListener listener) {
        mItemList = itemList;
        bookClickListener = listener;
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

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView poster;
        private final TextView title;
        private final TextView authors;
        private final TextView rating;
        private final View view;

        public ItemViewHolder(@NonNull View view) {
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
