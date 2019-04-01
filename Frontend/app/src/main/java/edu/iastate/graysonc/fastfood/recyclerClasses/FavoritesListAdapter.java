package edu.iastate.graysonc.fastfood.recyclerClasses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;

public class FavoritesListAdapter extends RecyclerView.Adapter<FavoritesListAdapter.FavoriteViewHolder> {
    private List<Food> mFavoritesList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView,
                        mTextView2,
                        mCalorieTextView,
                        mProteinTextView,
                        mFatTextView,
                        mCarbohydrateTextView,
                        mLocationTextView;
        public ImageView mDeleteImage, mRestaurantLogo;

        public FavoriteViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.nameTextView);
            //mTextView2 = itemView.findViewById(R.id.card_restaurant);
            mCalorieTextView = itemView.findViewById(R.id.calorie_text);
            mProteinTextView = itemView.findViewById(R.id.protein_text);
            mFatTextView = itemView.findViewById(R.id.fat_text);
            mCarbohydrateTextView = itemView.findViewById(R.id.carbohydrate_text);
            mLocationTextView = itemView.findViewById(R.id.location_text);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            mRestaurantLogo = itemView.findViewById(R.id.restaurant_logo);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            View dropDown = v.findViewById(R.id.food_info);
                            if (dropDown.getVisibility() == View.GONE) {
                                dropDown.setVisibility(View.VISIBLE);
                            } else {
                                dropDown.setVisibility(View.GONE);
                            }
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public FavoritesListAdapter(List<Food> favoriteList) {
        mFavoritesList = favoriteList;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_favorite, parent, false);
        FavoriteViewHolder evh = new FavoriteViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        Food currentItem = mFavoritesList.get(position);

        holder.mNameTextView.setText(currentItem.getName());
        //holder.mTextView2.setText("" + currentItem.getLocation());
        holder.mCalorieTextView.setText("" + currentItem.getCalorieTotal());
        holder.mProteinTextView.setText("" + currentItem.getProteinTotal());
        holder.mFatTextView.setText("" + currentItem.getFatTotal());
        holder.mCarbohydrateTextView.setText("" + currentItem.getCarbTotal());
        holder.mLocationTextView.setText("" + currentItem.getLocation());
        switch (currentItem.getLocation()) {
            case 0:
                //holder.mRestaurantLogo.setImageDrawable(R.drawable.mcdonalds);
        }

    }

    @Override
    public int getItemCount() {
        return mFavoritesList.size();
    }
}