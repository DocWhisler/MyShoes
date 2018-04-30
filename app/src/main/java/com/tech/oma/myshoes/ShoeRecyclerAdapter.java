package com.tech.oma.myshoes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoeRecyclerAdapter extends RecyclerView.Adapter<ShoeRecyclerAdapter.ShoeViewHolder> {

    private Context context;
    private ArrayList<Shoe> shoeList;
    private ArrayList<Integer> selectedIds = new ArrayList<>();

    public ShoeRecyclerAdapter(Context context, ArrayList<Shoe> shoes){
        this.context = context;
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
        String price = ""+shoe.getPrice();
        Bitmap bitmap = BitmapFactory.decodeFile(shoe.getImagePath());

        shoeViewholder.price.setText(price);
        shoeViewholder.titel.setText(shoe.getTitel());
        shoeViewholder.tag.setText(shoe.getArt());
        shoeViewholder.description.setText(shoe.getDescription());

        if(bitmap != null)
            shoeViewholder.shoeImage.setImageBitmap(bitmap);

        int id = shoe.getId();
        if (selectedIds.contains(id)){
            //if item is selected then,set foreground color of FrameLayout.
            shoeViewholder.card.setForeground(new ColorDrawable(ContextCompat.getColor(context,R.color.card_colorControlActivated)));
        }
        else {
            //else remove selected item color.
            shoeViewholder.card.setForeground(new ColorDrawable(ContextCompat.getColor(context,android.R.color.transparent)));
        }
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

    public void removeItem(int position) {
        shoeList.remove(position);
        notifyItemRemoved(position);
    }

    public Shoe getItem(int position){
        return shoeList.get(position);
    }

    public void setSelectedIds(ArrayList<Integer> selectedIds){
        this.selectedIds = selectedIds;
    }

    // INNER CLASS ViewHolder
    public static class ShoeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        protected CardView card;
        protected ImageView shoeImage;
        protected TextView titel;
        protected TextView tag;
        protected TextView price;
        protected TextView description;
        protected int pos = -1;

        public ShoeViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(this);

            card = itemView.findViewById(R.id.card_view);
            shoeImage = itemView.findViewById(R.id.cardshoeimage);
            titel = itemView.findViewById(R.id.cardtitel);
            tag = itemView.findViewById(R.id.cardtag);
            price = itemView.findViewById(R.id.cardprice);
            description = itemView.findViewById(R.id.carddescription);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(itemView.getContext(), "Position:" + this.getPos() , Toast.LENGTH_LONG).show();
        }

        public int getPos() {
            return getAdapterPosition();
        }
    }
}


