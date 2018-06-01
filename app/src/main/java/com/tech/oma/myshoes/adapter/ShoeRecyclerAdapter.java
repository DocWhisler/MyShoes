package com.tech.oma.myshoes.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.tech.oma.myshoes.R;
import com.tech.oma.myshoes.dataobjects.Shoe;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.tech.oma.myshoes.activities.MainActivity.clearDim;

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
        final Bitmap bitmap = BitmapFactory.decodeFile(shoe.getImagePath());

        shoeViewholder.price.setText(price);
        shoeViewholder.titel.setText(shoe.getTitel());
        shoeViewholder.tag.setText(shoe.getTag());
        shoeViewholder.description.setText(shoe.getDescription());

        if(bitmap != null){
            shoeViewholder.shoeImageView.setImageBitmap(bitmap);
            shoeViewholder.shoeImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                // Android PopUp Window
                createPopUpWindow(v, bitmap);
                }
            });
        }

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

    private void createPopUpWindow(View v, Bitmap bitmap) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);

        ViewGroup container = null;
        if(inflater != null)
            container = (ViewGroup) inflater.inflate(R.layout.popup_picture, null);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null && container != null)
        {
            windowManager.getDefaultDisplay().getMetrics(metrics);

            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setWidth((int) (width*.8));
            popupWindow.setHeight((int) (height*.8));
            popupWindow.setAnimationStyle(R.style.style_popup_anim);
            popupWindow.showAtLocation(v , Gravity.CENTER, 0, 0);

            ImageView popupView = container.findViewById(R.id.popup_picture);
            popupView.setImageBitmap(bitmap);

            final ViewGroup root = (ViewGroup) v.getRootView();
            applyDim(root, .5);

            // Close PopUp outside
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    clearDim(root);
                }
            });
        }
    }

    private void applyDim(@NonNull ViewGroup parent, double trimAmmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * trimAmmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public void refresh(ArrayList<Shoe> shoes) {
        this.shoeList.clear();
        this.shoeList.addAll(shoes);
        notifyDataSetChanged();
    }

    public Shoe getItem(int position){
        return shoeList.get(position);
    }

    public void setSelectedIds(ArrayList<Integer> selectedIds){
        this.selectedIds = selectedIds;
    }

    private ArrayList<Shoe> getShoeList() {
        return shoeList;
    }

    // INNER CLASS ViewHolder
    public class ShoeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private CardView card;
        private ImageView shoeImageView;
        private TextView titel;
        private TextView tag;
        private TextView price;
        private TextView description;

        private ShoeViewHolder(View itemView){
            super(itemView);

            itemView.setOnClickListener(this);

            card = itemView.findViewById(R.id.card_view);
            shoeImageView = itemView.findViewById(R.id.cardshoeimage);
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
            int pos = getAdapterPosition();

            if(pos != RecyclerView.NO_POSITION){
                Shoe shoe = getShoeList().get(pos);
                String message = "OID '" + shoe.getOid() + "' Erzeugt '" + shoe.getCreated() + "'";
                Toast.makeText(itemView.getContext(), message , Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(itemView.getContext(), "NISCHT!!!" , Toast.LENGTH_LONG).show();
        }
    }
}


