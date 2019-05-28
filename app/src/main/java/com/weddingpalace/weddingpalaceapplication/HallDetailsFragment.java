package com.weddingpalace.weddingpalaceapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HallDetailsFragment extends DialogFragment implements ReservationsAdapter.ItemClickListener {

    private ImageView hallImage;
    private TextView hallName, hallType, hallPrice, close;
    private DatePicker reservationDate;
    private MaterialButton hallLocation;
    private IReserveClick mListener;
    private Hall hall;
    private String dateString, startDate, endDate;
    private RatingBar ratingBar;

    private RecyclerView recyclerView;
    private List<ReservationTime> reservationTimeList;
    private ReservationsAdapter mAdapter;

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
        hallLocation = view.findViewById(R.id.hallLocation);
        hallImage = view.findViewById(R.id.hallImage);
        ratingBar = view.findViewById(R.id.hall_rating);

        //reserveButton = view.findViewById(R.id.reserve);
        //reservationDate = view.findViewById(R.id.reservationDate);

        reservationTimeList = new ArrayList<>();
        mAdapter = new ReservationsAdapter(getContext(), reservationTimeList, this);

        recyclerView = view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        hall = (Hall) getArguments().getSerializable("hall");

        ratingBar.setRating(hall.getRating());
        startDate = getArguments().getString("startDate");
        endDate = getArguments().getString("endDate");
        hallName.setText(hall.getName());
        hallPrice.setText(hall.getPrice()+"");
        hallType.setText(hall.getPlaceType() == PlaceType.OPEN_AIR?" Open Air":"Closed");


        Picasso.get().load(Config.IMAGE_BASE_URL + hall.getImage()).into(hallImage);
        close = view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        hallLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+hall.getLocation()+""));
                startActivity(intent);
            }
        });

        getAllHallReservations();
    }

    private void checkReservationDB() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.CHECK_RESERVATION_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "Halls Response: " + response);
                if (response.equals("available")){
                    //selectPackage();
                }else{
                    //reserveButton.setEnabled(true);
                    showErrorMessage();
                }

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
                params.put("hallId", hall.getId() + "");
                params.put("date", dateString);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void getAllHallReservations() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.GET_HALL_RESERVATIONS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "Halls Response: " + response);
                try {

                    getAllReservations(startDate, endDate);
                    JSONArray a=new JSONArray(response);
                    //Log.d("questionsTest",a.toString());
                    int sizeOfArray=a.length();
                    for(int i=0;i<sizeOfArray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database
                        searchForReservationDate(jObj.getString("day_date"));
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

                Map<String, String> params = new HashMap<>();
                params.put("startDate", startDate);
                params.put("endDate", endDate);
                params.put("hallId", hall.getId() + "");

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void searchForReservationDate(String date){
        for (ReservationTime rt : reservationTimeList) {
            if (rt.getDate().equals(date)){
                rt.setReserved(true);
            }
        }
    }

    public void getAllReservations(String startDate, String endDate){

        String[] startDateArray = startDate.split("-");
        String[] endDateArray = endDate.split("-");

        int startDay = Integer.parseInt(startDateArray[0]);
        int endDay = Integer.parseInt(endDateArray[0]);
        String currentDate = "";

        for (int i = startDay; i <=endDay ; i++) {

            currentDate = i+"-"+startDateArray[1]+"-"+startDateArray[2];

            reservationTimeList.add(new ReservationTime(currentDate, false));
        }
    }

    private void showErrorMessage() {
        Toast.makeText(getContext(), "reservation is not available on this date", Toast.LENGTH_SHORT).show();
    }

    private void selectPackage(ReservationTime reservationTime) {
        Toast.makeText(getContext(), "reservation is available", Toast.LENGTH_SHORT).show();
        mListener.onReserveClicked(hall, reservationTime);
        //openPackagesPage();
        dismiss();
    }

    public void setListener(IReserveClick listener) {
        this.mListener = listener;
    }

    @Override
    public void onReserveClickListener(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        //reserveButton.setEnabled(false);
        ReservationTime reservationTime = reservationTimeList.get(0);
        selectPackage(reservationTime);
    }
}
