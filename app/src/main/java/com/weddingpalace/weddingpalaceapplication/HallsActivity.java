package com.weddingpalace.weddingpalaceapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HallsActivity extends AppCompatActivity implements HallsAdapter.ItemClickListener, IReserveClick {

    private RecyclerView recyclerView;
    private List<Hall> hallsList;
    private HallsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halls);

        hallsList = new ArrayList<>();
        mAdapter = new HallsAdapter(hallsList, this);

        recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        getAllHalls();
    }

    private void getAllHalls() {

        Location hallLocation = new Location("");
        hallLocation.setLatitude(33.2432);
        hallLocation.setLongitude(-33.2432);

        List<ReservationTime> reservationTimes = new ArrayList<>();

        ReservationTime reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        Hall h = new Hall("", "El Lottouse", HallType.OPEN_AIR, hallLocation, ReservationType.WEDDING, 50000.0);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        hallLocation = new Location("");
        hallLocation.setLatitude(33.2432);
        hallLocation.setLongitude(-33.2432);

        reservationTimes = new ArrayList<>();

        reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);

        h = new Hall("", "El Lo2lo2a", HallType.OPEN_AIR, hallLocation, ReservationType.WEDDING, 36800.0);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        hallLocation = new Location("");
        hallLocation.setLatitude(33.2432);
        hallLocation.setLongitude(-33.2432);
        reservationTimes = new ArrayList<>();

        reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);

        h = new Hall("", "Diamond Hall", HallType.OPEN_AIR, hallLocation, ReservationType.WEDDING, 66000.0);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        hallLocation = new Location("");
        hallLocation.setLatitude(33.2432);
        hallLocation.setLongitude(-33.2432);
        reservationTimes = new ArrayList<>();

        reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);

        h = new Hall("", "Square Hall", HallType.OPEN_AIR, hallLocation, ReservationType.WEDDING, 52000.0);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        hallLocation = new Location("");
        hallLocation.setLatitude(33.2432);
        hallLocation.setLongitude(-33.2432);
        reservationTimes = new ArrayList<>();

        reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);

        h = new Hall("", "Lolo Hall", HallType.OPEN_AIR, hallLocation, ReservationType.WEDDING, 47000.0);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout){
            goToLogInPage();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToLogInPage() {
        //startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onViewClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Hall hall = hallsList.get(itemPosition);

        HallDetailsFragment fr = new HallDetailsFragment();
        fr.setListener(this);
        Bundle b = new Bundle();
        b.putSerializable("hall", hall);
        fr.setArguments(b);
        fr.show(getSupportFragmentManager(), "fragment");
    }

    @Override
    public void onReserveClicked() {
        openPackagesPage();
    }


    private void openPackagesPage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // String array for alert dialog multi choice items
        String[] colors = new String[]{
                "Photographer",
                "Bands",
                "MakeUp Artist",
                "Belly Dancer"
        };

        // Boolean array for initial selected items
        final boolean[] checkedColors = new boolean[]{
                false, // Red
                false, // Green
                false, // Blue
                false, // Purple

        };

        // Convert the color array to list
        final List<String> colorsList = Arrays.asList(colors);

        builder.setMultiChoiceItems(colors, checkedColors, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedColors[which] = isChecked;

                // Get the current focused item
                String currentItem = colorsList.get(which);


            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Kindly select from our packages?");

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
}
