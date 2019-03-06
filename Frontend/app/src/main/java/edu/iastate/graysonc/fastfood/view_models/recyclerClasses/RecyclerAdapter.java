package edu.iastate.graysonc.fastfood.view_models.recyclerClasses;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.iastate.graysonc.fastfood.R;

public class RecyclerAdapter extends  android.support.v7.widget.RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<recyler_card> mList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ViewHolder extends  android.support.v7.widget.RecyclerView.ViewHolder{
        public ImageView mDeleteImage;
        public TextView mLine1;
        public TextView mLine2;
        public ViewHolder(@NonNull View itemView, OnItemClickListener  listener) {
            super(itemView);
            mLine1 = itemView.findViewById(R.id.RecLine1);
            mLine2 = itemView.findViewById(R.id.RecLine2);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(pos);
                        }
                    }
                }
            });
        }
    }

    public RecyclerAdapter(ArrayList<recyler_card> list){
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int pos) {
        recyler_card currentCard = mList.get(pos);
        viewHolder.mLine1.setText(currentCard.getFood());
        viewHolder.mLine2.setText(currentCard.getData());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
