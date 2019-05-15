package com.weddingpalace.weddingpalaceapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HallDetailsFragment extends DialogFragment implements ReservationsAdapter.ItemClickListener{

    private ImageView hallImage;
    private TextView hallName, hallType, hallPrice, close;
    private RecyclerView recyclerView;
    private List<ReservationTime> reservationsList;
    private ReservationsAdapter mAdapter;
    private IReserveClick mListener;

    public HallDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hall_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        hallName = view.findViewById(R.id.hallName);
        hallType = view.findViewById(R.id.hallType);
        hallPrice = view.findViewById(R.id.hallPrice);

        reservationsList = new ArrayList<>();
        mAdapter = new ReservationsAdapter(getContext(), reservationsList, this);

        recyclerView = view.findViewById(R.id.recycerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        Hall hall = (Hall) getArguments().getSerializable("hall");
        hallName.setText(hall.getName());
        hallPrice.setText(hall.getPrice()+"");
        hallType.setText(hall.getHallType() == HallType.OPEN_AIR?" Open Air":"Closed");

        reservationsList.addAll(hall.getReservationTimes());
        close = view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onReserveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Toast.makeText(getContext(), "reserved successfully", Toast.LENGTH_SHORT).show();
        mListener.onReserveClicked();
        //openPackagesPage();
        dismiss();
    }

    private void openPackagesPage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // String array for alert dialog multi choice items
        String[] colors = new String[]{
                "Red",
                "Green",
                "Blue",
                "Purple",
                "Olive"
        };

        // Boolean array for initial selected items
        final boolean[] checkedColors = new boolean[]{
                false, // Red
                true, // Green
                false, // Blue
                true, // Purple
                false // Olive

        };

        // Convert the color array to list
        final List<String> colorsList = Arrays.asList(colors);

        // Set multiple choice items for alert dialog
                /*
                    AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[]
                    checkedItems, DialogInterface.OnMultiChoiceClickListener listener)
                        Set a list of items to be displayed in the dialog as the content,
                        you will be notified of the selected item via the supplied listener.
                 */
                /*
                    DialogInterface.OnMultiChoiceClickListener
                    public abstract void onClick (DialogInterface dialog, int which, boolean isChecked)

                        This method will be invoked when an item in the dialog is clicked.

                        Parameters
                        dialog The dialog where the selection was made.
                        which The position of the item in the list that was clicked.
                        isChecked True if the click checked the item, else false.
                 */
        builder.setMultiChoiceItems(colors, checkedColors, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedColors[which] = isChecked;

                // Get the current focused item
                String currentItem = colorsList.get(which);

                // Notify the current action
                Toast.makeText(getActivity(),
                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Your preferred colors?");

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                //tv.setText("Your preferred colors..... \n");
                for (int i = 0; i<checkedColors.length; i++){
                    boolean checked = checkedColors[i];
                    if (checked) {
                        //tv.setText(tv.getText() + colorsList.get(i) + "\n");
                    }
                }
            }
        });

        // Set the negative/no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    public void setListener(IReserveClick listener) {
        this.mListener = listener;
    }
}
