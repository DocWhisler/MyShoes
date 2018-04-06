package com.tech.oma.myshoes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Whisler on 23.03.2018.
 */

public class CustomAdapter extends BaseAdapter {

    private ArrayList<Shoe> listItem;
    private Context mContext;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Context mContext, ArrayList<Shoe> listItem) {
        this.listItem = listItem;
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.shoe_list, null);
        holder.textView = rowView.findViewById(R.id.textView1);
        holder.imageView = rowView.findViewById(R.id.imageView1);

        holder.textView.setText(listItem.get(position).getTitel());
//        holder.imageView.setImageResource(listItem.);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(mContext, "You Clicked " + listItem.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

    public void refreshEvents(ArrayList<Shoe> shoes) {
        this.listItem.clear();
        this.listItem.addAll(shoes);
        notifyDataSetChanged();
    }
}
