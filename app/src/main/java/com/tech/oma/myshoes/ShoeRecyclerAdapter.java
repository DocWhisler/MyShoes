package com.tech.oma.myshoes;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoeRecyclerAdapter extends RecyclerView.Adapter<ShoeRecyclerAdapter.ShoeViewHolder> {

    private ArrayList<Shoe> shoeList;

    public ShoeRecyclerAdapter(ArrayList<Shoe> shoes){
        this.shoeList = shoes;
    }

    @Override
    public ShoeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoe_card, parent, false);
        return new ShoeViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ShoeViewHolder shoeViewholder, int position) {
        final Shoe shoe = shoeList.get(position);
//        shoeViewholder.shoeImage.setImageBitmap(android.graphics.drawable.); XXX noch nicht gestzt
        shoeViewholder.titel.setText(shoe.getTitel());
        shoeViewholder.card.setBackgroundColor(shoe.isSelected() ? Color.CYAN : Color.WHITE);
        shoeViewholder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoe.setSelected(!shoe.isSelected());
                shoeViewholder.card.setBackgroundColor(shoe.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoeList.size();
    }

    public void refreshEvents(ArrayList<Shoe> shoes) {
        this.shoeList.clear();
        this.shoeList.addAll(shoes);
        notifyDataSetChanged();
    }


    // INNER CLASS ViewHolder
    public static class ShoeViewHolder extends RecyclerView.ViewHolder {

        protected CardView card;
        protected ImageView shoeImage;
        protected TextView titel;

        public ShoeViewHolder(View itemView) {
            super(itemView);

            shoeImage = itemView.findViewById(R.id.shoeimage);
            titel = itemView.findViewById(R.id.cardtitel);
            card = itemView.findViewById(R.id.card_view);
        }
    }
}


