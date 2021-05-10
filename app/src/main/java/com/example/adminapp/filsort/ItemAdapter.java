package com.example.adminapp.filsort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminapp.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "You clicked on " + items.get(position).getName() + " - color:" + items.get(position).getColor() + " | size:" + items.get(position).getSize() + " | price:" + items.get(position).getPrice();
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
        myViewHolder.name.setText(items.get(position).getName());
        myViewHolder.color.setText("Color: " + items.get(position).getColor());
        myViewHolder.size.setText("Size: " + items.get(position).getSize());
        myViewHolder.price.setText("Price: " + items.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView name;
        TextView color;
        TextView size;
        TextView price;

        public MyViewHolder(View view) {
            super(view);
            container = view;
            name = view.findViewById(R.id.name);
            color = view.findViewById(R.id.color);
            size = view.findViewById(R.id.size);
            price = view.findViewById(R.id.price);
        }
    }

}
