package com.luccahora.youtubeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luccahora.youtubeapp.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetail extends AppCompatActivity {

    private String idMovie;
    private String titleMovie;
    private String backdrop_path;
    private String overview;
    private String release_date;
    private String rating;
private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //        Config toolbar
        ImageView leftIcon = findViewById(R.id.left_icon);
        TextView titleToolbar = findViewById(R.id.titleToolbar);
        ImageView imageView = findViewById(R.id.poster_image);
        TextView ratingView = findViewById(R.id.movieRating);
        TextView movieOverview = findViewById(R.id.movieOverview);
        TextView data = findViewById(R.id.data);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idMovie = bundle.getString("idMovie");
            titleMovie = bundle.getString("titleMovie");
            backdrop_path = bundle.getString("moviePoster");
            overview = bundle.getString("movieOverview");
            release_date = bundle.getString("release_date");
            rating = bundle.getString("rating");
        }

        //        Toolbar
        titleToolbar.setText(titleMovie);

        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieDetail.this, MainActivity.class);
                startActivity(i);
            }
        });

        String url = backdrop_path;
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + url).into(imageView);
        movieOverview.setText(overview);
        data.setText(release_date);
        ratingView.setText(rating);
    }
}