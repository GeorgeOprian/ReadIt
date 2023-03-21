package com.example.readitapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
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
        private final TextView genres;
        private final TextView rating;

        public BooksViewHolder(@NonNull View view) {
            super(view);
            poster = view.findViewById(R.id.poster);
            title = view.findViewById(R.id.title);
            genres = view.findViewById(R.id.genres_values);
            rating = view.findViewById(R.id.rating_value);

            this.view = view;

        }

        public void bind(Item itemsResult) {
            VolumeInfo volumeInfo = itemsResult.getVolumeInfo();
            Picasso.get().load(volumeInfo.getImageLinks().getSmallThumbnail()).into(poster);
            title.setText(volumeInfo.getTitle());
            genres.setText("test");// FIXME: 20.03.2023 change value
            rating.setText("test");// FIXME: 20.03.2023 change value
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(itemsResult);
                }
            });
        }

    }
}
