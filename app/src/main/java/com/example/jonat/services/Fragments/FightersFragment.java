package com.example.jonat.services.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jonat.services.Activities.EventDetailActivity;
import com.example.jonat.services.Adapters.FightersAdapter;
import com.example.jonat.services.ApiClient;
import com.example.jonat.services.ApiInterface;
import com.example.jonat.services.Models.Fighters;
import com.example.jonat.services.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jonat on 10/11/2017.
 */

public class FightersFragment extends Fragment {

    public final static String FIGHTERS = "fighters";
    private static final String TAG = FightersFragment.class.getSimpleName();
    public ProgressBar progressBar;
    private FightersAdapter.Callbacks mCallbacks;
    private String mSortBy = FIGHTERS;
    private ApiInterface apiService;
    private RecyclerView recyclerView;
    private FightersAdapter mAdapter;

    public FightersFragment() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.recyclerviewlist, container, false);


        recyclerView = rootView.findViewById(R.id.mrecyclerview);
        recyclerView.setHasFixedSize(true);
        int padding = getResources().getDimensionPixelSize(R.dimen.padding);
        recyclerView.setPadding(padding, padding, padding, padding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number1)));
        progressBar = rootView.findViewById(R.id.progress_bar);

        mCallback();

        fetchFilms(mSortBy);

        return rootView;

    }

    private void fetchFilms(String mSortBy) {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Fighters>> call = apiService.getFighters(mSortBy);
        call.enqueue(new Callback<List<Fighters>>() {
            @Override
            public void onResponse(Call<List<Fighters>> call, Response<List<Fighters>> response) {
                int statusCode = response.code();
                List<Fighters> items = response.body();
                recyclerView.setAdapter(mAdapter = new FightersAdapter(items, R.layout.content_fighters, getActivity(), mCallbacks));
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<Fighters>> call, Throwable t) {
                // Log error here since request failed
                progressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, getResources().getString(R.string.noconnection));
            }
        });


    }








    public void mCallback() {
        mCallbacks = new FightersAdapter.Callbacks() {

            @Override
            public void onItemCompleted(Fighters items, int position) {
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra(EventDetailActivity.Args, items);
                startActivity(intent);

            }

        };
    }
}
