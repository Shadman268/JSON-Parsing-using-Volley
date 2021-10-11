package com.example.jsonparsing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView courseNameTV, courseTracksTV, courseBatchTV;
    private ImageView courseIV;
    private ProgressBar loadingPB;
    private CardView courseCV;

    String url = "https://jsonkeeper.com/b/63OH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingPB = findViewById(R.id.idLoadingPB);
        courseCV = findViewById(R.id.idCVCourse);
        courseNameTV = findViewById(R.id.idTVCourseName);
        courseTracksTV = findViewById(R.id.idTVTracks);
        courseBatchTV = findViewById(R.id.idTVBatch);
        courseIV = findViewById(R.id.idIVCourse);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                loadingPB.setVisibility(View.GONE);


                courseCV.setVisibility(View.VISIBLE);
                try {

                    String courseName = response.getString("courseName");
                    String courseTracks = response.getString("courseTracks");
                    String courseMode = response.getString("courseMode");
                    String courseImageURL = response.getString("courseimg");

                    courseNameTV.setText(courseName);
                    courseTracksTV.setText(courseTracks);
                    courseBatchTV.setText(courseMode);


                    Picasso.get().load(courseImageURL).into(courseIV);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}