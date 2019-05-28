package com.weddingpalace.weddingpalaceapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HallsActivity extends AppCompatActivity implements HallsAdapter.ItemClickListener, IReserveClick {

    private RecyclerView recyclerView;
    private List<Hall> hallsList;
    private List<Package> packagesList;
    private HallsAdapter mAdapter;
    private String[] userAnswers;

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

        userAnswers = getIntent().getStringArrayExtra("answers");

        getAllHalls();
    }

    private void getAllHallsDummy() {

        List<ReservationTime> reservationTimes = new ArrayList<>();

        ReservationTime reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        Hall h = new Hall("", "El Lottouse", HallType.HALL, "", ReservationType.WEDDING, 50000);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        reservationTimes = new ArrayList<>();

        reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);

        h = new Hall("", "El Lo2lo2a", HallType.HALL, "", ReservationType.WEDDING, 36800);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        reservationTimes = new ArrayList<>();

        reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);

        h = new Hall("", "Diamond Hall", HallType.HALL, "", ReservationType.WEDDING, 66000);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        reservationTimes = new ArrayList<>();

        reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);

        h = new Hall("", "Square Hall", HallType.HALL, "", ReservationType.WEDDING, 52000);
        h.setReservationTimes(reservationTimes);
        hallsList.add(h);

        reservationTimes = new ArrayList<>();

        reservationTime = new ReservationTime("1/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("2/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("3/6/2019  6 pm : 11 pm", true);
        reservationTimes.add(reservationTime);
        reservationTime = new ReservationTime("4/6/2019  6 pm : 11 pm", false);
        reservationTimes.add(reservationTime);

        h = new Hall("", "Lolo Hall", HallType.HALL, "", ReservationType.WEDDING, 47000);
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
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onViewClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        Hall hall = hallsList.get(itemPosition);

        String monthAndYear = getMonthAndYear(userAnswers[5]);

        String startDay = getStartDay(userAnswers[6]);
        String endDay = getEndDay(userAnswers[6]);

        HallDetailsFragment fr = new HallDetailsFragment();
        fr.setListener(this);
        Bundle b = new Bundle();
        b.putSerializable("hall", hall);
        b.putString("startDate", startDay + "-" + monthAndYear);
        b.putString("endDate", endDay + "-" + monthAndYear);
        fr.setArguments(b);
        fr.show(getSupportFragmentManager(), "fragment");
    }

    private String getStartDay(String userAnswer) {
        int startDay = 1;
        switch (userAnswer){
            case "first week": startDay += 0; break;
            case "second week": startDay += 7; break;
            case "third week": startDay += 14; break;
            case "fourth week": startDay += 21; break;
        }
        Log.e("", "");
        return startDay + "";
    }

    private String getEndDay(String userAnswer) {
        int endDay = 7;
        switch (userAnswer){
            case "first week": endDay += 0; break;
            case "second week": endDay += 7; break;
            case "third week": endDay += 14; break;
            case "fourth week": endDay += 21; break;
        }

        Log.e("", "");
        return endDay + "";
    }

    private String getMonthAndYear(String userAnswer) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) +1;

        switch (userAnswer){
            case "current months": month += 0; break;
            case "after 1 months": month += 1; break;
            case "after 2 months": month += 2; break;
            case "after 3 months": month += 3; break;
            case "after 4 months": month += 4; break;
        }

        Log.e("", "");

        return month + "-" + year;
    }

    @Override
    public void onReserveClicked(Hall hall, ReservationTime reservationTime) {
        getAllPackages(hall, reservationTime.getDate());
    }

    private void openPackagesPage(final Hall hall, final String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String[] packages = getPackagesArray();

        // Boolean array for initial selected items
        final boolean[] checkedPackage = new boolean[]{
                false, // Red
                false, // Green
                false, // Blue
                false, // Purple
        };

        // Convert the color array to list
        final List<String> colorsList = Arrays.asList(packages);

        builder.setMultiChoiceItems(packages, checkedPackage, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // Update the current focused item's checked status
                checkedPackage[which] = isChecked;
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
                int totalPrice = 0;
                for (int i = 0; i<checkedPackage.length; i++){
                    boolean checked = checkedPackage[i];
                    if (checked) {
                        totalPrice += packagesList.get(i).getPrice();
                        //tv.setText(tv.getText() + colorsList.get(i) + "\n");
                    }
                }
                totalPrice += hall.getPrice();

                reserveDB(hall, date, totalPrice);
            }
        });

        // Set the negative/no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
                reserveDB(hall, date, hall.getPrice());
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
                Toast.makeText(HallsActivity.this, "reservation is canceled", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    private String[] getPackagesArray() {
        String[] packagesArray = new String[packagesList.size()];
        for (int i = 0; i<packagesArray.length; i++){
            Package myPackage = packagesList.get(i);
            packagesArray[i] = myPackage.getName() + "  -  " + myPackage.getPrice() + " LE";
        }
        return packagesArray;
    }

    public void getAllHalls() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.GET_HALLS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "Halls Response: " + response);
                try {

                    JSONArray a=new JSONArray(response);
                    //Log.d("questionsTest",a.toString());
                    int sizeOfArray=a.length();
                    for(int i=0;i<sizeOfArray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database

                        Hall hall = new Gson().fromJson(jObj.toString(), Hall.class);
                        Log.e("hallsTest",hall.getId() + " , " + hall.getName() + " , " + hall.getPrice());

                        hallsList.add(hall);
                        //questionsList.add(question);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                String[] priceRange = userAnswers[0].split(" - ");
                String[] capacityRange = userAnswers[2].split(" - ");

                Map<String, String> params = new HashMap<>();
                params.put("minPrice", priceRange[0]);
                params.put("maxPrice", priceRange[1]);
                params.put("region", userAnswers[1]);
                params.put("minCapacity", capacityRange[0]);
                params.put("maxCapacity", capacityRange[1]);
                params.put("hallType", userAnswers[3].equals("Hall")?"0":"1");
                params.put("placeType", userAnswers[4].equals("Open Air")?"0":"1");

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void getAllPackages(final Hall hall, final String date) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        packagesList = new ArrayList<>();

        String url = Config.GET_PACKAGES_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "Packages Response: " + response);
                try {

                    JSONArray a=new JSONArray(response);
                    //Log.d("questionsTest",a.toString());
                    int sizeOfArray=a.length();
                    for(int i=0;i<sizeOfArray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database

                        Package aPackage = new Gson().fromJson(jObj.toString(), Package.class);
                        //Log.e("hallsTest",hall.getId() + " , " + hall.getName() + " , " + hall.getPrice());

                        packagesList.add(aPackage);
                    }

                    openPackagesPage(hall, date);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void reserveDB(final Hall hall, final String date, final int totalPrice) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.RESERVE_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "Halls Response: " + response);
                AlertDialogHelper.showAlert(HallsActivity.this, "Reserved successfully", "Your reservation has been submitted successfully \nWith total price = "+totalPrice+".\nYou need to pay a deposit (30%) within 3 days or your reservation will be canceled", "Ok");

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Registration Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<>();
                params.put("hallId", hall.getId()+"");
                params.put("userId", "1");
                params.put("total_price", totalPrice+"");
                params.put("date", date);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
