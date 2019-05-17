package com.weddingpalace.weddingpalaceapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HallDetailsFragment extends DialogFragment {

    private ImageView hallImage;
    private TextView hallName, hallType, hallPrice, close;
    private DatePicker reservationDate;
    private MaterialButton reserveButton;
    private IReserveClick mListener;
    private Hall hall;
    private String dateString;

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
        reserveButton = view.findViewById(R.id.reserve);
        reservationDate = view.findViewById(R.id.reservationDate);

        hall = (Hall) getArguments().getSerializable("hall");
        hallName.setText(hall.getName());
        hallPrice.setText(hall.getPrice()+"");
        hallType.setText(hall.getPlaceType() == PlaceType.OPEN_AIR?" Open Air":"Closed");

        close = view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserveButton.setEnabled(false);
                dateString = reservationDate.getDayOfMonth() + " - " + reservationDate.getMonth() + " - " + reservationDate.getYear();
                checkReservationDB();
            }
        });
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
                    selectPackage();
                }else{
                    reserveButton.setEnabled(true);
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

    private void showErrorMessage() {
        Toast.makeText(getContext(), "reservation is not available on this date", Toast.LENGTH_SHORT).show();
    }

    private void selectPackage() {
        Toast.makeText(getContext(), "reservation is available", Toast.LENGTH_SHORT).show();
        mListener.onReserveClicked(hall, dateString);
        //openPackagesPage();
        dismiss();
    }

    public void setListener(IReserveClick listener) {
        this.mListener = listener;
    }
}
