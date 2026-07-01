package edu.andrews.cptr252.leviwalker.jsonapp;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String jsonArrURL = BuildConfig.JSON_ARRAY_URL;
    private ArrayList<Animal> animalList = new ArrayList<>();
    private int arrSize = 0;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button next = findViewById(R.id.next);
        Button previous = findViewById(R.id.previous);
        TextView displayAnimal = findViewById(R.id.animalStats);

        if (jsonArrURL.isEmpty()) {
            displayAnimal.setText("Set JSON_GENERATOR_URL in your Gradle properties to load animal data.");
        } else {
            loadJSONArray(jsonArrURL, displayAnimal);
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrSize > 0) {
                    if (position == arrSize - 1) {
                        position = 0;
                    } else {
                        position++;
                    }
                    String animalText = "Common Name: " + animalList.get(position).getCommon_name() + "\n" +
                            "Scientific Name: " + animalList.get(position).getScientific_name() + "\n" +
                            "Gender: " + animalList.get(position).getGender() + "\n" +
                            "Weight: " + animalList.get(position).getWeight() + "\n" +
                            "ID: " + animalList.get(position).getId() + "\n" +
                            "AGE: " + animalList.get(position).getAge() + "\n";

                    displayAnimal.setText(animalText);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrSize > 0) {
                    if (position == 0) {
                        position = arrSize - 1;
                    } else {
                        position--;
                    }
                    String animalText = "Common Name: " + animalList.get(position).getCommon_name() + "\n" +
                            "Scientific Name: " + animalList.get(position).getScientific_name() + "\n" +
                            "Gender: " + animalList.get(position).getGender() + "\n" +
                            "Weight: " + animalList.get(position).getWeight() + "\n" +
                            "ID: " + animalList.get(position).getId() + "\n" +
                            "AGE: " + animalList.get(position).getAge() + "\n";

                    displayAnimal.setText(animalText);
                }
            }
        });

    }

    private void loadJSONArray(String JSON_ARRAY_URL, TextView textView){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_ARRAY_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String common_name = jsonObject.getString("common_name");
                                String scientific_name = jsonObject.getString("scientific_name");
                                String gender = jsonObject.getString("gender");
                                int weight = jsonObject.getInt("weight");
                                int id = jsonObject.getInt("id");
                                int age = jsonObject.getInt("Age");
                                Animal animal = new Animal(common_name,scientific_name,gender, weight, id, age);
                                animalList.add(animal);
                            }
                            arrSize = response.length();
                            String animalText = "Common Name: " + animalList.get(position).getCommon_name() + "\n" +
                                    "Scientific Name: " + animalList.get(position).getScientific_name() + "\n" +
                                    "Gender: " + animalList.get(position).getGender() + "\n" +
                                    "Weight: " + animalList.get(position).getWeight() + "\n" +
                                    "ID: " + animalList.get(position).getId() + "\n" +
                                    "AGE: " + animalList.get(position).getAge() + "\n";

                            textView.setText(animalText);
                        } catch(JSONException e){
                        e.printStackTrace();
                        textView.setText(e.toString());

                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "error with JSON: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Error with Json", error);
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    public void load_JSON(TextView textView, String JSON_URL){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            response.put("name", "Bill");
                            response.put("lastname", "Wolfer");

                            String name = response.getString("name");
                            String lastname = response.getString("lastname");
                            int social = response.getInt("social");



                        } catch (JSONException e) {
                            e.printStackTrace();
                            textView.setText(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"error with JSON: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Error with Json", error);
                    }
                });

        requestQueue.add(jsonObjectRequest);

    }

    public void local_JSON(){
        String myJson = "{\n" +
                "  \"name\": \"Alex\",\n" +
                "  \"lastname\": \"Morgan\",\n" +
                "  \"age\": 22,\n" +
                "  \"active\": true,\n" +
                "  \"accounts\": [\n" +
                "      \"Checking\",\n" +
                "      \"Savings\",\n" +
                "      \"Investment\"\n" +
                "  ],\n" +
                "  \"children\": [\n" +
                "      {\n" +
                "          \"name\": \"Luke\",\n" +
                "          \"lastname\": \"Morgan\"\n" +
                "      },\n" +
                "      {\n" +
                "          \"name\": \"Lenzie\",\n" +
                "          \"lastname\": \"Morgan\"\n" +
                "      }\n" +
                "  ]\n" +
                "}";

        try {
            JSONObject jsonObject = new JSONObject(myJson);
            String name = jsonObject.getString("name");
            String lastname = jsonObject.getString("lastname");
            int age = jsonObject.getInt("age");
            boolean active = jsonObject.getBoolean("active");

            JSONArray children = jsonObject.getJSONArray("children");

            String childtext = "\nChildren\n";

            String locresult = name + " " + lastname + " " + age + " " + active;

            for (int i = 0; i < children.length(); i++) {
                JSONObject child = children.getJSONObject(i);
                locresult += "\n" + child.getString("name")  + " ";
                locresult += child.getString("lastname");
            }




        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
