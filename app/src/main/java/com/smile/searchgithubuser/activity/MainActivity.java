package com.smile.searchgithubuser.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.smile.searchgithubuser.R;
import com.smile.searchgithubuser.adapter.ListAdapter;
import com.smile.searchgithubuser.api.UserApi;
import com.smile.searchgithubuser.model.GithubUser;
import com.smile.searchgithubuser.model.Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by suryamudti on 29/10/18.
 */

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Retrofit retrofit;
    private List<GithubUser> list;
    private ListAdapter listAdapter;
    final private int TIMEOUT = 90;

    final private String BASE_URL = "https://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init progressbar
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);

        // init list and list adapter
        list = new ArrayList<>();
        listAdapter = new ListAdapter(this,list);

        // init recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);

        // init searchbar
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search Github Users");

        // set the searchview event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                loadUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
    }

    /**
     * this will load every user submit text in searchview
     * @param username is text contain user github name
     */
    private void loadUsers(final String username){
        // show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // init the http client with retrofit
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .build();
                return chain.proceed(newRequest);
            }
        }).readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // define the Api Service
        UserApi service = retrofit.create(UserApi.class);
        Call<Users> call = service.getUsers(username);

        // do call the service API
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                // check if request is successfull
                if (response.raw().isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    // clear the current list
                    list.clear();

                    // add all response body to the list
                    list.addAll(response.body().getGithubUsers());

                    Log.e("surya", "list count "+list.size());

                    // set the current list data to list adapter
                    listAdapter.notifyDataSetChanged();


                    if (list.size()>0){
                        for (GithubUser g : list){
                            // print github id to logcat if list exist
                            Log.e("surya", "message "+g.getLogin());
                        }
                    }else {
                        // showing toast if there is no result
                        Toast.makeText(getApplicationContext(),"There is no result called name "+username,Toast.LENGTH_SHORT).show();
                    }
                }else{
                    // print this message if the request is not successfull
                    Log.e("surya", "somthing wrong");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                // show this toast if the device not have internet connection
                Toast.makeText(getApplicationContext(),"Please check your network connection !",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
