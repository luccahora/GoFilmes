package com.luccahora.youtubeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luccahora.youtubeapp.R;
import com.luccahora.youtubeapp.model.MoviesResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {

    private List<MoviesResult> videos = new ArrayList<>();
    private Context context;

    public AdapterMovie(List<MoviesResult> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video, parent, false);
        return new AdapterMovie.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MoviesResult movie = videos.get(position);
        holder.titulo.setText(movie.title);

        String url = movie.backdrop_path;
        Picasso.get().load("https://image.tmdb.org/t/p/w400/" + url).into(holder.capa);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        ImageView capa;

        public MyViewHolder(View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textTitulo);
            capa = itemView.findViewById(R.id.imageCapa);
        }

    }

}
