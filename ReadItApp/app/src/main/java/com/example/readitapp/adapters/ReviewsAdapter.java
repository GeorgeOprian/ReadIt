package com.example.readitapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readitapp.R;
import com.example.readitapp.model.webserver.review.BookReviewDto;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private final List<BookReviewDto> mItemList;
    public static OnReviewClickListener reviewClickListener;

    public ReviewsAdapter(List<BookReviewDto> mItemList, OnReviewClickListener listener, boolean isClickable) {
        this.mItemList = mItemList;
        this.reviewClickListener = listener;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_card, parent, false);
        return new ReviewsAdapter.ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsViewHolder holder, int position) {
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

    public void submitList(List<BookReviewDto> books) {
        this.mItemList.clear();
        this.mItemList.addAll(books);
        notifyDataSetChanged();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private final TextView userName;
        private final TextView nbrStars;
        private final TextView date;
        private final TextView content;

        private final View view;

        public ReviewsViewHolder(@NonNull View view) {
            super(view);
            userName = view.findViewById(R.id.review_user_name);
            nbrStars = view.findViewById(R.id.review_nbr_stars);
            date = view.findViewById(R.id.review_date);
            content = view.findViewById(R.id.review_content);

            this.view = view;
        }

        public void bind(BookReviewDto review) {

            userName.setText(review.getUser().getUserName());
            nbrStars.setText(review.getNbrStars().toString());
            date.setText(review.getReviewDate());
            content.setText(review.getContent());

            view.setOnClickListener(v -> reviewClickListener.onClick(review));
        }

    }
}