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
import com.example.jonat.services.Adapters.TitlesAdapter;
import com.example.jonat.services.ApiClient;
import com.example.jonat.services.ApiInterface;
import com.example.jonat.services.Models.Title;
import com.example.jonat.services.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jonat on 10/11/2017.
 */
public class TitleholdersFragment extends Fragment {
    public final static String TITLES = "title_holders";
    private static final String TAG = TitleholdersFragment.class.getSimpleName();
    public ProgressBar progressBar;
    private TitlesAdapter.Callbacks mCallbacks;
    private String mSortBy = TITLES;
    private ApiInterface apiService;
    private RecyclerView recyclerView;
    private TitlesAdapter mAdapter;

    public TitleholdersFragment() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.recyclerviewlist, container, false);


        recyclerView = rootView.findViewById(R.id.mrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number)));
        progressBar = rootView.findViewById(R.id.progress_bar);

        mCallback();

        fetchFilms(mSortBy);

        return rootView;


    }


    private void fetchFilms(String mSortBy) {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Title>> call = apiService.getTitles(mSortBy);
        call.enqueue(new Callback<List<Title>>() {
            @Override
            public void onResponse(Call<List<Title>> call, Response<List<Title>> response) {
                int statusCode = response.code();
                List<Title> items = response.body();
                recyclerView.setAdapter(mAdapter = new TitlesAdapter(items, R.layout.content_title, getActivity(), mCallbacks));
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<Title>> call, Throwable t) {
                // Log error here since request failed
                progressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, getResources().getString(R.string.noconnection));
            }
        });


    }





    public void mCallback() {
        mCallbacks = new TitlesAdapter.Callbacks() {

            @Override
            public void onItemCompleted(Title items, int position) {
                Intent intent = new Intent(getActivity(), EventDetailActivity.class);
                intent.putExtra(EventDetailActivity.Args, items);
                startActivity(intent);

            }

        };
    }
}

