package com.weddingpalace.weddingpalaceapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.MyViewHolder> {

    private List<ReservationTime> reservationTimeList;
    private ItemClickListener monClickListener;
    private Context mContext;

    public interface ItemClickListener{
        void onReserveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView date, status;
        public MaterialButton reserve;

        public MyViewHolder(View view) {
            super(view);
            date=view.findViewById(R.id.date);
            status=view.findViewById(R.id.status);
            reserve=view.findViewById(R.id.reserve);
            reserve.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItemPosition=getAdapterPosition();
            monClickListener.onReserveClickListener(this, clickedItemPosition);
        }
    }

    public ReservationsAdapter(Context mContext, List<ReservationTime> reservationTimeList, ItemClickListener monClickListener) {
        this.reservationTimeList = reservationTimeList;
        this.monClickListener = monClickListener;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reservations_list_item, viewGroup, false);

        return new ReservationsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ReservationTime reservationTime = reservationTimeList.get(i);

        myViewHolder.date.setText(reservationTime.getDate());
        myViewHolder.status.setText(reservationTime.isReserved()? "Reserved": "Available");
        myViewHolder.status.setTextColor(reservationTime.isReserved()? mContext.getResources().getColor(R.color.red): mContext.getResources().getColor(R.color.green));
        myViewHolder.reserve.setVisibility(reservationTime.isReserved()? View.GONE:View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return reservationTimeList.size();
    }

}
