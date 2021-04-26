package com.luccahora.youtubeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.luccahora.youtubeapp.R;
import com.luccahora.youtubeapp.adapter.AdapterMovie;
import com.luccahora.youtubeapp.helder.RetrofitConfig;
import com.luccahora.youtubeapp.helder.RequestConfig;
import com.luccahora.youtubeapp.listener.RecyclerItemClickListener;
import com.luccahora.youtubeapp.model.MoviesResult;
import com.luccahora.youtubeapp.model.Resultado;
import com.luccahora.youtubeapp.services.MovieService;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerVideos;
    private MaterialSearchView searchView;

    private List<MoviesResult> moviesList = new ArrayList<>();
    private Resultado resultado;
    private AdapterMovie adapterMovie;

    //    Retrofit
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Inicializar componentes
        recyclerVideos = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.searchView);

//        Configurar Retrofit
        retrofit = RetrofitConfig.getRetrofit();


//        Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Filmes");
        setSupportActionBar(toolbar);

//        Recuperar Filmes Popular
        getPopularmMovies();

//        Pesquisa

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovies(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                getPopularmMovies();
            }
        });

    }

    private void getPopularmMovies() {

        MovieService movieService = retrofit.create(MovieService.class);

        movieService.recuperarVideos(RequestConfig.CHAVE_API, "pt-br").enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {

                if (response.isSuccessful()) {
                    resultado = response.body();
                    moviesList = resultado.results;
                    configRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha ao carregar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMovies(String search) {

        String inputUser = search.replaceAll(" ", "+");

        MovieService movieService = retrofit.create(MovieService.class);

        movieService.searchMovies(RequestConfig.CHAVE_API, inputUser).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {

                if (response.isSuccessful()) {
                    resultado = response.body();
                    moviesList = resultado.results;
                    configRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Filme não encontrado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void configRecyclerView() {
        adapterMovie = new AdapterMovie(moviesList, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterMovie);

        recyclerVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                MoviesResult movie = moviesList.get(position);
                                String idMovie = movie.id;

                                Intent i = new Intent(MainActivity.this, MovieDetail.class);
                                i.putExtra("idMovie", idMovie);
                                i.putExtra("titleMovie", movie.title);
                                i.putExtra("moviePoster", movie.backdrop_path);
                                i.putExtra("movieOverview", movie.overview);
                                i.putExtra("release_date", movie.release_date);
                                i.putExtra("rating", movie.vote_average);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }

                )
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        return true;
    }
}