package com.weddingpalace.weddingpalaceapplication;

import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HallsAdapter extends RecyclerView.Adapter<HallsAdapter.MyViewHolder> {

    private List<Hall> hallsList;
    private ItemClickListener monClickListener;

    public interface ItemClickListener{
        void onViewClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name, price;
        public MaterialButton view;

        public MyViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.hallName);
            price=view.findViewById(R.id.hallPrice);
            view=view.findViewById(R.id.view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItemPosition=getAdapterPosition();
            monClickListener.onViewClickListener(this, clickedItemPosition);
        }
    }

    public HallsAdapter(List<Hall> hallsList, ItemClickListener monClickListener) {
        this.hallsList = hallsList;
        this.monClickListener = monClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.halls_list_item, viewGroup, false);

        return new HallsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Hall h = hallsList.get(i);

        myViewHolder.name.setText(h.getName());
        myViewHolder.price.setText(h.getPrice()+" LE");
    }

    @Override
    public int getItemCount() {
        return hallsList.size();
    }
}
