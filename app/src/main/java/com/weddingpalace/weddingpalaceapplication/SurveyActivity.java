package com.weddingpalace.weddingpalaceapplication;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyActivity extends AppCompatActivity {

    private TextView question;
    private Spinner answers;
    private MaterialButton done;
    List<Question> questionsList;
    private int count = 0;
    private String[] userAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        question = findViewById(R.id.question);
        answers = findViewById(R.id.answers);
        done = findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHallsPage();
            }
        });
        questionsList = new ArrayList<>();

        //getQuestionsDummy();
        getAllQuestions();
        //setQuestion();
    }

    private void openHallsPage() {
        Intent i = new Intent(this, HallsActivity.class);
        i.putExtra("answers", userAnswers);
        startActivity(i);
        finish();
    }

    private void getQuestionsDummy() {
        questionsList = new ArrayList<>();

        List<String> answers = new ArrayList<>();
        answers.add("Select answer");
        answers.add("< 20000");
        answers.add("> 20000 & < 50000");
        answers.add("> 50000 & < 80000");
        answers.add("> 80000");

        Question q = new Question("What is your budget?", answers);
        questionsList.add(q);

        answers = new ArrayList<>();
        answers.add("Select answer");
        answers.add("next week");
        answers.add("this month");
        answers.add("next month");
        answers.add("after 2 months");
        q = new Question("Please select date", answers);
        questionsList.add(q);

        answers = new ArrayList<>();
        answers.add("Select answer");
        answers.add("Heliopolis");
        answers.add("Nasr city");
        answers.add("El merghani");
        answers.add("Sheraton");
        q = new Question("Please select your target location", answers);
        questionsList.add(q);

        answers = new ArrayList<>();
        answers.add("Select answer");
        answers.add("Hall");
        answers.add("Hotel");
        q = new Question("Please select the hall type", answers);
        questionsList.add(q);

        answers = new ArrayList<>();
        answers.add("Select answer");
        answers.add("Open Air");
        answers.add("Closed");
        q = new Question("Please select place type", answers);
        questionsList.add(q);

        answers = new ArrayList<>();
        answers.add("Select answer");
        answers.add("< 200");
        answers.add("> 200 & < 500");
        q = new Question("How many person will be invited?", answers);

        questionsList.add(q);
    }

    private void setQuestion() {
        Question currentQuestion = questionsList.get(count);

        question.setText(currentQuestion.getQuestion());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currentQuestion.getAnswers());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answers.setAdapter(dataAdapter);
    }

    public void next(View view) {
        userAnswers[count] = answers.getSelectedItem().toString();
        count++;
        if(count == questionsList.size()){
            done.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        }else{
            setQuestion();
        }
    }

    public void getAllQuestions() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.GET_SURVEY_QUESTIONS_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("test", "GetCards Response: " + response.toString());
                try {

                    JSONArray a=new JSONArray(response);
                    //Log.d("questionsTest",a.toString());
                    int sizeOfArray=a.length();
                    for(int i=0;i<sizeOfArray;i++){
                        JSONObject jObj = a.getJSONObject(i);//all the users in the database

                        Question question = new Gson().fromJson(jObj.toString(), Question.class);
                        //Log.e("questionsTest",question.getId() + " , " + question.getQuestion() + " , " + question.getAnswers().size());

                        questionsList.add(question);
                    }
                    userAnswers = new String[questionsList.size()];
                    setQuestion();

                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("userID", "1");

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
