package edu.iastate.graysonc.fastfood.recycler_classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private List<Food> mFavoritesList;
    private FoodListAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(FoodListAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView, mPriceTextView;
        public ImageView mRestaurantLogo;
        public RatingBar mRatingBar;

        public FoodViewHolder(View itemView, final FoodListAdapter.OnItemClickListener listener) {
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

    public FoodListAdapter(List<Food> favoriteList) {
        mFavoritesList = favoriteList;
    }

    @Override
    public FoodListAdapter.FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_food, parent, false);
        FoodListAdapter.FoodViewHolder evh = new FoodListAdapter.FoodViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(FoodListAdapter.FoodViewHolder holder, int position) {
        Food currentItem = mFavoritesList.get(position);
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
    public int getItemCount() {
        return mFavoritesList.size();
    }
}
