package com.tech.oma.myshoes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tech.oma.myshoes.R;
import com.tech.oma.myshoes.dataobjects.ShoeList;

import java.util.ArrayList;

public class ShoeListRecyclerAdapter extends RecyclerView.Adapter<ShoeListRecyclerAdapter.ListViewHolder>{
    private Context context;
    private ArrayList<ShoeList> shoeLists;

    public ShoeListRecyclerAdapter(Context context, ArrayList<ShoeList> lists){
        this.context = context;
        this.shoeLists = lists;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmanagement_layout, parent, false);
        return new ShoeListRecyclerAdapter.ListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return shoeLists.size();
    }

    //INNER CLASS Viewholder
    class ListViewHolder extends RecyclerView.ViewHolder{

        public ListViewHolder(View itemView) {
            super(itemView);
        }
    }
}


