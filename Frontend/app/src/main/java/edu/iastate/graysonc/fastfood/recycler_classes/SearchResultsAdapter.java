package edu.iastate.graysonc.fastfood.recycler_classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;

public class SearchResultsAdapter extends PagedListAdapter<Food, SearchResultsAdapter.MyViewHolder> {
    private OnItemClickListener mListener;

    public SearchResultsAdapter() {
        super(DIFF_CALLBACK);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(SearchResultsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_food, parent, false);
        SearchResultsAdapter.MyViewHolder evh = new SearchResultsAdapter.MyViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Food currentItem = getItem(position);
        holder.mNameTextView.setText(currentItem.getName());
        holder.mPriceTextView.setText(currentItem.getPrice());
        holder.mRatingBar.setRating((float)currentItem.getRating());
        switch (currentItem.getLocation()) {
            case 0:
                holder.mRestaurantLogo.setImageResource(R.drawable.mcdonalds);
                break;
            case 1:
                holder.mRestaurantLogo.setImageResource(R.drawable.chickfila);
                break;
            case 2:
                holder.mRestaurantLogo.setImageResource(R.drawable.subway);
                break;
            default:
                // Maybe display "Image not found" image
                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView, mPriceTextView;
        public ImageView mRestaurantLogo;
        public RatingBar mRatingBar;

        MyViewHolder(View itemView, SearchResultsAdapter.OnItemClickListener listener) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.food_name);
            mPriceTextView = itemView.findViewById(R.id.food_price);
            mRestaurantLogo = itemView.findViewById(R.id.restaurant_logo);
            mRatingBar = itemView.findViewById(R.id.rating_bar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Do shit
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    private static DiffUtil.ItemCallback<Food> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Food>() {
                @Override
                public boolean areItemsTheSame(Food oldItem, Food newItem) {
                    // The ID property identifies when items are the same.
                    return oldItem.getId() == newItem.getId();
                }
                @Override
                public boolean areContentsTheSame(Food oldItem, Food newItem) {
                    // Don't use the "==" operator here. Either implement and use .equals(),
                    // or write custom data comparison logic here.
                    return oldItem.equals(newItem);
                }
            };

    @Nullable
    @Override
    public Food getItem(int position) {
        if (getItemCount() != 0) {
            return super.getItem(position);
        } else {
            return null;
        }
    }
}
