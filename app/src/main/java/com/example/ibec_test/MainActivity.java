package com.example.ibec_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {

    NewsAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;

    List<News> newsList;
    ArrayList<GetNewsResponse> mModel;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.main_progress);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newsList.size() == 0)
                    return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                    if (itemPosition == (newsList.size() - 1)) {
                        // here you can fetch new data from server.
                    }
                }
            }
        });

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    // Scrolling up
//                } else {
//                    // Scrolling down
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//                    // Do something
//                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    // Do something
//                } else {
//                    // Do something
////                    progressBar.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        newsList = new ArrayList<>();
        getNews();

    }


    private void getNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api api = retrofit.create(Api.class);

        Call<GetNewsResponse> call = api.getNews();
        progressBar.setVisibility(View.GONE);

        call.enqueue(new Callback<GetNewsResponse>() {
            @Override
            public void onResponse(Call<GetNewsResponse> call, Response<GetNewsResponse> response) {
                List<News> newsList = response.body().articles;

                adapter = new NewsAdapter(newsList, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GetNewsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}