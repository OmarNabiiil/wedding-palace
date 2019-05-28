package com.weddingpalace.weddingpalaceapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText mEmail, mPassword, mConfirmPassword, mName, mMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);
        mName = findViewById(R.id.username);
        mMobile = findViewById(R.id.mobile);
    }

    public void register(View view) {
        if(validate()) {
            view.setEnabled(false);
            registerUserDB(view);
        }
    }


    public void registerUserDB(final View view) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.REGISTRATION_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "Registration Response: " + response);
                if (response.equals("fail")){
                    AlertDialogHelper.showAlert(RegistrationActivity.this, "Warning", "Login failed!", "Ok");
                    view.setEnabled(true);
                }else{
                    AlertDialogHelper.showAlert(RegistrationActivity.this, "Success", "Registration finished successfully", "Ok", new AlertDialogHelper.Callback() {
                        @Override
                        public void onSuccess() {
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "Login Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<>();
                params.put("email", mEmail.getText().toString());
                params.put("password", mPassword.getText().toString());
                params.put("name", mName.getText().toString());
                params.put("mobile", mMobile.getText().toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private boolean validate() {
        boolean isValid = true;

        mName.setError(null);
        mEmail.setError(null);
        mMobile.setError(null);
        mPassword.setError(null);
        mConfirmPassword.setError(null);

        if (TextUtils.isEmpty(mName.getText()) || mName.getText().toString().equals(" ")){
            mName.requestFocus();
            mName.setError(getString(R.string.validation_name));
            isValid = false;
        }
        if (TextUtils.isEmpty(mEmail.getText()) || mEmail.getText().toString().equals(" ")){
            mEmail.requestFocus();
            mEmail.setError(getString(R.string.validation_mail));
            isValid = false;
        }
        if (!mEmail.getText().toString().contains("@") || !mEmail.getText().toString().contains(".")){
            mEmail.requestFocus();
            mEmail.setError(getString(R.string.format_mail));
            isValid = false;
        }
        if (TextUtils.isEmpty(mMobile.getText()) || mMobile.getText().toString().equals(" ")){
            mMobile.requestFocus();
            mMobile.setError(getString(R.string.validation_mobile));
            isValid = false;
        }
        if (mMobile.getText().toString().length()<11 || mMobile.getText().toString().length()>11){
            mMobile.requestFocus();
            mMobile.setError(getString(R.string.format_mobile));
            isValid = false;
        }
        if (!mMobile.getText().toString().startsWith("0")){
            mMobile.requestFocus();
            mMobile.setError(getString(R.string.format_mobile_starting_digit));
            isValid = false;
        }
        if (TextUtils.isEmpty(mPassword.getText()) || mPassword.getText().toString().equals(" ")){
            mPassword.requestFocus();
            mPassword.setError(getString(R.string.password_validation));
            isValid = false;
        }
        if (TextUtils.isEmpty(mConfirmPassword.getText()) || mConfirmPassword.getText().toString().equals(" ")){
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getString(R.string.password_confirmation));
            isValid = false;
        }
        if (!mConfirmPassword.getText().toString().equals(mPassword.getText().toString())){
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getString(R.string.password_match));
            isValid = false;
        }

        if (mName.getText().toString().contains("<") || mName.getText().toString().contains(">") ||
                mName.getText().toString().contains("'")){
            mName.requestFocus();
            mName.setError(getString(R.string.format_name));
            isValid = false;
        }

        if (mEmail.getText().toString().contains("<") || mEmail.getText().toString().contains(">") ||
                mEmail.getText().toString().contains("'")){
            mEmail.requestFocus();
            mEmail.setError(getString(R.string.format_mail));
            isValid = false;
        }

        if (mMobile.getText().toString().contains("<") || mMobile.getText().toString().contains(">") ||
                mMobile.getText().toString().contains("'")){
            mMobile.requestFocus();
            mMobile.setError(getString(R.string.format_mobile));
            isValid = false;
        }

        if (mPassword.getText().toString().contains("<") || mPassword.getText().toString().contains(">") ||
                mPassword.getText().toString().contains("'")){
            mPassword.requestFocus();
            mPassword.setError(getString(R.string.password_format));
            isValid = false;
        }

        if (mConfirmPassword.getText().toString().contains("<") || mConfirmPassword.getText().toString().contains(">") ||
                mConfirmPassword.getText().toString().contains("'")){
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError(getString(R.string.format_field));
            isValid = false;
        }

        return isValid;
    }
}
