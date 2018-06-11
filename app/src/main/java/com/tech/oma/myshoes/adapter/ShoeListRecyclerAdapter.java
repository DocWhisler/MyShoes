package com.tech.oma.myshoes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tech.oma.myshoes.R;
import com.tech.oma.myshoes.dataobjects.ShoeList;

import java.util.ArrayList;

public class ShoeListRecyclerAdapter extends RecyclerView.Adapter<ShoeListRecyclerAdapter.ShoeListViewHolder>{
    private Context context;
    private ArrayList<ShoeList> shoeLists;

    public ShoeListRecyclerAdapter(Context context, ArrayList<ShoeList> lists){
        this.context = context;
        this.shoeLists = lists;
    }

    @Override
    public ShoeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoelist_card, parent, false);
        return new ShoeListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ShoeListViewHolder holder, int position) {
        ShoeList list = shoeLists.get(position);
        holder.tv.setText(list.getName());
    }

    @Override
    public int getItemCount() {
        return shoeLists.size();
    }

    //INNER CLASS Viewholder
    class ShoeListViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public ShoeListViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.list_card_name);
        }
    }
}


