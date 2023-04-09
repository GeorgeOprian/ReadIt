package com.example.readitapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.model.googlebooks.ImageLinks;
import com.example.readitapp.model.googlebooks.Item;
import com.example.readitapp.model.googlebooks.VolumeInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminBooksAdapter extends RecyclerView.Adapter<AdminBooksAdapter.BooksViewHolder> {

    private List<Item> localDataSet;
    public static OnAdminBookClickListener itemClickListener;

    public AdminBooksAdapter(OnAdminBookClickListener listener) {
        itemClickListener = listener;
    }

    public void submitList(List<Item> items) {
        this.localDataSet = items;
        notifyDataSetChanged();
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
        holder.bind(localDataSet.get(position));
        holder.itemView.setLongClickable(true);
    }

    @Override
    public int getItemCount() {
        if (localDataSet == null) {
            return 0;
        }
        return localDataSet.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {

        private View view;

        private final ImageView poster;
        private final TextView title;
        private final TextView authors;
        private final TextView rating;

        public BooksViewHolder(@NonNull View view) {
            super(view);
            poster = view.findViewById(R.id.poster);
            title = view.findViewById(R.id.title);
            authors = view.findViewById(R.id.author_value);
            rating = view.findViewById(R.id.rating_value);

            this.view = view;

        }

        public void bind(Item itemResult) {
            VolumeInfo volumeInfo = itemResult.getVolumeInfo();
            ImageLinks imageLinks = volumeInfo.getImageLinks();
            if (imageLinks != null) {
                Picasso.get().load(imageLinks.getSmallThumbnail()).into(poster);
            }
            title.setText(volumeInfo.getTitle());

            if (volumeInfo.getAuthors() != null && !volumeInfo.getAuthors().isEmpty()) {
                authors.setText(String.join(",", volumeInfo.getAuthors()));
            } else {
                authors.setText("");
            }

            if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() != 0) {
                rating.setText(volumeInfo.getRatingsCount().toString());
            } else {
                rating.setText("");
            }

            view.setOnClickListener(v -> itemClickListener.onItemClick(itemResult));
        }

    }
}
